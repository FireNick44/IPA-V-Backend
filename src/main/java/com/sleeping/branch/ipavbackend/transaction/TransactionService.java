package com.sleeping.branch.ipavbackend.transaction;

import com.sleeping.branch.ipavbackend.shared.models.HighestValueTransaction;
import com.sleeping.branch.ipavbackend.shared.models.PaginatedRequest;
import com.sleeping.branch.ipavbackend.shared.models.PaginatedResult;
import com.sleeping.branch.ipavbackend.transaction.charts.ChartConverter;
import com.sleeping.branch.ipavbackend.shared.models.DashedLineChart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final ChartConverter chartConverter;

    public TransactionService(
            TransactionRepository transactionRepository,
            TransactionConverter transactionConverter,
            ChartConverter chartConverter
    ) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.chartConverter = chartConverter;
    }

    public void receivedMQMessage(String receivedMessage) {
        Transaction transaction = transactionConverter.convert(receivedMessage);
        log.info("Storing Transaction -> {}", transaction.toString());
        transactionRepository.save(transaction);
    }

    public void deleteAllTransaction() {
        transactionRepository.deleteAll();
    }

    public DashedLineChart searchTransactionDataForVisualization(LocalDateTime start, LocalDateTime end) {
        List<Transaction> transactions = transactionRepository.findByTimestampBetween(start, end);
        return chartConverter.getDashedLineChart(transactions);
    }

    public DashedLineChart searchTransactionDataForVisualizationWithTimeZone(LocalDateTime start, LocalDateTime end, String timezone) {
        List<Transaction> transactions = transactionRepository.findByTimestampBetween(start, end);
        return chartConverter.getDashedLineChartBySenderServerLocation(transactions, timezone);
    }


    public List<HighestValueTransaction> getTransactionsWithHighestAttributes() {
        List<HighestValueTransaction> highestValueTransactions = new ArrayList<>();

        Transaction highestAmountTransaction = transactionRepository.findFirstByOrderByAmountDesc();
        highestValueTransactions.add(highestAmountTransaction != null ? new HighestValueTransaction("Highest Amount", highestAmountTransaction) : null);

        Transaction highestProcessingTimeTransaction = transactionRepository.findFirstByOrderByProcessingTimeDesc();
        highestValueTransactions.add(highestProcessingTimeTransaction != null ? new HighestValueTransaction("Highest Processing Time", highestProcessingTimeTransaction) : null);

        Transaction highestMemoryUsageTransaction = transactionRepository.findFirstByOrderByMemoryUsageDesc();
        highestValueTransactions.add(highestMemoryUsageTransaction != null ? new HighestValueTransaction("Highest Memory Usage", highestMemoryUsageTransaction) : null);

        return highestValueTransactions;
    }

    public Long[] findMinMaxIds(int requestedResults) {
        Long newestId = transactionRepository.findNewestId();

        if (newestId != null) {
            long minId = newestId - requestedResults + 1;
            long maxId = newestId;

            return new Long[] { minId, maxId };
        } else {
            return new Long[] { 1L, 1L };
        }
    }

    public PaginatedResult getPaginatedTransactions(PaginatedRequest paginatedRequest) {
        Long minId = paginatedRequest.getMinId();
        Long maxId = paginatedRequest.getMaxId();

        if (minId == null || maxId == null) {
            Long[] minMaxIds = findMinMaxIds(paginatedRequest.getRequestedResults());
            minId = minMaxIds[0];
            maxId = minMaxIds[1];
        }

        int totalTransactionsCount = transactionRepository.countTransactionsInRange(minId, maxId);

        List<Transaction> transactions = new ArrayList<>();
        if (totalTransactionsCount > 0) {
            transactions = getTransactionsInRange(minId, maxId);
        }

        PaginatedResult paginatedResult = new PaginatedResult();
        paginatedResult.setMinId(minId);
        paginatedResult.setMaxId(maxId);
        paginatedResult.setTransactions(transactions);
        paginatedResult.setTotalTransactions(getTotalEntryCount());

        return paginatedResult;
    }

    public List<Transaction> getTransactionsInRange(Long startId, Long endId) {
        return transactionRepository.findTransactionsInRange(startId, endId);
    }

    public long getTotalEntryCount() {
        return transactionRepository.countAllEntries();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }
}
