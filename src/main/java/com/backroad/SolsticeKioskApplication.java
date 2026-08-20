package com.backroad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
@RestController
public class SolsticeKioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolsticeKioskApplication.class, args);
    }

    public enum CheckInStatus { UNCHECKED, PENDING, CHECKED_IN }

    public static class Attendee {
        private String attendeeId;
        private String name;
        private CheckInStatus status = CheckInStatus.UNCHECKED;

        public Attendee() {}
        public Attendee(String attendeeId, String name) {
            this.attendeeId = attendeeId;
            this.name = name;
        }

        public String getAttendeeId() { return attendeeId; }
        public String getName() { return name; }
        public CheckInStatus getStatus() { return status; }
        public void setStatus(CheckInStatus status) { this.status = status; }
    }

    public static class PrintJobRequest {
        private String attendeeId;
        public PrintJobRequest() {}
        public PrintJobRequest(String attendeeId) { this.attendeeId = attendeeId; }
        public String getAttendeeId() { return attendeeId; }
    }

    public static class PrinterCallbackPayload {
        private String attendeeId;
        private String printJobId;
        private String status;

        public PrinterCallbackPayload() {}
        public PrinterCallbackPayload(String attendeeId, String printJobId, String status) {
            this.attendeeId = attendeeId;
            this.printJobId = printJobId;
            this.status = status;
        }

        public String getAttendeeId() { return attendeeId; }
        public String getPrintJobId() { return printJobId; }
        public String getStatus() { return status; }
    }

    // Deprecated Synchronous Service (Day 3 Spec)
    @Deprecated(since = "2.0.0", forRemoval = true)
    @Service
    public static class LegacySynchronousPrinterService {
        @Deprecated
        public boolean printBadgeSync(String attendeeId) {
            throw new UnsupportedOperationException(
                "Synchronous REST printing API decommissioned by Solstice vendor. Use VendorMessageQueuePublisher instead."
            );
        }
    }

    // Message Queue Publisher (Day 4 Pivot Requirement)
    @Service
    public static class VendorMessageQueuePublisher {
        private final BlockingQueue<PrintJobRequest> printQueue = new LinkedBlockingQueue<>();

        public void publishPrintJob(String attendeeId) {
            printQueue.add(new PrintJobRequest(attendeeId));
            System.out.println("[MQ-PUBLISH] Enqueued print job for Attendee ID: " + attendeeId);
        }

        public BlockingQueue<PrintJobRequest> getPrintQueue() { return printQueue; }
    }

    private final ConcurrentHashMap<String, Attendee> attendeeDb = new ConcurrentHashMap<>();
    private final VendorMessageQueuePublisher queuePublisher;

   public SolsticeKioskApplication(VendorMessageQueuePublisher queuePublisher) {
        this.queuePublisher = queuePublisher;
        
        // Pre-seed test attendees
        Attendee att1 = new Attendee("ATT001", "Caroline Jelagat");
        att1.setStatus(CheckInStatus.CHECKED_IN);

        Attendee att2 = new Attendee("ATT002", "Jones Mutheu");
        att2.setStatus(CheckInStatus.CHECKED_IN);

        Attendee att3 = new Attendee("ATT003", "Faith Makena");

        attendeeDb.put("ATT001", att1);
        attendeeDb.put("ATT002", att2);
        attendeeDb.put("ATT003", att3);
    }
    // 1. Kiosk QR Scan Endpoint: Enqueues print job & sets state to PENDING
    @PostMapping("/kiosk/scan")
    public ResponseEntity<?> scanAttendee(@RequestBody PrintJobRequest request) {
        String id = request.getAttendeeId();
        Attendee attendee = attendeeDb.get(id);

        if (attendee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendee not found.");
        }

        // Async Duplicate Scan Guard (Prevents scans when PENDING or CHECKED_IN)
        if (attendee.getStatus() == CheckInStatus.PENDING || attendee.getStatus() == CheckInStatus.CHECKED_IN) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Duplicate scan prevented. Attendee state is already " + attendee.getStatus());
        }

        attendee.setStatus(CheckInStatus.PENDING);
        queuePublisher.publishPrintJob(id);

        return ResponseEntity.ok(attendee);
    }

    // 2. Webhook Callback Endpoint: Receives vendor async print completion
    @PostMapping("/webhook/printer-callback")
    public ResponseEntity<?> handlePrinterCallback(@RequestBody PrinterCallbackPayload payload) {
        Attendee attendee = attendeeDb.get(payload.getAttendeeId());

        if (attendee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendee record missing.");
        }

        if ("SUCCESS".equalsIgnoreCase(payload.getStatus())) {
            attendee.setStatus(CheckInStatus.CHECKED_IN);
            System.out.println("[WEBHOOK-CALLBACK] Badge printed. Attendee " + attendee.getAttendeeId() + " set to CHECKED_IN.");
        }

        return ResponseEntity.ok(attendee);
    }

    // 3. Status Lookup Endpoint
    @GetMapping("/kiosk/attendees/{id}")
    public ResponseEntity<?> getAttendeeStatus(@PathVariable String id) {
        Attendee attendee = attendeeDb.get(id);
        if (attendee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendee not found.");
        }
        return ResponseEntity.ok(attendee);
    }
}
