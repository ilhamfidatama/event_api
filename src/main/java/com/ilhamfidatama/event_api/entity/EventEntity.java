package com.ilhamfidatama.event_api.entity;

import com.ilhamfidatama.event_api.model.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    @Size(min = 10, max = 30,
        message = "Event name must have minimum 10 and maximum 30 character")
    private String name;

    @Column(name = "description", nullable = false)
    @Size(min = 100, max = 500,
            message = "Event description must have minimum 100 and maximum 500 character")
    private String description;

    @Column(name = "term_and_condition", nullable = false)
    @Size(min = 100, max = 700,
            message = "Event Term and Condition must have minimum 100 and maximum 700 character")
    private String tnc;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "start_date", nullable = false)
    @DateTimeFormat
    private LocalDateTime startDate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @ManyToOne(targetEntity = UserEntity.class, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private UserEntity organizer;

    public Event toModel() {
        return Event.builder().id(id).name(name).description(description).tnc(tnc).location(location)
                .startDate(startDate).createdDate(createdDate).updatedDate(updatedDate)
                .build();
    }

}
