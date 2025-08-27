package com.example.dbem.service;

import com.example.dbem.exception.custom.distance.DistanceUnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DistanceService {
    public void calculateDistance(double lat1, double lon1, double lat2, double lon2, int distance) {
        final int R = 6371000; // 지구 반지름 (m)

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        if (R * c > distance) {
            throw new DistanceUnprocessableEntityException("예약 위치와 너무 멀리 위치합니다.");
        }
    }
}
