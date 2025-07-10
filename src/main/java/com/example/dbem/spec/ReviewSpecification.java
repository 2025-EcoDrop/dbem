package com.example.dbem.spec;

import com.example.dbem.entity.Review;
import com.example.dbem.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {
    public static Specification<Review> authorIs(User user) {
        return (root, query, cb) -> cb.equal(root.get("author"), user);
    }

    public static Specification<Review> productNameContains(String kw) {
        return (root, query, cb) -> cb.like(root.get("productName"), "%" + kw + "%");
    }

    public static Specification<Review> reviewContains(String kw) {
        return (root, query, cb) -> cb.like(root.get("review"), "%" + kw + "%");
    }

    public static Specification<Review> searchAll(String kw, User user) {
        if (kw == null || kw.isBlank()) return null;
        return authorIs(user).and(productNameContains(kw));
    }
}
