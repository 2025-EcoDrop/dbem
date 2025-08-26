package com.example.dbem.repository.region;

import com.example.dbem.entity.Booking;
import com.example.dbem.entity.region.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>  {
    Optional<City> findByCity(String city);
}
