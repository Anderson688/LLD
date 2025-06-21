package notification;

public interface INotificationStrategy {
    void sendNotification(String recipient, String message);
}