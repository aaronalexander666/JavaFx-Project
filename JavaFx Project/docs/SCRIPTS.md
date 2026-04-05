# Scripts Guide - JavaFX Hotel Management System

## 📜 PowerShell Automation Scripts

Complete reference guide for all automation scripts in the `Hotel-App-Scripts/` directory.

## ⚙️ Prerequisites for Scripts

- **Windows**: PowerShell 5.0 or higher
- **Execution Policy**: Must allow script execution
- **Admin Rights**: Some scripts require administrator privileges
- **System PATH**: Java, Maven, and MySQL must be in PATH

### Enable Script Execution

```powershell
# Allow scripts for current user
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Verify
Get-ExecutionPolicy
```

## 📋 Script Inventory

| Script | Purpose | Admin? | Frequency |
|--------|---------|--------|-----------|
| `launch-app.ps1` | Main application launcher | No | Daily |
| `run_hotel_app.ps1` | Alternative app runner | No | Daily |
| `run-hotel-jdk23.ps1` | JDK 23 specific launch | No | Daily |
| `clean-and-build.ps1` | Full Maven build | No | Weekly |
| `maven-optimize.ps1` | Maven cache optimization | No | Monthly |
| `mysql-fixdb.ps1` | Database initialization | Yes | Once |
| `audit-project.ps1` | Project health check | No | Weekly |
| `fix-project.ps1` | Auto-fix common issues | Yes | As needed |
| `Invoke-Launch-fixed-1.ps1` | Legacy launcher | No | Deprecated |
| `init-hotel-files.ps1` | Initialize project files | No | Once |
| `set-hotel-project.ps1` | Configure project paths | Yes | As needed |

## 🚀 Main Scripts

### 1. launch-app.ps1 - Production Application Launcher

**Purpose**: Production-grade launcher with environment validation

**Usage**:
```powershell
.\Hotel-App-Scripts\launch-app.ps1
```

**Features**:
- ✅ Java environment validation
- ✅ JavaFX library path verification
- ✅ MySQL JAR availability check
- ✅ Comprehensive error reporting
- ✅ Graceful failure handling

**Configuration Required**:

Edit the script header before first use:
```powershell
$Config = @{
    JavaExe      = "C:\Program Files\Java\jdk-23\bin\java.exe"
    JavaFXLib    = "C:\path\to\javafx-sdk-23\lib"
    MysqlJar     = "C:\path\to\mysql-connector-j-9.6.0.jar"
    OutDir       = "$PSScriptRoot\out\production\javafxdemo"
    MainClass    = "com.cts.javafxdemo.Main"
}
```

**Exit Codes**:
- `0`: Success
- `1`: Environment check failed
- `2`: Launch failed

---

### 2. run_hotel_app.ps1 - Alternative Application Runner

**Purpose**: Simpler application launcher with basic verification

**Usage**:
```powershell
.\Hotel-App-Scripts\run_hotel_app.ps1
```

**When to Use**:
- Quick development launches
- Less verbose than launch-app.ps1
- Suitable for configured environments

---

### 3. run-hotel-jdk23.ps1 - JDK 23 Specific Launcher

**Purpose**: Run application with explicit JDK 23 configuration

**Usage**:
```powershell
.\Hotel-App-Scripts\run-hotel-jdk23.ps1
```

**What It Does**:
- Forces JDK 23 runtime
- Verifies JDK 23 installation
- Sets appropriate JVM parameters
- Handles module system correctly

---

## 🔨 Build Scripts

### 4. clean-and-build.ps1 - Complete Maven Build

**Purpose**: Perform clean Maven build of the application

**Usage**:
```powershell
.\Hotel-App-Scripts\clean-and-build.ps1
```

**Steps Performed**:
1. Remove previous build artifacts
2. Download dependencies
3. Compile source code
4. Run tests (if available)
5. Package application
6. Install to local Maven repository

**Output**:
- Compiled classes: `target/classes/`
- JAR file: `target/javafxdemo-1.0.jar`
- Build logs: Console output

**Expected Duration**: 45-60 seconds for clean build

**Troubleshooting**:
```powershell
# If build fails, try:
# 1. Clear Maven cache
rm -Recurse $env:USERPROFILE\.m2\repository

# 2. Rebuild
.\Hotel-App-Scripts\clean-and-build.ps1

# 3. Check dependencies
mvn dependency:resolve
```

---

### 5. maven-optimize.ps1 - Maven Build Optimization

**Purpose**: Optimize Maven settings for faster builds

**Usage**:
```powershell
.\Hotel-App-Scripts\maven-optimize.ps1
```

**What It Optimizes**:
- Maven JVM memory settings
- Build parallelization
- Dependency caching
- Plugin optimization

**Benefits**:
- ~30% faster subsequent builds
- Better resource utilization
- Improved cache hit ratio

**When to Run**: Monthly or after adding major dependencies

---

## 🗄️ Database Scripts

### 6. mysql-fixdb.ps1 - Database Initialization and Repair

**Purpose**: Initialize MySQL database and fix common issues

**Usage** (Requires Admin):
```powershell
# Run as Administrator
.\Hotel-App-Scripts\mysql-fixdb.ps1
```

**Functions**:
- ✅ Check MySQL service status
- ✅ Create javafxdemo database
- ✅ Import schema from SQL files
- ✅ Set proper user permissions
- ✅ Test database connectivity

**Prerequisites**:
- MySQL Server installed and running
- Administrator privileges
- MySQL root user configured

**What It Does**:
1. Verifies MySQL service running
2. Connects to MySQL server
3. Creates database if not exists
4. Imports hotel-bd.sql schema
5. Tests initial connection

**When to Run**:
- Initial setup
- Database corruption recovery
- Schema updates

---

## 🔍 Diagnostic & Maintenance Scripts

### 7. audit-project.ps1 - Project Health Check

**Purpose**: Audit project structure and identify issues

**Usage**:
```powershell
.\Hotel-App-Scripts\audit-project.ps1
```

**Checks Performed**:
- ✅ File structure validation
- ✅ Required files present
- ✅ Java compilation
- ✅ Maven configuration validity
- ✅ Database connectivity
- ✅ Dependencies available
- ✅ Configuration issues
- ✅ Common problems

**Output Example**:
```
[PASS] Project structure valid
[PASS] Java files present: 15
[PASS] FXML files present: 4
[WARN] Database not connected (offline check)
[PASS] Maven configuration valid
[PASS] All dependencies found
```

**Response to Warnings**:
```
[WARN] Issue detected
→ Run: .\fix-project.ps1
```

---

### 8. fix-project.ps1 - Automatic Project Repair

**Purpose**: Automatically fix common project issues

**Usage** (Requires Admin on some systems):
```powershell
.\Hotel-App-Scripts\fix-project.ps1
```

**Issues It Fixes**:
- Missing Maven module
- Corrupted target directory
- Stale dependency cache
- Incorrect file permissions
- Missing required folders
- Misconfigured paths
- Build cache issues

**Repair Sequence**:
1. Backup current configuration
2. Identify issues
3. Attempt automatic repair
4. Verify repairs
5. Report results

**After Running**:
```powershell
# Test if fix worked
.\Hotel-App-Scripts\audit-project.ps1
```

---

### 9. init-hotel-files.ps1 - File Initialization

**Purpose**: Initialize project file structure and required files

**Usage**:
```powershell
.\Hotel-App-Scripts\init-hotel-files.ps1
```

**Initializes**:
- Directory structure
- Configuration files
- Template files
- Property files
- Required folders

**When to Run**: First time setup after clone

---

## ⚙️ Configuration Scripts

### 10. set-hotel-project.ps1 - Project Configuration

**Purpose**: Configure project paths and environment

**Usage** (Requires Admin):
```powershell
.\Hotel-App-Scripts\set-hotel-project.ps1
```

**Configures**:
- Java path
- Maven installation
- Database connection
- Project paths
- Environment variables

**Interactive Prompts**:
```
Where is JDK 23? [C:\Program Files\Java\jdk-23]
Where is Maven? [C:\Program Files\Maven]
Database host? [localhost]
Database port? [3306]
```

---

## 📖 Help Files

Located in `Hotel-App-Scripts/Help/`:

- `clean-and-build.md` - Detailed build guide
- `init-hotel-files.md` - File initialization guide
- `maven-optimize.md` - Build optimization tips
- `mysql-fix.md` - Database troubleshooting
- `rebuild-and-launch.md` - Full deployment guide
- `run-hotel-jdk23.md` - JDK 23 specific guide
- `setup-ui-layout.md` - UI configuration

**Access Help**:
```powershell
# View help content
Get-Content .\Hotel-App-Scripts\Help\clean-and-build.md

# Open in default editor
Invoke-Item .\Hotel-App-Scripts\Help\
```

---

## 🔄 Common Workflows

### Development Setup
```powershell
# 1. First time setup
.\Hotel-App-Scripts\init-hotel-files.ps1

# 2. Set up environment
.\Hotel-App-Scripts\set-hotel-project.ps1

# 3. Initialize database
.\Hotel-App-Scripts\mysql-fixdb.ps1

# 4. Build application
.\Hotel-App-Scripts\clean-and-build.ps1

# 5. Run application
.\Hotel-App-Scripts\launch-app.ps1
```

### Daily Development
```powershell
# Build and run
.\Hotel-App-Scripts\clean-and-build.ps1
.\Hotel-App-Scripts\run_hotel_app.ps1
```

### Optimization Cycle
```powershell
# Every few weeks
.\Hotel-App-Scripts\audit-project.ps1

# If issues found
.\Hotel-App-Scripts\fix-project.ps1

# Optimize build
.\Hotel-App-Scripts\maven-optimize.ps1
```

### Troubleshooting
```powershell
# Check project health
.\Hotel-App-Scripts\audit-project.ps1

# 1f problems found
if ($LastExitCode -ne 0) {
    .\Hotel-App-Scripts\fix-project.ps1
    .\Hotel-App-Scripts\audit-project.ps1
}
```

---

## 🛠️ Script Development

### Creating New Scripts

Template for new scripts:
```powershell
<#
.SYNOPSIS
    Brief description of script

.DESCRIPTION
    Detailed description

.PARAMETER Param1
    Description of parameter

.EXAMPLE
    .\script-name.ps1 -Param1 "value"

.NOTES
    Author: Your Name
    Date: 2026-04-05
#>

param(
    [Parameter(Mandatory=$false)]
    [string]$Param1 = "default"
)

# Script logic here

Write-Host "Script completed successfully"
Exit 0
```

---

## 🐛 Script Troubleshooting

### Issue: "Cannot be loaded because running scripts is disabled"

**Solution**:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Issue: "Java.exe not found"

**Solution**:
```powershell
# Add Java to PATH
$env:PATH += ";C:\Program Files\Java\jdk-23\bin"

# Or permanently set JAVA_HOME
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-23")
```

### Issue: "Maven not recognize"

**Solution**:
```powershell
# Verify Maven installed
mvn -version

# If not found, add to PATH
$env:PATH += ";C:\tools\maven\bin"
```

### Issue: "Database connection failed"

**Solution**:
```powershell
# Check MySQL service
Get-Service MySQL*

# Start if stopped
Start-Service MySQL80  # (adjust version number)

# Test connection
mysql -u root -p -e "SELECT 1;"
```

---

## 📊 Script Logging

All scripts support logging to file:

```powershell
# Redirect output to file
.\Hotel-App-Scripts\clean-and-build.ps1 > build_log_$(Get-Date -Format yyyyMMdd_HHmmss).txt 2>&1
```

View logs:
```powershell
Get-Content build_log_20260405_101530.txt

# Follow live logs
Get-Content build_log_20260405_101530.txt -Tail 20 -Wait
```

---

## 📚 Related Documentation

- [README.md](../README.md) - Project overview
- [BUILDING.md](BUILDING.md) - Maven build guide
- [SETUP.md](SETUP.md) - Installation instructions
- [DATABASE.md](DATABASE.md) - Database guide

---

**Last Updated**: April 5, 2026
**Scripts Location**: `Hotel-App-Scripts/`
**Platform**: Windows PowerShell 5.0+
