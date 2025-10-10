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
public final static short INVALID=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    2,
    2,    3,    3,    3,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    6,    6,    6,    6,   14,   14,   15,
   15,   15,    1,    1,    1,   17,   17,   16,   16,   16,
   19,   19,   19,   18,   18,   18,   18,   18,   18,   18,
   18,   18,    5,    5,    5,    5,    5,    5,    5,   20,
   21,   21,    8,    8,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   28,   28,
   28,   28,   28,   28,   28,   28,   28,   28,   26,   26,
   26,   26,   27,   27,   27,   27,   29,   29,   25,   25,
   25,   25,   25,   30,   30,   30,   30,   30,   30,   13,
   13,    7,    7,    7,    7,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   31,   31,   31,   31,   31,   31,
   31,   31,   31,   33,   33,   33,   33,   33,    9,    9,
    9,    9,   10,   23,   23,   23,   23,   11,   32,   32,
   32,   32,   32,   32,   12,   12,   12,   12,   35,   35,
   36,   36,   34,   34,   34,   34,   34,
};
final static short yylen[] = {                            2,
    4,    5,    3,    3,    3,    2,    3,    2,    1,    1,
    1,    2,    2,    1,    1,    1,    2,    2,    2,    1,
    2,    2,    2,    4,    3,    3,    4,    3,    2,    3,
    2,    0,    1,    2,    2,    1,    2,    1,    3,    2,
    1,    3,    2,    3,    2,    3,    2,    1,    2,    3,
    2,    2,    2,    3,    2,    1,    2,    3,    2,    3,
    1,    1,    1,    1,    5,    5,    4,    4,    5,    5,
    4,    4,    5,    4,    5,    4,    4,    4,    4,    4,
    4,    4,    3,    3,    2,    4,    4,    2,    3,    2,
    2,    1,    3,    3,    2,    2,    3,    2,    5,    5,
    4,    4,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    4,    4,    4,    3,    3,    1,    3,    3,
    1,    1,    2,    2,    3,    2,    3,    2,    3,    2,
    3,    2,    1,    1,    1,    1,    1,    1,    4,    4,
    4,    4,    2,    7,    6,    6,    4,    2,    3,    2,
    3,    2,    2,    1,    4,    5,    3,    4,    1,    3,
    3,    1,    1,    1,    2,    2,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  110,    0,
  111,    0,    0,    0,   33,   10,   11,    0,   14,   15,
   16,    0,    0,    0,   20,    0,   63,   64,    0,    8,
    0,   35,    0,    0,    0,    0,  163,    0,  164,    0,
  136,  167,    0,  118,    0,    0,    0,  137,    0,    0,
    0,    0,  133,  135,    0,  154,    0,  143,  148,    0,
    0,    0,   36,    0,    0,    0,    0,    7,    0,    4,
   34,   13,   59,   55,   12,    0,   21,   17,   22,   18,
   23,   19,    0,    0,    0,    0,    0,    0,    0,    3,
    0,    0,    0,    0,    0,    0,   29,    0,    0,   38,
    0,   41,    0,    0,   62,    0,   25,    0,  124,  123,
    0,  165,  166,  128,    0,  126,  132,  130,   98,  104,
  105,  106,  107,  108,  109,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   90,    0,    0,  153,  152,
    0,  150,    0,   96,    0,   95,   37,    0,    0,    0,
    0,    0,    0,   58,   54,    0,    0,   48,   26,    0,
  160,  157,  162,    0,  115,  114,  142,   49,   51,   45,
    0,    0,    0,   52,   47,   28,    0,   40,  140,    0,
   43,    0,   31,    0,   89,  117,    0,  120,    0,    0,
   88,    0,    0,   85,    0,    0,   77,    0,   74,    0,
   76,    0,   72,   68,    0,   71,   67,   78,  127,  125,
  131,  129,  151,  149,  102,  101,   94,   93,    0,  103,
  113,  112,  141,  139,    2,   24,   27,    0,  158,  155,
    0,   50,   46,   44,    0,   39,   42,   60,   30,    0,
    0,    0,   84,    0,   83,   73,   75,   70,   66,   69,
   65,  100,   99,  156,  161,    0,   86,   87,   82,   80,
   81,   79,    0,    0,    0,  146,  144,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   48,   23,
   24,   25,   26,   35,  107,   99,   64,  100,  101,  102,
  103,  104,  105,   27,   28,   50,   65,  135,   51,  128,
   52,   58,   53,   54,   29,  164,
};
final static short yysindex[] = {                      -104,
  580,  645,    0,  -27,  -14,  114,   70,   70,    0,  386,
    0,   10,  671,  693,    0,    0,    0,  -22,    0,    0,
    0,   22,   27,   32,    0,  -37,    0,    0,  -26,    0,
  713,    0,  147,   28, -103,   26,    0,   37,    0,   89,
    0,    0,  147,    0,  -93,  216,  220,    0,  473,  637,
   15,  120,    0,    0,  -35,    0,   77,    0,    0,   17,
  114,  746,    0,  789,   96,  147,  104,    0,  724,    0,
    0,    0,    0,    0,    0, -195,    0,    0,    0,    0,
    0,    0,  100,  100,  -39, -103,   94, -110,  -30,    0,
   31,  134,   26,  -87,  255, -212,    0, -168,    5,    0,
   52,    0,  -82,   -1,    0,  682,    0,  129,    0,    0,
  136,    0,    0,    0,  -93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  172,  182,  147,  613,  150,
  346,  -64,  -50,  -48,  156,    0,  306,  322,    0,    0,
  -18,    0,   41,    0,  605,    0,    0,  114,  232,  107,
  190,   61,  -63,    0,    0, -103, -103,    0,    0,   -6,
    0,    0,    0,  -16,    0,    0,    0,    0,    0,    0,
   39,   44,   55,    0,    0,    0,  265,    0,    0,  138,
    0,   79,    0,  735,    0,    0,  120,    0,  120,   -1,
    0,  753,   62,    0,  -45,   -4,    0,  266,    0,  278,
    0,   57,    0,    0,   64,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,    0,
    0,    0,    0,    0,    0,    0,    0,  -11,    0,    0,
   -6,    0,    0,    0,  305,    0,    0,    0,    0,  293,
  298,   66,    0,   68,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -99,    0,    0,    0,    0,
    0,    0,  823,  764,  778,    0,    0,
};
final static short yyrindex[] = {                         0,
  358,    0,    0,    0,   -8,    0,    0,    0,    0,    0,
    0,  -23,    0,  359,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,  422,    0,    0,    0,  445,
    0,    0,    0,    0,  465,    0,    0,    0,    0,    0,
  552,  489,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  365,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   86,  142,    0,    1,    0,    0,    0,    0,
    0,    0,  390,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   98,    0,    0,    0,    0,    0,    0,
  572,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  816,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  366,    0,    0,    1,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  512,    0,  532,  360,
    0,    0,    0,    0,  816,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  109,    0,    0,    0,    0,
    0,    0,    0,    0,  110,    0,    0,
};
final static short yygindex[] = {                         0,
   85,   16,    0,  -10,    0,    0,    0,    0,    3,    0,
    0,    0,  878,  272,   47,    0,  283,  -70,  310,  -38,
    0, 1026,    0,    0,    0,  -20,    8,   74,  347,    0,
  207,   23,  152,  -29,    0,  229,
};
final static int YYTABLESIZE=1174;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         63,
   32,   97,   85,   22,   22,  140,   46,   44,  203,   45,
  206,   47,   22,  243,  115,   22,   22,   88,    2,  106,
  159,   76,  214,  263,  126,   34,  127,  231,  178,   71,
   59,   32,  231,   22,   89,   56,   75,  159,  115,  133,
  143,  126,  230,  127,  149,  176,   71,  254,  177,   67,
   56,   63,   22,  147,  245,  136,  108,  134,    9,  163,
  110,  154,  181,   11,   22,  108,   22,   96,   97,   46,
   44,   22,   45,  126,   47,  127,   55,   56,   46,   44,
   78,   45,  155,   47,   71,   80,   31,  174,  175,  166,
   82,   96,  179,   46,   44,  180,   45,   69,   47,  216,
   96,  224,   46,   44,  180,   45,  236,   47,   22,   55,
   56,   46,   44,  181,   45,  249,   47,  142,  195,  126,
   63,  127,  251,  253,  260,   32,  262,  219,   67,   57,
  163,   22,  159,   22,  147,   43,  196,   46,   44,   85,
   45,  237,   47,   96,   57,   46,   44,   22,   45,  126,
   47,  127,    1,   43,  160,   46,   44,   60,   45,    6,
   47,  137,    7,    8,  112,  222,  138,  161,   96,  168,
   46,   44,   10,   45,  167,   47,  185,   96,   12,   46,
   44,   63,   45,  113,   47,   53,   22,  182,   46,   44,
  184,   45,  225,   47,   22,  129,  200,  116,  118,   71,
   53,  255,  226,  227,  199,  201,  204,  207,  197,  129,
  202,  129,  205,   46,  208,  242,  115,  158,   47,   83,
  139,   36,   37,   46,   94,  162,  115,   37,   47,   87,
  223,    9,  159,   72,   73,   95,   11,  213,   38,  229,
   84,   39,   40,   41,   42,   63,   39,   56,   56,   42,
   33,   37,   63,  147,  147,   74,  244,   32,   22,   32,
  115,  158,   32,   32,  115,   22,   22,   22,   94,   56,
   39,   32,   32,   42,   66,    9,   32,   77,   32,   95,
   11,   33,   79,   92,   93,   37,  165,   81,  210,  212,
  220,   94,  109,   36,   37,  232,  215,   86,    9,  233,
  234,   38,   95,   11,   39,   40,   41,   42,   36,   37,
   38,  235,  248,   39,   40,   41,   42,   36,   37,  250,
  252,  259,  241,  261,  246,   38,   36,   37,   39,   40,
   41,   42,  187,  189,   38,  238,  247,   39,   40,   41,
   42,   57,   57,   38,  145,  256,   39,   40,   41,   42,
  115,  257,   36,   37,  156,  157,  258,    9,    6,  151,
   36,   37,  221,   57,    5,    1,  115,   61,  148,   38,
   36,   37,   39,   40,   41,   42,  152,   38,  147,  145,
   39,   40,   41,   42,   92,   36,   37,   38,  228,  111,
   39,   40,   41,   42,   36,   37,    0,   53,   53,    0,
   97,    0,   38,   36,   37,   39,   40,   41,   42,    0,
    0,   38,    0,  145,   39,   40,   41,   42,   97,   53,
   38,    0,    0,   39,   40,   41,   42,  186,   36,   37,
   48,  138,  138,   48,  138,    0,  138,  188,   36,   37,
    0,    0,    0,    0,    0,    0,    0,    0,   39,   40,
   41,   42,    0,    0,    0,    0,    0,    0,   39,   40,
   41,   42,  138,  138,  138,    0,  138,    0,  138,    0,
  144,  114,   36,   37,  145,  117,   36,   37,    0,    0,
  138,  138,   97,  138,   97,  134,  134,  134,    0,  134,
    0,  134,   39,   40,   41,   42,   39,   40,   41,   42,
    0,    0,    0,  134,  134,  121,  134,  121,   62,  121,
  169,  170,    0,    0,    0,  126,    0,  127,  171,    0,
    0,  158,    0,  121,  121,    9,  121,    0,   94,  122,
   11,  122,  125,  122,  124,    9,    0,    0,  264,   95,
   11,    0,    0,    0,  138,  265,  138,  122,  122,    0,
  122,    0,  116,    0,  116,    0,  116,    0,    0,    0,
    0,  209,   36,   37,    0,    0,    0,  134,    0,  134,
  116,  116,  119,  116,  119,    0,  119,  211,   36,   37,
    0,    0,   39,   40,   41,   42,    0,  121,    0,  121,
  119,  119,    0,  119,    0,    0,    0,    0,   39,   40,
   41,   42,   60,    0,    6,  129,  198,    7,    8,    0,
   92,  122,    0,  122,    0,   97,   97,   10,   97,   97,
   97,   97,   97,   12,    0,    0,    0,    0,    0,    0,
   91,   97,    0,    0,  116,    0,  116,   97,    0,    0,
    0,    0,   60,    0,    6,    0,   48,    7,    8,    0,
    0,    0,    0,   48,  119,    0,  119,   10,   61,  138,
   48,    0,    0,   12,   48,   48,    0,    0,    0,    0,
    0,  194,    0,    0,   92,    0,   92,  138,  138,    0,
  138,  138,  138,  138,  138,    0,    0,  138,  138,  138,
  138,  138,    0,  138,   91,    0,   91,    0,    0,  138,
  134,  134,   13,  134,  134,  134,  134,  134,    0,    0,
  134,  134,  134,  134,  134,    0,  134,    0,    0,    0,
  121,  121,  134,  121,  121,  121,  121,  121,  119,  218,
  121,  121,  121,  121,  121,  192,  121,  193,  120,  121,
  122,  123,  121,    0,  122,  122,    0,  122,  122,  122,
  122,  122,    0,    0,  122,  122,  122,  122,  122,  131,
  122,  132,    0,    0,    0,    0,  122,  116,  116,   30,
  116,  116,  116,  116,  116,    0,    0,  116,  116,  116,
  116,  116,    0,  116,    0,    0,    0,  119,  119,  116,
  119,  119,  119,  119,  119,   68,    0,  119,  119,  119,
  119,  119,    0,  119,    0,    0,  183,   92,   92,  119,
   92,   92,   92,   92,   92,    0,    0,   70,    0,    0,
    0,    0,    0,   92,    0,    0,    0,   91,   91,   92,
   91,   91,   91,   91,   91,    4,    5,   90,    6,    0,
    0,    7,    8,   91,    0,    0,    0,    0,  153,   91,
    9,   10,    0,    0,    0,   11,    0,   12,    0,  239,
  217,   60,    0,    6,    0,    0,    7,    8,    0,   60,
  144,    6,    0,  191,    7,    8,   10,  144,    0,    0,
    0,    0,   12,    0,   10,    0,    0,    0,  266,    0,
   12,    0,    0,   60,    0,    6,  129,  130,    7,    8,
    4,    5,  267,    6,    0,    0,    7,    8,   10,    0,
    0,   98,    0,    0,   12,    9,   10,    0,    0,    0,
   11,    0,   12,    0,    0,    0,    4,    5,    0,    6,
    0,    0,    7,    8,    0,    0,    0,    4,    5,    0,
    6,    9,   10,    7,    8,    0,   11,    0,   12,    5,
    0,    6,    9,   10,    7,    8,    0,   11,    0,   12,
    0,    0,   98,    9,   10,    0,    0,    0,   11,    5,
   12,    6,  172,  173,    7,    8,   98,    0,    0,    0,
    5,    0,    6,    9,   10,    7,    8,    0,   11,    0,
   12,    5,    0,    6,    9,   10,    7,    8,    0,   11,
    0,   12,   60,    0,    6,    9,   10,    7,    8,   60,
   11,    6,   12,  240,    7,    8,    0,   10,    0,    0,
   60,    0,    6,   12,   10,    7,    8,    0,    0,    0,
   12,   49,   57,   57,   60,   10,    6,    0,    0,    7,
    8,   12,    0,    0,  146,   60,    0,    6,    0,   10,
    7,    8,    0,    0,   98,   12,    0,    0,   91,    0,
   10,    0,    0,   57,    0,    0,   12,    0,   49,    0,
    0,   36,   36,    0,   36,    0,    0,   36,   36,   60,
  141,    6,    0,    0,    7,    8,   49,   36,    0,    0,
   49,  150,    0,   36,   10,    0,    0,    0,    0,    0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  190,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         10,
    0,   41,   40,    1,    2,   41,   42,   43,   59,   45,
   59,   47,   10,   59,   45,   13,   14,   44,  123,  123,
   44,   44,   41,  123,   43,   40,   45,   44,   99,   14,
    8,   59,   44,   31,   61,   44,   59,   61,   45,   50,
   61,   43,   59,   45,   65,   41,   31,   59,   44,   40,
   59,   62,   50,   64,   59,   41,   40,   50,  271,   89,
   38,  257,  101,  276,   62,   40,   64,   40,   41,   42,
   43,   69,   45,   43,   47,   45,   40,   41,   42,   43,
   59,   45,  278,   47,   69,   59,    2,  256,  257,   59,
   59,   40,   41,   42,   43,   44,   45,   13,   47,   59,
   40,   41,   42,   43,   44,   45,  177,   47,  106,   40,
   41,   42,   43,  152,   45,   59,   47,   41,  129,   43,
  131,   45,   59,   59,   59,  125,   59,  148,   40,   44,
  160,  129,   86,  131,  145,   40,  129,   42,   43,   40,
   45,  180,   47,   40,   59,   42,   43,  145,   45,   43,
   47,   45,  257,   40,   61,   42,   43,  257,   45,  259,
   47,   42,  262,  263,  258,   59,   47,  278,   40,  257,
   42,   43,  272,   45,   41,   47,   41,   40,  278,   42,
   43,  192,   45,  277,   47,   44,  184,  270,   42,   43,
  106,   45,  256,   47,  192,  260,  261,   46,   47,  184,
   59,  231,  156,  157,  131,  132,  133,  134,   59,  260,
  261,  260,  261,   42,   59,  261,   45,  257,   47,  257,
  256,  257,  258,   42,  264,  256,   45,  258,   47,  256,
   41,  271,  256,  256,  257,  275,  276,  256,  274,  256,
  278,  277,  278,  279,  280,  256,  277,  256,  257,  280,
  265,  258,  263,  264,  265,  278,  261,  257,  256,  259,
   45,  257,  262,  263,   45,  263,  264,  265,  264,  278,
  277,  271,  272,  280,  265,  271,  276,  256,  278,  275,
  276,  265,  256,  256,  257,  258,  256,  256,  137,  138,
   59,  264,  256,  257,  258,  257,  256,   26,  271,  256,
  257,  274,  275,  276,  277,  278,  279,  280,  257,  258,
  274,  257,  256,  277,  278,  279,  280,  257,  258,  256,
  256,  256,  261,  256,   59,  274,  257,  258,  277,  278,
  279,  280,  126,  127,  274,  257,   59,  277,  278,  279,
  280,  256,  257,  274,   62,   41,  277,  278,  279,  280,
   45,   59,  257,  258,   83,   84,   59,    0,    0,  256,
  257,  258,  256,  278,    0,    0,   45,  270,  273,  274,
  257,  258,  277,  278,  279,  280,   67,  274,  270,  270,
  277,  278,  279,  280,  256,  257,  258,  274,  160,   43,
  277,  278,  279,  280,  257,  258,   -1,  256,  257,   -1,
   41,   -1,  274,  257,  258,  277,  278,  279,  280,   -1,
   -1,  274,   -1,  131,  277,  278,  279,  280,   59,  278,
  274,   -1,   -1,  277,  278,  279,  280,  256,  257,  258,
   41,   42,   43,   44,   45,   -1,   47,  256,  257,  258,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
  279,  280,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
  279,  280,   41,   42,   43,   -1,   45,   -1,   47,   -1,
  125,  256,  257,  258,  192,  256,  257,  258,   -1,   -1,
   59,   60,  123,   62,  125,   41,   42,   43,   -1,   45,
   -1,   47,  277,  278,  279,  280,  277,  278,  279,  280,
   -1,   -1,   -1,   59,   60,   41,   62,   43,  123,   45,
  256,  257,   -1,   -1,   -1,   43,   -1,   45,  264,   -1,
   -1,  257,   -1,   59,   60,  271,   62,   -1,  264,   41,
  276,   43,   60,   45,   62,  271,   -1,   -1,  256,  275,
  276,   -1,   -1,   -1,  123,  263,  125,   59,   60,   -1,
   62,   -1,   41,   -1,   43,   -1,   45,   -1,   -1,   -1,
   -1,  256,  257,  258,   -1,   -1,   -1,  123,   -1,  125,
   59,   60,   41,   62,   43,   -1,   45,  256,  257,  258,
   -1,   -1,  277,  278,  279,  280,   -1,  123,   -1,  125,
   59,   60,   -1,   62,   -1,   -1,   -1,   -1,  277,  278,
  279,  280,  257,   -1,  259,  260,  261,  262,  263,   -1,
   59,  123,   -1,  125,   -1,  256,  257,  272,  259,  260,
  261,  262,  263,  278,   -1,   -1,   -1,   -1,   -1,   -1,
   59,  272,   -1,   -1,  123,   -1,  125,  278,   -1,   -1,
   -1,   -1,  257,   -1,  259,   -1,  257,  262,  263,   -1,
   -1,   -1,   -1,  264,  123,   -1,  125,  272,  273,  270,
  271,   -1,   -1,  278,  275,  276,   -1,   -1,   -1,   -1,
   -1,   59,   -1,   -1,  123,   -1,  125,  256,  257,   -1,
  259,  260,  261,  262,  263,   -1,   -1,  266,  267,  268,
  269,  270,   -1,  272,  123,   -1,  125,   -1,   -1,  278,
  256,  257,  123,  259,  260,  261,  262,  263,   -1,   -1,
  266,  267,  268,  269,  270,   -1,  272,   -1,   -1,   -1,
  256,  257,  278,  259,  260,  261,  262,  263,  256,  125,
  266,  267,  268,  269,  270,  123,  272,  125,  266,  267,
  268,  269,  278,   -1,  256,  257,   -1,  259,  260,  261,
  262,  263,   -1,   -1,  266,  267,  268,  269,  270,  123,
  272,  125,   -1,   -1,   -1,   -1,  278,  256,  257,  125,
  259,  260,  261,  262,  263,   -1,   -1,  266,  267,  268,
  269,  270,   -1,  272,   -1,   -1,   -1,  256,  257,  278,
  259,  260,  261,  262,  263,  125,   -1,  266,  267,  268,
  269,  270,   -1,  272,   -1,   -1,  125,  256,  257,  278,
  259,  260,  261,  262,  263,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   -1,  272,   -1,   -1,   -1,  256,  257,  278,
  259,  260,  261,  262,  263,  256,  257,  125,  259,   -1,
   -1,  262,  263,  272,   -1,   -1,   -1,   -1,  125,  278,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,   -1,  125,
  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,  257,
  125,  259,   -1,  261,  262,  263,  272,  125,   -1,   -1,
   -1,   -1,  278,   -1,  272,   -1,   -1,   -1,  125,   -1,
  278,   -1,   -1,  257,   -1,  259,  260,  261,  262,  263,
  256,  257,  125,  259,   -1,   -1,  262,  263,  272,   -1,
   -1,   34,   -1,   -1,  278,  271,  272,   -1,   -1,   -1,
  276,   -1,  278,   -1,   -1,   -1,  256,  257,   -1,  259,
   -1,   -1,  262,  263,   -1,   -1,   -1,  256,  257,   -1,
  259,  271,  272,  262,  263,   -1,  276,   -1,  278,  257,
   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,  278,
   -1,   -1,   85,  271,  272,   -1,   -1,   -1,  276,  257,
  278,  259,   95,   96,  262,  263,   99,   -1,   -1,   -1,
  257,   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,
  278,  257,   -1,  259,  271,  272,  262,  263,   -1,  276,
   -1,  278,  257,   -1,  259,  271,  272,  262,  263,  257,
  276,  259,  278,  261,  262,  263,   -1,  272,   -1,   -1,
  257,   -1,  259,  278,  272,  262,  263,   -1,   -1,   -1,
  278,    6,    7,    8,  257,  272,  259,   -1,   -1,  262,
  263,  278,   -1,   -1,  256,  257,   -1,  259,   -1,  272,
  262,  263,   -1,   -1,  177,  278,   -1,   -1,   33,   -1,
  272,   -1,   -1,   38,   -1,   -1,  278,   -1,   43,   -1,
   -1,  256,  257,   -1,  259,   -1,   -1,  262,  263,  257,
   55,  259,   -1,   -1,  262,  263,   61,  272,   -1,   -1,
   65,   66,   -1,  278,  272,   -1,   -1,   -1,   -1,   -1,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  128,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  148,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=280;
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
"INVALID",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID '{' lista_sentencias '}'",
"programa : ID '{' lista_sentencias '}' error",
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
"expresion : expresion '+' error",
"expresion : '+'",
"expresion : expresion '-' termino",
"expresion : expresion '-' error",
"expresion : '-'",
"expresion : termino",
"expresion : TRUNC cuerpo_expresion",
"expresion : TRUNC error",
"termino : termino '*' factor",
"termino : '*' factor",
"termino : termino '*' error",
"termino : '*' error",
"termino : termino '/' factor",
"termino : '/' factor",
"termino : termino '/' error",
"termino : '/' error",
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
"constante : INVALID",
};

//#line 408 "parser_test.y"
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
//#line 719 "Parser.java"
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
{ errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine()); }
break;
case 3:
//#line 22 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 4:
//#line 24 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 5:
//#line 26 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 6:
//#line 28 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 7:
//#line 30 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 8:
//#line 32 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 9:
//#line 34 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 13:
//#line 45 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 57 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
//#line 59 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 61 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 24:
//#line 66 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 25:
//#line 68 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 26:
//#line 70 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 27:
//#line 72 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 29:
//#line 77 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 31:
//#line 82 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 32:
//#line 83 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 35:
//#line 91 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 43:
//#line 109 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 44:
//#line 114 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 45:
//#line 116 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 46:
//#line 118 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 47:
//#line 120 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 48:
//#line 122 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 49:
//#line 124 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
//#line 126 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 51:
//#line 128 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 130 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 53:
//#line 136 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
//#line 138 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 55:
//#line 140 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
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
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 59:
//#line 148 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 60:
//#line 155 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 65:
//#line 171 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
//#line 173 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 67:
//#line 175 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
//#line 177 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 69:
//#line 179 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 181 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 71:
//#line 183 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 185 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 73:
//#line 187 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 189 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 75:
//#line 191 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 193 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 77:
//#line 195 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 78:
//#line 197 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 81:
//#line 204 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 206 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
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
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 86:
//#line 214 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 87:
//#line 216 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 88:
//#line 218 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 90:
//#line 224 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 91:
//#line 226 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 92:
//#line 228 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 94:
//#line 234 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 95:
//#line 236 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 96:
//#line 238 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 97:
//#line 244 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 98:
//#line 246 "parser_test.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 99:
//#line 251 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 100:
//#line 253 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 101:
//#line 255 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 102:
//#line 257 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 103:
//#line 259 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 112:
//#line 278 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 113:
//#line 280 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 114:
//#line 282 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 115:
//#line 284 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 117:
//#line 290 "parser_test.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 118:
//#line 292 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 120:
//#line 295 "parser_test.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 121:
//#line 297 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 123:
//#line 300 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 124:
//#line 302 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 126:
//#line 308 "parser_test.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 127:
//#line 310 "parser_test.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 128:
//#line 312 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 130:
//#line 315 "parser_test.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 131:
//#line 317 "parser_test.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 132:
//#line 319 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 138:
//#line 329 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 139:
//#line 334 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 140:
//#line 336 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 141:
//#line 338 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 142:
//#line 340 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 143:
//#line 345 "parser_test.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 144:
//#line 349 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 145:
//#line 351 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 146:
//#line 353 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 147:
//#line 355 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 148:
//#line 360 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 150:
//#line 366 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 151:
//#line 368 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 152:
//#line 370 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 153:
//#line 372 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 154:
//#line 374 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 155:
//#line 379 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 156:
//#line 381 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 157:
//#line 383 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 158:
//#line 385 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 167:
//#line 404 "parser_test.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
//#line 1312 "Parser.java"
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
