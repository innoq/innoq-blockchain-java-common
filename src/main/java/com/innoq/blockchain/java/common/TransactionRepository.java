package com.innoq.blockchain.java.common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TransactionRepository {

  private final Map<String, Transaction> transactions;

  private final Deque<String> worklog;

  public TransactionRepository() {
    transactions = new HashMap<>();
    worklog = new ArrayDeque<>();
  }

  public void saveTransaction(Transaction tc) {
    transactions.put(tc.id, tc);
  }

  public Transaction getTransactionConfirmation(String id) {
    return transactions.get(id);
  }

  public void addToWorklog(Transaction tc) {
    worklog.addLast(tc.id);
  }

  public void removeFromWorklog(Transaction tc) {
    tc.confirmed = true;
    worklog.remove(tc.id);
  }

  public Stream<Transaction> getWorklog() {
    return worklog.stream().map(transactions::get);
  }
}
