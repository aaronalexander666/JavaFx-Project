DROP DATABASE IF EXISTS javafxdemo; 
CREATE DATABASE javafxdemo; 
USE javafxdemo; 

CREATE TABLE tblstaff (
    staffid INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(50) NULL,
    lastname VARCHAR(50) NOT NULL,
    street1 VARCHAR(50) NOT NULL,
    street2 VARCHAR(50) NULL,
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    mobilephone CHAR(14) NOT NULL,
    email VARCHAR(50) NULL,
    role VARCHAR(50) NOT NULL,
    note LONGTEXT NULL,
    active TINYINT NOT NULL,
    PRIMARY KEY (staffid)
); [cite: 59]

INSERT INTO tblstaff VALUES 
(null, 'John', 'Jones', '15 Century Drive', 'Macoya Industrial Estate', 'Macoya', 'Trinidad and Tobago', '(868) 785-6247', 'johnjones@gmail.com', 'Manager', 'Approaching retirement', 1); [cite: 60]
