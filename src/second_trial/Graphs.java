package second_trial;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import second_trial.DynamicProgramming.Dict;

public class Graphs {

		public static void dfs(WordNode root) {
			if (root != null && !root.visited) {
				root.visited = true;
				for (WordNode kid : root.children) {
					dfs(kid);
				}
				System.out.print(root + " ");
			}
		}

		public static Iterator<WordNode> dfsIterator(WordNode root) {
			final Stack<WordNode> stack = new Stack<>();
			stack.push(root);
			Iterator<WordNode> iter = new Iterator<WordNode>(){
				WordNode next = null;
				@Override
				public boolean hasNext() {
					while (next == null && !stack.isEmpty()) {
						next = stack.pop();
						if (next.children.isEmpty() || childrenVisisted(next)) {
							break;
						}
						else {
							stack.push(next);
							for (WordNode kid : next.children) {
								if (!kid.visited && !kid.inQueue) {
									kid.inQueue = true;
									stack.push(kid);
								}
							}
							next = null;
						}
					}
					
					return next != null;
				}

				@Override
				public WordNode next() {
					if (next == null && !hasNext()) {
						throw new NoSuchElementException();
					}
					WordNode ret = next;
					next = null;
					return ret;
				}
				
			};
			
			return iter;
		}
		
		public static void dfsIter(WordNode root) {
			Stack<WordNode> stack = new Stack<>();
			WordNode cur = root;
			
			while (cur != null || !stack.isEmpty()) {
				if (cur == null) {
					stack.pop();
				}
				else {
					if (cur.visited) {
						cur = null;
					}
					else {
						// leaf node or children already in queue
						if (cur.children.isEmpty() || childrenVisisted(cur)) {
							// visit
							System.out.print(cur + " ");
							cur.visited = true;
						}
						else {
							// put back
							stack.push(cur);
							// cur = first child
							// remove child fro mlist
							for (WordNode kid : cur.children) {
								if (!kid.visited && !kid.inQueue) {
									cur = kid;
									cur.inQueue = true;
									break;
								}
							}
						}
					}
				}
				
				// this algo push ALL the kid on the queue ....
				// that could result in bfs???
//				if (cur != null && !cur.visited) {
//					// leaf node or all children have been visisted
//					if (cur.children.isEmpty() || childrenVisisted(cur)) {
//						System.out.print(cur + " ");
//						cur.visited = true;
//					}
//					else {
//						// put it back
//						stack.push(cur);
//						
//						// orders of children doesn't matter but
//						// if we want the same order as recursive dfs
//						// then push the children in reverse order 
//						for (WordNode child :cur.children) {
//							if (!child.visited && !child.inQueue) {
//								stack.push(child);
//								child.inQueue = true;
//							}
//						}
//					}
//					
//				}
			}
			
			System.out.println();
		}
		
		public static void generateMaze(int m, int n)
		{
			final int WALL = 0;
			final int PATH = 1;
			// 0 means wall, 1
			int[][] maze = new int[m][n];
			boolean[][] visited = new boolean[m][n];
			Stack<Cell> stack = new Stack<>();
			Cell cur = new Cell(0,0);
			
			while (cur != null || !stack.isEmpty()) {
				if (cur == null) {
					stack.pop();
				}
				else {
					// choose a random unvisited neighbour
					Cell neigh = chooseRandomNeigh(cur, visited);
					if (neigh == null) {
						neigh = cur;
						cur = null;
					}
					else {
						stack.push(cur);
						cur = neigh;
					}

					maze[neigh.x][neigh.y]= neigh.wall;
					visited[neigh.x][neigh.y] = true;
				}
			}
			
			// print maze
			for (int[] row : maze) {
				
				for (int i : row) {
					System.out.print(" ");
					if (i != 0) {
						System.out.print((char)(i));
					}
					else {
						System.out.print(".");
					}
					//System.out.print(i + " ");
				}
				System.out.println();
			}
			
			System.out.println("----------------------");
		}
		
		static void addChild(boolean[][] visited, int x, int y, List<Cell> cells, char ch) {
			if (x >= 0 && y >= 0 && x < visited.length && y < visited[x].length && !visited[x][y]) {
				Cell c = new Cell(x, y);
				c.wall = ch;
				cells.add(c);
			}
		}
		static final Random rand = new Random();
		public static Cell chooseRandomNeigh(Cell cur, boolean[][] visited) {
			List<Cell> neighs = new ArrayList<>(4);
			
			// left, right, bottom, top
			addChild(visited, cur.x, cur.y - 1, neighs, L);
			addChild(visited, cur.x, cur.y + 1, neighs, R);
			addChild(visited, cur.x - 1, cur.y , neighs, T);
			addChild(visited, cur.x + 1, cur.y, neighs, B);
			if (neighs.isEmpty()) return null;
			return neighs.get(rand.nextInt(neighs.size()));
		}
		
		
		static boolean childrenVisisted(WordNode node) {
			for (WordNode child : node.children) {
				if (!child.visited && !child.inQueue) return false;
			}
			return true;
		}
	//
	public static class WordNode {
		public int dist = Integer.MAX_VALUE;
		public boolean visited = false;
		public boolean inQueue = false;
		WordNode prev = null;
		
		final String word;
		public final List<WordNode> children = new LinkedList<>();
		WordNode(String st) {
			word = st;
		}
		
		public String toString() {
			return word;
		}
		
		public boolean equals(Object o) {
			return o != null && o instanceof WordNode && ((WordNode)o).word.equals(word);
		}
		
		public int hashCode() {
			return word.hashCode();
		}
	}
	public static List<String> transformWord(String start, String end, Dict dict) {
		// build a graph starting at start,
		// each node is a transformation of the first by 1 change
		// add, remove or replace
		Map<String, WordNode> g = buildGraph(start, dict);
		// either word is not in the graph
		
		return shortestPath(g, start, end);
		
	}
	
	public static List<String> shortestPath(Map<String, WordNode> graph, String start, String end) {
		// disjstra
		if (!graph.containsKey(start) || !graph.containsKey(end)) return null;

		
		WordNode startNode = graph.get(start);
		startNode.dist = 0;
		PriorityQueue<WordNode> q = new PriorityQueue<>(new Comparator<WordNode>() {

			@Override
			public int compare(WordNode o1, WordNode o2) {
				return o1.dist - o2.dist;
			}
		});
		
		q.add(startNode);
		final int COST = 1;
		LinkedList<String> path = new LinkedList<>();
		while (!q.isEmpty()) {
			WordNode cur = q.poll();
			if(!cur.visited) {
				cur.visited = true;

				if (cur.word.equals(end)) break;
				
				// update children
				for (WordNode kid : cur.children) {
					if (!kid.visited) {
						int newDist = Math.min(kid.dist, cur.dist + COST);
						if (newDist != kid.dist) {
							kid.prev = cur;
							// update the queue because order will change
							if (kid.inQueue) {
								q.remove(kid);
							}
							else {
								kid.inQueue = true;
							}

							kid.dist = newDist;							
							q.add(kid);
						}
					}
				}
			}
		}
				
		WordNode prev = graph.get(end);
		while (prev != null) {
			path.addFirst(prev.word);
			prev = prev.prev;
		}
		return path;
	}
	
	public static Map<String, WordNode> buildGraph(String start, Dict dict) {
		Map<String, WordNode> graph = new HashMap<>();
		WordNode startNode = doBuildGraph(graph, start, dict);
		if (startNode == null) {
			return null;
		}
		else {
			return graph;
		}
	}
	
	public static WordNode doBuildGraph(Map<String, WordNode> graph, String word, Dict dict) {
		if (word.isEmpty() || !dict.containWord(word)) return null;

		WordNode node = graph.get(word);
		if (node == null) {
			graph.put(word, node = new WordNode(word));
			
			// replae ONE letter at a time
			char[] chars = word.toCharArray();
			for (int i = 0; i < chars.length; ++i) {
				final char orig = chars[i];
				for (Character ch : dict.getAlphabet()) {
					if (ch != orig) {
						chars[i] = ch;
						WordNode child = doBuildGraph(graph, new String(chars), dict);
						if (child != null) {
							node.children.add(child);
						}
					}
				}
				chars[i] = orig;
			}
			
			// delete one letter at a time
			//if (false)
			for (int i = 0; i < chars.length; ++i) {
				String left = "";
				if (i > 0) {
					left = word.substring(0, i-1);
				}
				
				if (i < chars.length - 1) {
					left += word.substring(i+1);
				}
				WordNode child = doBuildGraph(graph, left, dict);
				if (child != null) {
					node.children.add(child);
				}
			}
			
			// insert
		}
		
		return node;
		
	}
	public static void countIslandsSize(int[][] map, int target) {
		
		// size of each island
		List<Integer> sizes = new LinkedList<>();
		boolean[][] visited = new boolean[map.length][map.length];
		int hintedX = -1;
		int hintedY = -1;
		boolean done = false;
		while (!done) {
			// find the start point
			int x = 0;
			int y = 0;
			if (hintedX > 0 && hintedY > 0 && map[hintedX][hintedY] == target) {
				x = hintedX;
				y = hintedY;
			}
			
			// search for it
			for(; x < map.length; ++x) {
				for (; y < map[x].length; ++y) {
					if (!visited[x][y] && map[x][y] == target) {
						break;
					}
				}
			}
			
			// bfs
			LinkedList<int[]> queue = new LinkedList<>();
			queue.addFirst(new int[]{x,y});
			int count = 0;
			while (!queue.isEmpty()) {
				int[] point = queue.removeFirst();
				if (!visited[point[0]][point[1]]) {
					visited[point[0]][point[y]] = true;
					++count;
					
					addChild(visited, map, target, queue, x-1,y);
					addChild(visited, map, target, queue, x+1,y);
					addChild(visited, map, target, queue, x,y-1);
					addChild(visited, map, target, queue, x,y+1);
				}
				
				// if at the last level ?
				if (queue.size() == 1) {
					int[] p = queue.peek();
					for (int[] newP : new int[][] {{p[0] -1, p[1]},
						                            {p[0] -1, p[1] - 1},
						                            {p[0] +1, p[1] - 1},
						                            {p[0] +1, p[1] + 1},
						                            {p[0], p[1] + 1}}) {
						if (isTarget(map, visited, target, newP[0], newP[1])) {
							hintedX = newP[0];
							hintedY = newP[1];
							break;
						}
					}
				}
			}
			
			sizes.add(count);
		}
	}
	
	static boolean isTarget(int[][] map, boolean[][] visited, int target, int x, int y) {
		return x >= 0 && x < map.length && y >= 0 && y < map[x].length
				&& !visited[x][y] && map[x][y] == target;
	}
	
	static void addChild(boolean[][] visited, int[][] map, int target, List<int[]> q, int x, int y) {
		if (x >= 0 && x < map.length && y >= 0 && y < map[x].length
				&& !visited[x][y] && map[x][y] == target) {
			q.add(new int[] {x,y});
		}
	}
	static Set<Cell> buildGraph(int[][] map) {
		Set<Cell> ret = new HashSet<>(map.length * map[0].length);
		for (int x = 0; x < map.length; ++x) {
			for (int y = 0; y < map[x].length; ++y) {
				ret.add(new Cell(x, y, map[x][y]));
			}
		}
		
		return ret;
	}
	
	static char L = '|';
	static char R = '|';
	static char T = '_';
	static char B = '_';
	
	static class Cell {
		char wall;
		final int x;
		final int y;
		final int value;
		final int hash;
		boolean visited;
		public Cell(int x, int y) {
			this(x,y, -1);
		}
		public Cell(int x , int y, int v) {
			this.x = x;
			this.y = y;
			this.value = v;
			visited = false;
			hash = x * 37 + y;
		}
		
		public int hashCode() {
			return hash;
		}
		
		public boolean equals(Object o){
			return o != null && o instanceof Cell && ((Cell)o).x == x && ((Cell)o).y == y;
		}
	}
	public static class GraphNode {
		boolean visited = false;
		
		final char label;
		final Map<GraphNode, Integer> childrenToCosts = new HashMap<>();
		GraphNode(char c) {
			label = c;
		}
		
		public boolean equals(Object o) {
			return o != null && o instanceof GraphNode && ((GraphNode)o).label == label;
		}
		
		public int hashCode() {
			return label;
		}
	}

	static class Edge implements Comparable<Edge>{
		GraphNode start;
		GraphNode end;
		int cost;
		Edge (GraphNode s, GraphNode e, int d) {
			start =s;
			end = e;
			cost = d;
		}
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(cost, o.cost);
		}
	}
	
	static void addChildren(GraphNode newNode, GraphNode originalNode, PriorityQueue<Edge> q ) {
		for (Map.Entry<GraphNode, Integer> entry : originalNode.childrenToCosts.entrySet()) {
			q.add(new Edge(newNode, entry.getKey(), entry.getValue()));
		}
	}
	public static void prim(Map<Character, GraphNode> graph, GraphNode start) {
		// put start to queue
		// while graph is not full
		//   
		//   for all children that are NOT already in graph, pick the one with loest cost (to any node)
		//   
		
		Set<GraphNode> newGraph = new HashSet<>(graph.size());
		GraphNode startNode = new GraphNode(start.label);
		newGraph.add(startNode);
		
		PriorityQueue<Edge> q = new PriorityQueue<>();
		// init by adding children edges to the queue
		addChildren(startNode, start, q);
		
		while (newGraph.size() != graph.size()) {
			Edge shortest = q.poll();
			// if the end node is not already in graph
			if (!shortest.end.visited) {
				shortest.end.visited = true;
				
				// put it in
				GraphNode newNode = new GraphNode(shortest.end.label);
				shortest.start.childrenToCosts.put(newNode, shortest.cost);
				newGraph.add(newNode);
				
				addChildren(newNode, shortest.end, q);
				
			}
		}
		
		
	}
}
