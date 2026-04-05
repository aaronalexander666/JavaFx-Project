# Build Guide - JavaFX Hotel Management System

## 🔨 Building the Application

Complete guide to building and compiling the JavaFX Hotel Management System.

## 📋 Prerequisites

Before building, ensure you have:
- ✅ JDK 23 installed and in PATH
- ✅ Maven 3.8.1+ installed and configured
- ✅ Git clone of the repository
- ✅ Internet connection (for Maven dependency download)

### Verify Prerequisites

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Git
git --version
```

## 🏗️ Build Architecture

### Maven Project Structure

```
pom.xml (Project configuration)
├── Properties (Java version, dependencies)
├── Dependencies (JavaFX, MySQL Connector, etc.)
├── Plugins (Compiler, JavaFX Maven Plugin)
└── Build Profile
```

### Key Build Properties

```xml
<properties>
    <maven.compiler.source>23</maven.compiler.source>
    <maven.compiler.target>23</maven.compiler.target>
    <javafx.version>23.0.1</javafx.version>
    <main.class>com.demo.Main</main.class>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## 🔄 Maven Build Lifecycle

### Phases

1. **validate**: Validates project is correct
2. **compile**: Compiles source code
3. **test**: Runs unit tests (if present)
4. **package**: Packages code (JAR/WAR)
5. **verify**: Verifies package is valid
6. **install**: Installs package locally
7. **deploy**: Deploys package to repository

### Build Commands

#### Clean Build (Recommended)
```bash
mvn clean install
```
This removes all previous build artifacts and rebuilds from scratch.

#### Quick Build (Skip Tests)
```bash
mvn clean install -DskipTests
```

#### Compile Only
```bash
mvn clean compile
```

#### Package Only
```bash
mvn package
```

## ▶️ Running the Application

### Via Maven

```bash
# Using JavaFX Maven Plugin
mvn javafx:run

# Debug mode
mvn -X javafx:run
```

### Via JAR

```bash
# After packaging
java -jar target/javafxdemo-1.0.jar

# With explicit module path
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/javafxdemo-1.0.jar
```

### Via IDE

- **IntelliJ IDEA**: Run → Run 'Main'
- **Eclipse**: Right-click project → Run As → Java Application
- **VS Code**: Run → Run Without Debugging

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn -Dtest=TestClassName test
```

### Skip Tests During Build
```bash
mvn clean install -DskipTests
```

## 📦 Build Output

### Generated Artifacts

```
target/
├── classes/                 # Compiled .class files
├── javafxdemo-1.0.jar      # Application JAR
├── site/                    # Documentation site
├── surefire-reports/       # Test reports
└── generated-sources/      # Annotation processors output
```

### Target Directory
- `.class` files: `target/classes/`
- JAR file: `target/javafxdemo-1.0.jar`
- Dependencies: `target/dependency/`

## 🔧 Build Configuration

### Custom Build Settings

Edit `pom.xml` to modify:

```xml
<!-- Change main class -->
<main.class>com.new.MainClass</main.class>

<!-- Change Java version -->
<maven.compiler.source>23</maven.compiler.source>
<maven.compiler.target>23</maven.compiler.target>

<!-- Add new dependencies -->
<dependency>
    <groupId>group</groupId>
    <artifactId>artifact</artifactId>
    <version>1.0</version>
</dependency>
```

### Maven Configuration File

Edit `~/.m2/settings.xml` for global Maven settings:

```xml
<settings>
    <localRepository>/path/to/custom/repo</localRepository>
    <servers>
        <!-- Add repository credentials if needed -->
    </servers>
</settings>
```

## 🐛 Common Build Issues

### Issue: Java Version Mismatch
**Error**: `error: incompatible types`

**Solution**:
```bash
# Set JAVA_HOME
export JAVA_HOME=/path/to/jdk-23

# Rebuild
mvn clean install
```

### Issue: Maven Cannot Find Dependencies
**Error**: `Could not find artifact org.openjfx:javafx-controls`

**Solution**:
```bash
# Clear cache and retry
rm -rf ~/.m2/repository
mvn clean install

# Or force update
mvn clean install -U
```

### Issue: Memory Issues During Build
**Error**: `OutOfMemoryError: Java heap space`

**Solution**:
```bash
# Increase Maven memory
export MAVEN_OPTS="-Xmx2048m -Xms1024m"
mvn clean install
```

### Issue: Module Not Found
**Error**: `error: module not found: javafx.controls`

**Solution**:
```bash
# Ensure JavaFX module-info.java exists
# Check pom.xml has correct dependencies
mvn dependency:resolve
mvn clean install
```

### Issue: Compilation Succeeds but Runtime Error
**Error**: `ClassNotFoundException` or `ModuleNotFoundException`

**Solution**:
1. Verify resource files are in proper location
2. Check FXML file references
3. Ensure all classes are compiled:
   ```bash
   mvn clean compile
   ```

## 🚀 Advanced Build Options

### Building with Different Profiles

```bash
# Development profile
mvn clean install -Pdev

# Production profile
mvn clean install -Pprod
```

### Generate Project Site Documentation

```bash
# Generate site (includes JavaDoc)
mvn site

# View at target/site/index.html
```

### Generate JavaDoc

```bash
mvn javadoc:javadoc
```

### Build with Debug Information

```bash
mvn clean install -DdebugSymbols=true
```

## 📊 Build Performance

### Optimization Tips

1. **Skip Tests for Development**
   ```bash
   mvn clean install -DskipTests
   ```

2. **Parallel Builds**
   ```bash
   mvn -T 1C clean install  # 1 thread per core
   ```

3. **Offline Mode** (if dependencies cached)
   ```bash
   mvn -o clean install
   ```

4. **Skip Code Analysis**
   ```bash
   mvn clean install -DskipCodeAnalysis
   ```

### Build Speed Benchmarks

- Clean Build: ~45-60 seconds
- Incremental Build: ~15-20 seconds
- Package Only: ~5-10 seconds

## 🔐 Security in Build

### Dependency Security Check

```bash
# Check for known vulnerabilities
mvn dependency:tree

# Use OWASP Dependency Check
mvn org.owasp:dependency-check-maven:check
```

### Secure Build Environment

- Use HTTPS for Maven repositories
- Verify JAR signatures
- Use private repository for internal artifacts
- Keep Maven and dependencies updated

## 📋 Build Checklist

- [ ] JDK 23 installed: `java -version`
- [ ] Maven 3.8.1+: `mvn -version`
- [ ] Repository cloned
- [ ] Dependencies download: `mvn dependency:resolve`
- [ ] Clean compile: `mvn clean compile`
- [ ] Tests pass: `mvn test`
- [ ] Package builds: `mvn package`
- [ ] Application runs: `mvn javafx:run`
- [ ] JAR executable: `java -jar target/*.jar`

## 🤖 Automated Builds

### Using Git Hooks

Create `.git/hooks/post-commit`:
```bash
#!/bin/bash
mvn clean install -DskipTests
```

### GitHub Actions Example

```yaml
name: Build
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '23'
      - run: mvn clean install
```

## 📚 Related Documentation

- [README.md](../README.md) - Project overview
- [SETUP.md](SETUP.md) - Installation guide
- [pom.xml](../pom.xml) - Maven configuration

---

**Last Updated**: April 5, 2026
**Build Tool**: Maven 3.8.1+
**Java Version**: 23
