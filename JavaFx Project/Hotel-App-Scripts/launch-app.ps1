<#
.SYNOPSIS
    Production-Grade Launcher for JavaFX Hotel Management System.
    Optimized for MySQL Connector/J 9.5.0 and JDK 23.
#>

# --- 1. System Configuration ---
$Config = @{
# Core Paths
    JavaExe      = "C:\Program Files\Java\jdk-23\bin\java.exe"
    JavaFXLib    = "C:\path\to\javafx-sdk-23\lib" # <-- UPDATE THIS TO YOUR SDK LIB PATH

    # Driver: mysql-connector-j-9.5.0
    MysqlJar     = "C:\path\to\mysql-connector-j-9.5.0.jar" # <-- UPDATE THIS TO YOUR JAR PATH

    # Project Structure
    OutDir       = "$PSScriptRoot\out\production\javafxdemo"
    MainClass    = "com.cts.javafxdemo.Main"

    # Metadata
    ProjectName  = "Hotel Management System"
    Timestamp    = (Get-Date -Format "yyyy-MM-dd HH:mm:ss")
}

# --- 2. Pre-Flight Validation ---
function Invoke-EnvironmentCheck {
    Write-Host "[*] System Audit at $($Config.Timestamp)" -ForegroundColor Cyan

    $Targets = @($Config.JavaExe, $Config.JavaFXLib, $Config.MysqlJar, $Config.OutDir)
    $AllClear = $true

    foreach ($Path in $Targets) {
        if (-not (Test-Path $Path)) {
            Write-Host "[!] MISSING CRITICAL PATH: $Path" -ForegroundColor Red
            $AllClear = $false
        }
    }
    return $AllClear
}

# --- 3. Execution Logic ---
function Start-ProductionLaunch {
    if (-not (Invoke-EnvironmentCheck)) {
        Write-Host "[-] Launch Aborted: Please verify the paths in the script header." -ForegroundColor Yellow
        return
    }

    Write-Host "[+] Launching $($Config.ProjectName)..." -ForegroundColor Green

    # Arguments formatted as an array to prevent ParserErrors and "Unexpected Tokens"
    $LaunchArgs = @(
        "--module-path", "$($Config.JavaFXLib)",
        "--add-modules", "javafx.controls,javafx.fxml,java.sql",
        "-cp", "$($Config.OutDir);$($Config.MysqlJar)",
        $Config.MainClass
    )

    try {
        # Using Start-Process with -Wait for session persistence
        Start-Process -FilePath $Config.JavaExe -ArgumentList $LaunchArgs -Wait -NoNewWindow
    }
    catch {
        Write-Error "Execution Failed: $($_.Exception.Message)"
    }
}

# --- 4. Boot ---
Clear-Host
Start-ProductionLaunch