@echo off
setlocal
set "PSModulePath="
powershell.exe -NoProfile -ExecutionPolicy RemoteSigned -File "%~dp0iniciar-local.ps1" %*
exit /b %ERRORLEVEL%
