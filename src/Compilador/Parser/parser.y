%token ID CTELONG CTEFLOAT STRING
%token IF ELSE ENDIF PRINT RETURN LAMBDA LONG DO UNTIL TRUNC
%token ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO FLECHA
%token CR CTEF CTEL ID.ID

%{
  package Compilador.parser;
  import Compilador.lexer.AnalizadorLexico;
%}


%%
programa    
	: lista_sentencias
	;
	
sentencia    
	: sentencia_declarativa ';'    
	| sentencia_ejecutable    
	;

sentencia_declarativa
	: declaracion_variable
	| declaracion_funcion
	;

sentencia_ejecutable
	: asignacion ';'
	| control
	| llamada_funcion ';'
	| print ';'
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

lista_sentencias : sentencia    | lista_sentencias sentencia    ;

lista_sentencias_ejecutables : 
	sentencia_ejecutable 
	| lista_sentencias_ejecutables sentencia_ejecutable    ;

lista_parametros_formales
	: parametro_formal
	| lista_parametros_formales ',' parametro_formal
	;

lista_parametros_reales
	: parametro_real_compuesto
	| lista_parametros_reales ',' parametro_real_compuesto
	;

lista_identificadores
	: ID.ID
	| lista_identificadores ',' ID.ID
	;


parametro_formal
	: CR tipo ID
	| tipo ID	| LAMBDA tipo ID
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
	| IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ELSE '{' lista_sentencias_ejecutables '}'
	;

condicion
	: expresion comparador expresion
	;

do_until
	: DO '{' lista_sentencias_ejecutables '}' UNTIL '(' condicion ')'
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
asignacion	: ID ASIGNAR expresion
	;

expresion	: expresion '+' termino	| expresion '-' termino    | termino
	| TRUNC '(' expresion ')'    ;termino    : termino '*' factor    | termino '/' factor    | factor    ;factor    : ID    | CTEL    | CTEF
	| llamada_funcion    ;

llamada_funcion
	: ID '(' lista_parametros_reales ')'
	;

print
	: PRINT '(' STRING ')'
	| PRINT '(' expresion ')'
	;

lambda
	: '(' tipo ')' '{' lista_sentencias_ejecutables

retorno
	: RETURN '(' expresion ')'
	;