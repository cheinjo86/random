package collected_g;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.event.ListSelectionEvent;

public class Test_U_second {

	public static void main(String[] args) {
		
	}
	
	static interface Movie {
		int getId();
		float getRating();
		void addSimilarMovie(Movie v);
		public List<Movie> getSimilarMovies();
	}
	
	public static List<Movie> findSimilar(Movie movie, int N) {
		// list of N highest rating in a network
		// traverse all nodes and trim
		
		// do BFS to flatten the network
		// then get the highest
		
		
		Map<Integer, Movie> flatten =  new HashMap<>();
		
		LinkedList<Movie> queue = new LinkedList<>();
		queue.add(movie);
		while (!queue.isEmpty()) {
			Movie cur = queue.removeFirst();
			
			// if visited, then skip
			if (!flatten.containsKey(cur.getId())) {
				for (Movie related : cur.getSimilarMovies()) {
					queue.addLast(related);
				}
				
				flatten.put(cur.getId(), cur);
			}
		}
		
		
		// get the highest rating only (But exclude the original, if it's in there)
		Movie[] sortedMovie = new Movie[flatten.size()];
		sortedMovie = flatten.values().toArray(sortedMovie);
		Arrays.sort(sortedMovie, new Comparator<Movie>(){

			@Override
			public int compare(Movie o1, Movie o2) {
				return (int)Math.ceil(o1.getRating() - o2.getRating());
			}
			
		});
		
		
		List<Movie> ret = new ArrayList<>(N);
		for (int i = sortedMovie.length -1; i >= 0; ) {
			if (ret.size() == N) {
				break;
			}
			else {
				if (sortedMovie[i].getId() != movie.getId()) {
					ret.add(sortedMovie[i]);
				}
				
				// skip
				--i;
			}
		}
		
		
		return ret;
	}
}
