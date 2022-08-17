package com.sparta.miniproject.repository;

import com.sparta.miniproject.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByKey(String Key);
    void deleteByKey(String key);
}
