// $ANTLR 3.5.2 src/first_trial/Expr.g 2017-11-08 15:16:35

package first_trial;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("all")
public class ExprParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHA", "DIGIT", "DIV", "EQ", 
		"ID", "INT", "LPAREN", "MINUS", "MULT", "NEWLINE", "PLUS", "RPAREN", "WS"
	};
	public static final int EOF=-1;
	public static final int ALPHA=4;
	public static final int DIGIT=5;
	public static final int DIV=6;
	public static final int EQ=7;
	public static final int ID=8;
	public static final int INT=9;
	public static final int LPAREN=10;
	public static final int MINUS=11;
	public static final int MULT=12;
	public static final int NEWLINE=13;
	public static final int PLUS=14;
	public static final int RPAREN=15;
	public static final int WS=16;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public ExprParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public ExprParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return ExprParser.tokenNames; }
	@Override public String getGrammarFileName() { return "src/first_trial/Expr.g"; }


	/** Map variable name to Integer object holding value */
	HashMap memory = new HashMap();



	// $ANTLR start "prog"
	// src/first_trial/Expr.g:15:1: prog : ( evaluate )+ ;
	public final void prog() throws RecognitionException {
		double evaluate1 =0.0;

		try {
			// src/first_trial/Expr.g:15:5: ( ( evaluate )+ )
			// src/first_trial/Expr.g:15:9: ( evaluate )+
			{
			// src/first_trial/Expr.g:15:9: ( evaluate )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= ID && LA1_0 <= MINUS)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// src/first_trial/Expr.g:15:10: evaluate
					{
					pushFollow(FOLLOW_evaluate_in_prog33);
					evaluate1=evaluate();
					state._fsp--;
					if (state.failed) return;
					if ( state.backtracking==0 ) {System.out.println(evaluate1); }
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					if (state.backtracking>0) {state.failed=true; return;}
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "prog"



	// $ANTLR start "evaluate"
	// src/first_trial/Expr.g:17:1: evaluate returns [double value] : ( expr | ID '=' expr );
	public final double evaluate() throws RecognitionException {
		double value = 0.0;


		Token ID3=null;
		double expr2 =0.0;
		double expr4 =0.0;

		try {
			// src/first_trial/Expr.g:18:2: ( expr | ID '=' expr )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( ((LA2_0 >= INT && LA2_0 <= MINUS)) ) {
				alt2=1;
			}
			else if ( (LA2_0==ID) ) {
				int LA2_2 = input.LA(2);
				if ( (LA2_2==EQ) ) {
					alt2=2;
				}
				else if ( (LA2_2==EOF||LA2_2==DIV||(LA2_2 >= ID && LA2_2 <= MULT)||LA2_2==PLUS) ) {
					alt2=1;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return value;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return value;}
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// src/first_trial/Expr.g:19:3: expr
					{
					pushFollow(FOLLOW_expr_in_evaluate71);
					expr2=expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) {value =expr2;}
					}
					break;
				case 2 :
					// src/first_trial/Expr.g:20:9: ID '=' expr
					{
					ID3=(Token)match(input,ID,FOLLOW_ID_in_evaluate83); if (state.failed) return value;
					match(input,EQ,FOLLOW_EQ_in_evaluate85); if (state.failed) return value;
					pushFollow(FOLLOW_expr_in_evaluate87);
					expr4=expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) {memory.put((ID3!=null?ID3.getText():null), new Double(expr4));}
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
	// $ANTLR end "evaluate"



	// $ANTLR start "expr"
	// src/first_trial/Expr.g:24:1: expr returns [double value] : e= mult_expr ( options {backtrack=true; } : PLUS e= mult_expr | MINUS e= mult_expr )* ;
	public final double expr() throws RecognitionException {
		double value = 0.0;


		double e =0.0;

		try {
			// src/first_trial/Expr.g:25:5: (e= mult_expr ( options {backtrack=true; } : PLUS e= mult_expr | MINUS e= mult_expr )* )
			// src/first_trial/Expr.g:26:5: e= mult_expr ( options {backtrack=true; } : PLUS e= mult_expr | MINUS e= mult_expr )*
			{
			pushFollow(FOLLOW_mult_expr_in_expr125);
			e=mult_expr();
			state._fsp--;
			if (state.failed) return value;
			if ( state.backtracking==0 ) {value = e;}
			// src/first_trial/Expr.g:27:5: ( options {backtrack=true; } : PLUS e= mult_expr | MINUS e= mult_expr )*
			loop3:
			while (true) {
				int alt3=3;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==MINUS) ) {
					int LA3_5 = input.LA(2);
					if ( (synpred2_Expr()) ) {
						alt3=2;
					}

				}
				else if ( (LA3_0==PLUS) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// src/first_trial/Expr.g:28:6: PLUS e= mult_expr
					{
					match(input,PLUS,FOLLOW_PLUS_in_expr154); if (state.failed) return value;
					pushFollow(FOLLOW_mult_expr_in_expr158);
					e=mult_expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) {value += e;}
					}
					break;
				case 2 :
					// src/first_trial/Expr.g:29:9: MINUS e= mult_expr
					{
					match(input,MINUS,FOLLOW_MINUS_in_expr170); if (state.failed) return value;
					pushFollow(FOLLOW_mult_expr_in_expr174);
					e=mult_expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) {value -= e;}
					}
					break;

				default :
					break loop3;
				}
			}

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



	// $ANTLR start "mult_expr"
	// src/first_trial/Expr.g:33:1: mult_expr returns [double value] : left= unary_expr ( options {backtrack=true; } : MULT right= unary_expr | DIV right= unary_expr )* ;
	public final double mult_expr() throws RecognitionException {
		double value = 0.0;


		double left =0.0;
		double right =0.0;

		try {
			// src/first_trial/Expr.g:34:2: (left= unary_expr ( options {backtrack=true; } : MULT right= unary_expr | DIV right= unary_expr )* )
			// src/first_trial/Expr.g:35:2: left= unary_expr ( options {backtrack=true; } : MULT right= unary_expr | DIV right= unary_expr )*
			{
			pushFollow(FOLLOW_unary_expr_in_mult_expr206);
			left=unary_expr();
			state._fsp--;
			if (state.failed) return value;
			if ( state.backtracking==0 ) { value = left; }
			// src/first_trial/Expr.g:36:2: ( options {backtrack=true; } : MULT right= unary_expr | DIV right= unary_expr )*
			loop4:
			while (true) {
				int alt4=3;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==MULT) ) {
					alt4=1;
				}
				else if ( (LA4_0==DIV) ) {
					alt4=2;
				}

				switch (alt4) {
				case 1 :
					// src/first_trial/Expr.g:37:3: MULT right= unary_expr
					{
					match(input,MULT,FOLLOW_MULT_in_mult_expr227); if (state.failed) return value;
					pushFollow(FOLLOW_unary_expr_in_mult_expr233);
					right=unary_expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) { value *= right; }
					}
					break;
				case 2 :
					// src/first_trial/Expr.g:38:4: DIV right= unary_expr
					{
					match(input,DIV,FOLLOW_DIV_in_mult_expr240); if (state.failed) return value;
					pushFollow(FOLLOW_unary_expr_in_mult_expr246);
					right=unary_expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) { value /= right; }
					}
					break;

				default :
					break loop4;
				}
			}

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
	// $ANTLR end "mult_expr"



	// $ANTLR start "unary_expr"
	// src/first_trial/Expr.g:42:1: unary_expr returns [double value] : ( options {backtrack=true; } : atom | '-' e= unary_expr ) ;
	public final double unary_expr() throws RecognitionException {
		double value = 0.0;


		double e =0.0;
		double atom5 =0.0;

		try {
			// src/first_trial/Expr.g:43:2: ( ( options {backtrack=true; } : atom | '-' e= unary_expr ) )
			// src/first_trial/Expr.g:44:2: ( options {backtrack=true; } : atom | '-' e= unary_expr )
			{
			// src/first_trial/Expr.g:44:2: ( options {backtrack=true; } : atom | '-' e= unary_expr )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( ((LA5_0 >= ID && LA5_0 <= LPAREN)) ) {
				alt5=1;
			}
			else if ( (LA5_0==MINUS) ) {
				alt5=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return value;}
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// src/first_trial/Expr.g:45:3: atom
					{
					pushFollow(FOLLOW_atom_in_unary_expr285);
					atom5=atom();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) { value = atom5; }
					}
					break;
				case 2 :
					// src/first_trial/Expr.g:46:4: '-' e= unary_expr
					{
					match(input,MINUS,FOLLOW_MINUS_in_unary_expr292); if (state.failed) return value;
					pushFollow(FOLLOW_unary_expr_in_unary_expr296);
					e=unary_expr();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) { value = -1 * e; }
					}
					break;

			}

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
	// $ANTLR end "unary_expr"



	// $ANTLR start "atom"
	// src/first_trial/Expr.g:50:1: atom returns [double value] : ( INT | ID | LPAREN e= expr RPAREN );
	public final double atom() throws RecognitionException {
		double value = 0.0;


		Token INT6=null;
		Token ID7=null;
		double e =0.0;

		try {
			// src/first_trial/Expr.g:51:5: ( INT | ID | LPAREN e= expr RPAREN )
			int alt6=3;
			switch ( input.LA(1) ) {
			case INT:
				{
				alt6=1;
				}
				break;
			case ID:
				{
				alt6=2;
				}
				break;
			case LPAREN:
				{
				alt6=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return value;}
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// src/first_trial/Expr.g:51:9: INT
					{
					INT6=(Token)match(input,INT,FOLLOW_INT_in_atom323); if (state.failed) return value;
					if ( state.backtracking==0 ) {value = Integer.parseInt((INT6!=null?INT6.getText():null));}
					}
					break;
				case 2 :
					// src/first_trial/Expr.g:52:9: ID
					{
					ID7=(Token)match(input,ID,FOLLOW_ID_in_atom335); if (state.failed) return value;
					if ( state.backtracking==0 ) {
					        	Double v = (Double)memory.get((ID7!=null?ID7.getText():null));
					        	if ( v!=null ) value = v.doubleValue();
					        	else System.err.println("undefined variable "+(ID7!=null?ID7.getText():null));
					        }
					}
					break;
				case 3 :
					// src/first_trial/Expr.g:58:9: LPAREN e= expr RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_atom355); if (state.failed) return value;
					pushFollow(FOLLOW_expr_in_atom359);
					e=expr();
					state._fsp--;
					if (state.failed) return value;
					match(input,RPAREN,FOLLOW_RPAREN_in_atom361); if (state.failed) return value;
					if ( state.backtracking==0 ) {value = e;}
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
	// $ANTLR end "atom"

	// $ANTLR start synpred2_Expr
	public final void synpred2_Expr_fragment() throws RecognitionException {
		double e =0.0;

		// src/first_trial/Expr.g:29:9: ( MINUS e= mult_expr )
		// src/first_trial/Expr.g:29:9: MINUS e= mult_expr
		{
		match(input,MINUS,FOLLOW_MINUS_in_synpred2_Expr170); if (state.failed) return;
		pushFollow(FOLLOW_mult_expr_in_synpred2_Expr174);
		e=mult_expr();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred2_Expr

	// Delegated rules

	public final boolean synpred2_Expr() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred2_Expr_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_evaluate_in_prog33 = new BitSet(new long[]{0x0000000000000F02L});
	public static final BitSet FOLLOW_expr_in_evaluate71 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_evaluate83 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_EQ_in_evaluate85 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_expr_in_evaluate87 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_mult_expr_in_expr125 = new BitSet(new long[]{0x0000000000004802L});
	public static final BitSet FOLLOW_PLUS_in_expr154 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_mult_expr_in_expr158 = new BitSet(new long[]{0x0000000000004802L});
	public static final BitSet FOLLOW_MINUS_in_expr170 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_mult_expr_in_expr174 = new BitSet(new long[]{0x0000000000004802L});
	public static final BitSet FOLLOW_unary_expr_in_mult_expr206 = new BitSet(new long[]{0x0000000000001042L});
	public static final BitSet FOLLOW_MULT_in_mult_expr227 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_unary_expr_in_mult_expr233 = new BitSet(new long[]{0x0000000000001042L});
	public static final BitSet FOLLOW_DIV_in_mult_expr240 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_unary_expr_in_mult_expr246 = new BitSet(new long[]{0x0000000000001042L});
	public static final BitSet FOLLOW_atom_in_unary_expr285 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_unary_expr292 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_unary_expr_in_unary_expr296 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_atom323 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_atom335 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_atom355 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_expr_in_atom359 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_RPAREN_in_atom361 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_MINUS_in_synpred2_Expr170 = new BitSet(new long[]{0x0000000000000F00L});
	public static final BitSet FOLLOW_mult_expr_in_synpred2_Expr174 = new BitSet(new long[]{0x0000000000000002L});
}
