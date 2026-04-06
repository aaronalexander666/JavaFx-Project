<#
.SYNOPSIS
Provides a reusable logging function for PowerShell scripts.
#>
function Write-AppLog {
    param(
        [Parameter(Mandatory)] [string]$Message,
        [string]$Level = 'INFO',
        [string]$LogFile = "$PSScriptRoot\..\..\logs\app.log"
    )

    $timestamp = Get-Date -Format 'yyyy-MM-dd HH:mm:ss'
    $entry = "[$timestamp] [$Level] $Message"

    $directory = Split-Path -Parent $LogFile
    if (-not (Test-Path $directory)) { New-Item -ItemType Directory -Path $directory -Force | Out-Null }

    $entry | Add-Content -Path $LogFile -Encoding UTF8
    Write-Host $entry
}
