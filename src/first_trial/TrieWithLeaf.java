package first_trial;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.IntConsumer;

public class TrieWithLeaf {

	static class Node {
		private final char ch;
		private final Map<Character, Node> children;
		Node (char ch) {
			this.ch = ch;
			children = new HashMap<>();
		}
		
		public Node lookup(char ch) {
			return children.get(ch);
		}
		
		public Node addChar (char ch) {
			Node ret = new Node(ch);
			children.put(ch, ret);
			return ret;
		}
		
		public boolean hasChildren() {
			return !children.isEmpty();
		}
	}
	
	
	private final Node root = new Node((char)0);
	private final Set<String> words = new HashSet<>();
	public void addWord(String st) {
		words.add(st);
		st.chars().forEach(new IntConsumer() {
			Node cur = root;
			@Override
			public void accept(int value) {
				char ch = (char)value;
				Node node = cur.lookup(ch);
				if (node == null) {
					node = cur.addChar(ch);
				}
				cur = node;
			}
		});
	}
	
	
	public boolean lookup(String st) {
		// short
		if (words.contains(st)) return true;
		
		String word = st;
		Node cur = root;
		for (int i = 0; i < word.length(); ++i) {
			cur = cur.lookup(word.charAt(i));
			if (cur == null) {
				// no hope
				if (i == 0) return false;
				else {
					// find the partition
					word = st.substring(i);
					// reset i
					i = -1;
					cur = root;
				}

				
			}
		}
		// st: abcdefg
		// abcd
		// ef
		// efgh
		// if this is the leaf?
		
		return words.contains(word);
	}
	
}
