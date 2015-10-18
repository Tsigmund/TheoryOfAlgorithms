
public class Node {
	
	private double x;
	private double y; 
	private double speedLimit;//Value of speed limit assigned to a node
	
	public Node(double inX, double inY, double inLimit){
		x = inX;
		y = inY;
		speedLimit = inLimit;
	}
	
	//Getters
	public double getX(){return x;}
	public double getY(){return y;}
	public double getSpeedLimit(){return speedLimit;}
	
	//Setters
	public void setX(double inX){x = inX;}
	public void setY(double inY){y = inY;}
	public void setSpeedLimit(double inSpeedLimit){speedLimit = inSpeedLimit;}
	
	//Calculate distance(in time) between two nodes
	public static double timeCost(Node A, Node B){
		
			//Calculate distance between points using distance formula
			double distance = Math.sqrt(Math.pow(Math.abs(A.getX()-B.getX()), 2) + 
								Math.pow(Math.abs(A.getY()-B.getY()), 2));
			
			//Divide distance by lesser of A.speedLimit or B.speedLimit for time
			//Return this value for the travel time from A to B 
			return distance/Math.min(A.getSpeedLimit(), B.getSpeedLimit());
	}
	
	//Returns string representation in form (x, y) of node
	public String toString(){
		return "("+getX()+", "+getY()+")";
	}

}
