#!/bin/bash
#####   USO chmod +x run_wasm.sh    #####
#####  ./run_wasm.sh programa.wasm  #####

#### Requerimientos: Python
FILE="$1"

if [ -z "$FILE" ]; then
  echo "Uso: ./run_wasm.sh archivo.wasm"
  exit 1
fi

echo "Iniciando servidor local..."
python3 -m http.server 8000 &

SERVER_PID=$!
sleep 1

xdg-open "http://localhost:8000/index.html?file=$FILE"

wait $SERVER_PID
