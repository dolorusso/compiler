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
    0,    0,    0,    0,    0,    2,    2,    3,    3,    3,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    6,    6,    6,    6,   14,   14,   15,   15,
    1,    1,    1,   17,   17,   16,   16,   16,   19,   19,
   19,   18,   18,   18,   18,   18,   18,   18,   18,   18,
    5,    5,    5,    5,    5,    5,   20,   21,   21,    8,
    8,   24,   24,   26,   26,   26,   27,   27,   27,   27,
   28,   25,   29,   29,   29,   29,   29,   29,   13,   13,
    7,    7,   22,   22,   22,   22,   30,   30,   30,   31,
   31,   31,   31,   31,    9,    9,    9,   10,   10,   23,
   11,   12,   12,   12,   32,   32,   33,   33,   34,   34,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    1,    2,    2,    1,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    4,    3,    3,    4,    3,    3,    3,    2,
    1,    2,    2,    1,    2,    1,    3,    2,    1,    3,
    2,    3,    2,    3,    2,    1,    2,    3,    2,    2,
    2,    3,    2,    1,    2,    3,    3,    1,    1,    1,
    1,    4,    6,    3,    2,    2,    3,    1,    2,    2,
    3,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    3,    3,    3,    3,    1,    4,    3,    3,    1,    1,
    1,    1,    1,    1,    4,    4,    4,    4,    4,    4,
    4,    3,    4,    3,    1,    3,    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,    0,
   80,    0,    0,    0,   31,    6,    7,    0,   10,    0,
    0,    0,    0,    0,    0,    0,   60,   61,    0,    0,
   33,    0,    0,    0,    0,   91,    0,   92,    0,    0,
   93,    0,    0,    0,    0,   89,    0,    0,    0,    0,
   34,    0,    0,    0,    0,    0,    3,   32,    9,   53,
    8,    0,   17,   11,   18,   12,   19,   13,   20,   14,
   21,   15,   22,   16,    0,   51,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,
   36,    0,   39,    0,    0,   59,    0,   24,    0,    0,
    0,   73,   74,   75,   76,   77,   78,    0,    0,    0,
    0,   65,    0,    0,    0,    0,    0,    0,   70,   35,
    0,    0,    0,    0,    1,   56,   52,    0,   46,    0,
   25,    0,  106,  104,  109,  110,    0,  108,   28,   47,
   49,   43,    0,    0,    0,   50,   45,   27,    0,   38,
   96,    0,   41,    0,   30,    0,    0,   64,    0,    0,
    0,    0,   62,   87,   88,   98,   99,  101,   67,   72,
   97,   95,   23,   26,    0,    0,   48,   44,   42,    0,
   37,   40,   57,   29,   86,    0,  107,  100,   63,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   41,   23,
   24,   25,   26,   34,   98,   90,   52,   91,   92,   93,
   94,   95,   96,   27,   28,   43,   53,   44,  110,   45,
   46,   29,  137,  138,
};
final static short yysindex[] = {                       -98,
  166,  200,    0,  -33,  -39,  -17,    9,   33,    0,  -78,
    0,  -11,  200,  211,    0,    0,    0,  -23,    0,  -55,
  -52,  -47,  -46,  -37,    7,  -25,    0,    0,  -42,  222,
    0,   46,  -40,  -80,   51,    0,   53,    0,   72,   46,
    0,   66,  -78,   11,   61,    0,  -85,   46,   -9,  177,
    0,  280, -153,   46,   -7,  233,    0,    0,    0,    0,
    0, -209,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   92,    0,  291, -106,   79, -130,
 -212,    0,   42,  120,   51,  -88, -140, -166, -150,  123,
    0,  -35,    0,  -99,   42,    0,  189,    0,  -12,   46,
  142,    0,    0,    0,    0,    0,    0,   35,   35,   46,
 -146,    0,   35,   35,  149,   -6,  312,  287,    0,    0,
  -17,   42,  156,  -30,    0,    0,    0,  -80,    0,   70,
    0, -226,    0,    0,    0,    0,  159,    0,    0,    0,
    0,    0,  -51, -102,  171,    0,    0,    0, -119,    0,
    0,  -12,    0, -119,    0,  256,  390,    0,   61,   61,
   42,  -78,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  159, -226,    0,    0,    0,  -78,
    0,    0,    0,    0,    0,  -31,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    0,    0,    0,
    0,  -41,    0,  221,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   16,    0,    0,    0,   39,    0,
    0,    0,    0,    0,   59,    0,    0,    0,    0,    0,
    0, -127,    0,    0,    0,  258,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,    0,    0,    0,    0,    0,
    0,    0,    8,    0,  133,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   10,    0,    0,    0,    0,    0,
  154,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -62,    0,    0,
    0,   13,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   82,  106,
  129,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   20,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   25,    4,    0,   45,    0,    0,    0,    0,   40,    0,
    0,    0,  420,   38,  -54,    0,  237,  -60,  236,  -84,
    0,  482,    0,    0,    0,  176,  -27,  259,    0,   21,
   73,    0,  168,  134,
};
final static int YYTABLESIZE=603;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         88,
   33,   80,  105,   64,   88,  151,   66,  153,  152,   88,
  172,   68,   70,  152,   77,  111,   97,   58,   81,  105,
   62,   72,   40,  131,    2,   31,   30,   88,   55,  150,
   99,  135,   88,   58,  167,   61,  108,   56,  109,  153,
   22,   22,   97,  134,   50,  135,   54,  126,   47,   22,
  136,  112,   22,   22,   51,   55,   94,   94,   94,   58,
   94,   54,   94,   78,  136,   74,   82,  182,  127,   22,
   55,   81,   48,  173,   94,   94,  102,   94,  103,   90,
   90,   90,   22,   90,  108,   90,  109,   51,  181,   22,
   99,   22,  100,  183,   51,   22,  120,   90,   90,   85,
   90,   85,  113,   85,    9,  146,  147,  114,  108,   11,
  109,   55,  128,  162,  163,  141,  142,   85,   85,  121,
   85,  156,   83,  143,   83,  107,   83,  106,  159,  160,
    9,   77,   68,   68,  186,   11,   22,  129,   94,  132,
   83,   83,   68,   83,   86,   68,   84,  133,   84,  130,
   84,    9,  188,  178,  179,   87,   11,   22,    1,   58,
  139,   90,  120,  148,   84,   84,  149,   84,  140,   71,
  154,   35,   36,   46,   94,   94,   46,   94,   49,   94,
    6,   85,  158,    7,    8,  164,  165,   71,   37,  166,
  115,   38,   39,   10,  174,   22,  171,   69,   69,   12,
   63,   22,  176,   65,   83,  177,   51,   69,   67,   69,
   69,  180,   66,   79,  105,   84,   85,   36,   71,   22,
    5,   35,   36,   86,   51,   32,   35,   36,   84,  189,
    9,   75,   59,   37,   87,   11,   38,   39,   37,   35,
   36,   38,   39,   37,   35,   36,   38,   39,  123,   35,
   36,   71,   76,   54,   60,   32,   37,    4,   54,   38,
   39,   37,   73,   82,   38,   39,   37,   55,   81,   38,
   39,   94,   94,  102,   94,  103,   66,   94,   94,   58,
   54,   94,   94,   94,   94,   94,  118,   94,   13,   55,
  124,   35,   36,   94,   90,   90,  170,   90,  101,  175,
   90,   90,   35,   36,   90,   90,   90,   90,   90,  187,
   90,   38,   39,  155,   85,   85,   90,   85,    0,   37,
   85,   85,   38,   39,   85,   85,   85,   85,   85,    0,
   85,  102,  103,  104,  105,   57,   85,   83,   83,    0,
   83,    0,    0,   83,   83,    0,   82,   83,   83,   83,
   83,   83,  168,   83,  108,    0,  109,  125,    0,   83,
    0,   84,   84,    0,   84,    0,    0,   84,   84,    0,
    0,   84,   84,   84,   84,   84,    0,   84,    0,  129,
  184,    0,    0,   84,   71,   71,   86,   71,    0,   46,
   71,   71,    0,    9,    0,    0,   46,   87,   11,    0,
   71,    0,   94,   46,  119,    0,   71,   46,   46,   66,
   66,  169,   66,    0,    0,   66,   66,    0,    0,    0,
    0,    4,    5,    0,    6,   66,    0,    7,    8,    0,
  185,   66,  108,   49,  109,    6,    9,   10,    7,    8,
    0,   11,    0,   12,    4,    5,    0,    6,   10,    0,
    7,    8,   89,    0,   12,    4,    5,    0,    6,    9,
   10,    7,    8,    0,   11,    0,   12,    5,    0,    6,
    9,   10,    7,    8,    0,   11,    0,   12,    5,    0,
    6,    9,   10,    7,    8,    0,   11,   42,   12,    5,
    0,    6,    9,   10,    7,    8,   89,   11,    0,   12,
    0,    0,    0,    9,   10,    0,  144,  145,   11,   89,
   12,    0,    5,   83,    6,    0,    0,    7,    8,    0,
    0,   42,    0,    0,    0,    0,    9,   10,  116,  117,
    0,   11,    0,   12,    0,  122,   49,    0,    6,    0,
    0,    7,    8,   49,    0,    6,   84,  129,    7,    8,
    0,   10,    0,    0,   86,    0,    0,   12,   10,    0,
    0,    9,    0,    0,   12,   87,   11,    0,   89,    0,
    0,    0,    0,   89,    0,    0,    0,    0,    0,    0,
    0,  157,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  161,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   42,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   44,   44,   59,   40,   41,   59,   92,   44,   40,
   41,   59,   59,   44,   40,   43,  123,   14,   61,   61,
   44,   59,   40,   78,  123,   59,    2,   40,   40,   90,
   40,  258,   40,   30,   41,   59,   43,   13,   45,  124,
    1,    2,  123,  256,  123,  258,   44,  257,   40,   10,
  277,   41,   13,   14,   10,   44,   41,   42,   43,   56,
   45,   59,   47,   26,  277,   59,   59,  152,  278,   30,
   59,   59,   40,  128,   59,   60,   59,   62,   59,   41,
   42,   43,   43,   45,   43,   47,   45,   43,  149,   50,
   40,   52,   40,  154,   50,   56,   52,   59,   60,   41,
   62,   43,   42,   45,  271,  256,  257,   47,   43,  276,
   45,   40,   75,  260,  261,  256,  257,   59,   60,  273,
   62,   97,   41,  264,   43,   60,   45,   62,  108,  109,
  271,   40,  260,  261,  162,  276,   97,  257,  123,   61,
   59,   60,  270,   62,  264,  273,   41,  278,   43,  256,
   45,  271,  180,  256,  257,  275,  276,  118,  257,  156,
   41,  123,  118,   41,   59,   60,   44,   62,  257,   41,
  270,  257,  258,   41,   42,   43,   44,   45,  257,   47,
  259,  123,   41,  262,  263,  113,  114,   59,  274,   41,
  276,  277,  278,  272,  125,  156,   41,  260,  261,  278,
  256,  162,   44,  256,  123,  257,  162,  270,  256,  256,
  273,   41,   59,  256,  256,  256,  257,  258,  256,  180,
    0,  257,  258,  264,  180,  265,  257,  258,  123,  261,
  271,  257,  256,  274,  275,  276,  277,  278,  274,  257,
  258,  277,  278,  274,  257,  258,  277,  278,  256,  257,
  258,  123,  278,  265,  278,  265,  274,    0,  256,  277,
  278,  274,  256,  256,  277,  278,  274,  256,  256,  277,
  278,  256,  257,  256,  259,  256,  123,  262,  263,  270,
  278,  266,  267,  268,  269,  270,   50,  272,  123,  278,
   55,  257,  258,  278,  256,  257,  121,  259,   40,  132,
  262,  263,  257,  258,  266,  267,  268,  269,  270,  176,
  272,  277,  278,  125,  256,  257,  278,  259,   -1,  274,
  262,  263,  277,  278,  266,  267,  268,  269,  270,   -1,
  272,  266,  267,  268,  269,  125,  278,  256,  257,   -1,
  259,   -1,   -1,  262,  263,   -1,  125,  266,  267,  268,
  269,  270,   41,  272,   43,   -1,   45,  125,   -1,  278,
   -1,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   -1,  266,  267,  268,  269,  270,   -1,  272,   -1,  257,
  125,   -1,   -1,  278,  256,  257,  264,  259,   -1,  257,
  262,  263,   -1,  271,   -1,   -1,  264,  275,  276,   -1,
  272,   -1,  270,  271,  125,   -1,  278,  275,  276,  256,
  257,  125,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,
   -1,  256,  257,   -1,  259,  272,   -1,  262,  263,   -1,
   41,  278,   43,  257,   45,  259,  271,  272,  262,  263,
   -1,  276,   -1,  278,  256,  257,   -1,  259,  272,   -1,
  262,  263,   33,   -1,  278,  256,  257,   -1,  259,  271,
  272,  262,  263,   -1,  276,   -1,  278,  257,   -1,  259,
  271,  272,  262,  263,   -1,  276,   -1,  278,  257,   -1,
  259,  271,  272,  262,  263,   -1,  276,    6,  278,  257,
   -1,  259,  271,  272,  262,  263,   77,  276,   -1,  278,
   -1,   -1,   -1,  271,  272,   -1,   87,   88,  276,   90,
  278,   -1,  257,   32,  259,   -1,   -1,  262,  263,   -1,
   -1,   40,   -1,   -1,   -1,   -1,  271,  272,   47,   48,
   -1,  276,   -1,  278,   -1,   54,  257,   -1,  259,   -1,
   -1,  262,  263,  257,   -1,  259,  256,  257,  262,  263,
   -1,  272,   -1,   -1,  264,   -1,   -1,  278,  272,   -1,
   -1,  271,   -1,   -1,  278,  275,  276,   -1,  149,   -1,
   -1,   -1,   -1,  154,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  100,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  110,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  121,
};
}
final static short YYFINAL=3;
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
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : ID '{' lista_sentencias",
"programa : ID lista_sentencias",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia_declarativa : lista_identificadores ';'",
"sentencia_declarativa : lista_identificadores error",
"sentencia_declarativa : declaracion_funcion",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : control ';'",
"sentencia_ejecutable : llamada_funcion ';'",
"sentencia_ejecutable : print ';'",
"sentencia_ejecutable : retorno ';'",
"sentencia_ejecutable : asignacion_multiple ';'",
"sentencia_ejecutable : asignacion error",
"sentencia_ejecutable : control error",
"sentencia_ejecutable : llamada_funcion error",
"sentencia_ejecutable : print error",
"sentencia_ejecutable : retorno error",
"sentencia_ejecutable : asignacion_multiple error",
"declaracion_funcion : tipo ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : ID parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : tipo parametros_formales_opt cuerpo_funcion_opt",
"declaracion_funcion : tipo parametros_formales_opt error '}'",
"parametros_formales_opt : '(' lista_parametros_formales ')'",
"parametros_formales_opt : '(' error ')'",
"cuerpo_funcion_opt : '{' lista_sentencias '}'",
"cuerpo_funcion_opt : '{' '}'",
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
"parametro_real_compuesto : parametro_real FLECHA parametro_formal",
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
"llamada_funcion : IDCOMP '(' error ')'",
"print : PRINT '(' STRING ')'",
"print : PRINT '(' expresion ')'",
"lambda : '(' tipo ')' cuerpo_opt",
"retorno : RETURN '(' expresion ')'",
"asignacion_multiple : ids '=' lista_constantes",
"asignacion_multiple : ids error '=' lista_constantes",
"asignacion_multiple : ids '=' error",
"ids : IDCOMP",
"ids : ids ',' IDCOMP",
"lista_constantes : lista_constantes ',' constante",
"lista_constantes : constante",
"constante : CTEL",
"constante : CTEF",
};

//#line 252 "parser_test.y"
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
//#line 504 "Parser.java"
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
{ System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
break;
case 3:
//#line 24 "parser_test.y"
{ System.out.println("Falta apertura del programa '{'. Linea: " + al.getLine()); }
break;
case 4:
//#line 27 "parser_test.y"
{ System.out.println("Falta cierre del programa '}'. Linea: " + al.getLine()); }
break;
case 5:
//#line 28 "parser_test.y"
{ System.out.println("Faltan delimitadores de programa '{' y '}' '}'. Linea: " + al.getLine()); }
break;
case 9:
//#line 38 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 15:
//#line 47 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 17:
//#line 49 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ; en asignacion. Linea: " + al.getLine()); }
break;
case 18:
//#line 50 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 19:
//#line 51 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 20:
//#line 52 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 21:
//#line 53 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 22:
//#line 54 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 23:
//#line 59 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 24:
//#line 61 "parser_test.y"
{ System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
break;
case 25:
//#line 63 "parser_test.y"
{ System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
break;
case 28:
//#line 69 "parser_test.y"
{ System.out.println("Parametros formales invalidos. Linea: " + al.getLine()); }
break;
case 30:
//#line 74 "parser_test.y"
{ System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
break;
case 33:
//#line 81 "parser_test.y"
{ System.out.println("PROBLEMON. Linea: " + al.getLine()); }
break;
case 41:
//#line 98 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 42:
//#line 102 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 43:
//#line 103 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 44:
//#line 104 "parser_test.y"
{ System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
break;
case 45:
//#line 105 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 46:
//#line 106 "parser_test.y"
{ System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
break;
case 47:
//#line 107 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 48:
//#line 108 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 49:
//#line 109 "parser_test.y"
{ System.out.println("Se espera un tipo pero se encontro .... Linea: " + al.getLine()); }
break;
case 50:
//#line 110 "parser_test.y"
{ System.out.println("Se espera un Identifier pero se encontro .... Linea: " + al.getLine()); }
break;
case 51:
//#line 115 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 52:
//#line 116 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 53:
//#line 117 "parser_test.y"
{ System.out.println("Falta separador de variable ','. Linea: " + al.getLine()); }
break;
case 54:
//#line 118 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 55:
//#line 119 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 56:
//#line 120 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 57:
//#line 126 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 62:
//#line 142 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 63:
//#line 144 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 71:
//#line 162 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 72:
//#line 167 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 81:
//#line 185 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 82:
//#line 186 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 86:
//#line 193 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 94:
//#line 207 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 95:
//#line 211 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 96:
//#line 212 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 97:
//#line 213 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 98:
//#line 217 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 99:
//#line 218 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 100:
//#line 222 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 101:
//#line 226 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 102:
//#line 230 "parser_test.y"
{ System.out.println("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 103:
//#line 231 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
case 104:
//#line 232 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: xd,. Linea: " + al.getLine());}
break;
//#line 869 "Parser.java"
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
