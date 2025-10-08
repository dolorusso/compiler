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
    7,    7,    7,    7,   22,   22,   22,   22,   22,   22,
   31,   31,   31,   33,   33,   33,   33,   33,    9,    9,
    9,    9,   10,   23,   23,   23,   23,   11,   32,   32,
   32,   32,   32,   32,   12,   12,   12,   12,   35,   35,
   36,   36,   34,   34,
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
    4,    4,    4,    4,    3,    3,    2,    1,    2,    2,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    4,
    4,    4,    2,    7,    6,    6,    4,    2,    3,    2,
    3,    2,    2,    1,    4,    5,    3,    4,    1,    3,
    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  109,    0,
  110,    0,    0,    0,   32,    9,   10,    0,   13,   14,
   15,    0,    0,    0,   19,    0,   62,   63,    0,    7,
    0,   34,    0,    0,    0,    0,  153,    0,  154,    0,
  126,    0,  127,    0,    0,    0,    0,  123,  125,    0,
  144,    0,  133,  138,    0,    0,    0,   35,    0,    0,
    0,    0,    6,    0,    3,   33,   12,   58,   54,   11,
    0,   20,   16,   21,   17,   22,   18,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,   28,    0,    0,   37,    0,   40,    0,    0,   61,
    0,   24,    0,  120,  119,    0,  117,  102,  103,  104,
  105,  106,  107,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   89,    0,    0,  143,  142,    0,  140,
    0,   95,    0,   94,   36,    0,    0,    0,    0,    0,
    1,   57,   53,    0,    0,   47,   25,    0,  150,  147,
  152,    0,    0,  113,  132,   48,   50,   44,    0,    0,
    0,   51,   46,   27,    0,   39,  130,    0,   42,    0,
   30,    0,   88,    0,    0,    0,   87,    0,    0,   84,
    0,    0,   76,    0,   73,    0,   75,    0,   71,   67,
    0,   70,   66,   77,  121,  122,    0,  139,  100,   99,
   93,   92,    0,  101,    0,  111,  131,  129,   23,   26,
    0,  148,  145,    0,   49,   45,   43,    0,   38,   41,
   59,   29,    0,    0,    0,   83,    0,   82,   72,   74,
   69,   65,   68,   64,   98,   97,  146,  151,    0,   85,
   86,   81,   79,   80,   78,    0,    0,    0,  136,  134,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   43,   23,
   24,   25,   26,   35,  102,   94,   59,   95,   96,   97,
   98,   99,  100,   27,   28,   45,   60,  123,   46,  116,
   47,   53,   48,   49,   29,  152,
};
final static short yysindex[] = {                      -101,
  525,  557,    0,  -46,  -29,   83,   58,   58,    0,  532,
    0,   -6,  581,  616,    0,    0,    0,  -38,    0,    0,
    0,  -36,  -33,  -27,    0,  -40,    0,    0,  -42,    0,
  627,    0,  568,  -32,  -86,   25,    0,  -23,    0,   28,
    0,  568,    0,  501,  -84,   16,   33,    0,    0,  225,
    0,   13,    0,    0,    3,   83,  307,    0,  718,   65,
  568,   89,    0,  647,    0,    0,    0,    0,    0,    0,
 -230,    0,    0,    0,    0,    0,    0,   53,   53,  237,
  -86,   49, -196, -140,    0,   61,   86,   25, -133,  221,
 -114,    0, -161,  487,    0,   32,    0, -153,   70,    0,
  605,    0,   96,    0,    0,   93,    0,    0,    0,    0,
    0,    0,    0,  -87,  -87,  568,  514,   81, -118, -130,
  -54,   73,   87,    0,  -87,  -87,    0,    0,   36,    0,
   63,    0,  682,    0,    0,   83,  104,   69,  114,   50,
    0,    0,    0,  -86,  -86,    0,    0, -227,    0,    0,
    0,  -34,    0,    0,    0,    0,    0,    0, -109,  -88,
  -91,    0,    0,    0,  301,    0,    0,  107,    0,  -85,
    0,  658,    0,   33,   33,   70,    0,  665,  -80,    0,
  -45,  -30,    0,  127,    0,  144,    0,   74,    0,    0,
   92,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   99,    0,    0,    0,    0,    0,    0,    0,
   27,    0,    0, -227,    0,    0,    0,  164,    0,    0,
    0,    0,  149,  153,  100,    0,  102,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  549,    0,
    0,    0,    0,    0,    0,  192,  690,  707,    0,    0,
};
final static short yyrindex[] = {                         0,
  270,    0,    0,    0,   -4,    0,    0,    0,    0,    0,
    0,   26,    0,  288,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,  142,    0,    0,    0,  168,
    0,    0,    0,  592,    0,  364,  335,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  298,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   19,   94,    0,
   42,    0,    0,    0,    0,    0,    0,  479,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -49,    0,
    0,    0,    0,    0,    0,  384,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  741,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,   42,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  407,  431,  290,    0,    0,    0,    0,
  741,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  455,    0,    0,    0,
    0,    0,    0,    0,   24,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  101,    0,
    0,    0,    0,    0,    0,    0,    0,  109,    0,    0,
};
final static short yygindex[] = {                         0,
   51,   21,    0,  500,    0,    0,    0,    0,    2,    0,
    0,    0,   17,   10,   20,    0,  360,  -56,  175,  -66,
    0,  949,    0,    0,    0,  -11,  -17,   76,  261,    0,
   85,   54,  257,  -64,    0,  173,
};
final static int YYTABLESIZE=1085;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         80,
  114,   83,   22,   22,  189,   71,  132,   91,   92,  214,
   34,   22,   32,  226,   22,   22,   50,   51,   84,  151,
   70,    2,   73,  112,  213,   75,  142,  122,  228,  169,
   37,   77,   22,   62,   66,   81,  101,  166,  119,   55,
  120,   31,  103,  117,  131,  117,   22,  143,  137,   39,
   93,   66,   31,  130,   55,  114,  124,  115,   22,  114,
   22,   54,   56,   64,  103,   22,  117,   62,  117,  149,
  214,   91,  167,  169,  125,  168,  198,   56,  114,  126,
  115,  149,  112,  151,   66,  237,  149,  144,  145,   91,
  208,  105,   80,  168,  162,  163,   93,   50,   51,  182,
  147,  220,   22,  114,   42,  115,  160,  161,  219,  148,
   93,  114,  114,  115,  115,  150,  170,   37,   22,  154,
   22,  200,   42,  156,  203,  114,  155,  206,   91,  117,
  186,  192,  232,  173,   22,   91,   39,   52,   55,  183,
    6,  117,  184,    7,    8,  194,   91,  215,  112,  238,
  234,  172,   52,   10,  207,    1,    9,  236,  243,   12,
  245,   11,  204,  209,  210,  218,   31,  216,  217,   36,
   37,  221,   55,   22,    6,  117,  118,    7,    8,   22,
  224,   93,  128,  128,  128,  229,  128,   10,  128,   39,
   40,   41,   66,   12,  185,  187,  190,  193,  174,  175,
  128,  128,  230,  128,  239,  117,  188,  240,  124,  124,
  124,  241,  124,   82,  124,  225,   78,   67,   68,   72,
   60,  212,   74,   87,   88,   37,  124,  124,   76,  124,
  227,   89,  104,   36,   37,   33,  140,   79,    9,   69,
   22,   38,   90,   11,   39,   40,   41,   22,   22,   22,
   38,   55,   55,   39,   40,   41,  114,  114,   61,  114,
  114,  114,  114,  114,  128,  128,  128,   33,  107,    8,
  114,  114,  114,   55,   56,   56,  114,   92,  114,  112,
  112,  149,  112,  112,  112,  112,  112,    5,   36,   37,
  124,  197,  124,  112,  112,  112,   56,    4,   31,  112,
   31,  112,  106,   31,   31,   38,   36,   37,   39,   40,
   41,    0,   31,   31,   36,   37,  153,   31,  199,   31,
  211,   36,   37,   38,  205,  107,   39,   40,   41,  231,
   96,   38,  117,  191,   39,   40,   41,  136,   38,   36,
   37,   39,   40,   41,  139,   36,   37,  233,   96,   52,
   52,   87,   36,   37,  235,  242,   38,  244,    0,   39,
   40,   41,   38,   36,   37,   39,   40,   41,    0,   38,
  137,   52,   39,   40,   41,  118,    0,  118,  135,  118,
   38,  195,  196,   39,   40,   41,    0,    0,    0,    0,
    0,    0,    0,  118,  118,    0,  118,  128,  128,  128,
  128,  128,  128,  128,  128,    0,    0,  128,  128,  128,
  128,  128,   96,  128,   96,  128,  133,    0,  128,  128,
  128,    0,   91,  124,  124,  124,  124,  124,  124,  124,
  124,  132,    0,  124,  124,  124,  124,  124,    0,  124,
    0,  124,   90,    0,  124,  124,  124,  115,   55,  115,
    6,  115,    0,    7,    8,    0,    0,  118,    0,  118,
    0,    0,    0,   10,    0,  115,  115,    0,  115,   12,
    0,  116,    0,  116,    0,  116,  157,  158,  133,    0,
  127,   36,   37,    0,  159,    0,   91,    0,   91,  116,
  116,    9,  116,  146,    0,  117,   11,  117,   38,  117,
   89,   39,   40,   41,    0,    0,   90,    9,   90,   58,
    0,   90,   11,  141,  141,    0,  141,    0,    0,   47,
  128,  128,   47,  128,    0,  128,    0,  164,    0,  115,
  165,  115,    0,    0,    0,    0,    0,  133,    0,    0,
    0,    0,    0,  114,  121,  115,   96,    0,   96,   96,
   96,   96,   96,  116,    0,  116,   58,  146,  135,    0,
  113,   96,  112,   55,   89,    6,    0,   96,    7,    8,
    0,    9,  180,    0,    0,   90,   11,  141,   10,  141,
    0,    0,    0,    0,   12,    0,    0,    0,    0,    0,
  118,  118,  118,  118,  118,  118,  118,  118,  247,    0,
  118,  118,  118,  118,  118,  248,  118,    0,  118,    0,
    0,  118,  118,  118,    0,    0,  181,    0,   58,   91,
   91,    0,   91,   91,   91,   91,   91,    0,    0,    0,
    0,    0,  135,    0,    0,   91,  178,    0,  179,   90,
   90,   91,   90,   90,   90,   90,   90,   13,    0,    0,
    0,    0,    0,    0,   57,   90,    0,    0,    0,    0,
    0,   90,  115,  115,  115,  115,  115,  115,  115,  115,
    0,  246,  115,  115,  115,  115,  115,   58,  115,    0,
  115,   30,    0,  115,  115,  115,  116,  116,  116,  116,
  116,  116,  116,  116,    0,    0,  116,  116,  116,  116,
  116,    0,  116,    0,  116,   63,    0,  116,  116,  116,
  117,  141,  141,  141,  141,  141,  141,  141,    0,    0,
  141,  141,  141,  141,  141,    0,  141,    0,  141,  171,
    0,  141,  141,  141,  128,   47,    0,    0,   58,    0,
   65,    0,   47,  146,    0,   58,  135,  135,  128,   47,
   89,   85,    0,   47,   47,    0,  107,    9,    0,    0,
    0,   90,   11,    0,    0,    0,  108,  109,  110,  111,
   55,  141,    6,    0,  177,    7,    8,    0,    0,    0,
    4,    5,  222,    6,    0,   10,    7,    8,   55,  132,
    6,   12,    0,    7,    8,    9,   10,    0,    0,    0,
   11,    0,   12,   10,   56,   55,  202,    6,    0,   12,
    7,    8,    4,    5,  249,    6,    0,    0,    7,    8,
   10,    0,    0,    0,   36,   37,   12,    9,   10,    0,
    0,  250,   11,    0,   12,    0,    4,    5,    0,    6,
    0,   38,    7,    8,   39,   40,   41,    0,  108,  108,
    0,    9,   10,    0,    0,    0,   11,    0,   12,    0,
    4,    5,    0,    6,    0,  108,    7,    8,  108,  108,
  108,    0,    5,    0,    6,    9,   10,    7,    8,    0,
   11,    0,   12,    5,    0,    6,    9,   10,    7,    8,
    0,   11,    0,   12,    0,    0,    0,    9,   10,    0,
    0,    0,   11,    5,   12,    6,    0,    0,    7,    8,
    0,    0,    0,    0,    5,    0,    6,    9,   10,    7,
    8,   55,   11,    6,   12,  223,    7,    8,    9,   10,
    0,    0,    0,   11,    0,   12,   10,  201,   55,    0,
    6,    0,   12,    7,    8,    0,   55,    0,    6,    0,
    0,    7,    8,   10,   44,   52,   52,    0,    0,   12,
    0,   10,    0,   55,    0,    6,    0,   12,    7,    8,
    0,    0,    0,  134,   55,    0,    6,    0,   10,    7,
    8,   86,    0,    0,   12,    0,   52,    0,    0,   10,
   44,    0,    0,    0,    0,   12,   35,   35,  129,   35,
    0,    0,   35,   35,   44,    0,    0,    0,   44,  138,
    0,    0,   35,    0,    0,    0,    0,    0,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   44,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   44,    1,    2,   59,   44,  125,   40,   41,   44,
   40,   10,   59,   59,   13,   14,   40,   41,   61,   84,
   59,  123,   59,    0,   59,   59,  257,   45,   59,   96,
  258,   59,   31,   40,   14,   26,  123,   94,  123,   44,
  125,    0,   40,   43,   56,   45,   45,  278,   60,  277,
   34,   31,    2,   41,   59,   43,   41,   45,   57,   59,
   59,    8,   44,   13,   40,   64,   43,   40,   45,   44,
   44,   40,   41,  140,   42,   44,   41,   59,   43,   47,
   45,  278,   59,  148,   64,   59,   61,   78,   79,   40,
   41,   38,   40,   44,  256,  257,   80,   40,   41,  117,
   81,  168,  101,   43,   40,   45,   90,   91,  165,   61,
   94,   43,   43,   45,   45,  256,  270,  258,  117,   59,
  119,   59,   40,  257,  136,  125,   41,   59,   40,  260,
  261,   59,   59,   41,  133,   40,  277,   44,  257,   59,
  259,  260,  261,  262,  263,   59,   40,  257,  125,  214,
   59,  101,   59,  272,   41,  257,  271,   59,   59,  278,
   59,  276,   59,  144,  145,  257,  125,  256,  257,  257,
  258,  257,  257,  172,  259,  260,  261,  262,  263,  178,
  261,  165,   41,   42,   43,   59,   45,  272,   47,  277,
  278,  279,  172,  278,  119,  120,  121,  122,  114,  115,
   59,   60,   59,   62,   41,  260,  261,   59,   41,   42,
   43,   59,   45,  256,   47,  261,  257,  256,  257,  256,
  270,  256,  256,  256,  257,  258,   59,   60,  256,   62,
  261,  264,  256,  257,  258,  265,   62,  278,  271,  278,
  239,  274,  275,  276,  277,  278,  279,  246,  247,  248,
  274,  256,  257,  277,  278,  279,  256,  257,  265,  259,
  260,  261,  262,  263,  123,   41,  125,  265,  256,    0,
  270,  271,  272,  278,  256,  257,  276,   41,  278,  256,
  257,  256,  259,  260,  261,  262,  263,    0,  257,  258,
  123,  256,  125,  270,  271,  272,  278,    0,  257,  276,
  259,  278,   42,  262,  263,  274,  257,  258,  277,  278,
  279,   -1,  271,  272,  257,  258,  256,  276,  256,  278,
  148,  257,  258,  274,  256,  256,  277,  278,  279,  256,
   41,  274,  260,  261,  277,  278,  279,  273,  274,  257,
  258,  277,  278,  279,  256,  257,  258,  256,   59,  256,
  257,  256,  257,  258,  256,  256,  274,  256,   -1,  277,
  278,  279,  274,  257,  258,  277,  278,  279,   -1,  274,
  270,  278,  277,  278,  279,   41,   -1,   43,  270,   45,
  274,  125,  126,  277,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,  256,  257,  258,
  259,  260,  261,  262,  263,   -1,   -1,  266,  267,  268,
  269,  270,  123,  272,  125,  274,   57,   -1,  277,  278,
  279,   -1,   59,  256,  257,  258,  259,  260,  261,  262,
  263,  125,   -1,  266,  267,  268,  269,  270,   -1,  272,
   -1,  274,   59,   -1,  277,  278,  279,   41,  257,   43,
  259,   45,   -1,  262,  263,   -1,   -1,  123,   -1,  125,
   -1,   -1,   -1,  272,   -1,   59,   60,   -1,   62,  278,
   -1,   41,   -1,   43,   -1,   45,  256,  257,  119,   -1,
  256,  257,  258,   -1,  264,   -1,  123,   -1,  125,   59,
   60,  271,   62,  257,   -1,   41,  276,   43,  274,   45,
  264,  277,  278,  279,   -1,   -1,  123,  271,  125,   10,
   -1,  275,  276,   59,   60,   -1,   62,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   41,   -1,  123,
   44,  125,   -1,   -1,   -1,   -1,   -1,  178,   -1,   -1,
   -1,   -1,   -1,   43,   45,   45,  257,   -1,  259,  260,
  261,  262,  263,  123,   -1,  125,   57,  257,   59,   -1,
   60,  272,   62,  257,  264,  259,   -1,  278,  262,  263,
   -1,  271,   59,   -1,   -1,  275,  276,  123,  272,  125,
   -1,   -1,   -1,   -1,  278,   -1,   -1,   -1,   -1,   -1,
  256,  257,  258,  259,  260,  261,  262,  263,  239,   -1,
  266,  267,  268,  269,  270,  246,  272,   -1,  274,   -1,
   -1,  277,  278,  279,   -1,   -1,  117,   -1,  119,  256,
  257,   -1,  259,  260,  261,  262,  263,   -1,   -1,   -1,
   -1,   -1,  133,   -1,   -1,  272,  123,   -1,  125,  256,
  257,  278,  259,  260,  261,  262,  263,  123,   -1,   -1,
   -1,   -1,   -1,   -1,  123,  272,   -1,   -1,   -1,   -1,
   -1,  278,  256,  257,  258,  259,  260,  261,  262,  263,
   -1,  123,  266,  267,  268,  269,  270,  178,  272,   -1,
  274,  125,   -1,  277,  278,  279,  256,  257,  258,  259,
  260,  261,  262,  263,   -1,   -1,  266,  267,  268,  269,
  270,   -1,  272,   -1,  274,  125,   -1,  277,  278,  279,
  256,  257,  258,  259,  260,  261,  262,  263,   -1,   -1,
  266,  267,  268,  269,  270,   -1,  272,   -1,  274,  125,
   -1,  277,  278,  279,  256,  257,   -1,   -1,  239,   -1,
  125,   -1,  264,  257,   -1,  246,  247,  248,  270,  271,
  264,  125,   -1,  275,  276,   -1,  256,  271,   -1,   -1,
   -1,  275,  276,   -1,   -1,   -1,  266,  267,  268,  269,
  257,  125,  259,   -1,  261,  262,  263,   -1,   -1,   -1,
  256,  257,  125,  259,   -1,  272,  262,  263,  257,  125,
  259,  278,   -1,  262,  263,  271,  272,   -1,   -1,   -1,
  276,   -1,  278,  272,  273,  257,  125,  259,   -1,  278,
  262,  263,  256,  257,  125,  259,   -1,   -1,  262,  263,
  272,   -1,   -1,   -1,  257,  258,  278,  271,  272,   -1,
   -1,  125,  276,   -1,  278,   -1,  256,  257,   -1,  259,
   -1,  274,  262,  263,  277,  278,  279,   -1,  257,  258,
   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,   -1,
  256,  257,   -1,  259,   -1,  274,  262,  263,  277,  278,
  279,   -1,  257,   -1,  259,  271,  272,  262,  263,   -1,
  276,   -1,  278,  257,   -1,  259,  271,  272,  262,  263,
   -1,  276,   -1,  278,   -1,   -1,   -1,  271,  272,   -1,
   -1,   -1,  276,  257,  278,  259,   -1,   -1,  262,  263,
   -1,   -1,   -1,   -1,  257,   -1,  259,  271,  272,  262,
  263,  257,  276,  259,  278,  261,  262,  263,  271,  272,
   -1,   -1,   -1,  276,   -1,  278,  272,  256,  257,   -1,
  259,   -1,  278,  262,  263,   -1,  257,   -1,  259,   -1,
   -1,  262,  263,  272,    6,    7,    8,   -1,   -1,  278,
   -1,  272,   -1,  257,   -1,  259,   -1,  278,  262,  263,
   -1,   -1,   -1,  256,  257,   -1,  259,   -1,  272,  262,
  263,   33,   -1,   -1,  278,   -1,   38,   -1,   -1,  272,
   42,   -1,   -1,   -1,   -1,  278,  256,  257,   50,  259,
   -1,   -1,  262,  263,   56,   -1,   -1,   -1,   60,   61,
   -1,   -1,  272,   -1,   -1,   -1,   -1,   -1,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  116,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  136,
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
"expresion : expresion error",
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

//#line 384 "parser_test.y"
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
//#line 682 "Parser.java"
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
//#line 18 "parser_test.y"
{ errManager.debug("Declaracion de programa detectada", al.getLine()); }
break;
case 2:
//#line 20 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 3:
//#line 22 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 4:
//#line 24 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 5:
//#line 26 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 6:
//#line 28 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 7:
//#line 30 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 8:
//#line 32 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 43 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 20:
//#line 55 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 57 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
//#line 59 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 64 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 24:
//#line 66 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 25:
//#line 68 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 26:
//#line 70 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 28:
//#line 75 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 30:
//#line 80 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 31:
//#line 81 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 34:
//#line 89 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 42:
//#line 107 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 43:
//#line 112 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 44:
//#line 114 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 45:
//#line 116 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 46:
//#line 118 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 47:
//#line 120 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 48:
//#line 122 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 49:
//#line 124 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
//#line 126 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 128 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 134 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 53:
//#line 136 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
//#line 138 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 55:
//#line 140 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 56:
//#line 142 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 57:
//#line 144 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 58:
//#line 146 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 59:
//#line 153 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 64:
//#line 169 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 65:
//#line 171 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
//#line 173 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 67:
//#line 175 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
//#line 177 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 69:
//#line 179 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 181 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 71:
//#line 183 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 185 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 73:
//#line 187 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 189 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 75:
//#line 191 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 193 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 77:
//#line 195 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 80:
//#line 202 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 81:
//#line 204 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 206 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 83:
//#line 208 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
//#line 210 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
//#line 212 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 86:
//#line 214 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 87:
//#line 216 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 89:
//#line 222 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 90:
//#line 224 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 91:
//#line 226 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 93:
//#line 232 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 94:
//#line 234 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 95:
//#line 236 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 96:
//#line 242 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 97:
//#line 247 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 98:
//#line 249 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 99:
//#line 251 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 100:
//#line 253 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 101:
//#line 255 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 108:
//#line 266 "parser_test.y"
{ errManager.error("Falta comparador en condicion", al.getLine()); }
break;
case 111:
//#line 276 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 112:
//#line 278 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 113:
//#line 280 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 114:
//#line 282 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 119:
//#line 291 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 120:
//#line 293 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 128:
//#line 308 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 129:
//#line 313 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 130:
//#line 315 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 131:
//#line 317 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 132:
//#line 319 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 133:
//#line 324 "parser_test.y"
{errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 134:
//#line 328 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 135:
//#line 330 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 136:
//#line 332 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 137:
//#line 334 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 138:
//#line 339 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 140:
//#line 345 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 141:
//#line 347 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 142:
//#line 349 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 143:
//#line 351 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 144:
//#line 353 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 145:
//#line 358 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 146:
//#line 360 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 147:
//#line 362 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 148:
//#line 364 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
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
