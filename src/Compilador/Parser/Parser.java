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
    0,    0,   19,   21,   21,   22,   22,   22,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   24,   30,   30,
   30,   30,   32,   32,   31,   31,   31,   20,   20,   20,
   34,   34,   33,   33,   33,   36,   36,   36,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   12,   12,   12,
   12,   12,   12,   12,   37,    5,    5,   26,   26,   38,
   38,   38,   38,   38,   38,   38,   38,   38,   38,   38,
   38,   38,   38,   14,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   16,   17,   17,   17,   17,   40,
   40,   40,   40,   40,   18,   18,   39,   39,   39,   39,
   39,    9,    9,    9,    9,    9,    9,   13,   13,   25,
   25,   25,   25,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,    2,    2,    2,    2,    2,    7,    7,
    7,    7,   27,   11,   11,   10,   10,   10,   10,    8,
    8,   28,    6,    6,    6,    6,    6,    6,   29,   29,
   29,   29,   41,   41,   42,   42,    1,    1,    1,    1,
    1,    1,    1,
};
final static short yylen[] = {                            2,
    3,    3,    2,    4,    3,    3,    2,    2,    2,    2,
    1,    1,    2,    1,    1,    2,    2,    1,    1,    1,
    2,    2,    2,    1,    2,    2,    2,    2,    3,    2,
    2,    3,    3,    2,    3,    2,    0,    1,    2,    2,
    1,    2,    1,    3,    2,    1,    3,    2,    3,    2,
    3,    2,    1,    2,    3,    2,    2,    2,    3,    2,
    1,    2,    3,    2,    3,    1,    1,    1,    1,    4,
    4,    3,    3,    4,    4,    3,    3,    4,    3,    4,
    3,    3,    3,    2,    4,    4,    4,    4,    3,    3,
    2,    4,    4,    2,    1,    3,    2,    3,    1,    3,
    3,    2,    2,    3,    3,    2,    5,    5,    4,    4,
    4,    1,    1,    1,    1,    1,    1,    1,    1,    4,
    4,    4,    4,    3,    3,    1,    3,    3,    2,    1,
    2,    2,    3,    2,    3,    2,    3,    2,    3,    2,
    1,    1,    1,    1,    1,    2,    2,    2,    4,    4,
    4,    4,    2,    1,    3,    4,    3,    3,    1,    4,
    5,    2,    3,    2,    3,    2,    2,    1,    4,    5,
    3,    4,    1,    3,    3,    1,    1,    1,    2,    2,
    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  118,    0,  119,    0,   13,    0,    0,    0,    0,    0,
   38,   14,   15,   18,   19,   20,    0,    0,   24,    0,
   68,   69,    0,   10,    0,    0,    9,    0,   40,    0,
    0,   30,    0,    0,    0,  177,  178,  181,    0,    0,
    0,    0,    0,  143,  141,    0,    0,  144,   84,    0,
    0,  168,    0,  154,  153,    0,  162,    0,    0,    0,
   41,    0,    0,    0,    0,   25,   21,   17,   64,   60,
   16,    0,    0,    0,    0,   31,   95,    0,    0,    0,
    0,    0,    0,    0,    6,   39,   26,   22,   27,   23,
    0,   28,    0,    0,    0,    5,    2,    0,    0,    0,
    0,    0,    0,    0,   34,    0,    0,    0,   67,    0,
    0,   43,    0,   46,    0,  132,  131,    0,  182,  183,
  129,    0,    0,  179,  180,  146,  136,    0,    0,  134,
  140,  138,    0,    0,  106,  112,  113,  114,  115,  116,
  117,    0,    0,    0,   97,  167,    0,  166,    0,  164,
    0,    0,  103,    0,  102,   42,    0,    0,    0,    0,
    0,   63,   59,   29,   32,   53,   82,    0,   81,    0,
   79,   83,   94,    0,    0,   91,    0,    0,    0,   77,
   73,    0,   76,   72,   36,    0,    0,  174,  171,    0,
  176,    0,    4,  123,  122,  152,   54,   56,   50,    0,
    0,    0,    0,    0,    0,    0,   57,   52,   33,    0,
   45,  150,    0,   48,   98,   96,  135,  133,  139,  137,
  125,    0,  128,    0,    0,  155,  165,  163,  110,  109,
  104,  101,  100,    0,  111,  121,  120,  151,  149,   80,
   78,    0,    0,    0,   90,    0,   89,   75,   71,   74,
   70,   35,    0,  172,  169,    0,   55,   51,   49,    0,
    0,   65,    0,  158,   44,   47,  108,  107,   93,   92,
   88,   86,   87,   85,  170,  175,    0,  160,  156,  161,
};
final static short yydgoto[] = {                          4,
   54,   55,   56,  116,  117,   64,   58,  118,  154,  119,
   65,   17,   18,   19,   91,   92,   59,   60,    5,   20,
   21,   22,   23,   24,   25,   26,   27,   28,   29,   30,
  102,   42,  121,   72,  122,  123,  124,   31,   32,   73,
   33,  202,
};
final static short yysindex[] = {                       -75,
    0,  708,  741,    0,  763,   23,  -38,  120,   70,   82,
    0,  224,    0,  -24,    0,  -47,  100,  -16,  734,  393,
    0,    0,    0,    0,    0,    0,  -20,   42,    0,  -80,
    0,    0,  -19,    0,  807,   -4,    0,  818,    0,  182,
   17,    0,   12,   26,   50,    0,    0,    0,  182, -250,
  283,   62,  236,    0,    0,   92,  587,    0,    0,  110,
  147,    0,  387,    0,    0,  158,    0,  -17,  120,  865,
    0,  701,  -34,  182,   95,    0,    0,    0,    0,    0,
    0, -230,  124,  124,  202,    0,    0,  109,  -74,  770,
  134,  684,  -40,   77,    0,    0,    0,    0,    0,    0,
  787,    0,   53,  -72,  -28,    0,    0,  -73,   11,  167,
   12,  -43,   51, -225,    0,  135,  -37,  662,    0,  -60,
  172,    0,   44,    0,  107,    0,    0,  -41,    0,    0,
    0,   12,   50,    0,    0,    0,    0, -250,  112,    0,
    0,    0,  241,  266,    0,    0,    0,    0,    0,    0,
    0,  188,  223,  182,    0,    0,  220,    0,   -7,    0,
   47,  144,    0,  876,    0,    0,  120,  219,   73,  244,
   55,    0,    0,    0,    0,    0,    0,  235,    0,  251,
    0,    0,    0,   40,  842,    0,  -26,  -12,   69,    0,
    0,   74,    0,    0,    0,  849,  -25,    0,    0,  -57,
    0,   60,    0,    0,    0,    0,    0,    0,    0,   56,
  184, -117,   58,   75,  905,  882,    0,    0,    0,  371,
    0,    0,  132,    0,    0,    0,    0,    0,    0,    0,
    0,   92,    0,   92,  135,    0,    0,    0,    0,    0,
    0,    0,    0,   89,    0,    0,    0,    0,    0,    0,
    0,  285,  300,   97,    0,   98,    0,    0,    0,    0,
    0,    0,  102,    0,    0,  -25,    0,    0,    0,  108,
  326,    0,  888,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  329,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  372,    0,    0,    0,    0,  125,    0,    0,    0,
    0,    0,    0,   32,    0,    0,    0,    0,    0,  378,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,  379,    0,  395,    0,    0,
    0,    0,  428,    0,  466,    0,    0,    0,    0,  535,
    0,    0,    0,    0,    0,  557,    0,    0,    0,  642,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  348,  350,    0,    0,    0,    0,    0,    0,
    0,    0,  899,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  397,    0,    0,
  665,    0,    0,    0,    0,  133,    0,  137,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  488,  513,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  899,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  580,    0,  616,  325,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  150,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -54,   27,  104,  907,    0,   21,    2,    0,    0,    0,
    0,    0,  453,    0,  328,    0,  122,  364,    0,   72,
    6,    0,   -9,    0,    0,    0,    0,    0,    0,    0,
    0,  127,    0,  414,  -89,  341,  -93,    0,    0,   16,
    0,  246,
};
final static int YYTABLESIZE=1181;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        226,
   37,   41,   71,   16,   16,   49,   16,   52,   50,   93,
   51,   77,   53,   16,  138,   75,  200,  138,  190,  200,
   16,   16,  125,   85,  104,   96,  172,  129,  130,  224,
   67,  221,  255,  238,   94,  152,   16,  153,   98,   16,
   96,  105,  101,   96,   11,  173,  257,    3,  212,   13,
  201,  125,  136,  152,   39,  153,  114,  115,   52,   50,
   71,   51,  166,   53,  127,   66,   62,   52,   50,  205,
   51,   16,   53,   16,   35,  173,   38,  224,  140,  142,
   71,   39,  187,  114,  222,   52,   50,  223,   51,   75,
   53,   16,  173,   16,  114,  249,   52,   50,  223,   51,
  100,   53,   16,  266,  138,  240,  139,  188,   71,   61,
   62,   52,   50,  197,   51,  152,   53,  153,  265,   16,
  107,   66,   62,   52,   50,   37,   51,  259,   53,  276,
  275,  247,  261,  143,  114,  193,   52,   50,  144,   51,
  136,   53,  201,   82,   86,  266,  114,  278,   52,   50,
  155,   51,   11,   53,  166,  282,  284,   13,   81,   49,
  285,   52,   50,   85,   51,   16,   53,  177,   61,  228,
  230,  114,  196,   52,   50,   71,   51,  152,   53,  153,
    1,    2,  203,   61,   87,  178,   16,  158,   52,   50,
  161,   51,  182,   53,  168,  217,  218,   16,  158,   52,
   50,   96,   51,  198,   53,   71,  166,  206,   76,  174,
  175,  286,  219,  207,  225,  220,   16,   16,   87,  189,
  134,  135,   43,   52,   50,   40,   51,  199,   53,   52,
  138,  214,  139,  254,   53,   97,  103,  167,   44,   74,
   83,   45,  115,   46,   47,   48,   40,  256,  237,   46,
   47,   48,   46,   47,   48,  232,  234,   37,   37,   84,
  236,   37,   37,  166,   52,  138,  204,  139,  241,   53,
   37,   37,  110,  111,   16,   37,   37,  245,  138,  112,
  139,  126,   43,  138,  248,  139,   11,  173,  244,   44,
  113,   13,   45,  250,   46,   47,   48,   99,   44,  252,
   43,   45,  239,   46,   47,   48,  208,  209,  138,  251,
  139,   43,  267,  210,  271,  264,   44,  137,   43,   45,
   11,   46,   47,   48,  258,   13,   43,   44,  246,  260,
   45,  272,   46,   47,   48,   87,  192,   45,   43,   46,
   47,   48,   44,  279,  277,   45,   70,   46,   47,   48,
  170,   43,  281,  283,   44,   78,   79,   45,  280,   46,
   47,   48,  110,   43,  287,  105,  288,   44,  132,  290,
   45,   11,   46,   47,   48,   80,   43,    8,    3,   44,
   61,   61,   45,  105,   46,   47,   48,  133,   43,  134,
  135,   62,   44,   58,    7,   45,    1,   46,   47,   48,
   61,   66,  156,   43,   44,  159,   62,   45,   58,   46,
   47,   48,  128,  156,   43,  171,  179,  181,  157,   44,
  191,  194,   45,  157,   46,   47,   48,  160,  176,  152,
   44,  153,    0,   45,  112,   46,   47,   48,   43,  268,
  269,   11,  263,  231,   43,  113,   13,  105,    0,  105,
    0,    0,    0,    0,   44,    0,    0,   45,  176,   46,
   47,   48,    0,   45,  112,   46,   47,   48,  145,  145,
  145,   11,  145,    0,  145,  113,   13,    0,  233,   43,
   68,    8,    0,  164,    9,   10,  145,  145,    0,  145,
    0,  141,   43,  120,   12,   69,  227,   43,   45,   14,
   46,   47,   48,  164,    0,    0,  142,  142,  142,    0,
  142,   45,  142,   46,   47,   48,   45,   95,   46,   47,
   48,  229,   43,    0,  142,  142,    0,  142,  148,  148,
  148,  216,  148,    0,  148,    0,    0,  120,  131,  132,
    0,   45,    0,   46,   47,   48,  148,  148,    0,  148,
  145,    0,  145,  147,  147,  147,    0,  147,  133,  147,
  134,  135,    0,    0,    0,  211,  213,    0,    0,    0,
    0,  147,  147,  120,  147,  126,    0,  126,    0,  126,
  105,  105,  105,  105,  105,  105,  105,    0,  142,    0,
  142,    0,    0,  126,  126,  105,  126,  130,  164,  130,
  105,  130,    0,   62,   62,   58,   58,    0,    0,    0,
  148,    0,  148,    0,    0,  130,  130,    0,  130,    0,
  124,    0,  124,   62,  124,   58,    0,  176,  273,  152,
    0,  153,    0,  112,    0,  147,    0,  147,  124,  124,
   11,  124,    0,    0,  113,   13,  151,    0,  150,    7,
    8,    0,    0,    9,   10,    0,  127,  126,  127,  126,
  127,    0,   11,   12,  270,    0,    0,   13,   14,    0,
    0,    0,  120,    0,  127,  127,    0,  127,    0,  130,
    0,  130,    0,  145,  145,  145,  145,  145,  145,  145,
    0,    0,  145,  145,  145,  145,  145,    0,  145,    0,
   99,    0,  124,  145,  124,   53,  145,  145,   53,  145,
    0,  145,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  142,  142,  142,  142,  142,  142,  142,    0,    0,
  142,  142,  142,  142,  142,    0,  142,    0,  127,    0,
  127,  142,  186,  148,  148,  148,  148,  148,  148,  148,
    0,    0,  148,  148,  148,  148,  148,    0,  148,    0,
    0,    0,    0,  148,   99,    0,   99,    0,  147,  147,
  147,  147,  147,  147,  147,    0,    0,  147,  147,  147,
  147,  147,    0,  147,  215,    0,    0,    0,  147,    0,
  126,  126,  126,  126,  126,  126,  126,    0,    0,  126,
  126,  126,  126,  126,    0,  126,  185,    0,  184,    0,
  126,    0,  130,  130,  130,  130,  130,  130,  130,    0,
    0,  130,  130,  130,  130,  130,    0,  130,    0,    0,
   15,    0,  130,    0,    0,  124,  124,  124,  124,  124,
  124,  124,  145,    0,  124,  124,  124,  124,  124,    0,
  124,  146,  147,  148,  149,  124,   90,    0,   89,    0,
    0,    0,    0,    0,    0,   34,    0,    0,    0,    0,
    0,  127,  127,  127,  127,  127,  127,  127,    0,    0,
  127,  127,  127,  127,  127,    0,  127,   37,    0,    0,
    0,  127,    0,    0,  163,    0,    0,   99,   99,   99,
   99,   99,   99,   99,    0,    0,    0,    0,    0,    0,
    0,  195,   99,    0,   57,   63,   63,   99,   68,    8,
    0,   53,    9,   10,    0,    0,    0,   53,    0,    0,
    0,  106,   12,  145,   53,    0,    0,   14,   53,   53,
   68,    8,  108,  183,    9,   10,  109,    0,    0,    0,
   63,    0,    0,    0,   12,   57,  165,   68,    8,   14,
    0,    9,   10,    6,    7,    8,  163,  159,    9,   10,
    0,   12,  159,  262,    0,   57,   14,   11,   12,   57,
  169,    0,   13,   14,    0,    0,    0,    0,    0,  163,
   68,    8,   87,   88,    9,   10,    6,    7,    8,    0,
  243,    9,   10,    0,   12,    0,  274,    0,    0,   14,
   11,   12,  289,    0,    0,   13,   14,    0,   36,    7,
    8,    0,    0,    9,   10,  162,   68,    8,   87,  180,
    9,   10,   11,   12,    0,    0,    0,   13,   14,    0,
   12,    0,    6,    7,    8,   14,    0,    9,   10,    0,
    0,    0,    0,    0,    0,    0,   11,   12,    0,    0,
  235,   13,   14,    7,    8,    0,    0,    9,   10,    0,
    0,    0,    0,   57,    7,    8,   11,   12,    9,   10,
    0,   13,   14,    0,    0,    0,    0,   11,   12,    0,
    0,    0,   13,   14,    0,    0,    0,  162,   68,    8,
    0,  253,    9,   10,    0,    7,    8,    0,    0,    9,
   10,    0,   12,    0,    0,    0,    0,   14,   11,   12,
  162,   68,    8,   13,   14,    9,   10,    0,    0,    0,
    0,  242,   68,    8,    0,   12,    9,   10,   68,    8,
   14,    0,    9,   10,   68,    8,   12,    0,    9,   10,
    0,   14,   12,    0,   41,   41,   41,   14,   12,   41,
   41,   68,    8,   14,    0,    9,   10,    0,    0,   41,
    0,    0,    0,    0,   41,   12,    0,    0,    0,    0,
   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   12,    2,    3,   40,    5,   42,   43,   19,
   45,   59,   47,   12,   43,   40,   45,   43,   59,   45,
   19,   20,   40,   40,   44,   20,  257,  278,  279,  123,
   10,  121,   59,   41,   19,   43,   35,   45,   59,   38,
   35,   61,  123,   38,  270,  276,   59,  123,  274,  275,
  105,   40,   51,   43,   59,   45,   40,   41,   42,   43,
   70,   45,   72,   47,   44,   40,   41,   42,   43,   59,
   45,   70,   47,   72,    3,   44,    5,  171,   52,   53,
   90,   59,   92,   40,   41,   42,   43,   44,   45,   40,
   47,   90,   61,   92,   40,   41,   42,   43,   44,   45,
   59,   47,  101,   44,   43,   59,   45,   92,  118,   40,
   41,   42,   43,   61,   45,   43,   47,   45,   59,  118,
  125,   40,   41,   42,   43,  125,   45,   59,   47,  223,
  220,   59,   59,   42,   40,   59,   42,   43,   47,   45,
  139,   47,  197,   44,   18,   44,   40,   59,   42,   43,
   41,   45,  270,   47,  164,   59,   59,  275,   59,   40,
   59,   42,   43,   40,   45,  164,   47,   59,   44,  143,
  144,   40,  101,   42,   43,  185,   45,   43,   47,   45,
  256,  257,  256,   59,  259,  260,  185,   41,   42,   43,
   69,   45,   59,   47,   73,  256,  257,  196,   41,   42,
   43,  196,   45,  276,   47,  215,  216,   41,  256,   83,
   84,  266,   41,  257,  256,   44,  215,  216,  259,  260,
  278,  279,  257,   42,   43,  264,   45,  256,   47,   42,
   43,  269,   45,  260,   47,  256,  256,  272,  273,  264,
  257,  276,   41,  278,  279,  280,  264,  260,  256,  278,
  279,  280,  278,  279,  280,  152,  153,  257,  258,  276,
   41,  261,  262,  273,   42,   43,  256,   45,  125,   47,
  270,  271,  256,  257,  273,  275,  276,   59,   43,  263,
   45,  256,  257,   43,   41,   45,  270,  256,  167,  273,
  274,  275,  276,   59,  278,  279,  280,  256,  273,  260,
  257,  276,  256,  278,  279,  280,  256,  257,   43,   59,
   45,  257,  257,  263,  257,  256,  273,  256,  257,  276,
  270,  278,  279,  280,  256,  275,  257,  273,  256,  256,
  276,  257,  278,  279,  280,  259,  260,  276,  257,  278,
  279,  280,  273,   59,  256,  276,  123,  278,  279,  280,
  256,  257,  256,  256,  273,  256,  257,  276,   59,  278,
  279,  280,  256,  257,  257,   41,   41,  273,  257,   41,
  276,    0,  278,  279,  280,  276,  257,    0,    0,  273,
  256,  257,  276,   59,  278,  279,  280,  276,  257,  278,
  279,   44,  273,   44,    0,  276,    0,  278,  279,  280,
  276,  269,  256,  257,  273,  269,   59,  276,   59,  278,
  279,  280,   49,  256,  257,   75,   89,   90,  269,  273,
   93,   94,  276,  277,  278,  279,  280,   41,  257,   43,
  273,   45,   -1,  276,  263,  278,  279,  280,  257,  256,
  257,  270,  197,  256,  257,  274,  275,  123,   -1,  125,
   -1,   -1,   -1,   -1,  273,   -1,   -1,  276,  257,  278,
  279,  280,   -1,  276,  263,  278,  279,  280,   41,   42,
   43,  270,   45,   -1,   47,  274,  275,   -1,  256,  257,
  257,  258,   -1,   70,  261,  262,   59,   60,   -1,   62,
   -1,  256,  257,   41,  271,  272,  256,  257,  276,  276,
  278,  279,  280,   90,   -1,   -1,   41,   42,   43,   -1,
   45,  276,   47,  278,  279,  280,  276,  125,  278,  279,
  280,  256,  257,   -1,   59,   60,   -1,   62,   41,   42,
   43,  118,   45,   -1,   47,   -1,   -1,   85,  256,  257,
   -1,  276,   -1,  278,  279,  280,   59,   60,   -1,   62,
  123,   -1,  125,   41,   42,   43,   -1,   45,  276,   47,
  278,  279,   -1,   -1,   -1,  113,  114,   -1,   -1,   -1,
   -1,   59,   60,  121,   62,   41,   -1,   43,   -1,   45,
  256,  257,  258,  259,  260,  261,  262,   -1,  123,   -1,
  125,   -1,   -1,   59,   60,  271,   62,   41,  185,   43,
  276,   45,   -1,  256,  257,  256,  257,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,   59,   60,   -1,   62,   -1,
   41,   -1,   43,  276,   45,  276,   -1,  257,  215,   43,
   -1,   45,   -1,  263,   -1,  123,   -1,  125,   59,   60,
  270,   62,   -1,   -1,  274,  275,   60,   -1,   62,  257,
  258,   -1,   -1,  261,  262,   -1,   41,  123,   43,  125,
   45,   -1,  270,  271,  212,   -1,   -1,  275,  276,   -1,
   -1,   -1,  220,   -1,   59,   60,   -1,   62,   -1,  123,
   -1,  125,   -1,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,
   59,   -1,  123,  276,  125,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,   -1,  123,   -1,
  125,  276,   59,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,
   -1,   -1,   -1,  276,  123,   -1,  125,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,   -1,  271,  123,   -1,   -1,   -1,  276,   -1,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,   -1,  271,  123,   -1,  125,   -1,
  276,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,   -1,
  123,   -1,  276,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,  256,   -1,  265,  266,  267,  268,  269,   -1,
  271,  265,  266,  267,  268,  276,  123,   -1,  125,   -1,
   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,  125,   -1,   -1,
   -1,  276,   -1,   -1,  125,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  125,  271,   -1,    8,    9,   10,  276,  257,  258,
   -1,  257,  261,  262,   -1,   -1,   -1,  263,   -1,   -1,
   -1,  125,  271,  269,  270,   -1,   -1,  276,  274,  275,
  257,  258,  125,  260,  261,  262,   40,   -1,   -1,   -1,
   44,   -1,   -1,   -1,  271,   49,  256,  257,  258,  276,
   -1,  261,  262,  256,  257,  258,  125,   61,  261,  262,
   -1,  271,   66,  125,   -1,   69,  276,  270,  271,   73,
   74,   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,  125,
  257,  258,  259,  260,  261,  262,  256,  257,  258,   -1,
  125,  261,  262,   -1,  271,   -1,  125,   -1,   -1,  276,
  270,  271,  125,   -1,   -1,  275,  276,   -1,  256,  257,
  258,   -1,   -1,  261,  262,  256,  257,  258,  259,  260,
  261,  262,  270,  271,   -1,   -1,   -1,  275,  276,   -1,
  271,   -1,  256,  257,  258,  276,   -1,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  270,  271,   -1,   -1,
  154,  275,  276,  257,  258,   -1,   -1,  261,  262,   -1,
   -1,   -1,   -1,  167,  257,  258,  270,  271,  261,  262,
   -1,  275,  276,   -1,   -1,   -1,   -1,  270,  271,   -1,
   -1,   -1,  275,  276,   -1,   -1,   -1,  256,  257,  258,
   -1,  260,  261,  262,   -1,  257,  258,   -1,   -1,  261,
  262,   -1,  271,   -1,   -1,   -1,   -1,  276,  270,  271,
  256,  257,  258,  275,  276,  261,  262,   -1,   -1,   -1,
   -1,  256,  257,  258,   -1,  271,  261,  262,  257,  258,
  276,   -1,  261,  262,  257,  258,  271,   -1,  261,  262,
   -1,  276,  271,   -1,  256,  257,  258,  276,  271,  261,
  262,  257,  258,  276,   -1,  261,  262,   -1,   -1,  271,
   -1,   -1,   -1,   -1,  276,  271,   -1,   -1,   -1,   -1,
  276,
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
"inicio_if : IF condicional_opt",
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

//#line 800 "parser.y"
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
//#line 802 "Parser.java"
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
//#line 26 "parser.y"
{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
		}
break;
case 2:
//#line 31 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 33 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 35 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 40 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 42 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 44 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 46 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 48 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 50 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 52 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 54 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 13:
//#line 59 "parser.y"
{
            generador.enterScope(val_peek(1).sval);
        }
break;
case 17:
//#line 72 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 82 "parser.y"
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
//#line 94 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 96 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 98 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 28:
//#line 103 "parser.y"
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
//#line 119 "parser.y"
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
//#line 135 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 137 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 139 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 144 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 149 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 150 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 158 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 176 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 181 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 186 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 188 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 190 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 195 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 197 "parser.y"
{ errManager.debug("Parametro formal lambda semantica detectado", al.getLine()); }
break;
case 55:
//#line 199 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 201 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 203 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 208 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 213 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 217 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 219 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 221 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 223 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 225 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 232 "parser.y"
{
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal(val_peek(2).sval, val_peek(0).sval);
	    }
break;
case 70:
//#line 251 "parser.y"
{
	        errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 1;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
	    }
break;
case 71:
//#line 261 "parser.y"
{
    	    errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 1;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
    	}
break;
case 72:
//#line 271 "parser.y"
{
	        errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
	    }
break;
case 73:
//#line 278 "parser.y"
{
            errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
        }
break;
case 74:
//#line 285 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 75:
//#line 287 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 289 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 77:
//#line 291 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 293 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 79:
//#line 295 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 297 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 81:
//#line 299 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 301 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 83:
//#line 303 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 84:
//#line 308 "parser.y"
{
            /* $2 = indice de terceto condicion.*/
            /* Generar BF con destino desconocido.*/
            int idxBF = generador.generarBF(val_peek(0).ival);
            yyval.ival = idxBF;
        }
break;
case 85:
//#line 317 "parser.y"
{
            /* Backpatch BF para saltar al Else.*/
            /*Lo pasamos para arriba asi se rellena el BF*/
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterElse = generador.getUltimoTerceto() + 1;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 86:
//#line 329 "parser.y"
{
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterElse = generador.getUltimoTerceto() + 1;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 87:
//#line 339 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 88:
//#line 341 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 89:
//#line 343 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 90:
//#line 345 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 91:
//#line 347 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 92:
//#line 349 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 93:
//#line 351 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 94:
//#line 353 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 95:
//#line 358 "parser.y"
{
          int idxBI = generador.generarBI();
          /*devolvemos la instruccion donde se genero el BI*/
          yyval.ival = idxBI;
        }
break;
case 96:
//#line 367 "parser.y"
{ yyval.ival = val_peek(1).ival; }
break;
case 97:
//#line 369 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 98:
//#line 371 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 99:
//#line 373 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 101:
//#line 379 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 102:
//#line 381 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 103:
//#line 383 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 104:
//#line 385 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 105:
//#line 391 "parser.y"
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
case 106:
//#line 404 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 107:
//#line 409 "parser.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 108:
//#line 411 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 109:
//#line 413 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 110:
//#line 415 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 111:
//#line 417 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 112:
//#line 422 "parser.y"
{ yyval.sval = "<="; }
break;
case 113:
//#line 424 "parser.y"
{ yyval.sval = ">="; }
break;
case 114:
//#line 426 "parser.y"
{ yyval.sval = "=="; }
break;
case 115:
//#line 428 "parser.y"
{ yyval.sval = "!="; }
break;
case 118:
//#line 435 "parser.y"
{ yyval.ival = 0; }
break;
case 119:
//#line 437 "parser.y"
{ errManager.error("Tipo string no permitido.",al.getLine()); }
break;
case 120:
//#line 442 "parser.y"
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
case 121:
//#line 466 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 122:
//#line 468 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 123:
//#line 470 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 124:
//#line 475 "parser.y"
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
case 125:
//#line 486 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 126:
//#line 488 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 127:
//#line 490 "parser.y"
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
case 128:
//#line 501 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 129:
//#line 503 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 130:
//#line 505 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 131:
//#line 507 "parser.y"
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
case 132:
//#line 519 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 133:
//#line 524 "parser.y"
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
case 134:
//#line 535 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 135:
//#line 537 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 136:
//#line 539 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 137:
//#line 541 "parser.y"
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
case 138:
//#line 552 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 139:
//#line 554 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 140:
//#line 556 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 141:
//#line 558 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 142:
//#line 563 "parser.y"
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
case 143:
//#line 582 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 144:
//#line 584 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 145:
//#line 586 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 146:
//#line 588 "parser.y"
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
case 147:
//#line 599 "parser.y"
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
case 148:
//#line 627 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 149:
//#line 632 "parser.y"
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
case 150:
//#line 662 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 151:
//#line 664 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 152:
//#line 666 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 153:
//#line 671 "parser.y"
{
	        errManager.debug("Print detectado", al.getLine());
	        generador.agregarTerceto("print", val_peek(0).sval, "-");
	    }
break;
case 154:
//#line 678 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 155:
//#line 680 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 156:
//#line 684 "parser.y"
{
	        errManager.debug("Definicion lambda detectada", al.getLine());

	        generador.agregarTerceto("fin_funcion", val_peek(3).sval, "-");
	        generador.exitScope();

	        yyval.sval = val_peek(3).sval;
	    }
break;
case 157:
//#line 693 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 158:
//#line 695 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 159:
//#line 697 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 160:
//#line 702 "parser.y"
{
            /* Obtenemos un nombre para la funcion lambda.*/
            String lamName = generador.getLambdaName();
            /* Entramos al scope de la funcion.*/
            generador.enterScope(lamName);
            String lamScope = generador.getCurrentScope();

            /* Agregamos el parametro, esto para reutilizar los metodos de funcion.*/
            generador.agregarParametro(false, val_peek(2).ival, val_peek(1).sval);
            /* Aplica ambito a los parametros y crea el terceto de funcion.*/
            generador.aplicarAmbito(al.ts, Atributo.invalidType, lamScope);

            /*Generamos el terceto correspondiente al inicio de la a funcion.*/
            generador.agregarTerceto("inicio_funcion", lamScope, "-");
            yyval.sval = lamScope;
        }
break;
case 161:
//#line 719 "parser.y"
{ errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
break;
case 162:
//#line 725 "parser.y"
{
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", val_peek(0).sval, "-");
	    }
break;
case 163:
//#line 733 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 164:
//#line 735 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 165:
//#line 737 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 166:
//#line 739 "parser.y"
{ errManager.error("Faltan argumentos", al.getLine()); }
break;
case 167:
//#line 741 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 168:
//#line 743 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 169:
//#line 748 "parser.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 170:
//#line 750 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 171:
//#line 752 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 172:
//#line 754 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 177:
//#line 769 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 178:
//#line 774 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 179:
//#line 779 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 180:
//#line 784 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 181:
//#line 789 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 182:
//#line 791 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 183:
//#line 796 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1870 "Parser.java"
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
