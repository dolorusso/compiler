param([string]$file)

if (-not $file) {
    Write-Host "Uso: .\run_wasm.ps1 archivo.wasm"
    exit
}

# Carpeta donde está este script (Assembler)
$root = Split-Path -Parent $MyInvocation.MyCommand.Path

# Ruta absoluta al archivo wasm dentro de la misma carpeta
$wasmPath = Join-Path $root $file

if (-not (Test-Path $wasmPath)) {
    Write-Host "El archivo WASM no existe: $wasmPath"
    exit
}

Write-Host "Iniciando servidor en $root"

# Levanta el servidor desde Assembler
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd `"$root`"; python -m http.server"

# Abrir navegador
Start-Process "http://localhost:8000/index.html"
