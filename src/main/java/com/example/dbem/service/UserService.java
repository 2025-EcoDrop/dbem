package com.example.dbem.service;

import com.example.dbem.dto.user.UsernameCheckResponseDTO;
import com.example.dbem.entity.User;
import com.example.dbem.enums.UserRole;
import com.example.dbem.exception.custom.user.InvalidPasswordFormatException;
import com.example.dbem.exception.custom.user.InvalidRefreshTokenException;
import com.example.dbem.exception.custom.user.UnauthorizedAccessException;
import com.example.dbem.exception.custom.user.UserExistsException;
import com.example.dbem.repository.UserRepository;
import com.example.dbem.security.jwt.JwtTokenProvider;
import com.example.dbem.security.userdetails.UserDetailsImpl;
import com.example.dbem.dto.user.LoginRequestDTO;
import com.example.dbem.dto.user.SignupRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    public UsernameCheckResponseDTO checkUsername(String username) {
        boolean exists = this.userRepository.existsByUsername(username);

        return UsernameCheckResponseDTO.builder()
                .exists(!exists)
                .message(
                        exists
                        ? "이미 존재하는 아이디입니다. 사용 불가능한 아이디입니다."
                        :"사용 가능한 아이디입니다."
                )
                .build();
    }

    public String checkAuth(User user) {
        if (user == null) {
            throw new UnauthorizedAccessException("로그인이 필요합니다.");
        } else {
            return user.getUsername();
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 20) {
            throw new InvalidPasswordFormatException("비밀번호는 8자 이상 20자 이하로 입력해주세요.");
        }
        if (!password.matches(".*[A-Za-z].*")) {
            throw new InvalidPasswordFormatException("비밀번호에 영문자를 최소 1자 이상 포함해야 합니다.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new InvalidPasswordFormatException("비밀번호에 숫자를 최소 1자 이상 포함해야 합니다.");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            throw new InvalidPasswordFormatException("비밀번호에 특수문자를 최소 1자 이상 포함해야 합니다. (사용 가능한 특수 문자: !, @, #, $, %, ^, &, *)");
        }
        if (!password.matches("^[A-Za-z0-9!@#$%^&*]+$")) {
            throw new InvalidPasswordFormatException("허용되지 않은 문자가 포함되어 있습니다.");
        }
    }

    @Transactional
    public void signup(SignupRequestDTO dto) {
        if (!this.userRepository.existsByUsername(dto.getUsername())) {
            this.userRepository.save(
                    SignupRequestDTO.toModel(dto, UserRole.USER, passwordEncoder)
            );
        } else {
            throw new UserExistsException("이미 존재하는 사용자 이름 입니다.");
        }
    }

    public void login(LoginRequestDTO dto, HttpServletResponse response) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = this.jwtTokenProvider.createToken(userDetails.getUsername());
        String refreshToken = this.jwtTokenProvider.createRefreshToken(userDetails.getUsername());

        response.setHeader(HttpHeaders.SET_COOKIE, setCookie(accessToken));
        response.addHeader(HttpHeaders.SET_COOKIE, setRefreshCookie(refreshToken));

        this.redisTemplate.opsForValue().set("RT:" + userDetails.getUsername(), refreshToken, 7, TimeUnit.DAYS);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractCookie(request, "refreshToken");

        if (refreshToken != null && this.jwtTokenProvider.isTokenValid(refreshToken)) {
            String username = this.jwtTokenProvider.getUsername(refreshToken);
            this.redisTemplate.delete("RT:" + username);
        }

        response.setHeader(HttpHeaders.SET_COOKIE, deleteCookie("jwt"));
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie("refreshToken"));
    }

    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractCookie(request, "refreshToken");
        if (refreshToken == null || !this.jwtTokenProvider.isTokenValid(refreshToken)) {
            throw new InvalidRefreshTokenException("유효하지 않은 Refresh Token");
        }

        String username = this.jwtTokenProvider.getUsername(refreshToken);
        String redisRefresh = this.redisTemplate.opsForValue().get("RT:" + username);

        if (!refreshToken.equals(redisRefresh)) {
            throw new InvalidRefreshTokenException("Refresh Token 불일치");
        }

        String newAccessToken = this.jwtTokenProvider.createToken(username);
        String newRefreshToken = this.jwtTokenProvider.createRefreshToken(username);

        this.redisTemplate.opsForValue().set("RT:" + username, newRefreshToken, 7, TimeUnit.DAYS);

        response.setHeader(HttpHeaders.SET_COOKIE, setCookie(newAccessToken));
        response.addHeader(HttpHeaders.SET_COOKIE, setRefreshCookie(newRefreshToken));
    }

    private String setCookie(String accessToken) {
        return ResponseCookie.from("jwt", accessToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build()
                .toString();
    }

    private String setRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(7))
                .build()
                .toString();
    }

    private String deleteCookie(String type) {
        return ResponseCookie.from(type, "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build()
                .toString();
    }

    private String extractCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
