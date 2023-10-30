package com.sleeping.branch.ipavbackend.shared.models;

import com.sleeping.branch.ipavbackend.transaction.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedResult {
    private Long minId;
    private Long maxId;
    private List<Transaction> transactions;
    private Long totalTransactions;

    public PaginatedResult() {}

    public PaginatedResult(Long minId, Long maxId, List<Transaction> transactions, Long totalTransactions) {
        this.minId = minId;
        this.maxId = maxId;
        this.transactions = transactions;
        this.totalTransactions = totalTransactions;
    }
}
