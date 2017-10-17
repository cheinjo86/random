package first_trial.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import first_trial.TreeProblems;
import first_trial.TreeProblems.TreeNode;
import first_trial.TreeProblems.TreeNodeVisitor;

public class TreeProblemsTest {

	@Test
	public void testReConstruct() {
		TreeNode root = buildBST();
		final LinkedList<Integer> preorder = new LinkedList<>();
		
		TreeProblems.preOrder(root, new TreeNodeVisitor() {
			@Override
			public void accept(TreeNode node) {
				preorder.addLast(node.value);
			}
		});
		
		System.out.println("pre order: " + preorder);
		
		final int[] inorder = new int[preorder.size()];
		
		TreeProblems.inOrder(root, new TreeNodeVisitor() {
			int i = 0;
			@Override
			public void accept(TreeNode node) {
				inorder[i++] = node.value;
			}
		});
		print("in order: ", inorder);
		
		TreeNode reconstructedRoot = TreeProblems.reconstructTree(inorder, 0, inorder.length - 1, preorder.iterator());
		
		System.out.println("------- reconstructed -----");
		System.out.print("in order :");
		TreeProblems.inOrder(reconstructedRoot, new TreeNodeVisitor() {
			@Override
			public void accept(TreeNode node) {
				System.out.print(node.value + ", ");
			}
		});
		System.out.println();
		
		System.out.print("pre order :");
		TreeProblems.preOrder(reconstructedRoot, new TreeNodeVisitor() {
			@Override
			public void accept(TreeNode node) {
				System.out.print(node.value + ", ");
			}
		});
		System.out.println();
		
	}
	
	static void print(String label, int[] num) {
		System.out.print(label);
		for (int n : num) {
			System.out.print(n + ", ");
		}
		System.out.println();
	}
	@Test
	public void testDeSerialize() {
		TreeNode root = buildNonBST();
		
		TreeProblems.levelTraversal(root);
		
		StringBuilder ser = new StringBuilder();
		TreeProblems.serialise(root, ser);
		final String st =ser.toString();
		System.out.println("\n---------\n st: " + st);
		
		TreeNode newRoot = TreeProblems.deserialise(st);
		//System.out.println("\n---------\n deseraliazed tree: ");
		//TreeProblems.levelTraversal(newRoot);
		
		 ser = new StringBuilder();
		TreeProblems.serialise(newRoot, ser);
		System.out.println(ser);
	}
	
	@Test
	public void testReverseLevelOrder() {
		TreeNode root = new TreeNode(7);
		{
			TreeNode four = new TreeNode(4);
			TreeNode five = new TreeNode(5);
			four.right = five;

			TreeNode zero = new TreeNode(0);
			TreeNode one = new TreeNode(1, zero, four);
			root.left = one;

			TreeNode nine = new TreeNode(9);
			TreeNode ten = new TreeNode(10, nine, null);
			TreeNode eight = new TreeNode(8, null, ten);
			root.right = eight;
		}
		
		TreeProblems.levelTraversal(root);
		System.out.println("\n-----reverse ---");
		TreeProblems.reverseOrderLevelPrint(root);
	}
	@Test
	public void testPrintColumn() {
		TreeNode root = buildNonBST();
		//TreeProblems.printByColumn(root);
		TreeProblems.printByColumn2(root);
	}
	
	/*
	 *         6
	 *     1          2 
	 *  3     5     x    8
	 *  
	 *  
	 *  n = 2 ^ h -1
	 *  
	 *  leaf = 2^(h-1)
	 *  
	 *  leaf * h
	 *  2^2 * 3 = 12
	 *  
	 *  h. 2^(h-1)
	 *  
	 *  n = 3, h = 2 ==> 4
	 *  n = 7, h = 3 ==> 12
	 *  n = 15 h =4  ==> 32
	 *      x   x     7  4
	 */
	
	
	static TreeNode buildNonBST() {
		/*
		 *         6
		 *     1       2 
		 *  3     5       8
		 *           7  4
		 */
		
		TreeNode root = new TreeNode(6);

		TreeNode seven = new TreeNode(7);
		TreeNode five = new TreeNode(5);
		five.right = seven;

		TreeNode three = new TreeNode(3);
		TreeNode one = new TreeNode(1, three, five);
		root.left = one;

		TreeNode four = new TreeNode(4);
		TreeNode eight = new TreeNode(8, four, null);
		TreeNode two = new TreeNode(2, null, eight);
		root.right = two;
		
		return root;
		
	}
	
	@Test
	public void testIsBSTPost() {
		TreeNode root = buildWrongBST();
		assertFalse(TreeProblems.isBSTPost(root, null, null));
	}
	
	
	/*
	 *         7
	 *   5          10 
	 * 0  8       11   12
	 *   4        
	 */
	static TreeNode buildWrongBST() {
		TreeNode root = new TreeNode(7);
		
		TreeNode four = new TreeNode(4);
		TreeNode eight = new TreeNode(8, four, null);
		
		TreeNode zero = new TreeNode(0);
		
		TreeNode five = new TreeNode(5, zero, eight);
		
		TreeNode eleven = new TreeNode(11);
		TreeNode twelve = new TreeNode(12, eleven, null);
		TreeNode ten = new TreeNode(10, eleven, twelve);
		
		root.left = five;
		root.right = ten;
		
		return root;
	}
	
	@Test
	public void testPrintPaths() throws Exception {
		List<List<TreeNode>> paths = new LinkedList<>();
		TreeNode root = buildBST();
		
		TreeProblems.printPaths(root, new LinkedList<TreeNode>(), paths);
		
		for (List<TreeNode> p : paths) 
			System.out.println(p);
	}
	
	static TreeNode buildBST() {
		/*
		 *         7
		 *   1         11 
		 * 0  4      8    12
		 *     5      9
		 *       6        10
		 */
		
		TreeNode root = new TreeNode(7);

		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		four.right = five;

		TreeNode zero = new TreeNode(0);
		TreeNode one = new TreeNode(1, zero, four);
		root.left = one;

		TreeNode ten = new TreeNode(10, null, null);
		TreeNode nine = new TreeNode(9, null, ten);
		TreeNode eight = new TreeNode(8, null, nine);
		
		TreeNode twelve = new TreeNode(12);
		TreeNode eleven = new TreeNode(11, eight, twelve);
		
		
		root.right = eleven;
		
		return root;
	}
	
	@Test
	public void testIsBST() {
		TreeNode root1 = buildBST();
		TreeNode root2 = buildNonBST();
		TreeNode root3 = buildWrongBST();
		System.out.println("is BST? " + TreeProblems.isBST(root1));
		System.out.println("is non-BST? " + TreeProblems.isBST(root2));
		System.out.println("is wrong-BST? " + TreeProblems.isBST(root3));
	
	}
	
	@Test
	public void testIsBSTInOrder() {
		TreeNode root1 = buildBST();
		TreeNode root2 = buildNonBST();
		TreeNode root3 = buildWrongBST();
		System.out.println("is BST? " + TreeProblems.isBSTInOrder(root1));
		System.out.println("is non-BST? " + TreeProblems.isBSTInOrder(root2));
		System.out.println("is wrong-BST? " + TreeProblems.isBST(root3));
	
	}
	

	@Test
	public void testToAray() {
		TreeNode root = new TreeNode(7);

		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		four.right = five;

		TreeNode zero = new TreeNode(0);
		TreeNode one = new TreeNode(1, zero, four);
		root.left = one;

		TreeNode nine = new TreeNode(9);
		TreeNode ten = new TreeNode(10, nine, null);
		TreeNode eight = new TreeNode(8, null, ten);
		root.right = eight;
		
		System.out.print("\n\n original tree: " );
		TreeProblems.preOrder(root);
		
		TreeNode[] arr = TreeProblems.toArray(root);
		System.out.print("\n\narray: ");
		for (TreeNode n : arr) {
			System.out.print(n + " ");
		}
		System.out.println();
		
		String serialized = TreeProblems.serialize(arr);
		System.out.println("serialized: " + serialized);
		
		TreeNode[] deserialized = TreeProblems.deserialize(serialized);
		System.out.print("\n\ndeserialized array: ");
		for (TreeNode n : deserialized) {
			System.out.print(n + " ");
		}
		System.out.println();
		
		TreeNode root2 = TreeProblems.fromArray(new int[] {0}, deserialized);
		
		System.out.print("\n\n reconstructed tree: " );
		TreeProblems.preOrder(root2);
	}
	@Test
	public void testFindLCAWithBST() {
		/*
		 *         7
		 *   1         8 
		 * 0  4          10
		 *      5      9
		 */
		
		System.out.println("true ^ false" + (true ^ true));
		
		TreeNode root = new TreeNode(7);

		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		four.right = five;

		TreeNode zero = new TreeNode(0);
		TreeNode one = new TreeNode(1, zero, four);
		root.left = one;

		TreeNode nine = new TreeNode(9);
		TreeNode ten = new TreeNode(10, nine, null);
		TreeNode eight = new TreeNode(8, null, ten);
		root.right = eight;
		
//		doTestBST(4,5,root);
//		doTestBST(0,5,root);
//		doTestBST(9,8,root);
//		doTestBST(9,0,root);
//		doTestBST(0,15,root);
		// 
		
		//System.out.println("before: ...");
		//TreeProblems.levelTraversal(root);
		
		TreeProblems.FindLCA.setupParent(root);
		System.out.println("\nafter: ...");
		TreeProblems.levelTraversal(root);
		
		System.out.println("\n WITH parent");

		doTestParent(4,5, root);
		doTestParent(0,5, root);
		doTestParent(9,8, root);
		doTestParent(9,0, root);
		doTestParent(0,15, root);
		
	}
	
	static void doTestParent(int left, int right, TreeNode root) {
		TreeNode leftNode = findNode(root, left);
		TreeNode rightNode = findNode(root, right);
		System.out.println("Common [" + left + ", " + right + "]: " + 
				TreeProblems.FindLCA.withParentNode(leftNode, rightNode));
	}
	static void doTestBST(int left, int right, TreeNode root) {
		System.out.println("Common [" + left + ", " + right + "]: " + TreeProblems.FindLCA.withBSTProperty(root, new TreeNode(left), new TreeNode(right)));
	}
	
	private static TreeNode findNode(TreeNode root,  int target) {
		if (root == null) return null;
		
		if (root.value == target) return root;
		TreeNode node;
		if ((node = findNode(root.left, target))  != null
				||(node = findNode(root.right, target)) != null) {
			return node;
		}
		return null;
			 
				
	}
	
	@Test
	public void testFindLCA() {
		/*
		 *         6
		 *   1         2 
		 * 3  5          8
		 *      7      4
		 */
		
		TreeNode root = new TreeNode(6);

		TreeNode seven = new TreeNode(7);
		TreeNode five = new TreeNode(5);
		five.right = seven;

		TreeNode three = new TreeNode(3);
		TreeNode one = new TreeNode(1, three, five);
		root.left = one;

		TreeNode four = new TreeNode(4);
		TreeNode eight = new TreeNode(8, four, null);
		TreeNode two = new TreeNode(2, null, eight);
		root.right = two;
		
		doTest(2, 4, root);
		doTest(7, 4, root);
		doTest(6, 4, root);
		doTest(3, 7, root);
		doTest(1, 5, root);
		
	}
	
	static void doTest(int left, int right, TreeNode root) {
		System.out.println("Common [" + left + ", " + right + "]: " + TreeProblems.FindLCA.withoutParentNode(root, new TreeNode(left), new TreeNode(right)));
	}
}
