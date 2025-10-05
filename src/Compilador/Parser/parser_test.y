%token ID CTEL IF ELSE ENDIF PRINT RETURN LAMBDA ASIGNAR MENORIGUAL MAYORIGUAL IGUALIGUAL DISTINTO
%token FLECHA LONG DO UNTIL TRUNC CR STRING CTEF IDCOMP


%{
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
%}


%start programa

%%

programa    
	: ID '{'
	    lista_sentencias
	'}' { System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); }
    | error '{'
        lista_sentencias
    '}' { System.out.println("Declaracion de programa invalida, identificador invalido. Linea: " + al.getLine()); }
    | '{'
              lista_sentencias
          '}' { System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
    | ID
        lista_sentencias
    '}' { System.out.println("Falta apertura del programa. Linea: " + al.getLine()); }

    | ID '{'
            lista_sentencias
         { System.out.println("Falta apertura del programa. Linea: " + al.getLine()); }
	;

sentencia    
	: sentencia_declarativa
	| sentencia_ejecutable
	;

sentencia_declarativa
	: declaracion_variable ';'
	| declaracion_variable { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	| declaracion_funcion
	;

sentencia_ejecutable
	: asignacion ';'
	| asignacion  { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	| control
	| llamada_funcion ';'
	| llamada_funcion { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	| print ';'
	| print { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	| retorno ';' { System.out.println("checkear valido"); }
	| retorno { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
	;

declaracion_variable
	: tipo lista_identificadores
	| error lista_identificadores { System.out.println("Tipo invalido/ausente"); }
	| tipo asignacion { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
	| error asignacion { System.out.println("Tipo invalido/ausente"); }
	;

declaracion_funcion
	: tipo ID '(' lista_parametros_formales ')' '{' // Declaracion completa
			lista_sentencias
		'}' { System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
	| tipo ID '(' ')' '{' // Declaracion sin argumentos
            lista_sentencias
        '}' { System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
	| error ID '(' lista_parametros_formales ')' '{' // Declaracion tipo invalido
            lista_sentencias
        '}' { System.out.println("Tipo de funcion invalido/ausente. Linea: " + al.getLine()); }
    | tipo ID '(' lista_parametros_formales ')' '{' // Declaracion sin cuerpo

    '}' { System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine()); }
    |  tipo ID '(' ')' '{' // Declaracion sin argumentos ni cuerpo

    '}' { System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine());
         System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }

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
	| lista_parametros_formales error parametro_formal { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
	;

lista_parametros_reales
	: parametro_real_compuesto
	| lista_parametros_reales ',' parametro_real_compuesto
	| lista_parametros_reales error parametro_real_compuesto{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
	;

lista_identificadores
	: IDCOMP { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
	| lista_identificadores ',' IDCOMP { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
	| lista_identificadores error IDCOMP { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
	| ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	| lista_identificadores ',' ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	;

parametro_formal
	: CR tipo ID { System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
	| tipo ID { System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
	| LAMBDA tipo ID { System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
	| CR LAMBDA tipo ID { System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
	;

parametro_real_compuesto
	: parametro_real FLECHA parametro_formal { System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
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
	  { System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
	| IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ELSE '{' lista_sentencias_ejecutables '}' ENDIF
	  { System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
	;

condicion
	: expresion comparador expresion { System.out.println("Condicion detectada. Linea: " + al.getLine()); }
	;

do_until
	: DO '{' lista_sentencias_ejecutables '}' UNTIL '(' condicion ')'
	  { System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
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
	: IDCOMP ASIGNAR expresion { System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
	| ID ASIGNAR expresion { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	;

expresion
	: expresion '+' termino
	| expresion '-' termino
	| termino
	| TRUNC '(' expresion ')' {System.out.println("Trunc detectado. Linea: " + al.getLine()); }
	;

termino
	: termino '*' factor
	| termino '/' factor
	| factor
	;

factor
	: IDCOMP
	| CTEL
	| CTEF
	| llamada_funcion
	| ID { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	;

llamada_funcion
	: IDCOMP '(' lista_parametros_reales ')' {System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
	| ID '(' lista_parametros_reales ')' { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
    | IDCOMP '(' ')' {System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
    | ID '(' ')' { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
	;

print
	: PRINT '(' STRING ')' {System.out.println("Print detectado. Linea: " + al.getLine());}
	| PRINT '(' expresion ')' {System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
	;

lambda
	: '(' tipo ')' '{' lista_sentencias_ejecutables {System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
	;

retorno
	: RETURN '(' expresion ')' {System.out.println("Retorno detectado. Linea: " + al.getLine());}
	;



%%
private AnalizadorLexico al;

public int yylex(){
    this.al = AnalizadorLexico.getInstance();
    int token = al.yylex();
    System.err.println("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ") En linea " + al.getLine());
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
