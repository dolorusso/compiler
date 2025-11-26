param([string]$inputFile)

$OUT_DIR = "out"
$JAR_NAME = "Compilador.jar"
$ASSEMBLER_DIR = "src/Compilador/Assembler"

$WAT_FILE = "$ASSEMBLER_DIR/output.wat"
$WASM_FILE = "$ASSEMBLER_DIR/output.wasm"
$WAT2WASM = "$ASSEMBLER_DIR/wat2wasm.exe"
$RUN_WASM_SCRIPT = "$ASSEMBLER_DIR/run_wasm.ps1"

if (-not $inputFile) {
    Write-Host "Uso: .\run.ps1 <archivo_entrada>"
    exit
}

# Verificar jar
if (!(Test-Path "$OUT_DIR/$JAR_NAME")) {
    Write-Host "Error: no existe $OUT_DIR\$JAR_NAME"
    exit
}

# Ejecutar compilador
Write-Host "Ejecutando compilador..."
java -jar "$OUT_DIR/$JAR_NAME" $inputFile

# Verificar que el compilador genero output.wat
if (!(Test-Path $WAT_FILE)) {
    Write-Host "Error: no se genero output.wat"
    exit
}

# Verificar wat2wasm.exe
if (!(Test-Path $WAT2WASM)) {
    Write-Host "Error: no existe wat2wasm.exe en $ASSEMBLER_DIR"
    exit
}

# Convertir WAT a WASM
Write-Host "Convirtiendo WAT a WASM..."
& $WAT2WASM $WAT_FILE -o $WASM_FILE

if (!(Test-Path $WASM_FILE)) {
    Write-Host "Error: no se genero output.wasm"
    exit
}

# Ejecutar pagina
Write-Host "Ejecutando entorno WASM en el navegador..."
powershell -ExecutionPolicy Bypass -File $RUN_WASM_SCRIPT (Split-Path $WASM_FILE -Leaf)
