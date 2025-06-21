package user;

import core.RentalSystemManager;

public class UserService {
    private final RentalSystemManager manager;

    public UserService(RentalSystemManager manager) {
        this.manager = manager;
    }

    public User registerCustomer(String username, String email, String password, String firstName, String lastName) {
        return manager.registerUser(username, email, "hashed_" + password, firstName, lastName);
    }

    public void loginUser(String username, String password) {
        manager.getUserByUsername(username)
                .filter(user -> ("hashed_" + password).equals(user.getPasswordHash()));
    }
}