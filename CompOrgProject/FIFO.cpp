/* Trevor Sigmund
*	12/11/15 
*	FIFO Page Replacement algorithm
*
*   Implement FIFO, user specifies number of frames, and 
*	User enters 10 page requests. Assume memory empty.
*	Program outputs state of main memory w/ each new
*   request and also the number of page faults
*/

#include <iostream>
#include <iomanip>
using namespace std;

int insertPage(int*, int*, int, int);
void printArray(int*, int, string);
void increment(int*, int);

int main(){

	int numFrames = 0;
	const int NUMPAGEREQ = 10;
	int pages[NUMPAGEREQ];

	while(true){
		//Get num frames
		cout << "Enter number of frames: ";
		cin >> numFrames;
		cout << endl;

		if (numFrames <= 0){
			cout << "Please enter at least one frame.";
			continue;
		}

		//Get page requests
		for(int i = 0; i < NUMPAGEREQ; i++){
			cout << "Enter page request " << i << " (positive integers): ";
			cin >> pages[i];
			if(pages[i] < 0)
				pages[i] = pages[i]*-1;
			cout << endl;
		}

		break;//exit loop	
	}
	//initialize frames
	int frames[numFrames];
	//counter keeps track of age of each frame
	int counter[numFrames];
	for(int i=0; i<numFrames; i++){
		frames[i] = -1;
		counter[i] = 0;
	}

	//Display initial frames
	cout << "InitialFrames:" << endl;
	printArray(frames, numFrames, "Frames: ");
	cout << endl;
	
	//Insert pages
	int faults = 0;
	for(int i=0; i<NUMPAGEREQ; i++){
		cout << "Inserting page: " << pages[i] << endl;
		//Insert page and check for a fault
		if (insertPage(frames, counter, numFrames, pages[i]) == 1){
			faults++;
			cout << "Page Fault. Total Faults: " << faults << endl;
		}
		else cout << "No Fault. Total Faults: " << faults << endl;
		printArray(frames, numFrames, "Frames: ");
		printArray(counter, numFrames, "Age: ");
		cout << endl;
	}
	cout << "Total Faults: " << faults << endl;

}
//Increment an array of integers
void increment(int* array, int length){
	for(int i=0; i<length; i++)
		array[i]++;
}

//insert page
int insertPage(int* frames, int* counter, int numFrames, int page){

	for(int i=0; i<numFrames; i++){
		if(frames[i] == page){
			increment(counter, numFrames);
			return 0; //Signify no page fault
		}
		//If empty frame, add page to first one
		if (frames[i] < 0){
			frames[i] = page;
			//increment all initialized frames
			for(int j = i; j >= 0; j--){
				counter[j]++;
			}
			return 1; //Signify page fault
		}
	}

	//Otherwise, replace first frame added(oldest counter)
	int maxCount = counter[0], oldestPosition = 0;
	for(int i=0; i<numFrames; i++){
		if(maxCount < counter[i]){
			maxCount = counter[i];
			oldestPosition = i;
		} 
	}

	//Increment counter
	increment(counter, numFrames);

	//Insert page
	frames[oldestPosition] = page; 
	counter[oldestPosition] = 0;
	return 1; //Signify page fault
}

//Print an integer array, one line with header, sep by spaces
void printArray(int* array, int length, string header){
	cout << header;
	for(int i=0; i<length; i++){
		if(array[i] < 0)
			cout << "EMPTY ";
		else
			cout << array[i] << " ";
	}

	cout << endl;
}
