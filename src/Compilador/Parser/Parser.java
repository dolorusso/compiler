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
//#line 22 "Parser.java"




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
    0,    3,    3,    4,    4,    4,    5,    5,    5,    5,
    5,    5,    5,    5,    5,    7,    7,    7,    7,   15,
   15,   16,   16,   16,    2,    2,    2,   18,   18,   17,
   17,   17,   20,   20,   20,   19,   19,   19,   19,   19,
   19,   19,   19,   19,    6,    6,    6,    6,    6,    6,
    6,   21,   22,   22,    9,    9,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   27,   27,   27,   27,   28,   28,   28,   28,   30,   30,
   26,   26,   26,   26,   26,   31,   31,   31,   31,   31,
   31,   14,   14,    8,    8,    8,    8,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   32,   32,   32,   32,
   32,   32,   32,   32,   32,   34,   34,   34,   34,   34,
   34,   34,   34,   34,   10,   10,   10,   10,   11,   24,
   24,   24,   24,   12,   33,   33,   33,   33,   33,   33,
   13,   13,   13,   13,   35,   35,   36,   36,    1,    1,
    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    4,    4,    3,    5,    3,    3,    3,    2,    3,    2,
    1,    1,    1,    2,    2,    1,    1,    1,    2,    2,
    2,    1,    2,    2,    2,    4,    3,    3,    4,    3,
    2,    3,    2,    0,    1,    2,    2,    1,    2,    1,
    3,    2,    1,    3,    2,    3,    2,    3,    2,    1,
    2,    3,    2,    2,    2,    3,    2,    1,    2,    3,
    2,    3,    1,    1,    1,    1,    5,    5,    4,    4,
    5,    5,    4,    4,    5,    4,    5,    4,    4,    4,
    4,    4,    4,    4,    3,    3,    2,    4,    4,    2,
    3,    2,    3,    1,    3,    3,    2,    2,    3,    2,
    5,    5,    4,    4,    4,    1,    1,    1,    1,    1,
    1,    1,    1,    4,    4,    4,    4,    3,    3,    1,
    3,    3,    2,    1,    2,    2,    3,    2,    3,    2,
    3,    2,    3,    2,    1,    1,    1,    1,    1,    1,
    2,    2,    2,    2,    4,    4,    4,    4,    2,    7,
    6,    6,    4,    2,    3,    2,    3,    2,    2,    1,
    4,    5,    3,    4,    1,    3,    3,    1,    1,    1,
    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  112,    0,
  113,    0,    0,    0,   35,   12,   13,    0,   16,   17,
   18,    0,    0,    0,   22,    0,   65,   66,    0,   10,
    0,   37,    0,    0,    0,    0,    0,    0,  138,  169,
  170,  173,    0,    0,    0,    0,    0,  137,  139,    0,
    0,    0,    0,  135,    0,  160,    0,  149,  154,    0,
    0,    0,   38,    0,    0,    0,    0,    0,    9,    0,
    6,   36,   15,   61,   57,   14,    0,   23,   19,   24,
   20,   25,   21,    0,    0,    0,    0,    0,    0,    0,
    5,    0,    0,    0,    0,    0,    0,   31,    0,    0,
   40,    0,   43,    0,    0,   64,    0,   27,    0,  126,
  125,    0,  174,  175,  123,    0,    0,  144,  171,  172,
  141,  130,    0,    0,  128,  134,  132,  100,  106,  107,
  108,  109,  110,  111,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   92,    0,    0,  159,  158,    0,
  156,    0,   98,    0,   97,   39,    0,    0,    0,    0,
    0,    2,    0,   60,   56,    0,    0,   50,   28,    0,
  166,  163,    0,  168,    0,  117,  116,  148,   51,   53,
   47,    0,    0,    0,   54,   49,   30,    0,   42,  146,
    0,   45,    0,   33,    0,   93,   91,  119,    0,  122,
    0,    0,   90,    0,    0,   87,    0,    0,   79,    0,
   76,    0,   78,    0,   74,   70,    0,   73,   69,   80,
  129,  127,  133,  131,  157,  155,  104,  103,   96,   95,
    0,  105,  115,  114,  147,  145,    4,   26,   29,    0,
  164,  161,    0,   52,   48,   46,    0,   41,   44,   62,
   32,    0,    0,    0,   86,    0,   85,   75,   77,   72,
   68,   71,   67,  102,  101,  162,  167,    0,   88,   89,
   84,   82,   83,   81,    0,    0,    0,  152,  150,
};
final static short yydgoto[] = {                          3,
   48,   14,   15,   16,   17,   18,   19,   20,   21,   49,
   23,   24,   25,   26,   35,  108,  100,   64,  101,  102,
  103,  104,  105,  106,   27,   28,   51,   65,  144,   52,
  137,   53,   58,   54,   29,  175,
};
final static short yysindex[] = {                       -83,
  321,  687,    0,  -20,   -3,   77,   50,   50,    0,  639,
    0,   15,  710,  712,    0,    0,    0,  173,    0,    0,
    0,  -30,  -28,   79,    0,   95,    0,    0,  -33,    0,
  800,    0,  112,  -26,  -97,   13,   25,   24,    0,    0,
    0,    0,  112, -232,  212,    9,  143,    0,    0,  575,
  615,   57,  124,    0,  104,    0,  164,    0,    0,   20,
   77,  568,    0,  275,  -37,  112,   60,  -16,    0,  816,
    0,    0,    0,    0,    0,    0, -114,    0,    0,    0,
    0,    0,    0,   61,   61,  301,  -97,   51, -155,  -23,
    0,  -11,   89,   13, -121,  -73, -106,    0,  194,  370,
    0,   33,    0, -125,  135,    0,  784,    0,   68,    0,
    0,  -34,    0,    0,    0,   13,   24,    0,    0,    0,
    0,    0, -232,  218,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  118,  130,  112,  632,  109,  822,
  -44,  -41,  -35,  145,    0,  148,  156,    0,    0,  -18,
    0,   93,    0,  608,    0,    0,   77,  149,  -10,  154,
   42,    0,  -29,    0,    0,  -97,  -97,    0,    0,   14,
    0,    0,  174,    0,   35,    0,    0,    0,    0,    0,
    0,  -36,  210,    1,    0,    0,    0,  349,    0,    0,
   86,    0,   11,    0,  850,    0,    0,    0,  124,    0,
  124,  135,    0,  874,  -31,    0,   37,   40,    0,  175,
    0,  237,    0,  117,    0,    0,  120,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  122,    0,    0,    0,    0,    0,    0,    0,    0,  141,
    0,    0,   14,    0,    0,    0,  267,    0,    0,    0,
    0,  255,  273,  123,    0,  137,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  659,    0,    0,
    0,    0,    0,    0,  903,  852,  881,    0,    0,
};
final static short yyrindex[] = {                         0,
  351,    0,    0,    0,  236,    0,    0,    0,    0,    0,
    0,   27,    0,  401,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    2,  396,    0,  418,    0,    0,
    0,    0,    0,  485,    0,    0,    0,    0,    0,    0,
    0,  592,  507,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  403,    0,  442,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  358,  372,    0,    2,    0,    0,    0,
    0,    0,    0,  558,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  101,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  440,  463,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  897,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  446,    0,    0,    2,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  736,    0,
  758,  548,    0,    0,    0,    0,  897,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  103,    0,    0,
    0,    0,    0,    0,    0,    0,  171,    0,    0,
};
final static short yygindex[] = {                         0,
  -54,   49,  136,    0,   -6,    0,    0,    0,    0,   -1,
    0,    0,    0,  438,  389,  281,    0,  305,    4,  291,
  -21,    0,  899,    0,    0,    0,   53,  -13,   71,  375,
    0,  335,  150,   -5,    0,  284,
};
final static int YYTABLESIZE=1179;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         22,
   22,   34,   43,   63,   46,   44,  197,   45,   22,   47,
   89,   22,   22,   97,   98,   46,   44,  215,   45,  123,
   47,  173,  226,  218,  135,  107,  136,   90,   79,   22,
   81,  135,  135,  136,  136,  174,   34,  143,   32,    2,
  125,  127,   32,  121,  142,  113,  114,  177,  234,   22,
   31,  123,  109,  124,   67,   63,  123,  156,  173,  109,
   22,   70,   22,   67,   55,   56,   46,   44,   22,   45,
  165,   47,   97,  190,   46,   44,  191,   45,  243,   47,
  192,   97,  236,   46,   44,  191,   45,  165,   47,   55,
   56,   46,   44,  242,   45,  255,   47,  145,  257,   97,
   86,   46,   44,  189,   45,   22,   47,   97,  162,   46,
   44,  170,   45,  152,   47,  174,   43,  158,   46,   44,
  171,   45,  121,   47,  208,   97,   34,   46,   44,  178,
   45,  207,   47,   63,   86,  179,   22,   83,   22,  192,
  222,  224,  164,  193,  149,   46,   44,  156,   45,   72,
   47,  228,   22,   46,   44,  195,   45,   59,   47,   46,
  123,  165,  124,    9,   47,  146,   72,  209,   11,  249,
  147,   46,  123,    1,  124,  261,   47,  135,  263,  136,
  265,  272,  180,  181,  243,  123,  111,  124,  267,  182,
  123,  248,  124,   22,  235,  274,    9,   63,  123,  266,
  124,   11,   22,  220,  151,   72,  135,  232,  136,  231,
  211,  213,  216,  219,  138,  212,   77,  138,  214,   36,
  244,  196,   88,  138,  217,   78,  237,   80,  253,   93,
   94,   76,  172,  258,  157,   37,   95,  225,   38,   39,
   40,   41,   42,    9,  176,  233,   37,   96,   11,   38,
   39,   40,   41,   42,   40,   41,   42,  247,   34,   34,
   33,   63,   34,   34,  122,   36,   22,  250,   63,  156,
  156,   34,   34,   22,   22,   22,   34,   34,   66,   58,
  110,   36,  165,   33,   38,   39,   40,   41,   42,   36,
  241,   40,   41,   42,   58,  259,  254,   37,   36,  256,
   38,   39,   40,   41,   42,   37,   36,  268,   38,   39,
   40,   41,   42,  269,   37,  160,   36,   38,   39,   40,
   41,   42,   37,   93,   36,   38,   39,   40,   41,   42,
   72,  270,   37,   36,   82,   38,   39,   40,   41,   42,
   37,   98,   36,   38,   39,   40,   41,   42,  227,   37,
   11,   84,   38,   39,   40,   41,   42,  161,   37,  148,
   36,   38,   39,   40,   41,   42,  154,  169,   36,   63,
   85,  153,  260,  198,   36,  262,   37,  264,  271,   38,
   39,   40,   41,   42,   37,  200,   36,   38,   39,   40,
   41,   42,  273,   38,   39,   40,   41,   42,  126,   36,
    8,   59,    3,  221,   36,   38,   39,   40,   41,   42,
  187,  223,   36,  188,   87,   55,   59,  112,   38,   39,
   40,   41,   42,   38,   39,   40,   41,   42,   73,   74,
   55,   38,   39,   40,   41,   42,  140,  140,  140,  151,
  140,    7,  140,   13,  154,    1,  238,  239,   75,  185,
  186,  119,  120,  240,  140,  140,    0,  140,  136,  136,
  136,    0,  136,    0,  136,  245,  246,  115,  116,  199,
  201,   99,  166,  167,  116,    0,  136,  136,    0,  136,
  143,  143,  143,    0,  143,    0,  143,  117,  118,  119,
  120,   58,   58,  117,  118,  119,  120,    0,  143,  143,
    0,  143,    0,  142,  142,  142,    0,  142,  154,  142,
    0,   58,    0,    0,    0,    0,    0,    0,  140,    0,
  140,  142,  142,   99,  142,  120,    0,  120,    0,  120,
  155,   60,    6,  183,  184,    7,    8,   99,    0,    0,
  136,    0,  136,  120,  120,   10,  120,  124,    0,  124,
   12,  124,    0,    0,    0,    0,    0,  168,    0,    0,
    0,    0,  143,   95,  143,  124,  124,    0,  124,    0,
    9,    0,  276,    0,   96,   11,    4,    5,    6,  277,
    0,    7,    8,    0,    0,  142,    0,  142,   99,    0,
    9,   10,    0,    0,    0,   11,   12,    0,   50,  140,
  140,   50,  140,    0,  140,  168,   99,  120,    0,  120,
    0,   95,    0,   59,   59,    0,    0,  135,    9,  136,
    0,    0,   96,   11,    0,   99,  168,   55,   55,  124,
    0,  124,   95,   59,  134,    0,  133,    0,    0,    9,
    0,    0,    0,   96,   11,    0,    0,   55,    0,    0,
   94,  140,  140,  140,  140,  140,  140,  140,    0,    0,
  140,  140,  140,  140,  140,    0,  140,    0,    0,    0,
   99,  140,   99,  136,  136,  136,  136,  136,  136,  136,
    0,    0,  136,  136,  136,  136,  136,    0,  136,    0,
  206,    0,  153,  136,    0,  143,  143,  143,  143,  143,
  143,  143,    0,    0,  143,  143,  143,  143,  143,    0,
  143,    0,    0,    0,   94,  143,   94,    0,  142,  142,
  142,  142,  142,  142,  142,    0,    0,  142,  142,  142,
  142,  142,  230,  142,    0,    0,    0,  140,  142,  141,
  120,  120,  120,  120,  120,  120,  120,    0,    0,  120,
  120,  120,  120,  120,  204,  120,  205,    0,    0,    0,
  120,   62,  124,  124,  124,  124,  124,  124,  124,    0,
    0,  124,  124,  124,  124,  124,  118,  124,  118,    0,
  118,  275,  124,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  118,  118,    0,  118,  121,    0,
  121,    0,  121,   99,   99,   99,   99,   99,   99,   99,
    0,   30,    0,    0,   50,    0,  121,  121,   99,  121,
   50,    0,    0,   99,   60,    6,  140,   50,    7,    8,
  128,   50,   50,    0,   69,    0,   71,    0,   10,  129,
  130,  131,  132,   12,    0,    0,    0,   94,   94,   94,
   94,   94,   94,   94,    0,    0,    0,    0,  118,    0,
  118,    0,   94,  229,   60,    6,    0,   94,    7,    8,
    0,   60,    6,  138,  139,    7,    8,    0,   10,    0,
  121,    0,  121,   12,    0,   10,    0,    0,   60,    6,
   12,  203,    7,    8,    0,   60,    6,    0,    0,    7,
    8,    0,   10,    0,   50,   57,   57,   12,  194,   10,
   61,    0,    0,    0,   12,   60,    6,    0,    0,    7,
    8,    0,    0,    0,   91,    0,    0,    0,    0,   10,
    0,   92,    0,    0,   12,   57,    0,    0,    0,    0,
  163,   50,    4,    5,    6,    0,  153,    7,    8,    0,
    0,    0,    0,  150,    0,    0,    9,   10,    0,   50,
    0,   11,   12,   50,  159,   68,    5,    6,    5,    6,
    7,    8,    7,    8,  251,    0,  278,    0,    0,    9,
   10,    9,   10,    0,   11,   12,   11,   12,    0,    0,
    0,  118,  118,  118,  118,  118,  118,  118,  153,    0,
  118,  118,  118,  118,  118,  279,  118,    0,    0,    0,
    0,  118,    0,  121,  121,  121,  121,  121,  121,  121,
    0,    0,  121,  121,  121,  121,  121,    0,  121,    0,
    0,    0,    0,  121,    0,  202,    0,    0,    0,    4,
    5,    6,    0,    0,    7,    8,    0,    0,    0,    0,
    0,    0,    0,    9,   10,   50,    5,    6,   11,   12,
    7,    8,    0,    0,    0,    0,    0,    0,    0,    9,
   10,    0,    5,    6,   11,   12,    7,    8,   60,    6,
  138,  210,    7,    8,    0,    9,   10,    0,    0,    0,
   11,   12,   10,    0,    0,    0,    0,   12,    0,    0,
    0,    0,    0,    0,    0,    0,    5,    6,   60,    6,
    7,    8,    7,    8,    0,    0,    0,    0,    0,    9,
   10,    0,   10,    0,   11,   12,    0,   12,    0,    0,
   60,    6,    0,  252,    7,    8,    0,   60,    6,    0,
    0,    7,    8,    0,   10,    0,    0,    0,    0,   12,
    0,   10,   38,   38,   38,    0,   12,   38,   38,   60,
    6,    0,    0,    7,    8,    0,    0,   38,    0,    0,
    0,    0,   38,   10,    0,    0,    0,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    2,    0,   40,   10,   42,   43,   41,   45,   10,   47,
   44,   13,   14,   40,   41,   42,   43,   59,   45,   43,
   47,   45,   41,   59,   43,  123,   45,   61,   59,   31,
   59,   43,   43,   45,   45,   90,   40,   51,   59,  123,
   46,   47,   59,   45,   51,  278,  279,   59,   59,   51,
    2,   43,   40,   45,   40,   62,   43,   64,   45,   40,
   62,   13,   64,   40,   40,   41,   42,   43,   70,   45,
   44,   47,   40,   41,   42,   43,   44,   45,   44,   47,
  102,   40,   41,   42,   43,   44,   45,   61,   47,   40,
   41,   42,   43,   59,   45,   59,   47,   41,   59,   40,
   40,   42,   43,  100,   45,  107,   47,   40,  125,   42,
   43,   61,   45,   61,   47,  170,   40,   65,   42,   43,
  276,   45,  124,   47,  138,   40,  125,   42,   43,   41,
   45,  138,   47,  140,   40,  257,  138,   59,  140,  161,
  146,  147,  257,  269,   41,   42,   43,  154,   45,   14,
   47,   59,  154,   42,   43,  107,   45,    8,   47,   42,
   43,  276,   45,  270,   47,   42,   31,   59,  275,  191,
   47,   42,   43,  257,   45,   59,   47,   43,   59,   45,
   59,   59,  256,  257,   44,   43,   37,   45,  243,  263,
   43,  188,   45,  195,   41,   59,  270,  204,   43,   59,
   45,  275,  204,   59,   41,   70,   43,   59,   45,  157,
  140,  141,  142,  143,  259,  260,   44,  259,  260,  257,
  257,  256,  256,  259,  260,  256,  256,  256,  260,  256,
  257,   59,  256,   59,  272,  273,  263,  256,  276,  277,
  278,  279,  280,  270,  256,  256,  273,  274,  275,  276,
  277,  278,  279,  280,  278,  279,  280,  257,  257,  258,
  264,  268,  261,  262,  256,  257,  268,  257,  275,  276,
  277,  270,  271,  275,  276,  277,  275,  276,  264,   44,
  256,  257,  256,  264,  276,  277,  278,  279,  280,  257,
  256,  278,  279,  280,   59,   59,  260,  273,  257,  260,
  276,  277,  278,  279,  280,  273,  257,   41,  276,  277,
  278,  279,  280,   59,  273,  256,  257,  276,  277,  278,
  279,  280,  273,  256,  257,  276,  277,  278,  279,  280,
  195,   59,  273,  257,  256,  276,  277,  278,  279,  280,
  273,   41,  257,  276,  277,  278,  279,  280,  256,  273,
    0,  257,  276,  277,  278,  279,  280,   67,  273,  256,
  257,  276,  277,  278,  279,  280,   62,   87,  257,  269,
  276,  269,  256,  256,  257,  256,  273,  256,  256,  276,
  277,  278,  279,  280,  273,  256,  257,  276,  277,  278,
  279,  280,  256,  276,  277,  278,  279,  280,  256,  257,
    0,   44,    0,  256,  257,  276,  277,  278,  279,  280,
   41,  256,  257,   44,   26,   44,   59,   43,  276,  277,
  278,  279,  280,  276,  277,  278,  279,  280,  256,  257,
   59,  276,  277,  278,  279,  280,   41,   42,   43,  269,
   45,    0,   47,  123,  140,    0,  166,  167,  276,  256,
  257,  278,  279,  170,   59,   60,   -1,   62,   41,   42,
   43,   -1,   45,   -1,   47,  256,  257,  256,  257,  135,
  136,   34,   84,   85,  257,   -1,   59,   60,   -1,   62,
   41,   42,   43,   -1,   45,   -1,   47,  276,  277,  278,
  279,  256,  257,  276,  277,  278,  279,   -1,   59,   60,
   -1,   62,   -1,   41,   42,   43,   -1,   45,  204,   47,
   -1,  276,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
  125,   59,   60,   86,   62,   41,   -1,   43,   -1,   45,
  256,  257,  258,   96,   97,  261,  262,  100,   -1,   -1,
  123,   -1,  125,   59,   60,  271,   62,   41,   -1,   43,
  276,   45,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
   -1,   -1,  123,  263,  125,   59,   60,   -1,   62,   -1,
  270,   -1,  268,   -1,  274,  275,  256,  257,  258,  275,
   -1,  261,  262,   -1,   -1,  123,   -1,  125,   41,   -1,
  270,  271,   -1,   -1,   -1,  275,  276,   -1,   41,   42,
   43,   44,   45,   -1,   47,  257,   59,  123,   -1,  125,
   -1,  263,   -1,  256,  257,   -1,   -1,   43,  270,   45,
   -1,   -1,  274,  275,   -1,  188,  257,  256,  257,  123,
   -1,  125,  263,  276,   60,   -1,   62,   -1,   -1,  270,
   -1,   -1,   -1,  274,  275,   -1,   -1,  276,   -1,   -1,
   59,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,   -1,   -1,   -1,
  123,  276,  125,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,
   59,   -1,  125,  276,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,  123,  276,  125,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,  125,  271,   -1,   -1,   -1,  123,  276,  125,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,  123,  271,  125,   -1,   -1,   -1,
  276,  123,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,   41,  271,   43,   -1,
   45,  123,  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   60,   -1,   62,   41,   -1,
   43,   -1,   45,  256,  257,  258,  259,  260,  261,  262,
   -1,  125,   -1,   -1,  257,   -1,   59,   60,  271,   62,
  263,   -1,   -1,  276,  257,  258,  269,  270,  261,  262,
  256,  274,  275,   -1,  125,   -1,  125,   -1,  271,  265,
  266,  267,  268,  276,   -1,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,  123,   -1,
  125,   -1,  271,  256,  257,  258,   -1,  276,  261,  262,
   -1,  257,  258,  259,  260,  261,  262,   -1,  271,   -1,
  123,   -1,  125,  276,   -1,  271,   -1,   -1,  257,  258,
  276,  260,  261,  262,   -1,  257,  258,   -1,   -1,  261,
  262,   -1,  271,   -1,    6,    7,    8,  276,  125,  271,
  272,   -1,   -1,   -1,  276,  257,  258,   -1,   -1,  261,
  262,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,  271,
   -1,   33,   -1,   -1,  276,   37,   -1,   -1,   -1,   -1,
  125,   43,  256,  257,  258,   -1,  125,  261,  262,   -1,
   -1,   -1,   -1,   55,   -1,   -1,  270,  271,   -1,   61,
   -1,  275,  276,   65,   66,  256,  257,  258,  257,  258,
  261,  262,  261,  262,  125,   -1,  125,   -1,   -1,  270,
  271,  270,  271,   -1,  275,  276,  275,  276,   -1,   -1,
   -1,  256,  257,  258,  259,  260,  261,  262,  125,   -1,
  265,  266,  267,  268,  269,  125,  271,   -1,   -1,   -1,
   -1,  276,   -1,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,
   -1,   -1,   -1,  276,   -1,  137,   -1,   -1,   -1,  256,
  257,  258,   -1,   -1,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  270,  271,  157,  257,  258,  275,  276,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,
  271,   -1,  257,  258,  275,  276,  261,  262,  257,  258,
  259,  260,  261,  262,   -1,  270,  271,   -1,   -1,   -1,
  275,  276,  271,   -1,   -1,   -1,   -1,  276,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  257,  258,
  261,  262,  261,  262,   -1,   -1,   -1,   -1,   -1,  270,
  271,   -1,  271,   -1,  275,  276,   -1,  276,   -1,   -1,
  257,  258,   -1,  260,  261,  262,   -1,  257,  258,   -1,
   -1,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,  271,  256,  257,  258,   -1,  276,  261,  262,  257,
  258,   -1,   -1,  261,  262,   -1,   -1,  271,   -1,   -1,
   -1,   -1,  276,  271,   -1,   -1,   -1,   -1,  276,
};
}
final static short YYFINAL=3;
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
"programa : ID '{' lista_sentencias '}'",
"programa : ID '{' error '}'",
"programa : ID '{' error",
"programa : ID '{' lista_sentencias '}' error",
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : ID '{' lista_sentencias",
"programa : ID lista_sentencias",
"programa : ID '{' '}'",
"programa : '{' '}'",
"programa : ID",
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
"declaracion_funcion : tipo ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : tipo parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : tipo IDCOMP parametros_formales_opt cuerpo_funcion_opt",
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
"factor : CADENASTR",
"factor : llamada_funcion",
"factor : ID",
"factor : '-' llamada_funcion",
"factor : '-' IDCOMP",
"factor : '-' ID",
"factor : '-' CADENASTR",
"llamada_funcion : IDCOMP '(' lista_parametros_reales ')'",
"llamada_funcion : ID '(' lista_parametros_reales ')'",
"llamada_funcion : IDCOMP '(' error ')'",
"llamada_funcion : ID '(' error ')'",
"print : PRINT cuerpo_expresion",
"lambda : '(' tipo ID ')' '{' lista_sentencias_ejecutables '}'",
"lambda : '(' tipo ID ')' '{' lista_sentencias_ejecutables",
"lambda : '(' tipo ID ')' lista_sentencias_ejecutables '}'",
"lambda : '(' tipo ID ')'",
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

//#line 447 "parser.y"
private AnalizadorLexico al;
private ErrorManager errManager;
private static final ParserVal dummyParserVal = new ParserVal();

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
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
    if(atributoAnterior.ref > 1){
        Atributo atributoNuevo = new Atributo(Atributo.longType,-1 * atributoAnterior.numValue);
        al.ts.insertar(nuevoLexema, atributoNuevo);
        atributoAnterior.ref -= 1;
    } else {
        atributoAnterior.numValue = -1 * atributoAnterior.numValue;
        al.ts.modificar(lexemaAnterior, nuevoLexema, atributoAnterior);
    }
}

public void checkearRango(String lexemaActual){
    errManager.debug("Checkeando rango de constante " + lexemaActual,al.getLine());
    Atributo atributoActual = al.ts.obtener(lexemaActual);
    if (atributoActual.ref == 1){
        if (atributoActual.numValue == (2147483648L)) {
            errManager.warning("Constante long fuera de rango, truncando.");
            atributoActual.numValue -= 1;
        }
    }

}

public void run()
{
  yyparse();
  errManager.debug("Tabla de simbolos resultante" + '\n' +  al.ts.toString());
}

public void yyerror(String s) {
}
//#line 771 "Parser.java"
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
//#line 21 "parser.y"
{ errManager.debug("Declaracion de programa detectada", al.getLine()); }
break;
case 2:
//#line 23 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 25 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 27 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 32 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 34 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 36 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 38 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 40 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 42 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 44 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 15:
//#line 55 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 67 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 24:
//#line 69 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 25:
//#line 71 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 76 "parser.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 27:
//#line 78 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 28:
//#line 80 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 29:
//#line 82 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 31:
//#line 87 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 33:
//#line 92 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 34:
//#line 93 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 37:
//#line 101 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 45:
//#line 119 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 46:
//#line 124 "parser.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 47:
//#line 126 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 48:
//#line 128 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 49:
//#line 130 "parser.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 50:
//#line 132 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 134 "parser.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 52:
//#line 136 "parser.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 53:
//#line 138 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 140 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 55:
//#line 145 "parser.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 56:
//#line 147 "parser.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 57:
//#line 149 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 58:
//#line 151 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 59:
//#line 153 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 60:
//#line 155 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 61:
//#line 157 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 62:
//#line 164 "parser.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 67:
//#line 180 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 68:
//#line 182 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 69:
//#line 184 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 70:
//#line 186 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 71:
//#line 188 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 72:
//#line 190 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 73:
//#line 192 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 74:
//#line 194 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 75:
//#line 196 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 76:
//#line 198 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 77:
//#line 200 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 78:
//#line 202 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 79:
//#line 204 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 80:
//#line 206 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 83:
//#line 213 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 84:
//#line 215 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 85:
//#line 217 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 86:
//#line 219 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 87:
//#line 221 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 88:
//#line 223 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 89:
//#line 225 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 90:
//#line 227 "parser.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 92:
//#line 233 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 93:
//#line 235 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 94:
//#line 237 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 96:
//#line 243 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 97:
//#line 245 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 98:
//#line 247 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 99:
//#line 253 "parser.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 100:
//#line 255 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 101:
//#line 260 "parser.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 102:
//#line 262 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 103:
//#line 264 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 104:
//#line 266 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 105:
//#line 268 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 114:
//#line 287 "parser.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 115:
//#line 289 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 116:
//#line 291 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 117:
//#line 293 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 119:
//#line 299 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 120:
//#line 301 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 122:
//#line 304 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 123:
//#line 306 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 125:
//#line 309 "parser.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 126:
//#line 311 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 128:
//#line 317 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 129:
//#line 319 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 130:
//#line 321 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 132:
//#line 324 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 133:
//#line 326 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 134:
//#line 328 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 140:
//#line 338 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 142:
//#line 341 "parser.y"
{ errManager.debug("Identificador con -", al.getLine()); }
break;
case 143:
//#line 343 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 144:
//#line 345 "parser.y"
{ errManager.error("Operador '-' no permitido en este contexto", al.getLine()); }
break;
case 145:
//#line 350 "parser.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 146:
//#line 352 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 147:
//#line 354 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 148:
//#line 356 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 149:
//#line 361 "parser.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 150:
//#line 365 "parser.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 151:
//#line 367 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 152:
//#line 369 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 153:
//#line 371 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 154:
//#line 376 "parser.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 156:
//#line 382 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 157:
//#line 384 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 158:
//#line 386 "parser.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 159:
//#line 388 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 160:
//#line 390 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 161:
//#line 395 "parser.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 162:
//#line 397 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 163:
//#line 399 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 164:
//#line 401 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 169:
//#line 416 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 170:
//#line 421 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 171:
//#line 426 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 172:
//#line 431 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 173:
//#line 436 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 174:
//#line 438 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 175:
//#line 443 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1426 "Parser.java"
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
