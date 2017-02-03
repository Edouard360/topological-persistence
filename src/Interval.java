import java.util.ArrayList;
import java.util.TreeSet;

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
		return "[" + Integer.toString(begin) + ":" + Integer.toString(end) + "]";
	}

	public static TreeSet<Interval> toIntervals(ArrayList<Integer> lowIndices) {
		TreeSet<Interval> intervals = new TreeSet<Interval>();
		int j = 0;
		for (Integer i : lowIndices) {
			if (i != -1) {
				intervals.add(new Interval(i, j));
			}
			j++;
		}
		j = 0;
		for (Integer i : lowIndices) {
			if (i == -1) {
				intervals.add(new Interval(j, -1));
			}
			j++;
		}
		return intervals;
	}
}
