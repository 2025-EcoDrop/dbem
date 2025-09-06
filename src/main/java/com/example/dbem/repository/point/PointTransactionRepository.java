package com.example.dbem.repository.point;

import com.example.dbem.entity.User;
import com.example.dbem.entity.point.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
    Optional<PointTransaction> findBySource(String source);
    List<PointTransaction> findAllByUser(User user);
}
