CREATE DATABASE IF NOT EXISTS javafxdemo;
USE javafxdemo;
-- Use the database
USE javafxdemo;

-- Drop existing tables to recreate with correct structure
DROP TABLE IF EXISTS tblbooking;
DROP TABLE IF EXISTS tblroom;
DROP TABLE IF EXISTS tblstaff;
DROP TABLE IF EXISTS tblcustomer;

-- Customer Table (matches your Java Controller)
CREATE TABLE tblcustomer (
    CustomerID INT PRIMARY KEY AUTO_INCREMENT,
    BusinessName VARCHAR(100),
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Street VARCHAR(100),
    Village VARCHAR(100),
    City VARCHAR(50),
    Country VARCHAR(50),
    MobilePhone VARCHAR(20) NOT NULL,
    BusinessPhone VARCHAR(20),
    Email VARCHAR(100) UNIQUE NOT NULL,
    CustomerType VARCHAR(50) DEFAULT 'Standard',
    Note TEXT,
    Active TINYINT(1) DEFAULT 1
);

-- Staff Table
CREATE TABLE tblstaff (
    StaffID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL,
    FullName VARCHAR(100) NOT NULL,
    Role VARCHAR(20) NOT NULL
);

-- Room Table
CREATE TABLE tblroom (
    RoomID INT PRIMARY KEY AUTO_INCREMENT,
    RoomNumber VARCHAR(10) UNIQUE NOT NULL,
    RoomType VARCHAR(50) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Status VARCHAR(20) DEFAULT 'Available'
);

-- Booking Table
CREATE TABLE tblbooking (
    BookingID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerID INT NOT NULL,
    RoomID INT NOT NULL,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    TotalAmount DECIMAL(10,2) NOT NULL,
    Status VARCHAR(20) DEFAULT 'Pending',
    BookingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CustomerID) REFERENCES tblcustomer(CustomerID),
    FOREIGN KEY (RoomID) REFERENCES tblroom(RoomID)
);

-- Insert sample staff
INSERT INTO tblstaff (Username, Password, FullName, Role) 
VALUES ('admin', 'admin123', 'Administrator', 'Admin');

-- Insert sample rooms
INSERT INTO tblroom (RoomNumber, RoomType, Price, Status) VALUES
('101', 'Single', 80.00, 'Available'),
('102', 'Double', 120.00, 'Available'),
('103', 'Suite', 200.00, 'Available');

-- Insert sample customer (matches your Java code fields)
INSERT INTO tblcustomer (BusinessName, FirstName, LastName, Street, Village, City, Country, MobilePhone, Email, CustomerType, Active) 
VALUES ('Grand Hotel Ltd', 'John', 'Doe', '123 Beach Rd', 'Maracas', 'Port of Spain', 'Trinidad', '555-1234', 'john@email.com', 'VIP', 1);

-- Insert sample booking
INSERT INTO tblbooking (CustomerID, RoomID, CheckInDate, CheckOutDate, TotalAmount, Status) 
VALUES (1, 1, '2026-04-10', '2026-04-15', 400.00, 'Pending');