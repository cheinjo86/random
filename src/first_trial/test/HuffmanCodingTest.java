package first_trial.test;

import org.junit.Test;

import static first_trial.HuffmanCoding.*;
public class HuffmanCodingTest {

	static int[] array(int...x) { return x; }
	
	static void print(int[] ar) {
		for (int n : ar) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	@Test
	public void testMinHeap() {
		int [] ar = array(4,1,3,9,5);
		print(ar);
		
		minHeapSort(ar);
		print(ar);
		
		int[] copy  = array(4,1,3,9,5);
		maxHeapSort(copy);
		print(copy);
	}
	
	@Test
	public void testHuffman() {
		encode(null);
	}
}
