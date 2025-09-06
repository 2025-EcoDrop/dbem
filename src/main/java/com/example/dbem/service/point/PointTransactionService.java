package com.example.dbem.service.point;

import com.example.dbem.dto.point.PointTransactionResponseDTO;
import com.example.dbem.entity.User;
import com.example.dbem.entity.point.PointTransaction;
import com.example.dbem.exception.custom.point.PointTransactionNotFoundException;
import com.example.dbem.repository.UserRepository;
import com.example.dbem.repository.point.PointTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointTransactionService {
    private final PointTransactionRepository pointTransactionRepository;
    private final UserRepository userRepository;

    public void savePointTransaction(String username, Integer amount, String type, Integer weeks) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 이름을 찾을 수 없습니다."));

        this.pointTransactionRepository.save(
                PointTransaction.builder()
                        .user(user)
                        .amount(amount)
                        .type(type)
                        .source("APP")
                        .expiredAt(LocalDateTime.now().plusWeeks(weeks))
                        .build()
        );
    }

    public void savePointTransaction(String username, Integer amount, String type, String source, Integer years) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 이름을 찾을 수 없습니다."));

        this.pointTransactionRepository.save(
                PointTransaction.builder()
                        .user(user)
                        .amount(amount)
                        .type(type)
                        .source(source)
                        .expiredAt(LocalDateTime.now().plusYears(years))
                        .build()
        );
    }

    public void updatePointTransaction(String username, String bookingId) {
        PointTransaction pt = this.pointTransactionRepository.findBySource(bookingId)
                .orElseThrow(() -> new PointTransactionNotFoundException("해당 유저의 포인트 이력을 찾을 수 없습니다."));

        pt.updateSource(username);
        this.pointTransactionRepository.save(pt);
    }

    public List<PointTransactionResponseDTO> getMyPointRecords(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 이름을 찾을 수 없습니다."));

        return this.pointTransactionRepository.findAllByUser(user).stream().map(PointTransactionResponseDTO::toDto).toList();
    }
}
