package second_trial;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

public class Trees {

	public static class TreeNode {
		public final char ch;
		public final int value;
		public TreeNode left;
		public TreeNode right;
		final boolean isInt;
		
		public TreeNode(int v) {
			value = v;
			ch = 0;
			isInt = true;
		}
		
		public TreeNode(char ch) {
			this.ch = ch;
			value = 0;
			isInt = false;
		}
		public String toString() {
			return ch + "";
		}
//		public String toString() {
//			return value + "(" + (left == null ? "null" : left.value)
//			      +  ", " + (right == null ? "null" : right.value)
//			      +")";
//		}
	}
	
	// ------- traversals -------
	
	public static void perimeter(TreeNode root) {
		// top --> down left,
		// across bottup
		// up right
		
		// left is pre or 
		
		// bottom is any, but only do leaves (left is alwasy beefore right, so doesnt matter)
		
		// right is post
	}
	public static void inOrder(TreeNode root) {
		if (root != null) {
			inOrder(root.left);
			System.out.print(root + " ");
			inOrder(root.right);
		}
	}
	
	public static Iterator<TreeNode> inOrderIterator(TreeNode root) {
		final Stack<TreeNode> stack = new Stack<>();
		return new Iterator<TreeNode>(){
			TreeNode next = null;
			TreeNode cur = root;
			@Override
			public boolean hasNext() {
				while(next == null && (!stack.isEmpty() || cur != null))
				{
					if (cur != null) {
						stack.push(cur);
						cur = cur.left;
					}
					else {
						cur = stack.pop();
						next = cur;
						cur = cur.right;
					}
				}
				
				return next != null;
			}

			@Override
			public TreeNode next() {
				if (next == null && !hasNext()) {
					throw new NoSuchElementException();
				}
				
				TreeNode ret = next;
				next = null;
				return ret;
			}
			
		};
	}
	
	public static void inOrderIter(TreeNode root) {
		// left, parent ,right
		Stack<TreeNode> stack = new Stack<>();
		TreeNode cur = root;
		
		while (cur != null || !stack.isEmpty()) {
			if (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}
			else {
				cur = stack.pop();
				// visit
				System.out.print(cur + " ");
				cur = cur.right;
			}
		}
		System.out.println();
	}
	
//	public static void inOrderIter(TreeNode root) {
//		Stack<TreeNode> stack = new Stack<>();
//		TreeNode cur = root;
//		
//		do {
//			if (cur != null) {
//				stack.push(cur);
//				cur = cur.left;
//			}
//			else {
//				cur = stack.pop();
//				// visit
//				System.out.print(cur + " ");
//				cur = cur.right;
//			}
//		}
//		while (!stack.isEmpty() || cur != null);
//		
//		System.out.println();
//	}
//	
	public static void inOrderIterNoSet(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		
		TreeNode next = root;

		
		do {
			if (next == null) {
				next = stack.pop();
				System.out.print(next + " ");
				next = next.right;
			}
			else {
				stack.push(next);
				next = next.left;
			}
		}
		while (!stack.isEmpty() || next != null);
	}

	public static void preOrder(TreeNode root) {
		if (root != null) {
			// parent
			System.out.print(root + " ");
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	public static Iterator<TreeNode> preOrderIterator(TreeNode root) {
		final Stack<TreeNode> stack = new Stack<>();
		stack.add(root);
		
		return new Iterator<TreeNode>(){
			TreeNode next = null;
			
			@Override
			public boolean hasNext() {
				while (next == null && !stack.isEmpty()) {
					TreeNode cur = stack.pop();
					if (cur.right != null) {
						stack.push(cur.right);
					}
					
					if (cur.left != null) {
						stack.push(cur.left);
					}
					
					next = cur;
				}
				
				
				return next != null;
			}

			@Override
			public TreeNode next() {
				if (next == null && !hasNext()) {
					throw new NoSuchElementException();
				}
				TreeNode ret = next;
				next = null;
				return ret;		
			}
			
		};
	}
	
	public static void preOrderIter(TreeNode root) {
		// parent, left, right
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			// visit
			System.out.print(cur + " ");
			// push children, right first 
			if (cur.right != null) {
				stack.push(cur.right);
			}
			
			if (cur.left != null) {
				stack.push(cur.left);
			}
		}
		
	}
	
	
	public static void postOrder(TreeNode root) {
		// parent, left, right (dfs)
		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			System.out.print(root.ch + " ");
		}
	}
	
	public static Iterator<TreeNode> postOrderIterator(TreeNode root) {
		final Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		final Set<TreeNode> done = new HashSet<>();
		return new Iterator<TreeNode>() {
			TreeNode next = null;
			@Override
			public boolean hasNext() {
				while (next == null && !stack.isEmpty()) {
					TreeNode cur = stack.pop();
					if (cur.left == null && cur.right == null || visited(done, cur.left) && visited(done, cur.right)) {
						next = cur;
						done.add(cur);
					}
					else {
						// put back
						stack.push(cur);
						// put right first
						if (cur.right != null) {
							stack.push(cur.right);
						}
						
						if (cur.left != null) {
							stack.push(cur.left);
						}
						
					}
				}
				return next != null;
			}

			@Override
			public TreeNode next() {
				// force hasNext to be called, if needed
				if (next == null && !hasNext()) {
					throw new NoSuchElementException();
				}
				TreeNode ret = next;
				next = null;
				return ret;
			}
			
		};
	}
	
	public static void postOrderTwoStacks(TreeNode root) {
		Stack<TreeNode> temp = new Stack<>();
		//Stack<TreeNode> ret = new Stack<>();
		LinkedList<TreeNode> ret = new LinkedList<>();
		temp.push(root);
		
		while (!temp.isEmpty()) {
			TreeNode cur = temp.pop();
			ret.addFirst(cur);
			
			if (cur.left != null) {
				temp.push(cur.left);
			}
			
			if (cur.right != null) {
				temp.push(cur.right);
			}
		}
		
		System.out.println(ret);
//		while (!ret.isEmpty()) {
//			System.out.print(ret.pop() + " ");
//		}
		System.out.println();
	}
	public static void postOrderIter(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		Set<TreeNode> seen = new HashSet<>();
		stack.push(root);
		// left, right, VISIT parent
		
		while (!stack.isEmpty()) {
			TreeNode top = stack.pop();
			// if leaf node or both left and right has been visited
			if (top.left == null && top.right == null
					|| visited(seen, top.left) && visited(seen, top.right)) {
				System.out.print(top + " ");
				seen.add(top);
			}
			else {
				stack.push(top);
				if (top.right != null) {
					stack.push(top.right);
				}
				if (top.left != null) {
					stack.push(top.left);
				}
			}
		}
	}
//	public static void postOrderIter(TreeNode root) {
//		Set<TreeNode> done = new HashSet<>();
//		
//		Stack<TreeNode> stack = new Stack<>();
//		stack.push(root);
//		
//		while (!stack.isEmpty()) {
//			TreeNode cur = stack.pop();
//			
//			// leaf
//			if (cur.left == null && cur.right == null || visited(done, cur.left) && visited(done, cur.right)) {
//				System.out.print(cur.ch + " ");
//				done.add(cur);
//				
//			}
//			else {
//				stack.push(cur);
//				// push right first
//				if (cur.right != null) {
//					stack.push(cur.right);
//				}
//				if (cur.left != null) {
//					stack.push(cur.left);
//				}
//			}
//		}
//		
//	}
	
	static boolean visited(Set<TreeNode> done, TreeNode node) {
		return node == null || done.contains(node);
	}
	// ------- END traversals -------
	
	public static TreeNode lca_elegant(TreeNode root, TreeNode x, TreeNode y) {
		if (root == null || root == x || root == y) return root;
		
		TreeNode left = lca_elegant(root.left, x,y);
		TreeNode right = lca_elegant(root.right, x, y);
		return left == null ? right : right == null ? left : root;
	}
	static interface Visitor {
		void visit(TreeNode node);
	}
	public static void preOrder(TreeNode root, int level) {
		if (root != null) {
			if (level > 0) {
				System.out.print("|");
				for (int i = 0; i < level -1; ++i) {
					System.out.print("__");
				}
				System.out.print("+--");
				
			}
			System.out.println( root.value);

			if (root.left != null) {
			// prep for children
//				for (int i = 0; i < level; ++i){
//					System.out.print("  ");
//				}
//
//				System.out.print("| --");
				preOrder(root.left, level + 1);
			}
			
			if (root.right != null) {
//				for (int i = 0; i < level; ++i){
//					System.out.print("  ");
//				}
//				System.out.print("| --");
				preOrder(root.right, level + 1);
			}
		}
		else {
			System.out.println();
		}
	}
	public static void levelOrder(TreeNode root) {
		LinkedList<TreeNode> q = new LinkedList<>();
		q.add(root);
		
		while (!q.isEmpty()) {
			int size = q.size();
			while (size > 0) {
				--size;
				
				TreeNode cur = q.removeFirst();
				System.out.print(cur + " ");
				
				if (cur.left != null) {
					q.addLast(cur.left);
					
				}
				if (cur.right != null) {
					q.addLast(cur.right);
					
				}
			}
			System.out.println();
		}
	}
	public static TreeNode mostFrequent(TreeNode root) {
		class CountVisitor implements Visitor {
			int curCount = 0;
			TreeNode curNode = null;
			
			int maxCount = 0;
			TreeNode maxCountNode = null;
			
			@Override
			public void visit(TreeNode node) {
				if (curNode == null) {
					curCount = maxCount = 1;
					curNode = maxCountNode = node;
				}
				else if (curNode.value == node.value) {
					++curCount;
				}
				else {
					if (maxCount < curCount) {
						maxCount = curCount;
						maxCountNode = curNode;
					}
					curCount = 1;
					curNode = node;
				}
			}
			
		};
		CountVisitor countVisitor = new CountVisitor();
		inorder(root, countVisitor);
		return countVisitor.maxCountNode;
	}
	
	static void inorder(TreeNode root, Visitor v) {
		if (root != null) {
			inorder(root.left, v);
			v.visit(root);
			inorder(root.right, v);
		}
	}
}
