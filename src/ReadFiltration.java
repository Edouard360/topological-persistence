import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ReadFiltration {

	static Vector<Simplex> readFiltration (String filename) throws FileNotFoundException {
		Vector<Simplex> F = new Vector<Simplex>();
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		while (sc.hasNext())
			F.add(new Simplex(sc));
		sc.close();
				
		return F;
	}

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			
			
			Filtration F = new Filtration(ReadFiltration.readFiltration(System.getProperty("user.dir")+"/src/Test.txt"));
			
			// Creating the matrix representation 
			SparseRepresentation M = new SparseRepresentation(F);
			System.out.println("Original matrix representation:\n");
			System.out.println(M);
			
			// Reducing the matrix (and get the lowest indices for each simplex)
			ArrayList<Integer> lowIndices = M.reduce();
			System.out.println("Matrix after reduction:\n");
			System.out.println(M);
			
			// From the lowest indices, create the intervals
			TreeSet<Interval> intervals =  Interval.toIntervals(lowIndices);
			
			// Translate back the intervals using the filtration (which was indexed by the attribute order)
			String result = F.getIntervals(intervals);
			System.out.println("Barcode:\n");
			System.out.println(result);
		
			PrintWriter out = new PrintWriter("result.txt");
			out.println(result);
			out.close();
			System.exit(0);
		}
			
		System.out.println(readFiltration(args[0]));
	}
}
