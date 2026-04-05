function Invoke-Launch {
    param($Build)

    Write-Host "Launching JavaFX application..." -ForegroundColor Magenta

    $cp = $Build.OutDir + ";" + $Build.MysqlJar

    $RunParams = @(
        "--module-path", $Build.FxLib,
        "--add-modules", "javafx.controls,javafx.fxml",
        "-cp", $cp,
        $Build.MainClass
    )

    & $Build.JavaPath @RunParams

    if ($LASTEXITCODE -ne 0) {
        throw "Application exited with code $LASTEXITCODE"
    }
}
