@echo off
REM Maven Installation Script

setlocal enabledelayedexpansion

REM Check if Maven directory exists
if exist "%USERPROFILE%\.maven\apache-maven-3.9.6\bin\mvn.cmd" (
    echo Maven already installed
    set MAVEN_BIN=%USERPROFILE%\.maven\apache-maven-3.9.6\bin
    goto test_maven
)

echo Maven installation not found
goto end

:test_maven
echo.
echo Testing Maven installation...
"%MAVEN_BIN%\mvn.cmd" --version
goto end

:end
echo.
echo Setup complete.
pause
