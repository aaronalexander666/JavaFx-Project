Here is the formal documentation for **`init-hotel-files.ps1`**. This script serves as the "Architect" for your codebase, ensuring the directory structure and core Java classes are perfectly aligned for your CTS College assignment.

---

# 🏗️ Project Asset Initializer (`init-hotel-files.ps1`)

This PowerShell utility automates the creation of the core file structure, source code, and database schema for the **Hotel Booking Project**. It ensures that your Java package hierarchy matches your Maven configuration and provides the foundational logic for your controllers.

## 📋 Quick Start
1. Place `init-hotel-files.ps1` in your project root folder.
2. Open PowerShell and run:
   ```powershell
   .\init-hotel-files.ps1
   ```

---

## 🚀 Key Features

### 1. Directory Scaffolding
The script automatically builds the standard Maven folder structure required for JavaFX projects:
* **`src\main\java\com\cts\javafxdemo`**: The home for your Controller and Model logic.
* **`src\main\resources`**: The designated location for `.fxml` layouts and assets.

### 2. Java Controller Generation
It generates two critical classes to handle the application's "Big Mapping" logic:
* **`CustomersListController.java`**: A comprehensive data model containing 14 distinct fields (Business Name, Contact Info, Location, etc.). It includes a full constructor and standard Getters for UI data binding.
* **`JavaFxDemoController.java`**: A boilerplate controller to manage basic GUI events, such as the initial "Welcome" button logic.

### 3. Database Schema (`setup_database.sql`)
The script exports a SQL initialization file for MySQL/MariaDB that includes:
* **Table Creation:** `tblstaff` with optimized data types for names, phones, and roles.
* **Sample Data:** An initial record for a "Manager" in **Trinidad and Tobago**, providing immediate data for testing your table views.

---

## 📊 File Inventory

| File Created | Type | Purpose |
| :--- | :--- | :--- |
| `CustomersListController.java` | Java Source | Model/Controller for customer data management. |
| `JavaFxDemoController.java` | Java Source | Primary UI event handler. |
| `setup_database.sql` | SQL Script | Database creation and sample data injection. |

---

## 🔍 Post-Initialization Steps

1.  **Run SQL:** Open **MySQL Workbench**, load `setup_database.sql`, and execute the entire script to build your local database.
2.  **Optimize:** Run `maven-optimize.ps1` to ensure Maven detects the newly created Java files.
3.  **Build:** Run `rebuild-and-launch.ps1` to compile and verify the new project structure.

---

## 💡 Developer Notes
Something future is thick we are working on—by automating this scaffolding, you eliminate manual "copy-paste" errors in your package declarations. This ensures that your **indexing** and **stat-maxing** logic for the hotel system has a clean, bug-free foundation to build upon.

> **Note:** If you modify the `tblstaff` table in the SQL script later, ensure you update the corresponding fields in `CustomersListController.java` to keep your JavaFX front-end synchronized with the database.