package first_trial;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * TODO:
 * - implelent sorted circular linkedlist
 * - reverse s linked list
 * - combine k sorted lists (merge)
 * 
 *
 */
public class QueueList {

	public static class FlattnedList {
		
		public static class Node {
			public Node data;
			public Node next;
			public Node prev;
			
			public Node getNext() {
				return next;
			}
			
			public Node getData() {
				return data;
			}
			
			public boolean isLeaf() {
				return false;
			}
			
			@Override
			public String toString() {
				return "(" + data + ") --> " + next;
			}
		}
		
		public static class LiteralNode extends Node{
			private final int value;
			public LiteralNode(int v) {
				value = v;
			}
			
			@Override
			public boolean isLeaf() {
				return true;
			}
			
			public int getValue() {
				return value;
			}
			
			@Override
			public String toString() {
				return Integer.toString(value) + " --> " + next;
			}
		}
		public static void dfs(Node root) {
			if (root == null) return;
			
			if (root.isLeaf()) {
				System.out.print(((LiteralNode)root).value + " --> ");
			}
			else {
				dfs(root.data);
			}
			dfs(root.next);
		}
		
		public static Node buildDFS( Node parent, Node root) {
			if (root == null) return parent;
			
			
			if (root.isLeaf()) {
				if (parent == null) {
					parent = root;
				}
				else {
					parent.next = root;
					root.prev = parent;
					parent = parent.next;
				}
			}
			else {
				parent = buildDFS(parent, root.data);
			}
			return buildDFS(parent, root.next);
	
			
		}
		public static Node flattenList(Node root) {
//			if (root == null) return root;
//			Node next = flattenList(root.next);
//			if (root.isLeaf()) {
//				root.next = next;
//			}
//			else {
//				root = doFlatten(root.data, next);
//			}
//			return root;
			
			return doFlatten(root, null);
			
		}
		
		public static Node doFlatten(Node cur, Node next) {
			if (cur == null) return null;
			
			Node curNext = cur.next;
			if (curNext != null) {
				if (curNext.isLeaf()) {
					// go to the end and add next
					if (next != null) {
						Node n = curNext;
						while (n.next != null) {
							n = n.next;
						}
						n.next = next;
					}
					
				}
				else {
					curNext = doFlatten(curNext, next);
				}
			}
			else {
				curNext = next;
			}
			
			if (cur.isLeaf()) {
				cur.next = curNext;
				return cur;
			}
			else {
				return  doFlatten(cur.data, curNext);
				
			}
		}
	}
	public static class SortedList {
		static class Node {
			int data;
			Node next;
			
			@Override
			public String toString() {
				return data + "";
			}
			Node (int i) { data = i; }
		}
		
		private Node head = null;
		private Node tail = null;
		
		private int size = 0;
		
		public int size() {
			return size;
		}
		
		public void insert(int node) {
			if (head == null) {
				head  = tail = new Node(node);
			}
			else {
				final Node newNode = new Node(node);
				
				// easy cases
				if (node < head.data) {
					newNode.next = head;
					head = newNode;
					tail.next = head;
				}
				else if (node > tail.data) {
					tail.next = newNode;
					tail = newNode;
					tail.next = head;
				}
				else {
					Node cur = head;
					while (cur.next.data <= node) {
						cur = cur.next;
					}
					newNode.next = cur.next;
					cur.next = newNode;
				}
			}
			
			++size;
		}
		
		public void printAt(int n) {
			Node start = head;
			int count = 0;
			while(start != null && start.data != n) {
				++count;
				start = start.next;
				if (count >= size) break;
			}
			
			if (start == null || count > size) {
				System.out.println("NOT found");
				return;
			}
			
			count = size;
			while (count > 0) {
				System.out.print (start + ", ");
				start = start.next;
				--count;
			}
			System.out.println("[size = " + size + "]");
			
		}
	}
	public static class ListNode {
		public ListNode next;
		public ListNode prev;
		ListNode random;
		
		int data;
		public ListNode() {}
		public ListNode(int d) { data = d; }
		
		@Override
		public String toString() {
			return "[" + data + ", next: " + toRep(next) + ", prev: " + toRep(prev) + "]";
		}
		
		static String toRep(ListNode n) {
			return n == null ? "NULL" : n.data + "";
		}
	}
	
	public static ListNode reverseIterative(ListNode root) {
		// head -> next --> tail
		ListNode cur = root;
		ListNode prev = null;
		
		while (cur != null) {
			ListNode oldNext = cur.next;
			cur.next = prev;
			cur.prev = oldNext;
			prev = cur;
			cur = oldNext;
		}
		
		return prev;
	}
	
	public static ListNode reverseRecursive(ListNode prev, ListNode root) {
		if (root == null) return prev;
		
		ListNode oldNext = root.next;
		root.next = root.prev;
		root.prev = oldNext;
		return reverseRecursive(root, oldNext);
	}
	public static ListNode reverse(ListNode root) {
		// head --> next -- ... --> tail
		// 
		ListNode tail = root;
		ListNode prev = null;
		while (tail != null) {
			ListNode oldNext = tail.next;
			tail.next = prev;
			tail.prev = oldNext;
			prev = tail;
			tail = oldNext;
		}
		
		return prev;
		
	}
	public static List<Integer> combineSeq(int[][] lists) {
		int max = 0;
		for (int[] l : lists) {
			max += l.length;
		}
		
		List<Integer> ret = new LinkedList<>();
		int[] idx = new int[lists.length];
		while (ret.size() < max) {
			int minIdx = -1;
			for (int i = 0; i < idx.length; ++i) {
				if (idx[i] >= lists[i].length) {
					continue;
				}
				if (minIdx == -1) {
					minIdx = i;
				}
				else if (lists[i][idx[i]] < lists[minIdx][idx[minIdx]]) {
					minIdx = i;
				}
			}
			ret.add(lists[minIdx][idx[minIdx]]);
			++idx[minIdx];
		}
		
		return ret;
		
	}
	public static List<Integer> combine(List<Integer>[] lists) throws InterruptedException {
		// 0 1 2 3 4
		// 0   3   6
		// 0 null 6
		// 5 ==> 3
		// 6 ==> 3
		int remaining = lists.length;
		//  k* n/2 + k* n/4
		// k*n (
		while (remaining > 2) {
			List<Thread> threads = new LinkedList<>();
			for (int i = 0; i < remaining - 1; i += 2) {
				// multiple thread access is afe because they each
				// ahve their own area
				Runnable r = new DoMerge(i, i + 1, lists);
				Thread mergeThread = new Thread(r);
				threads.add(mergeThread);
				mergeThread.start();
			}
			// ahve to wait for them all to finish
			for (Thread t : threads) {
				t.join();
			}
			for (int j = 1, i = 2; i < remaining; i += 2, ++j) {
				lists[j] = lists[i];
				lists[i] = null;
			}
			remaining = (int) Math.ceil(remaining / 2.0);
		}
		
		return combine(lists[0], lists[1]);
	}
	
	static class DoMerge implements Runnable {
		final int l,r;
		final List<Integer>[] lists;
		List<Integer> ret;
		
		DoMerge(int l, int r, List<Integer>[] lists){
			this.l = l;
			this.r = r;
			this.lists = lists;
		}
		
		
		@Override
		public void run() {
			ret = combine(lists[l], lists[r]);
			lists[l] = ret;
			lists[r]= null;
		}
	}
	public static List<Integer> combine (List<Integer> left, List<Integer> right) {
		List<Integer> ret = new ArrayList<>(left.size() + right.size());
		Iterator<Integer> leftIter = left.iterator();
		Iterator<Integer> rightIter = right.iterator();
		Integer leftHead = leftIter.next();
		Integer rightHead = rightIter.next();
		while(leftIter.hasNext() && rightIter.hasNext()) {
			if (leftHead < rightHead) {
				ret.add(leftHead);
				leftHead = leftIter.next();
			}
			else if (rightHead < leftHead){
				ret.add(rightHead);
				rightHead = rightIter.next();
			}
			else {
				ret.add(leftHead);
				ret.add(rightHead);
				
				leftHead = leftIter.next();
				rightHead = rightIter.next();
			}
			
		}
		if (leftHead <= rightHead) {
			ret.add(leftHead);
			ret.add(rightHead);
		}
		else {
			ret.add(rightHead);
			ret.add(leftHead);
		}
		while (leftIter.hasNext()) {
			ret.add(leftIter.next());
		}
		
		while (rightIter.hasNext()) {
			ret.add(rightIter.next());
		}
		
		return ret;
	}
	public static ListNode deepCopy(ListNode root) {
		if (root == null) return null;
		
		// old --> new
		IdentityHashMap<ListNode, ListNode> lookup = new IdentityHashMap<>();
		
		ListNode newRoot = new ListNode();
		lookup.put(root, newRoot);
		newRoot.data = root.data;
		
		ListNode copied = newRoot;
		ListNode cur = root;
		
		while (cur != null) {
			
			if (cur.next != null) {
				ListNode copiedNext = lookup.get(cur.next);
				if (copiedNext == null) {
					copiedNext = new ListNode();
					copiedNext.data = cur.next.data;
					lookup.put(cur.next, copiedNext);
				}
				copied.next = copiedNext;
			}
			if (cur.random != null) {
				ListNode newRand = lookup.get(cur.random);
				if (newRand == null) {
					newRand = new ListNode();
					newRand.data = cur.next.data;
					lookup.put(cur.random, newRand);
				}
				
				copied.random = newRand;
			}
			
			cur = cur.next;
			copied = copied.next;
		}
		
		return newRoot;
		
	}
	public static class Queue {
		private int[] data;
		private int head;
		private int tail;
		public Queue(int initialCapacity) {
			data = new int[initialCapacity];
			head = tail = -1 ;
			
		}
		
		public int size(){
			// [h][][][][h][]
			// 0  1  2 3 4
			// 1 - 4 + 1= -2
			if (head < 0) return 0;
			
			if (head > tail) {
				return data.length - (head - tail - 1);
			}
			return tail - head + 1;
		}
		public void addFirst(int num) {
			if (size() < data.length) {
				// still have enough capacity
				if (head > 0) {
					data[--head] = num;
				}
				else {
					data[head = data.length - 1] = num;
				}
			}
			else {
				// need new array 
				int[] newData = new int[data.length * 2];
				newData[0] = num;
				int i = 1;
				for (int j = head; j != tail; ++j, ++i) {
					newData[i] = data[j];
					
				}
				data = newData;
				head = 0;
				tail = i;
			}
		}
		
		public Iterator<Integer> forwardIterator() {
			return new Iterator<Integer>() {
				int i = head;
				@Override
				public boolean hasNext() {
					return i != tail;
				}

				@Override
				public Integer next() {
					Integer ret = data[i++];
					
					// wrap around
					if (i == data.length) {
						i = 0;
					}
					return ret;
				}
			};
		}
		
		public void addLast(int num) {
			if (size() < data.length) {
				
			}
			else {
					
			}
		}
		
		public int removeFirst() {
			return data[head++];
		}
		
		public int removeLast() {
			return data[tail--];
		}
		
		
	}
}
