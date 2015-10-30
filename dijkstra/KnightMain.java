package dijkstra;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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

// Knight Move/Jump => Edge
// Knight Square/Space/Position/Coordinate => Node/Vertex
// Calculate and return possible moves for Knight

// 1. Pull map from file
// 2. Translate to 2D array of vertices
// 3. Calculate adjacencies from edges of map
// 4. Apply dijkstras

public class KnightMain
{

	private static int rows;
	private static int columns;
	private static Vertex start;
	private static Vertex end;
	private static Vertex[][] board;	

	// Accept input for (n x m) (?)
	public static void main(String[] args)
	{
		String fileName = "KnightFile.txt";

		String[] fileLines = readFromFile(fileName);

		createBoardArray(fileLines);

		createGraph();

		Dijkstra.computePaths(start);

		// List of vertices
		List<Vertex> path = Dijkstra.getShortestPathTo(end);

		printPath(path);

	} // End main

	//********************************************************

	@SuppressWarnings("resource")
	private static String[] readFromFile(String fileName)
	{
		// Default hardcode 10 file lines
		String[] fileLines = new String[10];

		// Each of the next n lines will contain m characters
		// ., K, G, T

		// The board will be given in an input file.
		// * First line of the file will contain 2 integers: n and m.
		// * Each of the next n lines will contain m characters.
		// * jth character of ith line will contain info about c_i,j.
		// * If jth character of ith line is '.', c_i,j is an empty cell.
		// * If j th character of ith line is K, c_i,j contains the knight.
		// * If jth character of ith line is G, c_i,j contains the gold.
		// * If jth character of ith line is T, c_i,j contains a tree.
		// * Input file contains exactly one K and exactly one G characters

		Scanner fileScan;
		try
		{
			fileScan = new Scanner(new FileReader(fileName));
			int count = 0;

			while (fileScan!= null && fileScan.hasNextLine())
			{
				// BoardFormatException if file format doesn't match dimensions
				String line = fileScan.nextLine();
				count++;
				fileLines[count] = line;
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return fileLines;		

	} // End readFromFile(fileName)

	//********************************************************

	// Create (i, j) board (2D array) using (n x m) input
	// And given obstacles from file
	// Return 2D array to be used by Adjacency Matrix
	private static void createBoardArray(String[] lineFile)
	{
		// First line gives dimensions of board
		String[] boardDimension = lineFile[1].split(" ");
		String x = boardDimension[0];
		String y = boardDimension[1];

		// TODO IS THIS CORRECT OR SHOULD IT BE FLIPED?
		// CORRECT
		rows = Integer.parseInt(x);
		columns = Integer.parseInt(y);

		// Creates uninitialized vertex board of certain size
		board = new Vertex[rows][columns];

		// TODO : Possibly need to add 2-squares-thick null border

		for(int r = 0; r < rows; r++)
		{
			// TODO ARE THE VALUES DELIMINATED BY ""?
			// YES
			if (r < rows-1)
			{
				// Fill each board row with values given from file
				String[] rowVal = lineFile[r+1].split("");
				
				// TODO why do we need to initialize the columns?
				// We are not searching for anything . . .
				// Just need columns for jumps don't we?
				for(int c = 0; c < columns; c++)
				{
					// colVal changes with each iteration
					// colVal is temp
					String colVal = rowVal[c];
					Vertex v  = new Vertex(createVertexName(r, c, colVal));
					board[r][c] = v;

					if(isKnight(v))
					{
						start = v;
					}
					else if (isGold(v))
					{
						end = v;
					}
				}
			}
			else break;
		}

	} // End createBoard(file)

	//********************************************************

	private static String createVertexName(int row, int col, String value)
	{
		// If square
		if(value.contentEquals("."))
		{
			value = "";
		}
		return (row + " " + col + value);

	} // End createVertexName(row, col, val)

	//********************************************************

	private static void createGraph()
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < columns; c++)
			{
				Vertex v = board[r][c];

				if(!isTree(v))
				{
					createVertexEdge(r, c, v);
				}
			}
		}

	} // End createGraph()

	//********************************************************

	private static void createVertexEdge(int row, int col, Vertex v)
	{
		List<Edge> adjacencies = new ArrayList<Edge>();

		// Check positions starting clockwise @ 1:00

		// 1:00 position
		int rowDest = row + 1;
		int colDest = col + 2;
		if((rowDest < rows) && (colDest > -1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		// 2:00
		rowDest = row + 2;
		colDest = col + 1;
		if((rowDest < rows) && (colDest > -1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}


		// 4:00
		rowDest = row + 2;
		colDest = col - 1;
		if((rowDest < rows) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		// 5:00
		rowDest = row + 1;
		colDest = col - 2;
		if((rowDest < rows) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		// 7:00
		rowDest = row - 1;
		colDest = col - 2;
		if((rowDest > -1) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		// 8:00
		rowDest = row - 2;
		colDest = col - 1;
		if((rowDest > -1) && (colDest > columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		// 10:00
		rowDest = row - 2;
		colDest = col + 1;
		if((rowDest > -1) && (colDest > -1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		// 11:00
		rowDest = row - 1;
		colDest = col + 2;
		if((rowDest > -1) && (colDest > -1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			adjacencies.add(new Edge(vert, 0));
		}

		if(!adjacencies.isEmpty())
			v.adjacencies = (Edge[]) adjacencies.toArray();

	} // End giveVertexEdges

	//********************************************************

	private static boolean isKnight(Vertex v)
	{
		return v.value.contains("K");
	}

	//********************************************************

	private static boolean isTree(Vertex v)
	{
		return v.value.contains("T");
	}

	//********************************************************

	private static boolean isGold(Vertex v)
	{
		return v.value.contains("G");
	}

	//********************************************************

	public static void printPath(List<Vertex> moves)
	{
		// Each list element is a vertex
		
		// StringBuilder
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < moves.size(); i++)
		{
			// TODO print list vertices
			//System.out.println(moves[i]);
			// if (i != moves.size()-1) {
			sb.append("->");
		}

		// (3,3)->(1,2)->(3,1)->(4,3)->(2,2)->(3,4)
	}

} // End Knight.java
