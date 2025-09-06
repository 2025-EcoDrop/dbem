package com.example.dbem.repository.point;

import com.example.dbem.entity.User;
import com.example.dbem.entity.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByUser(User user);
}
