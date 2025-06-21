package user;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private final String userId;
    private final String username;
    private final String email;
    private String passwordHash;
    private final String firstName;
    private final String lastName;
//    private Role role;
    private final LocalDateTime dateRegistered;

    public User(String username, String email, String passwordHash, String firstName, String lastName) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.role = role;
        this.dateRegistered = LocalDateTime.now();
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
//    public Role getRole() { return role; }
    public LocalDateTime getDateRegistered() { return dateRegistered; }

    // Setters (for updatable fields)
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; } // For password reset

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
//                ", role=" + role +
                '}';
    }
}