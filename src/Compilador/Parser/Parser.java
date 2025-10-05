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
    4,    4,    4,    4,    4,    4,    4,    4,    4,    5,
    5,    5,    5,    6,    6,    6,    6,    6,    1,    1,
   15,   15,   14,   14,   14,   17,   17,   17,   13,   13,
   13,   13,   13,   16,   16,   16,   16,   18,   19,   19,
    8,    8,   22,   22,   24,   23,   25,   25,   25,   25,
   25,   25,   12,   12,    7,    7,   20,   20,   20,   20,
   26,   26,   26,   27,   27,   27,   27,   27,    9,    9,
    9,    9,   10,   10,   21,   11,
};
final static short yylen[] = {                            2,
    4,    4,    3,    3,    3,    1,    1,    2,    1,    1,
    2,    1,    1,    2,    1,    2,    1,    2,    1,    2,
    2,    2,    2,    8,    7,    8,    7,    6,    1,    2,
    1,    2,    1,    3,    3,    1,    3,    3,    1,    3,
    3,    1,    3,    3,    2,    3,    4,    3,    1,    1,
    2,    2,    8,   12,    3,    8,    1,    1,    1,    1,
    1,    1,    1,    1,    3,    3,    3,    3,    1,    4,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    4,
    3,    3,    4,    4,    5,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,    0,   64,    0,    0,    0,   29,    6,    7,    0,
   10,    0,   13,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   23,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    4,   30,    8,   11,   14,   16,
   18,    0,   22,    0,   51,   52,    3,    2,    0,    0,
    0,    0,   75,    0,   76,    0,   77,    0,    0,   73,
    0,   82,    0,   36,    0,    0,   50,    0,    0,    0,
    0,    0,   31,    0,    0,   81,    0,    1,    0,    0,
    0,    0,    0,   33,   41,   43,   40,    0,    0,    0,
    0,    0,    0,    0,   80,    0,    0,   57,   58,   59,
   60,   61,   62,    0,    0,   83,   84,   86,    0,   32,
   79,    0,    0,    0,    0,    0,   45,    0,    0,    0,
    0,    0,    0,   71,   72,    0,   38,   37,   48,    0,
    0,    0,    0,    0,   46,    0,   44,   35,    0,   34,
   70,    0,    0,    0,   28,    0,    0,   47,    0,    0,
    0,    0,   25,   27,    0,   26,    0,   53,   56,   24,
    0,    0,    0,   54,
};
final static short yydgoto[] = {                          4,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   35,   93,   84,   94,   73,   74,   75,   76,
   77,   28,   29,   79,  114,   69,   70,
};
final static short yysindex[] = {                      -100,
 -105,  -95,  539,    0,  539, -227,  -40,  -11,   -8,   32,
    0,  -74,    0,  -31,  539,  363,    0,    0,    0,   26,
    0,   42,    0,   46,   55,   59, -202,   66,   69,  381,
  392,  -30, -174,    0,  -36, -241,  -34, -241, -154, -241,
  -75, -241,  -29,  410,    0,    0,    0,    0,    0,    0,
    0,  -26,    0,  -36,    0,    0,    0,    0, -131, -147,
 -201,  108,    0,  111,    0,  119,    0,  135,   15,    0,
 -182,    0,  -39,    0, -107,  135,    0,  308,  128,  129,
   67,   76,    0,  300,  135,    0,  -10,    0,  -38, -182,
 -196,  -78,   -3,    0,    0,    0,    0, -241, -238, -238,
 -238, -238,  144,  -27,    0,  -27, -131,    0,    0,    0,
    0,    0,    0, -241,   68,    0,    0,    0,  -84,    0,
    0,   71,    9,  -67, -182,  -64,    0, -131,   77, -131,
   96,   15,   15,    0,    0,   78,    0,    0,    0,  135,
  -75,  164,  428,   82,    0,  -50,    0,    0,  539,    0,
    0,  -75,  352, -241,    0,  439,  457,    0,  480,  -75,
 -239,  169,    0,    0,  503,    0,   88,    0,    0,    0,
  -75,  399,  -49,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  289,
    0,  196,    0,  219,  242,  266,    0,    0,    0,    0,
    0,  116,  127,    0,  322,    0,    0,    0,    0,    0,
    0,    0,    0,  213,    0,    0,    0,    0,    0,    0,
    0,  116,    0,  345,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    0,   24,    0,  150,   47,    0,
    0,    0,    0,    0,    0,  -55,    0,    0,    0,    0,
    0,    0,    0,    0,  173,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,   93,    0,    0,    0,    0,    0,    0,  175,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -48,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   49,   43,    0,   -6,    0,    0,   52,    0,  746,    0,
    0,  702,  194,  138, -126, -103,  193,   -9,    0,   60,
    0,    0,    0,  100,    0,  -18,   41,
};
final static int YYTABLESIZE=900;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   78,  105,  122,  139,  106,   71,   72,   61,   43,   59,
   71,   86,   71,   89,  153,   62,   63,    5,   62,   63,
  167,  168,    3,   74,  148,  160,  150,   15,   38,   32,
  121,   39,   64,  106,   83,   65,   66,  129,   65,   66,
  130,   78,   78,   78,  172,   78,   69,   78,   41,  144,
   33,   30,  130,   31,   52,   96,  101,   34,   46,   78,
   78,  102,   78,   44,   74,   74,   74,  125,   74,   67,
   74,   40,   46,   46,   11,   33,   97,  120,   53,   13,
  132,  133,   74,   74,   47,   74,   46,   69,   11,   69,
   42,   69,   68,   13,  137,   68,  138,   78,   81,   82,
   48,   85,   62,   63,   49,   69,   69,  117,   69,   99,
   67,  100,   67,   50,   67,   42,  118,   51,   99,   64,
  100,   80,   65,   66,   55,   78,   39,   56,   67,   67,
   95,   67,   90,   68,   83,   68,  151,   68,   99,   11,
  100,  134,  135,   91,   13,   83,  120,   37,   74,   66,
   98,   68,   68,  120,   68,    1,    2,  131,   43,   42,
    6,    7,  107,    8,   83,  120,    9,   10,  115,  116,
   39,   69,   65,  140,   42,   11,   12,   99,  127,  100,
   13,    7,   14,    8,  136,   39,    9,   10,  142,  145,
  141,  156,  147,  143,   67,   12,   12,  159,   46,  149,
  152,   46,   14,  154,  157,  165,  158,   46,   66,  169,
  171,  174,    5,   78,   49,   55,  104,   68,   15,   60,
   54,   85,   62,   63,   36,   90,  123,   62,   63,   62,
   63,   65,   11,   42,   36,   87,   91,   13,   36,   64,
   42,   17,   65,   66,   64,  104,   64,   65,   66,   65,
   66,   39,  128,  162,    0,    0,   78,   78,    0,   78,
    0,    0,   78,   78,  128,   19,   78,   78,   78,   78,
   78,   78,   78,    0,   66,    0,   78,    0,   78,   74,
   74,    0,   74,    0,    0,   74,   74,    0,    9,   74,
   74,   74,   74,   74,   74,   74,    0,   65,    0,   74,
    0,   74,   69,   69,    0,   69,    0,    0,   69,   69,
    0,    0,   69,   69,   69,   69,   69,   69,   69,    0,
   12,   21,   69,    0,   69,   67,   67,    0,   67,    0,
    0,   67,   67,    0,    0,   67,   67,   67,   67,   67,
   67,   67,    0,   15,   20,   67,    0,   67,   68,   68,
   99,   68,  100,    0,   68,   68,    0,    0,   68,   68,
   68,   68,   68,   68,   68,    0,   17,  113,   68,  112,
   68,   42,   42,    0,   42,    0,    0,   42,   42,    0,
   21,    0,   39,   39,    0,   39,   42,   42,   39,   39,
   19,   42,    0,   42,    0,    0,    0,   39,   39,    0,
    0,    0,   39,   20,   39,   66,   66,    0,   66,    0,
    0,   66,   66,    9,    0,    0,    0,    0,    0,   66,
   66,   66,    0,    0,  119,   66,    0,   66,   65,   65,
    0,   65,    0,    0,   65,   65,    0,    0,    0,    0,
    0,    0,   65,   65,   65,    0,   21,    0,   65,    0,
   65,   12,   12,    0,   12,    0,    0,   12,   12,    0,
    0,    0,    0,    0,    0,   12,   12,   12,    0,   20,
    0,   12,    0,   12,   15,   15,  161,   15,    0,    0,
   15,   15,    0,    0,    0,    0,    0,   45,   15,   15,
   15,    0,    0,    0,   15,    0,   15,   17,   17,    0,
   17,    0,    0,   17,   17,   57,    0,    0,    0,    0,
    0,   17,   17,   17,    0,    0,   58,   17,    0,   17,
    0,   19,   19,  173,   19,    0,    0,   19,   19,    0,
    0,    0,    0,    0,   88,   19,   19,   19,    0,    0,
    0,   19,    0,   19,    9,    9,    0,    9,    0,    0,
    9,    9,  155,    0,    0,    0,    7,    0,    8,    9,
    9,    9,   10,  163,    9,    0,    9,    0,    0,    0,
    0,   12,    0,  108,  109,  110,  111,   14,   21,    0,
   21,  164,    0,   21,   21,    0,    0,    0,    0,    0,
    0,    0,   21,   21,    0,    0,    0,   21,    0,   21,
    0,   20,    0,   20,  166,    0,   20,   20,    7,    0,
    8,    0,    0,    9,   10,   20,   20,    0,    6,    7,
   20,    8,   20,   12,    9,   10,    0,  170,    0,   14,
    0,    0,    0,   11,   12,    0,    6,    7,   13,    8,
   14,    0,    9,   10,    0,    0,    0,    6,    7,    0,
    8,   11,   12,    9,   10,    7,   13,    8,   14,    0,
    9,   10,   11,   12,    0,    6,    7,   13,    8,   14,
   12,    9,   10,    0,    0,    0,   14,    0,    0,    0,
   11,   12,    0,    6,    7,   13,    8,   14,    0,    9,
   10,    0,    0,    0,    6,    7,    0,    8,   11,   12,
    9,   10,    0,   13,    0,   14,    0,    0,    0,   11,
   12,    0,    6,    7,   13,    8,   14,    0,    9,   10,
    0,    0,    0,    0,    0,    0,    0,   11,   12,    0,
    0,    0,   13,    0,   14,    6,    7,    0,    8,    0,
    0,    9,   10,    0,    0,    0,    0,    0,    0,    0,
   11,   12,    0,    0,    0,   13,    0,   14,    6,    7,
   92,    8,    0,    0,    9,   10,    0,    0,    0,    0,
    0,    0,  103,   11,   12,    0,    0,    0,   13,    0,
   14,   67,   67,   67,   67,   67,    0,   67,   67,    0,
   92,  124,  126,    0,    6,    7,    0,    8,    0,    0,
    9,   10,    0,    0,    0,    0,    0,    0,   92,   11,
   12,    0,    0,    0,   13,    0,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  146,    0,    0,   92,
    0,   92,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,   67,   67,   67,   67,    0,   67,
    0,   67,    0,    0,    0,    0,    0,    0,    0,   67,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   67,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,   41,  107,   44,   40,   41,   44,   40,   40,
   40,   41,   40,   40,  141,  257,  258,  123,  257,  258,
  260,  261,  123,    0,  128,  152,  130,  123,   40,  257,
   41,   40,  274,   44,   41,  277,  278,   41,  277,  278,
   44,   41,   42,   43,  171,   45,    0,   47,  123,   41,
  278,    3,   44,    5,  257,  257,   42,    6,   16,   59,
   60,   47,   62,   15,   41,   42,   43,  264,   45,    0,
   47,   40,   30,   31,  271,  278,  278,   84,   27,  276,
   99,  100,   59,   60,   59,   62,   44,   41,  271,   43,
  265,   45,    0,  276,  104,   36,  106,   38,   39,   40,
   59,   42,  257,  258,   59,   59,   60,   41,   62,   43,
   41,   45,   43,   59,   45,    0,   41,   59,   43,  274,
   45,  276,  277,  278,   59,  125,    0,   59,   59,   60,
  278,   62,  264,   41,  141,   43,   41,   45,   43,  271,
   45,  101,  102,  275,  276,  152,  153,   40,  125,    0,
   40,   59,   60,  160,   62,  256,  257,   98,   40,   44,
  256,  257,  270,  259,  171,  172,  262,  263,   41,   41,
   44,  125,    0,  114,   59,  271,  272,   43,  257,   45,
  276,  257,  278,  259,   41,   59,  262,  263,  273,  257,
  123,  143,  257,  123,  125,    0,  272,  149,  156,  123,
  123,  159,  278,   40,  123,  157,  257,  165,   59,   41,
  123,  261,    0,  154,  270,   41,  256,  125,    0,  256,
   27,  270,  257,  258,  265,  264,   89,  257,  258,  257,
  258,   59,  271,  265,  265,   43,  275,  276,  265,  274,
  125,    0,  277,  278,  274,  256,  274,  277,  278,  277,
  278,  125,  256,  154,   -1,   -1,  256,  257,   -1,  259,
   -1,   -1,  262,  263,  256,    0,  266,  267,  268,  269,
  270,  271,  272,   -1,  125,   -1,  276,   -1,  278,  256,
  257,   -1,  259,   -1,   -1,  262,  263,   -1,    0,  266,
  267,  268,  269,  270,  271,  272,   -1,  125,   -1,  276,
   -1,  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,  271,  272,   -1,
  125,    0,  276,   -1,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   -1,  266,  267,  268,  269,  270,
  271,  272,   -1,  125,    0,  276,   -1,  278,  256,  257,
   43,  259,   45,   -1,  262,  263,   -1,   -1,  266,  267,
  268,  269,  270,  271,  272,   -1,  125,   60,  276,   62,
  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   59,   -1,  256,  257,   -1,  259,  271,  272,  262,  263,
  125,  276,   -1,  278,   -1,   -1,   -1,  271,  272,   -1,
   -1,   -1,  276,   59,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,  125,   -1,   -1,   -1,   -1,   -1,  270,
  271,  272,   -1,   -1,  125,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  125,   -1,  276,   -1,
  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  125,
   -1,  276,   -1,  278,  256,  257,  125,  259,   -1,   -1,
  262,  263,   -1,   -1,   -1,   -1,   -1,  125,  270,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,   -1,
  259,   -1,   -1,  262,  263,  125,   -1,   -1,   -1,   -1,
   -1,  270,  271,  272,   -1,   -1,  125,  276,   -1,  278,
   -1,  256,  257,  125,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,   -1,   -1,  125,  270,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  256,  257,   -1,  259,   -1,   -1,
  262,  263,  125,   -1,   -1,   -1,  257,   -1,  259,  271,
  272,  262,  263,  125,  276,   -1,  278,   -1,   -1,   -1,
   -1,  272,   -1,  266,  267,  268,  269,  278,  257,   -1,
  259,  125,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,
   -1,  257,   -1,  259,  125,   -1,  262,  263,  257,   -1,
  259,   -1,   -1,  262,  263,  271,  272,   -1,  256,  257,
  276,  259,  278,  272,  262,  263,   -1,  125,   -1,  278,
   -1,   -1,   -1,  271,  272,   -1,  256,  257,  276,  259,
  278,   -1,  262,  263,   -1,   -1,   -1,  256,  257,   -1,
  259,  271,  272,  262,  263,  257,  276,  259,  278,   -1,
  262,  263,  271,  272,   -1,  256,  257,  276,  259,  278,
  272,  262,  263,   -1,   -1,   -1,  278,   -1,   -1,   -1,
  271,  272,   -1,  256,  257,  276,  259,  278,   -1,  262,
  263,   -1,   -1,   -1,  256,  257,   -1,  259,  271,  272,
  262,  263,   -1,  276,   -1,  278,   -1,   -1,   -1,  271,
  272,   -1,  256,  257,  276,  259,  278,   -1,  262,  263,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,   -1,
   -1,   -1,  276,   -1,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,
   59,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,   71,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,   36,   37,   38,   39,   40,   -1,   42,   43,   -1,
   89,   90,   91,   -1,  256,  257,   -1,  259,   -1,   -1,
  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,  107,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,  128,
   -1,  130,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   98,   99,  100,  101,  102,   -1,  104,
   -1,  106,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  114,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  154,
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
"sentencia_declarativa : declaracion_variable",
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
"control : do_until ';'",
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
//#line 529 "Parser.java"
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
case 9:
//#line 41 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 12:
//#line 47 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 15:
//#line 50 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 17:
//#line 52 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 18:
//#line 53 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 19:
//#line 54 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 21:
//#line 59 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 22:
//#line 60 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 23:
//#line 61 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 24:
//#line 67 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 25:
//#line 70 "parser_test.y"
{ System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 26:
//#line 73 "parser_test.y"
{ System.out.println("Tipo de funcion invalido/ausente. Linea: " + al.getLine()); }
break;
case 27:
//#line 76 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine()); }
break;
case 28:
//#line 79 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine());
         System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 35:
//#line 98 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 38:
//#line 104 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 39:
//#line 108 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 40:
//#line 109 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 41:
//#line 110 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 42:
//#line 111 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 43:
//#line 112 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 44:
//#line 116 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 45:
//#line 117 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 46:
//#line 118 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 47:
//#line 119 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 48:
//#line 123 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 53:
//#line 138 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 54:
//#line 140 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 55:
//#line 144 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 56:
//#line 149 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 65:
//#line 167 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 66:
//#line 168 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 70:
//#line 175 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 78:
//#line 189 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 79:
//#line 193 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 80:
//#line 194 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 81:
//#line 195 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 82:
//#line 196 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 83:
//#line 200 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 84:
//#line 201 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 85:
//#line 205 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 86:
//#line 209 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
//#line 867 "Parser.java"
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
