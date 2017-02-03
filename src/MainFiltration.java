import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainFiltration {
	final static List<String> COMPUTED_FILTRATIONS= Arrays.asList("klein-bottle.txt","mobius-strip.txt","projective-plane.txt","tore.txt","ball3.txt","sphere3.txt");
	final static String FILTRATION_PATH = "/tests/filtrations/";
	
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {			
			//createFiltrations();
			printBarcodes();
			System.exit(0);
		}
			
		System.out.println(readFiltration(args[0]));
	}
	
	static Vector<Simplex> readFiltration (String filename) throws FileNotFoundException {
		Vector<Simplex> F = new Vector<Simplex>();
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		sc.hasNext(Pattern.compile("//"));
		sc.nextLine();
		while (sc.hasNext())
			F.add(new Simplex(sc));
		sc.close();
				
		return F;
	}
	
	static void printBarcodes() throws FileNotFoundException{
		for(String s:COMPUTED_FILTRATIONS)
			printBarcode(s);	
	}
	
	static void printBarcode(String filtrationName) throws FileNotFoundException{
		printBarcode(filtrationName,false);
	}
		
	static void printBarcode(String filtrationName,boolean verbose) throws FileNotFoundException{
		Filtration F = new Filtration(MainFiltration.readFiltration(System.getProperty("user.dir")+FILTRATION_PATH+filtrationName));
		
		// Creating the matrix representation 
		SparseRepresentation M = new SparseRepresentation(F);
		if(verbose){
			System.out.println("Original matrix representation:\n");
			System.out.println(M);
		}
		
		// Reducing the matrix (and get the lowest indices for each simplex)
		ArrayList<Integer> lowIndices = M.reduce();
		if(verbose){
			System.out.println("Matrix after reduction:\n");
			System.out.println(M);
		}

		// From the lowest indices, create the intervals
		TreeSet<Interval> intervals =  Interval.toIntervals(lowIndices);
		
		// Translate back the intervals using the filtration (which was indexed by the attribute order)
		String result = F.getIntervals(intervals);
		if(verbose){
			System.out.println("Barcode:\n");
			System.out.println(result);
		}

		PrintWriter out = new PrintWriter(System.getProperty("user.dir")+FILTRATION_PATH+"results/"+filtrationName);
		out.println(result);
		out.close();	
	}
	
	static void createFiltrations() throws FileNotFoundException {
		for(String s:COMPUTED_FILTRATIONS)
			createFiltrationFromFile("simplices/"+s,s);
	}
	
	static void createSphere(){
		
	}
	
	static void createFiltrationFromFile(String fromFilename,String toFilename) throws FileNotFoundException {
		String dirf = System.getProperty("user.dir")+"/tests/filtrations/";
		Scanner sc = new Scanner(new File(dirf+fromFilename));
		sc.useLocale(Locale.US);
		int i = 0;
		String s = "",nextLine;
		float f = 1.0f;
		while (sc.hasNext()){
			nextLine = sc.nextLine();		
			if(nextLine.startsWith("//")){
				f+= 1.0f;
			}else{
				s += Float.toString(f)+" "+nextLine+"\n";
			}
		}
		sc.close();
		
		PrintWriter out = new PrintWriter(dirf+toFilename);
		out.println(s);
		out.close();
	}

}
