-- ============================================================
-- RentHouse Database Initialization Script
-- Run this in MySQL before starting the Spring Boot app
-- ============================================================

CREATE DATABASE IF NOT EXISTS renthouse_db;
USE renthouse_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(15),
  user_type ENUM('SEEKER','OWNER','ADMIN') NOT NULL,
  is_verified BOOLEAN DEFAULT FALSE,
  profile_image_url VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Properties Table
CREATE TABLE IF NOT EXISTS properties (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  description TEXT,
  address VARCHAR(300),
  city VARCHAR(100),
  state VARCHAR(100),
  pincode VARCHAR(10),
  rent DECIMAL(10,2) NOT NULL,
  bedrooms INT,
  bathrooms INT,
  area_sqft INT,
  property_type ENUM('HOUSE','APARTMENT','VILLA','PG','STUDIO') NOT NULL,
  furnishing ENUM('FURNISHED','SEMI_FURNISHED','UNFURNISHED'),
  amenities JSON,
  status ENUM('ACTIVE','INACTIVE','SOLD') DEFAULT 'ACTIVE',
  owner_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Property Images Table
CREATE TABLE IF NOT EXISTS property_images (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  property_id BIGINT NOT NULL,
  image_url VARCHAR(500) NOT NULL,
  cloudinary_public_id VARCHAR(255),
  is_primary BOOLEAN DEFAULT FALSE,
  uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE
);

-- Favorites Table
CREATE TABLE IF NOT EXISTS favorites (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  property_id BIGINT NOT NULL,
  saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY unique_favorite (user_id, property_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE
);

-- ============================================================
-- SEED DATA (Optional — comment out if you prefer empty DB)
-- ============================================================
-- Admin user (password: admin123)
INSERT IGNORE INTO users (full_name, email, password, phone, user_type, is_verified)
VALUES (
  'Admin User',
  'admin@renthouse.com',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lMz6',
  '+91 98765 00000',
  'ADMIN',
  TRUE
);
