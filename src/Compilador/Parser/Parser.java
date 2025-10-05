//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package Compilador.Parser;



//#line 7 "parser_test.y"
  import Compilador.Lexer.AnalizadorLexico;

//#line 20 "Parser.java"




public class Parser
{

boolean yydebug = true;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTELONG=258;
public final static short CTEFLOAT=259;
public final static short STRING=260;
public final static short IF=261;
public final static short ELSE=262;
public final static short ENDIF=263;
public final static short PRINT=264;
public final static short RETURN=265;
public final static short LAMBDA=266;
public final static short LONG=267;
public final static short DO=268;
public final static short UNTIL=269;
public final static short TRUNC=270;
public final static short ASIGNAR=271;
public final static short MENORIGUAL=272;
public final static short MAYORIGUAL=273;
public final static short IGUALIGUAL=274;
public final static short DISTINTO=275;
public final static short FLECHA=276;
public final static short CR=277;
public final static short CTEF=278;
public final static short CTEL=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    2,    3,    3,    4,    4,    4,    4,    5,
    5,    6,    1,    1,   15,   15,   13,   13,   17,   17,
   12,   12,   16,   16,   16,   16,   18,   19,   19,    8,
    8,   22,   22,   24,   23,   25,   25,   25,   25,   25,
   25,   11,   11,    7,   20,   20,   20,   20,   26,   26,
   26,   27,   27,   27,   27,    9,   10,   10,   21,   14,
};
final static short yylen[] = {                            2,
    4,    2,    1,    1,    1,    2,    1,    2,    2,    2,
    2,    9,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    3,    3,    2,    3,    4,    3,    1,    1,    2,
    2,    8,   11,    3,    8,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    1,    4,    3,    3,
    1,    1,    1,    1,    1,    4,    4,    4,    5,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   43,    0,    0,   42,    0,    0,
   13,    0,    3,    4,    5,    0,    7,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,   14,    2,
    6,    8,    9,    0,   11,    0,   30,   31,    0,    0,
   54,   53,   55,    0,    0,   51,    0,    0,   19,    0,
    0,   29,    0,    0,    0,    0,   15,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   56,    0,    0,   36,
   37,   38,   39,   40,   41,    0,    0,   57,   58,    0,
   16,    0,    0,    0,    0,   17,   22,    0,    0,    0,
   49,   50,    0,   20,   27,    0,    0,    0,    0,    0,
    0,   24,    0,    0,   48,    0,    0,    0,   25,    0,
   23,    0,   18,    0,    0,    0,   26,    0,    0,   32,
   35,    0,    0,    0,    0,   12,    0,    0,   33,   60,
};
final static short yydgoto[] = {                          2,
   10,   11,   12,   13,   14,   15,   16,   17,   43,   19,
   20,   36,   85,  123,   58,   86,   48,   49,   50,   51,
   52,   21,   22,   54,   76,   45,   46,
};
final static short yysindex[] = {                      -200,
  -46,    0, -158,  -21,    0,   58,   85,    0,  -31, -124,
    0,   70,    0,    0,    0,   72,    0,   76,   80, -140,
   82,   86, -212,  -40, -212, -210, -145,    0,    0,    0,
    0,    0,    0,   -6,    0,  104,    0,    0,  112,  115,
    0,    0,    0,   48,  -30,    0, -181,   20,    0, -120,
   48,    0,  -14,  121,  122,   77,    0, -115, -182,  -88,
 -212, -203, -203, -203, -203,  129,    0,  -40, -182,    0,
    0,    0,    0,    0,    0, -212,   49,    0,    0,  -96,
    0, -181, -179,  -83,   21,    0,    0,   83,  -30,  -30,
    0,    0,   55,    0,    0,   48, -145,  139,  -76, -181,
  -75,    0,   61, -182,    0, -145, -110, -212,    0,  -72,
    0, -158,    0, -145, -211,  145,    0, -160,   64,    0,
    0,  148,   65, -145, -212,    0, -100,   89,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   11,    0,  130,    0,    0,  -39,    0,
    0,    0,    0,  132,  -32,    0,    0,    0,    0,    0,
  -84,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -27,  -19,
    0,    0,    0,    0,    0,  153,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -81,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   87,   -5,    0,   69,    0,    0,  177,    0,   53,    0,
  -10,    0,    0,    0,  -53,  -62,    0,  133,    0,   13,
    0,    0,    0,   90,    0,   52,   18,
};
final static int YYTABLESIZE=265;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   28,   52,   52,   52,   29,   52,   95,   52,   47,   80,
   47,   64,   47,   45,  115,   45,   65,   45,   24,   52,
   52,   46,   52,   46,  129,   46,   47,   47,   62,   47,
   63,   45,   45,   59,   45,   44,   66,   53,   56,   46,
   46,  113,   46,  107,   39,   75,   39,   74,   84,   55,
  119,  120,  114,   39,   21,   18,    1,   40,   84,   40,
   67,  103,   18,   68,  104,   41,   42,   41,   42,   21,
  127,   99,  101,   88,   41,   42,    3,    5,    5,   18,
    5,   91,   92,   82,    8,    8,  100,    8,   96,  110,
   62,   27,   63,   84,   83,   57,    4,   25,    4,    5,
    6,    5,    6,    7,  122,    7,    8,    9,    8,    9,
   18,    4,   29,   89,   90,    6,   34,   79,    7,   62,
   53,   63,    9,  105,   26,   62,   81,   63,   30,  130,
   31,   62,    4,   63,   32,    5,    6,  128,   33,    7,
   37,    4,    8,    9,   38,    6,    4,   60,    7,   18,
    6,   24,    9,    7,   61,   69,    4,    9,   18,   18,
    6,   77,   78,    7,   18,   57,   18,    9,   87,   93,
   18,   97,   98,  102,   57,   81,   18,  106,  108,   18,
  109,  111,   81,  112,  117,  121,  124,  125,   10,  126,
   44,   28,   57,   34,   59,   81,   35,  116,  118,    0,
   94,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   40,
    0,    0,   52,   52,   52,   52,   52,   41,   42,   47,
   47,   47,   47,   47,   45,   45,   45,   45,   45,   23,
    0,    0,   46,   46,   46,   46,   46,   70,   71,   72,
   73,    0,    0,    0,   23,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  125,   41,   42,   43,   10,   45,   69,   47,   41,  125,
   43,   42,   45,   41,  125,   43,   47,   45,   40,   59,
   60,   41,   62,   43,  125,   45,   59,   60,   43,   62,
   45,   59,   60,   40,   62,   23,   47,   25,   26,   59,
   60,  104,   62,   97,  257,   60,  257,   62,   59,  260,
  262,  263,  106,  257,   44,    3,  257,  270,   69,  270,
   41,   41,   10,   44,   44,  278,  279,  278,  279,   59,
  124,   82,   83,   61,  278,  279,  123,  260,  260,   27,
  260,   64,   65,  266,  267,  267,  266,  267,   76,  100,
   43,  123,   45,  104,  277,   27,  257,   40,  257,  260,
  261,  260,  261,  264,  265,  264,  267,  268,  267,  268,
   58,  257,  118,   62,   63,  261,  257,   41,  264,   43,
  108,   45,  268,   41,   40,   43,   58,   45,   59,   41,
   59,   43,  257,   45,   59,  260,  261,  125,   59,  264,
   59,  257,  267,  268,   59,  261,  257,   44,  264,   97,
  261,   40,  268,  264,   40,  276,  257,  268,  106,  107,
  261,   41,   41,  264,  112,   97,  114,  268,  257,   41,
  118,  123,  269,  257,  106,  107,  124,  123,   40,  127,
  257,  257,  114,  123,  257,   41,  123,   40,   59,  125,
   59,  276,  124,   41,  276,  127,   20,  108,  112,   -1,
   68,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,
   -1,   -1,  272,  273,  274,  275,  276,  278,  279,  272,
  273,  274,  275,  276,  272,  273,  274,  275,  276,  271,
   -1,   -1,  272,  273,  274,  275,  276,  272,  273,  274,
  275,   -1,   -1,   -1,  271,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTELONG","CTEFLOAT","STRING","IF",
"ELSE","ENDIF","PRINT","RETURN","LAMBDA","LONG","DO","UNTIL","TRUNC","ASIGNAR",
"MENORIGUAL","MAYORIGUAL","IGUALIGUAL","DISTINTO","FLECHA","CR","CTEF","CTEL",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID '{' lista_sentencias '}'",
"sentencia : sentencia_declarativa ';'",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funcion",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : control",
"sentencia_ejecutable : llamada_funcion ';'",
"sentencia_ejecutable : print ';'",
"declaracion_variable : tipo lista_identificadores",
"declaracion_variable : tipo asignacion",
"declaracion_funcion : tipo ID '(' lista_parametros_formales ')' '{' lista_sentencias retorno '}'",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_parametros_formales : parametro_formal",
"lista_parametros_formales : lista_parametros_formales ',' parametro_formal",
"lista_parametros_reales : parametro_real_compuesto",
"lista_parametros_reales : lista_parametros_reales ',' parametro_real_compuesto",
"lista_identificadores : ID",
"lista_identificadores : lista_identificadores ',' ID",
"parametro_formal : CR tipo ID",
"parametro_formal : tipo ID",
"parametro_formal : LAMBDA tipo ID",
"parametro_formal : CR LAMBDA tipo ID",
"parametro_real_compuesto : parametro_real FLECHA parametro_formal",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF ';'",
"control : do_until ';'",
"sentencia_IF : IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ENDIF",
"sentencia_IF : IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ELSE '{' lista_sentencias_ejecutables '}'",
"condicion : expresion comparador expresion",
"do_until : DO '{' lista_sentencias_ejecutables '}' UNTIL '(' condicion ')'",
"comparador : MENORIGUAL",
"comparador : MAYORIGUAL",
"comparador : IGUALIGUAL",
"comparador : DISTINTO",
"comparador : '>'",
"comparador : '<'",
"tipo : LONG",
"tipo : STRING",
"asignacion : ID ASIGNAR expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : TRUNC '(' expresion ')'",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTEL",
"factor : CTEF",
"factor : llamada_funcion",
"llamada_funcion : ID '(' lista_parametros_reales ')'",
"print : PRINT '(' STRING ')'",
"print : PRINT '(' expresion ')'",
"lambda : '(' tipo ')' '{' lista_sentencias_ejecutables",
"retorno : RETURN '(' expresion ')'",
};

//#line 169 "parser_test.y"
private AnalizadorLexico al;

public int yylex(){
    this.al = AnalizadorLexico.getInstance();
    int token = al.yylex();
    System.err.println("Se reconocio el token " + token);
    this.yylval = al.getYylval();
    return token;
}

/* Manejo de errores */
public void yyerror(String s) {
    System.err.println("Error sintáctico: " + s);
    System.err.println("Linea: " + al.getLine());

}
//#line 355 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 4:
//#line 28 "parser_test.y"
{ System.out.println("Declaración de variable detectada"); }
break;
case 5:
//#line 29 "parser_test.y"
{ System.out.println("Declaración de función detectada"); }
break;
case 6:
//#line 33 "parser_test.y"
{ System.out.println("Asignación detectada"); }
break;
case 8:
//#line 35 "parser_test.y"
{ System.out.println("Llamada a función detectada"); }
break;
case 9:
//#line 36 "parser_test.y"
{ System.out.println("Print detectado"); }
break;
case 32:
//#line 99 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE)"); }
break;
case 33:
//#line 101 "parser_test.y"
{ System.out.println("IF-ELSE detectado"); }
break;
case 35:
//#line 110 "parser_test.y"
{ System.out.println("DO-UNTIL detectado"); }
break;
//#line 536 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
