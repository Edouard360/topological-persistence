import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * The Simplex class.
 * 
 * Implements Comparable so that we can easily sort them.
 * It has a method to get the derivative of the simplex.
 * 
 */

public class Simplex implements Comparable<Simplex> {
	public float val;
	public int dim;
	public int order; // We added this attribute to rank the simplices
	TreeSet<Integer> vert;

	Simplex(Scanner sc) {
		val = sc.nextFloat();
		dim = sc.nextInt();
		vert = new TreeSet<Integer>();
		for (int i = 0; i <= dim; i++)
			vert.add(sc.nextInt());
	}

	public int compareTo(Simplex j) {
		int compval = Float.compare(val, j.val);
		if (compval != 0) {
			return compval;
		} else {
			return (Integer.compare(dim, j.dim));
		}
	}

	public ArrayList<TreeSet<Integer>> derivate() {
		/*
		 * The derivate method. Returns the derivative of a vertex in an array
		 * containing the corresponding simplices, since a simplex is uniquely
		 * represented by a TreeSet<Integer>.
		 */
		ArrayList<TreeSet<Integer>> list = new ArrayList<TreeSet<Integer>>(dim + 1);
		for (Integer intVert : this.vert) {
			@SuppressWarnings("unchecked")
			TreeSet<Integer> t = (TreeSet<Integer>) this.vert.clone();
			t.remove(intVert);
			list.add(t);
		}
		return list;
	}

	public TreeSet<Integer> getTreeSet() {
		/*
		 * Prevent illegal access to private attribute
		 */
		return this.vert;
	}

	public String toString() {
		/*
		 * Prints a simplex. Used only for debugging purpose.
		 */
		return "{val=" + val + "; dim=" + dim + "; order=" + order + "; " + vert + "}\n";
	}

	public String toStringSimple() {
		/*
		 * Prints a simplex more nicely and simply. Used only for debugging
		 * purpose.
		 */
		return "\u03C3" + order + " => " + vert + "\n";
	}

}
