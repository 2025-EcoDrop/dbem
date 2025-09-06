package com.example.dbem.controller;

import com.example.dbem.dto.point.PointTransactionResponseDTO;
import com.example.dbem.dto.point.MyPointsInfoResponseDTO;
import com.example.dbem.security.userdetails.UserDetailsImpl;
import com.example.dbem.service.point.PointService;
import com.example.dbem.service.point.PointTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/point")
@RequiredArgsConstructor
@RestController
public class PointController {
    private final PointService pointService;
    private final PointTransactionService pointTransactionService;

    @Operation(summary = "Check user profile and point balance", description = "Retrieve the authenticated user's profile information along with their current point balance.")
    @GetMapping("/my")
    public ResponseEntity<?> getMyPointInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        MyPointsInfoResponseDTO response = this.pointService.getMyPointInfo(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Check point transaction history", description = "Retrieve the authenticated user's point transaction records, including earned and spent points.")
    @GetMapping("/record")
    public ResponseEntity<?> getMyPointRecords(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PointTransactionResponseDTO> response = this.pointTransactionService.getMyPointRecords(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
