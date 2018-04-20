package com.innoq.blockchain.java.common.implementation.events;

import com.innoq.blockchain.java.common.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EventRepository {

  final List<Event> events;

  public EventRepository() {
    this.events = new ArrayList<>();
  }

  public Stream<Event> getEvents() {
    return events.stream();
  }

  public void storeEvent(Event event) {
    events.add(event);
  }
}
