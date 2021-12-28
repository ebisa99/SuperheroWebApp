DROP DATABASE IF EXISTS SuperheroSightings;
CREATE DATABASE SuperheroSightings;

USE SuperheroSightings;
/*
insert into SuperPower(superPowerName, superPowerDescription)value('super power1', 'superpower description 1');
insert into SuperPower(superPowerName, superPowerDescription)value('super power2', 'superpower description 2');
insert into SuperPower(superPowerName, superPowerDescription)value('super power3', 'superpower description 3');
insert into Superhero(heroName, heroDescription, isHero, superPowerId)
values('hero1', 'hero1 description', true, 1);
insert into Superhero(heroName, heroDescription, isHero, superPowerId)
values('hero2', 'hero2 description', true, 2);
insert into Superhero(heroName, heroDescription, isHero, superPowerId)
values('hero3', 'hero3 description', true, 2);
insert into Address(street, city , state, country, zipcode)
values('123 main st', 'st.paul', 'MN', 'USA', '55332');
insert into Address(street, city , state, country, zipcode)
values('123 st.paul st', 'st.paul', 'MN', 'USA', '55112');
insert into Address(street, city , state, country, zipcode)
values('123 dunlap st', 'st.paul', 'MN', 'USA', '55222');
insert into Location(locationName, locationDescription , longitude, latitude, addressId)
values('location1', 'location1 description', '23.33333', '33.3333', 2);
insert into Location(locationName, locationDescription , longitude, latitude, addressId)
values('location2', 'location2 description', '23.33333', '23.3333', 1);
insert into Location(locationName, locationDescription , longitude, latitude, addressId)
values('location3', 'location3 description', '23.33333', '43.3333', 3);
insert into Sighting(sightingDate, locationId, heroId)values('2021-10-27', 1, 2);
insert into Sighting(sightingDate, locationId, heroId)values('2021-11-27', 2, 1);
insert into Sighting(sightingDate, locationId, heroId)values('2021-12-27', 3, 2);
insert into Organization(organizationName, organizationDescription, phone, email, addressId)
values('honame', 'heroO description1', '6789078123', 'ho@gmail.com', 1);
insert into Organization(organizationName, organizationDescription, phone, email, addressId)
values('honame1', 'heroO description1', '6789078123', 'ho@gmail.com', 2);
insert into Organization(organizationName, organizationDescription, phone, email, addressId)
values('honame2', 'heroO description2', '6789078123', 'ho@gmail.com', 3);
insert into HeroOrganization(heroId, organizationId)values(1, 2);
insert into HeroOrganization(heroId, organizationId)values(2, 1);
insert into HeroOrganization(heroId, organizationId)values(3, 2);
USE SuperheroSightings;
select sp.* from SuperPower sp
join Superhero sh on sh.superPowerId = sp.superPowerId
where sh.heroId = 1;
SET SQL_SAFE_UPDATES = 0;
DELETE ho FROM HeroOrganization ho
JOIN organization o ON o.organizationId = ho.organizationId
WHERE o.addressId =  1;
DELETE FROM organization WHERE addressId =  1;
DELETE s FROM sighting s
JOIN Location l ON l.locationId = s.locationId
WHERE l.addressId =  1;
delete from Location where addressId = 1;
DELETE FROM Address WHERE addressId =  1;
*/
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