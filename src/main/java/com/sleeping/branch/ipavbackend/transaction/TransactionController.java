package com.sleeping.branch.ipavbackend.transaction;

import com.sleeping.branch.ipavbackend.shared.models.HighestValueTransaction;
import com.sleeping.branch.ipavbackend.shared.models.DashedLineChart;
import com.sleeping.branch.ipavbackend.shared.models.PaginatedRequest;
import com.sleeping.branch.ipavbackend.shared.models.PaginatedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/highest")
    public ResponseEntity<List<HighestValueTransaction>> getHighestTransactions() {
        List<HighestValueTransaction> transactionList = transactionService.getTransactionsWithHighestAttributes();
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping()
    public ResponseEntity<String> systemPerformance() {
        String hallo = "hallo";
        return new ResponseEntity<>(hallo, HttpStatus.OK);
    }

    @PostMapping("/chart-data")
    public ResponseEntity<DashedLineChart> postVisualizationData(@RequestBody Map<String, String> dateRange) {
        LocalDateTime from = LocalDateTime.parse(dateRange.get("from"), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime to = LocalDateTime.parse(dateRange.get("to"), DateTimeFormatter.ISO_DATE_TIME);

        DashedLineChart transactionData = transactionService.searchTransactionDataForVisualization(from, to);
        return ResponseEntity.ok(transactionData);
    }

    @PostMapping("/chart-timezone-data")
    public ResponseEntity<DashedLineChart> postVisualizationDataByTimeZone(@RequestBody Map<String, String> request) {
        String timezone = request.get("timezone");
        LocalDateTime from = LocalDateTime.parse(request.get("from"), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime to = LocalDateTime.parse(request.get("to"), DateTimeFormatter.ISO_DATE_TIME);

        DashedLineChart transactionData = transactionService.searchTransactionDataForVisualizationWithTimeZone(from, to, timezone);
        return ResponseEntity.ok(transactionData);
    }



    @PostMapping("/paginated")
    public ResponseEntity<PaginatedResult> getPaginatedTransactions(@RequestBody PaginatedRequest paginatedRequestBody) {
        PaginatedRequest paginatedRequest = new PaginatedRequest();
        paginatedRequest.setMinId(paginatedRequestBody.getMinId());
        paginatedRequest.setMaxId(paginatedRequestBody.getMaxId());
        paginatedRequest.setRequestedResults(paginatedRequestBody.getRequestedResults());

        PaginatedResult result = transactionService.getPaginatedTransactions(paginatedRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping("transaction/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteAll() {
        transactionService.deleteAllTransaction();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
