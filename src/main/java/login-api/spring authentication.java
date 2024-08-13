 //code-start
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.UUID;

@SpringBootApplication
@RestController
public class LoginController {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Validate user input
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required.");
        }

        // Security: Hash password for secure storage
        String hashedPassword = passwordEncoder.encode(loginRequest.getPassword());

        // Simulate login process by checking username and hashed password
        boolean isAuthenticated = isUserAuthenticated(loginRequest.getUsername(), hashedPassword);

        // Return appropriate response based on authentication status
        return isAuthenticated ? ResponseEntity.ok().build() : ResponseEntity
            .status(401).body("Invalid username or password");
    }

    private boolean isUserAuthenticated(String username, String hashedPassword) {
        // Check if the user with the given username and hashed password exists
        // This is a placeholder for actual authentication logic
        return "admin".equals(username) && hashedPassword.equals(passwordEncoder.encode("password"));
    }

    public static void main(String[] args) {
        SpringApplication.run(LoginController.class, args);
    }

    @Data
    public static class LoginRequest {

        @NotNull(message = "Username is required")
        @Size(min = 5, message = "Username must be at least 5 characters long")
        private String username;

        @NotNull(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        private String password;
    }

}
//code-end
