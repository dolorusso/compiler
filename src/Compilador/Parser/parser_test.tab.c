
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton implementation for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.4.1"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1

/* Using locations.  */
#define YYLSP_NEEDED 0



/* Copy the first part of user declarations.  */

/* Line 189 of yacc.c  */
#line 5 "parser_test.y"

    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;


/* Line 189 of yacc.c  */
#line 79 "parser_test.tab.c"

/* Enabling traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Enabling the token table.  */
#ifndef YYTOKEN_TABLE
# define YYTOKEN_TABLE 0
#endif


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     ID = 258,
     CTEL = 259,
     IF = 260,
     ELSE = 261,
     ENDIF = 262,
     PRINT = 263,
     RETURN = 264,
     LAMBDA = 265,
     ASIGNAR = 266,
     MENORIGUAL = 267,
     MAYORIGUAL = 268,
     IGUALIGUAL = 269,
     DISTINTO = 270,
     FLECHA = 271,
     LONG = 272,
     DO = 273,
     UNTIL = 274,
     TRUNC = 275,
     CR = 276,
     STRING = 277,
     CTEF = 278,
     IDCOMP = 279
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif


/* Copy the second part of user declarations.  */


/* Line 264 of yacc.c  */
#line 145 "parser_test.tab.c"

#ifdef short
# undef short
#endif

#ifdef YYTYPE_UINT8
typedef YYTYPE_UINT8 yytype_uint8;
#else
typedef unsigned char yytype_uint8;
#endif

#ifdef YYTYPE_INT8
typedef YYTYPE_INT8 yytype_int8;
#elif (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
typedef signed char yytype_int8;
#else
typedef short int yytype_int8;
#endif

#ifdef YYTYPE_UINT16
typedef YYTYPE_UINT16 yytype_uint16;
#else
typedef unsigned short int yytype_uint16;
#endif

#ifdef YYTYPE_INT16
typedef YYTYPE_INT16 yytype_int16;
#else
typedef short int yytype_int16;
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif ! defined YYSIZE_T && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned int
# endif
#endif

#define YYSIZE_MAXIMUM ((YYSIZE_T) -1)

#ifndef YY_
# if YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(msgid) dgettext ("bison-runtime", msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(msgid) msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(e) ((void) (e))
#else
# define YYUSE(e) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(n) (n)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int yyi)
#else
static int
YYID (yyi)
    int yyi;
#endif
{
  return yyi;
}
#endif

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#     ifndef _STDLIB_H
#      define _STDLIB_H 1
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's `empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (YYID (0))
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined _STDLIB_H \
       && ! ((defined YYMALLOC || defined malloc) \
	     && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef _STDLIB_H
#    define _STDLIB_H 1
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined _STDLIB_H && (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
	 || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yytype_int16 yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (sizeof (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (sizeof (yytype_int16) + sizeof (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

/* Copy COUNT objects from FROM to TO.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(To, From, Count) \
      __builtin_memcpy (To, From, (Count) * sizeof (*(From)))
#  else
#   define YYCOPY(To, From, Count)		\
      do					\
	{					\
	  YYSIZE_T yyi;				\
	  for (yyi = 0; yyi < (Count); yyi++)	\
	    (To)[yyi] = (From)[yyi];		\
	}					\
      while (YYID (0))
#  endif
# endif

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)				\
    do									\
      {									\
	YYSIZE_T yynewbytes;						\
	YYCOPY (&yyptr->Stack_alloc, Stack, yysize);			\
	Stack = &yyptr->Stack_alloc;					\
	yynewbytes = yystacksize * sizeof (*Stack) + YYSTACK_GAP_MAXIMUM; \
	yyptr += yynewbytes / sizeof (*yyptr);				\
      }									\
    while (YYID (0))

#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  31
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   352

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  37
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  29
/* YYNRULES -- Number of rules.  */
#define YYNRULES  86
/* YYNRULES -- Number of states.  */
#define YYNSTATES  176

/* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   279

#define YYTRANSLATE(YYX)						\
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const yytype_uint8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
      28,    29,    35,    33,    30,    34,     2,    36,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    27,
      32,     2,    31,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,    25,     2,    26,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const yytype_uint16 yyprhs[] =
{
       0,     0,     3,     8,    13,    17,    21,    25,    27,    29,
      32,    34,    37,    39,    41,    44,    46,    49,    51,    54,
      56,    59,    62,    65,    68,    77,    85,    94,   102,   109,
     111,   114,   116,   119,   121,   125,   129,   131,   135,   139,
     141,   145,   149,   151,   155,   159,   162,   166,   171,   175,
     177,   179,   182,   185,   194,   207,   211,   220,   222,   224,
     226,   228,   230,   232,   234,   236,   240,   244,   248,   252,
     254,   259,   263,   267,   269,   271,   273,   275,   277,   279,
     284,   289,   293,   297,   302,   307,   313
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const yytype_int8 yyrhs[] =
{
      38,     0,    -1,     3,    25,    44,    26,    -1,     1,    25,
      44,    26,    -1,    25,    44,    26,    -1,     3,    44,    26,
      -1,     3,    25,    44,    -1,    40,    -1,    41,    -1,    42,
      27,    -1,    43,    -1,    58,    27,    -1,    58,    -1,    52,
      -1,    62,    27,    -1,    62,    -1,    63,    27,    -1,    63,
      -1,    65,    27,    -1,    65,    -1,    57,    48,    -1,     1,
      48,    -1,    57,    58,    -1,     1,    58,    -1,    57,     3,
      28,    46,    29,    25,    44,    26,    -1,    57,     3,    28,
      29,    25,    44,    26,    -1,     1,     3,    28,    46,    29,
      25,    44,    26,    -1,    57,     3,    28,    46,    29,    25,
      26,    -1,    57,     3,    28,    29,    25,    26,    -1,    39,
      -1,    44,    39,    -1,    41,    -1,    45,    41,    -1,    49,
      -1,    46,    30,    49,    -1,    46,     1,    49,    -1,    50,
      -1,    47,    30,    50,    -1,    47,     1,    50,    -1,    24,
      -1,    48,    30,    24,    -1,    48,     1,    24,    -1,     3,
      -1,    48,    30,     3,    -1,    21,    57,     3,    -1,    57,
       3,    -1,    10,    57,     3,    -1,    21,    10,    57,     3,
      -1,    51,    16,    49,    -1,    59,    -1,    64,    -1,    53,
      27,    -1,    55,    27,    -1,     5,    28,    54,    29,    25,
      45,    26,     7,    -1,     5,    28,    54,    29,    25,    45,
      26,     6,    25,    45,    26,     7,    -1,    59,    56,    59,
      -1,    18,    25,    45,    26,    19,    28,    54,    29,    -1,
      12,    -1,    13,    -1,    14,    -1,    15,    -1,    31,    -1,
      32,    -1,    17,    -1,    22,    -1,    24,    11,    59,    -1,
       3,    11,    59,    -1,    59,    33,    60,    -1,    59,    34,
      60,    -1,    60,    -1,    20,    28,    59,    29,    -1,    60,
      35,    61,    -1,    60,    36,    61,    -1,    61,    -1,    24,
      -1,     4,    -1,    23,    -1,    62,    -1,     3,    -1,    24,
      28,    47,    29,    -1,     3,    28,    47,    29,    -1,    24,
      28,    29,    -1,     3,    28,    29,    -1,     8,    28,    22,
      29,    -1,     8,    28,    59,    29,    -1,    28,    57,    29,
      25,    45,    -1,     9,    28,    59,    29,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const yytype_uint8 yyrline[] =
{
       0,    16,    16,    19,    22,    25,    29,    35,    36,    40,
      42,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      58,    59,    60,    61,    65,    68,    71,    74,    77,    86,
      87,    91,    92,    96,    97,    98,   102,   103,   104,   108,
     109,   110,   111,   112,   116,   117,   118,   119,   123,   127,
     128,   132,   133,   137,   139,   144,   148,   153,   154,   155,
     156,   157,   158,   162,   163,   167,   168,   172,   173,   174,
     175,   179,   180,   181,   185,   186,   187,   188,   189,   193,
     194,   195,   196,   200,   201,   205,   209
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || YYTOKEN_TABLE
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "ID", "CTEL", "IF", "ELSE", "ENDIF",
  "PRINT", "RETURN", "LAMBDA", "ASIGNAR", "MENORIGUAL", "MAYORIGUAL",
  "IGUALIGUAL", "DISTINTO", "FLECHA", "LONG", "DO", "UNTIL", "TRUNC", "CR",
  "STRING", "CTEF", "IDCOMP", "'{'", "'}'", "';'", "'('", "')'", "','",
  "'>'", "'<'", "'+'", "'-'", "'*'", "'/'", "$accept", "programa",
  "sentencia", "sentencia_declarativa", "sentencia_ejecutable",
  "declaracion_variable", "declaracion_funcion", "lista_sentencias",
  "lista_sentencias_ejecutables", "lista_parametros_formales",
  "lista_parametros_reales", "lista_identificadores", "parametro_formal",
  "parametro_real_compuesto", "parametro_real", "control", "sentencia_IF",
  "condicion", "do_until", "comparador", "tipo", "asignacion", "expresion",
  "termino", "factor", "llamada_funcion", "print", "lambda", "retorno", 0
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[YYLEX-NUM] -- Internal token number corresponding to
   token YYLEX-NUM.  */
static const yytype_uint16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   123,   125,    59,    40,    41,
      44,    62,    60,    43,    45,    42,    47
};
# endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_uint8 yyr1[] =
{
       0,    37,    38,    38,    38,    38,    38,    39,    39,    40,
      40,    41,    41,    41,    41,    41,    41,    41,    41,    41,
      42,    42,    42,    42,    43,    43,    43,    43,    43,    44,
      44,    45,    45,    46,    46,    46,    47,    47,    47,    48,
      48,    48,    48,    48,    49,    49,    49,    49,    50,    51,
      51,    52,    52,    53,    53,    54,    55,    56,    56,    56,
      56,    56,    56,    57,    57,    58,    58,    59,    59,    59,
      59,    60,    60,    60,    61,    61,    61,    61,    61,    62,
      62,    62,    62,    63,    63,    64,    65
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const yytype_uint8 yyr2[] =
{
       0,     2,     4,     4,     3,     3,     3,     1,     1,     2,
       1,     2,     1,     1,     2,     1,     2,     1,     2,     1,
       2,     2,     2,     2,     8,     7,     8,     7,     6,     1,
       2,     1,     2,     1,     3,     3,     1,     3,     3,     1,
       3,     3,     1,     3,     3,     2,     3,     4,     3,     1,
       1,     2,     2,     8,    12,     3,     8,     1,     1,     1,
       1,     1,     1,     1,     1,     3,     3,     3,     3,     1,
       4,     3,     3,     1,     1,     1,     1,     1,     1,     4,
       4,     3,     3,     4,     4,     5,     4
};

/* YYDEFACT[STATE-NAME] -- Default rule to reduce with in state
   STATE-NUM when YYTABLE doesn't specify something else to do.  Zero
   means the default is an error.  */
static const yytype_uint8 yydefact[] =
{
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,    63,     0,    64,     0,     0,    29,     7,     8,     0,
      10,     0,    13,     0,     0,     0,    12,    15,    17,    19,
       0,     1,     0,    42,    39,     0,    23,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     9,     5,    30,    51,
      52,    42,     0,    22,    11,    14,    16,    18,     4,     3,
       0,     0,     0,    78,    75,     0,    76,    74,    66,    69,
      73,    77,     0,    82,     0,    36,     0,    49,    50,     0,
       0,     0,     0,     0,    31,     0,    65,    81,     0,     2,
       0,     0,     0,     0,    33,     0,    41,    43,    40,     0,
       0,     0,     0,     0,     0,     0,    80,     0,     0,     0,
      57,    58,    59,    60,    61,    62,     0,    83,    84,    86,
       0,    32,    79,     0,     0,     0,     0,     0,     0,     0,
       0,    45,     0,    67,    68,    71,    72,     0,    38,    37,
      48,     0,    55,     0,     0,     0,    46,     0,    44,    35,
       0,    34,    70,     0,     0,     0,    28,     0,     0,    47,
       0,    85,     0,     0,    25,    27,     0,    26,     0,    53,
      56,    24,     0,     0,     0,    54
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
      -1,     4,    16,    17,    18,    19,    20,    21,    85,    93,
      74,    35,    94,    75,    76,    22,    23,    79,    24,   116,
      25,    26,    77,    69,    70,    27,    28,    78,    29
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -133
static const yytype_int16 yypact[] =
{
      50,   -22,   250,   261,    11,   261,     2,    94,    21,    63,
      75,  -133,   -18,  -133,   110,   261,  -133,  -133,  -133,    -2,
    -133,   162,  -133,    41,    87,    59,    99,   112,   115,   121,
     173,  -133,   184,   133,   139,     5,  -133,   297,    73,   297,
     113,   297,   301,   297,    95,   151,  -133,  -133,  -133,  -133,
    -133,   155,     7,  -133,  -133,  -133,  -133,  -133,  -133,  -133,
     136,   138,   101,   144,  -133,   166,  -133,   177,    24,    25,
    -133,  -133,   274,  -133,     9,  -133,   148,    24,  -133,   187,
      96,   198,   295,   304,  -133,   268,    24,  -133,    13,  -133,
     305,   274,    42,    40,  -133,   235,  -133,  -133,  -133,   297,
     308,   308,   308,   308,   220,    43,  -133,    43,   136,   277,
    -133,  -133,  -133,  -133,  -133,  -133,   297,  -133,  -133,  -133,
     241,  -133,  -133,   282,    44,   310,   274,   315,   136,   298,
     136,  -133,   306,    25,    25,  -133,  -133,   311,  -133,  -133,
    -133,   301,    24,   302,   195,   316,  -133,   341,  -133,  -133,
     261,  -133,  -133,   301,   279,   297,  -133,   206,   217,  -133,
     228,   301,   283,   317,  -133,  -133,   239,  -133,   320,  -133,
    -133,  -133,   301,   290,   340,  -133
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -133,  -133,   -17,  -133,   -41,  -133,  -133,    -3,  -132,   258,
     307,   324,   -43,   -57,  -133,  -133,  -133,   197,  -133,  -133,
     -36,    82,   -10,   180,   240,   -21,  -133,  -133,  -133
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If zero, do what YYDEFACT says.
   If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -22
static const yytype_int16 yytable[] =
{
      30,    84,    32,     5,    48,    33,    61,    42,    61,   154,
     105,    31,    45,    48,   105,    48,    71,    71,    71,    71,
      71,   161,    71,    71,    95,    46,    34,    68,    48,    80,
      82,    83,   -21,    86,   -20,    62,   104,    62,   106,   107,
     173,   128,   122,   107,   121,   128,    63,    64,   138,    39,
     139,     1,   126,     2,    95,   125,   127,   100,   101,    11,
     102,   103,    51,    65,    13,   140,    66,    67,    49,   129,
     130,    72,    95,   145,   130,     3,    63,    64,    71,    71,
      71,    71,    71,    34,    71,   149,    71,   151,    36,   132,
     147,    40,    95,    65,    95,    71,    66,    67,    63,    64,
      84,    72,    73,    41,    97,    37,   142,    53,   110,   111,
     112,   113,    84,   121,    50,    65,    63,    64,    66,    67,
     121,    43,    38,    72,    87,    98,    54,   114,   115,   100,
     101,    84,   121,    65,    71,    81,    66,    67,    44,    55,
      48,   157,    56,    48,    37,    80,    91,   160,    57,    48,
      43,    -6,     6,    11,     7,   166,     8,    92,    13,     9,
      10,    60,    96,     6,   108,     7,    37,     8,    11,    12,
       9,    10,    38,    13,     6,    14,     7,    89,     8,    11,
      12,     9,    10,    90,    13,     6,    14,     7,    47,     8,
      11,    12,     9,    10,    99,    13,     6,    14,     7,    58,
       8,    11,    12,     9,    10,    44,    13,     6,    14,     7,
      59,     8,    11,    12,     9,    10,   109,    13,     6,    14,
       7,   156,     8,    11,    12,     9,    10,   117,    13,     6,
      14,     7,   164,     8,    11,    12,     9,    10,   131,    13,
       6,    14,     7,   165,     8,    11,    12,     9,    10,   137,
      13,     6,    14,     7,   167,     8,    11,    12,     9,    10,
     143,    13,     6,    14,     7,   171,     8,    11,    12,     9,
      10,     7,    13,     8,    14,    15,     9,    10,    11,    12,
     133,   134,     7,    13,     8,    14,    12,     9,    10,   168,
     169,    11,    14,     7,   120,     8,    13,    12,     9,    10,
      63,    64,   141,    14,     7,   162,     8,   144,    12,     9,
      10,    63,    64,   146,    14,    91,   174,    65,   148,    12,
      66,    67,    11,   150,   118,    14,    92,    13,   100,   101,
     155,    66,    67,   119,   123,   152,   153,   100,   101,   100,
     101,   158,   135,   136,   159,   172,   170,   175,   124,    52,
       0,    88,   163
};

static const yytype_int16 yycheck[] =
{
       3,    42,     5,    25,    21,     3,     1,    25,     1,   141,
       1,     0,    15,    30,     1,    32,    37,    38,    39,    40,
      41,   153,    43,    44,    60,    27,    24,    37,    45,    39,
      40,    41,    27,    43,    27,    30,    72,    30,    29,    30,
     172,     1,    29,    30,    85,     1,     3,     4,   105,    28,
     107,     1,    10,     3,    90,    91,    92,    33,    34,    17,
      35,    36,     3,    20,    22,   108,    23,    24,    27,    29,
      30,    28,   108,    29,    30,    25,     3,     4,    99,   100,
     101,   102,   103,    24,   105,   128,   107,   130,     6,    99,
     126,    28,   128,    20,   130,   116,    23,    24,     3,     4,
     141,    28,    29,    28,     3,    11,   116,    25,    12,    13,
      14,    15,   153,   154,    27,    20,     3,     4,    23,    24,
     161,    11,    28,    28,    29,    24,    27,    31,    32,    33,
      34,   172,   173,    20,   155,    22,    23,    24,    28,    27,
     157,   144,    27,   160,    11,   155,    10,   150,    27,   166,
      11,     0,     1,    17,     3,   158,     5,    21,    22,     8,
       9,    28,    24,     1,    16,     3,    11,     5,    17,    18,
       8,     9,    28,    22,     1,    24,     3,    26,     5,    17,
      18,     8,     9,    28,    22,     1,    24,     3,    26,     5,
      17,    18,     8,     9,    28,    22,     1,    24,     3,    26,
       5,    17,    18,     8,     9,    28,    22,     1,    24,     3,
      26,     5,    17,    18,     8,     9,    29,    22,     1,    24,
       3,    26,     5,    17,    18,     8,     9,    29,    22,     1,
      24,     3,    26,     5,    17,    18,     8,     9,     3,    22,
       1,    24,     3,    26,     5,    17,    18,     8,     9,    29,
      22,     1,    24,     3,    26,     5,    17,    18,     8,     9,
      19,    22,     1,    24,     3,    26,     5,    17,    18,     8,
       9,     3,    22,     5,    24,    25,     8,     9,    17,    18,
     100,   101,     3,    22,     5,    24,    18,     8,     9,     6,
       7,    17,    24,     3,    26,     5,    22,    18,     8,     9,
       3,     4,    25,    24,     3,    26,     5,    25,    18,     8,
       9,     3,     4,     3,    24,    10,    26,    20,     3,    18,
      23,    24,    17,    25,    29,    24,    21,    22,    33,    34,
      28,    23,    24,    29,    29,    29,    25,    33,    34,    33,
      34,    25,   102,   103,     3,    25,    29,     7,    90,    25,
      -1,    44,   155
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const yytype_uint8 yystos[] =
{
       0,     1,     3,    25,    38,    25,     1,     3,     5,     8,
       9,    17,    18,    22,    24,    25,    39,    40,    41,    42,
      43,    44,    52,    53,    55,    57,    58,    62,    63,    65,
      44,     0,    44,     3,    24,    48,    58,    11,    28,    28,
      28,    28,    25,    11,    28,    44,    27,    26,    39,    27,
      27,     3,    48,    58,    27,    27,    27,    27,    26,    26,
      28,     1,    30,     3,     4,    20,    23,    24,    59,    60,
      61,    62,    28,    29,    47,    50,    51,    59,    64,    54,
      59,    22,    59,    59,    41,    45,    59,    29,    47,    26,
      28,    10,    21,    46,    49,    57,    24,     3,    24,    28,
      33,    34,    35,    36,    57,     1,    29,    30,    16,    29,
      12,    13,    14,    15,    31,    32,    56,    29,    29,    29,
      26,    41,    29,    29,    46,    57,    10,    57,     1,    29,
      30,     3,    59,    60,    60,    61,    61,    29,    50,    50,
      49,    25,    59,    19,    25,    29,     3,    57,     3,    49,
      25,    49,    29,    25,    45,    28,    26,    44,    25,     3,
      44,    45,    26,    54,    26,    26,    44,    26,     6,     7,
      29,    26,    25,    45,    26,     7
};

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		(-2)
#define YYEOF		0

#define YYACCEPT	goto yyacceptlab
#define YYABORT		goto yyabortlab
#define YYERROR		goto yyerrorlab


/* Like YYERROR except do call yyerror.  This remains here temporarily
   to ease the transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */

#define YYFAIL		goto yyerrlab

#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)					\
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    {								\
      yychar = (Token);						\
      yylval = (Value);						\
      yytoken = YYTRANSLATE (yychar);				\
      YYPOPSTACK (1);						\
      goto yybackup;						\
    }								\
  else								\
    {								\
      yyerror (YY_("syntax error: cannot back up")); \
      YYERROR;							\
    }								\
while (YYID (0))


#define YYTERROR	1
#define YYERRCODE	256


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#define YYRHSLOC(Rhs, K) ((Rhs)[K])
#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)				\
    do									\
      if (YYID (N))                                                    \
	{								\
	  (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;	\
	  (Current).first_column = YYRHSLOC (Rhs, 1).first_column;	\
	  (Current).last_line    = YYRHSLOC (Rhs, N).last_line;		\
	  (Current).last_column  = YYRHSLOC (Rhs, N).last_column;	\
	}								\
      else								\
	{								\
	  (Current).first_line   = (Current).last_line   =		\
	    YYRHSLOC (Rhs, 0).last_line;				\
	  (Current).first_column = (Current).last_column =		\
	    YYRHSLOC (Rhs, 0).last_column;				\
	}								\
    while (YYID (0))
#endif


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if YYLTYPE_IS_TRIVIAL
#  define YY_LOCATION_PRINT(File, Loc)			\
     fprintf (File, "%d.%d-%d.%d",			\
	      (Loc).first_line, (Loc).first_column,	\
	      (Loc).last_line,  (Loc).last_column)
# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */

#ifdef YYLEX_PARAM
# define YYLEX yylex (YYLEX_PARAM)
#else
# define YYLEX yylex ()
#endif

/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)			\
do {						\
  if (yydebug)					\
    YYFPRINTF Args;				\
} while (YYID (0))

# define YY_SYMBOL_PRINT(Title, Type, Value, Location)			  \
do {									  \
  if (yydebug)								  \
    {									  \
      YYFPRINTF (stderr, "%s ", Title);					  \
      yy_symbol_print (stderr,						  \
		  Type, Value); \
      YYFPRINTF (stderr, "\n");						  \
    }									  \
} while (YYID (0))


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_value_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_value_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyoutput, yytoknum[yytype], *yyvaluep);
# else
  YYUSE (yyoutput);
# endif
  switch (yytype)
    {
      default:
	break;
    }
}


/*--------------------------------.
| Print this symbol on YYOUTPUT.  |
`--------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_symbol_print (FILE *yyoutput, int yytype, YYSTYPE const * const yyvaluep)
#else
static void
yy_symbol_print (yyoutput, yytype, yyvaluep)
    FILE *yyoutput;
    int yytype;
    YYSTYPE const * const yyvaluep;
#endif
{
  if (yytype < YYNTOKENS)
    YYFPRINTF (yyoutput, "token %s (", yytname[yytype]);
  else
    YYFPRINTF (yyoutput, "nterm %s (", yytname[yytype]);

  yy_symbol_value_print (yyoutput, yytype, yyvaluep);
  YYFPRINTF (yyoutput, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_stack_print (yytype_int16 *yybottom, yytype_int16 *yytop)
#else
static void
yy_stack_print (yybottom, yytop)
    yytype_int16 *yybottom;
    yytype_int16 *yytop;
#endif
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)				\
do {								\
  if (yydebug)							\
    yy_stack_print ((Bottom), (Top));				\
} while (YYID (0))


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yy_reduce_print (YYSTYPE *yyvsp, int yyrule)
#else
static void
yy_reduce_print (yyvsp, yyrule)
    YYSTYPE *yyvsp;
    int yyrule;
#endif
{
  int yynrhs = yyr2[yyrule];
  int yyi;
  unsigned long int yylno = yyrline[yyrule];
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %lu):\n",
	     yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
		       &(yyvsp[(yyi + 1) - (yynrhs)])
		       		       );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)		\
do {					\
  if (yydebug)				\
    yy_reduce_print (yyvsp, Rule); \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef	YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif



#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen strlen
#  else
/* Return the length of YYSTR.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static YYSIZE_T
yystrlen (const char *yystr)
#else
static YYSIZE_T
yystrlen (yystr)
    const char *yystr;
#endif
{
  YYSIZE_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static char *
yystpcpy (char *yydest, const char *yysrc)
#else
static char *
yystpcpy (yydest, yysrc)
    char *yydest;
    const char *yysrc;
#endif
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYSIZE_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYSIZE_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
	switch (*++yyp)
	  {
	  case '\'':
	  case ',':
	    goto do_not_strip_quotes;

	  case '\\':
	    if (*++yyp != '\\')
	      goto do_not_strip_quotes;
	    /* Fall through.  */
	  default:
	    if (yyres)
	      yyres[yyn] = *yyp;
	    yyn++;
	    break;

	  case '"':
	    if (yyres)
	      yyres[yyn] = '\0';
	    return yyn;
	  }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return yystrlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

/* Copy into YYRESULT an error message about the unexpected token
   YYCHAR while in state YYSTATE.  Return the number of bytes copied,
   including the terminating null byte.  If YYRESULT is null, do not
   copy anything; just return the number of bytes that would be
   copied.  As a special case, return 0 if an ordinary "syntax error"
   message will do.  Return YYSIZE_MAXIMUM if overflow occurs during
   size calculation.  */
static YYSIZE_T
yysyntax_error (char *yyresult, int yystate, int yychar)
{
  int yyn = yypact[yystate];

  if (! (YYPACT_NINF < yyn && yyn <= YYLAST))
    return 0;
  else
    {
      int yytype = YYTRANSLATE (yychar);
      YYSIZE_T yysize0 = yytnamerr (0, yytname[yytype]);
      YYSIZE_T yysize = yysize0;
      YYSIZE_T yysize1;
      int yysize_overflow = 0;
      enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
      char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
      int yyx;

# if 0
      /* This is so xgettext sees the translatable formats that are
	 constructed on the fly.  */
      YY_("syntax error, unexpected %s");
      YY_("syntax error, unexpected %s, expecting %s");
      YY_("syntax error, unexpected %s, expecting %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s");
      YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s");
# endif
      char *yyfmt;
      char const *yyf;
      static char const yyunexpected[] = "syntax error, unexpected %s";
      static char const yyexpecting[] = ", expecting %s";
      static char const yyor[] = " or %s";
      char yyformat[sizeof yyunexpected
		    + sizeof yyexpecting - 1
		    + ((YYERROR_VERBOSE_ARGS_MAXIMUM - 2)
		       * (sizeof yyor - 1))];
      char const *yyprefix = yyexpecting;

      /* Start YYX at -YYN if negative to avoid negative indexes in
	 YYCHECK.  */
      int yyxbegin = yyn < 0 ? -yyn : 0;

      /* Stay within bounds of both yycheck and yytname.  */
      int yychecklim = YYLAST - yyn + 1;
      int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
      int yycount = 1;

      yyarg[0] = yytname[yytype];
      yyfmt = yystpcpy (yyformat, yyunexpected);

      for (yyx = yyxbegin; yyx < yyxend; ++yyx)
	if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR)
	  {
	    if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
	      {
		yycount = 1;
		yysize = yysize0;
		yyformat[sizeof yyunexpected - 1] = '\0';
		break;
	      }
	    yyarg[yycount++] = yytname[yyx];
	    yysize1 = yysize + yytnamerr (0, yytname[yyx]);
	    yysize_overflow |= (yysize1 < yysize);
	    yysize = yysize1;
	    yyfmt = yystpcpy (yyfmt, yyprefix);
	    yyprefix = yyor;
	  }

      yyf = YY_(yyformat);
      yysize1 = yysize + yystrlen (yyf);
      yysize_overflow |= (yysize1 < yysize);
      yysize = yysize1;

      if (yysize_overflow)
	return YYSIZE_MAXIMUM;

      if (yyresult)
	{
	  /* Avoid sprintf, as that infringes on the user's name space.
	     Don't have undefined behavior even if the translation
	     produced a string with the wrong number of "%s"s.  */
	  char *yyp = yyresult;
	  int yyi = 0;
	  while ((*yyp = *yyf) != '\0')
	    {
	      if (*yyp == '%' && yyf[1] == 's' && yyi < yycount)
		{
		  yyp += yytnamerr (yyp, yyarg[yyi++]);
		  yyf += 2;
		}
	      else
		{
		  yyp++;
		  yyf++;
		}
	    }
	}
      return yysize;
    }
}
#endif /* YYERROR_VERBOSE */


/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
#else
static void
yydestruct (yymsg, yytype, yyvaluep)
    const char *yymsg;
    int yytype;
    YYSTYPE *yyvaluep;
#endif
{
  YYUSE (yyvaluep);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
	break;
    }
}

/* Prevent warnings from -Wmissing-prototypes.  */
#ifdef YYPARSE_PARAM
#if defined __STDC__ || defined __cplusplus
int yyparse (void *YYPARSE_PARAM);
#else
int yyparse ();
#endif
#else /* ! YYPARSE_PARAM */
#if defined __STDC__ || defined __cplusplus
int yyparse (void);
#else
int yyparse ();
#endif
#endif /* ! YYPARSE_PARAM */


/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;

/* Number of syntax errors so far.  */
int yynerrs;



/*-------------------------.
| yyparse or yypush_parse.  |
`-------------------------*/

#ifdef YYPARSE_PARAM
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void *YYPARSE_PARAM)
#else
int
yyparse (YYPARSE_PARAM)
    void *YYPARSE_PARAM;
#endif
#else /* ! YYPARSE_PARAM */
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
int
yyparse (void)
#else
int
yyparse ()

#endif
#endif
{


    int yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       `yyss': related to states.
       `yyvs': related to semantic values.

       Refer to the stacks thru separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yytype_int16 yyssa[YYINITDEPTH];
    yytype_int16 *yyss;
    yytype_int16 *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYSIZE_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYSIZE_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yytoken = 0;
  yyss = yyssa;
  yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */
  yyssp = yyss;
  yyvsp = yyvs;

  goto yysetstate;

/*------------------------------------------------------------.
| yynewstate -- Push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
 yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;

 yysetstate:
  *yyssp = yystate;

  if (yyss + yystacksize - 1 <= yyssp)
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYSIZE_T yysize = yyssp - yyss + 1;

#ifdef yyoverflow
      {
	/* Give user a chance to reallocate the stack.  Use copies of
	   these so that the &'s don't force the real ones into
	   memory.  */
	YYSTYPE *yyvs1 = yyvs;
	yytype_int16 *yyss1 = yyss;

	/* Each stack pointer address is followed by the size of the
	   data in use in that stack, in bytes.  This used to be a
	   conditional around just the two extra args, but that might
	   be undefined if yyoverflow is a macro.  */
	yyoverflow (YY_("memory exhausted"),
		    &yyss1, yysize * sizeof (*yyssp),
		    &yyvs1, yysize * sizeof (*yyvsp),
		    &yystacksize);

	yyss = yyss1;
	yyvs = yyvs1;
      }
#else /* no yyoverflow */
# ifndef YYSTACK_RELOCATE
      goto yyexhaustedlab;
# else
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
	goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
	yystacksize = YYMAXDEPTH;

      {
	yytype_int16 *yyss1 = yyss;
	union yyalloc *yyptr =
	  (union yyalloc *) YYSTACK_ALLOC (YYSTACK_BYTES (yystacksize));
	if (! yyptr)
	  goto yyexhaustedlab;
	YYSTACK_RELOCATE (yyss_alloc, yyss);
	YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
	if (yyss1 != yyssa)
	  YYSTACK_FREE (yyss1);
      }
# endif
#endif /* no yyoverflow */

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YYDPRINTF ((stderr, "Stack size increased to %lu\n",
		  (unsigned long int) yystacksize));

      if (yyss + yystacksize - 1 <= yyssp)
	YYABORT;
    }

  YYDPRINTF ((stderr, "Entering state %d\n", yystate));

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;

/*-----------.
| yybackup.  |
`-----------*/
yybackup:

  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yyn == YYPACT_NINF)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = YYLEX;
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yyn == 0 || yyn == YYTABLE_NINF)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);

  /* Discard the shifted token.  */
  yychar = YYEMPTY;

  yystate = yyn;
  *++yyvsp = yylval;

  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- Do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     `$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
        case 2:

/* Line 1455 of yacc.c  */
#line 18 "parser_test.y"
    { System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); ;}
    break;

  case 3:

/* Line 1455 of yacc.c  */
#line 21 "parser_test.y"
    { System.out.println("Declaracion de programa invalida, identificador invalido. Linea: " + al.getLine()); ;}
    break;

  case 4:

/* Line 1455 of yacc.c  */
#line 24 "parser_test.y"
    { System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); ;}
    break;

  case 5:

/* Line 1455 of yacc.c  */
#line 27 "parser_test.y"
    { System.out.println("Falta apertura del programa. Linea: " + al.getLine()); ;}
    break;

  case 6:

/* Line 1455 of yacc.c  */
#line 31 "parser_test.y"
    { System.out.println("Falta apertura del programa. Linea: " + al.getLine()); ;}
    break;

  case 12:

/* Line 1455 of yacc.c  */
#line 47 "parser_test.y"
    { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); ;}
    break;

  case 15:

/* Line 1455 of yacc.c  */
#line 50 "parser_test.y"
    { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); ;}
    break;

  case 17:

/* Line 1455 of yacc.c  */
#line 52 "parser_test.y"
    { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); ;}
    break;

  case 18:

/* Line 1455 of yacc.c  */
#line 53 "parser_test.y"
    { System.out.println("checkear valido"); ;}
    break;

  case 19:

/* Line 1455 of yacc.c  */
#line 54 "parser_test.y"
    { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); ;}
    break;

  case 21:

/* Line 1455 of yacc.c  */
#line 59 "parser_test.y"
    { System.out.println("Tipo invalido/ausente"); ;}
    break;

  case 22:

/* Line 1455 of yacc.c  */
#line 60 "parser_test.y"
    { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); ;}
    break;

  case 23:

/* Line 1455 of yacc.c  */
#line 61 "parser_test.y"
    { System.out.println("Tipo invalido/ausente"); ;}
    break;

  case 24:

/* Line 1455 of yacc.c  */
#line 67 "parser_test.y"
    { System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); ;}
    break;

  case 25:

/* Line 1455 of yacc.c  */
#line 70 "parser_test.y"
    { System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); ;}
    break;

  case 26:

/* Line 1455 of yacc.c  */
#line 73 "parser_test.y"
    { System.out.println("Tipo de funcion invalido/ausente. Linea: " + al.getLine()); ;}
    break;

  case 27:

/* Line 1455 of yacc.c  */
#line 76 "parser_test.y"
    { System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine()); ;}
    break;

  case 28:

/* Line 1455 of yacc.c  */
#line 79 "parser_test.y"
    { System.out.println("Declaracion de funcion vacia detectada. Linea: " + al.getLine());
         System.out.println("Declaracion de funcion sin parametros detectada. Linea: " + al.getLine()); ;}
    break;

  case 35:

/* Line 1455 of yacc.c  */
#line 98 "parser_test.y"
    { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); ;}
    break;

  case 38:

/* Line 1455 of yacc.c  */
#line 104 "parser_test.y"
    { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); ;}
    break;

  case 39:

/* Line 1455 of yacc.c  */
#line 108 "parser_test.y"
    { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); ;}
    break;

  case 40:

/* Line 1455 of yacc.c  */
#line 109 "parser_test.y"
    { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); ;}
    break;

  case 41:

/* Line 1455 of yacc.c  */
#line 110 "parser_test.y"
    { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); ;}
    break;

  case 42:

/* Line 1455 of yacc.c  */
#line 111 "parser_test.y"
    { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); ;}
    break;

  case 43:

/* Line 1455 of yacc.c  */
#line 112 "parser_test.y"
    { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); ;}
    break;

  case 44:

/* Line 1455 of yacc.c  */
#line 116 "parser_test.y"
    { System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); ;}
    break;

  case 45:

/* Line 1455 of yacc.c  */
#line 117 "parser_test.y"
    { System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); ;}
    break;

  case 46:

/* Line 1455 of yacc.c  */
#line 118 "parser_test.y"
    { System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); ;}
    break;

  case 47:

/* Line 1455 of yacc.c  */
#line 119 "parser_test.y"
    { System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); ;}
    break;

  case 48:

/* Line 1455 of yacc.c  */
#line 123 "parser_test.y"
    { System.out.println("Parametro real detectado. Linea: " + al.getLine()); ;}
    break;

  case 53:

/* Line 1455 of yacc.c  */
#line 138 "parser_test.y"
    { System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); ;}
    break;

  case 54:

/* Line 1455 of yacc.c  */
#line 140 "parser_test.y"
    { System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); ;}
    break;

  case 55:

/* Line 1455 of yacc.c  */
#line 144 "parser_test.y"
    { System.out.println("Condicion detectada. Linea: " + al.getLine()); ;}
    break;

  case 56:

/* Line 1455 of yacc.c  */
#line 149 "parser_test.y"
    { System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); ;}
    break;

  case 65:

/* Line 1455 of yacc.c  */
#line 167 "parser_test.y"
    { System.out.println("Asignacion detectada. Linea: " + al.getLine()); ;}
    break;

  case 66:

/* Line 1455 of yacc.c  */
#line 168 "parser_test.y"
    { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); ;}
    break;

  case 70:

/* Line 1455 of yacc.c  */
#line 175 "parser_test.y"
    {System.out.println("Trunc detectado. Linea: " + al.getLine()); ;}
    break;

  case 78:

/* Line 1455 of yacc.c  */
#line 189 "parser_test.y"
    { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); ;}
    break;

  case 79:

/* Line 1455 of yacc.c  */
#line 193 "parser_test.y"
    {System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());;}
    break;

  case 80:

/* Line 1455 of yacc.c  */
#line 194 "parser_test.y"
    { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); ;}
    break;

  case 81:

/* Line 1455 of yacc.c  */
#line 195 "parser_test.y"
    {System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());;}
    break;

  case 82:

/* Line 1455 of yacc.c  */
#line 196 "parser_test.y"
    { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); ;}
    break;

  case 83:

/* Line 1455 of yacc.c  */
#line 200 "parser_test.y"
    {System.out.println("Print detectado. Linea: " + al.getLine());;}
    break;

  case 84:

/* Line 1455 of yacc.c  */
#line 201 "parser_test.y"
    {System.out.println("Print detectado con expresion. Linea: " + al.getLine());;}
    break;

  case 85:

/* Line 1455 of yacc.c  */
#line 205 "parser_test.y"
    {System.out.println("Definicion lambda detectada. Linea: " + al.getLine());;}
    break;

  case 86:

/* Line 1455 of yacc.c  */
#line 209 "parser_test.y"
    {System.out.println("Retorno detectado. Linea: " + al.getLine());;}
    break;



/* Line 1455 of yacc.c  */
#line 1848 "parser_test.tab.c"
      default: break;
    }
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now `shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTOKENS] + *yyssp;
  if (0 <= yystate && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTOKENS];

  goto yynewstate;


/*------------------------------------.
| yyerrlab -- here on detecting error |
`------------------------------------*/
yyerrlab:
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
      {
	YYSIZE_T yysize = yysyntax_error (0, yystate, yychar);
	if (yymsg_alloc < yysize && yymsg_alloc < YYSTACK_ALLOC_MAXIMUM)
	  {
	    YYSIZE_T yyalloc = 2 * yysize;
	    if (! (yysize <= yyalloc && yyalloc <= YYSTACK_ALLOC_MAXIMUM))
	      yyalloc = YYSTACK_ALLOC_MAXIMUM;
	    if (yymsg != yymsgbuf)
	      YYSTACK_FREE (yymsg);
	    yymsg = (char *) YYSTACK_ALLOC (yyalloc);
	    if (yymsg)
	      yymsg_alloc = yyalloc;
	    else
	      {
		yymsg = yymsgbuf;
		yymsg_alloc = sizeof yymsgbuf;
	      }
	  }

	if (0 < yysize && yysize <= yymsg_alloc)
	  {
	    (void) yysyntax_error (yymsg, yystate, yychar);
	    yyerror (yymsg);
	  }
	else
	  {
	    yyerror (YY_("syntax error"));
	    if (yysize != 0)
	      goto yyexhaustedlab;
	  }
      }
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
	 error, discard it.  */

      if (yychar <= YYEOF)
	{
	  /* Return failure if at end of input.  */
	  if (yychar == YYEOF)
	    YYABORT;
	}
      else
	{
	  yydestruct ("Error: discarding",
		      yytoken, &yylval);
	  yychar = YYEMPTY;
	}
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:

  /* Pacify compilers like GCC when the user code never invokes
     YYERROR and the label yyerrorlab therefore never appears in user
     code.  */
  if (/*CONSTCOND*/ 0)
     goto yyerrorlab;

  /* Do not reclaim the symbols of the rule which action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;	/* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (yyn != YYPACT_NINF)
	{
	  yyn += YYTERROR;
	  if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
	    {
	      yyn = yytable[yyn];
	      if (0 < yyn)
		break;
	    }
	}

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
	YYABORT;


      yydestruct ("Error: popping",
		  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  *++yyvsp = yylval;


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;

/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;

#if !defined(yyoverflow) || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif

yyreturn:
  if (yychar != YYEMPTY)
     yydestruct ("Cleanup: discarding lookahead",
		 yytoken, &yylval);
  /* Do not reclaim the symbols of the rule which action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
		  yystos[*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  /* Make sure YYID is used.  */
  return YYID (yyresult);
}



/* Line 1675 of yacc.c  */
#line 214 "parser_test.y"

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

