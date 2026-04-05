# Hotel Project Surgical Fixer
Write-Host "--- STARTING SURGICAL REPAIR ---" -ForegroundColor Cyan

$srcRoot = ".\src\main\java"

# 1. Fix Illegal Escapes in CustomerDashboardController
$targetFile = "$srcRoot\UI\UI-Controllers\com.hotel.ui\CustomerDashboardController.java"
if (Test-Path $targetFile) {
    $content = Get-Content $targetFile -Raw
    # Replaces single backslashes (not followed by another backslash) with double backslashes
    $fixedContent = $content -replace '(?<!\\)\\(?!\\|[ntr"''bf0-7u])', '\\'
    Set-Content $targetFile $fixedContent -Encoding UTF8
    Write-Host "[FIXED] Illegal escapes in CustomerDashboardController." -ForegroundColor Green
}

# 2. Re-align Package Structure (Optional but recommended for Maven)
# Note: If your code says 'package com.hotel.ui;', the file MUST be in com/hotel/ui/
# For now, we will fix the common 'copy-paste' artifact at the top of files.

$allFiles = Get-ChildItem -Path $srcRoot -Filter *.java -Recurse
foreach ($file in $allFiles) {
    $lines = Get-Content $file.FullName

    # Remove any non-Java characters or "Echo" artifacts at the very top
    if ($lines.Count -gt 0 -and $lines[0] -notmatch 'package|import|/\*|\/\/|public|class') {
        $cleanLines = $lines[1..($lines.Count - 1)]
        $cleanLines | Set-Content $file.FullName -Encoding UTF8
        Write-Host "[CLEANED] Header artifacts removed from $($file.Name)" -ForegroundColor Yellow
    }
}

# 3. Kill the "Echo" Mystery (Maven Force-Run)
Write-Host "Attempting Clean Compile via Direct CMD..." -ForegroundColor Cyan
& cmd.exe /c "mvn clean compile -Dexec.skip=true"

Write-Host "--- REPAIR ATTEMPT COMPLETE ---" -ForegroundColor Cyan