package first_trial;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class GraphProblems {

	
	static final char[] alphabets;
	static {
		alphabets = new char['z' - 'a' + 1];
		for (char i = 'a'; i <= 'z'; ++i) {
			alphabets[i - 'a'] = i;
		}
	}
	public static class Pair{
		final public String first;
		final public String second;
		
		public Pair(String f, String s) {
			first = f;
			second = s;
		}
	}
	
	/*
	 * In Docker, building an image has dependencies. An image can only be built once 
its dependency is built (If the dependency is from outside, then the image can 
be built immediately). 

Sometimes, engineers make mistakes by forming a cycle dependency of local images. 
In this case, ignore the cycle and all the images depending on this cycle. 

Input is vector of pair of images (image, its dependency). 

Output the order of images to be built in order. 

##Example: 
``` 
Example 1: 
{{"master", "ubuntu"}, {"numpy", "master"}, {"tensorflow", "numpy"}} 
Output: master, numpy, tensorflow 

master --> ubuntu
 ^
 |
 |
 numpy <--- tensorflow 

Example 2: 
{{"python", "numpy"}, {"numpy", "python"}, {"tensorflow", "ubuntu"}} 
Output: tensorflow 

 python --> numpy 
   ^         |
   |         |
   -----------   

Example 3: 
{{"b", "c"}, {"c", "d"}, {"a", "b"}, {"d", "e"}, {"e","c"}, {"f", "g"}} 
Ouput: f
		  ______________
          |            |
          v            |
	b --> c --> d --> e   --> h --> i --> j
	^
	|
	a
	
	f-->g

	 */
	public static void eliminateDeps(Pair[] deps) {
		// eliminate cycles and anyd eps involved in the cycles
		Map<String, WordNode> graph = new HashMap<>(deps.length);
		for (Pair p : deps) {
			
			WordNode node = graph.get(p.first);
			if (node == null) {
				node = new WordNode();
				node.word = p.first;
				graph.put(node.word, node);
			}

			WordNode kid = graph.get(p.second);
			if (kid == null) {
				kid = new WordNode();
				kid.word = p.second;
				graph.put(kid.word, kid);
			}
			node.kids.add(kid);
		}
		
		// 
		Set<WordNode> unsellted = new HashSet<>();
		unsellted.addAll(graph.values());
		//
		Set<WordNode> cyclic = new HashSet<>();
		while (!unsellted.isEmpty()) {
			Iterator<WordNode> iter = unsellted.iterator();
			WordNode root = iter.next();
			LinkedList<WordNode> path = new LinkedList<>();

			boolean hasCycle = hasCycle(root, path, cyclic);
			// remove all the nodes in this path as we've already dealt with them
			unsellted.removeAll(path);

			if (hasCycle) {
				// dont do anything
				cyclic.addAll(path);
			}
			else {
			// root --> x --> target
			// (root depends on x, on target)
			// need to build on the opposite direction
			// skipping the last (which presumably is leaf)
			path.removeLast();
				Iterator<WordNode> reverse = path.descendingIterator();
				while (reverse.hasNext()) {
					System.out.print(reverse.next().word + " <-- ");
				}
				System.out.println();
			}
			
		}
	}
	
	static boolean hasCycle(WordNode root, List<WordNode> path, Set<WordNode> cyclic){
		// return a path in "path"
		// simple bfs
		
		LinkedList<WordNode> queue = new LinkedList<>();
		queue.add(root);
		
		while(!queue.isEmpty()) {
			WordNode cur = queue.removeFirst();
			if (cyclic.contains(cur)) {
				// return, dont do anything
				return true;
			}
			else if (cur.visited) {
				// see a cycle!
				// stop here
				return true;
			}
			else {
				cur.visited = true;
				path.add(cur);
				
				for (WordNode k : cur.kids) {
					queue.addLast(k);
				}
			}
		}
		
		return false;
	}
	
	public static class WordNode {
		public int dist = Integer.MAX_VALUE;
		public String word;
		final public Set<WordNode> kids = new HashSet<>();
		public boolean visited = false;
		public LinkedList<WordNode> path = new LinkedList<>();
		public WordNode pred = null;
		public boolean includeKids = true;
		
		
		@Override
		public String toString() {
			StringBuilder bd = new StringBuilder(word);
			bd.append("-->[");
			for (WordNode w : kids) {
				bd.append(w.word).append(", ");
			}
			bd.append("]");
			return bd.toString();
		}
		
		@Override
		public int hashCode() {
			return word.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			return o != null && o instanceof WordNode && ((WordNode)o).word.equals(word);
		}
	}
	
	static WordNode getShortestDist(Set<WordNode> q) {
		Iterator<WordNode> iter = q.iterator();
		WordNode min = iter.next();
		
		while (iter.hasNext()) {
			WordNode cur = iter.next();
			if (cur.dist < min.dist) {
				min = cur;
			}
		}
		q.remove(min);
		return min;
	}
	public static void findShortestPaht(Map<String, WordNode> g, String start, String end) {
		for (WordNode node : g.values()) {
			node.visited = false;
		}
		
		Set<WordNode> unsettled = new HashSet<>();
		final int weight = 1;
		WordNode startNode = g.get(start);
		startNode.dist = 0;
		unsettled.add(startNode);
		
		while (!unsettled.isEmpty()) {
			WordNode cur = getShortestDist(unsettled);
			if (!cur.visited) {
				// update the children
				for (WordNode k : cur.kids) {
					if (!k.visited) {
						int d = cur.dist + weight;
						if (d < k.dist) {
							k.dist = d;
							
							// update path
							k.pred = cur;
						}
						unsettled.add(k);
					}
				}
				cur.visited = true;
			}
		}
	}
	
	public static Map<String, WordNode> buildGraph(Set<String> dict, String start, char[] alpha){
		WordNode startNode = new WordNode();
		startNode.word = start;
		Map<String, WordNode> g = new HashMap<>();
		g.put(start, startNode);
		
		buildGraph(dict, g, startNode, alpha);
		return g;
	}
	
	private static WordNode buildGraph(Set<String> dict, Map<String, WordNode> graph, WordNode start, char[] alphabets) {
		// build a graph 
		assert dict.contains(start.word);
		
		if (start.visited) return start;
		
		char[] str = start.word.toCharArray();

		for (int i = 0; i < str.length; ++i) {
			addChildren(dict, graph, start, str, i, alphabets);
		}
		start.visited = true;
		// now descen to children level
		for (WordNode kid : start.kids) {
			buildGraph(dict, graph, kid, alphabets);
		}
		return start;
	}
	
	static void addChildren(Set<String> dict, Map<String, WordNode> graph, WordNode start, char[] word, int i, char[] alphabets) {
		final char orig = word[i];
		for (char ch : alphabets) {
			if (ch == orig) continue;
			word[i] = ch;
			String st = new String(word);
			
			if (dict.contains(st)) {
				WordNode kid = graph.get(st);
				if (kid == null) {
					kid = new WordNode();
					kid.word = st;
					graph.put(st, kid);
				}
				
				start.kids.add(kid);
			}
		}
		word[i] = orig;
	}
	
	public static class GraphNode {
		int totalDist = Integer.MAX_VALUE;
		int value;
		boolean visited = false;
		public Map<GraphNode, Integer> childToDist = new HashMap<>();
		public GraphNode(int i) {
			value = i;
		}
		public boolean equals(Object o) {
			return o != null && o instanceof GraphNode && ((GraphNode)o).value == value;
		}
		
		public int hashCode() {
			return value;
		}
		
		@Override
		public String toString() {
			StringBuilder bd = new StringBuilder("\n");
			bd.append((char)value).append("\n");
			for (Map.Entry<GraphNode, Integer> entry : childToDist.entrySet()) {
				bd.append("  --").append(entry.getValue()).append("-->").append((char)entry.getKey().value);
				bd.append("\n");
			}
			bd.append("\n");
			
			return bd.toString();
		}
	}
	
	public void bfs(GraphNode node) {
		LinkedList<GraphNode> unsettled = new LinkedList<>();
		unsettled.add(node);
		
		while (!unsettled.isEmpty()) {
			GraphNode cur = unsettled.getFirst();
			if (!cur.visited) {
				cur.visited = true;
				for (GraphNode child : cur.childToDist.keySet()) {
					unsettled.addLast(child);
				}
			}
		}
	}
	
	public static void dfs(GraphNode node) {
		Stack<GraphNode> unsettled = new Stack<>();
		unsettled.push(node);
		
		
	}
	static GraphNode findWithMinDist(Set<GraphNode> set) {
		int minDist = Integer.MAX_VALUE;
		GraphNode min = null;
		for (GraphNode node : set) {
			if (node.totalDist <= minDist) {
				min = node;
				minDist = node.totalDist;
			}
		}
		
		return min;
	}

	public void dijstra(GraphNode root, GraphNode target) {
		// visited
		Set<GraphNode> visited = new HashSet<>();
		// unvisited
		Set<GraphNode> unsettled = new HashSet<>();
		root.totalDist = 0;
		unsettled.add(root);
		
		while (!unsettled.isEmpty()) {
			GraphNode nearest = findWithMinDist(unsettled);
			visited.add(nearest);
			unsettled.remove(nearest);
			// update dist
			for (Map.Entry<GraphNode, Integer> entry : nearest.childToDist.entrySet()) {
				if (entry.getKey().totalDist > entry.getValue() + nearest.totalDist) {
					entry.getKey().totalDist = entry.getValue() + nearest.totalDist;
				}
				unsettled.add(entry.getKey());
			}
		}
	}
	
	static class Edge {
		GraphNode start;
		GraphNode end;
		int dist;
		
		Edge(GraphNode s, GraphNode e, int d) {
			start = s;
			end = e;
			dist = d;
		}
	}
	
	static void addChildren(GraphNode inGraphNode, GraphNode node, PriorityQueue<Edge> queue) {
		// skip children if already visited
		for (Map.Entry<GraphNode, Integer> entry : node.childToDist.entrySet()) {
			if (!entry.getKey().visited) {
				queue.add(new Edge(inGraphNode, entry.getKey(), entry.getValue()));
			}
		}
		
	}
	static public Set<GraphNode> prim(Map<GraphNode, GraphNode> g, GraphNode start) {
		Set<GraphNode> tree = new HashSet<>();
		GraphNode startNode = new GraphNode(start.value);
		startNode.visited = true;
		start.visited = true;
		tree.add(startNode);
		
		PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.dist - o2.dist;
			}
		});
		addChildren(startNode, start, queue);
		
		while(tree.size() != g.size()) {
			Edge e = queue.poll();
			if (!e.end.visited){
				e.end.visited = true;
				GraphNode newNode = new GraphNode(e.end.value);
				tree.add(newNode);
				// mark the edge on parent
				e.start.childToDist.put(newNode, e.dist);
				addChildren(newNode, e.end, queue);
			}
		}
		
		return tree;
		
	}
	
	public static class Point {
		public final int x;
		public final int y;
		final int hash;
		int distance = Integer.MAX_VALUE;
		Point parent = null;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
			
			int h = 17;
			h = h * 31 + x;
			h = h * 31 + y;
			hash = h;
		}
		
		public boolean equals(Object o) {
			if (o != null && o instanceof Point) {
				Point other = (Point) o;
				return other.x == x && other.y == y;
			}
			else {
				return false;
			}
		}
		
		public int hashCode() {
			return hash;
		}
		
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
	
	static final int PASSABLE = 0;
	static final int BLOCKED = 1;
	
	public static List<Point> shortestPath(int[][] m, Point start, Point end) {
		// build a Set of points
		Map<Point, Point> unvisited = new HashMap<>(m.length * m[0].length);
		
		for (int x = 0; x < m.length; ++x) {
			for (int y = 0; y < m[x].length; ++y) {
				if (m[x][y] == PASSABLE) {
					Point p = new Point(x,y);
					unvisited.put(p, p);
				}
			}
		}
		
		LinkedList<Point> path = new LinkedList<>();
		end = unvisited.get(end);
		
		if (true) {
		//  djstra
		start.distance = 0;
		Set<Point> unsettled = new HashSet<>();
		unsettled.add(start);
		while (!unsettled.isEmpty()) {
			System.out.println(unsettled);
			// nearest
			Point nearest = removeNearest(unsettled);
			if (unvisited.containsKey(nearest)) {
				unvisited.remove(nearest);
				//path.add(nearest);
				
				//System.out.println("nearest: " + nearest);
				if (nearest.equals(end)) break;
				
				//final int newDist = nearest.distance;
				// update children
				visitChild(m, nearest, nearest.x - 1, nearest.y, unvisited, unsettled);
				visitChild(m, nearest, nearest.x + 1, nearest.y, unvisited, unsettled);
				visitChild(m, nearest, nearest.x, nearest.y + 1, unvisited, unsettled);
				visitChild(m, nearest, nearest.x, nearest.y - 1, unvisited, unsettled);
				
				// diagonal
				visitChild(m, nearest, nearest.x -1, nearest.y - 1, unvisited, unsettled);
				visitChild(m, nearest, nearest.x -1, nearest.y +1 , unvisited, unsettled);
				visitChild(m, nearest, nearest.x +1, nearest.y - 1, unvisited, unsettled);
				visitChild(m, nearest, nearest.x +1, nearest.y +1, unvisited, unsettled);
			}
		}
		
		System.out.println("end.parent: " + end.parent);
		// find thte path
		Point next = end;
		while (next != null) {
			path.addFirst(next);
			next = next.parent;
		}
		
		}
		
		
		// bfs
		if (false) {
		LinkedList<Point> unsettled = new LinkedList<>();
		unsettled.add(start);
		Point next = null;
		while (!unsettled.isEmpty()) {
		//	System.out.println("unsettled: " + unsettled);
			next = unsettled.removeFirst();
			
			if (unvisited.containsKey(next)) {
				// mark as visited
				unvisited.remove(next);
				if (next.equals(end)) {
					break;
				}
				// visit kid
				visitKid(m, next.x - 1, next.y, next, unsettled, unvisited);
				visitKid(m, next.x + 1, next.y, next, unsettled, unvisited);
				visitKid(m, next.x, next.y -1, next, unsettled, unvisited);
				visitKid(m, next.x, next.y +1, next, unsettled, unvisited);
				
				visitKid(m, next.x-1, next.y -1, next, unsettled, unvisited);
				visitKid(m, next.x-1, next.y +1, next, unsettled, unvisited);
				visitKid(m, next.x+1, next.y -1, next, unsettled, unvisited);
				visitKid(m, next.x+1, next.y +1, next, unsettled, unvisited);
			}
		}
		
		// find thte path
		while (next != null) {
			path.addFirst(next);
			next = next.parent;
		}
		
		}
		System.out.println("path: " + path);
		return path;
	
		
	}
	static void visitKid(int[][] m, int x, int y, Point pred, LinkedList<Point> unsettled, Map<Point, Point> unvisited) {
		if (x >= 0 && x < m.length && y >= 0 && y < m[x].length && m[x][y] == PASSABLE) {
			// only add to list if hasn't been visited
			Point point = new Point(x, y);
			if (unvisited.containsKey(point)) {
				// want the original object
				point = unvisited.get(point);
				point.parent = pred;
				unsettled.addLast(point);
			}
		}
	}
	
	static void visitChild(int[][] m, Point parent, int x, int y, Map<Point, Point> unvisited, Set<Point> unsettled) {
		if (x >= 0 && x < m.length && y >= 0 && y < m[x].length && m[x][y] == PASSABLE) {
			Point p = new Point(x,y);
			p = unvisited.get(p);
			if (p != null) {
				//update the distance
				if (p.distance > parent.distance + 1) {
					p.distance = parent.distance + 1;
					unvisited.put(p, p);
					if (unsettled.contains(p)) {
						unsettled.remove(p);
					}
					unsettled.add(p);
					p.parent = parent;
				}
			}
		}
	}
	static Point removeNearest (Set<Point> points) {
		Iterator<Point> iter = points.iterator();
		Point min = iter.next();
		while (iter.hasNext()) {
			Point cur = iter.next();
			if (cur.distance < min.distance) {
				min = cur;
			}
		}
		points.remove(min);
		return min;
	}
}
