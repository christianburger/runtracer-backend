#!/bin/bash

# Define the directory where the scripts will be stored
SCRIPT_DIR="src/main/resources/db/migration"

# Create the directory if it doesn't exist
mkdir -p $SCRIPT_DIR

# Create the Flyway scripts
echo "CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  roles VARCHAR(255) NOT NULL
);" > $SCRIPT_DIR/V1__create_users_table.sql

echo "CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);" > $SCRIPT_DIR/V2__create_roles_table.sql

echo "CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT(20) NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);" > $SCRIPT_DIR/V3__create_user_roles_table.sql

echo "INSERT INTO roles (name) VALUES ('ADMIN');" > $SCRIPT_DIR/V4__insert_admin_role.sql

echo "-- Note: Replace the '?' placeholders with actual values
INSERT INTO users (username, password, email)
VALUES ('admin', 'password', 'admin@example.com');" > $SCRIPT_DIR/V5__insert_admin_user.sql

echo "INSERT INTO user_roles (user_id, role_id) 
VALUES ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ADMIN'));" > $SCRIPT_DIR/V6__associate_admin_user_role.sql

echo "Flyway scripts have been created in $SCRIPT_DIR"
