@echo off
REM Script: compile_yacc.bat
REM Uso: compile_yacc.bat
REM Compila el parser usando yacc.exe

set YACC_CMD=yacc.exe
set YACC_ARGS=-v -J -Jclass=Parser -Jpackage=Compilador.Parser -Jnorun parser.y

if not exist "%YACC_CMD%" (
    echo Error: No se encontró %YACC_CMD%.
    exit /b 1
)

echo Usando yacc.exe para generar Parser.java...
%YACC_CMD% %YACC_ARGS%

if %ERRORLEVEL%==0 (
    echo Parser generado correctamente.
) else (
    echo Error al ejecutar yacc.
)

pause