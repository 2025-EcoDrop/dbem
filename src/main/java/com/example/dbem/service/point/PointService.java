package com.example.dbem.service.point;

import com.example.dbem.dto.point.MyPointsInfoResponseDTO;
import com.example.dbem.entity.User;
import com.example.dbem.entity.point.Point;
import com.example.dbem.exception.custom.point.PointConflictException;
import com.example.dbem.exception.custom.point.PointNotFoundException;
import com.example.dbem.exception.custom.user.UserNotFoundException;
import com.example.dbem.repository.UserRepository;
import com.example.dbem.repository.point.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public void createPointByUser(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        this.pointRepository.saveAndFlush(
                Point.builder()
                        .user(user)
                        .balance(0)
                        .build()
        );
    }

    public void gainPoints(String username, Integer amount) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Point point = this.pointRepository.findByUser(user)
                .orElseThrow(() -> new PointNotFoundException("해당 유저의 포인트를 찾을 수 없습니다."));

        point.updatePlus(amount);
        this.pointRepository.save(point);
    }

    public void payPoints(String username, Integer amount) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Point point = this.pointRepository.findByUser(user)
                .orElseThrow(() -> new PointNotFoundException("해당 유저의 포인트를 찾을 수 없습니다."));

        point.updateMinus(amount);
        this.pointRepository.save(point);
    }

    public MyPointsInfoResponseDTO getMyPointInfo(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 네임을 찾을 수 없습니다."));

        Point point = this.pointRepository.findByUser(user)
                .orElseThrow(() -> new PointNotFoundException("해당 유저의 포인트를 찾을 수 없습니다."));

        return MyPointsInfoResponseDTO.toDto(user, point);
    }

    public void checkMyPoints(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저 네임을 찾을 수 없습니다."));

        Point point = this.pointRepository.findByUser(user)
                .orElseThrow(() -> new PointNotFoundException("해당 유저의 포인트를 찾을 수 없습니다."));

        if (point.getBalance() <= 100) {
            throw new PointConflictException("포인트가 부족합니다. 다른 사용자의 수거를 도와주거나 포인트를 충전해 주세요.");
        }
    }
}
