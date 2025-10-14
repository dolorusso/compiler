# Compilador

Este proyecto forma parte de los Trabajos Prácticos 1 y 2 de la materia **Compiladores e Intérpretes**.  
El objetivo fue implementar un compilador funcional que realice las etapas de análisis léxico y sintáctico, detecte errores, maneje una tabla de símbolos y procese un lenguaje diseñado por el grupo.

---

## Descripción General

El compilador está desarrollado en **Java** e incluye las siguientes fases principales:

### Analizador Léxico (`AnalizadorLexico`)
- Implementa un autómata finito determinista que reconoce los tokens del lenguaje.
- Utiliza matrices de transición y acciones semánticas para procesar cada carácter y construir los lexemas.

### Analizador Sintáctico (`Parser`)
- Generado con YACC para Java.
- Se encarga de validar la estructura gramatical del programa y resolver las reglas del lenguaje definido.

### Manejo de Errores y Warnings (`ErrorManager`)
- Clase encargada de registrar y mostrar todos los mensajes de error o advertencia detectados durante la compilación, tanto en el léxico como en el parser.

### Tabla de Símbolos
- Gestiona los lexemas reconocidos, sus atributos (tipo, uso, valor, referencias) y permite acceder a ellos durante todo el proceso de compilación.


---

## Ejecución del Compilador

### 1. Dar permisos de ejecución a los scripts

Antes de compilar o ejecutar el compilador, asegurate de tener permisos de ejecución en ambos scripts.  
En la raíz del proyecto, corré:

```bash
chmod +x build.sh
chmod +x run.sh
```
### 2. Compilar el proyecto

Ejecutá el script `build.sh`, que se encarga de compilar todas las clases necesarias y generar los archivos intermedios:

```bash
./build.sh
```

### 3. Ejecutar el compilador

Una vez compilado, podés ejecutar el compilador pasando como argumento un archivo de entrada con el código fuente a analizar (en la carpeta /Programas se encuentran varios archivos de prueba):
```bash
./run.sh Programas/pruebaLexico.txt
```
El analizador procesará el archivo, mostrando por consola los tokens reconocidos, los errores, las advertencias y los resultados del análisis.

---

## Detalles Técnicos Relevantes

- **Gestión de errores y warnings:**  
  Implementación de `ErrorManager` para centralizar la captura y visualización de todos los errores y advertencias del compilador.

- **Patrón Singleton:**  
  Uso del patrón **Singleton** en el analizador léxico para asegurar que solo exista una instancia durante todo el proceso.

- **Construcción de lexemas eficiente:**  
  Uso de `StringBuilder` para la creación y manipulación rápida de lexemas durante el análisis léxico.

- **Lectura desacoplada del código fuente:**  
  Implementación de la interfaz `MyReader` para separar la lógica de lectura del archivo del resto del compilador.

- **Acciones semánticas reutilizables:**  
  Definición de acciones semánticas que pueden aplicarse a distintos tipos de tokens sin duplicar código.

- **Manejo especial de casos complejos:**  
  Consideración de constantes negativas, tokens inválidos e identificadores largos.

- **Gramática modular y segura:**  
  Estructura de la gramática diseñada para evitar conflictos **shift–reduce** y **reduce–reduce**.

