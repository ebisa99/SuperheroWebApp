DROP DATABASE IF EXISTS SuperheroSightingsTest;
CREATE DATABASE SuperheroSightingsTest;

USE SuperheroSightingsTest;

CREATE TABLE SuperPower(
	superPowerId INT PRIMARY KEY AUTO_INCREMENT,
	superPowerName VARCHAR(45) NOT NULL,
    superPowerDescription VARCHAR(200) NOT NULL
);
CREATE TABLE Superhero(
	heroId INT PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR(45) NOT NULL,
    heroDescription VARCHAR(200) NOT NULL,
    isHero BOOLEAN DEFAULT true,
    imageFileName VARCHAR(45),
    superPowerId INT NOT NULL,
    FOREIGN KEY (superPowerId) REFERENCES SuperPower(superPowerId)
);
CREATE TABLE Address(
	addressId INT PRIMARY KEY AUTO_INCREMENT,
	street VARCHAR(55) NOT NULL,
    city VARCHAR(30) NOT NULL,
    state CHAR(2) NOT NULL,
    country VARCHAR(30) NOT NULL,
    zipcode CHAR(5) NOT NULL
);
CREATE TABLE Location(
	locationId INT PRIMARY KEY AUTO_INCREMENT,
	locationName VARCHAR(45) NOT NULL,
    locationDescription VARCHAR(200) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,
    latitude DECIMAL(8, 6) NOT NULL,
    addressId INT NOT NULL,
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);
CREATE TABLE Sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    sightingDate DATE NOT NULL,
    locationId INT NOT NULL,
    heroId INT NOT NULL,
    FOREIGN KEY (locationId) REFERENCES Location(locationId),
    FOREIGN KEY (heroId) REFERENCES Superhero(heroId)
);
CREATE TABLE Organization(
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
    organizationName VARCHAR(45) NOT NULL,
    organizationDescription VARCHAR(200) NOT NULL,
    phone CHAR(10) NOT NULL,
    email VARCHAR(40) NOT NULL,
    addressId INT NOT NULL,
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);
CREATE TABLE HeroOrganization(
	heroId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY(heroId, organizationId),
    FOREIGN KEY (heroId) REFERENCES Superhero(heroId),
    FOREIGN KEY (organizationId) REFERENCES Organization(organizationId)
);