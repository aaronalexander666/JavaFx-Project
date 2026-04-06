@echo off
REM Launch the Pro Feature Tester in isolation
echo 🔧 Launching Pro Feature Tester...
echo.

if exist "mvnw.cmd" (
    mvnw.cmd exec:java -Dexec.mainClass="com.cts.javafxdemo.pro.ProFeatureTesterLauncher" -q
) else (
    mvn exec:java -Dexec.mainClass="com.cts.javafxdemo.pro.ProFeatureTesterLauncher" -q
)
