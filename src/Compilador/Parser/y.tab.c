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
    4,    4,    4,    4,    4,    5,    5,    5,    5,    6,
    6,    6,    6,    6,    1,    1,   15,   15,   14,   14,
   14,   17,   17,   17,   13,   13,   13,   13,   13,   16,
   16,   16,   16,   18,   19,   19,    8,    8,   22,   22,
   24,   23,   25,   25,   25,   25,   25,   25,   12,   12,
    7,    7,   20,   20,   20,   20,   26,   26,   26,   27,
   27,   27,   27,   27,    9,    9,    9,    9,   10,   10,
   21,   11,
};
short yylen[] = {                                         2,
    4,    4,    3,    3,    3,    1,    2,    1,    2,    1,
    1,    1,    1,    1,    1,    2,    2,    2,    2,    8,
    7,    8,    7,    6,    1,    2,    1,    2,    1,    3,
    3,    1,    3,    3,    1,    3,    3,    1,    3,    3,
    2,    3,    4,    3,    1,    1,    1,    1,    8,   12,
    3,    8,    1,    1,    1,    1,    1,    1,    1,    1,
    3,    3,    3,    3,    1,    4,    3,    3,    1,    1,
    1,    1,    1,    1,    4,    4,    3,    3,    4,    4,
    5,    4,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   59,    0,   60,    0,    0,    0,   25,    6,    0,    0,
   10,   11,   12,   13,   14,   15,    0,   47,   48,    0,
    0,    0,    0,   19,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    4,   26,    7,    9,    0,   18,
    0,    3,    2,    0,    0,    0,    0,   71,    0,   72,
    0,   73,    0,    0,   69,    0,   78,    0,   32,    0,
    0,   46,    0,    0,    0,    0,    0,   27,    0,    0,
   77,    0,    1,    0,    0,    0,    0,    0,   29,   37,
   39,   36,    0,    0,    0,    0,    0,    0,    0,   76,
    0,    0,   53,   54,   55,   56,   57,   58,    0,    0,
   79,   80,   82,    0,   28,   75,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,    0,   67,   68,
    0,   34,   33,   44,    0,    0,    0,    0,    0,   42,
    0,   40,   31,    0,   30,   66,    0,    0,    0,   24,
    0,    0,   43,    0,    0,    0,    0,   21,   23,    0,
   22,    0,   49,   52,   20,    0,    0,    0,   50,
};
short yydgoto[] = {                                       4,
   16,   17,   18,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   35,   88,   79,   89,   68,   69,   70,   71,
   72,   28,   29,   74,  109,   64,   65,
};
short yysindex[] = {                                    -87,
  -98,  -78,  373,    0,  373, -243,  -36,    9,   18,   19,
    0,  -90,    0,  -34,  373,  174,    0,    0,   38,   46,
    0,    0,    0,    0,    0,    0, -238,    0,    0,  197,
  220,  -33, -197,    0,   -5, -178,  -38, -178, -201, -178,
 -168, -178,  -32,  243,    0,    0,    0,    0,  -28,    0,
   -5,    0,    0,  -62, -200, -206,   45,    0,  101,    0,
  118,    0,   74,  -15,    0, -173,    0,  -23,    0, -156,
   74,    0,  114,  113,  115,   77,   94,    0, -112,   74,
    0,  -18,    0,  -41, -173, -143,  -96,  -13,    0,    0,
    0,    0, -178, -176, -176, -176, -176,  124,  -30,    0,
  -30,  -62,    0,    0,    0,    0,    0,    0, -178,   48,
    0,    0,    0, -100,    0,    0,   57,   -7,  -75, -173,
  -70,    0,  -62,   73,  -62,  103,  -15,  -15,    0,    0,
   83,    0,    0,    0,   74, -168,  168,  266,   87,    0,
  -45,    0,    0,  373,    0,    0, -168,  -95, -178,    0,
  284,  302,    0,  313, -168, -245,  175,    0,    0,  336,
    0,   92,    0,    0,    0, -168,  -71,  -40,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  163,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -39,   -6,    0,  165,    0,    0,    0,    0,    0,
    0,    0,    0,  222,    0,    0,    0,    0,  -39,    0,
  193,    0,    0,    0,    0,    0,    1,    0,    0,    0,
   24,    0,  116,   47,    0,    0,    0,    0,    0,    0,
  -17,    0,    0,    0,    0,    0,    0,    0,    0,  140,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   70,   93,    0,    0,
    0,    0,    0,    0,  213,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
   59,  450,    0,  -24,    0,    0,   81,    0,  579,    0,
    0,  540,  228,  177, -125,  -73,  216,   26,    0,  494,
    0,    0,    0,  117,    0,   95,  108,
};
#define YYTABLESIZE 728
short yytable[] = {                                     117,
   74,   66,   67,   37,   38,   43,   54,   66,   81,   66,
  148,   84,  114,   32,  162,  163,   78,  100,   49,   38,
  101,  155,  116,   70,    5,  101,   96,  124,  134,  156,
  125,   97,   41,  139,   33,    3,  125,   35,   56,   33,
  167,   74,   74,   74,   15,   74,   65,   74,   38,  143,
   91,  145,   35,  168,  115,   57,   58,   39,   40,   74,
   74,   30,   74,   31,   70,   70,   70,   42,   70,   63,
   70,   92,   59,   44,   75,   60,   61,   90,   57,   58,
   57,   58,   70,   70,   37,   70,   34,   65,    7,   65,
    8,   65,   64,    9,   10,   59,   47,   11,   60,   61,
   60,   61,   13,   12,   48,   65,   65,   50,   65,   14,
   63,   78,   63,  102,   63,   62,   94,  112,   95,   94,
  120,   95,   78,  115,  132,   74,  133,   11,   63,   63,
  115,   63,   13,   64,  113,   64,   94,   64,   95,   61,
   93,   78,  115,  146,    7,   94,    8,   95,   70,    9,
   10,   64,   64,  110,   64,  111,   94,   43,   95,   12,
  122,    7,    8,    8,  131,   14,    9,   10,    1,    2,
  136,   65,  137,  108,   62,  107,   12,    6,    7,  138,
    8,  140,   14,    9,   10,    7,  142,    8,  127,  128,
    9,   10,   11,   12,   63,  144,  151,   13,   61,   14,
   12,   85,  154,  129,  130,  147,   14,  149,   11,  152,
  160,  153,   86,   13,  166,  164,   38,   64,   57,   58,
  169,    5,   85,   17,   57,   58,   57,   58,   36,   11,
   42,   36,   99,   86,   13,   59,   36,   99,   60,   61,
   62,   59,  123,   59,   60,   61,   60,   61,  123,   35,
   55,   16,   45,   51,   51,   81,   74,   74,   82,   74,
  118,    0,   74,   74,   61,  157,   74,   74,   74,   74,
   74,   74,   74,    0,    0,    0,   74,    0,   74,   70,
   70,    0,   70,    0,    0,   70,   70,    8,    0,   70,
   70,   70,   70,   70,   70,   70,    0,    0,   45,   70,
    0,   70,   65,   65,    0,   65,    0,    0,   65,   65,
    0,    0,   65,   65,   65,   65,   65,   65,   65,    0,
    0,   52,   65,    0,   65,   63,   63,    0,   63,    0,
    0,   63,   63,    0,    0,   63,   63,   63,   63,   63,
   63,   63,    0,    0,   53,   63,    0,   63,   64,   64,
    0,   64,    0,    0,   64,   64,    0,    0,   64,   64,
   64,   64,   64,   64,   64,    0,    0,   83,   64,    0,
   64,   62,   62,    0,   62,    0,    0,   62,   62,  103,
  104,  105,  106,    0,    0,   62,   62,   62,    0,    0,
  150,   62,    0,   62,    0,   61,   61,    0,   61,    0,
    0,   61,   61,    0,    0,    0,    0,    0,  158,   61,
   61,   61,    0,    0,    0,   61,    0,   61,    8,    8,
    0,    8,    0,    0,    8,    8,  159,    0,    0,    6,
    7,    0,    8,    8,    8,    9,   10,  161,    8,    0,
    8,    0,    0,    0,   11,   12,    0,    0,    0,   13,
    0,   14,    6,    7,    0,    8,    0,    0,    9,   10,
  165,    0,    0,    0,    0,   46,    0,   11,   12,    0,
    0,    0,   13,    0,   14,    6,    7,    0,    8,   46,
   46,    9,   10,    0,    0,    0,    0,    0,    0,    0,
   11,   12,    0,   46,    0,   13,    0,   14,    6,    7,
    0,    8,    0,    0,    9,   10,    0,    0,    0,    0,
    0,    0,    0,   11,   12,    0,    0,    0,   13,    0,
   14,    6,    7,    0,    8,    0,    0,    9,   10,   63,
    0,   73,   76,   77,    0,   80,   11,   12,    0,    6,
    7,   13,    8,   14,    0,    9,   10,    0,    0,    0,
    0,    0,    0,    0,   11,   12,    0,    6,    7,   13,
    8,   14,    0,    9,   10,    0,    0,    0,    6,    7,
    0,    8,   11,   12,    9,   10,    0,   13,    0,   14,
    0,    0,    0,   11,   12,    0,  126,    0,   13,    0,
   14,    6,    7,   87,    8,    0,    0,    9,   10,    0,
   46,    0,  135,   46,    0,   98,   11,   12,    0,   46,
    0,   13,    0,   14,   62,   62,   62,   62,   62,    0,
   62,   62,    0,   87,  119,  121,    0,    0,    6,    7,
    0,    8,    0,    0,    9,   10,    0,    0,    0,    0,
    0,   87,   73,   11,   12,    0,    0,    0,   13,    0,
   14,    0,    0,    0,    0,    0,    0,    0,    0,  141,
    0,    0,   87,    0,   87,    0,    0,    0,    0,    0,
    0,   62,   62,   62,   62,   62,    0,   62,    0,   62,
    0,    0,    0,    0,    0,    0,    0,   62,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   62,
};
short yycheck[] = {                                      41,
    0,   40,   41,   40,   44,   40,   40,   40,   41,   40,
  136,   40,  125,  257,  260,  261,   41,   41,  257,   59,
   44,  147,   41,    0,  123,   44,   42,   41,  102,  125,
   44,   47,  123,   41,  278,  123,   44,   44,   44,  278,
  166,   41,   42,   43,  123,   45,    0,   47,   40,  123,
  257,  125,   59,  125,   79,  257,  258,   40,   40,   59,
   60,    3,   62,    5,   41,   42,   43,  265,   45,    0,
   47,  278,  274,   15,  276,  277,  278,  278,  257,  258,
  257,  258,   59,   60,   40,   62,    6,   41,  257,   43,
  259,   45,    0,  262,  263,  274,   59,  271,  277,  278,
  277,  278,  276,  272,   59,   59,   60,   27,   62,  278,
   41,  136,   43,  270,   45,    0,   43,   41,   45,   43,
  264,   45,  147,  148,   99,  125,  101,  271,   59,   60,
  155,   62,  276,   41,   41,   43,   43,   45,   45,    0,
   40,  166,  167,   41,  257,   43,  259,   45,  125,  262,
  263,   59,   60,   41,   62,   41,   43,   40,   45,  272,
  257,  257,    0,  259,   41,  278,  262,  263,  256,  257,
  123,  125,  273,   60,   59,   62,  272,  256,  257,  123,
  259,  257,  278,  262,  263,  257,  257,  259,   94,   95,
  262,  263,  271,  272,  125,  123,  138,  276,   59,  278,
  272,  264,  144,   96,   97,  123,  278,   40,  271,  123,
  152,  257,  275,  276,  123,   41,  256,  125,  257,  258,
  261,    0,  264,   59,  257,  258,  257,  258,  265,  271,
  265,  265,  256,  275,  276,  274,  265,  256,  277,  278,
  125,  274,  256,  274,  277,  278,  277,  278,  256,  256,
  256,   59,  270,   41,   27,  270,  256,  257,   43,  259,
   84,   -1,  262,  263,  125,  149,  266,  267,  268,  269,
  270,  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,
  257,   -1,  259,   -1,   -1,  262,  263,  125,   -1,  266,
  267,  268,  269,  270,  271,  272,   -1,   -1,  125,  276,
   -1,  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,
   -1,   -1,  266,  267,  268,  269,  270,  271,  272,   -1,
   -1,  125,  276,   -1,  278,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   -1,  266,  267,  268,  269,  270,
  271,  272,   -1,   -1,  125,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,   -1,   -1,  266,  267,
  268,  269,  270,  271,  272,   -1,   -1,  125,  276,   -1,
  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,  266,
  267,  268,  269,   -1,   -1,  270,  271,  272,   -1,   -1,
  125,  276,   -1,  278,   -1,  256,  257,   -1,  259,   -1,
   -1,  262,  263,   -1,   -1,   -1,   -1,   -1,  125,  270,
  271,  272,   -1,   -1,   -1,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,  125,   -1,   -1,  256,
  257,   -1,  259,  271,  272,  262,  263,  125,  276,   -1,
  278,   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,  276,
   -1,  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,
  125,   -1,   -1,   -1,   -1,   16,   -1,  271,  272,   -1,
   -1,   -1,  276,   -1,  278,  256,  257,   -1,  259,   30,
   31,  262,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  271,  272,   -1,   44,   -1,  276,   -1,  278,  256,  257,
   -1,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,  256,  257,   -1,  259,   -1,   -1,  262,  263,   36,
   -1,   38,   39,   40,   -1,   42,  271,  272,   -1,  256,
  257,  276,  259,  278,   -1,  262,  263,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  271,  272,   -1,  256,  257,  276,
  259,  278,   -1,  262,  263,   -1,   -1,   -1,  256,  257,
   -1,  259,  271,  272,  262,  263,   -1,  276,   -1,  278,
   -1,   -1,   -1,  271,  272,   -1,   93,   -1,  276,   -1,
  278,  256,  257,   54,  259,   -1,   -1,  262,  263,   -1,
  151,   -1,  109,  154,   -1,   66,  271,  272,   -1,  160,
   -1,  276,   -1,  278,   36,   37,   38,   39,   40,   -1,
   42,   43,   -1,   84,   85,   86,   -1,   -1,  256,  257,
   -1,  259,   -1,   -1,  262,  263,   -1,   -1,   -1,   -1,
   -1,  102,  149,  271,  272,   -1,   -1,   -1,  276,   -1,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  120,
   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   93,   94,   95,   96,   97,   -1,   99,   -1,  101,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  109,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  149,
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
"';'","'<'",0,"'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,"ID","CTEL","IF","ELSE","ENDIF","PRINT","RETURN","LAMBDA",
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
"sentencia_declarativa : declaracion_funcion",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : control",
"sentencia_ejecutable : llamada_funcion",
"sentencia_ejecutable : print",
"sentencia_ejecutable : retorno",
"declaracion_variable : tipo lista_identificadores",
"declaracion_variable : error lista_identificadores",
"declaracion_variable : tipo asignacion",
"declaracion_variable : error asignacion",
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
"lista_identificadores : IDCOMP",
"lista_identificadores : lista_identificadores ',' IDCOMP",
"lista_identificadores : lista_identificadores error IDCOMP",
"lista_identificadores : ID",
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
#line 212 "parser_test.y"
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
#line 424 "y.tab.c"
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
case 15:
#line 51 "parser_test.y"
{ System.out.println("checkear valido"); }
break;
case 17:
#line 56 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 18:
#line 57 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 19:
#line 58 "parser_test.y"
{ System.out.println("Tipo invalido/ausente"); }
break;
case 20:
#line 64 "parser_test.y"
{ System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
break;
case 21:
#line 67 "parser_test.y"
{ System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 22:
#line 70 "parser_test.y"
{ System.out.println("Tipo de funcion invalido/ausente. Linea: " + al.getLine()); }
break;
case 23:
#line 73 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine()); }
break;
case 24:
#line 76 "parser_test.y"
{ System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine());
         System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); }
break;
case 31:
#line 95 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 34:
#line 101 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 35:
#line 105 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 36:
#line 106 "parser_test.y"
{ System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
break;
case 37:
#line 107 "parser_test.y"
{ System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
break;
case 38:
#line 108 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 39:
#line 109 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 40:
#line 113 "parser_test.y"
{ System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
break;
case 41:
#line 114 "parser_test.y"
{ System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
break;
case 42:
#line 115 "parser_test.y"
{ System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
break;
case 43:
#line 116 "parser_test.y"
{ System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
break;
case 44:
#line 120 "parser_test.y"
{ System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
break;
case 49:
#line 135 "parser_test.y"
{ System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
break;
case 50:
#line 137 "parser_test.y"
{ System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
break;
case 51:
#line 141 "parser_test.y"
{ System.out.println("Condicion detectada. Linea: " + al.getLine()); }
break;
case 52:
#line 146 "parser_test.y"
{ System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
break;
case 61:
#line 164 "parser_test.y"
{ System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
break;
case 62:
#line 165 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 66:
#line 172 "parser_test.y"
{System.out.println("Trunc detectado. Linea: " + al.getLine()); }
break;
case 74:
#line 186 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 75:
#line 190 "parser_test.y"
{System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
break;
case 76:
#line 191 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 77:
#line 192 "parser_test.y"
{System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
break;
case 78:
#line 193 "parser_test.y"
{ System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
break;
case 79:
#line 197 "parser_test.y"
{System.out.println("Print detectado. Linea: " + al.getLine());}
break;
case 80:
#line 198 "parser_test.y"
{System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
break;
case 81:
#line 202 "parser_test.y"
{System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
break;
case 82:
#line 206 "parser_test.y"
{System.out.println("Retorno detectado. Linea: " + al.getLine());}
break;
#line 737 "y.tab.c"
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
