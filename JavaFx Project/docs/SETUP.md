# Setup Guide - JavaFX Hotel Management System

Complete setup instructions for getting the JavaFX Hotel Management System running in your environment.

## 📋 System Requirements

### Minimum Requirements
- **OS**: Windows 10/11, macOS 10.15+, or Linux (Ubuntu 18.04+)
- **RAM**: 4GB minimum
- **Disk Space**: 2GB for development environment
- **Network**: Internet connection for dependency downloads

### Software Requirements
- **Java**: JDK 23 (included in project or download from [oracle.com](https://www.oracle.com/java/technologies/downloads/))
- **Maven**: 3.8.1 or higher ([maven.apache.org](https://maven.apache.org/))
- **MySQL**: 8.0 or higher ([mysql.com](https://www.mysql.com/downloads/))
- **PowerShell**: 5.0+ (Windows only, for automation scripts)

## ⚙️ Installation Steps

### Step 1: Install Java Development Kit (JDK 23)

#### Windows
1. Download JDK 23 from [Oracle Java](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer
3. Note the installation path (usually `C:\Program Files\Java\jdk-23`)
4. Add to system PATH:
   - Open `Environment Variables`
   - Add Java bin directory to PATH
   - Verify: `java -version` in command prompt

#### macOS
```bash
# Using Homebrew (recommended)
brew install openjdk@23

# Or download from Oracle and add to PATH in ~/.zshrc
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-23.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```

#### Linux (Ubuntu)
```bash
sudo apt update
sudo apt install openjdk-23-jdk

# Verify installation
java -version
```

### Step 2: Install Maven

#### Windows
1. Download Apache Maven from [maven.apache.org](https://maven.apache.org/)
2. Extract to a directory (e.g., `C:\tools\maven`)
3. Add to PATH:
   - Add Maven bin directory to environment PATH
4. Verify: `mvn -version` in command prompt

#### macOS
```bash
# Using Homebrew
brew install maven

# Verify
mvn -version
```

#### Linux (Ubuntu)
```bash
sudo apt install maven

# Verify
mvn -version
```

### Step 3: Install and Setup MySQL

#### Windows
1. Download MySQL Community Server from [mysql.com](https://www.mysql.com/downloads/)
2. Run the installer
3. Choose setup type (Developer Default recommended)
4. Configure MySQL Server:
   - Port: 3306 (default)
   - Authentication: MySQL 8 Strong Password
5. Complete the installation
6. Start MySQL service

#### macOS
```bash
# Using Homebrew
brew install mysql

# Start MySQL
brew services start mysql

# Secure installation
mysql_secure_installation
```

#### Linux (Ubuntu)
```bash
sudo apt install mysql-server

# Secure installation
sudo mysql_secure_installation

# Start service
sudo systemctl start mysql
```

### Step 4: Clone and Setup Project

```bash
# Clone repository
git clone <repository-url>
cd "JavaFx Project"

# Verify environment
mvn -version
java -version
```

### Step 5: Setup Database

#### Option A: Using MySQL CLI
```bash
# Login to MySQL
mysql -u root -p

# Run database setup
mysql -u root -p < db/hotel-bd.sql
```

#### Option B: Using PowerShell (Windows)
```powershell
# Run the database setup script
.\Hotel-App-Scripts\mysql-fixdb.ps1
```

#### Option C: Manual Setup
1. Open MySQL Workbench or command line
2. Create database: `CREATE DATABASE IF NOT EXISTS javafxdemo;`
3. Import schema: Run SQL from `db/hotel-bd.sql`

### Step 6: Update Database Credentials (if needed)

Edit `src/main/java/com/cts/javafxdemo/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/javafxdemo";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

## 🔨 Build the Application

### Using Maven Command Line
```bash
# Clean and build
mvn clean install

# Run the application
mvn javafx:run
```

### Using Provided Scripts (Windows)
```powershell
# Clean and build
.\Hotel-App-Scripts\clean-and-build.ps1

# Run application
.\Hotel-App-Scripts\run_hotel_app.ps1
```

## ▶️ Running the Application

### Option 1: Maven
```bash
mvn javafx:run
```

### Option 2: PowerShell (Windows)
```powershell
.\Hotel-App-Scripts\run_hotel_app.ps1
# or with JDK 23 explicitly
.\Hotel-App-Scripts\run-hotel-jdk23.ps1
```

### Option 3: After Build
```bash
# After mvn package
java -jar target/javafxdemo-1.0.jar
```

## 🧪 Testing the Installation

1. **Database Connection Test**: The app will show an error if database is not accessible
2. **Login Screen**: If you see the login window, the UI is working
3. **Test Credentials**: Use admin/admin123 (can be changed in database)

## 🐛 Troubleshooting

### Java Version Mismatch
**Problem**: `error: incompatible types: int cannot be converted to double`

**Solution**: 
```bash
# Verify correct Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/jdk-23
```

### Maven Build Fails
**Problem**: `BUILD FAILURE - compilation failed`

**Solution**:
```bash
# Clear cache
mvn clean

# Update dependencies
mvn dependency:resolve

# Rebuild
mvn clean install
```

### Database Connection Error
**Problem**: `Communications link failure` or `Access denied`

**Solution**:
1. Verify MySQL is running: `mysql -u root -p -e "SELECT 1;"`
2. Check credentials in DatabaseConnection.java
3. Ensure database exists: `mysql -u root -p -e "SHOW DATABASES LIKE 'javafxdemo';"`
4. Verify port 3306 is accessible

### Module Not Found
**Problem**: `error: module not found: com.mycomp` 

**Solution**:
```bash
# Ensure module-info.java is present and correct
# Rebuild with clean
mvn clean compile

# Check project structure matches pom.xml
```

### Missing JavaFX Libraries
**Problem**: `ClassNotFoundException: javafx`

**Solution**:
```bash
# Force dependency update
mvn dependency:resolve-plugins

# Clear Maven cache
rm -rf ~/.m2/repository/org/openjfx

# Rebuild
mvn clean install
```

### PowerShell Execution Policy (Windows)
**Problem**: `cannot be loaded because running scripts is disabled`

**Solution**:
```powershell
# Allow script execution for current user
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Verify
Get-ExecutionPolicy
```

## 📦 Verifying Installation

### Check All Components
```bash
# Java
java -version

# Maven
mvn -version

# MySQL (assuming root user set up)
mysql -u root -p -e "SELECT VERSION();"

# Git
git --version

# Project build
cd "JavaFx Project"
mvn clean install
```

## 🔐 Security Recommendations

1. **Change Default Credentials**: Update admin password in database
2. **Use Strong MySQL Passwords**: Don't leave mysql root without password
3. **Database Backups**: Regular backups of javafxdemo database
4. **Update Dependencies**: Keep JavaFX and MySQL Connector updated
5. **Firewall**: Support both 3306 (MySQL) is restricted appropriately

## 📞 Getting Help

If you encounter issues:
1. Check logs in console output
2. Run audit script: `.\Hotel-App-Scripts\audit-project.ps1`
3. Try the fix script: `.\Hotel-App-Scripts\fix-project.ps1`
4. Review relevant documentation in `/docs` folder
5. Check project README.md for additional guidance

## ✅ Installation Checklist

- [ ] JDK 23 installed and in PATH
- [ ] Maven installed and configured
- [ ] MySQL installed and running
- [ ] Project cloned from repository
- [ ] Database schema imported
- [ ] Database credentials verified
- [ ] Maven dependency downloaded successfully
- [ ] Application builds without errors
- [ ] Application starts successfully
- [ ] Database connection established

---

**Last Updated**: April 5, 2026
