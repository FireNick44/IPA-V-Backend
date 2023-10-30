package com.sleeping.branch.ipavbackend.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class Transaction implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private Double amount;

    @Column(name = "sender_server_location")
    private String senderServerLocation;

    @Column(name = "receiver_server_location")
    private String receiverServerLocation;

    @Column(name = "memory_usage")
    private Long memoryUsage;

    @Column(name = "processing_time")
    private Long processingTime;

    @Column(name = "sender_account_number")
    private String senderAccountNumber;

    @Column(name = "receiver_account_number")
    private String receiverAccountNumber;

    @Override
    public String toString() {
        return "{ " +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                ", senderServerLocation='" + senderServerLocation + '\'' +
                ", receiverServerLocation='" + receiverServerLocation + '\'' +
                ", memoryUsage=" + memoryUsage +
                ", processingTime=" + processingTime +
                ", senderAccountNumber='" + senderAccountNumber + '\'' +
                ", receiverAccountNumber='" + receiverAccountNumber + '\'' +
                " }";
    }
}
