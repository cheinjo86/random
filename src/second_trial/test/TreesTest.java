package second_trial.test;


import org.junit.Test;

import second_trial.Trees.TreeNode;
import static second_trial.Trees.*;

import java.util.Iterator;

public class TreesTest {

	public static TreeNode buildTree() {
		TreeNode f = new TreeNode('F');
		TreeNode b = new TreeNode('B');
		TreeNode g = new TreeNode('G');
		TreeNode a = new TreeNode('A');
		TreeNode d = new TreeNode('D');
		TreeNode i = new TreeNode('I');
		TreeNode c = new TreeNode('C');
		TreeNode e = new TreeNode('E');
		TreeNode h = new TreeNode('H');
		
		d.left = c;
		d.right = e;
		
		i.left = h;
		
		b.left = a;
		b.right = d;
		
		g.right = i;
		
		f.left = b;
		f.right = g;
		
		return f;
	}
	
	@Test
	public void testInOrder() {
		TreeNode root = buildTree();
		inOrder(root);
		System.out.println();
		
		inOrderIter(root);
		
		Iterator<TreeNode> iter = inOrderIterator(root);
		while(iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
	}
	
	@Test
	public void tetsPreOrder() {
		TreeNode root = buildTree();
		preOrder(root);
		System.out.println();
		
		preOrderIter(root);
		System.out.println();
		
		
	}
	
	@Test
	public void testPostOrder() {
		TreeNode root = buildTree();
		postOrder(root);
		System.out.println();
		postOrderIter(root);
		System.out.println();
		
		Iterator<TreeNode> iter = postOrderIterator(root);
		while (iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		
		System.out.println();
		
		postOrderTwoStacks(root);
	}
	@Test
	public void testMostFrequent() {
		// 1, 1, 3, 4, 4, 5, 5, 6, 7, 7, 8 
		TreeNode root = new TreeNode (5);
		int[] ar = {1, 1, 3, 4, 4,  5, 6, 7,7, 7, 8};
		for (int i : ar) {
			buildBst(root, i);
		}
		
		printTree(root);
		System.out.println("---------");
		preOrder(root, 0);
		
		System.out.println("most frequestn: " + mostFrequent(root));
	}
	
	static void printTree(TreeNode root) {
		levelOrder(root);
	}
	
	static void buildBst(TreeNode root, int value) {
		if (value > root.value) {
			if (root.right == null) {
				root.right = new TreeNode(value);
			}
			else {
				buildBst(root.right, value);
			}
		}
		else {
			if (root.left == null) {
				root.left = new TreeNode(value);
			}
			else {
				buildBst(root.left, value);
			}
		}
	}
}
