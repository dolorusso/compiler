#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 6 "parser_test.y"
    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;
    import Compilador.ErrorHandler.ErrorManager;
    import Compilador.Lexer.Atributo;
#line 11 "y.tab.c"
#define ID 257
#define IF 258
#define ELSE 259
#define ENDIF 260
#define PRINT 261
#define RETURN 262
#define LAMBDA 263
#define ASIGNAR 264
#define MENORIGUAL 265
#define MAYORIGUAL 266
#define IGUALIGUAL 267
#define DISTINTO 268
#define FLECHA 269
#define LONG 270
#define DO 271
#define UNTIL 272
#define TRUNC 273
#define CR 274
#define STRING 275
#define IDCOMP 276
#define CADENASTR 277
#define CTEL 278
#define CTEF 279
#define INVALID 280
#define YYERRCODE 256
short yylhs[] = {                                        -1,
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
   36,   36,    1,    1,    1,    1,    1,    1,    1,
};
short yylen[] = {                                         2,
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
    3,    1,    1,    1,    2,    2,    1,    2,    2,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,  110,    0,
  111,    0,    0,    0,   33,   10,   11,    0,   14,   15,
   16,    0,    0,    0,   20,    0,   63,   64,    0,    8,
    0,   35,    0,    0,    0,    0,    0,    0,  136,  163,
  164,  167,    0,    0,    0,    0,    0,  135,  137,    0,
    0,    0,    0,  133,    0,  154,    0,  143,  148,    0,
    0,    0,   36,    0,    0,    0,    0,    7,    0,    4,
   34,   13,   59,   55,   12,    0,   21,   17,   22,   18,
   23,   19,    0,    0,    0,    0,    0,    0,    0,    3,
    0,    0,    0,    0,    0,    0,   29,    0,    0,   38,
    0,   41,    0,    0,   62,    0,   25,    0,  124,  123,
    0,  168,  169,  165,  166,  128,    0,    0,  126,  132,
  130,   98,  104,  105,  106,  107,  108,  109,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   90,    0,
    0,  153,  152,    0,  150,    0,   96,    0,   95,   37,
    0,    0,    0,    0,    0,    0,   58,   54,    0,    0,
   48,   26,    0,  160,  157,  162,    0,  115,  114,  142,
   49,   51,   45,    0,    0,    0,   52,   47,   28,    0,
   40,  140,    0,   43,    0,   31,    0,   89,  117,    0,
  120,    0,    0,   88,    0,    0,   85,    0,    0,   77,
    0,   74,    0,   76,    0,   72,   68,    0,   71,   67,
   78,  127,  125,  131,  129,  151,  149,  102,  101,   94,
   93,    0,  103,  113,  112,  141,  139,    2,   24,   27,
    0,  158,  155,    0,   50,   46,   44,    0,   39,   42,
   60,   30,    0,    0,    0,   84,    0,   83,   73,   75,
   70,   66,   69,   65,  100,   99,  156,  161,    0,   86,
   87,   82,   80,   81,   79,    0,    0,    0,  146,  144,
};
short yydgoto[] = {                                       3,
   48,   14,   15,   16,   17,   18,   19,   20,   21,   49,
   23,   24,   25,   26,   35,  107,   99,   64,  100,  101,
  102,  103,  104,  105,   27,   28,   51,   65,  138,   52,
  131,   53,   58,   54,   29,  167,
};
short yysindex[] = {                                   -104,
  608,  692,    0,  -12,  -38,  126,   69,   69,    0,  371,
    0,  -26,  713,  736,    0,    0,    0,  104,    0,    0,
    0,   24,   31,   54,    0,  -30,    0,    0,  -31,    0,
  743,    0,  -25,   27, -100,  -11,   35,   11,    0,    0,
    0,    0,  -25, -234,  -84,  164,  169,    0,    0,  592,
  685,   16,   -1,    0,  -36,    0,  419,    0,    0,  -15,
  126,  832,    0,  281,   78,  -25,  109,    0,  780,    0,
    0,    0,    0,    0,    0, -218,    0,    0,    0,    0,
    0,    0,   21,   21,  -41, -100,   12, -197,   97,    0,
   88,   65,  -11, -133,  240, -216,    0,  -33,  244,    0,
   52,    0, -140,   43,    0,  720,    0,  117,    0,    0,
  114,    0,    0,    0,    0,    0, -234,  -84,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   42,  159,
  -25,  660,   86,  786,  173,  -24,  111,   99,    0,  174,
  202,    0,    0,  -17,    0,   68,    0,  809,    0,    0,
  126,  102,  236,  124,   60,  -78,    0,    0, -100, -100,
    0,    0,   -5,    0,    0,    0,    4,    0,    0,    0,
    0,    0,    0,  -85,  216,  -77,    0,    0,    0,  200,
    0,    0,  134,    0,  -74,    0,  802,    0,    0,   -1,
    0,   -1,   43,    0,  826,  -75,    0,  -32,   -4,    0,
  133,    0,  144,    0,   85,    0,    0,  108,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  116,    0,    0,    0,    0,    0,    0,    0,    0,
   84,    0,    0,   -5,    0,    0,    0,  167,    0,    0,
    0,    0,  151,  154,  123,    0,  125,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -71,    0,
    0,    0,    0,    0,    0,  388,  834,  854,    0,    0,
};
short yyrindex[] = {                                      0,
  215,    0,    0,    0,  152,    0,    0,    0,    0,    0,
    0,   37,    0,  218,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,  424,    0,  446,    0,    0,
    0,    0,    0,  468,  491,    0,    0,    0,    0,    0,
    0,  615,  513,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  250,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  308,  325,    0,    1,    0,    0,    0,    0,
    0,    0,  580,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,    0,    0,    0,    0,    0,    0,
  638,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  342,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  267,    0,    0,    1,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  535,
    0,  764,  571,    0,    0,    0,    0,  342,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   17,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,    0,
};
short yygindex[] = {                                      0,
  -46,   29,  387,    0,   -2,    0,    0,    0,    0,    2,
    0,    0,    0,  427,  115,  -21,    0,  300,  -65,  227,
  -64,    0, 1111,    0,    0,    0,  -29,    5,  426,  239,
    0,  101,   13,  376,    0,  153,
};
#define YYTABLESIZE 1262
short yytable[] = {                                      97,
   32,   34,   22,   22,  143,   46,   44,   63,   45,   85,
   47,   22,   88,   67,   22,   22,   46,   44,    2,   45,
   59,   47,  106,  217,  108,  129,  246,  130,  108,   89,
   31,  146,   22,  181,  206,  152,  184,  117,  157,  118,
  140,   69,  166,  112,  113,  141,   32,  234,  136,  110,
   67,  266,   22,    9,  248,  137,  139,  158,   11,   63,
   85,  150,  233,   22,  162,   22,   96,   97,   46,   44,
   22,   45,  163,   47,   55,   56,   46,   44,  164,   45,
  159,   47,   78,   46,  117,  129,  118,  130,   47,   80,
  184,   96,  182,   46,   44,  183,   45,  159,   47,   96,
  227,   46,   44,  183,   45,  170,   47,   22,   55,   56,
   46,   44,   82,   45,  239,   47,  166,   43,  240,   46,
   44,  222,   45,  171,   47,   32,  219,  234,  185,  198,
  129,   63,  130,   22,  187,   22,  199,  229,  230,  117,
   86,  118,  257,  252,  200,  150,  169,   76,   96,   22,
   46,   44,    1,   45,  188,   47,   96,  211,   46,   44,
  223,   45,   75,   47,  226,   43,  254,   46,   44,  209,
   45,  235,   47,   96,  256,   46,   44,  228,   45,  238,
   47,  263,  241,  265,  244,   60,    6,  258,   22,    7,
    8,  249,   63,  114,  115,   56,   22,  159,  160,   10,
   46,  117,  250,  118,   12,   47,  117,  259,  118,  260,
   56,  117,  261,  118,    9,  161,  117,    6,  118,  142,
   36,   94,  177,  178,   87,   33,   83,  245,    9,  190,
  192,   36,   95,   11,  132,  205,   37,   66,  216,   38,
   39,   40,   41,   42,  117,   84,  118,   37,   33,    5,
   38,   39,   40,   41,   42,  247,   63,   32,   32,  232,
   22,   32,   32,   63,  150,  150,    1,   22,   22,   22,
   32,   32,   40,   41,   42,   32,   32,   61,  129,   77,
  130,  111,   92,   93,  179,  147,   79,  180,  145,   94,
  109,   36,  159,  155,  225,    0,    9,  189,   36,   37,
   95,   11,   38,   39,   40,   41,   42,   37,   36,   81,
   38,   39,   40,   41,   42,  231,   36,   38,   39,   40,
   41,   42,    0,  218,   37,   36,    0,   38,   39,   40,
   41,   42,   37,    0,   36,   38,   39,   40,   41,   42,
  251,   37,    0,  168,   38,   39,   40,   41,   42,  151,
   37,   57,  165,   38,   39,   40,   41,   42,    0,   72,
   73,  148,    0,  253,  154,   36,   57,    0,   53,  132,
  208,  255,   92,   36,   40,   41,   42,    0,  262,   74,
  264,   37,   36,   53,   38,   39,   40,   41,   42,   37,
   36,    0,   38,   39,   40,   41,   42,    0,   37,    0,
   71,   38,   39,   40,   41,   42,   37,   56,   56,   38,
   39,   40,   41,   42,  191,   36,    0,   71,    0,  116,
   36,  119,  121,    0,  120,   36,    0,   56,    0,  212,
   36,  132,  203,  148,   38,   39,   40,   41,   42,   38,
   39,   40,   41,   42,   38,   39,   40,   41,   42,   38,
   39,   40,   41,   42,    0,   71,  161,  214,   36,  145,
   98,  129,   94,  130,  138,  138,  138,    0,  138,    9,
  138,  236,  237,   95,   11,    0,    0,   38,   39,   40,
   41,   42,  138,  138,    0,  138,  134,  134,  134,    0,
  134,  224,  134,   62,  148,  172,  173,    0,    0,    0,
  161,    0,  174,    0,  134,  134,   94,  134,  118,    9,
  118,   98,  118,    9,   11,  213,  215,   95,   11,    0,
    0,  175,  176,    0,    0,   98,  118,  118,    0,  118,
    0,  121,    0,  121,    0,  121,  149,   60,    6,    0,
    0,    7,    8,    0,    0,    0,  138,    0,  138,  121,
  121,   10,  121,  122,    0,  122,   12,  122,  267,  202,
  204,  207,  210,   57,   57,  268,    0,    0,  134,    0,
  134,  122,  122,   71,  122,  116,    0,  116,    0,  116,
   53,   53,    0,   57,    0,    0,    0,    0,    0,    0,
  118,    0,  118,  116,  116,    0,  116,   36,   36,   36,
   53,    0,   36,   36,    0,    0,   98,    0,    0,    0,
    0,   97,   36,  121,    0,  121,    0,   36,    0,    0,
   48,  138,  138,   48,  138,    0,  138,   60,    6,   97,
    0,    7,    8,    0,  129,  122,  130,  122,    0,    0,
    0,   10,   61,    0,   60,    6,   12,    0,    7,    8,
    0,  128,    0,  127,    0,    0,    0,  116,   10,  116,
    0,    0,    0,   12,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,    0,    0,    0,    0,    0,  138,
  138,  138,  138,  138,  138,  138,    0,    0,  138,  138,
  138,  138,  138,   97,  138,   97,   91,    0,    0,  138,
    0,  134,  134,  134,  134,  134,  134,  134,    0,    0,
  134,  134,  134,  134,  134,    0,  134,    0,  197,    0,
    0,  134,    0,  118,  118,  118,  118,  118,  118,  118,
   13,    0,  118,  118,  118,  118,  118,   92,  118,   92,
    0,    0,    0,  118,    0,    0,  121,  121,  121,  121,
  121,  121,  121,    0,    0,  121,  121,  121,  121,  121,
   91,  121,   91,    0,    0,    0,  121,    0,  122,  122,
  122,  122,  122,  122,  122,    0,    0,  122,  122,  122,
  122,  122,  195,  122,  196,    0,    0,    0,  122,    0,
  116,  116,  116,  116,  116,  116,  116,    0,    0,  116,
  116,  116,  116,  116,  119,  116,  119,  134,  119,  135,
  116,    0,    0,    0,    0,    0,   30,    0,    0,    0,
    0,    0,  119,  119,    0,  119,   97,   97,   97,   97,
   97,   97,   97,    0,    0,    0,   48,   68,    0,    0,
    0,   97,   48,    0,  186,    0,   97,  122,  138,   48,
    0,    0,    0,   48,   48,    0,  123,  124,  125,  126,
   70,    0,    0,    4,    5,    6,    0,   90,    7,    8,
   92,   92,   92,   92,   92,   92,   92,    9,   10,    0,
    0,    0,   11,   12,    0,   92,  119,    0,  119,    0,
   92,    0,    0,   91,   91,   91,   91,   91,   91,   91,
    0,    0,    0,    0,  156,    0,    0,    0,   91,    0,
  147,    0,    0,   91,    0,    0,   60,    6,    0,  194,
    7,    8,    0,    0,    0,    0,  242,    0,    0,    0,
   10,    0,    0,  221,    0,   12,    0,    0,    0,    0,
    0,   60,    6,  132,  133,    7,    8,    4,    5,    6,
  147,    0,    7,    8,    0,   10,  147,    0,  269,    0,
   12,    9,   10,    0,    0,    0,   11,   12,    4,    5,
    6,    0,    0,    7,    8,    4,    5,    6,  270,    0,
    7,    8,    9,   10,    0,    0,    0,   11,   12,    9,
   10,    0,    5,    6,   11,   12,    7,    8,    0,    5,
    6,    0,    0,    7,    8,    9,   10,    0,    0,    0,
   11,   12,    9,   10,    0,    0,    0,   11,   12,  119,
  119,  119,  119,  119,  119,  119,    0,    0,  119,  119,
  119,  119,  119,    0,  119,    0,    5,    6,    0,  119,
    7,    8,   60,    6,  132,  201,    7,    8,    0,    9,
   10,    0,    0,    0,   11,   12,   10,    0,    5,    6,
    0,   12,    7,    8,  220,   60,    6,    0,    0,    7,
    8,    9,   10,    0,    0,    0,   11,   12,    0,   10,
    0,    0,   60,    6,   12,  243,    7,    8,   60,    6,
   60,    6,    7,    8,    7,    8,   10,    0,    0,    0,
    0,   12,   10,    0,   10,    0,    0,   12,    0,   12,
   60,    6,    0,    0,    7,    8,   50,   57,   57,    0,
    0,    0,    0,    0,   10,    0,    0,    0,    0,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   91,    0,    0,    0,   57,    0,    0,
    0,    0,    0,   50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  144,    0,    0,    0,    0,
    0,   50,    0,    0,    0,   50,  153,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  193,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,
};
short yycheck[] = {                                      41,
    0,   40,    1,    2,   41,   42,   43,   10,   45,   40,
   47,   10,   44,   40,   13,   14,   42,   43,  123,   45,
    8,   47,  123,   41,   40,   43,   59,   45,   40,   61,
    2,   61,   31,   99,   59,   65,  101,   43,  257,   45,
   42,   13,   89,  278,  279,   47,   59,   44,   51,   37,
   40,  123,   51,  270,   59,   51,   41,  276,  275,   62,
   40,   64,   59,   62,   86,   64,   40,   41,   42,   43,
   69,   45,   61,   47,   40,   41,   42,   43,  276,   45,
   44,   47,   59,   42,   43,   43,   45,   45,   47,   59,
  155,   40,   41,   42,   43,   44,   45,   61,   47,   40,
   41,   42,   43,   44,   45,   41,   47,  106,   40,   41,
   42,   43,   59,   45,  180,   47,  163,   40,  183,   42,
   43,  151,   45,  257,   47,  125,   59,   44,  269,  132,
   43,  134,   45,  132,  106,  134,  132,  159,  160,   43,
   26,   45,   59,   59,   59,  148,   59,   44,   40,  148,
   42,   43,  257,   45,   41,   47,   40,   59,   42,   43,
   59,   45,   59,   47,   41,   40,   59,   42,   43,   59,
   45,  257,   47,   40,   59,   42,   43,  256,   45,  257,
   47,   59,  257,   59,  260,  257,  258,  234,  187,  261,
  262,   59,  195,  278,  279,   44,  195,   83,   84,  271,
   42,   43,   59,   45,  276,   47,   43,   41,   45,   59,
   59,   43,   59,   45,    0,  257,   43,    0,   45,  256,
  257,  263,  256,  257,  256,  264,  257,  260,  270,  129,
  130,  257,  274,  275,  259,  260,  273,  264,  256,  276,
  277,  278,  279,  280,   43,  276,   45,  273,  264,    0,
  276,  277,  278,  279,  280,  260,  259,  257,  258,  256,
  259,  261,  262,  266,  267,  268,    0,  266,  267,  268,
  270,  271,  278,  279,  280,  275,  276,  269,   43,  256,
   45,   43,  256,  257,   41,  269,  256,   44,  269,  263,
  256,  257,  256,   67,   59,   -1,  270,  256,  257,  273,
  274,  275,  276,  277,  278,  279,  280,  273,  257,  256,
  276,  277,  278,  279,  280,  163,  257,  276,  277,  278,
  279,  280,   -1,  256,  273,  257,   -1,  276,  277,  278,
  279,  280,  273,   -1,  257,  276,  277,  278,  279,  280,
  256,  273,   -1,  256,  276,  277,  278,  279,  280,  272,
  273,   44,  256,  276,  277,  278,  279,  280,   -1,  256,
  257,   62,   -1,  256,  256,  257,   59,   -1,   44,  259,
  260,  256,  256,  257,  278,  279,  280,   -1,  256,  276,
  256,  273,  257,   59,  276,  277,  278,  279,  280,  273,
  257,   -1,  276,  277,  278,  279,  280,   -1,  273,   -1,
   14,  276,  277,  278,  279,  280,  273,  256,  257,  276,
  277,  278,  279,  280,  256,  257,   -1,   31,   -1,  256,
  257,   46,   47,   -1,  256,  257,   -1,  276,   -1,  256,
  257,  259,  260,  134,  276,  277,  278,  279,  280,  276,
  277,  278,  279,  280,  276,  277,  278,  279,  280,  276,
  277,  278,  279,  280,   -1,   69,  257,  256,  257,   41,
   34,   43,  263,   45,   41,   42,   43,   -1,   45,  270,
   47,  256,  257,  274,  275,   -1,   -1,  276,  277,  278,
  279,  280,   59,   60,   -1,   62,   41,   42,   43,   -1,
   45,  256,   47,  123,  195,  256,  257,   -1,   -1,   -1,
  257,   -1,  263,   -1,   59,   60,  263,   62,   41,  270,
   43,   85,   45,  270,  275,  140,  141,  274,  275,   -1,
   -1,   95,   96,   -1,   -1,   99,   59,   60,   -1,   62,
   -1,   41,   -1,   43,   -1,   45,  256,  257,  258,   -1,
   -1,  261,  262,   -1,   -1,   -1,  123,   -1,  125,   59,
   60,  271,   62,   41,   -1,   43,  276,   45,  259,  134,
  135,  136,  137,  256,  257,  266,   -1,   -1,  123,   -1,
  125,   59,   60,  187,   62,   41,   -1,   43,   -1,   45,
  256,  257,   -1,  276,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   59,   60,   -1,   62,  256,  257,  258,
  276,   -1,  261,  262,   -1,   -1,  180,   -1,   -1,   -1,
   -1,   41,  271,  123,   -1,  125,   -1,  276,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,  257,  258,   59,
   -1,  261,  262,   -1,   43,  123,   45,  125,   -1,   -1,
   -1,  271,  272,   -1,  257,  258,  276,   -1,  261,  262,
   -1,   60,   -1,   62,   -1,   -1,   -1,  123,  271,  125,
   -1,   -1,   -1,  276,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,  123,  271,  125,   59,   -1,   -1,  276,
   -1,  256,  257,  258,  259,  260,  261,  262,   -1,   -1,
  265,  266,  267,  268,  269,   -1,  271,   -1,   59,   -1,
   -1,  276,   -1,  256,  257,  258,  259,  260,  261,  262,
  123,   -1,  265,  266,  267,  268,  269,  123,  271,  125,
   -1,   -1,   -1,  276,   -1,   -1,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,  265,  266,  267,  268,  269,
  123,  271,  125,   -1,   -1,   -1,  276,   -1,  256,  257,
  258,  259,  260,  261,  262,   -1,   -1,  265,  266,  267,
  268,  269,  123,  271,  125,   -1,   -1,   -1,  276,   -1,
  256,  257,  258,  259,  260,  261,  262,   -1,   -1,  265,
  266,  267,  268,  269,   41,  271,   43,  123,   45,  125,
  276,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,  256,  257,  258,  259,
  260,  261,  262,   -1,   -1,   -1,  257,  125,   -1,   -1,
   -1,  271,  263,   -1,  125,   -1,  276,  256,  269,  270,
   -1,   -1,   -1,  274,  275,   -1,  265,  266,  267,  268,
  125,   -1,   -1,  256,  257,  258,   -1,  125,  261,  262,
  256,  257,  258,  259,  260,  261,  262,  270,  271,   -1,
   -1,   -1,  275,  276,   -1,  271,  123,   -1,  125,   -1,
  276,   -1,   -1,  256,  257,  258,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,  271,   -1,
  125,   -1,   -1,  276,   -1,   -1,  257,  258,   -1,  260,
  261,  262,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,
  271,   -1,   -1,  125,   -1,  276,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  256,  257,  258,
  125,   -1,  261,  262,   -1,  271,  125,   -1,  125,   -1,
  276,  270,  271,   -1,   -1,   -1,  275,  276,  256,  257,
  258,   -1,   -1,  261,  262,  256,  257,  258,  125,   -1,
  261,  262,  270,  271,   -1,   -1,   -1,  275,  276,  270,
  271,   -1,  257,  258,  275,  276,  261,  262,   -1,  257,
  258,   -1,   -1,  261,  262,  270,  271,   -1,   -1,   -1,
  275,  276,  270,  271,   -1,   -1,   -1,  275,  276,  256,
  257,  258,  259,  260,  261,  262,   -1,   -1,  265,  266,
  267,  268,  269,   -1,  271,   -1,  257,  258,   -1,  276,
  261,  262,  257,  258,  259,  260,  261,  262,   -1,  270,
  271,   -1,   -1,   -1,  275,  276,  271,   -1,  257,  258,
   -1,  276,  261,  262,  256,  257,  258,   -1,   -1,  261,
  262,  270,  271,   -1,   -1,   -1,  275,  276,   -1,  271,
   -1,   -1,  257,  258,  276,  260,  261,  262,  257,  258,
  257,  258,  261,  262,  261,  262,  271,   -1,   -1,   -1,
   -1,  276,  271,   -1,  271,   -1,   -1,  276,   -1,  276,
  257,  258,   -1,   -1,  261,  262,    6,    7,    8,   -1,
   -1,   -1,   -1,   -1,  271,   -1,   -1,   -1,   -1,  276,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   33,   -1,   -1,   -1,   37,   -1,   -1,
   -1,   -1,   -1,   43,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   55,   -1,   -1,   -1,   -1,
   -1,   61,   -1,   -1,   -1,   65,   66,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  131,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  151,
};
#define YYFINAL 3
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 280
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,"ID","IF","ELSE","ENDIF","PRINT","RETURN","LAMBDA",
"ASIGNAR","MENORIGUAL","MAYORIGUAL","IGUALIGUAL","DISTINTO","FLECHA","LONG",
"DO","UNTIL","TRUNC","CR","STRING","IDCOMP","CADENASTR","CTEL","CTEF","INVALID",
};
char *yyrule[] = {
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
"constante : '+' CTEL",
"constante : '+' CTEF",
};
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
#line 433 "parser_test.y"
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
#line 696 "y.tab.c"
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
#line 21 "parser_test.y"
{ errManager.debug("Declaracion de programa detectada", al.getLine()); }
break;
case 2:
#line 23 "parser_test.y"
{ errManager.error("No se permiten sentencias luego del cierre de programa", al.getLine()); }
break;
case 3:
#line 25 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 4:
#line 27 "parser_test.y"
{ errManager.error("Falta apertura del programa '{'", al.getLine()); }
break;
case 5:
#line 29 "parser_test.y"
{ errManager.error("Falta cierre del programa '{'", al.getLine()); }
break;
case 6:
#line 31 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 7:
#line 33 "parser_test.y"
{ errManager.error("Programa vacio.", al.getLine()); }
break;
case 8:
#line 35 "parser_test.y"
{ errManager.error("Falta identificador del programa", al.getLine()); }
break;
case 9:
#line 37 "parser_test.y"
{ errManager.error("Faltan delimitadores de programa de programa '{' '}'", al.getLine()); }
break;
case 13:
#line 48 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 21:
#line 60 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 22:
#line 62 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 23:
#line 64 "parser_test.y"
{ errManager.error("Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 24:
#line 69 "parser_test.y"
{ errManager.debug("Declaracion de funcion detectada.", al.getLine()); }
break;
case 25:
#line 71 "parser_test.y"
{ errManager.error("Funcion sin tipo.", al.getLine()); }
break;
case 26:
#line 73 "parser_test.y"
{ errManager.error("Funcion sin nombre.", al.getLine()); }
break;
case 27:
#line 75 "parser_test.y"
{ errManager.error("No se debe especificar la unidad en declaracion de funcion", al.getLine()); }
break;
case 29:
#line 80 "parser_test.y"
{ errManager.error("Parametros formales faltantes", al.getLine()); }
break;
case 31:
#line 85 "parser_test.y"
{ errManager.error("Faltan cuerpo de la funcion", al.getLine()); }
break;
case 32:
#line 86 "parser_test.y"
{ errManager.error("Faltan llaves de la funcion", al.getLine()); }
break;
case 35:
#line 94 "parser_test.y"
{ errManager.error("Sentencia invalida.", al.getLine()); }
break;
case 43:
#line 112 "parser_test.y"
{ errManager.error("Falta separador de parametros ','", al.getLine()); }
break;
case 44:
#line 117 "parser_test.y"
{ errManager.debug("Parametro formal con semantica opia-resultado detectado.", al.getLine()); }
break;
case 45:
#line 119 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 46:
#line 121 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 47:
#line 123 "parser_test.y"
{ errManager.debug("Parametro formal con semantica por defecto detectado.", al.getLine()); }
break;
case 48:
#line 125 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 49:
#line 127 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 50:
#line 129 "parser_test.y"
{ errManager.debug("Parametro formal lambda semantica copia-resultado detectado", al.getLine()); }
break;
case 51:
#line 131 "parser_test.y"
{ errManager.error("Se espera un tipo correspondiente al parametro formal", al.getLine()); }
break;
case 52:
#line 133 "parser_test.y"
{ errManager.error("Se espera un Identifier correspondiente al parametro formal", al.getLine()); }
break;
case 53:
#line 138 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 54:
#line 140 "parser_test.y"
{ errManager.debug("Declaracion de variable detectada.",  al.getLine()); }
break;
case 55:
#line 142 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 56:
#line 144 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 57:
#line 146 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 58:
#line 148 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 59:
#line 150 "parser_test.y"
{ errManager.error("Falta separador de variable ','", al.getLine()); }
break;
case 60:
#line 157 "parser_test.y"
{ errManager.debug("Parametro real detectado", al.getLine()); }
break;
case 65:
#line 173 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 66:
#line 175 "parser_test.y"
{ errManager.debug("IF detectado (sin ELSE)", al.getLine()); }
break;
case 67:
#line 177 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 68:
#line 179 "parser_test.y"
{ errManager.debug("IF-ELSE detectado", al.getLine()); }
break;
case 69:
#line 181 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 70:
#line 183 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 71:
#line 185 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 72:
#line 187 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 73:
#line 189 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 74:
#line 191 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 75:
#line 193 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 76:
#line 195 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 77:
#line 197 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 78:
#line 199 "parser_test.y"
{ errManager.error("Falta cuerpo de la sentencia", al.getLine()); }
break;
case 81:
#line 206 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 82:
#line 208 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 83:
#line 210 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 84:
#line 212 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 85:
#line 214 "parser_test.y"
{ errManager.error("Falta cierre endif",  al.getLine()); }
break;
case 86:
#line 216 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 87:
#line 218 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 88:
#line 220 "parser_test.y"
{ errManager.error(" Falta cuerpo de la sentencia ;.", al.getLine()); }
break;
case 90:
#line 226 "parser_test.y"
{ errManager.error("Falta parentesis de apertura de la condicion", al.getLine()); }
break;
case 91:
#line 228 "parser_test.y"
{ errManager.error("Falta parentesis de cierre de la condicion", al.getLine()); }
break;
case 92:
#line 230 "parser_test.y"
{ errManager.error("Faltan parentesis de cierre y apertura de la condicion", al.getLine()); }
break;
case 94:
#line 236 "parser_test.y"
{ errManager.error("Falta llave de cierre", al.getLine()); }
break;
case 95:
#line 238 "parser_test.y"
{ errManager.error("Falta llave de apertura", al.getLine()); }
break;
case 96:
#line 240 "parser_test.y"
{ errManager.error("Falta contenido en bloque", al.getLine()); }
break;
case 97:
#line 246 "parser_test.y"
{ errManager.debug("Condicion detectada. Linea: " + al.getLine()); }
break;
case 98:
#line 248 "parser_test.y"
{ errManager.error("Comparador de condicion invalido/faltante", al.getLine()); }
break;
case 99:
#line 253 "parser_test.y"
{ errManager.debug("DO-UNTIL detectado", al.getLine()); }
break;
case 100:
#line 255 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 101:
#line 257 "parser_test.y"
{ errManager.error("Falta cuerpo de DO", al.getLine()); }
break;
case 102:
#line 259 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 103:
#line 261 "parser_test.y"
{ errManager.error("Falta cierre de bloque UNTIL", al.getLine()); }
break;
case 112:
#line 280 "parser_test.y"
{ errManager.debug("Asignacion detectada", al.getLine()); }
break;
case 113:
#line 282 "parser_test.y"
{ errManager.error(" Falta delimitador de sentencias ;.", al.getLine()); }
break;
case 114:
#line 284 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 115:
#line 286 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 117:
#line 292 "parser_test.y"
{ errManager.error("Falta el segundo operando en la suma", al.getLine()); }
break;
case 118:
#line 294 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 120:
#line 297 "parser_test.y"
{ errManager.error("Falta el segundo operando en la resta", al.getLine()); }
break;
case 121:
#line 299 "parser_test.y"
{ errManager.debug("Faltan los dos operandos", al.getLine()); }
break;
case 123:
#line 302 "parser_test.y"
{ errManager.debug("Trunc detectado", al.getLine()); }
break;
case 124:
#line 304 "parser_test.y"
{ errManager.error("Cuerpo del trunc invalido", al.getLine()); }
break;
case 126:
#line 310 "parser_test.y"
{ errManager.debug("Falta el primer operando en la multiplicacion", al.getLine()); }
break;
case 127:
#line 312 "parser_test.y"
{ errManager.error("Falta el segundo operando en la multiplicacion", al.getLine()); }
break;
case 128:
#line 314 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la multiplicacion", al.getLine()); }
break;
case 130:
#line 317 "parser_test.y"
{ errManager.debug("Falta el primer operando en la division", al.getLine()); }
break;
case 131:
#line 319 "parser_test.y"
{ errManager.error("Falta el segundo operando en la division", al.getLine()); }
break;
case 132:
#line 321 "parser_test.y"
{ errManager.debug("Faltan los dos operandos en la division", al.getLine()); }
break;
case 138:
#line 331 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 139:
#line 336 "parser_test.y"
{ errManager.debug("Llamado a funcion detectado", al.getLine()); }
break;
case 140:
#line 338 "parser_test.y"
{ errManager.error("Falta prefijo obligatorio del ID", al.getLine()); }
break;
case 141:
#line 340 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 142:
#line 342 "parser_test.y"
{ errManager.error("Llamado a funcion con parametros invalidos", al.getLine()); }
break;
case 143:
#line 347 "parser_test.y"
{ errManager.debug("Print detectado con expresion", al.getLine());}
break;
case 144:
#line 351 "parser_test.y"
{ errManager.debug("Definicion lambda detectada", al.getLine()); }
break;
case 145:
#line 353 "parser_test.y"
{ errManager.error("Falta llave de cierre en lambda", al.getLine()); }
break;
case 146:
#line 355 "parser_test.y"
{ errManager.error("Falta llave de apertura en lambda", al.getLine()); }
break;
case 147:
#line 357 "parser_test.y"
{ errManager.error("Faltan llaves en lambda", al.getLine()); }
break;
case 148:
#line 362 "parser_test.y"
{errManager.debug("Retorno detectado. Linea: " + al.getLine());}
break;
case 150:
#line 368 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 151:
#line 370 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 152:
#line 372 "parser_test.y"
{errManager.error("Faltan argumentos", al.getLine());}
break;
case 153:
#line 374 "parser_test.y"
{errManager.error("Falta parentesis de cierre", al.getLine());}
break;
case 154:
#line 376 "parser_test.y"
{errManager.error("Falta parentesis de apertura", al.getLine());}
break;
case 155:
#line 381 "parser_test.y"
{ errManager.debug("Asignacion multiple detectada. Linea: " + al.getLine()); }
break;
case 156:
#line 383 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 157:
#line 385 "parser_test.y"
{ errManager.error("Error en asignacion multiple, separador a utilizar: ','", al.getLine()); }
break;
case 158:
#line 387 "parser_test.y"
{ errManager.error("Falta separador ';'", al.getLine()); }
break;
case 163:
#line 402 "parser_test.y"
{
            Atributo atributoActual = al.ts.obtener(yyvsp[0].sval);
            if (atributoActual.ref == 1){
                if (atributoActual.numValue == (2147483648L)) {
                    errManager.warning("Constante long fuera de rango, truncando.");
                    atributoActual.numValue -= 1;
                }
            }

            yyval.sval = yyvsp[0].sval;
        }
break;
case 165:
#line 416 "parser_test.y"
{
	        tratarNegativos(yyvsp[0].sval);
	        yyval.sval = '-' + yyvsp[0].sval;
	    }
break;
case 166:
#line 421 "parser_test.y"
{
           tratarNegativos(yyvsp[0].sval);
           yyval.sval = '-' + yyvsp[0].sval;
        }
break;
case 167:
#line 426 "parser_test.y"
{ errManager.error("factor invalido", al.getLine()); }
break;
#line 1308 "y.tab.c"
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
