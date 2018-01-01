package second_trial.test;

import org.junit.Test;

import  static second_trial.Sets.*;
public class SetsTest {

	@Test
	public void testPermutation() {
		//permutation("abcd");
		//0 1 0 0 9
	//	permute("aaab");
		//
		permutation("aaabc");
		permuteWithDups("aaabc");
	}
}
/*
 * 
 *  [c .]   | aba
    
i = 1 [c a ]
i = 2 [c b ]
i = 3 [c a ]
*/