@echo off
REM ==========================================
REM  HOTEL SYSTEM PRO BOOTSTRAPPER (Windows)
REM ==========================================
REM
REM This script automates:
REM   1. Database pre-flight check
REM   2. Maven compilation
REM   3. JavaFX application launch
REM

cls
color 0A
echo.
echo ==========================================
echo   🏨 HOTEL BOOKING SYSTEM
echo   PRO BOOTSTRAPPER
echo ==========================================
echo.

REM Determine Maven command (prefer wrapper)
if exist "mvnw.cmd" (
    set MVN_CMD=mvnw.cmd
    echo Using: Maven Wrapper (mvnw.cmd)
) else (
    set MVN_CMD=mvn
    echo Using: Global Maven command
)

echo.

REM Step 1: Pre-flight system check
echo [Step 1/3] Running pre-flight checks...
echo.

%MVN_CMD% exec:java -Dexec.mainClass="com.cts.javafxdemo.util.DbHealthCheck" -q

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 🚨 ERROR: Database check failed
    echo.
    echo Troubleshooting:
    echo  1. Is MySQL service running?
    echo     - On Windows: net start MySQL80
    echo  2. Are credentials correct in DbHealthCheck.java?
    echo  3. Is port 3306 blocked by firewall?
    echo.
    pause
    exit /b 1
)

echo.
echo [Step 2/3] Compiling source code...
echo.

%MVN_CMD% clean compile -q

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 🚨 ERROR: Compilation failed
    echo Run with verbose output for details:
    echo   %MVN_CMD% clean compile
    echo.
    pause
    exit /b 1
)

echo ✅ Compilation successful
echo.
echo [Step 3/3] Launching JavaFX application...
echo.

REM Set environment variables for optimal Celeron performance
set MAVEN_OPTS=-Xms256m -Xmx1024m -Dprism.order=sw

REM Launch the main application
%MVN_CMD% javafx:run

echo.
echo ==========================================
echo Application closed
echo ==========================================
echo.
pause
