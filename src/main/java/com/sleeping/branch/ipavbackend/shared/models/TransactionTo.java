package com.sleeping.branch.ipavbackend.shared.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionTo {
    private LocalDateTime timestamp;
    private Double amount;

    private String senderServerLocation;
    private String receiverServerLocation;

    private Long memoryUsage;
    private Long processingTime;

    private String senderAccountNumber;
    private String receiverAccountNumber;
}
