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

prog:   (evaluate {System.out.println($evaluate.value); })+ ;
                
evaluate returns [double value]
	:  
		expr {$value=$expr.value;}
    |   ID '=' expr 
        {memory.put($ID.text, new Double($expr.value));}
    ;

expr returns [double value]
    :
    e=mult_expr {$value = $e.value;}
    (   PLUS e=mult_expr {$value += $e.value;}
    |   MINUS e=mult_expr {$value -= $e.value;}
    )*
    ;

mult_expr returns [double value]
	:
	left = unary_expr { $value = $left.value; }
	(
		MULT right = unary_expr { $value *= $right.value; }
	|	DIV right = unary_expr { $value /= $right.value; }
	)*
	;
	
unary_expr returns [double value]
	:
	(
		atom { $value = $atom.value; }
	/*|	'-' e=unary_expr { $value = -1 * $e.value; } */
	)
	;	

atom returns [double value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
        	Double v = (Double)memory.get($ID.text);
        	if ( v!=null ) $value = v.doubleValue();
        	else System.err.println("undefined variable "+$ID.text);
        }
    |   LPAREN e=expr RPAREN {$value = $e.value;}
    ;

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
