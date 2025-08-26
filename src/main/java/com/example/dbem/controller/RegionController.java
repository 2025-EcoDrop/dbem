package com.example.dbem.controller;

import com.example.dbem.dto.region.CityResponse;
import com.example.dbem.dto.region.DistrictResponse;
import com.example.dbem.dto.region.TownResponse;
import com.example.dbem.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/region")
@RequiredArgsConstructor
@RestController
public class RegionController {
    private final RegionService regionService;

    @Operation(summary = "Get Cities", description = "Retrieves a list of cities in South Korea for use in region selection.")
    @GetMapping("/city")
    public ResponseEntity<?> getCities() {
        List<CityResponse> cities= this.regionService.getCities();
        return ResponseEntity.ok(cities);
    }

    @Operation(summary = "Get Districts by City", description = "Retrieves a list of districts belonging to the specified city in South Korea for region selection.")
    @GetMapping("/{city}/district")
    public ResponseEntity<?> getDistricts(@PathVariable String city) {
        List<DistrictResponse> districts= this.regionService.getDistricts(city);
        return ResponseEntity.ok(districts);
    }

    @Operation(summary = "Get Towns by District", description = "Retrieves a list of towns belonging to the specified district in South Korea for detailed region selection.")
    @GetMapping("/{district}/town")
    public ResponseEntity<?> getTowns(@PathVariable String district) {
        List<TownResponse> towns= this.regionService.getTowns(district);
        return ResponseEntity.ok(towns);
    }
}
