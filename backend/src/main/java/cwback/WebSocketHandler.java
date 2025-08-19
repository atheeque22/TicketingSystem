package cwback;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
/**
 * Handles WebSocket communication for the ticket simulation system.
 */

public class WebSocketHandler extends TextWebSocketHandler {
    private TicketPool ticketPool;
    private Thread vendorThread;
    private Thread customerThread;

    public WebSocketHandler() {
    }
    /**
     * Handles incoming text messages from the WebSocket client.
     * @param session WebSocket session associated with the client.
     * @param message Text message received from the client.
     */    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the configuration object from the incoming WebSocket message
            Configuration config = objectMapper.readValue(message.getPayload(), Configuration.class);

            // Validate the configuration using the InputValidator
            String validationMessage = InputValidator.validateConfiguration(config);

            // If validation fails, send the error message back to the frontend
            if (validationMessage != null) {
                session.sendMessage(new TextMessage(validationMessage));
                return;
            }

            // Proceed with the simulation if input is valid
            this.ticketPool = new TicketPool(config, session);
            Vendor vendor = new Vendor(this.ticketPool, config);
            Customer customer = new Customer(this.ticketPool, config);

            this.vendorThread = new Thread(() -> this.runVendor(session, vendor), "Vendor");
            this.customerThread = new Thread(() -> this.runCustomer(session, customer), "Customer");

            this.vendorThread.start();
            this.customerThread.start();

            session.sendMessage(new TextMessage("Simulation started with configuration: " + message.getPayload()));
        } catch (Exception e) {
            // Handle parsing errors or other issues
            e.printStackTrace();
            session.sendMessage(new TextMessage("Error: Invalid input. " + e.getMessage()));
        }
    }

    /**
     * Simulates vendor operations for adding tickets.
     * @param session WebSocket session to communicate with the frontend.
     * @param vendor Vendor object for ticket operations.
     */
    private void runVendor(WebSocketSession session, Vendor vendor) {
        try {
            for (int i = 1; i <= vendor.maxCapacity; ++i) {
                this.ticketPool.addTicket("Ticket: " + i);
                Thread.sleep((long) vendor.ticketReleaseRate);
            }

            session.sendMessage(new TextMessage("Vendor simulation completed."));
        } catch (Exception var6) {
            var6.printStackTrace();

            try {
                session.sendMessage(new TextMessage(""));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }
    }

    /**
     * Simulates customer operations for removing tickets.
     * @param session WebSocket session to communicate with the frontend.
     * @param customer Customer object for ticket operations.
     */
    private void runCustomer(WebSocketSession session, Customer customer) {
        try {
            for (int i = 1; i <= customer.maxCapacity; ++i) {
                this.ticketPool.removeTicket();
                Thread.sleep((long) customer.customerRetrievalRate);
            }

            session.sendMessage(new TextMessage("Customer simulation completed."));
        } catch (Exception var6) {
            var6.printStackTrace();

            try {
                session.sendMessage(new TextMessage(""));
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }
    }
}
