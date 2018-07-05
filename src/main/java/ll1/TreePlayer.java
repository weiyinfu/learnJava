 
import java.util.ArrayList;

class StringNode {
	String str;
	ArrayList<StringNode> sons;

	StringNode(String s) {
		str = new String(s);
		sons = new ArrayList<StringNode>();
	}
}

public class TreePlayer {
	StringNode root;

	public TreePlayer(StringNode root) {
		this.root = root;
	}

	void fileStyle() {
		fileStyle("", root);
	}

	public void fileStyle(String prefix, StringNode node) {
		System.out.println(prefix + node.str);
		if (node.sons.size() == 0)
			return;
		String temp = prefix.replace('┣', '┃');
		temp=temp.replace("━","  ");
		temp=temp.replace("┗", "  "); //一定要注意，一个这个符号是两个空格
		int i;
		for (i = 0; i < node.sons.size() - 1; i++) {
			fileStyle(temp + "┣━", node.sons.get(i));
		}
		fileStyle(temp + "┗━", node.sons.get(i));
	}
}
