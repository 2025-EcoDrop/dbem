package com.example.dbem.repository;

import com.example.dbem.dto.booking.BookingResponseDTO;
import com.example.dbem.entity.Booking;
import com.example.dbem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByStatus(String status, Pageable pageable);
    Page<Booking> findAllByBookerAndStatus(User user, String bookingStatus, Pageable pageable);
    Page<Booking> findAllByCollectorAndStatus(User user, String name, Pageable pageable);
}
