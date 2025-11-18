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
    0,    0,    4,    6,    6,    7,    7,    7,    8,    8,
    8,    8,    8,    8,    8,    8,    8,    9,   16,   16,
   16,   16,   18,   18,   17,   17,   17,    5,    5,    5,
   20,   20,   19,   19,   19,   22,   22,   22,   21,   21,
   21,   21,   21,   21,   21,   21,   21,    2,    2,    2,
    2,    2,    2,    2,   23,   24,   24,   11,   11,   27,
   27,   27,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   31,   31,   31,   31,   31,   31,   31,
   31,   31,   31,   29,   29,   29,   29,   30,   30,   30,
   30,   30,   32,   32,   28,   28,   28,   28,   28,   33,
   33,   33,   33,   33,   33,    3,    3,   10,   10,   10,
   10,   25,   25,   25,   25,   25,   25,   25,   25,   25,
   34,   34,   34,   34,   34,   34,   34,   34,   34,   36,
   36,   36,   36,   36,   36,   36,   36,   36,   12,   12,
   12,   12,   13,   26,   26,   26,   26,   14,   35,   35,
   35,   35,   35,   35,   15,   15,   15,   15,   37,   37,
   38,   38,    1,    1,    1,    1,    1,    1,    1,
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
    1,    1,    1,    1,    2,    2,    2,    2,    4,    4,
    4,    4,    2,    7,    6,    6,    4,    2,    3,    2,
    3,    2,    2,    1,    4,    5,    3,    4,    1,    3,
    3,    1,    1,    1,    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,    0,  117,    0,   13,    0,    0,    0,   38,   14,
   15,   18,   19,   20,    0,    0,    0,   24,    0,   68,
   69,    0,   10,    0,    0,    9,    0,   40,    0,    0,
   30,    0,    0,    0,  142,  173,  174,  177,    0,    0,
    0,    0,    0,  141,  143,    0,    0,    0,    0,  139,
    0,  164,    0,  153,  158,    0,    0,    0,   41,    0,
    0,    0,    0,   17,   64,   60,   16,    0,    0,    0,
    0,   31,    6,   39,   25,   21,   26,   22,   27,   23,
    0,   28,    0,    0,    0,    5,    2,    0,    0,    0,
    0,    0,    0,    0,   34,    0,    0,   43,    0,   46,
    0,    0,   67,    0,  130,  129,    0,  178,  179,  127,
    0,    0,  148,  175,  176,  145,  134,    0,    0,  132,
  138,  136,  104,  110,  111,  112,  113,  114,  115,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   95,
    0,    0,  163,  162,    0,  160,    0,    0,  101,    0,
  100,   42,    0,    0,    0,    0,    0,   63,   59,   29,
   32,   53,   36,    0,    0,  170,  167,    0,  172,    0,
    4,  121,  120,  152,   54,   56,   50,    0,    0,    0,
   57,   52,   33,    0,   45,  150,    0,   48,    0,   96,
   94,  123,    0,  126,    0,    0,   93,    0,    0,   90,
    0,    0,   82,    0,   81,    0,   79,    0,   77,   73,
    0,   76,   72,   83,  133,  131,  137,  135,  161,  159,
  108,  107,  102,   99,   98,    0,  109,  119,  118,  151,
  149,   35,    0,  168,  165,    0,   55,   51,   49,    0,
   44,   47,   65,    0,    0,    0,   89,    0,   88,   80,
   78,   75,   71,   74,   70,  106,  105,  166,  171,    0,
   92,   91,   87,   85,   86,   84,    0,    0,    0,  156,
  154,
};
final static short yydgoto[] = {                          4,
   54,   16,   17,    5,   18,   19,   20,   21,   22,   23,
   24,   55,   26,   27,   28,   29,   92,   41,  107,   70,
  108,  109,  110,  111,  112,  113,   30,   31,   57,   71,
  149,   58,  142,   59,   64,   60,   32,  180,
};
final static short yysindex[] = {                       -75,
    0,  656,  683,    0,  699,  -15,  -33,   89,   54,   54,
    0,  632,    0,  -10,    0,  -35,  -38,  726,    0,    0,
    0,    0,    0,    0,  -30,  -23,  -22,    0,  -64,    0,
    0,   31,    0,  728,   71,    0,  791,    0,  123,  -28,
    0,   58,  -20,   69,    0,    0,    0,    0,  123, -115,
  354,  148,  157,    0,    0,  561,  676,   84,   -1,    0,
  114,    0,   22,    0,    0,   -9,   89,  230,    0,  814,
   63,  123,   72,    0,    0,    0,    0, -215,   73,   73,
  233,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  706,    0,   61, -134,  -17,    0,    0, -104,   43,  139,
   58,  -97,  293, -159,    0,   33,  278,    0,   29,    0,
  -84,  126,    0,   81,    0,    0,  -36,    0,    0,    0,
   58,   69,    0,    0,    0,    0,    0, -115,  236,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  132,
  141,  123,  649,  128,  147,  314,  -55,  -16,  133,    0,
  166,  173,    0,    0,   11,    0,   45,   85,    0,  846,
    0,    0,   89,  155,   48,  182,   37,    0,    0,    0,
    0,    0,    0,  798,    2,    0,    0,  162,    0,  -26,
    0,    0,    0,    0,    0,    0,    0,  -25,  206,   34,
    0,    0,    0,  878,    0,    0,  106,    0,   41,    0,
    0,    0,   -1,    0,   -1,  126,    0,   25,  821,    0,
  -48,   28,    0,  168,    0,  181,    0,   88,    0,    0,
   91,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  108,    0,    0,    0,    0,
    0,    0,   76,    0,    0,    2,    0,    0,    0,  255,
    0,    0,    0,  241,  259,  117,    0,  119,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -63,
    0,    0,    0,    0,    0,    0,  871,  610,  848,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,  321,    0,    0,    0,    0,  253,    0,    0,    0,
    0,    0,    0,   39,    0,    0,    0,  353,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    8,    0,
    0,    0,    0,    0,  356,    0,  374,    0,    0,    0,
    0,  413,    0,  435,    0,    0,    0,    0,    0,  502,
    0,    0,    0,    0,    0,    0,    0,  604,  524,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  372,  388,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  376,    0,    0,
  573,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  103,    0,    0,    0,    0,    0,    0,    0,    0,
  457,  480,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  869,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  753,    0,  775,  336,    0,    0,    0,    0,
  869,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  109,
    0,    0,    0,    0,    0,    0,    0,    0,  112,    0,
    0,
};
final static short yygindex[] = {                         0,
  -57,    0,  430,    0,  212,   16,    0,   -6,    0,    0,
    0,   -2,    0,    0,    0,    0,    0,  145,    0,  380,
  -49,  342,  -24,    0,  830,    0,    0,    0,  130,  149,
  178,  389,    0,  324,   47,  -13,    0,  282,
};
final static int YYTABLESIZE=1153;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   25,   81,   25,  219,  201,   69,   40,   37,   78,   25,
  257,  104,  105,   52,   50,   25,   51,  246,   53,   61,
   62,   52,   50,   77,   51,  128,   53,  178,   86,   73,
  114,   25,  245,   84,   25,   88,   90,  179,  130,  132,
  151,  168,  222,   38,  128,  152,  178,    3,  126,   84,
  147,  230,   84,  140,   25,  141,   65,  195,   91,  277,
  169,   69,  156,  162,  140,   25,  141,   25,  104,  196,
   52,   50,  197,   51,   94,   53,  104,  241,   52,   50,
  197,   51,  169,   53,  198,  140,  259,  141,   25,  116,
  140,   95,  141,   61,   62,   52,   50,  114,   51,  169,
   53,  183,   49,  232,   52,   50,  239,   51,   73,   53,
   11,  104,   81,   52,   50,   13,   51,  179,   53,  246,
  104,  175,   52,   50,  150,   51,  126,   53,   49,   38,
   52,   50,   37,   51,  268,   53,  211,  226,  228,   69,
   25,  176,  198,   25,  251,  104,  263,   52,   50,  265,
   51,  181,   53,  162,  154,   52,   50,   25,   51,  185,
   53,   82,  118,  119,   52,   50,  267,   51,  140,   53,
  141,   25,  252,   52,  128,  274,  129,  276,   53,  184,
    1,    2,   52,  128,  199,  129,  213,   53,  269,   84,
  128,  224,  129,   66,    8,   97,  157,    9,   10,  128,
  164,  129,   69,  143,  218,  148,   25,   12,  128,  233,
  129,  256,   14,  237,   34,  128,   37,  129,   79,  200,
   74,   75,  240,  170,  171,   85,  260,  100,  101,  244,
   39,  247,   87,   89,  102,  115,   42,   80,  177,  261,
   76,   11,  143,  221,   43,  103,   13,   44,   45,   46,
   47,   48,   43,   72,   39,   44,   45,   46,   47,   48,
   46,   47,   48,   69,   37,   37,  229,   25,   37,   37,
   69,  162,  162,  105,   25,   25,   25,   37,   37,   46,
   47,   48,   37,   37,  254,   42,   93,  258,  191,  192,
  250,  212,  236,   42,  169,  270,   61,  253,  182,  271,
  231,   43,  174,  238,   44,   45,   46,   47,   48,   43,
   42,   61,   44,   45,   46,   47,   48,  272,  193,   42,
   11,  194,  215,  217,  220,  223,   43,  166,   42,   44,
   45,   46,   47,   48,  163,   43,  100,   42,   44,   45,
   46,   47,   48,  262,   43,   42,  264,   44,   45,   46,
   47,   48,    8,   43,  159,    3,   44,   45,   46,   47,
   48,   43,   42,  266,   44,   45,   46,   47,   48,  153,
   42,   66,  273,    7,  275,    1,  103,  157,   43,   42,
  155,   44,   45,   46,   47,   48,   43,  202,   42,   44,
   45,   46,   47,   48,  103,   43,  204,   42,   44,   45,
   46,   47,   48,  127,   42,  143,  214,   44,   45,   46,
   47,   48,  131,   42,  167,   62,   44,   45,   46,   47,
   48,  225,   42,   44,   45,   46,   47,   48,  227,   42,
   62,   58,   44,   45,   46,   47,   48,  117,  159,  124,
  125,   44,   45,   46,   47,   48,   58,  160,   44,   45,
   46,   47,   48,  144,  144,  144,  243,  144,  103,  144,
  103,  248,  249,  203,  205,    0,    0,    0,    0,  106,
    0,  144,  144,    0,  144,  140,  140,  140,    0,  140,
    0,  140,    0,    0,    0,  158,   66,    8,    0,  172,
    9,   10,  121,  140,  140,  102,  140,  147,  147,  147,
   12,  147,   11,  147,    0,   14,  103,   13,   61,   61,
  106,  122,  123,  124,  125,  147,  147,    0,  147,    0,
  146,  146,  146,    0,  146,  160,  146,    0,   61,    0,
    0,    0,  189,  190,  172,  144,  106,  144,  146,  146,
  102,  146,  124,    0,  124,    0,  124,   11,  186,  187,
    0,  103,   13,    0,    0,  188,    0,  140,    0,  140,
  124,  124,   11,  124,  128,    0,  128,   13,  128,  158,
   66,    8,  143,  216,    9,   10,    0,    0,    0,  147,
    0,  147,  128,  128,   12,  128,    0,    0,  160,   14,
    0,  103,  103,  103,  103,  103,  103,  103,    0,    0,
    0,    0,  146,  140,  146,  141,  103,    0,    0,  120,
  121,  103,    0,   53,  144,  144,   53,  144,    0,  144,
  139,    0,  138,  106,  124,    0,  124,   62,   62,  122,
  123,  124,  125,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   58,   58,    0,  128,   62,  128,  278,
    0,    0,    0,    0,    0,    0,  279,    0,    0,    0,
    0,    0,   97,   58,    0,    0,    0,    0,  144,  144,
  144,  144,  144,  144,  144,    0,    0,  144,  144,  144,
  144,  144,    0,  144,    0,    0,    0,    0,  144,    0,
  140,  140,  140,  140,  140,  140,  140,    0,    0,  140,
  140,  140,  140,  140,    0,  140,    0,  210,    0,    0,
  140,    0,  147,  147,  147,  147,  147,  147,  147,    0,
    0,  147,  147,  147,  147,  147,   97,  147,   97,    0,
    0,    0,  147,    0,  280,  146,  146,  146,  146,  146,
  146,  146,    0,    0,  146,  146,  146,  146,  146,    0,
  146,    0,    0,    0,   68,  146,    0,  124,  124,  124,
  124,  124,  124,  124,    0,    0,  124,  124,  124,  124,
  124,  209,  124,  208,    0,    0,    0,  124,   15,  128,
  128,  128,  128,  128,  128,  128,    0,    0,  128,  128,
  128,  128,  128,  122,  128,  122,    0,  122,  146,  128,
  145,    0,    0,    0,    0,    0,    0,   33,    0,    0,
    0,  122,  122,    0,  122,  125,  133,  125,    0,  125,
    0,    0,    0,   36,    0,  134,  135,  136,  137,   53,
  173,    0,    0,  125,  125,   53,  125,   56,   63,   63,
    0,  144,   53,    0,    0,    0,   53,   53,    0,    0,
   83,    0,   96,    0,    0,    0,    0,    0,    0,   97,
   97,   97,   97,   97,   97,   97,   66,    8,   99,    0,
    9,   10,   63,    0,   97,  122,    0,  122,   56,   97,
   12,    0,    0,    0,    0,   14,    0,    0,   66,    8,
  155,    0,    9,   10,    0,    0,   56,  125,    0,  125,
   56,  165,   12,   67,    0,   66,    8,   14,  207,    9,
   10,    6,    7,    8,    0,   98,    9,   10,    0,   12,
    0,    0,  242,    0,   14,   11,   12,    0,    0,    0,
   13,   14,   66,    8,  143,  144,    9,   10,    6,    7,
    8,    0,    0,    9,   10,  159,   12,    0,    0,    0,
    0,   14,   11,   12,   35,    7,    8,   13,   14,    9,
   10,    6,    7,    8,    0,    0,    9,   10,   11,   12,
  235,  206,  281,   13,   14,   11,   12,    0,    0,    0,
   13,   14,    7,    8,    7,    8,    9,   10,    9,   10,
    0,    0,   56,    0,    0,   11,   12,   11,   12,    0,
   13,   14,   13,   14,    0,    0,    0,    0,  122,  122,
  122,  122,  122,  122,  122,    0,    0,  122,  122,  122,
  122,  122,    0,  122,    0,    0,    0,    0,  122,    0,
  125,  125,  125,  125,  125,  125,  125,    0,    0,  125,
  125,  125,  125,  125,    0,  125,    0,    7,    8,    0,
  125,    9,   10,    0,    7,    8,    0,    0,    9,   10,
   11,   12,    0,    0,    0,   13,   14,   11,   12,  161,
   66,    8,   13,   14,    9,   10,  158,   66,    8,    0,
  255,    9,   10,    0,   12,    0,    0,    0,    0,   14,
    0,   12,    0,    0,    0,    0,   14,    0,    0,    0,
    0,  234,   66,    8,   66,    8,    9,   10,    9,   10,
    0,    0,    0,    0,    0,    0,   12,    0,   12,    0,
    0,   14,    0,   14,   41,   41,   41,   66,    8,   41,
   41,    9,   10,    0,  172,    0,    0,    0,    0,   41,
  102,   12,    0,    0,   41,    0,   14,   11,    0,    0,
    0,  103,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    3,   40,    5,   59,   41,   12,   40,    0,   44,   12,
   59,   40,   41,   42,   43,   18,   45,   44,   47,   40,
   41,   42,   43,   59,   45,   43,   47,   45,   59,   40,
   40,   34,   59,   18,   37,   59,   59,   95,   52,   53,
   42,  257,   59,   59,   43,   47,   45,  123,   51,   34,
   57,   41,   37,   43,   57,   45,   10,  107,  123,  123,
  276,   68,   41,   70,   43,   68,   45,   70,   40,   41,
   42,   43,   44,   45,   44,   47,   40,   41,   42,   43,
   44,   45,   44,   47,  109,   43,   59,   45,   91,   43,
   43,   61,   45,   40,   41,   42,   43,   40,   45,   61,
   47,   59,   40,   59,   42,   43,   59,   45,   40,   47,
  270,   40,   40,   42,   43,  275,   45,  175,   47,   44,
   40,   61,   42,   43,   41,   45,  129,   47,   40,   59,
   42,   43,  125,   45,   59,   47,  143,  151,  152,  146,
  143,  276,  167,  146,  194,   40,   59,   42,   43,   59,
   45,  256,   47,  160,   41,   42,   43,  160,   45,  257,
   47,   17,  278,  279,   42,   43,   59,   45,   43,   47,
   45,  174,  197,   42,   43,   59,   45,   59,   47,   41,
  256,  257,   42,   43,  269,   45,   59,   47,  246,  174,
   43,   59,   45,  257,  258,  125,   67,  261,  262,   43,
   71,   45,  209,  259,  260,   57,  209,  271,   43,  125,
   45,  260,  276,   59,    3,   43,    5,   45,  257,  256,
  256,  257,   41,   79,   80,  256,   59,  256,  257,  256,
  264,  257,  256,  256,  263,  256,  257,  276,  256,   59,
  276,  270,  259,  260,  273,  274,  275,  276,  277,  278,
  279,  280,  273,  264,  264,  276,  277,  278,  279,  280,
  278,  279,  280,  270,  257,  258,  256,  270,  261,  262,
  277,  278,  279,   41,  277,  278,  279,  270,  271,  278,
  279,  280,  275,  276,  260,  257,  256,  260,  256,  257,
  257,  143,  163,  257,  256,   41,   44,  257,  256,   59,
  256,  273,   91,  256,  276,  277,  278,  279,  280,  273,
  257,   59,  276,  277,  278,  279,  280,   59,   41,  257,
    0,   44,  145,  146,  147,  148,  273,  256,  257,  276,
  277,  278,  279,  280,  272,  273,  256,  257,  276,  277,
  278,  279,  280,  256,  273,  257,  256,  276,  277,  278,
  279,  280,    0,  273,  125,    0,  276,  277,  278,  279,
  280,  273,  257,  256,  276,  277,  278,  279,  280,  256,
  257,  269,  256,    0,  256,    0,   41,  269,  273,  257,
  269,  276,  277,  278,  279,  280,  273,  256,  257,  276,
  277,  278,  279,  280,   59,  273,  256,  257,  276,  277,
  278,  279,  280,  256,  257,  259,  260,  276,  277,  278,
  279,  280,  256,  257,   73,   44,  276,  277,  278,  279,
  280,  256,  257,  276,  277,  278,  279,  280,  256,  257,
   59,   44,  276,  277,  278,  279,  280,   49,  125,  278,
  279,  276,  277,  278,  279,  280,   59,   68,  276,  277,
  278,  279,  280,   41,   42,   43,  175,   45,  123,   47,
  125,  256,  257,  140,  141,   -1,   -1,   -1,   -1,   40,
   -1,   59,   60,   -1,   62,   41,   42,   43,   -1,   45,
   -1,   47,   -1,   -1,   -1,  256,  257,  258,   -1,  257,
  261,  262,  257,   59,   60,  263,   62,   41,   42,   43,
  271,   45,  270,   47,   -1,  276,  274,  275,  256,  257,
   81,  276,  277,  278,  279,   59,   60,   -1,   62,   -1,
   41,   42,   43,   -1,   45,  146,   47,   -1,  276,   -1,
   -1,   -1,  103,  104,  257,  123,  107,  125,   59,   60,
  263,   62,   41,   -1,   43,   -1,   45,  270,  256,  257,
   -1,  274,  275,   -1,   -1,  263,   -1,  123,   -1,  125,
   59,   60,  270,   62,   41,   -1,   43,  275,   45,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,  123,
   -1,  125,   59,   60,  271,   62,   -1,   -1,  209,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,  123,   43,  125,   45,  271,   -1,   -1,  256,
  257,  276,   -1,   41,   42,   43,   44,   45,   -1,   47,
   60,   -1,   62,  194,  123,   -1,  125,  256,  257,  276,
  277,  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,  257,   -1,  123,  276,  125,  270,
   -1,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   59,  276,   -1,   -1,   -1,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,   -1,  271,   -1,   -1,   -1,   -1,  276,   -1,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,   -1,  271,   -1,   59,   -1,   -1,
  276,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,  123,  271,  125,   -1,
   -1,   -1,  276,   -1,  125,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,  123,  276,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,  123,  271,  125,   -1,   -1,   -1,  276,  123,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   41,  271,   43,   -1,   45,  123,  276,
  125,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   59,   60,   -1,   62,   41,  256,   43,   -1,   45,
   -1,   -1,   -1,  125,   -1,  265,  266,  267,  268,  257,
  125,   -1,   -1,   59,   60,  263,   62,    8,    9,   10,
   -1,  269,  270,   -1,   -1,   -1,  274,  275,   -1,   -1,
  125,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,  256,
  257,  258,  259,  260,  261,  262,  257,  258,   39,   -1,
  261,  262,   43,   -1,  271,  123,   -1,  125,   49,  276,
  271,   -1,   -1,   -1,   -1,  276,   -1,   -1,  257,  258,
   61,   -1,  261,  262,   -1,   -1,   67,  123,   -1,  125,
   71,   72,  271,  272,   -1,  257,  258,  276,  260,  261,
  262,  256,  257,  258,   -1,  125,  261,  262,   -1,  271,
   -1,   -1,  125,   -1,  276,  270,  271,   -1,   -1,   -1,
  275,  276,  257,  258,  259,  260,  261,  262,  256,  257,
  258,   -1,   -1,  261,  262,  125,  271,   -1,   -1,   -1,
   -1,  276,  270,  271,  256,  257,  258,  275,  276,  261,
  262,  256,  257,  258,   -1,   -1,  261,  262,  270,  271,
  125,  142,  125,  275,  276,  270,  271,   -1,   -1,   -1,
  275,  276,  257,  258,  257,  258,  261,  262,  261,  262,
   -1,   -1,  163,   -1,   -1,  270,  271,  270,  271,   -1,
  275,  276,  275,  276,   -1,   -1,   -1,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,   -1,  271,   -1,   -1,   -1,   -1,  276,   -1,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,   -1,  271,   -1,  257,  258,   -1,
  276,  261,  262,   -1,  257,  258,   -1,   -1,  261,  262,
  270,  271,   -1,   -1,   -1,  275,  276,  270,  271,  256,
  257,  258,  275,  276,  261,  262,  256,  257,  258,   -1,
  260,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,  271,   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,
   -1,  256,  257,  258,  257,  258,  261,  262,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,  271,   -1,
   -1,  276,   -1,  276,  256,  257,  258,  257,  258,  261,
  262,  261,  262,   -1,  257,   -1,   -1,   -1,   -1,  271,
  263,  271,   -1,   -1,  276,   -1,  276,  270,   -1,   -1,
   -1,  274,  275,
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

//#line 531 "parser.y"
private AnalizadorLexico al;
private ErrorManager errManager;
private static final ParserVal dummyParserVal = new ParserVal();
private Generador generador;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
    this.generador = new Generador();
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
}

public void yyerror(String s) {
}
//#line 786 "Parser.java"
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
	        } else {
	            errManager.error("Falta sentencia return.", al.getLine());
	        }
	        generador.exitScope();
	    }
break;
case 29:
//#line 116 "parser.y"
{
            /* Entramos al scope de la funcion.*/
            generador.enterScope(val_peek(1).sval);
            /* Checkeamos si esta declarada usando el scope, ya que este sera el mangle name de la funcion.*/
            if (generador.estaDeclarada(generador.getCurrentScope(), al.ts)){
                errManager.error("Redeclaracion de funcion no permitida.", al.getLine());
            } else {
                /* Generamos las entradas solo si es correcta.*/
                generador.aplicarAmbito(al.ts,val_peek(2).ival);
            }
        }
break;
case 30:
//#line 128 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 130 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 132 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 137 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 142 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 143 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 151 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 169 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 174 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 179 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 181 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 183 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 188 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 190 "parser.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 55:
//#line 192 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 194 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 196 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 201 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 206 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 210 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 212 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 214 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 216 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 218 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 225 "parser.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 70:
//#line 241 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 71:
//#line 243 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 72:
//#line 245 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 73:
//#line 247 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 74:
//#line 249 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 75:
//#line 251 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 253 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 77:
//#line 255 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 257 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 79:
//#line 259 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 261 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 81:
//#line 263 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 265 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 83:
//#line 267 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 86:
//#line 274 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 87:
//#line 276 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 88:
//#line 278 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 89:
//#line 280 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 90:
//#line 282 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 91:
//#line 284 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 92:
//#line 286 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 93:
//#line 288 "parser.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 95:
//#line 294 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 96:
//#line 296 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 97:
//#line 298 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 99:
//#line 304 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 100:
//#line 306 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 101:
//#line 308 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 102:
//#line 310 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 103:
//#line 316 "parser.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 104:
//#line 318 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 105:
//#line 323 "parser.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 106:
//#line 325 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 107:
//#line 327 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 108:
//#line 329 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 109:
//#line 331 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 116:
//#line 345 "parser.y"
{ yyval.ival = 0; }
break;
case 117:
//#line 347 "parser.y"
{ yyval.ival = 2; }
break;
case 118:
//#line 352 "parser.y"
{
            if (generador.checkearAlcance(val_peek(3).sval, al.ts)){
                errManager.debug("Asignacion valida detectada", al.getLine());
            } else {
                errManager.error("Variable invalida para asignacion (no encontrada/no declarada)", al.getLine());
            }
	    }
break;
case 119:
//#line 360 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 120:
//#line 362 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 121:
//#line 364 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 123:
//#line 370 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 124:
//#line 372 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 126:
//#line 375 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 127:
//#line 377 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 129:
//#line 380 "parser.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 130:
//#line 382 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 132:
//#line 388 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 133:
//#line 390 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 134:
//#line 392 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 136:
//#line 395 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 137:
//#line 397 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 138:
//#line 399 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 140:
//#line 405 "parser.y"
{
            Atributo id = al.ts.obtener(val_peek(0).sval);
            if (id.uso == Atributo.USO_PARAMETRO){
                if (id.esCR){
                    errManager.error("La variable no puede ser usada para leer.", al.getLine());
                } else {
                    errManager.debug("Se asigno a un parametro", al.getLine());
                }
            }
        }
break;
case 144:
//#line 419 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 146:
//#line 422 "parser.y"
{ errManager.debug("Identificador con -", al.getLine()); }
break;
case 147:
//#line 424 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 148:
//#line 426 "parser.y"
{ errManager.error("Operador '-' no permitido en este contexto", al.getLine()); }
break;
case 149:
//#line 431 "parser.y"
{
	        errManager.debug("Llamado a funcion detectado", al.getLine());

	    }
break;
case 150:
//#line 436 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 151:
//#line 438 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 152:
//#line 440 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 153:
//#line 445 "parser.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 154:
//#line 449 "parser.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 155:
//#line 451 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 156:
//#line 453 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 157:
//#line 455 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 158:
//#line 460 "parser.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 160:
//#line 466 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 161:
//#line 468 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 162:
//#line 470 "parser.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 163:
//#line 472 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 164:
//#line 474 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 165:
//#line 479 "parser.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 166:
//#line 481 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 167:
//#line 483 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 168:
//#line 485 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 173:
//#line 500 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 174:
//#line 505 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 175:
//#line 510 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 176:
//#line 515 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 177:
//#line 520 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 178:
//#line 522 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 179:
//#line 527 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1535 "Parser.java"
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
