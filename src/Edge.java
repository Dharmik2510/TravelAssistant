

//Class that will hold the edges from the source to its adjacent nodes
public class Edge
{
    private Vertex source;
    private Vertex destination;
    private int travelTime;
    private int travelCost;
    private int weight;
    private String travelMode;
    private boolean isVisited = false;  //Initially it will be false, will be in used when calculating distance in the dijkstra algorithm


    /**
     * @param
     * source = source from where the edge is originated
     * @param
     * destination = destination to which edge terminated
     * @param
     * flightTime = flightTime of this connection
     * @param
     * flightCost = flighcost of this connection
     * @param
     * weight  = weight to travel on this edge/connection
     * @param
     * travelMode = travelmode (fly/train) of this connection
     */

    public Edge(Vertex source, Vertex destination, int flightTime, int flightCost, int weight, String travelMode) {
        this.source = source;
        this.destination = destination;
        this.travelTime = flightTime;
        this.travelCost = flightCost;
        this.weight = weight;
        this.travelMode = travelMode;
    }

    //Getting the destination vertex
    public Vertex getDestination() {
        return destination;
    }

    //setting the destination vertex
    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    //getting the travel time
    public int getTravelTime() {
        return travelTime;
    }

    //setting the travel time
    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    //getting travelcost
    public int getTravelCost() {
        return travelCost;
    }

    //setting travelcost
    public void setTravelCost(int travelCost) {
        this.travelCost = travelCost;
    }

    //getting edge weight
    public int getWeight() {
        return weight;
    }

    //setting edge weight
    public void setWeight(int weight) {
        this.weight = weight;
    }

    //getting the source vertex
    public Vertex getSource() {
        return source;
    }

    //setting the source vertex
    public void setSource(Vertex source) {
        this.source = source;
    }

    //getting the travel mode
    public String getTravelMode() {
        return travelMode;
    }

    //setting the travel mode
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    //getting the  value of isvisited
    public boolean getVisited() {
        return isVisited;
    }

    //setting the value of visited
    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
