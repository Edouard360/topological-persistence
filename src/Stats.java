import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * The Stats class and the Statitic class.
 * 
 * Used for printing the runtime of our algorithm on the generated and provided
 * filtrations.
 * 
 * This class has only a static function to write the stats obtained at the end
 * of the printBarcode function in the MainFiltration class.
 * 
 */
class Statistic {
	NumberFormat formatter;
	public long time;
	public double average;
	public double n2;
	public double n3;

	Statistic(long time, double size) {
		this.formatter = new DecimalFormat("0.00E0");
		this.time = time;
		this.average = time / size;
		this.n2 = time / Math.pow(size, 2);
		this.n3 = time / Math.pow(size, 3);
	}

	public String toString() {
		return formatter.format(time) + "\t\t" + formatter.format(average) + "\t\t" + formatter.format(n2) + "\t\t"
				+ formatter.format(n3) + "\n";
	}
}

public class Stats {
	public static void printStats(PrintWriter detailedOutput, PrintWriter commonOutput, String filtrationName,
			double filtrationSize, long[] timeStamps) {
		/*
		 * This function outputs to the PrintWriter detailedOutput the full
		 * stats for the filtration filtationName of size filtrationSize with
		 * the appropriate timeStamps, meaning an array of long, of length 6. In
		 * the commonOutput, only interesting time features are printed so as to
		 * compare the running time of the algorithm only when it does create
		 * the matrix with sparse representation or when it reduces the matrix.
		 */

		Statistic total = new Statistic(timeStamps[5] - timeStamps[0], filtrationSize);
		Statistic readSort = new Statistic(timeStamps[1] - timeStamps[0], filtrationSize);
		Statistic sparseRep = new Statistic(timeStamps[2] - timeStamps[1], filtrationSize);
		Statistic reduce = new Statistic(timeStamps[3] - timeStamps[2], filtrationSize);
		Statistic identification = new Statistic(timeStamps[4] - timeStamps[3], filtrationSize);
		Statistic output = new Statistic(timeStamps[5] - timeStamps[4], filtrationSize);

		String title = "Statistics for " + filtrationName + " of size " + Integer.toString((int)filtrationSize) + "\n";
		String header = "\t\t\tTime (ms)\tAverage (ms)\tT/(n^2)\t\tT/(n^3)\n";
		detailedOutput.print(title + header + "Full Algorithm\t\t" + total + "Reading/Sorting\t\t" + readSort
				+ "Sparse Representation\t" + sparseRep + "Reducing Matrix\t\t" + reduce + "Identifying intervals\t"
				+ identification + "Outputing Result\t" + output);
		
		if(commonOutput!=null)
			commonOutput.println(title + header + "Sparse Representation\t" + sparseRep + "Reducing Matrix\t\t" + reduce);
	}

}
