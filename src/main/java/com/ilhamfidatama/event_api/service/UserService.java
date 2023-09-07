package com.ilhamfidatama.event_api.service;

import com.ilhamfidatama.event_api.model.response.Response4xx;
import com.ilhamfidatama.event_api.entity.UserEntity;
import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.model.User;
import com.ilhamfidatama.event_api.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Response<Object> createUser(String username, String email, String fullName,
                                       String address, String password) {
        try {
            var currentDateTime = LocalDateTime.now();
            var userEntity = UserEntity.builder()
                    .username(username).fullName(fullName).emailAddress(email).address(address)
                    .password(password).createdDate(currentDateTime).updatedDate(currentDateTime)
                    .build();
            var result = userRepository.saveAndFlush(userEntity);

            return Response.builder().data(result).status("created.").build();
        } catch (ConstraintViolationException e) {
            System.out.println("log exception register "+e.getConstraintViolations());
            return Response.builder().status("failed").message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> loginUser(String username, String password) {
        try {
            var result = userRepository.findUserLogin(username, password);
            if (result.isPresent()) {
                UserEntity userEntity = result.get();
                User userData = userEntity.toModel();

                return Response.builder().data(userData).status("success").message("testing login").build();
            }
            return Response.builder().status("failed").message("testing login").build();
        } catch (Exception e) {
            return Response.builder().status("failed").message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> deleteUser(String userId) {
        var uuid = UUID.fromString(userId);

        if (userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid);
            return Response.builder().status("success").message("user has been deleted.").build();
        } else {
            return Response4xx.DATANOTFOUND.body;
        }
    }

    public Response<Object> findUserById(String userId) {
        var uuid = UUID.fromString(userId);
        Optional<UserEntity> resultDb = userRepository.findById(uuid);

        if (resultDb.isPresent()) {
            UserEntity userEntity = resultDb.get();
            User user = userEntity.toModel();
            return Response.builder()
                    .data(user)
                    .status("success")
                    .build();
        } else {
            return Response4xx.DATANOTFOUND.body;
        }
    }

}
