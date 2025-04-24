-- Clear non-user data
DELETE FROM activities;
DELETE FROM shift_assignments;
DELETE FROM user_positions;
DELETE FROM shifts;
DELETE FROM events;
DELETE FROM positions;

-- Only insert default users if they don't exist
-- Admin user
MERGE INTO users KEY (email) 
VALUES (
    'admin@frogcrew.com',
    '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', -- This is the bcrypt hash for 'admin123'
    'Admin',
    'User',
    'ADMIN',
    true,
    CURRENT_TIMESTAMP(),
    CURRENT_TIMESTAMP()
);

-- Crew user
MERGE INTO users KEY (email)
VALUES (
    'crew@example.com', 
    'Crew', 
    'Member', 
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 
    'USER', 
    true, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP
);

-- Add more crew members if they don't exist
MERGE INTO users KEY (email)
VALUES (
    'john.doe@example.com', 
    'John', 
    'Doe', 
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 
    'USER', 
    true, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP
);

MERGE INTO users KEY (email)
VALUES (
    'jane.smith@example.com', 
    'Jane', 
    'Smith', 
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 
    'USER', 
    true, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP
);

-- Create positions first
INSERT INTO positions (name, description, color, created_at, updated_at)
VALUES ('Camera Operators', 'Operate filming equipment', 'bg-blue-500', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO positions (name, description, color, created_at, updated_at)
VALUES ('Directors', 'Direct show production', 'bg-green-500', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO positions (name, description, color, created_at, updated_at)
VALUES ('Producers', 'Oversee production process', 'bg-yellow-500', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO positions (name, description, color, created_at, updated_at)
VALUES ('Audio Engineers', 'Manage sound equipment', 'bg-purple-500', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO positions (name, description, color, created_at, updated_at)
VALUES ('Technical Directors', 'Coordinate technical aspects', 'bg-red-500', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Now assign positions to users using subqueries
INSERT INTO user_positions (user_id, position_id, created_at)
SELECT u.id, p.id, CURRENT_TIMESTAMP
FROM users u, positions p
WHERE u.email = 'crew@example.com' AND p.name = 'Camera Operators';

INSERT INTO user_positions (user_id, position_id, created_at)
SELECT u.id, p.id, CURRENT_TIMESTAMP
FROM users u, positions p
WHERE u.email = 'john.doe@example.com' AND p.name = 'Camera Operators';

INSERT INTO user_positions (user_id, position_id, created_at)
SELECT u.id, p.id, CURRENT_TIMESTAMP
FROM users u, positions p
WHERE u.email = 'jane.smith@example.com' AND p.name = 'Directors';

-- Create events
INSERT INTO events (title, description, start_time, end_time, created_at, updated_at)
VALUES ('Morning News', 'Daily morning news broadcast', DATEADD('DAY', 1, CURRENT_TIMESTAMP), DATEADD('HOUR', 3, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO events (title, description, start_time, end_time, created_at, updated_at)
VALUES ('Sports Coverage', 'Live sports event coverage', DATEADD('DAY', 2, CURRENT_TIMESTAMP), DATEADD('HOUR', 4, DATEADD('DAY', 2, CURRENT_TIMESTAMP)), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO events (title, description, start_time, end_time, created_at, updated_at)
VALUES ('Evening News', 'Daily evening news broadcast', DATEADD('HOUR', 8, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), DATEADD('HOUR', 10, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Create shifts
INSERT INTO shifts (title, start_time, end_time, required_staff, current_staff, event_id, created_at, updated_at)
SELECT 'Morning News', DATEADD('DAY', 1, CURRENT_TIMESTAMP), DATEADD('HOUR', 3, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), 5, 5, e.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e WHERE e.title = 'Morning News';

INSERT INTO shifts (title, start_time, end_time, required_staff, current_staff, event_id, created_at, updated_at)
SELECT 'Sports Coverage', DATEADD('DAY', 2, CURRENT_TIMESTAMP), DATEADD('HOUR', 4, DATEADD('DAY', 2, CURRENT_TIMESTAMP)), 8, 6, e.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e WHERE e.title = 'Sports Coverage';

INSERT INTO shifts (title, start_time, end_time, required_staff, current_staff, event_id, created_at, updated_at)
SELECT 'Evening News', DATEADD('HOUR', 8, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), DATEADD('HOUR', 10, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), 5, 4, e.id, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM events e WHERE e.title = 'Evening News';

INSERT INTO shifts (title, start_time, end_time, required_staff, current_staff, created_at, updated_at)
VALUES ('Late Night', DATEADD('HOUR', 22, DATEADD('DAY', 1, CURRENT_TIMESTAMP)), DATEADD('HOUR', 1, DATEADD('DAY', 2, CURRENT_TIMESTAMP)), 5, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Create activities
INSERT INTO activities (description, user_id, activity_type, created_at)
SELECT 'John Doe updated their availability', u.id, 'AVAILABILITY_UPDATE', DATEADD('HOUR', -2, CURRENT_TIMESTAMP)
FROM users u WHERE u.email = 'john.doe@example.com';

INSERT INTO activities (description, user_id, activity_type, created_at)
SELECT 'New shift created: Morning Broadcast', u.id, 'SHIFT_CREATION', DATEADD('HOUR', -4, CURRENT_TIMESTAMP)
FROM users u WHERE u.email = 'admin@frogcrew.com';

INSERT INTO activities (description, user_id, activity_type, created_at)
SELECT 'Jane Smith requested time off', u.id, 'TIME_OFF_REQUEST', DATEADD('DAY', -1, CURRENT_TIMESTAMP)
FROM users u WHERE u.email = 'jane.smith@example.com'; 