package com.example.dbem.service;

import com.example.dbem.dto.booking.BookingRequestDTO;
import com.example.dbem.dto.booking.BookingResponseDTO;
import com.example.dbem.entity.Booking;
import com.example.dbem.entity.User;
import com.example.dbem.enums.BookingStatus;
import com.example.dbem.exception.custom.booking.BookingForbiddenException;
import com.example.dbem.exception.custom.booking.BookingNotFoundException;
import com.example.dbem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingResponseDTO createBooking(BookingRequestDTO request, User user) {
        Booking booking = this.bookingRepository.saveAndFlush(
                Booking.builder()
                        .content(request.getContent())
                        .booker(user)
                        .address(request.getAddress())
                        .region_1depth(request.getRegion_1depth())
                        .region_2depth(request.getRegion_2depth())
                        .region_3depth(request.getRegion_3depth())
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .status(BookingStatus.REQUESTED.toString())
                        .build());

        return BookingResponseDTO.toDto(booking);
    }

    public BookingResponseDTO getBooking(Long id) {
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("해당 예약을 찾을 수 없습니다."));

        return BookingResponseDTO.toDto(booking);
    }

    public BookingResponseDTO updateBooking(BookingRequestDTO request, Long id, String username) {
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("해당 예약을 찾을 수 없습니다."));

        if (!booking.getBooker().getUsername().equals(username)) {
            throw new BookingForbiddenException("해당 예약에 접근 권한이 없습니다.");
        }

        booking.update(
                request.getContent(),
                request.getAddress(),
                request.getRegion_1depth(),
                request.getRegion_2depth(),
                request.getRegion_3depth(),
                request.getLatitude(),
                request.getLongitude());

        booking = this.bookingRepository.saveAndFlush(booking);

        return BookingResponseDTO.toDto(booking);
    }

    public void deleteBooking(Long id, String username) {
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("해당 예약을 찾을 수 없습니다."));

        if (!booking.getBooker().getUsername().equals(username)) {
            throw new BookingForbiddenException("해당 예약에 접근 권한이 없습니다.");
        }

        this.bookingRepository.delete(booking);
    }

    public BookingResponseDTO acceptBooking(Long id, User user) {
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("해당 예약을 찾을 수 없습니다."));

        booking.accept(BookingStatus.IN_PROGRESS.name(), user);
        booking = this.bookingRepository.saveAndFlush(booking);

        return BookingResponseDTO.toDto(booking);
    }

    public BookingResponseDTO completeBooking(Long id, User user) {
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("해당 예약을 찾을 수 없습니다."));

        if (!booking.getCollector().getUsername().equals(user.getUsername())) {
            throw new BookingForbiddenException("해당 예약에 접근 권한이 없습니다.");
        }

        booking.complete(BookingStatus.COMPLETED.name());
        booking = this.bookingRepository.saveAndFlush(booking);

        return BookingResponseDTO.toDto(booking);
    }

    // category = {0:"내가 신청한 것 & 신청중", 1:"내가 신청한 것 & 남이 수락(완료X)", 2:"내가 신청한 것 & 남이 완료", 3:"남이 신청한 것 & 내가 수락", 4:"남이 신청한 것 & 내가 완료"}
    public Page<BookingResponseDTO> getMyBookings(int category, User user, int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        if (category == 0) {
            return this.bookingRepository.findAllByBookerAndStatus(user, BookingStatus.REQUESTED.name(), pageable).map(BookingResponseDTO::toDto);
        } else if (category == 1) {
            return this.bookingRepository.findAllByBookerAndStatus(user, BookingStatus.IN_PROGRESS.name(), pageable).map(BookingResponseDTO::toDto);
        } else if (category == 2) {
            return this.bookingRepository.findAllByBookerAndStatus(user, BookingStatus.COMPLETED.name(), pageable).map(BookingResponseDTO::toDto);
        } else if (category == 3) {
            return this.bookingRepository.findAllByCollectorAndStatus(user, BookingStatus.IN_PROGRESS.name(),  pageable).map(BookingResponseDTO::toDto);
        } else {
            return this.bookingRepository.findAllByCollectorAndStatus(user, BookingStatus.COMPLETED.name(),  pageable).map(BookingResponseDTO::toDto);
        }
    }
}
