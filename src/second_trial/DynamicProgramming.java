package second_trial;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DynamicProgramming {
	public static interface Dict {
		/**
		 * return the word [i,j] if it's in the dict
		 * or null
		 * @return
		 */
		String buildWord(char[] text, int i, int j);
		boolean containWord(String word);
		Set<Character> getAlphabet();
	}
	
	public static List<String> findAllInterpretationOpt(char[] text, Dict dict) {
		List<String>[][] meaning = new List[text.length][text.length];
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = i; j < text.length; ++j) {
				String word = dict.buildWord(text, i, j);
				if (word != null) {
					meaning[i][j] = new LinkedList<>();
					meaning[i][j].add(word);
				}
			}
		}
		
		for (int i = 0; i < text.length; ++i) {
			for (int j = 0; j < i; ++j) {
				// if both sides have meanings
				if (meaning[0][j] != null && meaning[j+1][i] != null) {
					if (meaning[0][i] == null) {
						meaning[0][i] = new LinkedList<>();
					}
					// combine the twi
					for (String left : meaning[0][j]) {
						for (String right : meaning[j+1][i]) {
							meaning[0][i].add(left + " " + right);
						}
					}
				}
			}
		}
		
		return meaning[0][text.length - 1];
	}
	
	public static void longestCommonSeq(int[] first, int[] second) {
		int[][]seqLen = new int[first.length+1][second.length+1];
		// f[i] == f[j]  | common seq = common[i-1][j-1] + 1
		// other wise, it's the max of [i][j-1
		class CommonCell {
			// position in first seq
			final int i;
			// position in second seq
			final int j;
			final CommonCell pred;
			CommonCell(int i, int j, CommonCell pred) {
				this.i = i;
				this.j = j;
				this.pred = pred;
			}
		}
		
		CommonCell[][] seq = new CommonCell[first.length + 1][second.length+1];
		
		for (int i = 1; i <= first.length; ++i) {
			for (int j = 1; j <= second.length; ++j) {
				if (first[i-1] == second[j-1]) {
					seqLen[i][j] = seqLen[i-1][j-1] + 1;
					seq[i][j] = new CommonCell(i-1, j-1, seq[i-1][j-1]);
				}
				else {
					if (seqLen[i-1][j] >= seqLen[i][j-1]) {
						seqLen[i][j] = seqLen[i-1][j];
						seq[i][j] = seq[i-1][j];
					}
					else {
						seqLen[i][j] = seqLen[i][j-1];
						seq[i][j] = seq[i][j-1];
					}
				}
			}
		}
		
		CommonCell p = seq[first.length][second.length];
		while (p != null) {
			System.out.print(first[p.i] + " ");
			p = p.pred;
		}
	}
	
	
	public static void cutPalinDromeOpt(char[] text) {
		boolean[][] isPalin = new boolean[text.length][text.length];
		List<Integer>[] nCuts = new List[text.length];
		
		for (int i = 0; i < text.length; ++i) {
			isPalin[i][i] = true;
			if (i < text.length - 1) {
				isPalin[i][i+1] = text[i] == text[i+1];
			}
		}
		
		for (int len = 3; len <= text.length; ++len) {
			int maxI = text.length - len;
			for (int i = 0; i <= maxI; ++i) {
				final int j = i + len - 1;
				isPalin[i][j] = isPalin[i+1][j-1] && text[i] == text[j];
			}
		}
		
		for (int i = 0; i < text.length; ++i) {
			if (!isPalin[0][i]) {
				// find j
				// idx where min cut can be archived
				int minJ = -1;
				int minCuts = Integer.MAX_VALUE;
				for (int j = 0; j < i; ++j) {
					if (isPalin[j+1][i]
							&& (nCuts[j] == null 
							        || nCuts[j].size() + 1 < minCuts)) {
						minJ = j;
						minCuts = 1+ (nCuts[j] == null ? 0 : nCuts[j].size());
					}
				}
				
				if (minJ == -1 || nCuts[i] != null) {
					throw new IllegalSelectorException();
				}
				
				nCuts[i] = new ArrayList<>(minCuts);
				if (nCuts[minJ] != null) {
					nCuts[i].addAll(nCuts[minJ]);
				}
				nCuts[i].add(minJ);
			}
		}
		
		System.out.println("cut at : " + nCuts[text.length - 1]);
	}

	public static int lis(int[] nums) {
		// longest in creasing subseq
		// s[0, i] = s[0,i-1]  || s[0,i] and i > max
		int prev = Integer.MIN_VALUE;
		int s[] = new int[nums.length];
		
		for (int i = 0; i < nums.length; ++i) {
			s[i] = 1;
		}
		int maxLen = 0;
		for (int i = 1; i < nums.length; ++i) {
			int longest = -1;
			// s[i] = 0; (initial val)
			for (int j = 0; j < i; ++j) {
				if ( nums[i] > nums[j] &&  s[j] + 1> s[i]) {
					s[i] = s[j] + 1;
					if (s[i] > maxLen) {
						maxLen = s[i];
					}
				}
			}
		}
		
		return maxLen;
	}
	
	public static int lisOpt (int[] nums) {
		int[] tails = new int[nums.length];
		int t = 0;

		for (int num : nums) {	
			// actually, only checked the first  (shortest)
			// because we'll creat new with lengh 1
			if (t == 0 || num < tails[0]) {
				// start a new seq at 1
				tails[0] = num;
				if (t==0) ++t;
			}
			// if we were to append to the longest,
			// num 'll have to be largest tail of largest
			// so only need to check the largest
			//else if (num > maxTail) {
			else if (num > tails[t-1]) {
				// new seq
				tails[t] = num;
				++t;
			}
			else {
				
				// append to the num to the longest list ending with the f
				// so find the smallest that is > num to be replaced
				// replace the next list
				tails[findCeilIdx(tails, 0, t-1, num)] = num;
			}
				
		}
		
		return t;
	}
	
	public static int listOptWithTree(int[] nums) {
		TreeSet<Integer> tails = new TreeSet<>();
		tails.add(nums[0]);
		
		for (int i = 1; i < nums.length; ++i) {
			if (nums[i] < tails.first()) {
				// replace the smallest
				tails.pollFirst();
				tails.add(nums[i]);
			}
			else if (nums[i] > tails.last()) {
				tails.add(nums[i]);
			}
			else {
				// replace the smallest > num[i]
				tails.remove(tails.ceiling(nums[i]));
				tails.add(nums[i]);
			}
		}
		return tails.size();
	}
	
	// smallest that is > target in the [low,high] range (inclusive)
	public static int findCeilIdx(int[] nums, int low, int high, int target) {
		while (low < high) {
			int mid = (low+high)/2;
			if (nums[mid] <= target) {
				low = mid+1;
			}
			else {
				high = mid;
			}
		}
		
		return high;
	}
	
	
}
