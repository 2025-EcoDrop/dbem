package com.example.dbem.repository.region;

import com.example.dbem.entity.region.City;
import com.example.dbem.entity.region.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findAllByCity(City city);
    Optional<District> findByCityAndDistrict(City city, String district);
}
