package com.example.dbem.service;

import com.example.dbem.dto.open.response.ApiResponse;
import com.example.dbem.dto.open.response.Item;
import com.example.dbem.exception.custom.PublicDataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicDataService {
    @Value("${public.api.base-url}")
    private String baseUrl;

    @Value("${public.api.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    public List<String> getNames(String keyword) {
        String encodedKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", encodedKey)
                .queryParam("itemName", encodedKeyword)
                .queryParam("type", "json")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 30)
                .build(true)  // 이미 인코딩된 문자열은 그대로 유지
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ApiResponse> response = this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                ApiResponse.class);

        if (response.getBody() == null || response.getBody().getBody().getItems() == null) {
            throw new PublicDataNotFoundException("검색 결과가 없습니다.");
        } else {
            return response.getBody().getBody().getItems().stream().map(Item::getItemName).toList();
        }
    }
}