package com.innoq.blockchain.java.common.noderegisty;

import java.util.Objects;

public class Node {

  public  String noteId;
  public  String host;

  public Node() {
  }

  public Node(String noteId, String host) {
    this.noteId = noteId;
    this.host = host;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Objects.equals(noteId, node.noteId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(noteId);
  }
}
