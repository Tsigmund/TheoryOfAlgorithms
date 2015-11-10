package heap;

/*********************************************************
 * Authors: Ericaceous Wood, Trevor Sigmund, Topher Witt
 * Date: 11.9.2015
 * Course: CS433 - Theory of Algorithms
 * Program Description: Heap portion of Homework 7
 *********************************************************/

public class MaxHeap
{
	// create array
	// partially complete binary search tree
	// parent >= child
	// child -> parent = (k-1)/2
	
	public static int[] heapArray = new int[100];
	
	public MaxHeap() { }
	
	public void insert(int item)
	{
		// insert int into heap
	}
	
	public int remove()
	{
		// pop off the parent and return it
	}
	
	public static int[] createRandomArray()
	{
		// Build a heap with 100 random numbers
		//int[] heapArray = new int[100];
		
		for (int i=0; i < heapArray.length; i++)
		{
			heapArray[i] = (int) Math.random();
		}
		
		return heapArray;
	}

	public static void main(String[] args)
	{

		MaxHeap mh = new MaxHeap();
		
		long startTime = System.nanoTime();
		
		for (int i=0; i < heapArray.length; i++)
		{
			mh.insert(heapArray[i]);
		}
		
		// Test
		//System.out.print("Original heap: " + heapArray.toString());
		
		for (int i=0; i < heapArray.length; i++)
		{
			mh.remove();
		}
		
		long endTime = System.nanoTime();
		
		// Test
		//System.out.println("Sorted items after heap removal: " + heapArray.toString());
		
		long totalTime = (endTime - startTime);
		
		System.out.println("Total time: " + totalTime);
	}
	
	
} // End MaxHeap.java
