package first_trial;

import java.util.PriorityQueue;

public class Sortings {

	public static int findKthSmallest(int[] arr, int k) {
		return arr[orderStats(arr, k, 0, arr.length - 1)];
	}
	
	public static int findKLargest(int[] arr, int k) {
		return arr[orderStats(arr, arr.length - k + 1, 0, arr.length - 1)];
	}
	
	public static int[] findKSmallestElements(int[] arr, int k) {
		int n = arr.length;
		for (int i = n / 2 - 1; i > -1; --i) {
			minheapify(arr, n, i);
		}
		
		// extract min k time
		int max = n - k - 1;
		// i + 1 +k = n
		for (int i = n - 1; i >= max; --i) {
			int t = arr[i];
			arr[i] = arr[0];
			arr[0] = t;
			
			// 
			minheapify(arr, i, 0);
		}
		
		// x y z 
		
		int[] ret = new int[k];
		for (int i = 0; i < k; ++i) {
			ret[i] = arr[n - i - 1];
		}
		
		return ret;
	}
	
	
	public static int orderStats(int[] arr, int k, int low, int high) {
	
		int pivotIdx = partition(arr, low, high);
		if (pivotIdx == k -1) {
			return pivotIdx;
		}
		else if (pivotIdx > k - 1) {
			return orderStats(arr, k, low, pivotIdx - 1);
		}
		else {
			return orderStats(arr, k, pivotIdx + 1, high);
		}
	}
	
	static void minheapify(int[] arr, int n, int i) {
		int min = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < n && arr[min] > arr[l]) {
			min = l;
		}
		
		if (r < n && arr[min] > arr[r]) {
			min = r;
		}
		
		if (min != i) {
			int tem = arr[i];
			arr[i] = arr[min];
			arr[min] = tem;
			
			minheapify(arr, n, min);
		}
	}
	
//	public static void partialSort(int[] colors) {
//		// 1 2 or 3 only
//		int head = 0;
//		int tail = colors.length - 1;
//		
//		for (int k = 0; k < colors.length; ++k) {
//			switch(colors[k]) {
//			case 1: //swap to front
//				swap(colors, head, k);
//				++head;
//				break;
//
//			case 2:
//				// dont do naything;
//			case 3:
//				swap(colors, head, tail);
//				--tail;
//			}
//		}
//		
//	}
	
	static void swap(int[] num, int a, int b) {
		int t = num[a];
		num[a] = num[b];
		num[b] = t;
	}
	// heap
	public static void heapsort(int[] arr) {
		int n = arr.length;
		
		for (int i = n /2 - 1; i >= 0; --i) {
			heapify(arr, n, i);
		}
		
		// One by one extract an element from heap
        for (int i=n-1; i>=0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
 
            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
	}
	
	static void heapify(int arr[], int n, int i) {
		int max = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		
		if (l < n && arr[l] > arr[max]) {
			max = l;
		}
		
		if (r < n && arr[r] > arr[max]) {
			max = r;
		}
		
		if (max != i) {
			int temp = arr[max];
			arr[max] = arr[i];
			arr[i] = temp;
			
			heapify(arr, n, max);
		}
		
	}
	// quick
	
	public static void quickSort(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot

		int pivot = arr[(low + high) / 2];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
 
			while (arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j);
 
		if (high > i)
			quickSort(arr, i, high);
	}
	
	public static void quicksort2(int arr[], int low, int high) {
	      int i = partition(arr, low, high);
	  	System.out.printf("i = %d, low=%d, high=%d\n", i, low, high);
	      if (low < i - 1)
	            quickSort(arr, low, i - 1);
	      if (i < high)
	            quickSort(arr, i, high);
	}
	
	static int getCategory(int num) {
		if (num < 4) return 0;
		else if (num < 11) return 1;
		else return 2;
	}
	
	public static void sortPartialWithQueue(int[] nums, int k) {
		PriorityQueue<Integer> heap = new PriorityQueue<>(k+1);
		for (int i = 0; i < k; ++i) {
			heap.add(nums[i]);
		}
		
		for (int i = k , j = 0; j < nums.length; ++j) {
			if (i < nums.length) {
				nums[j] = heap.poll();
				heap.add(nums[i]);
			}
			else {
				// just extract
				nums[j] = heap.poll();
			}
		}
	}
	public static void sortPartiallySorted(int[] nums, int k) {
		// make a heap out of the first k + 1 element
		int[] heap = new int[k];
		for (int i = 0; i < k; ++i) {
			heap[i] = nums[i];
		}
		
		for (int i = heap.length / 2 - 1; i >= 0; --i) {
			heapify2(heap, i, heap.length);
		}
		
		//int[] ret = new int[nums.length];
		int size = heap.length;
		for (int i = k , j = 0; j < nums.length; ++i) {
			if (i < nums.length) {
				int t = nums[j];
				nums[j] = heap[0];
				heap[0] = t;
				heapify2(heap, 0, heap.length );
			}
			else {
				// exgract
				nums[j] = heap[0];
				// move last to top,
				heap[0] = heap[size-1];
				--size;
				heapify(heap, 0, size);
			}
		}
	}
	
	static void heapify2(int[] nums, int i, int n) {
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int min = i;
		
		if (l < n && nums[l] < nums[min]) {
			min = l;
		}
		
		if (r < n && nums[r] < nums[min]) {
			min = r;
		}
		
		if (i != min) {
			swap(nums, i, min);
			heapify2(nums, min, n);
		}
	}
	public static void partialSort(int[] nums) {
		partialSort(nums, 0, nums.length - 1);
	}
	public static void partialSort(int[] nums, int low, int high) {
		if (low < high) {
			int i = partialPartition(nums, low, high);
			partialSort(nums, low, i -1);
			partialSort(nums, i, high);
		}
		
	}
	
	static int partialPartition(int[] nums, int low, int high) {
		int pivot = getCategory(nums[(low + high) / 2]);
		int i = low;
		int j = high;
		
		while (i <= j) {
			while (getCategory(nums[i]) < pivot) {
				++i;
			}
			
			while (getCategory(nums[j]) > pivot) {
				--j;
			}
			
			if (i <= j) {
				// if diff, then swap
				int t = nums[i];
				nums[i] = nums[j];
				nums[j] = t;
				
				++i;
				--j;
						
			}
		}
		
		return i;
	}
	
	public static void quicksort(int[] array, int low, int high) {
		if (low < high) {
			int i = partition(array, low, high);
			quicksort(array, low, i - 1);
			quicksort(array, i, high);
		}
		
	}
	//11, 2 ,7 ,12, 10, 26, 26, 4 ,21, 11, 22, 28, 3 ,5 ,25
	static int partition(int[] array, int low, int high) {
		int pivot = array[(low + high)/2];
		int i = low;
		int j = high;

		while (i <= j) {

		
			while (array[i] < pivot) {
				++i;
			}
			
			while ( array[j] > pivot) {
				--j;
			}
			
			if (i <= j) {
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				
				++i;
				--j;
			}
		}
		
		return i;
		
	}
	
	public static void mergesort(int[] arr) {
		mergesort(arr, 0, arr.length - 1);
	}
	
	public static void mergesort(int[] arr, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergesort(arr, low, mid);
			mergesort(arr, mid + 1, high);
			
			// merge
			merge(arr, low, mid, high);
		}
	}
	
	static void merge(int[] arr, final int low, final int mid, final int high) {
		int[] merged = new int[high - low + 1];
		int i = low;
		int j = mid + 1;
		int n = 0;
		
		while (i <= mid && j <= high) {
			if (arr[i] < arr[j]) {
				merged[n++] = arr[i++];
			}
			else if (arr[i] > arr[j]) {
				merged[n++] = arr[j++];
			}
			else {
				merged[n++] = arr[i++];
				merged[n++] = arr[j++];
			}
		}
		
		while (i <= mid) {
			merged[n++] = arr[i++];
		}
		
		while (j <= high) {
			merged[n++] = arr[j++];
		}
		
		// copy back
		for (i = low, n = 0; n < merged.length; ++n, ++i) {
			arr[i] = merged[n];
		}
	}
	// merge
//	public static void mergesort(int[] arr, int low, int high) {
//		if (low < high) {
//			// split
//			int m = (low + high) / 2;
//			mergesort(arr, low, m);
//			mergesort(arr, m+1, high);
//			
//			// merge
//			merge(arr, low, m, high);
//		}
//	}
//	
//	static void merge(int[] arr, final int low, final int mid, final int high) {
//		int[] newArray = new int[high - low + 1];
//		int i = low;
//		int j = mid + 1;
//		int n = 0;
//		while (i <= mid && j <= high) {
//			if (arr[i] < arr[j]){
//				newArray[n++] = arr[i++];
//			}
//			else if (arr[j] < arr[i]) {
//				newArray[n++] = arr[j++];
//			}
//			else {
//				// incr both
//				newArray[n++] = arr[i++];
//				newArray[n++] = arr[j++];
//			}
//		}
//		
//		while (i <= mid) {
//			newArray[n++] = arr[i++];
//		}
//		
//		while (j <= high) {
//			newArray[n++] = arr[j++];
//		}
//		
//		for (i = low, n = 0; i <= high; ++i, ++n) {
//			arr[i] = newArray[n];
//		}
//		
//	}
	
}
