package collected_g;

import java.util.HashSet;
import java.util.Set;

public class Collected {

	// find all possible interpretations
	public static void findAllInterpretations(char[] text, Set<String> dict) {
		// s[i,j] = s[i,k] + s[k+1,j] + s[ij]
		Set<String>[][] lookup = new Set[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < text.length; ++j) {
				Set<String> ret = null;
				
				Set<String> left = null;
				Set<String> right = null;
				
				for (int k = i; k < j; ++k) {
					left = lookup[i][k];
					right = lookup[k+1][j];
					if (left != null && right != null) {
						// merge them
						if (ret == null) {
							ret = new HashSet<>();
						}
						
						for (String l : left) {
							for (String r : right) {
								ret.add(l + " " + r);
							}
						}
					}
				}
				
				String st = isInDict(dict, text, i, j);
				if (st != null) {
					if (ret == null) {
						ret = new HashSet<>();
					}
					ret.add(st);
				}
				
				lookup[i][j] = ret;
			}
		}
		
		System.out.println(lookup[0][text.length - 1]);
	}
	
	public static void findAllInterpretationsOpt(char[] text, Set<String> dict) { 
		// s[i,j] = s[i,k] + s[k+1,j] + s[ij]
		Set<String>[][] lookup = new Set[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < text.length; ++j) {
				
				String st = isInDict(dict, text, i, j);
				if (st != null) {
					Set<String> ret = new HashSet<>();
					ret.add(st);
					lookup[i][j] = ret;
				}
			}
		}
		
		for (int i = 0; i < text.length; ++i) {
			for (int k = 0; k < i; ++k) {
				Set<String> left = lookup[0][k];
				Set<String> right = lookup[k+1][i];
				if (left != null && right != null) {
					Set<String> ret = lookup[0][i];
					if (ret == null) {
						lookup[0][i] = ret = new HashSet<>();
					}
					
					for (String l : left) {
						for (String r : right) {
							ret.add(l + " " + r);
						}
					}
				}
			}
		}
			
		
	}
		
	private static String isInDict(Set<String> dict, char[] text, int i, int j) {
		String ret = new String(text, i, j-i + 1);
		if (dict.contains(ret)) {
			return ret;
		}
		else {
			return null;
		}
	}
}
