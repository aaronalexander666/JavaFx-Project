<#
.SYNOPSIS
Build a packaged HotelApp distribution using Maven and jpackage.
.DESCRIPTION
This script runs a Maven package and then invokes the configured jpackage plugin.
#>

[CmdletBinding()]
param(
    [string]$Version = '1.0.0',
    [switch]$SkipTests
)

$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Resolve-Path "$scriptDir\..\.."

$javaHome = [Environment]::GetEnvironmentVariable('JAVA_HOME', 'Machine')
if (-not $javaHome -or -not (Test-Path "$javaHome\bin\javac.exe")) {
    Write-Warning 'JAVA_HOME is not configured or does not point to a valid JDK. Verify your build environment before packaging.'
}

$mavenArgs = @('clean', 'package')
if ($SkipTests) {
    $mavenArgs += '-DskipTests'
}

Push-Location $projectRoot
try {
    & mvn $mavenArgs
    if ($LASTEXITCODE -ne 0) { throw 'Maven build failed.' }
    & mvn -Dapp.version=$Version jpackage:jpackage
    if ($LASTEXITCODE -ne 0) { throw 'jpackage execution failed.' }
}
finally {
    Pop-Location
}

$distDir = Join-Path $projectRoot 'target\dist'
if (Test-Path $distDir) {
    Write-Host "Distribution created at: $distDir" -ForegroundColor Green
} else {
    Write-Warning 'jpackage did not produce a distribution directory. Check Maven output for details.'
}
