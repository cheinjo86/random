package first_trial;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Aggregation {

	static interface Window{
		void incr();
		void decr();
		void calculate();
	}
	
	static interface WindowFactory {
		Window newWindow();
	}
	private WindowFactory fact;
	private LinkedList<Window> openWindows;
	private int size;
	public Aggregation(WindowFactory fact, int size) {
		openWindows = new LinkedList<Window>();
		this.fact = fact;
		this.size = size;
	}
	
	public void emit(Window w) {}
	public void incr() {
		Window newWindow = fact.newWindow();
		openWindows.addLast(newWindow);
		for (Window w : openWindows) {
			w.incr();
		}
		
		if (openWindows.size() == size) {
			// emit and remove the oldest window
			emit(openWindows.getFirst());
		}
	}
	public static void main(String[] args) {
		
	}
}
