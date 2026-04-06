@echo off
REM Quick launch - minimal output
if exist "mvnw.cmd" (
    mvnw.cmd javafx:run
) else (
    mvn javafx:run
)
