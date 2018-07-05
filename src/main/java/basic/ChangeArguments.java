package basic;

public class ChangeArguments {
	static int f(int...x){
		int s=0;
		for (int i : x) {
			s+=i;
		}
		return s;
	}
	public static void main(String[]args) {
		System.out.println(f(1,2,3));
	}
}
