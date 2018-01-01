package las_trial_all;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Consumer;
import java.util.stream.Stream;


public class Last {

	public static int findKSmallest(int[] left, int[] right, final int k) {
		// sort of like merge
		int l = 0;
		int r = 0;
		int i = 0;
		//  0
		// i = l+r + 1
		while (i< k - 1) {
			// step by the remaining element/2
			// 0 1 2 3 k-1
			int d =  (k - 1 - l - r) / 2;
			int newL = l + d - 1;
			int newR = r + d - 1;
			i = i + d;
			if (newL > left.length && (newR >= right.length || left[newL] < right[newL])) {
				l = newL + 1;
			}
			else {
				r = newR + 1;
			}
		}
		
		// the last element
		if (l < left.length && (r >= right.length || left[l] < right[r])){
			return left[l];
		}
		else {
			return right[r];
		}
	}
	
	public static int findKLargest(int[] left, int right[], final int k) {
		int l = 0;
		int r = 0;
		
		// index into the imaginary merged array
		int i = 0;
		while (i < k-1) {
			// remaining elements count split by half
			int d = (k - 1 - i + 1) / 2;
			int newL = l+d-1;
			int newR = r+d-1;
			
			// adjusted because we want from the tail back
			int adjustedL = left.length - 1 - newL;
			int adjustedR = right.length - 1 - newR;
			i += d;
			if (adjustedL >= 0 && (adjustedR < 0 || left[adjustedL] > right[adjustedR])){
				l = newL + 1;
			}
			else {
				r = newR + 1;
			}
		}
		
		
		int adjustedL = left.length - 1 - l;
		int adjustedR = right.length - 1 - r;
		
		if (adjustedL >= 0
				&& (adjustedR < 0 || left[adjustedL] > right[adjustedR])) {
			return left[adjustedL];
		}
		else {
			return right[adjustedR];
		}
	}
	
	public static int findKSmallest2D(final int[][] matrix, final int k) {
		class Cell implements Comparable<Cell> {
			final int x,y;
			Cell(int x, int y) {
				this.x = x;
				this.y = y;
			}
			@Override
			public int compareTo(Cell o) {
				return Integer.compare(getValue(), o.getValue());
			}
			
			int getValue() {
				return matrix[x][y];
			}
			
		}
		// use a queue to store the indices
		PriorityQueue<Cell> idx = new PriorityQueue<>();
		for (int x = 0; x < matrix.length; ++x) {
			idx.add(new Cell(x, 0));
		}
		
		int i = 0;
		while (i < k - 1) {
			Cell cur = idx.poll();
			// add it
			++i;
			
			// move to next, if there more in the row
			if (cur.y < matrix[cur.x].length - 1) {
				idx.add(new Cell(cur.x, cur.y + 1));
			}
		}
		
		return idx.poll().getValue();
		
	}
	
	// ----- dyn -----
	
	static interface Dict {
		public String buildWord(char[] text, int low, int high);
		public boolean containsWord(String word);
		char[] getAlphabet();
	}
	
	public static List<String> findAllInterpretationOpt(char[] text, Dict dict) {
		List<String>[][] meaning = new List[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			// find all the words that exists from i->j
			for (int j = i; j < text.length; ++j) {
				String word = dict.buildWord(text, i, j);
				if (word != null) {
					meaning[i][j] = new LinkedList<>();
					meaning[i][j].add(word);
				}
			}
		}
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < i; ++j) {
				if (meaning[0][j] != null && meaning[j+1][i] != null) {
					// if this didn't alreayd have a word
					if (meaning[0][i] != null) {
						meaning[0][i] = new LinkedList<>();
					}
					
					// combine left and right
					for (String left : meaning[0][j]) {
						for (String right : meaning[j+1][i]){
							meaning[0][i].add(left + " " + right);
						}
					}
				}
			}
		}
		
		return meaning[0][text.length - 1];
	}

	public static List<Integer> longestCommonSeq(int[] first, int[] second) {
		
		class CommonElement {
			final int val;
			CommonElement pred;
			CommonElement(int val ,CommonElement pred) {
				this.val = val;
				this.pred = pred;
			}
		
			int getValue() {
				return val;
			}
		}
		
		int[][] seqLen = new int[first.length + 1][second.length + 1];
		CommonElement[][] preds = new CommonElement[first.length + 1][second.length + 1];
		
		for (int i = 1; i <= first.length; ++i) {
			for (int j = 1; j <= second.length; ++j) {
				if (first[i-1] == second[j-1]) {
					seqLen[i][j] = seqLen[i-1][j-1] + 1;
					preds[i][j] = new CommonElement(first[i-1], preds[i-1][j-1]);
				}
				else {
					if (seqLen[i][j-1] >= seqLen[i-1][j]) {
						seqLen[i][j] = seqLen[i][j-1];
						preds[i][j] = preds[i][j];
					}
					else {
						seqLen[i][j] = seqLen[i-1][j];
						preds[i][j] = preds[i-1][j];
					}
				}
			}
		}
		
		LinkedList<Integer> ret = new LinkedList<>();
		CommonElement cur = preds[first.length][second.length];
		while (cur != null) {
			ret.addFirst(cur.getValue());
			cur = cur.pred;
		}
		
		return ret;
	}
	
	public static void cutPalindrome(char[] text) {
		boolean[][] isPalin = new boolean[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			isPalin[i][i] = true;
			if (i < text.length - 1 && text[i] == text[i+1]) {
				isPalin[i][i+1] = true;
			}
		}
		
		// find aplin of all sizes
		for (int len = 3; len <= text.length; ++len) {
			// 0 1 2 
			final int maxI = text.length - len;
			for (int i = 0; i <= maxI; ++i) {
				final int j = i + len - 1;
				isPalin[i][j] = isPalin[i+1][j-1] && text[i] == text[j];
			}
		}
		
		// find the cuts positions 
		List<Integer>[] nCuts = new List[text.length];
		for (int i = 0; i < text.length; ++i) {
			if (!isPalin[0][i]) {
				int minJ = -1;
				int minCuts = Integer.MAX_VALUE;
				
				for (int j = 0; j < i; ++j) {
					if (isPalin[j+1][i]
							// no cuts form 0 to j
							&& (nCuts[j] == null
							/// or cut count is smaller
							     || nCuts[j].size() + 1 < minCuts)) {
						minJ = j;
						minCuts = (nCuts[j] == null ? 0 : nCuts[j].size()) + 1;
					}
				}
				if (minJ == -1) throw new IllegalStateException();
				
				nCuts[i] = new ArrayList<>(minCuts);
				if (nCuts[minJ] != null) {
					nCuts[i].addAll(nCuts[minJ]);
				}
				nCuts[i].add(minJ);
			}
			
		}
	}
	
	public static int lis(int[] nums) {
		int[] tails = new int[nums.length];
		int t = 0;
		
		for (int num : nums) {
			// insert first item
			if (t == 0 || num < tails[0]) {
				// new seq
				tails[0] = num;
			}
			else if (num > tails[t-1]) {
				// insert as the longest tail-element
				tails[t++] = num;
			}
			else {
				int i = findCeiling(tails, 0, t-1, num);
				tails[i] = num;
			}
		}
		return t;
	}
	
	// smallest number > (NOT equal) key
	public static int findCeiling(int[] nums, int low, int high, int target) {
		while (low < high) {
			int m = (low+high)/2;
			if (nums[m] <= target) {
				low = m+1;
			}
			else {
				high = m;
			}
		}
		
		return high;
	}
	
	public static void findMaxPalindrome(char[] text) {
		// s[i,j] if [
		boolean[][] isPalin = new boolean[text.length][text.length];
		int head = 0;
		int tail = 0;
		int maxLen = 1;
		
		for (int i = 0; i < text.length; ++i) {
			isPalin[i][i] = true;
			if (i < text.length - 1 && text[i] == text[i+1]) {
				isPalin[i][i+1] = true;
				maxLen = 2;
				head=i;
				tail=i+1;
			}
		}
		

		for (int len = 3; len <= text.length; ++len) {
			int maxI = text.length - len;
			for (int i = 0; i <= maxI; ++i) {
				final int j = i + len - 1;
				isPalin[i][j] = isPalin[i+1][j-1] && text[i] == text[j];
				
				if (isPalin[i][j]) {
					if (len > maxLen) {
						maxLen = len;
						head = i;
						tail =j;
					}
				}
			}
		}
	}
	
	static class HuffmanNode implements Comparable<HuffmanNode>{
		final char ch;
		final int freq;
		
		HuffmanNode left;
		HuffmanNode right;
		
		HuffmanNode(char ch, int freq) {
			this.ch = ch;
			this.freq = freq;
		}

		@Override
		public int compareTo(HuffmanNode o) {
			return Integer.compare(freq, o.freq);
		}
		
	}
	public static void huffmanEncoding(char[] text) {
		// basic idea
		// find frequency of char
		// create a heap of n seperate huffmnanode (leaf node)
		// while list size > 1	
		//  pick the two smallest nodes (lwest freq)
		//  create a new combinedNode . left point to smaller, right poit to larger
		//  put it back to the queue
		Map<Character, Integer> hist = new HashMap<>();
		for (char ch : text) {
			hist.put(ch, hist.getOrDefault(ch, 0) + 1);
		}
		
		PriorityQueue<HuffmanNode> heap = new PriorityQueue<>(hist.size());
		for (Map.Entry<Character, Integer> entry : hist.entrySet()) {
			heap.add(new HuffmanNode(entry.getKey(), entry.getValue()));
		}
		
		while (heap.size() > 1) {
			HuffmanNode first = heap.poll();
			HuffmanNode second = heap.poll();
			// internal node's char is 0
			HuffmanNode combined = new HuffmanNode((char)0, first.freq + second.freq);
			combined.left = first;
			combined.right = second;
			heap.add(combined);
		}
		
		// this is the root
		
	}
	
	public static void huffmanEncoding(HuffmanNode[] ret) {
		// basic idea
		// find frequency of char
		// create a heap of n seperate huffmnanode (leaf node)
		// while list size > 1	
		//  pick the two smallest nodes (lwest freq)
		//  create a new combinedNode . left point to smaller, right poit to larger
		//  put it back to the queue
	
		
		PriorityQueue<HuffmanNode> heap = new PriorityQueue<>(ret.length);
		for (HuffmanNode n : ret) {
			heap.add(n);
		}
		
		while (heap.size() > 1) {
			HuffmanNode first = heap.poll();
			HuffmanNode second = heap.poll();
			// internal node's char is 0
			HuffmanNode combined = new HuffmanNode((char)0, first.freq + second.freq);
			combined.left = first;
			combined.right = second;
			heap.add(combined);
		}
		
		// this is the root
		printNode(heap.poll(), new LinkedList<Integer>());
	}
	
	static void printNode(HuffmanNode root, LinkedList<Integer> prefix) {
		if (root.left == null && root.right == null) {
			System.out.println(prefix);
		}
		else {
			// left
			prefix.addLast(0);
			printNode(root.left, prefix);
			prefix.removeLast();
			
			// right
			prefix.addLast(1);
			printNode(root.right, prefix);
			prefix.removeLast();
		}
	}
	
	public static void main(String[] args) {
		int[] ar = {1, 2 , 2, 3, 2};
		
		sum2(ar, 4);
		sum2NoMap(ar, 4);
	}
	
	public static double median(Stream<Integer> stream) {
		// first half, keep max heap (max value at front)
		final PriorityQueue<Integer> firstHalf = new PriorityQueue<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		
		// second half, min (min value at front)
		final PriorityQueue<Integer> secondHalf = new PriorityQueue<>();
		// first size >= second
		
		stream.forEach(new Consumer<Integer>(){

			// incr method
			@Override
			public void accept(Integer t) {
				if (!secondHalf.isEmpty() && t >= secondHalf.peek()) {
					secondHalf.add(t);
					while (secondHalf.size() > firstHalf.size()) {
						firstHalf.add(secondHalf.poll());
					}
				}
				else {
					firstHalf.add(t);
					while (secondHalf.size() < firstHalf.size() - 1) {
						secondHalf.add(firstHalf.poll());
					}
				}
			}
			
			public double emit() {
				if ((firstHalf.size() + secondHalf.size()) % 2 == 0) {
					return (firstHalf.peek() + secondHalf.peek()) / 2.0;
				}
				else {
					return firstHalf.poll();
				}
			}
			
		});
		
		return 0;
	}
	static void swap(char[] ar, int i, int j) {
		char ch = ar[i];
		ar[i] = ar[j];
		ar[j] = ch;
	}
	static void reverse(char[] arr, int low, int high) {
		int mid = (low+high) / 2;
		// 0 1 2 3 4
		if ((high - low + 1) % 2 == 1) {
			// if odd, skip the mid
			--mid;
		}
		
		for (int i = low; i <= mid; ++i) {
			swap(arr, i, high - (i - low));
		}
	}
	
	public static void reverseWords(char[] text) {
		System.out.println("original: " + new String(text));
		// reverse the entire array first
		reverse(text, 0, text.length - 1);
		System.out.println("all reverse: " + new String(text));
		
		// then reverse each word back
		int i = 0;
		while (i < text.length) {
			int j = i + 1;
			while (j < text.length && text[j] != ' ') ++j;
			// reverse the word (not incuding the white space
		    reverse(text, i, j-1);
			
			// skip the space
			i = j + 1;
		}
		
		System.out.println("reversed: " + new String(text));
	}
	
	public static void powerset(int[] nums) {
		// xxx | 3 
		// 
		final int max = (1 << nums.length )- 1;
		System.out.println("max mask: " + Integer.toBinaryString(max));
		for (int i = 0; i <= max; ++i) {
			// if the bit is on, include it
			// xxx : len = 3
			//      
			System.out.print(i + ": ");
			for (int j = 0; j < nums.length; ++j) {
				if ( ((1 << j) & i) != 0) {
					System.out.print(nums[j] + " ");
				}
			}
			System.out.println();
		}
	}
	
	// numbers: sums, sum3, subset sum, weighted random resevoir sample
	public static void sum2(int[] nums, int target) {
		Map<Integer, Integer> hist = new HashMap<>();
		
		for (int n : nums) {
			int second = target - n;
			Integer count = hist.get(second);
			if (count == null || count == 0) {
				// dont do dups
				if (!hist.containsKey(n)) {
					hist.put(n, 1);
				}
			}
			else {
				hist.put(second, count - 1);
				// report
				System.out.println(n + ", " + second);
				
				// do NOT include 'n' again because it's been used
				hist.put(n, 0);
			}
		}
	}
	
	public static void sum2NoMap(int[] nums, int target) {
		// sort
		Arrays.sort(nums);
		
		int i = 0;
		int j = nums.length - 1;
		while (i < j) {
			int sum = nums[i] + nums[j];
			if (sum == target) {
				// skip the equal
				while (i < nums.length - 1 && nums[i+1] == nums[i]) ++i;
				while (j > 0 && nums[j-1] == nums[j]) --j;
				
				System.out.println(nums[i] + " " + nums[j]);
				++i;
				--j;
			}
			else if (sum > target) {
				--j;
			}
			else {
				++i;
			}
		}
	}
	
	public static void sum3NoMap(int[] nums, int target) {
		// nlogn
		Arrays.sort(nums);

		//n
		for (int i = 0; i < nums.length - 2; ++i) {
			int low = i + 1;
			int high = nums.length - 1;
			int sum = target = nums[i];
			// // n
			while (low < high) {
				int sum2 = nums[low] + nums[high] ;
				if (sum2 == sum) {
					// skip dups
					while (low < nums.length - 1 && nums[low+1]==nums[low]) ++low;
					while (high > 0 && nums[high-1] == nums[high]) --high;
					
					System.out.println(nums[i] + ", " + nums[low] + ", " + nums[high]);
					++low;
					--high;
				}
				else if (sum2 > sum) {
					--high;
				}
				else {
					++low;
				}
			}
			
			// skip the dups of i too
			while (i < nums.length -2 && nums[i+1] == nums[i]) ++i;
		}
	}
	
	public static int[] resevoirSampling(int[] nums, int k) {
		// another way:
		// gen permutations of nusm, pick a random one, then pick first k
		// int rand(0, n!)
		// each bit
		
		///
		int[] ret = new int[k];
		for (int i = 0; i < k; ++i) {
			ret[i] = nums[i];
		}
		
		for (int i = k; i < nums.length; ++i) {
			int idx = randIntInRange(0, i-1);
			// [0 -- i-1] == k / i probablitlyt
			if (idx < k) {
				ret[idx] = nums[i];
			}
		}
		
		return ret;
	}
	//inclusive
	public static int randIntInRange(int low, int high) {
		// [0, 1)
		return (int)(Math.floor(Math.random() * (high - low + 1 ))) + low;
	}
	
	// --- quick sort
	static void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static void quicksort(int[] nums, int low, int high) {
		if (low < high) {
			int pIdx = partition(nums, low, high);
			quicksort(nums, low, pIdx - 1);
			quicksort(nums, pIdx,high);
		}
	}

	public static int partition(int[] nums, int low, int high) {
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
	
	// k is 0-based
	// return the kth smallest element (unsorted array)
	public static int orderStats(int[] nums, int k, int low, int high) {
		int i = partition(nums, low, high);
		if (i == k) return nums[k];
		
		if (i < k) {
			return orderStats(nums, k, i+1, high);
		}
		else {
			return orderStats(nums, k, low, i-1);
		}
	}
	
	public static void mergesort(int[] nums, int low, int high) {
		if (low < high) {
			int mid = (low+high)/2;
			mergesort(nums, low, mid);
			mergesort(nums, mid+1, high);
			
			// merge
			merge(nums, low, mid, high);
			
		}
	}
	public static void merge (int[] nums, int low, int mid, int high) {
		int[] temp = new int[high - low + 1];
		int i = 0;
		int l = low;
		int r = mid +1;
		
		while (i < temp.length) {
			if (l <= mid && (r > high || nums[l] < nums[r])) {
				temp[i++] = nums[l];
			}
			else {
				temp[i++] = nums[r];
			}
		}
		
		// copy it back
		for (i = 0; i < temp.length; ++i) {
			nums[low+i] = temp[i];
		}
	}
	
	
	static void buildHeap(int[] nums) {
		for (int i = nums.length / 2 - 1; i>= 0; --i) {
			minHeapify(nums, i, nums.length);
		}
		// this part downward is heap sort (max first
		for (int i = nums.length - 1; i >= 0; --i) {
			// extract min, move it to the last
			swap(nums, 0, i);
		
			minHeapify(nums, 0, i);
		}
	}
	public static void minHeapify(int nums[], int i, int n) {
		int min = i;
		int l = 2 * i + 1;
		int r = l + 1;
		
		if (l < n && nums[l] < nums[min]) {
			min = l;
		}
		
		if (r < n && nums[r] < nums[min]) {
			min = r;
		}
		
		if (min != i) {
			swap(nums, i, min);
			minHeapify(nums, min, n);
		}
	}
	
	// graph and tree traversal
	// iter
}
