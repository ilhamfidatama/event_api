package com.ilhamfidatama.event_api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ilhamfidatama.event_api.entity.EventEntity;
import com.ilhamfidatama.event_api.entity.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Event implements Serializable {

    @Nullable
    private UUID id;
    @NotEmpty
    @NotBlank
    private String name;
    @NotEmpty
    @NotBlank
    private String description;
    @NotEmpty
    @NotBlank
    private String tnc;
    @NotEmpty
    @NotBlank
    private String location;
    @NotEmpty
    @NotBlank
    private LocalDateTime startDate;
    @Nullable
    private LocalDateTime createdDate;
    @Nullable
    private LocalDateTime updatedDate;
    @Nullable
    private List<Tickets> tickets;

    public EventEntity toEventEntity() {
        return EventEntity.builder().name(name).description(description).location(location).tnc(tnc)
                .startDate(startDate).build();
    }

}
