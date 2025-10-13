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
public final static short IF=258;
public final static short ELSE=259;
public final static short ENDIF=260;
public final static short PRINT=261;
public final static short RETURN=262;
public final static short LAMBDA=263;
public final static short ASIGNAR=264;
public final static short MENORIGUAL=265;
public final static short MAYORIGUAL=266;
public final static short IGUALIGUAL=267;
public final static short DISTINTO=268;
public final static short FLECHA=269;
public final static short LONG=270;
public final static short DO=271;
public final static short UNTIL=272;
public final static short TRUNC=273;
public final static short CR=274;
public final static short STRING=275;
public final static short IDCOMP=276;
public final static short CADENASTR=277;
public final static short CTEL=278;
public final static short CTEF=279;
public final static short INVALID=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    3,    4,    4,    4,    5,    5,    5,    5,    5,    5,
    5,    5,    5,    7,    7,    7,    7,   15,   15,   16,
   16,   16,    2,    2,    2,   18,   18,   17,   17,   17,
   20,   20,   20,   19,   19,   19,   19,   19,   19,   19,
   19,   19,    6,    6,    6,    6,    6,    6,    6,   21,
   22,   22,    9,    9,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   25,   29,   29,
   29,   29,   29,   29,   29,   29,   29,   29,   27,   27,
   27,   27,   28,   28,   28,   28,   30,   30,   26,   26,
   26,   26,   26,   31,   31,   31,   31,   31,   31,   14,
   14,    8,    8,    8,    8,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   32,   32,   32,   32,   32,   32,
   32,   32,   32,   34,   34,   34,   34,   34,   34,   34,
   34,   34,   10,   10,   10,   10,   11,   24,   24,   24,
   24,   12,   33,   33,   33,   33,   33,   33,   13,   13,
   13,   13,   35,   35,   36,   36,    1,    1,    1,    1,
    1,    1,    1,
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
    2,    1,    2,    2,    3,    2,    3,    2,    3,    2,
    3,    2,    1,    1,    1,    1,    1,    1,    2,    2,
    2,    2,    4,    4,    4,    4,    2,    7,    6,    6,
    4,    2,    3,    2,    3,    2,    2,    1,    4,    5,
    3,    4,    1,    3,    3,    1,    1,    1,    2,    2,
    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  110,    0,
  111,    0,    0,    0,   33,   10,   11,    0,   14,   15,
   16,    0,    0,    0,   20,    0,   63,   64,    0,    8,
    0,   35,    0,    0,    0,    0,    0,    0,  136,  167,
  168,  171,    0,    0,    0,    0,    0,  135,  137,    0,
    0,    0,    0,  133,    0,  158,    0,  147,  152,    0,
    0,    0,   36,    0,    0,    0,    0,    7,    0,    4,
   34,   13,   59,   55,   12,    0,   21,   17,   22,   18,
   23,   19,    0,    0,    0,    0,    0,    0,    0,    3,
    0,    0,    0,    0,    0,    0,   29,    0,    0,   38,
    0,   41,    0,    0,   62,    0,   25,    0,  124,  123,
    0,  172,  173,  121,    0,    0,  142,  169,  170,  139,
  128,    0,    0,  126,  132,  130,   98,  104,  105,  106,
  107,  108,  109,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,    0,    0,  157,  156,    0,  154,
    0,   96,    0,   95,   37,    0,    0,    0,    0,    0,
    0,   58,   54,    0,    0,   48,   26,    0,  164,  161,
    0,  166,    0,  115,  114,  146,   49,   51,   45,    0,
    0,    0,   52,   47,   28,    0,   40,  144,    0,   43,
    0,   31,    0,   89,  117,    0,  120,    0,    0,   88,
    0,    0,   85,    0,    0,   77,    0,   74,    0,   76,
    0,   72,   68,    0,   71,   67,   78,  127,  125,  131,
  129,  155,  153,  102,  101,   94,   93,    0,  103,  113,
  112,  145,  143,    2,   24,   27,    0,  162,  159,    0,
   50,   46,   44,    0,   39,   42,   60,   30,    0,    0,
    0,   84,    0,   83,   73,   75,   70,   66,   69,   65,
  100,   99,  160,  165,    0,   86,   87,   82,   80,   81,
   79,    0,    0,    0,  150,  148,
};
final static short yydgoto[] = {                          3,
   48,   14,   15,   16,   17,   18,   19,   20,   21,   49,
   23,   24,   25,   26,   35,  107,   99,   64,  100,  101,
  102,  103,  104,  105,   27,   28,   51,   65,  143,   52,
  136,   53,   58,   54,   29,  173,
};
final static short yysindex[] = {                       -87,
  606,  683,    0,  -18,    3,   80,   56,   56,    0,  660,
    0,   45,  690,  714,    0,    0,    0,  -33,    0,    0,
    0,  -43,  -30,  -22,    0,  -16,    0,    0,  -36,    0,
  735,    0,  132,  -28,  -47,   26,   22,   55,    0,    0,
    0,    0,  132,  -86,  198,  146,  157,    0,    0,  -12,
  639,   27,   -7,    0,  121,    0,   87,    0,    0,   66,
   80,  869,    0,  340,  -40,  132,   65,    0,  800,    0,
    0,    0,    0,    0,    0, -215,    0,    0,    0,    0,
    0,    0,   76,   76,  105,  -47,   68, -157,   28,    0,
  113,  119,   26,  -84,  283, -226,    0,  -58,  287,    0,
   39,    0,  -93,  122,    0,  712,    0,   94,    0,    0,
  149,    0,    0,    0,   26,   55,    0,    0,    0,    0,
    0,  -86,  222,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,  139,  132,  348,  135,  741,  -53,
  -55,  -29,  144,    0,  171,  182,    0,    0,   99,    0,
   41,    0,  831,    0,    0,   80,  151,  126,  167,   47,
  -76,    0,    0,  -47,  -47,    0,    0,   12,    0,    0,
  -60,    0,   58,    0,    0,    0,    0,    0,    0,  -35,
   37,    7,    0,    0,    0,  841,    0,    0,  108,    0,
   48,    0,  824,    0,    0,   -7,    0,   -7,  122,    0,
  852,   -3,    0,  -39,   15,    0,  252,    0,  280,    0,
   54,    0,    0,   84,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
    0,    0,    0,    0,    0,    0,   34,    0,    0,   12,
    0,    0,    0,  307,    0,    0,    0,    0,  293,  302,
   98,    0,  137,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  662,    0,    0,    0,    0,    0,
    0,  913,  889,  891,    0,    0,
};
final static short yyrindex[] = {                         0,
  363,    0,    0,    0,  347,    0,    0,    0,    0,    0,
    0,  134,    0,  364,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,  422,    0,  444,    0,    0,
    0,    0,    0,  511,    0,    0,    0,    0,    0,    0,
    0,  393,  533,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  366,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  385,  387,    0,    1,    0,    0,    0,    0,
    0,    0,  584,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  123,    0,    0,    0,    0,    0,    0,
  613,    0,    0,    0,  466,  489,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  807,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  383,    0,    0,    1,    1,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  762,    0,  784,  574,    0,
    0,    0,    0,  807,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  161,    0,    0,    0,    0,    0,
    0,    0,    0,  176,    0,    0,
};
final static short yygindex[] = {                         0,
  -57,   32,   90,    0,   -4,    0,    0,    0,    0,    8,
    0,    0,    0,  398,  128,   23,    0,  314,  -71,  337,
  114,    0, 1126,    0,    0,    0,  -38,  -13,  301,  413,
    0,  212,   38,  374,    0,  289,
};
final static int YYTABLESIZE=1282;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         43,
   32,   46,   44,  212,   45,   63,   47,   88,   22,   22,
   76,   96,   97,   46,   44,   78,   45,   22,   47,  252,
   22,   22,  151,   85,   89,   75,  157,  187,   80,  215,
  134,  172,  135,   31,  145,    2,   82,  142,   22,  146,
   32,  162,   34,    9,   69,   59,  141,  133,   11,  132,
   46,  122,  120,  123,  122,   47,  171,   63,   22,  155,
  163,   55,   56,   46,   44,  108,   45,  144,   47,   22,
  122,   22,  171,  254,  110,  106,   22,  240,   96,  188,
   46,   44,  189,   45,   67,   47,   96,  233,   46,   44,
  189,   45,  263,   47,   67,   55,   56,   46,   44,  225,
   45,  240,   47,   71,   96,  108,   46,   44,  167,   45,
  172,   47,  258,   22,  245,   85,  239,  228,  169,   43,
   71,   46,   44,  205,   45,   32,   47,  150,  168,  134,
  120,  135,  204,   96,   63,   46,   44,  193,   45,  223,
   47,  134,  260,  135,   22,   97,   22,   96,  155,   46,
   44,  262,   45,   86,   47,  134,  269,  135,   71,  176,
   22,  148,   46,   44,  134,   45,  135,   47,  134,    1,
  135,  175,  177,   46,   44,  191,   45,  163,   47,  234,
   46,  122,  264,  123,  231,   47,  235,  236,  122,  194,
  123,  112,  113,  206,  163,  271,   63,  183,  184,  122,
   22,  123,  217,  137,  211,  137,  209,  232,   22,  229,
  164,  165,   77,  122,  190,  123,   36,  118,  119,   87,
  251,  241,   72,   73,  122,   79,  123,   92,   93,  137,
  214,  156,   37,   81,   94,   38,   39,   40,   41,   42,
   83,    9,   74,  127,   37,   95,   11,   38,   39,   40,
   41,   42,  128,  129,  130,  131,  250,   32,   32,   84,
   63,   32,   32,  244,  195,   36,   33,   63,  155,  155,
   32,   32,   22,  190,  253,   32,   32,  109,   36,   22,
   22,   22,   71,  170,   38,   39,   40,   41,   42,   40,
   41,   42,  242,  243,   37,   36,  224,   38,   39,   40,
   41,   42,  246,   36,  247,   40,   41,   42,   66,  257,
  255,   37,   36,  238,   38,   39,   40,   41,   42,   37,
  159,   36,   38,   39,   40,   41,   42,  185,   37,   33,
  186,   38,   39,   40,   41,   42,   36,   37,  256,  259,
   38,   39,   40,   41,   42,  196,  198,  265,  261,   92,
   36,  266,   37,  268,  222,   38,   39,   40,   41,   42,
  267,  166,    9,    6,   36,    5,   37,   94,  174,   38,
   39,   40,   41,   42,    9,  153,  147,   36,   95,   11,
   37,  230,    1,   38,   39,   40,   41,   42,   36,  163,
   56,   61,  270,   37,  197,   36,   38,   39,   40,   41,
   42,  121,   36,  160,   37,   56,  203,   38,   39,   40,
   41,   42,  125,   36,   38,   39,   40,   41,   42,  124,
  126,   38,   39,   40,   41,   42,  218,   36,   57,  151,
   53,   98,   38,   39,   40,   41,   42,  220,   36,  208,
  210,  213,  216,   57,  149,   53,   38,   39,   40,   41,
   42,   92,  153,  114,  115,  111,  237,   38,   39,   40,
   41,   42,  138,  138,  138,    0,  138,    0,  138,    0,
  201,    0,  202,  116,  117,  118,  119,    0,  115,    0,
  138,  138,   98,  138,  134,  134,  134,    0,  134,    0,
  134,    0,  181,  182,    0,    0,   98,  116,  117,  118,
  119,    0,  134,  134,    0,  134,  141,  141,  141,    0,
  141,    0,  141,    0,  153,   92,    0,   92,  219,  221,
    0,    0,    0,    0,  141,  141,    0,  141,    0,  140,
  140,  140,    0,  140,    0,  140,    0,    0,  178,  179,
    0,    0,    0,  166,  138,  180,  138,  140,  140,   94,
  140,  118,    9,  118,    0,  118,    9,   11,    0,    0,
   95,   11,    0,    0,    0,    0,  134,    0,  134,  118,
  118,    0,  118,  122,    0,  122,    0,  122,  273,    0,
    0,    0,    0,   98,    0,  274,    0,    0,  141,    0,
  141,  122,  122,    0,  122,  154,   60,    6,    0,    0,
    7,    8,   56,   56,   60,    6,    0,  200,    7,    8,
   10,  140,    0,  140,   97,   12,    0,    0,   10,    0,
    0,    0,   56,   12,   48,  138,  138,   48,  138,    0,
  138,    0,   97,  118,    0,  118,    0,    0,    0,    0,
   57,   57,   53,   53,    0,    0,    0,    0,   92,   92,
   92,   92,   92,   92,   92,  122,    0,  122,    0,    0,
   57,    0,   53,   92,    0,    0,    0,    0,   92,    0,
    0,   91,    0,    0,    0,    0,    0,  138,  138,  138,
  138,  138,  138,  138,    0,    0,  138,  138,  138,  138,
  138,    0,  138,    0,    0,    0,   97,  138,   97,  134,
  134,  134,  134,  134,  134,  134,    0,    0,  134,  134,
  134,  134,  134,    0,  134,    0,    0,    0,    0,  134,
    0,  141,  141,  141,  141,  141,  141,  141,   13,    0,
  141,  141,  141,  141,  141,   91,  141,   91,    0,    0,
    0,  141,    0,    0,  140,  140,  140,  140,  140,  140,
  140,    0,    0,  140,  140,  140,  140,  140,    0,  140,
    0,  139,    0,  140,  140,    0,  118,  118,  118,  118,
  118,  118,  118,    0,    0,  118,  118,  118,  118,  118,
    0,  118,   62,    0,  272,    0,  118,    0,  122,  122,
  122,  122,  122,  122,  122,    0,    0,  122,  122,  122,
  122,  122,  116,  122,  116,    0,  116,   30,  122,    0,
    0,    0,    0,    0,   68,    0,    0,    0,    0,    0,
  116,  116,    0,  116,  119,    0,  119,    0,  119,   97,
   97,   97,   97,   97,   97,   97,  192,    0,   70,    0,
   48,    0,  119,  119,   97,  119,   48,    0,    0,   97,
    0,    0,  138,   48,    0,    0,    0,   48,   48,   90,
    0,    4,    5,    6,    0,  152,    7,    8,   91,   91,
   91,   91,   91,   91,   91,    9,   10,    0,    0,    0,
   11,   12,    0,   91,  116,    0,  116,    0,   91,    0,
    0,    0,    0,    0,    0,   60,    6,  137,  138,    7,
    8,    0,    0,    0,    0,    0,  119,    0,  119,   10,
    0,    0,    0,    0,   12,    0,   60,    6,   60,    6,
    7,    8,    7,    8,  161,    0,    0,    0,    0,    0,
   10,   61,   10,    0,    0,   12,    0,   12,    4,    5,
    6,    0,    0,    7,    8,    4,    5,    6,  248,    0,
    7,    8,    9,   10,    0,  227,    0,   11,   12,    9,
   10,    0,    0,    0,   11,   12,    0,    4,    5,    6,
    5,    6,    7,    8,    7,    8,  152,    0,    0,    0,
    0,    9,   10,    9,   10,    0,   11,   12,   11,   12,
    0,    5,    6,  152,    0,    7,    8,   60,    6,  137,
  207,    7,    8,    0,    9,   10,    0,    0,    0,   11,
   12,   10,    0,  275,    0,  276,   12,  116,  116,  116,
  116,  116,  116,  116,    0,    0,  116,  116,  116,  116,
  116,    0,  116,    0,    0,    0,    0,  116,    0,  119,
  119,  119,  119,  119,  119,  119,    0,    0,  119,  119,
  119,  119,  119,    0,  119,    0,    5,    6,    0,  119,
    7,    8,   36,   36,   36,    0,    0,   36,   36,    9,
   10,    0,    0,    0,   11,   12,    0,   36,    0,    0,
    5,    6,   36,    0,    7,    8,  226,   60,    6,    0,
    0,    7,    8,    9,   10,    0,    0,  166,   11,   12,
    0,   10,    0,   94,    0,    0,   12,    0,   60,    6,
    9,  249,    7,    8,   95,   11,    0,    0,    0,    0,
    0,    0,   10,    0,    0,   60,    6,   12,    0,    7,
    8,   50,   57,   57,    0,    0,    0,    0,    0,   10,
    0,    0,    0,    0,   12,   60,    6,   60,    6,    7,
    8,    7,    8,    0,    0,    0,    0,    0,   91,   10,
    0,   10,   57,    0,   12,    0,   12,    0,   50,   60,
    6,    0,    0,    7,    8,    0,    0,    0,    0,    0,
  149,    0,    0,   10,    0,    0,   50,    0,   12,    0,
   50,  158,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  199,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   42,   43,   59,   45,   10,   47,   44,    1,    2,
   44,   40,   41,   42,   43,   59,   45,   10,   47,   59,
   13,   14,   61,   40,   61,   59,   65,   99,   59,   59,
   43,   89,   45,    2,   42,  123,   59,   51,   31,   47,
   59,  257,   40,  270,   13,    8,   51,   60,  275,   62,
   42,   43,   45,   45,   43,   47,   45,   62,   51,   64,
  276,   40,   41,   42,   43,   40,   45,   41,   47,   62,
   43,   64,   45,   59,   37,  123,   69,   44,   40,   41,
   42,   43,   44,   45,   40,   47,   40,   41,   42,   43,
   44,   45,   59,   47,   40,   40,   41,   42,   43,   59,
   45,   44,   47,   14,   40,   40,   42,   43,   86,   45,
  168,   47,   59,  106,  186,   40,   59,  156,  276,   40,
   31,   42,   43,  137,   45,  125,   47,   41,   61,   43,
  123,   45,  137,   40,  139,   42,   43,  106,   45,   41,
   47,   43,   59,   45,  137,   41,  139,   40,  153,   42,
   43,   59,   45,   26,   47,   43,   59,   45,   69,   41,
  153,   41,   42,   43,   43,   45,   45,   47,   43,  257,
   45,   59,  257,   42,   43,  269,   45,   44,   47,  256,
   42,   43,  240,   45,   59,   47,  164,  165,   43,   41,
   45,  278,  279,   59,   61,   59,  201,  256,  257,   43,
  193,   45,   59,  259,  260,  259,  260,   41,  201,   59,
   83,   84,  256,   43,  101,   45,  257,  278,  279,  256,
  260,  257,  256,  257,   43,  256,   45,  256,  257,  259,
  260,  272,  273,  256,  263,  276,  277,  278,  279,  280,
  257,  270,  276,  256,  273,  274,  275,  276,  277,  278,
  279,  280,  265,  266,  267,  268,  260,  257,  258,  276,
  265,  261,  262,  257,  256,  257,  264,  272,  273,  274,
  270,  271,  265,  160,  260,  275,  276,  256,  257,  272,
  273,  274,  193,  256,  276,  277,  278,  279,  280,  278,
  279,  280,  256,  257,  273,  257,  256,  276,  277,  278,
  279,  280,  189,  257,  257,  278,  279,  280,  264,  256,
   59,  273,  257,  256,  276,  277,  278,  279,  280,  273,
  256,  257,  276,  277,  278,  279,  280,   41,  273,  264,
   44,  276,  277,  278,  279,  280,  257,  273,   59,  256,
  276,  277,  278,  279,  280,  134,  135,   41,  256,  256,
  257,   59,  273,  256,  256,  276,  277,  278,  279,  280,
   59,  257,    0,    0,  257,    0,  273,  263,  256,  276,
  277,  278,  279,  280,  270,   62,  256,  257,  274,  275,
  273,  256,    0,  276,  277,  278,  279,  280,  257,  256,
   44,  269,  256,  273,  256,  257,  276,  277,  278,  279,
  280,  256,  257,   67,  273,   59,   59,  276,  277,  278,
  279,  280,  256,  257,  276,  277,  278,  279,  280,   46,
   47,  276,  277,  278,  279,  280,  256,  257,   44,  269,
   44,   34,  276,  277,  278,  279,  280,  256,  257,  139,
  140,  141,  142,   59,  269,   59,  276,  277,  278,  279,
  280,   59,  139,  256,  257,   43,  168,  276,  277,  278,
  279,  280,   41,   42,   43,   -1,   45,   -1,   47,   -1,
  123,   -1,  125,  276,  277,  278,  279,   -1,  257,   -1,
   59,   60,   85,   62,   41,   42,   43,   -1,   45,   -1,
   47,   -1,   95,   96,   -1,   -1,   99,  276,  277,  278,
  279,   -1,   59,   60,   -1,   62,   41,   42,   43,   -1,
   45,   -1,   47,   -1,  201,  123,   -1,  125,  145,  146,
   -1,   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   -1,  256,  257,
   -1,   -1,   -1,  257,  123,  263,  125,   59,   60,  263,
   62,   41,  270,   43,   -1,   45,  270,  275,   -1,   -1,
  274,  275,   -1,   -1,   -1,   -1,  123,   -1,  125,   59,
   60,   -1,   62,   41,   -1,   43,   -1,   45,  265,   -1,
   -1,   -1,   -1,  186,   -1,  272,   -1,   -1,  123,   -1,
  125,   59,   60,   -1,   62,  256,  257,  258,   -1,   -1,
  261,  262,  256,  257,  257,  258,   -1,  260,  261,  262,
  271,  123,   -1,  125,   41,  276,   -1,   -1,  271,   -1,
   -1,   -1,  276,  276,   41,   42,   43,   44,   45,   -1,
   47,   -1,   59,  123,   -1,  125,   -1,   -1,   -1,   -1,
  256,  257,  256,  257,   -1,   -1,   -1,   -1,  256,  257,
  258,  259,  260,  261,  262,  123,   -1,  125,   -1,   -1,
  276,   -1,  276,  271,   -1,   -1,   -1,   -1,  276,   -1,
   -1,   59,   -1,   -1,   -1,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,   -1,  271,   -1,   -1,   -1,  123,  276,  125,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,  123,   -1,
  265,  266,  267,  268,  269,  123,  271,  125,   -1,   -1,
   -1,  276,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,
   -1,  123,   -1,  125,  276,   -1,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,  266,  267,  268,  269,
   -1,  271,  123,   -1,  123,   -1,  276,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,   41,  271,   43,   -1,   45,  125,  276,   -1,
   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   41,   -1,   43,   -1,   45,  256,
  257,  258,  259,  260,  261,  262,  125,   -1,  125,   -1,
  257,   -1,   59,   60,  271,   62,  263,   -1,   -1,  276,
   -1,   -1,  269,  270,   -1,   -1,   -1,  274,  275,  125,
   -1,  256,  257,  258,   -1,  125,  261,  262,  256,  257,
  258,  259,  260,  261,  262,  270,  271,   -1,   -1,   -1,
  275,  276,   -1,  271,  123,   -1,  125,   -1,  276,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,  271,
   -1,   -1,   -1,   -1,  276,   -1,  257,  258,  257,  258,
  261,  262,  261,  262,  125,   -1,   -1,   -1,   -1,   -1,
  271,  272,  271,   -1,   -1,  276,   -1,  276,  256,  257,
  258,   -1,   -1,  261,  262,  256,  257,  258,  125,   -1,
  261,  262,  270,  271,   -1,  125,   -1,  275,  276,  270,
  271,   -1,   -1,   -1,  275,  276,   -1,  256,  257,  258,
  257,  258,  261,  262,  261,  262,  125,   -1,   -1,   -1,
   -1,  270,  271,  270,  271,   -1,  275,  276,  275,  276,
   -1,  257,  258,  125,   -1,  261,  262,  257,  258,  259,
  260,  261,  262,   -1,  270,  271,   -1,   -1,   -1,  275,
  276,  271,   -1,  125,   -1,  125,  276,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,   -1,  271,   -1,   -1,   -1,   -1,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,   -1,  257,  258,   -1,  276,
  261,  262,  256,  257,  258,   -1,   -1,  261,  262,  270,
  271,   -1,   -1,   -1,  275,  276,   -1,  271,   -1,   -1,
  257,  258,  276,   -1,  261,  262,  256,  257,  258,   -1,
   -1,  261,  262,  270,  271,   -1,   -1,  257,  275,  276,
   -1,  271,   -1,  263,   -1,   -1,  276,   -1,  257,  258,
  270,  260,  261,  262,  274,  275,   -1,   -1,   -1,   -1,
   -1,   -1,  271,   -1,   -1,  257,  258,  276,   -1,  261,
  262,    6,    7,    8,   -1,   -1,   -1,   -1,   -1,  271,
   -1,   -1,   -1,   -1,  276,  257,  258,  257,  258,  261,
  262,  261,  262,   -1,   -1,   -1,   -1,   -1,   33,  271,
   -1,  271,   37,   -1,  276,   -1,  276,   -1,   43,  257,
  258,   -1,   -1,  261,  262,   -1,   -1,   -1,   -1,   -1,
   55,   -1,   -1,  271,   -1,   -1,   61,   -1,  276,   -1,
   65,   66,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  136,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  156,
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
null,null,null,null,null,null,null,"ID","IF","ELSE","ENDIF","PRINT","RETURN",
"LAMBDA","ASIGNAR","MENORIGUAL","MAYORIGUAL","IGUALIGUAL","DISTINTO","FLECHA",
"LONG","DO","UNTIL","TRUNC","CR","STRING","IDCOMP","CADENASTR","CTEL","CTEF",
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
"expresion : '-' error",
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
"factor : '-' llamada_funcion",
"factor : '-' IDCOMP",
"factor : '-' ID",
"factor : '-' CADENASTR",
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
"constante : '+' CTEL",
"constante : '+' CTEF",
};

//#line 443 "parser_test.y"
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
    ParserVal val = al.getYylval();

    if (val != null){
        this.yylval = val;
        errManager.debug("Se almaceno el yylval " + val);
    }
    else
        this.yylval = null;

    return token;
}

public void tratarNegativos(String lexemaAnterior){
    errManager.debug("Tratando numero negativo " + lexemaAnterior,al.getLine());
    Atributo atributoAnterior = al.ts.obtener(lexemaAnterior);
    String nuevoLexema = "-" + lexemaAnterior;
    if(atributoAnterior.ref > 1){
        Atributo atributoNuevo = new Atributo(Atributo.longType,-1 * atributoAnterior.numValue);
        al.ts.insertar(nuevoLexema, atributoNuevo);
        atributoAnterior.ref -= 1;
    } else {
        atributoAnterior.numValue = -1 * atributoAnterior.numValue;
        al.ts.modificar(lexemaAnterior, nuevoLexema, atributoAnterior);
    }
}

public void checkearRango(String lexemaActual){
    errManager.debug("Checkeando rango de constante " + lexemaActual,al.getLine());
    Atributo atributoActual = al.ts.obtener(lexemaActual);
    if (atributoActual.ref == 1){
        if (atributoActual.numValue == (2147483648L)) {
            errManager.warning("Constante long fuera de rango, truncando.");
            atributoActual.numValue -= 1;
        }
    }

}

public void run()
{
  yyparse();
  errManager.debug("Tabla de simbolos resultante" + '\n' +  al.ts.toString());

}

public void yyerror(String s) {
    System.err.println("Error generico: " + s);
    System.err.println("Linea: " + al.getLine());
}
//#line 792 "Parser.java"
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
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 3:
//#line 28 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 4:
//#line 30 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 5:
//#line 32 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 6:
//#line 34 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 7:
//#line 36 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 8:
//#line 38 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 9:
//#line 40 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 13:
//#line 51 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 63 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
//#line 65 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 67 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 24:
//#line 72 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 25:
//#line 74 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 26:
//#line 76 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 27:
//#line 78 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 29:
//#line 83 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 31:
//#line 88 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 32:
//#line 89 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 35:
//#line 97 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 43:
//#line 115 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 44:
//#line 120 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 45:
//#line 122 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 46:
//#line 124 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 47:
//#line 126 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 48:
//#line 128 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 49:
//#line 130 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
//#line 132 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 51:
//#line 134 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 136 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 53:
//#line 141 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
//#line 143 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 55:
//#line 145 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 56:
//#line 147 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 57:
//#line 149 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 58:
//#line 151 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 59:
//#line 153 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 60:
//#line 160 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 65:
//#line 176 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
//#line 178 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 67:
//#line 180 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
//#line 182 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 69:
//#line 184 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 186 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 71:
//#line 188 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 190 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 73:
//#line 192 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 194 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 75:
//#line 196 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 198 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 77:
//#line 200 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 78:
//#line 202 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 81:
//#line 209 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 211 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 83:
//#line 213 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
//#line 215 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
//#line 217 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 86:
//#line 219 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 87:
//#line 221 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 88:
//#line 223 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 90:
//#line 229 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 91:
//#line 231 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 92:
//#line 233 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 94:
//#line 239 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 95:
//#line 241 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 96:
//#line 243 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 97:
//#line 249 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 98:
//#line 251 "parser_test.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 99:
//#line 256 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 100:
//#line 258 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 101:
//#line 260 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 102:
//#line 262 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 103:
//#line 264 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 112:
//#line 283 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 113:
//#line 285 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 114:
//#line 287 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 115:
//#line 289 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 117:
//#line 295 "parser_test.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 118:
//#line 297 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 120:
//#line 300 "parser_test.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 121:
//#line 302 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 123:
//#line 305 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 124:
//#line 307 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 126:
//#line 313 "parser_test.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 127:
//#line 315 "parser_test.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 128:
//#line 317 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 130:
//#line 320 "parser_test.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 131:
//#line 322 "parser_test.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 132:
//#line 324 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 138:
//#line 334 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 140:
//#line 337 "parser_test.y"
{ errManager.debug("Identificador con -", al.getLine()); }
break;
case 141:
//#line 339 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 142:
//#line 341 "parser_test.y"
{ errManager.error("Operador '-' no permitido en este contexto", al.getLine()); }
break;
case 143:
//#line 346 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 144:
//#line 348 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 145:
//#line 350 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 146:
//#line 352 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 147:
//#line 357 "parser_test.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 148:
//#line 361 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 149:
//#line 363 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 150:
//#line 365 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 151:
//#line 367 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 152:
//#line 372 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 154:
//#line 378 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 155:
//#line 380 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 156:
//#line 382 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 157:
//#line 384 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 158:
//#line 386 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 159:
//#line 391 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 160:
//#line 393 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 161:
//#line 395 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 162:
//#line 397 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 167:
//#line 412 "parser_test.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 168:
//#line 417 "parser_test.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 169:
//#line 422 "parser_test.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 170:
//#line 427 "parser_test.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 171:
//#line 432 "parser_test.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 172:
//#line 434 "parser_test.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 173:
//#line 439 "parser_test.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1439 "Parser.java"
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
//## The -Jnorun option was used ##
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
