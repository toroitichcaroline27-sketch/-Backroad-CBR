package com.backroad.service;

import org.springframework.stereotype.Service;

@Deprecated(since = "2.0.0", forRemoval = true)
@Service
public class WarehousePollingScheduler {

    @Deprecated
    public void pollWarehouseApi() {
        throw new UnsupportedOperationException("Warehouse polling API decommissioned.");
    }
}
