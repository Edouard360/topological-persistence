import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

public class Filtration {
	Vector<Simplex> F;
	HashMap<TreeSet<Integer>,Simplex> H;
	HashMap<Integer,Simplex> orderToSimplex;

	Filtration(Vector<Simplex> F){
		this.F = F;
		H = new HashMap<TreeSet<Integer>,Simplex>();
		orderToSimplex = new HashMap<Integer,Simplex>();
	}
	
	public Vector<Simplex> get(){
		return F;
	}
	
	public String toString(){
		String s = "";
		for(Simplex simplex:F){
			s+=simplex.toString();
		}
		return s;
	}
	
	public String toStringSimple(){
		String s = "";
		for(Simplex simplex:F){
			s+=simplex.toStringSimple();
		}
		return s;
	}
	
	public String getIntervals(TreeSet<Interval> t){
		/*
		 * For debugging purpose, or simple (=short) filtrations.
		 * Indeed, operating on a long string is heavy, and therefore
		 * the function scales poorly
		 */
		String s ="";
		for(Interval interval:t){
			Simplex begin = orderToSimplex.get(interval.begin);
			s+=Integer.toString(begin.dim)+" "+Float.toString(begin.val)+" ";
			if(interval.end!= -1){
				s+=Float.toString(orderToSimplex.get(interval.end).val);
			}else{
				s+="inf";
			}
			s+="\n";
		}
		s = s.substring(0, s.length()-1); // To remove the last "\n"
		return s;
	}
	
	public void printIntervalsToFile(TreeSet<Interval> t,PrintWriter out){
		/*
		 * Prints the intervals to the specified output.
		 * Probably many I/Os here, but this method
		 * scales far better than the one above.
		 */
		for(Interval interval:t){
			Simplex begin = orderToSimplex.get(interval.begin);
			out.print(Integer.toString(begin.dim)+" "+Float.toString(begin.val)+" ");
			if(interval.end!= -1){
				out.print(Float.toString(orderToSimplex.get(interval.end).val));
			}else{
				out.print("inf");
			}
			out.println("");
		}
	}
	
	public void sort(){
		Collections.sort(F, new Comparator<Simplex>() {
			public int compare(Simplex si, Simplex sj) {
				return si.compare(sj);
			}
			});
	}
	
	public void computeOrder(){
		/*
		 * Insted of updating the val attribute (like in the course),
		 * we simply compute a order attribute which accounts for the
		 * order of the filtration
		 */
		int j = 0;
		for(Simplex s:F){
			s.order = j++;
			H.put(s.getTreeSet(), s);
			orderToSimplex.put(s.order, s);
		}
	}
	
	public TreeSet<Integer> getOrderList(ArrayList<TreeSet<Integer>> listSimplices){
		/*
		 * Instead of a for loop in O(n), on the Vector<Simplex> F, using a hashMap
		 * O(1) to find the derivatives...
		 */
		TreeSet<Integer> orderList = new TreeSet<Integer>();
		for(TreeSet<Integer> t : listSimplices){
			if(!t.isEmpty()){
				orderList.add(H.get(t).order);
			}			
		}
		return orderList;
	}
	
	public ArrayList<TreeSet<Integer>> getSparseRepresentation(){
		ArrayList<TreeSet<Integer>> sparseMatrix = new ArrayList<TreeSet<Integer>>();
		for(Simplex s : F){
			ArrayList<TreeSet<Integer>> listSimplices = s.derivate();
			sparseMatrix.add(this.getOrderList(listSimplices));
		}
		return sparseMatrix;
	}
}
