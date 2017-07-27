package collected_g;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * U's test 
 * @author vynguye
 *
 */
public class Test {

	public static void main(String[] args) {
		// 
		// last score = score of prev row 
		// 
	
		//  0 rep missing prev
		// 
		// 5 -2 4 z x 9 + + 
		// N = 8
		// 
		// 5
		// 3
		// 7
		//z:  3
		// 3 + -2 *2 = -1
		// 9 
		// 
		
		/**
		 * 5 -2 4 z x 9 +
		 *         symbol   |   Total       |  series
		 *         5        |     5         |         5
		 *         -2       |3              |   5 -2
		 *         4        | 7              | 5 -2 4
		 *         z        | 7 -4 = 3        |  5 -2 
		 *         x        | 10 + -4 = 6    |  5 -2
		 *         9        |   6 + 9  14     |   5 -2 9
		 *         +      |     14 + -2 + 9   |   5 -2 9
		 *         +       |    14 -2 + 9 + 9 -2  
		 *                          10 + 28 
		 * 
		 * 
		 */
		// block marked with int ==> total score, added
		//               X      ==> last score doubled, added
		//               +     ===> last two scores added
		//              Z      ==> Last score is removed
		
		String[] in = {"5", "-2", "4", "z", "x", "9", "+", "+"};
		
		System.out.println(compute(in));
		
	}
	
	public static int compute(String[] ar) {
		LinkedList<Integer> series = new LinkedList<>();
		
		
		int total = 0;
		for (String st : ar) {
			System.out.print(st + "   | ");
			boolean isNumber = false;
			switch(st.trim().toLowerCase().charAt(0)) {
			case 'x': // last socre doubled, added
				if (!series.isEmpty()) {
					Integer last = series.removeLast() ;
					total += last * 2;
					series.addLast(last);
					//series.addLast(total);
				}
				
				break;
			case '+': // last two scores added
				Integer last = series.isEmpty() ? null : series.removeLast();
				Integer secondLast = series.isEmpty() ? null : series.removeLast();
				
				if (secondLast != null) {
					total += secondLast;
					series.addLast(secondLast);
				}
				if (last != null) {
					total += last;
					series.addLast(last);
				}
				
				
				break;
			case 'z': // last score is removed
				if (!series.isEmpty()) {
					Integer num = series.removeLast();
					total -= num;
					
				}
				break;
			default: isNumber = true;
			
			}
			
			if (isNumber) {
				Integer i = Integer.parseInt(st);
				series.addLast(i);
				total += i;
			}
			
			System.out.println(total + " |   " + series);
		}
		
		return total;
	}
	
	
	public static interface Movie {
		public int getId();
		public void addSimilarMovie();
		public float getRating();
		public List<Movie> getSimilarMovies();
	}
	
	
	public static Set<Movie> getHighest(Movie root, int N) {
		// set of visited movies
		Set<Integer> visited = new HashSet<>();
		// queue to do BFS
		LinkedList<Movie> q = new LinkedList<>();
		// set of output movies
		TreeSet<Movie> out = new TreeSet<>(new Comparator<Movie>() {
			@Override
			public int compare(Movie o1, Movie o2) {
				int eq = Float.compare(o1.getRating(), o2.getRating());
				if (eq == 0) {
					return Integer.compare(o1.getId(), o2.getId());
				}
				else return eq;
			}
		});
		// set of output movies by ID
		Map<Integer, Movie> outById = new HashMap<>();
		
		// start the traversal by the root node
		q.add(root);
		
		while (!q.isEmpty()) {
			final Movie cur = q.removeFirst();
			
			// if haven't visited
			if (!visited.contains(cur.getId())) {
				
				// wehther to put this movie into the output
				// (skip the original )
				if (cur.getId() != root.getId()) {
					
					// output not full
					if (outById.size() < N) {
						out.add(cur);
						outById.put(cur.getId(), cur);
					}
					// else have to purge item off if needed
					else {
						Movie lowest = out.first();
						if (lowest.getRating() < cur.getRating()
								// if current is NOT already in the output
								&& !outById.containsKey(cur.getId())) {
							out.remove(lowest);
							outById.remove(lowest.getId());
							
							out.add(cur);
							outById.put(cur.getId(), cur);
						}
					}
				}
				
				// bfs traversal code
				// add neighbors
				for (Movie child : cur.getSimilarMovies()) {
					q.addLast(child);
				}
				
				//marked this ask visited
				visited.add(cur.getId());
			}
		}
		
		//Set<OU>
		
		return new HashSet<>(outById.values());
	}
	
	
	
}
