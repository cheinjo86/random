package second_trial.test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import second_trial.DynamicProgramming.Dict;
import second_trial.Graphs.WordNode;
import second_trial.test.DynamicTests.AbcDict;
import static second_trial.Graphs.*;

public class GraphsTest {

	@Test
	public void testMaze() {
		generateMaze(10, 10);
		generateMaze(10, 10);
		generateMaze(10, 10);
		
		
	}
	
	@Test
	public void testDFS() {
		String[] dict = {"AICC", "MCC", "BCCI","AICC","ICC","CCI","MCC","MCA", "ACC"};
		
		Set<String> d = new HashSet<>(dict.length);
		Set<Character> alpha = new HashSet<>();
		for (String s : dict) {
			d.add(s);
			for (char ch : s.toCharArray()) {
				alpha.add(ch);
			}
		}
		
		Dict dictionary = new AbcDict(d, alpha);
		Map<String, WordNode> graph = buildGraph("AICC", dictionary);
		
		WordNode root = graph.get("AICC");
		dfs(root);
		reset(graph);
		System.out.println();

		dfsIter(root);
		reset(graph);
		
		Iterator<WordNode> iter = dfsIterator(root);
		while(iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.println();
		reset(graph);
		
		// next without has next
		iter = dfsIterator(root);
		System.out.print(iter.next() + " ");
		System.out.print(iter.next() + " ");
		System.out.print(iter.next() + " ");
		System.out.print(iter.next() + " ");
		System.out.print(iter.next() + " ");
		System.out.print(iter.next() + " "); // throw here
		
	}
	
	public static void reset(Map<String, WordNode> g) {
		for (WordNode n : g.values()) {
			n.visited = false;
			n.inQueue = false;
		}
	}
	@Test
	public void testTransformWord() { // AICC --> ICC --> MCC
		testTransform("AICC", "MCC", "BCCI","AICC","ICC","CCI","MCC","MCA", "ACC");
	}
	
	static void testTransform(String st, String end, String...dict) {
		//{"BCCI","AICC","ICC","CCI","MCC","MCA", "ACC"},
		Set<String> d = new HashSet<>(dict.length);
		Set<Character> alpha = new HashSet<>();
		for (String s : dict) {
			d.add(s);
			for (char ch : s.toCharArray()) {
				alpha.add(ch);
			}
		}
		
		Dict dictionary = new AbcDict(d, alpha);
		Map<String, WordNode> graph = buildGraph(st, dictionary);
	
		for (WordNode node : graph.values()) {
			System.out.println("node: " + node);
			for (WordNode kid : node.children){
				System.out.println("  " + kid);
			}
		}
		
		System.out.println("-------");
		System.out.println("shortest path [" + st + " --> " + end + "]") ;
		List<String> path = shortestPath(graph, st, end);
		System.out.println(path);
		
	}
}
