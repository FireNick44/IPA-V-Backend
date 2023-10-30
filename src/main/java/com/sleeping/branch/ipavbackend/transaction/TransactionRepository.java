package com.sleeping.branch.ipavbackend.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    Transaction findFirstByOrderByAmountDesc();
    Transaction findFirstByOrderByProcessingTimeDesc();
    Transaction findFirstByOrderByMemoryUsageDesc();

    @Query("SELECT t FROM Transaction t WHERE t.id >= :startId AND t.id <= :endId ORDER BY t.id ASC")
    List<Transaction> findTransactionsInRange(Long startId, Long endId);

    @Query("SELECT MIN(t.id), MAX(t.id) FROM Transaction t")
    Long[] findMinMaxIds();

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.id >= :minId AND t.id <= :maxId")
    int countTransactionsInRange(Long minId, Long maxId);

    @Query("SELECT MAX(t.id) FROM Transaction t")
    Long findNewestId();

    @Query("SELECT COUNT(t) FROM Transaction t")
    long countAllEntries();
}
