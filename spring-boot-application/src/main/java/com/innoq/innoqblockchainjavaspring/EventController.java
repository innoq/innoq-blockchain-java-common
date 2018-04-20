package com.innoq.innoqblockchainjavaspring;

import com.innoq.blockchain.java.common.events.Event;
import com.innoq.blockchain.java.common.events.EventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class EventController {

  EventRepository eventRepository;

  public EventController(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @GetMapping("/events")
 	public Stream<Event> events() {
 		return eventRepository.getEvents();
 	}
}
