package second_trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.plaf.multi.MultiSeparatorUI;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import first_trial.ExprLexer;
import first_trial.ExprParser;

public class NumsStrings {
	// nums is sorted
	public static int find(int[] nums, int target) {
		
		int low = 0;
		int high = nums.length -1;
		
		while (low < high) {
			int mid = (low+high)/2;
			if (nums[mid] == target) {
				// find the first?
				while (mid > 0 && (nums[mid-1] == nums[mid])) --mid;
				return mid;
			}
			else if (nums[mid] > target) {
				high = mid-1;
				
			}
			else {
				low = mid+1;
			}
		}
		
		if (low == high) {
			return nums[low] == target ? low : -1;
		}
		System.out.println("low: " + low);
		return -1;
		
	}
	public static int findWithMinCount (int[] nums, final int minCount) {
		// array is sorted (with dups)
		// find the first element with  count >= minCount
		
		int low = 0;
		int high = nums.length - 1;
		while (low < high) {
			int i = low + minCount - 1;
			if (nums[low] == nums[i]) return nums[low];
			
			
		}
		
		return -1;
	}
	
	// find the element that appears more than 50%
	public static int findMajority(int[] nums) {
		final int majorityCount = (int)Math.ceil(nums.length / 2.0);
		
		int low = 0;
		int high = nums.length - 1;
	
		int i = low + majorityCount;
		
		
		if (nums[low] == nums[i]) {
			return nums[low];
		}
		else {
			// if there is a majoirty,
			// it HAS to be nums[i]
			
			int j = (low + i) / 2;
			while (j < i && j > low) {
				if (nums[j] == nums[i]) {
					// somewhere before j, or j itself
					if (low+1== j) break;
					j = (low + j) / 2;
				}
				else {
					low = j;
					// somewhere after j
					if (i == j+1) {
						j = i;
						break;
					}
					j = (j+i) / 2;
				}
			}
			
			low  = j;
			i = low+majorityCount;
			 
			if (i > high) return -1;
			if (nums[low] == nums[i]) return nums[low];
		}
			
		
		return -1;
	}
	//pub
	static Random rand = new Random();
	static boolean one = false;
	static int randBit() {
		// 0 or 1
		if (one) {
			one = false;
			return 1;
		}
		else {
			one = true;
			return 0;
		}
	}
	public static int generateRandom(int max) {
		// max number of bits
		int i = (int)Math.floor(Math.log(max) / Math.log(2)) + 1;
		
		int ret = 0;
		int upperBound = 1;
		// i-->1 ==> ret's length  = i
		while (i-- > 0) {
			ret = ret << 1 | randBit();
			upperBound = upperBound << 1;
		}

//		System.out.println("ret: " + Integer.toBinaryString(ret));
//		System.out.println("mask: " + Integer.toBinaryString(upperBound - 1));
		
		// ret  = x...t ==> i bits of either 1 or 0
		// mask = 2^(i+1) ==> mask - 1 = 1...1 (i's bits of 1)
		// ==>       ret / (mask) = [0, 1) uniformly (alwasy --> 1
		// ==>  (max +1)* ret / (mask) = [0, max +1) uniformly
		
		//    taking the floor yieds max] (for ints)
		return (int)((max + 1) * ret / upperBound);
	}
	
	// full-heap (min)
	/**
	 *                   0001
	 *         0010 (2)        0011 (3)
	 *      
	 *   0100(4)   0101(5) .. 0110 (6)  0111(7)  
	 *   
	 * k = 5 (0101)                     
	 */
	public static class HeapNode{
		public HeapNode left;
		public HeapNode right;
		public HeapNode parent;
	}
	
	public static HeapNode findKthNode(HeapNode root, final int k) {
		int level = (int)(Math.log(k)/Math.log(2));
		List<Boolean> left = new ArrayList<>(level + 1);
		// reverse from tail to head
		int i = 0;
		while (i > 1) {
			if (i % 2 == 0) {
				// coming from the left
				left.add(true);
			}
			else {
				left.add(false);
			}
			i = i >> 1;
		}
		
		// now to find the node
		ListIterator<Boolean> leftIter = left.listIterator(left.size() - 1);
		HeapNode ret = root;
		while (leftIter.hasPrevious()) {
			// if left
			if (leftIter.previous()) {
				ret = ret.left;
			}
			else {
				ret = ret.right;
			}
		}
		
		return ret;
	}
	
    

	static final Map<String, Boolean> flippable = new HashMap<>();
	public static boolean isFlipNumber(String st) {
		if (flippable.containsKey(st)) {
			return flippable.get(st);
		}
		char[] digits = st.toCharArray();
		// if a number is strobogrammatic (0, 1, 6, 8 9)
		
		for (char ch : digits) {
			switch(ch) {
			case '0':
			case'1':
			case '6':
			case '8':
			case '9':
				// does nothing
				break;
			default:
				flippable.put(st, false);
				return false;
			}
		}
		
		return true;
	}
	
	static final char[] DIGITS = new char[] {'0', '1', '6', '8', '9'};
	
	static final List<Set<String>> cache = new ArrayList<>(10);
	static
	{
		// generate the first set of numbers (can't start with '0')
		Set<String> first = new HashSet<>();
		for (int i = 1; i < DIGITS.length; ++i) {
			first.add(Character.toString(DIGITS[i]));
		}
		cache.add(first);
	}
	
	public static void printCache() {
		System.out.println("---------");
		for (Set<String> set : cache) {
			System.out.println(set);
		}
		System.out.println("---------");
	}
	public static boolean isFlipNumberWithCache(String st) {
		int len = st.length();
		if (len > cache.size()) {
			generateFlip(len);
		}
		
		return cache.get(len - 1).contains(st);
	}
	
	public static void generateFlip(int newLen) {
		Set<String> prev = cache.get(cache.size() - 1);
		while (cache.size() < newLen) {
			Set<String> newRow = new HashSet<>(prev.size() * DIGITS.length);
			for (String num : prev) {
				for (char ch : DIGITS) {
					newRow.add(num + ch);
				}
			}
			prev = newRow;
			cache.add(newRow);
		}
	}

	public static void findAnagram(char[] text, char[]word) {
		Map<Character, Integer> hist = new HashMap<>(word.length);
		for (char ch : word) {
			hist.put(ch, hist.getOrDefault(ch, 0) + 1);
		}
		
		// sort of copied boyer-moore string search
		final int maxI = text.length - word.length;

		Outer:
		for (int i = 0; i <= maxI;) {
			int j = i + word.length - 1;
			Map<Character, Integer> copied = clone(hist);
			
			do {
				Integer c = copied.get(text[j]);
				if (c == null) {
					// doesn't match, so skip all this (pass it
					i = j + 1;
					continue Outer;
				}
				else if (c== 0) {
					// could match, but didn't have enougyh
					++i;
					// find the first i' such that t[i'] is in w
					while(i <= maxI && !hist.containsKey(text[i])) ++i;
					continue Outer;
				}
				else {
					// reduce the count (since we matches this)
					copied.put(text[j], c - 1);
					--j;

				}
			}
			while (j >= i);
			
			// if we are here, then it matches
			System.out.println("found matches at [" + i + " --> " + (i + word.length - 1) + "]");
			// continue?
			++i;
			//i = i + word.length;
			
		}
	}
	
	static Map<Character, Integer> clone(Map<Character, Integer> m) {
		Map<Character, Integer> ret = new HashMap<>();
		ret.putAll(m);
		return ret;
	}
	
	/**
	 * pick a random number in nums[] such that
	 * its propability is proprotional with its weight
	 * 
	 * @param nums
	 * @return
	 */
	public static int weightedRandom(int[] nums) {
		// sum up all elemtns in arr
		// 0[  a  |   b       |   c   |  d   ]x
		// [0, x)
		
		int[] accSum = new int[nums.length];
		accSum[0] = nums[0];
		
		for (int i = 1; i < nums.length; ++i) {
			accSum[i] = accSum[i-1] + nums[i];
		}
		
		// [0, x-1]
		int rand = intInRange(0, accSum[nums.length - 1] - 1);
		return nums[binSearchCeiling(accSum, rand)];
		
	}
	// find the ceiling
	// find the idx of the smallest that > target
	public static int binSearchCeiling(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;
		while (low < high) {
			int mid = (low+high) / 2;
			// 0   1   2   4
			// 3   7    9
			// [0, 3) --> 0
			// [3, 7) --> 1
			// [7, 9) --> 2
			// target = 6
			//
			if (target == arr[mid]) {
				// want the next (if there's one)
				if (mid == arr.length - 1) return mid;
				return mid + 1;
			}
			else if (target > arr[mid]) {
				low = mid + 1;
			}
			else {
				high = mid;
			}
		}
		
		return low;
	}
	public static int[] resevoirSampling(int[] pool, int k) {
		assert k <= pool.length;
		int ret[] = new int[k];
		for (int i = 0; i < k; ++i) {
			ret[i] = pool[i];
		}
		
		//Random rand = new Random();
		for (int i = k; i < pool.length; ++i) {
			int j = intInRange(0, i-1); // [0,i) 
			// j has prop k / i
			// [0 --> k) / [0, i)
			if (j < k) {
				ret[j] = pool[i];
			}
		}
		
		return ret;
	}
	
	public static double randInRange(int lower, int higher) {
		// [lower, higher)
		return Math.random() * (higher - lower) + lower;
	}
	
	public static int intInRange(int lower, int higher) {
		// [0, 1)
		// 0 <= x < 1
		// 0 <= x < higher - lower + 1
		// 0<= floor(x) < floor(high-lower + 1) == floor  
		return (int)(Math.floor(Math.random() * (higher - lower + 1)) + lower);
	}
	static class Pair {
		int a,b;
		Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}
	// nCk
	// n! / (n-k)!
	public static int nChooseK(int n, int k) {
		// n * (n-1) * ... (n - k + 1);
		int res = 1;
		int high = n;
		final int low = n - k + 1;
		while (high >= low) {
			res *= high;
			--high;
		}
		return res;
	}
	

	public static List<List<Integer>> sum2NoMap(int[] nums, final int target) {
		Arrays.sort(nums);
		// 11 222 33456
		List<List<Integer>> res = new LinkedList<>();
		int low = 0;
		int high = nums.length - 1;
		while(low < high) {
			if (nums[low] + nums[high] == target) {
				List<Integer> pair = new ArrayList<>(2);
				pair.add(nums[low]);
				pair.add(nums[high]);
				res.add(pair);
				
				// also skipping the dups
				while (low < high && nums[low] == nums[low+1]) ++low;
				while (low < high && nums[high - 1] == nums[high]) --high;
				
				++low;
				--high;
			}
			else if (nums[low] + nums[high] < target) {
				++low;
			}
			else {
				--high;
			}
		}
		return res;
	}
	public static List<List<Integer>> sum2Map(int[] nums, final int target) {
		List<List<Integer>> res = new LinkedList<>();
		// num --> idx
		Map<Integer, Integer> hist = new HashMap<>();
		for (int n : nums) {
			final int second = target - n;
			Integer c = hist.get(second);
			if (c != null){
				if (c != 0) {
					List<Integer> p = new ArrayList<>(2);
					p.add(n);
					p.add(second);
					res.add(p);
					
					// remove "second" because we've used it
					hist.put(second, 0);
				}
				// if ocut is 0, it means we've already used this element

			}
			// dont do dups (if hasn't seen this value before)
			else if (!hist.containsKey(n)) {
				hist.put(n, 1);
			}
		}
		return res;
	}
	
	public static List<List<Integer>> sum3Map(int[] nums, final int target) {
		final Map<Integer, Integer> EMPTY = new HashMap<>();
		List<List<Integer>> res = new LinkedList<>();
		Map<Integer, Map<Integer, Integer>> hist = new HashMap<>();
		
		for (int i = 0; i < nums.length; ++i) {
			final int first = nums[i];
			final int sum = target - first;
			Map<Integer, Integer> sub = hist.get(sum);
			if (sub != null) {
				// make sure we didn't used up this one
				if (sub != EMPTY) {
					// there are some pairs
					Integer second = sub.keySet().iterator().next();
					Integer third = sum - second;
					List<Integer> trip= new ArrayList<>(3);
					trip.add(first);
					trip.add(second);
					trip.add(third);
					res.add(trip);
					
//					if (sub.size() == 1) {
//						hist.put(sum, EMPTY);
//					}
//					else {
//						sub.put(second, 0);
//					}
				}
			}
			// dont recount if we've seen the same value before
			else if (!hist.containsKey(sum)) {
				hist.put(first, sub = new HashMap<>());
				
				// go compute all the possible sum
				for (int j = 0; j < i; ++j) {
					if (i == j) {
						continue;
					}
					
					final int second = nums[j];
					final int third = sum - second;
					Integer c = sub.get(third);
					if (c != null) {
						if (c != 0) {
							List<Integer> trip= new ArrayList<>(3);
							trip.add(first);
							trip.add(second);
							trip.add(third);
							res.add(trip);
							
							sub.put(third, 0);
						}
					}
					else if (!sub.containsKey(second)) {
						sub.put(second, 1);
					}
						
				}
				
				if (sub.isEmpty()) {
					hist.put(first, EMPTY);
				}
			}

		}
		
		return res;
	}
//	public static List<List<Integer>> sum3Map(int[] nums, final int target) {
//		List< List<Integer>> res = new LinkedList<>();
//		Map<Integer, Map<Integer, Integer>> hist = new HashMap<>();
//		for (int i = 0; i < nums.length - 2; ++i) {
//			
//			
//			
//			boolean added = false;
//			int sum = target - nums[i];
//			for (int j = i + 1; j < nums.length; ++j) {
//				Integer count = hist.get(sum - nums[j]);
//				if (count != null) {
//					if (count > 0) {
//						res.add(Arrays.asList(nums[i], nums[j], sum - nums[j]));
//						
//						// remove the pair
//						hist.put(sum - nums[j], 0);
//						hist.put(nums[j], 0);
//						added = true;
//					}
//				}
//				else if (!hist.containsKey(nums[j])) {
//					hist.put(nums[j], 1);
//				}
//			}
//			// set stuff to 0
//			
//			if (added) {
//				hist.put(nums[i], 0);
//			}
//			e
//		}
//		return res;
//	}
	
	public static void nSum(int[] nums, int target)  {
		
		subsetSum(nums, -1, 0, target, new boolean[nums.length]);
	}
	

	public static void subsetSum(int[] nums, int i,
			                     int curSum, int targetSum,
			                     boolean[] chosen) {
	
		
		if (curSum == targetSum && i >= 0) {
			// print
			
			for (int j = 0; j < nums.length; ++j) {
				if (chosen[j]) {
					System.out.print(nums[j] + ", ");
				}
			}
			System.out.println();
			
			
		}
		// 
		if (i >= nums.length) {
			return;
		}
		
		{
			if (i < 0) i=0;
			
			chosen[i] = true;
			curSum += nums[i];
			subsetSum(nums, i+1, curSum, targetSum, chosen);
			
			// revert
			curSum -= nums[i];
			chosen[i] = false;
			subsetSum(nums, i+1, curSum, targetSum, chosen);
		}
	}
	
	public static void powesetWithSum(int target, int[] num) {
		int max = (int)(Math.pow(2, num.length)) - 1;
		for (int c = 0; c < max; ++c) {
			int sum = 0;
			List<Integer> set = new LinkedList<>();
			for (int i = 0; i < num.length; ++i) {
				if ( (c & (1 << i)) != 0) {
					set.add(num[i]);
					sum += num[i];
				}
			}
			
			if (!set.isEmpty() && sum == target) {
				System.out.println("sum: " + set);
			}
		}
	}
	// a b c 
	public static void powerset(int...num){

		// i = 0 >
		int max = (int) (Math.pow(2, num.length ) );
		
		for (int c = 0; c < max; ++c){
			// if bit at pos i is ON, then include it
			
			for (int i = 0; i < num.length; ++i) {
				if( (c & (1 << i)) != 0) {
					System.out.print(num[i] + " ");
				}
			}
			System.out.println();
		}
	}
	
	public static void powersetRecursive(int[] num) {
		powersetRecursive(num, 0, new LinkedList<>());
	}
	public static void powersetRecursive(int[] pool, int i, List<Integer> prefix) {
		//
		// a b c
		// 
		if (prefix.size() == pool.length) return;
		if (i == pool.length) return;
		
		// include i, o r not
		LinkedList<Integer> child = new LinkedList<>();
		child.addAll(prefix);
		child.add(pool[i]);
		System.out.println(child);
		
		powersetRecursive(pool, i + 1, child);
		child = new LinkedList<>();
		child.addAll(prefix);
		System.out.println(child);
		powersetRecursive(pool, i + 1, child);
	}
	
//	public static List<List<Integer>> sumN(int[] nums, final int target) {
//		//Arrays.sort(nums);
//		// s[i,j] =  s[i+1, j]
//		// find all sub sets of nums (power set)
//		Set<Map<Integer, Integer>>[][] lookup = new Set[nums.length][nums.length];
//		int[][] sums = new int[nums.length][nums.length];
//		
//		for (int i = 0; i < nums.length; ++i) {
//			sums[i][i] = nums[i];
//			lookup[i][i] = new HashSet<>();
//			Map<Integer, Integer> map = new HashMap<>();
//			map.put(nums[i], 1);
//			lookup[i][i].add(map);
//			
//			if (i < nums.length - 1) {
//				sums[i][i+1] = nums[i] + nums[i+1];
//				lookup[i][i+1] = new HashSet<>();
//				map = new HashMap<>();
//				map.put(nums[i], 1);
//				map.put(nums[i+1], map.getOrDefault(nums[i+1], 0) + 1);
//			}
//		}
//		
//		for (int len = 3; len <= nums.length; ++len) {
//			// maxI + len - 1 = LENGTh - 1
//			for (int i = nums.length - len; i >= 0; --i) {
//				final int j = i + len - 1;
//				for (int k = i + 1; k < j; ++k) {
//					if (sums[i][k] + sums[k + 1][j] == target) {
//						
//					}
//				}
//			}
//		}
//	}
	
	
	// same vlaues are not counted even if they're from diff indices
	public static List<List<Integer>> sum3NoDups(int[] nums, final int target) {
		// sort first 
		Arrays.sort(nums);
		List<List<Integer>> res = new LinkedList<>();
		for (int i = 0; i < nums.length - 2; ++i) {
			final int sum = target - nums[i];
			int low = i + 1;
			int high = nums.length - 1;
			while (low < high) {
				if (nums[low] + nums[high] == sum) {
					List<Integer> triplet = new ArrayList<>(3);
					triplet.add(nums[i]);
					triplet.add(nums[low]);
					triplet.add(nums[high]);
					res.add(triplet);
					
					// skip the dups
					while (low < high && nums[low] == nums[low+1]) ++low;
					while (low < high && nums[high - 1] == nums[high]) --high;
					
					++low;
					--high;
				}
				else if (nums[low] + nums[high] > sum) {
					--high;
				}
				else {
					++low;
				}
			}
			// skip the next i if it's dup
			while (i < nums.length - 2 && nums[i+1] == nums[i]) ++i;
		}
		
		return res;
		
	}
	
	/**
	 * find all uniqueue combinations of elements summing up to a target
	 * (if two indices have the same values, they are counted as different.)
	 * @param nums
	 * @param target
	 * @return
	 */
	public static List<List<Integer>> sum3(int[] nums, final int target) {
		// sum --> (first --> second, second -->first)
		Map<Integer, Map<Integer, Set<Integer>>> map = new HashMap<>(nums.length);
		for (int i = 0; i < nums.length; ++i) {
			for (int j = i + 1; j < nums.length; ++j){
				int sum= nums[i] + nums[j];
				Map<Integer, Set<Integer>> pairs  = map.get(sum);
				if (pairs == null) {
					map.put(sum, pairs = new HashMap<>());
				}
				
				Set<Integer> seconds = pairs.get(i);
				if (seconds == null) {
					pairs.put(i, seconds = new HashSet<>());
				}
				
				seconds.add(j);
				
				seconds = pairs.get(j);
				if (seconds == null) {
					pairs.put(j, seconds = new HashSet<>());
				}
				seconds.add(i);

			}
		}
		List<List<Integer>> ret = new LinkedList<>();
		for (int i = 0; i < nums.length; ++i) {

			int diff = target - nums[i];
			Map<Integer, Set<Integer>> pairs = map.get(diff);
			// doesnt contain key OR  pairs contain keys OTHER than just i
			//                          (ie the number of pairs > the pairs that involves i
			if (pairs != null && !pairs.isEmpty() && (!pairs.containsKey(i) ||  pairs.size() > pairs.get(i).size() * 2)) {
				// remove the first entry, doesn't matter which
				// pick an entry which does NOT involve i
				Iterator<Integer> firstIter = pairs.keySet().iterator();
				Integer first = null;
				Integer second = null;
				
				while (first == null && second == null && firstIter.hasNext())
				{
					first = firstIter.next();
					if (first == i) {
						first = firstIter.next();
					}
					Set<Integer> seconds = pairs.get(first);
					Iterator<Integer> secondIter = seconds.iterator();
					// pick an entry NOT involving i
					second = secondIter.next();
					if (second == i) {
						if (secondIter.hasNext()) {
							second = secondIter.next();
						}
						else {
							second = null;
						}
					}
				}
				removePair(pairs, first, second);
				removePair(pairs, second, first);
				// (first, third)
				// (third, first)
				{
					pairs = map.get(nums[first] + nums[i]);
					removePair(pairs, first, i);
					removePair(pairs, i, first);
				}
				// (second, third)
				// (third, second)
				{
					pairs = map.get(nums[second] + nums[i]);
					removePair(pairs, second, i);
					removePair(pairs, i, second);
				}
				
				List<Integer> triplet = new ArrayList<>(3);
//				triplet.add(nums[first]);
//				triplet.add(nums[second]);
//				triplet.add(nums[i]);
				triplet.add(first);
				triplet.add(second);
				triplet.add(i);
				ret.add(triplet);
			}
		}
		
		return ret;
	}
	
	static void removePair(Map<Integer, Set<Integer>> map, Integer first, Integer second) {
		Set<Integer> seconds = map.get(first);
		if (seconds != null) {
			seconds.remove(second);
			if(seconds.isEmpty()) {
				map.remove(first);
			}
		}
		
	}

	
	public static int findFloor(int[] nums, int target) {
		// return index of nums[i] <= target
		if (nums[0] > target) return -1;
		int low = 0;
		int high = nums.length - 1;
		while (low < high) {
			int mid = (low + high) / 2;
			if (nums[mid] > target) {
				high = mid - 1;
			}
			else if (nums[mid] < target) {
				low = low + 1;
			}
			else {
				// move left t the first occurrences
				low = mid;
				
				break;
			}
		}
		int min = Math.min(low, high);
		if (min < 0) return  -1;
		// move left until the first occurrences
		int retVal = nums[min];
		while (min > 0 && nums[min-1] == retVal) {
			--min;
		}
		return min;
		
	}
	
	public static List<int[]> sum3Prob(int[] nums, int target){
		Arrays.sort(nums);
		List<int[]> ret = new LinkedList<>();
		for (int i = 0; i < nums.length - 2; ++i) {
			final int sum = target - nums[i];
			int low = i+1;
			int high = nums.length - 1;
			while (low < high) {
				int curSum = nums[low] + nums[high];
				
				if (curSum == sum) {
					ret.add(new int[] {nums[i], nums[low], nums[high]});
					// skip over dups too
					while (low < nums.length -1 && nums[low+1] == nums[low]) ++low;
					while (high > 0 && nums[high-1] == nums[high]) --high;
					
					++low;
					--high;
				}
				else if (curSum < sum) {
					++low;
				}
				else {
					--high;
				}
			}
			
			// skip dups i too
			while (i < nums.length - 2 && nums[i+1] == nums[i]) ++i;
		}
		
		return ret;
		
	}
	
	public static void evalPrefix(String st) {
		CharStream c = new ANTLRStringStream(st);
		System.out.print("evaluating [" + st + "]: ... ");
		PrefixExprLexer lexer = new PrefixExprLexer(c);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PrefixExprParser parser = new PrefixExprParser(tokens);
		
		try {
			parser.evaluate();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}
}
