grammar Expr;

@header {
package first_trial;
import java.util.HashMap;
}

@lexer::header {package first_trial;}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}


// OPS
LPAREN	: '(';
RPAREN	: ')';
PLUS	: '+';
MINUS	: '-';
MULT	: '*';
DIV		: '/';
EQ		: '=';

// operands
protected ALPHA : 'A'..'Z' | 'a'..'z' | '_';
protected DIGIT : '0'..'9';

INT :   '0'..'9'+ ;
ID	: ALPHA (ALPHA | DIGIT)*;

// whitespaces
WS		: (' ' | '\t')+ {skip();} ;
NEWLINE :'\r'? '\n' ;

prog returns [double ret]
	:   evaluate { $ret=$evaluate.ret; };
                
evaluate returns [double ret]
	:   expr NEWLINE {System.out.println($expr.value); $ret=$expr.value;}
    |   ID '=' expr NEWLINE
        {memory.put($ID.text, new Integer($expr.value));}
    ;

expr returns [int value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {$value += $e.value;}
        |   '-' e=multExpr {$value -= $e.value;}
        )*
    ;

multExpr returns [int value]
    :   e=atom {$value = $e.value;} ('*' e=atom {$value *= $e.value;})*
    ; 

atom returns [int value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
        Integer v = (Integer)memory.get($ID.text);
        if ( v!=null ) $value = v.intValue();
        else System.err.println("undefined variable "+$ID.text);
        }
    |   '(' e=expr ')' {$value = $e.value;}
    ;
