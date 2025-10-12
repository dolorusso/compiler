%token ID IF ELSE ENDIF PRINT RETURN LAMBDA ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO
%token FLECHA LONG DO UNTIL TRUNC CR STRING IDCOMP CADENASTR


%{
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
    import Compilador.Lexer.Atributo;
%}

%start programa

%type <sval> constante
%token <sval> CTEL CTEF INVALID

%%

programa    
	: ID '{' lista_sentencias '}'
	    { errManager.debug("Declaracion de programa detectada", al.getLine()); }
    | ID '{' lista_sentencias '}' error
            { errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine()); }
    | '{' lista_sentencias '}'
        { errManager.error("Falta identificador del programa", al.getLine()); }
    | ID lista_sentencias '}'
        { errManager.error("Falta apertura del programa '{'", al.getLine()); }
    | ID '{' lista_sentencias
        { errManager.error("Falta cierre del programa '{'", al.getLine()); }
    | ID lista_sentencias
        { errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
	| ID '{' '}'
	    { errManager.error("Programa vacio.", al.getLine()); }
	| '{' '}'
        { errManager.error("Falta identificador del programa", al.getLine()); }
	| ID
	    { errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
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
	| asignacion_multiple
    | llamada_funcion error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
    | print error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
    | retorno error
        { errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
	;

declaracion_funcion
	: tipo ID parametros_formales_opt cuerpo_funcion_opt
	    { errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
    | ID parametros_formales_opt cuerpo_funcion_opt
         { errManager.error("Funcion sin tipo.", al.getLine()); }
    | tipo parametros_formales_opt cuerpo_funcion_opt
        { errManager.error("Funcion sin nombre.", al.getLine()); }
    | tipo IDCOMP parametros_formales_opt cuerpo_funcion_opt
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
	    { errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
	| CR ID
	    { errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
	| CR tipo error
	    { errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
	| tipo ID
	    { errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
	| ID
	    { errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
	| LAMBDA ID
	    { errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
	| CR LAMBDA ID
	    { errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
	| CR error
	    { errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
	| tipo error
	    { errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
	;

lista_identificadores
	: tipo IDCOMP
	    { errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
	| lista_identificadores ',' IDCOMP
	    { errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
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
	    { errManager.debug("Parametro real detectado", al.getLine()); }
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
    | '(' condicion
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
	| STRING
	;

asignacion
	: IDCOMP ASIGNAR expresion ';'
	    { errManager.debug("Asignacion detectada", al.getLine()); }
	| IDCOMP ASIGNAR expresion error
	    { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| ID ASIGNAR expresion ';'
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	| ID ASIGNAR expresion error
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	;

expresion
	: expresion '+' termino
	| expresion '+' error 
		{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
	| '+'
		{ errManager.debug("Faltan los dos operandos", al.getLine()); }
	| expresion '-' termino
	| expresion '-' error 
		{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
	| '-'
		{ errManager.debug("Faltan los dos operandos", al.getLine()); }
	| termino
	| TRUNC cuerpo_expresion
	    { errManager.debug("Trunc detectado", al.getLine()); }
	| TRUNC error
	    { errManager.error("Cuerpo del trunc invalido", al.getLine()); }
	;

termino
	: termino '*' factor
	| '*' factor
		{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
	| termino '*' error
		{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
	| '*' error
		{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
	| termino '/' factor
	| '/' factor
		{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
	| termino '/' error
		{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
	| '/' error
		{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
	| factor
	;

factor
	: IDCOMP
	| constante
	| CADENASTR
	| llamada_funcion
	| ID
	    { errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
	;

llamada_funcion
	: IDCOMP '(' lista_parametros_reales ')'
	    { errManager.debug("Llamado a funcion detectado", al.getLine()); }
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
        {errManager.error("Falta parentesis de apertura", al.getLine());}
    | '(' expresion error
        {errManager.error("Falta parentesis de cierre", al.getLine());}
    | '(' ')'
        {errManager.error("Faltan argumentos", al.getLine());}
    | '(' error
        {errManager.error("Falta parentesis de cierre", al.getLine());}
    | ')'
        {errManager.error("Falta parentesis de apertura", al.getLine());}
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
            Atributo atributoActual = al.ts.obtener($1);
            if (atributoActual.ref == 1){
                if (atributoActual.numValue == (2147483648L)) {
                    errManager.warning("Constante long fuera de rango, truncando.");
                    atributoActual.numValue -= 1;
                }
            }

            $$ = $1;
        }
    | CTEF

	| '-' CTEL
	    {
	        tratarNegativos($2);
	        $$ = '-' + $2;
	    }
	| '-' CTEF
	    {
           tratarNegativos($2);
           $$ = '-' + $2;
        }
	| INVALID
	    { errManager.error("factor invalido", al.getLine()); }
    ;

%%
private AnalizadorLexico al;
private ErrorManager errManager;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
}

public int yylex(){
    int token = al.yylex();
    errManager.debug("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ")", al.getLine());
    ParserVal val = al.getYylval();

    if (val != null)
        this.yylval = val;
    else
        this.yylval = null;

    return token;
}

public void tratarNegativos(String lexemaAnterior){
    errManager.debug("Tratando numero negativo " + lexemaAnterior,al.getLine());
    Atributo atributoAnterior = al.ts.obtener(lexemaAnterior);
    if(atributoAnterior.ref > 1){
        Atributo atributoNuevo = new Atributo(Atributo.longType,-1 * atributoAnterior.numValue);
        al.ts.insertar('-'+lexemaAnterior, atributoNuevo);
        atributoAnterior.ref -= 1;
    } else {
        atributoAnterior.numValue = -1 * atributoAnterior.numValue;
        al.ts.modificar(lexemaAnterior,'-'+lexemaAnterior, atributoAnterior);
    }
}

public void run()
{
  yyparse();
  errManager.debug("Tabla de simbolos resultante" + '\n' +  al.ts.toString());

}

public void yyerror(String s) {
    System.err.println("Error generico: " + s);
    System.err.println("Linea: " + al.getLine());
}
