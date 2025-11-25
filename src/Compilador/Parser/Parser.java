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
    0,    0,   13,   24,   24,   25,   25,   25,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   27,   33,   33,
   33,   33,   35,   35,   34,   34,   34,   23,   23,   23,
   37,   37,   36,   36,   36,   39,   39,   39,   38,   38,
   38,   38,   38,   38,   38,   38,   38,   14,   14,   14,
   14,   14,   14,   14,   40,   40,    5,    5,   29,   29,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   16,   43,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   18,   19,   19,   19,
   19,   42,   42,   42,   42,   42,   20,   20,   41,   41,
   41,   41,   41,   21,    9,    9,    9,    9,    9,    9,
   15,   15,   28,   28,   28,   28,    4,    4,    4,    4,
    4,    4,    4,    4,    4,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    2,    2,    2,    2,
    2,    7,    7,    7,    7,   30,   11,   11,   10,   10,
   10,   10,    8,    8,   31,    6,    6,    6,    6,    6,
    6,   32,   32,   32,   32,   12,   12,   44,   44,    1,
    1,    1,    1,    1,    1,    1,
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
    4,    3,    3,    3,    2,    1,    4,    4,    4,    4,
    3,    3,    2,    4,    4,    2,    1,    3,    2,    3,
    1,    3,    3,    2,    2,    3,    3,    2,    5,    5,
    4,    4,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    4,    4,    4,    4,    3,    3,    1,    3,
    3,    2,    1,    2,    2,    3,    2,    3,    2,    3,
    2,    3,    2,    1,    1,    1,    1,    1,    2,    2,
    2,    4,    4,    4,    4,    2,    1,    3,    4,    3,
    3,    1,    4,    5,    2,    3,    2,    3,    2,    2,
    1,    4,    5,    3,    4,    1,    3,    3,    1,    1,
    1,    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,   86,    0,    0,
  121,  114,  122,    0,   13,    0,    0,    0,    0,    0,
    0,   69,    0,   38,   14,   15,   18,   19,   20,    0,
    0,   24,    0,   70,    0,   10,    0,    0,    9,    0,
   40,    0,    0,   30,    0,    0,    0,  180,  181,  184,
    0,  171,    0,    0,    0,    0,  146,  144,    0,    0,
  157,  147,  156,    0,  165,    0,    0,   25,   21,    0,
    0,    0,   17,   64,   60,   16,    0,    0,    0,    0,
   31,    0,   97,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    6,   39,   26,   22,   27,
   23,    0,   28,    0,    0,   85,    0,    5,    2,    0,
    0,    0,    0,    0,    0,    0,   34,    0,    0,    0,
   68,    0,    0,   43,    0,   46,    0,  135,  134,  170,
    0,  169,    0,  185,  186,  132,    0,    0,  182,  183,
  149,  139,    0,    0,  137,  143,  141,    0,    0,  167,
    0,    0,    0,    0,    0,    0,  177,  174,    0,  179,
    0,   63,   59,   29,   32,   53,   83,    0,   82,    0,
    0,  105,   80,    0,   84,   96,    0,    0,   93,    0,
    0,    0,   78,   74,  104,   42,    0,   77,   73,    0,
    0,    0,   36,    0,    0,  108,  115,  116,  117,  118,
  119,  120,    0,   99,    4,  126,  125,  155,   54,   56,
   50,    0,    0,    0,    0,   66,    0,    0,    0,   57,
   52,   33,    0,   45,  153,    0,   48,  158,  168,  166,
  138,  136,  142,  140,  128,    0,  131,    0,  124,  123,
  154,  152,    0,  175,  172,    0,   81,  106,   79,  103,
  102,    0,    0,    0,   92,    0,   91,   76,   72,   75,
   71,  112,  111,    0,  113,   35,  100,   98,    0,   55,
   51,   49,    0,    0,   65,    0,  161,   44,   47,  173,
  178,   95,   94,   90,   88,   89,   87,  110,  109,    0,
  163,  159,  164,
};
final static short yydgoto[] = {                          4,
   57,   58,   59,  118,  119,   61,   62,  120,  203,  121,
   63,   17,    5,   18,   19,   20,   87,   88,  106,  107,
   21,   22,   23,   24,   25,   26,   27,   28,   29,   30,
   31,   32,   33,  103,   44,  123,   90,  124,  125,  126,
   34,   91,   35,  161,
};
final static short yysindex[] = {                        25,
    0,  603,  703,    0,  714,  -14,  -26,    0,   42,   56,
    0,    0,    0,   -4,    0,  -40,  140,  129,  -34,  638,
  655,    0,  682,    0,    0,    0,    0,    0,    0,  -27,
   58,    0,  -46,    0,   93,    0,  804,   32,    0,  824,
    0,  122,  -30,    0,   46,   10,   60,    0,    0,    0,
  111,    0, -104,  241,  -25,  154,    0,    0,  183,  397,
    0,    0,    0,  136,    0,  122,   67,    0,    0,   45,
 -145,  -43,    0,    0,    0,    0, -228,  119,  119,  321,
    0,   -3,    0,   92,  -48,  737,  112,  214,   43,  358,
   52,   93,  879,    0,   76,    0,    0,    0,    0,    0,
    0,  797,    0,  122,  580,    0,  162,    0,    0,  -74,
   70,  193,   46,  -66,  324,  195,    0,  308, -222,  680,
    0,   36,  284,    0,   18,    0,   85,    0,    0,    0,
  233,    0,  -17,    0,    0,    0,   46,   60,    0,    0,
    0,    0, -104,  298,    0,    0,    0,  165,  179,    0,
  147,  172,  127,  238,   28,  -10,    0,    0,   31,    0,
  -28,    0,    0,    0,    0,    0,    0,  225,    0,  175,
  228,    0,    0,  886,    0,    0,   59,  852,    0,  -13,
    5,   61,    0,    0,    0,    0,   75,    0,    0,   82,
   93,  246,    0,  863,  -38,    0,    0,    0,    0,    0,
    0,    0,  122,    0,    0,    0,    0,    0,    0,    0,
    0,   87,  120,  -18,  113,    0,  117,  908,  830,    0,
    0,    0,  -70,    0,    0,  102,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  183,    0,  183,    0,    0,
    0,    0,   80,    0,    0,  -10,    0,    0,    0,    0,
    0,  257,  268,  101,    0,  104,    0,    0,    0,    0,
    0,    0,    0,  150,    0,    0,    0,    0,  308,    0,
    0,    0,  137,  289,    0,  906,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  366,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  399,    0,    0,    0,    0,  197,    0,    0,    0,
    0,    0,    0,  141,    0,    0,    0,    0,    0,    0,
    0,    0,  413,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,  437,    0,  456,
    0,    0,    0,    0,  419,    0,  441,    0,    0,    0,
    0,    0,  508,    0,    0,    0,    0,    0,  530,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  236,  380,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  632,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  610,    0,    0,  477,
    0,    0,  560,    0,    0,    0,    0, -202,    0, -176,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  463,  486,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  632,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  759,    0,  781,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  390,    0,
    0,    0,    0,    0,    0, -101,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -51,  391,  267,  841,    0,   20,    2,    0,    0,    0,
    0,    0,    0,    0,  740,    0,  471,    0,  325,  359,
    0,    0,   35,    4,    0,  -12,    0,    0,    0,    0,
    0,    0,    0,    0,  389,    0,  394,  -80,  422,  -76,
    0,   73,    0,  335,
};
final static int YYTABLESIZE=1184;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        143,
   37,  159,  268,   16,   16,   80,   16,   89,   94,  116,
  117,   55,   53,   43,   54,  246,   56,  143,   69,  144,
  160,   16,   16,  230,   16,  151,   97,  152,  162,   65,
  245,   99,  143,  216,  159,   67,  127,   37,   16,   40,
   97,   16,  224,   97,   41,  255,  217,  163,  227,   64,
   52,   55,   53,   67,   54,  141,   56,  116,  225,   55,
   53,  226,   54,  257,   56,  129,   67,  116,  242,   55,
   53,  226,   54,   94,   56,  180,  102,  186,  227,  162,
   94,   51,   52,   55,   53,  127,   54,   16,   56,   16,
   41,   16,  162,   95,   16,   64,   52,   55,   53,   67,
   54,  183,   56,   16,  160,  156,  116,   94,   55,   53,
  188,   54,  151,   56,  152,  104,  101,   55,   53,  259,
   54,   16,   56,  246,  116,   37,   55,   53,  207,   54,
  157,   56,  104,  261,   55,   53,  194,   54,  280,   56,
  263,  116,  278,   55,   53,  141,   54,    3,   56,  279,
  167,  132,   55,   53,  160,   54,  109,   56,   80,  285,
  181,  186,  287,   55,   53,   94,   54,  160,   56,  151,
  175,  152,   77,  134,  135,   16,  132,   55,   53,   16,
   54,  205,   56,   71,  176,  240,  166,   76,   55,  143,
  209,  144,  114,   56,  281,   16,  143,   97,  144,   11,
   72,  176,  204,  115,   13,   94,  186,  143,  289,  144,
   83,  168,  158,   55,  143,   68,  144,  267,   56,   16,
   16,  143,   78,  144,  148,  112,  113,  244,   98,  149,
  142,   45,  114,  208,   48,   49,   50,   42,  229,   11,
   61,   79,   46,  115,   13,   47,  254,   48,   49,   50,
   47,   11,   48,   49,   50,   61,   13,   37,   37,   66,
   42,   37,   37,  186,  256,  128,   45,   48,   49,   50,
   37,   37,  179,  228,   45,   37,   37,   16,  241,   62,
    1,    2,   46,  247,   45,   47,  249,   48,   49,   50,
   46,  220,  221,   47,   62,   48,   49,   50,   45,  248,
   46,   83,  182,   47,  265,   48,   49,   50,  139,  140,
   83,  187,   45,  100,   46,  282,  258,   47,  252,   48,
   49,   50,  154,   45,  222,  206,  283,  223,   46,  291,
  260,   47,   45,   48,   49,   50,  178,  262,  177,   46,
  112,   45,   47,  270,   48,   49,   50,  191,   46,   45,
  151,   47,  152,   48,   49,   50,  284,   46,   45,  286,
   47,  117,   48,   49,   50,   46,  130,   45,   47,  274,
   48,   49,   50,  275,   46,  271,  272,   47,   45,   48,
   49,   50,  239,   46,   73,   74,   47,  131,   48,   49,
   50,  130,   45,  290,   46,   70,  176,   47,   11,   48,
   49,   50,  235,   45,   75,  288,  293,   81,   46,  146,
   45,   47,    8,   48,   49,   50,  190,  236,  238,  192,
  231,   45,   47,   58,   48,   49,   50,  237,   45,   47,
  107,   48,   49,   50,  233,   45,    3,  150,   58,  151,
   47,  152,   48,   49,   50,  145,  147,   47,  107,   48,
   49,   50,   61,   61,   47,    7,   48,   49,   50,  148,
  148,  148,  195,  148,   11,  148,  164,  165,  214,   13,
   82,    8,   61,  176,    9,   10,    1,  148,  148,  174,
  148,  145,  145,  145,   12,  145,  174,  145,  155,   14,
  243,   62,   62,    0,    0,    0,  136,  137,    0,  145,
  145,    0,  145,  151,  151,  151,    0,  151,    0,  151,
    0,   62,  107,  219,  107,  264,  138,    0,  139,  140,
    0,  151,  151,    0,  151,    0,  150,  150,  150,    0,
  150,    0,  150,    0,    0,    0,    0,    0,  232,  234,
  166,  148,    0,  148,  150,  150,  114,  150,  129,    0,
  129,    0,  129,   11,  137,  169,  173,  115,   13,  184,
    0,  189,    0,  145,    0,  145,  129,  129,    0,  129,
  133,  174,  133,  138,  133,  139,  140,  166,    0,  210,
  211,    0,    0,  114,    0,  151,  212,  151,  133,  133,
   11,  133,    0,   11,  115,   13,    0,    0,   13,    0,
   53,  148,  148,   53,  148,    0,  148,    0,  150,    0,
  150,  276,    0,  185,   82,    8,    0,    0,    9,   10,
    0,    0,  151,    0,  152,    0,    0,    0,   12,    0,
  129,    0,  129,   14,    0,   58,   58,    0,    0,  202,
    0,  201,    0,    0,    0,  107,  107,  107,  107,  107,
  107,  107,  133,    0,  133,   58,    0,    0,    0,    0,
  107,    0,    0,    0,    0,  107,    0,    0,  101,    0,
    0,    0,    0,    0,  148,  148,  148,  148,  148,  148,
  148,    0,    0,  148,  148,  148,  148,  148,    0,  148,
    0,    0,    0,    0,  148,    0,  145,  145,  145,  145,
  145,  145,  145,    0,    0,  145,  145,  145,  145,  145,
    0,  145,    0,    0,    0,    0,  145,    0,  151,  151,
  151,  151,  151,  151,  151,   15,    0,  151,  151,  151,
  151,  151,  101,  151,  101,    0,    0,    0,  151,    0,
    0,  150,  150,  150,  150,  150,  150,  150,    0,    0,
  150,  150,  150,  150,  150,    0,  150,    0,    0,    0,
   86,  150,   85,  129,  129,  129,  129,  129,  129,  129,
    0,    0,  129,  129,  129,  129,  129,   93,  129,    0,
    0,    0,  122,  129,    0,  133,  133,  133,  133,  133,
  133,  133,    0,    0,  133,  133,  133,  133,  133,  127,
  133,  127,  218,  127,    0,  133,   96,    0,    0,    0,
    0,    0,    0,    0,    0,  148,   53,  127,  127,  122,
  127,  130,   53,  130,    0,  130,    0,   36,  148,   53,
    0,    0,    0,   53,   53,  196,    0,    0,   39,  130,
  130,    0,  130,    0,  197,  198,  199,  200,    0,   60,
   60,    0,    0,    0,  213,  215,    0,    0,    6,    7,
    8,  172,  122,    9,   10,  101,  101,  101,  101,  101,
  101,  101,   11,   12,    0,  105,    0,   13,   14,    0,
  101,  127,  111,  127,    0,  101,   60,   41,   41,   41,
    0,  133,   41,   41,   82,    8,   83,   84,    9,   10,
    0,    0,   41,  130,  133,  130,  153,   41,   12,    0,
    0,   82,    8,   14,    0,    9,   10,    0,    0,    0,
    0,  193,    0,    0,    0,   12,   92,    0,  108,    0,
   14,    0,  105,    0,    0,  105,   82,    8,    7,    8,
    9,   10,    9,   10,  105,    0,    0,    0,  110,    0,
   12,   11,   12,  273,  277,   14,   13,   14,    6,    7,
    8,    0,  122,    9,   10,    0,    0,    0,    0,   38,
    7,    8,   11,   12,    9,   10,  172,   13,   14,    0,
    0,    0,    0,   11,   12,    0,    0,  266,   13,   14,
    0,    0,  170,   82,    8,   83,  171,    9,   10,    0,
    0,    0,    0,  172,    0,    0,    0,   12,    0,    0,
  251,    0,   14,    0,  127,  127,  127,  127,  127,  127,
  127,    0,    0,  127,  127,  127,  127,  127,    0,  127,
  292,  105,    0,    0,  127,    0,  130,  130,  130,  130,
  130,  130,  130,  269,    0,  130,  130,  130,  130,  130,
    0,  130,    6,    7,    8,    0,  130,    9,   10,    0,
    7,    8,    0,    0,    9,   10,   11,   12,    0,    0,
    0,   13,   14,   11,   12,    0,    0,    0,   13,   14,
    7,    8,    0,    0,    9,   10,   82,    8,    0,    0,
    9,   10,    0,   11,   12,    0,    0,    0,   13,   14,
   12,    0,    0,    0,    0,   14,    0,  170,   82,    8,
    0,  253,    9,   10,    0,    0,    0,    0,    0,    7,
    8,    0,   12,    9,   10,    0,    0,   14,    0,    0,
    0,    0,   11,   12,  170,   82,    8,   13,   14,    9,
   10,  250,   82,    8,    0,    0,    9,   10,    0,   12,
    0,    0,    0,    0,   14,    0,   12,    0,    0,    0,
    0,   14,   82,    8,   82,    8,    9,   10,    9,   10,
    0,    0,    0,    0,    0,    0,   12,    0,   12,    0,
    0,   14,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         43,
    0,   45,   41,    2,    3,   40,    5,   20,   21,   40,
   41,   42,   43,   40,   45,   44,   47,   43,   59,   45,
   72,   20,   21,   41,   23,   43,   23,   45,  257,   10,
   59,   59,   43,  256,   45,   40,   40,    3,   37,    5,
   37,   40,  123,   40,   59,   59,  269,  276,  125,   40,
   41,   42,   43,  256,   45,   54,   47,   40,   41,   42,
   43,   44,   45,   59,   47,   46,  269,   40,   41,   42,
   43,   44,   45,   86,   47,   88,  123,   90,  155,  256,
   93,   40,   41,   42,   43,   40,   45,   86,   47,   88,
   59,   90,  269,   21,   93,   40,   41,   42,   43,   40,
   45,   59,   47,  102,  156,   61,   40,  120,   42,   43,
   59,   45,   43,   47,   45,   40,   59,   42,   43,   59,
   45,  120,   47,   44,   40,  125,   42,   43,   59,   45,
  276,   47,   40,   59,   42,   43,  102,   45,   59,   47,
   59,   40,  223,   42,   43,  144,   45,  123,   47,  226,
   59,   41,   42,   43,  256,   45,  125,   47,   40,   59,
   88,  174,   59,   42,   43,  178,   45,  269,   47,   43,
   59,   45,   44,  278,  279,  174,   41,   42,   43,  178,
   45,  256,   47,   44,   44,   59,  257,   59,   42,   43,
  257,   45,  263,   47,  246,  194,   43,  194,   45,  270,
   61,   61,   41,  274,  275,  218,  219,   43,   59,   45,
  259,  260,  256,   42,   43,  256,   45,  256,   47,  218,
  219,   43,  257,   45,   42,  256,  257,  256,  256,   47,
  256,  257,  263,   41,  278,  279,  280,  264,  256,  270,
   44,  276,  273,  274,  275,  276,  260,  278,  279,  280,
  276,  270,  278,  279,  280,   59,  275,  257,  258,  264,
  264,  261,  262,  276,  260,  256,  257,  278,  279,  280,
  270,  271,   59,   41,  257,  275,  276,  276,   41,   44,
  256,  257,  273,   59,  257,  276,   59,  278,  279,  280,
  273,  256,  257,  276,   59,  278,  279,  280,  257,  125,
  273,  259,  260,  276,   59,  278,  279,  280,  278,  279,
  259,  260,  257,  256,  273,   59,  256,  276,  260,  278,
  279,  280,  256,  257,   41,  256,   59,   44,  273,   41,
  256,  276,  257,  278,  279,  280,  123,  256,  125,  273,
  256,  257,  276,  257,  278,  279,  280,  272,  273,  257,
   43,  276,   45,  278,  279,  280,  256,  273,  257,  256,
  276,   41,  278,  279,  280,  273,  256,  257,  276,  257,
  278,  279,  280,  257,  273,  256,  257,  276,  257,  278,
  279,  280,  256,  273,  256,  257,  276,  277,  278,  279,
  280,  256,  257,  257,  273,  256,  256,  276,    0,  278,
  279,  280,  256,  257,  276,  256,   41,   19,  273,  256,
  257,  276,    0,  278,  279,  280,   92,  151,  152,   95,
  256,  257,  276,   44,  278,  279,  280,  256,  257,  276,
   41,  278,  279,  280,  256,  257,    0,   41,   59,   43,
  276,   45,  278,  279,  280,   55,   56,  276,   59,  278,
  279,  280,  256,  257,  276,    0,  278,  279,  280,   41,
   42,   43,  104,   45,  270,   47,   78,   79,  274,  275,
  257,  258,  276,  260,  261,  262,    0,   59,   60,   86,
   62,   41,   42,   43,  271,   45,   93,   47,   67,  276,
  156,  256,  257,   -1,   -1,   -1,  256,  257,   -1,   59,
   60,   -1,   62,   41,   42,   43,   -1,   45,   -1,   47,
   -1,  276,  123,  120,  125,  191,  276,   -1,  278,  279,
   -1,   59,   60,   -1,   62,   -1,   41,   42,   43,   -1,
   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,  148,  149,
  257,  123,   -1,  125,   59,   60,  263,   62,   41,   -1,
   43,   -1,   45,  270,  257,   85,   86,  274,  275,   89,
   -1,   91,   -1,  123,   -1,  125,   59,   60,   -1,   62,
   41,  178,   43,  276,   45,  278,  279,  257,   -1,  256,
  257,   -1,   -1,  263,   -1,  123,  263,  125,   59,   60,
  270,   62,   -1,  270,  274,  275,   -1,   -1,  275,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,  123,   -1,
  125,  218,   -1,  256,  257,  258,   -1,   -1,  261,  262,
   -1,   -1,   43,   -1,   45,   -1,   -1,   -1,  271,   -1,
  123,   -1,  125,  276,   -1,  256,  257,   -1,   -1,   60,
   -1,   62,   -1,   -1,   -1,  256,  257,  258,  259,  260,
  261,  262,  123,   -1,  125,  276,   -1,   -1,   -1,   -1,
  271,   -1,   -1,   -1,   -1,  276,   -1,   -1,   59,   -1,
   -1,   -1,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,
   -1,   -1,   -1,   -1,  276,   -1,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,  266,  267,  268,  269,
   -1,  271,   -1,   -1,   -1,   -1,  276,   -1,  256,  257,
  258,  259,  260,  261,  262,  123,   -1,  265,  266,  267,
  268,  269,  123,  271,  125,   -1,   -1,   -1,  276,   -1,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,   -1,   -1,   -1,
  123,  276,  125,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,  265,  266,  267,  268,  269,  123,  271,   -1,
   -1,   -1,   43,  276,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   41,
  271,   43,  123,   45,   -1,  276,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   59,   60,   80,
   62,   41,  263,   43,   -1,   45,   -1,  125,  269,  270,
   -1,   -1,   -1,  274,  275,  256,   -1,   -1,  125,   59,
   60,   -1,   62,   -1,  265,  266,  267,  268,   -1,    9,
   10,   -1,   -1,   -1,  115,  116,   -1,   -1,  256,  257,
  258,  125,  123,  261,  262,  256,  257,  258,  259,  260,
  261,  262,  270,  271,   -1,   35,   -1,  275,  276,   -1,
  271,  123,   42,  125,   -1,  276,   46,  256,  257,  258,
   -1,   51,  261,  262,  257,  258,  259,  260,  261,  262,
   -1,   -1,  271,  123,   64,  125,   66,  276,  271,   -1,
   -1,  257,  258,  276,   -1,  261,  262,   -1,   -1,   -1,
   -1,  125,   -1,   -1,   -1,  271,  272,   -1,  125,   -1,
  276,   -1,   92,   -1,   -1,   95,  257,  258,  257,  258,
  261,  262,  261,  262,  104,   -1,   -1,   -1,  125,   -1,
  271,  270,  271,  214,  125,  276,  275,  276,  256,  257,
  258,   -1,  223,  261,  262,   -1,   -1,   -1,   -1,  256,
  257,  258,  270,  271,  261,  262,  125,  275,  276,   -1,
   -1,   -1,   -1,  270,  271,   -1,   -1,  125,  275,  276,
   -1,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,  125,   -1,   -1,   -1,  271,   -1,   -1,
  125,   -1,  276,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,  271,
  125,  191,   -1,   -1,  276,   -1,  256,  257,  258,  259,
  260,  261,  262,  203,   -1,  265,  266,  267,  268,  269,
   -1,  271,  256,  257,  258,   -1,  276,  261,  262,   -1,
  257,  258,   -1,   -1,  261,  262,  270,  271,   -1,   -1,
   -1,  275,  276,  270,  271,   -1,   -1,   -1,  275,  276,
  257,  258,   -1,   -1,  261,  262,  257,  258,   -1,   -1,
  261,  262,   -1,  270,  271,   -1,   -1,   -1,  275,  276,
  271,   -1,   -1,   -1,   -1,  276,   -1,  256,  257,  258,
   -1,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,  257,
  258,   -1,  271,  261,  262,   -1,   -1,  276,   -1,   -1,
   -1,   -1,  270,  271,  256,  257,  258,  275,  276,  261,
  262,  256,  257,  258,   -1,   -1,  261,  262,   -1,  271,
   -1,   -1,   -1,   -1,  276,   -1,  271,   -1,   -1,   -1,
   -1,  276,  257,  258,  257,  258,  261,  262,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,  271,   -1,
   -1,  276,   -1,  276,
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
"inicio_if : inicio_if_sin_condicion condicional_opt",
"inicio_if_sin_condicion : IF",
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

//#line 862 "parser.y"
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
    al.ts.insertar("-1L", new Atributo(0,-1,Atributo.USO_AUXILIAR));
    al.ts.insertar("0L", new Atributo(0,0,Atributo.USO_AUXILIAR));
    this.traductor = new Traductor(al.ts,errManager);
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
//#line 813 "Parser.java"
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
case 21:
//#line 84 "parser.y"
{ generador.agregarTerceto("drop", "-", "-"); }
break;
case 23:
//#line 87 "parser.y"
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
//#line 99 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 101 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 103 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 28:
//#line 108 "parser.y"
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
//#line 124 "parser.y"
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
//#line 140 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 31:
//#line 142 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 32:
//#line 144 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 34:
//#line 149 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 36:
//#line 154 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 37:
//#line 155 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 40:
//#line 163 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 48:
//#line 181 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 49:
//#line 186 "parser.y"
{
	        errManager.debug("Parametro formal con semantica copia-resultado detectado.", al.getLine());
	        generador.agregarParametro(true,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 50:
//#line 191 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 193 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 195 "parser.y"
{
	        errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine());
	        generador.agregarParametro(false,val_peek(1).ival,val_peek(0).sval);
	    }
break;
case 53:
//#line 200 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 54:
//#line 202 "parser.y"
{
	        errManager.debug("Parametro formal lambda semantica detectado", al.getLine());
	        generador.agregarParametro(false,Atributo.lambdaType,val_peek(0).sval);
	    }
break;
case 55:
//#line 207 "parser.y"
{ errManager.error("Semantica Copia Resultado invalida en el contexto", al.getLine()); }
break;
case 56:
//#line 209 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 211 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 58:
//#line 216 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(1).ival);
            yyval.ival = val_peek(1).ival;
	    }
break;
case 59:
//#line 221 "parser.y"
{
	        declararVariable(val_peek(0).sval,val_peek(2).ival);
	    }
break;
case 60:
//#line 225 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 61:
//#line 227 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 229 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 231 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 64:
//#line 233 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 65:
//#line 240 "parser.y"
{
	        errManager.debug("Parametro real detectado", al.getLine());
            generador.agregarParametroReal(val_peek(2).sval, val_peek(0).sval);
	    }
break;
case 66:
//#line 245 "parser.y"
{
            errManager.error("Parametro real debe ser vinculado a formal (->)", al.getLine());
        }
break;
case 69:
//#line 257 "parser.y"
{
	        generador.agregarTerceto("endif", "-", "-");
	    }
break;
case 71:
//#line 266 "parser.y"
{
	        errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 2; /*+2 por terceto endif*/

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);

	    }
break;
case 72:
//#line 277 "parser.y"
{
    	    errManager.debug("IF detectado (sin ELSE)", al.getLine());

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque then.*/
            int afterThen = generador.getUltimoTerceto() + 2;

            /* Backpatch BF para saltar a afterThen (no hay else).*/
            generador.rellenarOperando(val_peek(3).ival, afterThen, 2);

    	}
break;
case 73:
//#line 288 "parser.y"
{
	        errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);

	    }
break;
case 74:
//#line 296 "parser.y"
{
            errManager.debug("IF-ELSE detectado", al.getLine());

            /* rellenamos Backpatch BF para saltar al inicio del else*/
            generador.rellenarOperando(val_peek(2).ival, val_peek(0).ival, 2);

        }
break;
case 75:
//#line 304 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 76:
//#line 306 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 77:
//#line 308 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 78:
//#line 310 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 79:
//#line 312 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 80:
//#line 314 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 81:
//#line 316 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 82:
//#line 318 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 83:
//#line 320 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 84:
//#line 322 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 85:
//#line 327 "parser.y"
{
            /* $2 = indice de terceto condicion.*/
            /* Generar BF con destino desconocido.*/
            int idxBF = generador.generarBF(val_peek(0).ival);
            yyval.ival = idxBF;
        }
break;
case 86:
//#line 335 "parser.y"
{ generador.agregarTerceto("if_inicio", "-", "-"); }
break;
case 87:
//#line 339 "parser.y"
{
            /* Backpatch BF para saltar al Else.*/
            /*Lo pasamos para arriba asi se rellena el BF*/
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque else.*/
            int afterElse = generador.getUltimoTerceto() + 2;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 88:
//#line 351 "parser.y"
{
            yyval.ival = val_peek(3).ival + 1;

            /* Calculamos el indice del terceto siguiente a la ultima instruccion del bloque else.*/
            int afterElse = generador.getUltimoTerceto() + 2;

            /* Backpatch BI para saltar a afterElse.*/
            generador.rellenarOperando(val_peek(3).ival, afterElse, 1);
        }
break;
case 89:
//#line 361 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 90:
//#line 363 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 91:
//#line 365 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 92:
//#line 367 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 93:
//#line 369 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 94:
//#line 371 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 95:
//#line 373 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 96:
//#line 375 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 97:
//#line 380 "parser.y"
{
          int idxBI = generador.generarBI();
          /*devolvemos la instruccion donde se genero el BI*/
          yyval.ival = idxBI;
        }
break;
case 98:
//#line 389 "parser.y"
{ yyval.ival = val_peek(1).ival; }
break;
case 99:
//#line 391 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 100:
//#line 393 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 101:
//#line 395 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 103:
//#line 401 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 104:
//#line 403 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 105:
//#line 405 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 106:
//#line 407 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 107:
//#line 413 "parser.y"
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
case 108:
//#line 426 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 109:
//#line 431 "parser.y"
{
	        errManager.debug("DO-UNTIL detectado", al.getLine());
            int indiceInicio = val_peek(4).ival;   /* primer terceto del cuerpo*/
            int indiceCondicion  = val_peek(1).ival;   /* indice del terceto que representa la condicion*/

            /* Generamos BF y luego rellenamos su destino al inicio del cuerpo.*/
            int indiceBF = generador.generarBF(indiceCondicion);
            generador.rellenarOperando(indiceBF, indiceInicio, 2);
	    }
break;
case 110:
//#line 441 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 111:
//#line 443 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 112:
//#line 445 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 113:
//#line 447 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 114:
//#line 452 "parser.y"
{
            /* Guardamos la direccion de inicio del DO.*/
            generador.agregarTerceto("do_inicio","-","-");
            yyval.ival = generador.getUltimoTerceto() + 1;
        }
break;
case 115:
//#line 460 "parser.y"
{ yyval.sval = "<="; }
break;
case 116:
//#line 462 "parser.y"
{ yyval.sval = ">="; }
break;
case 117:
//#line 464 "parser.y"
{ yyval.sval = "=="; }
break;
case 118:
//#line 466 "parser.y"
{ yyval.sval = "!="; }
break;
case 119:
//#line 468 "parser.y"
{ yyval.sval = ">"; }
break;
case 120:
//#line 470 "parser.y"
{ yyval.sval = "<"; }
break;
case 121:
//#line 475 "parser.y"
{ yyval.ival = 0; }
break;
case 122:
//#line 477 "parser.y"
{ errManager.error("Tipo string no permitido.",al.getLine()); }
break;
case 123:
//#line 482 "parser.y"
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
case 124:
//#line 506 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 125:
//#line 508 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 126:
//#line 510 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 127:
//#line 515 "parser.y"
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
case 128:
//#line 526 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 129:
//#line 528 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 130:
//#line 530 "parser.y"
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
case 131:
//#line 541 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 132:
//#line 543 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 133:
//#line 545 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 134:
//#line 547 "parser.y"
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
case 135:
//#line 559 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 136:
//#line 564 "parser.y"
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
case 137:
//#line 575 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 138:
//#line 577 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 139:
//#line 579 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 140:
//#line 581 "parser.y"
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
case 141:
//#line 592 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 142:
//#line 594 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 143:
//#line 596 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 144:
//#line 598 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 145:
//#line 603 "parser.y"
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
case 146:
//#line 615 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 147:
//#line 617 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 148:
//#line 619 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 149:
//#line 621 "parser.y"
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
case 150:
//#line 632 "parser.y"
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
case 151:
//#line 654 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 152:
//#line 659 "parser.y"
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
case 153:
//#line 689 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 154:
//#line 691 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 155:
//#line 693 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 156:
//#line 698 "parser.y"
{
	        errManager.debug("Print detectado", al.getLine());
	        generador.agregarTerceto("print", val_peek(0).sval, "-");
	    }
break;
case 157:
//#line 705 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 158:
//#line 707 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 159:
//#line 711 "parser.y"
{
	        errManager.debug("Definicion lambda detectada", al.getLine());

	        generador.agregarTerceto("fin_funcion", val_peek(3).sval, "-");
	        generador.exitScope();

	        yyval.sval = val_peek(3).sval;
	    }
break;
case 160:
//#line 720 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 161:
//#line 722 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 162:
//#line 724 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 163:
//#line 729 "parser.y"
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
case 164:
//#line 746 "parser.y"
{ errManager.error("Semantica invalida en funciones lambda.", al.getLine()); }
break;
case 165:
//#line 752 "parser.y"
{
	        errManager.debug("Retorno detectado. Linea: " + al.getLine());
	        generador.agregarTerceto("return", val_peek(0).sval, "-");
	    }
break;
case 166:
//#line 760 "parser.y"
{ yyval.sval = val_peek(1).sval; }
break;
case 167:
//#line 762 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 168:
//#line 764 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 169:
//#line 766 "parser.y"
{ errManager.error("Faltan argumentos", al.getLine()); }
break;
case 170:
//#line 768 "parser.y"
{ errManager.error("Falta parentesis de cierre", al.getLine()); }
break;
case 171:
//#line 770 "parser.y"
{ errManager.error("Falta parentesis de apertura", al.getLine()); }
break;
case 172:
//#line 775 "parser.y"
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
case 173:
//#line 790 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 174:
//#line 792 "parser.y"
{ errManager.error("Falta de separador: ','", al.getLine()); }
break;
case 175:
//#line 794 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 176:
//#line 799 "parser.y"
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
//#line 810 "parser.y"
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
case 178:
//#line 824 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 179:
//#line 826 "parser.y"
{ generador.agregarConstanteMultiple(val_peek(0).sval, al.ts); }
break;
case 180:
//#line 831 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 181:
//#line 836 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 182:
//#line 841 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 183:
//#line 846 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 184:
//#line 851 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 185:
//#line 853 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 186:
//#line 858 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1969 "Parser.java"
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
