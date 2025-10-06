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
//#line 20 "Parser.java"




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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    2,    2,    2,    3,
    3,    3,    4,    4,    4,    4,    4,    4,    6,    6,
    6,   14,   14,   15,   15,    1,    1,   17,   17,   16,
   16,   16,   19,   19,   19,    5,    5,    5,   21,   21,
   21,   21,   21,   18,   18,   18,   18,   18,   18,   18,
   18,   20,   22,   22,    8,    8,   25,   25,   27,   26,
   28,   28,   28,   28,   28,   28,   13,   13,    7,    7,
   23,   23,   23,   23,   29,   29,   29,   30,   30,   30,
   30,   30,    9,    9,    9,    9,   10,   10,   24,   11,
   12,   12,   12,   12,   31,   31,   32,   32,   33,   33,
};
final static short yylen[] = {                            2,
    4,    4,    3,    3,    3,    2,    1,    2,    1,    2,
    1,    1,    1,    1,    1,    1,    1,    1,    4,    3,
    3,    3,    2,    3,    2,    1,    2,    1,    2,    1,
    3,    3,    1,    3,    3,    1,    2,    2,    2,    3,
    1,    2,    3,    3,    2,    2,    1,    2,    1,    2,
    3,    3,    1,    1,    1,    1,    8,   12,    3,    8,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    3,
    3,    3,    1,    4,    3,    3,    1,    1,    1,    1,
    1,    1,    4,    4,    3,    3,    4,    4,    5,    4,
    3,    4,    3,    5,    1,    3,    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   67,    0,   68,    0,    0,    0,   26,    7,    0,    0,
   12,   13,   14,   15,   16,   17,   18,    0,    0,   55,
   56,    0,    0,    0,    0,    0,   38,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    4,   27,
    8,   10,    0,    0,    0,   37,    0,    0,    0,    0,
    3,    2,    0,    0,   79,    0,   80,    0,   81,    0,
    0,   77,    0,    0,    0,    0,    0,    0,    0,   30,
    0,   33,    0,    0,   54,    0,   20,    0,    0,    0,
    0,    0,    0,    0,   28,    0,    0,   85,    0,    1,
    0,   49,   23,   21,   43,   40,   96,   93,   99,  100,
    0,   98,    0,    0,    0,    0,    0,    0,    0,    0,
   50,   45,    0,    0,    0,   48,    0,   22,    0,    0,
   84,    0,    0,   25,    0,   61,   62,   63,   64,   65,
   66,    0,    0,   87,   88,   90,   95,    0,   29,   83,
   19,    0,    0,   86,    0,    0,    0,   75,   76,   51,
   44,    0,   32,   31,   35,   34,   52,   24,    0,    0,
    0,   97,   74,    0,    0,    0,    0,    0,    0,    0,
   57,   60,    0,    0,    0,   58,
};
final static short yydgoto[] = {                          4,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   28,   41,   87,   79,   96,   80,   81,   82,
   29,   83,   84,   85,   30,   31,   89,  142,   71,   72,
   32,  111,  112,
};
final static short yysindex[] = {                       -98,
 -101, -115,  521,    0,  521, -193,  -36,   -5,   49,   55,
    0,  -94,    0,  -35,  521,  317,    0,    0,   51,   84,
    0,    0,    0,    0,    0,    0,    0,  -40,  110,    0,
    0,   -6,  424,  435,  -91,  -84,    0,   37, -112,  400,
   41, -112, -153, -112, -160, -112,  -38,  453,    0,    0,
    0,    0,  -34,  -84,  163,    0,   41, -170,  -89, -200,
    0,    0, -176,  156,    0,  161,    0,  172,    0,  135,
   72,    0,  156,  -67,  200, -136,    0,  -43,  -21,    0,
    9,    0,  -48,  135,    0,  471,    0,  148,  182,  183,
  352,  420,  -53,  -28,    0,  -80,  135,    0,   10,    0,
   41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  198,    0, -183,  198,  -30, -112,  -90,  -90,  -90,  -90,
    0,    0,  -14,   -2,  205,    0,  191,    0,  191,  -24,
    0,  -24,  191,    0,  489,    0,    0,    0,    0,    0,
    0, -112,  128,    0,    0,    0,    0,  -12,    0,    0,
    0, -183,  198,    0,  458,   72,   72,    0,    0,    0,
    0,  139,    0,    0,    0,    0,    0,    0,  135, -160,
  238,    0,    0, -160,  -57, -112, -160, -182,  241,  166,
    0,    0, -160,  356,   23,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  116,    0,    0,    0,
    0,    0,    0,   78,    0,  297,    0,    0,  368,  391,
    0,    0,    0,    0,    0,    0,    0,    0,  345,    0,
    0,    0,    0,    0,    0,   78,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  301,    0,    0,
    0,    0,  127,  150,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,   24,    0,  173,
   47,    0,  -11,    0,   18,    0,  197,    0,    0,    0,
    0,    0,    0,   35,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  226,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  249,    0,    0,  276,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   32,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  299,    0,    0,   70,   93,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  266,    0,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   34,   -7,    0,  468,    0,    0,   46,    0,  729,    0,
    0,    0,  439,  -13,  -29,    0,   33,    4,  264,   81,
    0,    0,  -25,    0,    0,    0,  145,    0,   52,   65,
    7,  -56,  176,
};
final static int YYTABLESIZE=905;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         55,
   82,   76,   98,   40,   47,   55,  114,   15,   50,   76,
  154,  115,   38,   70,   57,   76,   88,   91,   92,  128,
   97,    5,  129,   78,    3,   50,   50,  104,   45,   49,
   82,   82,   49,   82,   42,   82,   33,   59,   34,  101,
   50,   82,   82,   82,  148,   82,   73,   82,   48,  131,
  150,   37,  132,  132,   60,  108,  153,  109,   47,   82,
   82,   47,   82,   35,   78,   78,   78,  178,   78,   71,
   78,  151,   46,   56,  109,   46,  110,  180,  181,  113,
   59,  109,   78,   78,   36,   78,  105,   73,   43,   73,
  155,   73,   72,  110,   44,   93,   94,   63,    8,   38,
  110,    9,   10,   64,   65,   73,   73,  106,   73,   51,
   71,   12,   71,  119,   71,   41,  169,   14,  120,  135,
   66,   95,   90,   67,   68,   82,   42,   50,   71,   71,
  163,   71,  164,   72,   11,   72,  167,   72,   95,   13,
    6,    7,   52,    8,   64,   65,    9,   10,   78,   39,
   88,   72,   72,   58,   72,   11,   12,    1,    2,   41,
   13,   66,   14,   86,   67,   68,   64,   65,  156,  157,
   42,   73,   70,   39,   41,   93,   94,  117,    8,  118,
   46,    9,   10,  158,  159,   42,   67,   68,  107,  121,
  117,   12,  118,   39,   71,  115,   86,   14,   93,   94,
  116,    8,  175,  103,    9,   10,  177,  141,   39,  140,
  165,   47,  166,  126,   12,  184,   53,   72,   64,   65,
   14,  133,  143,  144,  147,   69,   64,   65,   39,   46,
   39,   70,   64,   65,  127,   66,   39,   54,   67,   68,
   41,  152,  160,   66,   49,  162,   67,   68,   91,   66,
  170,   42,   67,   68,  161,   86,   82,   82,   82,   82,
  171,  174,   82,   82,  130,  130,   82,   82,   82,   82,
   82,   82,   82,   47,   39,   92,   82,  176,   82,   78,
   78,  182,   78,  186,   69,   78,   78,   46,  183,   78,
   78,   78,   78,   78,   78,   78,    6,   70,   94,   78,
    5,   78,   73,   73,   53,   73,   59,   91,   73,   73,
   99,   89,   73,   73,   73,   73,   73,   73,   73,   23,
  179,   86,   73,    0,   73,   71,   71,  172,   71,    0,
    0,   71,   71,    0,   92,   71,   71,   71,   71,   71,
   71,   71,    0,    0,   36,   71,    0,   71,   72,   72,
   69,   72,    0,    0,   72,   72,    0,   94,   72,   72,
   72,   72,   72,   72,   72,    0,    0,    9,   72,    0,
   72,   41,   41,   91,   41,    0,    0,   41,   41,    0,
    0,    0,   42,   42,    0,   42,   41,   41,   42,   42,
   11,   41,  145,   41,  117,    0,  118,   42,   42,    0,
   92,    0,   42,   36,   42,   39,   39,    0,   39,    0,
    0,   39,   39,  136,  137,  138,  139,    0,    0,  102,
   39,   39,    0,   94,    0,   39,   74,   39,   70,   70,
    0,   70,    0,   11,   70,   70,    0,   75,   13,   76,
   77,   49,   70,   70,   70,    0,    0,  102,   70,    0,
   70,    0,   86,   86,   74,   86,  122,    0,   86,   86,
  146,   11,  117,  123,  118,   75,   13,   86,   86,   36,
   11,    0,   86,    0,   86,   13,    0,    0,   78,    0,
  185,   69,   69,    0,   69,    0,    0,   69,   69,    0,
    0,    0,    9,   78,    0,   69,   69,   69,  173,    0,
  117,   69,  118,   69,   91,   91,    0,   91,    0,    0,
   91,   91,   95,  124,  125,   11,    0,    0,   91,   91,
   91,    0,    0,    0,   91,    0,   91,    0,    0,    0,
    0,   92,   92,    0,   92,    0,    0,   92,   92,    0,
    0,    0,    0,    0,    0,   92,   92,   92,   61,    0,
    0,   92,    0,   92,   94,   94,    0,   94,    0,   62,
   94,   94,    0,  149,    0,   78,    0,   78,   94,   94,
   94,   78,    6,    7,   94,    8,   94,  100,    9,   10,
    0,    0,    0,    0,    0,    0,    0,   11,   12,    0,
    0,    0,   13,    0,   14,  134,    0,    0,    0,    0,
   36,   36,    0,   36,    0,    0,   36,   36,    0,    0,
    0,   93,   94,  168,    8,   36,   36,    9,   10,    0,
   36,    0,   36,    9,    9,    0,    9,   12,    0,    9,
    9,    0,    0,   14,    0,    0,    0,   95,    9,    9,
    0,   95,  149,    9,  149,    9,   11,   11,    0,   11,
   95,  149,   11,   11,    0,    0,   73,   65,    0,    0,
    0,   11,   11,   74,    0,    0,   11,    0,   11,    0,
   11,    0,    0,   66,   75,   13,   67,   68,    0,    6,
    7,    0,    8,    0,    0,    9,   10,    0,    0,    0,
    6,    7,    0,    8,   11,   12,    9,   10,    0,   13,
    0,   14,    0,    0,    0,   11,   12,    0,    6,    7,
   13,    8,   14,    0,    9,   10,    0,    0,    0,    0,
    0,    0,    0,   11,   12,    0,    6,    7,   13,    8,
   14,    0,    9,   10,    0,    0,    0,    0,    0,    0,
    0,   11,   12,    0,    6,    7,   13,    8,   14,    0,
    9,   10,    0,    0,    0,    0,    0,    0,    0,   11,
   12,    0,    0,    0,   13,    0,   14,   69,   69,    0,
   69,   69,   69,    0,   69,   69,    6,    7,    0,    8,
    0,    0,    9,   10,    0,    0,    0,    0,    0,    0,
    0,   11,   12,    0,    0,    0,   13,    0,   14,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   69,   69,   69,   69,   69,   69,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   69,    0,
   69,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   69,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   69,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   41,   40,   40,   40,   63,  123,   16,   40,
   41,   40,    6,   39,   28,   40,   42,   43,   44,   41,
   46,  123,   44,    0,  123,   33,   34,   57,  123,   41,
   42,   43,   44,   45,   40,   47,    3,   44,    5,   53,
   48,   41,   42,   43,  125,   45,    0,   47,   15,   41,
   41,    6,   44,   44,   61,  256,  113,  258,   41,   59,
   60,   44,   62,  257,   41,   42,   43,  125,   45,    0,
   47,  101,   41,   28,  258,   44,  277,  260,  261,  256,
   44,  258,   59,   60,  278,   62,  257,   41,   40,   43,
  116,   45,    0,  277,   40,  256,  257,   61,  259,   93,
  277,  262,  263,  257,  258,   59,   60,  278,   62,   59,
   41,  272,   43,   42,   45,    0,  142,  278,   47,   86,
  274,   44,  276,  277,  278,  125,    0,  135,   59,   60,
  127,   62,  129,   41,  271,   43,  133,   45,   61,  276,
  256,  257,   59,  259,  257,  258,  262,  263,  125,    0,
  176,   59,   60,   44,   62,  271,  272,  256,  257,   44,
  276,  274,  278,  123,  277,  278,  257,  258,  117,  118,
   44,  125,    0,  265,   59,  256,  257,   43,  259,   45,
  265,  262,  263,  119,  120,   59,  277,  278,  278,  257,
   43,  272,   45,   44,  125,   40,    0,  278,  256,  257,
   40,  259,  170,   41,  262,  263,  174,   60,   59,   62,
  130,   40,  132,  257,  272,  183,  257,  125,  257,  258,
  278,  270,   41,   41,  278,    0,  257,  258,  265,  265,
  265,   59,  257,  258,  256,  274,  265,  278,  277,  278,
  125,   44,  257,  274,  256,   41,  277,  278,    0,  274,
  123,  125,  277,  278,  257,   59,  256,  257,  270,  259,
  273,  123,  262,  263,  256,  256,  266,  267,  268,  269,
  270,  271,  272,  256,  125,    0,  276,   40,  278,  256,
  257,   41,  259,  261,   59,  262,  263,  256,  123,  266,
  267,  268,  269,  270,  271,  272,    0,  125,    0,  276,
    0,  278,  256,  257,  270,  259,   41,   59,  262,  263,
   47,  270,  266,  267,  268,  269,  270,  271,  272,  123,
  176,  125,  276,   -1,  278,  256,  257,  152,  259,   -1,
   -1,  262,  263,   -1,   59,  266,  267,  268,  269,  270,
  271,  272,   -1,   -1,    0,  276,   -1,  278,  256,  257,
  125,  259,   -1,   -1,  262,  263,   -1,   59,  266,  267,
  268,  269,  270,  271,  272,   -1,   -1,    0,  276,   -1,
  278,  256,  257,  125,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,  256,  257,   -1,  259,  271,  272,  262,  263,
    0,  276,   41,  278,   43,   -1,   45,  271,  272,   -1,
  125,   -1,  276,   59,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,  266,  267,  268,  269,   -1,   -1,  257,
  271,  272,   -1,  125,   -1,  276,  264,  278,  256,  257,
   -1,  259,   -1,  271,  262,  263,   -1,  275,  276,   40,
   41,  125,  270,  271,  272,   -1,   -1,  257,  276,   -1,
  278,   -1,  256,  257,  264,  259,  257,   -1,  262,  263,
   41,  271,   43,  264,   45,  275,  276,  271,  272,  125,
  271,   -1,  276,   -1,  278,  276,   -1,   -1,   40,   -1,
  125,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,  125,   55,   -1,  270,  271,  272,   41,   -1,
   43,  276,   45,  278,  256,  257,   -1,  259,   -1,   -1,
  262,  263,   45,   75,   76,  125,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,   -1,   -1,   -1,
   -1,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,   -1,   -1,   -1,  270,  271,  272,  125,   -1,
   -1,  276,   -1,  278,  256,  257,   -1,  259,   -1,  125,
  262,  263,   -1,   96,   -1,  127,   -1,  129,  270,  271,
  272,  133,  256,  257,  276,  259,  278,  125,  262,  263,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,   -1,
   -1,   -1,  276,   -1,  278,  125,   -1,   -1,   -1,   -1,
  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,   -1,
   -1,  256,  257,  125,  259,  271,  272,  262,  263,   -1,
  276,   -1,  278,  256,  257,   -1,  259,  272,   -1,  262,
  263,   -1,   -1,  278,   -1,   -1,   -1,  170,  271,  272,
   -1,  174,  175,  276,  177,  278,  256,  257,   -1,  259,
  183,  184,  262,  263,   -1,   -1,  257,  258,   -1,   -1,
   -1,  271,  272,  264,   -1,   -1,  276,   -1,  278,   -1,
  271,   -1,   -1,  274,  275,  276,  277,  278,   -1,  256,
  257,   -1,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,
  256,  257,   -1,  259,  271,  272,  262,  263,   -1,  276,
   -1,  278,   -1,   -1,   -1,  271,  272,   -1,  256,  257,
  276,  259,  278,   -1,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  271,  272,   -1,  256,  257,  276,  259,
  278,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  271,  272,   -1,  256,  257,  276,  259,  278,   -1,
  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,   39,   40,   -1,
   42,   43,   44,   -1,   46,   47,  256,  257,   -1,  259,
   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  115,  116,  117,  118,  119,  120,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  130,   -1,
  132,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  142,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  176,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=278;
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
"FLECHA","LONG","DO","UNTIL","TRUNC","CR","STRING","CTEF","IDCOMP",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID '{' lista_sentencias '}'",
"programa : error '{' lista_sentencias '}'",
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : ID '{' lista_sentencias",
"programa : ID lista_sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable ';'",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable ';'",
"sentencia_declarativa : declaracion_variable",
"sentencia_declarativa : declaracion_funcion",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : control",
"sentencia_ejecutable : llamada_funcion",
"sentencia_ejecutable : print",
"sentencia_ejecutable : retorno",
"sentencia_ejecutable : asignacion_multiple",
"declaracion_funcion : tipo ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : tipo parametros_formales_opt cuerpo_funcion_opt",
"parametros_formales_opt : '(' lista_parametros_formales ')'",
"parametros_formales_opt : '(' ')'",
"cuerpo_funcion_opt : '{' lista_sentencias '}'",
"cuerpo_funcion_opt : '{' '}'",
"lista_sentencias : sentencia",
"lista_sentencias : lista_sentencias sentencia",
"lista_sentencias_ejecutables : sentencia_ejecutable",
"lista_sentencias_ejecutables : lista_sentencias_ejecutables sentencia_ejecutable",
"lista_parametros_formales : parametro_formal",
"lista_parametros_formales : lista_parametros_formales ',' parametro_formal",
"lista_parametros_formales : lista_parametros_formales error parametro_formal",
"lista_parametros_reales : parametro_real_compuesto",
"lista_parametros_reales : lista_parametros_reales ',' parametro_real_compuesto",
"lista_parametros_reales : lista_parametros_reales error parametro_real_compuesto",
"declaracion_variable : lista_identificadores",
"declaracion_variable : tipo asignacion",
"declaracion_variable : error asignacion",
"lista_identificadores : tipo IDCOMP",
"lista_identificadores : lista_identificadores ',' IDCOMP",
"lista_identificadores : ID",
"lista_identificadores : tipo ID",
"lista_identificadores : lista_identificadores ',' ID",
"parametro_formal : CR tipo ID",
"parametro_formal : CR ID",
"parametro_formal : CR tipo",
"parametro_formal : CR",
"parametro_formal : tipo ID",
"parametro_formal : ID",
"parametro_formal : LAMBDA ID",
"parametro_formal : CR LAMBDA ID",
"parametro_real_compuesto : parametro_real FLECHA parametro_formal",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF",
"control : do_until",
"sentencia_IF : IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ENDIF",
"sentencia_IF : IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ELSE '{' lista_sentencias_ejecutables '}' ENDIF",
"condicion : expresion comparador expresion",
"do_until : DO '{' lista_sentencias_ejecutables '}' UNTIL '(' condicion ')'",
"comparador : MENORIGUAL",
"comparador : MAYORIGUAL",
"comparador : IGUALIGUAL",
"comparador : DISTINTO",
"comparador : '>'",
"comparador : '<'",
"tipo : LONG",
"tipo : STRING",
"asignacion : IDCOMP ASIGNAR expresion",
"asignacion : ID ASIGNAR expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : TRUNC '(' expresion ')'",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : IDCOMP",
"factor : CTEL",
"factor : CTEF",
"factor : llamada_funcion",
"factor : ID",
"llamada_funcion : IDCOMP '(' lista_parametros_reales ')'",
"llamada_funcion : ID '(' lista_parametros_reales ')'",
"llamada_funcion : IDCOMP '(' ')'",
"llamada_funcion : ID '(' ')'",
"print : PRINT '(' STRING ')'",
"print : PRINT '(' expresion ')'",
"lambda : '(' tipo ')' '{' lista_sentencias_ejecutables",
"retorno : RETURN '(' expresion ')'",
"asignacion_multiple : ids '=' lista_constantes",
"asignacion_multiple : error ids '=' lista_constantes",
"asignacion_multiple : ids '=' error",
"asignacion_multiple : error ids '=' error lista_constantes",
"ids : IDCOMP",
"ids : ids ',' IDCOMP",
"lista_constantes : lista_constantes ',' constante",
"lista_constantes : constante",
"constante : CTEL",
"constante : CTEF",
};

//#line 238 "parser_test.y"
private AnalizadorLexico al;

public int yylex(){
    this.al = AnalizadorLexico.getInstance();
    int token = al.yylex();
    System.err.println("Se reconocio el token " + token + " ("+ TokenNames.getTokenName(token) + ") En linea " + al.getLine());
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
//#line 552 "Parser.java"
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
{ System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); }
break;
case 2:
//#line 21 "parser_test.y"
{ System.out.println("Declaracion de programa invalida, identificador invalido. Linea: " + al.getLine()); }
break;
case 3:
//#line 24 "parser_test.y"
{ System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
break;
case 4:
//#line 27 "parser_test.y"
{ System.out.println("Falta apertura del programa '{'. Linea: " + al.getLine()); }
break;
case 5:
//#line 31 "parser_test.y"
{ System.out.println("Falta cierre del programa '}'. Linea: " + al.getLine()); }
break;
case 6:
//#line 32 "parser_test.y"
{ System.out.println("Faltan delimitadores de programa '{' y '}' '}'. Linea: " + al.getLine()); }
break;
case 9:
//#line 38 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 11:
//#line 43 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 17:
//#line 52 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 19:
//#line 59 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 20:
//#line 61 "parser_test.y"
{ System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
break;
case 21:
//#line 63 "parser_test.y"
{ System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
break;
case 25:
//#line 73 "parser_test.y"
{ System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
break;
case 32:
//#line 90 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 35:
//#line 96 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 37:
//#line 101 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 38:
//#line 102 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 39:
//#line 106 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 40:
//#line 107 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 41:
//#line 108 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 42:
//#line 109 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 43:
//#line 110 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 44:
//#line 114 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 45:
//#line 115 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 46:
//#line 116 "parser_test.y"
{ System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
break;
case 47:
//#line 117 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 48:
//#line 118 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 49:
//#line 119 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 50:
//#line 120 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 51:
//#line 121 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 52:
//#line 125 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 57:
//#line 140 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 58:
//#line 142 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 59:
//#line 146 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 60:
//#line 151 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 69:
//#line 169 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 70:
//#line 170 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 74:
//#line 177 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 82:
//#line 191 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 83:
//#line 195 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 84:
//#line 196 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 85:
//#line 197 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 86:
//#line 198 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 87:
//#line 202 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 88:
//#line 203 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 89:
//#line 207 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 90:
//#line 211 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 91:
//#line 215 "parser_test.y"
{ System.out.println("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 92:
//#line 216 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
case 93:
//#line 217 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
case 94:
//#line 218 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
//#line 905 "Parser.java"
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
