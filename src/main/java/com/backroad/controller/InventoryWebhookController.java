package com.backroad.controller;

import com.backroad.model.BlockerJournal;
import com.backroad.model.InventoryProduct;
import com.backroad.repository.BlockerJournalRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class InventoryWebhookController {

    private final BlockerJournalRepository blockerJournalRepository;

    public InventoryWebhookController(
            BlockerJournalRepository blockerJournalRepository) {

        this.blockerJournalRepository = blockerJournalRepository;
    }

    @PostMapping("/inventory")
    public Map<String, Object> receiveInventoryWebhook(
            @RequestBody InventoryProduct product) {

        System.out.println("Inventory webhook received:");
        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Quantity: " + product.getQuantity());

        BlockerJournal blocker = null;

        if (product.getQuantity() <= 0) {

            blocker = new BlockerJournal(
                    product.getProductId(),
                    "INVENTORY",
                    "Inventory quantity is zero or below",
                    "OPEN"
            );

            blocker = blockerJournalRepository.save(blocker);

            System.out.println(
                    "Blocker saved for product: "
                    + product.getProductId()
            );
        }

        Map<String, Object> response = new HashMap<>();

        response.put("status", "success");
        response.put("message", "Inventory update received");
        response.put("data", product);
        response.put("blocker", blocker);

        return response;
    }

    @GetMapping("/blockers")
    public Map<String, Object> getBlockers() {

        Map<String, Object> response = new HashMap<>();

        response.put("status", "success");
        response.put("message", "Blockers retrieved successfully");
        response.put("blockers", blockerJournalRepository.findAll());

        return response;
    }
}