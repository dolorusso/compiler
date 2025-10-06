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
   18,   20,   22,   22,    8,    8,   25,   25,   27,   27,
   27,   28,   28,   28,   28,   29,   26,   30,   30,   30,
   30,   30,   30,   13,   13,    7,    7,   23,   23,   23,
   23,   31,   31,   31,   32,   32,   32,   32,   32,    9,
    9,    9,    9,   10,   10,   24,   11,   12,   12,   12,
   12,   33,   33,   34,   34,   35,   35,
};
final static short yylen[] = {                            2,
    4,    4,    3,    3,    3,    2,    1,    2,    1,    2,
    1,    1,    1,    1,    1,    1,    1,    1,    4,    3,
    3,    3,    2,    3,    2,    1,    2,    1,    2,    1,
    3,    3,    1,    3,    3,    1,    2,    2,    2,    3,
    1,    2,    3,    3,    2,    2,    1,    2,    1,    2,
    3,    3,    1,    1,    1,    1,    4,    6,    3,    2,
    2,    3,    1,    2,    2,    3,    8,    1,    1,    1,
    1,    1,    1,    1,    1,    3,    3,    3,    3,    1,
    4,    3,    3,    1,    1,    1,    1,    1,    1,    4,
    4,    3,    3,    4,    4,    5,    4,    3,    4,    3,
    5,    1,    3,    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   74,    0,   75,    0,    0,    0,   26,    7,    0,    0,
   12,   13,   14,   15,   16,   17,   18,    0,    0,   55,
   56,    0,    0,    0,    0,    0,   38,    0,    0,    0,
    0,    0,   86,    0,   87,    0,    0,   88,    0,    0,
    0,    0,   84,    0,    0,    0,    0,    0,    0,    4,
   27,    8,   10,    0,    0,    0,   37,    0,    0,    0,
    0,    3,    2,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   30,    0,   33,    0,    0,   54,    0,   20,
    0,    0,    0,   68,   69,   70,   71,   72,   73,    0,
    0,    0,    0,    0,    0,   28,    0,    0,   60,    0,
    0,    0,    0,    0,    0,    0,   92,    0,    1,    0,
   49,   23,   21,   43,   40,  103,  100,  106,  107,    0,
  105,    0,    0,   50,   45,    0,    0,    0,   48,    0,
   22,    0,    0,   91,    0,    0,   25,    0,   93,    0,
   59,    0,    0,    0,  102,    0,   65,   29,    0,   57,
   82,   83,   94,   95,   97,    0,   90,   19,    0,    0,
   51,   44,    0,   32,   31,   35,   34,   52,   24,   81,
   62,    0,    0,  104,    0,   58,    0,    0,    0,   67,
};
final static short yydgoto[] = {                          4,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   28,   41,   90,   82,  107,   83,   84,   85,
   29,   86,   87,   88,   30,   31,   50,  108,   51,  102,
   52,   53,   32,  130,  131,
};
final static short yysindex[] = {                      -112,
 -103,  -65,  650,    0,  650, -166,  -34,  295,    9,   41,
    0,  -84,    0,  -33,  650,  426,    0,    0,   36,   51,
    0,    0,    0,    0,    0,    0,    0,  -40,   75,    0,
    0,   64,  497,  520, -151, -126,    0,   74,  -69,  -28,
   19,  106,    0,  111,    0,  126,  -69,    0,  -41,  -95,
  128,    3,    0, -180,  -69,  320,  -69,  -23,  543,    0,
    0,    0,    0,  -32, -126,  283,    0,   19, -157, -107,
 -201,    0,    0, -155,   88,  106,  -83,  197, -148,    0,
  -81,  -19,    0,  -12,    0,  -85,   88,    0,  570,    0,
  258,  -69,  146,    0,    0,    0,    0,    0,    0, -204,
 -204,  -69,  -88,  -26,  320,    0,  604, -230,    0, -204,
 -204,  160,  115,  159,  616,   88,    0,   -4,    0,   19,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  196,
    0, -242,  196,    0,    0,    2,   32,  215,    0,  -61,
    0,  -61,  307,    0,  307,  -61,    0,  593,    0,  325,
    0,    3,    3,   88,    0,  627,    0,    0,  -95,    0,
    0,    0,    0,    0,    0,   39,    0,    0, -242,  196,
    0,    0,  174,    0,    0,    0,    0,    0,    0,    0,
    0,   21,  265,    0,  320,    0,  -69,  320,  279,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  345,    0,    0,    0,
    0,    0,    0,   76,    0,  321,    0,    0,  266,  334,
    0,    0,    0,    0,    0,    0,    0,    0,  242,    0,
    0,    0,    0,    0,    0,   76,    0,    0,    0,    0,
    0,    1,    0,    0,    0,   24,    0,    0,    0,    0,
    0,   47,    0,    0,    0,    0,    0,    0,  322,    0,
    0,    0,    0,  385,  408,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  116,  137,    0,   -3,    0,  437,
    0,    0,    0,    0,    0,    0,   58,    0,    0,    0,
    0,    0,  371,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -156,    0,    0,    0,
    0,    0,    0,    0,    0,  140,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  163,
    0,    0,  186,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,   93,  465,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  219,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,    0,    0,
};
final static short yygindex[] = {                         0,
   84,   -7,    0,  795,    0,    0,   28,    0,  693,    0,
    0,    0,  574,  -13,  -45,    0,  -20,    8,  293,   14,
    0,    0,   25,    0,    0,    0,    0,  198,  -44,    0,
  120,  165,   -1,  -64,  189,
};
final static int YYTABLESIZE=983;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         66,
   89,  100,   93,  101,   38,   40,   58,   66,   61,  133,
    3,   79,   80,   91,   68,  128,   79,  117,   99,    5,
   98,  141,  123,   85,  142,   61,   61,  105,  144,  159,
  160,  145,   49,   37,  129,  115,  167,   47,   56,  145,
   47,   89,   89,   89,  110,   89,   80,   89,   54,  111,
  120,   61,   42,   43,  127,   67,  128,   15,   46,   89,
   89,   46,   89,   75,   85,   85,   85,  170,   85,   78,
   85,   49,   45,   46,  168,  129,   42,   43,  113,  114,
   55,  116,   85,   85,  156,   85,   33,   80,   34,   80,
   35,   80,   79,   44,   62,  112,   45,   46,   59,  124,
  132,   38,  128,   63,   63,   80,   80,   70,   80,   63,
   78,   36,   78,   39,   78,   77,  150,   70,   69,  102,
  125,  129,   11,   89,   71,   89,  154,   13,   78,   78,
  100,   78,  101,   79,   74,   79,  102,   79,   57,   76,
   61,   89,  189,    1,    2,   91,   85,  174,   85,  175,
   92,   79,   79,  178,   79,  164,  176,  100,  177,  101,
  103,  104,   98,    8,  188,   58,    9,   10,  109,   80,
  126,   80,  148,  134,   77,  139,   12,   49,   89,   89,
   49,   89,   14,   89,  146,   99,  151,   42,   43,  155,
    6,    7,   78,    8,   78,  121,    9,   10,   76,  165,
  163,  100,   77,  101,   44,   11,   12,   45,   46,   11,
   13,   49,   14,   78,   13,   79,   64,   79,  101,  152,
  153,   98,   64,   64,   94,   95,   96,   97,   76,   43,
   39,   57,   39,   42,   43,   77,  140,   65,   39,  169,
   77,   36,   11,  143,   99,   44,   78,   13,   45,   46,
   44,  143,   47,   45,   46,  173,   89,   89,  171,   89,
   89,   89,   89,   89,   76,    9,   89,   89,   89,   89,
   89,   89,   89,   46,  161,  162,   89,  101,   89,   85,
   85,  186,   85,   85,   85,   85,   85,   98,  172,   85,
   85,   85,   85,   85,   85,   85,  185,   79,  149,   85,
   36,   85,   80,   80,  187,   80,   80,   80,   80,   80,
   99,  183,   80,   80,   80,   80,   80,   80,   80,  190,
    6,    5,   80,  122,   80,   78,   78,   53,   78,   78,
   78,   78,   78,   11,   47,   78,   78,   78,   78,   78,
   78,   78,   96,  101,   41,   78,   79,   78,   79,   79,
  118,   79,   79,   79,   79,   79,  182,  184,   79,   79,
   79,   79,   79,   79,   79,  180,   36,  100,   79,  101,
   79,   77,   77,    0,   77,   77,   77,   77,   77,    0,
    0,    0,    0,    0,   42,   77,   77,   77,   41,    0,
    9,   77,   49,   77,    0,   76,   76,    0,   76,   76,
   76,   76,   76,   41,    0,    0,   89,   39,    0,   76,
   76,   76,    0,    0,    0,   76,    0,   76,   98,   98,
    0,   98,   98,   98,   98,   98,    0,    0,   42,    0,
    0,    0,   98,   98,   98,    0,   93,    0,   98,    0,
   98,   99,   99,   42,   99,   99,   99,   99,   99,    0,
    0,   39,    0,  135,    0,   99,   99,   99,   11,    0,
  136,   99,    0,   99,    0,    0,   39,   11,    0,   41,
    0,    0,   13,    0,  101,  101,    0,  101,  101,  101,
  101,  101,    0,    0,    0,    0,    0,    0,  101,  101,
  101,    0,    0,   61,  101,   93,  101,   36,   36,    0,
   36,    0,    0,   36,   36,   66,    0,    0,    0,   42,
    0,    0,   36,   36,   42,   43,    0,   36,    0,   36,
    0,    9,    9,    0,    9,    0,    0,    9,    9,    0,
    0,   44,   39,    0,   45,   46,    9,    9,    0,  121,
    0,    9,    0,    9,    0,    0,   77,    0,    0,    0,
   60,   42,   43,   11,    0,    0,    0,   78,   13,   23,
    0,   93,    0,   42,   43,    0,    0,    0,   44,    0,
    0,   45,   46,    0,    0,  103,  104,    0,    8,    0,
   44,    9,   10,   45,   46,    0,    0,   66,    0,   11,
   11,   12,   11,    0,    0,   11,   11,   14,    0,    0,
   41,   41,    0,   41,   11,   11,   41,   41,    0,   11,
    0,   11,    0,   81,    0,   41,   41,    0,    0,    0,
   41,   72,   41,    0,    0,    0,   61,   61,    0,   61,
    0,    0,   61,   61,    0,    0,    0,    0,    0,   81,
   42,   42,   61,   42,   73,    0,   42,   42,   61,    0,
    0,  137,  138,    0,    0,   42,   42,    0,    0,    0,
   42,    0,   42,   39,   39,    0,   39,  119,    0,   39,
   39,    0,    0,    0,    0,    0,    0,    0,   39,   39,
    0,    6,    7,   39,    8,   39,    0,    9,   10,    0,
    0,    0,   93,   93,  147,   93,   11,   12,   93,   93,
   48,   13,    0,   14,    0,    0,    0,   93,   93,    0,
    0,    0,   93,   81,   93,   81,    0,  179,    0,   81,
   66,   66,    0,   66,    0,    0,   66,   66,  157,    0,
    0,   48,   48,    0,    0,    0,   66,    0,    0,   48,
  166,    0,   66,    0,    0,    0,   48,   48,    0,   48,
   48,  181,    6,    7,    0,    8,    0,    0,    9,   10,
    0,    0,    0,    0,    0,    0,    0,   11,   12,    0,
    0,    0,   13,    0,   14,    6,    7,    0,    8,    0,
    0,    9,   10,   48,   48,    0,    0,    0,    0,    0,
   11,   12,   48,   48,   48,   13,    0,   14,    6,    7,
    0,    8,   48,   48,    9,   10,    0,    0,    0,    0,
    0,    0,    0,   11,   12,    0,    0,    0,   13,    0,
   14,    0,    0,    0,    0,    6,    7,    0,    8,    0,
    0,    9,   10,    0,    0,   48,    0,   48,    0,    0,
   11,   12,    0,    0,  106,   13,    0,   14,    6,    7,
  106,    8,    0,    0,    9,   10,    0,    0,    0,  103,
  104,    0,    8,   11,   12,    9,   10,    0,   13,    0,
   14,  103,  104,    0,    8,   12,    0,    9,   10,   48,
    0,   14,  103,  104,    0,    8,    0,   12,    9,   10,
    0,    0,    0,   14,    0,    0,    0,    0,   12,  106,
    0,  158,    0,    0,   14,    6,    7,    0,    8,  158,
    0,    9,   10,    0,    0,    0,    0,    0,    0,    0,
   11,   12,    0,    0,    0,   13,    0,   14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  158,    0,    0,  106,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  106,
    0,    0,  158,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   43,   47,   45,    6,   40,   40,   40,   16,   74,
  123,   40,   41,   40,   28,  258,   40,   41,   60,  123,
   62,   41,   68,    0,   44,   33,   34,  123,   41,  260,
  261,   44,    8,    6,  277,   56,   41,   41,  123,   44,
   44,   41,   42,   43,   42,   45,    0,   47,   40,   47,
   64,   59,  257,  258,  256,   28,  258,  123,   41,   59,
   60,   44,   62,   39,   41,   42,   43,  132,   45,    0,
   47,   47,  277,  278,  120,  277,  257,  258,   54,   55,
   40,   57,   59,   60,  105,   62,    3,   41,    5,   43,
  257,   45,    0,  274,   59,  276,  277,  278,   15,  257,
  256,  103,  258,  260,  261,   59,   60,   44,   62,   59,
   41,  278,   43,  265,   45,    0,   92,   44,   44,   44,
  278,  277,  271,  123,   61,  125,  102,  276,   59,   60,
   43,   62,   45,   41,   61,   43,   61,   45,  265,    0,
  148,  123,  187,  256,  257,   40,  123,  140,  125,  142,
   40,   59,   60,  146,   62,   41,  143,   43,  145,   45,
  256,  257,    0,  259,  185,   40,  262,  263,   41,  123,
  278,  125,   89,  257,   59,  257,  272,   41,   42,   43,
   44,   45,  278,   47,  270,    0,   41,  257,  258,  278,
  256,  257,  123,  259,  125,  257,  262,  263,   59,   41,
   41,   43,  264,   45,  274,  271,  272,  277,  278,  271,
  276,  187,  278,  275,  276,  123,  257,  125,    0,  100,
  101,   59,  260,  261,  266,  267,  268,  269,  257,  258,
  265,  265,  265,  257,  258,  264,  256,  278,  265,   44,
  125,    0,  271,  256,   59,  274,  275,  276,  277,  278,
  274,  256,  256,  277,  278,   41,  256,  257,  257,  259,
  260,  261,  262,  263,  125,    0,  266,  267,  268,  269,
  270,  271,  272,  256,  110,  111,  276,   59,  278,  256,
  257,  261,  259,  260,  261,  262,  263,  125,  257,  266,
  267,  268,  269,  270,  271,  272,  123,   40,   41,  276,
   59,  278,  256,  257,   40,  259,  260,  261,  262,  263,
  125,  273,  266,  267,  268,  269,  270,  271,  272,   41,
    0,    0,  276,   41,  278,  256,  257,  270,  259,  260,
  261,  262,  263,    0,   40,  266,  267,  268,  269,  270,
  271,  272,  270,  125,    0,  276,   40,  278,  256,  257,
   58,  259,  260,  261,  262,  263,  159,  169,  266,  267,
  268,  269,  270,  271,  272,   41,  125,   43,  276,   45,
  278,  256,  257,   -1,  259,  260,  261,  262,  263,   -1,
   -1,   -1,   -1,   -1,    0,  270,  271,  272,   44,   -1,
  125,  276,  256,  278,   -1,  256,  257,   -1,  259,  260,
  261,  262,  263,   59,   -1,   -1,  270,    0,   -1,  270,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,
   -1,  259,  260,  261,  262,  263,   -1,   -1,   44,   -1,
   -1,   -1,  270,  271,  272,   -1,    0,   -1,  276,   -1,
  278,  256,  257,   59,  259,  260,  261,  262,  263,   -1,
   -1,   44,   -1,  257,   -1,  270,  271,  272,  125,   -1,
  264,  276,   -1,  278,   -1,   -1,   59,  271,   -1,  125,
   -1,   -1,  276,   -1,  256,  257,   -1,  259,  260,  261,
  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,   -1,  123,  276,   59,  278,  256,  257,   -1,
  259,   -1,   -1,  262,  263,   41,   -1,   -1,   -1,  125,
   -1,   -1,  271,  272,  257,  258,   -1,  276,   -1,  278,
   -1,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   -1,  274,  125,   -1,  277,  278,  271,  272,   -1,  257,
   -1,  276,   -1,  278,   -1,   -1,  264,   -1,   -1,   -1,
  125,  257,  258,  271,   -1,   -1,   -1,  275,  276,  123,
   -1,  125,   -1,  257,  258,   -1,   -1,   -1,  274,   -1,
   -1,  277,  278,   -1,   -1,  256,  257,   -1,  259,   -1,
  274,  262,  263,  277,  278,   -1,   -1,  123,   -1,  256,
  257,  272,  259,   -1,   -1,  262,  263,  278,   -1,   -1,
  256,  257,   -1,  259,  271,  272,  262,  263,   -1,  276,
   -1,  278,   -1,   40,   -1,  271,  272,   -1,   -1,   -1,
  276,  125,  278,   -1,   -1,   -1,  256,  257,   -1,  259,
   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   66,
  256,  257,  272,  259,  125,   -1,  262,  263,  278,   -1,
   -1,   78,   79,   -1,   -1,  271,  272,   -1,   -1,   -1,
  276,   -1,  278,  256,  257,   -1,  259,  125,   -1,  262,
  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,
   -1,  256,  257,  276,  259,  278,   -1,  262,  263,   -1,
   -1,   -1,  256,  257,  125,  259,  271,  272,  262,  263,
    8,  276,   -1,  278,   -1,   -1,   -1,  271,  272,   -1,
   -1,   -1,  276,  140,  278,  142,   -1,  125,   -1,  146,
  256,  257,   -1,  259,   -1,   -1,  262,  263,  125,   -1,
   -1,   39,   40,   -1,   -1,   -1,  272,   -1,   -1,   47,
  125,   -1,  278,   -1,   -1,   -1,   54,   55,   -1,   57,
   58,  125,  256,  257,   -1,  259,   -1,   -1,  262,  263,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,   -1,
   -1,   -1,  276,   -1,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   91,   92,   -1,   -1,   -1,   -1,   -1,
  271,  272,  100,  101,  102,  276,   -1,  278,  256,  257,
   -1,  259,  110,  111,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,   -1,   -1,   -1,   -1,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   -1,  143,   -1,  145,   -1,   -1,
  271,  272,   -1,   -1,   50,  276,   -1,  278,  256,  257,
   56,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,  256,
  257,   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,
  278,  256,  257,   -1,  259,  272,   -1,  262,  263,  187,
   -1,  278,  256,  257,   -1,  259,   -1,  272,  262,  263,
   -1,   -1,   -1,  278,   -1,   -1,   -1,   -1,  272,  105,
   -1,  107,   -1,   -1,  278,  256,  257,   -1,  259,  115,
   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  156,   -1,   -1,  159,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  185,
   -1,   -1,  188,
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

//#line 253 "parser_test.y"
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
//#line 577 "Parser.java"
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
//#line 141 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 58:
//#line 143 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 66:
//#line 161 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 67:
//#line 166 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 76:
//#line 184 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 77:
//#line 185 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 81:
//#line 192 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 89:
//#line 206 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 90:
//#line 210 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 91:
//#line 211 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 92:
//#line 212 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 93:
//#line 213 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 94:
//#line 217 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 95:
//#line 218 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 96:
//#line 222 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 97:
//#line 226 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 98:
//#line 230 "parser_test.y"
{ System.out.println("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 99:
//#line 231 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
case 100:
//#line 232 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
case 101:
//#line 233 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
//#line 930 "Parser.java"
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
