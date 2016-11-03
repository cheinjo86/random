package antlr;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class PolishExprDemo {

	public static void main(String[] args) throws RecognitionException {
		String expr = "(+ 2 3 (- 5 1))";
		
		 ANTLRStringStream input = new ANTLRStringStream(expr);
	     TokenStream tokens = new CommonTokenStream( new PolishExprLexer( input ) );
	     PolishExprParser parser = new PolishExprParser(tokens);
	     
	     System.out.println(parser.evaluate());
		
	}
}
