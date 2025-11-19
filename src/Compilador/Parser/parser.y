%token ID IF ELSE ENDIF PRINT RETURN LAMBDA ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO
%token FLECHA LONG DO UNTIL TRUNC CR STRING IDCOMP CADENASTR


%{
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
    import Compilador.Lexer.Atributo;
    import Compilador.Generador.Generador;
%}

%start programa

%type <sval> constante factor termino expresion parametro_real
%token <sval> CTEL CTEF INVALID ID IDCOMP

%type <ival> lista_identificadores tipo
%token <ival> STRING LONG

%%

programa    
	: inicio_programa lista_sentencias '}'
		{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
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
            if (generador.estaDeclarada(generador.getCurrentScope(), al.ts)){
                errManager.error("Redeclaracion de funcion no permitida.", al.getLine());
            } else {
                // Generamos las entradas solo si es correcta.
                generador.aplicarAmbito(al.ts,$1);
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
	    { errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
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
	;

parametro_real
	: expresion
	| lambda
	;

control
	: sentencia_IF
	| do_until
	;


sentencia_IF
	: IF condicional_opt cuerpo_opt ENDIF ';'
	    { errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
	| IF condicional_opt sentencia_ejecutable ENDIF ';'
    	{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
	| IF condicional_opt cuerpo_opt else_opt
	    { errManager.debug("IF-ELSE detectado", al.getLine()); }
    | IF condicional_opt sentencia_ejecutable else_opt
        { errManager.debug("IF-ELSE detectado", al.getLine()); }
	| IF condicional_opt cuerpo_opt ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
    | IF condicional_opt sentencia_ejecutable ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| IF condicional_opt cuerpo_opt ';'
	    { errManager.error("Falta cierre endif",  al.getLine()); }
	| IF condicional_opt sentencia_ejecutable ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | IF condicional_opt '{' ENDIF ';'
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | IF condicional_opt '{' else_opt
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | IF condicional_opt '}' ENDIF ';'
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | IF condicional_opt '}' else_opt
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | IF condicional_opt ENDIF ';'
        { errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
    | IF condicional_opt else_opt ';'
        { errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
	;

else_opt
    : ELSE cuerpo_opt ENDIF ';'
    | ELSE sentencia_ejecutable ENDIF ';'
    | ELSE cuerpo_opt ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
    | ELSE sentencia_ejecutable ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
    | ELSE cuerpo_opt ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | ELSE sentencia_ejecutable ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | ELSE ';'
        { errManager.error("Falta cierre endif",  al.getLine()); }
    | ELSE '{' ENDIF ';'
        { errManager.error("Falta llave de cierre", al.getLine()); }
    | ELSE '}' ENDIF ';'
        { errManager.error("Falta llave de apertura", al.getLine()); }
    | ELSE ENDIF
        { errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
    ;

condicional_opt
    : '(' condicion ')'
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
	    { errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
	| expresion error
	    { errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
	;

do_until
	: DO cuerpo_opt UNTIL condicional_opt ';'
	    { errManager.debug("DO-UNTIL detectado", al.getLine()); }
	| DO cuerpo_opt UNTIL condicional_opt error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| DO UNTIL condicional_opt ';'
	    { errManager.error("Falta cuerpo de DO", al.getLine()); }
	| DO UNTIL condicional_opt error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| DO cuerpo_opt condicional_opt ';'
	    { errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
	;

comparador
	: MENORIGUAL
	| MAYORIGUAL
	| IGUALIGUAL
	| DISTINTO
	| '>'
	| '<'
	;

tipo
	: LONG
	    { $$ = 0; }
	| STRING
	    { $$ = 2; }
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
	    { errManager.debug("Trunc detectado", al.getLine()); }
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
            // Verificamos que se pueda leer el IDCOMP.
            String mensaje = generador.puedoLeer($1, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break;
            }

            // Verificamos que se encuetre al alcance.
            mensaje = generador.checkearAlcance($1, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            //pongo en factor el valor necesario para el terceto
            $$ = $1;
        }
	| constante
	    { $$ = $1; }
	| llamada_funcion
	    {
	        // a

	    }
	| ID
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
    | '-' llamada_funcion // TODO !!!!. Es tipo long igualmente.
    | '-' IDCOMP
        {
            errManager.debug("Identificador con -", al.getLine());

            // Verificamos que se pueda leer el IDCOMP.
            String mensaje = generador.puedoLeer($2, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            // Verificamos que se encuetre al alcance.
            mensaje = generador.checkearAlcance($2, al.ts);
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

            mensaje = generador.generarTercetosLlamado() ....

	    }
	| ID '(' lista_parametros_reales ')'
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
    | IDCOMP '(' error ')'
        { errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
	| ID '(' error ')'
	    { errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
	;

print
	: PRINT cuerpo_expresion
	    { errManager.debug("Print detectado con expresion", al.getLine());}	;

lambda
	: '(' tipo ID')' '{' lista_sentencias_ejecutables '}'
	    { errManager.debug("Definicion lambda detectada", al.getLine()); }
	| '(' tipo ID ')' '{' lista_sentencias_ejecutables
	    { errManager.error("Falta llave de cierre en lambda", al.getLine()); }
	| '(' tipo ID ')' lista_sentencias_ejecutables '}'
	    { errManager.error("Falta llave de apertura en lambda", al.getLine()); }
    | '(' tipo ID ')'
        { errManager.error("Faltan llaves en lambda", al.getLine()); }
	;

retorno
	: RETURN cuerpo_expresion
	    {errManager.debug("Retorno detectado. Linea: " + al.getLine());}
	;

cuerpo_expresion
    : '(' expresion ')'
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
        { errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
    | ids error '=' lista_constantes ';'
        { errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
    | ids '=' error
        { errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
    | ids '=' lista_constantes error
        { errManager.error("Falta separador ';'", al.getLine()); }
    ;

ids
    : IDCOMP
    | ids ',' IDCOMP
    ;

lista_constantes
    : lista_constantes ',' constante
    | constante
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

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
    this.generador = new Generador();
    al.ts.insertar("-1L", new Atributo(0,-1,"auxiliar"));
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
    errManager.debug("Tabla de simbolos resultante" + '\n' +  al.ts.toString());
    errManager.debug("Tercetos resultante" + '\n' +  generador.tercetos.toString());
}

public void yyerror(String s) {
}
