
import java.util.*;
import java.io.*;

abstract class Symbol {
	String str;
	int num;

	Symbol(String s, int n) {
		this.str = s;
		num = n;
	}
}

class Terminal extends Symbol {
	Terminal(String s, int n) {
		super(s, n);
	}
}

class NonTerminal extends Symbol {
	NonTerminal(String s, int n) {
		super(s, n);
	}

	boolean nullable = false;
	Production[] productions;
	Edge[] edges;
	Production[] ifSee; 
	Node follow;
	Node begin;// 此处begin表示全部的次非终结符可以开始的东西
}

class Edge {
	ArrayList<Terminal> terminals = new ArrayList<Terminal>();
	ArrayList<Node> nodes = new ArrayList<Node>();
}

class Node extends Edge {
	int num;

	Node(int num) {
		this.num = num;
	}
}

class Production extends ArrayList<Symbol> {
	boolean isNull() {
		if (get(0).num != 1)// terminal[0]=#;terminal[1]=@表示空
			return false;
		else if (get(0) instanceof Terminal)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		String ans = "";
		for (Symbol s : this)
			ans += s.str + " ";
		return ans;
	}
}

public class LL1 {
	public static void main(String[] args) throws Exception {
		 new LL1();
	}

	Terminal[] t;
	NonTerminal[] n;
	Terminal[] sentence;

	LL1() throws Exception {
		input();
		debugInput();
		nullClosure();// 求空闭包
		debugNullClosure();
		initNonTerminal();// 对非终结符中的数据进行初始化
		initEdges();
		initBegin();// 初始化开始
		initFollow();// 初始化跟随
		init();
		debugIfSee();
		parse();
		parseTree();
	}

	private void nullClosure() {
		boolean changed = true;
		for (NonTerminal non : n)
			for (Production p : non.productions)
				if (p.isNull())
					non.nullable = true;
		while (changed) {
			changed=false;
			for (NonTerminal non : n)
				if (!non.nullable)
					for (Production p : non.productions) {
						int i;
						for (i = 0; i < p.size(); i++) {
							if (p.get(0) instanceof NonTerminal) {
								if (!((NonTerminal) p.get(0)).nullable)
									break;
							} else
								break;
						}
						if (i == p.size()) {
							non.nullable = true;
							changed = true;
							break;
						}
					}
		}
	}

	void debugNullClosure() {
		o("These NonTerminals can be null ");
		for (NonTerminal non : n) {
			if (non.nullable)
				o(non.str + " ");
		}
		oline();
	}

	private void initNonTerminal() {
		for (NonTerminal non : n) {
			non.ifSee = new Production[t.length];
			non.begin = new Node(non.num);
			non.follow = new Node(non.num + n.length);
			non.edges = new Edge[non.productions.length];
			for (int i = 0; i < non.edges.length; i++)
				non.edges[i] = new Edge();
		}
	}

	private void initEdges() {
		for (NonTerminal non : n)
			for (int i = 0; i < non.productions.length; i++) {
				Production p = non.productions[i];
				if (p.isNull()) {
					non.edges[i].nodes.add(non.follow);
				} else {
					int j;
					for (j = 0; j < p.size(); j++) {
						if (p.get(j) instanceof NonTerminal) {
							NonTerminal temp = (NonTerminal) p.get(j);
							non.edges[i].nodes.add(temp.begin);
							if (temp.nullable)
								non.edges[i].nodes.add(temp.follow);
							else
								break;
						} else {
							non.edges[i].terminals.add((Terminal) p.get(j));
							break;
						}
					}
					if (j == p.size()) {
						non.edges[i].nodes.add(non.follow);
					}
				}
			}
	}

	void initBegin() {
		for (NonTerminal non : n)
			for (Edge e : non.edges) {
				for (Terminal ter : e.terminals)
					non.begin.terminals.add(ter);
				for (Node node : e.nodes)
					non.begin.nodes.add(node);
			}
	}

	void initFollow() {
		n[0].follow.terminals.add(t[0]);// 开始符号的follow+="#"
		for (NonTerminal non : n)
			for (Production p : non.productions)
				if (!p.isNull()) {
					int last = 0;
					for (int i = 0; i < p.size(); i++)
						if (p.get(i) instanceof Terminal) {
							for (int j = last; j < i; j++)
								((NonTerminal) p.get(j)).follow.terminals
										.add((Terminal) p.get(i));
							last = i + 1;
						} else {
							NonTerminal temp = (NonTerminal) p.get(i);
							for (int j = last; j < i; j++)
								((NonTerminal) p.get(j)).follow.nodes
										.add(temp.begin);
							if (!temp.nullable) {
								last = i;
							}
						}
					for (int j = last; j < p.size(); j++)
						((NonTerminal) p.get(j)).follow.nodes.add(non.follow);
				}
	}

	private void init() {
		for (NonTerminal non : n)
			for (int i = 0; i < non.edges.length; i++) {
				for (Terminal ter : non.edges[i].terminals)
					ifSee(non, ter, non.productions[i]);
				for (Node node : non.edges[i].nodes)
					ifSee(non, canReach(node, new boolean[n.length * 2]),
							non.productions[i]);
			}
	}

	void merge(boolean[] sea, boolean[] river) {// 百川归海
		for (int i = 0; i < river.length; i++)
			if (river[i])
				sea[i] = true;
	}

	boolean[] canReach(Node from, boolean visited[]) {
		boolean[] ans = new boolean[t.length];
		visited[from.num] = true;
		for (Terminal ter : from.terminals)
			ans[ter.num] = true;
		for (Node node : from.nodes)
			if (!visited[node.num])
				merge(ans, canReach(node, visited));
		return ans;
	}

	void ifSee(NonTerminal non, boolean[] reach, Production p) {
		for (int i = 0; i < reach.length; i++) {
			if (reach[i])
				ifSee(non, t[i], p);
		}
	}

	void ifSee(NonTerminal non, Terminal ter, Production p) {
		if (non.ifSee[ter.num] == null)
			non.ifSee[ter.num] = p;
		else {
			oline(non.str + " if see " + ter.str + ", can't decide to select "
					+ p + " or " + non.ifSee[ter.num]);
			oline("This is not an LL(1) grammer");
			System.exit(0);
		}
	}

	void debugIfSee() {
		for (NonTerminal non : n) {
			oline(non.str + " ***********");
			for (int i = 0; i < non.ifSee.length; i++) {
				if (non.ifSee[i] != null) {
					o("\t if see " + t[i].str + " select " + non.ifSee[i]
							+ "\n");
				}
			}
		}
	}

	void over(int i) {
		if (i == sentence.length)
			oline("successfully accepted the sentence");
		else {
			oline("failed to accepted the sentence");
			System.exit(0);
		}
	}

	void parse() {
		Stack<Symbol> stack = new Stack<Symbol>();
		stack.push(t[0]);
		stack.push(n[0]);
		int index = 0;
		while (!stack.empty()) {
			Symbol now = stack.pop();
			if (now instanceof Terminal) {
				if (sentence[index].num == now.num)
					index++;
				else
					over(index);
			} else {
				Production p = ((NonTerminal) now).ifSee[sentence[index].num];
				if (p == null)
					over(index);
				else if (p.isNull())
					continue;
				else
					for (int i = p.size() - 1; i >= 0; i--)
						stack.push(p.get(i));
			}
		}
		over(index);
	}

	void parseTree() {
		Stack<Symbol> stack = new Stack<Symbol>();
		Stack<StringNode> nodeStack = new Stack<StringNode>();
		stack.push(t[0]);
		stack.push(n[0]);
		StringNode root = new StringNode(n[0].str);
		nodeStack.push(root);
		nodeStack.push(root);// 一个是#号
		int index = 0;
		while (!stack.empty()) {
			Symbol now = stack.pop();
			StringNode node = nodeStack.pop();
			if (now instanceof Terminal) {
				index++;
			} else {
				Production p = ((NonTerminal) now).ifSee[sentence[index].num];
				if (p.isNull()) {
					node.sons.add(new StringNode(t[1].str));
					continue;
				} else {
					for (int i = p.size() - 1; i >= 0; i--) {
						stack.push(p.get(i));
						nodeStack.push(new StringNode(p.get(i).str));
					}
					for (int i = 0; i < p.size(); i++)
						node.sons.add(nodeStack.get(nodeStack.size() - 1 - i));
				}
			}
		}
		new TreePlayer(root).fileStyle();
	}

	/**
	 * 以下部分为从文件读入部分，比较傻，比较简单
	 */
	int getTerminal(String s) {
		for (int i = 0; i < t.length; i++) {
			if (t[i].str.equals(s))
				return i;
		}
		return -1;
	}

	int getNonTerminal(String s) {
		for (int i = 0; i < n.length; i++) {
			if (n[i].str.equals(s))
				return i;
		}
		return -1;
	}

	void input() throws Exception {
		FileReader file = new FileReader(new File("input.txt"));
		Scanner cin = new Scanner(file);
		while (cin.hasNext()) {// 注释部分
			String s = cin.next();
			if (s.equals("#"))
				break;
		}
		inputNonTerminal(cin);
		inputTerminal(cin);
		inputProduction(cin);
		inputSentence(cin);
		cin.close();
		file.close();
	}

	private void inputSentence(Scanner cin) {
		ArrayList<Terminal> str = new ArrayList<Terminal>();
		while (cin.hasNext()) {// 待判别的串
			String s = cin.next();
			if (s.equals("#"))
				break;
			str.add(t[getTerminal(s)]);
		}
		str.add(this.t[0]);
		sentence = new Terminal[str.size()];
		for (int i = 0; i < str.size(); i++)
			sentence[i] = str.get(i);
	}

	private void inputTerminal(Scanner cin) {
		ArrayList<Terminal> t = new ArrayList<Terminal>();
		t.add(new Terminal("#", 0));
		t.add(new Terminal("@", 1));
		while (cin.hasNext()) {// Terminal
			String s = cin.next();
			if (s.equals("#"))
				break;
			t.add(new Terminal(s, t.size()));
		}
		this.t = new Terminal[t.size()];
		for (int i = 0; i < t.size(); i++)
			this.t[i] = t.get(i);
	}

	private void inputNonTerminal(Scanner cin) {
		ArrayList<NonTerminal> n = new ArrayList<NonTerminal>();
		while (cin.hasNext()) {// NonTerminal
			String s = cin.next();
			if (s.equals("#"))
				break;
			n.add(new NonTerminal(s, n.size()));
		}
		this.n = new NonTerminal[n.size()];
		for (int i = 0; i < n.size(); i++)
			this.n[i] = n.get(i);
	}

	private void inputProduction(Scanner cin) throws Exception {
		while (cin.hasNext()) {
			String str = cin.next();
			if (str.equals("#"))
				break;
			cin.next();// "read =>"
			NonTerminal left = n[getNonTerminal(str)];
			ArrayList<Production> right = new ArrayList<Production>();
			Production pro = new Production();
			while (cin.hasNext()) {
				str = cin.next();
				if (str.equals(";")) {
					right.add(pro);
					break;
				} else if (str.equals("|")) {
					right.add(pro);
					pro = new Production();
				} else {
					int where = getTerminal(str);
					if (where != -1)
						pro.add(t[where]);
					else
						pro.add(n[getNonTerminal(str)]);
				}
			}
			left.productions = new Production[right.size()];
			for (int i = 0; i < right.size(); i++)
				left.productions[i] = right.get(i);
		}
	}

	static void o(String s) {
		System.out.print(s);
	}

	void oline(String s) {
		o(s);
		oline();
	}

	void oline() {
		o("\n");
	}

	void debugInput() {
		oline("The Length of the terminal is " + t.length
				+ "\nthe Length of the NonTerminal is " + n.length + " ");
		o("terminals: ");
		for (int i = 0; i < t.length; i++)
			o(t[i].str + " ");
		o("\nNonTerminals :");
		for (NonTerminal i : n)
			o(i.str + " ");
		oline("\nproductions : ");
		for (NonTerminal i : n) {
			o(i.str + "=>");
			if (i.productions == null) {
				oline(" noting ");
				continue;
			}
			for (Production p : i.productions) {
				for (Symbol s : p) {
					o(s.str + " ");
				}
				o(" | ");
			}
			oline();
		}
		o("\nsentence : ");
		for (Terminal tt : sentence) {
			o(tt.str + " ");
		}
		oline();
	}
}
