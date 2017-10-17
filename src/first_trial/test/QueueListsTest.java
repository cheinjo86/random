package first_trial.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import first_trial.QueueList;
import first_trial.QueueList.FlattnedList.LiteralNode;
import first_trial.QueueList.FlattnedList.Node;
import first_trial.QueueList.FlattnedList;
import first_trial.QueueList.ListNode;
import first_trial.QueueList.SortedList;

public class QueueListsTest {

	@Test
	public void testFlatten() {
		LiteralNode one = new LiteralNode(1);
		LiteralNode two = new LiteralNode(2);
		LiteralNode three = new LiteralNode(3);
		
		one.next = two;
		two.next = three;
		Node onetwothree = new Node();
		onetwothree.data = one;
		
		Node root = new LiteralNode(0);
		root.next = onetwothree;
		
//		System.out.println("original: " + root);
//		System.out.println("Flattedn: " + FlattnedList.flattenList(root));
		
	//	if (true) return;
		
		//[0, [1,2,3],  [ [ [4,5], 6] ], [ [[7]], [8] ]
		
		LiteralNode four = new LiteralNode(4);
		LiteralNode five = new LiteralNode(5);
		four.next = five;
		
		Node fourFive = new Node();
		fourFive.data = four;
		fourFive.next = new LiteralNode(6);
		
		Node fourFiveSix = new Node();
		fourFiveSix.data = fourFive;
		Node fourFiveSixWrap = new Node();
		fourFiveSixWrap.data = fourFiveSix;
		// fourfivesixWrap.next = ....
		
		
		LiteralNode seven = new LiteralNode(7);
		Node wrap1 = new Node();
		wrap1.data = seven;
		Node wrap2 = new Node();
		wrap2.data = wrap1;
		
		LiteralNode eight = new LiteralNode(8);
		Node eightWrap1 = new Node();
		eightWrap1.data = eight;
		
		wrap2.next = eightWrap1;
		
		Node sevenEight  = new Node();
		sevenEight.data = wrap2;
		
		fourFiveSixWrap.next = sevenEight;
		
	
		onetwothree.next = fourFiveSixWrap;
		
		//root = fourFiveSixWrap;
		System.out.println("original: " + root);
		System.out.print("dfs: ");
		FlattnedList.dfs(root);
		System.out.println();
		
		
		root = FlattnedList.buildDFS(null, root);
		
		//System.out.println("flattened: " + root);
		// this return the last node, hence have to find my way back to the head
		while (root.prev != null) {
			root = root.prev;
		}
		System.out.println("flattend since beginning: " + root);
		
	}
	@Test
	public void testCircular() {
		SortedList list = new SortedList();
		
		list.insert(1);
		list.printAt(1);
		
		list.insert(3);
		list.printAt(3);
		
		list.insert(4);
		list.insert(2);
		list.insert(8);
		list.insert(5);
		list.insert(7);
		list.insert(6);
		
		list.printAt(1);
		list.printAt(8);
		list.printAt(3);
		list.printAt(2);
	}
	static void print(String l, ListNode n) {
		System.out.print(l);
		while (n != null) {
			System.out.print(n + " ");
			n = n.next;
		}
		
		System.out.println();
	}
	
	@Test
	public void testReverseRecurse() {
		ListNode one = new ListNode(1);
		ListNode two = new ListNode(2);
		ListNode three = new ListNode(3);
		ListNode four = new ListNode(4);
		ListNode five = new ListNode(5);
		ListNode six = new ListNode(6);
		
		one.next = two;
		
		two.next = three;
		two.prev = one;
		
		three.next = four;
		three.prev = two;
		
		four.next = five;
		four.prev = three;
		
		five.next = six;
		five.prev = four;
		
		six.prev = five;
		
		print("original: ", one);
		
		ListNode newHead = QueueList.reverseRecursive(null, one);
		
		print("reversed: ", newHead);
		
	}
	
	
	@Test
	public void testReverse() {
		System.out.print("reversed null: " + QueueList.reverse(null));
		
		ListNode one = new ListNode(1);
		System.out.println("reverse ONE element: " + QueueList.reverse(one));
		
		ListNode two = new ListNode(2);
		ListNode three = new ListNode(3);
		ListNode four = new ListNode(4);
		ListNode five = new ListNode(5);
		ListNode six = new ListNode(6);
		
		one.next = two;
		
		two.next = three;
		two.prev = one;
		
		three.next = four;
		three.prev = two;
		
		four.next = five;
		four.prev = three;
		
		five.next = six;
		five.prev = four;
		
		six.prev = five;
		
		print("original: ", one);
		
		ListNode newHead = QueueList.reverse(one);
		
		print("reversed: ", newHead);
		
	}
	@Test
	public void testCombineList() throws InterruptedException {
		doTest(Arrays.asList(1, 3, 8, 10, 24),
			   Arrays.asList(3, 4, 7, 9, 10, 11),
			   Arrays.asList(-1, 2, 3),
			   Arrays.asList(-1, 2, 3));
	}
	
	public void doTest(List<Integer>...lists) throws InterruptedException {
		int[][] ar = new int[lists.length][];
		
		int i = 0;
		for (List<Integer> l : lists) {
			System.out.println(l);
			ar[i] = new int[l.size()];
			Iterator<Integer> iter = l.iterator();
			int j = 0;
			while (iter.hasNext()) {
				ar[i][j++] = iter.next(); 
			}
			++i;
		}
		System.out.println("\n------------");
		System.out.println("===> " + QueueList.combine(lists));
		System.out.println("\n------------");
		System.out.println("===> " + QueueList.combineSeq(ar));
	}
}
