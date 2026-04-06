<#
.SYNOPSIS
Repair a broken bundled application installation.
.DESCRIPTION
This script validates the key distribution folders and reports missing files.
#>
[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$installRoot = Resolve-Path "$scriptDir\..\..\.."

$expected = @(
    'app',
    'app\HotelApp.jar',
    'runtime',
    'runtime\bin\java.exe'
)

$missing = @()
foreach ($item in $expected) {
    if (-not (Test-Path (Join-Path $installRoot $item))) {
        $missing += $item
    }
}

if ($missing.Count -gt 0) {
    Write-Host "Repair check found missing items:" -ForegroundColor Yellow
    $missing | ForEach-Object { Write-Host " - $_" }
    Write-Host 'Please reinstall or restore the missing files.' -ForegroundColor Red
    exit 1
}

Write-Host 'Installation repair check passed.' -ForegroundColor Green
