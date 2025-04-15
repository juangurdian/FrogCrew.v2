package com.tcu.frogcrew.security;

import com.tcu.frogcrew.model.User;
import com.tcu.frogcrew.model.UserRole;
import com.tcu.frogcrew.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        // Create a test user
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(UserRole.USER);
        user.setActive(true);
        userRepository.save(user);
    }

    @Test
    public void testPublicEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a public endpoint"));
    }

    @Test
    public void testProtectedEndpointWithoutToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/user"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testLoginAndAccessProtectedEndpoint() throws Exception {
        // Login to get token
        String loginResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract token from response
        String token = loginResponse.split("\"token\":\"")[1].split("\"")[0];

        // Access protected endpoint with token
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a user endpoint"));
    }
} 