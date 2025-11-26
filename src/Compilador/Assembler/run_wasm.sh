#!/bin/bash
#####   USO chmod +x run_wasm.sh    #####
#####  ./run_wasm.sh output.wasm    #####

FILE="$1"

if [ -z "$FILE" ]; then
  echo "Uso: ./run_wasm.sh archivo.wasm"
  exit 1
fi

# Carpeta donde está este script (equivalente a $root en PowerShell)
ROOT="$(cd "$(dirname "$0")" && pwd)"

# Ruta absoluta al wasm
WASM_PATH="$ROOT/$FILE"

if [ ! -f "$WASM_PATH" ]; then
  echo "El archivo WASM no existe: $WASM_PATH"
  exit 1
fi

echo "Iniciando servidor en $ROOT"

# Levanta servidor en la carpeta Assembler
(cd "$ROOT" && python3 -m http.server 8000) &
SERVER_PID=$!

sleep 1

# Abrir navegador (Linux)
xdg-open "http://localhost:8000/index.html"

# Esperar al servidor
wait $SERVER_PID
