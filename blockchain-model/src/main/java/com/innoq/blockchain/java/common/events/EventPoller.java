package com.innoq.blockchain.java.common.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.List;

public class EventPoller {

  List<Event> poll(String host) {
    try {
      return new ObjectMapper().readValue(new URL(host).openStream(), new TypeReference<List<Event>>() {});
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
