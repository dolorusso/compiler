#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 6 "parser_test.y"
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
#line 9 "y.tab.c"
#define ID 257
#define CTEL 258
#define IF 259
#define ELSE 260
#define ENDIF 261
#define PRINT 262
#define RETURN 263
#define LAMBDA 264
#define ASIGNAR 265
#define MENORIGUAL 266
#define MAYORIGUAL 267
#define IGUALIGUAL 268
#define DISTINTO 269
#define FLECHA 270
#define LONG 271
#define DO 272
#define UNTIL 273
#define TRUNC 274
#define CR 275
#define STRING 276
#define CTEF 277
#define IDCOMP 278
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    0,    0,    0,    0,    2,    2,    2,    3,    3,
    3,    4,    4,    4,    4,    4,    4,    6,    6,    6,
    6,    6,    1,    1,   15,   15,   14,   14,   14,   17,
   17,   17,    5,    5,    5,   19,   19,   19,   19,   19,
   16,   16,   16,   16,   18,   20,   20,    8,    8,   23,
   23,   25,   24,   26,   26,   26,   26,   26,   26,   13,
   13,    7,    7,   21,   21,   21,   21,   27,   27,   27,
   28,   28,   28,   28,   28,    9,    9,    9,    9,   10,
   10,   22,   11,   12,   12,   12,   29,   29,   30,   30,
   31,   31,
};
short yylen[] = {                                         2,
    4,    4,    3,    3,    3,    1,    2,    1,    2,    1,
    1,    1,    1,    1,    1,    1,    1,    8,    7,    8,
    7,    6,    1,    2,    1,    2,    1,    3,    3,    1,
    3,    3,    1,    2,    2,    2,    3,    1,    2,    3,
    3,    2,    3,    4,    3,    1,    1,    1,    1,    8,
   12,    3,    8,    1,    1,    1,    1,    1,    1,    1,
    1,    3,    3,    3,    3,    1,    4,    3,    3,    1,
    1,    1,    1,    1,    1,    4,    4,    3,    3,    4,
    4,    5,    4,    3,    4,    3,    1,    3,    3,    1,
    1,    1,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,   61,    0,    0,    0,   23,    6,    0,    0,
   11,   12,   13,   14,   15,   16,   17,    0,    0,   48,
   49,    0,    0,    0,    0,    0,   35,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    4,   24,    7,
    9,    0,    0,   34,    0,    0,    0,    3,    2,    0,
    0,    0,   72,    0,   73,    0,   74,    0,    0,   70,
    0,   79,    0,   30,    0,    0,   47,    0,    0,    0,
    0,    0,    0,    0,   25,    0,    0,   78,    0,    1,
    0,   40,   37,   88,   86,   91,   92,    0,   90,    0,
    0,    0,    0,   27,    0,    0,    0,    0,    0,    0,
    0,    0,   77,    0,    0,   54,   55,   56,   57,   58,
   59,    0,    0,   80,   81,   83,   87,    0,   26,   76,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,   68,   69,    0,   32,   31,   45,    0,
    0,    0,    0,    0,   89,   43,    0,   41,   29,    0,
   28,   67,    0,    0,    0,   22,    0,    0,   44,    0,
    0,    0,    0,   19,   21,    0,   20,    0,   50,   53,
   18,    0,    0,    0,   51,
};
short yydgoto[] = {                                       4,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   28,  103,   86,  104,   73,   74,   29,   75,
   76,   77,   30,   31,   79,  122,   69,   70,   32,   98,
   99,
};
short yysindex[] = {                                   -100,
  -90,  -93,  518,    0,  518, -242,  -40,   16,   58,   64,
    0,  -36,    0,  -31,  518,  340,    0,    0,   49,   55,
    0,    0,    0,    0,    0,    0,    0, -220,   78,    0,
    0,  -16,  358,  369,  -30, -140,    0,   15, -134,  -34,
 -134, -157, -134, -160, -134,  -29,  392,    0,    0,    0,
    0,  -26, -140,    0, -206, -150, -204,    0,    0,  -87,
 -237,  111,    0,  114,    0,  121,    0,  -23,   38,    0,
 -166,    0,  -39,    0, -105,  -23,    0,  148,  135,  146,
   90,   96,  -88,  -40,    0,  -57,  -23,    0,  -10,    0,
  -38,    0,    0,    0,    0,    0,    0,  153,    0, -166,
 -182,  -65,   -3,    0,  153, -134,  119,  119,  119,  119,
  157,  -27,    0,  -27,  -87,    0,    0,    0,    0,    0,
    0, -134,   80,    0,    0,    0,    0,  -69,    0,    0,
   84,    9, -237,  -46, -166,  -44,    0,  -87,   97,  -87,
  171,   38,   38,    0,    0,   99,    0,    0,    0,  -23,
 -160,  187,  403,  113,    0,    0,   -1,    0,    0,  518,
    0,    0, -160,  484, -134,    0,  421,  432,    0,  455,
 -160, -115,  213,    0,    0,  466,    0,  136,    0,    0,
    0, -160,  498,    5,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,  116,    0,    0,    0,
    0,    0,    0,   30,    0,    0,    0,    0,  299,  322,
    0,    0,    0,    0,    0,    0,    0,    0,  276,    0,
    0,    0,    0,    0,    0,   30,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  261,    0,    0,    0,
    0,  127,  150,    0,    0,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    0,   24,    0,  173,   47,    0,
    0,    0,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  196,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  219,    0,    0,
    0,    0,    0,    0,  242,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,   93,    0,    0,    0,    0,    0,    0,  221,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
   14,   -8,    0,  360,    0,    0,   21,    0,  726,    0,
    0,    0,  425,  191, -147,  -83,  238,  -50,    0,    0,
   36,    0,    0,    0,  120,    0,   40,   71,   12,  228,
  164,
};
#define YYTABLESIZE 891
short yytable[] = {                                      40,
   75,  113,  131,  164,  114,   71,   72,   49,   46,   60,
   71,   88,   71,   91,   35,  171,   33,   38,   34,  107,
   96,  108,    3,   71,   49,   49,   37,   56,   47,   15,
  130,  149,    5,  114,  183,   36,   52,  139,   49,   97,
  140,   75,   75,   75,   57,   75,   66,   75,   54,  154,
   92,   95,  140,   96,  159,   41,  161,   53,   56,   75,
   75,  147,   75,  148,   71,   71,   71,  128,   71,   64,
   71,   93,   97,   87,   68,   61,   78,   81,   82,  109,
   87,  135,   71,   71,  110,   71,   44,   66,   11,   66,
   87,   66,   65,   13,   38,   83,   84,   42,    8,   62,
   63,    9,   10,   43,   11,   66,   66,   50,   66,   13,
   64,   12,   64,   51,   64,   38,   64,   14,   80,   65,
   66,   55,   62,   63,   45,   75,   39,   94,   64,   64,
  125,   64,  107,   65,  108,   65,  126,   65,  107,   64,
  108,  141,   65,   66,  178,  179,  142,  143,   71,   36,
   40,   65,   65,  106,   65,    1,    2,  150,   49,   38,
   46,   49,    6,    7,  115,    8,  167,   49,    9,   10,
   39,   66,   63,  170,   38,  123,  100,   11,   12,  144,
  145,  176,   13,   11,   14,   39,  124,  101,   13,  127,
  107,  137,  108,   36,   64,   62,  133,  146,   83,   84,
   78,    8,  151,  152,    9,   10,  153,  121,   36,  120,
  156,  162,  158,  107,   12,  108,  112,   65,   84,  160,
   14,  163,   62,   63,   39,  100,  165,   62,   63,   62,
   63,   63,   11,   45,   39,  168,  101,   13,   39,   64,
   38,   85,   65,   66,   64,  112,   64,   65,   66,   65,
   66,   39,  138,  180,   62,  169,   75,   75,  182,   75,
    5,   52,   75,   75,  138,  185,   75,   75,   75,   75,
   75,   75,   75,   46,   36,   33,   75,   84,   75,   71,
   71,  132,   71,   89,  173,   71,   71,   82,  105,   71,
   71,   71,   71,   71,   71,   71,  155,   63,    8,   71,
   85,   71,   66,   66,    0,   66,    0,    0,   66,   66,
    0,    0,   66,   66,   66,   66,   66,   66,   66,    0,
   62,   10,   66,    0,   66,   64,   64,    0,   64,    0,
    0,   64,   64,    0,   33,   64,   64,   64,   64,   64,
   64,   64,    0,   84,    0,   64,    0,   64,   65,   65,
    0,   65,    0,    0,   65,   65,    0,    0,   65,   65,
   65,   65,   65,   65,   65,    0,   85,    0,   65,    0,
   65,   38,   38,    0,   38,   62,   63,   38,   38,    0,
    0,    0,   39,   39,    0,   39,   38,   38,   39,   39,
    0,   38,    0,   38,    0,   65,   66,   39,   39,    0,
   33,    0,   39,   85,   39,   36,   36,    0,   36,    0,
    0,   36,   36,  116,  117,  118,  119,    0,    0,    0,
   36,   36,    0,    8,    0,   36,    0,   36,   63,   63,
    0,   63,    0,    0,   63,   63,    0,    0,    0,    0,
    0,    0,   63,   63,   63,  129,   10,    0,   63,    0,
   63,   62,   62,    0,   62,    0,    0,   62,   62,    0,
    0,    0,    0,    0,   48,   62,   62,   62,    0,    0,
    0,   62,    0,   62,   84,   84,    0,   84,    0,    0,
   84,   84,   58,    0,  102,    0,    0,    0,   84,   84,
   84,    0,    0,   59,   84,  111,   84,   85,   85,    0,
   85,    0,    0,   85,   85,    0,    0,    0,    0,    0,
   85,   85,   85,   85,    0,  102,   90,   85,    0,   85,
    0,    0,   85,  129,  134,  136,    0,  166,    0,    0,
  129,   33,   33,    0,   33,    0,    0,   33,   33,  102,
    0,   85,  129,    0,    0,  174,   33,   33,    0,    0,
    0,   33,    0,   33,    8,    8,  175,    8,    0,  157,
    8,    8,  102,    0,  102,    0,    0,    0,    0,    8,
    8,    0,    0,    0,    8,    0,    8,   10,   10,  177,
   10,    0,    0,   10,   10,    0,    0,    0,    0,    0,
  181,    0,   10,   10,    0,    6,    7,   10,    8,   10,
    0,    9,   10,    0,    0,    0,    0,    0,  172,    0,
   11,   12,    0,    6,    7,   13,    8,   14,    0,    9,
   10,    0,  184,    0,    6,    7,    0,    8,   11,   12,
    9,   10,    0,   13,    0,   14,    0,    0,    0,   11,
   12,    0,    0,    0,   13,    0,   14,    6,    7,    0,
    8,    0,    0,    9,   10,    0,    0,    0,    6,    7,
    0,    8,   11,   12,    9,   10,    0,   13,    0,   14,
    0,    0,    0,   11,   12,    0,    6,    7,   13,    8,
   14,    0,    9,   10,    0,    0,    0,    6,    7,    0,
    8,   11,   12,    9,   10,    0,   13,    0,   14,    0,
    0,    0,   11,   12,    0,    0,    0,   13,    0,   14,
    6,    7,    0,    8,    0,    0,    9,   10,    0,    0,
    0,    6,    7,    0,    8,   11,   12,    9,   10,    0,
   13,    0,   14,    0,    0,    0,   11,   12,    0,   83,
   84,   13,    8,   14,    0,    9,   10,    0,    0,    0,
    0,    0,    0,   83,   84,   12,    8,    0,    0,    9,
   10,   14,    0,    0,   67,   67,   67,   67,   67,   12,
   67,   67,    0,    6,    7,   14,    8,    0,    0,    9,
   10,    0,    0,    0,    0,    0,    0,    0,   11,   12,
    0,    0,    0,   13,    0,   14,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   67,   67,   67,   67,   67,    0,   67,    0,   67,
    0,    0,    0,    0,    0,    0,    0,   67,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   67,
};
short yycheck[] = {                                      40,
    0,   41,   41,  151,   44,   40,   41,   16,   40,   40,
   40,   41,   40,   40,  257,  163,    3,    6,    5,   43,
  258,   45,  123,    0,   33,   34,    6,   44,   15,  123,
   41,  115,  123,   44,  182,  278,  257,   41,   47,  277,
   44,   41,   42,   43,   61,   45,    0,   47,   28,   41,
  257,  256,   44,  258,  138,   40,  140,  278,   44,   59,
   60,  112,   62,  114,   41,   42,   43,  125,   45,    0,
   47,  278,  277,   44,   39,   61,   41,   42,   43,   42,
   45,  264,   59,   60,   47,   62,  123,   41,  271,   43,
   61,   45,    0,  276,   83,  256,  257,   40,  259,  257,
  258,  262,  263,   40,  271,   59,   60,   59,   62,  276,
   41,  272,   43,   59,   45,    0,  274,  278,  276,  277,
  278,   44,  257,  258,  265,  125,    0,  278,   59,   60,
   41,   62,   43,   41,   45,   43,   41,   45,   43,  274,
   45,  106,  277,  278,  260,  261,  107,  108,  125,    0,
   40,   59,   60,   40,   62,  256,  257,  122,  167,   44,
   40,  170,  256,  257,  270,  259,  153,  176,  262,  263,
   44,  125,    0,  160,   59,   41,  264,  271,  272,  109,
  110,  168,  276,  271,  278,   59,   41,  275,  276,  278,
   43,  257,   45,   44,  125,    0,   44,   41,  256,  257,
  165,  259,  123,  273,  262,  263,  123,   60,   59,   62,
  257,   41,  257,   43,  272,   45,  256,  125,    0,  123,
  278,  123,  257,  258,  265,  264,   40,  257,  258,  257,
  258,   59,  271,  265,  265,  123,  275,  276,  265,  274,
  125,    0,  277,  278,  274,  256,  274,  277,  278,  277,
  278,  125,  256,   41,   59,  257,  256,  257,  123,  259,
    0,   41,  262,  263,  256,  261,  266,  267,  268,  269,
  270,  271,  272,  270,  125,    0,  276,   59,  278,  256,
  257,   91,  259,   46,  165,  262,  263,  270,   61,  266,
  267,  268,  269,  270,  271,  272,  133,  125,    0,  276,
   59,  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,  271,  272,   -1,
  125,    0,  276,   -1,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   59,  266,  267,  268,  269,  270,
  271,  272,   -1,  125,   -1,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,   -1,   -1,  266,  267,
  268,  269,  270,  271,  272,   -1,  125,   -1,  276,   -1,
  278,  256,  257,   -1,  259,  257,  258,  262,  263,   -1,
   -1,   -1,  256,  257,   -1,  259,  271,  272,  262,  263,
   -1,  276,   -1,  278,   -1,  277,  278,  271,  272,   -1,
  125,   -1,  276,   44,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,  266,  267,  268,  269,   -1,   -1,   -1,
  271,  272,   -1,  125,   -1,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,  270,  271,  272,   86,  125,   -1,  276,   -1,
  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,
   -1,   -1,   -1,   -1,  125,  270,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  256,  257,   -1,  259,   -1,   -1,
  262,  263,  125,   -1,   60,   -1,   -1,   -1,  270,  271,
  272,   -1,   -1,  125,  276,   71,  278,  256,  257,   -1,
  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,
  151,  270,  271,  272,   -1,   91,  125,  276,   -1,  278,
   -1,   -1,  163,  164,  100,  101,   -1,  125,   -1,   -1,
  171,  256,  257,   -1,  259,   -1,   -1,  262,  263,  115,
   -1,  182,  183,   -1,   -1,  125,  271,  272,   -1,   -1,
   -1,  276,   -1,  278,  256,  257,  125,  259,   -1,  135,
  262,  263,  138,   -1,  140,   -1,   -1,   -1,   -1,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,  125,
  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,
  125,   -1,  271,  272,   -1,  256,  257,  276,  259,  278,
   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,  125,   -1,
  271,  272,   -1,  256,  257,  276,  259,  278,   -1,  262,
  263,   -1,  125,   -1,  256,  257,   -1,  259,  271,  272,
  262,  263,   -1,  276,   -1,  278,   -1,   -1,   -1,  271,
  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,   -1,
  259,   -1,   -1,  262,  263,   -1,   -1,   -1,  256,  257,
   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,  278,
   -1,   -1,   -1,  271,  272,   -1,  256,  257,  276,  259,
  278,   -1,  262,  263,   -1,   -1,   -1,  256,  257,   -1,
  259,  271,  272,  262,  263,   -1,  276,   -1,  278,   -1,
   -1,   -1,  271,  272,   -1,   -1,   -1,  276,   -1,  278,
  256,  257,   -1,  259,   -1,   -1,  262,  263,   -1,   -1,
   -1,  256,  257,   -1,  259,  271,  272,  262,  263,   -1,
  276,   -1,  278,   -1,   -1,   -1,  271,  272,   -1,  256,
  257,  276,  259,  278,   -1,  262,  263,   -1,   -1,   -1,
   -1,   -1,   -1,  256,  257,  272,  259,   -1,   -1,  262,
  263,  278,   -1,   -1,   39,   40,   41,   42,   43,  272,
   45,   46,   -1,  256,  257,  278,  259,   -1,   -1,  262,
  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,
   -1,   -1,   -1,  276,   -1,  278,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  106,  107,  108,  109,  110,   -1,  112,   -1,  114,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  122,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  165,
};
#define YYFINAL 4
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 278
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,"ID","CTEL","IF","ELSE","ENDIF","PRINT","RETURN","LAMBDA",
"ASIGNAR","MENORIGUAL","MAYORIGUAL","IGUALIGUAL","DISTINTO","FLECHA","LONG",
"DO","UNTIL","TRUNC","CR","STRING","CTEF","IDCOMP",
};
char *yyrule[] = {
"$accept : programa",
"programa : ID '{' lista_sentencias '}'",
"programa : error '{' lista_sentencias '}'",
"programa : '{' lista_sentencias '}'",
"programa : ID lista_sentencias '}'",
"programa : ID '{' lista_sentencias",
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
"declaracion_variable : lista_identificadores",
"declaracion_variable : tipo asignacion",
"declaracion_variable : error asignacion",
"lista_identificadores : tipo IDCOMP",
"lista_identificadores : lista_identificadores ',' IDCOMP",
"lista_identificadores : ID",
"lista_identificadores : tipo ID",
"lista_identificadores : lista_identificadores ',' ID",
"parametro_formal : CR tipo ID",
"parametro_formal : tipo ID",
"parametro_formal : LAMBDA tipo ID",
"parametro_formal : CR LAMBDA tipo ID",
"parametro_real_compuesto : parametro_real FLECHA parametro_formal",
"parametro_real : expresion",
"parametro_real : lambda",
"control : sentencia_IF",
"control : do_until",
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
"asignacion_multiple : ids '=' lista_constantes",
"asignacion_multiple : error ids '=' lista_constantes",
"asignacion_multiple : ids '=' error",
"ids : IDCOMP",
"ids : ids ',' IDCOMP",
"lista_constantes : lista_constantes ',' constante",
"lista_constantes : constante",
"constante : CTEL",
"constante : CTEF",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 232 "parser_test.y"
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
#line 478 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 1:
#line 18 "parser_test.y"
{ System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); }
break;
case 2:
#line 21 "parser_test.y"
{ System.out.println("Declaracion de programa invalida, identificador invalido. Linea: " + al.getLine()); }
break;
case 3:
#line 24 "parser_test.y"
{ System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
break;
case 4:
#line 27 "parser_test.y"
{ System.out.println("Falta apertura del programa. Linea: " + al.getLine()); }
break;
case 5:
#line 31 "parser_test.y"
{ System.out.println("Falta apertura del programa. Linea: " + al.getLine()); }
break;
case 8:
#line 37 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 10:
#line 42 "parser_test.y"
{ System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
break;
case 16:
#line 51 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 18:
#line 58 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 19:
#line 61 "parser_test.y"
{ System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 20:
#line 64 "parser_test.y"
{ System.out.println("Tipo de funcion invalido/ausente. Linea: " + al.getLine()); }
break;
case 21:
#line 67 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine()); }
break;
case 22:
#line 70 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine());
         System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 29:
#line 89 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 32:
#line 95 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 34:
#line 100 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 35:
#line 101 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 36:
#line 105 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 37:
#line 106 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 38:
#line 107 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 39:
#line 108 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 40:
#line 109 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 41:
#line 113 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 42:
#line 114 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 43:
#line 115 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 44:
#line 116 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 45:
#line 120 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 50:
#line 135 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 51:
#line 137 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 52:
#line 141 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 53:
#line 146 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 62:
#line 164 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 63:
#line 165 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 67:
#line 172 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 75:
#line 186 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 76:
#line 190 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 77:
#line 191 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 78:
#line 192 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 79:
#line 193 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 80:
#line 197 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 81:
#line 198 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 82:
#line 202 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 83:
#line 206 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
case 84:
#line 210 "parser_test.y"
{ System.out.println("Asignacion multiple detectada. Linea: " + al.getLine());}
break;
case 85:
#line 211 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
case 86:
#line 212 "parser_test.y"
{ System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
break;
#line 803 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
