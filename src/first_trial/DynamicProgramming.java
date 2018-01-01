package first_trial;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicProgramming {

	//Given a map that maps letters for a number. Write a method that takes a phone number as input and retur\
	// ns all possible letter combinations for that phone number.
	public static Set<String> encodePhoneNumber(int[] nums, Map<Integer, Character> dict) {
		// xyzt
		// x[yz
		
		// s[i,j] = s[i,k] + s[k+1,j]
		// find all possible individually encodable numbers
		Set<String>[][] lookup = new Set[nums.length][nums.length];
		for (int i = 0; i < nums.length; ++i) {
			for (int j = i; j < nums.length; ++j) {
				Character ch = doEncode(nums, i, j, dict);
				if (ch != null) {
					lookup[i][j] = new HashSet<String>();
					lookup[i][j].add(ch.toString());
				}
			}
		}
		
		for (int i = 0; i < nums.length; ++i) {
			for (int k = 0; k < i; ++k) {
				Set<String> left = lookup[0][k];
				Set<String> right = lookup[k+1][i];
				if (left != null && right != null) {
					// permutate the two
					if (lookup[0][i] == null) {
						lookup[0][i] = new HashSet<>();
					}
					for (String l : left) {
						for (String r : right) {
							lookup[0][i].add(l + " " + r);
						}
					}
				}
			}
		}
		
		return lookup[0][nums.length - 1];
		
	}
	
	static Character doEncode(int[] nums, int i, int j, Map<Integer, Character> dict) {
		// build the number
		int num = 0;
		int pow = 0;
		while (j >= i) {
			num = num + nums[j] * (int)Math.pow(10, pow);
			++pow;
			--j;
			// 1 2 3 4
			// 4
			// 
		}
		
		return dict.get(num);
	}
	
	public static int lis(int[] arr) {
		//l[i] = l[i-1] + 1if a[i] > 
		int[] lis = new int[arr.length];
		for (int i= 0; i < lis.length; ++i) {
			lis[i] = 1;
		}
		
		int max = -1;
		for (int i = 1; i < arr.length; ++i) {
			for (int j = 0; j < i; ++j) {
				if (arr[i] > arr[j] && lis[i] < lis[j] + 1) {
					lis[i] = lis[j] + 1;
					if (max < lis[i]){
						max = lis[i];
					}
				}
			}
		}
		
		
		
		return max;
	}
	
	public boolean isPalin(char[] st) {
		// 0, 1, 2, 3 m1 = 1, m2 = 2
		//  0 1 2 3 4
		//
		boolean isEven = st.length / 2 == 0;
		for (int i = st.length / 2 - 1, j = isEven ?  i + 1 : i + 2;
		     i >= 0;
		     --i, ++j)
		{
			if (st[i] != st[j]) return false;
		}
		
		return true;
	}
	
	public static void findAllSubPalin(char[] st) {
		// s[i,j] =  s[i] s[i+1,j-1] s[j] if s[i] == s[j]
		boolean[][] isPalin = new boolean[st.length][st.length];
		String[][] palins = new String[st.length][st.length];
		
		for (int i = 0; i < st.length; ++i) {
			isPalin[i][i] = true;
			palins[i][i] = Character.toString(st[i]);
			if (i < st.length - 1) {
				isPalin[i][i+1] = st[i] == st[i+1];
				if(isPalin[i][i+1]) {
					palins[i][i+1] = st[i] + "" + st[i+1];
					System.out.println(palins[i][i+1]);
				}
			}
		}
		
		for (int len = 3; len <= st.length; ++len) {
			final int max = st.length - len;
			for (int i = 0; i <= max; ++i) {
				final int j = i + len - 1;
				isPalin[i][j] = isPalin[i+1][j-1] && st[i] == st[j];
				if (isPalin[i][j]) {
					palins[i][j] = st[i] + palins[i+1][j-1] + st[j];
					System.out.println(palins[i][j]);
				}
			}
		}
		
		
	}
	
	static int toBit(int i) {
		// 0
		return 1 << i;
	}
	
	static void print(int i ) {
		System.out.println("i = " + i + " | " + Integer.toBinaryString(toBit(i)));
	}
	public static void main(String[] args) {
		int left = 0b0101001;
		int right =0b0001000;
		int[] encoded = new int[10];
		
		/*
		 * i = 1 | 10
i = 2 | 100
i = 3 | 1000
i = 4 | 10000
		 */
		print(1);
		print(2);
		print(3);
		print(4);
		System.out.println(Integer.toBinaryString(toBit(2) | toBit(3)));
		// <<
		
		System.out.println(left & right);
	}
	public static int longestCommon (int[] left, int[] right) {
		int[][] common = new int[left.length + 1][right.length + 1];
		// common[0][*] and common[*][0] are initted to 0
		
		for (int l = 1; l <= left.length; ++l) {
			for (int r = 1; r <= right.length; ++r) {
				if (left[l-1] == right[r-1]){
					common[l][r] = common[l-1][r-1] + 1;
				}
				else {
					common[l][r] = Math.max(common[l-1][r], common[l][r-1]);
				}
			}
		}
		
		return common[left.length][right.length];
	}
	public static void findMaxPalindrome(char[] str) {
		// s[i,j] = palin | s[i] == s[j] && s[i+1,j-1] is palin
		boolean isPalin[][] = new boolean[str.length + 1][str.length + 1];
		int nCuts[][] = new int[str.length + 1][str.length + 1];
		for (int i = 0; i <= str.length; ++i){
			isPalin[i][i] = true;
		}
		
		for (int len = 2; len <= str.length; ++len) {
			// head
			for (int i = 0; i <= str.length - len; ++i) {
				// maxi + len -1 = str.len -1
				// j = i + len -1
				int j = i + len -1;
				if (len == 2) {
					isPalin[i][j] = str[i] == str[j];
				}
				else {
					isPalin[i][j] = isPalin[i+1][j-1] && str[i]==str[j];
				}
				
				// find min number of cuts
				if(!isPalin[i][j]){
					nCuts[i][j] = Integer.MAX_VALUE;
					for (int k = i; k < j; ++k) {
						nCuts[i][j] = Math.min(nCuts[i][j], nCuts[i][k] + nCuts[k+1][j] + 1);
					}
				}
			}
		}
	}


	public static void countDecodingDP(int[]digits)
	{
		int n = digits.length;
	    int count[] = new int[n]; 
	    count[0] = 1;
	 
	    for (int i = 1; i < n; i++)
	    {
	        // if this is a stand-alone digit
	        if (digits[i] > 0) {
	        	// then you have at least as
	        	// many possible count as [i-1]
	            count[i] = count[i-1];
	        }
	        
	        int num = digits[i-1] * 10 + digits[i];
	        if (num < 27 && num > 0) {
	        	// include [i-2] or 1 if i ==1
	        	if (i == 1) {
	        		++count[i];
	        	}
	        	else {
	        		count[i] += count[i-2];
	        	}
	        }
	 
	    }
	    System.out.println("COUNT ONLY: " + count[n - 1]);
	}
	
	public static void encodingOpt(int[] digits) {
		// s[i]: s[i-1], s[i] OR [i-2], [i-1, 1]
		List<List<Integer>>[]lookup = new List[digits.length + 1];
		
		lookup[0] = Arrays.asList(Arrays.asList(digits[0]));
		for (int i = 1; i < digits.length; ++i) {
			// no need for k because
			// we know a max length is 2
			//if (di)
			List<List<Integer>> combined = null;
			
			// if the digit standing alone is legit
			if (digits[i] > 0 ) {
				// then the total possible at this position
				// is s[i-1] + d[i]
				List<List<Integer>> left = lookup[i-1];
				// append [i] to each of these
				combined = new LinkedList<>();
				for (List<Integer> c : left) {
					List<Integer> n = new LinkedList<>(c);
					n.add(digits[i]);
					
					combined.add(n);
				}
			}
			
			int num = digits[i-1] * 10 + digits[i];
			
			if (num < 27 && num > 0) {
				if (combined == null) {
					combined = new LinkedList<>();
				}
				// [i-2] concat [i-1-i]
				if (i > 1) {
					List<List<Integer>> left = lookup[i-2];
					// append [i-1_i] to each of these?
					
					for (List<Integer> c : left) {
						List<Integer> n = new LinkedList<>(c);
						n.add(num);
						
						combined.add(n);
					}
				}
				else {
					combined.add(Arrays.asList(num));
				}
				
			
			}
			lookup[i] = combined;
			 
		}
		
		System.out.println("opt calc: " + lookup[digits.length - 1].size() );
		for (List<Integer> l : lookup[digits.length - 1]){
			System.out.println(l);
		}
		//System.out.println("opt calc: " + lookup[0][digits.length - 1]);
	}
	
	public static String isValid(Set<String> dict, char[] word, int i, int len) {
		String wrd = new String(word, i, len);
		if (dict.contains(wrd)) {
			return wrd;
		}
		else {
			return null;
		}
	}

	public static void longestIncreasingSubseq(int[] seq) {
		// l[i] = l[i-1] + s[i] if s[i] > s[i-1]
		int[] pred = new int[seq.length];
		int[] lis = new int[seq.length];
		
		for (int i = 0; i < pred.length; ++i) {
			pred[i] = -1;
			lis[i] = 1;
		}
		
		
		int maxLen = 0;
		int maxTail = -1;
		for (int i = 1; i < seq.length; ++i) {
			for (int k = 0; k < i; ++k) {
				if (seq[i] > seq[k] && lis[i] < lis[k] + 1) {
					lis[i] = lis[k] + 1;
					pred[i] = k;
					
					if (lis[i] > maxLen) {
						maxLen = lis[i];
						maxTail = i;
					}
				}
			}
		}
		
		System.out.println("LIS length: " + maxLen);
		for (int i = maxTail; i >= 0; ) {
			System.out.print(seq[i] + " ");
			i = pred[i];
		}
	}
	public static void parseMeanings(Set<String> dict, char[] text) {
		List<String>[] lookup = new List[text.length];
		for (int i = 0; i < text.length; ++i) {
			String word = null;
			for (int k = 0; k < i; ++k) {
				// if both sides make legit words
				List<String> pre = lookup[k];
				if (pre != null && (word = isValid(dict, text, k + 1, i - k )) != null) {
					if (lookup[i] == null) {
						lookup[i] = new LinkedList<>();
					}
					
					// combine left and this word to make the new stuff
					for (String prefix : pre) {
						lookup[i].add(prefix + " " + word);
					}
				}
			}
			
			// if the whole [0,i] is also a legit word?
			if ((word = isValid(dict, text, 0, i +1)) != null) {
				if (lookup[i] == null) {
					lookup[i] = new LinkedList<>();
				}
				lookup[i].add(word);
			}
		}
		
		System.out.println(lookup[text.length - 1]);
	}
	public static void encoding(int[] digits) {
		// S[i,j] = s[i,k] + s[k+1,j] + (1 iff i = j+1)
		
		// find all s[i,j] such that it's s[i,j] <=26
		Set<List<Integer>>[][] lookup = new Set[digits.length][digits.length];
		
		for (int i = 0; i < digits.length; ++i) {
			assert digits[i] <= 26;
			
			LinkedList<Integer> list = null;
			if (digits[i] > 0) {
				lookup[i][i] = new HashSet<>();
				list = new LinkedList<>();
				list.add(digits[i]);
				lookup[i][i].add(list);
			}
			//
			int num;
			if (i < digits.length - 1 && (num = digits[i] * 10 + digits[i+1]) <= 26 && num > 0) {
				lookup[i][i+1] = new HashSet<>();
				list = new LinkedList<>();
				list.add(num);
				lookup[i][i+1].add(list);
				
				if (digits[i] > 0 && digits[i+1] > 0)
				lookup[i][i+1].add(Arrays.asList(digits[i], digits[i+1]));
			}
		}
		
		for (int i = 2; i < digits.length; ++i) {
			for (int k = 0; k < i; ++k) {
				Set<List<Integer>> left = lookup[0][k];
				Set<List<Integer>> right = lookup[k+1][i];
				
				if (left != null && right != null) {
					Set<List<Integer>> total = lookup[0][i];
					if (total == null) {
						lookup[0][i] = total = new HashSet<>();
					}
					else {
						//System.out.println("i = " + i + ", k = " + k + ", total:[0->i] = " + total);
					}
					// combine left and right (keeping order
					for (List<Integer> leftList : left) {
						for (List<Integer> rightList : right) {
							List<Integer> combined = new LinkedList<>();
							combined.addAll(leftList);
							combined.addAll(rightList);
							if(!total.add(combined)) {
//								System.out.println("left: " + left);
//								System.out.println("right: " + right);
//								System.out.println();
							}
						}
					}
				}
			}
		}
		
		if (lookup[0][digits.length - 1] == null) {
			System.out.println("NO encoding found");
		}
		else {
			System.out.println("total possible encodings: " + lookup[0][digits.length - 1].size());
			for (List<Integer> l : lookup[0][digits.length - 1]) {
				System.out.println(l);
			}
		}
	}
	
	
	public static void encodingMax2(int[] digits) {
		// S[i,j] = s[i,k] + s[k+1,j] + (1 iff i = j+1)
		
		// find all s[i,j] such that it's s[i,j] <=26
		List<List<Integer>>[][] lookup = new List[digits.length][digits.length];
		
		for (int i = 0; i < digits.length; ++i) {
			assert digits[i] <= 26;
			lookup[i][i] = new LinkedList<>();
			LinkedList<Integer> list = new LinkedList<>();
			list.add(digits[i]);
			lookup[i][i].add(list);
			//
			int num;
			if (i < digits.length - 1 && (num = digits[i] * 10 + digits[i+1]) <= 26) {
				lookup[i][i+1] = new LinkedList<>();
				list = new LinkedList<>();
				list.add(num);
				lookup[i][i+1].add(list);
				lookup[i][i+1].add(Arrays.asList(digits[i], digits[i+1]));
			}
		}
		
		for (int i = 2; i < digits.length; ++i) {
			// dont need to run k
			// onlythe last two digits matter
			// k = i - 1
			// 
			
			
			for (int k = i - 1; k < i; ++k) {
				List<List<Integer>> left = lookup[0][k];
				List<List<Integer>> right = lookup[k+1][i];
				
				if (left != null && right != null) {
					List<List<Integer>> total = lookup[0][i];
					if (total == null) {
						lookup[0][i] = total = new LinkedList<>();
					}
					else {
						//System.out.println("i = " + i + ", k = " + k + ", total:[0->i] = " + total);
					}
					// combine left and right (keeping order
					for (List<Integer> leftList : left) {
						for (List<Integer> rightList : right) {
							List<Integer> combined = new LinkedList<>();
							combined.addAll(leftList);
							combined.addAll(rightList);
							if(!total.add(combined)) {
//								System.out.println("left: " + left);
//								System.out.println("right: " + right);
//								System.out.println();
							}
						}
					}
				}
			}
		}
		
		System.out.println("total possible encodings: " + lookup[0][digits.length - 1].size());
		for (List<Integer> l : lookup[0][digits.length - 1]) {
			System.out.println(l);
		}
	}
	
	
	
	public static void findAllInterpretations(char[] text, Set<String> dict) {
		// s[i,j] = s[i,k] + s[k+1, j] + s[]
		Set<String>[][] lookup = new Set[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < text.length; ++j) {
				// look up left and right
				Set<String> left = null;
				Set<String> right = null;
				
				Set<String> total = null;
				
				for (int k = i; k < j; ++k) {
					left = lookup[i][k];
					right = lookup[k+1][j];
					if (left != null && right != null) {
						if (total == null) {
							total = new HashSet<>();
						}
						
						// merge left and right
						for (String l : left) {
							for (String r : right) {
								total.add(l + " " + r);
							}
						}
					}
				}
				
				// check if the whole string [i,j] is also legit
				String st = isInDict(dict, text, i, j);
				if (st != null) {
					if (total == null) {
						total = new HashSet<>();
					}
					total.add(st);
				}
				
				lookup[i][j] = total;
			}
		}
	}
	
	static void findAllInterOpt(char[] text, Set<String> dict) {
		// find all st[i,j] that's a word first
		Set<String>[][] lookup = new Set[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = i; j < text.length; ++j) {
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
	
	static String isInDict(Set<String> dict, char[] text, int i, int j){
		StringBuilder ret = new StringBuilder();
		for (int n = i; n < j; ++n) {
			ret.append(text[n]);
		}
		String st = ret.toString();
		return dict.contains(st) ? st : null;
	}
	
	public static void longestCommonSeq(int[] a, int[] b) {
		int[][] cLen = new int[a.length +1 ][b.length + 1];

		class Pair { 
			int x; int y;
			Pair p;
			int val;
			Pair(int x, int y, int val) {
				this.x = x;
				this.y = y;
				this.val = val;
			}
			
			public String toString() { return "val = " + val;}
		};
		Pair[][] pred = new Pair[a.length+1][b.length+1];
		
//		System.out.println("a length: "+ a.length);
//		System.out.println("b length: "+ b.length);
		for (int i = 1; i <= a.length; ++i) {
			for (int j = 1; j <= b.length; ++j) {
				if (a[i - 1] == b[j - 1]){
					cLen[i][j] = cLen[i-1][j-1] + 1;
					pred[i][j] = new Pair(i-1,j-1, a[i-1]);
					pred[i][j].p = pred[i-1][j-1];
				}
				else {
					if (cLen[i-1][j] > cLen[i][j-1]) {
						cLen[i][j] =cLen[i-1][j];
						pred[i][j] = pred[i-1][j];
					}
					else {
						cLen[i][j] =cLen[i][j-1];
						pred[i][j] = pred[i][j-1];
					}
				}
			}
		}
		
		System.out.println("max common seq len: " + cLen[a.length][b.length]);
		
		Pair p = pred[a.length][b.length];
		while (p != null) {
			System.out.print(p.val + ", ");
			p = p.p;
		}
	}
}

