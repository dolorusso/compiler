#!/bin/bash
set -euo pipefail

################################
# CONFIGURACIÓN
################################
ASSEMBLER_DIR="src/Compilador/Assembler"
PORT=8000

# Carpeta donde está index.html y output.wasm
ROOT="$(cd "$(dirname "$0")" && cd "$ASSEMBLER_DIR" && pwd)"
PID_FILE="$ROOT/.wasm_server.pid"

################################
# VERIFICAR QUE EXISTE output.wasm
################################
if [ ! -f "$ROOT/output.wasm" ]; then
  echo "Error: no existe $ROOT/output.wasm"
  echo "Ejecutá primero: ./compile_to_wasm.sh <input>"
  exit 1
fi

################################
# VER SI YA HAY SERVIDOR
################################
if curl -s --head "http://localhost:$PORT" >/dev/null 2>&1; then
  echo "Servidor ya corriendo en http://localhost:$PORT"
else
  echo "Iniciando servidor en $ROOT (puerto $PORT)..."

  cd "$ROOT"
  python3 -m http.server "$PORT" >/dev/null 2>&1 &
  SERVER_PID=$!

  echo "$SERVER_PID" > "$PID_FILE"
  echo "Servidor iniciado (PID $SERVER_PID)"
  sleep 0.5
fi

################################
# ABRIR NAVEGADOR
################################
xdg-open "http://localhost:$PORT/index.html" >/dev/null 2>&1 || \
echo "Abrí manualmente: http://localhost:$PORT/index.html"
