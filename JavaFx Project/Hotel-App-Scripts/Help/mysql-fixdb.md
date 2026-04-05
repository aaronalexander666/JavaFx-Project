# mysql-fixdb.ps1 - Database Health Check & Repair

**Status**: ✅ Production Ready  
**Complexity**: Low  
**Priority**: Critical  
**Platform**: Windows PowerShell 5.0+

---

## 📋 Description

Database diagnostics and health check utility for MySQL. Validates that the MySQL service is running, port 3306 is accessible, and the database is ready for the application.

**Key Features**:
- Detects MySQL service
- Starts service if stopped
- Validates port connectivity
- Detects alternative installations (XAMPP, WAMP)
- Non-destructive diagnostics
- Colored output for clarity

---

## 🎯 Purpose

Ensure MySQL is operational and accessible before launching the application or running database initialization scripts.

---

## ▶️ Usage

### Basic Execution

```powershell
# From any directory
.\Hotel-App-Scripts\mysql-fixdb.ps1
```

### Output Example

```
--- MySQL Health Check & Repair ---
Found MySQL Service: MySQL80
Service is already running.
SUCCESS: Port 3306 is open. Your database is alive!
--- Diagnostics Complete ---
```

---

## 📊 Checks Performed

### 1. Service Detection
Looks for any Windows service matching "MySQL*"

**Success Output**:
```
Found MySQL Service: MySQL80
```

**Failure Output**:
```
CRITICAL: No MySQL service found!
```

### 2. Service Status
Checks if MySQL service is running

**Running**:
```
Service is already running.
```

**Stopped**:
```
Service is Stopped. Attempting to start...
```

### 3. Port Validation
Tests if port 3306 is listening

**Open**:
```
SUCCESS: Port 3306 is open. Your database is alive!
```

**Closed**:
```
FAILURE: Port 3306 is closed. Workbench cannot connect.
```

### 4. Alternative Detection
Identifies XAMPP/WAMP installations

**Found**:
```
Notice: XAMPP detected. Ensure you click 'Start' in the XAMPP Control Panel.
```

---

## ✅ Success Indicators

### All Green ✅
```
[✓] Found MySQL Service: MySQL80
[✓] Service is already running
[✓] Port 3306 is open. Your database is alive!
[✓] --- Diagnostics Complete ---
```

---

## 🐛 Troubleshooting

### Error: "No MySQL service found"

**Problem**: MySQL not installed as Windows service

**Solution**: Install MySQL Server Community Edition

1. Download from [mysql.com](https://www.mysql.com/downloads/)
2. Run installer
3. Choose "MySQL Server"
4. Select "Windows Service" during setup
5. Retry script

---

### Error: "Port 3306 is closed"

**Problem**: MySQL service exists but not listening

**Solution**:
```powershell
# Start the service
.\mysql-fixdb.ps1

# The script should auto-start it
# If still fails, restart Windows
```

---

### Error: Service won't start

**Problem**: MySQL configuration issues

**Solution**:
```powershell
# Check MySQL error log
# Typically at: C:\ProgramData\MySQL\MySQL Server 8.0\Data\
Get-Content "C:\ProgramData\MySQL\MySQL Server 8.0\Data\*.err"

# Or use MySQL Workbench to diagnose
```

---

### XAMPP/WAMP Warning

**Problem**: Using alternative database server

**Solution**:
1. Open XAMPP Control Panel
2. Click "Start" next to MySQL
3. Wait 5 seconds
4. Retry script

---

## 🔧 Advanced Options

### Manual Service Start

```powershell
# If script fails to start service
Start-Service MySQL80  # Adjust service name

# Or directly:
net start MySQL80
```

### Check Service Details

```powershell
# Exact service name
Get-Service -DisplayName MySQL*

# Service status
Get-Service MySQL80 | Select Status
```

### Test Database Connection

After script succeeds:

```powershell
# Connect to MySQL
mysql -u root -p

# In MySQL prompt:
mysql> SHOW DATABASES;
mysql> USE javafxdemo;
mysql> SELECT COUNT(*) FROM tblcustomer;
```

---

## 📊 MySQL Port & Service Info

| Component | Port | Protocol | Status |
|-----------|------|----------|--------|
| MySQL Server | 3306 | TCP | Should be LISTENING |
| MySQL Service | - | Windows Service | Should be RUNNING |

---

## ⏱️ Performance

- Service detection: ~1 second
- Service startup (if needed): ~3-5 seconds
- Port check: ~1-2 seconds
- **Total**: ~2-10 seconds

---

## 🔐 Security Notes

- ✅ Non-destructive diagnostics only
- ✅ No changes to data or configuration
- ✅ Only reports status
- ⚠️ Requires admin rights to start service

---

## 🔄 Related Scripts

- [clean-and-build.ps1](clean-and-build.md) - Build project
- [launch-app.ps1](launch-app.md) - Launch application
- [init-hotel-files.ps1](../Help/init-hotel-files.md) - Initialize database

---

**Last Updated**: April 5, 2026  
**Tested With**: PowerShell 5.0+, MySQL 8.0+, Windows 10/11
