call gradlew build
if "%ERRORLEVEL%" == "0" goto start
echo.
echo GRANDLEW BUILD has errors - breaking work
goto fail

:start
start chrome "http://localhost:8888/tasks_frontend/"
goto end

:fail
echo.
echo There were errors.
goto end

:end
echo.
echo Work is end.
