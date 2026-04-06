<#
.SYNOPSIS
Normalize and repair Java path entries in a Windows PATH string.
.DESCRIPTION
This utility can be used by launch scripts to ensure the resolved Java bin path is valid.
#>
function Normalize-LaunchPath {
    param([string]$PathEntry)
    if ([string]::IsNullOrWhiteSpace($PathEntry)) { return $null }
    $clean = $PathEntry.Trim().Trim('"')
    try {
        return (Resolve-Path -Path $clean -ErrorAction Stop).Path
    } catch {
        return $clean
    }
}

function Join-UniquePathEntries {
    param([string[]]$Entries)
    $seen = [System.Collections.Generic.HashSet[string]]::new([System.StringComparer]::OrdinalIgnoreCase)
    $result = [System.Collections.Generic.List[string]]::new()

    foreach ($entry in $Entries) {
        if ([string]::IsNullOrWhiteSpace($entry)) { continue }
        $normalized = $entry.Trim()
        if ($seen.Add($normalized)) { $result.Add($normalized) }
    }

    return $result -join ';'
}
