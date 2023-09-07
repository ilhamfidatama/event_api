package com.ilhamfidatama.event_api.repository;

import com.ilhamfidatama.event_api.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistRepository extends JpaRepository<WishlistEntity, UUID> {
    List<WishlistEntity> findWishlistByUserId(UUID userId);
}
