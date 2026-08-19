package com.backroad.model;

import java.time.LocalDateTime;

public class BlockerJournal {

    private String productId;
    private String blockerType;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public BlockerJournal() {
        this.createdAt = LocalDateTime.now();
    }

    public BlockerJournal(
            String productId,
            String blockerType,
            String description,
            String status) {

        this.productId = productId;
        this.blockerType = blockerType;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBlockerType() {
        return blockerType;
    }

    public void setBlockerType(String blockerType) {
        this.blockerType = blockerType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}