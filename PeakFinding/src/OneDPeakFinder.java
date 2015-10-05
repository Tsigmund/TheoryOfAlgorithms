import java.util.Random;
import java.util.Arrays;

/**
 * 1D peak finding program.
 * @author Topher Witt & Trever Sigmund
 *
 */
public class OneDPeakFinder {
	private final static int arrayLength = 10;
	private final static int maxNumSize = 40;
	private final static int minNumSize = 0;
	private static int[] oneDArray = new int[arrayLength];

	/**
	 * Main method fills an array with random integers
	 * then searches through it for a local peak.
	 * @param args
	 */
	public static void main(String[] args) {
		intilaize();
		printArray();
		
		int firstIndex = 0;
		
		int lastIndex = arrayLength-1;
		
		// find and print local peak value
		System.out.println(peak1D(oneDArray, firstIndex, lastIndex));
	}
	
	/**
	 * Recursively find a local peak.
	 */
	private static int peak1D(int[] array, int currentI, int endI){		
		// middle index
		int midI = (int)Math.floor( (currentI + endI) / 2.0 );
		
		System.out.println("index: "+midI);
		
		int midVal = array[midI];
		int prevI = midI - 1;
		int nextI = midI + 1;
		
		// handle edge cases
		if(prevI <= -1 && midVal >= array[nextI]){
			// hit the left end
			return midVal;
		} else if (nextI >= array.length && midVal >= array[prevI]){
			// hit the right end
			return midVal;
		}
		
		int prevVal = array[prevI];
		int nextVal = array[nextI];

		// found a local peak
		if( prevVal <= midVal && midVal >= nextVal){
			return midVal;
		} else if (prevVal > midVal){
			// not found so recurse on larger neighbor
			return peak1D(array, currentI, midI - 1);
		} else if( midVal < nextVal ){
			// not found so recurse on larger neighbor
			return peak1D(array, midI + 1, endI);
		} else {
			// error
			return -2;
		}
		
	}
	
	/**
	 * Fill the 1D array with random integers.
	 */
	private static void intilaize(){
		Random random = new Random();
		for(int index = 0; index < arrayLength; index++){
			oneDArray[index] = random.nextInt(maxNumSize - minNumSize + 1) + minNumSize;
		}
	}

	/**
	 * Print the values of the array.
	 */
	private static void printArray(){
		System.out.println(Arrays.toString(oneDArray));
	}
}
