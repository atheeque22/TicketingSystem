package cwback;
/**
 * Runnable class representing a customer buying tickets.
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    final int maxCapacity;
    final int customerRetrievalRate;

    public Customer(TicketPool ticketPool, Configuration config) {
        this.ticketPool = ticketPool;
        this.maxCapacity = config.getMaxTicketCapacity() + config.getTotalTickets();
        this.customerRetrievalRate = config.getCustomerRetrievalRate();
    }

    @Override
    public void run() {
        for (int i = 1; i <= maxCapacity; i++) {
            ticketPool.removeTicket();
            try {
                Thread.sleep(customerRetrievalRate);  // Simulate customer retrieval rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
