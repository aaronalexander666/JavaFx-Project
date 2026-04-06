<#
.SYNOPSIS
Verify and install Java dependencies required for development.
.DESCRIPTION
This script checks for a compatible JDK and ensures JavaFX dependencies are available for local development.
#>
[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Resolve-Path "$scriptDir\..\.."

function Write-Status {
    param([string]$Message, [string]$Level = 'INFO')
    $color = switch ($Level) {
        'ERROR' { 'Red' }
        'WARN' { 'Yellow' }
        'SUCCESS' { 'Green' }
        default { 'Cyan' }
    }
    Write-Host "[$Level] $Message" -ForegroundColor $color
}

Write-Status 'Verifying Java installation...'
$java = Get-Command java -ErrorAction SilentlyContinue
$javac = Get-Command javac -ErrorAction SilentlyContinue

if (-not $java -or -not $javac) {
    Write-Status 'Java or javac not found in PATH. Please install JDK 21 and restart PowerShell.' 'ERROR'
    exit 1
}

$versionText = (& java -version 2>&1 | Select-Object -First 1)
Write-Status "Java found: $versionText"

Write-Status 'JavaFX dependencies are managed by Maven in this project.'
Write-Status 'If you want a local JavaFX SDK, install it manually and update the scripts/launch/launch_app.ps1 file.' 'WARN'

Write-Status 'Java dependency check complete.' 'SUCCESS'
