package avlTree;

import java.awt.List;

/*********************************************************
 * Authors: Ericaceous Wood, Trevor Sigmund, Topher Witt
 * Date: 11.9.2015
 * Course: CS433 - Theory of Algorithms
 * Program Description: AVL Tree portion of Homework 7
 * 
 * Use rotations to balance/sort the tree.
 * Implemented using linked list.
 *********************************************************/

public class AVLTree
{
	// simple BST insert
	// fix AVL property
	// create array
		// partially complete binary search tree
		// parent >= child
		// child -> parent = (k-1)/2
		
		public static int[] avlArray = new int[100];
		
		List ls = new List();
		
		public AVLTree() { }
		
		//********************************************************
		
		public void insert(int item)
		{
			// insert int into AVL tree
		}
		
		//********************************************************
		
		public int search(int item)
		{
			// determins if item is contained in the tree
			// if so, returns int (not location?)
			return 0;
		}
		
		//********************************************************
		
		public void displayInorder()
		{
			// traverse tree using inorder
			// print
		}
		
		//********************************************************
		
		public int remove()
		{
			// pop off the parent and return it
			return 0;
		}
		
		//********************************************************
		
		public static int[] createRandomArray()
		{
			// Build a heap with 100 random numbers
			//int[] heapArray = new int[100];
			
			for (int i=0; i < avlArray.length; i++)
			{
				avlArray[i] = (int) Math.random();
			}
			
			return avlArray;
		}
		
		//********************************************************

		public static void main(String[] args)
		{

			AVLTree avlt = new AVLTree();
			
			long startTime = System.nanoTime();
			
			for (int i=0; i < avlArray.length; i++)
			{
				avlt.insert(avlArray[i]);
			}
			
			// Test
			//System.out.print("Original heap: " + heapArray.toString());
			
			for (int i=0; i < avlArray.length; i++)
			{
				avlt.remove();
			}
			
			long endTime = System.nanoTime();
			
			// Test
			//System.out.println("Sorted items after heap removal: " + heapArray.toString());
			
			long totalTime = (endTime - startTime);
			
			System.out.println("Total time: " + totalTime);
		}
		
} // End AVLTree.java
