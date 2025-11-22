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
    import Compilador.Assembler.Traductor;
//#line 24 "Parser.java"




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
    0,    0,   13,   23,   23,   24,   24,   24,   25,   25,
   25,   25,   25,   25,   25,   25,   25,   26,   32,   32,
   32,   32,   34,   34,   33,   33,   33,   22,   22,   22,
   36,   36,   35,   35,   35,   38,   38,   38,   37,   37,
   37,   37,   37,   37,   37,   37,   37,   14,   14,   14,
   14,   14,   14,   14,   39,   39,    5,    5,   28,   28,
   40,   40,   40,   40,   40,   40,   40,   40,   40,   40,
   40,   40,   40,   40,   16,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   18,   19,   19,   19,   19,
   42,   42,   42,   42,   42,   20,   20,   41,   41,   41,
   41,   41,   21,    9,    9,    9,    9,    9,    9,   15,
   15,   27,   27,   27,   27,    4,    4,    4,    4,    4,
    4,    4,    4,    4,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    2,    2,    2,    2,    2,    2,
    7,    7,    7,    7,   29,   11,   11,   10,   10,   10,
   10,    8,    8,   30,    6,    6,    6,    6,    6,    6,
   31,   31,   31,   31,   12,   12,   43,   43,    1,    1,
    1,    1,    1,    1,    1,
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
    1,    4,    4,    4,    4,    3,    3,    1,    3,    3,
    2,    1,    2,    2,    3,    2,    3,    2,    3,    2,
    3,    2,    1,    1,    1,    1,    1,    2,    2,    2,
    4,    4,    4,    4,    2,    1,    3,    4,    3,    3,
    1,    4,    5,    2,    3,    2,    3,    2,    2,    1,
    4,    5,    3,    4,    1,    3,    3,    1,    1,    1,
    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  120,  113,  121,    0,   13,    0,    0,    0,    0,    0,
    0,    0,   38,   14,   15,   18,   19,   20,    0,    0,
   24,    0,   69,   70,   10,    0,    0,    9,    0,   40,
    0,    0,   30,    0,    0,    0,  179,  180,  183,    0,
    0,    0,    0,    0,  145,  143,    0,    0,  146,   85,
    0,    0,  170,    0,  156,  155,    0,  164,    0,    0,
   25,   21,    0,    0,    0,   17,   64,   60,   16,    0,
    0,    0,    0,   31,    0,   96,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   41,    0,    6,   39,
   26,   22,   27,   23,    0,   28,    5,    2,    0,    0,
    0,    0,    0,    0,    0,   34,    0,    0,    0,   68,
    0,    0,   43,    0,   46,    0,  134,  133,    0,  184,
  185,  131,    0,    0,  181,  182,  148,  138,    0,    0,
  136,  142,  140,    0,    0,  107,  114,  115,  116,  117,
  118,  119,    0,    0,    0,   98,  169,    0,  168,    0,
  166,    0,    0,    0,    0,  176,  173,    0,  178,    0,
   63,   59,   29,   32,   53,   83,    0,   82,    0,    0,
  104,   80,    0,   84,   95,    0,    0,   92,    0,    0,
    0,   78,   74,  103,   42,    0,   77,   73,    0,    0,
    0,   36,    0,    4,  125,  124,  154,   54,   56,   50,
    0,    0,    0,    0,   66,    0,    0,    0,   57,   52,
   33,    0,   45,  152,    0,   48,   99,   97,  137,  135,
  141,  139,  127,    0,  130,    0,    0,  157,  167,  165,
  123,  122,  153,  151,    0,  174,  171,    0,   81,  105,
   79,  102,  101,    0,    0,    0,   91,    0,   90,   76,
   72,   75,   71,  111,  110,    0,  112,   35,   55,   51,
   49,    0,    0,   65,    0,  160,   44,   47,  172,  177,
   94,   93,   89,   87,   88,   86,  109,  108,    0,  162,
  158,  163,
};
final static short yydgoto[] = {                          4,
   55,   56,   57,  117,  118,   65,   59,  119,  155,  120,
   66,   17,    5,   18,   19,   20,   90,   91,   60,   61,
   21,   22,   23,   24,   25,   26,   27,   28,   29,   30,
   31,   32,  106,   43,  122,   93,  123,  124,  125,   33,
   34,   94,  170,
};
final static short yysindex[] = {                       -94,
    0,  691,  739,    0,  746,  -24,  -36,  104,   27,   69,
    0,    0,    0,  -13,    0,  -38,  -29,    4,  -20,  360,
  693,  786,    0,    0,    0,    0,    0,    0,   45,   59,
    0, -105,    0,    0,    0,  834,    6,    0,  845,    0,
  162,  -31,    0,   -2,    9,   50,    0,    0,    0,  162,
  -85,  270,  -26,  224,    0,    0,   78,  265,    0,    0,
  117,  130,    0,  423,    0,    0,  138,    0,  162,   55,
    0,    0,  103, -235,   60,    0,    0,    0,    0, -210,
  136,  136,  -41,    0,   -9,    0,  111,  153,  660,  119,
  652,   97,  938,  139,  104,  894,    0,   87,    0,    0,
    0,    0,    0,    0,  779,    0,    0,    0,  -74,   48,
  143,   -2,  -62,  755,  -73,    0,  105, -223,  714,    0,
  166,   80,    0,   17,    0,   95,    0,    0,   12,    0,
    0,    0,   -2,   50,    0,    0,    0,    0,  -85,  176,
    0,    0,    0,  230,  236,    0,    0,    0,    0,    0,
    0,    0,  168,  193,  162,    0,    0,  182,    0,  -15,
    0,   74,  229,   41,   44,    0,    0,  250,    0,  -37,
    0,    0,    0,    0,    0,    0,  233,    0,  158,  235,
    0,    0,  901,    0,    0,   39,  866,    0,  -46,  -14,
  102,    0,    0,    0,    0,  133,    0,    0,  137,  104,
  243,    0,  873,    0,    0,    0,    0,    0,    0,    0,
   52,  278,  -58,   56,    0,   72,  716,  921,    0,    0,
    0,  670,    0,    0,  112,    0,    0,    0,    0,    0,
    0,    0,    0,   78,    0,   78,  105,    0,    0,    0,
    0,    0,    0,    0,  101,    0,    0,   44,    0,    0,
    0,    0,    0,  273,  277,  149,    0,  165,    0,    0,
    0,    0,    0,    0,    0,  205,    0,    0,    0,    0,
    0,   84,  305,    0,  927,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  312,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  362,    0,    0,    0,    0,  144,    0,    0,    0,
    0,    0,    0,   62,    0,    0,    0,    0,    0,    0,
    0,  370,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    0,    0,  372,    0,  378,    0,
    0,    0,    0,  415,    0,  477,    0,    0,    0,    0,
  544,    0,    0,    0,    0,    0,  567,    0,    0,    0,
  626,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  320,  337,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  949,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  426,    0,
    0,  387,    0,    0,    0,    0, -141,    0, -103,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  500,  522,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  949,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  589,    0,  818,  448,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -82,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  211,   23,  397,  831,    0,   34,    3,    0,    0,    0,
    0,    0,    0,    0,  820,    0,  402,    0,  -61,  352,
    0,  186,  443,    0,  -18,    0,    0,    0,    0,    0,
    0,    0,    0,  417,    0,  308,  -86,  345,  -84,    0,
    0,   22,  272,
};
final static int YYTABLESIZE=1225;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        116,
   37,   92,   97,   42,   16,   16,  248,   16,  115,  116,
   53,   51,  257,   52,   74,   54,  139,  105,  140,   83,
   72,  247,   16,   16,   16,  240,   70,  153,    3,  154,
  126,   75,  215,  199,   40,  223,  201,  126,   16,  226,
  166,   16,   98,   68,  259,  216,  171,   80,   67,   63,
   53,   51,  228,   52,  137,   54,  115,  224,   53,   51,
  225,   52,   79,   54,   40,  172,   62,   63,   53,   51,
   97,   52,  189,   54,  195,  141,  143,   97,  128,  226,
  115,  244,   53,   51,  225,   52,  139,   54,  168,   70,
  153,   16,  154,   16,  115,   16,   53,   51,   16,   52,
   97,   54,  139,  102,  168,  175,  206,   16,   67,   63,
   53,   51,  190,   52,   67,   54,  153,  104,  154,  144,
  221,   16,  175,  222,  145,   37,   50,   67,   53,   51,
  108,   52,  242,   54,  115,  277,   53,   51,  266,   52,
  278,   54,  137,   50,  248,   53,   51,  153,   52,  154,
   54,  115,  161,   53,   51,  192,   52,  156,   54,  279,
  261,    1,    2,  165,  195,  161,  230,  232,   97,  176,
  159,   53,   51,  159,   52,   83,   54,  184,  159,   53,
   51,  204,   52,  207,   54,   16,  159,   61,   36,   16,
   39,  263,  130,  131,  208,  265,   11,  197,   97,  195,
  213,   13,   61,   53,   51,   16,   52,  284,   54,   53,
  139,   11,  140,  256,   54,  175,   13,   71,  246,   16,
   16,  113,  238,  286,  111,  112,   73,   41,   11,  138,
   44,  113,  114,   13,   53,  139,   81,  140,   11,   54,
  239,   45,  114,   13,   46,  258,   47,   48,   49,   46,
   69,   47,   48,   49,   41,   82,  195,   37,   37,   76,
   77,   37,   37,  288,  127,   44,  139,  227,  140,  243,
   37,   37,  139,   44,  140,   37,   37,   16,  139,   78,
  140,   45,  250,   44,   46,  169,   47,   48,   49,   45,
  203,  249,   46,  251,   47,   48,   49,   44,  254,   45,
  101,  267,   46,  205,   47,   48,   49,  153,  269,  154,
  163,   44,  273,   45,  103,  167,   46,  175,   47,   48,
   49,   47,   48,   49,  152,   44,  151,   45,  274,  241,
   46,  281,   47,   48,   49,  282,  175,   47,   48,   49,
  289,   45,  113,   44,   46,  290,   47,   48,   49,   11,
  111,   44,  292,  114,   13,   86,  191,  260,  200,   45,
   44,   11,   46,   62,   47,   48,   49,   45,   44,    8,
   46,    3,   47,   48,   49,  169,   45,    7,   62,   46,
   58,   47,   48,   49,   45,  157,   44,   46,  262,   47,
   48,   49,  264,  157,   44,   58,  183,   86,  196,   61,
   61,  129,   45,  183,  283,   46,  158,   47,   48,   49,
   45,   86,  177,   46,  164,   47,   48,   49,   44,   61,
  285,  219,  220,  233,   44,    1,  218,   53,  147,  147,
   53,  147,  133,  147,   45,   84,  245,   46,    0,   47,
   48,   49,    0,   46,    0,   47,   48,   49,  235,   44,
    0,  134,    0,  135,  136,  147,  147,  147,  280,  147,
  287,  147,    0,  161,  100,  153,    0,  154,   46,    0,
   47,   48,   49,  147,  147,    0,  147,    0,  100,  142,
   44,  100,   89,    0,   88,  229,   44,    0,  106,  178,
  182,  231,   44,  193,  183,  198,    0,  173,  174,   46,
    0,   47,   48,   49,    0,   46,  106,   47,   48,   49,
    0,   46,    0,   47,   48,   49,    0,  144,  144,  144,
  146,  144,    0,  144,  275,  132,  133,  135,  136,  147,
  148,  149,  150,  270,  271,  144,  144,  147,  144,  147,
  150,  150,  150,    0,  150,  134,  150,  135,  136,  234,
  236,    0,    0,    0,    0,    0,    0,    0,  150,  150,
    0,  150,  149,  149,  149,    0,  149,    0,  149,    0,
  106,    0,  106,    0,    0,   62,   62,    0,    0,    0,
  149,  149,    0,  149,  128,    0,  128,    0,  128,    0,
    0,    0,   58,   58,    0,   62,    0,    0,    0,  144,
    0,  144,  128,  128,    0,  128,    0,  132,    0,  132,
    0,  132,   58,    0,    0,    0,   85,    8,   86,   87,
    9,   10,  150,    0,  150,  132,  132,    0,  132,  126,
   12,  126,    0,  126,    0,   14,    0,    0,    0,    0,
    0,    0,  147,   53,  149,  100,  149,  126,  126,   53,
  126,    0,    0,    0,    0,  147,   53,    0,    0,    0,
   53,   53,    0,    0,    0,    0,  128,    0,  128,    0,
  147,  147,  147,  147,  147,  147,  147,    0,    0,  147,
  147,  147,  147,  147,  100,  147,    0,    0,    0,  132,
  147,  132,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  106,  106,  106,  106,  106,  106,  106,
  188,  126,    0,  126,    0,    0,    0,    0,  106,    0,
    0,    0,    0,  106,    0,    0,    0,    0,    0,    0,
    0,    0,  144,  144,  144,  144,  144,  144,  144,    0,
    0,  144,  144,  144,  144,  144,    0,  144,  100,    0,
  100,    0,  144,    0,    0,  150,  150,  150,  150,  150,
  150,  150,    0,    0,  150,  150,  150,  150,  150,    0,
  150,    0,    0,    0,  187,  150,  186,  149,  149,  149,
  149,  149,  149,  149,  181,    0,  149,  149,  149,  149,
  149,    0,  149,    0,    0,    0,    0,  149,    0,  128,
  128,  128,  128,  128,  128,  128,    0,    0,  128,  128,
  128,  128,  128,   15,  128,   96,    0,    0,    0,  128,
    0,    0,  132,  132,  132,  132,  132,  132,  132,    0,
    0,  132,  132,  132,  132,  132,  217,  132,   58,   64,
   64,    0,  132,    0,  126,  126,  126,  126,  126,  126,
  126,    0,    0,  126,  126,  126,  126,  126,  129,  126,
  129,  121,  129,   35,  126,    0,    0,    0,    0,    0,
   38,  110,    0,    0,    0,   64,  129,  129,    0,  129,
   58,  100,  100,  100,  100,  100,  100,  100,    0,    0,
    0,    0,  160,    0,    0,    0,  100,  160,    0,  162,
    0,  100,  121,  202,    0,    0,    0,    0,   85,    8,
   99,  185,    9,   10,    0,  179,   85,    8,   86,  180,
    9,   10,   12,    0,    0,   58,  175,   14,   58,    0,
   12,    0,  113,  212,  214,   14,    0,    0,    0,   11,
  129,  121,  129,  114,   13,    0,    6,    7,    8,   85,
    8,    9,   10,    9,   10,    0,    0,    0,  107,    0,
   11,   12,    0,   12,   95,   13,   14,    0,   14,  109,
   85,    8,   85,    8,    9,   10,    9,   10,    0,    0,
    0,    0,    0,    0,   12,  237,   12,    0,    0,   14,
  181,   14,    0,    0,    6,    7,    8,  268,    0,    9,
   10,   37,    7,    8,    0,    0,    9,   10,   11,   12,
  209,  210,    0,   13,   14,   11,   12,  211,  181,    0,
   13,   14,    0,    0,   11,  253,    0,    0,    0,   13,
   58,    0,  272,    0,    6,    7,    8,    0,    0,    9,
   10,  121,    7,    8,    0,  276,    9,   10,   11,   12,
    0,  291,    0,   13,   14,   11,   12,    0,    0,    0,
   13,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  129,  129,  129,  129,  129,  129,  129,
    0,    0,  129,  129,  129,  129,  129,    0,  129,    0,
    7,    8,    0,  129,    9,   10,    0,    0,    0,    0,
    0,    7,    8,   11,   12,    9,   10,    0,   13,   14,
    0,    0,    0,    0,   11,   12,    0,    0,    0,   13,
   14,  179,   85,    8,    0,  255,    9,   10,    0,    7,
    8,    0,    0,    9,   10,    0,   12,    0,    0,    0,
    0,   14,   11,   12,    0,    0,    0,   13,   14,  179,
   85,    8,    0,    0,    9,   10,  252,   85,    8,    0,
    0,    9,   10,    0,   12,    0,    0,    0,    0,   14,
    0,   12,    0,    0,    0,    0,   14,   85,    8,    0,
    0,    9,   10,   85,    8,    0,    0,    9,   10,    0,
    0,   12,    0,  194,   85,    8,   14,   12,    9,   10,
    0,    0,   14,    0,   41,   41,   41,    0,   12,   41,
   41,    0,    0,   14,    0,    0,    0,    0,    0,   41,
    0,    0,    0,    0,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   20,   21,   40,    2,    3,   44,    5,   40,   41,
   42,   43,   59,   45,   44,   47,   43,  123,   45,   40,
   59,   59,   20,   21,   22,   41,   40,   43,  123,   45,
   40,   61,  256,   95,   59,  122,   98,   40,   36,  124,
  276,   39,   21,   10,   59,  269,  257,   44,   40,   41,
   42,   43,   41,   45,   52,   47,   40,   41,   42,   43,
   44,   45,   59,   47,   59,  276,   40,   41,   42,   43,
   89,   45,   91,   47,   93,   53,   54,   96,   45,  164,
   40,   41,   42,   43,   44,   45,   43,   47,   45,   40,
   43,   89,   45,   91,   40,   93,   42,   43,   96,   45,
  119,   47,   43,   59,   45,   44,   59,  105,   40,   41,
   42,   43,   91,   45,  256,   47,   43,   59,   45,   42,
   41,  119,   61,   44,   47,  125,   40,  269,   42,   43,
  125,   45,   59,   47,   40,  222,   42,   43,  200,   45,
  225,   47,  140,   40,   44,   42,   43,   43,   45,   45,
   47,   40,  256,   42,   43,   59,   45,   41,   47,   59,
   59,  256,  257,   61,  183,  269,  144,  145,  187,   59,
   41,   42,   43,  256,   45,   40,   47,   59,   41,   42,
   43,  256,   45,   41,   47,  183,  269,   44,    3,  187,
    5,   59,  278,  279,  257,   59,  270,   59,  217,  218,
  274,  275,   59,   42,   43,  203,   45,   59,   47,   42,
   43,  270,   45,  260,   47,  257,  275,  256,  256,  217,
  218,  263,   41,   59,  256,  257,  256,  264,  270,  256,
  257,  263,  274,  275,   42,   43,  257,   45,  270,   47,
  256,  273,  274,  275,  276,  260,  278,  279,  280,  276,
  264,  278,  279,  280,  264,  276,  275,  257,  258,  256,
  257,  261,  262,   59,  256,  257,   43,  256,   45,   41,
  270,  271,   43,  257,   45,  275,  276,  275,   43,  276,
   45,  273,  125,  257,  276,   75,  278,  279,  280,  273,
  105,   59,  276,   59,  278,  279,  280,  257,  260,  273,
  256,   59,  276,  256,  278,  279,  280,   43,  257,   45,
  256,  257,  257,  273,  256,  256,  276,  256,  278,  279,
  280,  278,  279,  280,   60,  257,   62,  273,  257,  256,
  276,   59,  278,  279,  280,   59,  257,  278,  279,  280,
  257,  273,  263,  257,  276,   41,  278,  279,  280,  270,
  256,  257,   41,  274,  275,  259,  260,  256,  272,  273,
  257,    0,  276,   44,  278,  279,  280,  273,  257,    0,
  276,    0,  278,  279,  280,  165,  273,    0,   59,  276,
   44,  278,  279,  280,  273,  256,  257,  276,  256,  278,
  279,  280,  256,  256,  257,   59,   89,  259,  260,  256,
  257,   50,  273,   96,  256,  276,  277,  278,  279,  280,
  273,  259,  260,  276,   70,  278,  279,  280,  257,  276,
  256,  256,  257,  256,  257,    0,  119,   41,   42,   43,
   44,   45,  257,   47,  273,   19,  165,  276,   -1,  278,
  279,  280,   -1,  276,   -1,  278,  279,  280,  256,  257,
   -1,  276,   -1,  278,  279,   41,   42,   43,  248,   45,
  256,   47,   -1,   41,   22,   43,   -1,   45,  276,   -1,
  278,  279,  280,   59,   60,   -1,   62,   -1,   36,  256,
  257,   39,  123,   -1,  125,  256,  257,   -1,   41,   88,
   89,  256,  257,   92,  187,   94,   -1,   81,   82,  276,
   -1,  278,  279,  280,   -1,  276,   59,  278,  279,  280,
   -1,  276,   -1,  278,  279,  280,   -1,   41,   42,   43,
  256,   45,   -1,   47,  217,  256,  257,  278,  279,  265,
  266,  267,  268,  256,  257,   59,   60,  123,   62,  125,
   41,   42,   43,   -1,   45,  276,   47,  278,  279,  153,
  154,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   -1,   62,   41,   42,   43,   -1,   45,   -1,   47,   -1,
  123,   -1,  125,   -1,   -1,  256,  257,   -1,   -1,   -1,
   59,   60,   -1,   62,   41,   -1,   43,   -1,   45,   -1,
   -1,   -1,  256,  257,   -1,  276,   -1,   -1,   -1,  123,
   -1,  125,   59,   60,   -1,   62,   -1,   41,   -1,   43,
   -1,   45,  276,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,  123,   -1,  125,   59,   60,   -1,   62,   41,
  271,   43,   -1,   45,   -1,  276,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,  123,  203,  125,   59,   60,  263,
   62,   -1,   -1,   -1,   -1,  269,  270,   -1,   -1,   -1,
  274,  275,   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,   59,  271,   -1,   -1,   -1,  123,
  276,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,  257,  258,  259,  260,  261,  262,
   59,  123,   -1,  125,   -1,   -1,   -1,   -1,  271,   -1,
   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,   -1,  271,  123,   -1,
  125,   -1,  276,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   -1,   -1,  123,  276,  125,  256,  257,  258,
  259,  260,  261,  262,  125,   -1,  265,  266,  267,  268,
  269,   -1,  271,   -1,   -1,   -1,   -1,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,  123,  271,  123,   -1,   -1,   -1,  276,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,  123,  271,    8,    9,
   10,   -1,  276,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   41,  271,
   43,   42,   45,  125,  276,   -1,   -1,   -1,   -1,   -1,
  125,   41,   -1,   -1,   -1,   45,   59,   60,   -1,   62,
   50,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   62,   -1,   -1,   -1,  271,   67,   -1,   69,
   -1,  276,   83,  125,   -1,   -1,   -1,   -1,  257,  258,
  125,  260,  261,  262,   -1,  256,  257,  258,  259,  260,
  261,  262,  271,   -1,   -1,   95,  257,  276,   98,   -1,
  271,   -1,  263,  114,  115,  276,   -1,   -1,   -1,  270,
  123,  122,  125,  274,  275,   -1,  256,  257,  258,  257,
  258,  261,  262,  261,  262,   -1,   -1,   -1,  125,   -1,
  270,  271,   -1,  271,  272,  275,  276,   -1,  276,  125,
  257,  258,  257,  258,  261,  262,  261,  262,   -1,   -1,
   -1,   -1,   -1,   -1,  271,  155,  271,   -1,   -1,  276,
  125,  276,   -1,   -1,  256,  257,  258,  125,   -1,  261,
  262,  256,  257,  258,   -1,   -1,  261,  262,  270,  271,
  256,  257,   -1,  275,  276,  270,  271,  263,  125,   -1,
  275,  276,   -1,   -1,  270,  125,   -1,   -1,   -1,  275,
  200,   -1,  213,   -1,  256,  257,  258,   -1,   -1,  261,
  262,  222,  257,  258,   -1,  125,  261,  262,  270,  271,
   -1,  125,   -1,  275,  276,  270,  271,   -1,   -1,   -1,
  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,   -1,
  257,  258,   -1,  276,  261,  262,   -1,   -1,   -1,   -1,
   -1,  257,  258,  270,  271,  261,  262,   -1,  275,  276,
   -1,   -1,   -1,   -1,  270,  271,   -1,   -1,   -1,  275,
  276,  256,  257,  258,   -1,  260,  261,  262,   -1,  257,
  258,   -1,   -1,  261,  262,   -1,  271,   -1,   -1,   -1,
   -1,  276,  270,  271,   -1,   -1,   -1,  275,  276,  256,
  257,  258,   -1,   -1,  261,  262,  256,  257,  258,   -1,
   -1,  261,  262,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,  271,   -1,   -1,   -1,   -1,  276,  257,  258,   -1,
   -1,  261,  262,  257,  258,   -1,   -1,  261,  262,   -1,
   -1,  271,   -1,  256,  257,  258,  276,  271,  261,  262,
   -1,   -1,  276,   -1,  256,  257,  258,   -1,  271,  261,
  262,   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,  271,
   -1,   -1,   -1,   -1,  276,
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
"do_until : inicio_do cuerpo_opt UNTIL condicional_opt ';'",
"do_until : inicio_do cuerpo_opt UNTIL condicional_opt error",
"do_until : inicio_do UNTIL condicional_opt ';'",
"do_until : inicio_do UNTIL condicional_opt error",
"do_until : inicio_do cuerpo_opt condicional_opt ';'",
"inicio_do : DO",
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

//#line 850 "parser.y"
private AnalizadorLexico al;
private ErrorManager errManager;
private static final ParserVal dummyParserVal = new ParserVal();
private Generador generador;
private Traductor traductor;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
    this.generador = new Generador();
    al.ts.insertar("-1L", new Atributo(0,-1,"auxiliar"));
    al.ts.insertar("0L", new Atributo(0,0,"auxiliar"));
    this.traductor = new Traductor(al.ts);
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
    traductor.traducir(generador.getTercetos());
}

public void yyerror(String s) {
}
//#line 820 "Parser.java"
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
//#line 27 "parser.y"
{
		    errManager.debug("Declaracion de programa detectada", al.getLine());
		    generador.exitScope();
		    generador.agregarTerceto("fin_main", val_peek(2).sval, "-");
		}
break;
case 2:
//#line 33 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 35 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 37 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 42 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 44 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 46 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 48 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 50 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 52 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 54 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 56 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 13:
//#line 61 "parser.y"
{
            generador.enterScope(val_peek(1).sval);
            generador.agregarTerceto("inicio_main", val_peek(1).sval, "-");
            yyval.sval = val_peek(1).sval;
        }
break;
case 17:
//#line 76 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 86 "parser.y"
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
//#line 98 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 100 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 102 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 28:
//#line 107 "parser.y"
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
//#line 123 "parser.y"
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
//#line 139 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 141 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 143 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 148 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 153 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 154 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 162 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 180 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 185 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 190 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 192 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 194 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 199 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 201 "parser.y"
{
	        errManager.debug("Parametro formal lambda semantica detectado", al.getLine());
	        generador.agregarParametro(false,Atributo.lambdaType,val_peek(0).sval);
	    }
break;
case 55:
//#line 206 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 208 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 210 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 215 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 220 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 224 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 226 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 228 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 230 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 232 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 239 "parser.y"
{
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal(val_peek(2).sval, val_peek(0).sval);
	    }
break;
case 66:
//#line 244 "parser.y"
{
            errManager.error("Parametro real debe ser vinculado a formal (->)", al.getLine());
        }
break;
case 71:
//#line 262 "parser.y"
{
	        errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 1;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
	    }
break;
case 72:
//#line 272 "parser.y"
{
    	    errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 1;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);
    	}
break;
case 73:
//#line 282 "parser.y"
{
	        errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
	    }
break;
case 74:
//#line 289 "parser.y"
{
            errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);
        }
break;
case 75:
//#line 296 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 298 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 77:
//#line 300 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 302 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 79:
//#line 304 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 306 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 81:
//#line 308 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 310 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 83:
//#line 312 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 84:
//#line 314 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 85:
//#line 319 "parser.y"
{
            /* $2 = indice de terceto condicion.*/
            /* Generar BF con destino desconocido.*/
            int idxBF = generador.generarBF(val_peek(0).ival);
            yyval.ival = idxBF;
        }
break;
case 86:
//#line 328 "parser.y"
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
//#line 340 "parser.y"
{
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterElse = generador.getUltimoTerceto() + 1;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 88:
//#line 350 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 89:
//#line 352 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 90:
//#line 354 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 91:
//#line 356 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 92:
//#line 358 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 93:
//#line 360 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 94:
//#line 362 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 95:
//#line 364 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 96:
//#line 369 "parser.y"
{
          int idxBI = generador.generarBI();
          /*devolvemos la instruccion donde se genero el BI*/
          yyval.ival = idxBI;
        }
break;
case 97:
//#line 378 "parser.y"
{ yyval.ival = val_peek(1).ival; }
break;
case 98:
//#line 380 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 99:
//#line 382 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 100:
//#line 384 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 102:
//#line 390 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 103:
//#line 392 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 104:
//#line 394 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 105:
//#line 396 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 106:
//#line 402 "parser.y"
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
//#line 415 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 108:
//#line 420 "parser.y"
{
	        errManager.debug("DO-UNTIL detectado", al.getLine());
            int indiceInicio = val_peek(4).ival;   /* primer terceto del cuerpo*/
            int indiceCondicion  = val_peek(1).ival;   /* indice del terceto que representa la condicion*/

            /* Generamos BF y luego rellenamos su destino al inicio del cuerpo.*/
            int indiceBF = generador.generarBF(indiceCondicion);
            generador.rellenarOperando(indiceBF, indiceInicio, 2);
	    }
break;
case 109:
//#line 430 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 110:
//#line 432 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 111:
//#line 434 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 112:
//#line 436 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 113:
//#line 441 "parser.y"
{
            /* Guardamos la direccion de inicio del DO.*/
            yyval.ival = generador.getUltimoTerceto();
        }
break;
case 114:
//#line 448 "parser.y"
{ yyval.sval = "<="; }
break;
case 115:
//#line 450 "parser.y"
{ yyval.sval = ">="; }
break;
case 116:
//#line 452 "parser.y"
{ yyval.sval = "=="; }
break;
case 117:
//#line 454 "parser.y"
{ yyval.sval = "!="; }
break;
case 118:
//#line 456 "parser.y"
{ yyval.sval = ">"; }
break;
case 119:
//#line 458 "parser.y"
{ yyval.sval = "<"; }
break;
case 120:
//#line 463 "parser.y"
{ yyval.ival = 0; }
break;
case 121:
//#line 465 "parser.y"
{ errManager.error("Tipo string no permitido.",al.getLine()); }
break;
case 122:
//#line 470 "parser.y"
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
case 123:
//#line 494 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 124:
//#line 496 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 125:
//#line 498 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 126:
//#line 503 "parser.y"
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
case 127:
//#line 514 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 128:
//#line 516 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 129:
//#line 518 "parser.y"
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
case 130:
//#line 529 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 131:
//#line 531 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 132:
//#line 533 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 133:
//#line 535 "parser.y"
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
case 134:
//#line 547 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 135:
//#line 552 "parser.y"
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
case 136:
//#line 563 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 137:
//#line 565 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 138:
//#line 567 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 139:
//#line 569 "parser.y"
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
case 140:
//#line 580 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 141:
//#line 582 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 142:
//#line 584 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 143:
//#line 586 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 144:
//#line 591 "parser.y"
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
case 145:
//#line 603 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 146:
//#line 605 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 147:
//#line 607 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 148:
//#line 609 "parser.y"
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
case 149:
//#line 620 "parser.y"
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
case 150:
//#line 642 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 151:
//#line 647 "parser.y"
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
case 152:
//#line 677 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 153:
//#line 679 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 154:
//#line 681 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 155:
//#line 686 "parser.y"
{
	        errManager.debug("Print detectado", al.getLine());
	        generador.agregarTerceto("print", val_peek(0).sval, "-");
	    }
break;
case 156:
//#line 693 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 157:
//#line 695 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 158:
//#line 699 "parser.y"
{
	        errManager.debug("Definicion lambda detectada", al.getLine());

	        generador.agregarTerceto("fin_funcion", val_peek(3).sval, "-");
	        generador.exitScope();

	        yyval.sval = val_peek(3).sval;
	    }
break;
case 159:
//#line 708 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 160:
//#line 710 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 161:
//#line 712 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 162:
//#line 717 "parser.y"
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
case 163:
//#line 734 "parser.y"
{ errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
break;
case 164:
//#line 740 "parser.y"
{
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", val_peek(0).sval, "-");
	    }
break;
case 165:
//#line 748 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 166:
//#line 750 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 167:
//#line 752 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 168:
//#line 754 "parser.y"
{ errManager.error("Faltan argumentos", al.getLine()); }
break;
case 169:
//#line 756 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 170:
//#line 758 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 171:
//#line 763 "parser.y"
{
            errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine());
            /* Se checkeo alcance y lectura de las ID, por lo que solo resta verificar los tipos y*/
            /* realizar las asignaciones (generar tercetos).*/

            String mensaje = generador.generarAsignacionMultiple(al.ts);
            if (mensaje != null){
                errManager.error(mensaje, al.getLine());
                break ;
            }

            generador.limpiarAsignacionMultiple();

        }
break;
case 172:
//#line 778 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 173:
//#line 780 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 174:
//#line 782 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 175:
//#line 787 "parser.y"
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
//#line 798 "parser.y"
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
case 177:
//#line 812 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 178:
//#line 814 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 179:
//#line 819 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 180:
//#line 824 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 181:
//#line 829 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 182:
//#line 834 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 183:
//#line 839 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 184:
//#line 841 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 185:
//#line 846 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1957 "Parser.java"
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
