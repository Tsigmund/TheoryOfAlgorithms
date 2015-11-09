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

		//System.out.println(path.toString());

		printPath(path);

	} // End main

	//********************************************************

	@SuppressWarnings("resource")
	private static String[] readFromFile(String fileName) throws BoardFormatException
	{
		String[] fileLine = null;

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

			// First line gives dimensions of board
			String[] boardDimension = fileScan.nextLine().split(" ");
			String x = boardDimension[0];
			String y = boardDimension[1];

			rows = Integer.parseInt(x);
			columns = Integer.parseInt(y);

			fileLine = new String[rows];

			while (fileScan.hasNextLine())
			{
				// BoardFormatException if file format doesn't match dimensions
				String line = fileScan.nextLine();
				fileLine[count++] = line;
			}
			
			if (count != rows) { throw new BoardFormatException(); }
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File does not exist.");
		}
		catch (BoardFormatException e)
		{
			System.out.println("Given board dimensions do not match file format.");
		}

		return fileLine;		

	} // End readFromFile(fileName)

	//********************************************************

	// Create (i, j) board (2D array) using (n x m) input
	// And given obstacles from file
	// Return 2D array to be used by Adjacency Matrix
	private static void createBoardArray(String[] fileLine)
	{
		// Creates uninitialized vertex board of certain size
		board = new Vertex[rows][columns];
		String[][] rowVal = new String[rows][columns];

		int row = 0;
		for (String line : fileLine)
		{
			// Fill each board row with char values given from file
			for (int i=0; i < fileLine[row].length(); i++)
			{
				// Each row element is a textfile character, including "."
				rowVal[row][i] = String.valueOf(line.charAt(i));
			}

			row++;

		} // End for r

		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < columns; c++)
			{
				String colVal = rowVal[r][c];

				// Creates a "special" vertex value for non-period characters
				//		AKA Specifies K, T, G vertex values
				Vertex v  = new Vertex(createVertexName(r, c, colVal));

				board[r][c] = v;

				if (isKnight(v))
				{
					start = v;
				}
				else if (isGold(v))
				{
					end = v;
				}

			} // End for c
		}

	} // End createBoard(file)

	//********************************************************

	private static String createVertexName(int row, int col, String value)
	{
		// If blank square
		if (value.equals("."))
		{
			value = "";
		}

		// Test
		//System.out.println("(" + row + "," + col + ") = " + value);

		return (row + "" + col + "" + value);

	} // End createVertexName(row, col, val)

	//********************************************************

	private static void createGraph()
	{
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < columns; c++)
			{
				Vertex v = board[r][c];

				// Test
				//System.out.println(v);

				if (!isTree(v))
				{
					createVertexEdge(r, c, v);
				}

			} // End for c

		} // End for r

	} // End createGraph()

	//********************************************************

	private static void createVertexEdge(int row, int col, Vertex v)
	{
		List<Edge> adjacencies = new ArrayList<Edge>();

		// Check positions starting clockwise @ 1:00

		// 1:00 position
		int rowDest = row - 2;
		int colDest = col + 1;
		if ((rowDest > -1) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		// 2:00
		rowDest = row - 1;
		colDest = col + 2;
		if ((rowDest > - 1) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}


		// 4:00
		rowDest = row + 1;
		colDest = col + 2;
		if ((rowDest < rows) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		// 5:00
		rowDest = row + 2;
		colDest = col + 1;
		if ((rowDest < rows) && (colDest < columns) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		// 7:00
		rowDest = row + 2;
		colDest = col - 1;
		if ((rowDest < rows) && (colDest > - 1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		// 8:00
		rowDest = row + 1;
		colDest = col - 2;
		if ((rowDest < rows) && (colDest > - 1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		// 10:00
		rowDest = row - 1;
		colDest = col - 2;
		if ((rowDest > -1) && (colDest > - 1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		// 11:00
		rowDest = row - 2;
		colDest = col - 1;
		if ((rowDest > -1) && (colDest > - 1) && !isTree(v))
		{
			Vertex vert = board[rowDest][colDest];
			if (!isTree(vert))
			{
				adjacencies.add(new Edge(vert, 0));
			}
		}

		Edge[] adjacenciesArray = new Edge[adjacencies.size()];

		for (int index = 0; index < adjacencies.size(); index++)
		{
			adjacenciesArray[index] = adjacencies.get(index);
		}

		//		if (adjacenciesArray.length != 0)
		v.adjacencies = adjacenciesArray;

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

		StringBuilder sb = new StringBuilder();

		moves.toArray();

		for (int i=0; i < moves.size(); i++)
		{
			//System.out.println(moves.get(i));

			sb.append("(");

			sb.append(moves.get(i).toString().charAt(0));
			sb.append(",");
			sb.append(moves.get(i).toString().charAt(1));
			sb.append(")");

			if (i < moves.size() - 1)
			{
				sb.append("->");
			}
			else	// else (i == moves.size() - 1)
			{
				System.out.println(sb);
				// Our output:
				// (2,2)->(4,1)->(2,0)->(3,2)->(1,1)->(2,3)

				// Given output:
				// (3,3)->(1,2)->(3,1)->(4,3)->(2,2)->(3,4)
			}
		}


	} // End printPath

} // End Knight.java