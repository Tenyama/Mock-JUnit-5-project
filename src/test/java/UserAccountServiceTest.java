import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("UserAccountService Unit Tests")
public class UserAccountServiceTest {

    private UserAccountService service;

    @BeforeAll
    void setupAll() {
        System.out.println("== Starting UserAccountService Tests ==");
    }

    @BeforeEach
    void init() {
        service = new UserAccountService();
        service.registerUser("admin", "admin123", 30);
    }

    @AfterEach
    void tearDown() {
        service.clearAllUsers();
    }

    @AfterAll
    void teardownAll() {
        System.out.println("== Tests Complete ==");
    }

    @Test
    @Order(1)
    @Tag("positive")
    @DisplayName("Register a new user successfully")
    void shouldRegisterUser() {
        service.registerUser("user1", "password123", 25);
        assertTrue(service.login("user1", "password123"));
    }

    @Test
    @Order(2)
    @Tag("negative")
    @DisplayName("Should throw error when registering duplicate user")
    void shouldThrowOnDuplicateRegistration() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                service.registerUser("admin", "newpass123", 25)
        );
        assertEquals("User already exists.", ex.getMessage());
    }

    @Test
    @Order(3)
    @Tag("boundary")
    @DisplayName("Should fail when password is too short")
    void shouldFailShortPassword() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerUser("shortpass", "123", 22)
        );
    }

    @Test
    @Order(4)
    @Tag("boundary")
    @DisplayName("Should fail when age is below 18")
    void shouldRejectUnderageUser() {
        assertThrows(IllegalArgumentException.class, () ->
                service.registerUser("teen", "password123", 16)
        );
    }

    @Test
    @Order(5)
    @Tag("positive")
    @DisplayName("Login succeeds with valid credentials")
    void loginShouldSucceed() {
        assertTrue(service.login("admin", "admin123"));
    }

    @Test
    @Order(6)
    @Tag("negative")
    @DisplayName("Login fails with incorrect password")
    void loginFailsWrongPassword() {
        assertFalse(service.login("admin", "wrongpass"));
    }

    @Test
    @Order(7)
    @Tag("negative")
    @DisplayName("Login fails for unknown user")
    void loginFailsUnknownUser() {
        assertFalse(service.login("unknown", "any"));
    }

    @Test
    @Order(8)
    @Tag("positive")
    @DisplayName("Deactivate user makes them inactive")
    void deactivateUserShouldUpdateStatus() {
        service.deactivateUser("admin");
        assertFalse(service.isUserActive("admin"));
    }

    @Test
    @Order(9)
    @Tag("positive")
    @DisplayName("isUserActive returns true for active user")
    void activeUserCheck() {
        assertTrue(service.isUserActive("admin"));
    }

    @ParameterizedTest(name = "Valid password: {0}")
    @ValueSource(strings = { "validPass123", "qwerty123", "complexP@ss" })
    @Tag("parameterized")
    @Order(10)
    void shouldAcceptValidPasswords(String password) {
        service.registerUser(password, password, 21);
        assertTrue(service.login(password, password));
    }

    @Test
    @Order(11)
    @Tag("grouped")
    @DisplayName("Grouped user registration validation")
    void validateMultipleUserFields() {
        String username = "john_doe";
        String password = "securePass123";
        int age = 25;

        service.registerUser(username, password, age);

        assertAll("User registration checks",
                () -> assertTrue(service.login(username, password)),
                () -> assertTrue(service.isUserActive(username))
        );
    }

    @Nested
    @DisplayName("When user is registered")
    class RegisteredUserTests {

        @Test
        @Tag("nested")
        void canLogin() {
            assertTrue(service.login("admin", "admin123"));
        }

        @Test
        @Tag("nested")
        void isActive() {
            assertTrue(service.isUserActive("admin"));
        }
    }

    @RepeatedTest(3)
    @Tag("repeated")
    @DisplayName("Repeated login check")
    void repeatedLoginTest() {
        assertTrue(service.login("admin", "admin123"));
    }
}