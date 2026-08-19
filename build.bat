@echo off
cd /d "C:\Users\toroi\OneDrive\Desktop\Backroad-CBR"
set MAVEN_HOME=%USERPROFILE%\.maven\apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%

echo Checking Maven installation...
if exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Maven found at: %MAVEN_HOME%
    echo.
    echo Running Maven version check...
    call mvn --version
    echo.
    echo Building project...
    call mvn clean install -q
    echo Build completed!
) else (
    echo Maven not found at: %MAVEN_HOME%
    echo Please ensure Maven was properly extracted.
)
