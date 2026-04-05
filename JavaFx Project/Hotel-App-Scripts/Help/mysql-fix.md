Certainly! Here is a dedicated markdown documentation file for your MySQL diagnostic script.

---

# 🩺 MySQL Database Health Check - Documentation

This script is a diagnostic tool designed to verify that your **MySQL Server** is active and accessible. It is specifically useful for resolving connection issues in the **Hotel Booking Project**.

## 🛠 Prerequisites
* **Environment:** Windows PowerShell
* **Privileges:** Administrator access (required to start/stop system services).
* **Database:** MySQL Server (installed via standalone installer, XAMPP, or WAMP).

---

## 🚀 How to Use the Script

### 1. Save the Script
Save the code as **`db-check.ps1`**.

### 2. Run the Diagnostic
1.  Open **PowerShell** as **Administrator**.
2.  Navigate to your script folder.
3.  Run the command:
    ```powershell
    .\db-check.ps1
    ```

---

## 📊 Understanding the Output

The script performs three critical checks:

| Check | What it means | Solution if it fails |
| :--- | :--- | :--- |
| **MySQL Service** | Is the background engine "turned on"? | The script will attempt to start it automatically. |
| **Port 3306** | Is the "communication door" open for Java? | Check if a firewall is blocking the port or if another app is using it. |
| **XAMPP/WAMP** | Are you using a localized web server stack? | Ensure the MySQL module is "Started" in your XAMPP Control Panel. |

---

## 🛠 Manual Fixes
If the script reports a **CRITICAL** or **FAILURE** status, try these manual steps:

1.  **Restart the Service:**
    * Press `Win + R`, type `services.msc`, and press Enter.
    * Find **MySQL** (or **MySQL80**), right-click, and select **Restart**.
2.  **Check Credentials:**
    * Ensure your Java code’s `application.properties` or `hibernate.cfg.xml` uses the same port (3306) and credentials verified here.
3.  **Firewall:**
    * Ensure Windows Defender or your antivirus isn't blocking `mysqld.exe`.

---

### Future Integration
Something future is thick we are working on—keeping your database health in check is vital for the **big mapping** operations and room availability indexing required by the hotel system.

> **Tip:** Keep this script in the root of your project folder so you can run it instantly whenever you see a "Connection Refused" error in your Java logs.