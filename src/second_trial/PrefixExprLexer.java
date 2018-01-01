// $ANTLR 3.5.2 src/second_trial/PrefixExpr.g 2017-10-22 20:02:13
package second_trial; 

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class PrefixExprLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public PrefixExprLexer() {} 
	public PrefixExprLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public PrefixExprLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "src/second_trial/PrefixExpr.g"; }

	// $ANTLR start "LPAREN"
	public final void mLPAREN() throws RecognitionException {
		try {
			int _type = LPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:56:9: ( '(' )
			// src/second_trial/PrefixExpr.g:56:11: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LPAREN"

	// $ANTLR start "RPAREN"
	public final void mRPAREN() throws RecognitionException {
		try {
			int _type = RPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:57:9: ( ')' )
			// src/second_trial/PrefixExpr.g:57:11: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "RPAREN"

	// $ANTLR start "PLUS"
	public final void mPLUS() throws RecognitionException {
		try {
			int _type = PLUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:58:9: ( '+' )
			// src/second_trial/PrefixExpr.g:58:11: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PLUS"

	// $ANTLR start "SUB"
	public final void mSUB() throws RecognitionException {
		try {
			int _type = SUB;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:59:9: ( '-' )
			// src/second_trial/PrefixExpr.g:59:11: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SUB"

	// $ANTLR start "TIMES"
	public final void mTIMES() throws RecognitionException {
		try {
			int _type = TIMES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:60:9: ( '*' )
			// src/second_trial/PrefixExpr.g:60:11: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TIMES"

	// $ANTLR start "DIV"
	public final void mDIV() throws RecognitionException {
		try {
			int _type = DIV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:61:9: ( '/' )
			// src/second_trial/PrefixExpr.g:61:11: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIV"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:62:9: ( ( ' ' | '\\t' )+ )
			// src/second_trial/PrefixExpr.g:62:11: ( ' ' | '\\t' )+
			{
			// src/second_trial/PrefixExpr.g:62:11: ( ' ' | '\\t' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0=='\t'||LA1_0==' ') ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// src/second_trial/PrefixExpr.g:
					{
					if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
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

			skip();
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/second_trial/PrefixExpr.g:63:9: ( ( '0' .. '9' )+ )
			// src/second_trial/PrefixExpr.g:63:11: ( '0' .. '9' )+
			{
			// src/second_trial/PrefixExpr.g:63:11: ( '0' .. '9' )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// src/second_trial/PrefixExpr.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	@Override
	public void mTokens() throws RecognitionException {
		// src/second_trial/PrefixExpr.g:1:8: ( LPAREN | RPAREN | PLUS | SUB | TIMES | DIV | WS | INT )
		int alt3=8;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt3=1;
			}
			break;
		case ')':
			{
			alt3=2;
			}
			break;
		case '+':
			{
			alt3=3;
			}
			break;
		case '-':
			{
			alt3=4;
			}
			break;
		case '*':
			{
			alt3=5;
			}
			break;
		case '/':
			{
			alt3=6;
			}
			break;
		case '\t':
		case ' ':
			{
			alt3=7;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt3=8;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 3, 0, input);
			throw nvae;
		}
		switch (alt3) {
			case 1 :
				// src/second_trial/PrefixExpr.g:1:10: LPAREN
				{
				mLPAREN(); 

				}
				break;
			case 2 :
				// src/second_trial/PrefixExpr.g:1:17: RPAREN
				{
				mRPAREN(); 

				}
				break;
			case 3 :
				// src/second_trial/PrefixExpr.g:1:24: PLUS
				{
				mPLUS(); 

				}
				break;
			case 4 :
				// src/second_trial/PrefixExpr.g:1:29: SUB
				{
				mSUB(); 

				}
				break;
			case 5 :
				// src/second_trial/PrefixExpr.g:1:33: TIMES
				{
				mTIMES(); 

				}
				break;
			case 6 :
				// src/second_trial/PrefixExpr.g:1:39: DIV
				{
				mDIV(); 

				}
				break;
			case 7 :
				// src/second_trial/PrefixExpr.g:1:43: WS
				{
				mWS(); 

				}
				break;
			case 8 :
				// src/second_trial/PrefixExpr.g:1:46: INT
				{
				mINT(); 

				}
				break;

		}
	}



}
