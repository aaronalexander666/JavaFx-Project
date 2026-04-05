# 1. Update this to your ACTUAL JDK 23 folder name
# Check C:\Program Files\Java to be sure of the name!
$JAVA_HOME = "C:\Program Files\Java\jdk-23" 

$MAVEN_HOME = "C:\Program Files\Apache\maven"
$JAVA_EXE = "$JAVA_HOME\bin\java.exe"
$CLASS_PATH = (Get-ChildItem "$MAVEN_HOME\boot\plexus-classworlds-*.jar").FullName

Write-Host "Verifying Java Version..." -ForegroundColor Cyan
& $JAVA_EXE -version

Write-Host "Launching Hotel System with JDK 23..." -ForegroundColor Cyan

# 2. Execute
& $JAVA_EXE -classpath "`"$CLASS_PATH`"" `
    "-Dclassworlds.conf=$MAVEN_HOME\bin\m2.conf" `
    "-Dmaven.home=$MAVEN_HOME" `
    "-Dmaven.multiModuleProjectDirectory=$PWD" `
    org.codehaus.plexus.classworlds.launcher.Launcher clean javafx:run