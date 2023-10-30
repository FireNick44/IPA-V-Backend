package com.sleeping.branch.ipavbackend.shared.models;

import lombok.Data;

@Data
public class PaginatedRequest {
    private Long minId;
    private Long maxId;
    private int requestedResults;

    public PaginatedRequest() {}

    public PaginatedRequest(Long minId, Long maxId, int requestedResults) {
        this.minId = minId;
        this.maxId = maxId;
        this.requestedResults = requestedResults;
    }
}
