%token ID IF ELSE ENDIF PRINT RETURN LAMBDA ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO
%token FLECHA LONG DO UNTIL TRUNC CR STRING IDCOMP CADENASTR


%{
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
    import Compilador.Lexer.Atributo;
    import Compilador.Generador.Generador;
    import Compilador.Assembler.Traductor;
%}

%start programa

%type <sval> constante factor termino expresion parametro_real cuerpo_expresion llamada_funcion inicio_lambda comparador
%type <sval>  lambda cuerpo_print ids inicio_programa sentencia_IF
%token <sval> CTEL CTEF INVALID ID IDCOMP CADENASTR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO '>' '<'

%type <ival> lista_identificadores tipo inicio_if else_opt inicio_else condicional_opt condicion inicio_do
%token <ival> STRING LONG

%%

programa    
	: inicio_programa lista_sentencias '}'
		{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
		    generador.agregarTerceto("fin_main", $1, "-");
		}
	| inicio_programa error '}'
		{ errManager.error("Programa con error detectado", al.getLine()); }
	| inicio_programa error
	    { errManager.error("Programa con error detectado", al.getLine()); }
    | inicio_programa lista_sentencias '}' error
        {
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
    | '{' lista_sentencias '}'
        { errManager.error("Falta identificador del programa", al.getLine()); }
    | ID lista_sentencias '}'
        { errManager.error("Falta apertura del programa '{'", al.getLine()); }
    | inicio_programa lista_sentencias
        { errManager.error("Falta cierre del programa '{'", al.getLine()); }
    | ID lista_sentencias
        { errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
	| inicio_programa '}'
	    { errManager.error("Programa vacio.", al.getLine()); }
	| '{' '}'
        { errManager.error("Falta identificador del programa", al.getLine()); }
	| ID
	    { errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
	| error
	    { errManager.error("Falta identificador del programa", al.getLine()); }
	;

inicio_programa
    : ID '{'
        {
            generador.enterScope($1);
            generador.agregarTerceto("inicio_main", $1, "-");
            $$ = $1;
        }
    ;

sentencia
	: sentencia_declarativa
	| sentencia_ejecutable
	;

sentencia_declarativa
	: lista_identificadores ';'
	| lista_identificadores error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
	| declaracion_funcion
	;

sentencia_ejecutable
	: asignacion
	| control
	| llamada_funcion ';'
	    { generador.agregarTerceto("drop", "-", "-"); }
	| print ';'
	| retorno ';'
	    {
	        String scope = generador.getCurrentScope();
	        Atributo func = al.ts.obtener(scope);
	        if (func != null && func.uso == Atributo.USO_FUNCION) {
	            func.tieneReturn = true;
	        } else {
	            errManager.error("Return en lugar invalido.", al.getLine());
	        }

	    }
	| asignacion_multiple
    | llamada_funcion error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
    | print error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
    | retorno error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
	;

declaracion_funcion
	: inicio_funcion cuerpo_funcion_opt
	    {
	        String scope = generador.getCurrentScope();
            Atributo func = al.ts.obtener(scope);
            if (func.tieneReturn){
                errManager.debug("Declaracion de funcion detectada.", al.getLine());
                generador.agregarTerceto("fin_funcion", scope, "-");
	        } else {
	            errManager.error("Falta sentencia return.", al.getLine());
	        }

	        generador.exitScope();
	    }
    ;

inicio_funcion
    : tipo ID parametros_formales_opt
        {
            // Entramos al scope de la funcion.
            generador.enterScope($2);
            // Checkeamos si esta declarada usando el scope, ya que este sera el mangle name de la funcion.
            String ambitoFuncion = generador.getCurrentScope();
            if (generador.estaDeclarada(ambitoFuncion, al.ts)){
                errManager.error("Redeclaracion de funcion no permitida.", al.getLine());
            } else {
                // Generamos las entradas solo si es correcta.
                generador.aplicarAmbito(al.ts,$1, ambitoFuncion);

                //Generamos el terceto correspondiente al inicio de la a funcion.
                generador.agregarTerceto("inicio_funcion", ambitoFuncion, "-", $1);
            }
        }
    | ID parametros_formales_opt
        { errManager.error("Funcion sin tipo.", al.getLine()); }
    | tipo parametros_formales_opt
        { errManager.error("Funcion sin nombre.", al.getLine()); }
    | tipo IDCOMP parametros_formales_opt
        { errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
    ;

parametros_formales_opt
    : '(' lista_parametros_formales ')'
    | '('  ')'  { errManager.error("Parametros formales faltantes", al.getLine()); }
    ;

cuerpo_funcion_opt
    : '{' lista_sentencias '}'
    | '{' '}' { errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
    | { errManager.error("Faltan llaves de la funcion", al.getLine()); }
    ;


lista_sentencias
	: sentencia
	| lista_sentencias sentencia
	| error ';'
        { errManager.error("Sentencia invalida.", al.getLine()); }
    ;

lista_sentencias_ejecutables
	: sentencia_ejecutable
	| lista_sentencias_ejecutables sentencia_ejecutable
	;

lista_parametros_formales
	: parametro_formal
	| lista_parametros_formales ',' parametro_formal
	| lista_parametros_formales parametro_formal
	;

lista_parametros_reales
	: parametro_real_compuesto
	| lista_parametros_reales ',' parametro_real_compuesto
	| lista_parametros_reales parametro_real_compuesto
	    { errManager.error("Falta separador de parametros ','", al.getLine()); }
	;

parametro_formal
	: CR tipo ID
	    {
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,$2,$3);
	    }
	| CR ID
	    { errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
	| CR tipo error
	    { errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
	| tipo ID
	    {
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,$1,$2);
	    }
	| ID
	    { errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
	| LAMBDA ID
	    {
	        errManager.debug("Parametro formal lambda semantica detectado", al.getLine());
	        generador.agregarParametro(false,Atributo.lambdaType,$2);
	    }
	| CR LAMBDA ID
	    { errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
	| CR error
	    { errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
	| tipo error
	    { errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
	;

lista_identificadores
	: tipo IDCOMP
	    {
	        declararVariable($2,$1);
            $$ = $1;
	    }
	| lista_identificadores ',' IDCOMP
	    {
	        declararVariable($3,$1);
	    }
	| lista_identificadores IDCOMP
	    { errManager.error("Falta separador de variable ','", al.getLine()); }
	| ID
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	| tipo ID
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	| lista_identificadores ',' ID
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	| lista_identificadores ID
	    { errManager.error("Falta separador de variable ','", al.getLine()); }
	;



parametro_real_compuesto
	: parametro_real FLECHA ID
	    {
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal($1, $3);
	    }
	| parametro_real error
	    {
            errManager.error("Parametro real debe ser vinculado a formal (->)", al.getLine());
        }
	;

parametro_real
	: expresion
	| lambda
	;

control
	: sentencia_IF
	    {
	        generador.agregarTerceto("endif", $1, "-");
	    }
	| do_until
	;


sentencia_IF
	: inicio_if cuerpo_opt ENDIF ';'
	    {
	        errManager.debug("IF detectado (sin ELSE)", al.getLine());

            // Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.
            int afterThen = generador.getUltimoTerceto() + 2; //+2 por terceto endif

            // Backpatch BF para saltar a afterThen (no hay else).
            generador.rellenarOperando($1, afterThen, 2);
            $$ = "if";

	    }
	| inicio_if sentencia_ejecutable ENDIF ';'
    	{
    	    errManager.debug("IF detectado (sin ELSE)", al.getLine());

            // Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.
            int afterThen = generador.getUltimoTerceto() + 2;

            // Backpatch BF para saltar a afterThen (no hay else).
            generador.rellenarOperando($1, afterThen, 2);
            $$ = "if";
    	}
	| inicio_if cuerpo_opt else_opt
	    {
	        errManager.debug("IF-ELSE detectado", al.getLine());

            // rellenamos Backpatch BF para saltar al inicio del else
            generador.rellenarOperando($1, $3, 2);
            $$ = "else";
	    }
    | inicio_if sentencia_ejecutable else_opt
        {
            errManager.debug("IF-ELSE detectado", al.getLine());

            // rellenamos Backpatch BF para saltar al inicio del else
            generador.rellenarOperando($1, $3, 2);
            $$ = "else";
        }
	| inicio_if cuerpo_opt ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
    | inicio_if sentencia_ejecutable ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| inicio_if cuerpo_opt ';'
	    { errManager.error("Falta cierre endif",  al.getLine()); }
	| inicio_if sentencia_ejecutable ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | inicio_if '{' ENDIF ';'
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | inicio_if '{' else_opt
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | inicio_if '}' ENDIF ';'
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | inicio_if '}' else_opt
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | inicio_if ENDIF ';'
        { errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
    | inicio_if else_opt ';'
        { errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
	;

inicio_if
    : inicio_if_sin_condicion condicional_opt
        {
            // $2 = indice de terceto condicion.
            // Generar BF con destino desconocido.
            int idxBF = generador.generarBF($2);
            $$ = idxBF;
        }

inicio_if_sin_condicion
    : IF { generador.agregarTerceto("if_inicio", "-", "-"); }

else_opt
    : inicio_else cuerpo_opt ENDIF ';'
        {
            // Backpatch BF para saltar al Else.
            //Lo pasamos para arriba asi se rellena el BF
            $$ = $1 + 1;

            // Calculamos el indice del terceto siguiente a la ultima instruccion del bloque else.
            int afterElse = generador.getUltimoTerceto() + 2;

            // Backpatch BI para saltar a afterElse.
            generador.rellenarOperando($1, afterElse, 1);
        }
    | inicio_else sentencia_ejecutable ENDIF ';'
        {
            $$ = $1 + 1;

            // Calculamos el indice del terceto siguiente a la ultima instruccion del bloque else.
            int afterElse = generador.getUltimoTerceto() + 2;

            // Backpatch BI para saltar a afterElse.
            generador.rellenarOperando($1, afterElse, 1);
        }
    | inicio_else cuerpo_opt ENDIF error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
    | inicio_else sentencia_ejecutable ENDIF error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
    | inicio_else cuerpo_opt ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | inicio_else sentencia_ejecutable ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | inicio_else ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | inicio_else '{' ENDIF ';'
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | inicio_else '}' ENDIF ';'
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | inicio_else ENDIF
        { errManager.error("Falta cuerpo de la sentencia ;.", al.getLine()); }
    ;

inicio_else
    : ELSE
        {
          int idxBI = generador.generarBI();
          //devolvemos la instruccion donde se genero el BI
          $$ = idxBI;
        }
    ;

condicional_opt
    : '(' condicion ')'
        { $$ = $2; }
    | condicion ')'
        { errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
    | '(' condicion error
         { errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
    | condicion
        { errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
    ;

cuerpo_opt
    : '{' lista_sentencias_ejecutables '}'
    | '{' lista_sentencias_ejecutables error
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | lista_sentencias_ejecutables error
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | '{' '}'
        { errManager.error("Falta contenido en bloque", al.getLine()); }
    | '{' error '}'
        { errManager.error("Contenido del bloque invalido", al.getLine()); }
    ;


condicion
	: expresion comparador expresion
	    {
	        errManager.debug("Condicion detectada. Linea: " + al.getLine());

            String opLexema = $2; // adaptar si $2 no es string
            String mensaje = generador.generarTercetoValido(opLexema, $1, $3, al.ts);
            if (mensaje != null) {
                errManager.error(mensaje, al.getLine());
                break;
            } else {
                $$ = generador.getUltimoTerceto(); // indice del terceto de la condición
            }
	     }
	| expresion error
	    { errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
	;

do_until
	: inicio_do cuerpo_opt UNTIL condicional_opt ';'
	    {
	        errManager.debug("DO-UNTIL detectado", al.getLine());
            int indiceInicio = $1;   // primer terceto del cuerpo
            int indiceCondicion  = $4;   // indice del terceto que representa la condicion

            // Generamos BF y luego rellenamos su destino al inicio del cuerpo.
            int indiceBF = generador.generarBF(indiceCondicion);
            generador.rellenarOperando(indiceBF, indiceInicio, 2);
	    }
	| inicio_do cuerpo_opt UNTIL condicional_opt error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| inicio_do UNTIL condicional_opt ';'
	    { errManager.error("Falta cuerpo de DO", al.getLine()); }
	| inicio_do UNTIL condicional_opt error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| inicio_do cuerpo_opt condicional_opt ';'
	    { errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
	;

inicio_do
    : DO
        {
            // Guardamos la direccion de inicio del DO.
            generador.agregarTerceto("do_inicio","-","-");
            $$ = generador.getUltimoTerceto() + 1;
        }

comparador
	: MENORIGUAL
	    { $$ = "<="; }
	| MAYORIGUAL
	    { $$ = ">="; }
	| IGUALIGUAL
	    { $$ = "=="; }
	| DISTINTO
	    { $$ = "!="; }
	| '>'
	    { $$ = ">"; }
	| '<'
	    { $$ = "<"; }
	;

tipo
	: LONG
	    { $$ = 0; }
	| STRING
	    { errManager.error("Tipo string no permitido.",al.getLine()); }
	;

asignacion
	: IDCOMP ASIGNAR expresion ';'
	    {
            String mensaje = generador.puedoEscribir($1,al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            mensaje = generador.checkearAlcance($1, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.generarTercetoValido(":=", $1, $3, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            errManager.debug("Asignacion valida detectada", al.getLine());
            int indiceTerceto = generador.getUltimoTerceto();

	    }
	| IDCOMP ASIGNAR expresion error
	    { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
	| ID ASIGNAR expresion ';'
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	| ID ASIGNAR expresion error
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	;

expresion
	: expresion '+' termino
	    {
            String mensaje = generador.generarTercetoValido("+", $1, $3, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Suma valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                $$ = indiceTerceto + "";
            }
        }
	| expresion '+' error 
		{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
	| '+'
		{ errManager.debug("Faltan los dos operandos", al.getLine()); }
	| expresion '-' termino
	    {
            String mensaje = generador.generarTercetoValido("-", $1, $3, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Resta valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                $$ = indiceTerceto + "";
            }
        }
	| expresion '-' error
		{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
	| '-' error
		{ errManager.debug("Faltan los dos operandos", al.getLine()); }
	| termino
	    { $$ = $1; }
	| TRUNC cuerpo_expresion
	    {
	        errManager.debug("Trunc detectado", al.getLine());
	        String mensaje = generador.validarExpresionTrunc($2, al.ts);
	        if (mensaje != null){
	            errManager.error(mensaje, al.getLine());
	            break ;
	        }
	        // Generamos el terceto de salida. Al ser trunc devuelve un long .
	        int indiceTerceto = generador.agregarTerceto("trunc", $2, "-", Atributo.longType);
	        $$ = indiceTerceto + "";
        }
	| TRUNC error
	    { errManager.error("Cuerpo del trunc invalido", al.getLine()); }
	;

termino
	: termino '*' factor
	    {
            String mensaje = generador.generarTercetoValido("*", $1, $3, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Multiplicacion valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                $$ = indiceTerceto + "";
            }
	    }
	| '*' factor
		{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
	| termino '*' error
		{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
	| '*' error
		{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
	| termino '/' factor
	    {
	        String mensaje = generador.generarTercetoValido("/", $1, $3, al.ts);
	        if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Division valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                $$ = indiceTerceto + "";
            }
        }
	| '/' factor
		{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
	| termino '/' error
		{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
	| '/' error
		{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
	| factor
	    { $$ = $1; }
	;

factor
	: IDCOMP
	    {
            // Verificamos que el IDCOMP sea valido.
            String mensaje = generador.validarLecturaYAlcance($1, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break;
            }

            //pongo en factor el valor necesario para el terceto
            $$ = $1;
        }
	| constante
	    { $$ = $1; }
	| llamada_funcion
	    { $$ = $1; }
	| ID
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
    | '-' llamada_funcion
        {
            String mensaje = generador.generarTercetoValido("*", "-1L", $2, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            int indiceTerceto = generador.getUltimoTerceto();
            $$ = indiceTerceto + "";
        }
    | '-' IDCOMP
        {
            errManager.debug("Identificador con -", al.getLine());

            // Verificamos que el IDCOMP sea valido.
            String mensaje = generador.validarLecturaYAlcance($2, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }


            // Generamos el terceto correspondiente. Utilizamos un "-1L" previamente creado como auxiliar.
            mensaje = generador.generarTercetoValido("*", "-1L", $2, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            int indiceTerceto = generador.getUltimoTerceto();
            $$ = indiceTerceto + "";
        }
    | '-' ID
        { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	;

llamada_funcion
	: IDCOMP '(' lista_parametros_reales ')'
	    {
	        errManager.debug("Llamado a funcion detectado", al.getLine());
	        String mensaje = generador.puedoLlamar($1, al.ts);
	        if (mensaje != null){
	            errManager.error(mensaje, al.getLine());
	            break ;
	        }

	        mensaje = generador.checkearAlcance($1, al.ts);
	        if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.checkearParametrosLlamada($1, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.generarTercetosLlamado($1, al.ts);
            try{
                int indiceTerceto = Integer.parseInt(mensaje);
                $$ = indiceTerceto + "";
            } catch (Exception e){
                errManager.error(mensaje, al.getLine());
            }

	    }
	| ID '(' lista_parametros_reales ')'
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
    | IDCOMP '(' error ')'
        { errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
	| ID '(' error ')'
	    { errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
	;

print
	: PRINT cuerpo_print
	    {
	        errManager.debug("Print detectado", al.getLine());
	        generador.agregarTerceto("print", $2, "-");
	    }

cuerpo_print
    : cuerpo_expresion
        { $$ = $1; }
    | '(' CADENASTR ')'
        { $$ = $2; }

lambda
	: inicio_lambda '{' lista_sentencias_ejecutables '}'
	    {
	        errManager.debug("Definicion lambda detectada", al.getLine());

	        generador.agregarTerceto("fin_funcion", $1, "-");
	        generador.exitScope();

	        $$ = $1;
	    }
	| inicio_lambda '{' lista_sentencias_ejecutables
	    { errManager.error("Falta llave de cierre en lambda", al.getLine()); }
	| inicio_lambda lista_sentencias_ejecutables '}'
	    { errManager.error("Falta llave de apertura en lambda", al.getLine()); }
    | inicio_lambda
        { errManager.error("Faltan llaves en lambda", al.getLine()); }
	;

inicio_lambda
    : '(' tipo ID ')'
        {
            // Obtenemos un nombre para la funcion lambda.
            String lamName = generador.getLambdaName();
            // Entramos al scope de la funcion.
            generador.enterScope(lamName);
            String lamScope = generador.getCurrentScope();

            // Agregamos el parametro, esto para reutilizar los metodos de funcion.
            generador.agregarParametro(false, $2, $3);
            // Aplica ambito a los parametros y crea el terceto de funcion.
            generador.aplicarAmbito(al.ts, Atributo.lambdaType, lamScope);

            //Generamos el terceto correspondiente al inicio de la a funcion.
            generador.agregarTerceto("inicio_funcion", lamScope, "-");
            $$ = lamScope;
        }
    | '(' CR tipo ID ')'
        { errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
    ;


retorno
	: RETURN cuerpo_expresion
	    {
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", $2, "-");
	    }
	| RETURN expresion
        { errManager.error("Faltan ambos parentesis del return", al.getLine()); }

	;

cuerpo_expresion
    : '(' expresion ')'
        { $$ = $2; }
    | expresion ')'
        { errManager.error("Falta parentesis de apertura", al.getLine()); }
    | '(' expresion error
        { errManager.error("Falta parentesis de cierre", al.getLine()); }
    | '(' ')'
        { errManager.error("Faltan argumentos", al.getLine()); }
    | '(' error
        { errManager.error("Falta parentesis de cierre", al.getLine()); }
    | ')'
        { errManager.error("Falta parentesis de apertura", al.getLine()); }
    ;

asignacion_multiple
    : ids '=' lista_constantes ';'
        {
            errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());
            // Se checkeo alcance y lectura de las ID, por lo que solo resta verificar los tipos y
            // realizar las asignaciones (generar tercetos).

            String mensaje = generador.generarAsignacionMultiple(al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            generador.limpiarAsignacionMultiple();

        }
    | ids error '=' lista_constantes ';'
        { errManager.error("Falta de separador: ','", al.getLine()); }
    | ids '=' error
        { errManager.error("Falta de separador: ','", al.getLine()); }
    | ids '=' lista_constantes error
        { errManager.error("Falta separador ';'", al.getLine()); }
    ;

ids
    : IDCOMP
        {
            // Verificamos que el IDCOMP sea valido.
            String mensaje = generador.validarLecturaYAlcance($1, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            generador.agregarIDMultiple($1, al.ts);
        }
    | ids ',' IDCOMP
        {
            // Verificamos que el IDCOMP sea valido.
            String mensaje = generador.validarLecturaYAlcance($3, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            generador.agregarIDMultiple($3, al.ts);
        }
    ;

lista_constantes
    : lista_constantes ',' constante
        { generador.agregarConstanteMultiple($3, al.ts); }
    | constante
        { generador.agregarConstanteMultiple($1, al.ts); }
    ;

constante
    : CTEL
        {
            checkearRango($1);
            $$ = $1;
        }
    | CTEF
        {
            errManager.debug("CTEF detectada sin signo " + $1 ,al.getLine());
            $$ = $1;
        }
	| '-' CTEL
	    {
	        tratarNegativos($2);
	        $$ = "-" + $2;
	    }
	| '-' CTEF
	    {
           tratarNegativos($2);
           $$ = "-" + $2;
        }
	| INVALID
	    { errManager.error("factor invalido", al.getLine()); }
    | '+' CTEL
        {
            checkearRango($2);
            $$ = $2;
        }
    | '+' CTEF
        { $$ = $2; }
    ;

%%
private AnalizadorLexico al;
private ErrorManager errManager;
private static final ParserVal dummyParserVal = new ParserVal();
private Generador generador;
private Traductor traductor;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
    this.generador = new Generador();
    al.ts.insertar("-1L", new Atributo(0,-1,Atributo.USO_AUXILIAR));
    al.ts.insertar("0L", new Atributo(0,0,Atributo.USO_AUXILIAR));
    this.traductor = new Traductor(al.ts,errManager);
}

public int yylex(){
    int token = al.yylex();
    errManager.debug("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ")", al.getLine());
    ParserVal val = al.getYylval();

    if (val != null){
        this.yylval = val;
        errManager.debug("Se almaceno el yylval " + val);
    }
    else	
		this.yylval = dummyParserVal;
        //this.yylval = null; esto no funciona.

    return token;
}

public void tratarNegativos(String lexemaAnterior){
    errManager.debug("Tratando numero negativo " + lexemaAnterior,al.getLine());
    Atributo atributoAnterior = al.ts.obtener(lexemaAnterior);
    String nuevoLexema = "-" + lexemaAnterior;
    Atributo atributoNuevo = new Atributo(Atributo.longType,-1 * atributoAnterior.numValue, Atributo.USO_CONSTANTE);
    al.ts.insertar(nuevoLexema, atributoNuevo);
}

public void checkearRango(String lexemaActual){
    errManager.debug("Checkeando rango de constante " + lexemaActual,al.getLine());
    Atributo atributoActual = al.ts.obtener(lexemaActual);
    if (atributoActual.numValue == (2147483648L)) {
        errManager.warning("Constante long fuera de rango, truncando.");
        atributoActual.numValue -= 1;
    }
}

public void declararVariable(String IDCOMP, int tipo){
    errManager.debug("Declaracion de variable detectada.",  al.getLine());
    // Verificar que el prefijo coincida con el ambito.
    if (generador.checkearAmbito(IDCOMP)){
        // Verificar si ya se encuentra declarada.
        if (generador.estaDeclarada(IDCOMP,al.ts)){
            errManager.error("Redeclaracion de variable no permitida.", al.getLine());
        } else {
            // Insertar entrada en Tabla de Simbolos de la variable. Esta reemplaza la del lexico.
            Atributo info = new Atributo(tipo, Atributo.USO_VARIABLE);
            info.declarado = true;
            al.ts.reemplazar(IDCOMP, info);
        }
    } else {
        errManager.error("El ambito declarado es incorrecto.", al.getLine());
    }
}

public void run()
{
    yyparse();
    errManager.entrega("Tabla de simbolos resultante\n" +  al.ts.toString());
    errManager.entrega("Tercetos resultante\n" +  generador.imprimirTercetos());
    if (!errManager.hayError){
        errManager.entrega("Codigo resultante\n");
        traductor.traducir(generador.getTercetos());
    } else
        errManager.error("Error en compilacion", al.getLine());
}

public void yyerror(String s) {
}
