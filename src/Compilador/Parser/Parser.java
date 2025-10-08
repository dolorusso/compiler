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



//#line 6 "parser_test.y"
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
//#line 21 "Parser.java"




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
public final static short CTEL=258;
public final static short IF=259;
public final static short ELSE=260;
public final static short ENDIF=261;
public final static short PRINT=262;
public final static short RETURN=263;
public final static short LAMBDA=264;
public final static short ASIGNAR=265;
public final static short MENORIGUAL=266;
public final static short MAYORIGUAL=267;
public final static short IGUALIGUAL=268;
public final static short DISTINTO=269;
public final static short FLECHA=270;
public final static short LONG=271;
public final static short DO=272;
public final static short UNTIL=273;
public final static short TRUNC=274;
public final static short CR=275;
public final static short STRING=276;
public final static short CTEF=277;
public final static short IDCOMP=278;
public final static short CADENASTR=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    2,
    3,    3,    3,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    6,    6,    6,    6,   14,   14,   15,   15,
   15,    1,    1,    1,   17,   17,   16,   16,   16,   19,
   19,   19,   18,   18,   18,   18,   18,   18,   18,   18,
   18,    5,    5,    5,    5,    5,    5,    5,   20,   21,
   21,    8,    8,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   28,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   26,   26,   26,
   26,   27,   27,   27,   27,   29,   25,   25,   25,   25,
   25,   30,   30,   30,   30,   30,   30,   30,   13,   13,
    7,    7,    7,    7,   22,   22,   22,   22,   22,   31,
   31,   31,   33,   33,   33,   33,   33,    9,    9,    9,
    9,   10,   23,   23,   23,   23,   11,   32,   32,   32,
   32,   32,   32,   12,   12,   12,   12,   35,   35,   36,
   36,   34,   34,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    3,    2,    1,    1,    1,
    2,    2,    1,    1,    1,    2,    2,    2,    1,    2,
    2,    2,    4,    3,    3,    4,    3,    2,    3,    2,
    0,    1,    2,    2,    1,    2,    1,    3,    2,    1,
    3,    2,    3,    2,    3,    2,    1,    2,    3,    2,
    2,    2,    3,    2,    1,    2,    3,    2,    3,    1,
    1,    1,    1,    5,    5,    4,    4,    5,    5,    4,
    4,    5,    4,    5,    4,    4,    4,    4,    4,    4,
    4,    3,    3,    2,    4,    4,    2,    3,    2,    2,
    1,    3,    3,    2,    2,    3,    5,    5,    4,    4,
    4,    1,    1,    1,    1,    1,    1,    0,    1,    1,
    4,    4,    4,    4,    3,    3,    1,    2,    2,    3,
    3,    1,    1,    1,    1,    1,    1,    4,    4,    4,
    4,    2,    7,    6,    6,    4,    2,    3,    2,    3,
    2,    2,    1,    4,    5,    3,    4,    1,    3,    3,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  109,    0,
  110,    0,    0,    0,   32,    9,   10,    0,   13,   14,
   15,    0,    0,    0,   19,    0,   62,   63,    0,    7,
    0,   34,    0,    0,    0,    0,  152,    0,  153,    0,
  125,    0,  126,    0,    0,    0,    0,  122,  124,    0,
  143,    0,  132,  137,    0,    0,    0,   35,    0,    0,
    0,    0,    6,    0,    3,   33,   12,   58,   54,   11,
    0,   20,   16,   21,   17,   22,   18,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,   28,    0,    0,   37,    0,   40,    0,    0,   61,
    0,   24,    0,  119,  118,    0,  102,  103,  104,  105,
  106,  107,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   89,    0,    0,  142,  141,    0,  139,    0,
   95,    0,   94,   36,    0,    0,    0,    0,    0,    1,
   57,   53,    0,    0,   47,   25,    0,  149,  146,  151,
    0,  114,  113,  131,   48,   50,   44,    0,    0,    0,
   51,   46,   27,    0,   39,  129,    0,   42,    0,   30,
    0,   88,    0,    0,    0,   87,    0,    0,   84,    0,
    0,   76,    0,   73,    0,   75,    0,   71,   67,    0,
   70,   66,   77,  120,  121,  140,  138,  100,   99,   93,
   92,    0,  101,  112,  111,  130,  128,   23,   26,    0,
  147,  144,    0,   49,   45,   43,    0,   38,   41,   59,
   29,    0,    0,    0,   83,    0,   82,   72,   74,   69,
   65,   68,   64,   98,   97,  145,  150,    0,   85,   86,
   81,   79,   80,   78,    0,    0,    0,  135,  133,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   43,   23,
   24,   25,   26,   35,  102,   94,   59,   95,   96,   97,
   98,   99,  100,   27,   28,   45,   60,  122,   46,  115,
   47,   53,   48,   49,   29,  151,
};
final static short yysindex[] = {                      -110,
  286,  421,    0,  -16,  -12,   59,   17,   17,    0,  -81,
    0,   -6,  487,  516,    0,    0,    0,  -39,    0,    0,
    0,  -36,  -28,   14,    0,  -21,    0,    0,    9,    0,
  527,    0,  209,  -33,  -87,   22,    0,  -23,    0,   34,
    0,  209,    0,  145,  328,   30,   40,    0,    0,  191,
    0,  113,    0,    0,    6,   59,  627,    0,  591,   28,
  209,   41,    0,  545,    0,    0,    0,    0,    0,    0,
 -167,    0,    0,    0,    0,    0,    0,   44,   44,  228,
  -87,   27, -223, -131,    0,  -34,   55,   22, -153,  677,
 -157,    0, -118,  280,    0,    4,    0, -160,   35,    0,
  505,    0,   52,    0,    0,   80,    0,    0,    0,    0,
    0,    0,  315,  315,  209,  473,   57,  565,  -98,  -59,
  -57,   77,    0,  315,  315,    0,    0,  -19,    0,   24,
    0,  599,    0,    0,   59,   82,   20,   83,   10,    0,
    0,    0,  -87,  -87,    0,    0, -237,    0,    0,    0,
   58,    0,    0,    0,    0,    0,    0, -128,  -91, -112,
    0,    0,    0,  375,    0,    0,   65,    0, -104,    0,
  573,    0,   40,   40,   35,    0,  613, -101,    0,  -45,
    5,    0,  112,    0,  116,    0,   71,    0,    0,   84,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   85,    0,    0,    0,    0,    0,    0,    0,   -7,
    0,    0, -237,    0,    0,    0,  136,    0,    0,    0,
    0,  121,  126,   89,    0,  105,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  308,    0,    0,
    0,    0,    0,    0, -150,  635,  647,    0,    0,
};
final static short yyrindex[] = {                         0,
  200,    0,    0,    0,   47,    0,    0,    0,    0,    0,
    0,   79,    0,  206,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,   90,    0,    0,    0,  127,
    0,    0,    0,  249,    0,  451,  167,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  211,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   56,   98,    0,
    1,    0,    0,    0,    0,    0,    0,  430,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -61,    0,
    0,    0,    0,    0,    0,  459,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  659,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    1,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  357,  397,  252,    0,    0,    0,    0,  659,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -51,    0,    0,
    0,    0,    0,    0,    0,    0,  -49,    0,    0,
};
final static short yygindex[] = {                         0,
   54,   -4,    0,  363,    0,    0,    0,    0,    2,    0,
    0,    0,   -5,   15,  -46,    0,  314,  -56,  152,  -90,
    0,  894,    0,    0,    0,   16,  -15,   75,  188,    0,
   70,   31,   74,  -52,    0,  153,
};
final static int YYTABLESIZE=1029;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        188,
   31,  191,   22,   22,   71,  168,   91,   92,  113,   66,
  114,   22,    2,  225,   22,   22,   50,   51,   80,   70,
   37,  197,   73,  113,  153,  114,   66,   34,   93,  121,
   75,  150,   22,   62,  146,  101,  213,  165,   54,   39,
   81,   57,   32,   91,  166,  103,   22,  167,  168,   91,
  207,  236,   83,  167,  148,   31,   50,   51,   22,   66,
   22,  103,  113,  227,  114,   22,   64,   42,  105,   84,
  123,  130,   77,   62,   93,  136,  219,  113,  205,  114,
   91,  124,  199,   80,  159,  160,  125,  147,   93,  141,
   55,   91,  143,  144,  150,  154,  208,  209,   42,   56,
  181,  213,   22,  155,   91,   55,   55,  218,    6,  169,
  142,    7,    8,    9,   56,  182,  212,   22,   11,   22,
  172,   10,  148,  206,  149,   31,   37,   12,  214,  231,
  127,  127,  127,   22,  127,  193,  127,  161,  162,  148,
  203,   52,  233,  235,  217,   39,    1,  242,  127,  127,
  202,  127,  220,  129,  171,  113,   52,  114,   93,  223,
  237,  116,  185,  244,  215,  216,   66,  123,  123,  123,
  228,  123,   22,  123,  229,   55,  238,    6,   22,  239,
    7,    8,  173,  174,  240,  123,  123,  113,  123,  114,
   10,   56,  184,  186,  189,  192,   12,  194,  195,    8,
  116,  187,  116,  190,  112,    5,  111,  117,   60,  117,
    4,  117,  127,  139,  127,  224,   67,   68,  136,   72,
  134,  152,   87,   88,   37,  117,  117,   74,  117,  106,
   89,  127,  104,   36,   37,   78,  196,    9,   69,   22,
   38,   90,   11,   39,   40,   41,   22,   22,   22,  123,
   38,  123,   33,   39,   40,   41,   79,   31,   61,   31,
   36,   37,   31,   31,   82,  226,   36,   37,   92,   76,
   33,   31,   31,   36,   37,  204,   31,   38,   31,  198,
   39,   40,   41,   38,   36,   37,   39,   40,   41,  117,
   38,  117,   96,   39,   40,   41,  138,   36,   37,  210,
  135,   38,   55,   55,   39,   40,   41,   87,   36,   37,
   96,   56,   56,  211,   38,   36,   37,   39,   40,   41,
  163,   36,   37,  164,   55,   38,  230,    0,   39,   40,
   41,    0,   38,   56,  148,   39,   40,   41,   38,  232,
  234,   39,   40,   41,  241,  127,  127,  127,  127,  127,
  127,  127,  127,   52,   52,  127,  127,  127,  127,  127,
  243,  127,    0,  127,    0,    0,  127,  127,  127,    0,
  132,    0,   58,    0,   96,   52,   96,    0,    0,    0,
    0,    0,  123,  123,  123,  123,  123,  123,  123,  123,
    0,    0,  123,  123,  123,  123,  123,  115,  123,  115,
  123,  115,    0,  123,  123,  123,    0,  120,   13,    0,
  107,  108,  109,  110,    0,  115,  115,    0,  115,   58,
    0,  134,  117,  117,  117,  117,  117,  117,  117,  117,
  245,  132,  117,  117,  117,  117,  117,  116,  117,  116,
  117,  116,    0,  117,  117,  117,  126,   36,   37,    0,
  118,    0,  119,    0,    0,  116,  116,    0,  116,    0,
    0,    0,    0,    0,   38,   36,   37,   39,   40,   41,
   47,  127,  127,   47,  127,    0,  127,    0,  180,  115,
   58,  115,   38,    0,  145,   39,   40,   41,    0,    0,
  132,   89,    0,    0,  134,    0,    0,    0,    9,    0,
    0,    0,   90,   11,    0,  108,  108,   96,   96,   91,
   96,   96,   96,   96,   96,    0,    0,   90,    0,  116,
    0,  116,  108,   96,    0,  108,  108,  108,    0,   96,
    0,  179,    0,    0,    0,    0,  145,    0,    0,   58,
    0,    4,    5,   89,    6,   30,    0,    7,    8,    0,
    9,  246,    0,    0,   90,   11,    9,   10,  247,    0,
    0,   11,    0,   12,   55,    0,    6,    0,    0,    7,
    8,   36,   37,   91,    0,   91,    0,    0,    0,   10,
    0,   90,    0,   90,   55,   12,    6,  116,  117,    7,
    8,   39,   40,   41,    0,  177,    0,  178,    0,   10,
   58,    0,    0,    0,    0,   12,    0,   58,  134,  134,
    0,   63,  115,  115,  115,  115,  115,  115,  115,  115,
    0,    0,  115,  115,  115,  115,  115,    0,  115,  170,
  115,  145,    0,  115,  115,  115,    0,    0,   89,    0,
   65,    0,    0,    0,    0,    9,    0,    0,    0,   90,
   11,   85,  116,  116,  116,  116,  116,  116,  116,  116,
    0,    0,  116,  116,  116,  116,  116,    0,  116,  140,
  116,    0,    0,  116,  116,  116,    4,    5,    0,    6,
    0,    0,    7,    8,    0,    0,   47,    0,    0,  131,
    0,    9,   10,   47,    0,    0,   11,  221,   12,  127,
   47,    0,    0,    0,   47,   47,   91,   91,    0,   91,
   91,   91,   91,   91,   90,   90,    0,   90,   90,   90,
   90,   90,   91,  201,    0,    0,    0,    0,   91,   55,
   90,    6,    0,  176,    7,    8,   90,  131,    0,    0,
    0,    0,    4,    5,   10,    6,    0,    0,    7,    8,
   12,  131,    0,    0,    0,    0,    0,    9,   10,  248,
    4,    5,   11,    6,   12,    0,    7,    8,    0,    0,
    0,  249,    5,    0,    6,    9,   10,    7,    8,    0,
   11,    0,   12,    5,    0,    6,    9,   10,    7,    8,
    0,   11,    0,   12,    0,    0,    0,    9,   10,    0,
    0,    5,   11,    6,   12,    0,    7,    8,    0,    0,
    0,    0,    0,    0,    0,    9,   10,    0,    0,    0,
   11,   55,   12,    6,  116,  183,    7,    8,    0,    5,
    0,    6,    0,    0,    7,    8,   10,    0,    0,    0,
    0,    0,   12,    9,   10,    0,  133,   55,   11,    6,
   12,    0,    7,    8,  200,   55,    0,    6,    0,    0,
    7,    8,   10,    0,    0,    0,    0,    0,   12,   55,
   10,    6,    0,  222,    7,    8,   12,    0,    0,    0,
    0,    0,    0,   55,   10,    6,    0,    0,    7,    8,
   12,   55,    0,    6,    0,    0,    7,    8,   10,   44,
   52,   52,    0,   55,   12,    6,   10,    0,    7,    8,
    0,    0,   12,    0,   35,   35,    0,   35,   10,    0,
   35,   35,    0,    0,   12,    0,   86,    0,    0,    0,
   35,   52,  156,  157,    0,   44,   35,    0,    0,    0,
  158,    0,    0,  128,    0,    0,    0,    9,    0,   44,
    0,    0,   11,   44,  137,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  175,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   44,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   59,    1,    2,   44,   96,   40,   41,   43,   14,
   45,   10,  123,   59,   13,   14,   40,   41,   40,   59,
  258,   41,   59,   43,   59,   45,   31,   40,   34,   45,
   59,   84,   31,   40,   81,  123,   44,   94,    8,  277,
   26,  123,   59,   40,   41,   40,   45,   44,  139,   40,
   41,   59,   44,   44,  278,    2,   40,   41,   57,   64,
   59,   40,   43,   59,   45,   64,   13,   40,   38,   61,
   41,   56,   59,   40,   80,   60,  167,   43,   59,   45,
   40,   42,   59,   40,   90,   91,   47,   61,   94,  257,
   44,   40,   78,   79,  147,   41,  143,  144,   40,   44,
  116,   44,  101,  257,   40,   59,  257,  164,  259,  270,
  278,  262,  263,  271,   59,   59,   59,  116,  276,  118,
   41,  272,   44,   41,  256,  125,  258,  278,  257,   59,
   41,   42,   43,  132,   45,   59,   47,  256,  257,   61,
   59,   44,   59,   59,  257,  277,  257,   59,   59,   60,
  135,   62,  257,   41,  101,   43,   59,   45,  164,  261,
  213,  260,  261,   59,  256,  257,  171,   41,   42,   43,
   59,   45,  171,   47,   59,  257,   41,  259,  177,   59,
  262,  263,  113,  114,   59,   59,   60,   43,   62,   45,
  272,  273,  118,  119,  120,  121,  278,  124,  125,    0,
  260,  261,  260,  261,   60,    0,   62,   41,  270,   43,
    0,   45,  123,   62,  125,  261,  256,  257,  270,  256,
  270,  256,  256,  257,  258,   59,   60,  256,   62,   42,
  264,   41,  256,  257,  258,  257,  256,  271,  278,  238,
  274,  275,  276,  277,  278,  279,  245,  246,  247,  123,
  274,  125,  265,  277,  278,  279,  278,  257,  265,  259,
  257,  258,  262,  263,  256,  261,  257,  258,   41,  256,
  265,  271,  272,  257,  258,  256,  276,  274,  278,  256,
  277,  278,  279,  274,  257,  258,  277,  278,  279,  123,
  274,  125,   41,  277,  278,  279,  256,  257,  258,  147,
  273,  274,  256,  257,  277,  278,  279,  256,  257,  258,
   59,  256,  257,  256,  274,  257,  258,  277,  278,  279,
   41,  257,  258,   44,  278,  274,  256,   -1,  277,  278,
  279,   -1,  274,  278,  256,  277,  278,  279,  274,  256,
  256,  277,  278,  279,  256,  256,  257,  258,  259,  260,
  261,  262,  263,  256,  257,  266,  267,  268,  269,  270,
  256,  272,   -1,  274,   -1,   -1,  277,  278,  279,   -1,
   57,   -1,   10,   -1,  123,  278,  125,   -1,   -1,   -1,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,   41,  272,   43,
  274,   45,   -1,  277,  278,  279,   -1,   45,  123,   -1,
  266,  267,  268,  269,   -1,   59,   60,   -1,   62,   57,
   -1,   59,  256,  257,  258,  259,  260,  261,  262,  263,
  123,  118,  266,  267,  268,  269,  270,   41,  272,   43,
  274,   45,   -1,  277,  278,  279,  256,  257,  258,   -1,
  123,   -1,  125,   -1,   -1,   59,   60,   -1,   62,   -1,
   -1,   -1,   -1,   -1,  274,  257,  258,  277,  278,  279,
   41,   42,   43,   44,   45,   -1,   47,   -1,  116,  123,
  118,  125,  274,   -1,  257,  277,  278,  279,   -1,   -1,
  177,  264,   -1,   -1,  132,   -1,   -1,   -1,  271,   -1,
   -1,   -1,  275,  276,   -1,  257,  258,  256,  257,   59,
  259,  260,  261,  262,  263,   -1,   -1,   59,   -1,  123,
   -1,  125,  274,  272,   -1,  277,  278,  279,   -1,  278,
   -1,   59,   -1,   -1,   -1,   -1,  257,   -1,   -1,  177,
   -1,  256,  257,  264,  259,  125,   -1,  262,  263,   -1,
  271,  238,   -1,   -1,  275,  276,  271,  272,  245,   -1,
   -1,  276,   -1,  278,  257,   -1,  259,   -1,   -1,  262,
  263,  257,  258,  123,   -1,  125,   -1,   -1,   -1,  272,
   -1,  123,   -1,  125,  257,  278,  259,  260,  261,  262,
  263,  277,  278,  279,   -1,  123,   -1,  125,   -1,  272,
  238,   -1,   -1,   -1,   -1,  278,   -1,  245,  246,  247,
   -1,  125,  256,  257,  258,  259,  260,  261,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,   -1,  272,  125,
  274,  257,   -1,  277,  278,  279,   -1,   -1,  264,   -1,
  125,   -1,   -1,   -1,   -1,  271,   -1,   -1,   -1,  275,
  276,  125,  256,  257,  258,  259,  260,  261,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,   -1,  272,  125,
  274,   -1,   -1,  277,  278,  279,  256,  257,   -1,  259,
   -1,   -1,  262,  263,   -1,   -1,  257,   -1,   -1,  125,
   -1,  271,  272,  264,   -1,   -1,  276,  125,  278,  270,
  271,   -1,   -1,   -1,  275,  276,  256,  257,   -1,  259,
  260,  261,  262,  263,  256,  257,   -1,  259,  260,  261,
  262,  263,  272,  125,   -1,   -1,   -1,   -1,  278,  257,
  272,  259,   -1,  261,  262,  263,  278,  125,   -1,   -1,
   -1,   -1,  256,  257,  272,  259,   -1,   -1,  262,  263,
  278,  125,   -1,   -1,   -1,   -1,   -1,  271,  272,  125,
  256,  257,  276,  259,  278,   -1,  262,  263,   -1,   -1,
   -1,  125,  257,   -1,  259,  271,  272,  262,  263,   -1,
  276,   -1,  278,  257,   -1,  259,  271,  272,  262,  263,
   -1,  276,   -1,  278,   -1,   -1,   -1,  271,  272,   -1,
   -1,  257,  276,  259,  278,   -1,  262,  263,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,
  276,  257,  278,  259,  260,  261,  262,  263,   -1,  257,
   -1,  259,   -1,   -1,  262,  263,  272,   -1,   -1,   -1,
   -1,   -1,  278,  271,  272,   -1,  256,  257,  276,  259,
  278,   -1,  262,  263,  256,  257,   -1,  259,   -1,   -1,
  262,  263,  272,   -1,   -1,   -1,   -1,   -1,  278,  257,
  272,  259,   -1,  261,  262,  263,  278,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  272,  259,   -1,   -1,  262,  263,
  278,  257,   -1,  259,   -1,   -1,  262,  263,  272,    6,
    7,    8,   -1,  257,  278,  259,  272,   -1,  262,  263,
   -1,   -1,  278,   -1,  256,  257,   -1,  259,  272,   -1,
  262,  263,   -1,   -1,  278,   -1,   33,   -1,   -1,   -1,
  272,   38,  256,  257,   -1,   42,  278,   -1,   -1,   -1,
  264,   -1,   -1,   50,   -1,   -1,   -1,  271,   -1,   56,
   -1,   -1,  276,   60,   61,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  115,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  135,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=279;
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
null,null,null,null,null,null,null,"ID","CTEL","IF","ELSE","ENDIF","PRINT",
"RETURN","LAMBDA","ASIGNAR","MENORIGUAL","MAYORIGUAL","IGUALIGUAL","DISTINTO",
"FLECHA","LONG","DO","UNTIL","TRUNC","CR","STRING","CTEF","IDCOMP","CADENASTR",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID '{' lista_sentencias '}'",
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
"condicional_opt : '(' condicion",
"condicional_opt : condicion",
"cuerpo_opt : '{' lista_sentencias_ejecutables '}'",
"cuerpo_opt : '{' lista_sentencias_ejecutables error",
"cuerpo_opt : lista_sentencias_ejecutables error",
"cuerpo_opt : '{' '}'",
"condicion : expresion comparador expresion",
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
"comparador :",
"tipo : LONG",
"tipo : STRING",
"asignacion : IDCOMP ASIGNAR expresion ';'",
"asignacion : IDCOMP ASIGNAR expresion error",
"asignacion : ID ASIGNAR expresion ';'",
"asignacion : ID ASIGNAR expresion error",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : TRUNC cuerpo_expresion",
"expresion : TRUNC error",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : IDCOMP",
"factor : constante",
"factor : CADENASTR",
"factor : llamada_funcion",
"factor : ID",
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
};

//#line 330 "parser_test.y"
private AnalizadorLexico al;
private ErrorManager errManager;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    //errManager.
}

public int yylex(){
    int token = al.yylex();
    errManager.debug("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ") En linea " + al.getLine());

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
//#line 669 "Parser.java"
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
//#line 19 "parser_test.y"
{ errManager.debug("Declaracion de programa detectada", al.getLine()); }
break;
case 2:
//#line 22 "parser_test.y"
{ errManager.error("Declaracion de programa invalida", al.getLine()); }
break;
case 3:
//#line 25 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 4:
//#line 28 "parser_test.y"
{ System.out.println("Falta cierre del programa '}'. Linea: " + al.getLine()); }
break;
case 5:
//#line 29 "parser_test.y"
{ System.out.println("Faltan delimitadores de programa '{' y '}'. Linea: " + al.getLine()); }
break;
case 6:
//#line 30 "parser_test.y"
{ System.out.println("Programa vacio. Linea: " + al.getLine()); }
break;
case 7:
//#line 31 "parser_test.y"
{ System.out.println("Falta ID del programa. Linea: " + al.getLine()); }
break;
case 8:
//#line 32 "parser_test.y"
{ System.out.println("Faltan delimitadores de programa '{' y '}'. Linea: " + al.getLine()); }
break;
case 12:
//#line 42 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 14:
//#line 47 "parser_test.y"
{ System.out.println("Asignacion detectada"); }
break;
case 18:
//#line 51 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 20:
//#line 53 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 21:
//#line 54 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 22:
//#line 55 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 23:
//#line 60 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 24:
//#line 62 "parser_test.y"
{ System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
break;
case 25:
//#line 64 "parser_test.y"
{ System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
break;
case 26:
//#line 66 "parser_test.y"
{ System.out.println("No se debe especificar la unidad en declaracion de funcion. Linea: " + al.getLine()); }
break;
case 28:
//#line 71 "parser_test.y"
{ System.out.println("Parametros formales invalidos. Linea: " + al.getLine()); }
break;
case 30:
//#line 76 "parser_test.y"
{ System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
break;
case 31:
//#line 77 "parser_test.y"
{ System.out.println("Faltan llaves de la funcion. Linea: " + al.getLine()); }
break;
case 34:
//#line 84 "parser_test.y"
{ System.out.println("PROBLEMON. Linea: " + al.getLine()); }
break;
case 42:
//#line 101 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 43:
//#line 105 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 44:
//#line 106 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 45:
//#line 107 "parser_test.y"
{ System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
break;
case 46:
//#line 108 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 47:
//#line 109 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 48:
//#line 110 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 49:
//#line 111 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 50:
//#line 112 "parser_test.y"
{ System.out.println("Se espera un tipo pero se encontro .... Linea: " + al.getLine()); }
break;
case 51:
//#line 113 "parser_test.y"
{ System.out.println("Se espera un Identifier para el parametro formal  Linea: " + al.getLine()); }
break;
case 52:
//#line 118 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 53:
//#line 119 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 54:
//#line 120 "parser_test.y"
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 55:
//#line 121 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 56:
//#line 122 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 57:
//#line 123 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 58:
//#line 124 "parser_test.y"
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 59:
//#line 130 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 64:
//#line 146 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 65:
//#line 148 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 66:
//#line 150 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 67:
//#line 152 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 68:
//#line 154 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 69:
//#line 156 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 158 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 71:
//#line 160 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 162 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 73:
//#line 164 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 166 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 75:
//#line 168 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 170 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 77:
//#line 172 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 80:
//#line 179 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 81:
//#line 181 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 183 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 83:
//#line 185 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
//#line 187 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
//#line 189 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 86:
//#line 191 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 87:
//#line 193 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 89:
//#line 198 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 90:
//#line 199 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 91:
//#line 200 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 93:
//#line 205 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 94:
//#line 206 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 95:
//#line 207 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 96:
//#line 212 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 97:
//#line 217 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 98:
//#line 219 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 99:
//#line 221 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 100:
//#line 223 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 101:
//#line 225 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 108:
//#line 235 "parser_test.y"
{ errManager.error("Falta comparador", al.getLine()); }
break;
case 111:
//#line 244 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 112:
//#line 245 "parser_test.y"
{ System.out.println("Falta ;. Linea: " + al.getLine()); }
break;
case 113:
//#line 246 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 114:
//#line 247 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 117:
//#line 253 "parser_test.y"
{System.out.println("ENCONTRE TERMINO!. Linea: " + al.getLine()); }
break;
case 118:
//#line 254 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 119:
//#line 255 "parser_test.y"
{System.out.println("Falta delimitadores trunc. Linea: " + al.getLine()); }
break;
case 127:
//#line 269 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 128:
//#line 273 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 129:
//#line 274 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 130:
//#line 275 "parser_test.y"
{System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
break;
case 131:
//#line 276 "parser_test.y"
{System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
break;
case 132:
//#line 280 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 133:
//#line 284 "parser_test.y"
{ errManager.debug("Definicion lambda detectada. Linea: " + al.getLine()); }
break;
case 134:
//#line 286 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 135:
//#line 288 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 136:
//#line 290 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 137:
//#line 294 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 141:
//#line 301 "parser_test.y"
{System.out.println("Faltan argumentos. Linea: " + al.getLine());}
break;
case 142:
//#line 302 "parser_test.y"
{System.out.println("Falta parentesis de cierre. Linea: " + al.getLine());}
break;
case 143:
//#line 303 "parser_test.y"
{System.out.println("Falta parentesis de apertura. Linea: " + al.getLine());}
break;
case 144:
//#line 307 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 145:
//#line 308 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
break;
case 146:
//#line 309 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
break;
case 147:
//#line 310 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine());}
break;
//#line 1218 "Parser.java"
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
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
