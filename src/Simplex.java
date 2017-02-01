import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;

public class Simplex {
	public float val;
	public int dim;
	TreeSet<Integer> vert;
	
	public int order;

	Simplex(Scanner sc){
		val = sc.nextFloat();
		dim = sc.nextInt();
		vert = new TreeSet<Integer>();
		for (int i=0; i<=dim; i++)
			vert.add(sc.nextInt());
	}
	
	public ArrayList<TreeSet<Integer>> derivate(){
		ArrayList<TreeSet<Integer>> list = new ArrayList<TreeSet<Integer>>(dim+1);
		for(Integer intVert : this.vert){
			TreeSet<Integer> t = (TreeSet<Integer>) this.vert.clone();
			t.remove(intVert);
			list.add(t);
		}
		return list;
	}

	public String toString(){
		return "{val="+val+"; dim="+dim+"; order="+order+"; "+vert+"}\n";
	}
	public String toStringSimple(){
		return "\u03C3"+order+" => "+vert+"\n";
	}

	public void updateVal(float val){
		this.val = this.val + val;
	}
	
	public int compare(Simplex j){
		int compval = Float.compare(val, j.val);
		if(compval!=0){
			return compval;
		}
		else{
			return(Integer.compare(dim, j.dim));
		}
	}
	
	public boolean equals(TreeSet<Integer> t){
		return(vert.equals(t));
	}
}
