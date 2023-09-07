package com.ilhamfidatama.event_api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ilhamfidatama.event_api.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User implements Serializable {

    @NotEmpty
    @NotBlank
    private UUID id;
    @NotEmpty
    @NotBlank
    private String fullName;
    @NotEmpty
    @NotBlank
    private String userName;
    @NotEmpty
    @NotBlank
    private String emailAddress;
    @NotEmpty
    @NotBlank
    private String address;

    public UserEntity toEntity() {
        return UserEntity.builder().id(id).fullName(fullName).address(address).username(userName)
                .emailAddress(emailAddress).build();
    }

}
