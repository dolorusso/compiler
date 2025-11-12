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
    0,    0,    4,    4,    5,    5,    5,    6,    6,    6,
    6,    6,    6,    6,    6,    6,    8,   15,   15,   15,
   15,   17,   17,   16,   16,   16,    3,    3,    3,   19,
   19,   18,   18,   18,   21,   21,   21,   20,   20,   20,
   20,   20,   20,   20,   20,   20,    7,    7,    7,    7,
    7,    7,    7,   22,   23,   23,   10,   10,   26,   26,
   26,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   26,   26,   30,   30,   30,   30,   30,   30,   30,   30,
   30,   30,   28,   28,   28,   28,   29,   29,   29,   29,
   29,   31,   31,   27,   27,   27,   27,   27,   32,   32,
   32,   32,   32,   32,    2,    2,    9,    9,    9,    9,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   33,
   33,   33,   33,   33,   33,   33,   33,   33,   35,   35,
   35,   35,   35,   35,   35,   35,   35,   11,   11,   11,
   11,   12,   25,   25,   25,   25,   13,   34,   34,   34,
   34,   34,   34,   14,   14,   14,   14,   36,   36,   37,
   37,    1,    1,    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    4,    4,    3,    5,    3,    3,    3,    2,    3,    2,
    1,    1,    1,    1,    2,    2,    1,    1,    1,    2,
    2,    2,    1,    2,    2,    2,    2,    3,    2,    2,
    3,    3,    2,    3,    2,    0,    1,    2,    2,    1,
    2,    1,    3,    2,    1,    3,    2,    3,    2,    3,
    2,    1,    2,    3,    2,    2,    2,    3,    2,    1,
    2,    3,    2,    3,    1,    1,    1,    1,    5,    5,
    4,    4,    5,    5,    4,    4,    5,    4,    5,    4,
    4,    4,    4,    4,    4,    4,    3,    3,    2,    4,
    4,    2,    3,    2,    3,    1,    3,    3,    2,    2,
    3,    3,    2,    5,    5,    4,    4,    4,    1,    1,
    1,    1,    1,    1,    1,    1,    4,    4,    4,    4,
    3,    3,    1,    3,    3,    2,    1,    2,    2,    3,
    2,    3,    2,    3,    2,    3,    2,    1,    1,    1,
    1,    1,    1,    2,    2,    2,    2,    4,    4,    4,
    4,    2,    7,    6,    6,    4,    2,    3,    2,    3,
    2,    2,    1,    4,    5,    3,    4,    1,    3,    3,
    1,    1,    1,    2,    2,    1,    2,    2,
};
final static short yydefred[] = {                         0,
   12,    0,    0,    0,    0,    0,    0,    0,    0,  115,
    0,  116,    0,    0,    0,    0,   37,   13,   14,    0,
   17,   18,   19,    0,    0,    0,   23,    0,   67,   68,
    0,   10,    0,   39,    0,    0,   29,    0,    0,    0,
  141,  172,  173,  176,    0,    0,    0,    0,    0,  140,
  142,    0,    0,    0,    0,  138,    0,  163,    0,  152,
  157,    0,    0,    0,   40,    0,    0,    0,    0,    0,
    9,    0,    0,    0,    0,   30,    6,   38,   16,   63,
   59,   15,    0,   24,   20,   25,   21,   26,   22,    0,
   27,    0,    0,    0,    5,    0,    0,    0,    0,    0,
    0,   33,    0,    0,   42,    0,   45,    0,    0,   66,
    0,  129,  128,    0,  177,  178,  126,    0,    0,  147,
  174,  175,  144,  133,    0,    0,  131,  137,  135,  103,
  109,  110,  111,  112,  113,  114,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   94,    0,    0,  162,
  161,    0,  159,    0,    0,  100,    0,   99,   41,    0,
    0,    0,    0,    0,    2,    0,   28,   31,   52,   62,
   58,   35,    0,    0,  169,  166,    0,  171,    0,  120,
  119,  151,   53,   55,   49,    0,    0,    0,   56,   51,
   32,    0,   44,  149,    0,   47,    0,   95,   93,  122,
    0,  125,    0,    0,   92,    0,    0,   89,    0,    0,
   81,    0,   78,    0,   80,    0,   76,   72,    0,   75,
   71,   82,  132,  130,  136,  134,  160,  158,  107,  106,
  101,   98,   97,    0,  108,  118,  117,  150,  148,    4,
   34,    0,  167,  164,    0,   54,   50,   48,    0,   43,
   46,   64,    0,    0,    0,   88,    0,   87,   77,   79,
   74,   70,   73,   69,  105,  104,  165,  170,    0,   90,
   91,   86,   84,   85,   83,    0,    0,    0,  155,  153,
};
final static short yydgoto[] = {                          4,
   50,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   51,   25,   26,   27,   28,   91,   37,  104,   66,  105,
  106,  107,  108,  109,  110,   29,   30,   53,   67,  146,
   54,  139,   55,   60,   56,   31,  179,
};
final static short yysindex[] = {                       -73,
    0,  339,  655,    0,  -11,  -27,   83,   59,   59,    0,
  610,    0,   -3,  676,  -29,  771,    0,    0,    0,  170,
    0,    0,    0,  -40,  -23,   23,    0,  -20,    0,    0,
   36,    0,  782,    0,  127,  -25,    0,   70,   26,   89,
    0,    0,    0,    0,  127, -238,  187,    9,   41,    0,
    0,  562,  359,   49,   -4,    0,  119,    0,  168,    0,
    0,    4,   83,  836,    0,  869,  -37,  127,   69,  -17,
    0,  793,  105,  105,  530,    0,    0,    0,    0,    0,
    0,    0, -204,    0,    0,    0,    0,    0,    0,  687,
    0,   -5, -123,  -22,    0,  -10,  118,   70,  -92,  -70,
 -102,    0, -125,  324,    0,   34,    0,  -94,   20,    0,
   94,    0,    0,  -34,    0,    0,    0,   70,   89,    0,
    0,    0,    0,    0, -238,  191,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  101,  134,  127,  588,
  130,  708,  181,  -33,   63,  133,    0,  145,  152,    0,
    0,   44,    0,   24,   76,    0,  847,    0,    0,   83,
  147,  137,  167,   51,    0,  -44,    0,    0,    0,    0,
    0,    0,  804,   15,    0,    0,  193,    0,  -12,    0,
    0,    0,    0,    0,    0,  -42,  229,  -38,    0,    0,
    0,  -72,    0,    0,  109,    0,  -36,    0,    0,    0,
   -4,    0,   -4,   20,    0,  825,  -43,    0,  -35,  -30,
    0,  225,    0,  231,    0,   88,    0,    0,   99,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  108,    0,    0,    0,    0,    0,    0,
    0,   98,    0,    0,   15,    0,    0,    0,  260,    0,
    0,    0,  250,  256,  159,    0,  164,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  633,    0,
    0,    0,    0,    0,    0,  893,  853,  877,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  333,    0,    0,    0,  237,    0,    0,    0,    0,
    0,    0,  163,    0,    0,  341,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  392,    0,  414,
    0,    0,    0,    0,    0,  481,    0,    0,    0,    0,
    0,    0,    0,  564,  503,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  343,
    0,  383,  310,  453,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  533,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   84,    0,
    0,    0,    0,    0,    0,    0,    0,  436,  459,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  885,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  394,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  732,    0,  754,  293,    0,    0,    0,    0,  885,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  148,    0,
    0,    0,    0,    0,    0,    0,    0,  169,    0,    0,
};
final static short yygindex[] = {                         0,
  -67,  762,   25,  420,    0,   -7,    0,    0,    0,    0,
   -2,    0,    0,    0,    0,    0,   46,    0,  386,  -74,
  373,   -1,    0,  835,    0,    0,    0,   50,  -19,  365,
  400,    0,  353,   16,  440,    0,  273,
};
final static int YYTABLESIZE=1169;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         24,
   24,   36,   45,   65,   48,   46,  199,   47,   24,   49,
   75,   24,   36,   24,  101,  102,   48,   46,   85,   47,
  125,   49,  177,  256,   61,  217,  178,   33,  258,  193,
   24,  245,  137,  145,  138,   87,   69,  148,   72,  115,
  116,   34,  149,  111,  123,  144,  244,   34,  181,    3,
   24,  125,  170,  126,  113,  174,   65,  125,  159,  177,
   76,   24,  137,   24,  138,   57,   58,   48,   46,   24,
   47,  171,   49,  101,  194,   48,   46,  195,   47,   93,
   49,   89,  230,  125,  228,  126,  137,   24,  138,  147,
  101,  239,   48,   46,  195,   47,   94,   49,   57,   58,
   48,   46,   90,   47,  196,   49,  178,  165,  101,  111,
   48,   46,  154,   47,  173,   49,  161,  250,  167,  168,
  210,  220,   45,  123,   48,   46,   36,   47,   69,   49,
  189,  190,  209,  101,   65,   48,   46,   24,   47,   24,
   49,  245,   48,  125,   75,  126,  262,   49,  101,  159,
   48,   46,  175,   47,   24,   49,  267,  264,  182,  151,
   48,   46,  196,   47,  183,   49,  266,   10,   48,   46,
   24,   47,   12,   49,  197,   48,  125,  268,  126,  137,
   49,  138,    1,    2,  169,  184,  185,  125,  211,  126,
   99,  222,  186,  251,  125,  237,  126,   10,   65,   10,
  231,  100,   12,   24,   12,  235,  168,  238,  153,  234,
  137,  240,  138,   83,  246,   84,  254,  273,  249,   38,
  252,  198,  275,  168,  255,  140,  216,   73,   82,  257,
   97,   98,   86,  176,  160,   39,   35,   99,   40,   41,
   42,   43,   44,  243,   10,  180,   74,   39,  100,   12,
   40,   41,   42,   43,   44,   42,   43,   44,   36,   36,
   68,   65,   36,   36,  124,   38,   24,   35,   65,  159,
  159,   36,   36,   24,   24,   24,   36,   36,   88,  229,
   60,  112,   38,  259,   40,   41,   42,   43,   44,  260,
   38,   92,   42,   43,   44,   60,  128,   38,   39,  227,
  269,   40,   41,   42,   43,   44,   39,   38,  270,   40,
   41,   42,   43,   44,  271,   38,   40,   41,   42,   43,
   44,  140,  219,   39,  163,   38,   40,   41,   42,   43,
   44,   39,   11,  102,   40,   41,   42,   43,   44,   38,
    8,   39,    3,  261,   40,   41,   42,   43,   44,   97,
   38,  102,   65,   61,  263,   39,  200,   38,   40,   41,
   42,   43,   44,  265,  191,   38,   39,  192,   61,   40,
   41,   42,   43,   44,  150,   38,   40,   41,   42,   43,
   44,   39,    7,   38,   40,   41,   42,   43,   44,  202,
   38,   39,  236,    1,   40,   41,   42,   43,   44,   39,
  223,   38,   40,   41,   42,   43,   44,  225,   38,   40,
   41,   42,   43,   44,  272,  102,  156,  102,  168,  274,
   40,   41,   42,   43,   44,   79,   80,   40,   41,   42,
   43,   44,  143,  143,  143,   78,  143,  154,  143,  140,
  214,  164,  117,  118,  114,   81,  242,  118,    0,  157,
  143,  143,   78,  143,  139,  139,  139,    0,  139,    0,
  139,   14,  119,  120,  121,  122,  119,  120,  121,  122,
  121,  122,  139,  139,    0,  139,  146,  146,  146,    0,
  146,  142,  146,  143,  247,  248,    0,  127,  129,  201,
  203,   78,   60,   60,  146,  146,   57,  146,    0,  145,
  145,  145,    0,  145,    0,  145,  213,  215,  218,  221,
    0,   57,   60,    0,  143,    0,  143,  145,  145,    0,
  145,  123,    0,  123,    0,  123,    0,  157,    0,    0,
    0,    0,    0,    0,    0,    0,  139,    0,  139,  123,
  123,    0,  123,  127,    0,  127,    0,  127,  102,  102,
  102,  102,  102,  102,  102,    0,    0,    0,  146,    0,
  146,  127,  127,  102,  127,   61,   61,    0,  102,    0,
  102,    0,    0,   52,  143,  143,   52,  143,    0,  143,
  169,  145,    0,  145,    0,   61,   99,  224,  226,    0,
    0,  157,   78,   10,    5,    6,    7,  100,   12,    8,
    9,    0,    0,  123,  137,  123,  138,    0,   10,   11,
    0,    0,    0,   12,   13,   62,    7,  140,  141,    8,
    9,  136,   96,  135,    0,  127,    0,  127,    0,   11,
    0,    0,    0,    0,   13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  208,  143,  143,  143,
  143,  143,  143,  143,  277,    0,  143,  143,  143,  143,
  143,  278,  143,    0,    0,    0,    0,  143,    0,  139,
  139,  139,  139,  139,  139,  139,    0,    0,  139,  139,
  139,  139,  139,    0,  139,    0,   96,    0,   96,  139,
    0,  146,  146,  146,  146,  146,  146,  146,    0,    0,
  146,  146,  146,  146,  146,    0,  146,    0,   57,   57,
  206,  146,  207,    0,  145,  145,  145,  145,  145,  145,
  145,    0,    0,  145,  145,  145,  145,  145,   57,  145,
    0,    0,   64,    0,  145,    0,  123,  123,  123,  123,
  123,  123,  123,    0,    0,  123,  123,  123,  123,  123,
    0,  123,    0,    0,    0,  276,  123,    0,  127,  127,
  127,  127,  127,  127,  127,    0,    0,  127,  127,  127,
  127,  127,  121,  127,  121,    0,  121,    0,  127,   32,
    0,    0,    0,    0,    0,    0,  169,    0,    0,   52,
  121,  121,   99,  121,  124,   52,  124,  103,  124,   10,
   71,  143,   52,  100,   12,    0,   52,   52,    0,    0,
    0,  172,  124,  124,    0,  124,    0,  130,    0,   96,
   96,   96,   96,   96,   96,   96,  131,  132,  133,  134,
    0,    0,  156,    0,   96,    0,  103,    0,    0,   96,
    0,   52,   59,   59,   62,    7,    0,  205,    8,    9,
    0,    0,    0,    0,  121,    0,  121,    0,   11,    0,
    0,  187,  188,   13,    0,  103,   62,    7,    0,   96,
    8,    9,    0,   59,    0,    0,  124,    0,  124,   52,
   11,   63,    0,    0,    0,   13,    0,    0,    0,   62,
    7,  152,    0,    8,    9,   77,    0,   52,    0,    0,
    0,   52,  162,   11,    0,    0,   95,    0,   13,    0,
    5,    6,    7,    0,    0,    8,    9,  166,    0,    0,
    0,    0,    0,    0,   10,   11,    0,    0,  241,   12,
   13,   70,    6,    7,    0,    0,    8,    9,    0,    0,
    0,    0,    5,    6,    7,   10,   11,    8,    9,  156,
   12,   13,    0,  103,    0,    0,   10,   11,    0,    0,
  156,   12,   13,  155,   62,    7,  140,  212,    8,    9,
    0,  233,    0,  204,    0,    0,    0,  279,   11,    0,
    0,    0,    0,   13,    0,    0,    0,  121,  121,  121,
  121,  121,  121,  121,   52,    0,  121,  121,  121,  121,
  121,  280,  121,    0,    0,    0,    0,  121,    0,  124,
  124,  124,  124,  124,  124,  124,    0,    0,  124,  124,
  124,  124,  124,    0,  124,    0,    0,    6,    7,  124,
    0,    8,    9,    0,    0,    0,    0,    0,    6,    7,
   10,   11,    8,    9,    0,   12,   13,    0,    0,    6,
    7,   10,   11,    8,    9,    0,   12,   13,    0,    0,
    6,    7,   10,   11,    8,    9,    0,   12,   13,    0,
    0,    0,    0,   10,   11,    0,    0,    0,   12,   13,
  155,   62,    7,    0,  253,    8,    9,    0,    0,    0,
    0,  155,   62,    7,    0,   11,    8,    9,    0,    0,
   13,    0,  232,   62,    7,    0,   11,    8,    9,   62,
    7,   13,    0,    8,    9,    0,    0,   11,    0,    0,
    0,    0,   13,   11,  158,   62,    7,    0,   13,    8,
    9,    0,    0,   62,    7,    0,    0,    8,    9,   11,
   40,   40,   40,    0,   13,   40,   40,   11,    0,   62,
    7,    0,   13,    8,    9,   40,    0,    0,    0,    0,
   40,    0,    0,   11,    0,    0,    0,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    3,    0,   40,   11,   42,   43,   41,   45,   11,   47,
   40,   14,   40,   16,   40,   41,   42,   43,   59,   45,
   43,   47,   45,   59,    9,   59,   94,    3,   59,  104,
   33,   44,   43,   53,   45,   59,   40,   42,   14,  278,
  279,   59,   47,   40,   47,   53,   59,   59,   59,  123,
   53,   43,  257,   45,   39,   61,   64,   43,   66,   45,
   15,   64,   43,   66,   45,   40,   41,   42,   43,   72,
   45,  276,   47,   40,   41,   42,   43,   44,   45,   44,
   47,   59,   59,   43,   41,   45,   43,   90,   45,   41,
   40,   41,   42,   43,   44,   45,   61,   47,   40,   41,
   42,   43,  123,   45,  106,   47,  174,  125,   40,   40,
   42,   43,   63,   45,   90,   47,   67,  192,   73,   74,
  140,   59,   40,  126,   42,   43,  125,   45,   40,   47,
  256,  257,  140,   40,  142,   42,   43,  140,   45,  142,
   47,   44,   42,   43,   40,   45,   59,   47,   40,  157,
   42,   43,  276,   45,  157,   47,   59,   59,   41,   41,
   42,   43,  164,   45,  257,   47,   59,  270,   42,   43,
  173,   45,  275,   47,  269,   42,   43,  245,   45,   43,
   47,   45,  256,  257,  257,  256,  257,   43,   59,   45,
  263,   59,  263,  195,   43,   59,   45,  270,  206,  270,
  125,  274,  275,  206,  275,   59,   44,   41,   41,  160,
   43,  256,   45,   44,  257,  256,  260,   59,  257,  257,
  257,  256,   59,   61,  260,  259,  260,  257,   59,  260,
  256,  257,  256,  256,  272,  273,  264,  263,  276,  277,
  278,  279,  280,  256,  270,  256,  276,  273,  274,  275,
  276,  277,  278,  279,  280,  278,  279,  280,  257,  258,
  264,  269,  261,  262,  256,  257,  269,  264,  276,  277,
  278,  270,  271,  276,  277,  278,  275,  276,  256,  256,
   44,  256,  257,   59,  276,  277,  278,  279,  280,   59,
  257,  256,  278,  279,  280,   59,  256,  257,  273,  256,
   41,  276,  277,  278,  279,  280,  273,  257,   59,  276,
  277,  278,  279,  280,   59,  257,  276,  277,  278,  279,
  280,  259,  260,  273,  256,  257,  276,  277,  278,  279,
  280,  273,    0,   41,  276,  277,  278,  279,  280,  257,
    0,  273,    0,  256,  276,  277,  278,  279,  280,  256,
  257,   59,  269,   44,  256,  273,  256,  257,  276,  277,
  278,  279,  280,  256,   41,  257,  273,   44,   59,  276,
  277,  278,  279,  280,  256,  257,  276,  277,  278,  279,
  280,  273,    0,  257,  276,  277,  278,  279,  280,  256,
  257,  273,  256,    0,  276,  277,  278,  279,  280,  273,
  256,  257,  276,  277,  278,  279,  280,  256,  257,  276,
  277,  278,  279,  280,  256,  123,  269,  125,  256,  256,
  276,  277,  278,  279,  280,  256,  257,  276,  277,  278,
  279,  280,   41,   42,   43,   16,   45,  269,   47,  259,
  260,   69,  256,  257,   45,  276,  174,  257,   -1,   64,
   59,   60,   33,   62,   41,   42,   43,   -1,   45,   -1,
   47,  123,  276,  277,  278,  279,  276,  277,  278,  279,
  278,  279,   59,   60,   -1,   62,   41,   42,   43,   -1,
   45,  123,   47,  125,  256,  257,   -1,   48,   49,  137,
  138,   72,  256,  257,   59,   60,   44,   62,   -1,   41,
   42,   43,   -1,   45,   -1,   47,  142,  143,  144,  145,
   -1,   59,  276,   -1,  123,   -1,  125,   59,   60,   -1,
   62,   41,   -1,   43,   -1,   45,   -1,  142,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   59,
   60,   -1,   62,   41,   -1,   43,   -1,   45,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,   -1,  123,   -1,
  125,   59,   60,  271,   62,  256,  257,   -1,  276,   -1,
   41,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
  257,  123,   -1,  125,   -1,  276,  263,  148,  149,   -1,
   -1,  206,  173,  270,  256,  257,  258,  274,  275,  261,
  262,   -1,   -1,  123,   43,  125,   45,   -1,  270,  271,
   -1,   -1,   -1,  275,  276,  257,  258,  259,  260,  261,
  262,   60,   59,   62,   -1,  123,   -1,  125,   -1,  271,
   -1,   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   59,  256,  257,  258,
  259,  260,  261,  262,  269,   -1,  265,  266,  267,  268,
  269,  276,  271,   -1,   -1,   -1,   -1,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,   -1,  123,   -1,  125,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,   -1,  256,  257,
  123,  276,  125,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,  276,  271,
   -1,   -1,  123,   -1,  276,   -1,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,  266,  267,  268,  269,
   -1,  271,   -1,   -1,   -1,  123,  276,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,   41,  271,   43,   -1,   45,   -1,  276,  125,
   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,  257,
   59,   60,  263,   62,   41,  263,   43,   36,   45,  270,
  125,  269,  270,  274,  275,   -1,  274,  275,   -1,   -1,
   -1,  125,   59,   60,   -1,   62,   -1,  256,   -1,  256,
  257,  258,  259,  260,  261,  262,  265,  266,  267,  268,
   -1,   -1,  125,   -1,  271,   -1,   75,   -1,   -1,  276,
   -1,    7,    8,    9,  257,  258,   -1,  260,  261,  262,
   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,  271,   -1,
   -1,  100,  101,  276,   -1,  104,  257,  258,   -1,   35,
  261,  262,   -1,   39,   -1,   -1,  123,   -1,  125,   45,
  271,  272,   -1,   -1,   -1,  276,   -1,   -1,   -1,  257,
  258,   57,   -1,  261,  262,  125,   -1,   63,   -1,   -1,
   -1,   67,   68,  271,   -1,   -1,  125,   -1,  276,   -1,
  256,  257,  258,   -1,   -1,  261,  262,  125,   -1,   -1,
   -1,   -1,   -1,   -1,  270,  271,   -1,   -1,  125,  275,
  276,  256,  257,  258,   -1,   -1,  261,  262,   -1,   -1,
   -1,   -1,  256,  257,  258,  270,  271,  261,  262,  125,
  275,  276,   -1,  192,   -1,   -1,  270,  271,   -1,   -1,
  125,  275,  276,  256,  257,  258,  259,  260,  261,  262,
   -1,  125,   -1,  139,   -1,   -1,   -1,  125,  271,   -1,
   -1,   -1,   -1,  276,   -1,   -1,   -1,  256,  257,  258,
  259,  260,  261,  262,  160,   -1,  265,  266,  267,  268,
  269,  125,  271,   -1,   -1,   -1,   -1,  276,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,   -1,   -1,  257,  258,  276,
   -1,  261,  262,   -1,   -1,   -1,   -1,   -1,  257,  258,
  270,  271,  261,  262,   -1,  275,  276,   -1,   -1,  257,
  258,  270,  271,  261,  262,   -1,  275,  276,   -1,   -1,
  257,  258,  270,  271,  261,  262,   -1,  275,  276,   -1,
   -1,   -1,   -1,  270,  271,   -1,   -1,   -1,  275,  276,
  256,  257,  258,   -1,  260,  261,  262,   -1,   -1,   -1,
   -1,  256,  257,  258,   -1,  271,  261,  262,   -1,   -1,
  276,   -1,  256,  257,  258,   -1,  271,  261,  262,  257,
  258,  276,   -1,  261,  262,   -1,   -1,  271,   -1,   -1,
   -1,   -1,  276,  271,  256,  257,  258,   -1,  276,  261,
  262,   -1,   -1,  257,  258,   -1,   -1,  261,  262,  271,
  256,  257,  258,   -1,  276,  261,  262,  271,   -1,  257,
  258,   -1,  276,  261,  262,  271,   -1,   -1,   -1,   -1,
  276,   -1,   -1,  271,   -1,   -1,   -1,   -1,  276,
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
"programa : ID '{' lista_sentencias '}'",
"programa : ID '{' error '}'",
"programa : ID '{' error",
"programa : ID '{' lista_sentencias '}' error",
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : ID '{' lista_sentencias",
"programa : ID lista_sentencias",
"programa : ID '{' '}'",
"programa : '{' '}'",
"programa : ID",
"programa : error",
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

//#line 471 "parser.y"
private AnalizadorLexico al;
private ErrorManager errManager;
private static final ParserVal dummyParserVal = new ParserVal();
private Generador generador;

public Parser(ErrorManager.Nivel nivel){
    this.al = AnalizadorLexico.getInstance();
    this.errManager = ErrorManager.getInstance();
    errManager.setNivel(nivel);
    this.generador = new Generador();
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
}
//#line 775 "Parser.java"
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
//#line 22 "parser.y"
{ errManager.debug("Declaracion de programa detectada", al.getLine()); }
break;
case 2:
//#line 24 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 3:
//#line 26 "parser.y"
{ errManager.error("Programa con error detectado", al.getLine()); }
break;
case 4:
//#line 28 "parser.y"
{
            errManager.debug("Declaracion de programa detectada", al.getLine());
            errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine());
        }
break;
case 5:
//#line 33 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 6:
//#line 35 "parser.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 7:
//#line 37 "parser.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 8:
//#line 39 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 9:
//#line 41 "parser.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 10:
//#line 43 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 11:
//#line 45 "parser.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 12:
//#line 47 "parser.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 16:
//#line 58 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 24:
//#line 70 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 25:
//#line 72 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 26:
//#line 74 "parser.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 27:
//#line 79 "parser.y"
{
	        errManager.debug("Declaracion de funcion detectada.", al.getLine());
	        generador.exitScope();
	    }
break;
case 28:
//#line 87 "parser.y"
{
            generador.enterScope(val_peek(1).sval);
        }
break;
case 29:
//#line 91 "parser.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 30:
//#line 93 "parser.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 31:
//#line 95 "parser.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 33:
//#line 100 "parser.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 35:
//#line 105 "parser.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 36:
//#line 106 "parser.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 39:
//#line 114 "parser.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 47:
//#line 132 "parser.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 48:
//#line 137 "parser.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 49:
//#line 139 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 50:
//#line 141 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 51:
//#line 143 "parser.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 52:
//#line 145 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 53:
//#line 147 "parser.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 54:
//#line 149 "parser.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 55:
//#line 151 "parser.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 56:
//#line 153 "parser.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 57:
//#line 158 "parser.y"
{
	        errManager.debug("Declaracion de variable detectada.",  al.getLine());
	        errManager.debug("Mangle Name: " + generador.mangleName(val_peek(0).sval), al.getLine());
	        int tipo = Integer.parseInt(val_peek(1).sval);
	        al.ts.insertar(generador.mangleName(val_peek(0).sval),new Atributo(tipo));


	    }
break;
case 58:
//#line 167 "parser.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 59:
//#line 169 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 60:
//#line 171 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 61:
//#line 173 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 62:
//#line 175 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 63:
//#line 177 "parser.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 64:
//#line 184 "parser.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 69:
//#line 200 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 70:
//#line 202 "parser.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 71:
//#line 204 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 72:
//#line 206 "parser.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 73:
//#line 208 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 74:
//#line 210 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 75:
//#line 212 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 76:
//#line 214 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 77:
//#line 216 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 78:
//#line 218 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 79:
//#line 220 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 80:
//#line 222 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 81:
//#line 224 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 82:
//#line 226 "parser.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 85:
//#line 233 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 86:
//#line 235 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 87:
//#line 237 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 88:
//#line 239 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 89:
//#line 241 "parser.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 90:
//#line 243 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 91:
//#line 245 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 92:
//#line 247 "parser.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 94:
//#line 253 "parser.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 95:
//#line 255 "parser.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 96:
//#line 257 "parser.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 98:
//#line 263 "parser.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 99:
//#line 265 "parser.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 100:
//#line 267 "parser.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 101:
//#line 269 "parser.y"
{ errManager.error("Contenido del bloque invalido", al.getLine()); }
break;
case 102:
//#line 275 "parser.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 103:
//#line 277 "parser.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 104:
//#line 282 "parser.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 105:
//#line 284 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 106:
//#line 286 "parser.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 107:
//#line 288 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 108:
//#line 290 "parser.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 115:
//#line 304 "parser.y"
{ yyval.sval = "0"; }
break;
case 116:
//#line 306 "parser.y"
{ yyval.sval = "1"; }
break;
case 117:
//#line 311 "parser.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 118:
//#line 313 "parser.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 119:
//#line 315 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 120:
//#line 317 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 122:
//#line 323 "parser.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 123:
//#line 325 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 125:
//#line 328 "parser.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 126:
//#line 330 "parser.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 128:
//#line 333 "parser.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 129:
//#line 335 "parser.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 131:
//#line 341 "parser.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 132:
//#line 343 "parser.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 133:
//#line 345 "parser.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 135:
//#line 348 "parser.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 136:
//#line 350 "parser.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 137:
//#line 352 "parser.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 143:
//#line 362 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 145:
//#line 365 "parser.y"
{ errManager.debug("Identificador con -", al.getLine()); }
break;
case 146:
//#line 367 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 147:
//#line 369 "parser.y"
{ errManager.error("Operador '-' no permitido en este contexto", al.getLine()); }
break;
case 148:
//#line 374 "parser.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 149:
//#line 376 "parser.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 150:
//#line 378 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 151:
//#line 380 "parser.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 152:
//#line 385 "parser.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 153:
//#line 389 "parser.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 154:
//#line 391 "parser.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 155:
//#line 393 "parser.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 156:
//#line 395 "parser.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 157:
//#line 400 "parser.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 159:
//#line 406 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 160:
//#line 408 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 161:
//#line 410 "parser.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 162:
//#line 412 "parser.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 163:
//#line 414 "parser.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 164:
//#line 419 "parser.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 165:
//#line 421 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 166:
//#line 423 "parser.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 167:
//#line 425 "parser.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 172:
//#line 440 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 173:
//#line 445 "parser.y"
{
            errManager.debug("CTEF detectada sin signo " + val_peek(0).sval ,al.getLine());
            yyval.sval = val_peek(0).sval;
        }
break;
case 174:
//#line 450 "parser.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = "-" + val_peek(0).sval;
	    }
break;
case 175:
//#line 455 "parser.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = "-" + val_peek(0).sval;
        }
break;
case 176:
//#line 460 "parser.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
case 177:
//#line 462 "parser.y"
{
            checkearRango(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 178:
//#line 467 "parser.y"
{ yyval.sval = val_peek(0).sval; }
break;
//#line 1462 "Parser.java"
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
