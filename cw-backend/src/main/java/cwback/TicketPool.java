package cwback;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 * Represents the shared ticket pool with thread-safe operations.
 */
public class TicketPool {
    final Queue<String> tickets = new LinkedList<>();
    private final int maxCapacity;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());
    private WebSocketSession session;  // Add WebSocketSession

    // Static block to set up the file logging only
    static {
        try {
            FileHandler fileHandler = new FileHandler("ticketPool.log", true); // Append mode
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);  // Disables logging to console
        } catch (IOException e) {
            System.out.println("Error initializing file handler for logging: " + e.getMessage());
        }
    }

    /**
     * Constructs a TicketPool instance.
     * @param config Configuration object containing system parameters.
     * @param session WebSocket session for client communication.
     */
    public TicketPool(Configuration config, WebSocketSession session) {
        this.maxCapacity = config.getMaxTicketCapacity() + config.getTotalTickets();
        this.session = session;  // Set the WebSocket session
    }

    /**
     * Adds a ticket to the pool in a thread-safe manner.
     * @param ticket The ticket to be added.
     */
    public synchronized void addTicket(String ticket) {
        while (tickets.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted while waiting to add ticket: " + e.getMessage());
                logger.severe("Thread interrupted while waiting to add ticket: " + e.getMessage());
            }
        }
        tickets.add(ticket);
        String message = Thread.currentThread().getName() + " added a ticket: " + ticket;
        System.out.println(message);
        logger.info(message);

        // Send message to the WebSocket client
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));  // Send message to frontend
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        notifyAll();
    }


    /**
     * Removes a ticket from the pool in a thread-safe manner.
     * @return The removed ticket.
     */
    public synchronized String removeTicket() {
        while (tickets.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted while waiting to remove ticket: " + e.getMessage());
                logger.severe("Thread interrupted while waiting to remove ticket: " + e.getMessage());
            }
        }
        String ticket = tickets.poll();
        String message = Thread.currentThread().getName() + " removed a ticket: " + ticket;
        System.out.println(message);
        logger.info(message);

        // Send message to the WebSocket client
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));  // Send message to frontend
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        notifyAll();
        return ticket;
    }
}
