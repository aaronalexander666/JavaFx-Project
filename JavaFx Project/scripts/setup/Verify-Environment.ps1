<#
.SYNOPSIS
Verify the local project environment and required files.
.DESCRIPTION
Checks that the project contains the expected Maven POM, config files, and script layout.
#>
[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Resolve-Path "$scriptDir\..\.."

$checks = @(
    @{ Name = 'Maven POM'; Path = Join-Path $projectRoot 'pom.xml' },
    @{ Name = 'JVM config'; Path = Join-Path $projectRoot 'config\jvm.config' },
    @{ Name = 'Development launcher'; Path = Join-Path $projectRoot 'scripts\launch\launch_app.ps1' },
    @{ Name = 'Build script'; Path = Join-Path $projectRoot 'scripts\build\Build-Release.ps1' },
    @{ Name = 'DragonGuard'; Path = Join-Path $projectRoot 'scripts\setup\DragonGuard.ps1' }
)

$errors = 0
foreach ($check in $checks) {
    if (Test-Path $check.Path) {
        Write-Host "[OK] $($check.Name): $($check.Path)" -ForegroundColor Green
    } else {
        Write-Host "[MISSING] $($check.Name): $($check.Path)" -ForegroundColor Red
        $errors++
    }
}

if ($errors -gt 0) {
    Write-Host "Environment verification failed. $errors path(s) are missing." -ForegroundColor Red
    exit 1
}

Write-Host 'Environment verification succeeded.' -ForegroundColor Green
