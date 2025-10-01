%token ID CTELONG CTEFLOAT STRING
%token IF ELSE ENDIF PRINT RETURN LAMBDA LONG DO UNTIL TRUNC
%token ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO FLECHA
%token CR CTEF CTEL

%start programa

%%

programa    
	: lista_sentencias    
	;

sentencia    
	: sentencia_declarativa ';'    
	| sentencia_ejecutable    
	;

sentencia_declarativa
	: declaracion_variable   { System.out.println("Declaración de variable detectada"); }
	| declaracion_funcion    { System.out.println("Declaración de función detectada"); }
	;

sentencia_ejecutable
	: asignacion ';'         { System.out.println("Asignación detectada"); }
	| control
	| llamada_funcion ';'    { System.out.println("Llamada a función detectada"); }
	| print ';'              { System.out.println("Print detectado"); }
	;

declaracion_variable
	: tipo lista_identificadores
	| tipo asignacion
	;

declaracion_funcion
	: tipo ID '(' lista_parametros_formales ')' '{'
			lista_sentencias
			retorno
		'}'
	;

lista_sentencias 
	: sentencia
	| lista_sentencias sentencia
	;

lista_sentencias_ejecutables 
	: sentencia_ejecutable
	| lista_sentencias_ejecutables sentencia_ejecutable
	;

lista_parametros_formales
	: parametro_formal
	| lista_parametros_formales ',' parametro_formal
	;

lista_parametros_reales
	: parametro_real_compuesto
	| lista_parametros_reales ',' parametro_real_compuesto
	;

lista_identificadores
	: ID
	| lista_identificadores ',' ID
	;

parametro_formal
	: CR tipo ID
	| tipo ID
	| LAMBDA tipo ID
	| CR LAMBDA tipo ID
	;

parametro_real_compuesto
	: parametro_real FLECHA parametro_formal
	;

parametro_real
	: expresion
	| lambda
	;

control
	: sentencia_IF ';'
	| do_until ';'
	;

sentencia_IF
	: IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ENDIF
	  { System.out.println("IF detectado (sin ELSE)"); }
	| IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ELSE '{' lista_sentencias_ejecutables '}'
	  { System.out.println("IF-ELSE detectado"); }
	;

condicion
	: expresion comparador expresion
	;

do_until
	: DO '{' lista_sentencias_ejecutables '}' UNTIL '(' condicion ')'
	  { System.out.println("DO-UNTIL detectado"); }
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
	: ID ASIGNAR expresion
	;

expresion
	: expresion '+' termino
	| expresion '-' termino
	| termino
	| TRUNC '(' expresion ')'
	;

termino
	: termino '*' factor
	| termino '/' factor
	| factor
	;

factor
	: ID
	| CTEL
	| CTEF
	| llamada_funcion
	;

llamada_funcion
	: ID '(' lista_parametros_reales ')'
	;

print
	: PRINT '(' STRING ')'
	| PRINT '(' expresion ')'
	;

lambda
	: '(' tipo ')' '{' lista_sentencias_ejecutables
	;

retorno
	: RETURN '(' expresion ')'
	;

%%

/* Manejo de errores */
public void yyerror(String s) {
    System.err.println("Error sintáctico: " + s);
}
