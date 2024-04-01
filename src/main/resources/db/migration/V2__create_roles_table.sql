CREATE TABLE IF NOT EXISTS roles (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL
);