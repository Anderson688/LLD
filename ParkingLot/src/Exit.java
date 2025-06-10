public class Exit {

    public void releaseVehicle(Ticket ticket) {
        ticket.getParkingSpot().release();
        System.out.println("Please pay " + ticket.calculateCost());
    }
}