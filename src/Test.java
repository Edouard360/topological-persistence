import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeSet<Integer> arrayList = new TreeSet<Integer>(Arrays.asList(1,2,3));
		TreeSet<Integer> toSubstract = new TreeSet<Integer>(Arrays.asList(1,3));
		System.out.println(arrayList);
		System.out.println(toSubstract);
		SparseOperation.substract(arrayList, toSubstract);
		System.out.println(arrayList);
	}

}
