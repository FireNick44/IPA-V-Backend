package com.sleeping.branch.ipavbackend.rabbitmq;

import com.sleeping.branch.ipavbackend.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private final TransactionService transactionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    public RabbitMQConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        LOGGER.info(String.format("Received message -> %s", message));
        transactionService.receivedMQMessage(message);
    }
}
