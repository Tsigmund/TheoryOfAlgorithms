package dijkstra;

import java.util.Scanner;

// Import heap?
// Start out making it generic
// Then tailor it to the program

/*********************************************************
 * Authors: Ericaceous Wood, Trevor Sigmund, Topher Witt
 * Date: 10.12.2015
 * Course: CS433 - Theory of Algorithms
 *********************************************************
 *
 * Given two inputs: startLoc and endLoc.
 * The goal is to then calculate and print the shortest path
 * that the knight can take to get to the target location.
 * 
 * Given n x m (2D) board (2 <= n <= 500, 2 <= m <= 500)
 * Each cell of the board will be identified by (i,j)
 * 
 * ith row, jth column: c_i,j.
 * Top leftmost cell: c_1,1
 * Bottom rightmost cell: c_n,m
 * 
 * Graph: available moves are connected (value=1), and 
 * unavailable moves are disconnected (value=0).
 * 
 * Sparse matrix:
 * (i,j)=1, (i+1,j+2)=1, etc.
 * 
 * Relaxation: Continually reduce d-values (lengths of current
 * shortest paths) down to delta values (length of shortest path)
 * 		d[v] = length of current shortest path from source S to V
 * 		delta(S,V) = length of a shortest path
 * 		pi[v] = predecessor of V in the shortest path from S to V
 * 
 * Relaxation operation:
 * 		relax(u,v,w)
 * 		{
 * 			if (d[v] > (d[u] + w(u,v)))
 * 			then (d[v] = d[u] + w(u,v)) // Found shorter path to vertex d
 * 			pi[v] = u					// Update d-val & predecessor rln
 * 		}
 * 
 * Lemma: The relaxation operation maintains the invariant that
 * 		d[v] >= delta(S,V) for all V in V
 * 
 * Think of distances as shortest path method instead of edge
 * 
 * Greedy Algorithm: (Ball example: Gravity)
 * 
 *********************************************************
 *
 * Dijkstra Pseudocode:
 * 
 * Dijkstra(G, W, s) (Graph, Weights, startingVertex)
 * Initialize (G,s) // Mark s as starting vertex; Set D[s] = 0
 * S (set S) = null
 * Q = V[a] 		// Entire set of vertices
 * 
 * while (Q != null) {		// While set of vertices isn't empty
 * 		u <- EXTRACT_MIN(Q) // Delete u from Q
 * 		S <- S UNION {u}
 * 		for each vertex v in Adj[u]{
 * 			relax(u,v,w)	// Relax
 * 		}
 * 
 * To be more efficient, use min heap when searching for the 
 * 		next shortest distance node.
 *********************************************************/

// 1. Pull map from file
// 2. Translate to 2D array of vertices
// 3. Calculate adjacencies from edges of map
// 4. Apply dijkstras

public class KnightMain
{

	// Accept input for (n x m) (?)
	public static void main(String[] args)
	{
		String fileName = "KnightFile.txt";
		
		readFromFile(fileName);
		printMoves();

	} // End main
	
	//********************************************************

	public static void readFromFile(String fileName)
	{
		// Default hardcode 10 file lines
		String[] fileLine = new String[10];

		// Each of the next n lines will contain m characters
		// ., K, G, T
		// Do we have to test for more than one K, G in file?

		// The board will be given in an input file.
		// * First line of the file will contain 2 integers: n and m.
		// * Each of the next n lines will contain m characters.
		// * jth character of ith line will contain info about c_i,j.
		// * If jth character of ith line is '.', c_i,j is an empty cell.
		// * If j th character of ith line is K, c_i,j contains the knight.
		// * If jth character of ith line is G, c_i,j contains the gold.
		// * If jth character of ith line is T, c_i,j contains a tree.
		// * Input file contains exactly one K and exactly one G characters

		Scanner fileScan = new Scanner(fileName);
		int count = 0;

		while (fileScan != null)
		{
			// BoardFormatException if file format doesn't match dimensions
			String line = fileScan.nextLine();
			count++;
			fileLine[count] = line;
		}
		
		createBoardArray(fileLine);
		
		
	} // End readFromFile(fileName)

	//********************************************************

	// Create (i, j) board (2D array) using (n x m) input
	// And given obstacles from file
	// Return 2D array to be used by Adjacency Matrix
	public static int[][] createBoardArray(String[] lineFile)
	{
		int[][] boardArray = null;
		
		// First line gives dimensions of board
		String[] boardDimension = lineFile[0].split("");
		String x = boardDimension[0];
		String y = boardDimension[1];
		
		// Print i x j Board
		System.out.println(x + " x " + y + " Board.");

		// knightMoves(i, j);
		return boardArray;

	} // End createBoard(file)

	//********************************************************

	// Knight Move/Jump => Edge
	// Knight Square/Space/Position => Node/Vertex
	// Calculate and return possible moves for Knight
	// TODO: This method create Adjacency Matrix
	public int[][] knightMoves(int i,int j)
	{
		/*
		O(1) formula?
		Hard to find the function f( x , y ) that returns the number
		of moves required to go from square (0,0) to square (i,j)
		// if 0 <= j <= i
		// int f( int i , int j )
		{
		    int delta = i - j;

		    if( j > delta )
		        return 2 * ( ( j - delta ) / 3 ) + delta;
		    else
		        return delta - 2 * ( ( delta - j ) / 4 );
		}*/

		int spaces[][] = new int[i][j];
		// Target: Gold
		return spaces;
	} // End knightMoves(i,j)

	//********************************************************

	// 2D array of vertices
	public void computeVertexMatrix(int[][] vertices)
	{
		// loop through array, grab vertex
		// calculate 8 possibilities
		// create edge array with vertices that exist
		// set v0.adjacencies = blabla

	} // End computeVertexMatrix(int[][])


	//********************************************************

	public static void printMoves()
	{
		// StringBuilder
		// (3,3)->(1,2)->(3,1)->(4,3)->(2,2)->(3,4)
	}

} // End Knight.java
