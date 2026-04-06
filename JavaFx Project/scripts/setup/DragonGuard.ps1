<#
.SYNOPSIS
Configure a Windows Java development environment by setting JAVA_HOME and adding Java to PATH.
.DESCRIPTION
DragonGuard locates installed JDKs, chooses the requested JDK, and persists environment settings for development.
.NOTES
Target: Windows PowerShell 5.1+ / PowerShell 7+
#>

[CmdletBinding()]
param(
    [switch]$SetLatest,
    [string]$DesiredJdkPath
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

function Write-DragonGuard {
    param([string]$Message, [string]$Level = 'INFO')
    $color = switch ($Level) {
        'ERROR' { 'Red' }
        'WARN' { 'Yellow' }
        'SUCCESS' { 'Green' }
        default { 'Cyan' }
    }
    Write-Host "[DragonGuard] $Message" -ForegroundColor $color
}

function Test-IsAdministrator {
    try {
        $current = [Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()
        return $current.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
    } catch {
        return $false
    }
}

function Normalize-Path {
    param([string]$Path)
    if ([string]::IsNullOrWhiteSpace($Path)) { return $null }
    return (Resolve-Path -Path $Path -ErrorAction SilentlyContinue).Path.TrimEnd('\')
}

function Get-JdkCandidates {
    $roots = @(
        'C:\Program Files\Java',
        'C:\Program Files (x86)\Java',
        'C:\Program Files\Eclipse Adoptium',
        'C:\Program Files\Amazon Corretto',
        'C:\Program Files\Microsoft',
        "$env:LOCALAPPDATA\Programs\AdoptOpenJDK",
        "$env:LOCALAPPDATA\Programs\Eclipse Adoptium",
        "$env:USERPROFILE\.jdks",
        "$env:USERPROFILE\.jabba\jdk"
    )

    $candidates = [System.Collections.Generic.List[object]]::new()

    foreach ($root in $roots) {
        if (-not (Test-Path $root)) { continue }
        Get-ChildItem -Path $root -Directory -ErrorAction SilentlyContinue | ForEach-Object {
            $candidate = $_.FullName
            $bin = Join-Path $candidate 'bin'
            $java = Join-Path $bin 'java.exe'
            $javac = Join-Path $bin 'javac.exe'
            if ((Test-Path $java) -and (Test-Path $javac)) {
                try {
                    $versionText = (& $java -version 2>&1 | Select-Object -First 1)
                    if ($versionText -match '"(?<major>\d+)(?:\.(?<minor>\d+))?(?:\.(?<patch>\d+))?') {
                        $version = [version]::new($matches.major, ($matches.minor -as [int]) , ($matches.patch -as [int]))
                    } else {
                        $version = [version]::new(0,0,0)
                    }
                    $candidates.Add([pscustomobject]@{
                        Path = $candidate
                        Bin = $bin
                        Java = $java
                        Javac = $javac
                        Version = $version
                        RawVersion = $versionText
                    })
                } catch {
                    # ignore invalid candidates
                }
            }
        }
    }

    return $candidates | Sort-Object Version -Descending
}

function Set-JavaHome {
    param([Parameter(Mandatory)][string]$JdkPath)
    $normalized = Normalize-Path $JdkPath
    if (-not $normalized) { throw 'Invalid JDK path.' }

    $targetJavaHome = $normalized
    $targetJavaBin = Join-Path $targetJavaHome 'bin'

    if (-not (Test-Path $targetJavaBin)) {
        throw "JDK bin directory not found: $targetJavaBin"
    }

    if (Test-IsAdministrator) {
        Write-DragonGuard "Setting machine JAVA_HOME to: $targetJavaHome"
        setx /M JAVA_HOME "$targetJavaHome" | Out-Null
        if ($LASTEXITCODE -ne 0) {
            throw 'Failed to write JAVA_HOME to machine environment.'
        }
        Write-DragonGuard 'JAVA_HOME persisted to machine environment.' 'SUCCESS'

        $currentPath = [Environment]::GetEnvironmentVariable('Path', 'Machine')
        if ($currentPath -notmatch [regex]::Escape('%JAVA_HOME%\bin') -and $currentPath -notmatch [regex]::Escape($targetJavaBin)) {
            $newPath = "$currentPath;%JAVA_HOME%\bin"
            if ($newPath.Length -gt 32767) {
                Write-DragonGuard 'Machine PATH is too long to safely append JAVA_HOME\bin automatically.' 'WARN'
            } else {
                Write-DragonGuard 'Prepending %JAVA_HOME%\bin to machine PATH.'
                setx /M Path "$newPath" | Out-Null
            }
        }
    } else {
        Write-DragonGuard 'Not running as Administrator. Setting user environment only.' 'WARN'
        setx JAVA_HOME "$targetJavaHome" | Out-Null
        Write-DragonGuard 'JAVA_HOME persisted to user environment.' 'SUCCESS'
        Write-DragonGuard 'Please restart your PowerShell windows after running this script.' 'WARN'
    }
}

function Validate-Jdk {
    param([string]$Path)
    $bin = Join-Path $Path 'bin'
    $java = Join-Path $bin 'java.exe'
    $javac = Join-Path $bin 'javac.exe'
    return (Test-Path $java) -and (Test-Path $javac)
}

function Resolve-TargetJdk {
    param([object[]]$Candidates)
    if ($DesiredJdkPath) {
        if (-not (Validate-Jdk -Path $DesiredJdkPath)) {
            throw "Specified JDK path is not valid: $DesiredJdkPath"
        }
        return [pscustomobject]@{ Path = (Normalize-Path $DesiredJdkPath) }
    }

    if ($SetLatest -or $Candidates.Count -eq 1) {
        return $Candidates[0]
    }

    if ($Candidates.Count -gt 1 -and $Host.UI.RawUI.KeyAvailable -ne $null) {
        Write-DragonGuard 'Multiple JDKs found; selecting the newest available.'
        return $Candidates[0]
    }

    if ($Candidates.Count -gt 0) {
        return $Candidates[0]
    }

    throw 'No valid JDK installations were found.'
}

Write-DragonGuard 'Starting DragonGuard environment validation.'
$candidates = Get-JdkCandidates
if ($candidates.Count -eq 0) {
    throw 'No JDK installations were discovered on this machine.'
}
Write-DragonGuard "Discovered $($candidates.Count) JDK installation(s)."

$target = Resolve-TargetJdk -Candidates $candidates
if (-not $target.Path) {
    throw 'Target JDK resolution failed.'
}

Set-JavaHome -JdkPath $target.Path
Write-DragonGuard "DragonGuard completed. Target JDK: $($target.Path)" 'SUCCESS'
Write-DragonGuard 'Restart any open terminals before running Maven or Java commands.' 'WARN'
