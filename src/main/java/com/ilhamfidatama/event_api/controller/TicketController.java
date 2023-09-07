package com.ilhamfidatama.event_api.controller;

import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "tickets", name = "Tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping(path = "/{ticketId}")
    public Response<Object> getTicketsById(@PathVariable("ticketId") String ticketsId) {
        return ticketService.getTicketById(UUID.fromString(ticketsId));
    }

    @GetMapping
    public Response<Object> getTicketByEventId(@RequestParam String eventId) {
        return ticketService.getTicketsByEventId(UUID.fromString(eventId));
    }

}
