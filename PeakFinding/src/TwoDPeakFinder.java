import java.util.Random;

/**
 * 2D peak finding program.
 * @author Topher Witt & Trever Sigmund
 *
 */
public class TwoDPeakFinder {
	private final static int rowLength = 10;
	private final static int colLength = 10;
	private final static int maxNumSize = 40;
	private final static int minNumSize = 0;
	private final static int[][] twoDarray = new int[rowLength][colLength];

	/**
	 * Main method runs the program, filling the
	 * 2D array with values and finding a peak.
	 * @param args
	 */
	public static void main(String[] args) {
		intilaize();
		printArray();
		
		int firstIndex = 0;
		int lastIndex = colLength - 1;
		// find and print local peak value
		System.out.println(peak2D(twoDarray, firstIndex, lastIndex));
	}
	
	/**
	 * Recursively find a local peak value.
	 * 
	 * @param array
	 * @param firstColI
	 * @param endColI
	 * @return local peak value
	 */
	private static int peak2D(int[][] array, int firstColI, int endColI){
		// middle index
		int midColI = (int)Math.floor( (firstColI + endColI) / 2.0 );
		int indexColLeft = midColI-1;
		int indexColRight = midColI+1;
		
		System.out.println("mid column index: "+midColI);
		
		int[] midColArray = getColumn(midColI);
		
		int rowIndexOfMax = indexOfMaxVal(midColArray);
		
		int maxVal = midColArray[rowIndexOfMax];
		// handle edge cases
		if(midColI == 0 && maxVal >= array[rowIndexOfMax][midColI + 1]){
			// left edge and a peak
			return maxVal;
		} else if(midColI == colLength - 1 && maxVal >= array[rowIndexOfMax][colLength-2]){
			// right edge and a peak
			return maxVal;
		} else if(midColI == 0){
			// left edge but not a peak
			return peak2D(array, midColI + 1, endColI);
		} else if(midColI == colLength - 1){
			// right edge but not a peak
			return peak2D(array, firstColI, colLength-2);
		}
		
		int leftVal = array[rowIndexOfMax][indexColLeft];
		int rightVal = array[rowIndexOfMax][indexColRight];
		
		if(maxVal >= leftVal && maxVal >= rightVal){
			// this is a peak
			return maxVal;
		} else if(rightVal > maxVal){
			// not a peak, recurse
			return peak2D(array, indexColRight, endColI);
		} else if(leftVal > maxVal){
			// not a peak, recurse
			return peak2D(array, firstColI, indexColLeft);
		}  else {
			// error
			return -1;
		}
		
	}
	
	/**
	 * Find the index of the global max value of the array.
	 * @param array
	 * @return int index
	 */
	private static int indexOfMaxVal(int[] array){
		int indexOfMax = 0;
		int maxVal = array[indexOfMax];
		
		// get max value from array
		for(int index = 1; index < colLength; index++){
			int val = array[index];
			if(val > maxVal){
				maxVal = val;
				indexOfMax = index;
			}
		}
		
		return indexOfMax;
	}
	
	/**
	 * Grab a column from the 2D array
	 * @param columnIndex
	 * @return array
	 */
	private static int[] getColumn(int columnIndex){
		int[] array = new int[rowLength];
		// for each row, grab value from the chosen column
		for(int rowIndex = 0; rowIndex < rowLength; rowIndex++){
			array[rowIndex] = twoDarray[rowIndex][columnIndex];
		}
		return array;
	}
	

	/**
	 * Fill the 2D array with random integers.
	 */
	private static void intilaize(){
		Random random = new Random();
		// for each row and column
		for(int index = 0; index < colLength; index++){
			for(int index2 = 0; index2 < rowLength; index2++){
				twoDarray[index][index2] = random.nextInt(maxNumSize - minNumSize + 1) + minNumSize;
			}
		}
	}

	/**
	 * Print the values of the array.
	 */
	private static void printArray(){
		// for each row and column
		for(int rowIndex = 0; rowIndex < rowLength; rowIndex++){
			for(int colIndex = 0; colIndex < colLength; colIndex++){
			
				System.out.print(twoDarray[rowIndex][colIndex]+"  ");
			}
			System.out.println();
			System.out.println();
		}
	}
	
}
