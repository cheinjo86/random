package first_trial.test;

import org.junit.Test;

import static first_trial.DynamicProgramming.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicTests {
	@Test
	public void testIsPalin() {
		findAllSubPalin("abccba".toCharArray());
	}
	public static void print(String label, int[] num) {
		System.out.print(label);
		for (int n : num) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	static Map<Integer, Character> buildMaping() {
		Map<Integer, Character> ret = new HashMap<>();
		for (char ch = 'A'; ch <= 'Z'; ++ch) {
			ret.put(ch - 'A', ch);
		}
		
		return ret;
	}

	@Test
	public void testEncodePhoneNumber() {
		Map<Integer, Character> dict = buildMaping();
		
		phoneTest(121213411, dict);
	}
	
	static void phoneTest(int num, Map<Integer, Character> dict) {
		int[] ret = new int[(int)Math.ceil(Math.log10(num))];
		int j = ret.length - 1;
		while (j >= 0) {
			ret[j] = num % 10;
			num /= 10;
			--j;
		}
		print("num: ", ret);
		System.out.println("encoded: " + encodePhoneNumber(ret, dict));
	}
	static  int[] array(int...x){ return x; }
	@Test
	public void testLongestCommon() {
		longestCommonSeq(array(1,2,3,4,5,6),
				         array(2,1,3,6));
	}
	@Test
	public void testLIS() {
		longestIncreasingSubseq(new int[] {1, 2, 4, 5, 3, 4, 6, 7});
	}
	@Test
	public void testParseMeaning() {
		doTest("anotherandapples",
				"a", "an", "and", "other", "another", "apple", "s", "apples");
		
	}
	
	static void doTest(String word, String...text){
		Set<String> dict = new HashSet<>(text.length);
		for (String st : text) {
			dict.add(st);
		}
		
		parseMeanings(dict, word.toCharArray());
	}
	@Test
	public void testEncoding() {
		encoding(new int[] {1, 0, 0});
		encodingOpt(new int[] {1, 0});
		countDecodingDP(new int[] {1, 0});
		
//		encoding(new int[] {1, 1, 3, 2, 1,2, 4});
//		encodingOpt(new int[] {1, 1, 3, 2, 1,2, 4});
//		countDecodingDP(new int[] {1, 1, 3, 2, 1,2, 4});
//		System.out.println("----");
//		encoding(new int[] {1, 1, 1, 1});
//		encodingOpt(new int[] {1, 1, 1, 1});
//		countDecodingDP(new int[] {1, 1, 1, 1});
		// a, b, c, d
		// i = 3
		//   k = 0, l: a
		//			r: [b,c,d], [bc, d], [b, cd]
		// s = 3
		
		//   k = 1, l: [a,b], [ab]
		// 			r: [c,d], [cd]
		// s = 3 + 2 * 2 = 7
		
		//   k = 2, l: [a,b, c], [ab, c], [a, bc]
		// 			r: [d]
		// s = s + 3 = 10
		// 
		// s = s -(i-1) = 10 - 2 = 8
		
		// s = s - i + 1 = 4 -2 + 1 = 3 (a, b,c | ab, c| a,bc)
		
		
	}
	// i = 2
	//	 k = 0, left [0,0]  = 1
	//			 right [1,2] = [1,1] , [11]
	//    ==> 1, 1, 1 | 1, 11
	
	//	 k = 1, left [0,1]  = [11], [1,1]
	//			 right [2,2] = [1]
	// ==> 11, 1 | 1, 1, 1  
	
}
