import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * The Filtration class.
 * 
 * This class has a single constructor, which take a vector of simplices, sort
 * it (see Simplex class), and then set an order attribute to the simplices
 * according to that sort. At the same time, it creates two HashMap highly
 * useful for optimization.
 * 
 * The filtration object has a getSparseRepresentation method, which creates the
 * sparse matrix column by column, looking at the derivative of each simplex.
 * 
 */

public class Filtration {
	Vector<Simplex> simplices;
	HashMap<TreeSet<Integer>, Simplex> treeSetToSimplex;
	HashMap<Integer, Simplex> orderToSimplex;

	Filtration(Vector<Simplex> simplices) {
		this.simplices = simplices;
		treeSetToSimplex = new HashMap<TreeSet<Integer>, Simplex>();
		orderToSimplex = new HashMap<Integer, Simplex>();

		// Sorting the simplices vector
		Collections.sort(simplices, new Comparator<Simplex>() {
			public int compare(Simplex si, Simplex sj) {
				return si.compareTo(sj);
			}
		});

		// Set the attribute named `order` for each simplex, and fill the two
		// hashMap
		int j = 0;
		for (Simplex s : simplices) {
			// NB: Insted of updating the val attribute (like in the course), we
			// simply compute a order attribute
			// which accounts for th order of the filtration.
			s.order = j++;
			treeSetToSimplex.put(s.getTreeSet(), s);
			orderToSimplex.put(s.order, s);
		}
	}

	public ArrayList<TreeSet<Integer>> getSparseRepresentation() {
		/*
		 * The getSparseRepresentation method. Returns a sparseMatrix
		 * representation of our filtration. For each simplex (remember that
		 * they are already ordered), it computes the derivative of that simplex
		 * and uses the getOrderList method to return the correponding indexes.
		 */
		ArrayList<TreeSet<Integer>> sparseMatrix = new ArrayList<TreeSet<Integer>>();
		for (Simplex s : simplices) {
			ArrayList<TreeSet<Integer>> listSimplices = s.derivate();
			sparseMatrix.add(this.getOrderList(listSimplices));
		}
		return sparseMatrix;
	}

	public TreeSet<Integer> getOrderList(ArrayList<TreeSet<Integer>> listSimplices) {
		/*
		 * The getOrderList method. Instead of a for loop in O(n), on the
		 * Vector<Simplex> F, using a hashMap O(1) to find the Simplex
		 * corresponding to the TreeSet...
		 */
		TreeSet<Integer> orderList = new TreeSet<Integer>();
		for (TreeSet<Integer> t : listSimplices) {
			if (!t.isEmpty()) {
				orderList.add(treeSetToSimplex.get(t).order);
			}
		}
		return orderList;
	}

	public void printIntervalsToFile(TreeSet<Interval> t, PrintWriter out) {
		/*
		 * The printIntervalsToFile method, that prints the intervals to the
		 * specified output. It is the translation back to the real floats of
		 * the filtration ! The orderToSimplex HashMap guarantees O(1) to find
		 * the right Simplex. NB: Though there are many I/Os here, this method
		 * scales pretty well.
		 */
		for (Interval interval : t) {
			Simplex begin = orderToSimplex.get(interval.begin);
			out.print(Integer.toString(begin.dim) + " " + Float.toString(begin.val) + " ");
			if (interval.end != -1) {
				out.print(Float.toString(orderToSimplex.get(interval.end).val));
			} else {
				out.print("inf");
			}
			out.println("");
		}
	}

	public int getSize() {
		/*
		 * Used only for debugging purpose.
		 */
		return simplices.size();
	}

	public String toString() {
		/*
		 * Prints all the simplices in the filtration object. Used only for
		 * debugging purpose.
		 */
		String s = "";
		for (Simplex simplex : simplices) {
			s += simplex.toString();
		}
		return s;
	}

	public String toStringSimple() {
		/*
		 * Prints a bit more nicely all the simplices in the filtration object.
		 * Used only for debugging purpose.
		 */
		String s = "";
		for (Simplex simplex : simplices) {
			s += simplex.toStringSimple();
		}
		return s;
	}

	public String getIntervals(TreeSet<Interval> t) {
		/*
		 * Translate back to the real intervals, and returns the string. For
		 * debugging purpose, or simple (=short) filtrations. Indeed, operating
		 * on a long string is heavy, and therefore the function scales poorly
		 */
		String s = "";
		for (Interval interval : t) {
			Simplex begin = orderToSimplex.get(interval.begin);
			s += Integer.toString(begin.dim) + " " + Float.toString(begin.val) + " ";
			if (interval.end != -1) {
				s += Float.toString(orderToSimplex.get(interval.end).val);
			} else {
				s += "inf";
			}
			s += "\n";
		}
		s = s.substring(0, s.length() - 1); // To remove the last "\n"
		return s;
	}
}