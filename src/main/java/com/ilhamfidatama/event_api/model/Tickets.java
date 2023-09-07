package com.ilhamfidatama.event_api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ilhamfidatama.event_api.entity.TicketsEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Tickets implements Serializable {

    private UUID id;
    @NotEmpty
    @NotBlank
    @Size(min = 7)
    private String name;
    @NotEmpty
    @NotBlank
    @Min(1)
    private int qty;
    @NotEmpty
    @NotBlank
    @Min(1000L)
    private Long price;
    @NotEmpty
    @NotBlank
    @Size(min = 30)
    private String description;
    @NotEmpty
    @NotBlank
    @NotNull
    private LocalDateTime startDate;
    @NotEmpty
    @NotBlank
    @NotNull
    private LocalDateTime endDate;

    public TicketsEntity toEntity() {
        return TicketsEntity.builder().id(id).name(name).description(description).startDate(startDate)
                .price(price).endDate(endDate).build();
    }

}
