# Run from your project root folder!
$pomPath = ".\pom.xml"
$libPath = ".\lib" # Common place for manual JARs

Write-Host "Starting Project Optimization for Hotel System..." -ForegroundColor Cyan

# 1. Backup and Update pom.xml
if (Test-Path $pomPath) {
    Copy-Item $pomPath "$pomPath.bak"
    $content = Get-Content $pomPath -Raw
    
    $dependency = @"
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.0.0</version>
        </dependency>
"@

    if ($content -notlike "*mysql-connector-j*") {
        # Inject before the closing </dependencies> tag
        $content = $content -replace '</dependencies>', "$dependency`n    </dependencies>"
        Set-Content $pomPath $content
        Write-Host "MySQL Dependency injected into pom.xml" -ForegroundColor Green
    } else {
        Write-Host "Dependency already exists in pom.xml" -ForegroundColor Yellow
    }
}

# 2. Delete Duplicate/Manual JARs
# Since you use Maven, manual JARs in /lib cause conflicts!
if (Test-Path $libPath) {
    Write-Host "Cleaning up manual JAR duplicates in $libPath..." -ForegroundColor Yellow
    Get-ChildItem -Path $libPath -Filter "mysql-connector*.jar" | Remove-Item -Force
    Write-Host "Duplicate manual drivers removed. Maven will now handle it." -ForegroundColor Green
}

# 3. Trigger Maven Refresh
Write-Host "Refreshing Maven dependencies..." -ForegroundColor Cyan
mvn clean install -DskipTests

Write-Host "`nProject is optimized and clean for submission!" -ForegroundColor Magenta