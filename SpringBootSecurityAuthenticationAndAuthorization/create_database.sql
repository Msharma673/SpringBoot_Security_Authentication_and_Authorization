-- Script to create the database if it doesn't exist
-- Run this before starting the application:
-- mysql -u root -p < create_database.sql

CREATE DATABASE IF NOT EXISTS SpringSecurity CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE SpringSecurity;

-- Verify database was created
SELECT 'Database SpringSecurity created successfully!' AS Status;

