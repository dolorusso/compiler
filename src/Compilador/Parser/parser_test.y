%token ID CTEL IF ELSE ENDIF PRINT RETURN LAMBDA ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO
%token FLECHA LONG DO UNTIL TRUNC CR STRING CTEF IDCOMP CADENASTR


%{
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
%}


%start programa

%%

programa    
	: ID '{'
	    lista_sentencias
	'}' { System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); }
    | '{'
              lista_sentencias
          '}' { System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
    | ID
        lista_sentencias
    '}' { System.out.println("Falta apertura del programa '{'. Linea: " + al.getLine()); }
    | ID '{'
            lista_sentencias
         { System.out.println("Falta cierre del programa '}'. Linea: " + al.getLine()); }
    | ID lista_sentencias { System.out.println("Faltan delimitadores de programa '{' y '}'. Linea: " + al.getLine()); }
	| ID '{' '}' { System.out.println("Programa vacio. Linea: " + al.getLine()); }
	| '{' '}' { System.out.println("Falta ID del programa. Linea: " + al.getLine()); }
	//| ID { System.out.println("Faltan delimitadores de programa '{' y '}'. Linea: " + al.getLine()); }
	;

sentencia
	: sentencia_declarativa
	| sentencia_ejecutable
	;

sentencia_declarativa
	: lista_identificadores ';'
	| lista_identificadores error { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	| declaracion_funcion
	;

sentencia_ejecutable
	: asignacion { System.out.println("Asignacion detectada"); }
	| control
	| llamada_funcion ';'
	| print ';'
	| retorno ';' { System.out.println("checkear valido"); }
	| asignacion_multiple
    | llamada_funcion error { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
    | print error { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
    | retorno error { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	;

declaracion_funcion
	: tipo ID parametros_formales_opt cuerpo_funcion_opt
	    { System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
    | ID parametros_formales_opt cuerpo_funcion_opt
        { System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
    | tipo parametros_formales_opt cuerpo_funcion_opt
        { System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
    | tipo IDCOMP parametros_formales_opt cuerpo_funcion_opt
        { System.out.println("No se debe especificar la unidad en declaracion de funcion. Linea: " + al.getLine()); }
    ;

parametros_formales_opt
    : '(' lista_parametros_formales ')'
    | '('  ')'  { System.out.println("Parametros formales invalidos. Linea: " + al.getLine()); }
    ;

cuerpo_funcion_opt
    : '{' lista_sentencias '}'
    | '{' '}' { System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
    | { System.out.println("Faltan llaves de la funcion. Linea: " + al.getLine()); }
    ;


lista_sentencias
	: sentencia
	| lista_sentencias sentencia
	| error ';' { System.out.println("PROBLEMON. Linea: " + al.getLine()); } //Cuando no detecto absolutamente nada, descarto todo hasta encontrar el ';'
    ;

lista_sentencias_ejecutables
	: sentencia_ejecutable
	| lista_sentencias_ejecutables sentencia_ejecutable
	;

lista_parametros_formales
	: parametro_formal
	| lista_parametros_formales ',' parametro_formal
	| lista_parametros_formales parametro_formal //ojo con estas y la de abajo, shift/reduce
	;

lista_parametros_reales
	: parametro_real_compuesto
	| lista_parametros_reales ',' parametro_real_compuesto
	| lista_parametros_reales parametro_real_compuesto{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
	;

parametro_formal
	: CR tipo ID { System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
	| CR ID { System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
	| CR tipo error { System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
	| tipo ID { System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
	| ID { System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
	| LAMBDA ID { System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
	| CR LAMBDA ID { System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
	| CR error { System.out.println("Se espera un tipo pero se encontro .... Linea: " + al.getLine()); }
	| tipo error { System.out.println("Se espera un Identifier para el parametro formal  Linea: " + al.getLine()); }
	;


lista_identificadores
	: tipo IDCOMP { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
	| lista_identificadores ',' IDCOMP { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
	| lista_identificadores IDCOMP { System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
	| ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	| tipo ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	| lista_identificadores ',' ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	| lista_identificadores ID  { System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
	;



parametro_real_compuesto
	: parametro_real FLECHA ID { System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
	;

parametro_real
	: expresion
	| lambda
	;

control
	: sentencia_IF
	| do_until ';'
	| do_until error
	;


sentencia_IF
	: IF condicional_opt cuerpo_opt ENDIF ';'
	    { System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
	| IF condicional_opt cuerpo_opt else_opt
	    { System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
	| IF condicional_opt cuerpo_opt ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
	| IF condicional_opt cuerpo_opt ';'
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
    | ELSE cuerpo_opt ENDIF error
        { errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
    | ELSE cuerpo_opt ';'
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
    | condicion ')' { errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
    | '(' condicion { errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
    | condicion  { errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
    ;

cuerpo_opt
    : '{' lista_sentencias_ejecutables '}'
    | sentencia_ejecutable //una unica sentencia sin { }
    | '{' lista_sentencias_ejecutables { errManager.error("Falta llave de cierre", al.getLine()); }
    | lista_sentencias_ejecutables '}' { errManager.error("Falta llave de apertura", al.getLine()); }
    | '{' '}' { errManager.error("Falta contenido en bloque", al.getLine()); }
    ;


condicion
	: expresion comparador expresion { errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
	;

do_until
	: DO cuerpo_opt UNTIL condicional_opt { errManager.debug("DO-UNTIL detectado", al.getLine()); }
	| DO UNTIL condicional_opt { errManager.error("Falta cuerpo de DO", al.getLine()); }
	;

comparador
	: MENORIGUAL
	| MAYORIGUAL
	| IGUALIGUAL
	| DISTINTO
	| '>'
	| '<'
	| { errManager.error("Falta comparador", al.getLine()); }
	;

tipo
	: LONG
	| STRING
	;

asignacion
	: IDCOMP ASIGNAR expresion ';' { System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
	| IDCOMP ASIGNAR expresion error { System.out.println("Falta ;. Linea: " + al.getLine()); }
	| ID ASIGNAR expresion { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	| ID ASIGNAR expresion error { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	;

expresion
	: expresion '+' termino
	| expresion '-' termino
	| termino {System.out.println("ENCONTRE TERMINO!. Linea: " + al.getLine()); }
	| TRUNC cuerpo_expresion {System.out.println("Trunc detectado. Linea: " + al.getLine()); }
	| TRUNC error {System.out.println("Falta delimitadores trunc. Linea: " + al.getLine()); }
	;

termino
	: termino '*' factor
	| termino '/' factor
	| factor
	;

factor
	: IDCOMP
	| constante
	| CADENASTR
	| llamada_funcion
	| ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	;

llamada_funcion
	: IDCOMP '(' lista_parametros_reales ')' {System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
	| ID '(' lista_parametros_reales ')' { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
    | IDCOMP '(' error ')' {System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
	| ID '(' error ')' {System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
	;

print
	: PRINT cuerpo_expresion {System.out.println("Print detectado con expresion. Linea: " + al.getLine());}	;

lambda
	: '(' tipo ')' '{' lista_sentencias_ejecutables '}'  {errManager.debug("Definicion lambda detectada. Linea: " + al.getLine());}
	| '(' tipo ')' '{' lista_sentencias_ejecutables { errManager.error("Falta llave de cierre en lambda", al.getLine()); }
	| '(' tipo ')' lista_sentencias_ejecutables '}'
	;

retorno
	: RETURN cuerpo_expresion {System.out.println("Retorno detectado. Linea: " + al.getLine());}
	;

cuerpo_expresion
    : '(' expresion ')'
    | expresion ')'
    | '(' expresion error
    | '(' ')' {System.out.println("Faltan argumentos. Linea: " + al.getLine());}
    | '(' error {System.out.println("Falta parentesis de cierre. Linea: " + al.getLine());}
    | ')' {System.out.println("Falta parentesis de apertura. Linea: " + al.getLine());}
    ;

asignacion_multiple
    : ids '=' lista_constantes ';' { errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());}
    | ids error '=' lista_constantes ';' { errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
    | ids '=' error { errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
    | ids '=' lista_constantes error { errManager.error("Falta separador ';'", al.getLine());}
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
    | CTEF
    ;


%%
private AnalizadorLexico al;
private ErrorManager errManager;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    //errManager.
}

public int yylex(){
    int token = al.yylex();
    errManager.debug("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ") En linea " + al.getLine());

    this.yylval = al.getYylval();
    if(this.yylval == null)
        yylval = new ParserVal(0);
    return token;
}

/* Manejo de errores */
public void yyerror(String s) {

    System.err.println("Error sintáctico: " + s);
    System.err.println("Linea: " + al.getLine());

}
