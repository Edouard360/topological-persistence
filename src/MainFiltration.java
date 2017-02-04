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
	final static List<String> GENERATED_FILTRATIONS= Arrays.asList("klein-bottle.txt","mobius-strip.txt","projective-plane.txt","tore.txt","ball3.txt","sphere3.txt");
	final static List<String> PROVIDED_FILTRATIONS= Arrays.asList("filtration_A.txt","filtration_B.txt","filtration_C.txt","filtration_D.txt");
	final static String GENERATED_FILTRATIONS_PATH = "/tests/generated-filtrations/";
	final static String PROVIDED_FILTRATIONS_PATH = "/tests/provided-filtrations/";
	
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {			
			//createFiltrations();
			printBarcodes();
			//runTimeTest();
			
			System.exit(0);
		}	
		System.out.println(readFiltration(args[0]));
	}
	
	static Vector<Simplex> readFiltration (String filename) throws FileNotFoundException {
		Vector<Simplex> F = new Vector<Simplex>();
		Scanner sc = new Scanner(new File(filename));
		sc.useLocale(Locale.US);
		while (sc.hasNext())
			F.add(new Simplex(sc));
		sc.close();
				
		return F;
	}
	
	static void printBarcodes() throws FileNotFoundException{
		for(String s:GENERATED_FILTRATIONS)
			printBarcode(s);	
	}
	
	static void printBarcode(String filtrationName) throws FileNotFoundException{
		printBarcode(filtrationName,GENERATED_FILTRATIONS_PATH,false,false);
	}
	
	static void runTimeTest() throws FileNotFoundException{
		for(String s:PROVIDED_FILTRATIONS)
			printBarcode(s,PROVIDED_FILTRATIONS_PATH,false,true);	
	}
		
	static void printBarcode(String filtrationName,String filtrationPath,boolean verbose,boolean runtime) throws FileNotFoundException{	
		long startTime = System.currentTimeMillis();
		Filtration F = new Filtration(MainFiltration.readFiltration(System.getProperty("user.dir")+filtrationPath+filtrationName));
		double fSize = (double) F.get().size();
		
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
		PrintWriter out = new PrintWriter(System.getProperty("user.dir")+filtrationPath+"results/barcodes/"+filtrationName);
		F.printIntervalsToFile(intervals,out);
		out.close();

		if(verbose){
			System.out.println("Barcode:\n");
		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		if(runtime){
			out = new PrintWriter(System.getProperty("user.dir")+filtrationPath+"results/runtime/"+filtrationName);
			out.print("Running time for "+filtrationName+": "+Long.toString(totalTime)+" ms.\n\n"
		+"Filtration of size: "+Double.toString(fSize)+"\n\n"
		+"Time / (size^3) = "+Double.toString(totalTime/Math.pow(fSize, 3))+" ms.");
			out.close();
		}
		
	}
	
	static void createFiltrations() throws FileNotFoundException {
		for(String s:GENERATED_FILTRATIONS)
			createFiltrationFromFile("simplices/"+s,s);
	}
	
	static void createFiltrationFromFile(String fromFilename,String toFilename) throws FileNotFoundException {
		String dirf = System.getProperty("user.dir")+"/tests/generated-filtrations/";
		Scanner sc = null;
		try{
			sc = new Scanner(new File(dirf+fromFilename));
		}catch(FileNotFoundException e){
			return;	
		}
		sc.useLocale(Locale.US);
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
