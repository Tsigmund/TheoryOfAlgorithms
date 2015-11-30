package compareSorts;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/*********************************************************
 * Authors: Ericaceous Wood, Trevor Sigmund, Topher Witt
 * Date: 11.9.2015
 * Course: CS433 - Theory of Algorithms
 * Program Description: AVL Tree portion of Homework 7
 * 
 * Implemented using linked list or nodes.
 * 
 * insert(x) - duplicates OK.
 * search(x) - returns x if found
 * inOrder() - traverse and display tree in order
 * 
 * Build 100-element AVL tree randomly
 * 
 * Use rotations to balance/sort the tree.
 * Implemented using linked list.
 * 
 * An unbalanced state is defined as a state in which 
 * any subtree has a balance factor of greater than 1,
 * or less than -1. That is, any tree with a difference
 * between the heights of its two subtrees greater than 1,
 * is considered unbalanced. 
 * 
 * child -> parent = (k-1)/2
 *********************************************************/

class AVLNode
{    
	AVLNode left, right;
	int data;
	int height;

	public AVLNode()
	{
		left = null;
		right = null;
		data = 0;
		height = 0;
	}

	public AVLNode(int n)
	{
		left = null;
		right = null;
		data = n;
		height = 0;
	}     
}

public class AVLTree
{
	private AVLNode root;

	int size = 0;
	private int[] avlArray;
	
	//********************************************************
	
	public AVLTree(int size)
	{
		this.size = size;
		avlArray = new int[size];
	}

	//********************************************************

	public AVLTree()
	{
		root = null;
	}

	//********************************************************

	public boolean isEmpty()
	{
		return root == null;
	}

	//********************************************************

	public void clear()
	{
		root = null;
	}

	//********************************************************

	private int height(AVLNode t)
	{
		return (t == null ? -1 : t.height);
	}

	//********************************************************

	private int max(int lhs, int rhs)
	{
		return (lhs > rhs ? lhs : rhs);
	}

	//********************************************************

	public void insert(int[] items)
	{
		for (int i=0; i < items.length; i++)
		{
			insert(items[i]);
		}
	}
	public void insert(int data)
	{
		root = insert(data, root);
	}
	private AVLNode insert(int item, AVLNode node)
	{
		if (node == null)
		{
			node = new AVLNode(item);
		}

		// If tree is right-heavy
		else if (item < node.data)
		{
			node.left = insert(item, node.left);

			// If tree is right-heavy
			// Balance = 2
			if (height(node.left) - height(node.right) == 2)
			{
				// If tree's right subtree is right-heavy
				if (item < node.left.data)
				{
					node = leftRotate(node);
				}
				// If tree's right subtree is left-heavy
				else
				{
					node = doubleRotateLeft(node);
				}
			}
		}

		// If tree is left-heavy
		else if (item > node.data)
		{
			node.right = insert(item, node.right);

			// If tree is left-heavy
			if (height(node.right) - height(node.left) == 2)
			{
				// If tree's left subtree is left-heavy
				if (item > node.right.data)
				{
					node = rightRotate(node);
				}

				// If tree's left subtree is right-heavy
				else
				{
					node = doubleRotateRight(node);
				}

			}
		}

		node.height = max(height(node.left), height(node.right)) + 1;
		return node;
	}
	
	//********************************************************

	public int countNodes()
	{
		return countNodes(root);
	}
	private int countNodes(AVLNode node)
	{
		if (node == null)
		{
			return 0;
		}
		else
		{
			int count = 1;
			count += countNodes(node.left);
			count += countNodes(node.right);
			return count;
		}
	}

	//********************************************************

	private AVLNode leftRotate(AVLNode k2)
	{
		AVLNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = max(height(k2.left), height(k2.right)) + 1;
		k1.height = max(height(k1.left), k2.height) + 1;
		return k1;
	}

	//********************************************************

	private AVLNode rightRotate(AVLNode k1)
	{
		AVLNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = max(height(k1.left), height(k1.right)) + 1;
		k2.height = max(height(k2.right), k1.height) + 1;
		return k2;
	}

	//********************************************************

	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child */
	private AVLNode doubleRotateLeft(AVLNode k3)
	{
		k3.left = rightRotate(k3.left);
		return leftRotate(k3);
	}

	//********************************************************

	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child */      
	private AVLNode doubleRotateRight(AVLNode k1)
	{
		k1.right = leftRotate(k1.right);
		return rightRotate(k1);
	}

	//********************************************************

	// return boolean search

	/*public boolean search(int val)
		{
			return search(root, val);
		}
		private boolean search(Node node, int target)
		{
			boolean found = false;

			while ((node != null) && !found)
			{
				int rightTarget = node.data;

				if (target < rightTarget)
				{
					node = node.left;
				}
				else if (target > rightTarget)
				{
					node = node.right;
				}
				else
				{
					found = true;
					break;
				}
				found = search(node, target);
			}
			return found;
		}*/

	//********************************************************

	// return int val search

	public int search(int val)
	{
		return search(root, val);
	}
	private int search(AVLNode node, int target)
	{
		boolean found = false;

		while ((node != null) && !found)
		{
			int rightTarget = node.data;

			if (target < rightTarget)
			{
				node = node.left;
			}
			else if (target > rightTarget)
			{
				node = node.right;
			}
			else
			{
				found = true;
				return target;
			}

			//found = search(node, target);
		}
		return -1;
	}

	//********************************************************

	public void inOrder()
	{
		inOrder(root);
	}
	private void inOrder(AVLNode r)
	{
		if (r != null)
		{
			inOrder(r.left);
			System.out.print(r.data + " ");
			inOrder(r.right);
		}
	}

	//********************************************************

	public void preOrder()
	{
		preOrder(root);
	}
	private void preOrder(AVLNode r)
	{
		if (r != null)
		{
			System.out.print(r.data + " ");
			preOrder(r.left);             
			preOrder(r.right);
		}
	}

	//********************************************************

	public void postOrder()
	{
		postOrder(root);
	}
	private void postOrder(AVLNode r)
	{
		if (r != null)
		{
			postOrder(r.left);             
			postOrder(r.right);
			System.out.print(r.data + " ");
		}
	}

	//********************************************************

	public int[] createRandomArray()
	{
		// Build 100-element randomized array
		for (int i=0; i < avlArray.length; i++)
		{
			Random rn = new Random(System.nanoTime());	// nanoTime() seed
			int randomInt =  rn.nextInt(100);
			avlArray[i] = randomInt;
		}

		return avlArray;

	} // End cretaeRandomArray()

	//********************************************************

	public void printRandomArray()
	{
		System.out.println("Random Array:");
		System.out.println("1  2  3  4  5  6  7  8  9  10");
		System.out.println("-  -  -  -  -  -  -  -  -  --");
		for (int i = 0; i < avlArray.length; i++)
		{
			// Break into 10-element wide column
			if (i != 0 && (i % 10) == 0)
			{
				System.out.println();

				if (avlArray[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(avlArray[i] + "  ");
				}
				else
				{
					System.out.print(avlArray[i] + " ");
				}
			}
			else
			{
				if (avlArray[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(avlArray[i] + "  ");
				}
				else
				{
					System.out.print(avlArray[i] + " ");
				}
			}
		}

		System.out.println();
		System.out.println();

	} // End printRandomArray()

	//********************************************************

	public static void main(String[] args)
	{
		AVLTree avl = new AVLTree(100);
		
		System.out.println("//********************************************************");
		System.out.println("// AVL Search Tree");
		System.out.println("//********************************************************");
		System.out.println();

		avl.createRandomArray();

		avl.printRandomArray();

		long startTime = System.nanoTime();

		avl.insert(avl.avlArray);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime);

		System.out.println("Total insertion time: " + NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");
		
		System.out.println();
		
		System.out.println("Inorder Traversal:");
		avl.inOrder();

		System.out.println();
		System.out.println();

		System.out.println("Search for " + 17 + " resulted in " + avl.search(17) + ".");
		System.out.println("Search for " + 27 + " resulted in " + avl.search(27) + ".");
		System.out.println("Search for " + 3 + " resulted in " + avl.search(3) + ".");
		System.out.println("Search for " + 77 + " resulted in " + avl.search(77) + ".");

	} // End main

} // End AVLTree.java

