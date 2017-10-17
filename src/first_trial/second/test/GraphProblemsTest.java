package first_trial.second.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import first_trial.GraphProblems.WordNode;
import first_trial.second.GraphProblems;

public class GraphProblemsTest {
	public static Set<String> buildDict(String...st){
		Set<String> ret = new HashSet<>();
		for (String s : st) {
			ret.add(s);
		}
		return ret;
	}
	
	@Test
	public void testWordTransform() {
		Set<String> dict = buildDict("abc", "bca", "bbc", "cab", "bca", "bcc", "aac", "bac");
		char[] alpha = new char[] {'a', 'b', 'c'};
		String start = "abc";
		String end = "bac";
		
		Map<String,WordNode> graph = first_trial.GraphProblems.buildGraph(dict, start, alpha);
		System.out.println(graph);
		System.out.println(GraphProblems.shortestPath(start, end, dict, alpha));
	}
}
