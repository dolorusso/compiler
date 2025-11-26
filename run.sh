#!/bin/bash

# Directorio donde se genera el jar
OUT_DIR="out"
JAR_NAME="Compilador.jar"

# Directorio donde se encuentra el assembler y los archivos generados
ASSEMBLER_DIR="src/Compilador/Assembler"

# Archivos generados por el compilador
WAT_FILE="$ASSEMBLER_DIR/output.wat"
WASM_FILE="$ASSEMBLER_DIR/output.wasm"

# wat2wasm del sistema
WAT2WASM="wat2wasm"

# Script que ejecuta la pagina en el navegador
RUN_WASM_SCRIPT="$ASSEMBLER_DIR/run_wasm.sh"

# Verificar parametros
if [ $# -lt 1 ]; then
    echo "Uso: $0 <archivo_entrada>"
    exit 1
fi

INPUT_FILE="$1"

# Verificar existencia del jar
if [ ! -f "$OUT_DIR/$JAR_NAME" ]; then
    echo "Error: no existe $OUT_DIR/$JAR_NAME. Ejecutar build.sh primero."
    exit 1
fi

# Ejecutar compilador
echo "Ejecutando compilador con entrada: $INPUT_FILE"
java -jar "$OUT_DIR/$JAR_NAME" "$INPUT_FILE"
if [ $? -ne 0 ]; then
    echo "Error: el compilador produjo un error."
    exit 1
fi

# Verificar que se generó output.wat
if [ ! -f "$WAT_FILE" ]; then
    echo "Error: no se generó $WAT_FILE."
    exit 1
fi

# Verificar que wat2wasm está instalado
if ! command -v $WAT2WASM &> /dev/null; then
    echo "Error: wat2wasm no está instalado."
    echo "Instálalo con: sudo apt install wabt"
    exit 1
fi

# Convertir output.wat a output.wasm
echo "Convirtiendo WAT a WASM..."
"$WAT2WASM" "$WAT_FILE" -o "$WASM_FILE"
if [ $? -ne 0 ]; then
    echo "Error: falló la conversión con wat2wasm."
    exit 1
fi

echo "Se generó el archivo: $WASM_FILE"

# Ejecutar el script que abre la página web
if [ ! -f "$RUN_WASM_SCRIPT" ]; then
    echo "Error: no existe $RUN_WASM_SCRIPT."
    exit 1
fi

echo "Ejecutando entorno WASM en el navegador..."
bash "$RUN_WASM_SCRIPT" "$(basename "$WASM_FILE")"
