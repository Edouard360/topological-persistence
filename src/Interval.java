import java.util.ArrayList;
import java.util.TreeSet;

/**
 * The Interval class.
 * 
 * An interval has a beginning and an end. We implement the Comparable interface
 * using ONLY the begin attribute so that we can extract, from the lowest
 * indices of our reduced matrix, the interval for our filtration. NB: we use
 * the integer -1 to represent inf
 */

public class Interval implements Comparable<Interval> {
	public int begin, end;

	Interval(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public int compareTo(Interval i) {
		return Integer.compare(begin, i.begin);
	}

	public String toString() {
		/*
		 * Only for debugging purpose
		 */
		return "[" + Integer.toString(begin) + ":" + Integer.toString(end) + "]";
	}

	public static TreeSet<Interval> toIntervals(ArrayList<Integer> lowestIndices) {
		/*
		 * This static method is used after matrix reduction, taking the
		 * lowestIndices in each column to deduce the barcode intervals of the
		 * filtration.
		 */
		TreeSet<Interval> intervals = new TreeSet<Interval>();
		int j = 0;
		for (Integer i : lowestIndices) {
			if (i != -1) {
				intervals.add(new Interval(i, j));
			}
			j++;
		}
		j = 0;
		for (Integer i : lowestIndices) {
			if (i == -1) {
				intervals.add(new Interval(j, -1)); // -1 corresponds to inf
				// NB: In case a match has already been found for j in the
				// previous loop, it won't be added in the TreeSet since
				// intervals are compared only with their begin attribute.
			}
			j++;
		}
		return intervals;
	}
}
