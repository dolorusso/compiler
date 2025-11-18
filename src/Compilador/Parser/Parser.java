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
    0,    0,    7,    9,    9,   10,   10,   10,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   12,   19,   19,
   19,   19,   21,   21,   20,   20,   20,    8,    8,    8,
   23,   23,   22,   22,   22,   25,   25,   25,   24,   24,
   24,   24,   24,   24,   24,   24,   24,    5,    5,    5,
    5,    5,    5,    5,   26,   27,   27,   14,   14,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   33,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   31,   31,   31,   31,   32,   32,   32,
   32,   32,   34,   34,   30,   30,   30,   30,   30,   35,
   35,   35,   35,   35,   35,    6,    6,   13,   13,   13,
   13,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    2,    2,    2,    2,    2,    2,   15,   15,   15,   15,
   16,   28,   28,   28,   28,   17,   36,   36,   36,   36,
   36,   36,   18,   18,   18,   18,   37,   37,   38,   38,
    1,    1,    1,    1,    1,    1,    1,
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
    2,    7,    6,    6,    4,    2,    3,    2,    3,    2,
    2,    1,    4,    5,    3,    4,    1,    3,    3,    1,
    1,    1,    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,    0,  117,    0,   13,    0,    0,    0,   38,   14,
   15,   18,   19,   20,    0,    0,    0,   24,    0,   68,
   69,    0,   10,    0,    0,    9,    0,   40,    0,    0,
   30,    0,    0,    0,  171,  172,  175,    0,    0,    0,
    0,    0,  141,  139,    0,    0,  142,    0,    0,    0,
  162,    0,  151,  156,    0,    0,    0,   41,    0,    0,
    0,    0,   17,   64,   60,   16,    0,    0,    0,    0,
   31,    6,   39,   25,   21,   26,   22,   27,   23,    0,
   28,    0,    0,    0,    5,    2,    0,    0,    0,    0,
    0,    0,    0,   34,    0,    0,    0,   43,    0,   46,
    0,   67,    0,  130,  129,    0,  176,  177,  127,    0,
    0,  173,  174,  144,  134,    0,    0,  132,  138,  136,
    0,    0,  104,  110,  111,  112,  113,  114,  115,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   95,
  161,  160,    0,  158,    0,    0,  101,    0,  100,   42,
    0,    0,    0,    0,    0,   63,   59,   29,   32,   53,
   36,    0,    0,  168,  165,    0,  170,    0,    4,  121,
  120,  150,   54,   56,   50,    0,    0,    0,   57,   52,
   33,    0,   45,  148,    0,   48,    0,   96,   94,  133,
  131,  137,  135,  123,    0,  126,    0,    0,   93,    0,
    0,   90,    0,    0,   82,    0,   81,    0,   79,    0,
   77,   73,    0,   76,   72,   83,  159,  157,  108,  107,
  102,   99,   98,    0,  109,  119,  118,  149,  147,   35,
    0,  166,  163,    0,   55,   51,   49,    0,   44,   47,
   65,    0,    0,    0,   89,    0,   88,   80,   78,   75,
   71,   74,   70,  106,  105,  164,  169,    0,   92,   91,
   87,   85,   86,   84,    0,    0,    0,  154,  152,
};
final static short yydgoto[] = {                          4,
   53,   54,   55,  105,   16,   17,    5,   18,   19,   20,
   21,   22,   23,   24,   57,   26,   27,   28,   29,   91,
   41,  107,   69,  108,  109,  110,  111,  112,   30,   31,
   58,   70,  149,   59,  142,   63,   32,  178,
};
final static short yysindex[] = {                        30,
    0,  628,  660,    0,  682,  -26,  -21,  112,   56,   56,
    0,  586,    0,  -16,    0,  130,   -2,  788,    0,    0,
    0,    0,    0,    0,  -44,  -39,  -37,    0,  -93,    0,
    0,   -3,    0,  795,   50,    0,  816,    0,  148,  -29,
    0,    4,   28,   13,    0,    0,    0,  148, -242,  273,
  -43,  104,    0,    0,   18,  387,    0,  648,   43,  137,
    0,  127,    0,    0,  -12,  112,  851,    0,  311,   65,
  148,   74,    0,    0,    0,    0, -214,   53,   53,  366,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  704,
    0,   57, -143,  -14,    0,    0, -120,   70,  102,    4,
 -117,  362, -230,    0,   21,  -95,  329,    0,   36,    0,
 -113,    0,   99,    0,    0,  -35,    0,    0,    0,    4,
   13,    0,    0,    0,    0, -242, -222,    0,    0,    0,
  180,  187,    0,    0,    0,    0,    0,    0,    0,  155,
  162,  148,  579,  122,   29,  726,   32,   35,  129,    0,
    0,    0,   61,    0,  -32,   51,    0,  867,    0,    0,
  112,  133,  142,  153,   45,    0,    0,    0,    0,    0,
    0,  823,  -40,    0,    0,  -22,    0,   23,    0,    0,
    0,    0,    0,    0,    0,  -74,  101,  -58,    0,    0,
    0,  331,    0,    0,  124,    0,  -51,    0,    0,    0,
    0,    0,    0,    0,   18,    0,   18,   21,    0,  -52,
  844,    0,  -50,  -34,    0,  159,    0,  161,    0,  -25,
    0,    0,   63,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
  114,    0,    0,  -40,    0,    0,    0,  170,    0,    0,
    0,  195,  202,   71,    0,   72,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  671,    0,    0,
    0,    0,    0,    0,  898,  873,  875,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  270,    0,    0,    0,    0,  231,    0,    0,    0,
    0,    0,    0,   84,    0,    0,    0,  278,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,  283,    0,  305,    0,    0,    0,
    0,  410,    0,  432,    0,    0,    0,    0,  499,    0,
    0,    0,    0,    0,  521,    0,    0,    0,  562,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  252,  289,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  310,    0,    0,  321,
    0,    0,    0,    0,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  454,
  477,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  896,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  750,    0,  772,  355,    0,    0,
    0,    0,  896,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   77,    0,    0,
    0,    0,    0,    0,    0,    0,   80,    0,    0,
};
final static short yygindex[] = {                         0,
  -48,  394,  268,  817,    0,  382,    0,   44,   14,    0,
   -8,    0,    0,    0,    5,    0,    0,    0,    0,    0,
  403,    0,  -46,  -81,  267,  194,    0,    0,    0,    0,
   54,   -6,  152,  303,    0,  117,    0,  198,
};
final static int YYTABLESIZE=1174;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        126,
   37,  127,  126,   68,  176,  199,   25,   25,  255,   25,
  103,  104,   51,   49,   85,   50,   25,   52,   40,   87,
  158,   89,   25,   72,  257,  193,  230,  113,  126,   90,
  176,   83,   38,  261,  120,  117,  118,   80,   25,   11,
   93,   25,  166,  113,   13,  177,   34,   83,   37,  147,
   83,  148,   72,  121,  124,  122,  123,   94,   68,  131,
  160,  167,   25,  140,  132,  141,  244,   60,   61,   51,
   49,   25,   50,   25,   52,  103,  194,   51,   49,  195,
   50,  243,   52,  150,  103,  239,   51,   49,  195,   50,
  221,   52,   80,  224,   25,   60,   61,   51,   49,  158,
   50,  228,   52,  140,   48,  141,   51,   49,   38,   50,
  249,   52,  140,  103,  141,   51,   49,  173,   50,  155,
   52,  263,  265,  162,  177,   37,   64,  167,  181,  272,
  274,  124,  174,  172,  213,  179,  214,   68,  103,  183,
   51,   49,  182,   50,  167,   52,  126,   25,  127,  160,
   25,   48,    3,   51,   49,  197,   50,  244,   52,  115,
  189,  190,   25,  103,  158,   51,   49,  154,   50,  140,
   52,  141,  266,   77,   96,  231,   25,  152,   51,   49,
  215,   50,  245,   52,  140,   83,  141,  226,   76,   51,
   49,  235,   50,  238,   52,  267,   51,  126,  248,  127,
  237,   52,   68,   51,  126,  251,  127,  252,   52,  254,
  268,   84,  125,   42,  234,   25,   86,  258,   88,  259,
  198,  276,  126,  229,  127,  256,   99,  100,  277,  126,
  260,  127,   44,  101,   45,   46,   47,   45,   46,   47,
   11,  175,   39,   43,  102,   13,   44,   71,   45,   46,
   47,   39,   92,  269,   78,  122,  123,   37,   37,   68,
  270,   37,   37,   45,   46,   47,   68,  160,  160,   11,
   37,   37,   25,   79,   61,   37,   37,    8,  242,   25,
   25,   25,    3,  114,   42,    1,    2,  143,  216,   61,
  143,  220,   42,  143,  223,   62,  217,  219,  222,  225,
   43,   42,  196,   44,    7,   45,   46,   47,   43,    1,
   62,   44,   42,   45,   46,   47,  227,   43,  262,  264,
   44,   42,   45,   46,   47,  180,  271,  273,   43,  164,
   42,   44,   58,   45,   46,   47,  161,   43,  165,  167,
   44,   66,   45,   46,   47,  155,   43,   58,  153,   44,
  116,   45,   46,   47,   99,   42,  246,  247,  196,  129,
   42,   53,  143,  143,   53,  143,    0,  143,   42,  191,
  241,   43,  192,    0,   44,    0,   45,   46,   47,   44,
   42,   45,   46,   47,   43,   73,   74,   44,  250,   45,
   46,   47,  151,   42,    0,  103,   43,  236,    0,   44,
    0,   45,   46,   47,   42,   75,  104,  205,  207,   43,
  204,   42,   44,  103,   45,   46,   47,  206,   42,   81,
   43,  106,    0,   44,    0,   45,   46,   47,    0,  140,
   44,  141,   45,   46,   47,  200,   42,   44,    0,   45,
   46,   47,  202,   42,  128,  130,  139,    0,  138,    0,
  143,  143,  143,    0,  143,   44,  143,   45,   46,   47,
    0,  106,   44,    0,   45,   46,   47,    0,  143,  143,
    0,  143,  140,  140,  140,    0,  140,  103,  140,  103,
  168,  169,    0,  187,  188,    0,   61,   61,  106,    0,
  140,  140,    0,  140,  146,  146,  146,    0,  146,    0,
  146,    0,    0,    0,    0,    0,   61,   62,   62,    0,
    0,    0,  146,  146,    0,  146,    0,  145,  145,  145,
    0,  145,    0,  145,  201,  203,    0,   62,  119,  120,
    0,    0,  143,    0,  143,  145,  145,    0,  145,  124,
    0,  124,    0,  124,   58,   58,    0,    0,  121,    0,
  122,  123,    0,    0,  140,    0,  140,  124,  124,    0,
  124,  128,    0,  128,   58,  128,  159,   65,    8,    0,
    0,    9,   10,  106,    0,    0,  146,   53,  146,  128,
  128,   12,  128,   53,    0,  170,   14,  170,    0,  143,
   53,  101,    0,  101,   53,   53,    0,    0,   11,  145,
   11,  145,  102,   13,  102,   13,    0,    0,    0,    0,
  103,  103,  103,  103,  103,  103,  103,  184,  185,    0,
   97,  124,  170,  124,  186,  103,    0,    0,  101,    0,
  103,   11,    0,    0,    0,   11,   13,  212,    0,  102,
   13,    0,  133,  128,    0,  128,    0,    0,    0,    0,
    0,  134,  135,  136,  137,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  143,  143,  143,  143,  143,
  143,  143,    0,    0,  143,  143,  143,  143,  143,    0,
  143,    0,    0,    0,   97,  143,   97,  140,  140,  140,
  140,  140,  140,  140,    0,    0,  140,  140,  140,  140,
  140,  211,  140,  210,    0,    0,    0,  140,   67,  146,
  146,  146,  146,  146,  146,  146,    0,    0,  146,  146,
  146,  146,  146,    0,  146,    0,    0,    0,    0,  146,
    0,    0,  145,  145,  145,  145,  145,  145,  145,    0,
    0,  145,  145,  145,  145,  145,    0,  145,    0,    0,
   15,    0,  145,    0,  124,  124,  124,  124,  124,  124,
  124,    0,    0,  124,  124,  124,  124,  124,    0,  124,
  146,    0,  145,    0,  124,    0,  128,  128,  128,  128,
  128,  128,  128,    0,   33,  128,  128,  128,  128,  128,
  122,  128,  122,  275,  122,    0,  128,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   36,    0,  122,  122,
    0,  122,  125,    0,  125,    0,  125,   97,   97,   97,
   97,   97,   97,   97,   56,   62,   62,    0,  171,    0,
  125,  125,   97,  125,    0,   65,    8,   97,  209,    9,
   10,    0,   65,    8,    0,    0,    9,   10,    0,   12,
  157,    0,    0,    0,   14,   98,   12,   66,    0,   62,
    0,   14,    0,    0,   56,    0,    0,    0,    0,    0,
    0,    0,  122,    0,  122,    0,  153,    0,    0,    0,
    0,    0,   56,    6,    7,    8,   56,  163,    9,   10,
    0,    0,    0,    0,  125,    0,  125,   11,   12,    0,
    0,    0,   13,   14,   65,    8,  143,  144,    9,   10,
    0,    0,   82,    0,    0,    6,    7,    8,   12,   95,
    9,   10,    0,   14,    0,    0,    0,   65,    8,   11,
   12,    9,   10,    0,   13,   14,    0,   35,    7,    8,
   97,   12,    9,   10,    0,    0,   14,  240,    0,    0,
    0,   11,   12,    0,    0,    0,   13,   14,  208,    6,
    7,    8,    0,    0,    9,   10,    0,    0,  157,    0,
    0,    0,    0,   11,   12,  157,    0,   56,   13,   14,
    0,  156,   65,    8,  143,  218,    9,   10,    0,    0,
    0,  233,    0,    0,    0,    0,   12,  278,    0,  279,
    0,   14,    0,    0,    0,  122,  122,  122,  122,  122,
  122,  122,    0,    0,  122,  122,  122,  122,  122,    0,
  122,    0,    0,    0,    0,  122,    0,  125,  125,  125,
  125,  125,  125,  125,    0,    0,  125,  125,  125,  125,
  125,    0,  125,    0,    7,    8,    0,  125,    9,   10,
    0,    7,    8,    0,    0,    9,   10,   11,   12,    0,
    0,    0,   13,   14,   11,   12,    0,    0,    0,   13,
   14,    0,    7,    8,    0,    0,    9,   10,    0,    7,
    8,    0,    0,    9,   10,   11,   12,    0,    0,    0,
   13,   14,   11,   12,    0,    0,    0,   13,   14,  156,
   65,    8,    0,  253,    9,   10,  156,   65,    8,    0,
    0,    9,   10,    0,   12,    0,    0,    0,    0,   14,
    0,   12,  232,   65,    8,    0,   14,    9,   10,   65,
    8,   65,    8,    9,   10,    9,   10,   12,    0,    0,
    0,    0,   14,   12,    0,   12,    0,    0,   14,    0,
   14,   41,   41,   41,   65,    8,   41,   41,    9,   10,
    0,    0,    0,    0,    0,    0,   41,    0,   12,    0,
    0,   41,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         43,
    0,   45,   43,   12,   45,   41,    2,    3,   59,    5,
   40,   41,   42,   43,   59,   45,   12,   47,   40,   59,
   67,   59,   18,   40,   59,  107,   59,   40,   43,  123,
   45,   18,   59,   59,  257,  278,  279,   40,   34,  270,
   44,   37,  257,   40,  275,   94,    3,   34,    5,   58,
   37,   58,   40,  276,   50,  278,  279,   61,   67,   42,
   69,  276,   58,   43,   47,   45,   44,   40,   41,   42,
   43,   67,   45,   69,   47,   40,   41,   42,   43,   44,
   45,   59,   47,   41,   40,   41,   42,   43,   44,   45,
   59,   47,   40,   59,   90,   40,   41,   42,   43,  146,
   45,   41,   47,   43,   40,   45,   42,   43,   59,   45,
  192,   47,   43,   40,   45,   42,   43,   61,   45,   66,
   47,   59,   59,   70,  173,  125,   10,   44,   59,   59,
   59,  127,  276,   90,  143,  256,  143,  146,   40,  257,
   42,   43,   41,   45,   61,   47,   43,  143,   45,  158,
  146,   40,  123,   42,   43,  269,   45,   44,   47,   43,
  256,  257,  158,   40,  211,   42,   43,   41,   45,   43,
   47,   45,   59,   44,  125,  125,  172,   41,   42,   43,
   59,   45,  257,   47,   43,  172,   45,   59,   59,   42,
   43,   59,   45,   41,   47,  244,   42,   43,  257,   45,
   59,   47,  211,   42,   43,  257,   45,  260,   47,  260,
   41,  256,  256,  257,  161,  211,  256,   59,  256,   59,
  256,  268,   43,  256,   45,  260,  256,  257,  275,   43,
  256,   45,  276,  263,  278,  279,  280,  278,  279,  280,
  270,  256,  264,  273,  274,  275,  276,  264,  278,  279,
  280,  264,  256,   59,  257,  278,  279,  257,  258,  268,
   59,  261,  262,  278,  279,  280,  275,  276,  277,    0,
  270,  271,  268,  276,   44,  275,  276,    0,  256,  275,
  276,  277,    0,  256,  257,  256,  257,  259,  260,   59,
  259,  260,  257,  259,  260,   44,  145,  146,  147,  148,
  273,  257,  109,  276,    0,  278,  279,  280,  273,    0,
   59,  276,  257,  278,  279,  280,  256,  273,  256,  256,
  276,  257,  278,  279,  280,  256,  256,  256,  273,  256,
  257,  276,   44,  278,  279,  280,  272,  273,   72,  256,
  276,  269,  278,  279,  280,  269,  273,   59,  269,  276,
   48,  278,  279,  280,  256,  257,  256,  257,  165,  256,
  257,   41,   42,   43,   44,   45,   -1,   47,  257,   41,
  173,  273,   44,   -1,  276,   -1,  278,  279,  280,  276,
  257,  278,  279,  280,  273,  256,  257,  276,  195,  278,
  279,  280,  256,  257,   -1,   41,  273,  256,   -1,  276,
   -1,  278,  279,  280,  257,  276,   41,  140,  141,  273,
  256,  257,  276,   59,  278,  279,  280,  256,  257,   17,
  273,   40,   -1,  276,   -1,  278,  279,  280,   -1,   43,
  276,   45,  278,  279,  280,  256,  257,  276,   -1,  278,
  279,  280,  256,  257,   51,   52,   60,   -1,   62,   -1,
   41,   42,   43,   -1,   45,  276,   47,  278,  279,  280,
   -1,   80,  276,   -1,  278,  279,  280,   -1,   59,   60,
   -1,   62,   41,   42,   43,   -1,   45,  123,   47,  125,
   78,   79,   -1,  102,  103,   -1,  256,  257,  107,   -1,
   59,   60,   -1,   62,   41,   42,   43,   -1,   45,   -1,
   47,   -1,   -1,   -1,   -1,   -1,  276,  256,  257,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   41,   42,   43,
   -1,   45,   -1,   47,  131,  132,   -1,  276,  256,  257,
   -1,   -1,  123,   -1,  125,   59,   60,   -1,   62,   41,
   -1,   43,   -1,   45,  256,  257,   -1,   -1,  276,   -1,
  278,  279,   -1,   -1,  123,   -1,  125,   59,   60,   -1,
   62,   41,   -1,   43,  276,   45,  256,  257,  258,   -1,
   -1,  261,  262,  192,   -1,   -1,  123,  257,  125,   59,
   60,  271,   62,  263,   -1,  257,  276,  257,   -1,  269,
  270,  263,   -1,  263,  274,  275,   -1,   -1,  270,  123,
  270,  125,  274,  275,  274,  275,   -1,   -1,   -1,   -1,
  256,  257,  258,  259,  260,  261,  262,  256,  257,   -1,
   59,  123,  257,  125,  263,  271,   -1,   -1,  263,   -1,
  276,  270,   -1,   -1,   -1,  270,  275,   59,   -1,  274,
  275,   -1,  256,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,  265,  266,  267,  268,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,  123,  276,  125,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,  123,  271,  125,   -1,   -1,   -1,  276,  123,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,   -1,
  123,   -1,  276,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,
  123,   -1,  125,   -1,  276,   -1,  256,  257,  258,  259,
  260,  261,  262,   -1,  125,  265,  266,  267,  268,  269,
   41,  271,   43,  123,   45,   -1,  276,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   59,   60,
   -1,   62,   41,   -1,   43,   -1,   45,  256,  257,  258,
  259,  260,  261,  262,    8,    9,   10,   -1,  125,   -1,
   59,   60,  271,   62,   -1,  257,  258,  276,  260,  261,
  262,   -1,  257,  258,   -1,   -1,  261,  262,   -1,  271,
  125,   -1,   -1,   -1,  276,   39,  271,  272,   -1,   43,
   -1,  276,   -1,   -1,   48,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  125,   -1,   60,   -1,   -1,   -1,
   -1,   -1,   66,  256,  257,  258,   70,   71,  261,  262,
   -1,   -1,   -1,   -1,  123,   -1,  125,  270,  271,   -1,
   -1,   -1,  275,  276,  257,  258,  259,  260,  261,  262,
   -1,   -1,  125,   -1,   -1,  256,  257,  258,  271,  125,
  261,  262,   -1,  276,   -1,   -1,   -1,  257,  258,  270,
  271,  261,  262,   -1,  275,  276,   -1,  256,  257,  258,
  125,  271,  261,  262,   -1,   -1,  276,  125,   -1,   -1,
   -1,  270,  271,   -1,   -1,   -1,  275,  276,  142,  256,
  257,  258,   -1,   -1,  261,  262,   -1,   -1,  125,   -1,
   -1,   -1,   -1,  270,  271,  125,   -1,  161,  275,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,  125,   -1,   -1,   -1,   -1,  271,  125,   -1,  125,
   -1,  276,   -1,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,   -1,  276,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,   -1,  271,   -1,  257,  258,   -1,  276,  261,  262,
   -1,  257,  258,   -1,   -1,  261,  262,  270,  271,   -1,
   -1,   -1,  275,  276,  270,  271,   -1,   -1,   -1,  275,
  276,   -1,  257,  258,   -1,   -1,  261,  262,   -1,  257,
  258,   -1,   -1,  261,  262,  270,  271,   -1,   -1,   -1,
  275,  276,  270,  271,   -1,   -1,   -1,  275,  276,  256,
  257,  258,   -1,  260,  261,  262,  256,  257,  258,   -1,
   -1,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,  271,  256,  257,  258,   -1,  276,  261,  262,  257,
  258,  257,  258,  261,  262,  261,  262,  271,   -1,   -1,
   -1,   -1,  276,  271,   -1,  271,   -1,   -1,  276,   -1,
  276,  256,  257,  258,  257,  258,  261,  262,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,  271,   -1,
   -1,  276,   -1,  276,
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

//#line 547 "parser.y"
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

// Verifica si una variable se puede leer dependiendo de su semantica
public boolean puedoLeer(String IDCOMP){
    Atributo id = al.ts.obtener(IDCOMP);
    if (id.uso == Atributo.USO_PARAMETRO){
        if (id.esCR){
            errManager.error("La variable no puede ser usada para leer.", al.getLine());
            return false;
        }
    }
    return true;
}

public void run()
{
    yyparse();
    errManager.debug("Tabla de simbolos resultante" + '\n' +  al.ts.toString());
    errManager.debug("Tercetos resultante" + '\n' +  generador.tercetos.toString());
}

public void yyerror(String s) {
}
//#line 798 "Parser.java"
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
case 122:
//#line 369 "parser.y"
{
            int aux = generador.agregarTerceto(val_peek(2).sval, val_peek(0).sval, "+");
            yyval.sval = aux + "";
        }
break;
case 123:
//#line 374 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 124:
//#line 376 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 125:
//#line 378 "parser.y"
{
            int aux = generador.agregarTerceto(val_peek(2).sval, val_peek(0).sval, "-");
            yyval.sval = aux + "";
        }
break;
case 126:
//#line 383 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 127:
//#line 385 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 129:
//#line 388 "parser.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 130:
//#line 390 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 131:
//#line 395 "parser.y"
{
	        int aux = generador.agregarTerceto(val_peek(2).sval, val_peek(0).sval, "*");
	        yyval.sval = aux + "";
	    }
break;
case 132:
//#line 400 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 133:
//#line 402 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 134:
//#line 404 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 135:
//#line 406 "parser.y"
{
	        int aux = generador.agregarTerceto(val_peek(2).sval, val_peek(0).sval, "/");
            yyval.sval = aux + "";
        }
break;
case 136:
//#line 411 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 137:
//#line 413 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 138:
//#line 415 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 140:
//#line 421 "parser.y"
{
            if (puedoLeer(val_peek(0).sval)){
                /*pongo en factor el valor necesario para el terceto*/
                yyval.sval = val_peek(0).sval;
            }
        }
break;
case 141:
//#line 428 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 143:
//#line 431 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 145:
//#line 434 "parser.y"
{
            errManager.debug("Identificador con -", al.getLine());
            if (puedoLeer(val_peek(0).sval)){
                /*pongo en factor el valor necesario para el terceto*/
               yyval.sval = val_peek(0).sval;
            }
        }
break;
case 146:
//#line 442 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 147:
//#line 447 "parser.y"
{
	        errManager.debug("Llamado a funcion detectado", al.getLine());
	        /*generador.checkearLlamado();*/
	    }
break;
case 148:
//#line 452 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 149:
//#line 454 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 150:
//#line 456 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 151:
//#line 461 "parser.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 152:
//#line 465 "parser.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 153:
//#line 467 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 154:
//#line 469 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 155:
//#line 471 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 156:
//#line 476 "parser.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 158:
//#line 482 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 159:
//#line 484 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 160:
//#line 486 "parser.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 161:
//#line 488 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 162:
//#line 490 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 163:
//#line 495 "parser.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 164:
//#line 497 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 165:
//#line 499 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 166:
//#line 501 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 171:
//#line 516 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 172:
//#line 521 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 173:
//#line 526 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 174:
//#line 531 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 175:
//#line 536 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 176:
//#line 538 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 177:
//#line 543 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1577 "Parser.java"
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
