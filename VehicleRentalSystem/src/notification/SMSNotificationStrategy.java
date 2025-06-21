package notification;

public class SMSNotificationStrategy implements INotificationStrategy {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("[SMS] To: " + recipient + " | Message: " + message);
    }
}