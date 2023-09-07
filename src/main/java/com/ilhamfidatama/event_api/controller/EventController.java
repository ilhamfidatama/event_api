package com.ilhamfidatama.event_api.controller;

import com.ilhamfidatama.event_api.model.Event;
import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.model.Tickets;
import com.ilhamfidatama.event_api.model.request.EventRequest;
import com.ilhamfidatama.event_api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "event", name = "event")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping
    public Response<Object> createEvent(
            @RequestBody Event event,
            @RequestParam(name = "user_id") String userId){
        return eventService.createEvent(event, userId);
    }

    @GetMapping
    public Response<Object> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/{eventId}")
    public Response<Object> getEventById(@PathVariable String eventId) {
        return eventService.getEventById(UUID.fromString(eventId));
    }

    @PutMapping("/{eventId}")
    public Response<Object> updateEvent(@RequestBody EventRequest eventRequest, @PathVariable String eventId) {
        return eventService.updateEvent(eventRequest.getUserId(), UUID.fromString(eventId), eventRequest.getEvent());
    }

    @DeleteMapping("/{eventId}")
    public Response<Object> deleteEvent(
            @PathVariable String eventId,
            @RequestParam(name = "user_id") String userId) {
        return eventService.deleteEvent(UUID.fromString(userId), UUID.fromString(eventId));
    }

    @PutMapping("/{eventId}/tickets/{ticketId}")
    public Response<Object> updateTickets(
            @PathVariable String eventId,
            @PathVariable String ticketId,
            @RequestParam(name = "user_id") String userId,
            @RequestBody Tickets tickets
            ) {
        return eventService.udpdateTickets(UUID.fromString(userId), UUID.fromString(eventId), tickets);
    }

}
