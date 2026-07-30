-- =========================================================================
-- SEED DEMO PROPERTIES (Tamil Nadu - 38 Districts)
-- =========================================================================

START TRANSACTION;

-- 1. Ensure the demo owner "vijya" exists (or create if not present)
INSERT IGNORE INTO users (full_name, email, password, phone, user_type, is_verified)
VALUES ('Vijya (Demo Owner)', 'vijya@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lMz6', '+91 98765 43210', 'OWNER', TRUE);

SET @owner_id = (SELECT id FROM users WHERE email = 'vijya@example.com' LIMIT 1);

-- District: Chennai
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Luxury Apartment in Adyar', 'A beautiful and spacious property located in the heart of Chennai, perfect for comfortable living.', 'Luxury Apartment in Adyar, Chennai', 'Chennai', 'Tamil Nadu', '600020', 35000, 3, 2, 1500, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_0', TRUE);

-- District: Coimbatore
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Spacious Villa in RS Puram', 'A beautiful and spacious property located in the heart of Coimbatore, perfect for comfortable living.', 'Spacious Villa in RS Puram, Coimbatore', 'Coimbatore', 'Tamil Nadu', '641002', 45000, 4, 3, 2500, 'VILLA', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_1', TRUE);

-- District: Madurai
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Traditional House in Anna Nagar', 'A beautiful and spacious property located in the heart of Madurai, perfect for comfortable living.', 'Traditional House in Anna Nagar, Madurai', 'Madurai', 'Tamil Nadu', '625020', 18000, 2, 1, 1200, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'INACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_2', TRUE);

-- District: Tiruchirappalli
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Modern Flat in Srirangam', 'A beautiful and spacious property located in the heart of Tiruchirappalli, perfect for comfortable living.', 'Modern Flat in Srirangam, Tiruchirappalli', 'Tiruchirappalli', 'Tamil Nadu', '620006', 15000, 2, 1, 1100, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_3', TRUE);

-- District: Salem
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Cozy Studio in Fairlands', 'A beautiful and spacious property located in the heart of Salem, perfect for comfortable living.', 'Cozy Studio in Fairlands, Salem', 'Salem', 'Tamil Nadu', '636016', 8000, 1, 1, 500, 'STUDIO', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_4', TRUE);

-- District: Tirunelveli
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Independent House in Palayamkottai', 'A beautiful and spacious property located in the heart of Tirunelveli, perfect for comfortable living.', 'Independent House in Palayamkottai, Tirunelveli', 'Tirunelveli', 'Tamil Nadu', '627002', 12000, 2, 1, 1000, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_5', TRUE);

-- District: Erode
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Family Home in Perundurai', 'A beautiful and spacious property located in the heart of Erode, perfect for comfortable living.', 'Family Home in Perundurai, Erode', 'Erode', 'Tamil Nadu', '638052', 14000, 3, 2, 1300, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'INACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_6', TRUE);

-- District: Vellore
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Student PG near VIT', 'A beautiful and spacious property located in the heart of Vellore, perfect for comfortable living.', 'Student PG near VIT, Vellore', 'Vellore', 'Tamil Nadu', '632014', 6000, 1, 1, 300, 'PG', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_7', TRUE);

-- District: Thoothukudi
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Sea View Apartment', 'A beautiful and spacious property located in the heart of Thoothukudi, perfect for comfortable living.', 'Sea View Apartment, Thoothukudi', 'Thoothukudi', 'Tamil Nadu', '628001', 16000, 2, 1, 1150, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_8', TRUE);

-- District: Dindigul
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Central Townhouse', 'A beautiful and spacious property located in the heart of Dindigul, perfect for comfortable living.', 'Central Townhouse, Dindigul', 'Dindigul', 'Tamil Nadu', '624001', 11000, 2, 1, 950, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_9', TRUE);

-- District: Thanjavur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Heritage Villa near Temple', 'A beautiful and spacious property located in the heart of Thanjavur, perfect for comfortable living.', 'Heritage Villa near Temple, Thanjavur', 'Thanjavur', 'Tamil Nadu', '613001', 20000, 3, 2, 1800, 'VILLA', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_10', TRUE);

-- District: Ranipet
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Compact House in BHEL', 'A beautiful and spacious property located in the heart of Ranipet, perfect for comfortable living.', 'Compact House in BHEL, Ranipet', 'Ranipet', 'Tamil Nadu', '632406', 9000, 2, 1, 850, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_11', TRUE);

-- District: Sivaganga
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Quiet Apartment in Karaikudi', 'A beautiful and spacious property located in the heart of Sivaganga, perfect for comfortable living.', 'Quiet Apartment in Karaikudi, Sivaganga', 'Sivaganga', 'Tamil Nadu', '630001', 13000, 2, 1, 1050, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_12', TRUE);

-- District: Karur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Modern House in Sengunthapuram', 'A beautiful and spacious property located in the heart of Karur, perfect for comfortable living.', 'Modern House in Sengunthapuram, Karur', 'Karur', 'Tamil Nadu', '639002', 15000, 3, 2, 1400, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_13', TRUE);

-- District: Namakkal
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Budget Flat in Paramathi', 'A beautiful and spacious property located in the heart of Namakkal, perfect for comfortable living.', 'Budget Flat in Paramathi, Namakkal', 'Namakkal', 'Tamil Nadu', '637207', 10000, 2, 1, 900, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'INACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_14', TRUE);

-- District: Krishnagiri
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Spacious Villa in Hosur', 'A beautiful and spacious property located in the heart of Krishnagiri, perfect for comfortable living.', 'Spacious Villa in Hosur, Krishnagiri', 'Krishnagiri', 'Tamil Nadu', '635109', 25000, 3, 2, 2000, 'VILLA', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_15', TRUE);

-- District: Kanchipuram
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Temple View Apartment', 'A beautiful and spacious property located in the heart of Kanchipuram, perfect for comfortable living.', 'Temple View Apartment, Kanchipuram', 'Kanchipuram', 'Tamil Nadu', '631501', 14000, 2, 1, 1000, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_16', TRUE);

-- District: Cuddalore
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Coastal House in Chidambaram', 'A beautiful and spacious property located in the heart of Cuddalore, perfect for comfortable living.', 'Coastal House in Chidambaram, Cuddalore', 'Cuddalore', 'Tamil Nadu', '608001', 12000, 2, 1, 1100, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_17', TRUE);

-- District: Villupuram
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Town Center Apartment', 'A beautiful and spacious property located in the heart of Villupuram, perfect for comfortable living.', 'Town Center Apartment, Villupuram', 'Villupuram', 'Tamil Nadu', '605602', 11000, 2, 1, 950, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_18', TRUE);

-- District: Tiruvannamalai
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Ashram View Studio', 'A beautiful and spacious property located in the heart of Tiruvannamalai, perfect for comfortable living.', 'Ashram View Studio, Tiruvannamalai', 'Tiruvannamalai', 'Tamil Nadu', '606601', 9000, 1, 1, 600, 'STUDIO', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_19', TRUE);

-- District: Nagapattinam
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Beachside Villa', 'A beautiful and spacious property located in the heart of Nagapattinam, perfect for comfortable living.', 'Beachside Villa, Nagapattinam', 'Nagapattinam', 'Tamil Nadu', '611001', 18000, 3, 2, 1600, 'VILLA', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_20', TRUE);

-- District: Tiruvarur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Traditional Agrarian House', 'A beautiful and spacious property located in the heart of Tiruvarur, perfect for comfortable living.', 'Traditional Agrarian House, Tiruvarur', 'Tiruvarur', 'Tamil Nadu', '610001', 10000, 2, 1, 1200, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_21', TRUE);

-- District: Pudukkottai
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Suburban Flat', 'A beautiful and spacious property located in the heart of Pudukkottai, perfect for comfortable living.', 'Suburban Flat, Pudukkottai', 'Pudukkottai', 'Tamil Nadu', '622001', 12000, 2, 1, 1000, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_22', TRUE);

-- District: Theni
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Hill View House', 'A beautiful and spacious property located in the heart of Theni, perfect for comfortable living.', 'Hill View House, Theni', 'Theni', 'Tamil Nadu', '625531', 15000, 3, 2, 1400, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_23', TRUE);

-- District: Virudhunagar
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Central Apartment in Sivakasi', 'A beautiful and spacious property located in the heart of Virudhunagar, perfect for comfortable living.', 'Central Apartment in Sivakasi, Virudhunagar', 'Virudhunagar', 'Tamil Nadu', '626123', 13000, 2, 1, 1050, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_24', TRUE);

-- District: Ramanathapuram
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Rameswaram Guest House', 'A beautiful and spacious property located in the heart of Ramanathapuram, perfect for comfortable living.', 'Rameswaram Guest House, Ramanathapuram', 'Ramanathapuram', 'Tamil Nadu', '623526', 14000, 3, 2, 1300, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_25', TRUE);

-- District: Nilgiris
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Cozy Ooty Villa', 'A beautiful and spacious property located in the heart of Nilgiris, perfect for comfortable living.', 'Cozy Ooty Villa, Nilgiris', 'Nilgiris', 'Tamil Nadu', '643001', 35000, 4, 3, 2200, 'VILLA', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_26', TRUE);

-- District: Dharmapuri
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Spacious Townhouse', 'A beautiful and spacious property located in the heart of Dharmapuri, perfect for comfortable living.', 'Spacious Townhouse, Dharmapuri', 'Dharmapuri', 'Tamil Nadu', '636701', 11000, 2, 1, 1100, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_27', TRUE);

-- District: Ariyalur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Budget Flat', 'A beautiful and spacious property located in the heart of Ariyalur, perfect for comfortable living.', 'Budget Flat, Ariyalur', 'Ariyalur', 'Tamil Nadu', '621704', 8000, 1, 1, 700, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'INACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_28', TRUE);

-- District: Perambalur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Independent House', 'A beautiful and spacious property located in the heart of Perambalur, perfect for comfortable living.', 'Independent House, Perambalur', 'Perambalur', 'Tamil Nadu', '621212', 10000, 2, 1, 950, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_29', TRUE);

-- District: Kallakurichi
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Family Apartment', 'A beautiful and spacious property located in the heart of Kallakurichi, perfect for comfortable living.', 'Family Apartment, Kallakurichi', 'Kallakurichi', 'Tamil Nadu', '606202', 11000, 2, 1, 1000, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_30', TRUE);

-- District: Chengalpattu
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Modern House in OMR', 'A beautiful and spacious property located in the heart of Chengalpattu, perfect for comfortable living.', 'Modern House in OMR, Chengalpattu', 'Chengalpattu', 'Tamil Nadu', '603103', 28000, 3, 2, 1800, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_31', TRUE);

-- District: Tenkasi
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Courtallam Falls Villa', 'A beautiful and spacious property located in the heart of Tenkasi, perfect for comfortable living.', 'Courtallam Falls Villa, Tenkasi', 'Tenkasi', 'Tamil Nadu', '627802', 22000, 3, 2, 1900, 'VILLA', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_32', TRUE);

-- District: Tirupathur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Jolarpettai Home', 'A beautiful and spacious property located in the heart of Tirupathur, perfect for comfortable living.', 'Jolarpettai Home, Tirupathur', 'Tirupathur', 'Tamil Nadu', '635601', 9000, 2, 1, 900, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'INACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house4_s2c2x9.jpg', 'demo_public_id_33', TRUE);

-- District: Mayiladuthurai
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('River View House', 'A beautiful and spacious property located in the heart of Mayiladuthurai, perfect for comfortable living.', 'River View House, Mayiladuthurai', 'Mayiladuthurai', 'Tamil Nadu', '609001', 13000, 3, 2, 1400, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment5_j8m3b4.jpg', 'demo_public_id_34', TRUE);

-- District: Kanyakumari
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Nagercoil Modern Flat', 'A beautiful and spacious property located in the heart of Kanyakumari, perfect for comfortable living.', 'Nagercoil Modern Flat, Kanyakumari', 'Kanyakumari', 'Tamil Nadu', '629001', 16000, 2, 1, 1100, 'APARTMENT', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/house1_g0zjqk.jpg', 'demo_public_id_35', TRUE);

-- District: Tiruvallur
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Avadi Suburban House', 'A beautiful and spacious property located in the heart of Tiruvallur, perfect for comfortable living.', 'Avadi Suburban House, Tiruvallur', 'Tiruvallur', 'Tamil Nadu', '600054', 14000, 2, 1, 1000, 'HOUSE', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'INACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/apartment2_m5z00y.jpg', 'demo_public_id_36', TRUE);

-- District: Ranipet
INSERT INTO properties (title, description, address, city, state, pincode, rent, bedrooms, bathrooms, area_sqft, property_type, furnishing, amenities, status, owner_id)
VALUES ('Arcot Commercial PG', 'A beautiful and spacious property located in the heart of Ranipet, perfect for comfortable living.', 'Arcot Commercial PG, Ranipet', 'Ranipet', 'Tamil Nadu', '632503', 5000, 1, 1, 200, 'PG', 'SEMI_FURNISHED', '["WiFi","Parking","Security","Water Supply"]', 'ACTIVE', @owner_id);

SET @last_prop_id = LAST_INSERT_ID();
INSERT INTO property_images (property_id, image_url, cloudinary_public_id, is_primary)
VALUES (@last_prop_id, 'https://res.cloudinary.com/demo/image/upload/v1689230492/villa3_f0x1t2.jpg', 'demo_public_id_37', TRUE);

COMMIT;
