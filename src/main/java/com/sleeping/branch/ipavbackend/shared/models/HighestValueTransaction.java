package com.sleeping.branch.ipavbackend.shared.models;

import com.sleeping.branch.ipavbackend.transaction.Transaction;
import lombok.Data;

@Data
public class HighestValueTransaction {
    private String name;
    private Transaction transaction;

    public HighestValueTransaction(String attributeName, Transaction transaction) {
        this.name = attributeName;
        this.transaction = transaction;
    }

}
