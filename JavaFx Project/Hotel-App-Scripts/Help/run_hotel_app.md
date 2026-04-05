# run_hotel_app.ps1 - Simple Application Runner

**Status**: ✅ Production Ready  
**Complexity**: Medium  
**Priority**: High  
**Platform**: Windows PowerShell 5.0+

---

## 📋 Description

Direct application runner that compiles and launches the Hotel Management System without Maven. Suitable for development environments where Maven is not installed or when quick compilation is needed.

**Key Features**:
- Direct javac compilation
- Compiles all Java files recursively
- Outputs to standard directory
- Clean console output
- Database module support
- Minimal dependencies

---

## 🎯 Purpose

Quick alternative to Maven builds for developers who want to compile and run the application directly.

---

## ▶️ Usage

### Basic Execution

```powershell
# From project root or Hotel-App-Scripts directory
.\run_hotel_app.ps1
```

### Output Example

```
Compiling JavaFX Project...
Launching Hotel Management System...
[Application Window Appears]
```

---

## 🔧 Configuration Required

Edit the script header with your paths:

```powershell
$JAVA_HOME = "C:\Program Files\Java\jdk-23"
$FX_SDK_LIB = "C:\path\to\javafx-sdk-23\lib"
$MYSQL_JAR = "C:\path\to\mysql-connector-j-9.x.jar"
$PROJECT_ROOT = Get-Location
$OUT_DIR = "$PROJECT_ROOT\out\production\javafxdemo"
```

---

## 📊 Execution Flow

```
1. Cleanup old output directory
         ↓
2. Create fresh output directory
         ↓
3. Find all Java files
         ↓
4. Compile with javac
         ↓
5. Check compilation success
         ↓
6. Launch application
```

---

## ✅ Success Indicators

### Compilation Success ✅
```
Compiling JavaFX Project...
[Application Window Appears]
```

### Check Compiled Classes

```powershell
ls out\production\javafxdemo\*.class
```

---

## 🐛 Troubleshooting

### Error: "javac.exe not found"

**Problem**: Java not in PATH

**Solution**:
```powershell
$env:PATH += ";C:\Program Files\Java\jdk-23\bin"
.\run_hotel_app.ps1
```

---

### Error: "Compilation Failed"

**Problem**: Java source code has syntax errors

**Solution**:
1. Check error message output
2. Fix the Java file mentioned
3. Retry script

---

### Error: "Module not found"

**Problem**: Missing module dependencies

**Solution**:
Use Maven build instead:
```powershell
.\clean-and-build.ps1
.\launch-app.ps1
```

---

## 📋 Modules Used

```
--add-modules javafx.controls,javafx.fxml,java.sql
```

---

## ⏱️ Performance

- Cleanup: ~1 second
- Compilation: ~10-20 seconds
- Launch: ~3-5 seconds
- **Total**: ~15-30 seconds

---

## 🔄 Related Scripts

- [clean-and-build.ps1](clean-and-build.md) - Maven build
- [launch-app.ps1](launch-app.md) - Main launcher
- [run-hotel-jdk23.ps1](run-hotel-jdk23.md) - JDK specific launcher

---

**Last Updated**: April 5, 2026  
**Tested With**: PowerShell 5.0+, JDK 23, JavaFX 23.0.1
