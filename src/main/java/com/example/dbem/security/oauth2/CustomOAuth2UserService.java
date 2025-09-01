package com.example.dbem.security.oauth2;

import com.example.dbem.entity.User;
import com.example.dbem.enums.UserRole;
import com.example.dbem.repository.UserRepository;
import com.example.dbem.security.oauth2.info.OAuth2UserInfo;
import com.example.dbem.security.oauth2.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        if (userInfo.getEmail() == null) {
            throw new OAuth2AuthenticationException("이메일 정보가 없습니다.");
        }

        Optional<User> userOptional = this.userRepository.findByEmail(userInfo.getEmail());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = User.builder()
                    .username(provider + "_" + userInfo.getProviderId())
                    .email(userInfo.getEmail())
                    .password(UUID.randomUUID().toString())
                    .name(userInfo.getName())
                    .provider(provider)
                    .providerId(userInfo.getProviderId())
                    .role(UserRole.USER)
                    .build();
            this.userRepository.save(user);
        }

        return new CustomOAuth2User(user, oAuth2User.getAttributes());
    }
}
