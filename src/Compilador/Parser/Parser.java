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



//#line 6 "parser.y"
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
    import Compilador.Lexer.Atributo;
    import Compilador.Generador.Generador;
//#line 23 "Parser.java"




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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   20,   22,   22,   23,   23,   23,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   25,   31,   31,
   31,   31,   33,   33,   32,   32,   32,   21,   21,   21,
   35,   35,   34,   34,   34,   37,   37,   37,   36,   36,
   36,   36,   36,   36,   36,   36,   36,   13,   13,   13,
   13,   13,   13,   13,   38,   38,    5,    5,   27,   27,
   39,   39,   39,   39,   39,   39,   39,   39,   39,   39,
   39,   39,   39,   39,   15,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   17,   18,   18,   18,   18,
   41,   41,   41,   41,   41,   19,   19,   40,   40,   40,
   40,   40,    9,    9,    9,    9,    9,    9,   14,   14,
   26,   26,   26,   26,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    2,    2,    2,    2,    2,    7,
    7,    7,    7,   28,   11,   11,   10,   10,   10,   10,
    8,    8,   29,    6,    6,    6,    6,    6,    6,   30,
   30,   30,   30,   12,   12,   42,   42,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    3,    3,    2,    4,    3,    3,    2,    2,    2,    2,
    1,    1,    2,    1,    1,    2,    2,    1,    1,    1,
    2,    2,    2,    1,    2,    2,    2,    2,    3,    2,
    2,    3,    3,    2,    3,    2,    0,    1,    2,    2,
    1,    2,    1,    3,    2,    1,    3,    2,    3,    2,
    3,    2,    1,    2,    3,    2,    2,    2,    3,    2,
    1,    2,    3,    2,    3,    2,    1,    1,    1,    1,
    4,    4,    3,    3,    4,    4,    3,    3,    4,    3,
    4,    3,    3,    3,    2,    4,    4,    4,    4,    3,
    3,    2,    4,    4,    2,    1,    3,    2,    3,    1,
    3,    3,    2,    2,    3,    3,    2,    5,    5,    4,
    4,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    4,    4,    4,    3,    3,    1,    3,    3,    2,
    1,    2,    2,    3,    2,    3,    2,    3,    2,    3,
    2,    1,    1,    1,    1,    1,    2,    2,    2,    4,
    4,    4,    4,    2,    1,    3,    4,    3,    3,    1,
    4,    5,    2,    3,    2,    3,    2,    2,    1,    4,
    5,    3,    4,    1,    3,    3,    1,    1,    1,    2,
    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  119,    0,  120,    0,   13,    0,    0,    0,    0,    0,
    0,   38,   14,   15,   18,   19,   20,    0,    0,   24,
    0,   69,   70,   10,    0,    0,    9,    0,   40,    0,
    0,   30,    0,    0,    0,  178,  179,  182,    0,    0,
    0,    0,    0,  144,  142,    0,    0,  145,   85,    0,
    0,  169,    0,  155,  154,    0,  163,    0,    0,    0,
   41,    0,    0,    0,    0,   25,   21,    0,    0,    0,
   17,   64,   60,   16,    0,    0,    0,    0,   31,   96,
    0,    0,    0,    0,    0,    0,    0,    6,   39,   26,
   22,   27,   23,    0,   28,    5,    2,    0,    0,    0,
    0,    0,    0,    0,   34,    0,    0,    0,   68,    0,
    0,   43,    0,   46,    0,  133,  132,    0,  183,  184,
  130,    0,    0,  180,  181,  147,  137,    0,    0,  135,
  141,  139,    0,    0,  107,  113,  114,  115,  116,  117,
  118,    0,    0,    0,   98,  168,    0,  167,    0,  165,
    0,    0,  104,    0,  103,   42,    0,    0,    0,    0,
    0,    0,  175,  172,    0,  177,    0,   63,   59,   29,
   32,   53,   83,    0,   82,    0,   80,   84,   95,    0,
    0,   92,    0,    0,    0,   78,   74,    0,   77,   73,
   36,    0,    4,  124,  123,  153,   54,   56,   50,    0,
    0,    0,    0,   66,    0,    0,    0,   57,   52,   33,
    0,   45,  151,    0,   48,   99,   97,  136,  134,  140,
  138,  126,    0,  129,    0,    0,  156,  166,  164,  111,
  110,  105,  102,  101,    0,  112,  122,  121,  152,  150,
    0,  173,  170,    0,   81,   79,    0,    0,    0,   91,
    0,   90,   76,   72,   75,   71,   35,   55,   51,   49,
    0,    0,   65,    0,  159,   44,   47,  109,  108,  171,
  176,   94,   93,   89,   87,   88,   86,    0,  161,  157,
  162,
};
final static short yydgoto[] = {                          4,
   54,   55,   56,  116,  117,   64,   58,  118,  154,  119,
   65,   17,   18,   19,   20,   94,   95,   59,   60,    5,
   21,   22,   23,   24,   25,   26,   27,   28,   29,   30,
   31,  105,   42,  121,   72,  122,  123,  124,   32,   33,
   73,  177,
};
final static short yysindex[] = {                       -91,
    0,  749,  809,    0,  816,   61,  -32,  105,   48,   72,
    0,  781,    0,  -10,    0,  -54,  -40,   90,  -38,  770,
  859,    0,    0,    0,    0,    0,    0,  -25,  -13,    0,
 -105,    0,    0,    0,  870,  -19,    0,  890,    0,  156,
  -28,    0,   10,   11,   89,    0,    0,    0,  156,   44,
  246,  -23,  181,    0,    0,  111,  702,    0,    0,  128,
  132,    0,  361,    0,    0,  142,    0,   -3,  105,  915,
    0,  945,   58,  156,   85,    0,    0,   70, -112,   14,
    0,    0,    0,    0, -216,  136,  136,  219,    0,    0,
  122,   20,  837,  131,  742,  137,  141,    0,    0,    0,
    0,    0,    0,  848,    0,    0,    0,  -70,   51,  152,
   10,  -60,  366,   65,    0,  127, -207,  793,    0,   26,
  328,    0,   24,    0,   97,    0,    0,  -41,    0,    0,
    0,   10,   89,    0,    0,    0,    0,   44, -116,    0,
    0,    0,  221,  230,    0,    0,    0,    0,    0,    0,
    0,  167,  194,  156,    0,    0,  164,    0,  -12,    0,
   13,   88,    0,  922,    0,    0,  105,  166,  135,  170,
   38,   30,    0,    0,   54,    0,  -16,    0,    0,    0,
    0,    0,    0,  239,    0,  247,    0,    0,    0,  -37,
  438,    0,  -33,  -11,   18,    0,    0,   43,    0,    0,
    0,  892,    0,    0,    0,    0,    0,    0,    0,   28,
  169, -183,   68,    0,  100,  343,  685,    0,    0,    0,
  628,    0,    0,  114,    0,    0,    0,    0,    0,    0,
    0,    0,  111,    0,  111,  127,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,    0,    0,    0,    0,
  -21,    0,    0,   30,    0,    0,  284,  309,   57,    0,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  117,  341,    0,  938,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  345,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,  395,    0,    0,    0,    0,  408,    0,    0,    0,
    0,    0,    0,  -34,    0,    0,    0,    0,    0,    0,
  403,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,    0,    0,  407,    0,  414,    0,    0,
    0,    0,  421,    0,  470,    0,    0,    0,    0,  537,
    0,    0,    0,    0,    0,  568,    0,    0,    0,  718,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  531,  562,    0,    0,    0,
    0,    0,    0,    0,    0,  961,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  416,    0,    0,
  612,    0,    0,    0,    0, -118,    0,  -65,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  492,  514,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  961,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  590,    0,  661,  308,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -39,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  -36,  375,  287,  891,    0,  123,    4,    0,    0,    0,
    0,    0,    0,  775,    0,  263,    0,  119,  370,    0,
   42,   86,    0,   -9,    0,    0,    0,    0,    0,    0,
    0,    0,  293,    0,  226,  -86,  355,  -53,    0,    0,
   16,  269,
};
final static int YYTABLESIZE=1237;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        227,
   37,   88,   71,   79,   77,   16,   16,   41,   16,  174,
   96,  114,  115,   52,   50,   16,   51,  104,   53,  138,
   80,  139,  254,   16,   16,  260,  174,  254,  239,   75,
  152,    3,  153,  101,  222,   97,  125,  280,   16,   39,
  178,   16,  253,  176,   35,  103,   38,  262,  214,  125,
   66,   62,   52,   50,  136,   51,  138,   53,  175,  179,
   71,  215,  166,  114,  223,   52,   50,  224,   51,  225,
   53,  241,  138,   16,  175,   16,  264,  114,  250,   52,
   50,  224,   51,   71,   53,  193,   11,   61,   62,   52,
   50,   13,   51,  152,   53,  153,   16,   49,   16,   52,
   50,  266,   51,  279,   53,  107,   99,   16,   71,  205,
  194,   66,   62,   52,   50,  285,   51,  225,   53,   39,
   99,   16,  287,   99,  114,   37,   52,   50,   75,   51,
  172,   53,   67,   85,  276,  176,  114,   67,   52,   50,
  132,   51,  136,   53,   49,  202,   52,   50,   84,   51,
   67,   53,  143,  114,  166,   52,   50,  144,   51,  133,
   53,  134,  135,  173,    1,    2,  127,   16,  155,  152,
  277,  153,  158,   52,   50,   88,   51,  152,   53,  153,
  183,   71,  158,   52,   50,  203,   51,  161,   53,  188,
  160,  168,  206,  248,   16,  196,  207,   52,   50,  199,
   51,   76,   53,  160,  237,   16,   71,  166,   52,  138,
  249,  139,  242,   53,  226,   78,  158,  281,   86,   16,
   16,  174,  257,  138,  246,  139,  259,  110,  111,  158,
  100,   40,  137,   43,  112,   52,  138,   87,  139,  252,
   53,   11,  102,  238,   44,  113,   13,   45,  261,   46,
   47,   48,   45,   74,   46,   47,   48,   37,   37,  115,
   40,   37,   37,  138,  166,  139,  126,   43,  240,  174,
   37,   37,  138,  263,  139,   37,   37,   16,   90,  184,
   43,  218,  219,   44,  268,  245,   45,   99,   46,   47,
   48,   46,   47,   48,   43,  164,   44,  255,  265,   45,
  278,   46,   47,   48,   43,  256,  204,   46,   47,   48,
   44,   89,  284,   45,   43,   46,   47,   48,  164,  286,
   44,  129,  130,   45,  272,   46,   47,   48,   43,  167,
   44,  134,  135,   45,   11,   46,   47,   48,  212,   13,
  170,   43,  282,  217,   44,   81,   82,   45,  106,   46,
   47,   48,  110,   43,  185,  187,  273,   44,  197,  200,
   45,   43,   46,   47,   48,   83,  106,  283,  220,   44,
   43,  221,   45,  288,   46,   47,   48,   44,  180,  181,
   45,  289,   46,   47,   48,  291,   44,  156,   43,   45,
  247,   46,   47,   48,   11,   90,  195,  156,   43,   90,
  198,  160,    8,  152,   44,  153,    3,   45,  157,   46,
   47,   48,   43,    7,   44,    1,  164,   45,  128,   46,
   47,   48,  232,   43,  269,  270,  140,  142,   44,  171,
  106,   45,  106,   46,   47,   48,  141,   43,  233,  235,
  251,  274,   45,    0,   46,   47,   48,    0,    0,  234,
   43,   61,    0,    0,    0,    0,   45,    0,   46,   47,
   48,  146,  146,  146,    0,  146,   61,  146,    0,   45,
    0,   46,   47,   48,    0,  182,  228,   43,    0,  146,
  146,  112,  146,    0,    0,  230,   43,    0,   11,    0,
    0,    0,  113,   13,    0,    0,   45,    0,   46,   47,
   48,  131,  132,    0,    0,   45,    0,   46,   47,   48,
  143,  143,  143,    0,  143,    0,  143,  229,  231,    0,
    0,  133,    0,  134,  135,    0,    0,    0,  143,  143,
    0,  143,  149,  149,  149,    0,  149,    0,  149,    0,
    0,    0,    0,  146,    0,  146,    0,    0,    0,    0,
  149,  149,    0,  149,  148,  148,  148,    0,  148,    0,
  148,    0,  163,  106,  106,  106,  106,  106,  106,  106,
    0,    0,  148,  148,   62,  148,    0,  127,  106,  127,
    0,  127,    0,  106,  182,    0,    0,    0,    0,   62,
  112,    0,  143,    0,  143,  127,  127,   11,  127,   68,
    8,  113,   13,    9,   10,   58,    0,    0,  131,    0,
  131,    0,  131,   12,  149,    0,  149,    0,   14,    0,
   58,  208,  209,    0,    0,    0,  131,  131,  210,  131,
  125,    0,  125,    0,  125,   11,  148,    0,  148,    0,
   13,    0,    0,    0,    0,    0,    0,    0,  125,  125,
    0,  125,   53,  146,  146,   53,  146,    0,  146,  127,
    0,  127,    0,   61,   61,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  146,  146,  146,  146,
  146,  146,  146,   61,    0,  146,  146,  146,  146,  146,
  131,  146,  131,  162,   68,    8,  146,  258,    9,   10,
    0,  128,    0,  128,    0,  128,    0,    0,   12,    0,
    0,    0,  125,   14,  125,    0,    0,    0,    0,  128,
  128,    0,  128,    0,    0,  143,  143,  143,  143,  143,
  143,  143,    0,    0,  143,  143,  143,  143,  143,    0,
  143,    0,    0,    0,  152,  143,  153,  149,  149,  149,
  149,  149,  149,  149,    0,    0,  149,  149,  149,  149,
  149,  151,  149,  150,    0,    0,    0,  149,    0,  148,
  148,  148,  148,  148,  148,  148,  100,    0,  148,  148,
  148,  148,  148,  128,  148,  128,   62,   62,    0,  148,
    0,    0,  127,  127,  127,  127,  127,  127,  127,    0,
  192,  127,  127,  127,  127,  127,   62,  127,    0,  275,
    0,    0,  127,    0,    0,  120,    0,   58,   58,    0,
    0,    0,    0,  131,  131,  131,  131,  131,  131,  131,
    0,    0,  131,  131,  131,  131,  131,   58,  131,    0,
  100,    0,  100,  131,    0,  125,  125,  125,  125,  125,
  125,  125,    0,    0,  125,  125,  125,  125,  125,    0,
  125,    0,  120,    0,  191,  125,  190,  146,   53,    0,
    0,   15,    0,    0,   53,    0,    0,    0,    0,    0,
  146,   53,    0,    0,  182,   53,   53,  211,  213,    0,
  112,    0,   93,    0,   92,  120,    0,   11,   57,   63,
   63,  113,   13,   70,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  216,  128,  128,  128,  128,
  128,  128,  128,    0,    0,  128,  128,  128,  128,  128,
  109,  128,    0,   34,   63,    0,  128,    0,    0,   57,
   37,   68,    8,    0,    0,    9,   10,    0,    0,    0,
    0,  159,    0,    0,    0,   12,  159,  145,    0,   57,
   14,  163,    0,   57,  169,    0,  146,  147,  148,  149,
    0,    0,  201,  100,  100,  100,  100,  100,  100,  100,
    0,    0,    0,   98,    0,    0,  271,    0,  100,    0,
    0,    0,    0,  100,  106,  120,    0,    0,   68,    8,
    0,  189,    9,   10,    6,    7,    8,    0,    0,    9,
   10,    0,   12,    0,  108,    0,  267,   14,   11,   12,
    0,    0,    0,   13,   14,    0,   68,    8,   90,   91,
    9,   10,    0,    0,    0,    0,    0,   68,    8,  163,
   12,    9,   10,    0,  236,   14,  244,    0,    0,   68,
    8,   12,   69,    9,   10,    0,   14,   57,    0,    0,
    0,    0,  290,   12,    6,    7,    8,    0,   14,    9,
   10,   36,    7,    8,    0,    0,    9,   10,   11,   12,
    0,    0,    0,   13,   14,   11,   12,    0,    0,    0,
   13,   14,  162,   68,    8,   90,  186,    9,   10,    0,
    0,    0,    0,    6,    7,    8,    0,   12,    9,   10,
    0,    0,   14,    0,    0,    7,    8,   11,   12,    9,
   10,    0,   13,   14,    0,    0,    7,    8,   11,   12,
    9,   10,    0,   13,   14,    0,    0,    0,    0,   11,
   12,    0,    0,    0,   13,   14,    7,    8,    7,    8,
    9,   10,    9,   10,    0,    0,    0,    0,    0,   11,
   12,   11,   12,    0,   13,   14,   13,   14,    0,    0,
  162,   68,    8,    0,    0,    9,   10,  243,   68,    8,
    0,    0,    9,   10,    0,   12,    0,    0,    0,    0,
   14,    0,   12,    0,   68,    8,    0,   14,    9,   10,
  165,   68,    8,    0,    0,    9,   10,    0,   12,    0,
    0,    0,    0,   14,    0,   12,   41,   41,   41,    0,
   14,   41,   41,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,   12,   44,   59,    2,    3,   40,    5,   44,
   20,   40,   41,   42,   43,   12,   45,  123,   47,   43,
   61,   45,   44,   20,   21,   59,   61,   44,   41,   40,
   43,  123,   45,   59,  121,   20,   40,   59,   35,   59,
  257,   38,   59,   80,    3,   59,    5,   59,  256,   40,
   40,   41,   42,   43,   51,   45,   43,   47,   45,  276,
   70,  269,   72,   40,   41,   42,   43,   44,   45,  123,
   47,   59,   43,   70,   45,   72,   59,   40,   41,   42,
   43,   44,   45,   93,   47,   95,  270,   40,   41,   42,
   43,  275,   45,   43,   47,   45,   93,   40,   95,   42,
   43,   59,   45,   59,   47,  125,   21,  104,  118,   59,
   95,   40,   41,   42,   43,   59,   45,  171,   47,   59,
   35,  118,   59,   38,   40,  125,   42,   43,   40,   45,
   61,   47,   10,   44,  221,  172,   40,  256,   42,   43,
  257,   45,  139,   47,   40,  104,   42,   43,   59,   45,
  269,   47,   42,   40,  164,   42,   43,   47,   45,  276,
   47,  278,  279,  276,  256,  257,   44,  164,   41,   43,
  224,   45,   41,   42,   43,   40,   45,   43,   47,   45,
   59,  191,   41,   42,   43,  256,   45,   69,   47,   59,
  256,   73,   41,   59,  191,   59,  257,   42,   43,   59,
   45,  256,   47,  269,   41,  202,  216,  217,   42,   43,
   41,   45,  125,   47,  256,  256,  256,  254,  257,  216,
  217,  256,  260,   43,   59,   45,  260,  256,  257,  269,
  256,  264,  256,  257,  263,   42,   43,  276,   45,  256,
   47,  270,  256,  256,  273,  274,  275,  276,  260,  278,
  279,  280,  276,  264,  278,  279,  280,  257,  258,   41,
  264,  261,  262,   43,  274,   45,  256,  257,  256,  256,
  270,  271,   43,  256,   45,  275,  276,  274,  259,  260,
  257,  256,  257,  273,  257,  167,  276,  202,  278,  279,
  280,  278,  279,  280,  257,   70,  273,   59,  256,  276,
  256,  278,  279,  280,  257,   59,  256,  278,  279,  280,
  273,   19,  256,  276,  257,  278,  279,  280,   93,  256,
  273,  278,  279,  276,  257,  278,  279,  280,  257,  272,
  273,  278,  279,  276,  270,  278,  279,  280,  274,  275,
  256,  257,   59,  118,  273,  256,  257,  276,   41,  278,
  279,  280,  256,  257,   92,   93,  257,  273,   96,   97,
  276,  257,  278,  279,  280,  276,   59,   59,   41,  273,
  257,   44,  276,  257,  278,  279,  280,  273,   86,   87,
  276,   41,  278,  279,  280,   41,  273,  256,  257,  276,
  256,  278,  279,  280,    0,  259,  260,  256,  257,  259,
  260,   41,    0,   43,  273,   45,    0,  276,  277,  278,
  279,  280,  257,    0,  273,    0,  191,  276,   49,  278,
  279,  280,  256,  257,  256,  257,   52,   53,  273,   75,
  123,  276,  125,  278,  279,  280,  256,  257,  152,  153,
  172,  216,  276,   -1,  278,  279,  280,   -1,   -1,  256,
  257,   44,   -1,   -1,   -1,   -1,  276,   -1,  278,  279,
  280,   41,   42,   43,   -1,   45,   59,   47,   -1,  276,
   -1,  278,  279,  280,   -1,  257,  256,  257,   -1,   59,
   60,  263,   62,   -1,   -1,  256,  257,   -1,  270,   -1,
   -1,   -1,  274,  275,   -1,   -1,  276,   -1,  278,  279,
  280,  256,  257,   -1,   -1,  276,   -1,  278,  279,  280,
   41,   42,   43,   -1,   45,   -1,   47,  143,  144,   -1,
   -1,  276,   -1,  278,  279,   -1,   -1,   -1,   59,   60,
   -1,   62,   41,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   41,   42,   43,   -1,   45,   -1,
   47,   -1,  125,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,   59,   60,   44,   62,   -1,   41,  271,   43,
   -1,   45,   -1,  276,  257,   -1,   -1,   -1,   -1,   59,
  263,   -1,  123,   -1,  125,   59,   60,  270,   62,  257,
  258,  274,  275,  261,  262,   44,   -1,   -1,   41,   -1,
   43,   -1,   45,  271,  123,   -1,  125,   -1,  276,   -1,
   59,  256,  257,   -1,   -1,   -1,   59,   60,  263,   62,
   41,   -1,   43,   -1,   45,  270,  123,   -1,  125,   -1,
  275,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   -1,   62,   41,   42,   43,   44,   45,   -1,   47,  123,
   -1,  125,   -1,  256,  257,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,  258,  259,
  260,  261,  262,  276,   -1,  265,  266,  267,  268,  269,
  123,  271,  125,  256,  257,  258,  276,  260,  261,  262,
   -1,   41,   -1,   43,   -1,   45,   -1,   -1,  271,   -1,
   -1,   -1,  123,  276,  125,   -1,   -1,   -1,   -1,   59,
   60,   -1,   62,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,   43,  276,   45,  256,  257,  258,
  259,  260,  261,  262,   -1,   -1,  265,  266,  267,  268,
  269,   60,  271,   62,   -1,   -1,   -1,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   59,   -1,  265,  266,
  267,  268,  269,  123,  271,  125,  256,  257,   -1,  276,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   59,  265,  266,  267,  268,  269,  276,  271,   -1,  125,
   -1,   -1,  276,   -1,   -1,   41,   -1,  256,  257,   -1,
   -1,   -1,   -1,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,  276,  271,   -1,
  123,   -1,  125,  276,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   88,   -1,  123,  276,  125,  256,  257,   -1,
   -1,  123,   -1,   -1,  263,   -1,   -1,   -1,   -1,   -1,
  269,  270,   -1,   -1,  257,  274,  275,  113,  114,   -1,
  263,   -1,  123,   -1,  125,  121,   -1,  270,    8,    9,
   10,  274,  275,  123,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  123,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,  266,  267,  268,  269,
   40,  271,   -1,  125,   44,   -1,  276,   -1,   -1,   49,
  125,  257,  258,   -1,   -1,  261,  262,   -1,   -1,   -1,
   -1,   61,   -1,   -1,   -1,  271,   66,  256,   -1,   69,
  276,  125,   -1,   73,   74,   -1,  265,  266,  267,  268,
   -1,   -1,  125,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,  125,   -1,   -1,  212,   -1,  271,   -1,
   -1,   -1,   -1,  276,  125,  221,   -1,   -1,  257,  258,
   -1,  260,  261,  262,  256,  257,  258,   -1,   -1,  261,
  262,   -1,  271,   -1,  125,   -1,  125,  276,  270,  271,
   -1,   -1,   -1,  275,  276,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,  257,  258,  125,
  271,  261,  262,   -1,  154,  276,  125,   -1,   -1,  257,
  258,  271,  272,  261,  262,   -1,  276,  167,   -1,   -1,
   -1,   -1,  125,  271,  256,  257,  258,   -1,  276,  261,
  262,  256,  257,  258,   -1,   -1,  261,  262,  270,  271,
   -1,   -1,   -1,  275,  276,  270,  271,   -1,   -1,   -1,
  275,  276,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,  256,  257,  258,   -1,  271,  261,  262,
   -1,   -1,  276,   -1,   -1,  257,  258,  270,  271,  261,
  262,   -1,  275,  276,   -1,   -1,  257,  258,  270,  271,
  261,  262,   -1,  275,  276,   -1,   -1,   -1,   -1,  270,
  271,   -1,   -1,   -1,  275,  276,  257,  258,  257,  258,
  261,  262,  261,  262,   -1,   -1,   -1,   -1,   -1,  270,
  271,  270,  271,   -1,  275,  276,  275,  276,   -1,   -1,
  256,  257,  258,   -1,   -1,  261,  262,  256,  257,  258,
   -1,   -1,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,
  276,   -1,  271,   -1,  257,  258,   -1,  276,  261,  262,
  256,  257,  258,   -1,   -1,  261,  262,   -1,  271,   -1,
   -1,   -1,   -1,  276,   -1,  271,  256,  257,  258,   -1,
  276,  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  271,   -1,   -1,   -1,   -1,  276,
};
}
final static short YYFINAL=4;
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
"programa : inicio_programa lista_sentencias '}'",
"programa : inicio_programa error '}'",
"programa : inicio_programa error",
"programa : inicio_programa lista_sentencias '}' error",
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : inicio_programa lista_sentencias",
"programa : ID lista_sentencias",
"programa : inicio_programa '}'",
"programa : '{' '}'",
"programa : ID",
"programa : error",
"inicio_programa : ID '{'",
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
"declaracion_funcion : inicio_funcion cuerpo_funcion_opt",
"inicio_funcion : tipo ID parametros_formales_opt",
"inicio_funcion : ID parametros_formales_opt",
"inicio_funcion : tipo parametros_formales_opt",
"inicio_funcion : tipo IDCOMP parametros_formales_opt",
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
"parametro_real_compuesto : parametro_real error",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF",
"control : do_until",
"sentencia_IF : inicio_if cuerpo_opt ENDIF ';'",
"sentencia_IF : inicio_if sentencia_ejecutable ENDIF ';'",
"sentencia_IF : inicio_if cuerpo_opt else_opt",
"sentencia_IF : inicio_if sentencia_ejecutable else_opt",
"sentencia_IF : inicio_if cuerpo_opt ENDIF error",
"sentencia_IF : inicio_if sentencia_ejecutable ENDIF error",
"sentencia_IF : inicio_if cuerpo_opt ';'",
"sentencia_IF : inicio_if sentencia_ejecutable ';'",
"sentencia_IF : inicio_if '{' ENDIF ';'",
"sentencia_IF : inicio_if '{' else_opt",
"sentencia_IF : inicio_if '}' ENDIF ';'",
"sentencia_IF : inicio_if '}' else_opt",
"sentencia_IF : inicio_if ENDIF ';'",
"sentencia_IF : inicio_if else_opt ';'",
"inicio_if : IF condicional_opt",
"else_opt : inicio_else cuerpo_opt ENDIF ';'",
"else_opt : inicio_else sentencia_ejecutable ENDIF ';'",
"else_opt : inicio_else cuerpo_opt ENDIF error",
"else_opt : inicio_else sentencia_ejecutable ENDIF error",
"else_opt : inicio_else cuerpo_opt ';'",
"else_opt : inicio_else sentencia_ejecutable ';'",
"else_opt : inicio_else ';'",
"else_opt : inicio_else '{' ENDIF ';'",
"else_opt : inicio_else '}' ENDIF ';'",
"else_opt : inicio_else ENDIF",
"inicio_else : ELSE",
"condicional_opt : '(' condicion ')'",
"condicional_opt : condicion ')'",
"condicional_opt : '(' condicion error",
"condicional_opt : condicion",
"cuerpo_opt : '{' lista_sentencias_ejecutables '}'",
"cuerpo_opt : '{' lista_sentencias_ejecutables error",
"cuerpo_opt : lista_sentencias_ejecutables error",
"cuerpo_opt : '{' '}'",
"cuerpo_opt : '{' error '}'",
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
"factor : llamada_funcion",
"factor : ID",
"factor : '-' llamada_funcion",
"factor : '-' IDCOMP",
"factor : '-' ID",
"llamada_funcion : IDCOMP '(' lista_parametros_reales ')'",
"llamada_funcion : ID '(' lista_parametros_reales ')'",
"llamada_funcion : IDCOMP '(' error ')'",
"llamada_funcion : ID '(' error ')'",
"print : PRINT cuerpo_print",
"cuerpo_print : cuerpo_expresion",
"cuerpo_print : '(' CADENASTR ')'",
"lambda : inicio_lambda '{' lista_sentencias_ejecutables '}'",
"lambda : inicio_lambda '{' lista_sentencias_ejecutables",
"lambda : inicio_lambda lista_sentencias_ejecutables '}'",
"lambda : inicio_lambda",
"inicio_lambda : '(' tipo ID ')'",
"inicio_lambda : '(' CR tipo ID ')'",
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

//#line 828 "parser.y"
private AnalizadorLexico al;
private ErrorManager errManager;
private static final ParserVal dummyParserVal = new ParserVal();
private Generador generador;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
    this.generador = new Generador();
    al.ts.insertar("-1L", new Atributo(0,-1,"auxiliar"));
    al.ts.insertar("0L", new Atributo(0,0,"auxiliar"));
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
		this.yylval = dummyParserVal;
        //this.yylval = null; esto no funciona.

    return token;
}

public void tratarNegativos(String lexemaAnterior){
    errManager.debug("Tratando numero negativo " + lexemaAnterior,al.getLine());
    Atributo atributoAnterior = al.ts.obtener(lexemaAnterior);
    String nuevoLexema = "-" + lexemaAnterior;
    Atributo atributoNuevo = new Atributo(Atributo.longType,-1 * atributoAnterior.numValue, Atributo.USO_CONSTANTE);
    al.ts.insertar(nuevoLexema, atributoNuevo);
}

public void checkearRango(String lexemaActual){
    errManager.debug("Checkeando rango de constante " + lexemaActual,al.getLine());
    Atributo atributoActual = al.ts.obtener(lexemaActual);
    if (atributoActual.numValue == (2147483648L)) {
        errManager.warning("Constante long fuera de rango, truncando.");
        atributoActual.numValue -= 1;
    }
}

public void declararVariable(String IDCOMP, int tipo){
    errManager.debug("Declaracion de variable detectada.",  al.getLine());
    // Verificar que el prefijo coincida con el ambito.
    if (generador.checkearAmbito(IDCOMP)){
        // Verificar si ya se encuentra declarada.
        if (generador.estaDeclarada(IDCOMP,al.ts)){
            errManager.error("Redeclaracion de variable no permitida.", al.getLine());
        } else {
            // Insertar entrada en Tabla de Simbolos de la variable. Esta reemplaza la del lexico.
            Atributo info = new Atributo(tipo, Atributo.USO_VARIABLE);
            info.declarado = true;
            al.ts.reemplazar(IDCOMP, info);
        }
    } else {
        errManager.error("El ambito declarado es incorrecto.", al.getLine());
    }
}

public void run()
{
    yyparse();
    errManager.debug("Tabla de simbolos resultante" + '\n' +  al.ts.toString());
    errManager.debug("Tercetos resultante" + '\n' +  generador.imprimirTercetos());
}

public void yyerror(String s) {
}
//#line 817 "Parser.java"
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
//#line 26 "parser.y"
{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
		}
break;
case 2:
//#line 31 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 33 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 35 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 40 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 42 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 44 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 46 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 48 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 50 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 52 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 54 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 13:
//#line 59 "parser.y"
{
            generador.enterScope(val_peek(1).sval);
        }
break;
case 17:
//#line 72 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 82 "parser.y"
{
	        String scope = generador.getCurrentScope();
	        Atributo func = al.ts.obtener(scope);
	        if (func != null && func.uso == Atributo.USO_FUNCION) {
	            func.tieneReturn = true;
	        } else {
	            errManager.error("Return en lugar invalido.", al.getLine());
	        }

	    }
break;
case 25:
//#line 94 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 96 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 98 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 28:
//#line 103 "parser.y"
{
	        String scope = generador.getCurrentScope();
            Atributo func = al.ts.obtener(scope);
            if (func.tieneReturn){
                errManager.debug("Declaracion de funcion detectada.", al.getLine());
                generador.agregarTerceto("fin_funcion", scope, "-");
	        } else {
	            errManager.error("Falta sentencia return.", al.getLine());
	        }

	        generador.exitScope();
	    }
break;
case 29:
//#line 119 "parser.y"
{
            /* Entramos al scope de la funcion.*/
            generador.enterScope(val_peek(1).sval);
            /* Checkeamos si esta declarada usando el scope, ya que este sera el mangle name de la funcion.*/
            String ambitoFuncion = generador.getCurrentScope();
            if (generador.estaDeclarada(ambitoFuncion, al.ts)){
                errManager.error("Redeclaracion de funcion no permitida.", al.getLine());
            } else {
                /* Generamos las entradas solo si es correcta.*/
                generador.aplicarAmbito(al.ts,val_peek(2).ival, ambitoFuncion);

                /*Generamos el terceto correspondiente al inicio de la a funcion.*/
                generador.agregarTerceto("inicio_funcion", ambitoFuncion, "-", val_peek(2).ival);
            }
        }
break;
case 30:
//#line 135 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 137 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 139 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 144 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 149 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 150 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 158 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 176 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 181 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 186 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 188 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 190 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 195 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 197 "parser.y"
{
	        errManager.debug("Parametro formal lambda semantica detectado", al.getLine());
	        generador.agregarParametro(false,Atributo.lambdaType,val_peek(0).sval);
	    }
break;
case 55:
//#line 202 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 204 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 206 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 211 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 216 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 220 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 222 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 224 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 226 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 228 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 235 "parser.y"
{
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal(val_peek(2).sval, val_peek(0).sval);
	    }
break;
case 66:
//#line 240 "parser.y"
{
            errManager.error("Parametro real debe ser vinculado a formal (->)", al.getLine());
        }
break;
case 71:
//#line 258 "parser.y"
{
	        errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 1;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
	    }
break;
case 72:
//#line 268 "parser.y"
{
    	    errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 1;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
    	}
break;
case 73:
//#line 278 "parser.y"
{
	        errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
	    }
break;
case 74:
//#line 285 "parser.y"
{
            errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
        }
break;
case 75:
//#line 292 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 294 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 77:
//#line 296 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 298 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 79:
//#line 300 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 302 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 81:
//#line 304 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 306 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 83:
//#line 308 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 84:
//#line 310 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 85:
//#line 315 "parser.y"
{
            /* $2 = indice de terceto condicion.*/
            /* Generar BF con destino desconocido.*/
            int idxBF = generador.generarBF(val_peek(0).ival);
            yyval.ival = idxBF;
        }
break;
case 86:
//#line 324 "parser.y"
{
            /* Backpatch BF para saltar al Else.*/
            /*Lo pasamos para arriba asi se rellena el BF*/
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterElse = generador.getUltimoTerceto() + 1;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 87:
//#line 336 "parser.y"
{
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterElse = generador.getUltimoTerceto() + 1;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 88:
//#line 346 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 89:
//#line 348 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 90:
//#line 350 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 91:
//#line 352 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 92:
//#line 354 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 93:
//#line 356 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 94:
//#line 358 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 95:
//#line 360 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 96:
//#line 365 "parser.y"
{
          int idxBI = generador.generarBI();
          /*devolvemos la instruccion donde se genero el BI*/
          yyval.ival = idxBI;
        }
break;
case 97:
//#line 374 "parser.y"
{ yyval.ival = val_peek(1).ival; }
break;
case 98:
//#line 376 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 99:
//#line 378 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 100:
//#line 380 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 102:
//#line 386 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 103:
//#line 388 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 104:
//#line 390 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 105:
//#line 392 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 106:
//#line 398 "parser.y"
{
	        errManager.debug("Condicion detectada. Linea: " + al.getLine());

            String opLexema = val_peek(1).sval; /* adaptar si $2 no es string*/
            String mensaje = generador.generarTercetoValido(opLexema, val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null) {
                errManager.error(mensaje, al.getLine());
                break;
            } else {
                yyval.ival = generador.getUltimoTerceto(); /* indice del terceto de la condición*/
            }
	     }
break;
case 107:
//#line 411 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 108:
//#line 416 "parser.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 109:
//#line 418 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 110:
//#line 420 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 111:
//#line 422 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 112:
//#line 424 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 113:
//#line 429 "parser.y"
{ yyval.sval = "<="; }
break;
case 114:
//#line 431 "parser.y"
{ yyval.sval = ">="; }
break;
case 115:
//#line 433 "parser.y"
{ yyval.sval = "=="; }
break;
case 116:
//#line 435 "parser.y"
{ yyval.sval = "!="; }
break;
case 119:
//#line 442 "parser.y"
{ yyval.ival = 0; }
break;
case 120:
//#line 444 "parser.y"
{ errManager.error("Tipo string no permitido.",al.getLine()); }
break;
case 121:
//#line 449 "parser.y"
{
            String mensaje = generador.puedoEscribir(val_peek(3).sval,al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            mensaje = generador.checkearAlcance(val_peek(3).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.generarTercetoValido(":=", val_peek(3).sval, val_peek(1).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            errManager.debug("Asignacion valida detectada", al.getLine());
            int indiceTerceto = generador.getUltimoTerceto();

	    }
break;
case 122:
//#line 473 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 123:
//#line 475 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 124:
//#line 477 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 125:
//#line 482 "parser.y"
{
            String mensaje = generador.generarTercetoValido("+", val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Suma valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
        }
break;
case 126:
//#line 493 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 127:
//#line 495 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 128:
//#line 497 "parser.y"
{
            String mensaje = generador.generarTercetoValido("-", val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Resta valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
        }
break;
case 129:
//#line 508 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 130:
//#line 510 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 131:
//#line 512 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 132:
//#line 514 "parser.y"
{
	        errManager.debug("Trunc detectado", al.getLine());
	        String mensaje = generador.validarExpresionTrunc(val_peek(0).sval, al.ts);
	        if (mensaje != null){
	            errManager.error(mensaje, al.getLine());
	            break ;
	        }
	        /* Generamos el terceto de salida. Al ser trunc devuelve un long .*/
	        int indiceTerceto = generador.agregarTerceto("trunc", val_peek(0).sval, "-", Atributo.longType);
	        yyval.sval = indiceTerceto + "";
        }
break;
case 133:
//#line 526 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 134:
//#line 531 "parser.y"
{
            String mensaje = generador.generarTercetoValido("*", val_peek(2).sval, val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Multiplicacion valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
	    }
break;
case 135:
//#line 542 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 136:
//#line 544 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 137:
//#line 546 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 138:
//#line 548 "parser.y"
{
	        String mensaje = generador.generarTercetoValido("/", val_peek(2).sval, val_peek(0).sval, al.ts);
	        if (mensaje != null){
                errManager.error(mensaje,al.getLine());
            } else {
                errManager.debug("Division valida detectada", al.getLine());
                int indiceTerceto = generador.getUltimoTerceto();
                yyval.sval = indiceTerceto + "";
            }
        }
break;
case 139:
//#line 559 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 140:
//#line 561 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 141:
//#line 563 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 142:
//#line 565 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 143:
//#line 570 "parser.y"
{
            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break;
            }

            /*pongo en factor el valor necesario para el terceto*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 144:
//#line 582 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 145:
//#line 584 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 146:
//#line 586 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 147:
//#line 588 "parser.y"
{
            String mensaje = generador.generarTercetoValido("*", "-1L", val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            int indiceTerceto = generador.getUltimoTerceto();
            yyval.sval = indiceTerceto + "";
        }
break;
case 148:
//#line 599 "parser.y"
{
            errManager.debug("Identificador con -", al.getLine());

            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }


            /* Generamos el terceto correspondiente. Utilizamos un "-1L" previamente creado como auxiliar.*/
            mensaje = generador.generarTercetoValido("*", "-1L", val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje,al.getLine());
                break ;
            }

            int indiceTerceto = generador.getUltimoTerceto();
            yyval.sval = indiceTerceto + "";
        }
break;
case 149:
//#line 621 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 150:
//#line 626 "parser.y"
{
	        errManager.debug("Llamado a funcion detectado", al.getLine());
	        String mensaje = generador.puedoLlamar(val_peek(3).sval, al.ts);
	        if (mensaje != null){
	            errManager.error(mensaje, al.getLine());
	            break ;
	        }

	        mensaje = generador.checkearAlcance(val_peek(3).sval, al.ts);
	        if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.checkearParametrosLlamada(val_peek(3).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            mensaje = generador.generarTercetosLlamado(val_peek(3).sval, al.ts);
            try{
                int indiceTerceto = Integer.parseInt(mensaje);
                yyval.sval = indiceTerceto + "";
            } catch (Exception e){
                errManager.error(mensaje, al.getLine());
            }

	    }
break;
case 151:
//#line 656 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 152:
//#line 658 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 153:
//#line 660 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 154:
//#line 665 "parser.y"
{
	        errManager.debug("Print detectado", al.getLine());
	        generador.agregarTerceto("print", val_peek(0).sval, "-");
	    }
break;
case 155:
//#line 672 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 156:
//#line 674 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 157:
//#line 678 "parser.y"
{
	        errManager.debug("Definicion lambda detectada", al.getLine());

	        generador.agregarTerceto("fin_funcion", val_peek(3).sval, "-");
	        generador.exitScope();

	        yyval.sval = val_peek(3).sval;
	    }
break;
case 158:
//#line 687 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 159:
//#line 689 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 160:
//#line 691 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 161:
//#line 696 "parser.y"
{
            /* Obtenemos un nombre para la funcion lambda.*/
            String lamName = generador.getLambdaName();
            /* Entramos al scope de la funcion.*/
            generador.enterScope(lamName);
            String lamScope = generador.getCurrentScope();

            /* Agregamos el parametro, esto para reutilizar los metodos de funcion.*/
            generador.agregarParametro(false, val_peek(2).ival, val_peek(1).sval);
            /* Aplica ambito a los parametros y crea el terceto de funcion.*/
            generador.aplicarAmbito(al.ts, Atributo.lambdaType, lamScope);

            /*Generamos el terceto correspondiente al inicio de la a funcion.*/
            generador.agregarTerceto("inicio_funcion", lamScope, "-");
            yyval.sval = lamScope;
        }
break;
case 162:
//#line 713 "parser.y"
{ errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
break;
case 163:
//#line 719 "parser.y"
{
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", val_peek(0).sval, "-");
	    }
break;
case 164:
//#line 727 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 165:
//#line 729 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 166:
//#line 731 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 167:
//#line 733 "parser.y"
{ errManager.error("Faltan argumentos", al.getLine()); }
break;
case 168:
//#line 735 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 169:
//#line 737 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 170:
//#line 742 "parser.y"
{
            errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());
            /* Se checkeo alcance y lectura de las ID, por lo que solo resta verificar los tipos y*/
            /* realizar las asignaciones (generar tercetos).*/

            String mensaje = generador.generarAsignacionMultiple(al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }


        }
break;
case 171:
//#line 756 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 172:
//#line 758 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 173:
//#line 760 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 174:
//#line 765 "parser.y"
{
            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            generador.agregarIDMultiple(val_peek(0).sval, al.ts);
        }
break;
case 175:
//#line 776 "parser.y"
{
            /* Verificamos que el IDCOMP sea valido.*/
            String mensaje = generador.validarLecturaYAlcance(val_peek(0).sval, al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break;
            }

            generador.agregarIDMultiple(val_peek(0).sval, al.ts);
        }
break;
case 176:
//#line 790 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 177:
//#line 792 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 178:
//#line 797 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 179:
//#line 802 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 180:
//#line 807 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 181:
//#line 812 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 182:
//#line 817 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 183:
//#line 819 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 184:
//#line 824 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1927 "Parser.java"
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
