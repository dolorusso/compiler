#!/bin/bash
set -euo pipefail

# Configuración
ASSEMBLER_DIR="src/Compilador/Assembler"
PORT=8000
ROOT="$(cd "$(dirname "$0")" && cd "$ASSEMBLER_DIR" && pwd)"  # carpeta donde está index.html
PID_FILE="$ROOT/.wasm_server.pid"

# Si se pasa un wasm como argumento, lo copia al ROOT (opcional)
WASM_ARG="$1"

if [ -n "$WASM_ARG" ] && [ -f "$WASM_ARG" ]; then
  BASENAME="$(basename "$WASM_ARG")"
  echo "Copiando $WASM_ARG -> $ROOT/$BASENAME"
  cp "$WASM_ARG" "$ROOT/$BASENAME"
fi

# Compruebo si ya está levantado el servidor en el puerto
if curl -s --head "http://localhost:$PORT" >/dev/null 2>&1; then
  echo "Servidor ya corriendo en http://localhost:$PORT"
  # si hay PID file lo muestro
  if [ -f "$PID_FILE" ]; then
    echo "PID guardado en $PID_FILE: $(cat "$PID_FILE")"
  fi
else
  echo "Iniciando servidor en $ROOT (puerto $PORT)"
  (cd "$ROOT" && python3 -m http.server "$PORT" >/dev/null 2>&1 &)
  SERVER_PID=$!
  # guardo pid para poder detenerlo después
  echo "$SERVER_PID" > "$PID_FILE"
  echo "Servidor iniciado (PID $SERVER_PID)"
  # le doy un breve tiempo para que arranque
  sleep 0.5
fi

# Abrir navegador (Linux)
xdg-open "http://localhost:$PORT/index.html" >/dev/null 2>&1 || echo "Abre manualmente: http://localhost:$PORT/index.html"
