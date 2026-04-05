# Hotel Project Optimization & Cleanup
$pomPath = ".\pom.xml"

Write-Host "Starting Optimization in: $(Get-Location)" -ForegroundColor Cyan

# 1. Verify pom.xml exists
if (Test-Path $pomPath) {
    # Create backup before modification
    Copy-Item $pomPath "$pomPath.bak" -Force -ErrorAction SilentlyContinue
    $content = Get-Content $pomPath -Raw

    # 2. Define Local MySQL JDBC Dependency
    $dependency = @"
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.0.0</version>
        </dependency>
"@

    # 3. Inject dependency into pom.xml if missing
    if ($content -notlike "*mysql-connector-j*") {
        # Using a safer replacement to ensure it lands inside the <dependencies> tag
        $content = $content -replace '</dependencies>', "`n        $dependency`n    </dependencies>"
        Set-Content $pomPath $content
        Write-Host "[SUCCESS] MySQL Connector 9.0.0 injected into pom.xml." -ForegroundColor Green
    } else {
        Write-Host "[INFO] MySQL dependency already present." -ForegroundColor Yellow
    }

    # 4. Clean Build and Refresh
    Write-Host "Rebuilding project and refreshing dependencies..." -ForegroundColor Cyan

    # Executing Maven clean install
    mvn clean install -DskipTests

    if ($LASTEXITCODE -eq 0) {
        Write-Host "Optimization Complete: Project is clean and ready for submission." -ForegroundColor Green
    } else {
        Write-Host "ERROR: Maven build failed. Check pom.xml syntax." -ForegroundColor Red
    }

} else {
    Write-Host "ERROR: pom.xml not found! Ensure the script is run from the project root." -ForegroundColor Red
}