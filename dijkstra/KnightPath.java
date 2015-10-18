package dijkstra;

// Shortest path

public class KnightPath
{
	//********************************************************

	// Create (i, j) board (2D array) using (n x m) input
	// And given obstacles from file
	public static void createBoard(String[] lineFile)
	{
		// knightMoves(i, j);
		// call knightMoves()
	} // End createBoard(file)

	//********************************************************

	// Knight move to another position - "Edge"
	// Knight position - Node/Vertex
	// Calculate and return possible moves for Knight
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
	}
}
