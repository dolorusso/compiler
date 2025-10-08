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
    4,    4,    6,    6,    6,   14,   14,   15,   15,   15,
    1,    1,    1,   17,   17,   16,   16,   16,   19,   19,
   19,   18,   18,   18,   18,   18,   18,   18,   18,   18,
    5,    5,    5,    5,    5,    5,    5,   20,   21,   21,
    8,    8,   24,   24,   26,   26,   26,   27,   27,   27,
   27,   28,   25,   29,   29,   29,   29,   29,   29,   13,
   13,    7,    7,    7,   22,   22,   22,   22,   30,   30,
   30,   31,   31,   31,   31,   31,    9,    9,    9,   10,
   23,   11,   12,   12,   12,   12,   33,   33,   34,   34,
   32,   32,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    3,    2,    1,    1,    2,
    2,    1,    1,    2,    2,    2,    2,    1,    2,    2,
    2,    2,    4,    3,    3,    3,    2,    3,    2,    0,
    1,    2,    2,    1,    2,    1,    3,    2,    1,    3,
    2,    3,    2,    3,    2,    1,    2,    3,    2,    2,
    2,    3,    2,    1,    2,    3,    2,    3,    1,    1,
    1,    1,    4,    6,    3,    2,    2,    3,    1,    2,
    2,    3,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    4,    3,    3,    3,    1,    4,    3,    3,
    1,    1,    1,    1,    1,    1,    4,    4,    4,    4,
    4,    4,    4,    5,    3,    4,    1,    3,    3,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   80,    0,
   81,    0,    0,    0,   31,    8,    9,    0,   12,   13,
    0,    0,    0,    0,   18,    0,   61,   62,    0,    7,
    0,   33,    0,    0,    0,    0,  111,    0,  112,    0,
   94,    0,   95,    0,    0,    0,    0,   91,   93,    0,
    0,    0,    0,   34,    0,    0,    0,    0,    6,    0,
    3,   32,   11,   57,   53,   10,    0,   19,   14,   20,
   15,   21,   16,   22,   17,    0,   51,    0,    0,    0,
    0,    0,    2,    0,    0,    0,    0,    0,   27,    0,
    0,   36,    0,   39,    0,    0,   60,    0,   24,    0,
    0,    0,   74,   75,   76,   77,   78,   79,    0,    0,
    0,    0,   66,    0,    0,    0,    0,    0,   71,   35,
    0,    0,    0,    0,    1,   56,   52,    0,   46,   25,
    0,  108,  105,  110,    0,   47,   49,   43,    0,    0,
    0,   50,   45,   26,    0,   38,   98,    0,   41,    0,
   29,    0,    0,   65,    0,    0,    0,    0,   63,   89,
   90,  100,  102,   68,   73,   83,   82,   99,   97,   23,
    0,  106,  103,    0,   48,   44,   42,    0,   37,   40,
   58,   28,   88,    0,  104,  109,  101,   64,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   43,   23,
   24,   25,   26,   35,   99,   91,   55,   92,   93,   94,
   95,   96,   97,   27,   28,   45,   56,   46,  111,   47,
   48,   49,   29,  135,
};
final static short yysindex[] = {                      -101,
  245,  301,    0,  -29,  -38,  157,  -13,   -7,    0,  -84,
    0,  -37,  324,  371,    0,    0,    0,  139,    0,    0,
  -54,  -52,  -48,  -46,    0,  -40,    0,    0,  -25,    0,
  389,    0, -223,  -32,  -86,   32,    0,   45,    0,   51,
    0, -223,    0,  114,  -84,   54,   31,    0,    0, -223,
 -223,  -36,  267,    0,  -73, -172, -223,  151,    0,  418,
    0,    0,    0,    0,    0,    0, -216,    0,    0,    0,
    0,    0,    0,    0,    0,   63,    0,  218,  -86,   49,
 -166, -169,    0,   57,   32, -140, -113, -177,    0, -136,
  208,    0,  -24,    0, -145,   57,    0,  351,    0,  163,
 -223,  104,    0,    0,    0,    0,    0,    0, -181, -181,
 -223, -119,    0, -181, -181,   92,  105,  378,    0,    0,
  157,  -33,  113,  127,    0,    0,    0,  -86,    0,    0,
 -237,    0,    0,    0,  -21,    0,    0,    0,  -91,  -96,
  128,    0,    0,    0,  235,    0,    0,  163,    0,  -80,
    0,  436,  417,    0,   31,   31,   57,  -84,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    9,    0,    0, -237,    0,    0,    0,  -84,    0,    0,
    0,    0,    0,  -74,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  207,    0,    0,    0,    0,    0,
    0,  -16,    0,  192,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  140,    1,    0,    0,    0,   24,
    0,    0,    0,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,    0,  -49,    0,    0,    0,    0,  200,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  313,    0,    0,  140,    0,
    0,    0,    0,  116,  412,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -69,    0,    0,    0,    0,
    0,  275,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   28,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  140,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   70,   93,  189,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   16,   44,    0,    4,    0,    0,    0,    0,  467,    0,
    0,    0,   40,    6,  -64,    0,  153,  -66,  149,  -43,
    0,  667,    0,    0,    0,   88,  -39,  171,    0,   55,
   66,  -51,    0,   83,
};
final static int YYTABLESIZE=788;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         78,
   96,   34,   58,  100,   69,  112,   71,   88,   89,  109,
   73,  110,   75,   54,  130,   88,  147,   31,   81,  148,
   37,    2,  174,   92,  146,  167,   50,  107,   60,   32,
  134,   79,   51,   36,   37,   82,   98,  173,   53,   39,
  126,   96,   96,   96,  107,   96,   87,   96,   54,  149,
   38,  119,  174,   39,   40,   41,   54,   62,  120,   96,
   96,  127,   96,  170,   92,   92,   92,  185,   92,   85,
   92,  100,  114,   90,   62,   36,   37,  115,  179,  134,
  149,  128,   92,   92,  101,   92,  133,   87,   37,   87,
   58,   87,   86,    9,  113,   39,   40,   41,   11,  109,
  121,  110,   78,   62,  180,   87,   87,   39,   87,  131,
   85,  132,   85,  152,   85,   84,  136,   90,  184,  142,
  143,  120,  186,   96,  150,   96,  140,  141,   85,   85,
   90,   85,  162,   86,  109,   86,  110,   86,  187,   30,
  158,  159,  137,  138,  154,  163,   92,  109,   92,  110,
  139,   86,   86,  168,   86,    1,  109,    9,  110,  176,
  177,   54,   11,  155,  156,  175,   88,  169,  178,   87,
  148,   87,   52,  108,    6,  107,  181,    7,    8,  160,
  161,   54,   67,   52,   90,    6,  188,   10,    7,    8,
   88,    5,   85,   12,   85,   62,   42,   66,   10,    4,
   59,   68,   88,   70,   12,  118,  124,   72,  165,   74,
   69,   69,  102,  171,    0,   86,   76,   86,    0,    0,
   69,    0,  166,   69,   85,   37,   33,   57,   33,   72,
   80,   86,   36,   37,  172,    0,    0,   77,    9,  107,
   84,   38,   87,   11,   39,   40,   41,   72,  144,   38,
   54,  145,   39,   40,   41,    0,   96,   96,   89,   96,
   96,   96,   96,   96,   30,   54,   96,   96,   96,   96,
   96,   96,   96,   96,    0,    0,   96,    0,   96,   92,
   92,    0,   92,   92,   92,   92,   92,   70,   70,   92,
   92,   92,   92,   92,   92,   92,   92,   70,    0,   92,
   70,   92,   87,   87,    0,   87,   87,   87,   87,   87,
    0,   72,   87,   87,   87,   87,   87,   87,   87,   87,
    0,    0,   87,    0,   87,   85,   85,    0,   85,   85,
   85,   85,   85,   67,    0,   85,   85,   85,   85,   85,
   85,   85,   85,    0,    0,   85,    0,   85,   86,   86,
    0,   86,   86,   86,   86,   86,   55,    0,   86,   86,
   86,   86,   86,   86,   86,   86,    0,   13,   86,    0,
   86,   55,   84,    0,   84,   84,   84,   84,   84,  103,
  104,  105,  106,   36,   37,   84,   84,   84,   84,    0,
    0,   84,    0,   84,   63,   64,   30,   67,   30,    0,
   38,   30,   30,   39,   40,   41,  123,   36,   37,    0,
   30,   30,    0,   36,   37,   30,   65,   30,    0,   36,
   37,    0,    0,    0,   38,   30,    0,   39,   40,   41,
   38,    0,    0,   39,   40,   41,   38,    0,    0,   39,
   40,   41,    0,    0,   72,   72,    0,   72,   59,    0,
   72,   72,   46,   96,   96,   46,   96,  183,   96,  109,
   72,  110,   54,   54,  129,    0,   72,   22,   22,    0,
    0,   86,    0,    0,  129,  151,   22,    0,    9,   22,
   22,   86,   87,   11,   54,    0,    0,    0,    9,    0,
    0,  129,   87,   11,    0,   61,    0,   22,   86,    0,
    4,    5,  164,    6,    0,    9,    7,    8,    0,   87,
   11,   22,    0,   83,    0,    9,   10,    0,    0,   22,
   11,   22,   12,   52,    0,    6,   22,    0,    7,    8,
   67,   67,    0,   67,    0,    0,   67,   67,   10,    0,
    0,    0,  125,    0,   12,    0,   67,    0,    0,    0,
    0,    0,   67,    0,    0,    0,    4,    5,    0,    6,
  182,    0,    7,    8,   22,    0,    0,    0,   55,   55,
    0,    9,   10,    0,    0,    0,   11,    0,   12,    4,
    5,    0,    6,    0,   22,    7,    8,    0,    0,    0,
   55,    0,    0,    0,    9,   10,    0,    0,    0,   11,
    0,   12,    0,    0,    0,    0,    4,    5,    0,    6,
    0,    0,    7,    8,    0,    0,    0,    0,   22,    0,
    0,    9,   10,    0,   22,    0,   11,    5,   12,    6,
    0,    0,    7,    8,   52,    0,    6,    0,    0,    7,
    8,    9,   10,    0,   22,    5,   11,    6,   12,   10,
    7,    8,    0,    0,    0,   12,    0,    0,    0,    9,
   10,    0,    0,    0,   11,    0,   12,    0,   46,    0,
    0,    0,   44,    0,    5,   46,    6,    0,    0,    7,
    8,   96,   46,    0,    0,    0,   46,   46,    9,   10,
    0,    0,    5,   11,    6,   12,    0,    7,    8,   84,
    0,    0,    0,    0,    0,    0,    9,   10,   44,    0,
    0,   11,    0,   12,    0,    0,  116,  117,    0,    0,
    0,    0,    0,  122,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  153,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  157,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   44,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   40,   40,   59,   45,   59,   40,   41,   43,
   59,   45,   59,   10,   79,   40,   41,    2,   44,   44,
  258,  123,   44,    0,   91,   59,   40,   44,   13,   59,
   82,   26,   40,  257,  258,   61,  123,   59,  123,  277,
  257,   41,   42,   43,   61,   45,    0,   47,   45,   93,
  274,  125,   44,  277,  278,  279,   53,   14,   55,   59,
   60,  278,   62,  128,   41,   42,   43,   59,   45,    0,
   47,   40,   42,   34,   31,  257,  258,   47,  145,  131,
  124,   76,   59,   60,   40,   62,  256,   41,  258,   43,
   40,   45,    0,  271,   41,  277,  278,  279,  276,   43,
  273,   45,   40,   60,  148,   59,   60,  277,   62,   61,
   41,  278,   43,   98,   45,    0,  257,   78,  158,  256,
  257,  118,  174,  123,  270,  125,   87,   88,   59,   60,
   91,   62,   41,   41,   43,   43,   45,   45,  178,    0,
  260,  261,  256,  257,   41,   41,  123,   43,  125,   45,
  264,   59,   60,   41,   62,  257,   43,  271,   45,  256,
  257,  158,  276,  109,  110,  257,   40,   41,   41,  123,
   44,  125,  257,   60,  259,   62,  257,  262,  263,  114,
  115,  178,   44,  257,  145,  259,  261,  272,  262,  263,
   40,    0,  123,  278,  125,  152,   40,   59,  272,    0,
  270,  256,   40,  256,  278,   53,   58,  256,  121,  256,
  260,  261,   42,  131,   -1,  123,  257,  125,   -1,   -1,
  270,   -1,  256,  273,  257,  258,  265,  265,  265,   41,
  256,  264,  257,  258,  256,   -1,   -1,  278,  271,  256,
  125,  274,  275,  276,  277,  278,  279,   59,   41,  274,
   44,   44,  277,  278,  279,   -1,  256,  257,   41,  259,
  260,  261,  262,  263,  125,   59,  266,  267,  268,  269,
  270,  271,  272,  273,   -1,   -1,  276,   -1,  278,  256,
  257,   -1,  259,  260,  261,  262,  263,  260,  261,  266,
  267,  268,  269,  270,  271,  272,  273,  270,   -1,  276,
  273,  278,  256,  257,   -1,  259,  260,  261,  262,  263,
   -1,  123,  266,  267,  268,  269,  270,  271,  272,  273,
   -1,   -1,  276,   -1,  278,  256,  257,   -1,  259,  260,
  261,  262,  263,   59,   -1,  266,  267,  268,  269,  270,
  271,  272,  273,   -1,   -1,  276,   -1,  278,  256,  257,
   -1,  259,  260,  261,  262,  263,   44,   -1,  266,  267,
  268,  269,  270,  271,  272,  273,   -1,  123,  276,   -1,
  278,   59,  257,   -1,  259,  260,  261,  262,  263,  266,
  267,  268,  269,  257,  258,  270,  271,  272,  273,   -1,
   -1,  276,   -1,  278,  256,  257,  257,  123,  259,   -1,
  274,  262,  263,  277,  278,  279,  256,  257,  258,   -1,
  271,  272,   -1,  257,  258,  276,  278,  278,   -1,  257,
  258,   -1,   -1,   -1,  274,  125,   -1,  277,  278,  279,
  274,   -1,   -1,  277,  278,  279,  274,   -1,   -1,  277,
  278,  279,   -1,   -1,  256,  257,   -1,  259,  125,   -1,
  262,  263,   41,   42,   43,   44,   45,   41,   47,   43,
  272,   45,  256,  257,  257,   -1,  278,    1,    2,   -1,
   -1,  264,   -1,   -1,  257,  125,   10,   -1,  271,   13,
   14,  264,  275,  276,  278,   -1,   -1,   -1,  271,   -1,
   -1,  257,  275,  276,   -1,  125,   -1,   31,  264,   -1,
  256,  257,  125,  259,   -1,  271,  262,  263,   -1,  275,
  276,   45,   -1,  125,   -1,  271,  272,   -1,   -1,   53,
  276,   55,  278,  257,   -1,  259,   60,   -1,  262,  263,
  256,  257,   -1,  259,   -1,   -1,  262,  263,  272,   -1,
   -1,   -1,  125,   -1,  278,   -1,  272,   -1,   -1,   -1,
   -1,   -1,  278,   -1,   -1,   -1,  256,  257,   -1,  259,
  125,   -1,  262,  263,   98,   -1,   -1,   -1,  256,  257,
   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,
  257,   -1,  259,   -1,  118,  262,  263,   -1,   -1,   -1,
  278,   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,  276,
   -1,  278,   -1,   -1,   -1,   -1,  256,  257,   -1,  259,
   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,  152,   -1,
   -1,  271,  272,   -1,  158,   -1,  276,  257,  278,  259,
   -1,   -1,  262,  263,  257,   -1,  259,   -1,   -1,  262,
  263,  271,  272,   -1,  178,  257,  276,  259,  278,  272,
  262,  263,   -1,   -1,   -1,  278,   -1,   -1,   -1,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,   -1,  257,   -1,
   -1,   -1,    6,   -1,  257,  264,  259,   -1,   -1,  262,
  263,  270,  271,   -1,   -1,   -1,  275,  276,  271,  272,
   -1,   -1,  257,  276,  259,  278,   -1,  262,  263,   33,
   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,   42,   -1,
   -1,  276,   -1,  278,   -1,   -1,   50,   51,   -1,   -1,
   -1,   -1,   -1,   57,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  101,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  111,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  121,
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
"sentencia_ejecutable : control ';'",
"sentencia_ejecutable : llamada_funcion ';'",
"sentencia_ejecutable : print ';'",
"sentencia_ejecutable : retorno ';'",
"sentencia_ejecutable : asignacion_multiple",
"sentencia_ejecutable : control error",
"sentencia_ejecutable : llamada_funcion error",
"sentencia_ejecutable : print error",
"sentencia_ejecutable : retorno error",
"declaracion_funcion : tipo ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : tipo parametros_formales_opt cuerpo_funcion_opt",
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
"sentencia_IF : IF condicional_opt cuerpo_opt ENDIF",
"sentencia_IF : IF condicional_opt cuerpo_opt ELSE cuerpo_opt ENDIF",
"condicional_opt : '(' condicion ')'",
"condicional_opt : condicion ')'",
"condicional_opt : '(' condicion",
"cuerpo_opt : '{' lista_sentencias_ejecutables '}'",
"cuerpo_opt : lista_sentencias_ejecutables",
"cuerpo_opt : '{' lista_sentencias_ejecutables",
"cuerpo_opt : lista_sentencias_ejecutables '}'",
"condicion : expresion comparador expresion",
"do_until : DO cuerpo_opt UNTIL condicional_opt",
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
"asignacion : ID ASIGNAR expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : TRUNC '(' expresion ')'",
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
"print : PRINT '(' expresion ')'",
"lambda : '(' tipo ')' cuerpo_opt",
"retorno : RETURN '(' expresion ')'",
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

//#line 257 "parser_test.y"
private AnalizadorLexico al;
private ErrorManager error;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.error = ErrorManager.getInstance();
    //error.
}

public int yylex(){
    int token = al.yylex();
    error.debug("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ") En linea " + al.getLine());

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
//#line 554 "Parser.java"
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
//#line 56 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 23:
//#line 61 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 24:
//#line 63 "parser_test.y"
{ System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
break;
case 25:
//#line 65 "parser_test.y"
{ System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
break;
case 27:
//#line 70 "parser_test.y"
{ System.out.println("Parametros formales invalidos. Linea: " + al.getLine()); }
break;
case 29:
//#line 75 "parser_test.y"
{ System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
break;
case 30:
//#line 76 "parser_test.y"
{ System.out.println("Faltan llaves de la funcion. Linea: " + al.getLine()); }
break;
case 33:
//#line 83 "parser_test.y"
{ System.out.println("PROBLEMON. Linea: " + al.getLine()); }
break;
case 41:
//#line 100 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 42:
//#line 104 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 43:
//#line 105 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 44:
//#line 106 "parser_test.y"
{ System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
break;
case 45:
//#line 107 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 46:
//#line 108 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 47:
//#line 109 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 48:
//#line 110 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 49:
//#line 111 "parser_test.y"
{ System.out.println("Se espera un tipo pero se encontro .... Linea: " + al.getLine()); }
break;
case 50:
//#line 112 "parser_test.y"
{ System.out.println("Se espera un Identifier pero se encontro .... Linea: " + al.getLine()); }
break;
case 51:
//#line 117 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 52:
//#line 118 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 53:
//#line 119 "parser_test.y"
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 54:
//#line 120 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
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
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 58:
//#line 129 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 63:
//#line 145 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 64:
//#line 147 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 72:
//#line 165 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 73:
//#line 170 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 82:
//#line 188 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 83:
//#line 189 "parser_test.y"
{ System.out.println("Falta ;. Linea: " + al.getLine()); }
break;
case 84:
//#line 190 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 87:
//#line 196 "parser_test.y"
{System.out.println("ENCONTRE TERMINO!. Linea: " + al.getLine()); }
break;
case 88:
//#line 197 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 96:
//#line 211 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 97:
//#line 215 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 98:
//#line 216 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 99:
//#line 217 "parser_test.y"
{System.out.println("Llamado a funcion con parametros invalidos. Linea: " + al.getLine());}
break;
case 100:
//#line 221 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 101:
//#line 225 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 102:
//#line 229 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 103:
//#line 233 "parser_test.y"
{ error.debug("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 104:
//#line 234 "parser_test.y"
{ error.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
break;
case 105:
//#line 235 "parser_test.y"
{ error.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine());}
break;
case 106:
//#line 236 "parser_test.y"
{ error.error("Falta separador ';'", al.getLine());}
break;
//#line 939 "Parser.java"
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
