#!/bin/bash

OUT_DIR="out"
JAR_NAME="Compilador.jar"

if [ $# -lt 1 ]; then
    echo "Uso: $0 <ruta_archivo_entrada>"
    echo "Ejemplo: ./run.sh Programas/pruebaLexico.txt"
    exit 1
fi

INPUT_FILE="$1"

if [ ! -f "$OUT_DIR/$JAR_NAME" ]; then
    echo "❌ No se encontró $OUT_DIR/$JAR_NAME. Ejecuta primero build.sh."
    exit 1
fi

echo "Ejecutando JAR con archivo: $INPUT_FILE"
java -jar "$OUT_DIR/$JAR_NAME" "$INPUT_FILE"
