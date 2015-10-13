package dijkstra;

// Import heap?
// Start out making it generic
// Then tailor it to the program

public class BoardGraph
{	
	private final int vertices;
    private int[][] spaces;
    
    public BoardGraph(int v) 
    {
        vertices = v;
        spaces = new int[vertices + 1][vertices + 1];
    }
    
    public void makeEdge(int i, int j, int e) 
    {
        try 
        {
            spaces[i][j] = e;
        }
        catch (ArrayIndexOutOfBoundsException index) 
        {
            System.out.println("The vertices do not exist.");
        }
    } // End makeEdge(i,j,e)
 
    public int getEdge(int i, int j) 
    {
        try 
        {
            return spaces[i][j];
        }
        catch (ArrayIndexOutOfBoundsException index) 
        {
            System.out.println("The vertices do not exist.");
        }
        // No edge found
        return -1;
        
    } // End getEdge(i,j)
    
} // End BoardGraph.java