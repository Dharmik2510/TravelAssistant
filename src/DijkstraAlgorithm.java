import java.util.*;

//Class that will calculate shortest path
public class DijkstraAlgorithm
{

    //List of nodes
    private final List<Vertex> nodes;

    //List of edges
    private final List<Edge> edges;

    //Set that will hold the settled/visited vertices
    private Set<Vertex> settledNodes;

    //Set that will hold unsettled/unvisited vertices
    private Set<Vertex> unSettledNodes;

    //Map to store the predecessors of the vertices
    private Map<Vertex, Vertex> predecessors;

    //Map to store the distance of the vertex
    private Map<Vertex, Integer> distance;

    /**
     * @param
     * graph = graph containing nodes and edges
     */

    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }


    /**
     * Call to this method will start the calculation of the shortespath
     * @param
     * source = source from where traveler wants to start his/her journey
     */
    public void execute(Vertex source)
    {
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessors = new HashMap<Vertex, Vertex>();

        //adding the source distance equals to zero
        distance.put(source, 0);

        //adding first that is source in the unsettlednodes
        unSettledNodes.add(source);

        //The algorithms runs until the unsettledNodes are empty.
        // In each iteration it selects the node with the lowest distance from the source out of the unsettled nodes.
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }

    }

    /**
     * node in the parameter is now being visited to find the min distance edge/connection
     *
     * @param
     * node = this node is the node present in the set of unsettledNodes
     *
     */

    private void findMinimalDistances(Vertex node) {
        List<String> cityName = new ArrayList<>();
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {


            Integer dis = (Integer)getDistance(node,target);


            //calculating the distance and if initial distance of target is greater than the obtained distance
            //set the new distance to the obtained distance + distance from the node
            if (getShortestDistance(target) > getShortestDistance(node)
                    + dis)
            {

                distance.put(target,getShortestDistance(node) + dis);
                String travelMode = target.weightToTravelMode.get(dis);

                target.setTravelMode(travelMode);

                //adding the predecessors of the target in the predecessors map
                predecessors.put(target, node);

                //Adding the unvisited current node to the unsettlednodes
                unSettledNodes.add(target);

            }
        }
    }

    /**
     * It reads all edges which are outgoing from the source and evaluates for each destination node,
     * in the edges which are not yet settled, if the known distance from the source to this node
     * can be reduced while using the selected edge.
     *
     *
     * @param
     * node = source node from where to calculate the distance
     * @param
     * target = target node to where calculate the distance
     * @return
     * Return the edge weight/distance
     */

    private int getDistance(Vertex node, Vertex target)
    {
        for (Edge edge : edges)
        {

            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target))
            {

                //if edge already visited then continue and get out from the if condition
                if(edge.getVisited())
                {
                    continue;
                }
                else
                {
                    //set this edge visited
                    //return the weight of the edge
                    edge.setVisited(true);
                    return edge.getWeight();
                }

            }
        }

        throw new RuntimeException("Should not happen");
    }


    /**
     * @param
     * node = getting the neighbour node/vertex of the given node/vertex
     * @return
     * Return the list of neighbour nodes
     */

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        //Loop through the neighbour edges
        //And checking if destination of this is settled or not
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());

            }
        }
        return neighbors;
    }


    /**
     * @param
     * vertexes = get minimum vertex from the give n set of vertexes
     */
    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    /**
     * @param
     * vertex = to check if this vertex is settled/visited or not
     * @return
     * Return true if visited/settled otherwise false
     */
    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }


    /**
     * @param
     * destination = get the shortest distance of the destination
     *
     */
    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }


    /*
     * This method returns the path from the source to the selected target and
     * null if no path exists
     */

    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }


}
