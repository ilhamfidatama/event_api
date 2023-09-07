package com.ilhamfidatama.event_api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ilhamfidatama.event_api.entity.UserEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Register implements Serializable {
    @NotEmpty
    @NotBlank
    private String fullname;
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    private String address;
    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    public UserEntity toEntity() {
        return UserEntity.builder().fullName(fullname).username(username).emailAddress(email)
                .address(address).password(password)
                .build();
    }
}
