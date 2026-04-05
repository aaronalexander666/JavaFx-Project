# Hotel Project Metadata & Health Auditor
Write-Host "--- HOTEL PROJECT DIAGNOSTIC START ---" -ForegroundColor Cyan

$srcPath = ".\src\main\java"
$files = Get-ChildItem -Path $srcPath -Filter *.java -Recurse

$results = foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $bytes = [System.IO.File]::ReadAllBytes($file.FullName)

    # 1. Detect Byte Order Mark (BOM) - The "1,1" Error Culprit
    $hasBOM = ($bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF)

    # 2. Detect Illegal Windows Path Escapes
    $hasIllegalEscapes = $content -match '(?<!\\)\\(?!\\|[ntr"''bf0-7u])'

    # 3. Detect Main Class presence
    $isMain = $content -match "public static void main"

    [PSCustomObject]@{
        FileName       = $file.Name
        EncodingIssue  = if ($hasBOM) { "BOM DETECTED (Fix Required)" } else { "Clean" }
        PathEscapes    = if ($hasIllegalEscapes) { "BAD ESCAPES FOUND" } else { "Clean" }
        IsMainClass    = $isMain
        RelativePath   = $file.FullName.Replace((Get-Location).Path, "")
    }
}

$results | Format-Table -AutoSize
Write-Host "--- AUDIT COMPLETE ---" -ForegroundColor Cyan
Write-Host "Actionable Data Collected. Review the table above." -ForegroundColor Yellow