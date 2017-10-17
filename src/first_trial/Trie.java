package first_trial;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Trie {
	private final Character ch;
	private final Map<Character, Trie> children;
	
	@Override
	public String toString() {
		return ch + "";
	}
	public Trie(char ch) {
		this.ch = ch;
		children = new HashMap<>();
	}
	
	public void addChild(char ch) {
		children.put(ch, new Trie(ch));
	}
	
	public void addChildren(String st) {
		Trie cur = this;
		for (char ch : st.toCharArray()) {
			Trie child = cur.children.get(ch);
			if (child == null) {
				child = new Trie(ch);
				cur.children.put(ch, child);
			}
			cur = child;
		}
	}
	
	public void lookup(String prefix) {
		Trie cur = this;
		for (char ch : prefix.toCharArray()) {
			Trie child = cur.children.get(ch);
			if (child == null) {
				return;
			}
			cur = child;
		}
		
		// print all the possible children
		doPrint(new StringBuilder(prefix), cur);
		
	}
	
	//DFS
	static private void doPrint (StringBuilder prefix, Trie cur){
		if (cur == null || cur.children.isEmpty()) {
			System.out.println(prefix.toString());
		}
		else {
			for (Trie child : cur.children.values()) {
				doPrint(new StringBuilder(prefix).append(child.ch), child);
			}
		}
	}
	
	public static void main(String[] args) {
		// Ips addresses
		String[] wrds = new String[] {"facebook", "facts", "fact about GB", "fan", "apple", "great", "fancy", "fat", "facet", "facial"};
		
		Trie root = new Trie((char)0);
		for (String wd : wrds) {
			root.addChildren(wd);
		}
		
		root.lookup("fac");
		System.out.print("Enter a word: ...");
		Scanner in = new Scanner(System.in);
		String wd = null;
		while ((wd = in.next()) != null){
			System.out.println("\nMatches: ...");
			root.lookup(wd);
			System.out.println("\n\n----------\n");
			System.out.print("Enter a word: ...");
			
		}
	}
}
