package com.sleeping.branch.ipavbackend.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.sleeping.branch.ipavbackend.shared.models.TransactionTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionConverter {
    public TransactionTo convert(Transaction transaction) {
        TransactionTo transactionTo = new TransactionTo();
        transactionTo.setTimestamp(transaction.getTimestamp());
        transactionTo.setAmount(transaction.getAmount());
        transactionTo.setSenderServerLocation(transaction.getSenderServerLocation());
        transactionTo.setReceiverServerLocation(transaction.getReceiverServerLocation());
        transactionTo.setMemoryUsage(transaction.getMemoryUsage());
        transactionTo.setProcessingTime(transaction.getProcessingTime());
        transactionTo.setSenderAccountNumber(transaction.getSenderAccountNumber());
        transactionTo.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
        return transactionTo;
    }

    public Transaction convert(TransactionTo transactionTo) {
        Transaction transaction = new Transaction();
        transaction.setId(0L);
        transaction.setTimestamp(transactionTo.getTimestamp());
        transaction.setAmount(transactionTo.getAmount());
        transaction.setSenderServerLocation(transactionTo.getSenderServerLocation());
        transaction.setReceiverServerLocation(transactionTo.getReceiverServerLocation());
        transaction.setMemoryUsage(transactionTo.getMemoryUsage());
        transaction.setProcessingTime(transactionTo.getProcessingTime());
        transaction.setSenderAccountNumber(transactionTo.getSenderAccountNumber());
        transaction.setReceiverAccountNumber(transactionTo.getReceiverAccountNumber());
        return transaction;
    }

    public Transaction convert(String message) {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        try {
            TransactionTo receivedTransaction = objectMapper.readValue(message, TransactionTo.class);
            return convert(receivedTransaction);
        } catch (JsonProcessingException e) {
            log.error("Error while deserializing MQ-Message to JSON: {}", e.getMessage());
            throw new RuntimeException("Error while deserializing JSON", e);
        }
    }
}
