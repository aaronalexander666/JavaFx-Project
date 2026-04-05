# audit-project.ps1 - Project Health Auditor

**Status**: ✅ Production Ready  
**Complexity**: Low  
**Priority**: Medium  
**Platform**: Windows PowerShell 5.0+

---

## 📋 Description

Comprehensive project diagnostics tool that analyzes Java source code for common encoding issues, escape sequence problems, and structural problems that commonly cause compilation failures.

**Key Features**:
- BOM (Byte Order Mark) detection
- Illegal escape sequence detection
- Main class identification
- File encoding validation
- Detailed tabular reporting
- No modifications (read-only)

---

## 🎯 Purpose

Identify encoding and structural issues in code before compilation. Prevents cryptic compilation errors.

---

## ▶️ Usage

### Basic Execution

```powershell
# From project root
.\Hotel-App-Scripts\audit-project.ps1
```

### Output Example

```
FileName                              EncodingIssue    PathEscapes    IsMainClass
--------                              -----            -----------    -----------
Main.java                             Clean            Clean          True
DatabaseConnection.java               Clean            Clean          False
CustomerDashboardController.java      BOM DETECTED     BAD ESCAPES    False
```

---

## 🔍 What It Checks

### 1. BOM (Byte Order Mark) Detection

**What is BOM?**
Special hidden character sequence that appears at the start of UTF-8 files

**Why it matters?**
- Causes `error: 1,1` in Java compilation
- Makes code invisible to javac
- Common when editing in Notepad

**Detection Output**:
```
BOM DETECTED (Fix Required) → Use fix-project.ps1
Clean                       → OK, no action needed
```

---

### 2. Illegal Escape Sequence Detection

**What is it?**
Backslashes in Windows paths not properly escaped

**Example Problem**:
```java
String path = "C:\new\folder";  // ❌ WRONG - \n is newline, \f is form feed
String path = "C:\\new\\folder"; // ✅ CORRECT - escaped properly
```

**Detection Output**:
```
BAD ESCAPES FOUND  → Use fix-project.ps1
Clean              → OK, properly escaped
```

---

### 3. Main Class Identification

**What does it find?**
Files containing `public static void main(String[] args)`

**Output**:
```
True   → This file is a runnable main class
False  → This is a regular class or controller
```

---

## ✅ Success Indicators

### All Clean ✅
```
FileFileName                          EncodingIssue    PathEscapes    IsMainClass
--------                              -----            -----------    -----------
DatabaseConnection.java               Clean            Clean          False
Main.java                             Clean            Clean          True

--- AUDIT COMPLETE ---
Actionable Data Collected. Review the table above.
```

### Issues Found ⚠️
```
CustomerDashboardController.java      BOM DETECTED     BAD ESCAPES    False

--- AUDIT COMPLETE ---
Use fix-project.ps1 to repair detected issues.
```

---

## 🧪 Common Issues Found

### Issue 1: BOM in UTF-8 File

**Symptom**: `error: 1,1: '1' expected`

**Cause**: File saved with UTF-8 BOM

**Fix**:
```powershell
.\fix-project.ps1

# Or manually:
# Open in VS Code, set encoding to UTF-8 (no BOM), save
```

---

### Issue 2: Path Escapes

**Symptom**: `error: illegal escape sequence`

**Cause**: Windows paths with single backslashes

**Fix**:
```powershell
.\fix-project.ps1

# The script automatically fixes these
```

---

### Issue 3: Missing Main Class

**Info**: None (not an error, just informational)

**Usage**: Identify entry points for application

---

## 📊 Output Interpretation

### Column: FileName
Name of the Java file being analyzed

### Column: EncodingIssue
```
Clean                   → File is UTF-8 without BOM (Good)
BOM DETECTED (Fix...)   → File has BOM (Needs fixing)
```

### Column: PathEscapes
```
Clean                   → No illegal escapes found (Good)
BAD ESCAPES FOUND       → Incorrect path formatting (Needs fixing)
```

### Column: IsMainClass
```
True                    → Contains main() method (Runnable)
False                   → Regular class (Helper/Controller)
```

---

## 🔄 Workflow

### 1. Run Audit
```powershell
.\audit-project.ps1
```

### 2. Review Results
Look for:
- BOM DETECTED
- BAD ESCAPES FOUND

### 3. Fix Issues
```powershell
.\fix-project.ps1
```

### 4. Re-audit to Verify
```powershell
.\audit-project.ps1
```

If all show "Clean", proceed with build

---

## ⏱️ Performance

- File discovery: ~1 second
- Encoding analysis: ~1 second
- Report generation: ~0.5 seconds
- **Total**: ~2-3 seconds

---

## 🎓 Advanced Usage

### Find All Main Classes

```powershell
# Run audit
.\audit-project.ps1

# Look for IsMainClass = True
# These are runnable entry points
```

### Targeted Audit

To audit only one file:
```powershell
# Edit script to change $srcPath
./src/main/java/YourFile.java
```

---

## 📋 Required File Structure

Script looks for Java files in:
```
src/main/java/*.java        (Recursive)
```

Must run from project root where this directory exists

---

## 🔐 Security Notes

- ✅ Read-only operation
- ✅ No modifications to files
- ✅ No network access
- ✅ Safe to run repeatedly

---

## 🔄 Related Scripts

- [fix-project.ps1](fix-project.md) - Auto-fix detected issues
- [clean-and-build.ps1](clean-and-build.md) - Build project
- [launch-app.ps1](launch-app.md) - Run application

---

**Last Updated**: April 5, 2026  
**Tested With**: PowerShell 5.0+, Windows 10/11
