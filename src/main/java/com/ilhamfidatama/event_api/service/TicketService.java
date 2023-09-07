package com.ilhamfidatama.event_api.service;

import com.ilhamfidatama.event_api.entity.EventEntity;
import com.ilhamfidatama.event_api.entity.TicketsEntity;
import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.model.Tickets;
import com.ilhamfidatama.event_api.model.response.Response4xx;
import com.ilhamfidatama.event_api.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ilhamfidatama.event_api.model.response.Status.FAILED;
import static com.ilhamfidatama.event_api.model.response.Status.SUCCESS;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public Response<Object> createTickets(List<Tickets> ticketsList, EventEntity event) {
        try {
            List<TicketsEntity> ticketsEntityList = ticketsList.stream().map(tickets -> {
                var entity = tickets.toEntity();
                entity.setEvent(event);
                return entity;
            }).toList();
            List<TicketsEntity> ticketsSaved = ticketRepository.saveAllAndFlush(ticketsEntityList);
            if (ticketsSaved.isEmpty()) {
                return Response.builder().status(FAILED.value).message("Tickets not saved").build();
            }
            return Response.builder().status(SUCCESS.value).data(ticketsSaved).build();
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> getTicketById(UUID ticketId) {
        try {
            Optional<TicketsEntity> ticketDb = ticketRepository.findById(ticketId);
            if (ticketDb.isPresent()) {
                Tickets tickets = ticketDb.get().toModel();
                return Response.builder().status(SUCCESS.value).data(tickets).build();
            }
            return Response4xx.DATANOTFOUND.body;
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> getTicketsByEventId(UUID eventId) {
        try {
            Optional<List<TicketsEntity>> ticketsListDb = ticketRepository.filterTicketsByEventId(eventId);
            if (ticketsListDb.isPresent()) {
                List<Tickets> ticketsList = ticketsListDb.get().stream().map(TicketsEntity::toModel).toList();
                return Response.builder().status(SUCCESS.value).data(ticketsList).build();
            }
            return Response4xx.DATANOTFOUND.body;
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

}