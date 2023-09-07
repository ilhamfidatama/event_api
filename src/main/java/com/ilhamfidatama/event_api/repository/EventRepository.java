package com.ilhamfidatama.event_api.repository;

import com.ilhamfidatama.event_api.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {

}
