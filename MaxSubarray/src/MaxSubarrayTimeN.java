package TheoryOfAlgorithms.MaxSubarray.src;

import java.util.Arrays;
import java.util.Random;

import TheoryOfAlgorithms.MaxSubarray.src.Divide.ArrayValue;

public class MaxSubarrayTimeN {
	public static int arrayLength = 10;
	public static int minValue = -10;
	public static int maxValue = 10;
	
	public class ArrayValue {
		public int[] array;
		public int low;
		public int high;
		public int sum;
		public int mid;

		public ArrayValue(int[] a, int l, int h) {
			array = a;
			low = l;
			high = h;
			mid = (int) Math.floor((high + low) / 2);
			sum = 0;
			for (int i = 0; i < a.length; i++)
				sum += a[i];
		}
	}
	
	public ArrayValue generateRandAV() {
		Random generator = new Random(System.currentTimeMillis());
		int[] array = new int[arrayLength];
		for (int i = 0; i < arrayLength; i++)
			array[i] = generator.nextInt(maxValue - minValue + 1) + minValue;
		return new ArrayValue(array, 0, arrayLength - 1);
	}
	
	public static void main(String[] args) {
		MaxSubarrayTimeN n = new MaxSubarrayTimeN();
		
		ArrayValue wholeAV = n.generateRandAV();
		ArrayValue maxSubAV = n.findMaxSubarray(wholeAV);
		
		System.out.println("Original array: " + Arrays.toString(wholeAV.array) + "\n" + "Maximum subarray: "
				+ Arrays.toString(maxSubAV.array));
//		System.out.println(maxSubAV.sum);
		

	}
	
	public ArrayValue findMaxSubarray(ArrayValue fullArray){
		int maxSum = 0;
		int thisSum = 0;
		int left = 0;
		int potentialLeft = 0;
		int right = 0;
		
		for(int j=0; j < fullArray.array.length; j++ ){
			thisSum += fullArray.array[j];
			
			if( thisSum > maxSum ){
				right = j;
				left = potentialLeft;
				maxSum = thisSum;
			} else if(thisSum < 0){
				thisSum = 0;
				potentialLeft = j+1;
			}
			
		}
		System.out.println(maxSum);
		
		return new ArrayValue(Arrays.copyOfRange(fullArray.array, left, right+1), left, right);
		
	}

}
