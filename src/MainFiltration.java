import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The MainFiltration class. (originally the ReadFiltration class)
 * 
 * It has a static main function.
 * 
 * The filtration object has a getSparseRepresentation method, which creates the
 * sparse matrix column by column, looking at the derivative of each simplex.
 * 
 */

public class MainFiltration {
	final static List<String> GENERATED_FILTRATIONS = Arrays.asList("klein-bottle.txt", "mobius-strip.txt",
			"projective-plane.txt", "tore.txt", "sphere4.txt", "ball4.txt");
	final static List<String> PROVIDED_FILTRATIONS = Arrays.asList("filtration_A.txt", "filtration_B.txt",
			"filtration_C.txt", "filtration_D.txt");
	final static String GENERATED_FILTRATIONS_PATH = "/tests/generated-filtrations/";
	final static String PROVIDED_FILTRATIONS_PATH = "/tests/provided-filtrations/";

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {

			// First uncomment and run this to create the filtrations.
			// createFiltrations();

			// Then go in the GenerateFiltration.java and run the main to
			// generate the sphere and the ball.

			// Now that generated filtrations are ready, uncomment and run:
			// printBarcodes(GENERATED_FILTRATIONS,GENERATED_FILTRATIONS_PATH);

			// Now for the provided filtrations (make sure to put them in the
			// folder corresponding to PROVIDED_FILTRATIONS_PATH), uncomment and
			// run:
			// printBarcodes(PROVIDED_FILTRATIONS,PROVIDED_FILTRATIONS_PATH);

			System.exit(0);
		}
		System.out.println(readFiltration(args[0]));
	}

	static void printBarcodes(List<String> filtrationList, String filtrationPath) throws FileNotFoundException {
		for (String s : filtrationList)
			printBarcode(s, filtrationPath);
	}

	static void printBarcode(String filtrationName, String filtrationPath) throws FileNotFoundException {
		/*
		 * The printBarcode static function.
		 * 
		 * Loads the filtration, computes its matrix representation, reduces it,
		 * gets the corresponding lowestIndices array, deduces the intervals
		 * from this array, and, finally outputs the result to a file of the
		 * same name in the folder /results/barcodes/
		 * 
		 * Also computes statistics for the running time and output them in the
		 * folder: /results/runtime/
		 */

		System.out.println("Reading filtration at " + filtrationPath + filtrationName);

		long startTime = System.currentTimeMillis();
		Filtration F = new Filtration(
				MainFiltration.readFiltration(System.getProperty("user.dir") + filtrationPath + filtrationName));

		System.out.println("Getting sparse representation");
		SparseRepresentation M = new SparseRepresentation(F);

		System.out.println("Reducing the matrix");
		ArrayList<Integer> lowestIndices = M.reduce();

		System.out.println("Identifying intervals");
		TreeSet<Interval> intervals = Interval.toIntervals(lowestIndices);

		PrintWriter out = new PrintWriter(
				System.getProperty("user.dir") + filtrationPath + "results/barcodes/" + filtrationName);
		System.out.println("Translating back intervals and printing out to " + filtrationPath + "results/barcodes/"
				+ filtrationName);
		F.printIntervalsToFile(intervals, out); // Translation operation is
												// here.
		out.close();

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		double fSize = (double) F.getSize();
		System.out.println("Writting stats at " + filtrationPath + "results/runtime/" + filtrationName + "\n");
		out = new PrintWriter(System.getProperty("user.dir") + filtrationPath + "results/runtime/" + filtrationName);
		out.print("Running time for " + filtrationName + ": " + Long.toString(totalTime) + " ms.\n\n"
				+ "Filtration of size: " + Double.toString(fSize) + "\n\n" + "Time / (size^3) = "
				+ Double.toString(totalTime / Math.pow(fSize, 3)) + " ms.");
		out.close();
	}

	static Vector<Simplex> readFiltration(String filename) throws FileNotFoundException {
		/*
		 * The original readFiltration function
		 */
		Vector<Simplex> F = new Vector<Simplex>();
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		while (sc.hasNext())
			F.add(new Simplex(sc));
		sc.close();
		return F;
	}

	static void createFiltrations() throws FileNotFoundException {
		/*
		 * The createFiltrations function, that calls createFiltrationFromFile
		 * (see below) for each of the manually generated filtrations in
		 * GENERATED_FILTRATIONS (see below for details)
		 */
		for (String s : GENERATED_FILTRATIONS)
			createFiltrationFromFile("simplices/" + s, s);
	}

	static void createFiltrationFromFile(String fromFilename, String toFilename) throws FileNotFoundException {
		/*
		 * The following simplicial complex: klein-bottle, mobius-strip,
		 * projective-place and tore have been manually generated, in the:
		 * /tests/generated-filtrations/simplices/ folder.
		 * 
		 * To create the correponding filtrations in the folder:
		 * /tests/generated-filtrations/ the function createFiltrationFromFile
		 * needs to be called.
		 */
		String filtrationDirectory = System.getProperty("user.dir") + GENERATED_FILTRATIONS_PATH;
		System.out.println("Reading simplicial complex from " + GENERATED_FILTRATIONS_PATH + fromFilename);
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filtrationDirectory + fromFilename));
		} catch (FileNotFoundException e) {
			// "sphere4.txt" and "ball4.txt" would launch a file not found
			// exception since they are also generated filtrations, but don't
			// appear in the:
			// /tests/generated-filtrations/simplices/ folder.
			// Indeed are computed from scratch with the GenerateFilration
			// class.
			System.out.println("The corresponding filtration should be computed in the GenerateFiltration class\n");
			return;
		}
		sc.useLocale(Locale.US);
		String s = "", nextLine;
		float f = 1.0f;
		while (sc.hasNext()) {
			nextLine = sc.nextLine();
			if (nextLine.startsWith("//")) {
				f += 1.0f;
			} else {
				s += Float.toString(f) + " " + nextLine + "\n";
			}
		}
		sc.close();

		System.out
				.println("Writting the corresponding filtration at " + GENERATED_FILTRATIONS_PATH + toFilename + "\n");
		PrintWriter out = new PrintWriter(filtrationDirectory + toFilename);
		out.println(s);
		out.close();
	}

}
