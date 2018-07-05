package basic;
/*
 * 快速排序十句话
 * */
public class QuickSort {
	public static void main(String[] args) {
		int[] a = {34, 2, 33, 67, 12, 34, 23};
		sort(a, 0, a.length - 1);
		for (int i : a) {
			System.out.println(i + "  ");
		}
	}
	static void sort(int[] a, int from, int to) {
		if (from >= to)
			return;
		int f = from;
		int t = to;
		int temp = a[f];
		while (true) {
			while (a[t] > temp)
				t--;
			if (f == t)
				break;
			a[f] = a[t];
			a[t] = temp;
			f++;
			while (a[f] < temp)
				f++;
			if (f == t)
				break;
			a[t] = a[f];
			a[f] = temp;
			t--;
		}
		a[f] = temp;
		sort(a, from, f - 1);
		sort(a, f + 1, to);
	}
}
