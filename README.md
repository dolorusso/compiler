
# README

Este proyecto contiene un compilador que genera código **WAT (WebAssembly Text Format)** y **WASM (WebAssembly Binary Format)**, junto con herramientas para servir y ejecutar el código generado en un navegador web.
A continuación se describen la estructura del proyecto, los requisitos, los scripts disponibles y el flujo de uso recomendado.

---

## 1. Estructura del proyecto

```
C:.
├── .idea
├── out
│   ├── Compilador
│   └── production
├── Programas
│   ├── pruebasTP1_2
│   └── pruebasTP3-4
└── src
    └── Compilador
        ├── AccionesSemanticas
        ├── Assembler
        ├── ErrorHandler
        ├── Generador
        ├── Lexer
        │   └── Matrices
        └── Parser
```

---

## 2. Requisitos

### Herramientas necesarias

* Java 8 o superior
* bash (Linux o Git Bash en Windows)
* Python 3 (para el servidor HTTP)
* WABT (herramienta que incluye `wat2wasm`)

### Instalación de WABT (Ubuntu/Debian)

```
sudo apt update
sudo apt install wabt
```

O desde el repositorio oficial:
[https://github.com/WebAssembly/wabt](https://github.com/WebAssembly/wabt)

---

## 3. Scripts principales

### build.sh

Compila todos los archivos Java ubicados en `src/` y genera el archivo:

```
out/Compilador.jar
```

### compile.sh

Ejecuta el compilador sobre un archivo de entrada, genera `output.wat` y lo convierte a `output.wasm` utilizando `wat2wasm`.

Los resultados se guardan en:

```
src/Compilador/Assembler/output.wat
src/Compilador/Assembler/output.wasm
```

### serve_wasm.sh

Inicia un servidor web local para visualizar y ejecutar `output.wasm` desde un navegador.

El servidor se levanta sobre:

```
http://localhost:8000/index.html
```

### compile_all.sh

Compila todos los archivos ubicados en `Programas/pruebasTP3-4/`, deteniéndose después de cada uno hasta que el usuario presione ENTER.
Esto permite revisar cada resultado y luego ejecutar el WASM correspondiente en la interfaz web.

---

## 4. Flujo de uso recomendado

El siguiente procedimiento es el adecuado para compilar el proyecto, servir el entorno web y ejecutar los distintos programas de prueba.

### 1. Compilar el compilador

```
./build.sh
```

Esto genera:

```
out/Compilador.jar
```

### 2. Iniciar el servidor donde se ejecutará el WASM

```
./serve_wasm.sh
```

Luego abrir en el navegador (si no se abre automáticamente):

```
http://localhost:8000/index.html
```

En esta página aparecerá el botón **"Ejecutar WASM"**, que se usará más adelante.

### 3. Compilar todos los programas de prueba

```
./compile_all.sh
```

Para cada archivo:

* Se realiza la compilación.
* Se genera `output.wat` y `output.wasm`.
* El script detiene su ejecución esperando que el usuario presione ENTER para continuar con el siguiente.

### 4. Ejecutar cada programa en la interfaz web

Luego de compilar cada programa utilizando `compile_all.sh`, volver a la página:

```
http://localhost:8000/index.html
```

Y presionar el botón:

```
Ejecutar WASM
```

Este botón ejecutará el archivo `output.wasm` recién generado por la compilación.

Repetir el procedimiento para cada test:
Compilar → Cambiar a la pestaña del navegador → Presionar "Ejecutar WASM".

---

## 5. Resumen

1. `./build.sh` — Construye el compilador.
2. `./serve_wasm.sh` — Inicia el entorno web donde se ejecutará el código.
3. `./compile_all.sh` — Compila todos los programas de prueba uno por uno.
4. En la web local, presionar **Ejecutar WASM** después de cada compilación.

---
