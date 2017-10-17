grammar Expr;

@header {
	package first_trial;
}

// ------ lexer ---------
@lexer::header {package first_trial;}

@members {
	HashMap lookup = new HashMap();
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
IDENTIFIER	: ALPHA (ALPHA | DIGIT)*;

// whitespaces
WS		: (' ' | '\t')+ {skip();} ;
NEWLINE :'\r'? '\n' ;


// ------ parser ---------
// (For simplicyt will combine parser and walker)
evaluate returns [double ret]
	:
	(
		expr NEWLINE { ret = $expr.value; }
	|   IDENTIFIER EQ expr NEWLINE {lookup.put($IDENTIFIER.text, $expr.value);}
	)+
	;
	
expr returns [double value]
	:
	add_expr { $value = $add_expr.value; }
	;
	
add_expr returns [double value]
	:
	left = mult_expr { $value += $left.value} }
	(
		PLUS right = mult_expr { $value += $right.value; }
	|	MINUS right = mult_expr { $value-= $right.value; }
	)*
	;
	
mult_expr returns [double value]
	:
	left = unary_expr { $value = $left.value; }
	(
		TIMES right = unary_expr { $value *= $right.value; }
	|	DIV right = unary_expr { $value /= $right.value; }
	)*
	;
	
unary_expr returns [double value]
	:
	(
		atom { $value = $atom.value; }
	|	'-' e=unary_expr { $value = -1 * $e.value; }
	)
	;

atom returns [double value]
	:
	(
		IDDENTIFIER
		{
			$value = lookup.get($IDENTIFIER.text);
		}
	|	INT { $value = Integer.parseInt($INT.text); }
	|	LPAREN e=expr RPAREN {$value = $e.value; } 
	)
	;
	