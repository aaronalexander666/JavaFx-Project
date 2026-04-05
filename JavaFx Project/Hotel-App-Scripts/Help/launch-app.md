# launch-app.ps1 - Production Application Launcher

**Status**: ✅ Production Ready  
**Complexity**: High  
**Priority**: Critical  
**Platform**: Windows PowerShell 5.0+

---

## 📋 Description

Production-grade launcher for the JavaFX Hotel Management System. This is the primary recommended method for launching the application. It includes comprehensive environment validation, module configuration, and error handling.

**Key Features**:
- Environment validation (4 critical paths)
- Byte Order Mark (BOM) detection
- Colored output for easy reading
- Error recovery options
- Module system setup
- Comprehensive logging

---

## 🎯 Purpose

Safely launch the Hotel Management application after verifying that all required components are available and correctly configured.

---

## 📝 Configuration Required

Before running, edit the script configuration section:

```powershell
$Config = @{
    JavaExe      = "C:\Program Files\Java\jdk-23\bin\java.exe"
    JavaFXLib    = "C:\path\to\javafx-sdk-23\lib"
    MysqlJar     = "C:\path\to\mysql-connector-j-9.6.0.jar"
    OutDir       = "$PSScriptRoot\out\production\javafxdemo"
    MainClass    = "com.cts.javafxdemo.Main"
    ProjectName  = "Hotel Management System"
}
```

### Configuration Details

| Setting | Default | Purpose | Example |
|---------|---------|---------|---------|
| `JavaExe` | JDK 23 path | Java executable | `C:\Program Files\Java\jdk-23\bin\java.exe` |
| `JavaFXLib` | SDK lib folder | JavaFX module path | `C:\JavaFX\javafx-sdk-23\lib` |
| `MysqlJar` | MySQL driver | Database connector | `C:\mysql\mysql-connector-j-9.6.0.jar` |
| `OutDir` | Project output | Compiled classes location | `.\out\production\javafxdemo` |
| `MainClass` | Entry point | Application main class | `com.cts.javafxdemo.Main` |

---

## ▶️ Usage

### Basic Execution

```powershell
# From project root or Hotel-App-Scripts directory
.\launch-app.ps1
```

### With Custom Configuration

Edit the script and update the `$Config` hashtable, then run:

```powershell
.\launch-app.ps1
```

---

## ✅ Pre-Flight Validation

The script automatically validates:

1. **Java Executable** - Checks if java.exe exists at specified path
2. **JavaFX Library** - Verifies JavaFX SDK lib folder
3. **MySQL JAR** - Validates MySQL Connector JAR file location
4. **Output Directory** - Confirms compiled classes are present

**All 4 paths must pass validation for launch**

---

## 📊 Execution Flow

```
1. Display system audit timestamp
                ↓
2. Validate all 4 critical paths
                ↓
3. If any path missing, show error and abort
                ↓
4. If all paths valid, proceed to launch
                ↓
5. Build complete command with modules
                ↓
6. Start Java process with arguments
                ↓
7. Application window appears
```

---

## 🔧 Modules Loaded

The script automatically loads required JavaFX modules:

```
--add-modules javafx.controls,javafx.fxml,java.sql
```

This enables:
- ✅ UI components (Button, Label, TextField, etc.)
- ✅ FXML layout loading
- ✅ SQL database connectivity

---

## 💻 Command Line Arguments

The launcher constructs the following Java command:

```
java --module-path <javafx-lib>
     --add-modules javafx.controls,javafx.fxml,java.sql
     -cp <out-directory>;<mysql-jar>
     com.cts.javafxdemo.Main
```

**Meanings**:
- `--module-path`: Where to find JavaFX modules
- `--add-modules`: Which modules to enable
- `-cp`: Classpath (where to find .class files and libraries)

---

## 🐛 Troubleshooting

### Error: "MISSING CRITICAL PATH: java.exe"

**Problem**: Java 23 not found at specified location

**Solution**:
1. Verify JDK 23 installation location:
   ```powershell
   dir "C:\Program Files\Java" | grep jdk
   ```

2. Update the script with correct path:
   ```powershell
   $Config.JavaExe = "C:\Program Files\Java\jdk-23\bin\java.exe"
   ```

3. Verify it exists:
   ```powershell
   Test-Path "C:\Program Files\Java\jdk-23\bin\java.exe"
   ```

---

### Error: "MISSING CRITICAL PATH: javafx"

**Problem**: JavaFX SDK not found at specified location

**Solution**:
1. Download JavaFX 23 SDK from [openjfx.io](https://openjfx.io/)

2. Extract to a known location (e.g., `C:\JavaFX\javafx-sdk-23\`)

3. Update script:
   ```powershell
   $Config.JavaFXLib = "C:\JavaFX\javafx-sdk-23\lib"
   ```

4. Verify:
   ```powershell
   Test-Path "C:\JavaFX\javafx-sdk-23\lib"
   ls "C:\JavaFX\javafx-sdk-23\lib"  # Should list .jar files
   ```

---

### Error: "MISSING CRITICAL PATH: mysql-connector"

**Problem**: MySQL JDBC driver not found

**Solution**:
1. Download MySQL Connector/J 9.6.0 from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/)

2. Place JAR in known location (e.g., `C:\MySQL\`)

3. Update script:
   ```powershell
   $Config.MysqlJar = "C:\MySQL\mysql-connector-j-9.6.0.jar"
   ```

4. Verify:
   ```powershell
   Test-Path "C:\MySQL\mysql-connector-j-9.6.0.jar"
   ```

---

### Error: "MISSING CRITICAL PATH: out directory"

**Problem**: Project has not been compiled yet

**Solution**:
1. Compile the project first:
   ```powershell
   cd ..  # Go to project root
   mvn clean compile
   ```

2. Or run:
   ```powershell
   .\clean-and-build.ps1
   ```

3. Then retry launcher

---

### Application Starts But Database Connection Fails

**Problem**: MySQL is not running or credentials are wrong

**Solution**:
1. Verify MySQL is running:
   ```powershell
   .\mysql-fixdb.ps1
   ```

2. Check credentials in `DatabaseConnection.java`:
   ```java
   private static final String DB_URL = "jdbc:mysql://localhost:3306/javafxdemo";
   private static final String DB_USER = "root";
   private static final String DB_PASSWORD = "password";
   ```

3. Verify database exists:
   ```powershell
   mysql -u root -p -e "SHOW DATABASES LIKE 'javafxdemo';"
   ```

---

### Application Crashes After Launch

**Problem**: UI or runtime error

**Solution**:
1. Check console output for error messages
2. Run audit to check for encoding issues:
   ```powershell
   .\audit-project.ps1
   ```

3. Fix any detected issues:
   ```powershell
   .\fix-project.ps1
   ```

4. Rebuild and retry

---

## 🎓 Advanced Configuration

### Change Main Class

If using different main class name:

```powershell
$Config.MainClass = "com.mycompany.MyMainClass"
```

### Change Output Directory

If build output is in different location:

```powershell
$Config.OutDir = "C:\full\path\to\compiled\classes"
```

### Add JVM Arguments

To add memory settings (modify the java command):

```powershell
$LaunchArgs = @(
    "-Xmx2g",  # Max 2GB memory
    "-Xms1g",  # Initial 1GB memory
    "--module-path", "$($Config.JavaFXLib)",
    # ... rest of arguments
)
```

---

## ✨ Success Indicators

### Script Validation Passed ✅
```
[*] System Audit at 2026-04-05 14:30:00
[+] All paths valid!
[+] Launching Hotel Management System...
```

### Application Launched ✅
- Window appears with login screen
- No error dialogs
- Database connection established (message in console)

---

## 📋 Exit Codes

| Code | Meaning |
|------|---------|
| 0 | Success - Application ran and exited normally |
| 1 | Validation failed - Path check failed |
| Other | Java runtime error - Check console output |

---

## 🔐 Security Notes

- Script does NOT store credentials
- Database credentials are in Java code (not ideal, but files are)
- Paths should be localized per computer
- Keep JAR locations private (MySQL driver contains dependencies)

---

## 📊 Performance

- Pre-flight validation: ~1-2 seconds
- Java startup: ~3-5 seconds
- UI rendering: ~2-3 seconds
- **Total time to application window: ~6-10 seconds**

---

## 🔄 Related Scripts

- [clean-and-build.ps1](clean-and-build.md) - Compile project
- [mysql-fixdb.ps1](mysql-fix.md) - Verify database
- [audit-project.ps1](../Help/audit-project.md) - Check project health
- [run_hotel_app.ps1](../Help/run_hotel_app.md) - Alternative launcher

---

## 📚 Related Documentation

- [SCRIPTS.md](../docs/SCRIPTS.md) - All scripts overview
- [SETUP.md](../docs/SETUP.md) - Installation guide
- [BUILDING.md](../docs/BUILDING.md) - Build guide

---

**Last Updated**: April 5, 2026  
**Tested With**: PowerShell 5.0+, Windows 10/11, JDK 23, JavaFX 23.0.1
