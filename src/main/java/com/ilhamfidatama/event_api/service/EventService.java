package com.ilhamfidatama.event_api.service;

import com.ilhamfidatama.event_api.model.response.Response4xx;
import com.ilhamfidatama.event_api.entity.EventEntity;
import com.ilhamfidatama.event_api.entity.TicketsEntity;
import com.ilhamfidatama.event_api.entity.UserEntity;
import com.ilhamfidatama.event_api.model.Event;
import com.ilhamfidatama.event_api.model.Response;
import com.ilhamfidatama.event_api.model.Tickets;
import com.ilhamfidatama.event_api.repository.EventRepository;
import com.ilhamfidatama.event_api.repository.TicketRepository;
import com.ilhamfidatama.event_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ilhamfidatama.event_api.model.response.Status.FAILED;
import static com.ilhamfidatama.event_api.model.response.Status.SUCCESS;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;

    public Response<Object> createEvent(Event event, String userId) {
        try {
            var currentDate = LocalDateTime.now();
            // find users
            Optional<UserEntity> userData = userRepository.findById(UUID.fromString(userId));
            // conditional for userId is present
            if (userData.isPresent()) {
                UserEntity organizer = userData.get();
                // convert to entity class
                EventEntity eventEntity = event.toEventEntity();
                eventEntity.setOrganizer(organizer);
                eventEntity.setCreatedDate(currentDate);
                eventEntity.setUpdatedDate(currentDate);
                // save event
                var eventSaved = eventRepository.saveAndFlush(eventEntity);
                // save ticket
                var tickets = this.savedTicket(event.getTickets(), eventSaved);

                var eventSuccess = eventSaved.toModel();
                eventSuccess.setTickets(tickets.stream().map(TicketsEntity::toModel).toList());
                return Response.builder().status(SUCCESS.value).data(eventSuccess).build();
            }
            return Response4xx.USERNOTAUTHORIZED.body;
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    private List<TicketsEntity> savedTicket(List<Tickets> ticketsList, EventEntity event) {
        return ticketsList.stream().map((ticket) -> {
            TicketsEntity entity = ticket.toEntity();
            entity.setEvent(event);
            return ticketRepository.saveAndFlush(entity);
        }).toList();
    }

    public Response<Object> getEvents() {
        try {
            List<EventEntity> eventEntityList = eventRepository.findAll();
            List<Event> eventList = eventEntityList.stream().map((eventEntity) -> {
                Event event = eventEntity.toModel();
                List<Tickets> tickets = this.getTicketsByEventId(event.getId());
                event.setTickets(tickets);
                return event;
            }).toList();
            if (!eventList.isEmpty()) {
                return Response.builder().data(eventList).status(SUCCESS.value).build();
            }
            return Response4xx.DATANOTFOUND.body;
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> getEventById(UUID eventId) {
        try {
            Optional<EventEntity> eventEntityDb = eventRepository.findById(eventId);
            if (eventEntityDb.isPresent()) {
                Event event = eventEntityDb.get().toModel();
                List<Tickets> ticketsList = this.getTicketsByEventId(event.getId());
                event.setTickets(ticketsList);
                return Response.builder().status(SUCCESS.value).data(event).build();
            }
            return Response4xx.DATANOTFOUND.body;
        } catch (Exception e) {
            return Response.builder().status("failed").message(e.getLocalizedMessage()).build();
        }
    }

    public Response<Object> updateEvent(UUID userId, UUID eventId, Event event) {
        try {
            Optional<EventEntity> eventEntityResult = eventRepository.findById(eventId);
            if (eventEntityResult.isPresent()) {
                EventEntity eventEntity = eventEntityResult.get();
                if (isUserAuthorizedEvent(userId, eventEntity)) {
                    EventEntity newEventEntity = event.toEventEntity();
                    newEventEntity.setId(eventId);
                    EventEntity savedEvent = eventRepository.saveAndFlush(newEventEntity);
                    return Response.builder().data(savedEvent).status(SUCCESS.value).build();
                }
                return Response4xx.USERNOTAUTHORIZED.body;
            }
            return Response4xx.DATANOTFOUND.body;
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    private Boolean isUserAuthorizedEvent(UUID userId, EventEntity eventEntity) {
        return eventEntity.getId().equals(userId);
    }

    private Boolean isUserAuthorizedTickets(UUID userId, TicketsEntity ticketsEntity){
        return ticketsEntity.getId().equals(userId);
    }

    public Response<Object> deleteEvent(UUID userId, UUID eventId) {
        try {
            Optional<EventEntity> eventEntity = eventRepository.findById(eventId);
            if (eventEntity.isPresent()) {
                EventEntity event = eventEntity.get();
                if (isUserAuthorizedEvent(userId, event)) {
                    eventRepository.delete(event);
                    return Response.builder().status(SUCCESS.value).message("event has been deleted.").build();
                } else {
                    return Response4xx.USERNOTAUTHORIZED.body;
                }
            } else {
                return Response4xx.DATANOTFOUND.body;
            }
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

    private List<Tickets> getTicketsByEventId(UUID eventId) {
        List<TicketsEntity> ticketsDb = ticketRepository.findTicketsByEventId(eventId);
        return ticketsDb.stream().map(TicketsEntity::toModel).toList();
    }

    public Response<Object> udpdateTickets(UUID userId, UUID ticketId, Tickets newTickets) {
        try {
            Optional<TicketsEntity> ticketsEntity = ticketRepository.findById(ticketId);
            if (ticketsEntity.isPresent()) {
                TicketsEntity tickets = ticketsEntity.get();
                if (isUserAuthorizedTickets(userId, tickets)) {
                    TicketsEntity newTicketsEntity = newTickets.toEntity();
                    newTicketsEntity.setId(ticketId);
                    TicketsEntity savedTicket = ticketRepository.saveAndFlush(newTicketsEntity);
                    return Response.builder().data(savedTicket.toModel()).status(SUCCESS.value)
                            .message("ticket has been updated.").build();
                } else {
                    return Response4xx.USERNOTAUTHORIZED.body;
                }
            } else {
                return Response4xx.DATANOTFOUND.body;
            }
        } catch (Exception e) {
            return Response.builder().status(FAILED.value).message(e.getLocalizedMessage()).build();
        }
    }

}
