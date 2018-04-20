package com.innoq.blockchain.java.common.implementation;

import com.innoq.blockchain.java.common.Transaction;

import java.util.*;
import java.util.stream.Stream;

public class TransactionRepository {

  private final Map<String, Transaction> transactions;

  private final Deque<String> worklog;

  public TransactionRepository() {
    transactions = new HashMap<>();
    worklog = new ArrayDeque<>();
  }

  public Transaction getTransaction(String id) {
    return transactions.get(id);
  }

  public void addToWorklog(Transaction tc) {
    worklog.addLast(tc.id);
    saveTransaction(tc);
  }

  public void removeFromWorklog(Transaction tc) {
    worklog.remove(tc.id);
    saveTransaction(tc);
  }

  public Stream<Transaction> getWorklog() {
    return worklog.stream().map(transactions::get);
  }

  public void replaceTransactions(List<Transaction> transactions) {
    transactions.clear();
    transactions.addAll(transactions);
  }

  private void saveTransaction(Transaction tc) {
    transactions.put(tc.id, tc);
  }
}
