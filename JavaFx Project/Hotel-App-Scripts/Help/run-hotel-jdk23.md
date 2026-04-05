Since this script acts as the specialized engine for your application, naming it **`run-hotel-jdk23.ps1`** is a smart move. It clearly identifies the runtime version, which is crucial for high-performance projects.

Here is the `.md` documentation for your launcher.

---

# 🚀 Hotel System Launcher (JDK 23)

This PowerShell script is a high-performance, manual entry-point for the **Hotel Booking Project**. It bypasses the standard Windows `PATH` to invoke **JDK 23** directly, ensuring the application uses the latest Java runtime features.

## 📋 Quick Start
1. Ensure your JDK is located at `C:\Program Files\Java\jdk-23`.
2. Place `run-hotel-jdk23.ps1` in your project root.
3. Open PowerShell and execute:
   ```powershell
   .\run-hotel-jdk23.ps1
   ```

---

## 🛠️ How It Works

### 1. Surgical Path Targeting
Instead of relying on global environment variables which might point to older Java versions (like JDK 8 or 17), this script hard-codes the path to the **JDK 23** binaries. This is essential for:
* **Virtual Threads:** Improving concurrency for multiple guest bookings.
* **Modern JavaFX:** Ensuring the GUI renders with the latest hardware acceleration.

### 2. Manual Maven Bootstrapping
The script manually configures the **Plexus Classworlds Launcher**. This is the underlying engine that starts Maven. By defining `-Dclassworlds.conf` and `-Dmaven.home` manually, we ensure the build environment is "Future-Thick" and isolated from system-wide configuration errors.

### 3. Integrated Lifecycle Command
The script automatically appends the `clean javafx:run` goal. This ensures:
* The `/target` folder is wiped (No stale bytecode).
* The JavaFX application launches immediately after a successful compile.

---

## 🔍 Troubleshooting

| Issue | Potential Fix |
| :--- | :--- |
| **"Cannot find path..."** | Open `C:\Program Files\Java` and check the folder name. If it is `jdk-23.0.x`, update the `$JAVA_HOME` variable in the script to match. |
| **"Plexus jar not found"** | Verify Maven is installed in `C:\Program Files\Apache\maven`. |
| **"Permission Denied"** | Ensure you are running PowerShell with the necessary permissions to access `Program Files`. |

---

## 💡 Developer Notes
Something future is thick we are working on—utilizing JDK 23 allows us to leverage the most efficient **memory mapping** and **indexing** techniques available in the modern Java ecosystem. This ensures the Hotel System remains snappy even when managing large datasets or complex room-availability logic.

> **Note:** If you upgrade to a newer JDK in the future, simply update the `$JAVA_HOME` string at the top of the script to keep your launcher current.