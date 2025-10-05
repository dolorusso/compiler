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
    0,    0,    0,    0,    0,    2,    2,    3,    3,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    5,    5,
    5,    5,    6,    6,    6,    6,    6,    1,    1,   15,
   15,   14,   14,   14,   17,   17,   17,   13,   13,   13,
   13,   13,   16,   16,   16,   16,   18,   19,   19,    8,
    8,   22,   22,   24,   23,   25,   25,   25,   25,   25,
   25,   12,   12,    7,    7,   20,   20,   20,   20,   26,
   26,   26,   27,   27,   27,   27,   27,    9,    9,    9,
    9,   10,   10,   21,   11,
};
final static short yylen[] = {                            2,
    4,    4,    3,    3,    3,    1,    1,    2,    1,    2,
    1,    1,    2,    1,    2,    1,    2,    1,    2,    2,
    2,    2,    8,    7,    8,    7,    6,    1,    2,    1,
    2,    1,    3,    3,    1,    3,    3,    1,    3,    3,
    1,    3,    3,    2,    3,    4,    3,    1,    1,    2,
    1,    8,   12,    3,    8,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    3,    1,    4,    3,
    3,    1,    1,    1,    1,    1,    1,    4,    4,    3,
    3,    4,    4,    5,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   62,    0,   63,    0,    0,    0,   28,    6,    7,    0,
    9,    0,   12,    0,    0,    0,    0,    0,   51,    0,
    0,    0,    0,   22,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    4,   29,    8,   10,   13,   15,
   17,    0,   21,    0,   50,    3,    2,    0,    0,    0,
    0,   74,    0,   75,    0,   76,    0,    0,   72,    0,
   81,    0,   35,    0,    0,   49,    0,    0,    0,    0,
    0,   30,    0,    0,   80,    0,    1,    0,    0,    0,
    0,    0,   32,   40,   42,   39,    0,    0,    0,    0,
    0,    0,    0,   79,    0,    0,   56,   57,   58,   59,
   60,   61,    0,    0,   82,   83,   85,    0,   31,   78,
    0,    0,    0,    0,    0,   44,    0,    0,    0,    0,
    0,    0,   70,   71,    0,   37,   36,   47,    0,    0,
    0,    0,    0,   45,    0,   43,   34,    0,   33,   69,
    0,    0,    0,   27,    0,    0,   46,    0,    0,    0,
    0,   24,   26,    0,   25,    0,   52,   55,   23,    0,
    0,    0,   53,
};
final static short yydgoto[] = {                          4,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   35,   92,   83,   93,   72,   73,   74,   75,
   76,   28,   29,   78,  113,   68,   69,
};
final static short yysindex[] = {                      -109,
 -107,  -78,  451,    0,  451, -242,  -36,  -14,    9,   28,
    0,  -90,    0,  -34,  451,  259,    0,    0,    0,   19,
    0,   26,    0,   38,   46,   55, -225,   66,    0,  270,
  288,  -28, -210,    0,   -4, -178,  -38, -178, -201, -178,
 -168, -178,  -32,  299,    0,    0,    0,    0,    0,    0,
    0,  -27,    0,   -4,    0,    0,    0, -152, -151, -206,
   91,    0,   95,    0,   97,    0,  -26,   56,    0, -154,
    0,  -23,    0, -124,  -26,    0,  114,  115,  117,  -18,
  128,    0, -118,  -26,    0,  -13,    0,  -41, -154, -143,
  -96,   -7,    0,    0,    0,    0, -178, -176, -176, -176,
 -176,  124,  -30,    0,  -30, -152,    0,    0,    0,    0,
    0,    0, -178,   43,    0,    0,    0, -103,    0,    0,
   57,   -6,  -69, -154,  -67,    0, -152,   69, -152,  146,
   56,   56,    0,    0,   79,    0,    0,    0,  -26, -168,
  164,  322,   80,    0,  -52,    0,    0,  451,    0,    0,
 -168,  -95, -178,    0,  345,  368,    0,  386, -168, -202,
  165,    0,    0,  409,    0,   85,    0,    0,    0, -168,
  375,  -50,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  163,    0,  186,  209,  232,    0,    0,    0,    0,
    0,  -39,   -5,    0,  153,    0,    0,    0,    0,    0,
    0,    0,    0,  213,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,  155,    0,    0,    0,    0,    0,    0,
    1,    0,    0,    0,   24,    0,  116,   47,    0,    0,
    0,    0,    0,    0,  -54,    0,    0,    0,    0,    0,
    0,    0,    0,  140,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   70,   93,    0,    0,    0,    0,    0,    0,  180,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -48,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   59,  166,    0,  527,    0,    0,   81,    0,  652,    0,
    0,  642,  197,  167, -129,  -77,  210,   15,    0,  663,
    0,    0,    0,  101,    0,   44,   50,
};
final static int YYTABLESIZE=816;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        121,
   77,   70,   71,   37,   41,   43,  118,   70,   85,   70,
  152,   58,   88,    3,   32,    5,   98,  104,   99,   41,
  105,  159,  116,   73,   98,   38,   99,  120,  138,  160,
  105,   52,   41,  128,  143,   33,  129,  129,   38,   60,
  171,   77,   77,   77,   15,   77,   68,   77,   39,  147,
   95,  149,   33,   38,   42,   61,   62,  166,  167,   77,
   77,   30,   77,   31,   73,   73,   73,   40,   73,   66,
   73,   96,   63,   44,   79,   64,   65,   47,   61,   62,
   61,   62,   73,   73,   48,   73,   34,   68,    7,   68,
    8,   68,   67,    9,   10,   63,   49,  100,   64,   65,
   64,   65,  101,   12,   50,   68,   68,   53,   68,   14,
   66,   89,   66,   51,   66,   65,   11,  136,   11,  137,
  124,   13,   90,   13,   55,   77,   94,   11,   66,   66,
   37,   66,   13,   67,   97,   67,   43,   67,    7,   64,
    8,  131,  132,    9,   10,  106,    1,    2,   73,  133,
  134,   67,   67,   12,   67,  114,   98,  115,   99,   14,
  126,    7,   11,    8,  135,  140,    9,   10,  117,  141,
   98,   68,   99,  112,   65,  111,   12,    6,    7,  142,
    8,   46,   14,    9,   10,   14,  150,  144,   98,  146,
   99,  148,   11,   12,   66,   46,   46,   13,   64,   14,
  155,  151,  156,  153,  157,  168,  158,  170,   16,   46,
  173,   20,    5,   19,  164,   48,   41,   67,   61,   62,
   54,   84,   89,   54,   61,   62,   61,   62,   36,   11,
   42,   18,  103,   90,   13,   63,   36,   36,   64,   65,
   65,   63,  103,   63,   64,   65,   64,   65,  127,  127,
   38,   59,   86,  161,  122,    0,   77,   77,    0,   77,
    0,    0,   77,   77,   64,    0,   77,   77,   77,   77,
   77,   77,   77,    0,    0,    0,   77,    0,   77,   73,
   73,    0,   73,    0,    0,   73,   73,   11,    0,   73,
   73,   73,   73,   73,   73,   73,    0,    0,    0,   73,
    0,   73,   68,   68,    0,   68,    0,    0,   68,   68,
   14,    0,   68,   68,   68,   68,   68,   68,   68,    0,
   46,    0,   68,   46,   68,   66,   66,    0,   66,   46,
    0,   66,   66,   16,    0,   66,   66,   66,   66,   66,
   66,   66,    0,    0,    0,   66,    0,   66,   67,   67,
    0,   67,    0,    0,   67,   67,   18,    0,   67,   67,
   67,   67,   67,   67,   67,    0,    0,    0,   67,    0,
   67,   65,   65,    0,   65,    0,    0,   65,   65,  107,
  108,  109,  110,   45,    0,   65,   65,   65,    0,    0,
    0,   65,    0,   65,   56,   64,   64,    0,   64,    0,
    0,   64,   64,    0,    0,    0,    0,    0,    0,   64,
   64,   64,   57,    0,    0,   64,    0,   64,   11,   11,
    0,   11,    0,   87,   11,   11,    0,    0,    0,    0,
    0,    0,   11,   11,   11,    0,    0,    0,   11,    0,
   11,   14,   14,    0,   14,    0,  154,   14,   14,    0,
    0,    0,    0,    0,    0,   14,   14,   14,    0,    0,
    0,   14,    0,   14,   16,   16,    0,   16,    0,  162,
   16,   16,    0,    0,    0,    0,    0,    0,   16,   16,
   16,    0,    0,    0,   16,    0,   16,   18,   18,    0,
   18,    0,  163,   18,   18,    0,    0,    0,    0,  172,
    0,   18,   18,   18,    0,    0,    0,   18,    0,   18,
  165,    0,    0,    0,    6,    7,    0,    8,    0,    0,
    9,   10,    0,    0,    0,    6,    7,    0,    8,   11,
   12,    9,   10,  169,   13,    0,   14,    0,    0,    0,
   11,   12,    0,    6,    7,   13,    8,   14,    0,    9,
   10,    0,    0,    0,    6,    7,    0,    8,   11,   12,
    9,   10,    0,   13,    0,   14,    0,   82,    0,   11,
   12,    0,    0,    0,   13,    0,   14,    6,    7,    0,
    8,    0,    0,    9,   10,    0,    0,    0,    0,    0,
    0,    0,   11,   12,    0,    0,    0,   13,    0,   14,
    6,    7,    0,    8,    0,    0,    9,   10,    0,  119,
    0,    0,    0,    0,    0,   11,   12,    0,    0,    0,
   13,    0,   14,    6,    7,    0,    8,    0,    0,    9,
   10,    7,    0,    8,    0,    0,    9,   10,   11,   12,
    0,    6,    7,   13,    8,   14,   12,    9,   10,    0,
    0,    0,   14,    0,    0,    0,   11,   12,    0,    0,
    0,   13,    0,   14,    6,    7,   82,    8,    0,    0,
    9,   10,    0,    0,    0,    0,    0,   82,  119,   11,
   12,    0,    0,    0,   13,  119,   14,   66,   66,   66,
   66,   66,    0,   66,   66,    0,   82,  119,   67,   91,
   77,   80,   81,    0,   84,    0,    6,    7,    0,    8,
    0,  102,    9,   10,    0,    0,    0,    0,    0,    0,
    0,   11,   12,    0,    0,    0,   13,    0,   14,   91,
  123,  125,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   91,   66,   66,
   66,   66,   66,    0,   66,    0,   66,    0,    0,  130,
    0,    0,    0,    0,   66,  145,    0,    0,   91,    0,
   91,    0,    0,    0,    0,  139,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   77,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   41,   40,   44,   40,  125,   40,   41,   40,
  140,   40,   40,  123,  257,  123,   43,   41,   45,   59,
   44,  151,   41,    0,   43,   40,   45,   41,  106,  125,
   44,  257,  123,   41,   41,  278,   44,   44,   44,   44,
  170,   41,   42,   43,  123,   45,    0,   47,   40,  127,
  257,  129,  278,   59,  265,  257,  258,  260,  261,   59,
   60,    3,   62,    5,   41,   42,   43,   40,   45,    0,
   47,  278,  274,   15,  276,  277,  278,   59,  257,  258,
  257,  258,   59,   60,   59,   62,    6,   41,  257,   43,
  259,   45,    0,  262,  263,  274,   59,   42,  277,  278,
  277,  278,   47,  272,   59,   59,   60,   27,   62,  278,
   41,  264,   43,   59,   45,    0,  271,  103,  271,  105,
  264,  276,  275,  276,   59,  125,  278,  271,   59,   60,
   40,   62,  276,   41,   40,   43,   40,   45,  257,    0,
  259,   98,   99,  262,  263,  270,  256,  257,  125,  100,
  101,   59,   60,  272,   62,   41,   43,   41,   45,  278,
  257,  257,    0,  259,   41,  123,  262,  263,   41,  273,
   43,  125,   45,   60,   59,   62,  272,  256,  257,  123,
  259,   16,  278,  262,  263,    0,   41,  257,   43,  257,
   45,  123,  271,  272,  125,   30,   31,  276,   59,  278,
  142,  123,  123,   40,  257,   41,  148,  123,    0,   44,
  261,   59,    0,   59,  156,  270,  256,  125,  257,  258,
   41,  270,  264,   27,  257,  258,  257,  258,  265,  271,
  265,    0,  256,  275,  276,  274,  265,  265,  277,  278,
  125,  274,  256,  274,  277,  278,  277,  278,  256,  256,
  256,  256,   43,  153,   88,   -1,  256,  257,   -1,  259,
   -1,   -1,  262,  263,  125,   -1,  266,  267,  268,  269,
  270,  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,
  257,   -1,  259,   -1,   -1,  262,  263,  125,   -1,  266,
  267,  268,  269,  270,  271,  272,   -1,   -1,   -1,  276,
   -1,  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,
  125,   -1,  266,  267,  268,  269,  270,  271,  272,   -1,
  155,   -1,  276,  158,  278,  256,  257,   -1,  259,  164,
   -1,  262,  263,  125,   -1,  266,  267,  268,  269,  270,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,  125,   -1,  266,  267,
  268,  269,  270,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,  266,
  267,  268,  269,  125,   -1,  270,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  125,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,  270,
  271,  272,  125,   -1,   -1,  276,   -1,  278,  256,  257,
   -1,  259,   -1,  125,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,  256,  257,   -1,  259,   -1,  125,  262,  263,   -1,
   -1,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  256,  257,   -1,  259,   -1,  125,
  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,   -1,
  259,   -1,  125,  262,  263,   -1,   -1,   -1,   -1,  125,
   -1,  270,  271,  272,   -1,   -1,   -1,  276,   -1,  278,
  125,   -1,   -1,   -1,  256,  257,   -1,  259,   -1,   -1,
  262,  263,   -1,   -1,   -1,  256,  257,   -1,  259,  271,
  272,  262,  263,  125,  276,   -1,  278,   -1,   -1,   -1,
  271,  272,   -1,  256,  257,  276,  259,  278,   -1,  262,
  263,   -1,   -1,   -1,  256,  257,   -1,  259,  271,  272,
  262,  263,   -1,  276,   -1,  278,   -1,   41,   -1,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,   -1,
  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,
  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,   83,
   -1,   -1,   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,
  276,   -1,  278,  256,  257,   -1,  259,   -1,   -1,  262,
  263,  257,   -1,  259,   -1,   -1,  262,  263,  271,  272,
   -1,  256,  257,  276,  259,  278,  272,  262,  263,   -1,
   -1,   -1,  278,   -1,   -1,   -1,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  256,  257,  140,  259,   -1,   -1,
  262,  263,   -1,   -1,   -1,   -1,   -1,  151,  152,  271,
  272,   -1,   -1,   -1,  276,  159,  278,   36,   37,   38,
   39,   40,   -1,   42,   43,   -1,  170,  171,   36,   58,
   38,   39,   40,   -1,   42,   -1,  256,  257,   -1,  259,
   -1,   70,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,   88,
   89,   90,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  106,   97,   98,
   99,  100,  101,   -1,  103,   -1,  105,   -1,   -1,   97,
   -1,   -1,   -1,   -1,  113,  124,   -1,   -1,  127,   -1,
  129,   -1,   -1,   -1,   -1,  113,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  153,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  153,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=278;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variable ';'",
"sentencia_declarativa : declaracion_funcion",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : control",
"sentencia_ejecutable : llamada_funcion ';'",
"sentencia_ejecutable : llamada_funcion",
"sentencia_ejecutable : print ';'",
"sentencia_ejecutable : print",
"sentencia_ejecutable : retorno ';'",
"sentencia_ejecutable : retorno",
"declaracion_variable : tipo lista_identificadores",
"declaracion_variable : error lista_identificadores",
"declaracion_variable : tipo asignacion",
"declaracion_variable : error asignacion",
"declaracion_funcion : tipo ID '(' lista_parametros_formales ')' '{' lista_sentencias '}'",
"declaracion_funcion : tipo ID '(' ')' '{' lista_sentencias '}'",
"declaracion_funcion : error ID '(' lista_parametros_formales ')' '{' lista_sentencias '}'",
"declaracion_funcion : tipo ID '(' lista_parametros_formales ')' '{' '}'",
"declaracion_funcion : tipo ID '(' ')' '{' '}'",
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
"lista_identificadores : IDCOMP",
"lista_identificadores : lista_identificadores ',' IDCOMP",
"lista_identificadores : lista_identificadores error IDCOMP",
"lista_identificadores : ID",
"lista_identificadores : lista_identificadores ',' ID",
"parametro_formal : CR tipo ID",
"parametro_formal : tipo ID",
"parametro_formal : LAMBDA tipo ID",
"parametro_formal : CR LAMBDA tipo ID",
"parametro_real_compuesto : parametro_real FLECHA parametro_formal",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF ';'",
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
};

//#line 215 "parser_test.y"
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
//#line 512 "Parser.java"
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
{ System.out.println("Falta apertura del programa. Linea: " + al.getLine()); }
break;
case 5:
//#line 31 "parser_test.y"
{ System.out.println("Falta apertura del programa. Linea: " + al.getLine()); }
break;
case 11:
//#line 47 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 14:
//#line 50 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 16:
//#line 52 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 17:
//#line 53 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 18:
//#line 54 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 20:
//#line 59 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 21:
//#line 60 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 22:
//#line 61 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 23:
//#line 67 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 24:
//#line 70 "parser_test.y"
{ System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 25:
//#line 73 "parser_test.y"
{ System.out.println("Tipo de funcion invalido/ausente. Linea: " + al.getLine()); }
break;
case 26:
//#line 76 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine()); }
break;
case 27:
//#line 79 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine());
         System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 34:
//#line 98 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 37:
//#line 104 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 38:
//#line 108 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 39:
//#line 109 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 40:
//#line 110 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 41:
//#line 111 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 42:
//#line 112 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 43:
//#line 116 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 44:
//#line 117 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 45:
//#line 118 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 46:
//#line 119 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 47:
//#line 123 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 52:
//#line 138 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 53:
//#line 140 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 54:
//#line 144 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 55:
//#line 149 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 64:
//#line 167 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 65:
//#line 168 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 69:
//#line 175 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 77:
//#line 189 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 78:
//#line 193 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 79:
//#line 194 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 80:
//#line 195 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 81:
//#line 196 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 82:
//#line 200 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 83:
//#line 201 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 84:
//#line 205 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 85:
//#line 209 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
//#line 846 "Parser.java"
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
