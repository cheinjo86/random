grammar PrefixExpr;

@header {
package second_trial;
}

@lexer::header {package second_trial; }

@members {
StringBuilder buf = new StringBuilder();

}

//evaluate: (e=eval[buf] {System.out.println(buf.toString() + " = " + $e.value); buf = new StringBuilder();} )+ ;

//eval [StringBuilder buf] returns [double value]
//	:
	
//	e=expr[buf] {$value=$e.value;}
	
// 	
evaluate returns [double ret]
	: expr[null] { ret = $expr.value; }
	;


expr [StringBuilder buf] returns [double value]
	:
	LPAREN
	(
	 	(PLUS
	     o = operand[buf] { $value = $o.value;}
	   	 (o = operand[buf] { $value += $o.value;} )+
	 	)
	 |
	 	(MINUS
	     o = operand[buf] { $value = $o.value;}
	     (o = operand[buf] { $value -= $o.value;} )+
	    )
	 |
	 	(MULT
	     o = operand[buf] { $value = $o.value;}
	     (o = operand[buf] { $value *= $o.value;} )+
	    )
	 |
	    (DIV
	     o = operand[buf] { $value = $o.value;}
	     (o = operand[buf] { $value /= $o.value;} )+
	     )
	 )
	 RPAREN
	 ;
	 

operand [StringBuilder buf] returns [double value]
	:  INT { $value = Integer.parseInt($INT.text); }
	|  e=expr[buf] { $value=$e.value; }
	;
	

	
/*	
expr [StringBuilder buf] returns [double value]
	
	:
	
	(	op=MINUS 
	|   op=PLUS  
	|   op=MULT
	|   op=DIV
	)
	e=atom[buf]  {buf.append($op.text); $value=$e.value;}
	(
	  e=atom[buf]
	  {buf.append($op.text); $value=$e.value;}
	)+
	{
		//if ($op.text.equals("+") || $op.text.equals("-")) {buf.append(")");}
	}
	
	;
	
atom [StringBuilder buf] returns [double value]
	:
	INT
	{
		buf.append($INT.text);
		$value=Integer.parseInt($INT.text);
	}
	|
	LPAREN e=expr[buf] RPAREN{ $value=$e.value; }
	;
	
*/
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
	
	
