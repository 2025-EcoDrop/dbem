package com.example.dbem.service;

import com.example.dbem.dto.recommend.AnswerResponse;
import com.example.dbem.dto.recommend.MedicineResponse;
import com.example.dbem.dto.recommend.QueryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendService {
    private final WebClient webClient;

    public List<MedicineResponse> recommend(String symptom, Long userId) {
        QueryRequest request = QueryRequest.builder().query(symptom).user_id(userId).build();

        AnswerResponse response = this.webClient.post()
                .uri("/recommend")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AnswerResponse.class)
                .block();

        assert response != null;

        List<MedicineResponse> medicines = response.getAnswer().stream().map(
                data -> MedicineResponse.builder().entpName(data.getFirst()).itemName(data.getLast()).build()
        ).toList();

        return medicines;
    }
}
