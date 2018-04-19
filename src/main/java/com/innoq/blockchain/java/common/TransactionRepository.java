package com.innoq.blockchain.java.common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TransactionRepository {

	private final Map<String, TransactionConfirmation> transactions;
	
	private final Deque<String> worklog;
	
	public TransactionRepository() {
		transactions = new HashMap<>();
		worklog = new ArrayDeque<>();
	}
	public void saveTransaction(TransactionConfirmation tc) {
		transactions.put(tc.transaction.id, tc);
	}
	
	public TransactionConfirmation getTransactionConfirmation(String id) {
		return transactions.get(id);
	}
	
	public void addToWorklog(TransactionConfirmation tc) {
		worklog.addLast(tc.transaction.id);
	}
	
	public void removeFromWorklog(TransactionConfirmation tc) {
		tc.confirmed = true;
		worklog.remove(tc.transaction.id);
	}
	
	public Stream<TransactionConfirmation> getWorklog() {
		return worklog.stream().map(transactions::get);
	}
}
