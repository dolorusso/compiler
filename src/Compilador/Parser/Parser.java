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
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
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
    1,    2,    1,    1,    1,    2,    1,    2,    2,    2,
    2,    9,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    3,    3,    2,    3,    4,    3,    1,    1,    2,
    2,    8,   11,    3,    8,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    1,    4,    3,    3,
    1,    1,    1,    1,    1,    4,    4,    4,    5,    4,
};
final static short yydefred[] = {                         0,
    0,   43,    0,    0,   42,    0,    0,    0,   13,    0,
    3,    4,    5,    0,    7,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   14,    2,    6,    8,    9,
    0,   11,    0,   30,   31,    0,    0,   54,   53,   55,
    0,    0,   51,    0,    0,   19,    0,    0,   29,    0,
    0,    0,    0,   15,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   56,    0,    0,   36,   37,   38,   39,
   40,   41,    0,    0,   57,   58,    0,   16,    0,    0,
    0,    0,   17,   22,    0,    0,    0,   49,   50,    0,
   20,   27,    0,    0,    0,    0,    0,    0,   24,    0,
    0,   48,    0,    0,    0,   25,    0,   23,    0,   18,
    0,    0,    0,   26,    0,    0,   32,   35,    0,    0,
    0,    0,   12,    0,    0,   33,   60,
};
final static short yydgoto[] = {                          7,
    8,    9,   10,   11,   12,   13,   14,   15,   40,   17,
   18,   33,   82,  120,   55,   83,   45,   46,   47,   48,
   49,   19,   20,   51,   73,   42,   43,
};
final static short yysindex[] = {                      -151,
  -21,    0,  -28,   -4,    0,  -69,    0, -151,    0,    5,
    0,    0,    0,   23,    0,   34,   44, -143,   60,   64,
 -200,  -40, -200, -202, -195,    0,    0,    0,    0,    0,
   -6,    0,   83,    0,    0,   96,  105,    0,    0,    0,
   79,   38,    0, -165,   50,    0, -127,   79,    0,  -14,
  112,  115,   22,    0, -124, -179,  -94, -200, -219, -219,
 -219, -219,  126,    0,  -40, -179,    0,    0,    0,    0,
    0,    0, -200,   47,    0,    0,  -98,    0, -165, -177,
  -85,   74,    0,    0,  116,   38,   38,    0,    0,   51,
    0,    0,   79, -195,  133,  -82, -165,  -81,    0,   54,
 -179,    0, -195, -118, -200,    0,  -79,    0, -151,    0,
 -195, -133,  138,    0, -160,   57,    0,    0,  141,   58,
 -195, -200,    0, -110,  119,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  182,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -7,    0,  125,    0,    0,  -39,    0,    0,    0,    0,
  127,  -32,    0,    0,    0,    0,    0,  -91,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -27,  -19,    0,    0,    0,
    0,    0,  146,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -88,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   80,   -3,    0,   31,    0,    0,  172,    0,   17,    0,
   -5,    0,    0,    0,  -50,  -56,    0,  128,    0,   26,
    0,    0,    0,   86,    0,  106,  107,
};
final static int YYTABLESIZE=265;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   77,   52,   52,   52,   26,   52,  112,   52,   47,   92,
   47,   23,   47,   45,  126,   45,   16,   45,   22,   52,
   52,   46,   52,   46,   16,   46,   47,   47,   59,   47,
   60,   45,   45,   56,   45,   24,   21,   36,   63,   46,
   46,   16,   46,  104,  110,   72,   41,   71,   50,   53,
   81,   21,  111,   25,   36,   54,   36,   52,   38,   39,
   81,    1,   76,   27,   59,    3,   60,   37,    4,   37,
  124,   16,    6,   96,   98,   38,   39,   38,   39,   61,
    2,   28,    2,   85,   62,   78,   79,    5,   97,    5,
   64,  107,   29,   65,    2,   81,    1,   80,   93,    2,
    3,    5,   30,    4,  119,    1,    5,    6,    2,    3,
   16,   26,    4,   31,  100,    5,    6,  101,   34,   16,
   16,   59,   35,   60,   54,   16,   57,   16,  116,  117,
   50,   16,    1,   54,   78,   22,    3,   16,    1,    4,
   16,   78,    3,    6,   58,    4,    1,  125,   66,    6,
    3,   54,   74,    4,   78,   75,  102,    6,   59,  127,
   60,   59,   84,   60,   86,   87,   90,   88,   89,   94,
   95,   99,  105,  103,  106,  108,  109,  114,  118,  121,
  122,    1,  123,   10,   28,   44,   34,   59,  115,   32,
  113,    0,   91,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   36,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
    0,    0,   52,   52,   52,   52,   52,   38,   39,   47,
   47,   47,   47,   47,   45,   45,   45,   45,   45,   21,
    0,    0,   46,   46,   46,   46,   46,   67,   68,   69,
   70,    0,    0,    0,   21,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  125,   41,   42,   43,    8,   45,  125,   47,   41,   66,
   43,   40,   45,   41,  125,   43,    0,   45,   40,   59,
   60,   41,   62,   43,    8,   45,   59,   60,   43,   62,
   45,   59,   60,   40,   62,   40,   44,  257,   44,   59,
   60,   25,   62,   94,  101,   60,   21,   62,   23,   24,
   56,   59,  103,  123,  257,   25,  257,  260,  278,  279,
   66,  257,   41,   59,   43,  261,   45,  270,  264,  270,
  121,   55,  268,   79,   80,  278,  279,  278,  279,   42,
  260,   59,  260,   58,   47,   55,  266,  267,  266,  267,
   41,   97,   59,   44,  260,  101,  257,  277,   73,  260,
  261,  267,   59,  264,  265,  257,  267,  268,  260,  261,
   94,  115,  264,  257,   41,  267,  268,   44,   59,  103,
  104,   43,   59,   45,   94,  109,   44,  111,  262,  263,
  105,  115,  257,  103,  104,   40,  261,  121,  257,  264,
  124,  111,  261,  268,   40,  264,  257,  122,  276,  268,
  261,  121,   41,  264,  124,   41,   41,  268,   43,   41,
   45,   43,  257,   45,   59,   60,   41,   61,   62,  123,
  269,  257,   40,  123,  257,  257,  123,  257,   41,  123,
   40,    0,  125,   59,  276,   59,   41,  276,  109,   18,
  105,   -1,   65,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,
   -1,   -1,  272,  273,  274,  275,  276,  278,  279,  272,
  273,  274,  275,  276,  272,  273,  274,  275,  276,  271,
   -1,   -1,  272,  273,  274,  275,  276,  272,  273,  274,
  275,   -1,   -1,   -1,  271,
};
}
final static short YYFINAL=7;
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
"programa : lista_sentencias",
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

//#line 166 "parser_test.y"

/* Manejo de errores */
public void yyerror(String s) {
    System.err.println("Error sintáctico: " + s);
}
//#line 343 "Parser.java"
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
//#line 25 "parser_test.y"
{ System.out.println("Declaración de variable detectada"); }
break;
case 5:
//#line 26 "parser_test.y"
{ System.out.println("Declaración de función detectada"); }
break;
case 6:
//#line 30 "parser_test.y"
{ System.out.println("Asignación detectada"); }
break;
case 8:
//#line 32 "parser_test.y"
{ System.out.println("Llamada a función detectada"); }
break;
case 9:
//#line 33 "parser_test.y"
{ System.out.println("Print detectado"); }
break;
case 32:
//#line 96 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE)"); }
break;
case 33:
//#line 98 "parser_test.y"
{ System.out.println("IF-ELSE detectado"); }
break;
case 35:
//#line 107 "parser_test.y"
{ System.out.println("DO-UNTIL detectado"); }
break;
//#line 524 "Parser.java"
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
