package second_trial.test;

import org.junit.Test;
import static second_trial.Arrays.*;
public class ArraysTest {

	@Test
	public void testSmallest2D() {
		int[][] matrix = new int[][]
				{
					{1,5,9},
					{10,11,13},
					{12,13,15}
					
				};
				
				
		testSmallest2D(matrix, 8);
	}

	static void testSmallest2D(int[][] matrix, int k) {
		int i = 0;
		for (int[] row : matrix) {
			print(i++ + ": ", row);
		}
		
		System.out.println(k + "th smallest element: " + findKSmallestTwoD(matrix, k));
	}
	
	@Test
	public void testSmallestOpt() {
		testSmallestK(array(1, 3,9,10, 11, 13),
			      array(2,4,5),
			      8);
//		testSmallestK(array(0, 2, 4, 6),
//				      array(1,3,5),
//				      2);
//		
//		testSmallestK(array(0, 2, 4, 6),
//			      array(1,3,5),
//			      3);
//		
//		testSmallestK(array(0, 2, 4, 6),
//			      array(1,3,5),
//			      4);
//		
//		testSmallestK(array(0, 2, 4, 6,7,9),
//			      array(1,3,5, 10),
//			      9);
	}
	
	@Test
	public void testLargest() {
		testLargestK(array(0, 2, 4, 6),
			      array(1,3,5),
			      2);
	}
	static void testLargestK(int[] left, int[] right, int k) {
		print("left: ", left);
		print("right: ", right);
		int[] merged = merge(left, right);
		print("merged: ", true, merged);
		//System.out.println(k + "th smallest: " + findKSmallest(left, right, k));
		System.out.println(k + "th largest (OPT): " + findKLargest(left, right, k));
		System.out.println("-------");
	}
	static int[] array(int...n) { return n ; }
	
	static void print(String label, int...num){
		print(label, false, num);
	}
	static void print(String label, boolean withIdx, int...num){
		System.out.print(label);
		int i = 0;
		for (int n : num){
			if (withIdx) {
				System.out.print("[" + i + ": " + n + "]");
				++i;
			}
			else {
				System.out.print(n + ", ");
			}
			
		}
		System.out.println();
	}
	
	static void testSmallestK(int [] left, int[] right, int k) {
		print("left: ", left);
		print("right: ", right);
		int[] merged = merge(left, right);
		print("merged: ", true, merged);
		//System.out.println(k + "th smallest: " + findKSmallest(left, right, k));
		System.out.println(k + "th smallest (OPT): " + findKSmallestOpt(left, right, k));
		System.out.println(k + "th smallest (with i): " + findKSmallestUseI(left, right, k));
		System.out.println("-------");
	}
}
