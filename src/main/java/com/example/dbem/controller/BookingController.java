package com.example.dbem.controller;

import com.example.dbem.dto.booking.BookingRequestDTO;
import com.example.dbem.dto.booking.BookingResponseDTO;
import com.example.dbem.security.userdetails.UserDetailsImpl;
import com.example.dbem.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/booking")
@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;

    @Operation(summary = "Check Bookings", description = "Retrieves a paginated list of bookings with status REQUESTED made by the authenticated user. Supports sorting and pagination.")
    @GetMapping
    public ResponseEntity<?> getBookings(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "createdAt") String sortBy,
                                         @RequestParam(defaultValue = "desc") String sortDir) {
        Page<BookingResponseDTO> bookingPage = this.bookingService.getBookings(userDetails.getUser(), page, size, sortBy, sortDir);
        return ResponseEntity.ok(bookingPage);
    }

    @Operation(summary = "Write a Booking", description = "Creates a new Booking requested by the authenticated user.")
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDTO request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BookingResponseDTO bookingResponse = this.bookingService.createBooking(request, userDetails.getUser());
        return ResponseEntity.ok(bookingResponse);
    }

    @Operation(summary = "Check a Booking", description = "Retrieves the details of a specific booking made by the authenticated user.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BookingResponseDTO bookingResponse = this.bookingService.getBooking(id);
        return ResponseEntity.ok(bookingResponse);
    }

    @Operation(summary = "Update a Booking", description = "Updates the content of a specific booking made by the authenticated user.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@Valid @RequestBody BookingRequestDTO request, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BookingResponseDTO bookingResponse = this.bookingService.updateBooking(request, id, userDetails.getUsername());
        return ResponseEntity.ok(bookingResponse);
    }

    @Operation(summary = "Delete a Booking", description = "Deletes a specific booking made by the authenticated user.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.bookingService.deleteBooking(id, userDetails.getUsername());
        return ResponseEntity.ok("예약 삭제 완료");
    }

    @Operation(summary = "Accept a Booking", description = "Marks the booking as accepted, allowing further processing or fulfillment.")
    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl collector) {
        BookingResponseDTO bookingResponse = this.bookingService.acceptBooking(id, collector.getUser());
        return ResponseEntity.ok(bookingResponse);
    }

    @Operation(summary = "Complete a Booking", description = "Marks an accepted booking as completed by the authenticated user (collector).")
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl collector) {
        BookingResponseDTO bookingResponse = this.bookingService.completeBooking(id, collector.getUser());
        return ResponseEntity.ok(bookingResponse);
    }

    // category = {0:"내가 신청한 것 & 신청중", 1:"내가 신청한 것 & 남이 수락(완료X)", 2:"내가 신청한 것 & 남이 완료", 3:"남이 신청한 것 & 내가 수락", 4:"남이 신청한 것 & 내가 완료"}
    // category가 3이나 4이면 sortBy는 createdA가 아닌 updatedAt으로 정렬 해야함으로 updatedAt을 값으로 받도록 설계 해야함
    @Operation(summary = "Check My Bookings", description = "Retrieves a paginated list of bookings made by the authenticated user. Supports filtering by booking status (e.g., REQUESTED, IN_PROGRESS, COMPLETED).")
    @GetMapping("/mine/{category}")
    public ResponseEntity<?> getMyBookings(@PathVariable int category,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "createdAt") String sortBy,
                                           @RequestParam(defaultValue = "desc") String sortDir) {
        Page<BookingResponseDTO> bookingPage = this.bookingService.getMyBookings(category, userDetails.getUser(), page, size, sortBy, sortDir);
        return ResponseEntity.ok(bookingPage);
    }
}
