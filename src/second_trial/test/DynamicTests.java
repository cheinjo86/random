package second_trial.test;

import org.junit.Test;

import second_trial.Graphs.WordNode;

import static second_trial.DynamicProgramming.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicTests {
	static void print(String label, int...num){
		System.out.print(label);
		for (int n : num) {
			System.out.print(n + " ");
		}
		System.out.println();
	}
	@Test
	public void testCeil() {
		testCeil(2, 1, 1, 2);
		testCeil(1, 1, 1, 2);
		testCeil(1, 1, 2);
		testCeil(2, 1, 2);
	}
	
	static void testCeil(int target, int...num) {
		print("array: ", num);
		print("target: ", target);
		
		int idx = findCeilIdx(num, 0, num.length -1, target);
		System.out.println("ceiling at [" + idx + "] =" + num[idx]);
		System.out.println("------");
	}
	
	public static class AbcDict implements Dict {
		final Set<String> dict;
		final Set<Character> alphabet;
		public AbcDict(Set<String> dict, char...ch) {
			this.dict = dict;
			alphabet = new HashSet<>();
			for (char c : ch) {
				alphabet.add(c);
			}
		}
		public AbcDict(Set<String> dict, Set<Character> alpha) {
			this.dict = dict;
			alphabet = alpha;
		}
		@Override
		public String buildWord(char[] text, int i, int j) {
			String st = new String(text, i, j-i+1);
			if (dict.contains(st)) {
				return st;
			}
			else {
				return null;
			}
		}

		@Override
		public Set<Character> getAlphabet() {
			return alphabet;
		}
		@Override
		public boolean containWord(String word) {
			return dict.contains(word);
		}
		
	}

	
	@Test
	public void testInterpretation() {
		doTestDict("anotherandapples");
	}
	
	static final Set<String> d = new HashSet<>();
	static final Dict dict = new AbcDict(d, 'a', 'n', 'd', 'o');
	static {
		String[] text = {"a", "an", "and", "other", "another", "apple", "s", "apples"};
		for (String st : text) {
			d.add(st);
		}
	}
	static void doTestDict(String st) {
		System.out.println("text: " + st);
		System.out.println("possible meanings: ");
		for (String t : findAllInterpretationOpt(st.toCharArray(), dict)) {
			System.out.println("   " + t);
		}
	}
	@Test
	public void testCutPalin() {
		testPalin("aa|bccb|d");
		//         01|2345|6
	}
	
	static void testPalin(String st) {
		System.out.println("text: " + st);
		
		System.out.println("opt ");
		cutPalinDromeOpt(st.toCharArray());
	}
}
