grammar PrefixExpr;

@header{
	package second_trial;
}

@lexer::header {package second_trial; }

@members {
	StringBuilder buf = new StringBuilder();
}

evaluate returns [double ret]
	: expr[buf]
	  {
	   	   ret = $expr.value;
	   	   System.out.println("\n = " + buf + "\n = " + ret); 
	   	   buf = new StringBuilder(); 
	  }
	;

expr [StringBuilder buf] returns [double value]
	:
	LPAREN {buf.append("(");}
	(
	 	op=(PLUS | SUB | TIMES | DIV)
	    o = operand[buf] { $value = $o.value; }
	   	(
	   	   {buf.append($op.text); }
	   	   o = operand[buf]
	   	   {
	   	   	  switch($op.getType())
	   	   	  {
	   	   	  	case SUB: $value -= $o.value; break;
	   	   	  	case PLUS : $value += $o.value; break;
	   	   	  	case TIMES : $value *= $o.value; break;
	   	   	  	case DIV : $value /= $o.value; break;
	   	   	  	default: throw new RuntimeException("unexpected type: " + $op.getType());
	   	   	  }
	   	       
	   	   }
	   	)+
	 	
	)
	RPAREN {buf.append(")");}
	;
	 

operand [StringBuilder buf] returns [double value]
	:  INT { $value = Integer.parseInt($INT.text); buf.append($INT.text); }
	|  e=expr[buf] { $value=$e.value; }
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
	 
	  