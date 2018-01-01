package first_trial.test;

import org.junit.Test;

import first_trial.NumbersArray;

import static first_trial.NumbersArray.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NumbersArrayTest {
	static int[] array(int...n) {return n;}
	@Test
	public void testCombination() {
		combination(array(1,2,3,4,5), 3);
	}
	@Test
	public void testPermutation() {
		permutation("ABA");
	}
	@Test
	public void testFindNext() {
		System.out.println(findSmallestLargerThan(new int[] {4,3,2,2,1}));
	}
	@Test
	public void testFindMissing() {
		System.out.println(findMissingElement(new int[] {1, 4},
				                              new int[] {1, 3, 4}));
	}
	@Test
	public void testMaxSum() {
		findMaxConsecutiveSum(new int[] {1, 2, -4, 5, 3,-2});
	}
	static void testSwap(int low, int high, int...num){
		print("original: ", num);
		reverse(num, low, high);
		print("swapped: ", num);
	}
	
	static void doTestFloor(int...digit) {
		print("original num:", digit);
		System.out.println("floor: " + findLargestSmallerThan(digit));
	}
	@Test
	public void testFindFloor() {
		// find the largest number smaller than the given number
		// using the same digit
		doTestFloor(7,4,1,2,5);
		doTestFloor(7,4,1,2,2,2,6,7);
		doTestFloor(1,2,3,4);
	}
	@Test
	public void testSwap(){
		testSwap(2, 5, 0,1,2,3,4,5);
		testSwap(1, 5, 0,1,2,3,4,5);
		testSwap(0, 5, 0,1,2,3,4,5);
	}
	static void testPowerSet(int...num){
		Set<Integer> set = new HashSet<>();
		for (int i : num) {
			set.add(i);
		}
		
		System.out.println("power set: " +  powerset(set));
	}
	
	@Test
	public void powersetTest() {
		testPowerSet(1,2,3);
		testPowerSet(1, 2);
	}
	static void testMed(int...num) {
		LinkedList<Integer> iter = new LinkedList<>();
		for (int n : num) {
			System.out.print(n + " ");
			iter.add(n);
		}
		
		System.out.println();
		double actual =  NumbersArray.median(iter.stream());
		System.out.println("median: " + actual);
		Arrays.sort(num);
		double expected;
		if (num.length % 2 == 0) {
			// o 1 2 3 // 4 / 2 = 2
			expected = (num[num.length / 2 - 1] + num[num.length /2] ) / 2.0;
			System.out.println("real median: " +  expected);
		}
		else {
			expected = num[num.length / 2  ];
			
		}
		System.out.println("real median: " + expected);
		if (expected != actual) System.out.println("**** WRONG ***");
		System.out.println("-----------");
		
		
	}
	
	
	@Test
	public void testReverse() {
		reverseWord("hello world computer science degree");
		reverseWord("hello world, computer science, degree");
	}
	@Test
	public void testMedian() {
		testMed(1, 2, 6, 7, 8, 9, 20, 21,22);
		testMed(1, 2, 3);
		testMed(1, 2, 3, 4);
		testMed(4, 3, 2, 1);
		Random rand = new Random();
		testMed(rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),
				rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),
				rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),
				rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),
				rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),
				rand.nextInt(10));
		testMed(1,1,1,1,1,1,1,1,1,1,1,1,1);
		testMed(1,1,1,1,1,1,1,1,1,1,1,1,1,
				2,2,2,2,2,2,2,2,2,2,2,2,2);
	}
	
	@Test
	public void testConsect() {
		int[] in = new int[]{1, 2, 6, 7, 8, 9, 20, 21,22};
		findConsecutive(in, 0 );
	}
	
	
	public static void print(String label, int[] num) {
		System.out.print(label);
		for (int n : num) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	static void testAtMostK(int k, int...num ) {
		print("prices: ", num);
		System.out.println("k = " + k);
		
		System.out.println("Max profits: " + maxProfitBuySell_AtMostK(num, k));
	}
	
	@Test
	public void testMergeIntervals() {
		//interval(5, 8, 3, 4, 3, 6, 1,2);
		interval(4,5,2,4,4,6,3,4,0,0,1,1,3,5,2,2);
		
	}
	
	static void interval(int...nums) {
		List<Interval> ret = new LinkedList<>();
		
		for (int i = 0; i < nums.length - 1; i += 2) {
			ret.add(new Interval(nums[i], nums[i+1]));
		}
		
		System.out.println("original: " + ret);
		merge(ret.toArray(new Interval[ret.size()]));
	}
	
	@Test
	public void simplifyPath() {
		doTestSimply("/../a/../b");
		doTestSimply("/a/./b/../../c/");
		doTestSimply("/.././.././../././..");
		// 
	}
	
	static void doTestSimply(String path) {
		System.out.println(path + "  ==> " + simplify(path));
	}
	@Test
	public void testLongAddition() {
		doTestAddition(93792, 30145);
		doTestAddition(11111, 222222);
		doTestAddition(11111, 999999);
	}
	
	static void doTestAddition(int a, int b) {
		int[] left = toArray(a);
		int[] right = toArray(b);
		
		print(a + " : ", left);
		print(b + " : ", right);
		
		print(a + b + " : ", longAddition(left, right));
				
	}
	
	static int[] toArray(int num) {
		int[] arr = new int[(int)(Math.log(num) / Math.log(10)) + 1];
		
		for (int i =  arr.length - 1; i >= 0; --i) {
			arr[i] = num % 10;
			num = num / 10;
		}
		
		return arr;
	}
	
	@Test
	public void testBuySellMostK() {
		//testAtMostK(0, 1,3);
		//testAtMostK(2, 1, 2, 4);
		//2
		//[1,2,4,2,5,7,2,4,9,0]
		testAtMostK(2, 1,2,4,2,5,7,2,4,9,0);
	}
	
	@Test
	public void testStocksBuyingOnce() {
		maxProfitBuySell_AtMostOne(new int[] {100, 50, 180, 700, 310, 40, 535, 695});
		maxProfitBuySell_AtMostOne(new int[] {100, 50, 180, 260, 310, 40, 535, 695});
		
	}
	
	@Test
	public void testStocksBuying() {
		maxProfitBuySell_Multiple(new int[] {7, 6, 5, 3, 2});
	}
	
	@Test
	public void testIsoIsland() {
		int[][] m = new int[][]
        {
			{1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1}
		};
		
		findAreaOfIslands(m);
	}
	
/*
 * Random variable x      Index in the Cumulative Array      Value in Original Array
-----------------      -----------------------------      ----------------------
 0 <= x < 10                      0                            10
10 <= x < 70                      1                            60
70 <= x < 75                      2                             5
75 <= x < 100                     3                            25 
 */
	
	@Test
	public void testBinSearch() {
		double[] in = new double[] {10, 70, 75, 100};
		// [head,tail]
		// x = 10
		// mid = 70
		
		// 0.....10....70...75
		//    10     70   75
		
		
		// 10 15  25 30
		// target = 16
		// 
		// 
		doTestBin(new double[]{10, 15, 25, 30}, 16);
		doTestBin(in, 0);
		doTestBin(in, 10);
		doTestBin(in, 70);
		doTestBin(in, 75);
		doTestBin(in, 100);
		doTestBin(in, 9);
		doTestBin(in, 11);
		doTestBin(in, 72);
		doTestBin(in, 76);
	}
	
	static void doTestBin(double[] in, double target) {
		System.out.println("target: " + target + " | found at idx: " + binSearch(in, target));
	}
	
	static char[] array(String st ) {
		return st.toCharArray();
	}
	@Test
	public void testmaxKCount() {
		System.out.println(maxCount(array("aaabdbca"), 3));
	}
	@Test
	public void testSum2() {
		int x = 8;
		System.out.println((x ^ (x-1)));
		x = 9;
		System.out.println((x ^ (x-1)));
		x = 4;
		System.out.println((x ^ (x-1)));
		
		System.out.println("\n---");
		int[] arr = new int[] {2, 3,4,1,5,0,5,3,1};
		//sum2(arr, 4);
	}
	
	@Test
	public void testSum2Two() {
		int[] arr = new int[] {1,2,3};
		System.out.println(findCombination(arr, 5));
	}
	static void print(int[][] m) {
		for (int[] r : m) {
			for (int n : r)
				System.out.print(n + "  ");
			System.out.println();
		}
	}
	@Test
	public void testRotated() {
		int[][] m = new int[][]
	    {
			{1, 2, 3, 4},
			{5, 6, 7, 8},
			{9, 10, 11, 12},
			{13, 14, 15, 16}
		};
		
		print(rotate(m));
	}
	
	@Test
	public void testRotatedTWO() {
		int[][] m = new int[][]
	    {
			{1, 2, 3, 4},
			{5, 6, 7, 8},
			{9, 10, 11, 12},
			{13, 14, 15, 16}
		};
		
		rotateM(m);
		print(m);
	}
}

