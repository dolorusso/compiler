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
    import Compilador.Assembler.Traductor;
//#line 24 "Parser.java"




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
    0,    0,   13,   24,   24,   25,   25,   25,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   27,   33,   33,
   33,   33,   35,   35,   34,   34,   34,   23,   23,   23,
   37,   37,   36,   36,   36,   39,   39,   39,   38,   38,
   38,   38,   38,   38,   38,   38,   38,   15,   15,   15,
   15,   15,   15,   15,   40,   40,    5,    5,   29,   29,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   17,   43,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   19,   20,   20,   20,
   20,   42,   42,   42,   42,   42,   21,   21,   41,   41,
   41,   41,   41,   22,    9,    9,    9,    9,    9,    9,
   16,   16,   28,   28,   28,   28,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    2,    2,    2,    2,
    2,    7,    7,    7,    7,   30,   11,   11,   10,   10,
   10,   10,    8,    8,   31,   31,    6,    6,    6,    6,
    6,    6,   32,   32,   32,   32,   12,   12,   44,   44,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    3,    3,    2,    4,    3,    3,    2,    2,    2,    2,
    1,    1,    2,    1,    1,    2,    2,    1,    1,    1,
    2,    2,    2,    1,    2,    2,    2,    2,    3,    2,
    2,    3,    3,    2,    3,    2,    0,    1,    2,    2,
    1,    2,    1,    3,    2,    1,    3,    2,    3,    2,
    3,    2,    1,    2,    3,    2,    2,    2,    3,    2,
    1,    2,    3,    2,    3,    2,    1,    1,    1,    1,
    4,    4,    3,    3,    4,    4,    3,    3,    4,    3,
    4,    3,    3,    3,    2,    1,    4,    4,    4,    4,
    3,    3,    2,    4,    4,    2,    1,    3,    2,    3,
    1,    3,    3,    2,    2,    3,    3,    2,    5,    5,
    4,    4,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    4,    4,    4,    3,    3,    1,    3,
    3,    2,    1,    2,    2,    3,    2,    3,    2,    3,
    2,    3,    2,    1,    1,    1,    1,    1,    2,    2,
    2,    4,    4,    4,    4,    2,    1,    3,    4,    3,
    3,    1,    4,    5,    2,    2,    3,    2,    3,    2,
    2,    1,    4,    5,    3,    4,    1,    3,    3,    1,
    1,    1,    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,   86,    0,    0,
  121,  114,  122,    0,   13,    0,    0,   69,    0,    0,
    0,    0,    0,   38,   14,   15,   18,   19,   20,    0,
    0,   24,    0,   70,    0,   10,    0,    0,    9,    0,
   40,    0,    0,   30,    0,    0,    0,  181,  182,  185,
    0,  172,    0,    0,    0,    0,  146,  144,    0,    0,
  157,  147,  156,    0,    0,  165,    0,    0,   25,   21,
    0,    0,    0,   17,   64,   60,   16,    0,    0,    0,
    0,   31,    0,   97,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    6,   39,   26,   22,
   27,   23,    0,   28,    0,    0,   85,    0,    5,    2,
    0,    0,    0,    0,    0,    0,    0,   34,    0,    0,
    0,   68,    0,    0,   43,    0,   46,    0,  135,  134,
  171,    0,  170,    0,  186,  187,  132,    0,    0,  183,
  184,  149,  139,    0,    0,  137,  143,  141,    0,    0,
  168,    0,    0,    0,    0,    0,    0,  178,  175,    0,
  180,    0,   63,   59,   29,   32,   53,   83,    0,   82,
    0,    0,  105,   80,    0,   84,   96,    0,    0,   93,
    0,    0,    0,   78,   74,  104,   42,    0,   77,   73,
    0,    0,    0,   36,    0,    0,  108,  115,  116,  117,
  118,  119,  120,    0,   99,    4,  126,  125,  155,   54,
   56,   50,    0,    0,    0,    0,   66,    0,    0,    0,
   57,   52,   33,    0,   45,  153,    0,   48,  158,  169,
  167,  138,  136,  142,  140,  128,    0,  131,    0,  124,
  123,  154,  152,    0,  176,  173,    0,   81,  106,   79,
  103,  102,    0,    0,    0,   92,    0,   91,   76,   72,
   75,   71,  112,  111,    0,  113,   35,  100,   98,    0,
   55,   51,   49,    0,    0,   65,    0,  161,   44,   47,
  174,  179,   95,   94,   90,   88,   89,   87,  110,  109,
    0,  163,  159,  164,
};
final static short yydgoto[] = {                          4,
   57,   58,   59,  119,  120,   61,   62,  121,  204,  122,
   63,   17,    5,   18,   19,   20,   21,   88,   89,  107,
  108,   22,   23,   24,   25,   26,   27,   28,   29,   30,
   31,   32,   33,  104,   44,  124,   91,  125,  126,  127,
   34,   92,   35,  162,
};
final static short yysindex[] = {                       -93,
    0,  362,  710,    0,  717,  -14,  -40,    0,   43,   57,
    0,    0,    0,  -26,    0,  -43,   59,    0,  -15,  -37,
  678,  364,  799,    0,    0,    0,    0,    0,    0,  -33,
   96,    0,  -41,    0,  106,    0,  819,  -18,    0,  821,
    0,  131,  -30,    0,  110,   10,  125,    0,    0,    0,
  141,    0, -231,  210,  -25,  174,    0,    0,  -20,  462,
    0,    0,    0,  149,  462,    0,  131,   69,    0,    0,
   52, -101,   49,    0,    0,    0,    0, -170,  145,  145,
  276,    0,  -12,    0,  134,   -3,  627,  166,  651,   51,
  333,   84,  106,  872,    0,   82,    0,    0,    0,    0,
    0,    0,  733,    0,  131,  466,    0,  159,    0,    0,
  -52,   76,  188,  110,  -21,  553,  -59,    0,   34, -138,
  316,    0,   25,  126,    0,   18,    0,   94,    0,    0,
    0,  233,    0,   -9,    0,    0,    0,  110,  125,    0,
    0,    0,    0, -231,  102,    0,    0,    0,  185,  192,
    0,  156,  167,   85,  237,   28,  -10,    0,    0,  124,
    0,  -38,    0,    0,    0,    0,    0,    0,  225,    0,
  195,  240,    0,    0,  880,    0,    0,   53,  844,    0,
    5,   42,  101,    0,    0,    0,    0,  109,    0,    0,
  112,  106,  250,    0,  851,  -22,    0,    0,    0,    0,
    0,    0,    0,  131,    0,    0,    0,    0,    0,    0,
    0,    0,   67,  238, -221,   74,    0,   77,  595,  900,
    0,    0,    0,  395,    0,    0,  114,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -20,    0,  -20,    0,
    0,    0,    0,   73,    0,    0,  -10,    0,    0,    0,
    0,    0,  279,  281,  113,    0,  120,    0,    0,    0,
    0,    0,    0,    0,  121,    0,    0,    0,    0,   34,
    0,    0,    0,   89,  312,    0,  902,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  325,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  375,    0,    0,    0,    0,   36,    0,    0,    0,
    0,    0,    0,  108,    0,    0,    0,    0,    0,    0,
    0,    0,  391,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,  408,    0,  433,
    0,    0,    0,    0,  415,    0,  437,    0,    0,    0,
    0,    0,  504,    0,    0,    0,    0,    0,  526,    0,
    0,    0,    0,    0,  143,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  236,  259,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  658,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  589,    0,    0,
  440,    0,    0,  556,    0,    0,    0,    0, -111,    0,
  -61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  459,  482,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  658,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  755,    0,  777,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  385,
    0,    0,    0,    0,    0,    0,    4,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -42,  382,  361,  859,    0,   -8,    2,    0,    0,    0,
    0,    0,    0,    0,    0,  746,    0,  467,    0,  -50,
  290,    0,  184,  436,    0,  -13,    0,    0,    0,    0,
    0,    0,    0,    0,  336,    0,  396,  -84,  383,  -89,
    0,   44,    0,  298,
};
final static int YYTABLESIZE=1178;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   37,   66,   81,   16,   16,  247,   16,   90,   95,  117,
  118,   55,   53,   68,   54,   70,   56,  144,  269,  145,
  246,  149,   16,   16,   16,  100,  150,  128,   78,    3,
  161,  231,  144,  152,  160,  153,  228,  130,   16,  225,
   41,   16,  191,   77,   41,  193,  135,  136,   11,   64,
   52,   55,   53,   13,   54,  142,   56,  117,  226,   55,
   53,  227,   54,  256,   56,   96,  228,  117,  243,   55,
   53,  227,   54,   95,   56,  181,  152,  187,  153,   61,
   95,  103,   51,   52,   55,   53,  163,   54,   16,   56,
   16,  144,   16,  160,   61,   16,   64,   52,   55,   53,
  258,   54,   72,   56,   16,  164,  110,   95,  117,  184,
   55,   53,  157,   54,  161,   56,  247,  217,  152,   73,
  153,  105,   16,   55,   53,   37,   54,  152,   56,  153,
  218,  281,  182,  117,  208,   55,   53,  280,   54,  279,
   56,  265,  189,  241,   67,  105,  142,   55,   53,  128,
   54,  177,   56,  117,  102,   55,   53,   67,   54,  260,
   56,  187,    1,    2,   68,   95,  223,  262,  177,  224,
  264,  286,   55,   53,  158,   54,   16,   56,  288,  290,
   16,  133,   55,   53,   81,   54,   37,   56,   40,  133,
   55,   53,  168,   54,  162,   56,   16,   55,  144,  205,
  145,  166,   56,  206,  282,   95,  187,  162,   55,  144,
   11,  145,   69,   56,  215,   13,  144,  245,  145,   79,
   16,   16,   99,   42,  176,  113,  114,  144,  209,  145,
  143,   45,  115,  268,  144,  210,  145,   67,   80,   11,
   74,   75,   46,  116,   13,   47,  230,   48,   49,   50,
   47,   42,   48,   49,   50,   84,  169,   37,   37,  160,
   76,   37,   37,  187,  255,  129,   45,   48,   49,   50,
   37,   37,  160,  229,   45,   37,   37,  242,   16,   62,
  221,  222,   46,  248,   45,   47,  195,   48,   49,   50,
   46,   61,   61,   47,   62,   48,   49,   50,  250,   45,
   46,  257,   58,   47,  159,   48,   49,   50,  266,   84,
  183,   61,  253,   45,   71,   46,  118,   58,   47,  249,
   48,   49,   50,  271,  155,   45,   48,   49,   50,   46,
  275,  207,   47,  276,   48,   49,   50,  283,   45,  284,
  240,   46,   84,  188,   47,  291,   48,   49,   50,  113,
   45,  101,  292,  192,   46,   82,  259,   47,  138,   48,
   49,   50,   45,  177,  261,  294,   46,  263,  285,   47,
   45,   48,   49,   50,   11,  287,  289,  139,   46,  140,
  141,   47,  167,   48,   49,   50,   46,   45,  115,   47,
    8,   48,   49,   50,  196,   11,  131,   45,  166,  116,
   13,  140,  141,   46,  131,   45,   47,    3,   48,   49,
   50,  236,   45,   46,  165,  166,   47,  132,   48,   49,
   50,   46,  238,   45,   47,  107,   48,   49,   50,  147,
   45,   47,    7,   48,   49,   50,  146,  148,  219,    1,
  232,   45,   47,  107,   48,   49,   50,  234,   45,   47,
  156,   48,   49,   50,  244,  148,  148,  148,   98,  148,
   47,  148,   48,   49,   50,  137,  138,   47,    0,   48,
   49,   50,   98,  148,  148,   98,  148,  145,  145,  145,
    0,  145,  175,  145,   15,  139,   94,  140,  141,  175,
    0,   62,   62,  272,  273,  145,  145,    0,  145,  151,
  151,  151,  151,  151,  152,  151,  153,  107,  152,  107,
  153,   62,  237,  239,   58,   58,  220,  151,  151,    0,
  151,    0,  150,  150,  150,  203,  150,  202,  150,    0,
  233,  235,  167,    0,   58,    0,    0,  148,  115,  148,
  150,  150,    0,  150,  129,   11,  129,    0,  129,  116,
   13,    0,  170,  174,    0,    0,  185,    0,  190,  145,
    0,  145,  129,  129,    0,  129,  133,    0,  133,    0,
  133,    0,   83,    8,  175,    0,    9,   10,    0,    0,
    0,  151,    0,  151,  133,  133,   12,  133,  186,   83,
    8,   14,    0,    9,   10,    0,   53,  148,  148,   53,
  148,    0,  148,   12,  150,    0,  150,    0,   14,    0,
    0,    0,    0,    0,  277,    0,    0,    6,    7,    8,
   83,    8,    9,   10,    9,   10,  129,    0,  129,    0,
   98,   11,   12,    0,   12,   93,   13,   14,    0,   14,
  107,  107,  107,  107,  107,  107,  107,  101,  133,    0,
  133,  167,    0,    0,    0,  107,    0,  115,    0,    0,
  107,    0,    0,    0,   11,    0,    0,    0,  116,   13,
  148,  148,  148,  148,  148,  148,  148,    0,    0,  148,
  148,  148,  148,  148,    0,  148,    0,    0,    0,    0,
  148,    0,  145,  145,  145,  145,  145,  145,  145,    0,
    0,  145,  145,  145,  145,  145,    0,  145,    0,  180,
    0,  101,  145,  101,  151,  151,  151,  151,  151,  151,
  151,  197,    0,  151,  151,  151,  151,  151,    0,  151,
  198,  199,  200,  201,  151,    0,    0,  150,  150,  150,
  150,  150,  150,  150,    0,    0,  150,  150,  150,  150,
  150,  173,  150,    0,    0,    0,    0,  150,    0,  129,
  129,  129,  129,  129,  129,  129,    0,    0,  129,  129,
  129,  129,  129,  179,  129,  178,    0,    0,    0,  129,
    0,  133,  133,  133,  133,  133,  133,  133,  123,    0,
  133,  133,  133,  133,  133,  127,  133,  127,    0,  127,
   87,  133,   86,    0,    0,    0,    0,    0,  211,  212,
    0,  148,   53,  127,  127,  213,  127,  130,   53,  130,
    0,  130,   11,    0,  148,   53,  123,   13,    0,   53,
   53,    0,    0,    0,   36,  130,  130,    0,  130,    0,
    0,   39,    0,    0,  101,  101,  101,  101,  101,  101,
  101,   83,    8,    0,    0,    9,   10,  194,    0,  101,
    0,  214,  216,    0,  101,   12,    0,   60,   65,  123,
   14,    0,    0,    0,    0,    0,    0,  127,    0,  127,
    0,    0,  171,   83,    8,   84,  172,    9,   10,    0,
    0,    0,    0,  106,    0,    0,    0,   12,    0,  130,
  112,  130,   14,    0,   60,    0,    0,   83,    8,  134,
  177,    9,   10,   41,   41,   41,    0,    0,   41,   41,
    0,   12,  134,   97,    0,  154,   14,    0,   41,    0,
    0,    0,    0,   41,   83,    8,   84,   85,    9,   10,
    0,    0,    0,  109,    0,  111,    0,    0,   12,    0,
    0,  106,    0,   14,  106,    0,    0,    0,    0,    0,
  274,    0,    0,  106,    0,    6,    7,    8,  173,  123,
    9,   10,   38,    7,    8,  267,    0,    9,   10,   11,
   12,    0,    0,    0,   13,   14,   11,   12,    6,    7,
    8,   13,   14,    9,   10,    0,  173,    0,    0,    0,
    0,    0,   11,   12,  252,    0,    0,   13,   14,    0,
  127,  127,  127,  127,  127,  127,  127,    0,    0,  127,
  127,  127,  127,  127,  278,  127,  293,    0,    0,    0,
  127,    0,  130,  130,  130,  130,  130,  130,  130,    0,
    0,  130,  130,  130,  130,  130,    0,  130,    0,    0,
  106,    0,  130,    0,    0,    7,    8,    0,    0,    9,
   10,    0,  270,    0,    0,    0,    0,    0,   11,   12,
    0,    0,    0,   13,   14,    7,    8,    7,    8,    9,
   10,    9,   10,    0,    0,    0,    0,    0,   11,   12,
   11,   12,    0,   13,   14,   13,   14,    0,    0,  171,
   83,    8,    0,  254,    9,   10,    0,    7,    8,    0,
    0,    9,   10,    0,   12,    0,    0,    0,    0,   14,
   11,   12,    0,    0,    0,   13,   14,  171,   83,    8,
    0,    0,    9,   10,    0,  251,   83,    8,    0,    0,
    9,   10,   12,    0,    0,    0,    0,   14,    0,    0,
   12,    0,    0,    0,    0,   14,   83,    8,   83,    8,
    9,   10,    9,   10,    0,    0,    0,    0,    0,    0,
   12,    0,   12,    0,    0,   14,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   10,   40,    2,    3,   44,    5,   21,   22,   40,
   41,   42,   43,   40,   45,   59,   47,   43,   41,   45,
   59,   42,   21,   22,   23,   59,   47,   40,   44,  123,
   73,   41,   43,   43,   45,   45,  126,   46,   37,  124,
   59,   40,   93,   59,   59,   96,  278,  279,  270,   40,
   41,   42,   43,  275,   45,   54,   47,   40,   41,   42,
   43,   44,   45,   59,   47,   22,  156,   40,   41,   42,
   43,   44,   45,   87,   47,   89,   43,   91,   45,   44,
   94,  123,   40,   41,   42,   43,  257,   45,   87,   47,
   89,   43,   91,   45,   59,   94,   40,   41,   42,   43,
   59,   45,   44,   47,  103,  276,  125,  121,   40,   59,
   42,   43,   61,   45,  157,   47,   44,  256,   43,   61,
   45,   40,  121,   42,   43,  125,   45,   43,   47,   45,
  269,   59,   89,   40,   59,   42,   43,  227,   45,  224,
   47,  192,   59,   59,  256,   40,  145,   42,   43,   40,
   45,   44,   47,   40,   59,   42,   43,  269,   45,   59,
   47,  175,  256,  257,   40,  179,   41,   59,   61,   44,
   59,   59,   42,   43,  276,   45,  175,   47,   59,   59,
  179,   41,   42,   43,   40,   45,    3,   47,    5,   41,
   42,   43,   59,   45,  256,   47,  195,   42,   43,   41,
   45,   59,   47,  256,  247,  219,  220,  269,   42,   43,
  270,   45,  256,   47,  274,  275,   43,  256,   45,  257,
  219,  220,  256,  264,   59,  256,  257,   43,   41,   45,
  256,  257,  263,  256,   43,  257,   45,  264,  276,  270,
  256,  257,  273,  274,  275,  276,  256,  278,  279,  280,
  276,  264,  278,  279,  280,  259,  260,  257,  258,  256,
  276,  261,  262,  277,  260,  256,  257,  278,  279,  280,
  270,  271,  269,   41,  257,  275,  276,   41,  277,   44,
  256,  257,  273,   59,  257,  276,  103,  278,  279,  280,
  273,  256,  257,  276,   59,  278,  279,  280,   59,  257,
  273,  260,   44,  276,  256,  278,  279,  280,   59,  259,
  260,  276,  260,  257,  256,  273,   41,   59,  276,  125,
  278,  279,  280,  257,  256,  257,  278,  279,  280,  273,
  257,  256,  276,  257,  278,  279,  280,   59,  257,   59,
  256,  273,  259,  260,  276,  257,  278,  279,  280,  256,
  257,  256,   41,  272,  273,   20,  256,  276,  257,  278,
  279,  280,  257,  256,  256,   41,  273,  256,  256,  276,
  257,  278,  279,  280,    0,  256,  256,  276,  273,  278,
  279,  276,  257,  278,  279,  280,  273,  257,  263,  276,
    0,  278,  279,  280,  105,  270,  256,  257,  256,  274,
  275,  278,  279,  273,  256,  257,  276,    0,  278,  279,
  280,  256,  257,  273,   79,   80,  276,  277,  278,  279,
  280,  273,  256,  257,  276,   41,  278,  279,  280,  256,
  257,  276,    0,  278,  279,  280,   55,   56,  123,    0,
  256,  257,  276,   59,  278,  279,  280,  256,  257,  276,
   68,  278,  279,  280,  157,   41,   42,   43,   23,   45,
  276,   47,  278,  279,  280,  256,  257,  276,   -1,  278,
  279,  280,   37,   59,   60,   40,   62,   41,   42,   43,
   -1,   45,   87,   47,  123,  276,  123,  278,  279,   94,
   -1,  256,  257,  256,  257,   59,   60,   -1,   62,   41,
   42,   43,   41,   45,   43,   47,   45,  123,   43,  125,
   45,  276,  152,  153,  256,  257,  121,   59,   60,   -1,
   62,   -1,   41,   42,   43,   60,   45,   62,   47,   -1,
  149,  150,  257,   -1,  276,   -1,   -1,  123,  263,  125,
   59,   60,   -1,   62,   41,  270,   43,   -1,   45,  274,
  275,   -1,   86,   87,   -1,   -1,   90,   -1,   92,  123,
   -1,  125,   59,   60,   -1,   62,   41,   -1,   43,   -1,
   45,   -1,  257,  258,  179,   -1,  261,  262,   -1,   -1,
   -1,  123,   -1,  125,   59,   60,  271,   62,  256,  257,
  258,  276,   -1,  261,  262,   -1,   41,   42,   43,   44,
   45,   -1,   47,  271,  123,   -1,  125,   -1,  276,   -1,
   -1,   -1,   -1,   -1,  219,   -1,   -1,  256,  257,  258,
  257,  258,  261,  262,  261,  262,  123,   -1,  125,   -1,
  195,  270,  271,   -1,  271,  272,  275,  276,   -1,  276,
  256,  257,  258,  259,  260,  261,  262,   59,  123,   -1,
  125,  257,   -1,   -1,   -1,  271,   -1,  263,   -1,   -1,
  276,   -1,   -1,   -1,  270,   -1,   -1,   -1,  274,  275,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,   -1,  271,   -1,   -1,   -1,   -1,
  276,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,   59,
   -1,  123,  276,  125,  256,  257,  258,  259,  260,  261,
  262,  256,   -1,  265,  266,  267,  268,  269,   -1,  271,
  265,  266,  267,  268,  276,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,  125,  271,   -1,   -1,   -1,   -1,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,  123,  271,  125,   -1,   -1,   -1,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,   43,   -1,
  265,  266,  267,  268,  269,   41,  271,   43,   -1,   45,
  123,  276,  125,   -1,   -1,   -1,   -1,   -1,  256,  257,
   -1,  256,  257,   59,   60,  263,   62,   41,  263,   43,
   -1,   45,  270,   -1,  269,  270,   81,  275,   -1,  274,
  275,   -1,   -1,   -1,  125,   59,   60,   -1,   62,   -1,
   -1,  125,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,  257,  258,   -1,   -1,  261,  262,  125,   -1,  271,
   -1,  116,  117,   -1,  276,  271,   -1,    9,   10,  124,
  276,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   35,   -1,   -1,   -1,  271,   -1,  123,
   42,  125,  276,   -1,   46,   -1,   -1,  257,  258,   51,
  260,  261,  262,  256,  257,  258,   -1,   -1,  261,  262,
   -1,  271,   64,  125,   -1,   67,  276,   -1,  271,   -1,
   -1,   -1,   -1,  276,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,  125,   -1,  125,   -1,   -1,  271,   -1,
   -1,   93,   -1,  276,   96,   -1,   -1,   -1,   -1,   -1,
  215,   -1,   -1,  105,   -1,  256,  257,  258,  125,  224,
  261,  262,  256,  257,  258,  125,   -1,  261,  262,  270,
  271,   -1,   -1,   -1,  275,  276,  270,  271,  256,  257,
  258,  275,  276,  261,  262,   -1,  125,   -1,   -1,   -1,
   -1,   -1,  270,  271,  125,   -1,   -1,  275,  276,   -1,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,  125,  271,  125,   -1,   -1,   -1,
  276,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,   -1,
  192,   -1,  276,   -1,   -1,  257,  258,   -1,   -1,  261,
  262,   -1,  204,   -1,   -1,   -1,   -1,   -1,  270,  271,
   -1,   -1,   -1,  275,  276,  257,  258,  257,  258,  261,
  262,  261,  262,   -1,   -1,   -1,   -1,   -1,  270,  271,
  270,  271,   -1,  275,  276,  275,  276,   -1,   -1,  256,
  257,  258,   -1,  260,  261,  262,   -1,  257,  258,   -1,
   -1,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,  276,
  270,  271,   -1,   -1,   -1,  275,  276,  256,  257,  258,
   -1,   -1,  261,  262,   -1,  256,  257,  258,   -1,   -1,
  261,  262,  271,   -1,   -1,   -1,   -1,  276,   -1,   -1,
  271,   -1,   -1,   -1,   -1,  276,  257,  258,  257,  258,
  261,  262,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
  271,   -1,  271,   -1,   -1,  276,   -1,  276,
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
"parametro_real_compuesto : parametro_real error",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF",
"control : do_until",
"sentencia_IF : inicio_if cuerpo_opt ENDIF ';'",
"sentencia_IF : inicio_if sentencia_ejecutable ENDIF ';'",
"sentencia_IF : inicio_if cuerpo_opt else_opt",
"sentencia_IF : inicio_if sentencia_ejecutable else_opt",
"sentencia_IF : inicio_if cuerpo_opt ENDIF error",
"sentencia_IF : inicio_if sentencia_ejecutable ENDIF error",
"sentencia_IF : inicio_if cuerpo_opt ';'",
"sentencia_IF : inicio_if sentencia_ejecutable ';'",
"sentencia_IF : inicio_if '{' ENDIF ';'",
"sentencia_IF : inicio_if '{' else_opt",
"sentencia_IF : inicio_if '}' ENDIF ';'",
"sentencia_IF : inicio_if '}' else_opt",
"sentencia_IF : inicio_if ENDIF ';'",
"sentencia_IF : inicio_if else_opt ';'",
"inicio_if : inicio_if_sin_condicion condicional_opt",
"inicio_if_sin_condicion : IF",
"else_opt : inicio_else cuerpo_opt ENDIF ';'",
"else_opt : inicio_else sentencia_ejecutable ENDIF ';'",
"else_opt : inicio_else cuerpo_opt ENDIF error",
"else_opt : inicio_else sentencia_ejecutable ENDIF error",
"else_opt : inicio_else cuerpo_opt ';'",
"else_opt : inicio_else sentencia_ejecutable ';'",
"else_opt : inicio_else ';'",
"else_opt : inicio_else '{' ENDIF ';'",
"else_opt : inicio_else '}' ENDIF ';'",
"else_opt : inicio_else ENDIF",
"inicio_else : ELSE",
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
"do_until : inicio_do cuerpo_opt UNTIL condicional_opt ';'",
"do_until : inicio_do cuerpo_opt UNTIL condicional_opt error",
"do_until : inicio_do UNTIL condicional_opt ';'",
"do_until : inicio_do UNTIL condicional_opt error",
"do_until : inicio_do cuerpo_opt condicional_opt ';'",
"inicio_do : DO",
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
"print : PRINT cuerpo_print",
"cuerpo_print : cuerpo_expresion",
"cuerpo_print : '(' CADENASTR ')'",
"lambda : inicio_lambda '{' lista_sentencias_ejecutables '}'",
"lambda : inicio_lambda '{' lista_sentencias_ejecutables",
"lambda : inicio_lambda lista_sentencias_ejecutables '}'",
"lambda : inicio_lambda",
"inicio_lambda : '(' tipo ID ')'",
"inicio_lambda : '(' CR tipo ID ')'",
"retorno : RETURN cuerpo_expresion",
"retorno : RETURN expresion",
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

//#line 866 "parser.y"
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
    } else{
        errManager.error("Error en compilacion", al.getLine());
        throw new RuntimeException("Error en compilacion, no se procedera a la traduccion.");
    }
}

public void yyerror(String s) {
}
//#line 818 "Parser.java"
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
//#line 27 "parser.y"
{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
		    generador.agregarTerceto("fin_main", val_peek(2).sval, "-");
		}
break;
case 2:
//#line 33 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 35 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 37 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 42 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 44 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 46 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 48 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 50 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 52 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 54 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 56 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 13:
//#line 61 "parser.y"
{
            generador.enterScope(val_peek(1).sval);
            generador.agregarTerceto("inicio_main", val_peek(1).sval, "-");
            yyval.sval = val_peek(1).sval;
        }
break;
case 17:
//#line 76 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 84 "parser.y"
{ generador.agregarTerceto("drop", "-", "-"); }
break;
case 23:
//#line 87 "parser.y"
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
//#line 99 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 101 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 103 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 28:
//#line 108 "parser.y"
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
//#line 124 "parser.y"
{
            /* Entramos al scope de la funcion.*/
            generador.enterScope(val_peek(1).sval);
            /* Checkeamos si esta declarada usando el scope, ya que este sera el mangle name de la funcion.*/
            String ambitoFuncion = generador.getCurrentScope();
            if (generador.estaDeclarada(ambitoFuncion, al.ts)){
                errManager.error("Redeclaracion de funcion no permitida.", al.getLine());
            } else {
                /* Generamos las entradas solo si es correcta.*/
                generador.aplicarAmbito(al.ts,val_peek(2).ival, ambitoFuncion);

                /*Generamos el terceto correspondiente al inicio de la a funcion.*/
                generador.agregarTerceto("inicio_funcion", ambitoFuncion, "-", val_peek(2).ival);
            }
        }
break;
case 30:
//#line 140 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 142 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 144 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 149 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 154 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 155 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 163 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 181 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 186 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 191 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 193 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 195 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 200 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 202 "parser.y"
{
	        errManager.debug("Parametro formal lambda semantica detectado", al.getLine());
	        generador.agregarParametro(false,Atributo.lambdaType,val_peek(0).sval);
	    }
break;
case 55:
//#line 207 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 209 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 211 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 216 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 221 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 225 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 227 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 229 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 231 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 233 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 240 "parser.y"
{
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal(val_peek(2).sval, val_peek(0).sval);
	    }
break;
case 66:
//#line 245 "parser.y"
{
            errManager.error("Parametro real debe ser vinculado a formal (->)", al.getLine());
        }
break;
case 69:
//#line 257 "parser.y"
{
	        generador.agregarTerceto("endif", val_peek(0).sval, "-");
	    }
break;
case 71:
//#line 266 "parser.y"
{
	        errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 2; /*+2 por terceto endif*/

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
            yyval.sval = "if";

	    }
break;
case 72:
//#line 278 "parser.y"
{
    	    errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 2;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
            yyval.sval = "if";
    	}
break;
case 73:
//#line 289 "parser.y"
{
	        errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
            yyval.sval = "else";
	    }
break;
case 74:
//#line 297 "parser.y"
{
            errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
            yyval.sval = "else";
        }
break;
case 75:
//#line 305 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 307 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 77:
//#line 309 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 311 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 79:
//#line 313 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 315 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 81:
//#line 317 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 319 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 83:
//#line 321 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 84:
//#line 323 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 85:
//#line 328 "parser.y"
{
            /* $2 = indice de terceto condicion.*/
            /* Generar BF con destino desconocido.*/
            int idxBF = generador.generarBF(val_peek(0).ival);
            yyval.ival = idxBF;
        }
break;
case 86:
//#line 336 "parser.y"
{ generador.agregarTerceto("if_inicio", "-", "-"); }
break;
case 87:
//#line 340 "parser.y"
{
            /* Backpatch BF para saltar al Else.*/
            /*Lo pasamos para arriba asi se rellena el BF*/
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque else.*/
            int afterElse = generador.getUltimoTerceto() + 2;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 88:
//#line 352 "parser.y"
{
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque else.*/
            int afterElse = generador.getUltimoTerceto() + 2;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 89:
//#line 362 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 90:
//#line 364 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 91:
//#line 366 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 92:
//#line 368 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 93:
//#line 370 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 94:
//#line 372 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 95:
//#line 374 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 96:
//#line 376 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 97:
//#line 381 "parser.y"
{
          int idxBI = generador.generarBI();
          /*devolvemos la instruccion donde se genero el BI*/
          yyval.ival = idxBI;
        }
break;
case 98:
//#line 390 "parser.y"
{ yyval.ival = val_peek(1).ival; }
break;
case 99:
//#line 392 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 100:
//#line 394 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 101:
//#line 396 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 103:
//#line 402 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 104:
//#line 404 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 105:
//#line 406 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 106:
//#line 408 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 107:
//#line 414 "parser.y"
{
	        errManager.debug("Condicion detectada. Linea: " + al.getLine());

            String opLexema = val_peek(1).sval; /* adaptar si $2 no es string*/
            String mensaje = generador.generarTercetoValido(opLexema, val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null) {
                errManager.error(mensaje, al.getLine());
                break;
            } else {
                yyval.ival = generador.getUltimoTerceto(); /* indice del terceto de la condición*/
            }
	     }
break;
case 108:
//#line 427 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 109:
//#line 432 "parser.y"
{
	        errManager.debug("DO-UNTIL detectado", al.getLine());
            int indiceInicio = val_peek(4).ival;   /* primer terceto del cuerpo*/
            int indiceCondicion  = val_peek(1).ival;   /* indice del terceto que representa la condicion*/

            /* Generamos BF y luego rellenamos su destino al inicio del cuerpo.*/
            int indiceBF = generador.generarBF(indiceCondicion);
            generador.rellenarOperando(indiceBF, indiceInicio, 2);
	    }
break;
case 110:
//#line 442 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 111:
//#line 444 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 112:
//#line 446 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 113:
//#line 448 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 114:
//#line 453 "parser.y"
{
            /* Guardamos la direccion de inicio del DO.*/
            generador.agregarTerceto("do_inicio","-","-");
            yyval.ival = generador.getUltimoTerceto() + 1;
        }
break;
case 115:
//#line 461 "parser.y"
{ yyval.sval = "<="; }
break;
case 116:
//#line 463 "parser.y"
{ yyval.sval = ">="; }
break;
case 117:
//#line 465 "parser.y"
{ yyval.sval = "=="; }
break;
case 118:
//#line 467 "parser.y"
{ yyval.sval = "!="; }
break;
case 119:
//#line 469 "parser.y"
{ yyval.sval = ">"; }
break;
case 120:
//#line 471 "parser.y"
{ yyval.sval = "<"; }
break;
case 121:
//#line 476 "parser.y"
{ yyval.ival = 0; }
break;
case 122:
//#line 478 "parser.y"
{ errManager.error("Tipo string no permitido.",al.getLine()); }
break;
case 123:
//#line 483 "parser.y"
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
case 124:
//#line 507 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 125:
//#line 509 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 126:
//#line 511 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 127:
//#line 516 "parser.y"
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
case 128:
//#line 527 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 129:
//#line 529 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 130:
//#line 531 "parser.y"
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
case 131:
//#line 542 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 132:
//#line 544 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 133:
//#line 546 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 134:
//#line 548 "parser.y"
{
	        errManager.debug("Trunc detectado", al.getLine());
	        String mensaje = generador.validarExpresionTrunc(val_peek(0).sval, al.ts);
	        if (mensaje != null){
	            errManager.error(mensaje, al.getLine());
	            break ;
	        }
	        /* Generamos el terceto de salida. Al ser trunc devuelve un long .*/
	        int indiceTerceto = generador.agregarTerceto("trunc", val_peek(0).sval, "-", Atributo.longType);
	        yyval.sval = indiceTerceto + "";
        }
break;
case 135:
//#line 560 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 136:
//#line 565 "parser.y"
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
case 137:
//#line 576 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 138:
//#line 578 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 139:
//#line 580 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 140:
//#line 582 "parser.y"
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
case 141:
//#line 593 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 142:
//#line 595 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 143:
//#line 597 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 144:
//#line 599 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 145:
//#line 604 "parser.y"
{
            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break;
            }

            /*pongo en factor el valor necesario para el terceto*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 146:
//#line 616 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 147:
//#line 618 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 148:
//#line 620 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 149:
//#line 622 "parser.y"
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
case 150:
//#line 633 "parser.y"
{
            errManager.debug("Identificador con -", al.getLine());

            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
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
case 151:
//#line 655 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 152:
//#line 660 "parser.y"
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
case 153:
//#line 690 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 154:
//#line 692 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 155:
//#line 694 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 156:
//#line 699 "parser.y"
{
	        errManager.debug("Print detectado", al.getLine());
	        generador.agregarTerceto("print", val_peek(0).sval, "-");
	    }
break;
case 157:
//#line 706 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 158:
//#line 708 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 159:
//#line 712 "parser.y"
{
	        errManager.debug("Definicion lambda detectada", al.getLine());

	        generador.agregarTerceto("fin_funcion", val_peek(3).sval, "-");
	        generador.exitScope();

	        yyval.sval = val_peek(3).sval;
	    }
break;
case 160:
//#line 721 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 161:
//#line 723 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 162:
//#line 725 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 163:
//#line 730 "parser.y"
{
            /* Obtenemos un nombre para la funcion lambda.*/
            String lamName = generador.getLambdaName();
            /* Entramos al scope de la funcion.*/
            generador.enterScope(lamName);
            String lamScope = generador.getCurrentScope();

            /* Agregamos el parametro, esto para reutilizar los metodos de funcion.*/
            generador.agregarParametro(false, val_peek(2).ival, val_peek(1).sval);
            /* Aplica ambito a los parametros y crea el terceto de funcion.*/
            generador.aplicarAmbito(al.ts, Atributo.lambdaType, lamScope);

            /*Generamos el terceto correspondiente al inicio de la a funcion.*/
            generador.agregarTerceto("inicio_funcion", lamScope, "-");
            yyval.sval = lamScope;
        }
break;
case 164:
//#line 747 "parser.y"
{ errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
break;
case 165:
//#line 753 "parser.y"
{
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", val_peek(0).sval, "-");
	    }
break;
case 166:
//#line 758 "parser.y"
{ errManager.error("Faltan ambos parentesis del return", al.getLine()); }
break;
case 167:
//#line 764 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 168:
//#line 766 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 169:
//#line 768 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 170:
//#line 770 "parser.y"
{ errManager.error("Faltan argumentos", al.getLine()); }
break;
case 171:
//#line 772 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 172:
//#line 774 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 173:
//#line 779 "parser.y"
{
            errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());
            /* Se checkeo alcance y lectura de las ID, por lo que solo resta verificar los tipos y*/
            /* realizar las asignaciones (generar tercetos).*/

            String mensaje = generador.generarAsignacionMultiple(al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            generador.limpiarAsignacionMultiple();

        }
break;
case 174:
//#line 794 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 175:
//#line 796 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 176:
//#line 798 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 177:
//#line 803 "parser.y"
{
            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            generador.agregarIDMultiple(val_peek(0).sval, al.ts);
        }
break;
case 178:
//#line 814 "parser.y"
{
            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            generador.agregarIDMultiple(val_peek(0).sval, al.ts);
        }
break;
case 179:
//#line 828 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 180:
//#line 830 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 181:
//#line 835 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 182:
//#line 840 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 183:
//#line 845 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 184:
//#line 850 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 185:
//#line 855 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 186:
//#line 857 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 187:
//#line 862 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1979 "Parser.java"
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
