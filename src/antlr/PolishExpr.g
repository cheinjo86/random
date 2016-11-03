grammar PolishExpr;

@header{
	package antlr;
}

@lexer::header {package antlr; }

evaluate returns [double ret]
	: expr { ret = $expr.value; }
	;

expr returns [double value]
	:
	LPAREN
	(
	 	(PLUS
	     o = operand { $value = $o.value;}
	   	 (o = operand { $value += $o.value;} )+
	 	)
	 |
	 	(SUB
	     o = operand { $value = $o.value;}
	     (o = operand { $value -= $o.value;} )+
	    )
	 |
	 	(TIMES
	     o = operand { $value = $o.value;}
	     (o = operand { $value *= $o.value;} )+
	    )
	 |
	    (DIV
	     o = operand { $value = $o.value;}
	     (o = operand { $value /= $o.value;} )+
	     )
	 )
	 RPAREN
	 ;
	 

operand returns [double value]
	:  INT { $value = Integer.parseInt($INT.text); }
	|  e=expr { $value=$e.value; }
	;
	

// LEXER
LPAREN  : '(';
RPAREN  : ')';
PLUS    : '+';
SUB     : '-';
TIMES   : '*';
DIV     : '/';
WS      : (' '|'\t')+ {skip();} ;
INT     : '0'..'9'+;
	 
	  