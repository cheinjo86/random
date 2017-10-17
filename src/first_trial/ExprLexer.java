// $ANTLR 3.5.2 src/first_trial/Expr.g 2017-09-15 13:23:18
package first_trial;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class ExprLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public ExprLexer() {} 
	public ExprLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public ExprLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "src/first_trial/Expr.g"; }

	// $ANTLR start "LPAREN"
	public final void mLPAREN() throws RecognitionException {
		try {
			int _type = LPAREN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:60:8: ( '(' )
			// src/first_trial/Expr.g:60:10: '('
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
			// src/first_trial/Expr.g:61:8: ( ')' )
			// src/first_trial/Expr.g:61:10: ')'
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
			// src/first_trial/Expr.g:62:6: ( '+' )
			// src/first_trial/Expr.g:62:8: '+'
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

	// $ANTLR start "MINUS"
	public final void mMINUS() throws RecognitionException {
		try {
			int _type = MINUS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:63:7: ( '-' )
			// src/first_trial/Expr.g:63:9: '-'
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
	// $ANTLR end "MINUS"

	// $ANTLR start "MULT"
	public final void mMULT() throws RecognitionException {
		try {
			int _type = MULT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:64:6: ( '*' )
			// src/first_trial/Expr.g:64:8: '*'
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
	// $ANTLR end "MULT"

	// $ANTLR start "DIV"
	public final void mDIV() throws RecognitionException {
		try {
			int _type = DIV;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:65:6: ( '/' )
			// src/first_trial/Expr.g:65:8: '/'
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

	// $ANTLR start "EQ"
	public final void mEQ() throws RecognitionException {
		try {
			int _type = EQ;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:66:5: ( '=' )
			// src/first_trial/Expr.g:66:7: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EQ"

	// $ANTLR start "ALPHA"
	public final void mALPHA() throws RecognitionException {
		try {
			int _type = ALPHA;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:69:17: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
			// src/first_trial/Expr.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ALPHA"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			int _type = DIGIT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:70:17: ( '0' .. '9' )
			// src/first_trial/Expr.g:
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

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:72:5: ( ( '0' .. '9' )+ )
			// src/first_trial/Expr.g:72:9: ( '0' .. '9' )+
			{
			// src/first_trial/Expr.g:72:9: ( '0' .. '9' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// src/first_trial/Expr.g:
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
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
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

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:73:4: ( ALPHA ( ALPHA | DIGIT )* )
			// src/first_trial/Expr.g:73:6: ALPHA ( ALPHA | DIGIT )*
			{
			mALPHA(); 

			// src/first_trial/Expr.g:73:12: ( ALPHA | DIGIT )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// src/first_trial/Expr.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
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
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:76:5: ( ( ' ' | '\\t' )+ )
			// src/first_trial/Expr.g:76:7: ( ' ' | '\\t' )+
			{
			// src/first_trial/Expr.g:76:7: ( ' ' | '\\t' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0=='\t'||LA3_0==' ') ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// src/first_trial/Expr.g:
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
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
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

	// $ANTLR start "NEWLINE"
	public final void mNEWLINE() throws RecognitionException {
		try {
			int _type = NEWLINE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// src/first_trial/Expr.g:77:9: ( ( '\\r' )? '\\n' )
			// src/first_trial/Expr.g:77:10: ( '\\r' )? '\\n'
			{
			// src/first_trial/Expr.g:77:10: ( '\\r' )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0=='\r') ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// src/first_trial/Expr.g:77:10: '\\r'
					{
					match('\r'); 
					}
					break;

			}

			match('\n'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NEWLINE"

	@Override
	public void mTokens() throws RecognitionException {
		// src/first_trial/Expr.g:1:8: ( LPAREN | RPAREN | PLUS | MINUS | MULT | DIV | EQ | ALPHA | DIGIT | INT | ID | WS | NEWLINE )
		int alt5=13;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt5=1;
			}
			break;
		case ')':
			{
			alt5=2;
			}
			break;
		case '+':
			{
			alt5=3;
			}
			break;
		case '-':
			{
			alt5=4;
			}
			break;
		case '*':
			{
			alt5=5;
			}
			break;
		case '/':
			{
			alt5=6;
			}
			break;
		case '=':
			{
			alt5=7;
			}
			break;
		case 'A':
		case 'B':
		case 'C':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case '_':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			int LA5_8 = input.LA(2);
			if ( ((LA5_8 >= '0' && LA5_8 <= '9')||(LA5_8 >= 'A' && LA5_8 <= 'Z')||LA5_8=='_'||(LA5_8 >= 'a' && LA5_8 <= 'z')) ) {
				alt5=11;
			}

			else {
				alt5=8;
			}

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
			int LA5_9 = input.LA(2);
			if ( ((LA5_9 >= '0' && LA5_9 <= '9')) ) {
				alt5=10;
			}

			else {
				alt5=9;
			}

			}
			break;
		case '\t':
		case ' ':
			{
			alt5=12;
			}
			break;
		case '\n':
		case '\r':
			{
			alt5=13;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 5, 0, input);
			throw nvae;
		}
		switch (alt5) {
			case 1 :
				// src/first_trial/Expr.g:1:10: LPAREN
				{
				mLPAREN(); 

				}
				break;
			case 2 :
				// src/first_trial/Expr.g:1:17: RPAREN
				{
				mRPAREN(); 

				}
				break;
			case 3 :
				// src/first_trial/Expr.g:1:24: PLUS
				{
				mPLUS(); 

				}
				break;
			case 4 :
				// src/first_trial/Expr.g:1:29: MINUS
				{
				mMINUS(); 

				}
				break;
			case 5 :
				// src/first_trial/Expr.g:1:35: MULT
				{
				mMULT(); 

				}
				break;
			case 6 :
				// src/first_trial/Expr.g:1:40: DIV
				{
				mDIV(); 

				}
				break;
			case 7 :
				// src/first_trial/Expr.g:1:44: EQ
				{
				mEQ(); 

				}
				break;
			case 8 :
				// src/first_trial/Expr.g:1:47: ALPHA
				{
				mALPHA(); 

				}
				break;
			case 9 :
				// src/first_trial/Expr.g:1:53: DIGIT
				{
				mDIGIT(); 

				}
				break;
			case 10 :
				// src/first_trial/Expr.g:1:59: INT
				{
				mINT(); 

				}
				break;
			case 11 :
				// src/first_trial/Expr.g:1:63: ID
				{
				mID(); 

				}
				break;
			case 12 :
				// src/first_trial/Expr.g:1:66: WS
				{
				mWS(); 

				}
				break;
			case 13 :
				// src/first_trial/Expr.g:1:69: NEWLINE
				{
				mNEWLINE(); 

				}
				break;

		}
	}



}
