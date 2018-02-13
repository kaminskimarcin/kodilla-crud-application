call runcrud.bat
if "%ERRORLEVEL%" == "0" goto callBrowser
echo.
echo Fail with runcrud script! Check it out.
goto fail

:callBrowser
start firefox.exe http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.heroku
echo Could't open browser.
goto fail

:fail
echo.
echo Breaking script. Fail.

:end
echo.
echo Everything goes well.