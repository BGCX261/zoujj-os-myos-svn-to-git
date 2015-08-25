package zjj.os.test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * zoujj
 * @author user
 *
 */
public class UtilListTest {

	public static void main(String[] args) {
	
		List a = new ArrayList();
		List b = new ArrayList();
		a.add("111");
		a.add("222");
		b.add("111");
		b.add("222");
		System.out.println(compare(a, b));
	}
	
	public static <T extends Comparable<T>> boolean compare(List<T> a,List<T> b){
		if(a.size() !=b.size()){
			return false;
		}
		Collections.sort(a);
		Collections.sort(b);
		for(int i=0;i<a.size();i++){
			if(!a.get(i).equals(b.get(i))){
				return false;
			}
		}
		return true;
	}
}
