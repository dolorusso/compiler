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
    0,    0,    0,    0,    0,    0,    0,    0,    3,    3,
    4,    4,    4,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    7,    7,    7,    7,   15,   15,   16,   16,
   16,    2,    2,    2,   18,   18,   17,   17,   17,   20,
   20,   20,   19,   19,   19,   19,   19,   19,   19,   19,
   19,    6,    6,    6,    6,    6,    6,    6,   21,   22,
   22,    9,    9,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   29,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   27,   27,   27,
   27,   28,   28,   28,   28,   30,   30,   26,   26,   26,
   26,   26,   31,   31,   31,   31,   31,   31,   14,   14,
    8,    8,    8,    8,   23,   23,   23,   23,   23,   32,
   32,   32,   34,   34,   34,   34,   34,   10,   10,   10,
   10,   11,   24,   24,   24,   24,   12,   33,   33,   33,
   33,   33,   33,   13,   13,   13,   13,   35,   35,   36,
   36,    1,    1,    1,    1,
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
  125,    0,    0,  124,  126,    0,    0,    0,    0,  122,
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
   44,   14,   15,   16,   17,   18,   19,   20,   21,   45,
   23,   24,   25,   26,   35,  103,   95,   60,   96,   97,
   98,   99,  100,  101,   27,   28,   47,   61,  126,   48,
  119,   49,   54,   50,   29,  155,
};
final static short yysindex[] = {                      -117,
  266,  478,    0,  -15,    6,   66,   36,   36,    0,  288,
    0,   13,  496,  518,    0,    0,    0,  -21,    0,    0,
    0,   43,   64,   76,    0,  -40,    0,    0,  -31,    0,
  529,    0,  201,   -9,  -76,   17,    0,   18,    0,   32,
    0,  201, -160,    0,    0,  428,  351,   55,   -7,    0,
   98,    0,  114,    0,    0,   26,   66,  617,    0,  -94,
   48,  201,   60,    0,  540,    0,    0,    0,    0,    0,
    0, -223,    0,    0,    0,    0,    0,    0,   70,   70,
  190,  -76,   81, -189,    3,    0,  -16,   75,   17, -130,
 -123, -215,    0, -174,  245,    0,   24,    0, -133,   87,
    0,  507,    0,   73,    0,    0,  104,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  205,  205,  201,  467,
   88,  560, -140,  -52,  -49,   95,    0,  205,  205,    0,
    0,  -24,    0,   90,    0,  585,    0,    0,   66,   99,
   56,  123,   30,    0,    0,    0,  -76,  -76,    0,    0,
   42,    0,    0,    0,  -35,    0,    0,    0,    0,    0,
    0,  -86, -105,  -82,    0,    0,    0,  229,    0,    0,
   91,    0,  -78,    0,  574,    0,   -7,   -7,   87,    0,
  592,  -74,    0,  -48,  -45,    0,  129,    0,  133,    0,
   97,    0,    0,  101,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  111,    0,    0,    0,    0,
    0,    0,    0,   50,    0,    0,   42,    0,    0,    0,
  157,    0,    0,    0,    0,  140,  145,  126,    0,  127,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  337,    0,    0,    0,    0,    0,    0,  610,  635,
  646,    0,    0,
};
final static short yyrindex[] = {                         0,
  206,    0,    0,    0,   85,    0,    0,    0,    0,    0,
    0,  -19,    0,  210,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,  160,    0,    0,    0,  319,
    0,    0,    0,    0,    0,    0,    0,  430,  131,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  214,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  102,  156,
    0,    1,    0,    0,    0,    0,    0,    0,  363,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -37,
    0,    0,    0,    0,    0,    0,  444,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  603,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  390,  410,  -33,    0,
    0,    0,    0,  603,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,
   41,    0,    0,
};
final static short yygindex[] = {                         0,
  -67,   78,   14,    0,   -8,    0,    0,    0,    0,    2,
    0,    0,    0,  406,   28,  -62,    0,  315,  -90,  155,
  -46,    0,  877,    0,    0,    0,  -20,  -25,   72,  242,
    0,   49,   65,   53,    0,  177,
};
final static int YYTABLESIZE=1016;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         81,
   31,   59,   22,   22,  169,    2,  192,   96,  217,  195,
  229,   22,   84,  231,   22,   22,  201,  154,  117,  150,
  118,  125,   72,  216,  148,   96,  117,   67,  118,   85,
   92,   93,   22,  145,  128,   43,  134,   71,  124,  129,
  140,  148,  157,   32,   67,   34,  102,   43,   22,   59,
  172,  138,   63,   82,  146,    9,  104,   51,   52,   22,
   11,   22,   43,   92,  170,  104,   22,  171,   43,   92,
  211,   63,   55,  171,   43,   51,   52,  222,   67,   31,
   43,  165,  166,  154,  212,  213,   43,   42,  152,   96,
   65,   96,   43,  217,  185,  127,  172,  108,  117,   92,
  118,   74,  106,   22,   43,   42,  147,  148,  240,   81,
   43,  184,   92,   59,  209,  158,  109,   43,  206,  120,
  189,   22,   76,   22,  223,   31,  159,  138,   55,  117,
   92,  118,  160,  161,   78,   43,  173,   22,  131,    1,
  162,  151,   43,   55,  176,   56,  186,    9,  203,  241,
  219,  220,   11,  197,  133,  235,  117,  207,  118,  237,
   56,  137,   56,  210,    6,  177,  178,    7,    8,  239,
  218,  117,   59,  117,  221,  117,   22,   10,  224,  175,
  198,  199,   22,   12,  246,  248,  227,  232,   67,  117,
  117,  233,  117,  188,  190,  193,  196,  242,  243,   52,
  127,  127,  127,  244,  127,    8,  127,  120,  191,    5,
  120,  194,  228,    4,   52,  230,   79,  143,  127,  127,
  215,  127,   96,   96,   83,   96,   96,   96,   96,   96,
   93,  200,   60,   59,   68,   69,  148,   80,   96,  156,
   59,  138,  138,   22,   96,   43,   88,   89,   37,   43,
   22,   22,   22,  117,   90,  117,   70,   31,  153,   31,
   37,    9,   31,   31,   38,   91,   11,   39,   40,   41,
   33,   31,   31,  105,   36,   37,   31,   62,   31,   39,
   36,   37,  127,  107,  127,  167,   36,   37,  168,  136,
   33,   38,   36,   37,   39,   40,   41,   38,   73,   37,
   39,   40,   41,   38,   36,   37,   39,   40,   41,   38,
  134,  208,   39,   40,   41,  142,   36,   37,   39,   75,
  139,   38,   36,   37,   39,   40,   41,  214,   88,   36,
   37,   77,    0,   38,    0,    0,   39,   40,   41,   38,
   55,   55,   39,   40,   41,  202,   38,   36,   37,   39,
   40,   41,  234,  130,   36,   37,  236,   56,   56,  123,
  123,  123,   55,  123,   38,  123,  238,   39,   40,   41,
    0,   38,  136,    0,   39,   40,   41,  123,  123,   56,
  123,  245,  247,    0,    0,    0,  117,  117,   13,  117,
  117,  117,  117,  117,    0,    0,  117,  117,  117,  117,
  117,    0,  117,   47,  127,  127,   47,  127,  117,  127,
   58,   52,   52,    0,    0,  127,  127,    0,  127,  127,
  127,  127,  127,    0,    0,  127,  127,  127,  127,  127,
  115,  127,  115,   52,  115,    0,  136,  127,    0,   94,
    0,  123,    0,  123,    0,    0,  149,    0,  115,  115,
  116,  115,  116,   90,  116,    0,    0,   36,   37,  249,
    9,   36,   37,    0,   91,   11,    0,    0,  116,  116,
  117,  116,  118,  122,   38,  123,    0,   39,   40,   41,
    0,   39,   40,   41,    0,  149,   94,  116,   91,  115,
    0,    0,   90,    0,    0,  136,  163,  164,    0,    9,
   94,  149,   90,   91,   11,    0,    0,    0,   90,    0,
    0,    0,  115,    0,  115,    9,    0,    0,    0,   91,
   11,    4,    5,    0,    6,  183,    0,    7,    8,    0,
    0,    0,  116,    0,  116,    0,    9,   10,    0,    0,
    0,   11,    0,   12,   56,    0,    6,    0,    0,    7,
    8,    0,   91,    0,   91,    0,  250,    0,    0,   10,
   57,    0,    0,  251,    0,   12,   90,    0,   90,    0,
    0,    0,    0,   94,  123,  123,    0,  123,  123,  123,
  123,  123,    0,    0,  123,  123,  123,  123,  123,  181,
  123,  182,    0,   56,    0,    6,  123,    0,    7,    8,
    0,    0,   30,    0,    0,    0,    0,   56,   10,    6,
  120,  121,    7,    8,   12,    0,    0,    0,    0,   47,
   64,    0,   10,    0,    0,    0,   47,    0,   12,    0,
    0,  174,  127,   47,    0,    0,    0,   47,   47,    0,
    0,    0,   66,    0,    0,  115,  115,    0,  115,  115,
  115,  115,  115,   86,    0,  115,  115,  115,  115,  115,
    0,  115,    0,    0,  144,  116,  116,  115,  116,  116,
  116,  116,  116,    0,    0,  116,  116,  116,  116,  116,
    0,  116,    0,  110,  135,   91,   91,  116,   91,   91,
   91,   91,   91,  111,  112,  113,  114,    0,  225,   90,
   90,   91,   90,   90,   90,   90,   90,   91,    0,  205,
    0,    0,    0,    0,    0,   90,  135,    0,    0,    0,
    0,   90,    0,   56,    0,    6,    0,  180,    7,    8,
    0,    0,    0,    4,    5,    0,    6,    0,   10,    7,
    8,  135,    0,    0,   12,    0,    0,    0,    9,   10,
    0,    4,    5,   11,    6,   12,    0,    7,    8,  252,
    0,    0,    4,    5,    0,    6,    9,   10,    7,    8,
  253,   11,    0,   12,    5,    0,    6,    9,   10,    7,
    8,    0,   11,    0,   12,    5,    0,    6,    9,   10,
    7,    8,    0,   11,    0,   12,    5,    0,    6,    9,
   10,    7,    8,    0,   11,    0,   12,    0,    0,    0,
    9,   10,    0,    0,    0,   11,   56,   12,    6,  120,
  187,    7,    8,    0,    0,    0,    0,    0,    0,    0,
    5,   10,    6,    0,    0,    7,    8,   12,    0,    0,
  204,   56,    0,    6,    9,   10,    7,    8,   56,   11,
    6,   12,  226,    7,    8,    0,   10,    0,   35,   35,
    0,   35,   12,   10,   35,   35,   56,    0,    6,   12,
    0,    7,    8,   56,   35,    6,    0,    0,    7,    8,
   35,   10,   46,   53,   53,    0,    0,   12,   10,    0,
    0,   56,    0,    6,   12,    0,    7,    8,    0,    0,
    0,    0,   56,    0,    6,    0,   10,    7,    8,   87,
    0,    0,   12,    0,   53,    0,    0,   10,   46,    0,
    0,    0,    0,   12,    0,    0,    0,  132,    0,    0,
    0,    0,    0,   46,    0,    0,    0,   46,  141,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  179,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   46,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   10,    1,    2,   95,  123,   59,   41,   44,   59,
   59,   10,   44,   59,   13,   14,   41,   85,   43,   82,
   45,   47,   44,   59,   44,   59,   43,   14,   45,   61,
   40,   41,   31,  257,   42,   45,   57,   59,   47,   47,
   61,   61,   59,   59,   31,   40,  123,   45,   47,   58,
   97,   60,   40,   26,  278,  271,   40,   40,   41,   58,
  276,   60,   45,   40,   41,   40,   65,   44,   45,   40,
   41,   40,    8,   44,   45,   40,   41,  168,   65,    2,
   45,  256,  257,  151,  147,  148,   45,   40,  278,  123,
   13,  125,   45,   44,  120,   41,  143,  258,   43,   40,
   45,   59,   38,  102,   45,   40,   79,   80,   59,   40,
   45,  120,   40,  122,   59,   41,  277,   45,  139,  260,
  261,  120,   59,  122,  171,  125,  257,  136,   44,   43,
   40,   45,  256,  257,   59,   45,  270,  136,   41,  257,
  264,   61,   45,   59,   41,   44,   59,  271,   59,  217,
  256,  257,  276,   59,   41,   59,   43,   59,   45,   59,
   59,  256,  257,   41,  259,  117,  118,  262,  263,   59,
  257,   41,  181,   43,  257,   45,  175,  272,  257,  102,
  128,  129,  181,  278,   59,   59,  261,   59,  175,   59,
   60,   59,   62,  122,  123,  124,  125,   41,   59,   44,
   41,   42,   43,   59,   45,    0,   47,  260,  261,    0,
  260,  261,  261,    0,   59,  261,  257,   63,   59,   60,
  256,   62,  256,  257,  256,  259,  260,  261,  262,  263,
   41,  256,  270,  242,  256,  257,  256,  278,  272,  256,
  249,  250,  251,  242,  278,   45,  256,  257,  258,   45,
  249,  250,  251,  123,  264,  125,  278,  257,  256,  259,
  258,  271,  262,  263,  274,  275,  276,  277,  278,  279,
  265,  271,  272,  256,  257,  258,  276,  265,  278,  277,
  257,  258,  123,   42,  125,   41,  257,  258,   44,  270,
  265,  274,  257,  258,  277,  278,  279,  274,  256,  258,
  277,  278,  279,  274,  257,  258,  277,  278,  279,  274,
  270,  256,  277,  278,  279,  256,  257,  258,  277,  256,
  273,  274,  257,  258,  277,  278,  279,  151,  256,  257,
  258,  256,   -1,  274,   -1,   -1,  277,  278,  279,  274,
  256,  257,  277,  278,  279,  256,  274,  257,  258,  277,
  278,  279,  256,  256,  257,  258,  256,  256,  257,   41,
   42,   43,  278,   45,  274,   47,  256,  277,  278,  279,
   -1,  274,   58,   -1,  277,  278,  279,   59,   60,  278,
   62,  256,  256,   -1,   -1,   -1,  256,  257,  123,  259,
  260,  261,  262,  263,   -1,   -1,  266,  267,  268,  269,
  270,   -1,  272,   41,   42,   43,   44,   45,  278,   47,
  123,  256,  257,   -1,   -1,  256,  257,   -1,  259,  260,
  261,  262,  263,   -1,   -1,  266,  267,  268,  269,  270,
   41,  272,   43,  278,   45,   -1,  122,  278,   -1,   34,
   -1,  123,   -1,  125,   -1,   -1,  257,   -1,   59,   60,
   41,   62,   43,  264,   45,   -1,   -1,  257,  258,  123,
  271,  257,  258,   -1,  275,  276,   -1,   -1,   59,   60,
   43,   62,   45,  123,  274,  125,   -1,  277,  278,  279,
   -1,  277,  278,  279,   -1,  257,   81,   60,   59,   62,
   -1,   -1,  264,   -1,   -1,  181,   91,   92,   -1,  271,
   95,  257,   59,  275,  276,   -1,   -1,   -1,  264,   -1,
   -1,   -1,  123,   -1,  125,  271,   -1,   -1,   -1,  275,
  276,  256,  257,   -1,  259,   59,   -1,  262,  263,   -1,
   -1,   -1,  123,   -1,  125,   -1,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  257,   -1,  259,   -1,   -1,  262,
  263,   -1,  123,   -1,  125,   -1,  242,   -1,   -1,  272,
  273,   -1,   -1,  249,   -1,  278,  123,   -1,  125,   -1,
   -1,   -1,   -1,  168,  256,  257,   -1,  259,  260,  261,
  262,  263,   -1,   -1,  266,  267,  268,  269,  270,  123,
  272,  125,   -1,  257,   -1,  259,  278,   -1,  262,  263,
   -1,   -1,  125,   -1,   -1,   -1,   -1,  257,  272,  259,
  260,  261,  262,  263,  278,   -1,   -1,   -1,   -1,  257,
  125,   -1,  272,   -1,   -1,   -1,  264,   -1,  278,   -1,
   -1,  125,  270,  271,   -1,   -1,   -1,  275,  276,   -1,
   -1,   -1,  125,   -1,   -1,  256,  257,   -1,  259,  260,
  261,  262,  263,  125,   -1,  266,  267,  268,  269,  270,
   -1,  272,   -1,   -1,  125,  256,  257,  278,  259,  260,
  261,  262,  263,   -1,   -1,  266,  267,  268,  269,  270,
   -1,  272,   -1,  256,  125,  256,  257,  278,  259,  260,
  261,  262,  263,  266,  267,  268,  269,   -1,  125,  256,
  257,  272,  259,  260,  261,  262,  263,  278,   -1,  125,
   -1,   -1,   -1,   -1,   -1,  272,  125,   -1,   -1,   -1,
   -1,  278,   -1,  257,   -1,  259,   -1,  261,  262,  263,
   -1,   -1,   -1,  256,  257,   -1,  259,   -1,  272,  262,
  263,  125,   -1,   -1,  278,   -1,   -1,   -1,  271,  272,
   -1,  256,  257,  276,  259,  278,   -1,  262,  263,  125,
   -1,   -1,  256,  257,   -1,  259,  271,  272,  262,  263,
  125,  276,   -1,  278,  257,   -1,  259,  271,  272,  262,
  263,   -1,  276,   -1,  278,  257,   -1,  259,  271,  272,
  262,  263,   -1,  276,   -1,  278,  257,   -1,  259,  271,
  272,  262,  263,   -1,  276,   -1,  278,   -1,   -1,   -1,
  271,  272,   -1,   -1,   -1,  276,  257,  278,  259,  260,
  261,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  272,  259,   -1,   -1,  262,  263,  278,   -1,   -1,
  256,  257,   -1,  259,  271,  272,  262,  263,  257,  276,
  259,  278,  261,  262,  263,   -1,  272,   -1,  256,  257,
   -1,  259,  278,  272,  262,  263,  257,   -1,  259,  278,
   -1,  262,  263,  257,  272,  259,   -1,   -1,  262,  263,
  278,  272,    6,    7,    8,   -1,   -1,  278,  272,   -1,
   -1,  257,   -1,  259,  278,   -1,  262,  263,   -1,   -1,
   -1,   -1,  257,   -1,  259,   -1,  272,  262,  263,   33,
   -1,   -1,  278,   -1,   38,   -1,   -1,  272,   42,   -1,
   -1,   -1,   -1,  278,   -1,   -1,   -1,   51,   -1,   -1,
   -1,   -1,   -1,   57,   -1,   -1,   -1,   61,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  119,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  139,
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

//#line 404 "parser_test.y"
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
//#line 671 "Parser.java"
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
//#line 21 "parser_test.y"
{ errManager.debug("Declaracion de programa detectada", al.getLine()); }
break;
case 2:
//#line 23 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 3:
//#line 25 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 4:
//#line 27 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 5:
//#line 29 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 6:
//#line 31 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 7:
//#line 33 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 8:
//#line 35 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 46 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 20:
//#line 58 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 60 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
//#line 62 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 67 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 24:
//#line 69 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 25:
//#line 71 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 26:
//#line 73 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 28:
//#line 78 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 30:
//#line 83 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 31:
//#line 84 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 34:
//#line 92 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 42:
//#line 110 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 43:
//#line 115 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 44:
//#line 117 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 45:
//#line 119 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 46:
//#line 121 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 47:
//#line 123 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 48:
//#line 125 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 49:
//#line 127 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
//#line 129 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 131 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 137 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 53:
//#line 139 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
//#line 141 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 55:
//#line 143 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 56:
//#line 145 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 57:
//#line 147 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 58:
//#line 149 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 59:
//#line 156 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 64:
//#line 172 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 65:
//#line 174 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
//#line 176 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 67:
//#line 178 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
//#line 180 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 69:
//#line 182 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 184 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 71:
//#line 186 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 188 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 73:
//#line 190 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 192 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 75:
//#line 194 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 196 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 77:
//#line 198 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 80:
//#line 205 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 81:
//#line 207 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 209 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 83:
//#line 211 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
//#line 213 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
//#line 215 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 86:
//#line 217 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 87:
//#line 219 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 89:
//#line 225 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 90:
//#line 227 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 91:
//#line 229 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 93:
//#line 235 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 94:
//#line 237 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 95:
//#line 239 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 96:
//#line 245 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 97:
//#line 247 "parser_test.y"
{ errManager.error("Comparador de condificion invalido/faltante", al.getLine()); }
break;
case 98:
//#line 252 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 99:
//#line 254 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 100:
//#line 256 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 101:
//#line 258 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 102:
//#line 260 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 111:
//#line 279 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 112:
//#line 281 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 113:
//#line 283 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 114:
//#line 285 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 118:
//#line 293 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 119:
//#line 295 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 127:
//#line 310 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 128:
//#line 315 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 129:
//#line 317 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 130:
//#line 319 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 131:
//#line 321 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 132:
//#line 326 "parser_test.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 133:
//#line 330 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 134:
//#line 332 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 135:
//#line 334 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 136:
//#line 336 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 137:
//#line 341 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 139:
//#line 347 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 140:
//#line 349 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 141:
//#line 351 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 142:
//#line 353 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 143:
//#line 355 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 144:
//#line 360 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 145:
//#line 362 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 146:
//#line 364 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 147:
//#line 366 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 154:
//#line 383 "parser_test.y"
{
            this.yylval = al.getYylval();
            errManager.debug(val_peek(0).sval);


            errManager.debug("String value" + this.yylval);
            errManager.debug(al.ts.toString());
            errManager.debug(al.ts.obtener("30L").toString());
            errManager.debug("HAY: " + yyval.sval, al.getLine());
            errManager.debug("HAY: " + val_peek(0).sval, al.getLine());
            String lexema = val_peek(0).sval; /* el lexema de la constante*/
            errManager.debug("Constante negativa detectada: " + lexema, al.getLine());
            /* Almacenar como nueva entrada en tabla de simbolos*/
            Atributo nuevoAtributo = new Atributo(Atributo.longType, -1 * al.ts.obtener(lexema).numValue);
            al.ts.insertar("-" + lexema, nuevoAtributo); /*medio ineficiente, mejorar despues*/
        }
break;
//#line 1235 "Parser.java"
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
