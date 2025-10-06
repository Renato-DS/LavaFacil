@echo off
cd /d "%~dp0"

where mvnd >nul 2>nul
if %errorlevel%==0 (
    mvnd clean package
) else (
    where mvn >nul 2>nul
    if %errorlevel%==0 (
        mvn clean package
    ) else (
        echo Verifique se o Maven está instalado e configurado.
        pause
        exit /b
    )
)

pause
