package com.ilhamfidatama.event_api.repository;

import com.ilhamfidatama.event_api.entity.TicketsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketsEntity, UUID> {
    List<TicketsEntity> findTicketsByEventId(UUID eventId);

}
