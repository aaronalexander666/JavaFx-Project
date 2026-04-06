<#
.SYNOPSIS
Verify that the bundled Java runtime exists and is executable.
#>
[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$runtimeJava = Resolve-Path "$scriptDir\..\runtime\bin\java.exe" -ErrorAction SilentlyContinue

if (-not $runtimeJava) {
    Write-Host 'Bundled Java runtime not found.' -ForegroundColor Red
    exit 1
}

& "$runtimeJava" -version
if ($LASTEXITCODE -ne 0) {
    Write-Host 'Bundled Java runtime failed to execute.' -ForegroundColor Red
    exit 1
}

Write-Host 'Bundled Java runtime is valid.' -ForegroundColor Green
