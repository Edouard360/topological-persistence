import java.util.ArrayList;
import java.util.TreeSet;

public class SparseOperation {
	static void substract(TreeSet<Integer> arrayList,TreeSet<Integer> toSubstract){
		for(Integer i :toSubstract){
			if(!arrayList.remove(i)){arrayList.add(i);};
		}
		return ;
	}
}
