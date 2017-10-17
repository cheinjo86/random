package first_trial.test;

import static first_trial.Strings.*;

import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Test;

import first_trial.Strings;
import first_trial.Strings.LookupPosition.Trie;

public class StringsTest {
	static void palin(String st) {
		System.out.println(st + " is palin? " + canMakePalindrome(st.toCharArray()));
	}
	
	@Test
	public void testLinkedList() {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		
		ListIterator<Integer> iter = list.listIterator();
		
		iter.next();
		System.out.println("before: " + list);
		Integer removed = iter.next();
		iter.remove();
		System.out.println("after: " + list);
		System.out.println("removed: " + removed);
		
		iter.add(removed);
		System.out.println("added back: " + list);
	}
	@Test
	public void testIsValidParens() {
		//
		removeInvalid("()())()");
		removeInvalid("(v)())()");
		removeInvalid("(ab(c)");
	}
	static void removeInvalid(String st) {
		System.out.println(st + " --> " + Strings.removeInvalid(st));
		//System.out.println(st + " --> " + removeInvalidParentheses(st));
	
	}
	@Test
	public void testLookupWordPos() {
		String st = "one two three sub subone four substring twenty one threet string subtext";
		Trie trie = LookupPosition.createTrie(st);
		
		System.out.println(trie.lookup("threet"));
	}
	
	@Test
	public void testPermutate() {
		System.out.println(permutate("ABC"));
	}
	@Test
	public void testParens() {
		System.out.println(parensCheck("a(bc([x)])"));
	}
	@Test
	public void testPalindrme() {
		palin("MMO");
		palin("DOOR");
		palin("doo");
		
	}
	static void testIt(String word, String p) {
		System.out.println(word + " matches " + p + " : " + match(word, p));
	}
	@Test
	public void testRegex() {
		testIt("abstsgst", "*sg");
	}
}
