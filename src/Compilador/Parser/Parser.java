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
   32,   32,   32,   34,   34,   34,   34,   34,   10,   10,
   10,   10,   11,   24,   24,   24,   24,   12,   33,   33,
   33,   33,   33,   33,   13,   13,   13,   13,   35,   35,
   36,   36,    1,    1,    1,    1,    1,
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
    0,   35,    0,    0,    0,    0,    0,    0,  136,  163,
  164,  167,    0,  118,    0,    0,    0,  135,  137,    0,
    0,    0,    0,  133,    0,  154,    0,  143,  148,    0,
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
   48,   14,   15,   16,   17,   18,   19,   20,   21,   49,
   23,   24,   25,   26,   35,  107,   99,   64,  100,  101,
  102,  103,  104,  105,   27,   28,   51,   65,  135,   52,
  128,   53,   58,   54,   29,  164,
};
final static short yysindex[] = {                       -85,
  518,  643,    0,  -23,  -30,  120,   66,   66,    0,  562,
    0,  -15,  665,  692,    0,    0,    0,  -29,    0,    0,
    0,  -55,  -36,   45,    0,  -40,    0,    0,  -32,    0,
  712,    0,  138,   17,  -84,    2,   29,    9,    0,    0,
    0,    0,  138,    0, -140,   95,  192,    0,    0,  546,
  636,   12,   83,    0,  -34,    0,  114,    0,    0,   -3,
  120,  764,    0,  797,   77,  138,  103,    0,  714,    0,
    0,    0,    0,    0,    0, -224,    0,    0,    0,    0,
    0,    0,   27,   27,  297,  -84,   19, -184,   33,    0,
   32,   73,    2, -141,  280, -128,    0,  -80,  283,    0,
   41,    0, -137,   18,    0,  681,    0,  111,    0,    0,
  100,    0,    0,    0, -140,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -26,  153,  138,  614,   70,
  339,  -81,  -56,  -27,  107,    0,  219,  237,    0,    0,
  -21,    0,   59,    0,  741,    0,    0,  120,  110,   44,
  133,   53,  -74,    0,    0,  -84,  -84,    0,    0,   57,
    0,    0,    0,  -18,    0,    0,    0,    0,    0,    0,
  -70,  -65,  -68,    0,    0,    0,  299,    0,    0,  128,
    0,  -64,    0,  734,    0,    0,   83,    0,   83,   18,
    0,  758,  -63,    0,  -19,    6,    0,  140,    0,  143,
    0,   69,    0,    0,   72,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   85,    0,
    0,    0,    0,    0,    0,    0,    0,  -13,    0,    0,
   57,    0,    0,    0,  167,    0,    0,    0,    0,  152,
  159,  102,    0,  105,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  585,    0,    0,    0,    0,
    0,    0,  820,  766,  786,    0,    0,
};
final static short yyrindex[] = {                         0,
  221,    0,    0,    0,  146,    0,    0,    0,    0,    0,
    0,  -16,    0,  225,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,  378,    0,  400,    0,    0,
    0,    0,    0,    0,  422,    0,    0,    0,    0,    0,
    0,  569,  445,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  226,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  170,  443,    0,    1,    0,    0,    0,    0,
    0,    0,  534,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -50,    0,    0,    0,    0,    0,    0,
  592,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  808,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  265,    0,    0,    1,    1,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  467,    0,  489,  525,
    0,    0,    0,    0,  808,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   10,    0,    0,    0,    0,
    0,    0,    0,    0,   14,    0,    0,
};
final static short yygindex[] = {                         0,
  -45,   88,  355,    0,   -8,    0,    0,    0,    0,    4,
    0,    0,    0,  782,  126,   50,    0,  330,  -72,  203,
  -53,    0, 1043,    0,    0,    0,  -14,  -17,  231,  232,
    0,   89,   42,  166,    0,  118,
};
final static int YYTABLESIZE=1191;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   32,   63,  203,   78,   22,   22,  140,   46,   44,   34,
   45,   88,   47,   22,   76,   46,   22,   22,  115,  214,
   47,  126,   80,  127,   67,  231,  178,  159,   89,   75,
  231,  206,  154,  134,   22,   32,  108,    2,  106,  243,
  230,  108,  133,  163,  159,  254,  143,  181,   67,   59,
  149,  155,  136,   63,   22,  147,   96,   97,   46,   44,
  126,   45,  127,   47,  245,   22,   85,   22,   55,   56,
   46,   44,   22,   45,  126,   47,  127,  115,  110,  160,
   96,  179,   46,   44,  180,   45,  126,   47,  127,   31,
  166,  161,   96,  224,   46,   44,  180,   45,  181,   47,
   69,  115,  222,   82,  236,   55,   56,   46,   44,   22,
   45,  196,   47,  167,  163,  168,   43,  216,   46,   44,
  195,   45,   63,   47,  137,   32,  237,  249,  197,  138,
  251,  182,   22,  219,   22,  159,  147,  112,  113,  115,
  185,    9,   96,  253,   46,   44,   11,   45,   22,   47,
   96,   86,   46,   44,  142,   45,  126,   47,  127,   43,
  260,   46,   44,  262,   45,  208,   47,   96,  220,   46,
   44,    1,   45,  223,   47,  174,  175,  129,  200,   46,
   44,  225,   45,   63,   47,  255,  232,   22,  235,   56,
  233,  234,  238,  184,   46,   22,  241,  115,  246,   47,
   77,  247,  129,  202,   56,  226,  227,  256,  156,  157,
  257,  116,  118,   57,  187,  189,   83,  258,   61,   79,
    9,  139,   36,   87,    6,    5,   72,   73,   57,  186,
   36,  129,  205,   33,  213,   84,  115,  229,   37,  159,
  242,   38,   39,   40,   41,   42,   74,   63,   66,   38,
   39,   40,   41,   42,   63,  147,  147,   32,   32,   22,
   33,   32,   32,  115,    1,  244,   22,   22,   22,  152,
   32,   32,   92,   93,  111,   32,   32,  228,  147,   94,
    0,  115,  145,    0,  109,   36,    9,  165,  162,   37,
   95,   11,   38,   39,   40,   41,   42,   36,    0,  221,
   81,   37,  210,  212,   38,   39,   40,   41,   42,   36,
   40,   41,   42,   37,  215,    0,   38,   39,   40,   41,
   42,    0,   36,  176,  248,   37,  177,  250,   38,   39,
   40,   41,   42,   36,   40,   41,   42,   97,   37,    0,
  252,   38,   39,   40,   41,   42,    0,    0,  148,   37,
  114,   36,   38,   39,   40,   41,   42,  259,  151,   36,
  261,  199,  201,  204,  207,    0,   92,   36,   71,    0,
   38,   39,   40,   41,   42,   37,   36,    0,   38,   39,
   40,   41,   42,   37,   36,   71,   38,   39,   40,   41,
   42,  145,   37,    0,   36,   38,   39,   40,   41,   42,
   37,   56,   56,   38,   39,   40,   41,   42,  188,   36,
   37,    0,    0,   38,   39,   40,   41,   42,  138,  138,
  138,   56,  138,   71,  138,   57,   57,    0,   38,   39,
   40,   41,   42,    0,    0,    0,  138,  138,    0,  138,
  134,  134,  134,    0,  134,   57,  134,  117,   36,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  134,  134,
  145,  134,  121,  144,  121,    0,  121,   38,   39,   40,
   41,   42,    0,    0,  209,   36,    0,    0,    0,    0,
  121,  121,    0,  121,    0,  122,   53,  122,    0,  122,
    0,    0,  211,   36,   38,   39,   40,   41,   42,    0,
  138,   53,  138,  122,  122,    0,  122,  116,    0,  116,
    0,  116,   38,   39,   40,   41,   42,    0,    0,    0,
    0,  145,  134,    0,  134,  116,  116,    0,  116,  119,
    0,  119,    0,  119,    0,  169,  170,    0,   71,  158,
    0,    0,  171,    0,  121,   94,  121,  119,  119,    9,
  119,    0,    9,  158,   11,  158,   95,   11,    0,   94,
    0,   94,    0,    0,    0,   97,    9,  122,    9,  122,
   95,   11,   95,   11,   48,  138,  138,   48,  138,    0,
  138,    0,    0,   97,    0,  264,    0,    0,  126,  116,
  127,  116,  265,    0,    0,   60,    6,  129,  198,    7,
    8,    0,    0,    0,    0,  125,    0,  124,    0,   10,
    0,  119,    0,  119,   12,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   92,    0,    0,
    0,    0,    0,  138,  138,  138,  138,  138,  138,  138,
   13,    0,  138,  138,  138,  138,  138,   97,  138,   97,
   91,    0,    0,  138,    0,  134,  134,  134,  134,  134,
  134,  134,    0,    0,  134,  134,  134,  134,  134,    0,
  134,    0,  194,    0,    0,  134,    0,  121,  121,  121,
  121,  121,  121,  121,   62,    0,  121,  121,  121,  121,
  121,   92,  121,   92,    0,    0,    0,  121,   53,   53,
  122,  122,  122,  122,  122,  122,  122,  263,    0,  122,
  122,  122,  122,  122,   91,  122,   91,    0,   53,    0,
  122,    0,  116,  116,  116,  116,  116,  116,  116,    0,
    0,  116,  116,  116,  116,  116,  192,  116,  193,    0,
    0,    0,  116,    0,  119,  119,  119,  119,  119,  119,
  119,    0,    0,  119,  119,  119,  119,  119,  131,  119,
  132,    0,    0,    0,  119,    0,    0,   30,    0,    0,
    0,    0,    0,    4,    5,    6,    0,    0,    7,    8,
   97,   97,   97,   97,   97,   97,   97,    9,   10,   68,
   48,    0,   11,   12,    0,   97,   48,    0,    0,    0,
   97,  119,  138,   48,    0,  183,    0,   48,   48,    0,
  120,  121,  122,  123,    0,   98,   70,    0,   60,    6,
    0,    0,    7,    8,   92,   92,   92,   92,   92,   92,
   92,    0,   10,   61,    0,    0,   90,   12,  153,   92,
    0,   60,    6,    0,   92,    7,    8,   91,   91,   91,
   91,   91,   91,   91,    0,   10,    0,    0,  239,    0,
   12,    0,   91,    0,    0,  218,   98,   91,    0,    0,
   60,    6,    0,  191,    7,    8,  172,  173,    0,    0,
   98,    0,  144,    0,   10,    0,    0,    0,  144,   12,
  266,    0,   60,    6,  129,  130,    7,    8,    4,    5,
    6,    0,    0,    7,    8,    0,   10,    0,    0,    0,
  267,   12,    9,   10,    0,    0,    0,   11,   12,    0,
    4,    5,    6,    0,    0,    7,    8,    0,    0,    0,
    0,    0,    0,    0,    9,   10,    4,    5,    6,   11,
   12,    7,    8,    0,    0,    0,    0,    0,    5,    6,
    9,   10,    7,    8,    0,   11,   12,    0,   98,    0,
    0,    9,   10,    0,    0,    0,   11,   12,    5,    6,
    5,    6,    7,    8,    7,    8,    0,    0,    0,    0,
    0,    9,   10,    9,   10,    0,   11,   12,   11,   12,
    5,    6,    0,    0,    7,    8,  217,   60,    6,    0,
    0,    7,    8,    9,   10,    0,    0,    0,   11,   12,
    0,   10,    0,    0,   60,    6,   12,  240,    7,    8,
   60,    6,   60,    6,    7,    8,    7,    8,   10,    0,
    0,    0,    0,   12,   10,    0,   10,    0,    0,   12,
    0,   12,   60,    6,    0,    0,    7,    8,   50,   57,
   57,    0,  146,   60,    6,    0,   10,    7,    8,    0,
    0,   12,    0,   36,   36,   36,    0,   10,   36,   36,
    0,    0,   12,    0,    0,   91,   60,    6,   36,   57,
    7,    8,    0,   36,    0,   50,    0,    0,    0,    0,
   10,    0,    0,    0,    0,   12,    0,  141,    0,    0,
    0,    0,    0,   50,    0,    0,    0,   50,  150,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  190,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   50,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   10,   59,   59,    1,    2,   41,   42,   43,   40,
   45,   44,   47,   10,   44,   42,   13,   14,   45,   41,
   47,   43,   59,   45,   40,   44,   99,   44,   61,   59,
   44,   59,  257,   51,   31,   59,   40,  123,  123,   59,
   59,   40,   51,   89,   61,   59,   61,  101,   40,    8,
   65,  276,   41,   62,   51,   64,   40,   41,   42,   43,
   43,   45,   45,   47,   59,   62,   40,   64,   40,   41,
   42,   43,   69,   45,   43,   47,   45,   45,   37,   61,
   40,   41,   42,   43,   44,   45,   43,   47,   45,    2,
   59,  276,   40,   41,   42,   43,   44,   45,  152,   47,
   13,   45,   59,   59,  177,   40,   41,   42,   43,  106,
   45,  129,   47,   41,  160,  257,   40,   59,   42,   43,
  129,   45,  131,   47,   42,  125,  180,   59,   59,   47,
   59,  269,  129,  148,  131,   86,  145,  278,  279,   45,
   41,  270,   40,   59,   42,   43,  275,   45,  145,   47,
   40,   26,   42,   43,   41,   45,   43,   47,   45,   40,
   59,   42,   43,   59,   45,   59,   47,   40,   59,   42,
   43,  257,   45,   41,   47,  256,  257,  259,  260,   42,
   43,  256,   45,  192,   47,  231,  257,  184,  257,   44,
  256,  257,  257,  106,   42,  192,  260,   45,   59,   47,
  256,   59,  259,  260,   59,  156,  157,   41,   83,   84,
   59,   46,   47,   44,  126,  127,  257,   59,  269,  256,
    0,  256,  257,  256,    0,    0,  256,  257,   59,  256,
  257,  259,  260,  264,  256,  276,   45,  256,  273,  256,
  260,  276,  277,  278,  279,  280,  276,  256,  264,  276,
  277,  278,  279,  280,  263,  264,  265,  257,  258,  256,
  264,  261,  262,   45,    0,  260,  263,  264,  265,   67,
  270,  271,  256,  257,   43,  275,  276,  160,  269,  263,
   -1,   45,  269,   -1,  256,  257,  270,  256,  256,  273,
  274,  275,  276,  277,  278,  279,  280,  257,   -1,  256,
  256,  273,  137,  138,  276,  277,  278,  279,  280,  257,
  278,  279,  280,  273,  256,   -1,  276,  277,  278,  279,
  280,   -1,  257,   41,  256,  273,   44,  256,  276,  277,
  278,  279,  280,  257,  278,  279,  280,   41,  273,   -1,
  256,  276,  277,  278,  279,  280,   -1,   -1,  272,  273,
  256,  257,  276,  277,  278,  279,  280,  256,  256,  257,
  256,  131,  132,  133,  134,   -1,  256,  257,   14,   -1,
  276,  277,  278,  279,  280,  273,  257,   -1,  276,  277,
  278,  279,  280,  273,  257,   31,  276,  277,  278,  279,
  280,   62,  273,   -1,  257,  276,  277,  278,  279,  280,
  273,  256,  257,  276,  277,  278,  279,  280,  256,  257,
  273,   -1,   -1,  276,  277,  278,  279,  280,   41,   42,
   43,  276,   45,   69,   47,  256,  257,   -1,  276,  277,
  278,  279,  280,   -1,   -1,   -1,   59,   60,   -1,   62,
   41,   42,   43,   -1,   45,  276,   47,  256,  257,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
  131,   62,   41,  125,   43,   -1,   45,  276,  277,  278,
  279,  280,   -1,   -1,  256,  257,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   -1,   41,   44,   43,   -1,   45,
   -1,   -1,  256,  257,  276,  277,  278,  279,  280,   -1,
  123,   59,  125,   59,   60,   -1,   62,   41,   -1,   43,
   -1,   45,  276,  277,  278,  279,  280,   -1,   -1,   -1,
   -1,  192,  123,   -1,  125,   59,   60,   -1,   62,   41,
   -1,   43,   -1,   45,   -1,  256,  257,   -1,  184,  257,
   -1,   -1,  263,   -1,  123,  263,  125,   59,   60,  270,
   62,   -1,  270,  257,  275,  257,  274,  275,   -1,  263,
   -1,  263,   -1,   -1,   -1,   41,  270,  123,  270,  125,
  274,  275,  274,  275,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   59,   -1,  256,   -1,   -1,   43,  123,
   45,  125,  263,   -1,   -1,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,  271,
   -1,  123,   -1,  125,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,
   -1,   -1,   -1,  256,  257,  258,  259,  260,  261,  262,
  123,   -1,  265,  266,  267,  268,  269,  123,  271,  125,
   59,   -1,   -1,  276,   -1,  256,  257,  258,  259,  260,
  261,  262,   -1,   -1,  265,  266,  267,  268,  269,   -1,
  271,   -1,   59,   -1,   -1,  276,   -1,  256,  257,  258,
  259,  260,  261,  262,  123,   -1,  265,  266,  267,  268,
  269,  123,  271,  125,   -1,   -1,   -1,  276,  256,  257,
  256,  257,  258,  259,  260,  261,  262,  123,   -1,  265,
  266,  267,  268,  269,  123,  271,  125,   -1,  276,   -1,
  276,   -1,  256,  257,  258,  259,  260,  261,  262,   -1,
   -1,  265,  266,  267,  268,  269,  123,  271,  125,   -1,
   -1,   -1,  276,   -1,  256,  257,  258,  259,  260,  261,
  262,   -1,   -1,  265,  266,  267,  268,  269,  123,  271,
  125,   -1,   -1,   -1,  276,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   -1,  256,  257,  258,   -1,   -1,  261,  262,
  256,  257,  258,  259,  260,  261,  262,  270,  271,  125,
  257,   -1,  275,  276,   -1,  271,  263,   -1,   -1,   -1,
  276,  256,  269,  270,   -1,  125,   -1,  274,  275,   -1,
  265,  266,  267,  268,   -1,   34,  125,   -1,  257,  258,
   -1,   -1,  261,  262,  256,  257,  258,  259,  260,  261,
  262,   -1,  271,  272,   -1,   -1,  125,  276,  125,  271,
   -1,  257,  258,   -1,  276,  261,  262,  256,  257,  258,
  259,  260,  261,  262,   -1,  271,   -1,   -1,  125,   -1,
  276,   -1,  271,   -1,   -1,  125,   85,  276,   -1,   -1,
  257,  258,   -1,  260,  261,  262,   95,   96,   -1,   -1,
   99,   -1,  125,   -1,  271,   -1,   -1,   -1,  125,  276,
  125,   -1,  257,  258,  259,  260,  261,  262,  256,  257,
  258,   -1,   -1,  261,  262,   -1,  271,   -1,   -1,   -1,
  125,  276,  270,  271,   -1,   -1,   -1,  275,  276,   -1,
  256,  257,  258,   -1,   -1,  261,  262,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  270,  271,  256,  257,  258,  275,
  276,  261,  262,   -1,   -1,   -1,   -1,   -1,  257,  258,
  270,  271,  261,  262,   -1,  275,  276,   -1,  177,   -1,
   -1,  270,  271,   -1,   -1,   -1,  275,  276,  257,  258,
  257,  258,  261,  262,  261,  262,   -1,   -1,   -1,   -1,
   -1,  270,  271,  270,  271,   -1,  275,  276,  275,  276,
  257,  258,   -1,   -1,  261,  262,  256,  257,  258,   -1,
   -1,  261,  262,  270,  271,   -1,   -1,   -1,  275,  276,
   -1,  271,   -1,   -1,  257,  258,  276,  260,  261,  262,
  257,  258,  257,  258,  261,  262,  261,  262,  271,   -1,
   -1,   -1,   -1,  276,  271,   -1,  271,   -1,   -1,  276,
   -1,  276,  257,  258,   -1,   -1,  261,  262,    6,    7,
    8,   -1,  256,  257,  258,   -1,  271,  261,  262,   -1,
   -1,  276,   -1,  256,  257,  258,   -1,  271,  261,  262,
   -1,   -1,  276,   -1,   -1,   33,  257,  258,  271,   37,
  261,  262,   -1,  276,   -1,   43,   -1,   -1,   -1,   -1,
  271,   -1,   -1,   -1,   -1,  276,   -1,   55,   -1,   -1,
   -1,   -1,   -1,   61,   -1,   -1,   -1,   65,   66,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  128,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  148,
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

//#line 430 "parser_test.y"
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

    if (val != null)
        this.yylval = val;
    else
        this.yylval = null;

    return token;
}

public void tratarNegativos(String lexemaAnterior){
    errManager.debug("Tratando numero negativo " + lexemaAnterior,al.getLine());
    Atributo atributoAnterior = al.ts.obtener(lexemaAnterior);
    if(atributoAnterior.ref > 1){
        Atributo atributoNuevo = new Atributo(Atributo.longType,-1 * atributoAnterior.numValue);
        al.ts.insertar('-'+lexemaAnterior, atributoNuevo);
        atributoAnterior.ref -= 1;
    } else {
        atributoAnterior.numValue = -1 * atributoAnterior.numValue;
        al.ts.modificar(lexemaAnterior,'-'+lexemaAnterior, atributoAnterior);
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
//#line 748 "Parser.java"
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
{ errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine()); }
break;
case 3:
//#line 25 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 4:
//#line 27 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 5:
//#line 29 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 6:
//#line 31 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 7:
//#line 33 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 8:
//#line 35 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 9:
//#line 37 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 13:
//#line 48 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
//#line 60 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
//#line 62 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
//#line 64 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 24:
//#line 69 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 25:
//#line 71 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 26:
//#line 73 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 27:
//#line 75 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 29:
//#line 80 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 31:
//#line 85 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 32:
//#line 86 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 35:
//#line 94 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 43:
//#line 112 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 44:
//#line 117 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 45:
//#line 119 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 46:
//#line 121 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 47:
//#line 123 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 48:
//#line 125 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 49:
//#line 127 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
//#line 129 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 51:
//#line 131 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 52:
//#line 133 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 53:
//#line 138 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
//#line 140 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 55:
//#line 142 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 56:
//#line 144 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 57:
//#line 146 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 58:
//#line 148 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 59:
//#line 150 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 60:
//#line 157 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 65:
//#line 173 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
//#line 175 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 67:
//#line 177 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
//#line 179 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 69:
//#line 181 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
//#line 183 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 71:
//#line 185 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
//#line 187 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 73:
//#line 189 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
//#line 191 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 75:
//#line 193 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
//#line 195 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 77:
//#line 197 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 78:
//#line 199 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 81:
//#line 206 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
//#line 208 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 83:
//#line 210 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
//#line 212 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
//#line 214 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 86:
//#line 216 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 87:
//#line 218 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 88:
//#line 220 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 90:
//#line 226 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 91:
//#line 228 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 92:
//#line 230 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 94:
//#line 236 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 95:
//#line 238 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 96:
//#line 240 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 97:
//#line 246 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 98:
//#line 248 "parser_test.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 99:
//#line 253 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 100:
//#line 255 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 101:
//#line 257 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 102:
//#line 259 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 103:
//#line 261 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 112:
//#line 280 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 113:
//#line 282 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 114:
//#line 284 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 115:
//#line 286 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 117:
//#line 292 "parser_test.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 118:
//#line 294 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 120:
//#line 297 "parser_test.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 121:
//#line 299 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 123:
//#line 302 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 124:
//#line 304 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 126:
//#line 310 "parser_test.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 127:
//#line 312 "parser_test.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 128:
//#line 314 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 130:
//#line 317 "parser_test.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 131:
//#line 319 "parser_test.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 132:
//#line 321 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 138:
//#line 331 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 139:
//#line 336 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 140:
//#line 338 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 141:
//#line 340 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 142:
//#line 342 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 143:
//#line 347 "parser_test.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 144:
//#line 351 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 145:
//#line 353 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 146:
//#line 355 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 147:
//#line 357 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 148:
//#line 362 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 150:
//#line 368 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 151:
//#line 370 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 152:
//#line 372 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 153:
//#line 374 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 154:
//#line 376 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 155:
//#line 381 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 156:
//#line 383 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 157:
//#line 385 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 158:
//#line 387 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 163:
//#line 402 "parser_test.y"
{
            Atributo atributoActual = al.ts.obtener(val_peek(0).sval);
            if (atributoActual.ref == 1){
                if (atributoActual.numValue == (2147483648L)) {
                    errManager.warning("Constante long fuera de rango, truncando.");
                    atributoActual.numValue -= 1;
                }
            }

            yyval.sval = val_peek(0).sval;
        }
break;
case 165:
//#line 416 "parser_test.y"
{
	        tratarNegativos(val_peek(0).sval);
	        yyval.sval = '-' + val_peek(0).sval;
	    }
break;
case 166:
//#line 421 "parser_test.y"
{
           tratarNegativos(val_peek(0).sval);
           yyval.sval = '-' + val_peek(0).sval;
        }
break;
case 167:
//#line 426 "parser_test.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
//#line 1369 "Parser.java"
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
