package past.test;

import static org.junit.Assert.*;

import org.junit.Test;

import past.First;
import past.First.Node;

public class FirstTest {

	@Test
	public void trianglePrint() {
		/**
		 *   
		 *   
		 *                             a
		 *                         /     \
		 *                        b         c
		 *                      /  \         \
		 *                     e    d         l 
		 *                   /       \       / \
		 *                  f          g    m   n
		 *                 / \        /  \       \
		 *                h  i       j    k       o
		 *                   
		 *                      
		 *                      
		 *                      a
		 *                     / \
		 *                    b   c
		 *                    
		 *                    a
		 *                   /
		 *                  b
		 *   
		 *                    a
		 *                     \ b
		 *                     
		 *                     
		 *                     
		 *                     
         *                             a
		 *                              \
		 *                                 c
		 *                                  \
		 *                                   l 
		 *                                  / \
		 *                                 g     n
		 *                               /  \     \
		 *                              j    k     o
		 *                             /
		 *                            p 
		 *   
		 */
		// full tree
		{
			Node h = new Node("h");
			Node i = new Node("i");
			Node f = new Node("f");
			f.left = h;
			f.right = i;
			Node e = new Node("e");
			e.left = f;
			Node j = new Node("j");
			Node k = new Node("k");
			Node g = new Node("g");
			g.left = j;
			g.right = k;
			Node d = new Node("d");
			d.right = g;
			Node b = new Node("b");
			b.left = e;
			b.right = d;
			Node m = new Node("m");
			Node o = new Node("o");
			Node n = new Node("n");
			n.right = o;
			Node l = new Node("l");
			l.left = m;
			l.right = n;
			Node c = new Node("c");
			c.right = l;
			Node a = new Node("a");
			a.left = b;
			a.right = c;
			
			doTestTriangle("full tree", a);
		}
		
		// empty right
		{
			Node b = new Node("b");
			Node a = new Node("a");
			a.right = b;
			doTestTriangle("empty right", a);
		}
		
		// empty left
		{
			Node b = new Node("b");
			Node a = new Node("a");
			a.left = b;
			doTestTriangle("empty left", a);
		}
		
		// no left tree
		{
			Node p = new Node("p");
			Node j = new Node("j");
			j.left = p;
			Node k = new Node("k");
			Node g = new Node("g");
			g.left = j;
			g.right = k;
			Node o = new Node("o");
			Node n = new Node("n");
			n.right = o;
			Node l = new Node("l");
			l.left = g;
			l.right = n;
			Node c = new Node("c");
			c.right = l;
			Node a = new Node("a");
			a.right = c;
			
			doTestTriangle("no left tree", a);;
			
			
		}
	}
	
	private static void doTestTriangle(String name, Node root) {
		System.out.println(name);
		First.printTriangleTree(root);
		System.out.println();
	}
	
	@Test
	public void testFill() {
		int[][] m = {
				{1, 2, 1, 1, 1, 1},
				{1, 1, 2, 2, 1, 1},
				{1, 2, 2, 1, 1, 1},
				{1, 2, 2, 2, 2, 1},
				{1, 2, 2, 2, 2, 2},
				{1, 2, 2, 2, 2, 2},
				{1, 1, 2, 2, 1, 1},
		};
		
		doTestFill(m, 1, 2, 3);
	}
	
	static void print(int[][] m) {
		for (int[] row : m) {
			for (int i : row) {
				System.out.print("[ " + i + " ]");
			}
			System.out.println();
		}
	}
	static void doTestFill(int[][] m,int x, int y, int newColor) {
		System.out.println("original: ");
		print(m);
		System.out.println("started: " + x + " " + y);
		First.fillSquare(m, x, y, newColor);
		
		System.out.println("adjusted:");
		print(m);
	}

}
