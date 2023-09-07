package com.ilhamfidatama.event_api.repository;

import com.ilhamfidatama.event_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM USERS WHERE username = :username AND password = :password", nativeQuery = true)
    Optional<UserEntity> findUserLogin(String username, String password);

}
