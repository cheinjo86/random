package first_trial;

import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * - pre,post,in order traversal
 * - level traversal
 * - LCA of two nodes
 * - store bintree into a lsit and restore it
 * - how about with an array
 * - determine if a tree is BST
 * - find successor/predesessor in an inorder
 * - mirror (fliop) a binary tree
 * - given a bin tree, print all paths from root to leafs. what s the time complexity?
 * - given a sequence (pre or post order) , reconstruct the tree
 * -k-level tree ***
 * print a tree by column ***
 * 
 *
 */
public class TreeProblems {

	public static void printByColumn2(TreeNode root) {
		// do level order traversal and record the columns
		Map<TreeNode, Integer> temPosition = new IdentityHashMap<>();
		TreeMap<Integer, LinkedList<TreeNode>> finalPos = new TreeMap<>();
		temPosition.put(root, 0);
		int maxNum = root.value;
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.addLast(root);
		while(!queue.isEmpty()) {
			
			TreeNode cur = queue.removeFirst();
			if(cur.value > maxNum) {
				maxNum = cur.value;
			}
			Integer curPos = temPosition.get(cur);
			LinkedList<TreeNode> nodes = finalPos.get(curPos);
			if (nodes == null) {
				finalPos.put(curPos, nodes = new LinkedList<>());
			}
			nodes.addLast(cur);
			
			if (cur.left != null) {
				temPosition.put(cur.left, curPos - 1);
				queue.addLast(cur.left);
			}
			
			if (cur.right != null) {
				temPosition.put(cur.right, curPos + 1);
				queue.addLast(cur.right);
			}
		}
		
		doPrint(finalPos, (int)Math.ceil(Math.log10(maxNum)));
	}
	
	public static void doPrint(TreeMap<Integer, LinkedList<TreeNode>> columns, int maxNDigit) {
		Iterator<TreeNode>[] iters = new Iterator[columns.size()];
		int i = 0;
		for (Integer col : columns.keySet())
		{
			//System.out.println("col " + col + " : " + columns.get(col));
			iters[i++] = columns.get(col).iterator();
		}
		// 2 is the additional padding
	    final int padding = 2 + maxNDigit;
		final String formatNum = "%" + padding + "d";
		final String emptyFormat;
		StringBuilder bd = new StringBuilder();
		for (int j = 0; j < padding; ++j) bd.append(' ');
		emptyFormat = bd.toString();
		
		boolean hasNext = true;
		while (hasNext) {
			hasNext = false;
			for (i = 0; i < iters.length; ++i) {
				if(iters[i].hasNext()) {
					System.out.printf(formatNum, iters[i].next().value);
					//System.out.print(iters[i].next() + " ");
				}
				else {
					System.out.print(emptyFormat);
				}
				hasNext |= iters[i].hasNext();
			}
			System.out.println();
		}
	}
	public static void reverseOrderLevelPrint(TreeNode root) {
		if (root == null) return;
		Stack<String> prints = new Stack<>();
		
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			// number of children at this level
			int size = queue.size();
			StringBuilder print = new StringBuilder();
			while (size > 0) {
				TreeNode node = queue.removeFirst();
				print.append(node.value).append(" ");
				
				// add children
				if (node.left != null) {
					queue.addLast(node.left);
				}
				
				if (node.right != null) {
					queue.addLast(node.right);
				}
				--size;
			}
			prints.push(print.toString());
		}
		
		String st;
		while (!prints.isEmpty()) {
			st = prints.pop();
			System.out.println(st);
		}
	}
	public static TreeNode reconstructTree(int[] inorder, int low, int high,
			                               Iterator<Integer> preorder) {
		if(low > high) return null;
		
		// in : left parent right
		// pre: parent left right
		
		// root
		TreeNode root = new TreeNode(preorder.next());
		
		// find the root in inorder
		int i = low;
		for (; i <= high; ++i) {
			if (inorder[i] == root.value) {
				// found it
				break;
			}
		}
		assert i <= high : "didn't find root!";
		
		root.left = reconstructTree(inorder, low, i -1, preorder);
		root.right = reconstructTree(inorder, i+1, high, preorder);
		return root;
	}
	
	public static void preOrder(TreeNode root, TreeNodeVisitor visitor) {
		if (root == null) return;
		visitor.accept(root);
		preOrder(root.left, visitor);
		preOrder(root.right, visitor);
	}
	public static void printRootToLeaves(TreeNode root, List<String> prefixes) {
		//space complexity:
		// assume full tree, it'll take leaf * length(path)
		// = leaf * height
		// leaf = 2^h = (n + 1 ) /2
		if (root.left == null && root.right == null)  {
			// found the leaf
			System.out.println(prefixes + ", " + root);
		}
		else {
			visitKid(root, root.left, prefixes);
			visitKid(root, root.right, prefixes);
		}
	}
	
	static void visitKid(TreeNode parent, TreeNode kid, List<String> prefixes) {
		if (kid == null) return;
		LinkedList<String> copied = new LinkedList<>();
		copied.addAll(prefixes);
		copied.addLast(parent.toString());
		printRootToLeaves(kid, copied);
	}
	
	public static void serialise(TreeNode root, StringBuilder ret) {
		if (root == null) {
			//ret.append(",");
			return;
		}
		
		ret.append(root.value);
		
		// if is parent
		boolean isParent = root.left != null || root.right != null;
		if (isParent) {
			ret.append("+");
		}
		ret.append(",");
		if (root.left != null) {
			serialise(root.left, ret);
		}
		else if (isParent) {
			// append empty
			ret.append(",");
		}
		if (root.right != null) {
			serialise(root.right, ret);
		}
		else if (isParent) {
			// append empty
			ret.append(",");
		}
	}
	
	public static TreeNode  deserialise(String str) {
		String[] parts = str.split(",");
		
		return doDeserialize(new int[] {0}, parts);
	}
	
	static TreeNode doDeserialize(int[] i, String[] parts) {
		if (i[0] >= parts.length) return null;
		
		String st = parts[i[0]].trim();
		++i[0];
		
		if (st.isEmpty()) return null;
		
		boolean isParent = st.endsWith("+");
		if (isParent) {
			st = st.substring(0, st.length() - 1);
		}
		
		TreeNode ret = new TreeNode(Integer.parseInt(st));
		
		if (isParent) {
			ret.left = doDeserialize(i, parts);
			ret.right = doDeserialize(i, parts);
		}
		return ret;
	}
	public static class TreeNode {
		public int value;
		public TreeNode left;
		public TreeNode right;
		public TreeNode parent;
		
		public boolean isParent = false;

		public TreeNode(int value) {
			this.value = value;
		}
		public TreeNode(int value, TreeNode left, TreeNode right) {
			this.value = value;
			this.left = left;
			this.right = right;
		}
		
		@Override
		public String toString() {
			return Integer.toString(value);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TreeNode && obj != null) {
				return value == ((TreeNode)obj).value;
			}
			else { 
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			return value;
		}
	}
	
	
	public static void printByColumn(TreeNode root) {
	
		TreeMap<Integer, LinkedList<TreeNode>> map = new TreeMap<>();
		Map<TreeNode, Integer> nodeToColNum = new HashMap<>();
		
		
		LinkedList<TreeNode> q = new LinkedList<>();
		q.add(root);
		nodeToColNum.put(root, 0);
		
		while (!q.isEmpty()) {
			//int size = q.size();
			//while (size > 0) {
				TreeNode cur = q.removeFirst();
				int curCol = nodeToColNum.remove(cur);
				
				LinkedList<TreeNode> center = map.get(curCol);
				if (center == null) {
					map.put(curCol, center = new LinkedList<>());
				}
				center.addLast(cur);
				
				// add children
				 if (cur.left != null) {
					 nodeToColNum.put(cur.left, curCol - 1);
					 q.addLast(cur.left);
				 }
				 
				 if (cur.right != null) {
					 nodeToColNum.put(cur.right, curCol + 1);
					 q.addLast(cur.right);
				 }
				// --size;
		//	}
					
		}
		
		// print
		for (Map.Entry<Integer, LinkedList<TreeNode>> entry : map.entrySet()) {
			System.out.println(entry.getValue());
		}
	}
	
	public static interface TreeNodeVisitor {
		public void accept(TreeNode node);
	}
	
	public static void printPaths(TreeNode root, List<TreeNode> cur, List<List<TreeNode>> paths) {
		// print all paths from root to all leaves.
		if (root == null) return;
		
		cur.add(root);
		
		
		List<TreeNode> left = new LinkedList<>();
		left.addAll(cur);
		printPaths(root.left, left, paths);
		
		List<TreeNode> right = new LinkedList<>();
		right.addAll(cur);
		printPaths(root.right, right, paths);
		
		if (root.left == null && root.right == null) {
			paths.add(cur);
		}
		
		
		
	}
	public static void flip(TreeNode root) {
		if (root == null) return;
		
		//left, right then parent
		flip(root.left);
		flip(root.right);
		
		TreeNode i = root.left;
		root.left = root.right;
		root.right = i;
	}
	
	public static TreeNode findNext(TreeNode root, int target) {
		// find the node and keep track if the parent value as we go
		
		// parent is > children and p is closest to children (floor value)
		TreeNode ret = null;
		TreeNode cur = null;
		while (cur != null) {
			if (cur.value > target) {
				ret = cur;
				cur = cur.left;
			}
			else if (cur.value == target) {
				// found it;
				// if it has an immidate right child then find the min of (cur.right)
				if (cur.right != null) {
					ret = cur.right;
					while (ret.left != null) {
						ret = ret.left;
					}
				}
				// otherwise, it is the parent (already set)
				break;
			}
			else {
				cur = cur.right;
			}
		}
		
		return ret;
		
		// ret > target
		// * min(target.right)
		// * 
	}
	public static TreeNode find(TreeNode cur, int target) {
		if (cur == null) return null;
		
		if (cur.value == target) return cur;
		if (cur.value < target) {
			return find(cur.right, target);
		}
		else {
			return find(cur.left, target);
		}
	}

	static TreeNode min(TreeNode cur) {
		while (cur.left != null) {
			cur = cur.left;
		}
		return cur;
	}
	
	public static TreeNode successor(TreeNode root, int target) {
		TreeNode node = find(root, target);
		if (node.right != null) {
			return min(node.right);
		}
		
		// search for the node from root
		// find min node such that node >target
		TreeNode cur = root;
		TreeNode ret = null;
		while (cur != null) {
			if (cur.value > target) {
				ret = cur;
				cur = cur.left;
			}
			else if (cur.value == target) {
				// ret has been set
				break;
			}
			else {
				cur = cur.right;
			}
		}
		
		return ret;
		// search for the node, keep track of current min, such that min > child 
		
	}
	
	public static TreeNode predecessor(TreeNode root, int target) {
		TreeNode node = find(root, target);
		// if left is not null, the the pred will be in left sub tree
		// find max n | n < node
		if (node.left != null) {
			// find max
			TreeNode max = node.left;
			while (max.right != null) {
				max = max.right;
			}
			return max;
		}
		
		// other wise search from root, find the max
		TreeNode cur = root;
		TreeNode pred = null;
		while(cur != null) {
			if (cur.value > target) {
				cur = cur.left;
			}
			else if (cur.value < target) {
				pred = cur;
				cur = cur.right;
			}
			else {
				break;
			}
		}
		
		return pred;
	}
	
	// simply do an inorder, if 
	public static boolean isBSTInOrder(TreeNode root) {
		return isBSTInOrder(root, new TreeNode[1]);
	}
	
	public static boolean isBSTInOrder(TreeNode root, TreeNode[] pred) {
		if (root == null) return true;
		
		// left
		if (!isBSTInOrder(root.left, pred)) return false;
		
		// parent
		if (pred[0] != null && pred[0].value > root.value) {
			return false;
		}
		else {
			pred[0] = root;
		}
		
		// right
		return isBSTInOrder(root.right, pred);
	}
	public static boolean isBSTPost(TreeNode root, TreeNode min, TreeNode max) {
		if (root == null) return true;
		
		if (min != null && min.value > root.value) return false;
		if (max != null && max.value < root.value) return false;
		
		return isBSTPost(root.left, min, root) && isBSTPost(root.right, root, max);
	}
	
	public static boolean isBST(TreeNode root) {
		// is bst if left <= parent && parent <= right
		Map<TreeNode, TreeNode> min = new HashMap<>();
		Map<TreeNode, TreeNode> max = new HashMap<>();
		
		boolean ret = doIsBST(root,min, max);
		//System.out.println("\n min map: " + min);
		//System.out.println("\n max map: " + max);
		return ret;
	}
	
	static boolean doIsBST(TreeNode root, Map<TreeNode, TreeNode> minMap, Map<TreeNode, TreeNode> maxMap) {
		// node --> max value of its subtree
	
		// do left, right then parent
		if (root == null) return true;
		if (!doIsBST(root.left, minMap, maxMap)) return false;
		if (!doIsBST(root.right, minMap, maxMap)) return false;
		
		TreeNode max = maxMap.get(root);
		if (max == null) {
			max = root;
		}
		
		TreeNode min = minMap.get(root);
		if (min == null) {
			min = root;
		}
		
		if (root.left != null) {
			TreeNode leftMax = maxMap.get(root.left);
			
			if (leftMax.value > root.value) {
				return false;
			}
			
			TreeNode leftMin = minMap.get(root.left);
			if (leftMin.value > root.value) {
				return false;
			}
			min = leftMin;
		}
		
		if (root.right != null) {
			TreeNode rightMin = minMap.get(root.right);
			if (rightMin.value < root.value) {
				return false;
			}
			
			TreeNode rightMax = maxMap.get(root.right);
			if (rightMax.value < root.value) {
				return false;
			}
			// set max(root) to be max(root.right)
			max = rightMax;
		}
		
		minMap.put(root, min);
		maxMap.put(root, max);
		return true;
	}
	final static String PARENT = "+";
	public static String serialize(TreeNode[] arr) {
		StringBuilder bd = new StringBuilder();
		
		for (TreeNode node : arr) {
			if (node == null) {
				bd.append("null");
			}
			else {
				bd.append(node.value);
				if (node.isParent) {
					bd.append(PARENT);
				}
			}
			bd.append(",");
		}
		if (bd.length() > 0 && bd.charAt(bd.length() - 1) == ','){
			bd.deleteCharAt(bd.length() - 1);
		}
		return bd.toString();
	}
	
	public static TreeNode[] deserialize(String str) {
		LinkedList<TreeNode> list = new LinkedList<>();
		String[] frags = str.split(",");
		for (String st : frags) {
			if(st.equals("null")){
				list.addLast(null);
			}
			else {
				boolean isP = false;
				if (st.charAt(st.length() - 1) == '+') {
					isP = true;
					st = st.substring(0, st.length() - 1);
				}
				int num = Integer.parseInt(st);
				TreeNode n = new TreeNode(num);
				n.isParent = isP;
				list.addLast(n);
			}
		}
		
		return list.toArray(new TreeNode[list.size()]);
	}
	
	static void doPreOrder(TreeNode root, LinkedList<TreeNode> ser) {
		if (root == null) {
			ser.addLast(null);
			return;
		}
		
		// visit
		ser.addLast(root);
		if (root.left != null || root.right != null) {
			root.isParent = true;
			doPreOrder(root.left, ser);
			doPreOrder(root.right, ser);
		}
		else {
			// nothing to do
		}
	}
	
	
	public static TreeNode fromArray(int[] i, TreeNode[] arr) {
		TreeNode cur = arr[i[0]];
		++i[0];
		if (cur == null) return null;
		if (cur.isParent) {
			cur.left = fromArray(i, arr);
			cur.right = fromArray(i, arr);
			
			return cur;
		}
		else {
			return cur;
		}
	}
	
	public static TreeNode[] toArray(TreeNode root) {
		// cur = n
		// left = 2n +1
		// right = 2n +2
		
		LinkedList<TreeNode> list = new LinkedList<>();
		doPreOrder(root, list);
		return list.toArray(new TreeNode[list.size()]);
	}
	
	public static int findSize(TreeNode root) {
		int[] count = new int[1];
		TreeNodeVisitor countV = new TreeNodeVisitor() {
			
			@Override
			public void accept(TreeNode node) {
				if (node != null) ++count[0];
			}
		};
		doPreOrder(root, countV);
		return count[0];
	}
	public static int findLevels(TreeNode root) {
		LinkedList<TreeNode> queue = new LinkedList<>();
		int nLevel = 0;
		queue.add(root);
		
		int size = 0;
		while(!queue.isEmpty()) {
			size = queue.size();
			while (size > 0) {
				TreeNode cur = queue.removeFirst();
				if (cur.left != null) {
					queue.addLast(cur.left);
				}
				if (cur.right != null) {
					queue.addLast(cur.right);
				}
			}
			++nLevel;
		}
		
		return nLevel;
	}
	public static int findHeight(TreeNode root) {
		// h = max(h(left), h(right)) + 1
		if (root == null) return 0;
		return 1 + Math.max(findHeight(root.left), findHeight(root.right));
	}
	// parent, left, right
	public static void preOrder(TreeNode root) {
		Stack<TreeNode> stack  = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			System.out.print(cur + " ");
			
			if (cur.right != null) {
				stack.push(cur.right);
			}
			if (cur.left != null) {
				stack.push(cur.left);
			}
		}
		/*
		 * 
		 *         o
		 *       o    o
		 *      o      
 		 *     o o
		 *    o  o 
		 * 
		 * 
		 */
	}
	
	private static void doPreOrder(TreeNode cur, TreeNodeVisitor v) {
		if (cur == null) return;

		v.accept(cur);
		doPreOrder(cur.left, v);
		doPreOrder(cur.right, v);
	}
	

	public static void inOrder(TreeNode root) {
		// left, parent, right
		
	}

	// left, parent, right
	public static void inOrder(TreeNode root, TreeNodeVisitor v) {
		if (root == null) return;
		
		inOrder(root.left, v);
		v.accept(root);
		inOrder(root.right, v);
	}
	
	public static void postOrder(TreeNode root) {
		
	}
	
	public static void postOrder(TreeNode root, TreeNodeVisitor v) {
		// left, right parent
		if (root == null) return;
		
		postOrder(root.left,v);
		postOrder(root.right, v);
		v.accept(root);
	}
	
	public static void levelTraversal(TreeNode root) {
		// basically just BFS
		//use a queue
		if (root == null) return;
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.addLast(root);
		// number of nodes at this level
		int size = queue.size();
		
		while (!queue.isEmpty()) {
			size = queue.size();
			while (size > 0) {
				TreeNode cur = queue.removeFirst();
				System.out.print(cur + "  ");
				//System.out.print(cur + "(" + cur.parent+ ")  ");
				if (cur.left != null) queue.addLast(cur.left);
				if (cur.right != null) queue.addLast(cur.right);
				--size;
			}
			System.out.println("\n ");
		}
		
	}
	
	public static class FindLCA{
		public static void setupParent(TreeNode root) {
			if (root == null) return;
			
			if (root.left != null) {
				root.left.parent = root;
				setupParent(root.left);
			}
			
			if (root.right != null) {
				root.right.parent = root;
				setupParent(root.right);
			}
			
		}
		
		
		public static TreeNode withParentNode(TreeNode a, TreeNode b) {
			// walk up to build th4 path
			// then find the common intersection
			// done in O(h) ???s
			// require space of O(h)
			
			
			// sol2: walk both nodes up to the same level
			// then going up at the same time, stopping at the first common
			int aH = findHeights(a, 0);
			int bH = findHeights(b, 0);
			
			// walk the lower node up
			if (aH >bH) {
				a = walkUp(a, aH - bH);
			}
			else {
				b = walkUp(b, bH - aH);
			}
			
			// now walk both up until the first match or null (root)
			TreeNode left = a;
			TreeNode right = b;
			while (left != null && right != null) {
				if (left.value == right.value) {
					// found it
					return left;
				}
				left = left.parent;
				right = right.parent;
			}
			
			if ((left == null) != (right == null)) {
				// something not right
				return null;
			}
			else {
				return null;
			}
		}
		
		static TreeNode walkUp(TreeNode node, int delta) {
			while (delta-- > 0) {
				node = node.parent;
			}
			return node;
		}
		static int findHeights(TreeNode node, int cur) {
			if (node == null) return cur;
			else return findHeights(node.parent, cur + 1);
		}
		public static TreeNode withoutParentNode(TreeNode root, TreeNode a, TreeNode b) {
			// find path from root to a
			LinkedList<TreeNode> pathA = new LinkedList<>();
			LinkedList<TreeNode> pathB = new LinkedList<>();
			if (!findPath(root, a, pathA) || !findPath(root, b, pathB)) {
				System.out.println("node not in tree");
				return null;
			}
			else {
				// find the common
				Iterator<TreeNode> aIter = pathA.iterator();
				Iterator<TreeNode> bIter = pathB.iterator();
				System.out.println("\n\npath a: " + pathA);
				System.out.println("path b: " + pathB);
				TreeNode common = null;
				while (aIter.hasNext() && bIter.hasNext()) {
					TreeNode aNode = aIter.next();
					TreeNode bNode = bIter.next();
					if (aNode.value != bNode.value) {
						// found the divergent
						break;
					}
					else {
						common = aNode;
						// if saw one of the two nodes, then that's the common
						if (common.value == a.value || common.value == b.value) {
							break;
						}
					}
				}
				if (aIter.hasNext() && find(aIter, b) != null) {
					common = b;
				}
				else if (bIter.hasNext() && find(bIter, a) != null) {
					common = a;
				}
				return common;
				
			}
		}
		
		public static TreeNode withBSTProperty(TreeNode root, TreeNode a, TreeNode b) {
			
			// going top-down,
			// the first node n that's satisfied a < n < b is the LCA
			
			if  (root == null) {
				// nothing to find here
				return null;
			}
			
			// make sure a < b first
			if (a == null) {
				return b;
			}
			else if (b == null) {
				return a;
			}
			else if (a.value == b.value) {
				return a;
			}
			else if (a.value > b.value){
				return withBSTProperty(root, b, a);
			}
			else if (a.value < root.value && root.value < b.value) {
				return root;
			}
			else if (root.value == a.value || root.value == b.value) {
				return a;
			}
			else if (root.value < a.value){
				return withBSTProperty(root.right, a, b);
			}
			else {
				return withBSTProperty(root.left, a, b);
			}
			
			
		}
		static TreeNode find(Iterator<TreeNode> iter, TreeNode target) {
			while (iter.hasNext()) {
				if (target.value == iter.next().value) {
					return target;
				}
			}
			return null;
		}
		
		static boolean findPath(TreeNode root, TreeNode node, LinkedList<TreeNode> path) {
			if (root == null) return false;
			
			if (root.value == node.value) {
				return true;
			}
			
			if (findPath(root.left, node, path) || findPath(root.right, node, path)){
				path.addFirst(root);
				return true;
			}
			return false;
			
		}
	}
}


