package com.example.dbem.repository.region;

import com.example.dbem.entity.region.District;
import com.example.dbem.entity.region.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownRepository extends JpaRepository<Town, Long> {
    List<Town> findAllByDistrict(District district);
}
