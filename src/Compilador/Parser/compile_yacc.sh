#!/bin/bash

# Script: compile_yacc.sh
# Uso: ./compile_yacc.sh
# Compila el parser con el yacc correspondiente al SO Linux

YACC_CMD="./yacc.linux"
YACC_ARGS="-v -J -Jclass=Parser -Jpackage=Compilador.Parser -Jnorun parser.y"

if [ ! -f "$YACC_CMD" ]; then
    echo "Error: No se encontró $YACC_CMD"
    exit 1
fi

echo "Usando yacc.linux para generar Parser.java..."
$YACC_CMD $YACC_ARGS

if [ $? -eq 0 ]; then
    echo "Parser generado correctamente."
else
    echo "Error al ejecutar yacc."
fi
