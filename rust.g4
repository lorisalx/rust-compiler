grammar rust;

@header {
    package parser;
}

// Le fichier constitue la racine de votre code. Il est constitué d'un ensemble de déclarations terminés par
// le caractère spécial EOF.
program: decl* EOF ;

/*
On peut déclarer les éléments suivants :
 - structure
 - fonction
*/
decl: declFunction | declStruct ;

/*
Une fonction est définie par la syntaxe suivante:
fn nom_fonction(nom_argument_1:type1, mut nom_argument_2:type2 ...) -> type_retour {
    ... // instructions
}
Il est à noter que le type de retour de la fonction est optionnel. Dans le cas ou aucun type n'est précisé, on
considère que le type de retour est () (void).
*/
declFunction: 'fn' IDF ('(' declArgs ')' | '()') declReturnType bloc ;
// Liste de declArg, qui peut se finir avec une virgule ou ne contenir aucun élément
declArgs: (declArg ',')* declArg? ;
declArg: 'mut'? IDF ':' type ;
declReturnType: ('->' type)? ;
bloc: '{' instr* '}' ;


// Une instruction est constituée des éléments suivants :
instr:
    // Une expression suivie d'un point virgule
    expr ';' #instrExpr
    // Un déclaration de variable, qui commence par le mot clé let et est annoté par le type de la variable...
    | 'let' 'mut'? IDF':' type ('=' expr)? ';' #instrDeclLet
    // ... ou une déclaration de structure
    | 'let' 'mut'? IDF':' type ('=' IDF '{' declArgs '}')? ';' #instrDeclLetStruct
    // Une boucle while
    | 'while' expr bloc #instrWhile
    // Un return
    | 'return' expr? ';' #instrReturn
    // Un bloc ifthenelse
    | 'if' expr bloc ('else' bloc)? #instrIfElse
    // Assignation
    | IDF '=' expr ';' #instrAssignation
    | accessTable '=' expr ';' #instrAssignationTable
    ;

accessTable: IDF('[' expr ']')+;

// Les expressions sont constituées des opérations arithmétique et des appels sur les structures et les tableaux.
// Elles sont constitués des alternatives suivantes :
expr:
    expr1 #exprComputation
    // Un bloc (e.g. pour {let mut x = 0; x += 1; x} on considère le dernier x)
    | bloc #exprBloc
    // L'appel a la fonction spéciale print
    | 'print' '!' '(' STR ')' #exprPrint
    ;

// Priorité des opérateurs
expr1: ((expr2 '||' expr1) | expr2) #exprOr;
expr2: ((expr3 '&&' expr2) | expr3) #exprAnd;
expr3: ((expr4 ('==' | '!=' | '<' | '<=' | '>' | '>=') expr3) | expr4) #exprCond;
expr4: ((expr5 ('+' | '-') expr4) | expr5) #exprPlusOrMinus;
expr5: ((expr6 ('*' | '/' | '%') expr5) | expr6) #exprMultOrDiv;
expr6: (('!' | '-' | '*' | '&' | '&mut' | '&' 'mut') expr6 | expr7) #exprUnary;
expr7:
    // Les accès aux éléments d'une structure ou d'un tableau
    (expr8 '.' IDF | expr8) #exprDot
    | ((expr8 ('[' expr ']')+) | expr8) #exprIndex
    // L'appel à la méthode len
    | (expr8 '.' 'len' '()' | expr8) #exprLen
    // L'appel de fonction
    | (IDF '(' passArgs ')' | expr8) #exprFunc
    // La déclaration d'un vecteur
    | ('vec' '!' '[' passArgs ']' | expr8) #exprVec
    ;
// Une expression entre parenthèses
expr8: (('(' expr ')') | expr9) #exprParenthesis;
// Les terminaux entier et booléens + les identifiants
expr9: IDF #exprIdentifier
        | INT #exprInteger
        | BOOLEAN #exprBoolean
        ;

passArgs: (expr ',')* expr;

/*
Une structure est déclaré de la façon suivante :
struct nom_structure {
    nom_parametre_1 : type1,
    nom_parametre_2 : type2,
    ...
}
*/
declStruct: 'struct' IDF '{' declArgs '}' ;

/*
On considère qu'une valeur possède l'un des types suivant :
- i32 (le type entier de base)
- bool (le type booléen)
- Vec<T> (Un vecteur d'objets de type T)
- T (Une structure définie de nom T)
- () (Une notation sans type (est équivalent au type void))
- &T (Une référence sur un objet de type T)
*/
type: IDF #typeIdentifier
    | 'Vec<'type'>' #typeVec
    | '&' type #typeRef
    | ('&' 'mut' | '&mut') type #typeRefMut
    | '()' #typeVoid ;

// LEXIQUE
// Les nombres entiers qui contiennent au moins un chiffre
INT: ('0'..'9')+ ;
// Les deux booléens possibles
BOOLEAN: 'true' | 'false' ;
// Les chaines de caractères notées " CHAR* " où CHAR désigne n'importe quel caractère ASCII
// (pas d'utilisation de CHAR car STR doit être une unité lexicale)
STR: ('"'('\u0020'..'\u0126')*'"') ;
// Les identifiants, qui commence par une lettre ou un _ et qui peuvent contenir des lettres,
// des chiffres ou le caractère _
IDF: ('A'..'Z' | 'a'..'z' | '_') ('A'..'Z' | 'a'..'z' | '0'..'9' | '_')* ;
// Les whitespaces (\n, ' ' et \t) en dehors des chaines de caractères doivent être ignorés.
WS: ('\n' | '\r' | ' ' | '\t')+ -> skip ;
// Les commentaires sont définis de la façon suivante :
// - débutant par /* et terminant par */
// - débutant par // jusqu'à la fin de la ligne
COMMENT: ('/*'.*?'*/' | '//'.*?'\n') -> skip ;
