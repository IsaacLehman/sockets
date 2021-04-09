@echo off
:: Compiles and runs a .java file

:: Print the available files
echo Available .java Files:
for %%f in ("*.java") do @echo - %%~nf
echo.
echo ----------------------------------------------------------------
:: Get the java file name <file_name>.java
set /p file_name=Please enter the file-name (file-name.java):
echo.
:: create the full file name with ".java" extension
SET full_file_name=%file_name%.java
:: compile and run
javac %full_file_name% && (
  echo %full_file_name% was compiled succesfully.
  echo ----------------------------------------------------------------
  java %file_name%
) || (
  echo.
  echo ERROR: Could not compile %full_file_name%.
)
:: don't close terminal until the user presses a key
pause
