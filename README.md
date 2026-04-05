# JavaFX Hotel Management System - Repository

Welcome to the JavaFX Hotel Management System repository! This project contains a complete, production-ready hotel management application built with modern Java technologies.

## 📁 Repository Structure

```
.
├── JavaFx Project/               # Main application directory
│   ├── README.md                 # Detailed project documentation
│   ├── pom.xml                   # Maven build configuration
│   ├── .gitignore                # Git ignore rules
│   ├── src/                      # Source code
│   │   └── main/
│   │       ├── java/             # Java source files
│   │       └── resources/        # FXML, CSS, and other resources
│   ├── db/                       # Database files
│   │   ├── hotel-bd.sql          # Complete database schema
│   │   └── setup_database.sql    # Quick setup script
│   ├── docs/                     # Documentation
│   │   ├── SETUP.md              # Installation and setup guide
│   │   ├── ARCHITECTURE.md       # System design and architecture
│   │   ├── DATABASE.md           # Database schema and structure
│   │   ├── BUILDING.md           # Build instructions
│   │   ├── SCRIPTS.md            # PowerShell scripts reference
│   │   └── scripts/              # Script documentation
│   ├── Hotel-App-Scripts/        # Automation and build scripts
│   │   ├── *.ps1                 # PowerShell automation tools
│   │   ├── Help/                 # Script help documentation
│   │   └── setup_database.sql    # Database setup utility
│   ├── target/                   # Build output (auto-generated)
│   └── .mvn/                     # Maven wrapper configuration
├── README.md                     # This file
└── .git/                         # Git repository

```

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd JavaFx-Project
cd "JavaFx Project"
```

### 2. Install Prerequisites
- **JDK 23**: [Download from Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+**: [Download from Apache](https://maven.apache.org/)
- **MySQL 8.0+**: [Download from MySQL](https://www.mysql.com/downloads/)

### 3. Setup Database
```bash
mysql -u root -p < db/hotel-bd.sql
```

Or on Windows with PowerShell:
```powershell
.\Hotel-App-Scripts\mysql-fixdb.ps1
```

### 4. Build the Application
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn javafx:run
```

For detailed setup instructions, see [JavaFx Project/docs/SETUP.md](JavaFx%20Project/docs/SETUP.md)

## 📚 Documentation Guide

| Document | Purpose |
|----------|---------|
| [README.md](JavaFx%20Project/README.md) | Project overview and features |
| [docs/SETUP.md](JavaFx%20Project/docs/SETUP.md) | Complete installation guide |
| [docs/ARCHITECTURE.md](JavaFx%20Project/docs/ARCHITECTURE.md) | System design and layers |
| [docs/DATABASE.md](JavaFx%20Project/docs/DATABASE.md) | Database schema and queries |
| [docs/BUILDING.md](JavaFx%20Project/docs/BUILDING.md) | Build and compilation guide |
| [docs/SCRIPTS.md](JavaFx%20Project/docs/SCRIPTS.md) | PowerShell scripts reference |

## 🎯 Key Features

- ✅ **JavaFX 23** UI framework
- ✅ **MySQL** database integration
- ✅ **Maven** build automation
- ✅ **MVC Architecture** for clean code organization
- ✅ **Service Layer** for business logic
- ✅ **DAO Pattern** for data access
- ✅ **PowerShell Automation** for deployment
- ✅ **Comprehensive Documentation** for all components

## 🏗️ Project Structure

### Application Code (`src/`)
```
src/main/
├── java/
│   ├── com/cts/javafxdemo/        # Main application package
│   ├── UI/controllers/             # JavaFX controllers
│   ├── UI/models/                  # Data model classes
│   ├── *Service.java               # Business logic
│   └── *DAO.java                   # Data access objects
└── resources/
    ├── *.fxml                      # JavaFX UI layouts
    └── *.css                       # Stylesheets
```

### Database (`db/`)
```
db/
├── hotel-bd.sql       # Main schema with 4 core tables
└── setup_database.sql # Convenience setup script
```

### Automation Scripts (`Hotel-App-Scripts/`)
- `launch-app.ps1` - Main launcher
- `clean-and-build.ps1` - Maven build
- `run_hotel_app.ps1` - Application runner
- `mysql-fixdb.ps1` - Database initialization
- `audit-project.ps1` - Health check
- And 7+ more utility scripts

## 📦 Dependencies

### Core Libraries
- **JavaFX 23.0.1** - UI Framework
- **MySQL Connector/J 9.0.0** - Database Driver
- **Maven 3.8.1+** - Build Tool
- **JDK 23** - Java Runtime

## 🔄 Development Workflow

### Daily Development
```bash
# Build and run
mvn clean install
mvn javafx:run

# Or on Windows
.\Hotel-App-Scripts\run_hotel_app.ps1
```

### Making Changes
1. Edit source files in `src/main/java/`
2. Edit UI files in `src/main/resources/`
3. Test locally: `mvn javafx:run`
4. Commit: `git add . && git commit -m "message"`
5. Push: `git push origin main`

### Project Maintenance
```bash
# Check project health
.\Hotel-App-Scripts\audit-project.ps1

# Fix issues automatically
.\Hotel-App-Scripts\fix-project.ps1

# Optimize builds
.\Hotel-App-Scripts\maven-optimize.ps1
```

## 🐛 Troubleshooting

### Build Issues
See [docs/BUILDING.md](JavaFx%20Project/docs/BUILDING.md)

### Database Issues
See [docs/DATABASE.md](JavaFx%20Project/docs/DATABASE.md)

### Setup Issues
See [docs/SETUP.md](JavaFx%20Project/docs/SETUP.md)

### Script Issues
See [docs/SCRIPTS.md](JavaFx%20Project/docs/SCRIPTS.md)

## 🔐 Default Credentials

For initial testing:
- **Username**: admin
- **Password**: admin123

**⚠️ Change these in production!**

See [docs/DATABASE.md](JavaFx%20Project/docs/DATABASE.md#-user-management) for security recommendations.

## 📊 Technology Stack

```
┌─────────────────────────────────────┐
│  Application Layer                  │
│  JavaFX 23 (UI Framework)           │
│  FXML (UI Markup Language)          │
│  CSS (Styling)                      │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│  Business Logic Layer               │
│  Services (*Service.java)           │
│  Controllers (*Controller.java)     │
│  Models (Entity Classes)            │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│  Data Access Layer                  │
│  DAOs (*DAO.java)                   │
│  DatabaseConnection Manager         │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│  Database Layer                     │
│  MySQL 8.0+ (javafxdemo database)   │
│  JDBC Connectivity                  │
└─────────────────────────────────────┘
```

## 📝 Current Version

- **Version**: 1.0
- **Release Date**: April 5, 2026
- **Status**: Active Development
- **Java Target**: JDK 23
- **JavaFX Version**: 23.0.1

## 🤝 Contributing

When contributing to this project:

1. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** and test thoroughly
   ```bash
   mvn clean install
   mvn javafx:run
   ```

3. **Commit with clear messages**
   ```bash
   git commit -m "feat: description of your changes"
   ```

4. **Push to your branch**
   ```bash
   git push origin feature/your-feature-name
   ```

5. **Create a Pull Request**

## 📋 Project Checklist

### Development Setup
- [ ] Clone repository
- [ ] Install JDK 23
- [ ] Install Maven 3.8+
- [ ] Install MySQL 8.0+
- [ ] Run setup guide: `docs/SETUP.md`
- [ ] Build project: `mvn clean install`
- [ ] Test run: `mvn javafx:run`

### Code Quality
- [ ] Code follows project standards
- [ ] No compilation warnings
- [ ] Tests pass (if applicable)
- [ ] Documentation updated
- [ ] Commit message is clear

## 📞 Support & Issues

For issues or questions:
1. Check relevant documentation in `docs/` folder
2. Review the project README.md
3. Check troubleshooting sections
4. Review script documentation in `Hotel-App-Scripts/Help/`

## 📄 License

[Add your license information here]

## 🎓 Learning Resources

- [JavaFX Documentation](https://openjfx.io/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Java 23 Documentation](https://docs.oracle.com/en/java/javase/23/)

## 📞 Contact

[Add contact information here]

---

**Last Updated**: April 5, 2026  
**Repository Status**: Active  
**Maintained By**: [Your Team Name]

🌟 **If you find this project useful, please consider giving it a star!**