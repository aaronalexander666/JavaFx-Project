<#
.SYNOPSIS
Forwarding stub for the new launch script location.
.DESCRIPTION
Retains compatibility with the existing root launch_app.ps1 entrypoint while using scripts/launch/launch_app.ps1.
#>

[CmdletBinding()]
param(
    [Parameter(ValueFromRemainingArguments=$true)]
    [string[]]$Args
)

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$target = Join-Path $scriptDir 'scripts\launch\launch_app.ps1'

if (-not (Test-Path $target)) {
    Write-Error "Unable to locate launch script: $target"
    exit 1
}

Write-Host "Forwarding launch to $target" -ForegroundColor Cyan
& powershell.exe -NoProfile -ExecutionPolicy Bypass -File $target @Args
