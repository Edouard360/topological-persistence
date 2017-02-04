import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Vector;

public class Filtration {
	Vector<Simplex> F;
	
	Filtration(Vector<Simplex> F){
		this.F = F;
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
		String s ="";
		for(Interval interval:t){
			Simplex begin = F.get(interval.begin);
			s+=Integer.toString(begin.dim)+" "+Float.toString(begin.val)+" ";
			if(interval.end!= -1){
				s+=Float.toString(F.get(interval.end).val)+" ";
			}else{
				s+="inf";
			}
			s+="\n";
		}
		s = s.substring(0, s.length()-2); // To remove the last "\n"
		return s;
	}
	public void sort(){
		Collections.sort(F, new Comparator<Simplex>() {
			public int compare(Simplex si, Simplex sj) {
				return si.compare(sj);
			}
			});
	}
	
	public void computeOrder(){
		int j = 0;
		for(Simplex s:F){
			s.order = j++;
		}
	}
	
	public void updateVal(){	
		int i = 1,j = 0;
		int size = F.size();
		while(i<size){
			i = i*10;
		}
		for(Simplex s:F){
			s.updateVal(j++/(float)i);
		}
	}
	
	public TreeSet<Integer> getOrderList(ArrayList<TreeSet<Integer>> listSimplices){
		TreeSet<Integer> orderList = new TreeSet<Integer>();
		for(TreeSet<Integer> t : listSimplices){
			for(Simplex s_tmp : F){
				if(s_tmp.equals(t)){
					orderList.add(s_tmp.order);
				}
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
