package fourth_trial;

import org.junit.Test;

import fourth_trial.Compre.HuffmanNode;

import static fourth_trial.Compre.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
public class CompreTest {

	@Test
	public void testRemoveParen() {
		removeInvalidParen("a(bc (d)");
		removeInvalidParen("a(bc (d))) + (x (x)))");
	}
	
	@Test
	public void testFromToArray() {
		final TreeNode root = buildTree();
		confirmTree(root);
		
		TreeNode[] arr = toArray(root);
		TreeNode root2 = fromArray(arr);
		
		confirmTree(root2);
	}
	
	@Test
	public void testInOrder() {
		TreeNode root = buildTree();
		inOrder(root);
		System.out.println();
		inOrderIter(root);
		
	}
	
	@Test
	public void testPreOrder() {
		TreeNode root = buildTree();
		preOrder(root);
		System.out.println();
		preOrderIter(root);
		
	}
	
	@Test
	public void testPostOrder() {
		TreeNode root = buildTree();
		postOrder(root);
		System.out.println();
		postOrderIter(root);
		
	}
	
	@Test
	public void testIsBst() {
		TreeNode t = buildTree();
		System.out.println(isBst(t));
	}
	static void confirmTree(TreeNode root) {
		System.out.print("pre order: ");
		preOrder(root);
		System.out.println();
		System.out.print("in order: " );
		inOrder(root);
		System.out.println();
	}
	
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
	
	// --- graphs
	@Test
	public void testTransformWord() {
		Dict dict = new Dict() {
			Set<String> words = new HashSet<>();
			char[] alpha = new char[] {'a', 'b', 'c'};
			{
				String[] st = {"abc", "bca", "bbc", "cab", "bca", "bcc", "aac", "bac"};
				for (String s : st) {
					words.add(s);
				}
			}
			
			@Override
			public String buildWord(char[] ch) {
				String word = new String(ch);
				return words.contains(word) ? word : null;
			}

			@Override
			public char[] getAlphabets() {
				return alpha;
			}

			@Override
			public boolean isValid(String word) {
				return words.contains(word);
			}

			@Override
			public String buildWord(char[] ch, int i, int j) {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
		
		findTransform("abc", "bac", dict);
	}
	
	// ---- dynamic
	@Test
	public void testCountWords() {
		Dict dict = new Dict() {
			Set<String> words = new HashSet<>();
			char[] alpha = new char[26];
			{
				String[] st = {"apple", "apples", "and", "sand", "a", "an", "other", "another"};
				for (String s : st) {
					words.add(s);
				}
				
				for (int i = 0; i < alpha.length; ++i) {
					alpha[i] = (char)('a' + i);
				}
			}
			
			@Override
			public String buildWord(char[] ch) {
				String word = new String(ch);
				return words.contains(word) ? word : null;
			}

			@Override
			public char[] getAlphabets() {
				return alpha;
			}

			@Override
			public boolean isValid(String word) {
				return words.contains(word);
			}

			@Override
			public String buildWord(char[] ch, int i, int j) {
				StringBuilder ret = new StringBuilder();
				
				for (; i <=j; ++i) {
					ret.append(ch[i]);
				}
				String s = ret.toString();
				
				return words.contains(s) ? s : null;
			}
			
		};
		
	}
	// ---- nums
	@Test
	public void testFindMinRooms() {
		int[] start = {1035, 1040 ,1050 ,1500, 1605};
		int[] end =  {1040, 1042, 1055 ,1505 ,1610};
		System.out.println(findMinPlatform(start, end));
		//System.out.println(findMinRooms(create(start, end)));
	}
	
	static Meeting[] create(int[] start, int[] end) {
		Meeting[] ret = new Meeting[start.length];
		for (int i = 0; i < start.length; ++i) {
			ret[i] = new Meeting(start[i], end[i]);
		}
		return ret;
	}
	@Test
	public void testCountOne() {
		
		doCount(0b1011);
	}
	
	static void doCount(int n) {
		System.out.println("num: " + Integer.toString(n, 2));
		System.out.println("count: " + countOnes(n));
		System.out.println(countOnes2(n));
	}
	
	// sort s
	static void print(int...num) {
		for (int n : num) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	@Test
	public void testSortColors() {
		int[] num = new int[] { 1, 2, 0, 1, 0, 2};
		print(num);
		
		colorSort(num);
		print(num);
		
	}
	
	@Test
	public void testPowerset() {
		powerset(1,2,3);
	}
	
	@Test
	public void testSpellNumber() {
		System.out.println(spell(1234));
		System.out.println(spell(1001));
	}
	
	@Test
	public void testMergeSort() {
	
		testMergeSort(1, 5, 2, 6, 3, 9);
	}
	
	static void testMergeSort(int...num) {
		print(num);
		
		mergeSort(num);
		System.out.print("\nmerged: ");
		print(num);
	}
	
	@Test
	public void testSimplify() {
		simplify("/a/./b/../../c/");
	}
	
	@Test
	public void testReverse() {
		reverseWords("hello, world! how are you");
	}
	
	@Test
	public void testLongestPalin() {
		doTest("abcdceffeghgc");
	}
	
	static void doTest(String st) {
		findLongestPalindromeInAString(st.toCharArray());
	}
	
	@Test
	public void testReverseList() {
		ListNode head = buildList();
		ListNode head2 = buildList();
		printList(head);
		ListNode reversed = reverseIter(head);
		printList(reversed);
		printList(reverse(head2, null));
		
		
		
	}
	
	static ListNode buildList() {
		ListNode one = new ListNode(1);
		ListNode two = new ListNode(2);
		ListNode three = new ListNode(3);
		ListNode four = new ListNode(4);
		ListNode five = new ListNode(5);
		
		one.next = two;
		
		two.next = three;
		two.prev = one;
		
		three.next = four;
		three.prev = two;
		
		four.next = five;
		four.prev = three;
		
		five.prev = four;
		return one;
		
	}
	static void printList(ListNode head) {
		ListNode cur = head;
		while (cur != null) {
			System.out.print(cur + "  -->  ");
			cur = cur.next;
		}
		
		System.out.println();
	}
	
	@Test
	public void printAllPaths() {
		TreeNode root = buildTree();
		
		printAllPathToLeaves(root);
		
	}
	
	@Test
	public void testFlatten() {
		// [ a, [b, c, [d], e ],  [f, g]]
		
		LiteralNode a = new LiteralNode('a');
		LiteralNode b = new LiteralNode('b');
		LiteralNode c = new LiteralNode('c');
		LiteralNode d = new LiteralNode('d');
		LiteralNode e = new LiteralNode('e');
		LiteralNode f = new LiteralNode('f');
		LiteralNode g = new LiteralNode('g');
		
		NestedNode dd = new NestedNode();
		dd.data = d;
		
		b.next = c;
		c.prev = b;
		
		c.next = dd;
		dd.prev = c;
		
		dd.next = e;
		e.prev = dd;
		
		NestedNode bcde = new NestedNode();
		bcde.data = b;
		
		
		f.next = g;
		g.prev = f;
		
		NestedNode fg = new NestedNode();
		fg.data = f;
		
		a.next = bcde;
		bcde.prev = a;
		
		bcde.next = fg;
		fg.prev = bcde;
		
		System.out.println("unflattened: " + a);
		
		flatten(a);
		
		System.out.println("\n  flattened: " + a);
	}
	
	@Test
	public void testHuffman() {
		/*
		 *  a	        5
    	   b            9
    	   c           12
    	   d           13
    	   e           16
    	   f           45
    	   
    	   
    	   f : 0
			c : 100
			d : 101
			a : 1100
			b : 1101
			e : 111

		 */
		
		Map<Character, Integer> dist = new HashMap<>();
		dist.put('a', 5);
		dist.put('b', 9);
		dist.put('c', 12);
		dist.put('d', 13);
		dist.put('e', 16);
		dist.put('f', 45);
		
		 PriorityQueue<HuffmanNode> heap = buildHeap(dist);
		printTree(buildHuffmanTree(heap));
	}
	
	@Test
	public void testLexerMatch() {
		System.out.println(matchLexer("acd", ".c*a*d"));

	}
}
