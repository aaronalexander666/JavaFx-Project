Here is the professional documentation file for this specific script.

---

# 🛠️ Project Optimizer & Dependency Manager - Documentation

This script automates the transition from manual JAR management to **Maven-driven dependency management**. It ensures your **Hotel Booking Project** is clean, standardized, and ready for submission.

## ⚠️ Execution Requirements
* **Location:** This script **must** be placed and executed from the **root folder** of your project (where `pom.xml` is located).
* **Environment:** Requires **Maven** to be installed and added to your System PATH (use `setup-hotel-project.ps1` first if needed).

---

## 🚀 Script Functions

### 1. Automated `pom.xml` Patching
The script scans your `pom.xml` for the `mysql-connector-j` dependency.
* **Backup:** It creates a `pom.xml.bak` file before making changes.
* **Injection:** If missing, it injects the **MySQL Connector 9.0.0** coordinates directly into your XML structure.

### 2. Conflict Resolution (The `\lib` Purge)
Manual `.jar` files in a `/lib` folder often cause "Duplicate Class" errors when Maven is also trying to manage the classpath.
* The script identifies any `mysql-connector*.jar` files in your local library folder and removes them.
* This ensures Maven remains the single "Source of Truth" for your project dependencies.

### 3. Automated Lifecycle Refresh
The script automatically triggers a full build cycle:
```powershell
mvn clean install -DskipTests
```
This forces Maven to download the new driver and verify that the project compiles without errors.

---

## 💻 Instructions

1.  **Save the script** as `project-optimizer.ps1` in your project root.
2.  **Open PowerShell** and navigate to your project:
    ```powershell
    cd "C:\Users\YourName\Documents\hotel-booking-system"
    ```
3.  **Run the optimizer:**
    ```powershell
    .\project-optimizer.ps1
    ```

---

## ✅ Success Indicators

| Output Message | Meaning |
| :--- | :--- |
| **"MySQL Dependency injected..."** | Your `pom.xml` was successfully updated to the latest driver. |
| **"Duplicate manual drivers removed."** | Your project is now "clean" and follows Maven best practices. |
| **"BUILD SUCCESS"** | The project is structurally sound and ready for the next development phase. |

---

### Structural Integrity
Something future is thick we are working on—as you implement the **big mapping** and **indexing** logic for the hotel's room management, this optimized structure prevents classpath conflicts that could crash the application during runtime.