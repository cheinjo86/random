package second_trial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// combintion, permutation, powerset
public class Sets {

	/**
	 * total uniqueue elements:
	 *  - uniqueue set: n!
	 *  - set with dups = n! / (x!*y!*z!*t!, ...)
	 *    where x, y, z, t... are number of dups of each eelement
	 *    
	 *    because for each of dups, you have x! identical substrings
	 *    
	 *    https://brilliant.org/wiki/permutations-with-repetition/ 
	 *    
	 *   for eg., [a,b,c] ==> 3! = 6
	 *            [a,a,b] ==> 3! / (2) = 3 
	 * @param pool
	 */
	public static void permutation(String pool) {
		count = 0;

		System.out.println("pool: " + pool);
		permutation(pool.toCharArray(), 0);
		count = 0;
		for (String st : set) {
			System.out.println(count + ": " + st);
			++count;
		}
		System.out.println("---------");
	}
	
	static Set<String> set = new HashSet<>();
	static int count = 0;
	static void permutation(char[] pool, int low) {
		if (low == pool.length - 1) {
			set.add(new String(pool));
			++count;
		}
		else {
			for (int i = low; i < pool.length; ++i) {
				swap(pool, i, low);
				permutation(pool, low+1);
				swap(pool, i, low);
				
			}
		}
	}
	
	static void swap(char[] ar, int a, int b) {
		char t = ar[a];
		ar[a] = ar[b];
		ar[b]= t;
	}
	public static void permute(String st) {
		count = 0;
		System.out.println("pool: " + st);
		permute(st.toCharArray(), 0);
		System.out.println("---------");
	}
	
	/**
	 * THIS IS THE BEST algo so far (use NO extra mem, but could be slower due to swap)
	 * @param pool
	 * @param low
	 * @param high
	 */
	public static void permute(char[] pool, int low) {
		if (low == pool.length - 1) {
			System.out.println(count + ": " + new String(pool));
			++count;
		}
		else {
			for (int i = low; i < pool.length; ++i) {
				swap(pool, i, low);
				permute(pool, low+1);
				swap(pool, i,low);
			}
		}
	}
	
	static int nChooseK(int n, int k) {
		int ret = n;
		while (k-- > 0) {
			ret *= n - 1;
			--n;
		}
		return ret;
	}
	
	public static void combination(char[] pool, final int k) {
		//
		// 
		
	}
	
	public static void combination(char[] pool, int low, int high, final int k) {
		if (high > k) {
			for (int i = low; i < high; ++i) {
				System.out.print(pool[i]);
			}
			System.out.println();
		}
		else {
			for (int i = low; i < pool.length; ++i) {
				//pool[]
			}
		}
	}
	
	static void print(int...num){
		for (int n : num){
			System.out.print(n + ", ");
		}
		System.out.println();
	}
	

	public static void permuteWithDups(String word) {
		char[] ch = word.toCharArray();
		List<String> ret = new LinkedList<>();
		permuteWithDups(ch, 0, ret);
		for (String s: ret)
			System.out.println(s);
		System.out.println("--- size: " + ret.size());
	}

	/**
	 * this handles duplicates, and does NOT use extra memory, but could be slower (due to swapping)
	 * @param nums
	 * @param tail
	 * @param ret
	 */
	public static void permuteWithDups(char[] nums, final int tail, List<String> ret) {
		if (tail == nums.length - 1) {
			ret.add(new String(nums));
		}
		else {
			Set<Character> seen = new HashSet<>();
			for (int i = tail; i < nums.length; ++i) {
				if (seen.contains(nums[i])) {
					continue;
				}
				seen.add(nums[i]);
				swap(nums, i, tail);

				permuteWithDups(nums, tail+1, ret);
				swap(nums, i, tail);
	
			}
		}
	}

}
