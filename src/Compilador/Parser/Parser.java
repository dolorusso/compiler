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



//#line 6 "parser.y"
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
    import Compilador.Lexer.Atributo;
    import Compilador.Generador.Generador;
//#line 23 "Parser.java"




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
public final static short IF=258;
public final static short ELSE=259;
public final static short ENDIF=260;
public final static short PRINT=261;
public final static short RETURN=262;
public final static short LAMBDA=263;
public final static short ASIGNAR=264;
public final static short MENORIGUAL=265;
public final static short MAYORIGUAL=266;
public final static short IGUALIGUAL=267;
public final static short DISTINTO=268;
public final static short FLECHA=269;
public final static short LONG=270;
public final static short DO=271;
public final static short UNTIL=272;
public final static short TRUNC=273;
public final static short CR=274;
public final static short STRING=275;
public final static short IDCOMP=276;
public final static short CADENASTR=277;
public final static short CTEL=278;
public final static short CTEF=279;
public final static short INVALID=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   10,   12,   12,   13,   13,   13,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   15,   21,   21,
   21,   21,   23,   23,   22,   22,   22,   11,   11,   11,
   25,   25,   24,   24,   24,   27,   27,   27,   26,   26,
   26,   26,   26,   26,   26,   26,   26,    8,    8,    8,
    8,    8,    8,    8,   28,    5,    5,   17,   17,   30,
   30,   30,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   30,   30,   34,   34,   34,   34,   34,   34,   34,
   34,   34,   34,   32,   32,   32,   32,   33,   33,   33,
   33,   33,   35,   35,   31,   31,   31,   31,   31,   36,
   36,   36,   36,   36,   36,    9,    9,   16,   16,   16,
   16,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    2,    2,    2,    2,    2,    2,    7,    7,    7,    7,
   18,   29,   29,   29,   29,   29,   19,    6,    6,    6,
    6,    6,    6,   20,   20,   20,   20,   37,   37,   38,
   38,    1,    1,    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    3,    3,    2,    4,    3,    3,    2,    2,    2,    2,
    1,    1,    2,    1,    1,    2,    2,    1,    1,    1,
    2,    2,    2,    1,    2,    2,    2,    2,    3,    2,
    2,    3,    3,    2,    3,    2,    0,    1,    2,    2,
    1,    2,    1,    3,    2,    1,    3,    2,    3,    2,
    3,    2,    1,    2,    3,    2,    2,    2,    3,    2,
    1,    2,    3,    2,    3,    1,    1,    1,    1,    5,
    5,    4,    4,    5,    5,    4,    4,    5,    4,    5,
    4,    4,    4,    4,    4,    4,    4,    3,    3,    2,
    4,    4,    2,    3,    2,    3,    1,    3,    3,    2,
    2,    3,    3,    2,    5,    5,    4,    4,    4,    1,
    1,    1,    1,    1,    1,    1,    1,    4,    4,    4,
    4,    3,    3,    1,    3,    3,    2,    1,    2,    2,
    3,    2,    3,    2,    3,    2,    3,    2,    1,    1,
    1,    1,    1,    2,    2,    2,    4,    4,    4,    4,
    2,    7,    6,    6,    4,    8,    2,    3,    2,    3,
    2,    2,    1,    4,    5,    3,    4,    1,    3,    3,
    1,    1,    1,    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,    0,  117,    0,   13,    0,    0,    0,    0,   38,
   14,   15,   18,   19,   20,    0,    0,   24,    0,   68,
   69,    0,   10,    0,    0,    9,    0,   40,    0,    0,
   30,    0,    0,    0,  172,  173,  176,    0,    0,    0,
    0,    0,  141,  139,    0,    0,  142,    0,    0,    0,
  163,    0,  151,  157,    0,    0,    0,   41,    0,    0,
    0,    0,   25,   21,   17,   64,   60,   16,    0,    0,
    0,    0,   31,    6,   39,   26,   22,   27,   23,    0,
   28,    0,    0,    0,    5,    2,    0,    0,    0,    0,
    0,    0,    0,   34,    0,    0,    0,    0,   43,    0,
   46,   67,    0,  130,  129,    0,  177,  178,  127,    0,
    0,  174,  175,  144,  134,    0,    0,  132,  138,  136,
    0,    0,  104,  110,  111,  112,  113,  114,  115,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   95,
  162,  161,    0,  159,    0,    0,  101,    0,  100,   42,
    0,    0,    0,    0,    0,   63,   59,   29,   32,   53,
   36,    0,    0,  169,  166,    0,  171,    0,    4,  121,
  120,  150,   54,   56,   50,    0,    0,    0,    0,    0,
   57,   52,   33,    0,   45,  148,    0,   48,   96,   94,
  133,  131,  137,  135,  123,    0,  126,    0,    0,   93,
    0,    0,   90,    0,    0,   82,    0,   81,    0,   79,
    0,   77,   73,    0,   76,   72,   83,  160,  158,  108,
  107,  102,   99,   98,    0,  109,  119,  118,  149,  147,
   35,    0,  167,  164,    0,   55,   51,   49,    0,    0,
   65,   44,   47,    0,    0,    0,   89,    0,   88,   80,
   78,   75,   71,   74,   70,  106,  105,  165,  170,    0,
    0,   92,   91,   87,   85,   86,   84,    0,    0,    0,
    0,    0,  154,    0,  152,  156,
};
final static short yydgoto[] = {                          4,
   53,   54,   55,  105,  106,   63,   57,   17,   18,    5,
   19,   20,   21,   22,   23,   24,   25,   26,   27,   28,
   29,   91,   41,  108,   69,  109,  110,  111,  112,   30,
   31,   58,   70,  149,   59,  142,   32,  178,
};
final static short yysindex[] = {                       102,
    0,  625,  678,    0,  711,  -43,  -32,  105,   59,   59,
    0,  648,    0,   -4,    0,   46,    5,  -28,  773,    0,
    0,    0,    0,    0,    0,   54,   56,    0,  -60,    0,
    0,  -20,    0,  789,   15,    0,  796,    0,  143,  -36,
    0,   39,   35,   47,    0,    0,    0,  143, -235,  291,
  168,  185,    0,    0,   19,  372,    0,  671,    9,  132,
    0,  146,    0,    0,   12,  105,  845,    0,  897,   69,
  143,   77,    0,    0,    0,    0,    0,    0, -217,   78,
   78,  112,    0,    0,    0,    0,    0,    0,    0,  766,
    0,   60, -148,  -15,    0,    0, -120,   -6,  117,   39,
  -94,  386,  -52,    0,  -16, -104, -159,  360,    0,  -22,
    0,    0,   87,    0,    0,  -39,    0,    0,    0,   39,
   47,    0,    0,    0,    0, -235,  -73,    0,    0,    0,
  202,  210,    0,    0,    0,    0,    0,    0,    0,  150,
  157,  143,  603,  122, -152,  704,  -45,   44,  135,    0,
    0,    0,   17,    0,   84,   53,    0,  852,    0,    0,
  105,  142,   90,  166,   49,    0,    0,    0,    0,    0,
    0,  812,  -12,    0,    0, -110,    0,   51,    0,    0,
    0,    0,    0,    0,    0,  -81,   43, -124,  -48,  -38,
    0,    0,    0,  592,    0,    0,  119,    0,    0,    0,
    0,    0,    0,    0,    0,   19,    0,   19,  -16,    0,
  -29,  819,    0,  -44,   26,    0,  153,    0,  165,    0,
  108,    0,    0,  121,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  123,    0,    0,    0,    0,    0,
    0,   10,    0,    0,  -12,    0,    0,    0,   -8,  192,
    0,    0,    0,  193,  200,  124,    0,  137,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  256,
  349,    0,    0,    0,    0,    0,    0,  178,  920,  868,
  920,  874,    0,  876,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  309,    0,    0,    0,    0,   95,    0,    0,    0,
    0,    0,    0,  -10,    0,    0,    0,    0,  324,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   13,    0,
    0,    0,    0,    0,  336,    0,  354,    0,    0,    0,
    0,  409,    0,  432,    0,    0,    0,    0,  499,    0,
    0,    0,    0,    0,  521,    0,    0,    0,  582,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  228,
  246,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  368,    0,    0,  276,
    0,    0,    0,    0,  101,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  454,
  476,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  904,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  559,    0,  750,  332,    0,
    0,    0,    0,  904,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  103,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  125,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -47,  501,  190,  837,    0,   28,   -2,    0,  729,    0,
   81,   36,    0,   14,    0,    0,    0,    0,    0,    0,
    0,    0,  427,    0,  294,  -63,  302,  -42,    0,    0,
    0,  -24,   -1,  364,  342,    0,    0,  223,
};
final static int YYTABLESIZE=1196;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   16,  200,   16,  103,  104,   51,   49,   40,   50,   16,
   52,   82,   37,  222,  257,   38,   16,  103,  196,   51,
   49,  197,   50,   93,   52,   68,  140,  126,  141,  176,
  126,   16,  176,  168,   16,   72,  140,   64,  141,  166,
   94,  155,  117,  118,  195,  162,  177,  124,   79,  150,
  168,  113,  181,  245,   85,   16,  148,  229,  167,  140,
  131,  141,   90,   78,   16,  132,   16,  198,  268,   85,
  115,  147,   85,   38,   60,   61,   51,   49,  113,   50,
   68,   52,  160,   34,  259,   37,   72,   16,  103,  240,
   51,   49,  197,   50,  245,   52,  191,  192,   60,   61,
   51,   49,  225,   50,   74,   52,  143,  217,   48,  244,
   51,   49,   87,   50,   89,   52,  103,   82,   51,   49,
  173,   50,  198,   52,  124,  177,  103,  174,   51,   49,
  252,   50,  140,   52,  141,  179,  235,   37,   61,   96,
   16,  215,  231,   16,   48,   11,   51,   49,  238,   50,
   13,   52,  104,   61,  253,   16,  214,  182,  103,   68,
   51,   49,  183,   50,  190,   52,  263,  122,  123,   16,
  172,  160,  152,   51,   49,  246,   50,  232,   52,  265,
  216,  267,  275,  120,   51,   49,  154,   50,  140,   52,
  141,   51,  126,  227,  127,  277,   52,  269,   51,  126,
  236,  127,  121,   52,  122,  123,  239,   85,  250,   16,
  126,  260,  127,  143,  221,  256,  199,   11,  251,   99,
  100,  188,   13,  261,    3,   68,  101,  126,   80,  127,
  254,   39,  271,   11,   42,   92,   43,  102,   13,   44,
  175,   45,   46,   47,  126,  168,  127,   81,  270,  180,
   43,  272,  126,   44,  127,   45,   46,   47,  273,   71,
   75,   76,   45,   46,   47,   45,   46,   47,   16,   37,
   37,   62,  228,   37,   37,   39,   16,   16,   16,   16,
   77,   16,   37,   37,   68,  258,   62,   37,   37,   58,
  114,   42,   68,  160,   68,  160,  278,  160,  247,  248,
  281,   73,  143,  224,   58,   42,  243,   43,   11,   86,
   44,   88,   45,   46,   47,   42,   53,  143,  143,   53,
  143,   43,  143,    8,   44,   42,   45,   46,   47,  206,
  208,   43,  164,   42,   44,    3,   45,   46,   47,  230,
  161,   43,   99,   42,   44,  237,   45,   46,   47,   43,
   61,   61,   44,    7,   45,   46,   47,    1,    2,   43,
  158,   42,   44,  262,   45,   46,   47,    1,  170,   66,
   61,  155,  103,  165,  101,   42,  264,   43,  266,  274,
   44,   11,   45,   46,   47,  102,   13,  151,   42,  116,
  103,   43,  276,  153,   44,  242,   45,   46,   47,   42,
  193,    0,    0,  194,   43,  205,   42,   44,    0,   45,
   46,   47,  207,   42,  140,   43,  141,    0,   44,    0,
   45,   46,   47,  125,   42,   44,    0,   45,   46,   47,
    0,  139,   44,  138,   45,   46,   47,    0,    0,  158,
  129,   42,    0,   44,   83,   45,   46,   47,    0,  143,
  143,  143,    0,  143,  103,  143,  103,  201,   42,    0,
   44,    0,   45,   46,   47,  203,   42,  143,  143,    0,
  143,  279,  140,  140,  140,    0,  140,   44,  140,   45,
   46,   47,    0,   62,   62,   44,    0,   45,   46,   47,
  140,  140,    0,  140,  146,  146,  146,    0,  146,    0,
  146,   58,   58,   62,    0,  158,  168,  169,  218,  220,
  223,  226,  146,  146,    0,  146,  145,  145,  145,    0,
  145,   58,  145,    0,    0,    0,    0,    0,    0,    0,
    0,  143,   53,  143,  145,  145,    0,  145,   53,  124,
    0,  124,    0,  124,  143,   53,  119,  120,    0,   53,
   53,  128,  130,    0,  140,    0,  140,  124,  124,    0,
  124,  128,    0,  128,  280,  128,  121,    0,  122,  123,
    0,    0,  282,    0,  284,    0,  146,    0,  146,  128,
  128,    0,  128,    0,    0,    0,    0,  103,  103,  103,
  103,  103,  103,  103,    0,    0,    0,    0,  145,  122,
  145,  122,  103,  122,    0,   65,    8,  103,    0,    9,
   10,    0,    0,    0,    0,    0,  170,  122,  122,   12,
  122,  124,  101,  124,   14,    0,    0,  133,    0,   11,
    0,  202,  204,  102,   13,    0,  134,  135,  136,  137,
   97,  184,  185,  128,    0,  128,    0,    0,  186,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,    0,
   13,  213,    0,    0,  143,  143,  143,  143,  143,  143,
  143,    0,    0,  143,  143,  143,  143,  143,    0,  143,
    0,  122,    0,  122,  143,    0,    0,  140,  140,  140,
  140,  140,  140,  140,    0,    0,  140,  140,  140,  140,
  140,    0,  140,    0,   97,    0,   97,  140,    0,  146,
  146,  146,  146,  146,  146,  146,    0,    0,  146,  146,
  146,  146,  146,    0,  146,  212,    0,  211,    0,  146,
    0,  145,  145,  145,  145,  145,  145,  145,    0,    0,
  145,  145,  145,  145,  145,    0,  145,   15,    0,    0,
    0,  145,    0,    0,  124,  124,  124,  124,  124,  124,
  124,    0,    0,  124,  124,  124,  124,  124,  107,  124,
   67,    0,    0,    0,  124,    0,  128,  128,  128,  128,
  128,  128,  128,    0,    0,  128,  128,  128,  128,  128,
  125,  128,  125,  146,  125,  145,  128,    0,    0,    0,
    0,    0,   33,    0,    0,    0,    0,    0,  125,  125,
  107,  125,    0,    0,  122,  122,  122,  122,  122,  122,
  122,    0,    0,  122,  122,  122,  122,  122,  157,  122,
  187,  189,    0,    0,  122,   36,  107,   97,   97,   97,
   97,   97,   97,   97,   56,   62,   62,    0,  170,    0,
    0,    0,   97,    0,  101,    0,    0,   97,    0,   65,
    8,   11,  210,    9,   10,  102,   13,    0,    0,    0,
    0,    0,  125,   12,  125,   98,    0,    0,   14,   62,
    6,    7,    8,    0,   56,    9,   10,    0,    0,    0,
  171,    0,    0,    0,   11,   12,  153,   84,    0,   13,
   14,    0,   56,    0,   65,    8,   56,  163,    9,   10,
    0,    0,    0,   95,    0,    0,  249,    0,   12,   66,
   97,    0,  107,   14,    0,    0,    0,   65,    8,  143,
  144,    9,   10,    6,    7,    8,  241,    0,    9,   10,
    0,   12,    0,  157,    0,    0,   14,   11,   12,    0,
    0,    0,   13,   14,    0,    0,    0,    0,    0,  156,
   65,    8,  143,  219,    9,   10,   35,    7,    8,  157,
    0,    9,   10,    0,   12,    0,  234,    0,  209,   14,
   11,   12,    0,    0,    0,   13,   14,    0,    0,    0,
    0,    0,  283,    0,    0,    0,    0,   56,  285,    0,
  286,    0,    0,    0,    0,  125,  125,  125,  125,  125,
  125,  125,    0,    0,  125,  125,  125,  125,  125,    0,
  125,    6,    7,    8,    0,  125,    9,   10,    0,    7,
    8,    0,    0,    9,   10,   11,   12,    0,    0,    0,
   13,   14,   11,   12,    0,    7,    8,   13,   14,    9,
   10,    0,    7,    8,    0,    0,    9,   10,   11,   12,
    0,    0,    0,   13,   14,   11,   12,    0,    7,    8,
   13,   14,    9,   10,  156,   65,    8,    0,  255,    9,
   10,   11,   12,    0,    0,    0,   13,   14,    0,   12,
    0,    0,    0,    0,   14,    0,    0,    0,    0,    0,
  156,   65,    8,    0,    0,    9,   10,  233,   65,    8,
    0,    0,    9,   10,    0,   12,    0,    0,    0,    0,
   14,    0,   12,    0,   65,    8,    0,   14,    9,   10,
   65,    8,   65,    8,    9,   10,    9,   10,   12,    0,
    0,    0,    0,   14,   12,    0,   12,    0,    0,   14,
    0,   14,  159,   65,    8,    0,    0,    9,   10,   41,
   41,   41,    0,    0,   41,   41,    0,   12,    0,    0,
    0,    0,   14,    0,   41,    0,   65,    8,    0,   41,
    9,   10,    0,    0,    0,    0,    0,    0,    0,    0,
   12,    0,    0,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    3,   41,    5,   40,   41,   42,   43,   40,   45,   12,
   47,   40,    0,   59,   59,   59,   19,   40,   41,   42,
   43,   44,   45,   44,   47,   12,   43,   43,   45,   45,
   43,   34,   45,   44,   37,   40,   43,   10,   45,  257,
   61,   66,  278,  279,  108,   70,   94,   50,   44,   41,
   61,   40,   59,   44,   19,   58,   58,   41,  276,   43,
   42,   45,  123,   59,   67,   47,   69,  110,   59,   34,
   43,   58,   37,   59,   40,   41,   42,   43,   40,   45,
   67,   47,   69,    3,   59,    5,   40,   90,   40,   41,
   42,   43,   44,   45,   44,   47,  256,  257,   40,   41,
   42,   43,   59,   45,   59,   47,  259,  260,   40,   59,
   42,   43,   59,   45,   59,   47,   40,   40,   42,   43,
   61,   45,  165,   47,  127,  173,   40,  276,   42,   43,
  194,   45,   43,   47,   45,  256,  161,  125,   44,  125,
  143,  143,   59,  146,   40,  270,   42,   43,   59,   45,
  275,   47,   41,   59,  197,  158,  143,   41,   40,  146,
   42,   43,  257,   45,  269,   47,   59,  278,  279,  172,
   90,  158,   41,   42,   43,  257,   45,  125,   47,   59,
   59,   59,   59,  257,   42,   43,   41,   45,   43,   47,
   45,   42,   43,   59,   45,   59,   47,  245,   42,   43,
   59,   45,  276,   47,  278,  279,   41,  172,  257,  212,
   43,   59,   45,  259,  260,  260,  256,  270,  257,  256,
  257,  274,  275,   59,  123,  212,  263,   43,  257,   45,
  260,  264,   41,  270,  257,  256,  273,  274,  275,  276,
  256,  278,  279,  280,   43,  256,   45,  276,  257,  256,
  273,   59,   43,  276,   45,  278,  279,  280,   59,  264,
  256,  257,  278,  279,  280,  278,  279,  280,  271,  257,
  258,   44,  256,  261,  262,  264,  279,  280,  281,  282,
  276,  284,  270,  271,  271,  260,   59,  275,  276,   44,
  256,  257,  279,  280,  281,  282,   41,  284,  256,  257,
  123,  256,  259,  260,   59,  257,  256,  273,    0,  256,
  276,  256,  278,  279,  280,  257,   41,   42,   43,   44,
   45,  273,   47,    0,  276,  257,  278,  279,  280,  140,
  141,  273,  256,  257,  276,    0,  278,  279,  280,  256,
  272,  273,  256,  257,  276,  256,  278,  279,  280,  273,
  256,  257,  276,    0,  278,  279,  280,  256,  257,  273,
   67,  257,  276,  256,  278,  279,  280,    0,  257,  269,
  276,  269,   41,   72,  263,  257,  256,  273,  256,  256,
  276,  270,  278,  279,  280,  274,  275,  256,  257,   48,
   59,  273,  256,  269,  276,  173,  278,  279,  280,  257,
   41,   -1,   -1,   44,  273,  256,  257,  276,   -1,  278,
  279,  280,  256,  257,   43,  273,   45,   -1,  276,   -1,
  278,  279,  280,  256,  257,  276,   -1,  278,  279,  280,
   -1,   60,  276,   62,  278,  279,  280,   -1,   -1,  146,
  256,  257,   -1,  276,   18,  278,  279,  280,   -1,   41,
   42,   43,   -1,   45,  123,   47,  125,  256,  257,   -1,
  276,   -1,  278,  279,  280,  256,  257,   59,   60,   -1,
   62,  123,   41,   42,   43,   -1,   45,  276,   47,  278,
  279,  280,   -1,  256,  257,  276,   -1,  278,  279,  280,
   59,   60,   -1,   62,   41,   42,   43,   -1,   45,   -1,
   47,  256,  257,  276,   -1,  212,   80,   81,  145,  146,
  147,  148,   59,   60,   -1,   62,   41,   42,   43,   -1,
   45,  276,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  123,  257,  125,   59,   60,   -1,   62,  263,   41,
   -1,   43,   -1,   45,  269,  270,  256,  257,   -1,  274,
  275,   51,   52,   -1,  123,   -1,  125,   59,   60,   -1,
   62,   41,   -1,   43,  271,   45,  276,   -1,  278,  279,
   -1,   -1,  279,   -1,  281,   -1,  123,   -1,  125,   59,
   60,   -1,   62,   -1,   -1,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,  123,   41,
  125,   43,  271,   45,   -1,  257,  258,  276,   -1,  261,
  262,   -1,   -1,   -1,   -1,   -1,  257,   59,   60,  271,
   62,  123,  263,  125,  276,   -1,   -1,  256,   -1,  270,
   -1,  131,  132,  274,  275,   -1,  265,  266,  267,  268,
   59,  256,  257,  123,   -1,  125,   -1,   -1,  263,   -1,
   -1,   -1,   -1,   -1,   -1,  270,   -1,   -1,   -1,   -1,
  275,   59,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,
   -1,  123,   -1,  125,  276,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,   -1,  271,   -1,  123,   -1,  125,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,  123,   -1,  125,   -1,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,  123,   -1,   -1,
   -1,  276,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   40,  271,
  123,   -1,   -1,   -1,  276,   -1,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,  266,  267,  268,  269,
   41,  271,   43,  123,   45,  125,  276,   -1,   -1,   -1,
   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   59,   60,
   82,   62,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,  125,  271,
  102,  103,   -1,   -1,  276,  125,  108,  256,  257,  258,
  259,  260,  261,  262,    8,    9,   10,   -1,  257,   -1,
   -1,   -1,  271,   -1,  263,   -1,   -1,  276,   -1,  257,
  258,  270,  260,  261,  262,  274,  275,   -1,   -1,   -1,
   -1,   -1,  123,  271,  125,   39,   -1,   -1,  276,   43,
  256,  257,  258,   -1,   48,  261,  262,   -1,   -1,   -1,
  125,   -1,   -1,   -1,  270,  271,   60,  125,   -1,  275,
  276,   -1,   66,   -1,  257,  258,   70,   71,  261,  262,
   -1,   -1,   -1,  125,   -1,   -1,  188,   -1,  271,  272,
  125,   -1,  194,  276,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  256,  257,  258,  125,   -1,  261,  262,
   -1,  271,   -1,  125,   -1,   -1,  276,  270,  271,   -1,
   -1,   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,  256,
  257,  258,  259,  260,  261,  262,  256,  257,  258,  125,
   -1,  261,  262,   -1,  271,   -1,  125,   -1,  142,  276,
  270,  271,   -1,   -1,   -1,  275,  276,   -1,   -1,   -1,
   -1,   -1,  125,   -1,   -1,   -1,   -1,  161,  125,   -1,
  125,   -1,   -1,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,  256,  257,  258,   -1,  276,  261,  262,   -1,  257,
  258,   -1,   -1,  261,  262,  270,  271,   -1,   -1,   -1,
  275,  276,  270,  271,   -1,  257,  258,  275,  276,  261,
  262,   -1,  257,  258,   -1,   -1,  261,  262,  270,  271,
   -1,   -1,   -1,  275,  276,  270,  271,   -1,  257,  258,
  275,  276,  261,  262,  256,  257,  258,   -1,  260,  261,
  262,  270,  271,   -1,   -1,   -1,  275,  276,   -1,  271,
   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,
  256,  257,  258,   -1,   -1,  261,  262,  256,  257,  258,
   -1,   -1,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,
  276,   -1,  271,   -1,  257,  258,   -1,  276,  261,  262,
  257,  258,  257,  258,  261,  262,  261,  262,  271,   -1,
   -1,   -1,   -1,  276,  271,   -1,  271,   -1,   -1,  276,
   -1,  276,  256,  257,  258,   -1,   -1,  261,  262,  256,
  257,  258,   -1,   -1,  261,  262,   -1,  271,   -1,   -1,
   -1,   -1,  276,   -1,  271,   -1,  257,  258,   -1,  276,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  271,   -1,   -1,   -1,   -1,  276,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
null,null,null,null,null,null,null,"ID","IF","ELSE","ENDIF","PRINT","RETURN",
"LAMBDA","ASIGNAR","MENORIGUAL","MAYORIGUAL","IGUALIGUAL","DISTINTO","FLECHA",
"LONG","DO","UNTIL","TRUNC","CR","STRING","IDCOMP","CADENASTR","CTEL","CTEF",
"INVALID",
};
final static String yyrule[] = {
"$accept : programa",
"programa : inicio_programa lista_sentencias '}'",
"programa : inicio_programa error '}'",
"programa : inicio_programa error",
"programa : inicio_programa lista_sentencias '}' error",
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : inicio_programa lista_sentencias",
"programa : ID lista_sentencias",
"programa : inicio_programa '}'",
"programa : '{' '}'",
"programa : ID",
"programa : error",
"inicio_programa : ID '{'",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : lista_identificadores ';'",
"sentencia_declarativa : lista_identificadores error",
"sentencia_declarativa : declaracion_funcion",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : control",
"sentencia_ejecutable : llamada_funcion ';'",
"sentencia_ejecutable : print ';'",
"sentencia_ejecutable : retorno ';'",
"sentencia_ejecutable : asignacion_multiple",
"sentencia_ejecutable : llamada_funcion error",
"sentencia_ejecutable : print error",
"sentencia_ejecutable : retorno error",
"declaracion_funcion : inicio_funcion cuerpo_funcion_opt",
"inicio_funcion : tipo ID parametros_formales_opt",
"inicio_funcion : ID parametros_formales_opt",
"inicio_funcion : tipo parametros_formales_opt",
"inicio_funcion : tipo IDCOMP parametros_formales_opt",
"parametros_formales_opt : '(' lista_parametros_formales ')'",
"parametros_formales_opt : '(' ')'",
"cuerpo_funcion_opt : '{' lista_sentencias '}'",
"cuerpo_funcion_opt : '{' '}'",
"cuerpo_funcion_opt :",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"lista_sentencias : error ';'",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_parametros_formales : parametro_formal",
"lista_parametros_formales : lista_parametros_formales ',' parametro_formal",
"lista_parametros_formales : lista_parametros_formales parametro_formal",
"lista_parametros_reales : parametro_real_compuesto",
"lista_parametros_reales : lista_parametros_reales ',' parametro_real_compuesto",
"lista_parametros_reales : lista_parametros_reales parametro_real_compuesto",
"parametro_formal : CR tipo ID",
"parametro_formal : CR ID",
"parametro_formal : CR tipo error",
"parametro_formal : tipo ID",
"parametro_formal : ID",
"parametro_formal : LAMBDA ID",
"parametro_formal : CR LAMBDA ID",
"parametro_formal : CR error",
"parametro_formal : tipo error",
"lista_identificadores : tipo IDCOMP",
"lista_identificadores : lista_identificadores ',' IDCOMP",
"lista_identificadores : lista_identificadores IDCOMP",
"lista_identificadores : ID",
"lista_identificadores : tipo ID",
"lista_identificadores : lista_identificadores ',' ID",
"lista_identificadores : lista_identificadores ID",
"parametro_real_compuesto : parametro_real FLECHA ID",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF",
"control : do_until",
"sentencia_IF : IF condicional_opt cuerpo_opt ENDIF ';'",
"sentencia_IF : IF condicional_opt sentencia_ejecutable ENDIF ';'",
"sentencia_IF : IF condicional_opt cuerpo_opt else_opt",
"sentencia_IF : IF condicional_opt sentencia_ejecutable else_opt",
"sentencia_IF : IF condicional_opt cuerpo_opt ENDIF error",
"sentencia_IF : IF condicional_opt sentencia_ejecutable ENDIF error",
"sentencia_IF : IF condicional_opt cuerpo_opt ';'",
"sentencia_IF : IF condicional_opt sentencia_ejecutable ';'",
"sentencia_IF : IF condicional_opt '{' ENDIF ';'",
"sentencia_IF : IF condicional_opt '{' else_opt",
"sentencia_IF : IF condicional_opt '}' ENDIF ';'",
"sentencia_IF : IF condicional_opt '}' else_opt",
"sentencia_IF : IF condicional_opt ENDIF ';'",
"sentencia_IF : IF condicional_opt else_opt ';'",
"else_opt : ELSE cuerpo_opt ENDIF ';'",
"else_opt : ELSE sentencia_ejecutable ENDIF ';'",
"else_opt : ELSE cuerpo_opt ENDIF error",
"else_opt : ELSE sentencia_ejecutable ENDIF error",
"else_opt : ELSE cuerpo_opt ';'",
"else_opt : ELSE sentencia_ejecutable ';'",
"else_opt : ELSE ';'",
"else_opt : ELSE '{' ENDIF ';'",
"else_opt : ELSE '}' ENDIF ';'",
"else_opt : ELSE ENDIF",
"condicional_opt : '(' condicion ')'",
"condicional_opt : condicion ')'",
"condicional_opt : '(' condicion error",
"condicional_opt : condicion",
"cuerpo_opt : '{' lista_sentencias_ejecutables '}'",
"cuerpo_opt : '{' lista_sentencias_ejecutables error",
"cuerpo_opt : lista_sentencias_ejecutables error",
"cuerpo_opt : '{' '}'",
"cuerpo_opt : '{' error '}'",
"condicion : expresion comparador expresion",
"condicion : expresion error",
"do_until : DO cuerpo_opt UNTIL condicional_opt ';'",
"do_until : DO cuerpo_opt UNTIL condicional_opt error",
"do_until : DO UNTIL condicional_opt ';'",
"do_until : DO UNTIL condicional_opt error",
"do_until : DO cuerpo_opt condicional_opt ';'",
"comparador : MENORIGUAL",
"comparador : MAYORIGUAL",
"comparador : IGUALIGUAL",
"comparador : DISTINTO",
"comparador : '>'",
"comparador : '<'",
"tipo : LONG",
"tipo : STRING",
"asignacion : IDCOMP ASIGNAR expresion ';'",
"asignacion : IDCOMP ASIGNAR expresion error",
"asignacion : ID ASIGNAR expresion ';'",
"asignacion : ID ASIGNAR expresion error",
"expresion : expresion '+' termino",
"expresion : expresion '+' error",
"expresion : '+'",
"expresion : expresion '-' termino",
"expresion : expresion '-' error",
"expresion : '-' error",
"expresion : termino",
"expresion : TRUNC cuerpo_expresion",
"expresion : TRUNC error",
"termino : termino '*' factor",
"termino : '*' factor",
"termino : termino '*' error",
"termino : '*' error",
"termino : termino '/' factor",
"termino : '/' factor",
"termino : termino '/' error",
"termino : '/' error",
"termino : factor",
"factor : IDCOMP",
"factor : constante",
"factor : llamada_funcion",
"factor : ID",
"factor : '-' llamada_funcion",
"factor : '-' IDCOMP",
"factor : '-' ID",
"llamada_funcion : IDCOMP '(' lista_parametros_reales ')'",
"llamada_funcion : ID '(' lista_parametros_reales ')'",
"llamada_funcion : IDCOMP '(' error ')'",
"llamada_funcion : ID '(' error ')'",
"print : PRINT cuerpo_expresion",
"lambda : '(' tipo ID ')' '{' lista_sentencias_ejecutables '}'",
"lambda : '(' tipo ID ')' '{' lista_sentencias_ejecutables",
"lambda : '(' tipo ID ')' lista_sentencias_ejecutables '}'",
"lambda : '(' tipo ID ')'",
"lambda : '(' CR tipo ID ')' '{' lista_sentencias_ejecutables '}'",
"retorno : RETURN cuerpo_expresion",
"cuerpo_expresion : '(' expresion ')'",
"cuerpo_expresion : expresion ')'",
"cuerpo_expresion : '(' expresion error",
"cuerpo_expresion : '(' ')'",
"cuerpo_expresion : '(' error",
"cuerpo_expresion : ')'",
"asignacion_multiple : ids '=' lista_constantes ';'",
"asignacion_multiple : ids error '=' lista_constantes ';'",
"asignacion_multiple : ids '=' error",
"asignacion_multiple : ids '=' lista_constantes error",
"ids : IDCOMP",
"ids : ids ',' IDCOMP",
"lista_constantes : lista_constantes ',' constante",
"lista_constantes : constante",
"constante : CTEL",
"constante : CTEF",
"constante : '-' CTEL",
"constante : '-' CTEF",
"constante : INVALID",
"constante : '+' CTEL",
"constante : '+' CTEF",
};

//#line 675 "parser.y"
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
    errManager.debug("Tercetos resultante" + '\n' +  generador.imprimirTercetos());
}

public void yyerror(String s) {
}
//#line 795 "Parser.java"
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
case 1:
//#line 25 "parser.y"
{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
		}
break;
case 2:
//#line 30 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 32 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 34 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 39 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 41 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 43 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 45 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 47 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 49 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 51 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 53 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 13:
//#line 58 "parser.y"
{
            generador.enterScope(val_peek(1).sval);
        }
break;
case 17:
//#line 71 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 81 "parser.y"
{
	        String scope = generador.getCurrentScope();
	        Atributo func = al.ts.obtener(scope);
	        if (func != null && func.uso == Atributo.USO_FUNCION) {
	            func.tieneReturn = true;
	        } else {
	            errManager.error("Return en lugar invalido.", al.getLine());
	        }

	    }
break;
case 25:
//#line 93 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 95 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 97 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 28:
//#line 102 "parser.y"
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
break;
case 29:
//#line 118 "parser.y"
{
            /* Entramos al scope de la funcion.*/
            generador.enterScope(val_peek(1).sval);
            /* Checkeamos si esta declarada usando el scope, ya que este sera el mangle name de la funcion.*/
            String ambitoFuncion = generador.getCurrentScope();
            if (generador.estaDeclarada(ambitoFuncion, al.ts)){
                errManager.error("Redeclaracion de funcion no permitida.", al.getLine());
            } else {
                /* Generamos las entradas solo si es correcta.*/
                generador.aplicarAmbito(al.ts,val_peek(2).ival);

                /*Generamos el terceto correspondiente al inicio de la a funcion.*/
                generador.agregarTerceto("inicio_funcion", ambitoFuncion, "-", val_peek(2).ival);
            }
        }
break;
case 30:
//#line 134 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 136 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 138 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 143 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 148 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 149 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 157 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 175 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 180 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 185 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 187 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 189 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 194 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 196 "parser.y"
{ errManager.debug("Parametro formal lambda semantica detectado", al.getLine()); }
break;
case 55:
//#line 198 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 200 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 202 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 207 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 212 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 216 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 218 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 220 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 222 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 224 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 231 "parser.y"
{
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal(val_peek(2).sval, val_peek(0).sval);
	    }
break;
case 70:
//#line 250 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 71:
//#line 252 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 72:
//#line 254 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 73:
//#line 256 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 74:
//#line 258 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 75:
//#line 260 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 262 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 77:
//#line 264 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 266 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 79:
//#line 268 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 270 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 81:
//#line 272 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 274 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 83:
//#line 276 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 86:
//#line 283 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 87:
//#line 285 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 88:
//#line 287 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 89:
//#line 289 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 90:
//#line 291 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 91:
//#line 293 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 92:
//#line 295 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 93:
//#line 297 "parser.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 95:
//#line 303 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 96:
//#line 305 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 97:
//#line 307 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 99:
//#line 313 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 100:
//#line 315 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 101:
//#line 317 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 102:
//#line 319 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 103:
//#line 325 "parser.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 104:
//#line 327 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 105:
//#line 332 "parser.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 106:
//#line 334 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 107:
//#line 336 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 108:
//#line 338 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 109:
//#line 340 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 116:
//#line 354 "parser.y"
{ yyval.ival = 0; }
break;
case 117:
//#line 356 "parser.y"
{ errManager.error("Tipo string no permitido.",al.getLine()); }
break;
case 118:
//#line 361 "parser.y"
{
            String mensaje = generador.puedoEscribir(val_peek(3).sval,al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            mensaje = generador.checkearAlcance(val_peek(3).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.generarTercetoValido(":=", val_peek(3).sval, val_peek(1).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            errManager.debug("Asignacion valida detectada", al.getLine());
            int indiceTerceto = generador.getUltimoTerceto();

	    }
break;
case 119:
//#line 385 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 120:
//#line 387 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 121:
//#line 389 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 122:
//#line 394 "parser.y"
{
            String mensaje = generador.generarTercetoValido("+", val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Suma valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
        }
break;
case 123:
//#line 405 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 124:
//#line 407 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 125:
//#line 409 "parser.y"
{
            String mensaje = generador.generarTercetoValido("-", val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Resta valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
        }
break;
case 126:
//#line 420 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 127:
//#line 422 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 128:
//#line 424 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 129:
//#line 426 "parser.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 130:
//#line 428 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 131:
//#line 433 "parser.y"
{
            String mensaje = generador.generarTercetoValido("*", val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Multiplicacion valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
	    }
break;
case 132:
//#line 444 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 133:
//#line 446 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 134:
//#line 448 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 135:
//#line 450 "parser.y"
{
	        String mensaje = generador.generarTercetoValido("/", val_peek(2).sval, val_peek(0).sval, al.ts);
	        if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Division valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
        }
break;
case 136:
//#line 461 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 137:
//#line 463 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 138:
//#line 465 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 139:
//#line 467 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 140:
//#line 472 "parser.y"
{
            /* Verificamos que se pueda leer el IDCOMP.*/
            String mensaje = generador.puedoLeer(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break;
            }

            /* Verificamos que se encuetre al alcance.*/
            mensaje = generador.checkearAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            /*pongo en factor el valor necesario para el terceto*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 141:
//#line 491 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 142:
//#line 493 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 143:
//#line 495 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 144:
//#line 497 "parser.y"
{
            String mensaje = generador.generarTercetoValido("*", "-1L", val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            int indiceTerceto = generador.getUltimoTerceto();
            yyval.sval = indiceTerceto + "";
        }
break;
case 145:
//#line 508 "parser.y"
{
            errManager.debug("Identificador con -", al.getLine());

            /* Verificamos que se pueda leer el IDCOMP.*/
            String mensaje = generador.puedoLeer(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            /* Verificamos que se encuetre al alcance.*/
            mensaje = generador.checkearAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            /* Generamos el terceto correspondiente. Utilizamos un "-1L" previamente creado como auxiliar.*/
            mensaje = generador.generarTercetoValido("*", "-1L", val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            int indiceTerceto = generador.getUltimoTerceto();
            yyval.sval = indiceTerceto + "";
        }
break;
case 146:
//#line 536 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 147:
//#line 541 "parser.y"
{
	        errManager.debug("Llamado a funcion detectado", al.getLine());
	        String mensaje = generador.puedoLlamar(val_peek(3).sval, al.ts);
	        if (mensaje != null){
	            errManager.error(mensaje, al.getLine());
	            break ;
	        }

	        mensaje = generador.checkearAlcance(val_peek(3).sval, al.ts);
	        if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.checkearParametrosLlamada(val_peek(3).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.generarTercetosLlamado(val_peek(3).sval, al.ts);
            try{
                int indiceTerceto = Integer.parseInt(mensaje);
                yyval.sval = indiceTerceto + "";
            } catch (Exception e){
                errManager.error(mensaje, al.getLine());
            }

	    }
break;
case 148:
//#line 571 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 149:
//#line 573 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 150:
//#line 575 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 151:
//#line 580 "parser.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 152:
//#line 584 "parser.y"
{
	        errManager.debug("Definicion lambda detectada", al.getLine());

	    }
break;
case 153:
//#line 589 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 154:
//#line 591 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 155:
//#line 593 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 156:
//#line 595 "parser.y"
{ errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
break;
case 157:
//#line 600 "parser.y"
{
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", val_peek(0).sval, "-");
	    }
break;
case 158:
//#line 608 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 159:
//#line 610 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 160:
//#line 612 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 161:
//#line 614 "parser.y"
{ errManager.error("Faltan argumentos", al.getLine()); }
break;
case 162:
//#line 616 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 163:
//#line 618 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 164:
//#line 623 "parser.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 165:
//#line 625 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 166:
//#line 627 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 167:
//#line 629 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 172:
//#line 644 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 173:
//#line 649 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 174:
//#line 654 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 175:
//#line 659 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 176:
//#line 664 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 177:
//#line 666 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 178:
//#line 671 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1719 "Parser.java"
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
//## The -Jnorun option was used ##
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
