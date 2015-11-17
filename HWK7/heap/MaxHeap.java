package heap;

/*********************************************************
 * Authors: Ericaceous Wood, Trevor Sigmund, Topher Witt
 * Date: 11.9.2015
 * Course: CS433 - Theory of Algorithms
 * Program Description: Heap portion of Homework 7
 * 
 * An array represented as partially complete binary search tree
 * 		(1) parent >= child
 * 		(2) child -> parent = (k-1)/2
 *********************************************************/

public class MaxHeap
{
	public static int[] heapArray = new int[100];
	public static int size = heapArray.length;
	private static final int top = 1;

	//********************************************************

	public MaxHeap() { }

	//********************************************************

	private int parent(int index)
	{
		// mid
		return (index / 2);
	}

	//********************************************************

	private int leftChild(int index)
	{
		// 2i
		return (2 * index);
	}

	//********************************************************

	private int rightChild(int index)
	{
		// 2i + 1
		return (2 * index) + 1;
	}

	//********************************************************

	// childless node
	private boolean isLeaf(int index)
	{
		if (index >=  (size / 2)  &&  index <= size)
		{
			return true;
		}
		return false;
	}

	//********************************************************

	private void swap(int item1, int item2)
	{
		int temp;
		temp = heapArray[item1];
		heapArray[item1] = heapArray[item2];
		heapArray[item2] = temp;
	}

	//********************************************************

	private void insert(int index)
	{
		for (int i = (size / 2); i >= 1; i--)
		{
			if (!isLeaf(index))
			{ 
				if (heapArray[index] < heapArray[leftChild(index)] || heapArray[index] < heapArray[rightChild(index)])
				{
					if (heapArray[leftChild(index)] > heapArray[rightChild(index)])
					{
						swap(index, leftChild(index));
						insert(leftChild(index));
					}
					else
					{
						swap(index, rightChild(index));
						insert(rightChild(index));
					}
				}
			}
		}
		
	} // end MaxHeapify(index)

	//********************************************************

	public int remove()
	{
		// pop off the parent and return it
		int removedInt = heapArray[size];
		heapArray[top] = heapArray[size--]; 
		//
		return removedInt;
	}

	//********************************************************

	public int getPeak()
	{
		// Simply returns root (O(1))
		return this.remove();
	}

	//********************************************************

	public static int[] createRandomArray()
	{
		// Build a heap with 100 random numbers
		//int[] heapArray = new int[100];

		for (int i=0; i < size; i++)
		{
			heapArray[i] = (int) Math.random();
		}

		return heapArray;
	}

	//********************************************************

	public void printHeap()
	{
		// Starting AFTER the "root"
		for (int i = 0; i < (size / 2); i++ )
		{
			System.out.print("\tPARENT : " + heapArray[i] + 
					"\nLEFT CHILD : " + heapArray[2*i] + 
					"\tRIGHT CHILD :" + heapArray[(2*i) + 1]);
			System.out.println();
		}

	} // End printHeap


	//********************************************************

	public static void main(String[] args)
	{
		createRandomArray();

		MaxHeap mh = new MaxHeap();

		long start = System.nanoTime();

		for (int i=0; i < size; i++)
		{
			mh.insert(heapArray[i]);
		}

		for (int i=0; i < size; i++)
		{
			mh.remove();
		}

		long end = System.nanoTime();

		long duration = (end - start);

		System.out.println("Total time: " + duration);

	} // End main

} // End MaxHeap.java
