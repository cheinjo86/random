package first_trial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.IntConsumer;

public class Strings {

	public static String removeDup(String st) {
		char[] text = st.toCharArray();
		
		Set<Character> seen = new HashSet<>();
		StringBuilder ret = new StringBuilder();
		for (char ch : text) {
			if (!seen.contains(ch)){
				ret.append(ch);
				seen.add(ch);
			}
		}
		return ret.toString();
	}
	public static class LookupPosition {
		public static class Trie {
			static class Node {
				final char ch;
				// word can occur at multiple positions
				final LinkedList<Integer> positions = new LinkedList<>();
				final Map<Character, Node> children = new HashMap<>();
				
				Node(char ch) {
					this.ch = ch;
				}
			}
			
			final Node root = new Node((char)0);
			
			public void addWord(String st, int pos) {
				Node node = root;
				for (char ch : st.toCharArray()) {
					Node kid = node.children.get(ch);
					if (kid == null) {
						node.children.put(ch, kid = new Node(ch));
					}
					node = kid;
				}
				// set the position in the last node
				node.positions.addLast(pos);
			}
			
			public List<Integer> lookup(String word) {
				Node node = root;
				for (char ch : word.toCharArray()) {
					node = node.children.get(ch);
					if (node == null) {
						return null;
					}
				}
				
				return node.positions;
			}
		}
		
		public static Trie createTrie(String text) {
			String[] words = text.split("\\s+");
			Trie ret = new Trie();
			for (int i = 0; i < words.length; ++i) {
				ret.addWord(words[i], i);
			}
				
			return ret;
		}
	}
	public static Set<String> permutate(String st) {
		Set<String> ret = new HashSet<>();
		LinkedList<Character> pool = new LinkedList<>();
		for (char ch : st.toCharArray()) {
			pool.add(ch);
		}
		doPermutate(pool, new StringBuilder(), ret);
		return ret;
	}
	
	private static void doPermutate(LinkedList<Character> pool,
			                        StringBuilder prefix,
			                        Set<String> ret) {
		if (pool.isEmpty()) {
			ret.add(prefix.toString());
			return;
		}
		
		int size = pool.size();
		while (size > 0) {
			Character chosen = pool.removeFirst();
			
			StringBuilder copied = new StringBuilder(prefix);
			copied.append(chosen);
			LinkedList<Character> copiedPool = new LinkedList<>();
			copiedPool.addAll(pool);
			
			// put back the removed item, but at the end
			pool.addLast(chosen);
			
			doPermutate(copiedPool, copied, ret);
			-- size;
		}
	}
	public static boolean isValid(String st) {
		int open = 0;
		for (char ch : st.toCharArray()) {
			if (ch == '(') {
				++open;
			}
			else if (ch == ')') {
				if (open == 0) return false;
				--open;
			}
		}
		
		return open == 0;
		
	}
	
	public static boolean isValid(LinkedList<Character> chars) {
		int open = 0;
		for (Character ch : chars) {
			if (ch == '(') {
				++open;
			}
			else if (ch == ')') {
				// (( ))
				if (open == 0) return false;
				--open;
			}
		}
		return open == 0;
	}
	
	static String toString(LinkedList<Character> chars) {
		StringBuilder bd = new StringBuilder();
		for (Character ch : chars) {
			bd.append(ch);
		}
		return bd.toString();
	}
	public static List<String> removeInvalid(String st) {
		LinkedList<Character> chars = new LinkedList<>();
		for (char ch : st.toCharArray()) {
			chars.add(ch);
		}

		// do it with bfs to ensure minimal removal, if any is found first
		LinkedList<LinkedList<Character>> queue = new LinkedList<>();
		queue.add(chars);
		Set<LinkedList<Character>> visited = new HashSet<>();

		List<String> ret = new LinkedList<>();
		while (!queue.isEmpty()) {
			LinkedList<Character> cur = queue.removeFirst();
			if (!visited.contains(cur)){
				
				if (isValid(cur)) {
					ret.add(toString(cur));
				}
				else {
					// want least removal, so we'll stop if the cur is shorter
					// than existing answer
					if (!ret.isEmpty() && cur.size() < ret.get(0).length()) break;
					
					ListIterator<Character> iter = cur.listIterator();
					while (iter.hasNext()) {
						Character ch = iter.next();
						if (ch == '(' || ch == ')') {
							iter.remove();
							// make copied
							queue.addLast((LinkedList<Character>)cur.clone());
							// put the removed char back
							iter.add(ch);
						}
					}
				}
				visited.add(cur);
			}
		}

		return ret;
	}

	public static boolean parensCheck(String st) {
		char[] text = st.toCharArray();
		Map<Character, Character> match = new HashMap<>();
		match.put('(', ')');
		match.put('[', ']');
		match.put('{', '}');
		match.put('<', '>');
		Stack<Character> stack = new Stack<>();
		for (char ch : text) {
			switch(ch) {
			case '(':
			case '[':
			case '{':
			case '<':
				stack.push(ch);
				break;
			case ')':
			case ']':
			case '}':
			case '>':
				if (stack.isEmpty()) return false;
				else if (match.get(stack.pop()) != ch) {
					return false;
				}
				break;
			default: // do nothing
				break;
			}
		}
		return true;
	}
	public static boolean canMakePalindrome(char[] st) {
		// palindrom there is at most one type of character
		// with odd count and the rest even count
		// char --> odd
		Set<Character> odds= new HashSet<>(st.length);
		
		
		for (char ch : st) {
			if (odds.contains(ch)) {
				odds.remove(ch);
			}
			else {
				odds.add(ch);
			}
		}
		
		return odds.size() < 2;
	}
	public static enum Op{
		PLUS {

			@Override
			double evaluate(List<Double> values){
				Iterator<Double> iter = values.iterator();
				double ret = iter.next();
				while (iter.hasNext()) {
					ret += iter.next();
				}
				

				return ret;
			}

			
		},
		MINUS {
			@Override
			double evaluate(List<Double> values){
				Iterator<Double> iter = values.iterator();
				double ret = iter.next();
				while (iter.hasNext()) {
					ret -= iter.next();
				}
				

				return ret;
			}

			
		},
		TIMES {

			@Override
			double evaluate(List<Double> values){
				Iterator<Double> iter = values.iterator();
				double ret = iter.next();
				while (iter.hasNext()) {
					ret *= iter.next();
				}
				

				return ret;
			}

			
		},
		DIV {

			@Override
			double evaluate(List<Double> values){
				Iterator<Double> iter = values.iterator();
				double ret = iter.next();
				while (iter.hasNext()) {
					ret /= iter.next();
				}
				

				return ret;
			}
			
		}
		;
		
		abstract double evaluate(List<Double> values);
	}
	public static abstract class Node {
		
		private final List<Node> children;
		Node (List<Node> children) {
			this.children = children;
		}
		
		public List<Node> getChildren() { return children; }
		public abstract double evaluate();
	}
	
	
	public static void main(String[] args) {
		System.out.println(computeHash("star"));
		System.out.println(computeHash("rats"));
	}
	public static int computeHash(String st) {
		Map<Character, Integer> hist = new HashMap<>(st.length());
		
		st.chars().forEach(new IntConsumer() {
			
			@Override
			public void accept(int value) {
				char ch = (char) value;
				hist.put(ch, hist.getOrDefault(ch, 0) + 1);
			}
		});
		// h = h * 31 + char ^ count
		return hist.hashCode();
	}
	public static class OpNode extends Node {
		
		
		private final Op op;
		OpNode(List<Node> children, Op op) {
			super(children);
			this.op = op;
		}
		
		@Override
		public double evaluate() {
			List<Double> evaluated = new LinkedList<>();
			for (Node child : getChildren()) {
				evaluated.add(child.evaluate());
			}
			return op.evaluate(evaluated);
		}

	}
	
	public static class Literal extends Node {
		private final double value;
		Literal(String val) {
			super(new LinkedList<>());
			value = Double.parseDouble(val);
		}
		@Override
		public double evaluate() {
			return value;
		}
	}
	
	public static boolean match(String word, String p) {
		return match(word.toCharArray(), 0, p.toCharArray(), 0);
	}
	public static boolean match(char[] word, int wHead, char[] pattern, int pHead) {
		
		for (int w = wHead, i = pHead; w < word.length; ) {
			if (i >= pattern.length) return false;
			if (pattern[i] == '*') {
				while(i < pattern.length && pattern[i] == '*') {
					++i;
				}
				
				if (i == pattern.length ) return true;
				// fast forward
				boolean matched = false;
				for (int w2 = w+1, i2 = i; w2 < word.length; ++w2) {
					// *st | abstsgst
					if (word[w2] == pattern[i2] || pattern[i2] == '*') {
						matched |= match(word, w2, pattern, i2);
					}
					if (matched) return true;
					
				}
				return false;
			}
			else {
				if (pattern[i] != word[w]) return false;
				++w;
				++i;
			}
		}
		
		return true;
	}
	
	
	public static List<String> removeInvalidParentheses(String s) {
	    List<String> ans = new ArrayList<>();
	    remove(s, ans, 0, 0, new char[]{'(', ')'});
	    return ans;
	}

	public static void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
	    for (int stack = 0, i = last_i; i < s.length(); ++i) {
	        if (s.charAt(i) == par[0]) stack++;
	        if (s.charAt(i) == par[1]) stack--;
	        if (stack >= 0) continue;
	        for (int j = last_j; j <= i; ++j)
	            if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
	                remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
	        return;
	    }
	    String reversed = new StringBuilder(s).reverse().toString();
	    if (par[0] == '(') // finished left to right
	        remove(reversed, ans, 0, 0, new char[]{')', '('});
	    else // finished right to left
	        ans.add(reversed);
	}
}
