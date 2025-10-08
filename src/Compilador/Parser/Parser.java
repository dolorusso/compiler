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
    0,    0,    0,    0,    0,    0,    0,    2,    2,    3,
    3,    3,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    6,    6,    6,    6,   14,   14,   15,   15,   15,
    1,    1,    1,   17,   17,   16,   16,   16,   19,   19,
   19,   18,   18,   18,   18,   18,   18,   18,   18,   18,
    5,    5,    5,    5,    5,    5,    5,   20,   21,   21,
    8,    8,    8,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   28,   28,   28,   28,   28,   28,   28,
   26,   26,   26,   26,   27,   27,   27,   27,   27,   29,
   25,   25,   30,   30,   30,   30,   30,   30,   30,   13,
   13,    7,    7,    7,    7,   22,   22,   22,   22,   22,
   31,   31,   31,   33,   33,   33,   33,   33,    9,    9,
    9,    9,   10,   23,   23,   23,   11,   32,   32,   32,
   32,   32,   32,   12,   12,   12,   12,   35,   35,   36,
   36,   34,   34,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    3,    2,    1,    1,    2,
    2,    1,    1,    1,    2,    2,    2,    1,    2,    2,
    2,    4,    3,    3,    4,    3,    2,    3,    2,    0,
    1,    2,    2,    1,    2,    1,    3,    2,    1,    3,
    2,    3,    2,    3,    2,    1,    2,    3,    2,    2,
    2,    3,    2,    1,    2,    3,    2,    3,    1,    1,
    1,    2,    2,    5,    4,    5,    4,    5,    4,    5,
    4,    4,    4,    4,    4,    3,    2,    4,    4,    2,
    3,    2,    2,    1,    3,    1,    2,    2,    2,    3,
    4,    3,    1,    1,    1,    1,    1,    1,    0,    1,
    1,    4,    4,    3,    4,    3,    3,    1,    2,    2,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    4,
    4,    4,    2,    6,    5,    5,    2,    3,    2,    3,
    2,    2,    1,    4,    5,    3,    4,    1,    3,    3,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  100,    0,
  101,    0,    0,    0,   31,    8,    9,    0,   12,   13,
   14,    0,    0,    0,   18,    0,   61,    0,    0,    7,
    0,   33,    0,    0,    0,    0,  142,    0,  143,    0,
  116,    0,  117,    0,    0,    0,    0,  113,  115,    0,
  133,    0,  123,  127,    0,    0,    0,    0,    0,    0,
    0,    0,    6,    0,    3,   32,   11,   57,   53,   10,
    0,   19,   15,   20,   16,   21,   17,    0,    0,    0,
    0,   63,   62,    0,    0,    0,    2,    0,    0,    0,
    0,    0,    0,   27,    0,    0,   36,    0,   39,    0,
    0,   60,    0,   23,    0,  110,  109,    0,   93,   94,
   95,   96,   97,   98,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   82,    0,    0,  132,  131,    0,  129,
   92,   89,   34,    0,   88,   35,    0,    0,    0,    0,
    1,   56,   52,    0,    0,   46,   24,    0,  139,  136,
  141,    0,  105,  122,   47,   49,   43,    0,    0,    0,
   50,   45,   26,    0,   38,  120,    0,   41,    0,   29,
    0,   81,    0,    0,    0,   80,    0,    0,   77,    0,
   72,    0,   69,    0,   71,    0,   67,   65,   73,  111,
  112,  130,  128,   85,   91,  103,  102,  121,  119,   22,
   25,    0,  137,  134,    0,   48,   44,   42,    0,   37,
   40,   58,   28,    0,    0,    0,   76,   68,   70,   66,
   64,  135,  140,    0,    0,   78,   79,   75,   74,    0,
  126,  124,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   43,   23,
   24,   25,   26,   35,  104,   96,   59,   97,   98,   99,
  100,  101,  102,   27,   28,   45,   60,  123,   46,  117,
   47,   53,   48,   49,   29,  152,
};
final static short yysindex[] = {                      -114,
  321,  526,    0,  -25,  -30,  183,  154,  154,    0,  313,
    0,  -23,  549, -118,    0,    0,    0,  -24,    0,    0,
    0,  -53,  -35,  -32,    0,  -38,    0,   -3,   10,    0,
  578,    0,  206,  -28,  -85,   17,    0,  128,    0,   35,
    0,  206,    0,  458,  386,   38,   -2,    0,    0,  215,
    0,   78,    0,    0,  -21,  183,  537,    0,  630, -222,
  206,  -40,    0,  589,    0,    0,    0,    0,    0,    0,
 -195,    0,    0,    0,    0,    0,    0,   53,   53,  248,
  -85,    0,    0,   46, -176, -160,    0,  -29,   74,   17,
 -132,  -91, -202,    0, -168,  433,    0,  142,    0, -143,
   37,    0,  567,    0,  168,    0,    0,  105,    0,    0,
    0,    0,    0,    0,  249,  249,  206,  515,   88,  276,
 -155,  -55,   90,    0,  249,  249,    0,    0,  -15,    0,
    0,    0,    0,  641,    0,    0,  183,   -4,  111,  160,
    0,    0,    0,  -85,  -85,    0,    0, -225,    0,    0,
    0,  -41,    0,    0,    0,    0,    0, -102, -144,  123,
    0,    0,    0,  -87,    0,    0,  191,    0,  -86,    0,
  612,    0,   -2,   -2,   37,    0,  619,  -83,    0,  -54,
    0,  117,    0,  122,    0,   -1,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,    0,    0, -225,    0,    0,    0,  424,    0,
    0,    0,    0,  132,  133,   44,    0,    0,    0,    0,
    0,    0,    0,  689,  659,    0,    0,    0,    0,  666,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  131,    0,    0,    0,    0,    0,
    0,   34,    0,  187,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  151,    1,    0,    0,    0,   25,
    0,    0,    0,  353,    0,  473,   49,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  508,    0,    0,
    0,    0,    0,  197,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  345,  399,    0,
  151,    0,    0,    0,    0,    0,    0,  120,    0,  409,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -68,    0,    0,    0,    0,    0,    0,  501,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -48,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  151,  151,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   73,   97,  356,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -61,
    0,    0,
};
final static short yygindex[] = {                         0,
   19,   22,    0,  445,    0,    0,    0,    0,  486,    0,
    0,    0,  626,   50,  -44,    0,  -10,  -65,  148,  -76,
    0,  903,    0,    0,    0,  -33,  -37,   41,  169,    0,
   15,   21,   11,  -71,    0,  165,
};
final static int YYTABLESIZE=1040;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         93,
  118,   80,  205,  187,  217,   73,   65,  122,    2,   34,
   87,   93,   94,  115,  151,  116,   62,  204,  105,   71,
   31,  168,  131,   75,  114,  193,   77,  115,   54,  116,
  165,   64,   37,   32,   70,   66,  147,  103,  115,  125,
  116,  118,  118,  118,  126,  118,  134,  118,  108,  205,
  137,   39,   66,   85,  197,   83,  105,  221,  107,  118,
  118,  142,  118,  168,  222,  114,  114,  114,    9,  114,
   86,  114,  106,   11,   62,   81,  151,  138,  124,  115,
  180,  116,  143,  114,  114,   66,  114,  161,  162,  108,
  211,  108,   80,  108,  138,  150,  107,   37,  210,  200,
  201,  149,  229,  195,  118,  184,  148,  108,  108,  134,
  108,  207,  208,  106,  154,  106,   39,  106,  130,  104,
  115,  171,  116,  118,  155,  118,  169,  144,  145,  173,
  174,  106,  106,  223,  106,  190,  191,  107,    5,  107,
    6,  107,    1,    7,    8,  172,  181,  114,  189,  114,
   30,  198,    9,   10,  206,  107,  107,   11,  107,   12,
  183,  185,  188,  209,  156,  157,  134,   50,   51,  146,
  212,  108,  158,  108,   54,  218,   91,  215,  104,    9,
  219,   93,  166,    9,   11,  167,    5,   92,   11,   54,
  226,  227,   66,   50,   51,  106,    4,  106,  225,   93,
  199,   59,   72,  167,  118,  186,  216,   93,  125,  140,
  108,   87,   87,  230,  203,  139,   36,   37,   78,  107,
   74,  107,   42,   76,   87,    0,  153,   89,   90,   37,
   93,   67,   68,   38,   33,   91,   39,   40,   41,   79,
  192,   61,    9,   33,  104,   38,   92,   11,   39,   40,
   41,  196,   82,   69,  220,  128,  118,  118,  118,  118,
  118,  118,  118,  118,    0,   84,  118,  118,  118,  118,
  118,  118,  118,  118,  118,   30,  118,  118,  118,  118,
  114,  114,  114,  114,  114,  114,  114,  114,   94,  138,
  114,  114,  114,  114,  114,  114,  114,  114,  114,  228,
  114,  114,  114,  114,  108,  108,  108,  108,  108,  108,
  108,  108,  202,    0,  108,  108,  108,  108,  108,  108,
  108,  108,  108,    0,  108,  108,  108,  108,  106,  106,
  106,  106,  106,  106,  106,  106,    0,    0,  106,  106,
  106,  106,  106,  106,  106,  106,  106,    0,  106,  106,
  106,  106,  107,  107,  107,  107,  107,  107,  107,  107,
    0,    0,  107,  107,  107,  107,  107,  107,  107,  107,
  107,    0,  107,  107,  107,  107,  104,    0,  104,  104,
  104,  104,  104,  106,   36,   37,   54,   54,   55,  104,
  104,  104,  104,    0,    0,  104,   90,  104,   36,   37,
  132,   38,    0,   55,   39,   40,   41,   30,   54,   30,
   36,   37,   30,   30,   90,   38,   36,   37,   39,   40,
   41,   30,   30,   89,   36,   37,   30,   38,   30,    0,
   39,   40,   41,   38,    0,   57,   39,   40,   41,   36,
   37,   38,   51,   13,   39,   40,   41,   36,   37,   46,
  118,  118,   46,  118,   58,  118,   38,   51,    0,   39,
   40,   41,   36,   37,   38,    0,    0,   39,   40,   41,
  127,   36,   37,  163,    0,    0,  164,    0,   90,   38,
   90,    0,   39,   40,   41,    0,   22,   22,   38,   58,
    0,   39,   40,   41,    0,   22,    0,    0,   22,   22,
  115,  133,  116,  136,  146,   36,   37,    0,  120,    0,
  121,   91,    0,    0,    0,    0,   22,  114,    9,  113,
    0,    0,   92,   11,    0,   39,   40,   41,    0,    0,
   22,   84,   55,    0,    6,  118,  182,    7,    8,    0,
    0,    0,   22,    0,   22,    0,  224,   10,    0,   22,
    0,    0,    0,   12,    0,    0,    0,    0,    0,   83,
    0,    0,   58,    0,  133,    0,   86,    0,    0,   55,
    0,    6,    0,  179,    7,    8,    4,    5,  136,    6,
    0,    0,    7,    8,   10,   56,    0,    0,   22,    0,
   12,    9,   10,    0,    0,   84,   11,   84,   12,    0,
   55,   55,    0,   22,    0,   22,    0,    0,    0,   99,
   99,   90,   90,    0,   90,   90,   90,   90,   90,   22,
    0,  133,   55,   83,    0,   83,   99,   90,    0,   99,
   99,   99,   34,   90,    0,    0,    0,  177,    0,  178,
    0,    0,   55,    0,    6,  118,  119,    7,    8,    0,
   30,    0,    0,  133,   51,   51,   22,   10,    0,   95,
    0,  132,   22,   12,    0,   46,    0,    0,  133,  136,
    0,    0,   46,   63,  136,    0,   51,    0,  118,   46,
   55,    0,    6,   46,   46,    7,    8,    0,    0,  146,
    0,  170,    0,    0,   22,   10,   91,    0,    0,    0,
    0,   12,   87,    9,    0,   95,    0,   92,   11,   22,
   22,    0,    0,  141,    0,   22,    0,  159,  160,    0,
    0,   95,    0,  109,  110,  111,  112,    0,   84,   84,
    0,   84,   84,   84,   84,   84,  213,    0,    0,    0,
    0,    0,    0,  132,   84,    0,    0,    0,    0,    0,
   84,    0,    0,    0,  135,    0,   83,   83,    0,   83,
   83,   83,   83,   83,   34,  194,   34,   86,   86,   34,
   34,   55,   83,    6,    0,  176,    7,    8,   83,   34,
   86,    4,    5,  231,    6,   34,   10,    7,    8,   95,
  232,    0,   12,   55,    0,    6,    9,   10,    7,    8,
    0,   11,    0,   12,    4,    5,    0,    6,   10,    0,
    7,    8,    0,    0,   12,    0,    0,    0,    0,    9,
   10,    0,    4,    5,   11,    6,   12,    0,    7,    8,
    0,    0,    0,    0,    5,    0,    6,    9,   10,    7,
    8,    0,   11,    0,   12,    5,    0,    6,    9,   10,
    7,    8,    0,   11,    0,   12,    0,    0,    0,    9,
   10,    0,    0,    0,   11,    0,   12,    0,    5,    0,
    6,    0,    0,    7,    8,   55,    0,    6,    0,  214,
    7,    8,    9,   10,    0,    0,   55,   11,    6,   12,
   10,    7,    8,    0,    0,    0,   12,   55,    0,    6,
    0,   10,    7,    8,    0,    0,    0,   12,   44,   52,
   52,    0,   10,    0,    0,   55,    0,    6,   12,    0,
    7,    8,   55,    0,    6,    0,    0,    7,    8,    0,
   10,    0,    0,    0,    0,   88,   12,   10,    0,    0,
   52,    0,    0,   12,   44,   55,    0,    6,    0,    0,
    7,    8,  129,    0,    0,    0,    0,    0,   44,    0,
   10,    0,    0,  138,    0,    0,   12,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  175,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   44,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   44,   59,   59,   59,  125,   45,  123,   40,
   59,   40,   41,   43,   86,   45,   40,   59,   40,   44,
    2,   98,   56,   59,    0,   41,   59,   43,    8,   45,
   96,   13,  258,   59,   59,   14,   81,  123,   43,   42,
   45,   41,   42,   43,   47,   45,   57,   47,    0,   44,
  273,  277,   31,   44,   59,   59,   40,   59,   38,   59,
   60,  257,   62,  140,   59,   41,   42,   43,  271,   45,
   61,   47,    0,  276,   40,   26,  148,   44,   41,   43,
  118,   45,  278,   59,   60,   64,   62,  256,  257,   41,
  167,   43,   40,   45,   61,  256,    0,  258,  164,  144,
  145,  278,   59,  137,  260,  261,   61,   59,   60,  120,
   62,  256,  257,   41,   41,   43,  277,   45,   41,    0,
   43,  103,   45,  123,  257,  125,  270,   78,   79,  115,
  116,   59,   60,  205,   62,  125,  126,   41,  257,   43,
  259,   45,  257,  262,  263,   41,   59,  123,   59,  125,
    0,   41,  271,  272,  257,   59,   60,  276,   62,  278,
  120,  121,  122,   41,  256,  257,  177,   40,   41,  257,
  257,  123,  264,  125,   44,   59,  264,  261,   59,  271,
   59,   40,   41,  271,  276,   44,    0,  275,  276,   59,
   59,   59,  171,   40,   41,  123,    0,  125,  209,   40,
   41,  270,  256,   44,  260,  261,  261,   40,  270,   62,
   42,  260,  261,  224,  256,  256,  257,  258,  257,  123,
  256,  125,   40,  256,  273,   -1,  256,  256,  257,  258,
   40,  256,  257,  274,  265,  264,  277,  278,  279,  278,
  256,  265,  271,  265,  125,  274,  275,  276,  277,  278,
  279,  256,  256,  278,  256,   41,  256,  257,  258,  259,
  260,  261,  262,  263,   -1,  256,  266,  267,  268,  269,
  270,  271,  272,  273,  274,  125,  276,  277,  278,  279,
  256,  257,  258,  259,  260,  261,  262,  263,   41,  256,
  266,  267,  268,  269,  270,  271,  272,  273,  274,  256,
  276,  277,  278,  279,  256,  257,  258,  259,  260,  261,
  262,  263,  148,   -1,  266,  267,  268,  269,  270,  271,
  272,  273,  274,   -1,  276,  277,  278,  279,  256,  257,
  258,  259,  260,  261,  262,  263,   -1,   -1,  266,  267,
  268,  269,  270,  271,  272,  273,  274,   -1,  276,  277,
  278,  279,  256,  257,  258,  259,  260,  261,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,  271,  272,  273,
  274,   -1,  276,  277,  278,  279,  257,   -1,  259,  260,
  261,  262,  263,  256,  257,  258,  256,  257,   44,  270,
  271,  272,  273,   -1,   -1,  276,   41,  278,  257,  258,
  125,  274,   -1,   59,  277,  278,  279,  257,  278,  259,
  257,  258,  262,  263,   59,  274,  257,  258,  277,  278,
  279,  271,  272,  256,  257,  258,  276,  274,  278,   -1,
  277,  278,  279,  274,   -1,  123,  277,  278,  279,  257,
  258,  274,   44,  123,  277,  278,  279,  257,  258,   41,
   42,   43,   44,   45,   10,   47,  274,   59,   -1,  277,
  278,  279,  257,  258,  274,   -1,   -1,  277,  278,  279,
  256,  257,  258,   41,   -1,   -1,   44,   -1,  123,  274,
  125,   -1,  277,  278,  279,   -1,    1,    2,  274,   45,
   -1,  277,  278,  279,   -1,   10,   -1,   -1,   13,   14,
   43,   57,   45,   59,  257,  257,  258,   -1,  123,   -1,
  125,  264,   -1,   -1,   -1,   -1,   31,   60,  271,   62,
   -1,   -1,  275,  276,   -1,  277,  278,  279,   -1,   -1,
   45,   59,  257,   -1,  259,  260,  261,  262,  263,   -1,
   -1,   -1,   57,   -1,   59,   -1,  123,  272,   -1,   64,
   -1,   -1,   -1,  278,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,  118,   -1,  120,   -1,   59,   -1,   -1,  257,
   -1,  259,   -1,   59,  262,  263,  256,  257,  134,  259,
   -1,   -1,  262,  263,  272,  273,   -1,   -1,  103,   -1,
  278,  271,  272,   -1,   -1,  123,  276,  125,  278,   -1,
  256,  257,   -1,  118,   -1,  120,   -1,   -1,   -1,  257,
  258,  256,  257,   -1,  259,  260,  261,  262,  263,  134,
   -1,  177,  278,  123,   -1,  125,  274,  272,   -1,  277,
  278,  279,  125,  278,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,  257,   -1,  259,  260,  261,  262,  263,   -1,
  125,   -1,   -1,  209,  256,  257,  171,  272,   -1,   34,
   -1,  125,  177,  278,   -1,  257,   -1,   -1,  224,  225,
   -1,   -1,  264,  125,  230,   -1,  278,   -1,  270,  271,
  257,   -1,  259,  275,  276,  262,  263,   -1,   -1,  257,
   -1,  125,   -1,   -1,  209,  272,  264,   -1,   -1,   -1,
   -1,  278,  125,  271,   -1,   80,   -1,  275,  276,  224,
  225,   -1,   -1,  125,   -1,  230,   -1,   92,   93,   -1,
   -1,   96,   -1,  266,  267,  268,  269,   -1,  256,  257,
   -1,  259,  260,  261,  262,  263,  125,   -1,   -1,   -1,
   -1,   -1,   -1,  125,  272,   -1,   -1,   -1,   -1,   -1,
  278,   -1,   -1,   -1,  125,   -1,  256,  257,   -1,  259,
  260,  261,  262,  263,  257,  125,  259,  260,  261,  262,
  263,  257,  272,  259,   -1,  261,  262,  263,  278,  272,
  273,  256,  257,  125,  259,  278,  272,  262,  263,  164,
  125,   -1,  278,  257,   -1,  259,  271,  272,  262,  263,
   -1,  276,   -1,  278,  256,  257,   -1,  259,  272,   -1,
  262,  263,   -1,   -1,  278,   -1,   -1,   -1,   -1,  271,
  272,   -1,  256,  257,  276,  259,  278,   -1,  262,  263,
   -1,   -1,   -1,   -1,  257,   -1,  259,  271,  272,  262,
  263,   -1,  276,   -1,  278,  257,   -1,  259,  271,  272,
  262,  263,   -1,  276,   -1,  278,   -1,   -1,   -1,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,   -1,  257,   -1,
  259,   -1,   -1,  262,  263,  257,   -1,  259,   -1,  261,
  262,  263,  271,  272,   -1,   -1,  257,  276,  259,  278,
  272,  262,  263,   -1,   -1,   -1,  278,  257,   -1,  259,
   -1,  272,  262,  263,   -1,   -1,   -1,  278,    6,    7,
    8,   -1,  272,   -1,   -1,  257,   -1,  259,  278,   -1,
  262,  263,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
  272,   -1,   -1,   -1,   -1,   33,  278,  272,   -1,   -1,
   38,   -1,   -1,  278,   42,  257,   -1,  259,   -1,   -1,
  262,  263,   50,   -1,   -1,   -1,   -1,   -1,   56,   -1,
  272,   -1,   -1,   61,   -1,   -1,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  117,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  137,
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
"control : do_until ';'",
"control : do_until error",
"sentencia_IF : IF condicional_opt cuerpo_opt ENDIF ';'",
"sentencia_IF : IF condicional_opt cuerpo_opt else_opt",
"sentencia_IF : IF condicional_opt cuerpo_opt ENDIF error",
"sentencia_IF : IF condicional_opt cuerpo_opt ';'",
"sentencia_IF : IF condicional_opt '{' ENDIF ';'",
"sentencia_IF : IF condicional_opt '{' else_opt",
"sentencia_IF : IF condicional_opt '}' ENDIF ';'",
"sentencia_IF : IF condicional_opt '}' else_opt",
"sentencia_IF : IF condicional_opt ENDIF ';'",
"sentencia_IF : IF condicional_opt else_opt ';'",
"else_opt : ELSE cuerpo_opt ENDIF ';'",
"else_opt : ELSE cuerpo_opt ENDIF error",
"else_opt : ELSE cuerpo_opt ';'",
"else_opt : ELSE ';'",
"else_opt : ELSE '{' ENDIF ';'",
"else_opt : ELSE '}' ENDIF ';'",
"else_opt : ELSE ENDIF",
"condicional_opt : '(' condicion ')'",
"condicional_opt : condicion ')'",
"condicional_opt : '(' condicion",
"condicional_opt : condicion",
"cuerpo_opt : '{' lista_sentencias_ejecutables '}'",
"cuerpo_opt : sentencia_ejecutable",
"cuerpo_opt : '{' lista_sentencias_ejecutables",
"cuerpo_opt : lista_sentencias_ejecutables '}'",
"cuerpo_opt : '{' '}'",
"condicion : expresion comparador expresion",
"do_until : DO cuerpo_opt UNTIL condicional_opt",
"do_until : DO UNTIL condicional_opt",
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
"asignacion : ID ASIGNAR expresion",
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
"lambda : '(' tipo ')' '{' lista_sentencias_ejecutables '}'",
"lambda : '(' tipo ')' '{' lista_sentencias_ejecutables",
"lambda : '(' tipo ')' lista_sentencias_ejecutables '}'",
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

//#line 306 "parser_test.y"
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
//#line 656 "Parser.java"
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
{ System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); }
break;
case 2:
//#line 22 "parser_test.y"
{ System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
break;
case 3:
//#line 25 "parser_test.y"
{ System.out.println("Falta apertura del programa '{'. Linea: " + al.getLine()); }
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
case 11:
//#line 42 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 13:
//#line 47 "parser_test.y"
{ System.out.println("Asignacion detectada"); }
break;
case 17:
//#line 51 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 19:
//#line 53 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 20:
//#line 54 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 21:
//#line 55 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 22:
//#line 60 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 23:
//#line 62 "parser_test.y"
{ System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
break;
case 24:
//#line 64 "parser_test.y"
{ System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
break;
case 25:
//#line 66 "parser_test.y"
{ System.out.println("No se debe especificar la unidad en declaracion de funcion. Linea: " + al.getLine()); }
break;
case 27:
//#line 71 "parser_test.y"
{ System.out.println("Parametros formales invalidos. Linea: " + al.getLine()); }
break;
case 29:
//#line 76 "parser_test.y"
{ System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
break;
case 30:
//#line 77 "parser_test.y"
{ System.out.println("Faltan llaves de la funcion. Linea: " + al.getLine()); }
break;
case 33:
//#line 84 "parser_test.y"
{ System.out.println("PROBLEMON. Linea: " + al.getLine()); }
break;
case 41:
//#line 101 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 42:
//#line 105 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 43:
//#line 106 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 44:
//#line 107 "parser_test.y"
{ System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
break;
case 45:
//#line 108 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 46:
//#line 109 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 47:
//#line 110 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 48:
//#line 111 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 49:
//#line 112 "parser_test.y"
{ System.out.println("Se espera un tipo pero se encontro .... Linea: " + al.getLine()); }
break;
case 50:
//#line 113 "parser_test.y"
{ System.out.println("Se espera un Identifier para el parametro formal  Linea: " + al.getLine()); }
break;
case 51:
//#line 118 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 52:
//#line 119 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 53:
//#line 120 "parser_test.y"
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 54:
//#line 121 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 55:
//#line 122 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 56:
//#line 123 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 57:
//#line 124 "parser_test.y"
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 58:
//#line 130 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 64:
//#line 147 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 65:
//#line 149 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 66:
//#line 151 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 67:
//#line 153 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 68:
//#line 155 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 69:
//#line 157 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 70:
//#line 159 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 71:
//#line 161 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 72:
//#line 163 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 73:
//#line 165 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 75:
//#line 171 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 173 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 77:
//#line 175 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 177 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 79:
//#line 179 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 80:
//#line 181 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 82:
//#line 186 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 83:
//#line 187 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 84:
//#line 188 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 87:
//#line 194 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 88:
//#line 195 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 89:
//#line 196 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 90:
//#line 201 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 91:
//#line 205 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 92:
//#line 206 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 99:
//#line 216 "parser_test.y"
{ errManager.error("Falta comparador", al.getLine()); }
break;
case 102:
//#line 225 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 103:
//#line 226 "parser_test.y"
{ System.out.println("Falta ;. Linea: " + al.getLine()); }
break;
case 104:
//#line 227 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 105:
//#line 228 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 108:
//#line 234 "parser_test.y"
{System.out.println("ENCONTRE TERMINO!. Linea: " + al.getLine()); }
break;
case 109:
//#line 235 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 110:
//#line 236 "parser_test.y"
{System.out.println("Falta delimitadores trunc. Linea: " + al.getLine()); }
break;
case 118:
//#line 250 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 119:
//#line 254 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 120:
//#line 255 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 121:
//#line 256 "parser_test.y"
{System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
break;
case 122:
//#line 257 "parser_test.y"
{System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
break;
case 123:
//#line 261 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 124:
//#line 264 "parser_test.y"
{errManager.debug("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 125:
//#line 265 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 127:
//#line 270 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 131:
//#line 277 "parser_test.y"
{System.out.println("Faltan argumentos. Linea: " + al.getLine());}
break;
case 132:
//#line 278 "parser_test.y"
{System.out.println("Falta parentesis de cierre. Linea: " + al.getLine());}
break;
case 133:
//#line 279 "parser_test.y"
{System.out.println("Falta parentesis de apertura. Linea: " + al.getLine());}
break;
case 134:
//#line 283 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 135:
//#line 284 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
break;
case 136:
//#line 285 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
break;
case 137:
//#line 286 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine());}
break;
//#line 1157 "Parser.java"
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
