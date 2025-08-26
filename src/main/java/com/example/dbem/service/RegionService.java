package com.example.dbem.service;

import com.example.dbem.dto.region.CityResponse;
import com.example.dbem.dto.region.DistrictResponse;
import com.example.dbem.dto.region.TownResponse;
import com.example.dbem.entity.region.City;
import com.example.dbem.entity.region.District;
import com.example.dbem.repository.region.CityRepository;
import com.example.dbem.repository.region.DistrictRepository;
import com.example.dbem.repository.region.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;

    public List<CityResponse> getCities() {
        return this.cityRepository.findAll().stream().map(CityResponse::toDto).toList();
    }

    public List<DistrictResponse> getDistricts(String city) {
        City findCity = this.cityRepository.findByCity(city)
                .orElseThrow(() -> new RuntimeException(""));
        return this.districtRepository.findAllByCity(findCity).stream().map(DistrictResponse::toDto).toList();
    }

    public List<TownResponse> getTowns(String district) {
        District findDistrict = this.districtRepository.findByDistrict(district)
                .orElseThrow(() -> new RuntimeException(""));
        return this.townRepository.findAllByDistrict(findDistrict).stream().map(TownResponse::toDto).toList();
    }
}
