package second_trial.test;

import org.junit.Test;
import static second_trial.Sortings.*;

public class SortingsTest {

	@Test
	public void testSortDocks() {
		int[] docks = new int[] {0, 4, 5, 1, 2, 3, 6};
		
		print("original docks: ", docks);
		sortBoxes(docks);
		print("sorted: ", docks);
	}
	@Test
	public void testOrderStats() {
		orderStat(1, 3, 1, 2);
		orderStat(2, 3, 1, 2);
		orderStat(3, 3, 1, 2);
		orderStat(1, 3, 1);
		orderStat(2, 3, 1);
	}
	
	static int[] clone(int...num){
		int[] orig = new int[num.length];
		for (int i = 0; i < orig.length; ++i){
			orig[i] = num[i];
		}
		return orig;
	}
	static void orderStat(int k, int...num){
		print("original ", num);
		
		System.out.println(k + "th smallest: " + findKSmallestUnsorted(clone(num), k));
		System.out.println(k + "th largest: " + findKLargestUnsorted(clone(num), k));
		
		
		// sorted
		quicksort(num);
		print("sorted: ", true, num);
		System.out.println("-----------");
	}
	
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
}
