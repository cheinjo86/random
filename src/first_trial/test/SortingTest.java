package first_trial.test;

import java.util.Random;

import org.junit.Test;

import first_trial.Sortings;

public class SortingTest {
	@Test
	public void testPartialSort() {
		int[] num = new int[] {5,7,2,9,1,14,12,10,5,3};
		Sortings.partialSort(num);
		
		print(num);
	}
	@Test
	public void testMergeSort() {
		int[] nums = createRandom(5);
		print(nums);
		
		Sortings.mergesort(nums);
		print(nums);
		
		System.out.println("------");
		nums = createRandom(8);
		print(nums);
		
		Sortings.mergesort(nums);
		print(nums);
		
		System.out.println("------");
		nums = createRandom(10);
		print(nums);
		
		Sortings.mergesort(nums);
		print(nums);
	}
	
	static int[] createRandom(int count) {
		Random r = new Random();
		int[] arr = new int[count];
		for (int i = 0; i < arr.length; ++i) {
			arr[i] = r.nextInt(30);
		}
		
		return arr;
	}
	
	public int[] clone(int[] arr) {
		int ret[] = new int[arr.length];
		for (int i = 0; i < arr.length; ++i) {
			ret[i] = arr[i];
		}
		
		return ret;
	}
	static void print(int[] a) {
		for (int i : a) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	@Test
	public void testSorting() {
		
		int [] a = new int[]{11, 2 ,7 ,12, 10, 26, 26, 4 ,21, 11, 22, 28, 3 ,5 ,25 };//createRandom(15);
		int [] b = clone(a);
		int [] c = clone(a);
		int[] d = clone(a);
		int[] e = clone(a);
		
		print(a);
		
		Sortings.quicksort(a, 0, a.length - 1);
		Sortings.mergesort(b, 0, b.length -1 );;
		Sortings.heapsort(c);
		
		System.out.println("\n after sorted:");
		print(a);
		print(b);
		print(c);
		
		int k = Sortings.findKthSmallest(d, 6);
		System.out.println("6th smallest: " + k);
		int[] kSmallestEles = Sortings.findKSmallestElements(e, 6);
		print(kSmallestEles);
	}
}
