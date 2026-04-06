<#
.SYNOPSIS
Launch HotelApp in development mode using the Maven/target build output.
.DESCRIPTION
This script is intended for IDE or local development. It does not rely on a bundled jpackage runtime.
#>

[CmdletBinding()]
param(
    [switch]$Debug
)

$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Resolve-Path "$scriptDir\..\.."

$jvmConfigPath = Join-Path $projectRoot 'config\jvm.config'
if (Test-Path $jvmConfigPath) {
    $jvmArgs = Get-Content $jvmConfigPath | Where-Object { $_ -and $_ -notmatch '^\s*#' }
} else {
    $jvmArgs = @('-Dprism.order=sw')
}

if ($Debug) {
    $jvmArgs += '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005'
}

$mainModule = 'com.cts.javafxdemo/com.cts.javafxdemo.Main'
$targetDir = Join-Path $projectRoot 'target'
$jarFile = Get-ChildItem -Path $targetDir -Filter 'javafx-demo-*.jar' | Where-Object { $_.Name -notmatch 'sources|javadoc' } | Select-Object -First 1

if (-not $jarFile) {
    Write-Error "Main JAR not found. Run 'mvn clean package' first."
    exit 1
}

$modulePath = "$targetDir\lib;$targetDir"
$javaExe = 'java'

Write-Host "Launching HotelApp (development mode)..." -ForegroundColor Cyan
Write-Host "Java: $(& $javaExe -version 2>&1 | Select-Object -First 1)" -ForegroundColor Gray

& $javaExe @jvmArgs --module-path $modulePath --add-modules javafx.controls,javafx.fxml,java.sql -m $mainModule
