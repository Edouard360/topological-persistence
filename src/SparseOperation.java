import java.util.TreeSet;

/**
 * The SparseOperation class.
 * 
 * Only one static method used in SparseRepresentation for the substration of
 * the columns in the "reduce" function (that corresponds to the reduction
 * algorithm).
 */

public class SparseOperation {
	static void substract(TreeSet<Integer> arrayList, TreeSet<Integer> toSubstract) {
		for (Integer i : toSubstract) {
			if (!arrayList.remove(i)) {
				arrayList.add(i);
			}
		}
		return;
	}
}
