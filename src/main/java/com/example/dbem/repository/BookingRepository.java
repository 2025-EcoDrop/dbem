package com.example.dbem.repository;

import com.example.dbem.entity.Booking;
import com.example.dbem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByStatusAndBookerUsernameNot(String status, String username, Pageable pageable);
    Page<Booking> findAllByStatusAndRegion1depthAndBookerUsernameNot(String status, String region1depth, String username, Pageable pageable);
    Page<Booking> findAllByStatusAndRegion1depthAndRegion2depthAndBookerUsernameNot(String status, String region1depth, String region2depth,String username, Pageable pageable);
    Page<Booking> findAllByStatusAndRegion1depthAndRegion2depthAndRegion3depthAndBookerUsernameNot(String status, String region1depth, String region2depth,String region3, String username, Pageable pageable);
    Page<Booking> findAllByBookerAndStatus(User user, String bookingStatus, Pageable pageable);
    Page<Booking> findAllByCollectorAndStatus(User user, String name, Pageable pageable);
}
