package com.innoq.blockchain.java.common.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoq.blockchain.java.common.Block;
import com.innoq.blockchain.java.common.noderegisty.Node;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventPoller {

  public List<Event> poll(String host) {
    try {
      return parseEventList(host);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private List<Event> parseEventList(String host) throws java.io.IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.<List<Map<String, Object>>>readValue(new URL(host + "/events").openStream(), new TypeReference<List<Map<String, Object>>>() {})
        .stream()
        .flatMap(this::parseEvent)
        .collect(Collectors.toList());
  }

  private Stream<Event> parseEvent(Map<String, Object> event) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String eventType = (String) event.get("event");
      switch (eventType) {
        case "new_node":
          return Stream.of(new Event((Long) event.get("id"), eventType, objectMapper.convertValue(event.get("data"), Node.class)));
        case "new_block":
          return Stream.of(new Event((Long) event.get("id"), eventType, objectMapper.convertValue(event.get("data"), Block.class)));
        case "new_transaction":
          return Stream.of(new Event((Long) event.get("id"), eventType, objectMapper.convertValue(event.get("data"), Block.Transaction.class)));
      }
    } catch (Exception ignored) {
    }
    return Stream.empty();
  }
}
