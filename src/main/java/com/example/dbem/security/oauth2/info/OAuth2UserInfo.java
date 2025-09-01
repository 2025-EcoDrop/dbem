package com.example.dbem.security.oauth2.info;

import java.util.Map;

public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    Map<String, Object> getAttributes();
}
