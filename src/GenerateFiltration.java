import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The GenerateFiltration class.
 * 
 * This class has only static methods, with a main method to generate
 * filtrations for the ball or the sphere. The computeCombination function uses
 * the combinationRec function the return all the possible combinations of r
 * elements among n total elements in an array of integer.
 * 
 * computeCombination is used in the generateSphere function, since the
 * simplices for the d-sphere are all the r-combination of vertices for r in
 * [1,d+1]. generateBall calls generateSphere but adds the last simplex needed,
 * of size d+2.
 * 
 * As for the filtration timestamps, for each simplex, its timestamp is its
 * order, which in our conditions, ensures that the filtration is simplicial.
 */

public class GenerateFiltration {
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * This main writes out in the /tests/generated-filtrations/ folder, the
		 * filtrations for the 4-sphere and the 4-ball. You can loop if you want
		 * all the d-sphere for d<=10
		 */

		String ballFiltration = generateBall(4);
		String sphereFiltration = generateSphere(4);

		String filtrationDirectory = System.getProperty("user.dir") + "/tests/generated-filtrations/";
		PrintWriter out = new PrintWriter(filtrationDirectory + "ball4.txt");
		out.println(ballFiltration);
		out.close();
		out = new PrintWriter(filtrationDirectory + "sphere4.txt");
		out.println(sphereFiltration);
		out.close();

	}

	static String generateSphere(int d) {
		/*
		 * Generate the sphere filtration in a string format. Initializes with
		 * d+2 vertices ([1],[2],...,[d+2]). Calls the computeCombination
		 * function for every d in [0,d+1], since the simplices for the d-sphere
		 * are all the r-combination of vertices for r in [1,d+1].
		 */
		int arr[] = new int[d + 2];
		String s = "";
		for (int i = 0; i < d + 2; i++) {
			arr[i] = i + 1;
		}
		for (int i = 1; i < d + 2; i++) {
			s += computeCombination(arr, arr.length, i);
		}
		return s;
	}

	static String generateBall(int d) {
		/*
		 * Generate the ball filtration in a string format. Calls generateSphere
		 * and simply adds the last simplex.
		 */
		String s = generateSphere(d);
		s += Float.toString((float) d + 2) + " " + Integer.toString(d + 1);
		for (int i = 0; i < d + 2; i++) {
			s += " " + Integer.toString(i + 1);
		}
		return s;
	}

	static String computeCombination(int arr[], int n, int r) {
		/*
		 * Return in a string format all the possible combinations of r elements
		 * among n total elements in an array of integer.
		 */
		int data[] = new int[r];
		return combinationRec(arr, n, r, 0, data, 0);
	}

	static String combinationRec(int arr[], int n, int r, int index, int data[], int i) {
		/*
		 * Recursive combination function, that is called by computeCombination
		 */
		String s = "";

		if (index == r) {
			// Float.toString((float) r) is the filtration timestamp.
			// Any simplex of order r appears before any simplex of order r1>r
			// Therefore, it is a simplicial filtration.
			s += Float.toString((float) r) + " " + Integer.toString(r - 1) + " ";
			for (int j = 0; j < r; j++) {
				s += data[j] + " ";
			}
			s += "\n";
			return s;
		}

		if (i >= n)
			return s;

		data[index] = arr[i];
		s += combinationRec(arr, n, r, index + 1, data, i + 1);

		s += combinationRec(arr, n, r, index, data, i + 1);
		return s;
	}

}
