@echo off
cd /d "%~dp0"

if not exist "target" (
    echo Certifique-se de que o projeto foi compilado antes de executar.
    pause
    exit /b
)

cd target
echo Iniciando LavaFacil...
java -jar "LavaFacil-2.0.jar"
pause
