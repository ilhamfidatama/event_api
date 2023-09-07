package com.ilhamfidatama.event_api.service;

import com.ilhamfidatama.event_api.model.response.Response4xx;
import com.ilhamfidatama.event_api.model.response.Status;
import com.ilhamfidatama.event_api.entity.EventEntity;
import com.ilhamfidatama.event_api.entity.UserEntity;
import com.ilhamfidatama.event_api.entity.WishlistEntity;
import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.model.Wishlist;
import com.ilhamfidatama.event_api.repository.EventRepository;
import com.ilhamfidatama.event_api.repository.UserRepository;
import com.ilhamfidatama.event_api.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventRepository eventRepository;

    public Response<Object> addWishlist(UUID userId, UUID eventId) {
        try {
            Optional<UserEntity> userEntityDb = userRepository.findById(userId);
            Optional<EventEntity> eventEntityDb = eventRepository.findById(eventId);
            if (userEntityDb.isPresent() && eventEntityDb.isPresent()) {
                UserEntity userEntity = userEntityDb.get();
                EventEntity eventEntity = eventEntityDb.get();
                LocalDateTime currentDateTime = LocalDateTime.now();
                WishlistEntity wishlistEntity = WishlistEntity.builder()
                        .user(userEntity).event(eventEntity).createdDate(currentDateTime)
                        .updatedDate(currentDateTime).build();
                WishlistEntity savedWishlist = wishlistRepository.saveAndFlush(wishlistEntity);
                return Response.builder().data(savedWishlist).status(Status.SUCCESS.value).build();
            } else {
                return Response4xx.DATANOTFOUND.body;
            }
        }catch (Exception e) {
            return Response.builder().status("failed").message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> getWishlistByUser(UUID userId) {
        try {
            List<WishlistEntity> wishlistEntityList = wishlistRepository.findWishlistByUserId(userId);
            if (wishlistEntityList.isEmpty()) {
                return Response.builder().status(Status.SUCCESS.value).message("Wishlist is empty.").build();
            }
            List<Wishlist> wishlistList = wishlistEntityList.stream().map((wishlistEntity -> {
                Wishlist wishlist = wishlistEntity.toModel();
                Optional<EventEntity> eventEntityDb = eventRepository.findById(wishlistEntity.getEvent().getId());
                if (eventEntityDb.isPresent()) {
                    EventEntity eventEntity = eventEntityDb.get();
                    wishlist.setEvent(eventEntity.toModel());
                    return wishlist;
                }
                return wishlist;
            })).toList();
            return Response.builder().data(wishlistList).status(Status.SUCCESS.value).build();
        } catch (Exception e) {
            return Response.builder().status(Status.FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> deleteWishlist(UUID userId, UUID wishlistId) {
        try {
            Optional<WishlistEntity> wishlistEntityDb = wishlistRepository.findById(wishlistId);
            if (wishlistEntityDb.isPresent()) {
                WishlistEntity wishlistEntity = wishlistEntityDb.get();
                if (wishlistEntity.getUser().getId().equals(userId)) {
                    wishlistRepository.deleteById(wishlistId);
                    return Response.builder().status(Status.SUCCESS.value)
                            .message("Wishlist has been deleted.").build();
                } else {
                    return Response4xx.USERNOTAUTHORIZED.body;
                }
            } else {
                return Response4xx.DATANOTFOUND.body;
            }
        } catch (Exception e) {
            return Response.builder().status("failed").message(e.getLocalizedMessage()).build();
        }
    }

}
