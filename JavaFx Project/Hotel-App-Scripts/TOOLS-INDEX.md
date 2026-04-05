# TOOLS-INDEX.md - Complete PowerShell Scripts Reference

**Location**: `Hotel-App-Scripts/`  
**Platform**: Windows PowerShell 5.0+  
**Last Updated**: April 5, 2026

---

## 📖 Quick Navigation

### Critical Path Tools (Production Required)
1. [launch-app.ps1](#1-launch-app-production-launcher) - Main application launcher
2. [clean-and-build.ps1](#2-clean-and-build-maven-builder) - Maven build automation
3. [mysql-fixdb.ps1](#3-mysql-fixdb-database-validator) - Database health check
4. [audit-project.ps1](#4-audit-project-code-auditor) - Project diagnostics

### Developer Tools (Support & Debugging)
5. [run_hotel_app.ps1](#5-run_hotel_app-direct-launcher) - Direct Java launcher
6. [run-hotel-jdk23.ps1](#6-run-hotel-jdk23-jdk-specific-launcher) - JDK 23 specific
7. [maven-optimize.ps1](#7-maven-optimize-build-optimizer) - Build optimization
8. [fix-project.ps1](#8-fix-project-auto-repair) - Automatic issue fixing
9. [init-hotel-files.ps1](#9-init-hotel-files-initializer) - Project setup
10. [set-hotel-project.ps1](#10-set-hotel-project-configuration) - ⚠️ SECURITY ISSUE
11. [Invoke-Launch-fixed-1.ps1](#11-invoke-launch-helper) - Helper function

---

## 🔍 Tool Details

### 1. launch-app.ps1 - Production Launcher

**Purpose**: Safe, production-grade application launcher  
**Status**: ✅ Production Ready  
**Requires Configuration**: Yes

```powershell
.\launch-app.ps1
```

**What It Does**:
- Validates 4 critical paths (Java, JavaFX, MySQL JAR, output directory)
- Displays colored output for errors
- Loads required modules
- Starts application with proper arguments

**Configuration Required**:
```powershell
$Config.JavaExe = "C:\Program Files\Java\jdk-23\bin\java.exe"
$Config.JavaFXLib = "C:\path\to\javafx-sdk-23\lib"
$Config.MysqlJar = "C:\path\to\mysql-connector-j-9.6.0.jar"
```

**Documentation**: [launch-app.md](Help/launch-app.md)

---

### 2. clean-and-build.ps1 - Maven Builder

**Purpose**: Complete Maven build with dependency management  
**Status**: ✅ Production Ready  
**Requires Configuration**: No (runs from project root)

```powershell
.\Hotel-App-Scripts\clean-and-build.ps1
```

**What It Does**:
- Backs up pom.xml
- Injects MySQL dependency
- Removes duplicate JARs
- Runs: `mvn clean install -DskipTests`

**Output**:
- `target/javafxdemo-1.0.jar`
- Compiled classes in `target/classes/`

**Documentation**: [clean-and-build.md](Help/clean-and-build.md)

---

### 3. mysql-fixdb.ps1 - Database Validator

**Purpose**: Check MySQL health and start service if needed  
**Status**: ✅ Production Ready  
**Requires Configuration**: No

```powershell
.\Hotel-App-Scripts\mysql-fixdb.ps1
```

**What It Does**:
- Detects MySQL service
- Starts service if stopped
- Validates port 3306 connectivity
- Detects XAMPP/WAMP

**Output**:
```
✓ Found MySQL Service: MySQL80
✓ Service is already running
✓ Port 3306 is open
```

**Documentation**: [mysql-fixdb.md](Help/mysql-fixdb.md)

---

### 4. audit-project.ps1 - Code Auditor

**Purpose**: Detect encoding and structural issues  
**Status**: ✅ Production Ready  
**Requires Configuration**: No

```powershell
.\Hotel-App-Scripts\audit-project.ps1
```

**What It Detects**:
- BOM (Byte Order Mark) in files
- Illegal escape sequences
- Main class identification
- File encoding issues

**Output** (Tabular Format):
```
FileName                       EncodingIssue    PathEscapes    IsMainClass
Main.java                      Clean            Clean          True
DatabaseConnection.java        Clean            Clean          False
```

**Documentation**: [audit-project.md](Help/audit-project.md)

---

### 5. run_hotel_app.ps1 - Direct Launcher

**Purpose**: Compile and launch without Maven  
**Status**: ✅ Production Ready  
**Requires Configuration**: Yes

```powershell
.\run_hotel_app.ps1
```

**What It Does**:
- Direct javac compilation
- Cleans old output
- Compiles all Java files
- Launches application

**Configuration Required**:
```powershell
$JAVA_HOME = "C:\Program Files\Java\jdk-23"
$FX_SDK_LIB = "C:\path\to\javafx-sdk-23\lib"
$MYSQL_JAR = "C:\path\to\mysql-connector-j-9.x.jar"
```

**Documentation**: [run_hotel_app.md](Help/run_hotel_app.md)

---

### 6. run-hotel-jdk23.ps1 - JDK Specific Launcher

**Purpose**: Launch with JDK 23 specific configuration  
**Status**: ✅ Production Ready  
**Requires Configuration**: No

```powershell
.\Hotel-App-Scripts\run-hotel-jdk23.ps1
```

**What It Does**:
- Verifies JDK 23 installation
- Sets Maven classpath
- Uses Maven to compile and run
- Validates Java version

**Documentation**: [run-hotel-jdk23.md](Help/run-hotel-jdk23.md)

---

### 7. maven-optimize.ps1 - Build Optimizer

**Purpose**: Optimize Maven configuration and dependencies  
**Status**: ✅ Production Ready  
**Requires Configuration**: No

```powershell
.\Hotel-App-Scripts\maven-optimize.ps1
```

**What It Does**:
- Backs up pom.xml
- Injects MySQL dependency (if missing)
- Removes duplicate JARs from /lib
- Refreshes Maven cache
- Runs clean install

**Best Practice**: Run monthly

**Documentation**: [maven-optimize.md](Help/maven-optimize.md)

---

### 8. fix-project.ps1 - Auto-Repair Tool

**Purpose**: Automatically fix detected issues  
**Status**: ✅ Production Ready  
**Requires Configuration**: No

```powershell
.\Hotel-App-Scripts\fix-project.ps1
```

**What It Fixes**:
- Illegal escape sequences in files
- File header corruption
- Package structure issues
- Encoding problems

**Usage Workflow**:
1. Run: `audit-project.ps1` (identify issues)
2. Run: `fix-project.ps1` (fix automatically)
3. Run: `audit-project.ps1` (verify fixes)

**Documentation**: [fix-project.md](Help/fix-project.md)

---

### 9. init-hotel-files.ps1 - Project Initializer

**Purpose**: Initialize project folder structure and files  
**Status**: ✅ Production Ready  
**Requires Configuration**: No

```powershell
.\Hotel-App-Scripts\init-hotel-files.ps1
```

**What It Creates**:
- `src/main/java/com/cts/javafxdemo/` directory
- `src/main/resources/` directory
- Sample Java controller files
- Sample SQL schema

**Use**: First-time project setup

**Documentation**: [init-hotel-files.md](Help/init-hotel-files.md)

---

### 10. set-hotel-project.ps1 - Configuration Tool

**Purpose**: Configure project paths  
**Status**: ⚠️ SECURITY ISSUE DETECTED  
**Requires Configuration**: Manual removal

**⚠️ CRITICAL**: This file contains exposed credentials

```
File: set-hotel-project.ps1
Issue: supabase-password = VB94p1cFzl9mfXo6
Severity: CRITICAL
```

**Action Required**:
```powershell
# Remove from repository
git rm set-hotel-project.ps1
echo "set-hotel-project.ps1" >> .gitignore

# Use environment variables instead
$Password = Read-Host -AsSecureString "Enter Password"
```

**⚠️ DO NOT USE IN PRODUCTION**

**Documentation**: [set-hotel-project.md](Help/set-hotel-project.md)

---

### 11. Invoke-Launch-fixed-1.ps1 - Helper Function

**Purpose**: Helper function for script calling  
**Status**: ✅ Valid (Legacy)  
**Use**: Called by other scripts

**What It Does**:
- Encapsulates launch parameters
- Handles error codes
- Used by other scripts

**Documentation**: [Invoke-Launch-fixed-1.md](Help/Invoke-Launch-fixed-1.md)

---

## 🚀 Recommended Workflows

### First-Time Setup
```powershell
1. .\init-hotel-files.ps1          # Create folders
2. .\mysql-fixdb.ps1               # Verify database
3. .\clean-and-build.ps1           # Build project
4. .\launch-app.ps1                # Run application
```

### Daily Development
```powershell
1. .\audit-project.ps1             # Check for issues
2. .\clean-and-build.ps1           # Build
3. .\launch-app.ps1                # Run
```

### Problem Solving
```powershell
1. .\audit-project.ps1             # Identify issues
2. .\fix-project.ps1               # Auto-fix
3. .\clean-and-build.ps1           # Rebuild
4. .\launch-app.ps1                # Test
```

### Troubleshooting
```powershell
1. .\mysql-fixdb.ps1               # Check database
2. .\audit-project.ps1             # Check code
3. .\fix-project.ps1               # Auto-fix
4. .\maven-optimize.ps1            # Optimize
5. .\clean-and-build.ps1           # Clean rebuild
```

---

## 📊 Tools Comparison Matrix

| Tool | Type | Config | Time | Priority |
|------|------|--------|------|----------|
| launch-app | Launcher | Required | 10s | Critical |
| clean-and-build | Builder | No | 45s | Critical |
| mysql-fixdb | Validator | No | 5s | Critical |
| audit-project | Auditor | No | 3s | Medium |
| run_hotel_app | Launcher | Required | 20s | High |
| run-hotel-jdk23 | Launcher | No | 15s | High |
| maven-optimize | Optimizer | No | 35s | Medium |
| fix-project | Repair | No | 20s | Medium |
| init-hotel-files | Initializer | No | 2s | Low |
| set-hotel-project | Config | ⚠️ SECURITY | - | ❌ Broken |
| Invoke-Launch | Helper | No | - | Helper |

---

## ✅ Pre-Execution Checklist

### Environment
- [ ] PowerShell 5.0+ installed
- [ ] Execution Policy set: `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser`
- [ ] Windows 10/11 (or Windows Server 2016+)

### Software
- [ ] Java 23 installed and in PATH: `java -version`
- [ ] Maven installed and in PATH: `mvn -version`
- [ ] MySQL 8.0+ installed

### Configuration
- [ ] Paths in scripts configured correctly
- [ ] MySQL service running: `net start MySQL80`
- [ ] Port 3306 accessible: `.\mysql-fixdb.ps1`
- [ ] Project cloned and in working directory

### Security
- [ ] Removed `set-hotel-project.ps1` or added to .gitignore
- [ ] Environment variables set for sensitive data
- [ ] No credentials in scripts

---

## 🔐 Security Audit Results

### ✅ Safe Scripts
All scripts are syntactically valid and safe EXCEPT:

### ⚠️ Security Issues
**set-hotel-project.ps1**
- Contains hardcoded Supabase password
- Should be removed or protected
- Add to .gitignore
- Use environment variables instead

---

## 📚 Documentation Structure

```
Hotel-App-Scripts/
├── *.ps1                    # PowerShell scripts
├── VALIDATION-REPORT.md     # Overall validation
├── TOOLS-INDEX.md          # This file
└── Help/
    ├── launch-app.md
    ├── clean-and-build.md
    ├── run_hotel_app.md
    ├── mysql-fixdb.md
    ├── audit-project.md
    ├── fix-project.md
    ├── run-hotel-jdk23.md
    ├── maven-optimize.md
    ├── init-hotel-files.md
    └── [Other help files]
```

---

## 🎓 Learning Path

### Beginner
1. Read [VALIDATION-REPORT.md](VALIDATION-REPORT.md)
2. Read [launch-app.md](Help/launch-app.md)
3. Try: `.\launch-app.ps1`

### Intermediate
1. Read [clean-and-build.md](Help/clean-and-build.md)
2. Read [audit-project.md](Help/audit-project.md)
3. Try building and auditing

### Advanced
1. Review all script source code
2. Understand Maven lifecycle
3. Customize for your needs

---

## 💬 Quick Help

### Application Won't Start
```powershell
# 1. Check database
.\mysql-fixdb.ps1

# 2. Verify code
.\audit-project.ps1

# 3. Rebuild
.\clean-and-build.ps1

# 4. Launch
.\launch-app.ps1
```

### Build Fails
```powershell
# 1. Auto-fix issues
.\fix-project.ps1

# 2. Optimize Maven
.\maven-optimize.ps1

# 3. Retry build
.\clean-and-build.ps1
```

### Diagnostic Report
```powershell
# Full project audit
.\audit-project.ps1

# Database check
.\mysql-fixdb.ps1

# Both together
.\audit-project.ps1; .\mysql-fixdb.ps1
```

---

## 📞 Support Resources

- Individual tool help files in `Help/` directory
- Main documentation in `/docs/` folder
- Validation report: [VALIDATION-REPORT.md](VALIDATION-REPORT.md)
- Project setup: [../docs/SETUP.md](../docs/SETUP.md)

---

**Generated**: April 5, 2026  
**Validation Status**: 10/11 Scripts Production Ready  
**Security Status**: 1 Critical Issue Identified (set-hotel-project.ps1)  
**Recommendation**: Fix security issue before production deployment
