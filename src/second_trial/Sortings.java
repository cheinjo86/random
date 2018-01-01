package second_trial;

public class Sortings {

	public static void sortBoxes(int[] docks) {
		final int LEN = docks.length; 
		// there are elements at [0-->LEN-2], value ranging from [0,LEN-2]
		
		int emptySlot = LEN-1;
		docks[emptySlot] = 7;
		// for each doc[i]
		//    if doc[i] != i
		//       if emptySlot == doc[i]
		//           doc[emptySlot] = doc[i]
		//       else doc[em
		//       doc[emptySlot] = doc[i]
		//       
		boolean done = false;
		int i = 0;
		// find the first mismatch box
		while (i < LEN - 1) {
			if (docks[i] == i) ++i;
			else {
				break;
			}
		}
		while (!done) {
			if (i != docks[i]) {
				int moved = emptySlot;
				docks[emptySlot] = docks[docks[i]];
				docks[docks[i]] = docks[i];
				emptySlot = i;
				i = moved;
				docks[emptySlot] = -1;
			}
			else {
				done = true;
			}
		}
	}
	public static int findKSmallestUnsorted(int[] nums, int k) {
		// -1 for 0-based, 
		return orderStatistic(nums, k-1, 0, nums.length - 1);
	}
	public static int findKLargestUnsorted(int[] nums, int k) {
		// -1 for 0-based, 0 1 2 | k = 2, --> adjsuted = 1
		return orderStatistic(nums, nums.length - k, 0, nums.length - 1);
	}
	/**
	 * 
	 * @param nums
	 * @param k    zero0based
	 * @param low
	 * @param high
	 * @return
	 */
	public static int orderStatistic(int[] nums, int k, int low, int high) {
		// find the  kth largest element in an an sorrted array
		int i = partition(nums, low, high);
		
		if (i == k) return nums[k];
		
		if (i < k) {
			return orderStatistic(nums, k, i+1, high);
		}
		return orderStatistic(nums, k, low, i-1);
	}
	
	public static void quicksort(int[] nums) {
		quicksort(nums, 0, nums.length - 1);
	}
	public static void quicksort(int[] nums, int low, int high) {
		if (low < high){
			int i = partition(nums, low, high);
			quicksort(nums, low, i-1);
			quicksort(nums, i, high);
		}
	}
	
	
	private static int partition(int[] nums, int low, int high) {
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
	
	public static int searchMinHeap(int[] heap, int root, int target) {
		// (reached end of heap
		// or the min is larger than target, then don need to descend
		// because ever nodes in this subtree > traget
		if (root > heap.length || heap[root] > target ) return - 1;
		
		if (heap[root] == target) return root;
		
		// try left
		int l = searchMinHeap(heap, root * 2 + 1, target);
		if (l >= 0) return l;
		
		// try right
		return searchMinHeap(heap, root * 2 + 2, target);
	}

	public static void buildHeap(int[] nums) {
		for (int i = nums.length / 2 - 1; i >= 0; --i) {
			minHeapify(nums, i, nums.length);
		}
	}
	
	//
	public static void minHeapify(int[] num, int i, int n) {
		// put the min at the parent (front)
		int min = i;
		int l = 2 * i + 1;
		int r = + 2 * i + 2;
		
		if (l < n && num[l] < num[min]) {
			min = l;
		}
		
		if (r < n && num[r] < num[min]) {
			min = r;
		}
		
		if (min != i) {
			swap(num, i, min);
			minHeapify(num, min, n);
		}
	}
	
	static void swap(int[] nums, int i, int j) {
		int t = nums[i];
		nums[i] = nums[j];
		nums[j] = t;
	}
}
