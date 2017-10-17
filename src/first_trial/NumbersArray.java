package first_trial;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * count the number of 1's in bin rep
 *
 */
public class NumbersArray {
	static final int FALSE = -1;
	static final int TRUE = 1;
	static final int UNKNOWN = 0;
	
	public static int findOddOcurring(int[] nums) {
		// xx yy z
		// xx xy  zz
		Set<Integer> set = new HashSet<>(nums.length);
		for (int n : nums) {
			set.add(n);
		}
		
		int ret = nums[0];
		for (int i = 1; i < nums.length; ++i) {
			ret ^= nums[i];
		}
		
		// xor with the set twice
		for (int i= 0; i < 2; ++i) {
			for (int n : set) {
				ret ^= n;
			}
		}
		
		return ret;
	}
	public static <T> T findEvenlyOccuring(T[] nums) {
		// find uniqueue set of elements
		Map<Integer, T> map = new HashMap<>(nums.length);
		for (T t : nums) {
			map.put(t.hashCode(), t);
		}
		
		int hash = nums[0].hashCode();
		for (int i = 1; i < nums.length; ++i) {
			hash ^= nums[i].hashCode();
		}
		
		for(T t : map.values()) {
			hash ^= t.hashCode();
		}
		
		return map.get(hash);
	}
	public static int findMissingElement(int[] nums1, int[] nums2) {
		// or both together, the missing will be the left
		int ret = nums1[0];
		for (int i = 1; i < nums1.length; ++i) {
			ret ^= nums1[i];
		}
		
		for (int i = 0; i < nums2.length; ++i) {
			ret ^= nums2[i];
		}
		return ret;
	}
	public static int findSmallestLargerThan(int[] digits) {
		// find the right most digit (d) that has a larger digit to its right
		// swap d with the smallest of the larger
		// sort the remaining in ascending
		int max = digits[digits.length - 1];
		int i = digits.length - 1;
		while (i >= 0) {
			if (digits[i] < max) {
				break;
			}
			if (digits[i] > max) {
				max = digits[i];
			}
			--i;
		}
		
		if (i < 0) {
			System.out.println("impossible");
			return -1;
		}
		
		// find the min amongst the max (priority is given to the left most copy)
		int iMin = -1;
		for (int j = i + 1; j < digits.length; ++j) {
			if (digits[j] > digits[i]) {
				if (iMin == -1 || digits[j] < digits[iMin]) {
					iMin = j;
				}
				
			}
		}
		
		// swap
		int t = digits[i];
		digits[i] = digits[iMin];
		digits[iMin] = t;
		
		// sort the remaining
		Arrays.sort(digits, i+1, digits.length);
		
		int ret = 0;
		for (int n : digits) {
			ret = ret * 10 + n;
		}
		
		return ret;
	}
	// find the immediate previous number
	public static int findLargestSmallerThan(int[] digits) {
		// - find the last digit (d) that has a smaller digit to its right
		//- swap d with the largest of those digits taht are < d
		// - sort the remaining in desc
		
		int min = digits[digits.length - 1];
//		int max = digits[digits.length - 1];
		int i = digits.length - 1;
		while (i >= 0) {
			if (digits[i] > min) {
				break;
			}
			if (digits[i] < min) {
				min = digits[i];
			}
//			else if (digits[i] > max) {
//				max = digits[i];
//			}
			--i;
		}
		
		if (i < 0) {
			System.out.println("not possible");
			return -1;
		}
		
		// find the max amongst the smaller
		int iMax = -1;
		
		for (int j = i + 1; j < digits.length; ++j) {
			// wants the left most, hence do not accept '=='
			if (digits[j] < digits[i]) {
				if (iMax == -1 || digits[j] > digits[iMax]) {
					iMax = j;
				}
			}
		}
		int t = digits[i];
		digits[i] = digits[iMax];
		digits[iMax] = t;
		
	
		Arrays.sort(digits, i+1, digits.length);
		int ret = 0;
		for (int j = 0; j <= i; ++j) {
			ret = ret * 10 + digits[j];
		}
		
		for (int j = digits.length - 1; j > i; --j) {
			ret = ret * 10 + digits[j];
		}
		return ret;
		//reverse(digits, i+1, digits.length - 1);
		// reverse
	}
	
	public static void reverse(int[] nums, int low, int high) {
		// 0 1 2 3 4 5
		int midOffset = (low + high) / 2 ;
		
		for (int i = low; i <= midOffset; ++i) {
			int j = high - i + low;
			int t = nums[i];
			nums[i] = nums[j];
			nums[j] = t;
			
		}
		
	}
	public static Set<Set<Integer>> powerset(Set<Integer> set) {
		Set<Set<Integer>> ret = new HashSet<>((int)Math.pow(2, set.size()));
		powerSet(set, ret);
		//add the empty set too
		ret.add(Collections.EMPTY_SET);
		return ret;
	}
	
	public static void powerSet(Set<Integer> root, Set<Set<Integer>> ret) {
		if (root.isEmpty()) {
			return;
		}
		else {
			ret.add(root);
			
			// power set of its children
			Iterator<Integer> iter = root.iterator();
			while (iter.hasNext()) {
				Set<Integer> child = new HashSet<>();
				child.addAll(root);
				Integer removed =  iter.next();
				child.remove(removed);
				
				powerset(child, ret);
			}
		}
	}
	
	public static int findKthLargest(int[] nums, final int k, int head, int tail) {
		// 0 1 2 3 4 (k  = 1)
		// i = len - k
		if (head == tail) return nums[head];
		
		final int i = nums.length - k;
		
		int pIdx = partition(nums, head, tail);
		if (pIdx == i) return nums[pIdx];
		else if (i < pIdx) return findKthLargest(nums, k, head, pIdx - 1);
		else return findKthLargest(nums, k, pIdx + 1, tail);
	}
	static int partition(int[] nums, int head, int tail) {
		int pivot = nums[(head + tail) /2];
		int i = head;
		int j = tail;
		while (i <= j) {
			while (nums[i] < pivot) ++i;
			while (nums[j] > pivot) --j;
			
			if (i <= j) {
				int t = nums[i];
				nums[i] = nums[j];
				nums[j] = t;
				
				++i;
				--j;
			}
			
		}
		
		return i;
	}
	public static void reverseWord(String st) {
		char[] text = st.toCharArray();
		// reverse the whole string, then each word
		reverse(text, 0, text.length -1);
		char SPACE = ' ';
		// reverse the word
		for (int i = 0; i < text.length;) {
			if (text[i] == SPACE) {
				++i;
				continue;
			}
			int j = i;
			for (j = i; j < text.length && text[j] != SPACE; ++j);
			
			reverse(text, i, j - 1);
			i = j + 1;
		}
		
		System.out.println(st);
		for (char ch : text) {
			System.out.print(ch);
		}
		System.out.println("\n---------");
	}
	
	static void reverse(char[] text, int head, int tail) {
		// 0 1  2 3 4 5 7
		// mid = 2
		int mid = (head + tail) / 2;
		
		for (int i = head; i <= mid; ++i) {
			int j = tail - i + head ;
			char t = text[i];
			text[i] = text[j];
			text[j] = t;
		}
	}
	public static double median(Stream<Integer> nums) {
		// first half, max queue
		final PriorityQueue<Integer> firstHalf = new PriorityQueue<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.intValue() - o1.intValue();
			}
		});
		
		// second half, min queue
		final PriorityQueue<Integer> secondHalf = new PriorityQueue<>();
		
		nums.forEach(new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				// even: , them med = avg(first.head and second.head)
				// 0 1 2 3
				
				// odd:  then it's just first.head
				// 0 1 2
				
				// first >= second
				
				// greater than the min of second half
				if (!secondHalf.isEmpty() && t >= secondHalf.peek()) {
					secondHalf.add(t);
					while (secondHalf.size() > firstHalf.size()) {
						firstHalf.add(secondHalf.poll());
					}
				}
				else {
					firstHalf.add(t);
					//while (!(secondHalf.size() == firstHalf.size() || secondHalf.size() == firstHalf.size() - 1)) {
					while (secondHalf.size() < firstHalf.size() - 1) {
						// if (secondHalf == firstHalf - 1)
						secondHalf.add(firstHalf.poll());
					}
				}
			}
		});
		
		if ((firstHalf.size() + secondHalf.size() ) % 2 == 0) {
			assert firstHalf.size() == secondHalf.size() : "unexpected state";
			return (firstHalf.poll() + secondHalf.poll()) / 2.0;
		}
		else {
			return firstHalf.poll();
		}
	}
	static class Pair {
		int x;
		int y;
		
		Pair (int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public boolean equals(Object o) {
			return ((Pair)o).x == x && y == ((Pair)o).y;
		}
		
		@Override
		public int hashCode() {
			int h = 17;
			h = h* 31 + x;
			h = h* 31 + y;
			return h;
		}
		
		@Override
		public String toString() {
			return "[" + x + ", " + y + "]";
		}
	}
	public static void countGroup(int[][] matrix) {
		class Island {
			int color;
			int size;
			Island(int c) {
				color = c;
				size = 1;
			}
		}
		Map<Integer, List<Island>> result = new HashMap<>();
		
		Set<Pair> unvisited = new HashSet<>();
		for (int x = 0; x < matrix.length; ++x) {
			for (int y = 0; y < matrix[x].length; ++y) {
				unvisited.add(new Pair(x,y));
			}
		}
		
		while (!unvisited.isEmpty()) {
			Pair start = unvisited.iterator().next();
			
			// do a search (bfs or dfs) for a blob of the same color
			LinkedList<Pair> unsettled = new LinkedList<>();
			Island island = new Island(matrix[start.x][start.y]);
			List<Island> islands = result.get(island.color);
			if (islands == null) {
				result.put(island.color, islands = new LinkedList<>());
			}
			islands.add(island);
			
			// start counting
			unsettled.addLast(start);
			while (!unsettled.isEmpty()) {
				Pair cell = unsettled.removeFirst();
				if (unvisited.contains(cell)) {
					// makr as visisted
					unvisited.remove(cell);
					
					// increase the island size
					++island.size;
					
					// visit children, if they are of the same color
					// top
					int color = matrix[cell.x][cell.y];
					visitChild(matrix, cell.x - 1, cell.y, unsettled, color);
					// bottom
					visitChild(matrix, cell.x + 1, cell.y, unsettled, color);
					// left
					visitChild(matrix, cell.x, cell.y - 1, unsettled, color);
					// right
					visitChild(matrix, cell.x, cell.y + 1, unsettled, color);
				}
			}
			
		}
	}
	
	static void visitChild(int[][] matrix, int x, int y, LinkedList<Pair> unsettled, final int color) {
		if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[x].length && matrix[x][y] == color) {
			unsettled.addLast(new Pair(x,y));
		}
	}
	
	
	// true if st can be constructed from  elements of array
	public static boolean combinationOf(String[] array, String st) {
		// if st > min(length(array[i])) return false
		// define a specifie trie
		
		// build a trie from array
		TrieWithLeaf trie = new TrieWithLeaf();
		for (String string : array) {
			trie.addWord(string);
		}
		
		return trie.lookup(st);
	}
	
	public static void findMaxConsecutiveSum(int[] nums) {
		int maxSum = nums[0];
		int maxHead = 0;
		int maxTail = 0;
		int curSum = maxSum;
		
		int head = 0;

		for (int i = 1; i < nums.length; ++i) {
			int newSum = nums[i] + curSum;
			if (newSum > nums[i]) {
				curSum = newSum;
			}
			else {
				head = i;
				curSum = nums[i];
			}
			
			if (curSum > maxSum) {
				maxHead = head;
				maxTail = i;
				maxSum = curSum;
			}

		}
		
		System.out.println("max = [" + nums[maxHead] + " --> " + nums[maxTail] + "] = " + maxSum);
	}
	
	// find consecutive block of numbers summing up to target
	public static void findConsecutive(int[] nums, int target) {
		for (int low = 0; low < nums.length; ++low) {
			// first individual can't be larger than the element itself
			// (array is already sorted)
			if (nums[low] > target) {
				System.out.println("not found");
				return ;
			}
			int high = low + 1;
			int sum = nums[low];
			while (high < nums.length)  {
				if (sum > target) {
					break;
				}
				else if (sum == target) {
					System.out.println("[" + low + " -> " + high + ")");
					return;
				}
				else {
					sum += nums[high];
					++high;
				}
			}
		}
		
		System.out.println("not found");
	}
	public static class Interval {
		int start;
		int end;
		public Interval(int l, int r) {
			start = l;
			end = r;
		}
		
		public String toString() {
			return "[" + start + ", " + end + "]";
		}
	}
	
	
	static class IntervalNode {
		Interval data;
		IntervalNode next;
		IntervalNode prev;
		IntervalNode(Interval data) {
			this.data = data;
		}
		
		public String toString() {
			return data.toString();
		}
	}
	
	public static void sum3(int[] nums, int target) {
		// num --> count
		Map<Integer, Integer> numToCount = new HashMap<>(nums.length);
		for (int num : nums) {
			numToCount.put(num, numToCount.getOrDefault(num, 0) + 1);
		}
		
//		for (int a : nums) {
//			for (int b : nums) {
//				if (a )
//			}
//		}
	}
	//                x,y 
	//               
	//
	static void print(Interval[] intervals) {
		for (Interval i : intervals) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	public static void merge(Interval[] intervals) {
		// sort first
		// then merge
		Arrays.sort(intervals,
				    new Comparator<Interval>(){
						@Override
						public int compare(Interval o1, Interval o2) {
							if (o1.start < o2.start) return -1;
							else if (o1.start > o2.start) return 1;
							else if (o1.end < o2.end) return -1;
							else if (o1.end > o2.end) return 1;
							else return 0;
						}});
		System.out.print("sorted: ");
		print(intervals);
		
		int cur = 0;
		int next = 1; 
		for (; next < intervals.length; ++next) {
			if (intervals[cur].end < intervals[next].start) {
				++cur;
				continue;
			}
			// merge
			Interval newInt = new Interval(intervals[cur].start, Math.max(intervals[next].end, intervals[cur].end));
			intervals[cur] = null;
			intervals[next] = newInt;
			cur = next;
			
		}
		System.out.print("merged: ");
		print(intervals);
	}
	public static void combine(Interval[] intervals) {
		
		int i = 0;
		IntervalNode root = new IntervalNode(intervals[i]);
		
		while (i < intervals.length) {
			root = insert(root, intervals[i++]);
		}
		
		while (root.prev != null) {
			root = root.prev;
		}
		
		int size = 0;
		while (root != null) {
			System.out.print(root + "   ");
			root = root.next;
			++size;
		}
		
	}
	
	
	static IntervalNode insert(IntervalNode root, Interval data) {
		if (data.start >= root.data.start && data.end <= root.data.end) {
			return root;
		}
		else if (data.start < root.data.start && data.end > root.data.end) {
			// contain everything
			// find the lowest
			IntervalNode cur = root;
			IntervalNode next = cur.next;
			while (data.start <= cur.data.start) {
				next = cur;
				cur = cur.prev;
				if (cur == null) break;
			}
			
			// find the highest
			cur = root;
			IntervalNode prev = cur.prev;
			while (data.end >= cur.data.end) {
				prev = cur;
				cur = cur.next;
				if (cur == null) break;
			}
			
			IntervalNode newNode = new IntervalNode(new Interval(data.start, data.end));
			newNode.next = prev.next;
			newNode.prev = next.prev;
			
			if (next.prev != null) {
				next.prev.next = newNode;
			}
			
			if (prev.next != null) {
				prev.next.prev = newNode;
			}
			return newNode;
		}
		else if (data.end < root.data.start) {
			if (root.prev == null) {
				root.prev = new IntervalNode(data);
				root.prev.next = root;
				return root;
			}
			else {
				if (data.start < root.prev.data.start) 
					return insert(root.prev, data);
				// otherwise insert it between here
				IntervalNode newNode = new IntervalNode(data);
				newNode.next = root;
				newNode.prev = root.prev;
				newNode.prev.next = newNode;
				root.prev = newNode;
				return newNode;
			}
		}
		else if (data.start > root.data.end) {
			if (root.next == null) {
				root.next = new IntervalNode(data);
				root.next.prev = root;
				
				return root;
			}
			else {
				if (data.start > root.next.data.start)
					return insert(root.next, data);
				// otherwise insert it between here
				IntervalNode newNode = new IntervalNode(data);
				newNode.prev = root;
				newNode.next = root.next;
				newNode.prev.next = newNode;
				newNode.next.prev = newNode;
				return newNode;
			}
		}
		else if (data.start <= root.data.start && data.end <= root.data.end) {
			IntervalNode cur = root;
			IntervalNode next = cur.next;
			while (data.start <= cur.data.start) {
				next = cur;
				cur = cur.prev;
				if (cur == null) break;
			}
			IntervalNode newNode = new IntervalNode(new Interval(data.start, root.data.end));
			if (root.next == null && root.prev == null) {
				return newNode;
			}
			
			newNode.next = root.next;
			newNode.prev = next.prev;
			
			if (newNode.next != null) {
				newNode.next.prev = newNode;
			}
			if (newNode.prev != null) {
				newNode.prev.next = newNode;
			}
			 
			
			return newNode;

		}
		else {
			//if (data.left <= root.data.right && data.right >= root.data.right)
			// data.left >= root.data.left  && data.left <= root.data.right
			// data.right > root.data.right
			
			
			IntervalNode cur = root;
			IntervalNode prev = root.prev;
			while (data.end >= cur.data.end) {
				prev = cur;
				cur = cur.next;
				if (cur == null) break;
			}
			
			IntervalNode newNode = new IntervalNode(new Interval(root.data.start, data.end));
			if (root.next == null && root.prev == null) {
				return newNode;
			}
			newNode.prev = root.prev;
			newNode.next = prev.next;
			
			if (newNode.prev != null) {
				newNode.prev.next = newNode;
			}
			if (newNode.next != null) {
				newNode.next.prev = newNode;
			}
			return newNode;
		}
	}
	

//	static void insert(IntervalNode root, Interval data) {
//		
//		// sort by left- right first
//		// then insert
//		// [x,y]
//		// inserting [a,b]
//		// [5, 8], [3,4] [3,6] 
//		//
//		// [a,b] to the left of [x,y]
//		
//		if (data.right < root.data.left) {
//			root.left = inserOrNew(root.left, data);
//		}
//		else if (data.left > root.data.right) {
//			root.right = inserOrNew(root.right, data);
//		}
//		else if (data.left <= root.data.left) {
//			if (data.right <= root.data.right) {
//				// replace the left only
//			}
//		}
//	}
//	
	static IntervalNode insertOrNew(IntervalNode root, IntervalNode node, Interval data) {
		
		if (node == null) {
			return new IntervalNode(data);
		}
		else {
			insert(node,data);
			return node;
		}
	}
	static int seenNonNull(int[] flag, String[] parts, int j) {
		if (flag[j] == UNKNOWN) {
			// compute
			int i = j;
			while (i >= 0 && flag[i] == UNKNOWN) {
				--i;
			}
			if (i < 0) ++i;
			
			boolean seenNonNull = flag[i] == TRUE;
			for (; i <= j; ++i) {
				flag[i] = seenNonNull ? TRUE : FALSE;
				seenNonNull |= parts[i] != null;
			}
		}
		
		return flag[j];
	}
	public static String simplify(String path) {
		String[] parts = path.trim().split("/");
		LinkedList<String> newPath = new LinkedList<>();
		
		
		for (int i = 0; i < parts.length; ++i) {
			if (!parts[i].isEmpty()) {
				if (parts[i].equals(".")) {
					// dont do anythign
				}
				else if (parts[i].equals("..")) {
					// remove the last, if there's one
					// make sure the thing before is not also a ".."
					if (newPath.size() > 0 && !newPath.getLast().equals("..")) {
						newPath.removeLast();
					}
					// other wise, ahve to go up
					else {
						newPath.addLast("..");
					}
				}
				else {
					// regular name
					newPath.addLast(parts[i]);
				}
			}
		}
		
		StringBuilder ret = new StringBuilder();
		for (String st : newPath) {
			ret.append("/").append(st);
		}
		
		return ret.toString();
	}
	static void print(String label, int[] num) {
		System.out.print(label);
		for (int n : num) {
			System.out.print(n + " ");
		}
		System.out.println();
	}

	
	
	public static int[] longAddition(int[] left, int[] right) {
		int ret[] = new int[Math.max(left.length, right.length) + 1];
		// 1234
		//   39
		int carry = 0;
		for (int i = left.length - 1, j = right.length - 1, k = ret.length - 1; i >= 0 || j >= 0 || carry > 0; --i, --j, --k) {
			int sum = carry + getNum(left, i) + getNum(right, j);
			ret[k] = sum % 10;
			carry = sum / 10;
		}
		
		return ret;
	}
	
	static int getNum(int[] arr, int i) {
		return i >= 0 ? arr[i] : 0; 
	}
	
	
	public static int[] findMinK(int[] nums, int k) {
		if (nums.length <= k) return nums;
		
		// build a min heap
		// then extract k times
		for (int i = nums.length / 2 - 1; i >= 0; --i){
			minHeapify(nums, nums.length, i);
		}
		
		int ret[] = new int[k];
		
		for (int i = nums.length - 1, j = 0; j < k; --i, ++j) {
			ret[i] = nums[0];
			nums[0] = nums[i];
			minHeapify(nums, i, 0);
		}
		
		return ret;
	}
	
	public static int[] findMaxK(int[] nums, int k) {
		if (k >= nums.length) return nums;
		if (k == 0) return null;
		
		// heapify then extract max k times
		for (int i = nums.length / 2 - 1; i >= 0; --i) {
			// put max at parent
			maxHeapify(nums, nums.length, i);
		}
		
		int[] ret = new int[k];
		for (int i = nums.length - 1, j = 0; j < k; ++j, --i) {
			// extract max int ret,
			ret[j] = nums[0];
			
			// swap last element to front to re-heapify
			nums[0] = nums[i];
			maxHeapify(nums, i, 0);
		}
		
		return ret;
	}
	
	static void maxHeapify(int[] nums, int n, int i) {
		int max = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < n && nums[l] > nums[max]) {
			max = l;
		}
		
		if (r < n && nums[r] > nums[max]){
			max = r;
		}
		
		if (max != i) {
			int t = nums[i];
			nums[i] = nums[max];
			nums[max] = t;
			
			maxHeapify(nums, n, max);
		}
	}
	
	// move min
	static void minHeapify(int num[], int n, int i) {
		int min = i;
		int l = 2 * i + 1;
		int r = l + 1;
		
		if (l < n && num[min] > num[l]) {
			min = l;
		}
		
		if (r < n && num[min] > num[r]) {
			min = r;
		}
		
		if (min != i) {
			int t = num[i];
			num[i] = num[min];
			num[min] = t;
			
			minHeapify(num, n, min);
		}
	}
	
	public static int maxProfitAtMostTwo(int[] prices) {
		//
		// m[i,j]: max profit at day i after j txn
		// 		 = m[i-1][j] OR 
		//		   prices[i] - prices[x] + m[x,j-1] (x < i)
		
		int k = 2;
		int[][] m = new int[prices.length][k +1];
		
		for (int j = 1; j <= k; ++j) {
			int local = m[0][j-1] - prices[0];
			for (int i = 1; i < prices.length; ++i) {
				m[i][j] = Math.max(m[i-1][j], prices[i] + local);
				local = Math.max(local, m[i][j - 1] - prices[i]);
			}
		}
		
		return m[prices.length - 1][k];
		
	}
	
	public static int maxProfitBuySell_AtMostK(int[] prices, int k) {
		
		//
		// m[i,j] = max profit at day i , with at most j
		//          = Max( m[i-1][j] ,
		//	             prices[i] - prices[x] + m[x, j -1] )
		// 				x : [0,i]
		if (k < 1 || prices.length < 2) return 0;
		
		if (k > prices.length / 2) {
			// go the normal route
			return maxProfitBuySell_Multiple(prices);
		}
		int[][] max = new int[prices.length][k + 1];
		
		for (int j = 1; j <= k; ++j) {
			int maxP = max[0][j-1] - prices[0];
			for (int i = 1; i < prices.length; ++i) {
				max[i][j] = Math.max(max[i-1][j], prices[i] + maxP);
				maxP = Math.max(maxP, max[i][j-1] - prices[i]);
			}
		}
		return max[prices.length - 1][k];
//		for (int i = 1; i < prices.length; ++i) {
//			for (int j = 1; j <= k; ++j) {
//				int maxP =  0;
//				for (int x = 1; x < i; ++x) {
//					if (max[x][j-1] - prices[x] > maxP) {
//						maxP = max[x][j-1];
//					}
//				}
//				max[i][j] = Math.max(max[i-1][j],
//						             prices[i] + maxP);
//			}
//		}
//		
//		return max[prices.length - 1][k];
	}
	
	public static int maxProfitBuySell_AtMostOneOpt(int[] prices) {
		int buy = 0;
		int maxP = 0;
		
		for (int price : prices) {
			if (price < buy) {
				buy = price;
			}
			else if (price - buy > maxP) {
				maxP = price - buy;
			}
		}
		
		return maxP;
	}
	
	public static int maxProfitBuySell_AtMostOne(int[] prices) {
		// find the min and the max to the right of it
		//find the max at 
		int[] min = new int[prices.length];
		int[] max = new int[prices.length];
		
		int m = 0;
		for (int i = 0; i < min.length; ++i) {
			if (prices[i] < prices[m]) {
				m = i;
			}
			
			min[i] = m;
			
		}
		
		m = min.length - 1;
		for (int i = min.length - 1; i >= 0; --i) {
			if (prices[i] > prices[m]) {
				m = i;
			}
			max[i] = m;
		}
		
		print("min", min);
		print("max", max);
		int iMax = 0;
		int iMin = 0;
		int maxP = -1;
		for (int i = 0; i < prices.length; ++i) {
			int p;
			if (min[i] < max[i] && (p = prices[max[i]] - prices[min[i]]) > maxP) {
				maxP = p;
				iMin = min[i];
				iMax = max[i];
			}
		}
		System.out.println("buy at[" + iMin + "] = " + prices[iMin] + ", sell at[" + iMax + "] = " + prices[iMax] );
		System.out.println("profit = " + maxP);
		return maxP;
	}
	
	public static int maxProfitBuySell_Multiple(int[] prices) {
		//{100, 50, 180, 260, 310, 40, 535, 695},
		// want
		
		// buy phrase: itrate until i such that p[i] < p[i-1] and p[i] < p[i+1]
		// tryBuy = cur
		// iterating until p[i] is top
		// then sell
		
		int iBuy = 0;
		int iSell = 0;
		int profit = 0;
		boolean buyingPhase = true;
		while (iBuy < prices.length - 1) {
			if (buyingPhase) {
				if (prices[iBuy + 1] <= prices[iBuy]) {
					++iBuy;
				}
				else {
					// it sell phase
					buyingPhase = false;
					iSell = iBuy;
				}
			}
			else {
				if (iSell < prices.length - 1 && prices[iSell] <= prices[iSell + 1]){
					++iSell;
				}
				else {
					// found the local maxima
					System.out.println("buy at  [" + iBuy + "] = " + prices[iBuy]);
					System.out.println("sell at [" + iSell + "] = " + prices[iSell]);
					System.out.println("profit = " + (prices[iSell] - prices[iBuy]));
					profit += prices[iSell] - prices[iBuy];
					
					iBuy = iSell;
					buyingPhase = true;
				}
			}
		}
		
		return profit;
	}
	
	public static Integer[][] findCommon(char[][] arrays) {
		Map<Character, Set<Integer>> commonCharToArray = new HashMap<>();
		
		// find all the arrays with at least one common char
		for (int i = 0; i < arrays.length; ++i) {
			for (char ch : arrays[i]) {
				Set<Integer> prev = commonCharToArray.get(ch);
				if (prev == null) {
					commonCharToArray.put(ch, prev = new HashSet<>(arrays.length));
				}
				prev.add(i);
				
			}
		}
		// create bit maps
		// different buckets
		Integer[][] ret = new Integer[commonCharToArray.size()][];
		int i = 0;
		for (Set<Integer> set : commonCharToArray.values()) {
			ret[i++] = set.toArray(new Integer[set.size()]);
		}
		return ret;
	}

	
	static void visitChild(int[][] map, int x, int y, LinkedList<Pair> queue) {
		if (x >= 0 && x < map.length && y >= 0 && y < map[x].length && map[x][y] == 1) {
			queue.addLast(new Pair(x, y));
		}
	}
	public static void findAreaOfIslands(int[][] map) {
		// find all isolated numbre islands (denoted by '1')
		// and their area
		
		// bfs
		Map<Integer, Integer> sizeToCount = new HashMap<>();
		
		// go search for the first '1'
		for (int x = 0; x < map.length; ++x) {
			for (int y = 0; y < map[0].length; ++y){
				
				if (map[x][y] == 1) {
					
					LinkedList<Pair> unsettled = new LinkedList<>();
					unsettled.add(new Pair(x,y));
					int area = 0;
					while (!unsettled.isEmpty()) {
						Pair cur = unsettled.removeFirst();
						
						// if not visited
						if (map[cur.x][cur.y] == 1) {
							++area;
							// mark as visited
							map[cur.x][cur.y] = -1;
							
							// add chidren
							// top
							visitChild(map, cur.x - 1, cur.y, unsettled);
							// top
							visitChild(map, cur.x + 1, cur.y, unsettled);
							// left
							visitChild(map, cur.x, cur.y - 1, unsettled);
							//right
							visitChild(map, cur.x, cur.y + 1, unsettled);
							
						}
					}
					
					// record the area
					sizeToCount.put(area, sizeToCount.getOrDefault(area, 0) + 1);
					
					
					// TODO: maybe move y to the right most position
				}
			}
		}
		
		// repor
		for (Map.Entry<Integer, Integer> entry : sizeToCount.entrySet()) {
			System.out.println(entry.getValue() + " island(s) of size " + entry.getKey());
		}
	}
	
	public static int weightedRandom(int[] pool) {
		// find the cummulative sum of the weighted
		// "cummulative sum" because imagine each number 'a'
		// occupies an interval of |a| on the number axis
		// and we want to put all the numbers on the same axis
		// so the actual values (the point) is not important
		// what is important is the length of the interval:
		
		//   |a|      |b|             |c|
		// 0-----x--------------y-----------z
		// |0x| = a
		// x = a
		// |xy| = b
		// y = a + b
		// ...
		
		int sum = 0;
		for (int n : pool) {
			sum += n;
		}
		
	
		double [] weighted = new double[pool.length];
		double acc = 0;
		for (int i = 0; i < weighted.length; ++i) {
			weighted[i] = pool[i] / sum + acc;
			acc += weighted[i];
		}
		
		double rand = Math.random();
		int idx = binSearch(weighted, rand);
		return pool[idx];
	}
	
	// find index of the smallest number > target
	// find the ceiling
	//  
	// [0, a) --> 0 (idx)
	// [a, b) --> 1 (idx
	// ...
	//
	public static int binSearch(double[] val, double target) {
		int head = 0;
		int tail = val.length - 1;

		while (head < tail) {
			int mid = (head + tail) / 2;
			if (val[mid] == target) {
				if (mid == val.length - 1) {
					return mid;
				}
				else {
					return mid + 1;
				}
			}
			else if (val[mid] > target) {
				tail = mid;
			}
			else {
				// skip mid because we want ceiling
				// (ie., the result has to be > target)
				head = mid +1;
			}
			
		}
		
		return head;
	}
	
	static boolean isBroken(int commit) {
		return true;
	}
	public static void powerset(Set<Integer> arg, Set<Set<Integer>> ret) {
		if (arg.isEmpty()) {
			return;
		}
		
		// a b c
		// a b c
		
		ret.add(arg);
		
		Integer[] work = arg.toArray(new Integer[arg.size()]);
		for (int i = 0; i < work.length; ++i) {
			Set<Integer> sub = new HashSet<>(arg.size() - 1);
			for(int j = 0; j < work.length; ++j){
				if (i == j) continue;
				sub.add(work[j]);
			}
			powerset(sub, ret);
		}
	}
	public static int findFirstBroken(int[] commits) {
		// sort the commits
		
		// binary search
		int l = 0;
		int r = commits.length - 1;
		
		while (l < r) {
			int m = (l+r)/2;
			if (isBroken(commits[m])){
				r = m - 1;
			}
			else {
				l = m + 1;
			}
		}
		
		return l;
	}
	public static void moveZeros(int[] num) {
		//
		int j = num.length - 1;
		for (int i = 0; i < j; ++i) {
			if (num[i] == 0) {
				num[i] = num[j];
				num[j] = 0;
				--j;
			}
		}
		
	}
	static void print(int[][] m) {
		for (int[] r : m) {
			for (int n : r)
				System.out.print(n + "  ");
			System.out.println();
		}
	}
	static void swap(int[][] m, int x1, int y1, int x2, int y2) {
		int t = m[x1][y1];
		m[x1][y1] = m[x2][y2];
		m[x2][y2]= t;
	}
	
	public static int maxCount(char[] stream, int k) {
		class Pair {
			Character ch;
			Integer count;
			Pair(Character ch, Integer c) {
				this.ch = ch;
				count = c;
			}
		}
		Map<Character, Integer> m = new HashMap<>();
		for (char ch : stream) {
			m.put(ch, m.getOrDefault(ch, 0) + 1);
		}
		
		PriorityQueue<Pair> q = new PriorityQueue<>(new Comparator<Pair>() {

			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.count - o2.count;
			}
		});
		
		for (Map.Entry<Character, Integer> entry : m.entrySet()) {
			if (q.size() < k) {
				q.add(new Pair(entry.getKey(), entry.getValue()));
			}
			else {
				Pair min = q.peek();
				if (min.count < entry.getValue()) {
					q.poll();
					q.add(new Pair(entry.getKey(), entry.getValue()));
				}
			}
		}
		
		return q.poll().count;
	}
	
	public static void maxOccur(char[] text) {
		char maxChar = text[0];
		int maxCount = 1;
		Map<Character, Integer> histogram = new HashMap<>(text.length);
		histogram.put(maxChar, maxCount);
		
		for(int i = 1; i < text.length; ++i) {
			Integer curCount = histogram.get(text[i]);
			if(curCount == null) {
				histogram.put(text[i], 1);
			}
			else {
				curCount = curCount + 1;
				histogram.put(text[i], curCount);
				if (curCount > maxCount) {
					maxCount = curCount;
					maxChar = text[i];
				}
			}
		}
	}
	public static List<Pair> findCombination(int[] nums, int target) {
		Map<Integer, Integer> count = new HashMap<>();
		for (int n : nums) {
			count.put(n, count.getOrDefault(n, 0) + 1);
		}
		List<Pair> ret = new LinkedList<>();
		// find the pair
		for (int n : nums) {
			Integer firstCount = count.get(n);
			if (firstCount > 0) {
				int other = target - n;
				Integer secondCount = count.get(other);
				if (secondCount != null && secondCount > 0) {
					count.put(n, firstCount - 1);
					count.put(other, secondCount -1);
					ret.add(new Pair(n, other));
				}
				
			}
		}
		
		return ret;
	}
	public static void rotateM(int[][] m) {
		// r, c0, c -->  r = r0 --> c0, c = c
		//
		int r0 = 0;
		int c0 = 0;
		int r = m.length - 1;
		int c = m.length - 1;
		int max = r;
		while(r0 < r && c0 < c) {
			int col = c0;
			for (; col < c; ++col) {
				//first with right most
				swap(m, r0, col, col, c);
				// first with bottom, but reverse
				swap(m, r0, col, r, max - col);
				
				// bottom: 16, 15, 14, 13
				swap(m, r0, col, max - col, c0);
			}
			
			++r0;
			++c0;
			--r;
			--c;
			
		}
	}
	public static int[][] rotate(int[][] m) {
		/*
		 * [][][][]      [][][][]
		 * [][][][]      [][][][]
		 * [][][][]      [][][][]
		 * [][][][]      [][][][]
		 */
		// 1, 4
		/*
		 *  {4, 2, 3, 1},
			{5, 6, 7, 8},
			{9, 10, 11, 12},
			{13, 14, 15, 16}
			
			{13, 2, 3, 1},
			{5, 6, 7, 8},
			{9, 10, 11, 12},
			{16, 14, 15, 4}
		 */
		// [r,c] --> [c, R - r]
		// r = x -R0
		// c = y -C0
		int R0 = 0;
		int C0 = 0;
		int R = m.length - 1;
		int C = m[0].length - 1;
		final int Max = R;
		while (R0 < R && C0 < C)
		{
			// swap, clock wise
			// first edge
			int r = R0;
			int c = C0;
			for (; c < C; ++c) 
			{
				swap(m, r,c, c, Max-r);
				swap(m, r, c, Max -r, Max - c);
				swap(m, r,c, Max - c, Max- (Max - r));
				
			}
			
			// reduce
			++R0;
			--R;
			++C0;
			--C;
		}
		
	
		return m;
	}
	
	public static int[][] rotateExtra(int[][] m) {
		/*
		 * [][][][]      [][][]
		 * [][][][]      [][][]
		 * [][][][]      [][][]
		 *               [][][]
		 */
		
		//[0,0] --> [0, n-1]
		//[0,1] 	[1, n-1]
		
		// [r,c] --> [c, R - r]
		
		int[][] rotated = new int[m[0].length][m.length];
		
		final int R = m.length - 1;
		final int C = m[0].length - 1;
		
		for (int r = 0; r <= R; ++r) {
			for (int c = 0; c <= C; ++c) {
				rotated[c][R  - r] = m[r][c];
			}
		}
		
		return rotated;
	}
	//https://blogs.msdn.microsoft.com/jeuge/2005/06/08/bit-fiddling-3/
//	public int countOneBinary(int num) {
//		// num >> 
//		int work = num;
//		while (work > 0) {
//			//work >> 1
//			// 100 >>1
//			//  10
//			// 
//		}
//	}
	
	public static void main(String[] args) {
		
		spell(1);
		spell(0);
		spell(1_030_341);
		spell(1_003_012);
		spell(1123_009_109);
		
	}
	static final String[] digits = new String[] {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
	static final String[] tens = new String[] {"", "", "", "thir", "four", "fif", "six", "seven", "eight", "nine"};
	static final String[] ty = new String[] {"","", "twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};
	static TreeMap<Integer, String> m = new TreeMap<Integer, String>();
	static {

		m.put(100, "hundred");
		m.put(1000, "thousdand");
		m.put(1000000, "million");
	}
	
	public static void spell(int num) {
		StringBuilder bd = new StringBuilder();
		spell(num, bd, m);
		System.out.println(num + ": " + bd);
	}
	static void spellTwoDigits(int num, StringBuilder ret) {
		// 0 --> 99
		assert num < 100;
		
		// <= 99
		int reduced = num % 100;
		if (reduced <= 12) {
			ret.append(digits[reduced]).append(" ");
		}
		else if (num < 20) {
			ret.append(tens[num % 10]).append("teen ");
		}
		else {
			// the usual route
			int first = reduced / 10;
			int second = reduced % 10;
			ret.append(ty[first]).append(" ");
			if (second > 0) {
				ret.append(digits[second]).append(" ");
			}

		}
	}

	public static void swapToEnd(int[] nums) {
		// 
	}
	
	public static class Domino {
		int left;
		int right;
		final int combined = left * 10 + right;
		public Domino(int l, int r) {
			left = l;
			right = r;
		}
		
	
	}
	
	public static void findSix(Domino[] dominos) {
		// xy
		// yx
		
		Map<Integer, Integer> map = new HashMap<>(dominos.length);
		for (Domino d : dominos) {
			map.put(d.combined, map.getOrDefault(d.combined, 0) + 1);
		}
		final int target = 66;
		for (Domino d : dominos) {
			int diff = target - d.combined;
			Integer leftCount = map.get(d.combined);
			
			if (leftCount > 0 ) {
				// sbutact one count
				map.put(d.combined, leftCount - 1);
				// recalcualte right count
				Integer rightCount = map.get(diff);
				if (rightCount != null && rightCount > 0) {
					// fond it
					System.out.println("pair: " + d.combined + " | " + diff);
					// update map
					map.put(diff, rightCount -1 );
				}
				
			}
		}
		
	}
	public static void spell(int num, StringBuilder ret, TreeMap<Integer, String> m) {	
		Map.Entry<Integer, String> floor =  m.floorEntry(num);
		
		if (floor == null){
			// <=
			spellTwoDigits(num, ret);
		}
		else {
			// reduce it
			Integer reduce = num / floor.getKey();
			
			spell(reduce, ret, m);
			ret.append(" ").append(floor.getValue()).append(", ");
			
			// the rest
			spell(num % floor.getKey(), ret, m);
		}
		
		
		
	}
	public static void sum2(int[] arr, int n) {
		// num --> count
		Map<Integer, Integer> set = new HashMap<>(arr.length);
		
		for (int num : arr) {
			Integer c = set.getOrDefault(num, 0);
			//if (c == 0)
			set.put(num, c+1);
		}
		
		for (int num : arr) {
			int d = n - num;
			Integer c = set.remove(d);
			if (c != null) {
				System.out.println(num + ", " + d);
				if (c > 1) {
					set.put(d, c - 1);
				}
			}
			c = set.remove(num);
			if (c != null && c > 1) {
				set.put(num, c -1);
			}
		}
		
		
	}
}
