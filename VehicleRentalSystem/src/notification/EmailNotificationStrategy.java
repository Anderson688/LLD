package notification;

public class EmailNotificationStrategy implements INotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("[EMAIL] To: " + recipient + " | Message: " + message);
    }
}