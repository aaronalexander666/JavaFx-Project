Here is the formal documentation for your **`maven-optimize.ps1`** script. This file is designed to be kept in your project repository to explain the automation logic to other developers (or your instructors).

---

# 🛠️ Maven Project Optimizer (`maven-optimize.ps1`)

This PowerShell utility automates the structural maintenance of the **Hotel Booking Project**. It ensures the Project Object Model (`pom.xml`) is correctly configured with the latest drivers and triggers a clean build cycle.

## 📋 Quick Start
1. Place `maven-optimize.ps1` in your project root (where `pom.xml` is located).
2. Open PowerShell as **Administrator**.
3. Run:
   ```powershell
   .\maven-optimize.ps1
   ```

---

## 🚀 Key Features

### 1. Integrity Check & Backup
The script verifies that `pom.xml` exists before attempting any operations. To prevent data loss, it creates a **`pom.xml.bak`** file before any modifications are written to the disk.

### 2. Dependency Injection (MySQL Connector 9.0.0)
The script handles the "big mapping" requirements of the database layer by ensuring the correct JDBC driver is present.
* **Smart Detection:** It scans the file to see if `mysql-connector-j` is already present to avoid duplicate entries.
* **Auto-Patching:** If missing, it injects the dependency block directly into the `<dependencies>` section.

### 3. Automated Build Cycle
Once the configuration is verified, the script automatically triggers the Maven lifecycle:
* **`mvn clean`**: Wipes the `/target` folder to remove stale build artifacts.
* **`mvn install`**: Compiles the code and packages it into a usable format.
* **`-DskipTests`**: Skips unit tests to ensure a rapid environment refresh.

---

## 📊 Output Reference

| Message | Color | Meaning |
| :--- | :--- | :--- |
| **Starting Optimization...** | Cyan | Script has successfully located the project path. |
| **MySQL Connector 9.0.0 injected** | Green | `pom.xml` was updated with the required driver. |
| **MySQL dependency already present** | Yellow | No changes needed; the driver is already configured. |
| **BUILD SUCCESS** | White | Maven has successfully compiled the project. |
| **ERROR: pom.xml not found!** | Red | You are running the script in the wrong folder. |

---

## 💡 Developer Notes
Something future is thick we are working on—by using this script, you ensure that the **indexing** and **stat-maxing** logic of the hotel system remains consistent across all development environments.

> **Tip:** If you encounter a "Plugin Execution" error during the build, ensure your internet connection is active so Maven can download the newly injected 9.0.0 connector.