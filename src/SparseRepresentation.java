import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Vector;

public class SparseRepresentation {
	ArrayList<TreeSet<Integer>> matrix;
	
	SparseRepresentation(Vector<Simplex> vectorSimplex){
		this(new Filtration(vectorSimplex));
	}
	SparseRepresentation(Filtration F){
		
		matrix = new ArrayList<TreeSet<Integer>>();
		
		// Sorting the filtration F
		F.sort();
		// Set the order of each simplex in the filtration
		F.computeOrder();
		
		matrix = F.getSparseRepresentation();
	}
	
	public ArrayList<TreeSet<Integer>> transpose(){
		ArrayList<TreeSet<Integer>> tmatrix = new ArrayList<TreeSet<Integer>>(matrix.size());
		for(int i=0;i<matrix.size();i++){
			tmatrix.add(new TreeSet<Integer>());
		}
		int i = 0;
		for(TreeSet<Integer> orderList:matrix){
			for(Integer order:orderList){
				tmatrix.get(order).add(i);
			}
			i++;
		}
		return tmatrix;
	
	}
	
	public ArrayList<Integer> reduce(){
		ArrayList<Integer> lowIndices = new ArrayList<Integer>();
		int j = 0,lowerIndex=-1;
		boolean loopAgain = true;
		for(TreeSet<Integer> listOrder: matrix){
			loopAgain = true;
			while(loopAgain==true){
				loopAgain=false;
				if(listOrder.size()==0){
					lowerIndex = -1;
					break;
				}
				lowerIndex = listOrder.last();
				for(int k = 0;k<=j-1;k++){
					if(lowIndices.get(k)==lowerIndex){
						SparseOperation.substract(listOrder, matrix.get(k));
						loopAgain=true;
						break;
					}
				}	
			}
			lowIndices.add(lowerIndex);
			j++;
		}
		return(lowIndices);
	}
	

	
	public String toString(){
		int size = matrix.size();
		String s = "   ";
		for(int j = 0;j<size;j++){
			s+=" "+Integer.toString(j);
		}
		s += "\n   ";
		for(int j = 0;j<size;j++){
			s+=" |";
		}
		s += "\n";
		ArrayList<TreeSet<Integer>> transpose = this.transpose();
		int j =0;
		for(TreeSet<Integer> list: transpose){
			s += Integer.toString(j)+" - ";
			for(int i = 0;i<size;i++){
				s+=list.contains(i)?"1":"0";
				s+=(i+1!=size)?" ":"\n";
			}
			j++;
		}
		
		return s;
	 }
	
	

}
