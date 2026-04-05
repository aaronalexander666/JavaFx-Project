# JavaFX Hotel Management System

A modern, production-grade hotel management application built with **JavaFX 23**, **Maven**, **MySQL**, and **JDK 23**.

## 📋 Overview

This application provides comprehensive hotel management capabilities including:
- **Customer Management**: Register and manage customer information
- **Room Management**: Track room inventory and availability
- **Booking System**: Create and manage reservations
- **Staff Dashboard**: Staff-specific operations and reporting
- **Authentication**: Secure login system for both customers and staff

## 🎯 Key Features

- ✅ Clean JavaFX UI with FXML layouts
- ✅ Relational MySQL database with proper schema
- ✅ Service-oriented architecture (DAOs, Services)
- ✅ Transaction management for bookings
- ✅ Production-grade automation scripts
- ✅ Database migrations included
- ✅ Comprehensive error handling and logging

## 📁 Project Structure

```
JavaFx Project/
├── src/                              # Source code
│   └── main/
│       ├── java/                     # Java source files
│       │   ├── com/cts/javafxdemo/   # Main application code
│       │   ├── UI/                   # UI components and models
│       │   └── *.java                # Services and DAOs
│       └── resources/                # FXML files and assets
├── db/                               # Database files
│   ├── hotel-bd.sql                  # Main database schema
│   └── setup_database.sql            # Quick setup script
├── docs/                             # Documentation
│   ├── scripts/                      # Script documentation
│   ├── ARCHITECTURE.md               # System design
│   ├── BUILDING.md                   # Build instructions
│   └── DATABASE.md                   # Database guide
├── Hotel-App-Scripts/                # Automation scripts
│   ├── *.ps1                         # PowerShell automation tools
│   └── Help/                         # Script documentation
├── pom.xml                           # Maven configuration
└── target/                           # Build output (git ignored)
```

## 🚀 Quick Start

### Prerequisites
- **JDK 23** or higher
- **Maven 3.8+**
- **MySQL 8.0+**
- PowerShell 5.0+ (for Windows automation scripts)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd "JavaFx Project"
   ```

2. **Setup Database**
   - Start MySQL service
   - Run database setup:
     ```bash
     mysql -u root -p < db/hotel-bd.sql
     ```
   - Or use the provided script:
     ```powershell
     # Windows
     .\Hotel-App-Scripts\mysql-fixdb.ps1
     ```

3. **Build the Application**
   ```bash
   mvn clean install
   # or use the script
   .\Hotel-App-Scripts\clean-and-build.ps1
   ```

4. **Run the Application**
   ```bash
   mvn javafx:run
   # or
   .\Hotel-App-Scripts\run_hotel_app.ps1
   # or with JDK 23 specifically
   .\Hotel-App-Scripts\run-hotel-jdk23.ps1
   ```

## 📦 Dependencies

- **JavaFX 23.0.1**: Modern UI framework
- **MySQL Connector/J 9.0.0**: Database connectivity
- **Maven Compiler 3.13.0**: Build tool

## 📚 Documentation

- [**SETUP.md**](docs/SETUP.md) - Detailed setup and troubleshooting
- [**ARCHITECTURE.md**](docs/ARCHITECTURE.md) - System design and component overview
- [**DATABASE.md**](docs/DATABASE.md) - Database schema and relationships
- [**BUILDING.md**](docs/BUILDING.md) - Build and compilation instructions
- [**SCRIPTS.md**](docs/SCRIPTS.md) - PowerShell automation scripts reference

## 🔧 Automation Scripts

All automation scripts are located in `Hotel-App-Scripts/`:

| Script | Purpose |
|--------|---------|
| `launch-app.ps1` | Main application launcher with environment validation |
| `clean-and-build.ps1` | Clean build and Maven compilation |
| `run_hotel_app.ps1` | Run the application |
| `run-hotel-jdk23.ps1` | Run with JDK 23 specific configuration |
| `mysql-fixdb.ps1` | Database initialization and repair |
| `maven-optimize.ps1` | Optimize Maven build settings |
| `audit-project.ps1` | Project health and compliance check |
| `fix-project.ps1` | Auto-fix common project issues |
| `init-hotel-files.ps1` | Initialize required files and folders |

For detailed script documentation, see [SCRIPTS.md](docs/SCRIPTS.md)

## 🗄️ Database

The application uses MySQL with the following main tables:
- **tblcustomer**: Customer information and profiles
- **tblstaff**: User accounts and staff information
- **tblroom**: Room inventory and configuration
- **tblbooking**: Reservation management

See [DATABASE.md](docs/DATABASE.md) for complete schema documentation.

## 🔐 Default Credentials

For testing purposes, use:
- **Username**: admin
- **Password**: admin123

**Note**: Change these in production!

## 🛠️ Development

### Code Structure

- **Controllers** (`*.java`): FXML controller classes handling UI logic
- **Models** (`com.hotel.models`): Data model classes
- **Services** (`*Service.java`): Business logic layer
- **DAOs** (`*DAO.java`): Data access objects  
- **UI** (`resources/*.fxml`): JavaFX UI definitions

### Building for Development

```bash
# Clean build
mvn clean

# Compile
mvn compile

# Run tests (if added)
mvn test

# Package
mvn package
```

## 🐛 Troubleshooting

### Database Connection Issues
- Ensure MySQL is running on localhost:3306
- Verify credentials in `DatabaseConnection.java`
- Check firewall settings

### Build Failures
- Run: `mvn clean install`
- Check Maven dependencies: `mvn dependency:tree`
- Ensure JDK 23 is in PATH

### Runtime Issues
- Check logs in console output
- Verify all resources are in proper locations
- Ensure database tables are created

See [SETUP.md](docs/SETUP.md) for more troubleshooting steps.

## 📝 Version Info

- **Application Version**: 1.0
- **JavaFX Version**: 23.0.1
- **JDK Target**: 23
- **MySQL Connector**: 9.0.0
- **Java Compiler Source/Target**: 23

## 📄 License

[Add your license information here]

## 👥 Contributing

[Add contribution guidelines here]

## 📧 Support

For issues and questions, please contact the development team.

---

**Last Updated**: April 5, 2026  
**Project Status**: Active Development
