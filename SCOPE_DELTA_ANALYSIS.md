# Scope Delta Analysis: Solstice Events Co. Kiosk Check-In

**Project:** Tech Conference Check-in Kiosk Service  
**Sprint Phase:** Day 4/5 Pivot Refactor  
**Client:** Solstice Events Co.  

---

## 1. Executive Summary
Solstice Events Co.'s badge-printer vendor decommissioned its synchronous REST API without a deadline extension. The system was refactored to an asynchronous architecture: QR scans publish print requests to a message queue (`VendorMessageQueuePublisher`), and completion callbacks are received via a webhook endpoint (`POST /webhook/printer-callback`).

---

## 2. Feature Delta Matrix

| Feature / Task | Delta Status | Original Spec (Day 3) | Refactored Spec (Day 5) | Justification / Impact |
| :--- | :--- | :--- | :--- | :--- |
| **Sync Printer REST Call** | **Dropped** | Synchronous REST HTTP call to vendor printer | Deprecated (`LegacySynchronousPrinterService`) | API deprecation forced removal of blocking REST calls. |
| **Message Queue Integration** | **Added** | N/A | `VendorMessageQueuePublisher` | Publishes print jobs asynchronously to prevent UI freezing. |
| **Printer Webhook Callback** | **Added** | N/A | `POST /webhook/printer-callback` | Receives async completion notifications to flip state to `CHECKED_IN`. |
| **Duplicate Scan Protection** | **Modified** | Blocked scans only if `CHECKED_IN` | Blocks duplicate scans during both `PENDING` and `CHECKED_IN` states | Prevents duplicate badge prints while callbacks are in-flight or arrive out of order. |

---

## 3. Architectural Integrity & Technical Debt
* **State Machine:** Enforces `UNCHECKED -> PENDING -> CHECKED_IN`, ensuring state consistency under asynchronous delivery.
* **Technical Debt:** Implemented an in-memory `LinkedBlockingQueue` to meet the strict August 22 deadline; production requires external broker integration (RabbitMQ/Kafka).
