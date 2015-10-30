package dijkstra;

@SuppressWarnings("serial")
public class BoardFormatException extends Exception
{
	public BoardFormatException()
	{
		super("Invalid chess board format.");
	}
	
	// Constructor for custom error message
	public BoardFormatException(String message)
	{
		super(message); // Call the parents
						// The exception constructor is the parents
						// super calls constructor of the exception
	}
}
