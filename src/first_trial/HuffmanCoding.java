package first_trial;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {

	public static void minHeapify(int[] ar, int n, int i) {
		int max = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < n && ar[max] < ar[l]){
			max = l;
		}
		
		if (r < n && ar[max] < ar[r]) {
			max = r;
		}
		
		if (max != i) {
			int temp = ar[i];
			ar[i] = ar[max];
			ar[max] = temp;
			
			minHeapify(ar, n, max);
		}
	}
	
	static void print(int[] ar) {
		for (int n : ar) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	
	public static void minHeapSort(int[] arr) {
		final int n = arr.length;
		for (int i = n / 2; i >= 0; --i){
			minHeapify(arr, n, i);
		}
		print(arr);
		
		// extract max and put it at the end
		for (int i = n - 1; i >= 0; --i) {
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			
			minHeapify(arr, i, 0);
					
					
		}
	}
	
	public static void maxHeapSort(int[] arr) {
		final int n = arr.length;
		for (int i = n / 2; i >= 0; --i){
			maxHeapify(arr, n, i);
		}
		print(arr);
		
		// extract min and put it at the end
		for (int i = n - 1; i >= 0; --i) {
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			
			maxHeapify(arr, i, 0);
					
					
		}
	}
	
	public static void maxHeapify(int[] ar, int n, int i) {
		int min = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < n && ar[min] > ar[l]){
			min = l;
		}
		
		if (r < n && ar[min] > ar[r]) {
			min = r;
		}
		
		if (min != i) {
			int temp = ar[i];
			ar[i] = ar[min];
			ar[min] = temp;
			
			maxHeapify(ar, n, min);
		}
	}
	
	static class HuffmanNode {
		char ch;
		int freq;
		HuffmanNode left;
		HuffmanNode right;
		
		
		public String toString() {
			return (ch == (char)0 ? "NULL" : ch) + " : " + freq;
		}
		HuffmanNode(char d, int freq) {
			this.ch = d;
			this.freq = freq;
		}
	}
	
	static HuffmanNode[] createNodes() {
		/*
		 * a	        5
    	   b            9
    	   c           12
    	   d           13
    	   e           16
    	   f           45
		 */
		
		HuffmanNode[] ret = new HuffmanNode[6];
		ret[0] = new HuffmanNode('a', 5);
		ret[1] = new HuffmanNode('b', 9);
		ret[2] = new HuffmanNode('c', 12);
		ret[3] = new HuffmanNode('d', 13);
		ret[4] = new HuffmanNode('e', 16);
		ret[5] = new HuffmanNode('f', 45);
		
		return ret;
		
	}
	
	public static void encodeUsingQueue(char[] text) {
		// build the histogram
//		Map<Character, HuffmanNode> nodes = new HashMap<>();
//		for (char ch : text) {
//			HuffmanNode node = nodes.get(ch);
//			if (node == null) {
//				nodes.put(ch, node = new HuffmanNode(ch, 0));
//			}
//			++node.freq;
//		}

		PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>(new Comparator<HuffmanNode>() {

			@Override
			public int compare(HuffmanNode o1, HuffmanNode o2) {
				return o1.freq - o2.freq;
			}
		});
		for (HuffmanNode node : createNodes()) {
			minHeap.add(node);
		}
		
		while (minHeap.size() > 1) {
			HuffmanNode first = minHeap.poll();
			HuffmanNode second = minHeap.poll();
			HuffmanNode combined = new HuffmanNode((char)0, first.freq + second.freq);
			combined.left = first;
			combined.right = second;
			minHeap.add(combined);
		}
		
		// print the tree
		printTree(minHeap.poll(), new StringBuilder());
	}
	
	public static void printTree(HuffmanNode root, StringBuilder prefix) {
		// if is leaf
		if (root.left == null && root.right == null) {
			System.out.println(root.ch + " : " + prefix);
			return;
		}
		
		printTree(root.left, new StringBuilder(prefix).append('0'));
		printTree(root.right, new StringBuilder(prefix).append('1'));
	}
	
	public static void encode(char[] text) {
		// build the histogram
//		Map<Character, HuffmanNode> nodes = new HashMap<>();
//		for (char ch : text) {
//			HuffmanNode node = nodes.get(ch);
//			if (node == null) {
//				nodes.put(ch, node = new HuffmanNode(ch,0));
//			}
//			++node.freq;
//		}
		
		//HuffmanNode[] heap = nodes.values().toArray(new HuffmanNode[nodes.size()]);
		HuffmanNode[] heap = createNodes();
		// heapify this
		int n = heap.length;
		for (int i = n / 2; i >= 0; --i){
			heapify(heap, n, i);
		}

		int i = n - 1;
		while (i > 0) {
			HuffmanNode first = heap[0];
			// extract two smallest
			heap[0] = heap[i];
			heap[i] = null;
			heapify(heap, i, 0);
			
			//extract again
			--i;
			HuffmanNode second = heap[0];
			heap[0] = heap[i];
			heap[i] = null;
			
			// combine
			HuffmanNode newNode = new HuffmanNode((char)0, first.freq + second.freq);
			newNode.left = first;
			newNode.right = second;
			
			// insert into heap
			heap[i] = newNode;
			
			// heapify to bring the smallest to front
			heapify(heap, i + 1, 0);
		}
		
		HuffmanNode root = heap[0];
		printTree(root, new StringBuilder());
	}
	
	static void swap(HuffmanNode[] heap, int a, int b) {
		HuffmanNode t = heap[a];
		heap[a] = heap[b];
		heap[b]= t;
	}
	// put min at top
	public static void heapify(HuffmanNode[] heap, int n, int i){
		int min =i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < n && heap[min].freq > heap[l].freq) {
			min = l;
		}
		
		if (r < n && heap[min].freq > heap[r].freq) {
			min = r;
		}
		
		if (min != i) {
			HuffmanNode temp = heap[i];
			heap[i] = heap[min];
			heap[min] = temp;
			
			heapify(heap, n, min);
		}
	}
}
