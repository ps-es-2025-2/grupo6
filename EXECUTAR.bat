@echo off
chcp 65001 >nul
echo ========================================
echo    EasyStop - Gestão de Estacionamento
echo ========================================
echo.

echo [1/3] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Java não encontrado!
    echo Por favor, instale o Java JDK 11 ou superior.
    pause
    exit /b 1
)
echo ✓ Java encontrado
echo.

echo [2/3] Compilando projeto...
echo Aguarde, isso pode levar alguns segundos...
echo Compilando enums primeiro...
javac -d . -encoding UTF-8 model/enums/*.java 2>erros.txt
if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilação dos enums! Verifique o arquivo erros.txt
    type erros.txt
    pause
    exit /b 1
)

echo Compilando model...
javac -d . -encoding UTF-8 -cp "." model/*.java model/service/*.java 2>>erros.txt
if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilação do model! Verifique o arquivo erros.txt
    type erros.txt
    pause
    exit /b 1
)

echo Compilando view e components...
javac -d . -encoding UTF-8 -cp "." view/*.java view/components/*.java 2>>erros.txt
if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilação da view! Verifique o arquivo erros.txt
    type erros.txt
    pause
    exit /b 1
)

echo Compilando controllers...
javac -d . -encoding UTF-8 -cp "." controller/*.java 2>>erros.txt
if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilação dos controllers! Verifique o arquivo erros.txt
    type erros.txt
    pause
    exit /b 1
)

echo ✓ Compilação concluída
echo.

echo [3/3] Executando aplicação...
echo.
java -cp "." controller.AppController

if %errorlevel% neq 0 (
    echo.
    echo ERRO ao executar! Verifique:
    echo - JavaFX está instalado?
    echo - Bibliotecas ORMLite estão no classpath?
    echo - Arquivos FXML estão na pasta view/?
    pause
    exit /b 1
)

pause
