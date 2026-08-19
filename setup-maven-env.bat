@echo off
REM Set Maven environment permanently
setlocal enabledelayedexpansion

set MAVEN_HOME=%USERPROFILE%\.maven\apache-maven-3.9.6

REM Add to system PATH (requires admin)
echo Setting up Maven environment variables...
setx MAVEN_HOME %MAVEN_HOME%
setx Path "%MAVEN_HOME%\bin;%Path%"

echo.
echo Maven environment configured!
echo MAVEN_HOME = %MAVEN_HOME%
echo.
echo Testing Maven installation...
"%MAVEN_HOME%\bin\mvn.cmd" --version

echo.
echo ===================================
echo Setup complete! Restart PowerShell
echo and run: mvn --version
echo ===================================
pause
