#!/bin/bash

DIRECTORIO="Programas/pruebasTP3-4"

# Recorre todos los archivos del directorio
for PROG in "$DIRECTORIO"/*; do
    # Salto de línea para separar bien las salidas
    echo "==========================================="
    echo "Compilando: $PROG"
    echo "==========================================="

    ./compile.sh "$PROG"

    echo
    read -p "Presioná ENTER para continuar con el siguiente..."
    echo
done
