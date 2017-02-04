import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * The SparseRepresentation class.
 * 
 * This class has two constructor so as to be lenient with regards to
 * initialization: Initialize either with a vector of simplices or with a
 * Filtration object.
 * 
 * This class is mainly responsible of the reduction of the matrix via the
 * "reduce" method.
 * 
 * Then it is possible to "plot" the matrix using the toString method.
 */

public class SparseRepresentation {
	ArrayList<TreeSet<Integer>> matrix;

	SparseRepresentation(Vector<Simplex> vectorSimplex) {
		/*
		 * Calls the constructor below with the filtration correponding to
		 * vectorSimplex.
		 */
		this(new Filtration(vectorSimplex));
	}

	SparseRepresentation(Filtration F) {
		/*
		 * Calls the constructor below with the filtration correponding to
		 * vectorSimplex.
		 */
		matrix = new ArrayList<TreeSet<Integer>>();
		matrix = F.getSparseRepresentation();
	}

	public ArrayList<Integer> reduce() {
		/*
		 * The reduce method corresponding to the reduction algorithm. A column
		 * is represented by a TreeSet. Once we have our matrix, we use the
		 * reduction algorithm, keeping track of the lowest indices, and calling
		 * SparseOperation.substract for the operations on columns. NB: the
		 * lowMapToColumns hashMap enables fast access to the TreeSet in O(1).
		 */
		ArrayList<Integer> lowestIndices = new ArrayList<Integer>();
		HashMap<Integer, TreeSet<Integer>> lowMapToColumns = new HashMap<Integer, TreeSet<Integer>>();
		int j = 0, lowerIndex = -1;

		System.out.println(matrix.size());
		for (TreeSet<Integer> listOrder : matrix) {
			while (true) {
				if (listOrder.size() == 0) {
					lowerIndex = -1;
					break;
				}
				lowerIndex = listOrder.last();
				TreeSet<Integer> toSubstract = lowMapToColumns.get(lowerIndex);
				if (toSubstract != null) {
					SparseOperation.substract(listOrder, toSubstract);
				} else {
					break;
				}
			}
			lowestIndices.add(lowerIndex);
			if (lowerIndex != -1) {
				lowMapToColumns.put(lowerIndex, matrix.get(j));
			}

			j++;
		}
		return (lowestIndices);
	}

	public ArrayList<TreeSet<Integer>> transpose() {
		/*
		 * The transpose method. Does what it suggest. However, it is only used
		 * for debugging purpose. Keep in mind that the chosen representation of
		 * our original matrix is column by column and not line by line. This
		 * transpose the matrix and enables an easy plot of it.
		 */
		ArrayList<TreeSet<Integer>> tmatrix = new ArrayList<TreeSet<Integer>>(matrix.size());
		for (int i = 0; i < matrix.size(); i++) {
			tmatrix.add(new TreeSet<Integer>());
		}
		int i = 0;
		for (TreeSet<Integer> orderList : matrix) {
			for (Integer order : orderList) {
				tmatrix.get(order).add(i);
			}
			i++;
		}
		return tmatrix;

	}

	public String toString() {
		/*
		 * Only used for debugging purpose. Provides a nice print of the object.
		 */
		int size = matrix.size();
		String s = "   ";
		for (int j = 0; j < size; j++) {
			s += " " + Integer.toString(j);
		}
		s += "\n   ";
		for (int j = 0; j < size; j++) {
			s += " |";
		}
		s += "\n";
		ArrayList<TreeSet<Integer>> transpose = this.transpose();
		int j = 0;
		for (TreeSet<Integer> list : transpose) {
			s += Integer.toString(j) + " - ";
			for (int i = 0; i < size; i++) {
				s += list.contains(i) ? "1" : "0";
				s += (i + 1 != size) ? " " : "\n";
			}
			j++;
		}

		return s;
	}
}
