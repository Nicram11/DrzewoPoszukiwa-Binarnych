
import java.util.Iterator;
import java.util.Random;

public class Lista<T> implements Iterable<T>
{
	
	// -------- Implementacja Interfejsu Iterable --------  //
	public Iterator iterator()
    {
        return new ListIterator(this);
    }
	class ListIterator implements Iterator 
	{
	    Node node;
	      

	    public ListIterator(Lista lista)
	    {
	        node = 	lista.root;
	    }
	      

	    public boolean hasNext()
	    {
	        return node != null;
	    }
	      

	    public T next()
	    {
	        T val = node.val;
	        node = node.next;
	        return val;
	    }
	    }
	//--------------------------------//
	
	
	private class Node
	{
		Node next;
		T val;	
		Node(T val)
		{
			this.val = val;
		}
	}
	
	int size = 0;
	 Node root;
	 Node last;
	 
	
	
	public void Put(T val)
	{
		Node nowy = new Node(val);
		if(root == null)
		{
			root = nowy;
			last = root;
		}
		else 
		{
			last.next = nowy;
			last = last.next;
		}
		size++;
		
	}
	
	public int Size()
	{
		return size;
	}
	
	public void Del(T val)
	{
		root = Del(root, val);
	}
	
	private Node Del(Node node,T val)
	{
		if(node == null)
			return null;
		if(node.val == val)
			return node.next;
		
		node.next = Del(node.next, val);
		
		return node;
	}
	
	public void Reverse()
	{
		root = Reverse(root);
	}
	
	public Node ReverseZwróćWierzchołek()  
	{
		Node n = Reverse(root);
		
		return n;
	}
	
	private Node Reverse(Node root)
	{
		Node next = null;
		Node bef = null;
		Node node = root;
		
		
		while(node != null)
		{
			next = node.next;
			node.next = bef;
			bef = node;
			node = next;
		}
		node = bef;
		return node;
		
	}
	
	public boolean Contains(T val)
	{
		Node node = root;
		while(node!= null)
		{
			if(node.val == val)
				return true;
			node = node.next;
		}
		return false;
	}
	
	
	public T Index(int index)
	{
		Node node = root;
		int i = 0;
		
		if(index > size-1)
			return null;
		
		while(node!= null)
		{
			if(index == i)
				return node.val;
			i++;
			node = node.next;
		}
		return null;
	}
	
	
	public void Print()
	{
		Node node = root;
		while(node!= null)
		{
			System.out.print(node.val  + ", ");
			node = node.next;
		}
		System.out.println();
	} 
}
