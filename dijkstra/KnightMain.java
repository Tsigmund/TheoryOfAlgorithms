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

	public static void main(String[] args) throws BoardFormatException
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
	private static String[] readFromFile(String fileName) throws BoardFormatException
	{
		// Default hardcode 10 file lines
		String[] fileLine = new String[10];

		// Each of the next n lines will contain m characters.
		// jth character of ith line will contain info about c_i,j.
		// If jth character of ith line is '.', [i][j] is an empty cell.
		// If jth character of ith line is K, [i][j] contains the knight.
		// If jth character of ith line is G, [i][j] contains the gold.
		// If jth character of ith line is T, [i][j] contains a tree.
		// Input file contains exactly one K and exactly one G characters

		Scanner fileScan;
		try
		{
			fileScan = new Scanner(new FileReader(fileName));
			int count = 0;

			while (fileScan.hasNextLine())
			{
				// BoardFormatException if file format doesn't match dimensions
				String line = fileScan.nextLine()/*.trim()*/;
				fileLine[count++] = line;
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File does not exist.");
		}
		catch (Exception e)
		{
			// File does not follow specific rules for Knight Board
			throw new BoardFormatException();
		}

		return fileLine;		

	} // End readFromFile(fileName)

	//********************************************************

	// Create (i, j) board (2D array) using (n x m) input
	// And given obstacles from file
	// Return 2D array to be used by Adjacency Matrix
	private static void createBoardArray(String[] fileLine)
	{

		// First line gives dimensions of board
		String[] boardDimension = fileLine[0].split(" ");
		String x = boardDimension[0];
		String y = boardDimension[1];

		rows = Integer.parseInt(x);
		columns = Integer.parseInt(y);

		// Creates uninitialized vertex board of certain size
		board = new Vertex[rows][columns];

		// TODO: Possibly need to add 2-squares-thick null/tree border
		// in order to avoid erroneous edges.
		// 		[1] Increase 2D array to 9x9
		// 		[2a] Row 0,1,8,9 completely null / "T"
		// 		[2b] Col 0,1,8,9 completely null / "T"

		for (int r = 1; r < rows; r++)
		{
			// A 2D array is required to pull out characters individually
			// 		Because Scanner does not have a nextChar() method.
			// 		InputStreamReader would allow that, however.
			String[][] rowVal = new String[5][5];
			
			// Fill each board row with char values given from file
			for (int i=0; i < fileLine[r].length(); i++)
			{
				// Each row element is a textfile character, including "."
				rowVal[r][i] = String.valueOf(fileLine[r].charAt(i));
				// Test
				//System.out.print(rowVal[r][i]);
			}
			
			// Used in conjunction with Test print above
			// Newline for rowVal test print
			//System.out.println();

			for (int c = 0; c < columns; c++)
			{
				String colVal = rowVal[r][c];
				
				// Test that column values are filled properly
				//System.out.print(colVal);

				// Creates a "special" vertex value for non-period characters
				//		AKA Specifies K, T, G vertex values
				Vertex v  = new Vertex(createVertexName(r, c, colVal));
				
				// Test vertex values
				//System.out.println(v);

				board[r][c] = v;

				if(isKnight(v))
				{
					start = v;
				}
				else if (isGold(v))
				{
					end = v;
				}

			} // End for c

			// Newline for colVal test print
			System.out.println();


		} // End for r

	} // End createBoard(file)

	//********************************************************

	private static String createVertexName(int row, int col, String value)
	{
		// If blank square
		if (value.equals("."))
		{
			value = "";
		}

		return ("(" + row + ", " + col + "): " + value);

	} // End createVertexName(row, col, val)

	//********************************************************

	private static void createGraph()
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < columns; c++)
			{
				Vertex v = board[r][c];
				
				// Test
				//System.out.println(v);

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
		return v.value.equals("K");
	}

	//********************************************************

	private static boolean isTree(Vertex v)
	{
		// FIXME tried contains(), contentEquals(), v.value.trim(), etc.
		return v.value.contentEquals("T");
	}

	//********************************************************

	private static boolean isGold(Vertex v)
	{
		return v.value.equals("G");
	}

	//********************************************************

	public static void printPath(List<Vertex> moves)
	{
		// Each list element is a vertex

		// StringBuilder
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < moves.size(); i++)
		{
			moves.toArray();

			System.out.println(moves.get(i));

			System.out.println("(" + moves + ")");
			
			if (i < moves.size())
			{
				sb.append("->");
			}
		}

		// (3,3)->(1,2)->(3,1)->(4,3)->(2,2)->(3,4)
	}

} // End Knight.java
