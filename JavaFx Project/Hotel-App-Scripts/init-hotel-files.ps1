# 1. Define Paths
$pkgPath = "src\main\java\com\cts\javafxdemo"
$resPath = "src\main\resources"

# 2. Create Directory Structure
Write-Host "Creating folder structure..." -ForegroundColor Cyan
New-Item -ItemType Directory -Force -Path $pkgPath
New-Item -ItemType Directory -Force -Path $resPath

# 3. Create CustomersListController.java
$customersListCode = @"
package com.cts.javafxdemo;

public class CustomersListController {
    Integer customerId; [cite: 63]
    String businessName, firstname, lastname, street, village, city, country, mobilePhone, businessPhone, email, customerType, note; [cite: 64]
    Boolean active; [cite: 65]

    public CustomersListController(Integer customerId, String businessName, String firstname, String lastname,
                                  String street, String village, String city, String country,
                                  String mobilePhone, String businessPhone, String email, String customerType,
                                  String note, Boolean active) { [cite: 65, 66]
        this.customerId = customerId; [cite: 66]
        this.businessName = businessName; [cite: 67]
        this.firstname = firstname; [cite: 67]
        this.lastname = lastname; [cite: 67]
        this.street = street; [cite: 67]
        this.village = village; [cite: 67]
        this.city = city; [cite: 67]
        this.country = country; [cite: 68]
        this.mobilePhone = mobilePhone; [cite: 68]
        this.businessPhone = businessPhone; [cite: 68]
        this.email = email; [cite: 68]
        this.customerType = customerType; [cite: 68]
        this.note = note; [cite: 68]
        this.active = active; [cite: 69]
    }

    public Integer getCustomerId() { return customerId; } [cite: 69]
    public String getBusinessName() { return businessName; } [cite: 70]
    public String getFirstname() { return firstname; } [cite: 71]
    public String getLastname() { return lastname; } [cite: 72]
    public String getStreet() { return street; } [cite: 73]
    public String getVillage() { return village; } [cite: 74]
    public String getCity() { return city; } [cite: 75]
    public String getCountry() { return country; } [cite: 76]
    public String getMobilePhone() { return mobilePhone; } [cite: 77]
    public String getBusinessPhone() { return businessPhone; } [cite: 78]
    public String getEmail() { return email; } [cite: 79]
    public String getCustomerType() { return customerType; } [cite: 80]
    public String getNote() { return note; } [cite: 81]
    public Boolean getActive() { return active; } [cite: 82]
}
"@
Set-Content -Path "$pkgPath\CustomersListController.java" -Value $customersListCode

# 4. Create JavaFxDemoController.java
$demoControllerCode = @"
package com.cts.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JavaFxDemoController {
    @FXML
    private Label welcomeText; [cite: 1]

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!"); [cite: 2]
    }
}
"@
Set-Content -Path "$pkgPath\JavaFxDemoController.java" -Value $demoControllerCode

# 5. Create Database Setup Script
$sqlCode = @"
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
"@
Set-Content -Path "setup_database.sql" -Value $sqlCode

Write-Host "Files created successfully!" -ForegroundColor Green