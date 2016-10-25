package past;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class First {

	public static class Node {
		public final String label;
		public Node left;
		public Node right;
		
		public Node(String l) {
			label = l;
		}
	}
	
	// First
	// print tree in level
	public static void levelTraversal(Node root) {
		if (root == null) {
			// no tree to print
			return;
		}
		
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size-- > 0) {
				Node cur = queue.removeFirst();
				System.out.print(cur.label + ", ");
				
				if (cur.left != null) {
					queue.addLast(cur.left);
				}
				
				if (cur.right != null) {
					queue.addLast(cur.right);
				}
			}
		}
	}
	
	// second
	
	// third
	// design uestion, fairly easy
	
	// fourth (last)
	// print edge top-down (left edge), across (left-right), bottom-up (right edge)
	public static void printTriangleTree(Node root) {
		if (root == null) {
			// nothing to print
			return;
		}
		
		// left
		Node n = root;
		// while haven't reached the root node
		System.out.print("left: ");
		if (root.left == null) {
			System.out.print(root.label);
		}
		else {
			while (n.left != null) {
				// but skip the left most leaf, since will be part of the bottom
				if (n.left == null && n.right == null) {
					break;
				}
				System.out.print(n.label + " ");
				n = n.left;
			}
		}
		System.out.println();
		
		// across
		// could do post order (l-p-r)
		System.out.print("across: ");
		postOrderLeavesOnly(root);
		System.out.println();
		
		// ride
		// use a stack
		Stack<Node> stack = new Stack<>();
		n = root.right;
		System.out.print("right: ");
		while (n != null) {
			stack.push(n);
			n = n.right;
		}
		
		
		// pop the right most since it was part of the bottom
		if (!stack.isEmpty()) {
			stack.pop();
		}
		
	
		while (!stack.isEmpty()) {
			System.out.print(stack.pop().label + " ");
		}
		System.out.println();
	}
	
	private static void postOrderLeavesOnly(Node root) {
		if (root == null) return;
		
		postOrderLeavesOnly(root.left);
		postOrderLeavesOnly(root.right);
		
		// only print if it's the leaf
		if (root.left == null && root.right == null) {
			System.out.print(root.label + " ");
		}

	}
	
	// -------------------- redo-round -------------------------------
	// first: fill square
	public static void fillSquare(int[][] m, int x, int y, int newColor) {
		final int origColor = m[x][y];
		
		// just bfs (or dfs) 
		class Cell {
			int x;
			int y;
			Cell(int x, int y) {
				this.x = x;
				this.y = y;
			}
			
			void addCell(int x, int y, LinkedList<Cell> queue) {
				if (x > 0 && x < m.length && y > 0 && y < m[0].length && m[x][y] == origColor) {
					//System.out.println("adding [x,y] = [" + x + ", " + y + "]");
					queue.addLast(new Cell(x,y));
				}
			}
			
			@Override
			public boolean equals(Object o) {
				Cell c = (Cell)o;
				return c.x == x && c.y == y;
			}
			
			@Override
			public int hashCode(){
				return x * 37 + y;
			}
			
			public String toString() {
				return "[" + x + ", " + y + "] = " + m[x][y];
			}
		}
		Set<Cell> visited = new HashSet<>(m.length * m[0].length);
		
		LinkedList<Cell> queue = new LinkedList<>();
		final Cell start = new Cell(x,y);
		
		queue.add(start);
		
		while (!queue.isEmpty()) {
			Cell cur = queue.removeFirst();
			if (!visited.contains(cur)) {
				// change the color
				m[cur.x][cur.y]= newColor;
				
				visited.add(cur);
				start.addCell(cur.x - 1, cur.y, queue);
				start.addCell(cur.x + 1, cur.y, queue);
				start.addCell(cur.x, cur.y - 1, queue);
				start.addCell(cur.x, cur.y + 1, queue);

			}
		}
	}
	
	// second: find common ancessor (?)
	
	// third:
	// find mode
	
}
