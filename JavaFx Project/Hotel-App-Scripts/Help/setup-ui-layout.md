# 🎨 UI Layout Generator (`setup-ui-layout.ps1`) - Documentation

This PowerShell utility automates the creation of the **`hello.fxml`** file. This file acts as the visual "Blueprint" for your Hotel Guest Management System, defining how data is organized and displayed to the user in a modern, responsive window.

## 📋 Quick Start
1. Place `setup-ui-layout.ps1` in your project root folder.
2. Open PowerShell and run:
   ```powershell
   .\setup-ui-layout.ps1
   ```

---

## 🚀 Key Features

### 1. Responsive FXML Architecture
The script generates a **VBox** (Vertical Box) layout, ensuring all UI elements stay aligned in the center. 
* **Resolution:** Pre-configured for a comfortable **1000x600** workspace.
* **Compatibility:** Explicitly targets the **JavaFX 23.0.1** namespace to match your modern JDK 23 environment.

### 2. High-Capacity Data Table (`TableView`)
The core of the UI is a sophisticated `TableView` control. It is pre-configured with 9 distinct columns designed for the "big mapping" of guest records directly from your database:
* **Identity:** ID, Business Name, First/Last Name.
* **Geography:** City.
* **Communications:** Mobile Phone, Business Phone, and Email.
* **Categorization:** Customer Type.

### 3. Controller Synchronization
The FXML is hard-linked to the `CustomerListReportController`. This bridge ensures that when the application launches, the Java code can successfully populate the table and handle button clicks without configuration errors.

### 4. Styled Action Bar
Includes an **HBox** at the bottom containing a "PDF Export" button.
* **Styling:** Applied a modern green (`#2ecc71`) background with bold white text for a high-contrast, professional appearance.
* **Event Hook:** Pre-wired to the `#printToPdf` method in your Java controller.

---

## 🛠️ UI Element Map

| Element | ID (`fx:id`) | Functional Role |
| :--- | :--- | :--- |
| **Table** | `tblCustomers` | The "Live View" linked to your MySQL database. |
| **Button** | `btnPrintToPdf` | Triggers the report generation logic. |
| **VBox** | (Root) | Manages vertical flow and automatic table resizing. |

---

## 📊 Project Integration Flow

| Step | Goal | Script |
| :--- | :--- | :--- |
| **1. Connectivity** | Establish Java/DB Link | `setup-db-link.ps1` |
| **2. Interface** | **Generate UI (FXML)** | **`setup-ui-layout.ps1`** |
| **3. Execution** | Build and Launch | `rebuild-and-launch.ps1` |

---

## 💡 Developer Notes
Something future is thick we are working on—as you finalize your CTS College submission, this FXML file provides the professional "Front End" necessary for a high-grade project. By utilizing `VBox.vgrow="ALWAYS"`, the guest list will automatically scale to utilize all available screen space, ensuring your **indexing** of guest data remains clear and readable.

> **Pro-Tip:** If you decide to rename your controller package, ensure you update the `fx:controller` string in this FXML file to match, or the application will fail to launch.