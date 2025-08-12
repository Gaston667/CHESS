@echo off
setlocal EnableExtensions EnableDelayedExpansion
pushd "%~dp0"

:: === CONFIGURATION ===
set "OUT=out"
set "MAIN_CLASS=Main"   :: Si Main est dans un package, ex: monpackage.Main
:: =====================

if "%~1"=="" goto :help

if /I "%~1"=="help" goto :help
if /I "%~1"=="clean" goto :clean
if /I "%~1"=="build" goto :build
if /I "%~1"=="run" goto :run
if /I "%~1"=="count" goto :count
if /I "%~1"=="watch" goto :watch
if /I "%~1"=="purge" goto :purge
if /I "%~1"=="rebuild" goto :rebuild

goto :help

:clean
    if exist "%OUT%" (
        echo Suppression du dossier "%OUT%"...
        rmdir /S /Q "%OUT%"
    ) else (
        echo Aucun dossier "%OUT%" a supprimer.
    )
    goto :eof



:build
    where javac >nul 2>nul || (echo([ERREUR] javac introuvable dans le PATH & goto :eof)

    if not exist "%OUT%" mkdir "%OUT%"

    echo(Creation de la liste des sources...
    dir /s /b *.java > "%TEMP%\sources.txt"

    for /f %%I in ('find /c /v "" ^< "%TEMP%\sources.txt"') do set COUNT=%%I
    if "%COUNT%"=="0" (
        echo([ERREUR] Aucun fichier .java trouve dans %cd%
        del "%TEMP%\sources.txt" >nul 2>nul
        goto :eof
    )

    echo(%COUNT% fichiers trouves. Compilation...
    javac -d "%OUT%" @"%TEMP%\sources.txt"
    set ERR=%ERRORLEVEL%
    del "%TEMP%\sources.txt" >nul 2>nul

    if %ERR% EQU 0 (
        echo(Compilation reussie !
    ) else (
        echo([ERREUR] Compilation (code %ERR%)
    )
    goto :eof
 




:run
    call :build
    if %ERR% EQU 0 (
        echo(
        echo(Execution de %MAIN_CLASS% ...
        java -cp "%OUT%" %MAIN_CLASS%
    )
    goto :eof


:count
    for /f %%I in ('dir /s /b *.java ^| find /c /v ""') do set COUNT=%%I
    echo %COUNT% fichiers Java trouves.
    
    :: Compte le nombre de ligne des fichier java
    set /a LIGNES=0
    for /f "delims=" %%F in ('dir /s /b *.java') do (
        for /f %%N in ('type "%%F" ^| find /v /c ""') do set /a LIGNES+=%%N
    )
    echo %LIGNES% lignes Java trouvees.
    goto :eof


:watch
    echo Mode surveillance active : recompilation a chaque changement detecte
    echo Appuie sur Ctrl+C pour quitter.
    set "LASTSTATE="
    :loop
        for /f %%A in ('dir /s /b /a-d *.java') do (
            set "CURRENTSTATE=!CURRENTSTATE!%%~tA"
        )
        if "!CURRENTSTATE!" NEQ "!LASTSTATE!" (
            set "LASTSTATE=!CURRENTSTATE!"
            call :build
        )
        set "CURRENTSTATE="
        timeout /t 2 >nul
        goto loop

:purge
    echo Suppression des .class hors de "%OUT%"...
    for /f "delims=" %%C in ('dir /s /b *.class ^| findstr /vi /c:"\%OUT%\"') do del /q "%%C"
    goto :eof

:rebuild
    call :clean
    call :purge
    call :build
    goto :eof


:help
    echo Usage: build.bat [option]
    echo.
    echo   build   - Compile le projet
    echo   clean   - Supprime le dossier de sortie "%OUT%"
    echo   run     - Compile puis lance %MAIN_CLASS%
    echo   count   - Compte le nombre de fichiers Java
    echo   watch   - Recompile automatiquement si un fichier change
    echo   help    - Affiche cette aide
    echo   purge   - Supprime les .class hors de "%OUT%"
    echo   rebuild - Nettoie, purge et recompile le projet
    goto :eof
