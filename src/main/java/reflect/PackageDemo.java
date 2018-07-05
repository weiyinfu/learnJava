package reflect;

public class PackageDemo {
	public static void main(String[] args) {
		Package[] pack = Package.getPackages();
		for (int i = 0; i < pack.length; i++) {
			System.out.println("" + pack[i]);
		}
	}
}
