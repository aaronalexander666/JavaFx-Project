Since you’ve chosen the name **`rebuild-and-launch.ps1`**, here is the formal `.md` documentation for it. This name perfectly captures the "Clean and Run" nature of the script.

---

# 🚀 Project Rebuild & Launch Engine (`rebuild-and-launch.ps1`)

This PowerShell script is the primary execution point for the **Hotel Booking Project**. It provides a "Clean Room" environment, ensuring the application is compiled and launched using **Official JDK 23** and **JavaFX 23** support, bypassing any conflicting system-wide Java settings.

## 📋 Quick Start
1. Place `rebuild-and-launch.ps1` in your project root folder.
2. Verify your JDK 23 is installed at: `C:\Program Files\Java\jdk-23`.
3. Open PowerShell and execute:
   ```powershell
   .\rebuild-and-launch.ps1
   ```

---

## 🛠️ How It Works

### 1. Isolated JDK 23 Targeting
The script explicitly maps the `$JAVA_HOME` to the **JDK 23** directory. This is critical for:
* **Modern JavaFX Support:** Leveraging the latest hardware-accelerated rendering pipelines.
* **Performance:** Utilizing **Virtual Threads** to handle high-concurrency room booking logic.

### 2. Manual Maven Bootstrapping
By manually invoking the `plexus-classworlds` launcher, the script avoids common errors caused by incorrect environment variables. It directly informs the Java executable where the Maven home and configuration files are located, creating a "Future-Thick" bridge between your source code and the runtime.

### 3. The "Clean & Run" Lifecycle
The script executes the `clean javafx:run` goal, which performs two vital tasks:
* **Clean:** Deletes the `/target` folder to ensure no stale or incompatible bytecode from older Java versions remains.
* **JavaFX Run:** Compiles the fresh code and launches the Graphical User Interface (GUI) immediately.

---

## 🔍 Troubleshooting

| Issue | Resolution |
| :--- | :--- |
| **"JDK 23 path not found"** | Check `C:\Program Files\Java`. If your folder has a minor version (e.g., `jdk-23.0.1`), update the `$JAVA_HOME` variable at the top of the script. |
| **"Plexus JAR missing"** | Ensure Maven is correctly placed in `C:\Program Files\Apache\maven`. |
| **"JavaFX Module Error"** | Ensure your `pom.xml` dependencies are set to version `23` to match this launcher. |

---

## 💡 Developer Notes
Something future is thick we are working on—as you finalize the **big mapping** and **indexing** for the Hotel System, using this specific rebuilder ensures that the database drivers and UI components are synchronized. This prevents "ClassCastExceptions" or "Version Mismatch" errors during your final presentation or submission.

> **Note:** If you upgrade your environment later, simply edit the `$JAVA_HOME` path in the script to point to the new version.