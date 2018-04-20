package com.innoq.blockchain.java.common;

public class Event {

  public long id;
  public String event;
  public Object data;

  public Event() { }

  public Event(long id, String event, Object data) {
    this.id = id;
    this.event = event;
    this.data = data;
  }
}
