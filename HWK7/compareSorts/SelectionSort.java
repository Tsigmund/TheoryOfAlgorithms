package compareSorts;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 Selection sort and insertion sort have worst-case time O(N^2).

 Quick sort is also O(N^2) in the worst case, but its expected 
 time is O(N log N).

 Merge sort is O(N log N) in the worst case.

 Selection Sort:

 1. Find the smallest value in A; put it in A[0].
 2. Find the second smallest value in A; put it in A[1].
 3. etc.

 1. Use an outer loop from 0 to N-1 (the loop index, k, 
 	tells which position in A to fill next).
 2. Each time around, use a nested loop (from k+1 to N-1) 
 	to find the smallest value (and its index) in the unsorted 
 	part of the array.
 3. Swap that value with A[k].
 4. Note that after i iterations, A[0] through A[i-1] contain 
 	their final values (so after N iterations, A[0] through A[N-1] 
 	contain their final values and we're done!)

 public static void selectionSort(Comparable[] A)
 {
   int j, k, minIndex;
   Comparable min;
   int N = A.length;

   for (k = 0; k < N; k++)
   {
      min = A[k];
      minIndex = k;
      for (j = k+1; j < N; j++)
      {
         if (A[j].compareTo(min) < 0)
        {
	     min = A[j];
	     minIndex = j;
	 	}
      }
     A[minIndex] = A[k];
     A[k] = min;
   }
 }
 */

public class SelectionSort
{
	int size = 0;
	private int[] ssArray;

	//********************************************************

	public SelectionSort(int size)
	{
		this.size = size;
		ssArray = new int[size];
	}

	//********************************************************

	public void sort(int[] item) 
	{
		int minIndex;
		
		for (int i=0; i < item.length; i++) 
		{
			minIndex = i;
			
			for (int j = (i+1); j < item.length; j++) 
			{
				if (item[j] < item[minIndex])
				{
					minIndex = j;
				}
			}
			
			swap(item, i, minIndex);
		}

	} // End sort(int[])

	//********************************************************

	private static void swap(int[] item, int i, int j)
	{
		int temp = item[i];		// Swap smallest found with element at index i
		item[i] = item[j];
		item[j] = temp;
	}

	//********************************************************

	public int search(int[] items, int target)
	{
		int first = 0;
		int last = (items.length - 1);
		int mid = 0;
		
		boolean found = false;
		while (first <= last && !found)
		{
			mid = (first + last) / 2;

			if (items[mid] == target)
			{
				found = true;
				return target;
			}
			else if (items[mid] > target)
			{
				last = mid - 1;
			}
			else
			{
				first = mid + 1;
			}
		}

		if (!found)
		{
			target = -1; //it is an unsuccessful search
		}
		
		return target;
	}

	//********************************************************

	public int[] createRandomArray()
	{
		// Build 100-element randomized array
		for (int i=0; i < ssArray.length; i++)
		{
			Random rn = new Random(System.nanoTime());	// nanoTime() seed
			int randomInt =  rn.nextInt(100);
			ssArray[i] = randomInt;
		}

		return ssArray;

	} // End createRandomArray()

	//********************************************************

	public void inOrder(int[] items)
	{
		for (int i=0; i < items.length; i++)
		{
			System.out.print(items[i] + " ");
		}
	}

	//********************************************************

	public void printRandomArray(int[] items)
	{
		System.out.println("Random Array:");
		System.out.println("1  2  3  4  5  6  7  8  9  10");
		System.out.println("-  -  -  -  -  -  -  -  -  --");
		for (int i = 0; i < items.length; i++)
		{
			// Break into 10-element wide column
			if (i != 0 && (i % 10) == 0)
			{
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
	} // End printRandomArray()

	public static void main(String[] args)
	{
		SelectionSort ss = new SelectionSort(100);

		System.out.println("//********************************************************");
		System.out.println("// Selection Sort");
		System.out.println("//********************************************************");

		ss.createRandomArray();

		ss.printRandomArray(ss.ssArray);

		long startTime = System.nanoTime();

		ss.sort(ss.ssArray);

		long endTime = System.nanoTime();

		long duration = (endTime - startTime);

		System.out.println("Total insertion time: " + 
		NumberFormat.getNumberInstance(Locale.US).format(duration) + " ms");

		System.out.println();

		System.out.println("Sorted array:");
		ss.inOrder(ss.ssArray);

		System.out.println();
		System.out.println();

		System.out.println("Search for " + 17 + " resulted in " + ss.search(ss.ssArray, 17) + ".");
		System.out.println("Search for " + 27 + " resulted in " + ss.search(ss.ssArray, 27) + ".");
		System.out.println("Search for " + 3 + " resulted in " + ss.search(ss.ssArray, 3) + ".");
		System.out.println("Search for " + 77 + " resulted in " + ss.search(ss.ssArray, 77) + ".");
	}

} // End SelectionSort.java
