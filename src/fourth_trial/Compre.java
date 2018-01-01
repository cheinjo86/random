package fourth_trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class Compre {

	public static void removeInvalidParen(String st) {
		System.out.println("orig: " + st);
		char[] text = st.toCharArray();
		LinkedList<Integer> openPos = new LinkedList<>();
		Set<Integer> toRemove = new HashSet<>();
		
		for (int i = 0; i < text.length; ++i) {
			char ch = text[i];
			// (a b c (a ) b ) )

			switch(ch) {
			case '(': openPos.addLast(i); break;
			case ')':
				if (openPos.isEmpty()) {
					// remove this one?
					toRemove.add(i);
				}
				else {
					// match paren
					openPos.removeLast();
				}
				//default: break;
			}

		}
		
		// remove any remaining open parens
		toRemove.addAll(openPos);
		StringBuilder ret;
		if (!toRemove.isEmpty()) {
			ret = new StringBuilder();
			for (int i = 0; i < text.length; ++i) {
				if (toRemove.contains(i)) {
					toRemove.remove(i);
				}
				else {
					ret.append(text[i]);
				}
			}
		}
		else {
			ret = new StringBuilder(new String(text));
		}
		
		System.out.println("removed: " + ret);
	}
	
	// TODO: how about return ALL posible locs
	
	// trees
	public static class NestedNode {
		NestedNode data;
		NestedNode next;
		NestedNode prev;
		
		boolean isLeaf() {
			return false;
		}
		
		@Override
		public String toString() {
			return "[" + data + "]"  + (next == null ? "" : " --> " + next); 
		}
	}
	
	public static class LiteralNode extends NestedNode {
		char label;
		public LiteralNode(char ch) {
			label = ch;
		}
		public boolean isLeaf() {
			return true;
		}
		
		@Override
		public String toString() {
			return label + (next == null ? "" :  " --> " + next);
		}
	}
	
	// just dfs traversal
	public static void flatten(NestedNode root) {
		doFlatten(root, null);
	}
	
	static NestedNode doFlatten(NestedNode root, NestedNode prevCopied) {
		if (root == null) return prevCopied;
		
		if (root.isLeaf()) {
			if (prevCopied != null) {
				prevCopied.next = root;
			}
			root.prev = prevCopied;
			return doFlatten(root.next, root);
		}
		else {
			NestedNode last = doFlatten(root.data, prevCopied);
			return doFlatten(root.next, last);
			
		}
	}
	public static void printAllPathToLeaves(TreeNode root) {
		// pre-order (dfs) then print when reach leaf
		preOrder(root, new LinkedList<>());
	}
	
	static void preOrder(TreeNode root, LinkedList<Character> prefix) {
		if (root == null) return;
		
		prefix.addLast(root.label);
		// visit
		if (root.left == null && root.right == null) {
			System.out.println(prefix);
		}
		else {
			preOrder(root.left, prefix);
			preOrder(root.right, prefix);
		}
		// restore
		prefix.removeLast();
	}
	public static void printByColumns(TreeNode root) {
		// left - 1
		// right + 1
		Map<TreeNode, Integer> tempPosition = new HashMap<>();
		Map<Integer, List<TreeNode>> pos = new HashMap<>();
		
		LinkedList<TreeNode> q = new LinkedList<>();
		q.add(root);
		tempPosition.put(root, 0);
		// do bfs (level-order) to ensure top-down order
		
		int min = 0;
		while (!q.isEmpty()) {
			TreeNode  cur = q.removeFirst();
			Integer column = tempPosition.get(cur);
			if (column < min) {
				min = column;
			}
			List<TreeNode> col = pos.get(column);
			if (col == null) {
				pos.put(column, col = new LinkedList<>());
			}
			col.add(cur);
			
			if (cur.left != null) {
				tempPosition.put(cur.left, column - 1);
				q.addLast(cur.left);
			}
			
			if (cur.right != null) {
				tempPosition.put(cur.right, column + 1);
				q.addLast(cur.right);
			}
		}
		
		// print
		while (!pos.isEmpty()) {
			List<TreeNode> col = pos.remove(min);
			System.out.println(col);
			++min;
		}
		
	}
	public static void  preOrder(TreeNode root) {
		if (root == null) {
			return;
		}
		
		System.out.print(root.label + " ");
		preOrder(root.left);
		preOrder(root.right);
		
	}
	
	public static void inOrder(TreeNode root) {
		if (root == null) {
			return;
		}
		inOrder(root.left);
		System.out.print(root.label + " ");
		inOrder(root.right);
	}
	
	public static TreeNode findNext(TreeNode root, TreeNode target) {
		return lookupNext(root, target, null);
	}
	
	static TreeNode lookupNext(TreeNode cur, TreeNode target, TreeNode parent) {
		if (cur == null) {
			return parent;
		}
		else {
			if (cur.label == target.label) {
				// if has no right child
				if (cur.right == null) {
					// then it's parent
					return parent;
				}
				else {
					// find the left most
					TreeNode node = cur.right;
					while (node.left != null) {
						node = node.left;
					}
					return node;
				}
			}
			else if (cur.label < target.label) {
				return lookupNext(cur.right, target, cur);
			}
			else {
				return lookupNext(cur.left, target, cur);
			}
			
		}
	}
	
	
	
	static class TreeNode {
		final char label;
		TreeNode left;
		TreeNode right;
		boolean isParent;
		TreeNode(char ch ) {
			label = ch;
		}
	}
	
	public static void findMaxEachLevel(TreeNode root) {
		LinkedList<TreeNode> q = new LinkedList<>();
		q.add(root);
		
		while (!q.isEmpty()) {
			int size = q.size();
			TreeNode max = null;
			TreeNode secondMax = null;
			
			while (size > 0) {
				// all these are the same level
				TreeNode cur = q.removeFirst();
				
				// visit 
				{
					if (max != null && secondMax != null) {
						if (cur.label > max.label) {
							max = cur;
						}
						else if (cur.label > secondMax.label) {
							secondMax = cur;
						}
					}
					else if (max == null) {
						max = cur;
					}
					else if (secondMax == null) {
						secondMax = cur;
					}
				}
				
				if (cur.left != null) {
					q.addLast(cur.left);
				}
				if (cur.right != null) {
					q.addLast(cur.right);
				}
			}
			System.out.print("max: " + max + ", second max: " + secondMax);
		}
	}
	
	public static TreeNode[] toArray(TreeNode root) {
		// parent, left, right
		LinkedList<TreeNode> ret = new LinkedList<>();
		
		doToList(root, ret);
		
		return ret.toArray(new TreeNode[ret.size()]);
	}
	
	public static TreeNode fromArray(TreeNode[] arr) {
		return fromArray(arr, new int[]{0});
	}
	
	static TreeNode fromArray(TreeNode[] arr, int[] i) {
		TreeNode root = arr[i[0]];
		
		if (root != null && root.isParent) {
			++i[0];
			root.left = fromArray(arr, i);
			++i[0];
			root.right = fromArray(arr, i);
		}
		
		return root;
	}
	static void doToList(TreeNode root, List<TreeNode> retList) {
		if (root == null) {
			retList.add(null);
			return;
		}
		else {
			retList.add(root);
			if (root.left == null && root.right == null) {
				// leaf node, dont need the space holder
				root.isParent = false;
				return;
			}
			else {
				root.isParent = true;
			}
			doToList(root.left, retList);
			doToList(root.right, retList);

		}
	}
	
	public static void inOrderIter(TreeNode root) {
		 TreeNode cur = root;
		 Stack<TreeNode> stack = new Stack<>();
		 
		 while (cur != null || !stack.isEmpty()) {
			 if (cur == null) {
				 cur = stack.pop();
				 // visit
				 System.out.print(cur.label + " ");
				 cur = cur.right;
			 }
			 else {
				 stack.push(cur);
				 cur = cur.left;
			 }
		 }
	}
	
	public static void preOrderIter(TreeNode root) {
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			// visit
			System.out.print(cur.label + " ");
			if (cur.right != null) {
				stack.push(cur.right);
			}
			if (cur.left != null) {
				stack.push(cur.left);
			}
		}
	}
	
	public static void postOrder(TreeNode root) {
		if (root == null) {
			return;
		}
		
		postOrder(root.left);
		postOrder(root.right);
		System.out.print(root.label + " ");
	}
	public static void postOrderIter(TreeNode root) {
		// left right parent
		Set<TreeNode> visited = new HashSet<>();
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			// visit if is leaf
			if ( (cur.left == null || visited.contains(cur.left))
					&& (cur.right == null || visited.contains(cur.right)) ) {
				System.out.print(cur.label + " ");
				visited.add(cur);
			}
			else {
				// put it back
				stack.push(cur);
				
				if (cur.right != null) {
					stack.push(cur.right);
				}
				
				if (cur.left != null) {
					stack.push(cur.left);
				}
			}
		}
	}
	
	public static TreeNode lca(TreeNode root, TreeNode x, TreeNode y) {
		if (root == null || root == x || root == y) return root;
		
		// search left
		TreeNode ret = lca(root.left, x, y);
		if (ret != null) return ret;
		// search right
		ret = lca(root.right, x, y);
		return ret;
	}
	
	public static boolean isBst(TreeNode root) {
		// if in order is right, then it is right?
		return isBst(root, new TreeNode[]{null});
	}
	
	public static boolean isBst(TreeNode root, TreeNode[] prev) {
		if (root == null) {
			return true;
		}
		else {
			// left
			if(!isBst(root.left, prev)) {
				return false;
			}
			
			// parent
			if (prev[0] != null && root.label < prev[0].label) return false;
			prev[0] = root;
			
			return isBst(root.right, prev);
		}
	}
	/// ------- end trees
	
	// graphs/words
	public static class WordNode {
		boolean visited = false;
		boolean inQueue = false;
		int dist = Integer.MAX_VALUE;
		
		final String label;
		Map<String, WordNode> children = new HashMap<>();
		
		public WordNode(String s) {
			label = s;
		}
		
		@Override
		public String toString() {
			return label;
		}
	}
	
	public static interface Dict {
		public String buildWord(char[] ch);
		public String buildWord(char[] ch, int i, int j);
		public char[] getAlphabets();
		boolean isValid(String word);
	}
	
	static Map<String, WordNode> graph = new HashMap<>();
	
	public static void findTransform(String start, String end, Dict dict) {
		buildGraph(start, dict);
		
		if (graph.containsKey(start) && graph.containsKey(end)) {
			// bfs from start
			WordNode startNode = graph.get(start);
			WordNode endNode = graph.get(end);
			
			// keep map of predecessors
			Map<WordNode,WordNode> preds = new HashMap<>();
			Set<WordNode> visited = new HashSet<>();
			
			LinkedList<WordNode> q = new LinkedList<>();
			q.add(startNode);
			boolean found = false;
			while (!q.isEmpty()) {
				WordNode cur = q.removeFirst();
				
				if (!visited.contains(cur)) {
					visited.add(cur);
					
					if (cur == endNode) {
						found = true;
						break;
					}
					
					// otherwise  add children
					for (WordNode child : cur.children.values()) {
						if (!visited.contains(child)) {
							q.addLast(child);
							preds.put(child, cur);
						}
					}
				}
			}
			
			if (!found) {
				System.out.println("cant transform");
				return;
			}
			System.out.print("transformation: ");
			WordNode n = endNode;
			while (n != startNode) {
				System.out.print(n.label + " <-- ");
				n = preds.get(n);
			}
			System.out.println(startNode.label);
			
			System.out.println(dijstra(startNode, endNode));
			
		}
		else {
			System.out.println("transformation not possible");
		}
	}
	
	public static Map<String, WordNode> buildGraph(String start, Dict dict) {
		if (!dict.isValid(start)) return null;
		doBuild(start, graph, dict);
		return graph;
	}
	
	public static WordNode doBuild(String word, Map<String, WordNode> g, Dict dict) {
		if (g.containsKey(word)) {
			return g.get(word);
		}
		
		// otherwise build it
		WordNode node = new WordNode(word);
		g.put(word, node);
		
		char[] chars = word.toCharArray();
		// build children by swapping 1 char with a diff
		for (char ch : dict.getAlphabets()) {
			for(int i = 0; i < chars.length; ++i) {
				final char orig = chars[i];
				chars[i] = ch;
				String newWord = dict.buildWord(chars);
				if(newWord != null) {
					node.children.put(newWord, doBuild(newWord, g, dict));
				}
				chars[i] = orig;
			}
		}
		
		return node;
	}
	
	public static List<WordNode> dijstra(WordNode start, WordNode end) {
		Map<WordNode, WordNode> preds = new HashMap<>();
		PriorityQueue<WordNode> q = new PriorityQueue<>(new Comparator<WordNode>(){
			@Override
			public int compare(WordNode o1, WordNode o2) {
				return Integer.compare(o1.dist, o2.dist);
			}
		});
		
		start.dist = 0;
		q.add(start);
		boolean found = false;
		while (!q.isEmpty()) {
			WordNode cur = q.poll();
			
			if (!cur.visited) {
				cur.visited = true;
				
				if (cur == end) {
					found = true;
					break;
				}
				
				// visit children
				final int newDist = cur.dist + 1;
				for (WordNode kid : cur.children.values()) {
					
					if(!kid.visited && newDist < kid.dist) {
						// if already in quueue, need to remove it to update
						if (kid.inQueue) {
							q.remove(kid);
						}
						
						kid.dist = newDist;
						kid.inQueue = true;
						q.add(kid);
						preds.put(kid, cur);
					}
				}
			}
		}
		
		if (!found) {
			return null;
		}
		else {
			LinkedList<WordNode> path = new LinkedList<>();
			WordNode n = end;
			while (n != null) {
				path.addFirst(n);
				n = preds.get(n);
			}
			
			return path;
			
		}
	}
	// ---- end graphs
	
	// trie  -----
	public static int countWords(String st, Dict dict) {
		char[] text = st.toCharArray();
		Set<String> ret = new HashSet<>();
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = i;j < text.length; ++j) {
				// text[i][j]
				String wd = dict.buildWord(text, i, j);
				if (wd != null) {
					ret.add(wd);
				}
			}
		}
		return ret.size();
	}
	
	public static int countWordsWithTrie(String st, Set<String> validWords) {
		Trie trie = buildTrie(validWords);
		Set<String> ret = new HashSet<>();
		
		char[] text = st.toCharArray();

		for (int i = 0; i < text.length; ++i) {
			Trie.lookupPrefix(trie, text, i, ret);
		}
		return ret.size();
	}
	
	public static int countOnes(int num) {
		// 10011
		// 100 & 1
		int count = 0;
		while (num > 0) {
			if ((num & 1) != 0)
				++count;
			num = num >> 1;
			
		}
		
		return count;
		
	}
	
	public static int countOnes2(int n) {
		int count=0;
		while(n!=0){
		  n = n&(n-1);
		  count++;
		}
		return count;
	}
	static Trie buildTrie(Set<String> words) {
		Trie root = new Trie(null);
		
		for (String word : words) {
			root.addWord(word, words);
		}
		
		return root;
	}
	// E -> E1+E1
	// E1 -> F * F
	public static boolean match(List<String> words, String regex) {
		TrieNode root = buildTrieNode(words);
		
		// look up
		return root.lookup(regex);
	}
	
	public static TrieNode buildTrieNode(List<String> words) {
		TrieNode root = new TrieNode(null);
		for (String s : words) {
			root.addWord(s);
		}
		
		return root;
	}
	public static class TrieNode {
		String word;
		Map<Character, TrieNode> children;
		public TrieNode(String st) {
			word = st;
		}
		
		public boolean lookup(String regex) {
			return lookup(this, regex.toCharArray(), 0);
		}
		
		public static boolean lookup(TrieNode root, char[] text, int i) {
			if (i == text.length) {
				return root.word != null;
			}
			
			if (text[i] != '*') {
				TrieNode next = root.children == null ? null : root.children.get(text[i]);
				return lookup(next, text, i+1);
			}
			else {
				// could match antyhin
				if (root.children == null) return false;
				boolean ret = false;
				for (TrieNode next : root.children.values()) {
					ret |= lookup(next, text, i+1);
				}
				return ret;
			}
		}
		public void addWord(String word) {
			doAdd(this, word.toCharArray(), 0, new StringBuilder());
		}
		
		static void doAdd(TrieNode root, char[] text, int i, StringBuilder prefix) {
			if (i == text.length) {
				if (root.word == null) {
					root.word = prefix.toString();
				}

				prefix.append(text[i]);
				TrieNode node = root.children == null ? null : root.children.get(text[i]);
				if (node == null) {
					node = new TrieNode(null);
					if (root.children == null) {
						root.children = new HashMap<>();
					}
					root.children.put(text[i], node);
				}

				doAdd(node, text, i + 1, prefix);
			}

		}
	}
	public static class Trie {
		// null means it's an "internal" node and the word can't stop here
		final String word;
		Map<Character, Trie> children;
		
		public Trie(String word) {
			this.word = word;
		}
		
		public void addWord(String w, Set<String> validWords) {
			doAdd(this, w.toCharArray(), 0, new StringBuilder(), validWords);
		}
		
		static void doAdd(Trie root, char[] ch, int i, StringBuilder prefix, Set<String> validWords) {
			if (i >= ch.length) return;
			
			char cur = ch[i];
			if (root.children == null) {
				root.children = new HashMap<>();
			}
			prefix.append(cur);
			
			Trie curTrie = root.children.get(cur);
			if (curTrie == null) {
				String st = prefix.toString();
				if (!validWords.contains(st)) {
					st = null;
				}
				curTrie = new Trie(st);
				root.children.put(cur, curTrie);
			}
			
			
			doAdd(curTrie, ch, i+1, prefix, validWords);
		}
		
		public boolean lookup(String w) {
			return doLookup(this, w.toCharArray(), w.length(), 0) != null;
		}
		
		public String lookup(char[] text, int len, int low) {
			return doLookup(this, text, len, low);
		}
		
		public static void lookupPrefix(Trie root, char[] word, int i, Set<String> matches) {
			
			if (root.word != null) {
				matches.add(root.word);
			}
			if (i < word.length) {
				Trie next = root.children == null ? null : root.children.get(word[i]);
				if (next == null) return;
				
				lookupPrefix(next, word, i+1, matches);
			}
			
			
		}
		static String doLookup(Trie root, char[] word, int len, int i) {
			if (i == len) {
				return root.word ;
			}
			
			char cur = word[i];
			Trie next = root.children == null ? null : root.children.get(cur);
			if (next == null) return null;
			return doLookup(next, word, len, i+1);
			
		}
	}
	// end trie ----
	
	// dynamic
		
	public int[] longestIncreasing(int[] nums) {
		// contain the last element
		int[] tails = new int[nums.length];
		int len = 0;
		
		for (int n : nums) {
			if (len == 0) {
				tails[0] = n;
				++len;
			}
			else {
				// if it's < tail[0], then  start a new at tail[0]
				if (n < tails[0]) {
					tails[0] = n;
				}
				// if it's > tail[len-1], then add a new at tail[len]
				else if (n > tails[len-1]) {
					tails[len] = n;
					++len;
				}
				else {
					// find the  floor
					// the largest tail that is < n and replace the next element
					// or just find the ceiling idx ( smallest > tail)
					// then replace it with tail
					tails[findCeilingIdx(tails, n, len)] = n;
				}

			}
		}
		
		return tails;
	}
	
	public int findCeilingIdx(int[] nums, int target, int len) {
		// this is sorted, guaranteed
		int low = 0;
		int high = len - 1;
		while (low < high) {
			int mid = (low+high) / 2;
			// do not accept equal
			if (nums[mid] <= target) {
				low = mid + 1;
			}
			else {
				high = mid;
			}
			
		}
		
		return high;
	}
	
	public static void nCutPalin(char[] text) {
		// s[i][j] is palin if s[i] == s[j] && isPalin[i+1][j-1]
		// find all sub palin first
		boolean isPalin[][] = new boolean[text.length][text.length];
		for (int i = 0; i < text.length; ++i) {
			isPalin[i][i] = true;
			if (i < text.length -1 && text[i] == text[i+1]) {
				isPalin[i][i+1] = true;
			}
		}
		
		for (int len = 3; len <= text.length; ++len) {
			int maxI = text.length - len;
			for (int i = 0; i <= maxI; ++i) {
				int j = i + len - 1;
				isPalin[i][j] = isPalin[i+1][j-1] && text[i] == text[j];
			}
		}
		
		int[] nCuts = new int[text.length];
		for (int i = 0; i < text.length; ++i) {
			if (!isPalin[0][i]) {
				int minCuts = Integer.MAX_VALUE;
				for (int j = 0; j < i; ++j) {
					if (isPalin[j+1][i] && nCuts[j] + 1 < minCuts) {
						minCuts = nCuts[j] + 1;
					}
				}
				nCuts[i] = minCuts;
			}
		}
	}
	// end dynamic 
	
	static void swap(int[] num, int i, int j) {
		int t = num[i];
		num[i] = num[j];
		num[j] = t;
	}
	
	
	// sort
	public static int[] findMinKElements(int[] nums, int k) {
	
		// min heapify
		for (int i = nums.length / 2; i >= 0; --i) {
			minHeapify(nums, i, nums.length);
		}
		
		
		// extract k times
		int[] ret = new int[k];
		int j = 0;
		for (int i = nums.length - 1; j < ret.length; ++j ,--i) {
			ret[j] = nums[0];
			nums[0] = nums[i];
			minHeapify(nums, 0, i);
		}
		
		return ret;
	}
	
	
	public static int orderStats(int[] nums, int k, int low, int high) {
		int i = partition(nums, low, high);
		if (i == k) {
			return nums[i];
		}
		else if (i < k) {
			return orderStats (nums, k, i, high);
		}
		else {
			return orderStats(nums, k, low, i);
		}
	}
	public static void quicksort(int[] nums, int low, int high) {
		if (low < high) {
			int i = partition(nums, low, high);
			quicksort(nums, low, i - 1);
			quicksort(nums, i, high);
		}
	}
	static int partition(int[] nums, int low, int high) {
		int pivot = nums[high];
		int i = low - 1;
		for (int j = low; j < high; ++j) {
			if (nums[j] <= pivot) {
				++i;
				swap(nums, i, j);
			}
		}
		swap(nums, i+1, high);
		return i+1;
	}
	public static void heapSort(int[] nums) {
		for (int i = nums.length / 2; i >= 0; --i) {
			maxHeapify(nums, i, nums.length);
		}
		
		for (int i = nums.length - 1; i >= 0; --i) {
			// put max at the end
			swap(nums, 0, i);
			maxHeapify(nums, 0, i);
		}
	}
	
	public static void maxHeapify(int[] nums, int i, int len) {
		// put max at top
	}
	public static void mergeSort(int[] nums) {
		mergeSort(nums, 0, nums.length - 1);
	}
	
	static void merge(int[] nums, int low, int mid, int high) {
		int[] merged = new int[high-low + 1];
		
		int k = 0;
		int i = low;
		int j = mid + 1;
		for (; i <= mid && j <= high; ++k) {
			if (nums[i] < nums[j]) {
				merged[k] = nums[i++];
			}
			else {
				merged[k] = nums[j++];
			}
		}
		
		while (i <= mid) {
			merged[k++] = nums[i++];
		}
		
		while (j <= high) {
			merged[k++] = nums[j++];
		}
		
		k = 0;
		for (i = low; k < merged.length; ++i, ++k) {
			nums[i] = merged[k];
		}
	}
	static void mergeSort(int[] nums, int low, int high) {
		if (low < high) {
			int mid = (low+high) / 2;
			// 0 1, mid = 0
			mergeSort(nums, low, mid);
			mergeSort(nums, mid+1, high);
			
			merge(nums, low, mid, high);
		}
	}
	public static void colorSort(int[] colors) {
		// 0 1 or 2
		
		int head = 0;
		int tail = colors.length - 1;
		
		for (int i = head; i <= tail; ++i) {
			switch(colors[i]) {
			case 0: // put the front 
				swap(colors, head, i); 
				++head;
				break;
			case 2:
				swap(colors, tail, i);
				--tail;
				break;
				
			}
		}
	}
	
	public static void partialSort(int[] nums, int k) {
		// guarantee that each window of length k is already sorted
		PriorityQueue<Integer> heap = new PriorityQueue<>(k);
		for (int i = 0; i < k; ++i) {
			heap.add(nums[i]);
		}
		
		// unsorted: i = [k, len)
		// sorted: j = [0, len)
		for (int i = k, j = 0; j < nums.length; ++j, ++i) {
			if (i < nums.length) {
				nums[j] = heap.poll();
				heap.add(nums[i]);
			}
			else {
				// just pull from heap
				nums[j] = heap.poll();
			}
		}
	}
	
	public static void partialSortWithHeap(int[] nums, int k) {
		int[] heap = new int[k];
		for (int i = 0; i < k; ++i) {
			heap[i] = nums[i];
		}
		
		// build the heap
		int size = k;
		for (int i = heap.length / 2; i >= 0; --i) {
			minHeapify(heap, i,k);
		}
		
		for (int j = 0, i = k; j < nums.length; ++j, ++i) {
			if (i < nums.length) {
				nums[j] = heap[0];
				heap[0] = nums[i];
				minHeapify(heap, 0, k);
			}
			else {
				// jus extract
				nums[j] = heap[0];
				heap[0] = heap[size-1];
				--size;
				minHeapify(heap, 0, size);
			}
		}
	}
	
	public static void minHeapify(int [] heap, int i, int len) {
		int min = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < len && heap[l] < heap[min]) {
			min = l;
		}
		
		if (r < len && heap[l] < heap[min]) {
			min = r;
		}
		
		if (min != i) {
			swap(heap, i, min);
			minHeapify(heap, min, len);
		}
	}
	
	//---- end sort
	
	//--- numbers --
	
	private static TreeMap<Integer, String> units = new TreeMap<>();
	private static  final String[] DIGIT = new String[] {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
	private static final String[] TENS = new String[] {"thir", "four", "fif", "six", "seven", "eight", "nine"};
	private static final String[] TWO_DIGIT = new String[] {null, null, "twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};
	static {
		units.put(100, "hundred");
		units.put(1000, "thousand");
		units.put(1_000_000, "million");
		units.put(1_000_000_000, "billion");
	}
	public static String spell(int number) {
		StringBuilder ret = new StringBuilder();
		doSpell(number, ret);
		return ret.toString();
	}
	static void doSpellTwoDigit(int number, StringBuilder buf) {
		// 
		if (number < 13) {
			buf.append(DIGIT[number]);
		}
		else if (number < 20){
			// 13 -- 19
			buf.append(TENS[number % 10 - 3]).append("teen");
		}
		else {
			buf.append(TWO_DIGIT[number /10]);
			int remainder = number % 10;
			// dont want to say "twenty-zero"
			if (remainder != 0) {
				buf.append("-");
				doSpellTwoDigit(remainder, buf);
			}
		}
	}
	
	static void doSpell(int number, StringBuilder buf) {
		if (number < 0) {
			buf.append("negative ");
			doSpell (number * -1, buf);
		}
		else {
			if (number < 100) {
				doSpellTwoDigit(number, buf);
			}
			else {
				// 12230
				// 12
				// find the largest pwoer of 10 that's <= number
				Integer unit = units.floorKey(number);
				int front = number / unit;
				doSpell(front, buf);
				buf.append(" ").append(units.get(unit));
				
				int back = number % unit;
				if (back > 0) {
					buf.append(", ");
					doSpell(back, buf);
				}
			}
		}
	}
	public static class Meeting {
		int start;
		int end;
		public Meeting(int s, int e) {
			start = s;
			end = e;
		}
	}
	public static Meeting[] mergeOverlap(Meeting[] meetings) {
		// sort by start time?  have to
		Arrays.sort(meetings,
				    new Comparator<Meeting>(){

						@Override
						public int compare(Meeting o1, Meeting o2) {
							Integer ret = Integer.compare(o1.start, o2.start);
							return ret == 0 ? Integer.compare(o1.end, o2.end) : ret;
						}
			
		});
		
		for (int i = 0, j = 1; j < meetings.length; ++i, ++j) {
			if (meetings[i].end >= meetings[j].start) {
				Meeting combined = new Meeting(meetings[i].start, Math.max(meetings[i].end, meetings[j].end) );
				meetings[j] = combined;
				// null out i
				meetings[i] = null;
			}
		}
		
		return meetings;
	}
	
	public static int findMinRooms(Meeting[] meetings) {
		// list of meeting's start and end times
		
		// sort the meetings by start time
		// [a,    b] 
		// [a,   z]   [  ]
		// [a,k]      [  ]   
		//    [ ]
		//   
		// if there's overlaps ==> 2 rooms
		Arrays.sort(meetings, new Comparator<Meeting>(){
			@Override
			public int compare(Meeting o1, Meeting o2) {
				int startD = Integer.compare(o1.start, o2.start);
				if (startD != 0) return startD;
				return Integer.compare(o1.end, o2.end);
			}
		});
		
		PriorityQueue<Integer> rooms = new PriorityQueue<>();
		// there's a room ending at -1 (ie., available)
		rooms.add(-1);
		
		for (int i = 0; i < meetings.length; ++i) {
			// if the earliest a room can be empty is
			// <= start time, then it can be used ( = is because if start and end are the same, it's ok)
			if (rooms.peek() <= meetings[i].start) {
				rooms.poll();
				rooms.add(meetings[i].end);
			}
			else {
				// otherwise, need new room
				rooms.add(meetings[i].end);
			}
		}
		
		return rooms.size();
		
	}
	public static List<int[]> sum2(int[] nums, int target) {
		Arrays.sort(nums);
		List<int[]> ret = new LinkedList<>();
		
		int low = 0;
		int high = nums.length - 1;
		while (low < high) {
			int sum = nums[low] + nums[high];
			if (sum == target) {
				// skip dups
				while (low < nums.length - 1 && nums[low] == nums[low+1]) ++low;
				while (high > 0 && nums[high] == nums[high-1]) --high;
				
				int[] p = new int[] {nums[low], nums[high]};
				ret.add(p);
				++low;
				--high;
			}
			else if (sum > target) {
				--high;
			}
			else {
				++low;
			}
		}
		
		return ret;
	}
	
	public static List<int[]> sum2Map(int[] nums, int target) {
		// count
		List<int[]> ret = new LinkedList<>();
		Map<Integer, Integer> dist = new HashMap<>();
		for (int n : nums) {
			int d = target - n;
			Integer count = dist.get(d);
			if (count != null && count > 0) {
				ret.add(new int[] {n, d});
				dist.put(d, 0);
			}
			else if (!dist.containsKey(n)) {
				dist.put(n, 1);
			}
		}
		
		return ret;
	}
	
	public static List<int[]> sum3(int[] nums, int target) {
		Arrays.sort(nums);
		List<int[]> ret = new LinkedList<>();
		for (int i = 0; i < nums.length; ++i) {
			int first = nums[i];
			int diff = target - first;
			int low = i + 1;
			int high = nums.length - 1;
			while (low < high) {
				int sum = nums[low] + nums[high];
				if (sum == diff) {
					while (low < nums.length - 1 && nums[low] == nums[low+1]) ++low;
					while (high < nums.length - 1 && nums[high] == nums[high+1]) --high;
					
					ret.add(new int[] {first, nums[low], nums[high]} );
				}
				else if (sum < diff) {
					++low;
				}
				else {
					--high;
				}
			}
		}
		return ret;
	}
	
	// ----- array set 
	public static void permute(int[] nums, int i) {
		if (i == nums.length) {
			for (int n : nums) {
				System.out.print(n + " ");
			}
			System.out.println();
		}
		else {
			
			for (int j = i; j < nums.length; ++j) {
				swap(nums, i, j);
				permute(nums, i+1);
				swap(nums, i, j);
			}
		}
	}
	public static void permuteSkipDups(int[] nums) {
		// sort to ensure the identical element is next to each other
		Arrays.sort(nums);
		
		
	}
	
	static void permuteSkipDups(int[] nums, int low) {
		if (low == nums.length - 1) {
			// print
		}
		else {
			Set<Integer> seen = new HashSet<>();
			for (int i = low; i < nums.length; ++i) {
				if (!seen.contains(nums[i])){
					seen.add(nums[i]);
					
					swap(nums, i, low);
					permuteSkipDups(nums, low+1);
					swap(nums, i, low);
				}
			}
		}
	}
	
	public static void powersetIter(Set<Integer> pool) {
		// 2 ^n
		// 00
		// 01
		// 10
		// 11
		int n = (int) (Math.pow(2, pool.size()) - 1);
		
		while (n >= 0) {
			int i = 0;
			for (Integer num : pool) {
				if ((n & (1 << i) ) != 0) {
					System.out.print(num + " ");
				}
				++i;
			}
			System.out.println();
		}
	}
	
	public static void powerset(int...nums) {
		LinkedList<Integer> list = new LinkedList<>();
		for (int n : nums) list.add(n);
		
		powersetRecursive(list, new HashSet<>());
	}
	public static void powersetRecursive(List<Integer> pool, Set<Integer> cur) {
		if (pool.isEmpty()) {
			System.out.println(cur);
		}
		else {
			Integer element = pool.remove(pool.size() - 1);
			//  NOT include
			powersetRecursive(pool, cur);
			// or DO include
			cur.add(element);
			powersetRecursive(pool, cur);

			// restore statemm
			cur.remove(element);
			pool.add(element);
		}
	}
	// --- end array set
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = Integer.parseInt(in.nextLine().trim());
		
		while (N > 0) {
			final int LEN = Integer.parseInt(in.nextLine().trim());
			int[] arr = toArray(in.nextLine(), LEN);
			int[] dep = toArray(in.nextLine(), LEN);
			System.out.println(findMinPlatform(arr, dep));
			
			--N;
		}
	}
	
	public static int findMinPlatform(int[] arr, int[] dep) {
		int LEN = dep.length;

		PriorityQueue<Integer> platforms = new PriorityQueue<>();
		platforms.add(-1);
		for (int i = 0; i < LEN; ++i) {
			if (arr[i] > platforms.peek()) {
				// dont need a new one, just reuse
				platforms.poll();
				platforms.add(dep[i]);
			}
			else {
				platforms.add(dep[i]);
			}
		}
		return platforms.size();
	}
	static int[] toArray(String st, int len) {
		int[] ret = new int[len];
		String parts[] = st.split("\\s+");
		for (int i = 0; i < len; ++i) {
			ret[i] = Integer.parseInt(parts[i]);
		}
		return ret;
	}
	
	public static class CircularQueue<T>  {
		private static final int INITIAL_CAP = 10;
		private Object[] buffer;
		private int size;
		private int head;
		private int tail;
		public CircularQueue() {
			this(INITIAL_CAP);
		}
		public CircularQueue(int cap) {
			buffer = new Object[cap];
			head = -1;
			tail = -1;
			size = 0;
		}
		
		public void addLast(Object o) {
			if (size == 0) {
				head = tail = 0;
				
			}
			else if (size < buffer.length){
				tail = (tail + 1) % buffer.length;
			}
			else {
				growBuffer(null);
				++tail;
			}
			buffer[tail] = o;
			++size;
		}
		
		public void addFirst(Object o) {
			
			if (size == 0) {
				head = tail = 0;
				buffer[head] = o;
				++size;
			}
			else if (size < buffer.length) {
				head = head == 0 ? buffer.length - 1 : head - 1;
				buffer[head] = o;
				++size;
			}
			else {
				++size;growBuffer(o);
			}
		}
		// private heaper
		private void growBuffer(Object newHead) {
			Object[] newBuf = new Object[buffer.length * 2];
			int i = 0;
			if (newHead != null) {
				newBuf[i] = newHead;
				++i;
			}
			for (int j = head; i < size; ++i, j=(j+1)%buffer.length) {
				newBuf[i] = buffer[j];
			}
			head = 0;
			tail = i;
			buffer = newBuf;
		}
		
	}
	
	public static int weightedRandom(int[] nums) {
		// [ ... x....][....z....][..t..]
		double[] weights = new double[nums.length];
		double w = 0;
		for (int i = 0; i < nums.length; ++i) {
			weights[i] = w + nums[i];
			w = weights[i];
		}
		for (int i = 0; i < nums.length; ++i) {
			// divided by the total sum
			weights[i] = weights[i] / w;
		}
		
		// [0, 1) --> x
		//
		// [0, w[0]) --> 0
		// [w[0], w[1]) --> 1
		// ...
		// find the smalest number that is > x (strict ceiling
		return nums[findCeilIdx(weights, Math.random())];
		
	}
	
	public static int findCeilIdx (double[] nums, double target) {
		int low = 0;
		int high = nums.length - 1;
		while (low < high) {
			int mid = (low+high) / 2;
			if (nums[mid] <= target) {
				low = mid+1;
			}
			else {
				high = mid;
			}
		}
		return high;
	}
	
	static final int LAND = 1;
	static final int SEA = 0;
	
	static class Cell {
		final int x;
		final int y;
		Cell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Object o) {
			return o != null && o instanceof Cell && ((Cell)o).x == x && ((Cell)o).y == y;
		}
		
		public int hashCode() {
			return x * 31 + y;
		}
	}
	
	public static List<Integer> countIslandsSizes(int[][] map) {
		List<Integer> sizes = new LinkedList<>();
		
		Set<Cell> visited = new HashSet<>();
		for (int x = 0; x < map.length; ++x) {
			for (int y = 0; y < map[x].length; ++y) {
				Cell cell = new Cell(x,y);
				if (!visited.contains(cell)) {
					if (map[x][y] == LAND) {
						
						// do bfs here to find the island size
						LinkedList<Cell> q = new LinkedList<>();
						q.add(cell);
						int count = 0;
						while (!q.isEmpty()) {
							Cell cur = q.removeFirst();
							if (!visited.contains(cur)) {
								++count;
								visited.add(cell);
								
								// add children
								addChild(cur.x - 1, cur.y, q, map, visited);
								addChild(cur.x + 1, cur.y, q, map, visited);
								addChild(cur.x, cur.y -1, q, map, visited);
								addChild(cur.x, cur.y - 1, q, map, visited);
							}
						}
						sizes.add(count);
					}
				}
				
			}
		}
		return sizes;
	}
	
	static Set<Cell> createLandCells(int[][] map) {
		Set<Cell> ret = new HashSet<>();
		
		for (int x = 0; x < map.length; ++x) {
			for (int y = 0; y < map[x].length; ++y) {
				if (map[x][y] == LAND) {
					ret.add(new Cell(x,y));
				}
			}
		}
		
		return ret;
	}
	
	public static List<Integer> countIslandsSizesOpt(int[][] map) {
		List<Integer> sizes = new LinkedList<>();
		Set<Cell> landCells = createLandCells(map);
		
	
		while (!landCells.isEmpty()) {
			// pick a random unvisited
			Cell cell = landCells.iterator().next();
			
			Set<Cell> visited =  new HashSet<>(landCells.size());
			LinkedList<Cell> q = new LinkedList<>();
			q.add(cell);
			int count = 0;
			
			while (!q.isEmpty()) {
				Cell cur = q.removeFirst();
				if (!visited.contains(cur)) {
					++count;
					visited.add(cell);
					
					// add children
					addChild(cur.x - 1, cur.y, q, map, visited);
					addChild(cur.x + 1, cur.y, q, map, visited);
					addChild(cur.x, cur.y -1, q, map, visited);
					addChild(cur.x, cur.y - 1, q, map, visited);
				}
			}
			sizes.add(count);
			// remove all from current pool
			landCells.removeAll(visited);
		}
	
		return sizes;
	}
	
	
	
	public static void addChild(int x, int y, LinkedList<Cell> q, int[][] map, Set<Cell> visited) {
		if (x >= 0 && x < map.length && y >= 0 && y < map[x].length
				&& map[x][y] == LAND) {
			Cell cell = new Cell(x,y);
			if (!visited.contains(cell)) {
				q.addLast(cell);
			}
		}
	}
	
	public static void sellingStockOnce(int[] prices) {
		int buy = 0;
		
		int maxBuy = 0;
		int maxSell = 0;
		
		for (int price : prices) {
			if (price < buy) {
				buy = price;
			}
			else if (price - buy > maxSell - maxBuy) {
				maxSell = price;
				maxBuy = buy;
			}
		}
		
	}
	
	
	public static void sellingStockMultiple(int[] prices) {
		// buy and sell multiple, findp profits
		
		int iBuy = 0;
		int iSell = 0;
		
		int maxProfit = 0;
		boolean buyPhase = true;
		
		while (iBuy < prices.length - 1) {
			if (buyPhase) {
				// want the min
				if (prices[iBuy +1 ] <= prices[iBuy]) {
					++iBuy;
				}
				else {
					buyPhase = false;
					iSell = iBuy;
				}
			}
			else {
				if (iSell < prices.length - 1
						&& prices[iSell + 1] >= prices[iSell]) {
					++iSell;
				}
				else {
					// this is the max, sell it
					maxProfit += prices[iSell] - prices[iBuy];
					
					iBuy = iSell;
					buyPhase = true;
				}
			}
		
		}
	}
	
	// strings
	public static void simplify(String text) {
		System.out.println("original: "  + text);
		simplify(text.toCharArray());
	}
	public static void simplify(char[] text) {
		LinkedList<String> parts = new LinkedList<>();
		// /a/b/c
		int i = 0;
		boolean hasPreceding = text[0] == '/';
		if (hasPreceding) {
			++i;
		}
		while (i < text.length) {
			switch(text[i]) {
			case '/': ++i; break;
			case '.':
				// /a/./b  
				if (i == text.length - 1 || text[i+1] != '.') {
					// next is not another dot, then just ignore it
					++i;
				}
				else {
					// remove the lat component (if there is one)
					// /a/.. => a
					// ../../.
					if (parts.isEmpty() || parts.getLast().equals("..")) {
						parts.addLast("..");
					}
					else {
						parts.removeLast();
					}
					i += 2;
				}
				break;
		    default:
		    	// just names
		    	int j = i;
		    	while (j < text.length) {
		    		if (text[j] == '.' || text[j] == '/') {
		    			--j;
		    			break;
		    		}
		    		++j;
		    	}
		    	// back up if needed
		    	if (j == text.length) --j;
		    	
		    	String part = new String(text, i, j-i+1);
		    	parts.addLast(part);
		    	i = j + 1;
			}
		}
		
		if (!hasPreceding && parts.isEmpty()) {
			System.out.println("EMPTY path");
		}
		else {
			if (hasPreceding) System.out.print("/");
			i = 0;
			for (String s : parts) {
				System.out.print(s);
				
				// if not the last
				if (i < parts.size() - 1) {
					System.out.print("/");
				}
			}
		}
	}
	
	static int getCategory(int num) {
		// return 1 2 or 3
		return 1;
	}
	
	public static void sortByCategory(int[] nums) {
		int head = 0;
		int tail = nums.length - 1;
		for (int i = head; i <= tail; ++i) {
			switch(getCategory(nums[i])) {
			case 1: // swap with head
				swap(nums, i, head);
				++head;
				break;
				
			case 2:
				// do nothing
				break;
			case 3:
				swap(nums, i, tail);
				--tail;
				break;
				
			default: throw new IllegalStateException("unexpected category"); 
			}
		}
	}
	
	public static class ListNode {
		int label;

		ListNode next;
		ListNode prev;
		ListNode rand;

		public ListNode() {}
		public ListNode(int l) { label = l; }
		public String toString() {
			return label + "(" + (prev == null ? "null" : prev.label ) 
					     + ", " + (next == null ? "null": next.label)
					     + ")";
		}
	}
	public static ListNode reverseIter(ListNode head) {
		
		ListNode cur = head;
		ListNode prev = null;
		while (cur != null) {
			ListNode next = cur.next;
			cur.next = prev;
			cur.prev = next;
			prev = cur;
			cur = next;
		}
		return prev;
	}
	
	
	public static ListNode reverse(ListNode cur, ListNode prev) {
		if (cur == null) return prev;
		ListNode next = cur.next;
		cur.next = prev;
		cur.prev = next;
		return reverse(next, cur);
		
	}
	public static ListNode deepCopy(ListNode head) {
		// simple first pass
		
		// old --> new
		Map<ListNode, ListNode> oldToNew = new IdentityHashMap<Compre.ListNode, Compre.ListNode>();
		
		
		ListNode cur = head;
		ListNode copiedPrev = null;
		while (cur != null) {
			ListNode copied = new ListNode();
			copied.label = cur.label;
			oldToNew.put(cur, copied);
			
			if (copiedPrev != null) {
				copiedPrev.next = copied;
			}
			
			copiedPrev = copied;
			cur = cur.next;
		}
		
		// populate the "rand" reference
		for (ListNode old : oldToNew.keySet()) {
			ListNode copied = oldToNew.get(old);
			copied.rand = oldToNew.get(old.rand);
		}
		
		return oldToNew.get(head);
	}

	public static class SuggestBar {
		static class Trie {
			String label;
			Map<Character, Trie> children = new HashMap<>();
			
			static void collectChildren(Trie cur, List<String> ret) {
				if (cur.label != null) {
					ret.add(cur.label);
				}
				
				for (Trie kid : cur.children.values()) {
					collectChildren(kid, ret);
				}
			}
			static void lookup (Trie cur, char[] prefix, int i, List<String> ret) {
				if (i == prefix.length) {
					// matched all the prefix
					collectChildren(cur, ret);
				}
				else {
					Trie next = cur.children.get(prefix[i]);
					// no match
					if (next == null) return;
					lookup(next, prefix, i+1, ret);
				}
			}
			
			public List<String> lookup(String prefix) {
				LinkedList<String> ret = new LinkedList<>();
				lookup(this, prefix.toCharArray(), 0, ret);
				return ret;
			}
		}
	}
	
	public static List<Integer> combineSorted(List<List<Integer>> lists) {
		int size = 0;
		class MyIter {
			final Iterator<Integer> iter;
			Integer head;

			MyIter(Iterator<Integer> iter) {
				this.iter = iter;
				head = iter.next();
			}
		}
		
		PriorityQueue<MyIter> heap = new PriorityQueue<>(lists.size(),
				new Comparator<MyIter>() {

					@Override
					public int compare(MyIter o1, MyIter o2) {
						return Integer.compare(o1.head, o2.head);
					}
				});
		
		int i = 0;
		for (List<Integer> list : lists) {
			
			size += list.size();
			heap.add(new MyIter(list.iterator()));
		}
		List<Integer> ret =new ArrayList<>(size);
	
		i = 0;
		while (i < size) {
			MyIter iter = heap.poll();
			ret.add(iter.head);
			if (iter.iter.hasNext()) {
				// put it back for consideration
				iter.head = iter.iter.next();
				heap.add(iter);
			}
		}
		
		return ret;
		
	}
	
	public static int[] longAddition(int[] left, int[] right) {
		int carry = 0;
		int[] ret = new int[(int)Math.max(left.length, right.length) + 1];
		
		int k = ret.length - 1;
		int i = left.length - 1;
		int j = right.length - 1;
		
		while (k >= 0) {
			int sum = carry 
					   + (i >= 0 ? left[i] : 0)
					   + (j >= 0 ? right[j] : 0);
			ret[k] = sum % 10;
			carry = sum / 10;
			
			--k;
			--j;
			--i;
		}

		return ret;
	}
	public static void reverseWords(String text) {
		System.out.println("original: " + text);
		reverseWords(text.toCharArray());
		
	}
	public static void reverseWords(char[] text) {
		// reverse everything
		reverse(text, 0, text.length - 1);
		System.out.println("reversed whole: " + new String(text));
		// then reverse each word
		
		
		for (int i = 0; i < text.length;) {
			while(i < text.length && text[i] == ' ') ++i;
			if (i < text.length) {
				int j = i;
				while (j < text.length - 1 && text[j+1] != ' ') ++j;
				reverse(text, i, j);
				i = j + 1;
			}
		}
		System.out.println("reversed: " + new String(text));
	}
	static void swap(char[] text, int i, int j) {
		char t = text[i];
		text[i] = text[j];
		text[j] = t;
				
	}
	static void reverse(char[] text, int low, int high) {
		if (low < high) {
			int midCount =  (high-low+1) / 2;
			for (int i = 0; i < midCount; ++i) {
				swap(text, low+i, high -i);
			}
		}
	}
	
	static class CombinationOf {
		static class Trie {
			static class Node {
				// intermidate node does NOT have label
				String label;
				Map<Character, Node> children;
			}
			
			Node root = new Node();
			Set<String> words = new HashSet<>();
			
			public void addWord(String st) {
				words.add(st);
				doAdd(root, st.toCharArray(), 0);
			}
			
			boolean hasCombinationOf(String st) {
				int i[] = new int[] {0};
				char[] text = st.toCharArray();
				while (i[0] < st.length()) {
					int curI = i[0];
					
					if (!matchPrefix(root, text, i)) {
						//didn't progress
						if (i[0] == curI) {
							return false;
						}
					}
				}
				return true;
			}
			
			static boolean matchPrefix(Node cur, char[] text, int[] i) {
				if (i[0] == text.length) {
					return true;
				}
				Node next = cur.children.get(text[i[0]]);
				if (next == null) {
					// match failed here
					return false;
				}
				else {
					++i[0];
					return matchPrefix(next, text, i);
				}
			}
			
			static void doAdd(Node root, char[] text, int i) {
				if (i == text.length) {
					root.label = new String(text);
				}
				else {
					Node next = root.children.get(text[i]);
					if (next == null) {
						next = new Node();
						root.children.put(text[i], next);
					}
					doAdd(next, text, i+1);
				}
			}
		}
	}
	
	public static void findLongestPalindromeInAString(char[] text) {
		boolean[][] isPalin = new boolean[text.length][text.length];
		
		class Sub {
			int start;
			int end;
			Sub(int s, int e) {
				start = s;
				end = e;
			}
		}
		Sub[] longest = new Sub[text.length];
		
		Sub prev = null;
		for (int i = 0; i < text.length; ++i) {
			isPalin[i][i] = true;
			if (i < text.length - 1 && text[i+1] == text[i]) {
				isPalin[i][i+1] = true;
				prev = longest[i+1] = new Sub(i, i+1);
			}
			else {
				longest[i] = prev;
			}
		}
		
		for (int len = 3; len <= text.length; ++len) {
			int maxI = text.length - len;
			for (int i = 0; i <= maxI; ++i) {
				int j = i + len - 1;
				if (isPalin[i+1][j-1] && text[i] == text[j]) {
					if (longest[j] == null
							|| longest[j].end - longest[j].start + 1 <  len ) {
						prev = longest[j] = new Sub(i, j);
					}
				}
				else {
					longest[j] = prev;
				}
			}
		}
		
		if (longest[text.length - 1] != null) {
			Sub s = longest[text.length - 1];
			System.out.println("longest has length: " + (s.end - s.start + 1));
			for (int i = s.start; i <= s.end; ++i) {
				System.out.print(text[i]);
			}
		}
		else {
			System.out.println("Longest is 1 len");
		}
	}
	
	public static boolean canMakePalin(char[] text) {
		Set<Character> seen = new HashSet<>();
		for (char ch :text) {
			if (seen.contains(ch)) {
				// cancel it out
				seen.remove(ch);
			}
			else {
				seen.add(ch);
			}
		}
		
		return seen.size() <= 1;
	}
	
	//TODO: dont really understand this!!!
	public int maxProfit(int[] prices) {
		int sell1 = 0, sell2 = 0, buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
		for (int i = 0; i < prices.length; i++) {
			buy1 = Math.max(buy1, -prices[i]);
			sell1 = Math.max(sell1, buy1 + prices[i]);
			buy2 = Math.max(buy2, sell1 - prices[i]);
			sell2 = Math.max(sell2, buy2 + prices[i]);
		}
		return sell2;
	}
	
	public static class HuffmanNode {
		final char label;
		final int freq;
		final HuffmanNode left;
		final HuffmanNode right;
		
		HuffmanNode(char ch, int freq, HuffmanNode l, HuffmanNode r) {
			label = ch;
			this.freq = freq;
			left = l;
			right = r;
		}
	}
	
	public static Map<Character, Integer> buildDistribution(char[] text) {
		Map<Character, Integer> hist = new HashMap<>();
		for (char ch : text) {
			hist.put(ch, hist.getOrDefault(ch, 0) + 1);
		}
		
		return hist;
	}
	
	public static PriorityQueue<HuffmanNode> buildHeap(Map<Character, Integer> hist) {
		// use a queue to store all the nodes
		PriorityQueue<HuffmanNode> heap = new PriorityQueue<>(hist.size(),
				new Comparator<HuffmanNode>() {
					@Override
					public int compare(HuffmanNode o1, HuffmanNode o2) {
						return Integer.compare(o1.freq, o2.freq);
					}
				});

		
		for (Map.Entry<Character, Integer> entry : hist.entrySet()) {
			heap.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
		}
		return heap;
	}
	
	public static HuffmanNode buildHuffmanTree(PriorityQueue<HuffmanNode> heap) {

		// repeat until size == 1
		//    combine two smallest (ie less frquent char)
		//   the goal is to have most frequent at the top (with shortest encoding)
		while (heap.size() > 1) {
			HuffmanNode left = heap.poll();
			HuffmanNode right = heap.poll();
			HuffmanNode combined = new HuffmanNode((char)0, left.freq + right.freq, left, right);
			
			heap.add(combined);
		}
		
		return heap.poll();
	}
	
	public static  void printTree(HuffmanNode root) {
		printTree(root, new LinkedList<>());
	}
	static void printTree(HuffmanNode root, LinkedList<Integer> prefix) {
		if (root == null) return;
		if (root.left == null && root.right == null) {
			// if leaf
			System.out.println(root.label + " : " + prefix);
		}
		else {
			// 0 for left
			prefix.addLast(0);
			printTree(root.left, prefix);
			prefix.removeLast();
			
			// 1 for right
			prefix.addLast(1);
			printTree(root.right, prefix);
			prefix.removeLast();
		}
	}
	public static void printHuffmanCode(char[] text) {
		
		HuffmanNode root = buildHuffmanTree(buildHeap(buildDistribution(text)));
		printTree(root, new LinkedList<>()
		);
	}

	
	// traversals iter
	public static void dfsIter (WordNode root) {
		if (root == null) return;
		
		Map<WordNode, WordNode> visited = new IdentityHashMap<>();
		Map<WordNode, WordNode> inStack = new IdentityHashMap<>();
		
		Stack<WordNode> stack = new Stack<>();
		stack.push(root);
		inStack.put(root, root);
		
		while (!stack.isEmpty()) {
			WordNode cur = stack.pop();
			if (!visited.containsKey(cur)) {
				
				// if leaf or if all children have been visited
				if (cur.children.isEmpty() || childrenVisited(visited, cur)) {
					// visit
					System.out.println(cur.label);
					visited.put(cur, cur);
				}
				else {
					// put back
					stack.push(cur);
					inStack.put(cur, cur);
					for (WordNode kid : cur.children.values()) {
						if (!visited.containsKey(kid) && !inStack.containsKey(kid)) {
							stack.push(kid);
							inStack.put(kid, kid);
						}
					}
				}
				
			}
		}
	}
	
	public static void inOrderTreeIter(TreeNode root) {
		// left parent right
		TreeNode cur = root;
		Stack<TreeNode> stack = new Stack<>();
		
		while (cur != null || !stack.isEmpty()) {
			
			if (cur == null) {
				cur = stack.pop();
				// visit
				System.out.println(cur.label);
				cur = cur.right;
			}
			else {
				stack.push(cur);
				cur = cur.left;
			}
		}
		
	}
	static boolean childrenVisited(Map<WordNode, WordNode> visited, WordNode root) {
		for (WordNode kid : root.children.values()) {
			if (!visited.containsKey(kid)) return false;
		}
		return true;
	}
	

	
	static void wordTransform(String start, String end, Dict dict) {
		if (!dict.isValid(start) || !dict.isValid(end)) return;
		
		Map<String, WordNode> graph = new HashMap<>();
		buildGraph(start, graph, dict);
		
		if (!graph.containsKey(end)) return;
		
		// bfs
	}
	static List<String> shortestTransform (WordNode start, WordNode end, Map<String, WordNode> graph) {
		
		PriorityQueue<WordNode> q = new PriorityQueue<>(new Comparator<WordNode>() {

			@Override
			public int compare(WordNode o1, WordNode o2) {
				return o1.dist - o2.dist;
			}
		});
		
		// word --> predecessor
		Map<WordNode, WordNode> preds = new HashMap<>();
		start.dist = 0;
		q.add(start);
		
		while (!q.isEmpty()) {
			WordNode cur = q.poll();
			if (!cur.visited) {
				cur.visited = true;
				
				if (cur == end) {
					break;
				}
				
				// visit children
				// all chidlren's arcs have the same cost of 1
				int newDist = cur.dist + 1;
				
				for (WordNode kid : cur.children.values()) {
					if (kid.dist > newDist) {
						if (kid.inQueue) {
							// need to remove it to be reinserted 
							q.remove(kid);
						}
						
						kid.dist = newDist;
						kid.inQueue = true;
						q.add(kid);
						
						preds.put(kid, cur);
					}
				}
			}
		}
		if (!preds.containsKey(end)) {
			// didn't find the node
			return null;
		}
		
		LinkedList<String> path = new LinkedList<>();
		
		while (end != null) {
			path.addFirst(end.label);
			end = preds.get(end);
		}
		return path;
		
		
	}
	
	static WordNode buildGraph(String start, Map<String, WordNode> graph, Dict dict) {
		if (graph.containsKey(start)) return graph.get(start);
		
		WordNode startNode = new WordNode(start);
		graph.put(start, startNode);
		
		char[] text = start.toCharArray();
		// modifier each char
		for (int i = 0; i < text.length; ++i) {
			final char orig = text[i];
			for (char newChar : dict.getAlphabets()) {
				text[i] = newChar;
				String child = dict.buildWord(text);
				
				if (child != null) {
					startNode.children.put(child, buildGraph(child, graph, dict));
				}
			}
			text[i] = orig;
		}
		return startNode;
	}
	
	static final char DOT =  '.';
	static final char STAR = '*';
	public static boolean matchLexer(String s, String p) {
		return matchLexer(s.toCharArray(), 0, p.toCharArray(), 0);
	}
	public static boolean matchLexer(char[] s, int i, char[] p, int j) {
		if (i >= s.length || j >= p.length)  {
			return i >= s.length == j >= p.length;
		}

		if (p[j] == DOT) {
			return matchLexer(s, i+1, p, j+1);
		}
		else if (p[j] != STAR && j < p.length - 1 && p[j+1] == STAR) {
			// match 0 or more
			final char target = p[j];
			int k = i;
			while (k < s.length && s[k] == target) {
				if (matchLexer(s, k, p, j+2)) return true;
				++k;
			}
			return matchLexer(s, k, p, j+2);
		}
		else {
			// just literal matching
			return s[i] == p[j] && matchLexer(s, i+1, p, j+1);
		}
	}
}
