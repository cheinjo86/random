package first_trial.test;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import first_trial.ExprLexer;
import first_trial.ExprParser;

public class ExprTest {

	public static void main(String[] args)  throws Exception{
		
		doEval("2+3*5/2");
	}
	
	static void doEval(String expr) throws RecognitionException {
		CharStream c = new ANTLRStringStream(expr);
		System.out.print("evaluating [" + expr + "]: ... ");
		ExprLexer lexer = new ExprLexer(c);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		
		System.out.println(parser.evaluate());
	}
}
