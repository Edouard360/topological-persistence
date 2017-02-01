import java.io.File;
import java.io.FileNotFoundException;
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
			System.out.println("Syntax: java ReadFiltration <filename>");
			Filtration F = new Filtration(ReadFiltration.readFiltration(System.getProperty("user.dir")+"/src/Test.txt"));
			SparseRepresentation M = new SparseRepresentation(F);

			System.exit(0);
		}
			
		System.out.println(readFiltration(args[0]));
	}
}
