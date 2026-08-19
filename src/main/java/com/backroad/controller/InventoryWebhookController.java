package com.backroad.controller;

import com.backroad.model.BlockerJournal;
import com.backroad.model.InventoryProduct;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class InventoryWebhookController {

    @PostMapping("/inventory")
    public Map<String, Object> receiveInventoryWebhook(
            @RequestBody InventoryProduct product) {

        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Quantity: " + product.getQuantity());

        BlockerJournal blocker = null;

        if (product.getQuantity() <= 0) {
            blocker = new BlockerJournal();
            blocker.setProductId(product.getProductId());
            blocker.setProductName(product.getProductName());
            blocker.setReason("Inventory quantity is zero or below");
        }

        return Map.of(
                "status", "success",
                "message", "Inventory update received",
                "data", product,
                "blocker", blocker
        );
    }
}