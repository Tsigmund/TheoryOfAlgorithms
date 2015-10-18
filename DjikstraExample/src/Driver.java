/*Trevor Sigmund
 * Project 2 Mobile Computing
 * Uses Node.java, Dijkstra.java
 * 
 * Dijikstra.java taken from website: http://www.algolist.com/code/java/Dijkstra's_algorithm
 * 
 * Program computes the shortest path from an input coordinate to another input coordinate and prints the path and distance
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver {
	
	//All possible intersections/endpoints
	public static ArrayList<Node> nodeList = new ArrayList<Node>(Arrays.asList(
			new Node(4, 0,45), //0 
			new Node(8, 0,35), //1 
			new Node(0,2, 55), //2 
			new Node(4,2,55), //3  
			new Node(8,2,55), //4  
			new Node(10,2,55),//5  
			new Node(0,4,35), //6  
			new Node(4,4,45), //7  
			new Node(8,4,35), //8  
			new Node(10,4,35), //9 
			new Node(12,4,35),//10 
			new Node(0,5,75), //11 
			new Node(4,5,75), //12 
			new Node(6,5,75), //13
			new Node(8,5,75), //14 
			new Node(10,5,75), //15 
			new Node(12,5,75), //16 
			new Node(0,10,35), //17 
			new Node(4,10,45), //18 
			new Node(6,10,35), //19 
			new Node(8,10,35),//20  
			new Node(10,10,35), //21
			new Node(4,14,45), //22 
			new Node(6,14,35), //23 
			new Node(8,14,35), //24 
			new Node(12,14,35),//25 
			new Node(0,16,35), //26 
			new Node(4,16,35), //27 
			new Node(8,16,35), //28
			new Node(12,16,35)));//29
		
	//List of connections. In each array: {Host node, Top, Right, Bottom, Left} Connections for host node
	public static Node[][] adjacencyMatrix = { 
			{nodeList.get(0), nodeList.get(3), nodeList.get(1), null, null},
			{nodeList.get(1), nodeList.get(4), null, null, nodeList.get(0)},
			{nodeList.get(2), null, nodeList.get(3), null, null},
			{nodeList.get(3), nodeList.get(7), nodeList.get(4), nodeList.get(0), nodeList.get(2)},
			{nodeList.get(4), nodeList.get(8), nodeList.get(5), nodeList.get(1), nodeList.get(3)},
			{nodeList.get(5), nodeList.get(9), null, null, nodeList.get(4)},
			{nodeList.get(6), nodeList.get(11), nodeList.get(7), null, null},
			{nodeList.get(7), nodeList.get(12), nodeList.get(8), nodeList.get(3), nodeList.get(6)},
			{nodeList.get(8), nodeList.get(14), nodeList.get(9), nodeList.get(4), nodeList.get(7)},
			{nodeList.get(9), nodeList.get(15), nodeList.get(10), nodeList.get(5), nodeList.get(8) },
			{nodeList.get(10), null, null, null, nodeList.get(9)},
			{nodeList.get(11), nodeList.get(17), nodeList.get(12), nodeList.get(6), null},
			{nodeList.get(12), nodeList.get(18), nodeList.get(13), nodeList.get(7), nodeList.get(11)},
			{nodeList.get(13), nodeList.get(19), nodeList.get(14), null, nodeList.get(12)},
			{nodeList.get(14), null, nodeList.get(15), nodeList.get(8), nodeList.get(13)},
			{nodeList.get(15), nodeList.get(21), nodeList.get(16), nodeList.get(9), nodeList.get(14)},
			{nodeList.get(16),  null, null, null, nodeList.get(15)},
			{nodeList.get(17), nodeList.get(26), nodeList.get(18), nodeList.get(11), null},
			{nodeList.get(18), nodeList.get(27), nodeList.get(19), nodeList.get(12), nodeList.get(17)},
			{nodeList.get(19), nodeList.get(23), nodeList.get(20), nodeList.get(13), nodeList.get(18)},
			{nodeList.get(20), nodeList.get(24), nodeList.get(21), null, nodeList.get(19)},
			{nodeList.get(21), null, null, nodeList.get(15), nodeList.get(20)},
			{nodeList.get(22), nodeList.get(27), nodeList.get(23), nodeList.get(18), null},
			{nodeList.get(23), null, nodeList.get(24), nodeList.get(19), nodeList.get(22)},
			{nodeList.get(24), nodeList.get(28), nodeList.get(25), nodeList.get(20), nodeList.get(23)},
			{nodeList.get(25), null, null, null, nodeList.get(24)},
			{nodeList.get(26), null, nodeList.get(27), nodeList.get(17), null},
			{nodeList.get(27), null, nodeList.get(28), nodeList.get(22), nodeList.get(26)},
			{nodeList.get(28), null, nodeList.get(29), nodeList.get(24), nodeList.get(27)},
			{nodeList.get(29), null, null, null, nodeList.get(28)},
			{null, null, null, null, null}, //Start point
			{null, null, null, null, null}  //End point
	};//End array
	
	public static ArrayList<Vertex> vertexMap = new ArrayList<Vertex>();
	
	public static void buildVertexMap(){
		for(Node n: nodeList){
			vertexMap.add(new Vertex(n.toString()));
		}
		
		ArrayList<Edge> tempAdjacencies = new ArrayList<Edge>();
		
		for(Vertex v: vertexMap){
			for(int i = 0; i<adjacencyMatrix.length; i++){
				
				if(v.name.equals(adjacencyMatrix[i][0].toString())){
					
					for(int k = 0; k < adjacencyMatrix[i].length; k++){
						
						if(adjacencyMatrix[i][0] != null){
							
							for(Vertex adjV: vertexMap){
								
								if(adjacencyMatrix[i][k] != null)
									if(adjV.name.equals(adjacencyMatrix[i][k].toString()))
										tempAdjacencies.add(new Edge(adjV, Node.timeCost(adjacencyMatrix[i][0], adjacencyMatrix[i][k])));
							}//end for adjV
						}//end if != null
					}//end for k
					
					break;//exit loop if found name match
				}//end if v.name
			}//end for i
			
			v.adjacencies = tempAdjacencies.toArray(new Edge[0]);//Set adjacencies for current vertex
			tempAdjacencies = new ArrayList<Edge>(); //Clear temporary adjacency ArrayList
		}//end for v
				
		
	}
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter starting position:");
		Node startN = getUserNode(s);
		System.out.println("Enter ending position:");
		Node endN = getUserNode(s);

		buildVertexMap();
		
		Vertex startV = null, endV = null;
		//Find vertex representations of the nodes
		for(Vertex v:vertexMap){
			if(v.name.equals(startN.toString()))
				startV = v;
			if(v.name.equals(endN.toString()))
				endV = v;
		}
		
		//Compute paths for start node
		Dijkstra.computePaths(startV);
		
		//Get shortest path to end node 
		List<Vertex>  path = Dijkstra.getShortestPathTo(endV);
		
		System.out.println("The shortest path from "+startV.name+" to "+endV.name+" is: "+path);
		
		s.close();
	}

	public static Node getUserNode(Scanner s){
		double x = 0, y = 0;
		Node inNode;
		boolean validDouble = false;
		//Loop until get valid input
		while(true){
		//Get and validate user input
			validDouble = false;
			
			while(validDouble == false){
			System.out.println("X coordinate:");
			if(s.hasNextDouble()){
				x = s.nextDouble();
				validDouble = true;
			}
			else{
				System.out.println("Invalid. Please enter a double value:");
				s.next();
				validDouble = false;
			}
		}
			validDouble = false;
			
			while(validDouble == false){
			System.out.println("Y coordinate:");
			if(s.hasNextDouble()){
				y = s.nextDouble();
				validDouble = true;
			}
			else{
				System.out.println("Invalid. Please enter a double value:");
				s.next();
				validDouble = false;
			}
		}
		
		inNode = new Node(x, y, 0);
		
		
		//If input node is an intersection, return that node
		for(Node n: nodeList){
			if(inNode.getX() == n.getX() && inNode.getY() == n.getY()){
				return n;
			}
		}
		//If not an intersection, check if new node is on a road
		
		
		//A node on a road that is not an intersection must have two adjacent nodes.
		Node a1 = null, a2 = null;
	
		for(Node[] nArray: adjacencyMatrix){
			for(Node n: nArray){
				//If not null entry,
				if(nArray[0] != null && n != null){
					//If node's x coordinate is between the x coordinates of an existing node and it's adjacent,
					if(inNode.getX() <= Math.max(nArray[0].getX(), n.getX()) && inNode.getX() >= Math.min(nArray[0].getX(), n.getX()))
						//and it's y coordinate is between the y coordinates of an existing node and it's adjacent, 
						if(inNode.getY() <= Math.max(nArray[0].getY(), n.getY()) && inNode.getY() >= Math.min(nArray[0].getY(), n.getY())){
							//set speed limit of new node to minimum of adjacent nodes
							inNode.setSpeedLimit(Math.min(nArray[0].getSpeedLimit(), n.getSpeedLimit()));
							//Adjacent nodes
							a1 = nArray[0];
							a2 = n;
							
						}//end if y
				}//end if != null
				
				}
			}
		//If a valid node, 
		if(a1 != null && a2!= null){
			nodeList.add(inNode);
			
			for(Node[] n: adjacencyMatrix){
				//If null, replace with new node, break loop
				if(n[0] == null){
					n[0] = inNode;
					n[1] = a1;
					n[2] = a2;
					break;
				}
				//otherwise
				else{
					//If a1, update adjacencies
					if(n[0].equals(a1)){
						//If node is on same x position
						if(inNode.getX() == n[0].getX()){
							//Node is above a1, 
							if(inNode.getY() > n[0].getY())
								n[1] = inNode;
							//Node is below a1
							else 
								n[3] = inNode;
						}
						//If node not on same x position, must be same y position
						else {
							//Node to right of a1
							if(inNode.getX() > n[0].getX())
								n[2] = inNode;
							//Node is below a1
							else
								n[4] = inNode;
						}
					}
					
					//If a2, update adjacencies
					if(n[0].equals(a2)){
						//If node is on same x position
						if(inNode.getX() == n[0].getX()){
							//Node is above a2, 
							if(inNode.getY() > n[0].getY())
								n[1] = inNode;
							//Node is below a2
							else 
								n[3] = inNode;
						}
						//If node not on same x position, must be same y position
						else {
							//Node to right of a2
							if(inNode.getX() > n[0].getX())
								n[2] = inNode;
							//Node is below a2
							else
								n[4] = inNode;
						}
					}
				}//end else == null
				
			}
			return inNode; 
		}
		System.out.println("That position is not on a valid road. Please enter a new location.");
		
		}//end while
	}//end getUserInput()
	
}//end Driver class
