# === Configuración ===
$SRC_DIR = "src"
$OUT_DIR = "out"
$JAR_NAME = "Compilador.jar"
$MAIN_CLASS = "Main"

# === Crear carpeta de salida ===
if (-not (Test-Path $OUT_DIR)) {
    New-Item -ItemType Directory -Path $OUT_DIR | Out-Null
}

Write-Host "Compilando archivos Java..."

# Obtener lista de archivos .java
$sourceFiles = Get-ChildItem -Recurse -Filter *.java -Path $SRC_DIR | Select-Object -ExpandProperty FullName

if ($sourceFiles.Count -eq 0) {
    Write-Host "No se encontraron archivos .java en $SRC_DIR"
    exit 1
}

# Compilar
javac -d $OUT_DIR $sourceFiles

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error de compilación."
    exit 1
}

Write-Host "Empaquetando en $JAR_NAME..."

Push-Location $OUT_DIR

# Buscar todas las .class dentro de out/
$classFiles = Get-ChildItem -Recurse -Filter *.class | ForEach-Object { $_.FullName.Replace((Get-Location).Path + "\", "") }

jar cfe $JAR_NAME $MAIN_CLASS $classFiles

Pop-Location

Write-Host "JAR generado: $OUT_DIR\$JAR_NAME"
