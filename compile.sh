#!/bin/bash
set -euo pipefail

##############################
# CONFIGURACIÓN
##############################

# Directorio donde está el JAR compilado
OUT_DIR="out"
JAR_NAME="Compilador.jar"

# Directorio del assembler
ASSEMBLER_DIR="src/Compilador/Assembler"

# Archivos generados
WAT_FILE="$ASSEMBLER_DIR/output.wat"
WASM_FILE="$ASSEMBLER_DIR/output.wasm"

# wat2wasm del sistema
WAT2WASM=${WAT2WASM:-wat2wasm}

##############################
# VALIDACIONES
##############################

if [ $# -lt 1 ]; then
    echo "Uso: $0 <archivo_entrada>"
    exit 1
fi

INPUT_FILE="$1"

if [ ! -f "$OUT_DIR/$JAR_NAME" ]; then
    echo "Error: no existe $OUT_DIR/$JAR_NAME. Ejecutar build.sh primero."
    exit 1
fi

if ! command -v "$WAT2WASM" >/dev/null 2>&1; then
    echo "Error: wat2wasm no está instalado."
    echo "Instalalo con: sudo apt install wabt"
    exit 1
fi

##############################
# 1) EJECUTAR JAR → generar WAT
##############################

echo "Ejecutando compilador sobre: $INPUT_FILE"
java -jar "$OUT_DIR/$JAR_NAME" "$INPUT_FILE"
if [ ! -f "$WAT_FILE" ]; then
    echo "Error: el compilador no generó $WAT_FILE"
    exit 1
fi

echo "WAT generado: $WAT_FILE"

##############################
# 2) CONVERTIR WAT → WASM
##############################

echo "Convirtiendo WAT a WASM..."
"$WAT2WASM" "$WAT_FILE" -o "$WASM_FILE"

echo "WASM generado: $WASM_FILE"
echo "✔ Proceso completo."
