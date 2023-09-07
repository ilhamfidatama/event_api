package com.ilhamfidatama.event_api.entity;

import com.ilhamfidatama.event_api.model.Tickets;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class TicketsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne(targetEntity = EventEntity.class, optional = false)
    @JoinColumn(name = "event_id", nullable = false, referencedColumnName = "id")
    private EventEntity event;

    public Tickets toModel() {
        return Tickets.builder().id(id).name(name).description(description).qty(qty).price(price)
                .startDate(startDate).endDate(endDate)
                .build();
    }

}
