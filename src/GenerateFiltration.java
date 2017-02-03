import java.io.*;

public class GenerateFiltration {
	static String combinationRec(int arr[], int n, int r, int index, int data[], int i) {
		String s = "";

		if (index == r) {
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

	static String computeCombination(int arr[], int n, int r) {
		int data[] = new int[r];
		return combinationRec(arr, n, r, 0, data, 0);
	}

	static String generateSphere(int d) {
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
		String s = generateSphere(d);
		s += Float.toString((float) d + 2) + " " + Integer.toString(d + 1);
		for (int i = 0; i < d + 2; i++) {
			s += " " + Integer.toString(i + 1);
		}
		return s;
	}

	public static void main(String[] args) throws FileNotFoundException {

		String ballFiltration = generateBall(3);
		String sphereFiltration = generateSphere(3);
		
		String dirf = System.getProperty("user.dir")+"/tests/filtrations/";
		PrintWriter out = new PrintWriter(dirf+"ball3.txt");
		out.println(ballFiltration);
		out.close();
		out = new PrintWriter(dirf+"sphere3.txt");
		out.println(sphereFiltration);
		out.close();

	}

}
