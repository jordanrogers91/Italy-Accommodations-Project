DROP TABLE IF EXISTS accommodation_amenity;
DROP TABLE IF EXISTS amenity;
DROP TABLE IF EXISTS accommodation;
DROP TABLE IF EXISTS city;

CREATE TABLE city (
  city_id int NOT NULL AUTO_INCREMENT,
  city_name varchar(256) NOT NULL,
  city_area varchar(128) NOT NULL,
  PRIMARY KEY (city_id)
);

CREATE TABLE accommodation (
  accommodation_id int NOT NULL AUTO_INCREMENT,
  city_id int NOT NULL,
  accommodation_name varchar(128) NOT NULL,
  accommodation_address varchar(256) NOT NULL,
  accommodation_phone_number varchar(128),
  accommodation_rating int,
  PRIMARY KEY(accommodation_id),
  FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

CREATE TABLE amenity (
  amenity_id int NOT NULL AUTO_INCREMENT,
  amenity_name varchar(128),
  PRIMARY KEY(amenity_id)
);
  
CREATE TABLE accommodation_amenity (
  accommodation_id int NOT NULL,
  amenity_id int NOT NULL,
  FOREIGN KEY (accommodation_id) REFERENCES accommodation (accommodation_id) ON DELETE CASCADE,
  FOREIGN KEY (amenity_id) REFERENCES amenity (amenity_id) ON DELETE CASCADE
);