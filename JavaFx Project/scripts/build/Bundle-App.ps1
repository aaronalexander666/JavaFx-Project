<#
.SYNOPSIS
Wrapper for the release packaging workflow.
.DESCRIPTION
Calls Build-Release.ps1 to build and package the application using the configured jpackage workflow.
#>
[CmdletBinding()]
param(
    [string]$Version = '1.0.0',
    [switch]$SkipTests
)

$ErrorActionPreference = 'Stop'
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$buildScript = Join-Path $scriptDir 'Build-Release.ps1'

if (-not (Test-Path $buildScript)) {
    Throw "Build script not found: $buildScript"
}

if ($SkipTests) {
    & $buildScript -Version $Version -SkipTests
} else {
    & $buildScript -Version $Version
}
