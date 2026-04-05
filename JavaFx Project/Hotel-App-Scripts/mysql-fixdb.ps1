Write-Host "--- MySQL Health Check & Repair ---" -ForegroundColor Cyan

# 1. Check if MySQL Service exists
$service = Get-Service -Name "MySQL*" -ErrorAction SilentlyContinue

if ($service) {
    Write-Host "Found MySQL Service: $($service.Name)" -ForegroundColor Green
    
    if ($service.Status -ne 'Running') {
        Write-Host "Service is $($service.Status). Attempting to start..." -ForegroundColor Yellow
        Start-Service $service.Name
        Start-Sleep -Seconds 5
        $newStatus = (Get-Service $service.Name).Status
        Write-Host "New Status: $newStatus" -ForegroundColor Green
    } else {
        Write-Host "Service is already running." -ForegroundColor Green
    }
} else {
    Write-Host "CRITICAL: No MySQL service found! You may need to install MySQL Server." -ForegroundColor Red
}

# 2. Check if Port 3306 is listening
Write-Host "Checking Port 3306 (The Database Door)..." -ForegroundColor Cyan
$portCheck = Test-NetConnection -ComputerName localhost -Port 3306 -InformationLevel Quiet

if ($portCheck) {
    Write-Host "SUCCESS: Port 3306 is open. Your database is alive!" -ForegroundColor Green
} else {
    Write-Host "FAILURE: Port 3306 is closed. Workbench cannot connect." -ForegroundColor Red
}

# 3. Check for XAMPP/WAMP (Alternative MySQL hosts)
if (Test-Path "C:\xampp\mysql\bin\mysqld.exe") {
    Write-Host "Notice: XAMPP detected. Ensure you click 'Start' in the XAMPP Control Panel." -ForegroundColor Yellow
}

Write-Host "--- Diagnostics Complete ---" -ForegroundColor Cyan