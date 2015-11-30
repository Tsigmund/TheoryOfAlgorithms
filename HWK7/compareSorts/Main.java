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
 * Compare Heap, BST, AVL, and SelectionSort running time
 * for n=100,000.
 *********************************************************/

public class Main
{
	static int size = 100;
	public static final int[] randomHeapArray = new int[size+1];
	public static final int[] randomBSTArray = new int[size];
	public static final int[] randomAVLArray = new int[size];
	public static final int[] randomSSArray = new int[size];
	public static final int[] mainArray = new int[size];

	//********************************************************

	public static void createRandomArray()
	{
		// Build 100-element randomized array
		// For testing purposes, each algorithm gets the same original array
		// But they must be separately sorted, so 3 arrays required
		for (int i=0; i < size; i++)
		{
			Random rn = new Random(System.nanoTime());	// nanoTime() seed
			int randomInt =  rn.nextInt(size);
			mainArray[i] = randomInt;
			randomBSTArray[i] = randomInt;
			randomAVLArray[i] = randomInt;
			randomSSArray[i] = randomInt;
		}
		
		randomHeapArray[0] = Integer.MAX_VALUE;
		
		// MaxHeap needs to be shifted up due to hidden root @ index 0
		for (int i=1; i <= size; i++)
		{
			randomHeapArray[i] = mainArray[i-1];
		}

	} // End createRandomArray()

	//********************************************************

	public static void printRandomArray(int[] randomArray)
	{
		System.out.println("Random Array:");
		System.out.println("1  2  3  4  5  6  7  8  9  10");
		System.out.println("-  -  -  -  -  -  -  -  -  --");
		for (int i = 0; i < randomArray.length; i++)
		{
			// Break into 10-element wide column
			if (i != 0 && (i % 10) == 0)
			{
				System.out.println();

				if (randomArray[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(randomArray[i] + "  ");
				}
				else
				{
					System.out.print(randomArray[i] + " ");
				}

			}
			else
			{
				if (randomArray[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(randomArray[i] + "  ");
				}
				else
				{
					System.out.print(randomArray[i] + " ");
				}
			}
		}

		System.out.println();
		System.out.println();

	} // End printRandomArray()

	//********************************************************

	public static void testMaxHeap()
	{
		MaxHeap mh = new MaxHeap(100);

		System.out.println("//********************************************************");
		System.out.println("// Max Heap");
		System.out.println("//********************************************************");
		System.out.println();
	
		mh.printRandomArray(randomHeapArray);
		
		long startTime = System.nanoTime();

		mh.insert(randomHeapArray);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime);

		System.out.println("Total insertion time: " + 
				NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");

		System.out.println();

		// Pops off each int of max heap yields
		// descending order
		mh.inOrder(mh.popTraversal());

		System.out.println();

		System.out.println("Search for " + 17 + " resulted in " + mh.search(randomHeapArray, 17) + ".");
		System.out.println("Search for " + 27 + " resulted in " + mh.search(randomHeapArray, 27) + ".");
		System.out.println("Search for " + 3 + " resulted in " + mh.search(randomHeapArray, 3) + ".");
		System.out.println("Search for " + 77 + " resulted in " + mh.search(randomHeapArray, 77) + ".");
		
		System.out.println();

		} // End testMaxHeap

		//********************************************************

		public static void testBinarySearchTree()
		{
			BinarySearchTree bst = new BinarySearchTree();

			System.out.println("//********************************************************");
			System.out.println("// Binary Search Tree");
			System.out.println("//********************************************************");
			System.out.println();

			printRandomArray(randomBSTArray);

			long startTime = System.nanoTime();

			bst.insert(randomBSTArray);

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

			System.out.println();

		} // End testBinarySearchTree()

		//********************************************************

		public static void testAVLTree()
		{
			AVLTree avl = new AVLTree();

			System.out.println("//********************************************************");
			System.out.println("// AVL Search Tree");
			System.out.println("//********************************************************");
			System.out.println();

			printRandomArray(randomAVLArray);

			long startTime = System.nanoTime();

			avl.insert(randomAVLArray);

			long endTime = System.nanoTime();

			long duration = (endTime - startTime);

			System.out.println("Total insertion time: " + 
					NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");

			System.out.println();

			System.out.println("Inorder Traversal:");
			avl.inOrder();

			System.out.println();
			System.out.println();

			System.out.println("Search for " + 17 + " resulted in " + avl.search(17) + ".");
			System.out.println("Search for " + 27 + " resulted in " + avl.search(27) + ".");
			System.out.println("Search for " + 3 + " resulted in " + avl.search(3) + ".");
			System.out.println("Search for " + 77 + " resulted in " + avl.search(77) + ".");

			System.out.println();

		} // End testAVLTree()

		//********************************************************

		public static void testSelectionSort()
		{
			SelectionSort ss = new SelectionSort(100);

			System.out.println("//********************************************************");
			System.out.println("// Selection Sort");
			System.out.println("//********************************************************");
			
			ss.printRandomArray(randomSSArray);

			long startTime = System.nanoTime();

			ss.sort(randomSSArray);

			long endTime = System.nanoTime();

			long duration = (endTime - startTime);

			System.out.println("Total insertion time: " + NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");

			System.out.println();

			System.out.println("Sorted array:");
			ss.inOrder(randomSSArray);

			System.out.println();
			System.out.println();

			System.out.println("Search for " + 17 + " resulted in " + ss.search(randomSSArray, 17) + ".");
			System.out.println("Search for " + 27 + " resulted in " + ss.search(randomSSArray, 27) + ".");
			System.out.println("Search for " + 3 + " resulted in " + ss.search(randomSSArray, 3) + ".");
			System.out.println("Search for " + 77 + " resulted in " + ss.search(randomSSArray, 77) + ".");

		} // End testSelectionSort()

		//********************************************************

		public static void main(String[] args)
		{
			createRandomArray();
			testMaxHeap();
			testBinarySearchTree();
			testAVLTree();
			testSelectionSort();

		} // End main

	} // End Main.java
