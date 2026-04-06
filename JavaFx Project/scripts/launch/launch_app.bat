@echo off
REM Development launcher fallback for Windows.
set SCRIPT_DIR=%~dp0
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%SCRIPT_DIR%launch_app.ps1" %*
