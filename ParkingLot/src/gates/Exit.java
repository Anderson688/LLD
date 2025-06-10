package gates;

import payment.PaymentStatus;
import tickets.Ticket;
import core.ParkingLot;

public class Exit {

    private final int id;

    public Exit(int id) {
        this.id = id;
    }

    public double calculateFee(Ticket ticket) {
        return ParkingLot.getInstance().calculateFee(ticket);
    }

    public void processPayment(Ticket ticket, double amount) {
        System.out.println("Processing payment of $" + amount + " for ticket " + ticket.getId());
        ticket.setPaymentStatus(PaymentStatus.PAID);
        ParkingLot.getInstance().closeTicket(ticket);
        System.out.println("Thank you for your payment. Have a nice day!");
    }
}