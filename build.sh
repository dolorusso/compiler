
#!/bin/bash

# === Configuración ===
SRC_DIR="src"
OUT_DIR="out"
JAR_NAME="Compilador.jar"
MAIN_CLASS="Main"

# === Crear carpeta de salida ===
mkdir -p "$OUT_DIR"

echo "Compilando archivos Java..."
find "$SRC_DIR" -name "*.java" > sources.txt
javac -d "$OUT_DIR" @sources.txt

if [ $? -ne 0 ]; then
    echo "Error de compilacion."
    rm sources.txt
    exit 1
fi

echo "Empaquetando en $JAR_NAME..."
cd "$OUT_DIR" || exit 1
jar cfe "$JAR_NAME" "$MAIN_CLASS" $(find . -type f -name "*.class")
cd - >/dev/null

rm sources.txt
echo "JAR generado: $OUT_DIR/$JAR_NAME"
