# PRODUCTION-TESTING-GUIDE.md - Complete Test Suite for PowerShell Tools

**Created**: April 5, 2026  
**Purpose**: Comprehensive testing procedures for all PowerShell automation tools  
**Target Environment**: Windows PowerShell 5.0+, Windows 10/11

---

## 📋 Overview

This guide contains complete test procedures for all 11 PowerShell scripts used in the Hotel Management System. Each test validates that the script performs its intended function correctly and safely.

---

## ✅ Pre-Test Requirements

### System Requirements
- Windows 10 Pro, Windows 11, or Windows Server 2016+
- PowerShell 5.0 or higher
- Administrator account (recommended)
- 4GB RAM minimum
- 2GB free disk space

### Software Requirements
- Java 23 (Download from oracle.com)
- Maven 3.8.1+ (Download from apache.org)
- MySQL 8.0+ (Download from mysql.com)
- Git (For version control)

### Network Requirements
- Internet access (for Maven repository)
- localhost:3306 accessible (MySQL port)

### Pre-Test Checklist

```powershell
# Verify PowerShell version
$PSVersionTable.PSVersion

# Set execution policy
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Verify Java
java -version

# Verify Maven
mvn -version

# Verify MySQL
mysql -u root -p -e "SELECT 1;"

# Navigate to project
cd "C:\path\to\JavaFx Project"
```

---

## 🧪 Test Scenarios

### Test Scenario 1: mysql-fixdb.ps1 - Database Health Check

**Objective**: Verify MySQL service is accessible

**Prerequisites**:
- MySQL 8.0+ installed
- MySQL service installed as Windows service

**Test Steps**:

```powershell
# Step 1: Navigate to scripts
cd Hotel-App-Scripts

# Step 2: Run the script
.\mysql-fixdb.ps1

# Step 3: Verify output
# Expected: All checks should pass (green)
```

**Expected Output**:
```
--- MySQL Health Check & Repair ---
Found MySQL Service: MySQL80
Service is already running.
SUCCESS: Port 3306 is open. Your database is alive!
--- Diagnostics Complete ---
```

**Test Success Criteria**:
- ✅ Service discovered
- ✅ Service status confirmed running
- ✅ Port 3306 is open
- ✅ No errors/exceptions

**Troubleshooting**:
| Issue | Solution |
|-------|----------|
| Service not found | Install MySQL Server |
| Port closed | Verify MySQL running |
| Connection timeout | Check firewall |

---

### Test Scenario 2: audit-project.ps1 - Code Quality Audit

**Objective**: Detect encoding and structural issues in source code

**Prerequisites**:
- Java source files exist in `src/main/java/`
- No modifications to source since last commit

**Test Steps**:

```powershell
# Step 1: Navigate to project root
cd "..\JavaFx Project"

# Step 2: Run audit
.\Hotel-App-Scripts\audit-project.ps1

# Step 3: Review output table
# All files should show: EncodingIssue=Clean, PathEscapes=Clean
```

**Expected Output**:
```
FileName                               EncodingIssue    PathEscapes    IsMainClass
--------                               -----            -----------    -----------
Main.java                              Clean            Clean          True
DatabaseConnection.java                Clean            Clean          False
[Multiple more files...]
--- AUDIT COMPLETE ---
```

**Test Success Criteria**:
- ✅ All files show "Clean" for EncodingIssue
- ✅ All files show "Clean" for PathEscapes
- ✅ At least 1 file is identified as MainClass
- ✅ No BOM DETECTED entries
- ✅ No BAD ESCAPES FOUND entries

**If Issues Found**:
```powershell
# Auto-fix detected issues
.\Hotel-App-Scripts\fix-project.ps1

# Re-audit to verify fixes
.\Hotel-App-Scripts\audit-project.ps1
```

---

### Test Scenario 3: clean-and-build.ps1 - Maven Build

**Objective**: Complete application build with dependency management

**Prerequisites**:
- Maven 3.8.1+ installed and in PATH
- Java 23 in PATH
- Audit passes (no encoding issues)
- `pom.xml` exists in project root

**Test Steps**:

```powershell
# Step 1: Navigate to project root
cd "..\JavaFx Project"

# Step 2: Run clean build
.\Hotel-App-Scripts\clean-and-build.ps1

# Step 3: Monitor output
# Build should complete without errors

# Step 4: Verify artifacts
ls target\javafxdemo*.jar
ls target\classes\*.class
```

**Expected Output**:
```
Starting Project Optimization...
[INFO] Building javafxdemo 1.0
[INFO] BUILD SUCCESS
[INFO] Total time: 45.234 s
```

**Test Success Criteria**:
- ✅ BUILD SUCCESS message appears
- ✅ No compilation errors
- ✅ JAR file created: `target/javafxdemo-1.0.jar`
- ✅ .class files in `target/classes/`
- ✅ Build completes in under 90 seconds

**Common Issues**:
| Issue | Resolution |
|-------|------------|
| Java not found | Add Java to PATH |
| Maven not found | Install Maven |
| Compilation errors | Run audit and fix |
| Memory error | Increase MAVEN_OPTS |

---

### Test Scenario 4: launch-app.ps1 - Application Launch

**Objective**: Verify application starts successfully

**Prerequisites**:
- Project built successfully (clean-and-build.ps1 passed)
- All paths configured in script
- MySQL running and accessible
- Database javafxdemo exists

**Configuration Required**:

Edit script and verify paths:
```powershell
# Must configure these:
$Config.JavaExe = "C:\Program Files\Java\jdk-23\bin\java.exe"
$Config.JavaFXLib = "C:\path\to\javafx-sdk-23\lib"
$Config.MysqlJar = "C:\path\to\mysql-connector-j-9.6.0.jar"
```

**Test Steps**:

```powershell
# Step 1: Ensure database is available
.\Hotel-App-Scripts\mysql-fixdb.ps1

# Step 2: Ensure build is complete
.\Hotel-App-Scripts\clean-and-build.ps1

# Step 3: Configure paths in launch-app.ps1

# Step 4: Run launcher
.\Hotel-App-Scripts\launch-app.ps1

# Step 5: Observe UI
# Application window should appear
# Login screen should be visible
```

**Expected Behavior**:
- ✅ Console output shows validation passing
- ✅ No error messages
- ✅ Application window appears within 10 seconds
- ✅ Login screen is responsive
- ✅ Can enter text in username/password fields

**Test Success**:
- Try invalid credentials - should show error
- Check console output for database connection message
- Try typing in login fields - should be responsive

---

### Test Scenario 5: run_hotel_app.ps1 - Direct Compilation & Launch

**Objective**: Test alternative launcher without Maven

**Prerequisites**:
- Java 23 installed with javac
- JavaFX SDK 23 with lib folder
- MySQL Connector JAR available

**Configuration**:

Edit script header:
```powershell
$JAVA_HOME = "C:\Program Files\Java\jdk-23"
$FX_SDK_LIB = "C:\path\to\javafx-sdk-23\lib"
$MYSQL_JAR = "C:\path\to\mysql-connector-j-9.x.jar"
```

**Test Steps**:

```powershell
# Step 1: Navigate to project
cd "..\JavaFx Project"

# Step 2: Run compilation and launch
.\Hotel-App-Scripts\run_hotel_app.ps1

# Step 3: Verify compilation and launch
# Should see: "Compiling JavaFX Project..."
# Then: Application window appears
```

**Expected Output**:
```
Compiling JavaFX Project...
Launching Hotel Management System...
[Application Window Appears]
```

**Test Success Criteria**:
- ✅ No "Compilation Failed" messages
- ✅ Application window appears
- ✅ UI is responsive
- ✅ Complete in under 30 seconds

---

### Test Scenario 6: run-hotel-jdk23.ps1 - JDK 23 Specific Launch

**Objective**: Verify Maven-based launch with JDK 23

**Prerequisites**:
- Maven 3.8.1+ installed
- JDK 23 specifically installed
- Project built

**Test Steps**:

```powershell
# Step 1: Navigate to project
cd "..\JavaFx Project"

# Step 2: Run script
.\Hotel-App-Scripts\run-hotel-jdk23.ps1

# Step 3: Verify Java version shown
# Should display: openjdk version "23"
```

**Expected Output**:
```
Verifying Java Version...
openjdk version "23"
[Java details]
Launching Hotel System with JDK 23...
[Application Window Appears]
```

**Test Success Criteria**:
- ✅ Java 23 verified
- ✅ Application launches
- ✅ Maven integration works
- ✅ Builds and runs

---

### Test Scenario 7: maven-optimize.ps1 - Build Optimization

**Objective**: Verify Maven optimization and cleanup

**Prerequisites**:
- Maven 3.8.1+ installed
- pom.xml exists
- Project has manual JARs (optional)

**Test Steps**:

```powershell
# Step 1: Navigate to project
cd "..\JavaFx Project"

# Step 2: Run optimization
.\Hotel-App-Scripts\maven-optimize.ps1

# Step 3: Verify completion
# Should show: "Project is optimized and clean!"
```

**Expected Output**:
```
Starting Project Optimization...
[SUCCESS] MySQL Dependency injected into pom.xml
[Removing duplicate drivers]
Rebuilding project...
[INFO] BUILD SUCCESS
```

**Test Success Criteria**:
- ✅ pom.xml backup created
- ✅ MySQL dependency present
- ✅ Duplicate JARs removed
- ✅ Maven rebuild successful

---

### Test Scenario 8: fix-project.ps1 - Automatic Repair

**Objective**: Verify automatic issue fixing

**Prerequisites**:
- audit-project.ps1 found issues (BOM or escapes)
- Maven 3.8.1+ installed

**Test Steps**:

```powershell
# Step 1: Audit first
.\Hotel-App-Scripts\audit-project.ps1

# If issues found:
# Step 2: Run fix script
.\Hotel-App-Scripts\fix-project.ps1

# Step 3: Re-audit
.\Hotel-App-Scripts\audit-project.ps1

# Verify all issues are "Clean"
```

**Expected Workflow**:
```
[Before]
CustomerDashboardController.java    BOM DETECTED     BAD ESCAPES

[After fix-project.ps1]
FIXED: Illegal escapes...
CLEANED: Header artifacts...

[After re-audit]
CustomerDashboardController.java    Clean            Clean
```

**Test Success Criteria**:
- ✅ All "BOM DETECTED" entries become "Clean"
- ✅ All "BAD ESCAPES FOUND" become "Clean"
- ✅ Header artifacts removed
- ✅ Maven clean compile succeeds

---

### Test Scenario 9: init-hotel-files.ps1 - Project Initialization

**Objective**: Verify project folder structure setup

**Prerequisites**:
- New/empty project directory
- No existing src/ folder

**Test Steps**:

```powershell
# Step 1: Navigate to new project folder
cd new_project_folder

# Step 2: Run init
..\Hotel-App-Scripts\init-hotel-files.ps1

# Step 3: Verify structure
ls -Recurse

# Should see new folders and files created
```

**Expected Structure**:
```
src/
├── main/
│   ├── java/
│   │   └── com/cts/javafxdemo/
│   │       ├── CustomersListController.java
│   │       └── JavaFxDemoController.java
│   └── resources/
```

**Test Success Criteria**:
- ✅ Folders created
- ✅ Sample Java files generated
- ✅ SQL schema created
- ✅ Files have valid syntax

---

### Test Scenario 10: Workflow - Complete Production Deployment

**Objective**: End-to-end testing

**Estimated Time**: 15-20 minutes

**Full Test Workflow**:

```powershell
# 1. Environment Check
"=== ENVIRONMENT CHECK ==="
java -version
mvn -version
mysql -u root -p -e "SELECT 1;"

# 2. Database Health
"=== DATABASE CHECK ==="
.\Hotel-App-Scripts\mysql-fixdb.ps1

# 3. Code Quality
"=== CODE QUALITY CHECK ==="
.\Hotel-App-Scripts\audit-project.ps1

# 4. Project Build
"=== BUILD PROJECT ==="
.\Hotel-App-Scripts\clean-and-build.ps1

# 5. Application Launch
"=== LAUNCH APPLICATION ==="
.\Hotel-App-Scripts\launch-app.ps1

# 6. Manual Verification
"=== MANUAL VERIFICATION ==="
# - Login screen appears
# - Try invalid credentials
# - Check database entries
# - Navigate menus
```

**Expected Result**: ✅ All 6 steps pass successfully

---

## 📊 Test Results Template

```markdown
# Test Execution Report

**Date**: [Date]  
**Tester**: [Name]  
**Environment**: [Windows Version, Build]  
**Project Version**: [Version]

## Results

| Test # | Script | Status | Notes |
|--------|--------|--------|-------|
| 1 | mysql-fixdb.ps1 | ✅ PASS | Database operational |
| 2 | audit-project.ps1 | ✅ PASS | No encoding issues |
| 3 | clean-and-build.ps1 | ✅ PASS | Build successful |
| 4 | launch-app.ps1 | ✅ PASS | Application started |
| 5 | run_hotel_app.ps1 | ✅ PASS | Alternative launcher works |
| 6 | run-hotel-jdk23.ps1 | ✅ PASS | JDK 23 verified |
| 7 | maven-optimize.ps1 | ✅ PASS | Optimization complete |
| 8 | fix-project.ps1 | ✅ PASS | Issues fixed |
| 9 | init-hotel-files.ps1 | ✅ PASS | Structure created |
| 10 | Full Workflow | ✅ PASS | Production ready |

## Issues Found
None

## Recommendations
None

## Sign-Off
[ ] All tests passed on [Date]
[ ] Production deployment approved
```

---

## 🎓 Continuous Testing

### Weekly Tests
```powershell
# Run these weekly
.\audit-project.ps1
.\mysql-fixdb.ps1
```

### Pre-Release Tests
```powershell
# Run full suite before release
# See Test Scenario 10 above
```

### CI/CD Integration
```yaml
# For GitHub Actions (example)
- name: Audit Project
  run: .\Hotel-App-Scripts\audit-project.ps1
  
- name: Build Project
  run: .\Hotel-App-Scripts\clean-and-build.ps1
  
- name: Check Database
  run: .\Hotel-App-Scripts\mysql-fixdb.ps1
```

---

## ✅ Sign-Off Checklist

Before considering project production-ready:

- [ ] All 10 test scenarios pass
- [ ] No security issues present
- [ ] Database verified and accessible
- [ ] Code audit clean (no BOM or escapes)
- [ ] Build completes successfully
- [ ] Application launches and runs
- [ ] Manual functional testing completed
- [ ] Performance acceptable (builds < 60s)
- [ ] Error messages are clear
- [ ] Documentation is complete

---

## 📞 Escalation Procedures

### If Test Fails

```
1. Note which test failed
2. Run diagnostic scripts:
   .\audit-project.ps1
   .\mysql-fixdb.ps1
3. Review error message
4. Check relevant documentation in Help/
5. Run fix script if applicable:
   .\fix-project.ps1
6. Retry failed test
7. If still failing, escalate
```

### Support Resources

- Individual tool docs: `Help/*.md`
- Master validation: `VALIDATION-REPORT.md`
- Project setup: `../docs/SETUP.md`
- Architecture: `../docs/ARCHITECTURE.md`

---

**Generated**: April 5, 2026  
**Version**: 1.0  
**Status**: Ready for Production Testing
