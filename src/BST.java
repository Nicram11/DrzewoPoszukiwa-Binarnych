
public class BST<T extends Comparable<T>> 
{

	private class Node 
	{
		public Node left;
		public Node right;
		public T key;

		public Node(T key) 
		{
			this.key = key;
		}

		public StringBuilder toString(StringBuilder sb1, boolean czyLewy, StringBuilder sb2) 
		{
			if (right != null)
				right.toString(new StringBuilder().append(sb1).append(czyLewy ? "│   " : "    "),
						false, sb2);

			sb2.append(sb1).append(czyLewy ? "└── " : "┌── ").append(key.toString()).append("\n");
			if (left != null)
				left.toString(new StringBuilder().append(sb1).append(czyLewy ? "    " : "│   "),
						true, sb2);
			return sb2;
		}

		public String toString() 
		{
			return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
		}
	}

	private Node Root;
	public int size;

	public void Put(T key) 
	{

		Node node = Root;
		Node nowy = new Node(key);
		if (Root == null) 
		{
			Root = nowy;
			size = 1;
			return;
		} else 
		{
			while (node != null) 
			{
				int x = node.key.compareTo(key);
				if (x > 0) 
				{

					if (node.left == null) 
					{
						node.left = nowy;
						size++;
						break;
					} else
						node = node.left;

				} else if (x < 0) 
				{

					if (node.right == null) 
					{
						node.right = nowy;
						size++;
						break;
					} else
						node = node.right;

				} else
					break;
			}
		}
	}

	public T Get(T key) 
	{
		Node node = Root;
		while (node != null) 
		{
			int x = node.key.compareTo(key);
			if (x > 0)
				node = node.left;
			else if (x < 0)
				node = node.right;
			else
				return node.key;
		}
		return null;
	}

	public void Del(T key) 
	{
		Root = Del(Root, key);

	}

	private Node Del(Node node, T key) 
	{
		if (node == null)
			return null;

		int x = node.key.compareTo(key);
		if (x < 0)
			node.right = Del(node.right, key);
		else if (x > 0)
			node.left = Del(node.left, key);
		else 
		{
			size--;
			if (node.left == null)
				return node.right;
			if (node.right == null)
				return node.left;
			else {
				node.key = Min(node.right);
				node.right = Del(node.right, node.key);
			}
		}
		return node;
	}

	public T Min() 
	{
		return Min(Root);
	}

	private T Min(Node n) 
	{

		while (n.left != null)
			n = n.left;

		T min = n.key;
		return min;
	}

	public T Max() 
	{
		return Max(Root);
	}

	private T Max(Node n) 
	{

		while (n.right != null) 
		{

			n = n.right;
		}
		T max = n.key;
		return max;
	}

	public int WysokoscDrzewa() 
	{
		if (Root == null)
			return 0;
		return WysokoscDrzewa(Root);

	}

	private int WysokoscDrzewa(Node n) 
	{
		if (n == null)
			return 0;
		int lH = WysokoscDrzewa(n.left);
		int rH = WysokoscDrzewa(n.right);

		if (lH > rH)
			return lH + 1;
		else
			return rH + 1;
	}

	private int PorownajWysokosci(Node n) 
	{
		if (n == null)
			return 0;
		int lH = WysokoscDrzewa(n.left);
		int rH = WysokoscDrzewa(n.right);

		if (lH > rH)
			return -1;
		else if (lH < rH)
			return 1;
		else
			return 0;
	}

	public void ZmniejszDrzewoUsuwajacWierzcholek() 
	{
		ZmniejszDrzewoUsuwajacWierzcholek(Root);
	}

	private void ZmniejszDrzewoUsuwajacWierzcholek(Node n) 
	{

		if (n.right == null || n.left == null) 
		{
			Del(n.key);
			return;
		}

		else if (PorownajWysokosci(n) == -1)
			ZmniejszDrzewoUsuwajacWierzcholek(n.left);

		else if (PorownajWysokosci(n) == 1)
			ZmniejszDrzewoUsuwajacWierzcholek(n.right);

		else if (PorownajWysokosci(n) == 0) {
			System.out.println(
					"Dla obecnego drzewa NIE da się usunąć takiego wierzchołka żeby wysokość drzewa się zmniejszyła");
			return;
		}
	}

	private Integer[] tab;
	private int i;

	public Integer[] KonwertujNaTabliceInt() 
	{
		if ((Integer) Root.key == null)
			return null;

		i = 0;
		tab = new Integer[size];

		KonwertujNaTabliceInt(Root);
		return tab;
	}

	private void KonwertujNaTabliceInt(Node n) 
	{
		if (n != null) {
			KonwertujNaTabliceInt(n.left);
			tab[i++] = (int) n.key;
			KonwertujNaTabliceInt(n.right);

		}
	}

	public void MozliweKombinacje() 
	{
		Integer[] tab = KonwertujNaTabliceInt();
		Lista<Node> lista = MozliweKombinacje(tab, 0, tab.length - 1);

		for (Node n : lista) {
			System.out.println(n.toString());
		}
		System.out.println("Liczba wszystkich wydrukowanych drzew wynosi:" + lista.size);
	}

	private Lista<Node> MozliweKombinacje(Integer[] tab, int start, int koniec) 
	{
		Lista<Node> list = new Lista<>();
		if (start > koniec) {
			list.Put(null);
			return list;
		}

		for (int i = start; i <= koniec; i++) {

			Lista<Node> leweDrzewo = MozliweKombinacje(tab, start, i - 1);

			Lista<Node> praweDrzewo = MozliweKombinacje(tab, i + 1, koniec);

			for (int j = 0; j < leweDrzewo.size; j++) {

				Node left = leweDrzewo.Index(j);
				for (int l = 0; l < praweDrzewo.size; l++) {
					Node right = praweDrzewo.Index(l);
					Node node = new Node((T)tab[i]);
					node.right = right;
					node.left = left;
					list.Put(node);
				}
			}
		}
		return list;
	}
}
