package com.example.dbem.security.userdetails;

import com.example.dbem.entity.User;
import com.example.dbem.enums.UserRole;
import com.example.dbem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional;

        if (username.contains("@")) {
            userOptional = this.userRepository.findByEmail(username);
        } else {
            userOptional = this.userRepository.findByUsername(username);
        }

        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 사용자를 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }
}
