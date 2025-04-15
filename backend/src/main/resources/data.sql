-- Clear existing user data
DELETE FROM users;

-- Password for both users is 'password' (bcrypt encoded)
-- Admin user
INSERT INTO users (email, first_name, last_name, password, role, is_active, created_at, updated_at)
VALUES ('admin@example.com', 'Admin', 'User', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Crew user
INSERT INTO users (email, first_name, last_name, password, role, is_active, created_at, updated_at)
VALUES ('crew@example.com', 'Crew', 'Member', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 