package cwback;
/**
 * Runnable class representing a vendor producing tickets.
 */
public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    final int maxCapacity;
    final int ticketReleaseRate;

    public Vendor(TicketPool ticketPool, Configuration config) {
        this.ticketPool = ticketPool;
        this.maxCapacity = config.getMaxTicketCapacity() + config.getTotalTickets();
        this.ticketReleaseRate = config.getTicketReleaseRate();
    }

    @Override
    public void run() {
        for (int i = 1; i <= maxCapacity; i++) {
            ticketPool.addTicket("Ticket: " + i);
            try {
                Thread.sleep(ticketReleaseRate);  // Simulate ticket release rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
