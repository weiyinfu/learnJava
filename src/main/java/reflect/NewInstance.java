package reflect;

import java.lang.reflect.Constructor;

/*to use newInstance,must have a constructor without arguments*/
public class NewInstance {
	int x = 3;
	public NewInstance(int x) {
		this.x=x;
	}
	public NewInstance() {
	}
	public static void main(String[] args) {
		Class<?> x = NewInstance.class; 
		try {
			NewInstance y = (NewInstance) x.newInstance();
			System.out.println(y.x);
			Constructor<?>[]constructors=x.getConstructors();
			for(Constructor<?> i :constructors){
				System.out.println(i);
			}
			y=(NewInstance) constructors[0].newInstance(1);
			System.out.println(y.x);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
