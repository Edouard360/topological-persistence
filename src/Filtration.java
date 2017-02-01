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
