/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison implementation for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

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

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output, and Bison version.  */
#define YYBISON 30802

/* Bison version string.  */
#define YYBISON_VERSION "3.8.2"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* First part of user prologue.  */
#line 5 "parser_test.y"

    import Compilador.Lexer.AnalizadorLexico;
    import Compilador.Lexer.TokenNames;

#line 76 "parser_test.tab.c"

# ifndef YY_CAST
#  ifdef __cplusplus
#   define YY_CAST(Type, Val) static_cast<Type> (Val)
#   define YY_REINTERPRET_CAST(Type, Val) reinterpret_cast<Type> (Val)
#  else
#   define YY_CAST(Type, Val) ((Type) (Val))
#   define YY_REINTERPRET_CAST(Type, Val) ((Type) (Val))
#  endif
# endif
# ifndef YY_NULLPTR
#  if defined __cplusplus
#   if 201103L <= __cplusplus
#    define YY_NULLPTR nullptr
#   else
#    define YY_NULLPTR 0
#   endif
#  else
#   define YY_NULLPTR ((void*)0)
#  endif
# endif


/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    ID = 258,                      /* ID  */
    CTEL = 259,                    /* CTEL  */
    IF = 260,                      /* IF  */
    ELSE = 261,                    /* ELSE  */
    ENDIF = 262,                   /* ENDIF  */
    PRINT = 263,                   /* PRINT  */
    RETURN = 264,                  /* RETURN  */
    LAMBDA = 265,                  /* LAMBDA  */
    ASIGNAR = 266,                 /* ASIGNAR  */
    MENORIGUAL = 267,              /* MENORIGUAL  */
    MAYORIGUAL = 268,              /* MAYORIGUAL  */
    IGUALIGUAL = 269,              /* IGUALIGUAL  */
    DISTINTO = 270,                /* DISTINTO  */
    FLECHA = 271,                  /* FLECHA  */
    LONG = 272,                    /* LONG  */
    DO = 273,                      /* DO  */
    UNTIL = 274,                   /* UNTIL  */
    TRUNC = 275,                   /* TRUNC  */
    CR = 276,                      /* CR  */
    STRING = 277,                  /* STRING  */
    CTEF = 278,                    /* CTEF  */
    IDCOMP = 279                   /* IDCOMP  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);



/* Symbol kind.  */
enum yysymbol_kind_t
{
  YYSYMBOL_YYEMPTY = -2,
  YYSYMBOL_YYEOF = 0,                      /* "end of file"  */
  YYSYMBOL_YYerror = 1,                    /* error  */
  YYSYMBOL_YYUNDEF = 2,                    /* "invalid token"  */
  YYSYMBOL_ID = 3,                         /* ID  */
  YYSYMBOL_CTEL = 4,                       /* CTEL  */
  YYSYMBOL_IF = 5,                         /* IF  */
  YYSYMBOL_ELSE = 6,                       /* ELSE  */
  YYSYMBOL_ENDIF = 7,                      /* ENDIF  */
  YYSYMBOL_PRINT = 8,                      /* PRINT  */
  YYSYMBOL_RETURN = 9,                     /* RETURN  */
  YYSYMBOL_LAMBDA = 10,                    /* LAMBDA  */
  YYSYMBOL_ASIGNAR = 11,                   /* ASIGNAR  */
  YYSYMBOL_MENORIGUAL = 12,                /* MENORIGUAL  */
  YYSYMBOL_MAYORIGUAL = 13,                /* MAYORIGUAL  */
  YYSYMBOL_IGUALIGUAL = 14,                /* IGUALIGUAL  */
  YYSYMBOL_DISTINTO = 15,                  /* DISTINTO  */
  YYSYMBOL_FLECHA = 16,                    /* FLECHA  */
  YYSYMBOL_LONG = 17,                      /* LONG  */
  YYSYMBOL_DO = 18,                        /* DO  */
  YYSYMBOL_UNTIL = 19,                     /* UNTIL  */
  YYSYMBOL_TRUNC = 20,                     /* TRUNC  */
  YYSYMBOL_CR = 21,                        /* CR  */
  YYSYMBOL_STRING = 22,                    /* STRING  */
  YYSYMBOL_CTEF = 23,                      /* CTEF  */
  YYSYMBOL_IDCOMP = 24,                    /* IDCOMP  */
  YYSYMBOL_25_ = 25,                       /* '{'  */
  YYSYMBOL_26_ = 26,                       /* '}'  */
  YYSYMBOL_27_ = 27,                       /* ';'  */
  YYSYMBOL_28_ = 28,                       /* '('  */
  YYSYMBOL_29_ = 29,                       /* ')'  */
  YYSYMBOL_30_ = 30,                       /* ','  */
  YYSYMBOL_31_ = 31,                       /* '>'  */
  YYSYMBOL_32_ = 32,                       /* '<'  */
  YYSYMBOL_33_ = 33,                       /* '+'  */
  YYSYMBOL_34_ = 34,                       /* '-'  */
  YYSYMBOL_35_ = 35,                       /* '*'  */
  YYSYMBOL_36_ = 36,                       /* '/'  */
  YYSYMBOL_37_ = 37,                       /* '='  */
  YYSYMBOL_YYACCEPT = 38,                  /* $accept  */
  YYSYMBOL_programa = 39,                  /* programa  */
  YYSYMBOL_sentencia = 40,                 /* sentencia  */
  YYSYMBOL_sentencia_declarativa = 41,     /* sentencia_declarativa  */
  YYSYMBOL_sentencia_ejecutable = 42,      /* sentencia_ejecutable  */
  YYSYMBOL_declaracion_funcion = 43,       /* declaracion_funcion  */
  YYSYMBOL_parametros_formales_opt = 44,   /* parametros_formales_opt  */
  YYSYMBOL_cuerpo_funcion_opt = 45,        /* cuerpo_funcion_opt  */
  YYSYMBOL_lista_sentencias = 46,          /* lista_sentencias  */
  YYSYMBOL_lista_sentencias_ejecutables = 47, /* lista_sentencias_ejecutables  */
  YYSYMBOL_lista_parametros_formales = 48, /* lista_parametros_formales  */
  YYSYMBOL_lista_parametros_reales = 49,   /* lista_parametros_reales  */
  YYSYMBOL_declaracion_variable = 50,      /* declaracion_variable  */
  YYSYMBOL_lista_identificadores = 51,     /* lista_identificadores  */
  YYSYMBOL_parametro_formal = 52,          /* parametro_formal  */
  YYSYMBOL_parametro_real_compuesto = 53,  /* parametro_real_compuesto  */
  YYSYMBOL_parametro_real = 54,            /* parametro_real  */
  YYSYMBOL_control = 55,                   /* control  */
  YYSYMBOL_sentencia_IF = 56,              /* sentencia_IF  */
  YYSYMBOL_condicion = 57,                 /* condicion  */
  YYSYMBOL_do_until = 58,                  /* do_until  */
  YYSYMBOL_comparador = 59,                /* comparador  */
  YYSYMBOL_tipo = 60,                      /* tipo  */
  YYSYMBOL_asignacion = 61,                /* asignacion  */
  YYSYMBOL_expresion = 62,                 /* expresion  */
  YYSYMBOL_termino = 63,                   /* termino  */
  YYSYMBOL_factor = 64,                    /* factor  */
  YYSYMBOL_llamada_funcion = 65,           /* llamada_funcion  */
  YYSYMBOL_print = 66,                     /* print  */
  YYSYMBOL_lambda = 67,                    /* lambda  */
  YYSYMBOL_retorno = 68,                   /* retorno  */
  YYSYMBOL_asignacion_multiple = 69,       /* asignacion_multiple  */
  YYSYMBOL_ids = 70,                       /* ids  */
  YYSYMBOL_lista_constantes = 71,          /* lista_constantes  */
  YYSYMBOL_constante = 72                  /* constante  */
};
typedef enum yysymbol_kind_t yysymbol_kind_t;




#ifdef short
# undef short
#endif

/* On compilers that do not define __PTRDIFF_MAX__ etc., make sure
   <limits.h> and (if available) <stdint.h> are included
   so that the code can choose integer types of a good width.  */

#ifndef __PTRDIFF_MAX__
# include <limits.h> /* INFRINGES ON USER NAME SPACE */
# if defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stdint.h> /* INFRINGES ON USER NAME SPACE */
#  define YY_STDINT_H
# endif
#endif

/* Narrow types that promote to a signed type and that can represent a
   signed or unsigned integer of at least N bits.  In tables they can
   save space and decrease cache pressure.  Promoting to a signed type
   helps avoid bugs in integer arithmetic.  */

#ifdef __INT_LEAST8_MAX__
typedef __INT_LEAST8_TYPE__ yytype_int8;
#elif defined YY_STDINT_H
typedef int_least8_t yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef __INT_LEAST16_MAX__
typedef __INT_LEAST16_TYPE__ yytype_int16;
#elif defined YY_STDINT_H
typedef int_least16_t yytype_int16;
#else
typedef short yytype_int16;
#endif

/* Work around bug in HP-UX 11.23, which defines these macros
   incorrectly for preprocessor constants.  This workaround can likely
   be removed in 2023, as HPE has promised support for HP-UX 11.23
   (aka HP-UX 11i v2) only through the end of 2022; see Table 2 of
   <https://h20195.www2.hpe.com/V2/getpdf.aspx/4AA4-7673ENW.pdf>.  */
#ifdef __hpux
# undef UINT_LEAST8_MAX
# undef UINT_LEAST16_MAX
# define UINT_LEAST8_MAX 255
# define UINT_LEAST16_MAX 65535
#endif

#if defined __UINT_LEAST8_MAX__ && __UINT_LEAST8_MAX__ <= __INT_MAX__
typedef __UINT_LEAST8_TYPE__ yytype_uint8;
#elif (!defined __UINT_LEAST8_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST8_MAX <= INT_MAX)
typedef uint_least8_t yytype_uint8;
#elif !defined __UINT_LEAST8_MAX__ && UCHAR_MAX <= INT_MAX
typedef unsigned char yytype_uint8;
#else
typedef short yytype_uint8;
#endif

#if defined __UINT_LEAST16_MAX__ && __UINT_LEAST16_MAX__ <= __INT_MAX__
typedef __UINT_LEAST16_TYPE__ yytype_uint16;
#elif (!defined __UINT_LEAST16_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST16_MAX <= INT_MAX)
typedef uint_least16_t yytype_uint16;
#elif !defined __UINT_LEAST16_MAX__ && USHRT_MAX <= INT_MAX
typedef unsigned short yytype_uint16;
#else
typedef int yytype_uint16;
#endif

#ifndef YYPTRDIFF_T
# if defined __PTRDIFF_TYPE__ && defined __PTRDIFF_MAX__
#  define YYPTRDIFF_T __PTRDIFF_TYPE__
#  define YYPTRDIFF_MAXIMUM __PTRDIFF_MAX__
# elif defined PTRDIFF_MAX
#  ifndef ptrdiff_t
#   include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  endif
#  define YYPTRDIFF_T ptrdiff_t
#  define YYPTRDIFF_MAXIMUM PTRDIFF_MAX
# else
#  define YYPTRDIFF_T long
#  define YYPTRDIFF_MAXIMUM LONG_MAX
# endif
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned
# endif
#endif

#define YYSIZE_MAXIMUM                                  \
  YY_CAST (YYPTRDIFF_T,                                 \
           (YYPTRDIFF_MAXIMUM < YY_CAST (YYSIZE_T, -1)  \
            ? YYPTRDIFF_MAXIMUM                         \
            : YY_CAST (YYSIZE_T, -1)))

#define YYSIZEOF(X) YY_CAST (YYPTRDIFF_T, sizeof (X))


/* Stored state numbers (used for stacks). */
typedef yytype_uint8 yy_state_t;

/* State numbers in computations.  */
typedef int yy_state_fast_t;

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif


#ifndef YY_ATTRIBUTE_PURE
# if defined __GNUC__ && 2 < __GNUC__ + (96 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_PURE __attribute__ ((__pure__))
# else
#  define YY_ATTRIBUTE_PURE
# endif
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# if defined __GNUC__ && 2 < __GNUC__ + (7 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_UNUSED __attribute__ ((__unused__))
# else
#  define YY_ATTRIBUTE_UNUSED
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YY_USE(E) ((void) (E))
#else
# define YY_USE(E) /* empty */
#endif

/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
#if defined __GNUC__ && ! defined __ICC && 406 <= __GNUC__ * 100 + __GNUC_MINOR__
# if __GNUC__ * 100 + __GNUC_MINOR__ < 407
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")
# else
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")              \
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# endif
# define YY_IGNORE_MAYBE_UNINITIALIZED_END      \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

#if defined __cplusplus && defined __GNUC__ && ! defined __ICC && 6 <= __GNUC__
# define YY_IGNORE_USELESS_CAST_BEGIN                          \
    _Pragma ("GCC diagnostic push")                            \
    _Pragma ("GCC diagnostic ignored \"-Wuseless-cast\"")
# define YY_IGNORE_USELESS_CAST_END            \
    _Pragma ("GCC diagnostic pop")
#endif
#ifndef YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_END
#endif


#define YY_ASSERT(E) ((void) (0 && (E)))

#if !defined yyoverflow

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
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
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
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* !defined yyoverflow */

#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yy_state_t yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (YYSIZEOF (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (YYSIZEOF (yy_state_t) + YYSIZEOF (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYPTRDIFF_T yynewbytes;                                         \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * YYSIZEOF (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / YYSIZEOF (*yyptr);                        \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, YY_CAST (YYSIZE_T, (Count)) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYPTRDIFF_T yyi;                      \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  34
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   392

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  38
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  35
/* YYNRULES -- Number of rules.  */
#define YYNRULES  102
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  190

/* YYMAXUTOK -- Last valid token kind.  */
#define YYMAXUTOK   279


/* YYTRANSLATE(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, with out-of-bounds checking.  */
#define YYTRANSLATE(YYX)                                \
  (0 <= (YYX) && (YYX) <= YYMAXUTOK                     \
   ? YY_CAST (yysymbol_kind_t, yytranslate[YYX])        \
   : YYSYMBOL_YYUNDEF)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex.  */
static const yytype_int8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
      28,    29,    35,    33,    30,    34,     2,    36,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,    27,
      32,    37,    31,     2,     2,     2,     2,     2,     2,     2,
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
/* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_uint8 yyrline[] =
{
       0,    16,    16,    19,    22,    25,    29,    32,    36,    37,
      38,    42,    43,    44,    48,    49,    50,    51,    52,    53,
      58,    60,    62,    67,    68,    72,    73,    78,    79,    83,
      84,    88,    89,    90,    94,    95,    96,   100,   101,   102,
     106,   107,   108,   109,   110,   111,   115,   116,   117,   118,
     119,   120,   121,   122,   126,   130,   131,   135,   136,   140,
     142,   147,   151,   156,   157,   158,   159,   160,   161,   165,
     166,   170,   171,   175,   176,   177,   178,   182,   183,   184,
     188,   189,   190,   191,   192,   196,   197,   198,   199,   203,
     204,   208,   212,   216,   217,   218,   219,   223,   224,   228,
     229,   233,   234
};
#endif

/** Accessing symbol of state STATE.  */
#define YY_ACCESSING_SYMBOL(State) YY_CAST (yysymbol_kind_t, yystos[State])

#if YYDEBUG || 0
/* The user-facing name of the symbol whose (internal) number is
   YYSYMBOL.  No bounds checking.  */
static const char *yysymbol_name (yysymbol_kind_t yysymbol) YY_ATTRIBUTE_UNUSED;

/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "\"end of file\"", "error", "\"invalid token\"", "ID", "CTEL", "IF",
  "ELSE", "ENDIF", "PRINT", "RETURN", "LAMBDA", "ASIGNAR", "MENORIGUAL",
  "MAYORIGUAL", "IGUALIGUAL", "DISTINTO", "FLECHA", "LONG", "DO", "UNTIL",
  "TRUNC", "CR", "STRING", "CTEF", "IDCOMP", "'{'", "'}'", "';'", "'('",
  "')'", "','", "'>'", "'<'", "'+'", "'-'", "'*'", "'/'", "'='", "$accept",
  "programa", "sentencia", "sentencia_declarativa", "sentencia_ejecutable",
  "declaracion_funcion", "parametros_formales_opt", "cuerpo_funcion_opt",
  "lista_sentencias", "lista_sentencias_ejecutables",
  "lista_parametros_formales", "lista_parametros_reales",
  "declaracion_variable", "lista_identificadores", "parametro_formal",
  "parametro_real_compuesto", "parametro_real", "control", "sentencia_IF",
  "condicion", "do_until", "comparador", "tipo", "asignacion", "expresion",
  "termino", "factor", "llamada_funcion", "print", "lambda", "retorno",
  "asignacion_multiple", "ids", "lista_constantes", "constante", YY_NULLPTR
};

static const char *
yysymbol_name (yysymbol_kind_t yysymbol)
{
  return yytname[yysymbol];
}
#endif

#define YYPACT_NINF (-148)

#define yypact_value_is_default(Yyn) \
  ((Yyn) == YYPACT_NINF)

#define YYTABLE_NINF (-92)

#define yytable_value_is_error(Yyn) \
  0

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
static const yytype_int16 yypact[] =
{
      23,    15,   309,   320,    37,   320,    33,    10,    42,    71,
     131,  -148,    53,  -148,   110,   320,  -148,  -148,   101,  -148,
     144,   124,    41,  -148,  -148,  -148,    74,  -148,  -148,  -148,
    -148,  -148,   126,   210,  -148,   234,   170,   192,  -148,   135,
     285,   154,   187,   285,   113,   285,   350,   285,    90,   184,
    -148,  -148,  -148,  -148,   185,    40,   111,   192,   204,   187,
    -148,   196,    68,  -148,  -148,    88,   194,  -148,   195,  -148,
     201,    -6,   155,  -148,  -148,    32,   221,   370,    12,   205,
      44,    46,  -148,  -148,   215,   235,    -6,  -148,   245,  -148,
     211,   351,   212,    67,    96,   220,   132,  -148,   267,    -6,
    -148,    50,  -148,  -148,  -148,  -148,   187,  -148,  -148,  -148,
    -148,  -148,  -148,  -148,   217,  -148,   123,   217,   176,   285,
     100,   100,   100,   100,  -148,  -148,   242,   246,   226,   369,
    -148,   369,   296,  -148,   296,   369,  -148,  -148,   256,   241,
    -148,  -148,  -148,  -148,  -148,  -148,   285,  -148,  -148,  -148,
    -148,   258,  -148,  -148,  -148,   123,   217,  -148,   121,   155,
     155,  -148,  -148,  -148,  -148,   259,  -148,  -148,  -148,  -148,
    -148,  -148,   350,    -6,   273,  -148,  -148,   350,   278,   285,
     338,   188,   266,   281,  -148,  -148,   350,   289,   304,  -148
};

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
static const yytype_int8 yydefact[] =
{
       0,     0,     0,     0,     0,     0,     0,    43,     0,     0,
       0,    69,     0,    70,    97,     0,    27,     8,    10,    13,
       0,    12,     0,    15,    57,    58,     0,    14,    16,    17,
      18,    19,     0,     0,     1,     0,     0,    97,    39,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       9,     5,    28,    11,     0,     0,    44,    40,     0,     0,
      38,     0,     0,     4,     3,     0,    84,    81,     0,    82,
      80,    72,    75,    79,    83,    84,     0,    49,     0,    88,
       0,     0,    31,    34,     0,     0,    55,    56,     0,    21,
       0,     0,     0,     0,     0,     0,     0,    29,     0,    71,
      87,     0,     2,    42,    45,    41,     0,    51,    24,    22,
      98,    95,   101,   102,    93,   100,     0,    94,     0,     0,
       0,     0,     0,     0,    52,    47,     0,    48,     0,     0,
      23,     0,     0,    86,     0,     0,    50,    26,     0,     0,
      63,    64,    65,    66,    67,    68,     0,    89,    90,    92,
      97,     0,    30,    85,    20,     0,    96,    88,     0,    73,
      74,    77,    78,    53,    46,     0,    33,    32,    36,    35,
      54,    25,     0,    61,     0,    99,    76,     0,     0,     0,
       0,     0,     0,     0,    59,    62,     0,     0,     0,    60
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int16 yypgoto[] =
{
    -148,  -148,   -18,  -148,   -46,  -148,    -3,   -40,    17,  -147,
    -148,   255,  -148,  -148,    38,    54,  -148,  -148,  -148,   143,
    -148,  -148,   -23,    81,   -31,    77,    94,   -37,  -148,  -148,
    -148,  -148,    -5,   -60,   175
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_uint8 yydefgoto[] =
{
       0,     4,    16,    17,    18,    19,    42,    89,    20,    98,
      80,    81,    21,    22,    82,    83,    84,    23,    24,    90,
      25,   146,    26,    27,    86,    72,    73,    28,    29,    87,
      30,    31,    32,   114,   115
};

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int16 yytable[] =
{
      97,    39,    52,    74,    74,   117,    74,    74,    74,    71,
      74,    74,    91,    93,    94,    52,    99,    52,    85,   109,
      33,    40,    35,    59,     1,   178,     2,   120,   121,    11,
     180,    52,    49,   -51,    13,    85,    36,    34,    41,   187,
       5,   -37,    54,   104,   -37,   129,   -37,   132,     3,   -37,
     -37,   132,   152,   106,   127,   128,   156,    37,   -37,   -37,
     118,   -51,   -51,   -37,   105,   -37,   154,   -37,   -37,   111,
      43,    55,   112,   130,   131,   133,   134,    56,    46,   153,
     134,    74,    74,    74,    74,    74,    74,    38,   158,   116,
      39,   113,   112,    66,    67,    74,   148,    74,    57,    44,
     120,   121,    58,    66,    67,   138,    85,    60,    85,    74,
      68,   113,    85,    69,    70,   173,    66,    67,    78,   100,
      52,    47,    40,    69,    70,   149,    97,   112,    50,   120,
     121,    97,   152,    68,   152,    92,    69,    70,    48,    58,
      97,   152,    74,    40,    -7,     6,   113,     7,    91,     8,
     176,    53,     9,    10,   120,   121,    61,    75,    67,    45,
     118,    11,    12,    62,    76,    61,    13,   166,    14,   167,
      51,    11,    65,   170,    68,    77,    13,    69,    70,    66,
      67,    40,    78,    79,    -6,     6,   168,     7,   169,     8,
     122,   123,     9,    10,   183,   184,    68,   159,   160,    69,
      70,    11,    12,    47,    78,   157,    13,   107,    14,   103,
     102,     6,    88,     7,    76,     8,   161,   162,     9,    10,
     110,    11,   118,   119,   124,    77,    13,    11,    12,    48,
     -24,   135,    13,   108,    14,     6,    63,     7,   136,     8,
     139,   147,     9,    10,   150,   163,     6,   155,     7,   164,
       8,    11,    12,     9,    10,   165,    13,     6,    14,     7,
      64,     8,    11,    12,     9,    10,   172,    13,    95,    14,
      96,   137,     8,    11,    12,     9,    10,   174,    13,    95,
      14,    96,   171,     8,   177,    12,     9,    10,    66,    67,
      95,    14,    96,   151,     8,   185,    12,     9,    10,    66,
      67,   179,    14,   101,   181,    68,   186,    12,    69,    70,
       6,   189,     7,    14,     8,   188,    68,     9,    10,    69,
      70,     6,   182,     7,    78,     8,    11,    12,     9,    10,
     175,    13,     0,    14,    15,     0,     0,    11,    12,    95,
       0,    96,    13,     8,    14,     0,     9,    10,     0,     0,
       0,    95,     0,    96,   -91,     8,    12,     0,     9,    10,
       0,     0,    14,   140,   141,   142,   143,     0,    12,     0,
       0,     0,   107,   125,    14,     0,     0,     0,     0,    76,
     126,     0,   144,   145,   120,   121,    11,    11,     0,     0,
      77,    13,    13
};

static const yytype_int16 yycheck[] =
{
      46,     6,    20,    40,    41,    65,    43,    44,    45,    40,
      47,    48,    43,    44,    45,    33,    47,    35,    41,    59,
       3,    11,     5,    26,     1,   172,     3,    33,    34,    17,
     177,    49,    15,     1,    22,    58,     3,     0,    28,   186,
      25,     0,     1,     3,     3,     1,     5,     1,    25,     8,
       9,     1,    98,    56,    77,    78,   116,    24,    17,    18,
      28,    29,    30,    22,    24,    24,   106,    26,    27,     1,
      28,    30,     4,    29,    30,    29,    30,     3,    25,    29,
      30,   118,   119,   120,   121,   122,   123,     6,   119,     1,
      95,    23,     4,     3,     4,   132,    29,   134,    24,    28,
      33,    34,    28,     3,     4,    88,   129,    26,   131,   146,
      20,    23,   135,    23,    24,   146,     3,     4,    28,    29,
     138,    11,    11,    23,    24,    29,   172,     4,    27,    33,
      34,   177,   178,    20,   180,    22,    23,    24,    28,    28,
     186,   187,   179,    11,     0,     1,    23,     3,   179,     5,
      29,    27,     8,     9,    33,    34,    30,     3,     4,    28,
      28,    17,    18,    37,    10,    30,    22,   129,    24,   131,
      26,    17,    37,   135,    20,    21,    22,    23,    24,     3,
       4,    11,    28,    29,     0,     1,   132,     3,   134,     5,
      35,    36,     8,     9,     6,     7,    20,   120,   121,    23,
      24,    17,    18,    11,    28,    29,    22,     3,    24,    24,
      26,     1,    25,     3,    10,     5,   122,   123,     8,     9,
      24,    17,    28,    28,     3,    21,    22,    17,    18,    28,
      25,    16,    22,    29,    24,     1,    26,     3,     3,     5,
      29,    29,     8,     9,    24,     3,     1,    30,     3,     3,
       5,    17,    18,     8,     9,    29,    22,     1,    24,     3,
      26,     5,    17,    18,     8,     9,    25,    22,     1,    24,
       3,    26,     5,    17,    18,     8,     9,    19,    22,     1,
      24,     3,    26,     5,    25,    18,     8,     9,     3,     4,
       1,    24,     3,    26,     5,    29,    18,     8,     9,     3,
       4,    28,    24,    48,    26,    20,    25,    18,    23,    24,
       1,     7,     3,    24,     5,    26,    20,     8,     9,    23,
      24,     1,   179,     3,    28,     5,    17,    18,     8,     9,
     155,    22,    -1,    24,    25,    -1,    -1,    17,    18,     1,
      -1,     3,    22,     5,    24,    -1,     8,     9,    -1,    -1,
      -1,     1,    -1,     3,    16,     5,    18,    -1,     8,     9,
      -1,    -1,    24,    12,    13,    14,    15,    -1,    18,    -1,
      -1,    -1,     3,     3,    24,    -1,    -1,    -1,    -1,    10,
      10,    -1,    31,    32,    33,    34,    17,    17,    -1,    -1,
      21,    22,    22
};

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
static const yytype_int8 yystos[] =
{
       0,     1,     3,    25,    39,    25,     1,     3,     5,     8,
       9,    17,    18,    22,    24,    25,    40,    41,    42,    43,
      46,    50,    51,    55,    56,    58,    60,    61,    65,    66,
      68,    69,    70,    46,     0,    46,     3,    24,    61,    70,
      11,    28,    44,    28,    28,    28,    25,    11,    28,    46,
      27,    26,    40,    27,     1,    30,     3,    24,    28,    44,
      61,    30,    37,    26,    26,    37,     3,     4,    20,    23,
      24,    62,    63,    64,    65,     3,    10,    21,    28,    29,
      48,    49,    52,    53,    54,    60,    62,    67,    25,    45,
      57,    62,    22,    62,    62,     1,     3,    42,    47,    62,
      29,    49,    26,    24,     3,    24,    44,     3,    29,    45,
      24,     1,     4,    23,    71,    72,     1,    71,    28,    28,
      33,    34,    35,    36,     3,     3,    10,    60,    60,     1,
      29,    30,     1,    29,    30,    16,     3,    26,    46,    29,
      12,    13,    14,    15,    31,    32,    59,    29,    29,    29,
      24,    26,    42,    29,    45,    30,    71,    29,    62,    63,
      63,    64,    64,     3,     3,    29,    52,    52,    53,    53,
      52,    26,    25,    62,    19,    72,    29,    25,    47,    28,
      47,    26,    57,     6,     7,    29,    25,    47,    26,     7
};

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr1[] =
{
       0,    38,    39,    39,    39,    39,    39,    39,    40,    40,
      40,    41,    41,    41,    42,    42,    42,    42,    42,    42,
      43,    43,    43,    44,    44,    45,    45,    46,    46,    47,
      47,    48,    48,    48,    49,    49,    49,    50,    50,    50,
      51,    51,    51,    51,    51,    51,    52,    52,    52,    52,
      52,    52,    52,    52,    53,    54,    54,    55,    55,    56,
      56,    57,    58,    59,    59,    59,    59,    59,    59,    60,
      60,    61,    61,    62,    62,    62,    62,    63,    63,    63,
      64,    64,    64,    64,    64,    65,    65,    65,    65,    66,
      66,    67,    68,    69,    69,    69,    69,    70,    70,    71,
      71,    72,    72
};

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr2[] =
{
       0,     2,     4,     4,     3,     3,     3,     2,     1,     2,
       1,     2,     1,     1,     1,     1,     1,     1,     1,     1,
       4,     3,     3,     3,     2,     3,     2,     1,     2,     1,
       2,     1,     3,     3,     1,     3,     3,     1,     2,     2,
       2,     3,     3,     1,     2,     3,     3,     2,     2,     1,
       2,     1,     2,     3,     3,     1,     1,     1,     1,     8,
      12,     3,     8,     1,     1,     1,     1,     1,     1,     1,
       1,     3,     3,     3,     3,     1,     4,     3,     3,     1,
       1,     1,     1,     1,     1,     4,     4,     3,     3,     4,
       4,     5,     4,     3,     4,     3,     5,     1,     3,     3,
       1,     1,     1
};


enum { YYENOMEM = -2 };

#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab
#define YYNOMEM         goto yyexhaustedlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                    \
  do                                                              \
    if (yychar == YYEMPTY)                                        \
      {                                                           \
        yychar = (Token);                                         \
        yylval = (Value);                                         \
        YYPOPSTACK (yylen);                                       \
        yystate = *yyssp;                                         \
        goto yybackup;                                            \
      }                                                           \
    else                                                          \
      {                                                           \
        yyerror (YY_("syntax error: cannot back up")); \
        YYERROR;                                                  \
      }                                                           \
  while (0)

/* Backward compatibility with an undocumented macro.
   Use YYerror or YYUNDEF. */
#define YYERRCODE YYUNDEF


/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)




# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Kind, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*-----------------------------------.
| Print this symbol's value on YYO.  |
`-----------------------------------*/

static void
yy_symbol_value_print (FILE *yyo,
                       yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep)
{
  FILE *yyoutput = yyo;
  YY_USE (yyoutput);
  if (!yyvaluep)
    return;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/*---------------------------.
| Print this symbol on YYO.  |
`---------------------------*/

static void
yy_symbol_print (FILE *yyo,
                 yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyo, "%s %s (",
             yykind < YYNTOKENS ? "token" : "nterm", yysymbol_name (yykind));

  yy_symbol_value_print (yyo, yykind, yyvaluep);
  YYFPRINTF (yyo, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yy_state_t *yybottom, yy_state_t *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yy_state_t *yyssp, YYSTYPE *yyvsp,
                 int yyrule)
{
  int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %d):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       YY_ACCESSING_SYMBOL (+yyssp[yyi + 1 - yynrhs]),
                       &yyvsp[(yyi + 1) - (yynrhs)]);
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args) ((void) 0)
# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
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






/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg,
            yysymbol_kind_t yykind, YYSTYPE *yyvaluep)
{
  YY_USE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yykind, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/* Lookahead token kind.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;




/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    yy_state_fast_t yystate = 0;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus = 0;

    /* Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* Their size.  */
    YYPTRDIFF_T yystacksize = YYINITDEPTH;

    /* The state stack: array, bottom, top.  */
    yy_state_t yyssa[YYINITDEPTH];
    yy_state_t *yyss = yyssa;
    yy_state_t *yyssp = yyss;

    /* The semantic value stack: array, bottom, top.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs = yyvsa;
    YYSTYPE *yyvsp = yyvs;

  int yyn;
  /* The return value of yyparse.  */
  int yyresult;
  /* Lookahead symbol kind.  */
  yysymbol_kind_t yytoken = YYSYMBOL_YYEMPTY;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;



#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yychar = YYEMPTY; /* Cause a token to be read.  */

  goto yysetstate;


/*------------------------------------------------------------.
| yynewstate -- push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;


/*--------------------------------------------------------------------.
| yysetstate -- set current state (the top of the stack) to yystate.  |
`--------------------------------------------------------------------*/
yysetstate:
  YYDPRINTF ((stderr, "Entering state %d\n", yystate));
  YY_ASSERT (0 <= yystate && yystate < YYNSTATES);
  YY_IGNORE_USELESS_CAST_BEGIN
  *yyssp = YY_CAST (yy_state_t, yystate);
  YY_IGNORE_USELESS_CAST_END
  YY_STACK_PRINT (yyss, yyssp);

  if (yyss + yystacksize - 1 <= yyssp)
#if !defined yyoverflow && !defined YYSTACK_RELOCATE
    YYNOMEM;
#else
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYPTRDIFF_T yysize = yyssp - yyss + 1;

# if defined yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        yy_state_t *yyss1 = yyss;
        YYSTYPE *yyvs1 = yyvs;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * YYSIZEOF (*yyssp),
                    &yyvs1, yysize * YYSIZEOF (*yyvsp),
                    &yystacksize);
        yyss = yyss1;
        yyvs = yyvs1;
      }
# else /* defined YYSTACK_RELOCATE */
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        YYNOMEM;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yy_state_t *yyss1 = yyss;
        union yyalloc *yyptr =
          YY_CAST (union yyalloc *,
                   YYSTACK_ALLOC (YY_CAST (YYSIZE_T, YYSTACK_BYTES (yystacksize))));
        if (! yyptr)
          YYNOMEM;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YY_IGNORE_USELESS_CAST_BEGIN
      YYDPRINTF ((stderr, "Stack size increased to %ld\n",
                  YY_CAST (long, yystacksize)));
      YY_IGNORE_USELESS_CAST_END

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }
#endif /* !defined yyoverflow && !defined YYSTACK_RELOCATE */


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
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either empty, or end-of-input, or a valid lookahead.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token\n"));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = YYEOF;
      yytoken = YYSYMBOL_YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else if (yychar == YYerror)
    {
      /* The scanner already issued an error message, process directly
         to error recovery.  But do not keep the error token as
         lookahead, it is too special and may lead us to an endless
         loop in error recovery. */
      yychar = YYUNDEF;
      yytoken = YYSYMBOL_YYerror;
      goto yyerrlab1;
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
      if (yytable_value_is_error (yyn))
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
  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
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
| yyreduce -- do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
  case 2: /* programa: ID '{' lista_sentencias '}'  */
#line 18 "parser_test.y"
            { System.out.println("Declaracion de programa detectada. Linea: " + al.getLine()); }
#line 1361 "parser_test.tab.c"
    break;

  case 3: /* programa: error '{' lista_sentencias '}'  */
#line 21 "parser_test.y"
        { System.out.println("Declaracion de programa invalida, identificador invalido. Linea: " + al.getLine()); }
#line 1367 "parser_test.tab.c"
    break;

  case 4: /* programa: '{' lista_sentencias '}'  */
#line 24 "parser_test.y"
              { System.out.println("Declaracion de programa invalida, falta identificador. Linea: " + al.getLine()); }
#line 1373 "parser_test.tab.c"
    break;

  case 5: /* programa: ID lista_sentencias '}'  */
#line 27 "parser_test.y"
        { System.out.println("Falta apertura del programa '{'. Linea: " + al.getLine()); }
#line 1379 "parser_test.tab.c"
    break;

  case 6: /* programa: ID '{' lista_sentencias  */
#line 31 "parser_test.y"
         { System.out.println("Falta cierre del programa '}'. Linea: " + al.getLine()); }
#line 1385 "parser_test.tab.c"
    break;

  case 7: /* programa: ID lista_sentencias  */
#line 32 "parser_test.y"
                          { System.out.println("Faltan delimitadores de programa '{' y '}' '}'. Linea: " + al.getLine()); }
#line 1391 "parser_test.tab.c"
    break;

  case 10: /* sentencia: sentencia_ejecutable  */
#line 38 "parser_test.y"
                               { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
#line 1397 "parser_test.tab.c"
    break;

  case 12: /* sentencia_declarativa: declaracion_variable  */
#line 43 "parser_test.y"
                               { System.out.println("Falta delimitador de sentencias ;. Linea: " + al.getLine()); }
#line 1403 "parser_test.tab.c"
    break;

  case 18: /* sentencia_ejecutable: retorno  */
#line 52 "parser_test.y"
                  { System.out.println("checkear valido"); }
#line 1409 "parser_test.tab.c"
    break;

  case 20: /* declaracion_funcion: tipo ID parametros_formales_opt cuerpo_funcion_opt  */
#line 59 "parser_test.y"
            { System.out.println("Declaracion de funcion detectada. Linea: " + al.getLine()); }
#line 1415 "parser_test.tab.c"
    break;

  case 21: /* declaracion_funcion: ID parametros_formales_opt cuerpo_funcion_opt  */
#line 61 "parser_test.y"
        { System.out.println("Funcion sin tipo. Linea: " + al.getLine()); }
#line 1421 "parser_test.tab.c"
    break;

  case 22: /* declaracion_funcion: tipo parametros_formales_opt cuerpo_funcion_opt  */
#line 63 "parser_test.y"
        { System.out.println("Funcion sin nombre. Linea: " + al.getLine()); }
#line 1427 "parser_test.tab.c"
    break;

  case 26: /* cuerpo_funcion_opt: '{' '}'  */
#line 73 "parser_test.y"
              { System.out.println("Falta el cuerpo del bloque. Linea: " + al.getLine()); }
#line 1433 "parser_test.tab.c"
    break;

  case 33: /* lista_parametros_formales: lista_parametros_formales error parametro_formal  */
#line 90 "parser_test.y"
                                                           { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
#line 1439 "parser_test.tab.c"
    break;

  case 36: /* lista_parametros_reales: lista_parametros_reales error parametro_real_compuesto  */
#line 96 "parser_test.y"
                                                                { System.out.println("Separador de parametros invalido. Utilizar ','. Linea: " + al.getLine()); }
#line 1445 "parser_test.tab.c"
    break;

  case 38: /* declaracion_variable: tipo asignacion  */
#line 101 "parser_test.y"
                          { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
#line 1451 "parser_test.tab.c"
    break;

  case 39: /* declaracion_variable: error asignacion  */
#line 102 "parser_test.y"
                           { System.out.println("Tipo invalido/ausente"); }
#line 1457 "parser_test.tab.c"
    break;

  case 40: /* lista_identificadores: tipo IDCOMP  */
#line 106 "parser_test.y"
                      { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
#line 1463 "parser_test.tab.c"
    break;

  case 41: /* lista_identificadores: lista_identificadores ',' IDCOMP  */
#line 107 "parser_test.y"
                                           { System.out.println("Declaracion de variable detectada. Linea: " + al.getLine()); }
#line 1469 "parser_test.tab.c"
    break;

  case 43: /* lista_identificadores: ID  */
#line 109 "parser_test.y"
             { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1475 "parser_test.tab.c"
    break;

  case 44: /* lista_identificadores: tipo ID  */
#line 110 "parser_test.y"
                  { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1481 "parser_test.tab.c"
    break;

  case 45: /* lista_identificadores: lista_identificadores ',' ID  */
#line 111 "parser_test.y"
                                       { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1487 "parser_test.tab.c"
    break;

  case 46: /* parametro_formal: CR tipo ID  */
#line 115 "parser_test.y"
                     { System.out.println("Parametro formal copia resultado detectado. Linea: " + al.getLine()); }
#line 1493 "parser_test.tab.c"
    break;

  case 47: /* parametro_formal: CR ID  */
#line 116 "parser_test.y"
                { System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
#line 1499 "parser_test.tab.c"
    break;

  case 48: /* parametro_formal: CR tipo  */
#line 117 "parser_test.y"
                  { System.out.println("Falta nombre de parametro formal. Linea: " + al.getLine()); }
#line 1505 "parser_test.tab.c"
    break;

  case 49: /* parametro_formal: CR  */
#line 118 "parser_test.y"
             { System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
#line 1511 "parser_test.tab.c"
    break;

  case 50: /* parametro_formal: tipo ID  */
#line 119 "parser_test.y"
                  { System.out.println("Parametro formal con semantica por defecto detectado. Linea: " + al.getLine()); }
#line 1517 "parser_test.tab.c"
    break;

  case 51: /* parametro_formal: ID  */
#line 120 "parser_test.y"
             { System.out.println("Falta tipo de parametro formal. Linea: " + al.getLine()); }
#line 1523 "parser_test.tab.c"
    break;

  case 52: /* parametro_formal: LAMBDA ID  */
#line 121 "parser_test.y"
                    { System.out.println("Parametro formal lambda detectado. Linea: " + al.getLine()); }
#line 1529 "parser_test.tab.c"
    break;

  case 53: /* parametro_formal: CR LAMBDA ID  */
#line 122 "parser_test.y"
                       { System.out.println("Parametro formal lambda por copia resultado detectado. Linea: " + al.getLine()); }
#line 1535 "parser_test.tab.c"
    break;

  case 54: /* parametro_real_compuesto: parametro_real FLECHA parametro_formal  */
#line 126 "parser_test.y"
                                                 { System.out.println("Parametro real detectado. Linea: " + al.getLine()); }
#line 1541 "parser_test.tab.c"
    break;

  case 59: /* sentencia_IF: IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ENDIF  */
#line 141 "parser_test.y"
          { System.out.println("IF detectado (sin ELSE). Linea: " + al.getLine()); }
#line 1547 "parser_test.tab.c"
    break;

  case 60: /* sentencia_IF: IF '(' condicion ')' '{' lista_sentencias_ejecutables '}' ELSE '{' lista_sentencias_ejecutables '}' ENDIF  */
#line 143 "parser_test.y"
          { System.out.println("IF-ELSE detectado. Linea: " + al.getLine()); }
#line 1553 "parser_test.tab.c"
    break;

  case 61: /* condicion: expresion comparador expresion  */
#line 147 "parser_test.y"
                                         { System.out.println("Condicion detectada. Linea: " + al.getLine()); }
#line 1559 "parser_test.tab.c"
    break;

  case 62: /* do_until: DO '{' lista_sentencias_ejecutables '}' UNTIL '(' condicion ')'  */
#line 152 "parser_test.y"
          { System.out.println("DO-UNTIL detectado. Linea: " + al.getLine()); }
#line 1565 "parser_test.tab.c"
    break;

  case 71: /* asignacion: IDCOMP ASIGNAR expresion  */
#line 170 "parser_test.y"
                                   { System.out.println("Asignacion detectada. Linea: " + al.getLine()); }
#line 1571 "parser_test.tab.c"
    break;

  case 72: /* asignacion: ID ASIGNAR expresion  */
#line 171 "parser_test.y"
                               { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1577 "parser_test.tab.c"
    break;

  case 76: /* expresion: TRUNC '(' expresion ')'  */
#line 178 "parser_test.y"
                                  {System.out.println("Trunc detectado. Linea: " + al.getLine()); }
#line 1583 "parser_test.tab.c"
    break;

  case 84: /* factor: ID  */
#line 192 "parser_test.y"
             { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1589 "parser_test.tab.c"
    break;

  case 85: /* llamada_funcion: IDCOMP '(' lista_parametros_reales ')'  */
#line 196 "parser_test.y"
                                                 {System.out.println("Llamado a funcion detectado. Linea: " + al.getLine());}
#line 1595 "parser_test.tab.c"
    break;

  case 86: /* llamada_funcion: ID '(' lista_parametros_reales ')'  */
#line 197 "parser_test.y"
                                             { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1601 "parser_test.tab.c"
    break;

  case 87: /* llamada_funcion: IDCOMP '(' ')'  */
#line 198 "parser_test.y"
                     {System.out.println("Llamado a funcion sin parametros detectado. Linea: " + al.getLine());}
#line 1607 "parser_test.tab.c"
    break;

  case 88: /* llamada_funcion: ID '(' ')'  */
#line 199 "parser_test.y"
                 { System.out.println("Falta prefijo obligatorio del ID. Linea: " + al.getLine()); }
#line 1613 "parser_test.tab.c"
    break;

  case 89: /* print: PRINT '(' STRING ')'  */
#line 203 "parser_test.y"
                               {System.out.println("Print detectado. Linea: " + al.getLine());}
#line 1619 "parser_test.tab.c"
    break;

  case 90: /* print: PRINT '(' expresion ')'  */
#line 204 "parser_test.y"
                                  {System.out.println("Print detectado con expresion. Linea: " + al.getLine());}
#line 1625 "parser_test.tab.c"
    break;

  case 91: /* lambda: '(' tipo ')' '{' lista_sentencias_ejecutables  */
#line 208 "parser_test.y"
                                                        {System.out.println("Definicion lambda detectada. Linea: " + al.getLine());}
#line 1631 "parser_test.tab.c"
    break;

  case 92: /* retorno: RETURN '(' expresion ')'  */
#line 212 "parser_test.y"
                                   {System.out.println("Retorno detectado. Linea: " + al.getLine());}
#line 1637 "parser_test.tab.c"
    break;

  case 93: /* asignacion_multiple: ids '=' lista_constantes  */
#line 216 "parser_test.y"
                               { System.out.println("Asignacion multiple detectada. Linea: " + al.getLine());}
#line 1643 "parser_test.tab.c"
    break;

  case 94: /* asignacion_multiple: error ids '=' lista_constantes  */
#line 217 "parser_test.y"
                                      { System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
#line 1649 "parser_test.tab.c"
    break;

  case 95: /* asignacion_multiple: ids '=' error  */
#line 218 "parser_test.y"
                     { System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
#line 1655 "parser_test.tab.c"
    break;

  case 96: /* asignacion_multiple: error ids '=' error lista_constantes  */
#line 219 "parser_test.y"
                                           { System.out.println("Error en asignacion multiple, separador a utilizar: ,. Linea: " + al.getLine());}
#line 1661 "parser_test.tab.c"
    break;


#line 1665 "parser_test.tab.c"

      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", YY_CAST (yysymbol_kind_t, yyr1[yyn]), &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */
  {
    const int yylhs = yyr1[yyn] - YYNTOKENS;
    const int yyi = yypgoto[yylhs] + *yyssp;
    yystate = (0 <= yyi && yyi <= YYLAST && yycheck[yyi] == *yyssp
               ? yytable[yyi]
               : yydefgoto[yylhs]);
  }

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYSYMBOL_YYEMPTY : YYTRANSLATE (yychar);
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
      yyerror (YY_("syntax error"));
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
  /* Pacify compilers when the user code never invokes YYERROR and the
     label yyerrorlab therefore never appears in user code.  */
  if (0)
    YYERROR;
  ++yynerrs;

  /* Do not reclaim the symbols of the rule whose action triggered
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
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  /* Pop stack until we find a state that shifts the error token.  */
  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYSYMBOL_YYerror;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYSYMBOL_YYerror)
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
                  YY_ACCESSING_SYMBOL (yystate), yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", YY_ACCESSING_SYMBOL (yyn), yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturnlab;


/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturnlab;


/*-----------------------------------------------------------.
| yyexhaustedlab -- YYNOMEM (memory exhaustion) comes here.  |
`-----------------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  goto yyreturnlab;


/*----------------------------------------------------------.
| yyreturnlab -- parsing is finished, clean up and return.  |
`----------------------------------------------------------*/
yyreturnlab:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  YY_ACCESSING_SYMBOL (+*yyssp), yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif

  return yyresult;
}

#line 238 "parser_test.y"

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
