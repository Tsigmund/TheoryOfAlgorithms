import java.util.Arrays;
import java.util.Random;


public class Divide {
	
	public static int arrayLength = 10;
	public static int minValue = -10;
	public static int maxValue = 10;
	
	public class ArrayValue {
		public int[] array;
		public int low;
		public int high; 
		public int sum;
		public int mid;
		public ArrayValue(int[] a, int l, int h){
			array = a; 
			low = l; 
			high = h;
			mid = (int)Math.floor((high+low)/2);
			sum = 0;
			for(int i = 0; i<a.length; i++)
				sum += a[i];
		}
	}
	
public static void main(String[] args){
	Divide D = new Divide();
	ArrayValue wholeAV = D.generateRandAV();
	ArrayValue maxSubAV = D.findMaxSubArray(wholeAV);
		
	System.out.println("Original array: " + Arrays.toString(wholeAV.array)+ "\n"
				+"Maximum subarray: " + Arrays.toString(maxSubAV.array) );
}
public ArrayValue generateRandAV(){
	 Random generator = new Random(System.currentTimeMillis());
	 int[] array = new int[arrayLength];
	 for(int i=0; i<arrayLength; i++)
		 array[i] = generator.nextInt(maxValue- minValue +1) + minValue;
	 return new ArrayValue(array, 0, arrayLength-1);
}

public ArrayValue findMaxCrossSubArray(ArrayValue av){
	int leftSum = Integer.MIN_VALUE;
	int sum = 0;
	int maxLeft = 0;
	int maxRight = 0; 
	
	for(int i= av.mid; i>=av.low; i--){
		sum += av.array[i];
		if(sum > leftSum){
			leftSum = sum;
			maxLeft = i;
		}//end if
	}//end for
	
	sum = av.array[av.mid+1];
	int rightSum = Integer.MIN_VALUE;
	
	for(int i= av.mid+1; i<=av.high; i++){
		sum += av.array[i];
		if(sum > rightSum){
			rightSum = sum;
			maxRight = i;
		}//end if
	}//end for
	return new ArrayValue(Arrays.copyOfRange(av.array, maxLeft, maxRight+1), maxLeft, maxRight);
}

public ArrayValue findMaxSubArray(ArrayValue av){
	
	if(av.high == av.low)//single value case
		return av;
	
	else{
		//recursion
		ArrayValue left = findMaxSubArray(new ArrayValue(av.array, av.low, av.mid));
		ArrayValue right = findMaxSubArray(new ArrayValue(av.array, av.mid+1, av.high));
		ArrayValue cross = findMaxCrossSubArray(av);
		//base
		if (left.sum >= right.sum && left.sum >= cross.sum)
			return left;
		if (right.sum >= left.sum && right.sum >= cross.sum)
			return right;
		else 
			return cross; 
	}
		
		
	
}

}
