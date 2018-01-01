// $ANTLR 3.5.2 src/second_trial/PrefixExpr.g 2017-10-22 20:02:13

	package second_trial;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class PrefixExprParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "DIV", "INT", "LPAREN", "PLUS", 
		"RPAREN", "SUB", "TIMES", "WS"
	};
	public static final int EOF=-1;
	public static final int DIV=4;
	public static final int INT=5;
	public static final int LPAREN=6;
	public static final int PLUS=7;
	public static final int RPAREN=8;
	public static final int SUB=9;
	public static final int TIMES=10;
	public static final int WS=11;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public PrefixExprParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public PrefixExprParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return PrefixExprParser.tokenNames; }
	@Override public String getGrammarFileName() { return "src/second_trial/PrefixExpr.g"; }


		StringBuilder buf = new StringBuilder();



	// $ANTLR start "evaluate"
	// src/second_trial/PrefixExpr.g:13:1: evaluate returns [double ret] : expr[buf] ;
	public final double evaluate() throws RecognitionException {
		double ret = 0.0;


		double expr1 =0.0;

		try {
			// src/second_trial/PrefixExpr.g:14:2: ( expr[buf] )
			// src/second_trial/PrefixExpr.g:14:4: expr[buf]
			{
			pushFollow(FOLLOW_expr_in_evaluate35);
			expr1=expr(buf);
			state._fsp--;


				   	   ret = expr1;
				   	   System.out.println("\n = " + buf + "\n = " + ret); 
				   	   buf = new StringBuilder(); 
				  
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ret;
	}
	// $ANTLR end "evaluate"



	// $ANTLR start "expr"
	// src/second_trial/PrefixExpr.g:22:1: expr[StringBuilder buf] returns [double value] : LPAREN (op= ( PLUS | SUB | TIMES | DIV ) o= operand[buf] (o= operand[buf] )+ ) RPAREN ;
	public final double expr(StringBuilder buf) throws RecognitionException {
		double value = 0.0;


		Token op=null;
		double o =0.0;

		try {
			// src/second_trial/PrefixExpr.g:23:2: ( LPAREN (op= ( PLUS | SUB | TIMES | DIV ) o= operand[buf] (o= operand[buf] )+ ) RPAREN )
			// src/second_trial/PrefixExpr.g:24:2: LPAREN (op= ( PLUS | SUB | TIMES | DIV ) o= operand[buf] (o= operand[buf] )+ ) RPAREN
			{
			match(input,LPAREN,FOLLOW_LPAREN_in_expr59); 
			buf.append("(");
			// src/second_trial/PrefixExpr.g:25:2: (op= ( PLUS | SUB | TIMES | DIV ) o= operand[buf] (o= operand[buf] )+ )
			// src/second_trial/PrefixExpr.g:26:4: op= ( PLUS | SUB | TIMES | DIV ) o= operand[buf] (o= operand[buf] )+
			{
			op=input.LT(1);
			if ( input.LA(1)==DIV||input.LA(1)==PLUS||(input.LA(1) >= SUB && input.LA(1) <= TIMES) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			pushFollow(FOLLOW_operand_in_expr96);
			o=operand(buf);
			state._fsp--;

			 value = o; 
			// src/second_trial/PrefixExpr.g:28:6: (o= operand[buf] )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= INT && LA1_0 <= LPAREN)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// src/second_trial/PrefixExpr.g:29:9: o= operand[buf]
					{
					buf.append((op!=null?op.getText():null)); 
					pushFollow(FOLLOW_operand_in_expr130);
					o=operand(buf);
					state._fsp--;


						   	   	  switch(op.getType())
						   	   	  {
						   	   	  	case SUB: value -= o; break;
						   	   	  	case PLUS : value += o; break;
						   	   	  	case TIMES : value *= o; break;
						   	   	  	case DIV : value /= o; break;
						   	   	  	default: throw new RuntimeException("unexpected type: " + op.getType());
						   	   	  }
						   	       
						   	   
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

			match(input,RPAREN,FOLLOW_RPAREN_in_expr159); 
			buf.append(")");
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "expr"



	// $ANTLR start "operand"
	// src/second_trial/PrefixExpr.g:49:1: operand[StringBuilder buf] returns [double value] : ( INT |e= expr[buf] );
	public final double operand(StringBuilder buf) throws RecognitionException {
		double value = 0.0;


		Token INT2=null;
		double e =0.0;

		try {
			// src/second_trial/PrefixExpr.g:50:2: ( INT |e= expr[buf] )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==INT) ) {
				alt2=1;
			}
			else if ( (LA2_0==LPAREN) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// src/second_trial/PrefixExpr.g:50:5: INT
					{
					INT2=(Token)match(input,INT,FOLLOW_INT_in_operand182); 
					 value = Integer.parseInt((INT2!=null?INT2.getText():null)); buf.append((INT2!=null?INT2.getText():null)); 
					}
					break;
				case 2 :
					// src/second_trial/PrefixExpr.g:51:5: e= expr[buf]
					{
					pushFollow(FOLLOW_expr_in_operand192);
					e=expr(buf);
					state._fsp--;

					 value =e; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "operand"

	// Delegated rules



	public static final BitSet FOLLOW_expr_in_evaluate35 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_expr59 = new BitSet(new long[]{0x0000000000000690L});
	public static final BitSet FOLLOW_set_in_expr71 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_operand_in_expr96 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_operand_in_expr130 = new BitSet(new long[]{0x0000000000000160L});
	public static final BitSet FOLLOW_RPAREN_in_expr159 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_operand182 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expr_in_operand192 = new BitSet(new long[]{0x0000000000000002L});
}
