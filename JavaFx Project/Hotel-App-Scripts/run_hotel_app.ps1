# 1. Configuration - UPDATE THESE PATHS
$JAVA_HOME = "C:\Program Files\Java\jdk-23"
$FX_SDK_LIB = "C:\path\to\javafx-sdk-23\lib" # Change this to your actual JavaFX lib path
$MYSQL_JAR = "C:\path\to\mysql-connector-j-9.x.jar" # Change this to your mysql jar path
$PROJECT_ROOT = Get-Location
$OUT_DIR = "$PROJECT_ROOT\out\production\javafxdemo"

# 2. Cleanup old builds
Write-Host "Cleaning up old build files..." -ForegroundColor Cyan
if (Test-Path $OUT_DIR) { Remove-Item -Recurse -Force $OUT_DIR }
New-Item -ItemType Directory -Force -Path $OUT_DIR

# 3. Find all Java files
$JAVA_FILES = Get-ChildItem -Recurse "$PROJECT_ROOT\src\*.java" | Select-Object -ExpandProperty FullName

# 4. Compile the project
Write-Host "Compiling JavaFX Project..." -ForegroundColor Cyan
& "$JAVA_HOME\bin\javac.exe" --module-path $FX_SDK_LIB --add-modules javafx.controls,javafx.fxml `
    -cp $MYSQL_JAR -d $OUT_DIR $JAVA_FILES

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation Failed. Please check your code for syntax errors." -ForegroundColor Red
    exit
}

# 5. Launch the Application
Write-Host "Launching Hotel Management System..." -ForegroundColor Green
# Change 'com.cts.javafxdemo.Main' if your main class is named differently
$MAIN_CLASS = "com.cts.javafxdemo.Main"

& "$JAVA_HOME\bin\java.exe" --module-path "$FX_SDK_LIB;$OUT_DIR" `
    --add-modules javafx.controls,javafx.fxml `
    -cp "$MYSQL_JAR;$OUT_DIR" `
    $MAIN_CLASS