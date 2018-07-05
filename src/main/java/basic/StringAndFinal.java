package basic;

public class StringAndFinal {
	public static void main(String[] args) {
		final String finalStr = "haha";
		String constStr = "haha";
		String refStr = new String("haha");
		System.out.println((finalStr == constStr) + "  " + (finalStr == refStr)
				+ "  " + (constStr == refStr));//true false false
		System.out.println(finalStr.endsWith(constStr) + " "
				+ finalStr.endsWith(refStr));
		String finals = finalStr + "weidiao";
		String consts = constStr + "weidiao";
		String strSet = "hahaweidiao";
		System.out.println((finals == consts) + "  " + (finals == strSet)
				+ "  " + (consts == strSet));
		System.out.println((finals == consts.intern()) + "  " + (finals == strSet)
				+ "  " + (consts== strSet));
		consts=consts.intern();
		System.out.println((finals == consts) + "  " + (finals == strSet)
				+ "  " + (consts == strSet));
	}
}
