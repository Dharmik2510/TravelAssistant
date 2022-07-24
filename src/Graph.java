import java.util.List;

public class Graph {

    //Graph that will contain list of vertexes as well as edges
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    /**
     * @param
     * vertexes = This will hold the all the vertices of the graph
     * @param
     * edges = This will hold the all the edges of the graph that is all the connections
     */

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    //Getting vertexes of the graph
    public List<Vertex> getVertexes() {
        return vertexes;
    }

    //Getting edges of the graph
    public List<Edge> getEdges() {
        return edges;
    }

}


