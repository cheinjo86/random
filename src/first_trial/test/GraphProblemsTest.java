package first_trial.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import first_trial.GraphProblems;
import first_trial.GraphProblems.GraphNode;
import first_trial.GraphProblems.Pair;
import first_trial.GraphProblems.WordNode;

import first_trial.GraphProblems.Point;
import static first_trial.GraphProblems.*;

public class GraphProblemsTest {

	static Pair[] array(Pair...p) {
		return p;
	}
	

	
	static int[][] createMatrix() {
		int[][] m = new int[][]
		        {
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
				};
				return m;
	}
	
	static void print(int[][] m, List<Point> path) {
		for (Point p : path) {
			m[p.x][p.y] = 1;
		}
		
		for (int[] row : m) {
			System.out.print("[ ");
			for (int x : row) {
				System.out.print(x + " ");
			}
			System.out.println("]");
		}
	}
	@Test
	public void testShortestPathBFS() {
		int[][] m = createMatrix();
	
	    List<Point> path = shortestPath(m, new Point(4, 1), new Point(1, 4));
	    print(m, path);
	}
	
	
	@Test
	public  void testCycles() {
		Pair ab = new Pair("a", "b");
		Pair bc = new Pair("b", "c");
		Pair cd = new Pair("c", "d");
		Pair de = new Pair("d", "e");
		Pair eh = new Pair("e", "h");
		Pair hi = new Pair("h", "i");
		Pair ij = new Pair("i", "j");
		Pair ec = new Pair("e", "c");
		Pair fg = new Pair("f", "g");
		
		GraphProblems.eliminateDeps(array(ab, bc, cd, de, eh, hi, ij, ec, fg));
	}
	public static Set<String> buildDict(String...st){
		Set<String> ret = new HashSet<>();
		for (String s : st) {
			ret.add(s);
		}
		return ret;
	}
	
	@Test
	public void testPrim() {
		//s
		GraphNode s = new GraphNode ('s');
		// a
		GraphNode a = new GraphNode('a');
		
		// c
		GraphNode c = new GraphNode('c');
		
		// d
		GraphNode d = new GraphNode('d');
		
		//b
		GraphNode b = new GraphNode('b');
		
		//t
		GraphNode t = new GraphNode('t');
		
		s.childToDist.put(a, 7);
		s.childToDist.put(c, 8);
		
		a.childToDist.put(s, 7);
		a.childToDist.put(c, 3);
		a.childToDist.put(b,  6);
		
		c.childToDist.put(s,  8);
		c.childToDist.put(a, 3);
		c.childToDist.put(b, 4);
		c.childToDist.put(d, 3);
		
		b.childToDist.put(a, 6);
		b.childToDist.put(c, 3);
		b.childToDist.put(d, 2);
		b.childToDist.put(t, 5);
		
		d.childToDist.put(c, 3);
		d.childToDist.put(b, 2);
		d.childToDist.put(t, 2);
		
		t.childToDist.put(b, 5);
		t.childToDist.put(d, 2);
		
		Map<GraphNode, GraphNode> g = new HashMap<>();
		g.put(s, s);
		g.put(a, a);
		g.put(c, c);
		g.put(b, b);
		g.put(d, d);
		g.put(t, t);
		Set<GraphNode> prim = GraphProblems.prim(g, s);
		
		System.out.println(prim);
		
	}
	
	@Test
	public void testBuildGraph() {
		Set<String> dict = buildDict("abc", "bca", "bbc", "cab", "bca", "bcc", "aac", "bac");
		char[] alpha = new char[] {'a', 'b', 'c'};
		String start = "abc";
		String end = "bac";
		Map<String, WordNode> g = GraphProblems.buildGraph(dict, start, alpha);
		
		System.out.println(g);
		
		GraphProblems.findShortestPaht(g, start, end);
		WordNode endNode = g.get(end);
		
		WordNode n = endNode;
		while (n != null) {

			System.out.print(n.word + " <-- ");
			n = n.pred;
		}
		
	}
}
