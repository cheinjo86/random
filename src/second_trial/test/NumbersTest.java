package second_trial.test;

import java.util.Arrays;

import org.junit.Test;

import second_trial.NumsStrings;
import static second_trial.NumsStrings.*;

public class NumbersTest {
	
	@Test
	public void testFindMajority() {
		find50(1, 1, 2, 2, 2, 3);
		find50(1, 2, 2, 2, 3, 3);
		find50(1, 2, 2, 2, 2, 3);
		find50(1, 3, 2, 2, 2, 2);
		find50(1, 1, 2, 2, 2, 2);
	}
	
	static void find50(int...nums){
		print("array: ", nums);
		int i = findMajority(nums);
		if (i < 0) System.out.println("there is NO majority");
		else System.out.println("majority: " + i);
	}
	
	@Test
	public void testFind() {
		doTestFind(1, 1, 1, 2, 2, 3,4, 4, 5);
	}
	
	static void doTestFind(int target, int...nums) {
		print("nums: ", nums);
		print("target: ", target);
		System.out.println("idx = " + find(nums, target));
		
		
	}
	@Test
	public void testRandNum() {
		generateRandom(13);
	}
	
	static void print(String label, int...num){
		System.out.print(label);
		for (int n : num) System.out.print(n);
		System.out.println();
	}
	
	@Test
	public void testEvaluate() {
		//(+ 1 12 (- 17 3) 5 (* 2 8 (/ 120 4)) 46)
		//evalPrefix("(* 1 12 5 (+ 2 3) )");
		evalPrefix("(+ 1 12 (- 17 3) 5 (* 2 8 (/ 120 4)) 46)");
	}
	
	@Test
	public void testCeilignSearch() {
		testCeiling(6, 3, 7);
	}
	
	static void testCeiling(int target, int...num){
		print("array: ", num);
		print("target: ", target);
		System.out.println("ceiling: " + binSearchCeiling(num, target));
	}
	@Test
	public void testFlip() {
		
		printCache();
		generateFlip(2);
		printCache();
	}
	
	@Test
	public void testFindAnagram(){
		//           012345678
		testAnagram("aabcabaac", "abac");
		//testAnagram("bcab", "abac");
	}
	
	static void testAnagram(String t, String w) {
		
		findAnagram(t.toCharArray(), w.toCharArray());
	}
	@Test
	public void testSubsetSum() {
		subsetSum(2, 1,2,3,0, -2,4, -6);
	}
	
	static void subsetSum(int target, int...num) {
		NumsStrings.nSum(num, target);
		System.out.println("-----");
		NumsStrings.powesetWithSum(target, num);
	}
	@Test
	public void testShift() {
		testShift(2);
		testShift(3);
		testShift(4);
	}
	static void testShift(int n) {
		System.out.println(Math.pow(2, n) + " : " + (1 << n));
	}
	@Test
	public void testPowerSet() {
		testPower(1,2,3);
	}
	static void testPower(int...num){
		System.out.print("oiginal set: ");
		print(num);
		
		powerset(num);
		System.out.println("------");
		powersetRecursive(num);
		System.out.println("****************************");
	}
	@Test
	public void testSum2() {
//		testSum2(7, 0, 4, 1,2, 5, 2, 3, 6, 3, 4, 5);
//		testSum2(6, 1,2,3);
//		testSum2(0, -1,0,1,-1);
		testSum2(4,2,3, 2);
	}
	
	static void testSum2(int target,int...nums){
		print(nums);
		System.out.println(NumsStrings.sum2Map(nums, target));
		System.out.println(NumsStrings.sum2NoMap(nums, target));
		System.out.println("-----");
		
		
	}
	@Test
	public void testSum3Idx() {
		//[-1,0,1,2,-1,-4]
		testSum3(0, -1,0,1,2,-1,-4);
		testSum3(3, 1,1,1,1,1);
		testSum3(0, 1,2,-2,-1);
	}
	static void testSum3(int target, int...nums) {
		print(nums);
		System.out.println(NumsStrings.sum3Map(nums, target));
	//	System.out.println(Numbers.sum3(nums, target));
		System.out.println(NumsStrings.sum3NoDups(nums, target));
		System.out.println("-----");
	}
	@Test
	public void testFindFloor(){
		doTestFloor(1, 1,1,2,2,3,3,4,5,8);
		doTestFloor(2, 1,1,2,2,3, 3,3,4,5,8);
		doTestFloor(3, 1,1,2,2,3,3 ,3, 3,3,4,5,8);
		doTestFloor(2, 1,2);
		doTestFloor(1, 1,2);
		doTestFloor(3, 2,3);
		doTestFloor(3, 1, 1, 2, 2, 2, 4, 5);
		doTestFloor(3, 2, 2, 2, 4, 5);
	}
	
	static void doTestFloor(int target, int...num) {
		int ar[] = num;
		//Arrays.sort(ar);
		print(ar);
		System.out.println("target: " + target);
		int i = NumsStrings.findFloor(ar, target);
		System.out.println("floor: num["  + i + "] = " + (i < 0 ? "NULL" : Integer.toString(ar[i])));
		System.out.println("-------");
	}
	
	static void print(int...num) {
		for (int n : num)
			System.out.print(n + ", ");
		System.out.println();
	}
}
