package first_trial.second;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import first_trial.GraphProblems.WordNode;

/**
 * TODO: prim's
 *
 */
public class GraphProblems {

	public static List<WordNode> shortestPath(String start, String end,
			                                  Set<String> dict, char[] alphabet) {
		Map<String,WordNode> graph = buildGraph(start, dict, alphabet);
		for (WordNode n : graph.values()) {
			n.visited = false;
			;
		}
		System.out.println(graph);
		if (!graph.containsKey(end)) return null;
		
		// do bfs
		Set<WordNode>unsettled = new HashSet<WordNode>();
		WordNode startNode = graph.get(start);
		
		startNode.dist = 0;
		unsettled.add(startNode);
		
		while (!unsettled.isEmpty()) {
			WordNode cur = getShortest(unsettled);
			if (!cur.visited) {
				// update children
				for (WordNode kid : cur.kids) {
					if (kid.visited) continue;
					int newDist = cur.dist + 1;
					if (newDist < kid.dist) {
						kid.dist = newDist;
						kid.pred = cur;
					}
					
					unsettled.add(kid);
					// stop early
//					if(node.word.equals(end)) {
//						break;
//					}
				}
				cur.visited = true;
			}
		}
		
		LinkedList<WordNode> path = new LinkedList<>();
		WordNode endNode = graph.get(end);
		while(endNode != null) {
			
			endNode.includeKids = false;
			path.addFirst(endNode);
			endNode = endNode.pred;
		}
		
		return path;
	}
	
	static WordNode getShortest(Set<WordNode> set ) {
		Iterator<WordNode> iter = set.iterator();
		WordNode min = iter.next();
		while(iter.hasNext()) {
			WordNode cur = iter.next();
			if (cur.dist < min.dist) {
				min = cur;
			}
		}
		set.remove(min);
		return min;
	}
	public static Map<String, WordNode> buildGraph(String target,
			                                       Set<String> dict,
			                                       char[] alphabet) {
		assert dict.contains(target);
		
		Map<String,WordNode> graph = new HashMap<>(dict.size());
		WordNode start = new WordNode();
		start.word = target;
		graph.put(target, start);
		
		buildGraph(start, dict, graph, alphabet);
		//buildGraph(start, target.toCharArray(), 0, dict, graph, alphabet);
		return graph;
	}
//	public static void buildGraph(WordNode parent,
//			                      Set<String> dict,
//			                      Map<String, WordNode> graph,
//			                      char[] alphabet) {
//		if (parent)
//	}
	
	private static void buildGraph(WordNode parent, Set<String> dict, Map<String,WordNode> graph, char[] alphabet) {
		if (parent.visited) return;
		
		// add children by modifying the word at each idx
		char []text = parent.word.toCharArray();
		for (int i = 0; i < text.length; ++i) {
			addChildren(parent, text, i, dict, graph, alphabet);
		}
		parent.visited = true;
		
		// repeat with children
		for (WordNode kid : parent.kids) {
			buildGraph(kid, dict, graph, alphabet);
		}
	}
	
	private static void addChildren(WordNode parent, char[] text, int i, Set<String> dict, Map<String, WordNode> graph, char[] alphabet) {
		final char orig = text[i];
		for (char ch : alphabet) {
			if (ch == orig) continue;
			text[i] = ch;
			String st = new String(text);
			if (dict.contains(st)) {
				WordNode kid = graph.get(st);
				if (kid == null) {
					graph.put(st, kid = new WordNode());
					kid.word = st;
				}
				parent.kids.add(kid);
			}
		}
		text[i] = orig;
	}
	private static void buildGraph(WordNode parent,
								   char[] text, int i,
			                       Set<String> dict,
			                       Map<String, WordNode> graph,
			                       char[] alphabet) {
		if (parent.visited || i >= text.length) return;
		final char original = text[i];
		parent.visited = true;
		for (char ch : alphabet) {
			if (ch != original) {
				text[i] = ch;
				String st = new String(text);
				if (dict.contains(st)) {
					WordNode kid = graph.get(st);
					if (kid == null) {
						graph.put(st, kid = new WordNode());
						kid.word = st;
					}
					
					parent.kids.add(kid);
					buildGraph(kid, text, i + 1, dict, graph, alphabet);
				}
			}
			
		}
		text[i]=original;
	}
}
