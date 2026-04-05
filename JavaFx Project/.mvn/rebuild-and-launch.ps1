$JAVA_HOME = "C:\Program Files\Java\jdk-23" 
$MAVEN_HOME = "C:\Program Files\Apache\maven"
$JAVA_EXE = "$JAVA_HOME\bin\java.exe"
$CLASS_PATH = (Get-ChildItem "$MAVEN_HOME\boot\plexus-classworlds-*.jar").FullName

Write-Host "Rebuilding with Official JDK 23 / JavaFX 23 Support..." -ForegroundColor Cyan

& $JAVA_EXE -classpath "`"$CLASS_PATH`"" `
    "-Dclassworlds.conf=$MAVEN_HOME\bin\m2.conf" `
    "-Dmaven.home=$MAVEN_HOME" `
    "-Dmaven.multiModuleProjectDirectory=$PWD" `
    org.codehaus.plexus.classworlds.launcher.Launcher clean javafx:run