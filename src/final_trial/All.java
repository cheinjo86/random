package final_trial;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class All {

	
	// graph traversals
	// fill color
	public static void fillColor(final int[][] matrix, int x, int y, int newColor) {
		class Cell {
			final int x;
			final int y;
			final int hash;
			Cell(int x, int y) {
				this.x = x;
				this.y = y;
				hash = x * 31 + y;
			}
			
			@Override
			public boolean equals(Object o) {
				return o != null && o instanceof Cell && ((Cell)o).x == x && ((Cell)o).y == y;
			}
			
			@Override
			public int hashCode() {
				return hash;
			}
			
			boolean isValid(int x, int y) {
				return x >= 0 && x < matrix.length && y >= 0 && y < matrix[x].length;
			}
			
			void addChild(final int OLD, int x, int y, LinkedList<Cell> queue, Set<Cell> set) {
				if (isValid(x,y) 
						&& matrix[x][y] == OLD) {
					Cell kid = new Cell(x,y);
					if (!set.contains(kid)) {
						queue.addLast(kid);
					}
				}
			}
		}
		// do bfs from the starting point
		Cell start = new Cell(x,y);
		if (start.isValid(x, y)) return;
		
		final int OLD_COLOR = matrix[x][y];
		
		Set<Cell> visited = new HashSet<>();
		LinkedList<Cell> queue = new LinkedList<>();
		queue.addFirst(start);
		
		while (!queue.isEmpty()) {
			Cell cur = queue.removeFirst();
			if (!visited.contains(cur)) {
				visited.add(cur);
				assert matrix[cur.x][cur.y] == OLD_COLOR : "seeing something else: ";
				matrix[cur.x][cur.y] = newColor;
				
				// add children
				cur.addChild(OLD_COLOR, cur.x - 1, cur.y, queue, visited);
				cur.addChild(OLD_COLOR, cur.x + 1, cur.y, queue, visited);
				cur.addChild(OLD_COLOR, cur.x, cur.y -1, queue, visited);
				cur.addChild(OLD_COLOR, cur.x, cur.y + 1, queue, visited);
			}
		}
	}
	
	static class Cell {
		final int x;
		final int y;
		final int hash;
		Cell(int x, int y) {
			this.x = x;
			this.y = y;
			hash = hashCode(x,y);
		}
		
		static int hashCode(int x, int y) {
			return x * 31 + y;
		}
		
		// TODO: eq and hashcode
	}
	
	static Set<Cell> buildCells(int[][] matrix) {
		Set<Cell> ret = new HashSet<>();

		for (int x = 0; x < matrix.length; ++x) {
			for (int y = 0; y < matrix[x].length; ++y) {
				ret.add(new Cell(x,y));
			}
		}
		
		return ret;
	}
	
	public static void addChildren(int x, int y, Set<Cell> unsettled, LinkedList<Cell> q, int[][] matrix, final int color) {
		if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[x].length && matrix[x][y] == color) {
			Cell kid = new Cell(x,y);
			if (unsettled.contains(kid)) {
				q.addLast(kid);
			}
		}
	}
	public static void countColors(int[][] matrix) {
		// color --> list of sizes
		Map<Integer, List<Integer>> ret = new HashMap<>();
		
		Set<Cell> unsettled = buildCells(matrix);
		
		while (!unsettled.isEmpty()) {
			// pick a random start
			Cell start = unsettled.iterator().next();
			LinkedList<Cell> q = new LinkedList<>();
			q.add(start);
			final int color = matrix[start.x][start.y];
			int count = 0;
			while (!q.isEmpty()) {
				Cell cur = q.removeFirst();
				if (unsettled.contains(cur)) {
					unsettled.remove(cur);
					++count;
					
					// visit children
					addChildren(cur.x - 1, cur.y, unsettled, q, matrix, color);
					addChildren(cur.x + 1, cur.y, unsettled, q, matrix, color);
					addChildren(cur.x, cur.y - 1, unsettled, q, matrix, color);
					addChildren(cur.x, cur.y - 1, unsettled, q, matrix, color);
				}
			}
			
			// report color
			List<Integer> sizes = ret.get(color);
			if (sizes == null) {
				ret.put(color, sizes = new LinkedList<>());
			}
			sizes.add(count);
		}
	}
	
	// dijstra
	static class GraphNode {
		GraphNode pred = null;
		
		final int x;
		final int y;
		final Map<GraphNode, GraphNode> children = new HashMap<>();
		GraphNode(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static void addKid(int x, int y, int[][] dis, int newD, GraphNode pred, PriorityQueue<GraphNode> q ) {
		if (x >= 0 && x < dis.length && y >= 0 && y < dis[x].length && dis[x][y] > newD) {
			GraphNode kid = new GraphNode(x,y);
			if (q.contains(kid)) {
				// removej it to force re-heapify
				q.remove(kid);
			}
			
			dis[x][y] = newD;
			kid.pred = pred;
			q.add(kid);
		}
	}
	static public List<GraphNode> shortestPath(int[][] matrix, int x, int y, int a, int b) {
		int[][] dis = new int[matrix.length][matrix[0].length];
		
		GraphNode start = new GraphNode(x,y);
		GraphNode end = new GraphNode(a, b);
		
		PriorityQueue<GraphNode> q = new PriorityQueue<>(new Comparator<GraphNode>(){
			@Override
			public int compare(GraphNode o1, GraphNode o2) {
				return Integer.compare(dis[o1.x][o1.y], dis[o2.x][o2.y]);
			}
		});
		
		Set<GraphNode> visited = new HashSet<>();
		
		dis[x][y] = 0;
		q.add(start);
		
		while (!q.isEmpty()) {
			GraphNode cur = q.poll();
			
			if (!visited.contains(cur)) {
				visited.add(cur);
				
				if (cur.equals(end)) {
					break;
				}
				
				// visit children
				final int newDist = dis[cur.x][cur.y] + 1;
				addKid(cur.x - 1, cur.y, dis, newDist, cur, q);
				// ...
				
			}
		}
		
		LinkedList<GraphNode> path = new LinkedList<>();
		GraphNode cur = end;
		while (cur != null) {
			path.addFirst(cur);
			cur = cur.pred;
		}
		
		return path;
	}
	static interface Dict {
		/**
		 * return null if word not valid
		 * @param text
		 * @param low
		 * @param high
		 * @return
		 */
		String buildWord(char[] text, int low, int high);
		char[] getAlphabet();
	}
	
	public static void parseMeaning(char[] text, Dict dict) { 
		List<String>[][] meanings = new List[text.length][text.length];
		
		// find all possible words
		for (int len = 1; len <= text.length; ++len) {
			int maxI = text.length - len;
			for (int i = 0; i <= maxI; ++i) {
				final int j = i + len - 1;
				String word = dict.buildWord(text, i, j);
				if (word != null) {
					meanings[i][j] = new LinkedList<>();
					meanings[i][j].add(word);
				}
			}
		}
		
		// find all intepretations
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < i; ++j) {
				List<String> left = meanings[0][j];
				List<String> right = meanings[j+1][i];
				
				if (left != null && right != null) {
					List<String> combined = meanings[0][i];
					if (combined == null) {
						meanings[0][i] = combined = new LinkedList<>();
					}
					for (String l : left) {
						for (String r : right) {
							combined.add(l + " " + r);
						}
					}
				}
			}
		}
	}

	
	static class Pair {
		final char start;
		final char end;
		
		Pair(char a, char b) {
			start = a;
			end = b;
		}
	}
	/*
	 *    ______________
          |            |
          v            |
	b --> c --> d --> e   --> h --> i --> j
	^
	|
	a
	
	f-->g
	 */
	static class CharNode {
		boolean visited = false;
		final char label;
		// represent dependencies
		Map<Character, CharNode> children = new HashMap<>();
		
		CharNode (char c) {
			label = c;
		}
	}
	
	static Map<Character, CharNode> buildGraph(Pair[] pairs) {
		Map<Character, CharNode> g = new HashMap<>();
		
		for (Pair p : pairs) {
			CharNode start = g.get(p.start);
			CharNode end = g.get(p.end);
			
			if (start == null) {
				g.put(p.start, new CharNode(p.start));
			}
			
			if (end == null) {
				g.put(p.end, new CharNode(p.end));
			}
			
			start.children.put(end.label, end);
		}
		
		return g;
	}
	public static void eliminateDeps(Pair pairs[]) {
		// build a graph
		Map<Character, CharNode> g = buildGraph(pairs);
		
		// do traversal to find all the cycles
		Set<Character> unsettled = new HashSet<>(g.size());
		unsettled.addAll(g.keySet());
		
		Set<Character> cyclic = new HashSet<>();
		while (!unsettled.isEmpty()) {
			//  pick a random first node
			CharNode startNode = g.get(unsettled.iterator().next());
			
			LinkedList<Character> path = new LinkedList<>();
			boolean hasCycle = !traverse(startNode, path, cyclic);
			unsettled.removeAll(path);
			
			if (!hasCycle) {
				// print the dependencies
				Iterator<Character> reversed = path.descendingIterator();
				while (reversed.hasNext()) {
					System.out.print(reversed.next() + " ");
				}
			}
		}
	}
	
	static boolean traverse(CharNode start, LinkedList<Character> path, Set<Character> cyclic) {
		// bfs
		
		LinkedList<CharNode> q = new LinkedList<>();
		q.add(start);
		
		while (!q.isEmpty()) {
			CharNode cur = q.removeFirst();
			
			if (cur.visited || cyclic.contains(cur.label)) {
				cyclic.addAll(path);
				// see a cycle
				return false;
			}
			else {
				path.addLast(cur.label);
				cur.visited = true;
				
				// visit children
				for (CharNode kid : cur.children.values()) {
					q.addLast(kid);
				}
			}
		}
		
		return true;
	}
	public static int findKSmallest(int[] nums, int k) {
		if (k > nums.length || k < 1) throw new RuntimeException("illegal arg");
		
		return orderStats(nums, k - 1,0, nums.length - 1);
	}
	
	public static int findkLargest(int[] nums, int k) {
		if (k > nums.length || k < 1) throw new RuntimeException("illegal arg");
		
		return orderStats(nums, nums.length- k,0, nums.length - 1);
	}
	
	public static int orderStats(int[] nums, int k, int low, int high) {
		assert k >= low && k <= high;
		
		// k is 0-based
		int pIdx = partition(nums, low, high);
		if (pIdx == k) return nums[k];
		else if (pIdx < k) return orderStats(nums, k, pIdx + 1, high);
		else return orderStats(nums, k , low, pIdx - 1);
	}
	static void swap(int[] nums, int i, int j) {
		int t = nums[i];
		nums[i] = nums[j];
		nums[j] = t;
	}
	static int partition(int[] nums, int low, int high) {
		int pivot = nums[high];
		int i = low -1;
		for (int j = low; j < high; ++j) {
			if (nums[j] <= pivot) {
				++i;
				swap(nums, i, j);
			}
		}
		
		++i;
		swap(nums, i, high);
		return i;
	}
}
