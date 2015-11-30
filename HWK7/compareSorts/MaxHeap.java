package compareSorts;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/*********************************************************
 * Authors: Ericaceous Wood, Trevor Sigmund, Topher Witt
 * Date: 11.9.2015
 * Course: CS433 - Theory of Algorithms
 * Program Description: Heap portion of Homework 7
 * 
 * insert() and remove()
 * Implement heap sort with randomly filled 100-element heap
 * 
 * An array represented as partially complete binary search tree
 * 		(1) parent >= child
 * 		(2) child -> parent = (k-1)/2
 *********************************************************/

public class MaxHeap
{
	private int[] heapArray;
	private int currentSize = 0;
	private int maxSize;

	// Our actual root is @ index 1
	private final int root = 1;

	//********************************************************

	public MaxHeap(int maxSize)
	{
		this.maxSize = (maxSize);
		heapArray = new int[this.maxSize+1];
		heapArray[0] = Integer.MAX_VALUE;	// invisible root = +infinity
											// needed for max sort logic
	}

	//********************************************************

	private int parent(int i)
	{
		return (i / 2);
	}

	//********************************************************

	private int leftChild(int i)
	{
		return (2 * i);
	}

	//********************************************************

	private int rightChild(int i)
	{
		return ((2*i) + 1);
	}

	//********************************************************

	private boolean isLeaf(int i)
	{
		if ((i >= (currentSize / 2))  &&  (i <= currentSize))
		{
			return true;
		}

		return false;
	}

	//********************************************************

	public boolean isEmpty()
	{
		// If only invisible root is present, heap is empty.
		return (currentSize == 1);
	}

	//********************************************************

	private void swap(int first, int second)
	{
		int temp;
		temp = heapArray[first];
		heapArray[first] = heapArray[second];
		heapArray[second] = temp;
	}

	//********************************************************

	/**
	  Root the heap: Updates heap ordering as array data is updated

	  Given a tree that is a heap except for node i,
	  arranges node i and its subtrees

	  Given heapArray and int index, 
	  if (left(i) < heap.size) && (heap[left(i)] > heap(i))
			 largest = left(i)
			 else largest = i
	  if (right(i) <= heap.size) && (heap[right(i)] > heap[largest])
			 largest = right(i)
	  if (largest != i)
			 swap(heap[i], heap[largest]
			 updateHeap(largest)
	 */
	private void update(int current)
	{
		// if "node" has child(ren)
		if (!isLeaf(current))
		{
			// if current "node" is less than one of its children
			if (heapArray[current] < heapArray[leftChild(current)] 
					|| heapArray[current] < heapArray[rightChild(current)])
			{
				// If left > right, largest = left
				if (heapArray[leftChild(current)] > heapArray[rightChild(current)])
				{
					//largest = heapArray[leftChild(current)];
					swap(current, leftChild(current));
					update(leftChild(current));
				}
				// If right > left, largest = right
				else
				{
					//largest = heapArray[rightChild(current)];
					swap(current, rightChild(current));
					update(rightChild(current));
				}
			}

		}

	} // End updateHeap(i)

	//********************************************************

	public void updateHeap()
	{
		// No effect in updating i > (size / 2) since those are leaf nodes
		//for (int i = (size / 2); i > 1; i--)
		for (int i = parent(currentSize); i >= 0; i--)
		{
			update(i);
		}
	}

	//********************************************************

	protected void insert(int[] items)
	{	
		// by now this as an extra zero in front
		//System.out.println(java.util.Arrays.toString(items));
		
		for (int i=1; i < items.length; i++)
		{
			insert(items[i]);
		}

		updateHeap();
	}
	protected void insert(int item)
	{
		// 0 - 100 (101 elements)
		// currentSize <= 100
		heapArray[currentSize++] = item;
		int current = currentSize;

		// Start at end (size-1)
		// Best case, element to insert is smallest
		// and belongs in last position

		// While child > parent, swap
		while (heapArray[current] > heapArray[parent(current)])
		{
			// child <=> parent
			// Swaps elements
			swap(current, parent(current));
			current = parent(current);
		}
		

	} // End insert(element)

	//********************************************************

	public int search(int[] items, int target)
	{
		for (int i=1; i < items.length; i++)
		{
			if (items[i] == target)
			{
				return target;
			}
		}

		return -1;
	}

	//********************************************************

	// Pops largest key (root) off max heap
	public int pop()
	{
		int popped = heapArray[root];

		// Shift array and reduce size
		// Decrement size & shift newest max to root
		heapArray[root] = heapArray[--currentSize];

		// Update heap using new root
		update(root);

		return popped;
	}

	//********************************************************

	// Pops largest key off max heap for each element
	// Yields entire heap in descending order
	protected int[] popTraversal()
	{
		int popped[] = new int[maxSize];

		for (int i=0; i < (maxSize-1); i++)
		{
			popped[i] = pop();
		}

		return popped;
	}

	//********************************************************

	// inOrder = reverse of popTraversal()
	protected void inOrder(int[] popArray)
	{
		System.out.println("Inorder traversal:");
		for (int i=(maxSize-1); i > 0; i--)
		{
			System.out.print(popArray[i] + " ");
		}

		System.out.println();
	}

	//********************************************************

	protected int[] createRandomArray()
	{
		// currentSize only covers index 1 - 100
		// currentSize will only ever be 100
		// maxSize is 101
		for (int i=1; i <= maxSize; i++)
		{
			Random rn = new Random(System.nanoTime());	// nanoTime() seed
			int randomInt =  rn.nextInt(100);
			heapArray[i] = randomInt;
		}

		return heapArray;

	} // End createRandomArray()

	//********************************************************

	/**
	 Prints 10 columns
	 */
	public void printRandomArray(int[] items)
	{
		System.out.println("Random Array:");
		System.out.println("1  2  3  4  5  6  7  8  9  10");
		System.out.println("-  -  -  -  -  -  -  -  -  --");
		for (int i = 1; i < items.length; i++)
		{
			if ((i-1) != 0 && (i-1)%10 == 0)
			{
				// Every 10th digit starting shifted up by 1 gets new row
				System.out.println();

				if (items[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(items[i] + "  ");
				}
				else
				{
					System.out.print(items[i] + " ");
				}
			}
			else
			{
				if (items[i] < 10)
				{
					// Extra white space on single digits for alignment
					System.out.print(items[i] + "  ");
				}
				else
				{
					System.out.print(items[i] + " ");
				}
			}
		}

		System.out.println();
		System.out.println();
	}

	//********************************************************

	protected void displayMaxHeap()
	{
		// Only interested in first half of nodes
		// The rest are leaf nodes
		for (int i = 1; i < (maxSize / 2); i++ )
		{
			System.out.println("\tParent: " + heapArray[i]);
			System.out.println("Left: " + heapArray[2*i] + 
					"\tRight: " + heapArray[(2*i) + 1]);
			System.out.println();
		}

	}

	//********************************************************

	public static void main(String[] args)
	{
		MaxHeap mh = new MaxHeap(100);

		System.out.println("//********************************************************");
		System.out.println("// Max Heap");
		System.out.println("//********************************************************");
		System.out.println();

		mh.createRandomArray();

		mh.printRandomArray(mh.heapArray);

		long startTime = System.nanoTime();

		mh.insert(mh.heapArray);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime);

		System.out.println("Total insertion time: " + 
				NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");

		System.out.println();
		
		// extra weirdness with smallest element happens onward from here

		mh.inOrder(mh.popTraversal());

		System.out.println();

		System.out.println("Search for " + 17 + " resulted in " + mh.search(mh.heapArray, 17) + ".");
		System.out.println("Search for " + 27 + " resulted in " + mh.search(mh.heapArray, 27) + ".");
		System.out.println("Search for " + 3 + " resulted in " + mh.search(mh.heapArray, 3) + ".");
		System.out.println("Search for " + 77 + " resulted in " + mh.search(mh.heapArray, 77) + ".");
	}

} // End MaxHeap.java

