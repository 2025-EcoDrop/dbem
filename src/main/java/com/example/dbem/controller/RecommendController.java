package com.example.dbem.controller;

import com.example.dbem.dto.recommend.MedicineResponse;
import com.example.dbem.dto.recommend.SymptomRequest;
import com.example.dbem.security.userdetails.UserDetailsImpl;
import com.example.dbem.service.RecommendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/recommend")
@RequiredArgsConstructor
@RestController
public class RecommendController {
    private final RecommendService recommendService;

    @Operation(summary = "Recommend medications", description = "Generates medication recommendations based on the user's symptoms and review data.")
    @PostMapping("/rag")
    public ResponseEntity<?> recommend(@RequestBody SymptomRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<MedicineResponse> medicines = this.recommendService.recommend(request.getSymptom(), userDetails.getUser().getId());
        return ResponseEntity.ok(medicines);
    }
}
