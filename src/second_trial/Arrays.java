package second_trial;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Arrays {

	static void swap(int[] ar, int i, int j) {
		int t = ar[i];
		ar[i] = ar[j];
		ar[j] = t;
	}
	static void reverse(int[] arr) {
		// 0 1 2 3 4
		int mid = arr.length / 2;
		if (arr.length % 2 == 1) --mid;
		for (int i = 0; i <= mid; ++i) {
			swap(arr, i, arr.length - 1 - i);
		}
	}
	
	// sorted by row, for eg

	public static int findKLargest(int[] left, int[] right, final int k) {
		int l = 0;
		int r = 0;
		while (l + r < k - 1) {
			// find the number of remaining elements and div by half (rounded down)
			int d = (k - l - r) / 2;
			int newL = l + d - 1;
			int newR = r + d - 1;
			
			// readjust to be from tail
			int adjustedL = left.length - 1 - newL;
			int adjustedR = right.length - 1 - newR;
			if (adjustedL >= 0
					&& (adjustedR < 0 || left[adjustedL] > right[adjustedR])) {
				l = newL + 1;
			}
			else {
				r = newR +1;
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
	
	public static int findKSmallestTwoD(int[][] matrix, int k) {
		// all initted to 0
		int [] idx = new int[matrix.length];
		
		PriorityQueue<Integer> minHeap= new PriorityQueue<>(matrix.length,
				new Comparator<Integer>() {

					@Override
					public int compare(Integer o1, Integer o2) {
						return matrix[o1][idx[o1]] - matrix[o2][idx[o2]];
					}
				});
		for (int row = 0; row < matrix.length; ++row) {
			minHeap.add(row);
		}
		
		int i = 0;
		while (i < k - 1) {
			Integer minRow = minHeap.poll();

			System.out.println(matrix[minRow][idx[minRow]]);
			
			++idx[minRow];
			++i;
			// if there's more in this row to consider, then put it back
			if (idx[minRow] < matrix[minRow].length) {
				minHeap.add(minRow);
			}
		}
		int min = minHeap.poll();
		return matrix[min][idx[min]];
		
	}

	
	public static int findKSmallestUseI(int[] left, int[] right, final int k) {
		
		int l = 0;
		int r = 0;
		int i = 0;
		// l +1 + r + 1 = k
		// max (l+k) = k - 2 (or [0, k-1)
		while (i + 1< k) {
			// total ele = k - 1  - i +1
			int d = (k - i) / 2;
			int newL = l + d - 1;
			int newR = r + d - 1;
			i = i + d ;
			if (newL < left.length
					&& (newR >= right.length || left[newL] < right[newR])){
				// skip over newL because we've included it
				l = newL + 1;
			}
			else {
				r = newR + 1;
			}
		}
		
		// find the next min
		if (l < left.length && (r >= right.length || left[l] < right[r])){
			return left[l];
		}
		else {
			return right[r];
		}
	}
	

	public static int findKSmallest(int[] left, int[] right, final int k) {
	
		int l = 0;
		int r = 0;
		// l +1 + r + 1 = k
		// max (l+k) = k - 2 (or [0, k-1)
		while (l+r < k-1) {
			// total ele = k - 1  - (l+r) +1
			int d = (k - l - r) / 2;
			int newL = l + d - 1;
			int newR = r + d - 1;
			
			if (newL < left.length
					&& (newR >= right.length || left[newL] < right[newR])){
				// skip over newL because we've included it
				l = newL + 1;
			}
			else {
				r = newR + 1;
			}
		}
		
		// find the next min
		if (l < left.length && (r >= right.length || left[l] < right[r])){
			return left[l];
		}
		else {
			return right[r];
		}
	}
	
	public static int findKSmallestOpt(int[] left, int[] right, final int k) {
	
		// similar to the above,
		// but isntead of looking at one at a time, we look at a range
		// k - 1 = l + r
		// 
	
		int l = 0;
		int r = 0;
		
		// total elements = l+1 +r+1 = k
		while (l + r < k - 1) {
			// step is the diff of remaning element
			// i = l+r + 1
			// diff (remaining) = k - i + 1 = k +1 -l -r -1
			int step = (k - l - r)/ 2;
			int newL = l + step - 1;
			int newR = r + step - 1;
			// right is off the right end
			// or if min is left
			if (newL < left.length && 
					(newR >= right.length || left[newL] < right[newR])) {
				// +1 because we've inlcuded upto newL, hence skipping over newL
				l = newL + 1;
			}
			else { 
				r = newR + 1;
			}
		}
		// get the min, basically
		if (l < left.length && (r >= right.length || left[l] < right[r])){
			return left[l];
		}
		else {
			return right[r];
		}
	}
	
	public static int[] merge(int[] left, int[] right) {
		int ret[] = new int[left.length + right.length];
		int k = 0;
		int i = 0;
		int j = 0;
		while (i < left.length && j < right.length) {
			if (left[i] < right[j]) {
				ret[k++] = left[i++];
			}
			else if (left[i] > right[j]) {
				ret[k++] = right[j++];
			}
			else {
				ret[k++] = left[i++];
				ret[k++] = right[j++];
			}
		}
		
		while (i < left.length) {
			ret[k++] = left[i++];
		}
		while (j < right.length) {
			ret[k++] = right[j++];
		}
		return ret;
	}
}
