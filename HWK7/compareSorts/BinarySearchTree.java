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
 *********************************************************/

class BSTNode    
{
	BSTNode left, right;
	int data;

	public BSTNode(int n)
	{
		left = null;
		right = null;
		data = n;
	}         
}

//********************************************************

public class BinarySearchTree
{
	int size = 0;
	private int[] bstArray;
	
	//********************************************************
	
	public BinarySearchTree(int size)
	{
		this.size = size;
		bstArray = new int[size];
	}

	//********************************************************

	private BSTNode root;

	public BinarySearchTree()
	{
		root = null;
	}

	//********************************************************

	/*
	 * case 0: If node is null, insert
	 * case 1: If a match found, return false
	 * case 2: If no match, create an item node and return it
	 * case 3: If item < current node.data, insert into leftChild
	 * case 4: If item > current node.data, insert into rightChild
	 */
	public void insert(int[] items)
	{
		for (int i=0; i < items.length; i++)
		{
			insert(items[i]);
		}
	}
	public void insert(int data)
	{
		root = insert(root, data);
	}
	private BSTNode insert(BSTNode node, int data)
	{
		if (node == null)
		{
			node = new BSTNode(data);
		}
		else
		{
			// This allows duplicates, however
			if (data <= node.data)
			{
				node.left = insert(node.left, data);
			}
			else
			{
				node.right = insert(node.right, data);
			}
		}
		return node;
	}

	//********************************************************

	public int search(int target)
	{
		return search(target, root);
	}

	/*
	 * case 1: node is null, not found.
	 * case 2: target == node.data, found.
	 * case 3: target < node.data, search left
	 * case 4: target > node.data, search right
	 */
	private int search(int target, BSTNode node)
	{
		if (node == null)
		{
			return -1;
		}
		if (target == node.data)
		{
			return node.data;
		}
		if (target < node.data)
		{
			return search(target, node.left);
		}
		else
		{
			return search(target, node.right);
		}

	} // End find(E, Node)

	//********************************************************

	public void remove(int data)
	{
		remove(root, data);
	}

	/*
	 * case 1. item == null, not found.
	 * case 2: item < node.data, search left.
	 * case 3. item > node.data, search right.
	 * case 4. item not in tree, do nothing.
	 * case 5: item = node.data, remove it.
	 * 		   		(a) if node has a single child, attach the child
	 * 				to node's parent
	 * 				(b) if node has two children,
	 * 				replace node with the largest node in its left sub tree
	 */
	private BSTNode remove(BSTNode node, int data)
	{
		if (node == null)
		{
			return node;
		}
		if (data < node.data)
		{
			node.left = remove(node.left, data);
			return node;
		}
		if (data > node.data)
		{
			node.right = remove(node.right, data);
			return node;
		}
		else
		{
			// Once item found, remove

			// If has single child, replace parent with child
			if (node.left == null)
			{
				return node.right;
			}
			else if (node.right == null)
			{
				return node.left;
			}
			// If has two children, replace the largest node on left
			else
			{
				node.data = findMax(node.left);
				return node;
			}
		}

	} // End remove(Node)

	//********************************************************

	/**
	 * Return the largest child of the node parent which is the right most node
	 * in the left sub tree.
	 */
	private int findMax(BSTNode parent)
	{
		if (parent.right.right == null)
		{
			int value = parent.right.data;
			parent.right = parent.right.left;
			return value;
		}
		else
		{
			return findMax(parent.right);
		}

	} // End findLargestChild(Node))

	//********************************************************

	public void inOrder()
	{
		inOrder(root);
	}
	private void inOrder(BSTNode n)
	{
		if (n != null)
		{
			inOrder(n.left);
			System.out.print(n.data + " ");
			inOrder(n.right);
		}
	} // End inorder()

	//********************************************************

	public void preOrder()
	{
		preOrder(root);
	}
	private void preOrder(BSTNode n)
	{
		if (n != null)
		{
			System.out.print(n.data + " ");
			preOrder(n.left);             
			preOrder(n.right);
		}

	} // End preorder()

	//********************************************************

	public void postOrder()
	{
		postOrder(root);
	}
	private void postOrder(BSTNode n)
	{
		if (n != null)
		{
			postOrder(n.left);             
			postOrder(n.right);
			System.out.print(n.data + " ");
		}
	} // End postorder()

	//********************************************************

	public int[] createRandomArray()
	{
		// Build 100-element randomized array
		for (int i=0; i < bstArray.length; i++)
		{
			Random rn = new Random(System.nanoTime());	// nanoTime() seed
			int randomInt =  rn.nextInt(100);
			bstArray[i] = randomInt;
		}

		return bstArray;

	} // End createRandomArray()

	//********************************************************

	public void printRandomArray()
	{
		System.out.println("1  2  3  4  5  6  7  8  9  10");
		System.out.println("-  -  -  -  -  -  -  -  -  --");
		System.out.println("Random Array:");
		for (int i = 0; i < bstArray.length; i++)
		{
			// Break into 10-element wide column
			if (i != 0 && (i % 10) == 0)
			{
				System.out.println();

				if (bstArray[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(bstArray[i] + "  ");
				}
				else
				{
					System.out.print(bstArray[i] + " ");
				}

			}
			else
			{
				if (bstArray[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(bstArray[i] + "  ");
				}
				else
				{
					System.out.print(bstArray[i] + " ");
				}
			}
		}

		System.out.println();
		System.out.println();
	}

	//********************************************************

	public static void main(String[] args)
	{
		BinarySearchTree bst = new BinarySearchTree(100);
		
		System.out.println("//********************************************************");
		System.out.println("// Binary Search Tree");
		System.out.println("//********************************************************");
		System.out.println();

		bst.createRandomArray();

		bst.printRandomArray();

		long startTime = System.nanoTime();

		bst.insert(bst.bstArray);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		
		System.out.println("Total insertion time: " + 
				NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");
		
		System.out.println();
		
		System.out.println("Inorder Traversal:");
		bst.inOrder();
		
		System.out.println();
		System.out.println();
		
		System.out.println("Search for " + 17 + " resulted in " + bst.search(17) + ".");
		System.out.println("Search for " + 27 + " resulted in " + bst.search(27) + ".");
		System.out.println("Search for " + 3 + " resulted in " + bst.search(3) + ".");
		System.out.println("Search for " + 77 + " resulted in " + bst.search(77) + ".");

	} // End main

} // End BinarySearchTree.java
