CREATE TABLE users(
    username VARCHAR(30) NOT NULL,
    email_id VARCHAR(100) NOT NULL,
    encrypted_password VARCHAR(300) NOT NULL,
    is_admin BOOL DEFAULT FALSE,
    id VARCHAR(36) PRIMARY KEY,
    UNIQUE(email_id),
    UNIQUE(username)
);

