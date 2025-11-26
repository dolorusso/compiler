#!/bin/bash

# Directorio donde se genera el jar
OUT_DIR="out"
JAR_NAME="Compilador.jar"

# Directorio donde se encuentra el assembler y los archivos generados
ASSEMBLER_DIR="src/Compilador/Assembler"

# Archivos generados por el compilador
WAT_FILE="$ASSEMBLER_DIR/output.wat"
WASM_FILE="$ASSEMBLER_DIR/output.wasm"

# Binario local de wat2wasm (version Linux)
WAT2WASM="$ASSEMBLER_DIR/wat2wasm-linux"

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

# Verificar que se genero output.wat
if [ ! -f "$WAT_FILE" ]; then
    echo "Error: no se genero $WAT_FILE."
    exit 1
fi

# Verificar que wat2wasm existe y es ejecutable
if [ ! -x "$WAT2WASM" ]; then
    echo "Error: no se encontro un wat2wasm valido en:"
    echo "  $WAT2WASM"
    echo "Debe existir y tener permisos de ejecucion."
    exit 1
fi

# Convertir output.wat a output.wasm
echo "Convirtiendo WAT a WASM..."
"$WAT2WASM" "$WAT_FILE" -o "$WASM_FILE"
if [ $? -ne 0 ]; then
    echo "Error: fallo la conversion con wat2wasm."
    exit 1
fi

echo "Se genero el archivo: $WASM_FILE"

# Ejecutar el script que abre la pagina web
if [ ! -f "$RUN_WASM_SCRIPT" ]; then
    echo "Error: no existe $RUN_WASM_SCRIPT."
    exit 1
fi

echo "Ejecutando entorno WASM en el navegador..."
bash "$RUN_WASM_SCRIPT" "$(basename "$WASM_FILE")"
