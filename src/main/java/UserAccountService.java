import java.util.HashMap;
import java.util.Map;

public class UserAccountService {

    private final Map<String, User> userStore = new HashMap<>();

    public void registerUser(String username, String password, int age) {
        if (userStore.containsKey(username)) {
            throw new IllegalArgumentException("User already exists.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        }
        if (age < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old.");
        }
        userStore.put(username, new User(username, password, age, true));
    }

    public boolean login(String username, String password) {
        if (!userStore.containsKey(username)) {
            return false;
        }
        User user = userStore.get(username);
        return user.getPassword().equals(password);
    }

    public boolean isUserActive(String username) {
        User user = userStore.get(username);
        return user != null && user.isActive();
    }

    public void deactivateUser(String username) {
        if (userStore.containsKey(username)) {
            userStore.get(username).setActive(false);
        }
    }

    public void clearAllUsers() {
        userStore.clear();
    }

    public static class User {
        private final String username;
        private final String password;
        private final int age;
        private boolean active;

        public User(String username, String password, int age, boolean active) {
            this.username = username;
            this.password = password;
            this.age = age;
            this.active = active;
        }

        public String getPassword() {
            return password;
        }

        public int getAge() {
            return age;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}