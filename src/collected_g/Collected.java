package collected_g;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Collected {

	public static void main(String[] args) {
		testToFromArray();
	}
	// bin tree serialization 
	// tree --> array
	static class Node implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int value;
		private Node left;
		private Node right;
		public Node(int v, Node l, Node r) {
			value = v;
			left = l;
			right = r;
		}
		
		public Node(int v) {
			value = v;
		}
		
		public String toString() { return value + ""; }

	}
	
	static interface Visitor {
		void visit(Node root);
	}
	
	private static void addNode(Node parent, int leftV, int rightV) {
		parent.left = new Node(leftV); 
		parent.right = new Node(rightV);
	}
	
	public static void testCountNode(){
		// build a tree
		final Node root = new Node(0);
		
		addNode(root, 0, 1);
		
		addNode(root.left, 3, 4);
		addNode(root.left.left, 7, 8);
		addNode(root.left.right, 9, 1);
		
		addNode(root.right, 5, 6);
		addNode(root.right.right, 11, 12);
		root.right.right.right.right = new Node(14);
		root.right.right.left.left = new Node(13);
		addNode(root.right.right.left.left, 15, 16);
		
		System.out.println("Count: " + count(root));
	}
	static int count(Node root) {
		class CountVisitor implements Visitor{
			int c = 0;

			@Override
			public void visit(Node root) {
				++c;
			}
		}
		
		CountVisitor v = new CountVisitor();
		preorder(root, v);
		return v.c;
	
	}
	
	static void preorder(Node root, Visitor v) {
		if (root == null) return;
		
		v.visit(root);
		preorder(root.left, v);
		preorder(root.right, v);
	}
	
	static void inorder(Node root, Visitor v) {
		if (root == null) return;
		
		inorder(root.left, v);
		v.visit(root);
		inorder(root.right, v);
	}
	
	public static void testToFromArray() {
		// build a tree
		final Node root = new Node(0);
		
		addNode(root, 1, 2);
		
		addNode(root.left, 3, 4);
		addNode(root.left.left, 7, 8);
		addNode(root.left.right, 9, 10);
		
		addNode(root.right, 5, 6);
		addNode(root.right.right, 11, 12);
		root.right.right.right.right = new Node(14);
		root.right.right.left.left = new Node(13);
		addNode(root.right.right.left.left, 15, 16);
		
		System.out.println("Count: " + count(root));
		int h = findHeight(root);
		System.out.println("height: " + h);
		System.out.println("power sum: " + powerSum(h - 1));
		
		Node[] ar = toArray(root);
		
		printLevel(h, ar);
		
		System.out.println("resurcting from array: ...");
		
		Node newRoot = fromArray(ar);
		levelOrderTraversal(newRoot);
		
	}
	static void printLevel(int h, Node[] ar) {
		int i = 1;
		for (int n = 0; n < h; ++n) {
			int power = powerSum(n) ;
			System.out.print("h = " + n + " | ");
			while (i <= power) {
				System.out.print("[" + i + "]=(" + ar[i++] + ")  ");
			}
			System.out.println();
		}
	}
	
	static int findHeight (Node root) {
		if (root == null) return 0;
		
		return Math.max(findHeight(root.left), findHeight(root.right))+ 1; 
	}
	
	/**
	 * Sum i=0->n s^i
	 * @param n
	 * @return
	 */
	public static int powerSum(int n) {
		int ret = 1;
		while (n > 0) {
			ret += Math.pow(2, n);
			--n;
		}
		
		return ret;
	}
	
	
	static public Node[] toArray(Node root) {
		// l = 2n 
		// r = 2n + 1
		
		final int h = findHeight(root);
		// -1 because level is 0-based
		// +1 because we skip idx 0, hence needs the array to be 1 larger
		Node[] ret = new Node[powerSum(h - 1) + 1];
		doToArray(root, ret, 1);

		return ret;
	}
	
	private static void doToArray(Node parent, Node[] ret, int i) {
		if (parent == null) return;

		ret[i] = parent;
		doToArray(parent.left, ret, 2 * i);
		doToArray(parent.right, ret, 2 * i + 1);
	}
	
	public static Node fromArray(Node[] ret) {
		Node root = ret[1];
		if (root == null) return root;
		final int maxI = (ret.length - 2 ) / 2;
		
		Node parent = null;
		for (int i = 1; i <= maxI; ++i) {
			parent = ret[i];
			if (parent != null) {
				parent.left = ret[2 * i];
				parent.right = ret[2 * i + 1];
			}
		}
		
		
		return root;

		
	}
	
	// -------------------------------------------
	public static void levelOrderTraversal(Node root) {
		// just bfs;
		LinkedList<Node> q = new LinkedList<>();
		q.add(root);
		
		while(!q.isEmpty()) {
			int size = q.size();
			while (size > 0) {
				Node n = q.removeFirst();
				System.out.print(n + "  ");
				if (n.left != null) {
					q.addLast(n.left);
				}
				if (n.right != null) {
					q.addLast(n.right);
				}
				--size;
			}
			System.out.println();
		}
		
	}

	//----------------------------------------------
	public static void findPalindrome(char[] str) {
		boolean isPalin[][] = new boolean[str.length + 1][str.length + 1];
		int[][] p = new int[str.length + 1][str.length + 1];
		
		for (int i = 0; i < str.length; ++i) {
			isPalin[i][i] = true;
		}
		
		for (int len = 2; len <= str.length; ++len) {
			// head
			for (int i = 0; i <= str.length - len; ++i) {
				//   maxI -1 + len = str.len  -1
				// tail
				int j = i + len - 1;
				
				if (len == 2) {
					isPalin[i][j] = str[i] == str[j];
				}
				else {
					isPalin[i][j] = isPalin[i+1][j-1] && str[i] == str[j];
				}
				
				if (!isPalin[i][j]) {
					p[i][j] = Integer.MAX_VALUE;
					for (int k = i; k < j; ++k) {
						p[i][j] = Math.min(p[i][j], p[i][k] + p[k+1][j] + 1);
					}
				}
			}
		}
	}
	//----------------------------------------------
	// find all possible interpretations
	public static void findAllInterpretations(char[] text, Set<String> dict) {
		// s[i,j] = s[i,k] + s[k+1,j] + s[ij]
		Set<String>[][] lookup = new Set[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < text.length; ++j) {
				Set<String> ret = null;
				
				Set<String> left = null;
				Set<String> right = null;
				
				for (int k = i; k < j; ++k) {
					left = lookup[i][k];
					right = lookup[k+1][j];
					if (left != null && right != null) {
						// merge them
						if (ret == null) {
							ret = new HashSet<>();
						}
						
						for (String l : left) {
							for (String r : right) {
								ret.add(l + " " + r);
							}
						}
					}
				}
				
				String st = isInDict(dict, text, i, j);
				if (st != null) {
					if (ret == null) {
						ret = new HashSet<>();
					}
					ret.add(st);
				}
				
				lookup[i][j] = ret;
			}
		}
		
		System.out.println(lookup[0][text.length - 1]);
	}
	
	public static void findAllInterpretationsOpt(char[] text, Set<String> dict) { 
		// s[i,j] = s[i,k] + s[k+1,j] + s[ij]
		Set<String>[][] lookup = new Set[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < text.length; ++j) {
				
				String st = isInDict(dict, text, i, j);
				if (st != null) {
					Set<String> ret = new HashSet<>();
					ret.add(st);
					lookup[i][j] = ret;
				}
			}
		}
		
		for (int i = 0; i < text.length; ++i) {
			for (int k = 0; k < i; ++k) {
				Set<String> left = lookup[0][k];
				Set<String> right = lookup[k+1][i];
				if (left != null && right != null) {
					Set<String> ret = lookup[0][i];
					if (ret == null) {
						lookup[0][i] = ret = new HashSet<>();
					}
					
					for (String l : left) {
						for (String r : right) {
							ret.add(l + " " + r);
						}
					}
				}
			}
		}
			
		
	}
		
	private static String isInDict(Set<String> dict, char[] text, int i, int j) {
		String ret = new String(text, i, j-i + 1);
		if (dict.contains(ret)) {
			return ret;
		}
		else {
			return null;
		}
	}
	
	static void quicksort(int[] ar, int l, int h) {
		if (l < h) {
			int p = partition(ar, l, h);
			quicksort(ar, l, p);
			quicksort(ar, p + 1, h);
		}
	}
	
	static int partition(int[] ar, int l, int h) {
		final int pValue = ar[l];
		int i = l - 1;
		int j = h + 1;
		
		while (true) {
			do {
				++i;
			} while (ar[i] < pValue);
			
			do {
				--j;
			} while (ar[j] > pValue);
			
			if (i >= j) return j;
			
			int t = ar[i];
			ar[i] = ar[j];
			ar[j] = t;
		}
	}
	//----------------------------------------------------------------
	// given an expression string in polish notation, translate it to infix
}
