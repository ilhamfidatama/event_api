package com.ilhamfidatama.event_api.entity;

import com.ilhamfidatama.event_api.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fullname", nullable = false)
    @Size(min = 8, max = 50,
        message = "Full Name must have minimum 8 and maximum 50 character.")
    private String fullName;

    @Column(name = "username", nullable = false)
    @Size(min = 8, max = 15,
        message = "Username must have minimum 8 and maximum 15 character.")
    private String username;

    @Column(name = "address", nullable = false)
    @Size(min = 10, max = 250,
        message = "Address must have minimum 20 and maximum 250 character.")
    private String address;

    @Column(name = "email_address", nullable = false)
    @Email(message = "Must be email address.")
    private String emailAddress;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 30,
        message = "Password must have minimum 8 and maximum 30 character")
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public User toModel() {
        return User.builder().id(id).fullName(fullName).address(address).userName(username)
                .emailAddress(emailAddress).build();
    }

}
