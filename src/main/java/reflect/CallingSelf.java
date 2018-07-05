package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CallingSelf {
	public static void main(String[] args) {
		Class<?> x = Class.class;
		cout("modifiers:=========");
		cout("" + x.getModifiers());
		cout("fields:=========");
		for (Field i : x.getFields())
			cout("" + i);
		cout("declared fields:=========");
		for (Field i : x.getDeclaredFields())
			cout("" + i);
		cout("methods:========");
		for (Method i : x.getMethods())
			cout(i.toString());
		cout("declared methods:========");
		for (Method i : x.getDeclaredMethods())
			cout(i.toString());
		cout("constructors:=========");
		for(Constructor<?>i:x.getConstructors())
			cout(i.toString());
		cout("declared constructors:=========");
		for(Constructor<?>i:x.getDeclaredConstructors())
			cout(i.toString());
	}

	static void cout(String s) {
		System.out.println(s);
	}
}
