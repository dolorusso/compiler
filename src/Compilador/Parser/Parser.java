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
   26,   27,   27,   27,   27,   29,   29,   25,   25,   25,
   25,   25,   30,   30,   30,   30,   30,   30,   13,   13,
    7,    7,    7,    7,   22,   22,   22,   22,   22,   31,
   31,   31,   33,   33,   33,   33,   33,    9,    9,    9,
    9,   10,   23,   23,   23,   23,   11,   32,   32,   32,
   32,   32,   32,   12,   12,   12,   12,   35,   35,   36,
   36,   34,   34,   34,   34,
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
    1,    3,    3,    2,    2,    3,    2,    5,    5,    4,
    4,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    4,    4,    4,    3,    3,    1,    2,    2,    3,
    3,    1,    1,    1,    1,    1,    1,    4,    4,    4,
    4,    2,    7,    6,    6,    4,    2,    3,    2,    3,
    2,    2,    1,    4,    5,    3,    4,    1,    3,    3,
    1,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  109,    0,
  110,    0,    0,    0,   32,    9,   10,    0,   13,   14,
   15,    0,    0,    0,   19,    0,   62,   63,    0,    7,
    0,   34,    0,    0,    0,    0,  152,    0,  153,    0,
  125,    0,    0,  126,    0,    0,    0,    0,  122,  124,
    0,  143,    0,  132,  137,    0,    0,    0,   35,    0,
    0,    0,    0,    6,    0,    3,   33,   12,   58,   54,
   11,    0,   20,   16,   21,   17,   22,   18,    0,    0,
    0,    0,    0,    0,    0,    2,    0,    0,    0,    0,
    0,    0,   28,    0,    0,   37,    0,   40,    0,    0,
   61,    0,   24,    0,  119,  118,    0,  154,  155,   97,
  103,  104,  105,  106,  107,  108,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   89,    0,    0,  142,
  141,    0,  139,    0,   95,    0,   94,   36,    0,    0,
    0,    0,    0,    1,   57,   53,    0,    0,   47,   25,
    0,  149,  146,  151,    0,  114,  113,  131,   48,   50,
   44,    0,    0,    0,   51,   46,   27,    0,   39,  129,
    0,   42,    0,   30,    0,   88,    0,    0,    0,   87,
    0,    0,   84,    0,    0,   76,    0,   73,    0,   75,
    0,   71,   67,    0,   70,   66,   77,  120,  121,  140,
  138,  101,  100,   93,   92,    0,  102,  112,  111,  130,
  128,   23,   26,    0,  147,  144,    0,   49,   45,   43,
    0,   38,   41,   59,   29,    0,    0,    0,   83,    0,
   82,   72,   74,   69,   65,   68,   64,   99,   98,  145,
  150,    0,   85,   86,   81,   79,   80,   78,    0,    0,
    0,  135,  133,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   44,   23,
   24,   25,   26,   35,  103,   95,   60,   96,   97,   98,
   99,  100,  101,   27,   28,   46,   61,  126,   47,  119,
   48,   54,   49,   50,   29,  155,
};
final static short yysindex[] = {                      -104,
  266,  478,    0,   -4,  -30,   66,   36,   36,    0,  -94,
    0,  -29,  501,  286,    0,    0,    0,   85,    0,    0,
    0,  -42,   44,   64,    0,  -40,    0,    0,  -31,    0,
  523,    0,  201,   -9,  -76,   26,    0,   18,    0,   33,
    0,  201, -221,    0,  428,  351,   54,   49,    0,    0,
   98,    0,  121,    0,    0,    6,   66,  607,    0,  236,
   48,  201,   60,    0,  549,    0,    0,    0,    0,    0,
    0, -196,    0,    0,    0,    0,    0,    0,   67,   67,
  190,  -76,   69, -193,  187,    0,  -38,   68,   26, -136,
  248, -227,    0, -158,  245,    0,   24,    0, -145,   41,
    0,  512,    0,   73,    0,    0,   86,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  205,  205,  201,  467,
   78,  556, -159,   51,   75,   82,    0,  205,  205,    0,
    0,  -23,    0,   76,    0,  584,    0,    0,   66,   83,
   35,  104,   30,    0,    0,    0,  -76,  -76,    0,    0,
  -18,    0,    0,    0,  -35,    0,    0,    0,    0,    0,
    0, -105, -124, -103,    0,    0,    0,  -77,    0,    0,
   91,    0,  -98,    0,  573,    0,   49,   49,   41,    0,
  596,  -90,    0,  -53,  -45,    0,  108,    0,  116,    0,
   90,    0,    0,   97,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  101,    0,    0,    0,    0,
    0,    0,    0,   13,    0,    0,  -18,    0,    0,    0,
  140,    0,    0,    0,    0,  123,  127,  111,    0,  126,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  337,    0,    0,    0,    0,    0,    0,  489,  618,
  630,    0,    0,
};
final static short yyrindex[] = {                         0,
  188,    0,    0,    0,  102,    0,    0,    0,    0,    0,
    0,  -19,    0,  192,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,  160,    0,    0,    0,  319,
    0,    0,    0,    0,    0,    0,  430,  131,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  195,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  156,  240,
    0,    1,    0,    0,    0,    0,    0,    0,  363,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -74,
    0,    0,    0,    0,    0,    0,  444,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  656,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  390,  410,  -33,    0,
    0,    0,    0,  656,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -73,    0,    0,    0,    0,    0,    0,    0,    0,
  -66,    0,    0,
};
final static short yygindex[] = {                         0,
   38,   14,    0,   -8,    0,    0,    0,    0,    2,    0,
    0,    0,  550,   71,  -28,    0,  379,  -52,  143,  -56,
    0,  865,    0,    0,    0,  -22,  -12,   87,  171,    0,
   40,   45,   19,  -62,    0,  106,
};
final static int YYTABLESIZE=1004;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         81,
   31,   59,   22,   22,  117,  229,  118,   96,  217,   34,
   63,   22,   84,  231,   22,   22,   74,  201,    2,  117,
  157,  118,  154,  216,  148,   96,   43,   67,   58,   85,
   92,   93,   22,  125,  134,   43,  108,  124,  140,   31,
  172,  148,  169,    9,   67,  104,  102,   22,   11,   59,
   65,  138,   55,  150,   32,  109,  217,   51,   52,   22,
  145,   22,   43,   92,  170,  104,   22,  171,   43,   92,
  211,  240,   63,  171,   43,   51,   52,  117,   67,  118,
   43,  146,  106,  117,  152,  118,  172,   42,  154,   96,
  128,   96,   43,  209,  127,  129,   82,  165,  166,   92,
  120,  189,   76,   22,   43,   42,   81,  185,  158,  192,
   43,  184,   92,   59,  223,  222,  206,   43,  212,  213,
  159,   22,   78,   22,  173,   31,  176,  138,   72,  151,
   92,  219,  220,  195,  203,   43,  186,   22,  131,  175,
  197,  207,   43,   71,  210,   55,  198,  199,  235,  147,
  148,  218,    1,  221,  241,  237,  177,  178,  224,  239,
   55,  133,   56,  117,    6,  118,  232,    7,    8,  246,
  227,  117,   59,  117,  233,  117,   22,   10,   57,  149,
  242,  243,   22,   12,  248,  244,   90,    8,   67,  117,
  117,    5,  117,    9,    4,   60,  136,   91,   11,   56,
  127,  127,  127,  134,  127,  143,  127,  228,  188,  190,
  193,  196,  107,   73,   56,  230,   79,  156,  127,  127,
  215,  127,   96,   96,   83,   96,   96,   96,   96,   96,
   93,   43,  200,   59,   33,   62,  148,   80,   96,   37,
   59,  138,  138,   22,   96,   43,   88,   89,   37,   43,
   22,   22,   22,  117,   90,  117,  214,   31,   39,   31,
    0,    9,   31,   31,   38,   91,   11,   39,   40,   41,
   33,   31,   31,  105,   36,   37,   31,    0,   31,    0,
   36,   37,  127,   52,  127,  167,   36,   37,  168,    0,
  208,   38,   36,   37,   39,   40,   41,   38,   52,   75,
   39,   40,   41,   38,   36,   37,   39,   40,   41,   38,
  120,  191,   39,   40,   41,  142,   36,   37,    0,   77,
  139,   38,   36,   37,   39,   40,   41,    0,   88,   36,
   37,  202,    0,   38,  120,  194,   39,   40,   41,   38,
   68,   69,   39,   40,   41,  234,   38,   36,   37,   39,
   40,   41,  236,  130,   36,   37,  238,   55,   55,  123,
  123,  123,   70,  123,   38,  123,  245,   39,   40,   41,
    0,   38,    0,    0,   39,   40,   41,  123,  123,   55,
  123,  247,    0,    0,    0,    0,  117,  117,   13,  117,
  117,  117,  117,  117,    0,    0,  117,  117,  117,  117,
  117,    0,  117,   47,  127,  127,   47,  127,  117,  127,
   66,   56,   56,    0,    0,  127,  127,    0,  127,  127,
  127,  127,  127,    0,    0,  127,  127,  127,  127,  127,
  115,  127,  115,   56,  115,    0,  136,  127,    0,    0,
    0,  123,  153,  123,   37,    0,  149,    0,  115,  115,
  116,  115,  116,   90,  116,    0,    0,   36,   37,  249,
    9,   36,   37,   39,   91,   11,    0,    0,  116,  116,
  117,  116,  118,  122,   38,  123,    0,   39,   40,   41,
    0,   39,   40,   41,    0,    0,    0,  116,   91,  115,
    0,  137,   56,    0,    6,   52,   52,    7,    8,    0,
  136,  149,   90,  160,  161,    0,    0,   10,   90,    0,
    0,  162,  115,   12,  115,    9,    0,   52,    9,   91,
   11,    4,    5,   11,    6,  183,    0,    7,    8,    0,
    0,    0,  116,    0,  116,    0,    9,   10,    0,    0,
    0,   11,    5,   12,    6,    0,    0,    7,    8,    0,
    0,    0,   91,    0,   91,    0,    9,   10,    0,  136,
    0,   11,    0,   12,    0,    0,   90,    0,   90,    0,
    0,    0,    0,    0,  123,  123,    0,  123,  123,  123,
  123,  123,    0,   94,  123,  123,  123,  123,  123,  181,
  123,  182,    0,   56,    0,    6,  123,    0,    7,    8,
    0,    0,   30,    0,    0,    0,    0,   56,   10,    6,
  120,  121,    7,    8,   12,    0,    0,    0,    0,   47,
  250,    0,   10,    0,    0,   64,   47,  251,   12,    0,
   94,    0,  127,   47,    0,    0,  174,   47,   47,    0,
  163,  164,    0,    0,   94,  115,  115,   86,  115,  115,
  115,  115,  115,    0,    0,  115,  115,  115,  115,  115,
    0,  115,    0,    0,    0,  116,  116,  115,  116,  116,
  116,  116,  116,  144,    0,  116,  116,  116,  116,  116,
  135,  116,    0,  110,    0,   91,   91,  116,   91,   91,
   91,   91,   91,  111,  112,  113,  114,  225,    0,   90,
   90,   91,   90,   90,   90,   90,   90,   91,  205,    0,
    0,    0,    0,    0,    0,   90,    0,   94,    0,    0,
  135,   90,    0,   56,    0,    6,    0,  180,    7,    8,
    0,  135,    0,    4,    5,    0,    6,    0,   10,    7,
    8,    0,  252,    0,   12,   56,    0,    6,    9,   10,
    7,    8,    0,   11,  253,   12,    4,    5,    0,    6,
   10,    0,    7,    8,    0,    0,   12,    4,    5,    0,
    6,    9,   10,    7,    8,    0,   11,    0,   12,    5,
    0,    6,    9,   10,    7,    8,    0,   11,    0,   12,
    0,    0,    0,    9,   10,    0,    0,    0,   11,    0,
   12,    0,    0,    0,    0,    5,    0,    6,    0,    0,
    7,    8,   56,    0,    6,  120,  187,    7,    8,    9,
   10,    0,    0,    0,   11,    0,   12,   10,    0,    5,
    0,    6,    0,   12,    7,    8,    0,    0,    0,  204,
   56,    0,    6,    9,   10,    7,    8,    0,   11,    0,
   12,    0,   56,    0,    6,   10,  226,    7,    8,    0,
    0,   12,    0,   56,    0,    6,    0,   10,    7,    8,
   45,   53,   53,   12,   56,    0,    6,    0,   10,    7,
    8,    0,    0,    0,   12,    0,   56,    0,    6,   10,
    0,    7,    8,    0,    0,   12,    0,   87,    0,    0,
    0,   10,   53,    0,    0,    0,   45,   12,    0,    0,
    0,   35,   35,    0,   35,  132,    0,   35,   35,    0,
    0,   45,    0,    0,    0,   45,  141,   35,    0,    0,
    0,    0,    0,   35,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  179,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   45,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   10,    1,    2,   43,   59,   45,   41,   44,   40,
   40,   10,   44,   59,   13,   14,   59,   41,  123,   43,
   59,   45,   85,   59,   44,   59,   45,   14,  123,   61,
   40,   41,   31,   46,   57,   45,  258,   46,   61,    2,
   97,   61,   95,  271,   31,   40,  123,   46,  276,   58,
   13,   60,    8,   82,   59,  277,   44,   40,   41,   58,
  257,   60,   45,   40,   41,   40,   65,   44,   45,   40,
   41,   59,   40,   44,   45,   40,   41,   43,   65,   45,
   45,  278,   38,   43,  278,   45,  143,   40,  151,  123,
   42,  125,   45,   59,   41,   47,   26,  256,  257,   40,
  260,  261,   59,  102,   45,   40,   40,  120,   41,   59,
   45,  120,   40,  122,  171,  168,  139,   45,  147,  148,
  257,  120,   59,  122,  270,  125,   41,  136,   44,   61,
   40,  256,  257,   59,   59,   45,   59,  136,   41,  102,
   59,   59,   45,   59,   41,   44,  128,  129,   59,   79,
   80,  257,  257,  257,  217,   59,  117,  118,  257,   59,
   59,   41,  257,   43,  259,   45,   59,  262,  263,   59,
  261,   41,  181,   43,   59,   45,  175,  272,  273,  257,
   41,   59,  181,  278,   59,   59,  264,    0,  175,   59,
   60,    0,   62,  271,    0,  270,  270,  275,  276,   44,
   41,   42,   43,  270,   45,   63,   47,  261,  122,  123,
  124,  125,   42,  256,   59,  261,  257,  256,   59,   60,
  256,   62,  256,  257,  256,  259,  260,  261,  262,  263,
   41,   45,  256,  242,  265,  265,  256,  278,  272,  258,
  249,  250,  251,  242,  278,   45,  256,  257,  258,   45,
  249,  250,  251,  123,  264,  125,  151,  257,  277,  259,
   -1,  271,  262,  263,  274,  275,  276,  277,  278,  279,
  265,  271,  272,  256,  257,  258,  276,   -1,  278,   -1,
  257,  258,  123,   44,  125,   41,  257,  258,   44,   -1,
  256,  274,  257,  258,  277,  278,  279,  274,   59,  256,
  277,  278,  279,  274,  257,  258,  277,  278,  279,  274,
  260,  261,  277,  278,  279,  256,  257,  258,   -1,  256,
  273,  274,  257,  258,  277,  278,  279,   -1,  256,  257,
  258,  256,   -1,  274,  260,  261,  277,  278,  279,  274,
  256,  257,  277,  278,  279,  256,  274,  257,  258,  277,
  278,  279,  256,  256,  257,  258,  256,  256,  257,   41,
   42,   43,  278,   45,  274,   47,  256,  277,  278,  279,
   -1,  274,   -1,   -1,  277,  278,  279,   59,   60,  278,
   62,  256,   -1,   -1,   -1,   -1,  256,  257,  123,  259,
  260,  261,  262,  263,   -1,   -1,  266,  267,  268,  269,
  270,   -1,  272,   41,   42,   43,   44,   45,  278,   47,
  125,  256,  257,   -1,   -1,  256,  257,   -1,  259,  260,
  261,  262,  263,   -1,   -1,  266,  267,  268,  269,  270,
   41,  272,   43,  278,   45,   -1,   58,  278,   -1,   -1,
   -1,  123,  256,  125,  258,   -1,  257,   -1,   59,   60,
   41,   62,   43,  264,   45,   -1,   -1,  257,  258,  123,
  271,  257,  258,  277,  275,  276,   -1,   -1,   59,   60,
   43,   62,   45,  123,  274,  125,   -1,  277,  278,  279,
   -1,  277,  278,  279,   -1,   -1,   -1,   60,   59,   62,
   -1,  256,  257,   -1,  259,  256,  257,  262,  263,   -1,
  122,  257,   59,  256,  257,   -1,   -1,  272,  264,   -1,
   -1,  264,  123,  278,  125,  271,   -1,  278,  271,  275,
  276,  256,  257,  276,  259,   59,   -1,  262,  263,   -1,
   -1,   -1,  123,   -1,  125,   -1,  271,  272,   -1,   -1,
   -1,  276,  257,  278,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,  123,   -1,  125,   -1,  271,  272,   -1,  181,
   -1,  276,   -1,  278,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,   -1,   -1,  256,  257,   -1,  259,  260,  261,
  262,  263,   -1,   34,  266,  267,  268,  269,  270,  123,
  272,  125,   -1,  257,   -1,  259,  278,   -1,  262,  263,
   -1,   -1,  125,   -1,   -1,   -1,   -1,  257,  272,  259,
  260,  261,  262,  263,  278,   -1,   -1,   -1,   -1,  257,
  242,   -1,  272,   -1,   -1,  125,  264,  249,  278,   -1,
   81,   -1,  270,  271,   -1,   -1,  125,  275,  276,   -1,
   91,   92,   -1,   -1,   95,  256,  257,  125,  259,  260,
  261,  262,  263,   -1,   -1,  266,  267,  268,  269,  270,
   -1,  272,   -1,   -1,   -1,  256,  257,  278,  259,  260,
  261,  262,  263,  125,   -1,  266,  267,  268,  269,  270,
  125,  272,   -1,  256,   -1,  256,  257,  278,  259,  260,
  261,  262,  263,  266,  267,  268,  269,  125,   -1,  256,
  257,  272,  259,  260,  261,  262,  263,  278,  125,   -1,
   -1,   -1,   -1,   -1,   -1,  272,   -1,  168,   -1,   -1,
  125,  278,   -1,  257,   -1,  259,   -1,  261,  262,  263,
   -1,  125,   -1,  256,  257,   -1,  259,   -1,  272,  262,
  263,   -1,  125,   -1,  278,  257,   -1,  259,  271,  272,
  262,  263,   -1,  276,  125,  278,  256,  257,   -1,  259,
  272,   -1,  262,  263,   -1,   -1,  278,  256,  257,   -1,
  259,  271,  272,  262,  263,   -1,  276,   -1,  278,  257,
   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,  278,
   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,   -1,
  262,  263,  257,   -1,  259,  260,  261,  262,  263,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,  272,   -1,  257,
   -1,  259,   -1,  278,  262,  263,   -1,   -1,   -1,  256,
  257,   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,
  278,   -1,  257,   -1,  259,  272,  261,  262,  263,   -1,
   -1,  278,   -1,  257,   -1,  259,   -1,  272,  262,  263,
    6,    7,    8,  278,  257,   -1,  259,   -1,  272,  262,
  263,   -1,   -1,   -1,  278,   -1,  257,   -1,  259,  272,
   -1,  262,  263,   -1,   -1,  278,   -1,   33,   -1,   -1,
   -1,  272,   38,   -1,   -1,   -1,   42,  278,   -1,   -1,
   -1,  256,  257,   -1,  259,   51,   -1,  262,  263,   -1,
   -1,   57,   -1,   -1,   -1,   61,   62,  272,   -1,   -1,
   -1,   -1,   -1,  278,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  119,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  139,
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
"constante : '-' CTEL",
"constante : '-' CTEF",
};

//#line 397 "parser_test.y"
private AnalizadorLexico al;
private ErrorManager errManager;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
}

public int yylex(){
    int token = al.yylex();
    errManager.debug("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ")", al.getLine());

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
//#line 21 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 3:
//#line 23 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 4:
//#line 25 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 5:
//#line 27 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 6:
//#line 29 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 7:
//#line 31 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 8:
//#line 33 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 44 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 20:
//#line 56 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 58 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
//#line 60 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 65 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 24:
//#line 67 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 25:
//#line 69 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 26:
//#line 71 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 28:
//#line 76 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 30:
//#line 81 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 31:
//#line 82 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 34:
//#line 90 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 42:
//#line 108 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 43:
//#line 113 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 44:
//#line 115 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 45:
//#line 117 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 46:
//#line 119 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 47:
//#line 121 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 48:
//#line 123 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 49:
//#line 125 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
//#line 127 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 129 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 135 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 53:
//#line 137 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
//#line 139 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 55:
//#line 141 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 56:
//#line 143 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 57:
//#line 145 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 58:
//#line 147 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 59:
//#line 154 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 64:
//#line 170 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 65:
//#line 172 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
//#line 174 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 67:
//#line 176 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
//#line 178 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 69:
//#line 180 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 182 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 71:
//#line 184 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 186 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 73:
//#line 188 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 190 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 75:
//#line 192 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 194 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 77:
//#line 196 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 80:
//#line 203 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 81:
//#line 205 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 207 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 83:
//#line 209 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
//#line 211 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
//#line 213 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 86:
//#line 215 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 87:
//#line 217 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 89:
//#line 223 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 90:
//#line 225 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 91:
//#line 227 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 93:
//#line 233 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 94:
//#line 235 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 95:
//#line 237 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 96:
//#line 243 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 97:
//#line 245 "parser_test.y"
{ errManager.error("Comparador de condificion invalido/faltante", al.getLine()); }
break;
case 98:
//#line 250 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 99:
//#line 252 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 100:
//#line 254 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 101:
//#line 256 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 102:
//#line 258 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 111:
//#line 278 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 112:
//#line 280 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 113:
//#line 282 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 114:
//#line 284 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 118:
//#line 292 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 119:
//#line 294 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 127:
//#line 309 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 128:
//#line 314 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 129:
//#line 316 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 130:
//#line 318 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 131:
//#line 320 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 132:
//#line 325 "parser_test.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 133:
//#line 329 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 134:
//#line 331 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 135:
//#line 333 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 136:
//#line 335 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 137:
//#line 340 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 139:
//#line 346 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 140:
//#line 348 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 141:
//#line 350 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 142:
//#line 352 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 143:
//#line 354 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 144:
//#line 359 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 145:
//#line 361 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 146:
//#line 363 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 147:
//#line 365 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 154:
//#line 382 "parser_test.y"
{
            
            errManager.debug("HAY: " + yyval, al.getLine());
            errManager.debug("HAY: " + val_peek(0).obj, al.getLine());
            String lexema = val_peek(0).sval; /* el lexema de la constante*/
            errManager.debug("Constante negativa detectada: " + lexema, al.getLine());
            /* Almacenar como nueva entrada en tabla de simbolos*/
            Atributo nuevoAtributo = new Atributo(Atributo.longType, -1 * al.ts.obtener(lexema).numValue);
            al.ts.insertar("-" + lexema, nuevoAtributo); /*medio ineficiente, mejorar despues*/
        }
break;
//#line 1227 "Parser.java"
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
