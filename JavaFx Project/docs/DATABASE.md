# Database Guide - JavaFX Hotel Management System

## 🗄️ Database Overview

The application uses **MySQL 8.0+** with a relational database named **javafxdemo**. This document covers schema, relationships, setup, and management.

## 📊 Database Schema

### Entity Relationship Diagram

```
┌─────────────────────┐
│   tblcustomer       │
├─────────────────────┤
│ PK: CustomerID      │
│    FirstName        │
│    LastName         │
│    Email            │
│    MobilePhone      │
│    Address Info     │
│    CustomerType     │
└─────────────────────┘
        │
        │ 1:M
        │
        ▼
┌─────────────────────┐       ┌──────────────────┐
│   tblbooking        │───M:1─│    tblroom       │
├─────────────────────┤       ├──────────────────┤
│ PK: BookingID       │       │ PK: RoomID       │
│ FK: CustomerID      │       │    RoomNumber    │
│ FK: RoomID          │       │    RoomType      │
│ FK: StaffID         │       │    Price         │
│    CheckInDate      │       │    Status        │
│    CheckOutDate     │       └──────────────────┘
│    Status           │
│    Amount           │
└─────────────────────┘
        │
        │ M:1
        │
        ▼
┌──────────────────────┐
│    tblstaff          │
├──────────────────────┤
│ PK: StaffID          │
│    Username          │
│    Password          │
│    FullName          │
│    Role              │
└──────────────────────┘
```

## 📋 Table Specifications

### 1. tblcustomer - Customer Information

```sql
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
```

**Columns**:
- `CustomerID`: Unique identifier (auto-incremented)
- `BusinessName`: Optional business name for corporate customers
- `FirstName`, `LastName`: Customer name
- `Street`, `Village`, `City`, `Country`: Address information
- `MobilePhone`: Primary contact number
- `BusinessPhone`: Secondary/business contact number
- `Email`: Email address (unique, used for identification)
- `CustomerType`: Classification (Standard, VIP, Corporate, etc.)
- `Note`: Additional notes or preferences
- `Active`: Soft delete flag (0 = inactive, 1 = active)

**Indexes**:
- PK on CustomerID
- Unique index on Email
- Index on City for location-based queries

---

### 2. tblstaff - User Accounts & Authentication

```sql
CREATE TABLE tblstaff (
    StaffID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL,
    FullName VARCHAR(100) NOT NULL,
    Role VARCHAR(20) NOT NULL
);
```

**Columns**:
- `StaffID`: Unique identifier
- `Username`: Login identifier (unique, case-sensitive)
- `Password`: Stored password (should be hashed in production)
- `FullName`: Staff member's full name
- `Role`: Access level (Admin, Manager, Staff, etc.)

**Security Notes**:
- Currently stores plain text passwords
- **Recommendation**: Implement bcrypt or similar hashing
- Consider adding LastLogin timestamp
- Add LoginAttempts for brute-force protection

**Default Admin Account**:
```
Username: admin
Password: admin123
Role: Admin
```

**Indexes**:
- PK on StaffID
- Unique index on Username

---

### 3. tblroom - Room Inventory

```sql
CREATE TABLE tblroom (
    RoomID INT PRIMARY KEY AUTO_INCREMENT,
    RoomNumber VARCHAR(10) UNIQUE NOT NULL,
    RoomType VARCHAR(50) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Status VARCHAR(20) DEFAULT 'Available'
);
```

**Columns**:
- `RoomID`: Unique identifier
- `RoomNumber`: Room number/identifier (unique, e.g., "101", "Deluxe-5")
- `RoomType`: Category (Single, Double, Suite, Deluxe, etc.)
- `Price`: Nightly rate in decimal format
- `Status`: Current state (Available, Occupied, Maintenance, Reserved)

**Room Types & Pricing** (Example Configuration):
```
┌────────────────┬─────────────────┬────────────────┐
│ Room Type      │ Price per Night │ Total Rooms    │
├────────────────┼─────────────────┼────────────────┤
│ Single         │ $50.00          │ 10             │
│ Double         │ $75.00          │ 15             │
│ Suite          │ $120.00         │ 5              │
│ Deluxe Suite   │ $180.00         │ 3              │
│ Penthouse      │ $300.00         │ 1              │
└────────────────┴─────────────────┴────────────────┘
```

**Indexes**:
- PK on RoomID
- Unique index on RoomNumber
- Index on Status for availability queries

---

### 4. tblbooking - Reservations & Bookings

```sql
CREATE TABLE tblbooking (
    BookingID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerID INT NOT NULL,
    RoomID INT NOT NULL,
    StaffID INT,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE NOT NULL,
    NumberOfGuests INT DEFAULT 1,
    SpecialRequests TEXT,
    BookingStatus VARCHAR(20) DEFAULT 'Confirmed',
    Amount DECIMAL(10,2),
    PaymentStatus VARCHAR(20) DEFAULT 'Pending',
    CreatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CustomerID) REFERENCES tblcustomer(CustomerID),
    FOREIGN KEY (RoomID) REFERENCES tblroom(RoomID),
    FOREIGN KEY (StaffID) REFERENCES tblstaff(StaffID)
);
```

**Columns**:
- `BookingID`: Unique booking identifier
- `CustomerID`: Foreign key to tblcustomer
- `RoomID`: Foreign key to tblroom (reserved room)
- `StaffID`: Foreign key to tblstaff (booking agent)
- `CheckInDate`: Arrival date
- `CheckOutDate`: Departure date
- `NumberOfGuests`: Count of guests using the room
- `SpecialRequests`: Guest notes (accessibility, preferences, etc.)
- `BookingStatus`: State (Confirmed, Cancelled, Completed, No-Show)
- `Amount`: Total booking amount (calculated or stored)
- `PaymentStatus`: Payment state (Pending, Paid, Refunded)
- `CreatedDate`: Timestamp of booking creation

**Business Rules**:
- CheckOutDate must be after CheckInDate
- NumberOfGuests >= 1
- Overlapping bookings not allowed for same room
- Cancellation within 24 hours before check-in may incur charges

**Indexes**:
- PK on BookingID
- FK indexes on CustomerID, RoomID, StaffID
- Index on CheckInDate, CheckOutDate for availability queries
- Index on BookingStatus for filtering

## 🔄 Relationships

### One-to-Many (1:M)
- **Customer → Bookings**: One customer can have multiple bookings
- **Room → Bookings**: One room can have multiple bookings over time
- **Staff → Bookings**: One staff member can process multiple bookings

### Referential Integrity
All foreign keys enforce referential integrity:
```sql
-- Customer bookings are deleted if customer is deleted
-- (or SET NULL depending on business rules)
```

## 📈 Sample Data

### Sample Customers
```sql
INSERT INTO tblcustomer (FirstName, LastName, Email, MobilePhone, CustomerType) VALUES
('John', 'Smith', 'john.smith@email.com', '555-0101', 'Standard'),
('Jane', 'Doe', 'jane.doe@email.com', '555-0102', 'VIP'),
('Mike', 'Johnson', 'mike.johnson@email.com', '555-0103', 'Corporate');
```

### Sample Rooms
```sql
INSERT INTO tblroom (RoomNumber, RoomType, Price, Status) VALUES
('101', 'Single', 50.00, 'Available'),
('102', 'Double', 75.00, 'Available'),
('201', 'Suite', 120.00, 'Occupied'),
('301', 'Deluxe Suite', 180.00, 'Available'),
('501', 'Penthouse', 300.00, 'Available');
```

### Sample Staff
```sql
INSERT INTO tblstaff (Username, Password, FullName, Role) VALUES
('admin', 'admin123', 'Administrator', 'Admin'),
('manager', 'manager123', 'John Manager', 'Manager'),
('staff', 'staff123', 'Jane Staff', 'Staff');
```

## 🗃️ Database Setup

### Initial Setup
```bash
# Method 1: Using SQL file
mysql -u root -p < db/hotel-bd.sql

# Method 2: Manual
mysql -u root -p
CREATE DATABASE IF NOT EXISTS javafxdemo;
USE javafxdemo;
SOURCE db/hotel-bd.sql;
```

### Backup & Restore

**Create Backup**:
```bash
mysqldump -u root -p javafxdemo > backup_$(date +%Y%m%d_%H%M%S).sql
```

**Restore from Backup**:
```bash
mysql -u root -p javafxdemo < backup_20260405_101530.sql
```

## 🔐 User Management

### Create Database User
```sql
-- Create new user (instead of using root)
CREATE USER 'hotelapp'@'localhost' IDENTIFIED BY 'secure_password';

-- Grant permissions
GRANT ALL PRIVILEGES ON javafxdemo.* TO 'hotelapp'@'localhost';
FLUSH PRIVILEGES;
```

### Update Application Credentials
Edit `src/main/java/com/cts/javafxdemo/DatabaseConnection.java`:
```java
private static final String DB_USER = "hotelapp";
private static final String DB_PASSWORD = "secure_password";
```

## 📊 Common Queries

### Find Available Rooms
```sql
SELECT * FROM tblroom 
WHERE Status = 'Available' 
AND RoomType = 'Double';
```

### Get Customer Booking History
```sql
SELECT b.BookingID, b.CheckInDate, b.CheckOutDate, r.RoomNumber, b.BookingStatus
FROM tblbooking b
JOIN tblroom r ON b.RoomID = r.RoomID
WHERE b.CustomerID = 1
ORDER BY b.CheckInDate DESC;
```

### Calculate Revenue
```sql
SELECT 
    MONTH(b.CheckInDate) as Month,
    SUM(b.Amount) as TotalRevenue,
    COUNT(b.BookingID) as BookingCount
FROM tblbooking b
WHERE YEAR(b.CheckInDate) = 2026
GROUP BY MONTH(b.CheckInDate);
```

### Find Overlapping Bookings (Conflict Detection)
```sql
SELECT * FROM tblbooking b1
WHERE b1.RoomID = ? 
AND b1.BookingStatus != 'Cancelled'
AND b1.CheckInDate < DATE_ADD(?, INTERVAL 1 DAY)
AND b1.CheckOutDate > ?;
```

## 🔧 Maintenance

### Check Database Health
```sql
-- Check table sizes
SELECT 
    TABLE_NAME,
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS size_mb
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'javafxdemo';

-- Check for missing indexes
ANALYZE TABLE tblbooking;
ANALYZE TABLE tblcustomer;
```

### Optimize Tables
```sql
OPTIMIZE TABLE tblcustomer;
OPTIMIZE TABLE tblstaff;
OPTIMIZE TABLE tblroom;
OPTIMIZE TABLE tblbooking;
```

### Clear Old Cancelled Bookings (Archive Strategy)
```sql
-- Archive old cancelled bookings (example: older than 1 year)
DELETE FROM tblbooking 
WHERE BookingStatus = 'Cancelled' 
AND CreatedDate < DATE_SUB(NOW(), INTERVAL 1 YEAR);
```

## 📋 Data Migration

### Export Data
```bash
# Export to CSV
SELECT * FROM tblbooking 
INTO OUTFILE '/tmp/bookings.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"';
```

### Import Data
```bash
LOAD DATA INFILE '/tmp/bookings.csv'
INTO TABLE tblbooking
FIELDS TERMINATED BY ','
ENCLOSED BY '"';
```

## 🔍 Troubleshooting

### Connection Issues
```bash
# Test MySQL connectivity
mysql -h localhost -u root -p -e "SELECT 1;"

# Check MySQL service status
sudo systemctl status mysql    # Linux
brew services list            # macOS
```

### Data Integrity Issues

**Check Foreign Key Constraints**:
```sql
-- Find orphaned bookings
SELECT b.* FROM tblbooking b
LEFT JOIN tblcustomer c ON b.CustomerID = c.CustomerID
WHERE c.CustomerID IS NULL;
```

**Fix Orphaned Records**:
```sql
DELETE FROM tblbooking WHERE CustomerID NOT IN (SELECT CustomerID FROM tblcustomer);
```

## 📚 Related Documentation

- [README.md](../README.md) - Project overview
- [ARCHITECTURE.md](ARCHITECTURE.md) - System design
- [SETUP.md](SETUP.md) - Installation guide

---

**Last Updated**: April 5, 2026
**Database Version**: MySQL 8.0+
**Schema Version**: 1.0
