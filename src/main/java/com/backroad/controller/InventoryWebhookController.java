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

    public InventoryWebhookController(BlockerJournalRepository blockerJournalRepository) {
        this.blockerJournalRepository = blockerJournalRepository;
    }

    @PostMapping("/inventory")
    public Map<String, Object> receiveInventoryWebhook(@RequestBody InventoryProduct product) {
        BlockerJournal blocker = null;
        if (product.getQuantity() <= 0) {
            blocker = new BlockerJournal(
                product.getProductId(),
                "INVENTORY",
                "Inventory quantity is zero or below",
                "OPEN"
            );
            blocker = blockerJournalRepository.save(blocker);
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

    @PutMapping("/blockers/{id}/resolve")
    public Map<String, Object> resolveBlocker(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        return blockerJournalRepository.findById(id).map(blocker -> {
            blocker.setStatus("RESOLVED");
            blockerJournalRepository.save(blocker);

            response.put("status", "success");
            response.put("message", "Blocker resolved successfully");
            response.put("blocker", blocker);
            return response;
        }).orElseGet(() -> {
            response.put("status", "error");
            response.put("message", "Blocker not found with id: " + id);
            return response;
        });
    }
}