grammar Expr;

@header {
package second_trial;
}
@lexer::header {package second_trial;}

evaluate: (expr { System.out.println($expr.value); } )+ ;

expr returns [double value]
	:
	e=mult_expr { $value = $e.value; }
	(	PLUS  e=mult_expr { $value += $e.value; }
	|   MINUS e=mult_expr { $value -= $e.value; }
	)*
	;
	
mult_expr returns [double value]
	:
	e=unary_expr { $value = $e.value; }
	(	TIMES e=unar_expr { $value *= $e.value; }
	|   DIV   e=unary_expr { $value /= $e.value; }
	)*
	;
	
unary_expr returns [double value]
	:
	atom { $value=$atom.value; }
	;
	
atom returns [double value]
	:
		INT {$value=Integer.parseInt($INT.text);}
	|	LPAREN e=expr RPAREN { $value=$e.value; }
	;
	
	
	
LPAREN	: '(';
RPAREN	: ')';
PLUS	: '+';
MINUS	: '-';
MULT	: '*';
DIV		: '/';
EQ		: '=';

protected ALPHA : 'A'..'Z' | 'a'..'z' | '_';
protected DIGIT : '0'..'9';


INT : DIGIT+;
ID  : ALPHA (ALPHA | DIGIT)*;

WS  : (' ' | '\t')+ {skip();};
NEWLINE : '\n'?'\n' ;