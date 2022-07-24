import java.util.*;

public class TravelAssistant
{

    //HashMap that will hold whole graph as an adjacencyList
    private final Map<Vertex, List<Edge>> adjVertices = new HashMap<>();

    //Mapping city name to its object(Vertex)
    private final Map<String,Vertex> nameToVertex = new HashMap<>();

    private ArrayList<Edge> adjCityList = new ArrayList<>();

    //List of vertexes
    private final List<Vertex> nodes = new ArrayList<>();

    //List of edges
    private final List<Edge> edges = new ArrayList<>();

    private List<String> pathstring = new ArrayList<>();



    //Indicate that a particular city is a possible starting point or destination in the travel plans
    /**
     * @param
     *  cityName -       name of the city which should be kept unique.
     * @param
     * testRequired -   true if someone who is unvaccinated requires a negative covid test to
     *                   travel into the city.
     *  @param
     *  timetoTest -     The number of days that it takes to get a covid test result in the city
     *
     *  @param
     * nightlyhotelCost-  The cost of spending one night in a hotel in this city
     *
     *
     * @return true if city added successfully otherwise false
     */

    private boolean addCity(String cityName, boolean testRequired, int timetoTest, int nightlyhotelCost)
    {
        cityName = cityName.toUpperCase();
        if(cityName==null || cityName.isEmpty() || nightlyhotelCost<0 )
        {
            throw new IllegalArgumentException("input parameters are unacceptable");
        }
        else if(nameToVertex.get(cityName)==null)
        {
            Vertex vertex = new Vertex(cityName,testRequired,timetoTest,nightlyhotelCost);
            nodes.add(vertex);
            nameToVertex.putIfAbsent(cityName,vertex);
            adjVertices.putIfAbsent(vertex,new ArrayList<>());
            //System.out.println("City can now be used in the TA");
        }
        else
        {
            System.out.println("City already present in the system");
            return false;
        }
        return true;


    }

    //Record the existence of a one-way flight from the startCity to the destinationCity.
    /**
     * @param
     *  startCity -       City from where the flight will be scheduled
     * @param
     *  destinationCity -Destination city where the flight will arrive
     * @param
     *  flightTime -      Flight time associated with this flight
     * @param
     *  flightCost-       Flight cost associated with this flight
     *
     *
     * @return true if flight added successfully otherwise false
     */
    private boolean addFlight(String startCity, String destinationCity, int flightTime, int flightCost) throws IllegalArgumentException
    {


        startCity = startCity.toUpperCase();
        destinationCity = destinationCity.toUpperCase();

        //Checking for input validation
        if (startCity == null || startCity == "" || destinationCity == null || destinationCity == ""
                || flightCost < 0) {
            throw new IllegalArgumentException("Input parameters are not acceptable");
        }
        if(startCity.equals(destinationCity))
        {
            System.out.println("Source and destination should not be same");
            return false;
        }

        return  addRoute(startCity,destinationCity,flightTime,flightCost,"fly");


    }


    /**
     * @param
     *  startCity -       City from where the train will be scheduled
     * @param
     * destinationCity - Destination city where the train will arrive
     * @param
     * trainTime -      Train time associated with this train
     * @param
     * trainCost-       Train cost associated with this train
     *
     * @return true if flight added successfully otherwise false
     */
    private boolean addTrain( String startCity, String destinationCity, int trainTime, int trainCost)
    {
        startCity = startCity.toUpperCase();
        destinationCity = destinationCity.toUpperCase();

        if (startCity == null || startCity == "" || destinationCity == null || destinationCity == ""
                || trainCost < 0) {
            throw new IllegalArgumentException("Input parameters are not acceptable");
        }

        if(startCity.equals(destinationCity))
        {
            System.out.println("Source and destination should not be same");
            return false;
        }

        return  addRoute(startCity,destinationCity,trainTime,trainCost,"train");

    }


    private boolean addRoute(String startCity, String destinationCity, int flightTime, int flightCost, String travelmode)
    {
        Vertex startCityVertex = nameToVertex.get(startCity);
        Vertex  destCityVertex = nameToVertex.get(destinationCity);

        // Checking if start or destination cities are in the records or not
        if(startCityVertex==null || destCityVertex==null)
        {
            return false;
        }
        else
        {
            //getting adjacent nodes of the startcity
            adjCityList = (ArrayList<Edge>) adjVertices.get(startCityVertex);

            for(Edge edge:adjCityList)
            {
                if(edge.getSource().getCityName().equals(startCity) && edge.getDestination().getCityName().equals(destinationCity) && edge.getTravelMode().equals(travelmode))
                {

                    System.out.println("You can not enter the same route again with the same travel mode so this path is skipped");
                    return false;
                }
            }
            //Creating new edge connection from the source to destination with respective travel mode
            Edge edge = new Edge(startCityVertex,destCityVertex,flightTime,flightCost,0,travelmode);
            adjCityList.add(edge);
            return true;
        }
    }




    /**

     To Determine the sequence of plane, train, and city stays needed to travel from the startCity to the
     destinationCity
     *
     *
     * @param
     * startCity = starting city from where traveler wants to start his/her journey
     * @param
     * destinationCity= City name where traveler wants to travel from startcity
     * @param
     * isVaccinated = boolean value which shows if traveler is vaccinated or not
     * @param
     * costImportance = Importance of cost for this travel provided by the traveler
     * @param
     * travelTimeImportance = Importance of time for this travel provided by the traveler
     * @param
     * travelHopImportance = Importance of hop for this travel provided by the traveler
     *
     * @return
     * Return the list of strings containing the best travel path
     */

    private List<String> planTrip(String startCity, String destinationCity, boolean isVaccinated, int costImportance, int travelTimeImportance, int travelHopImportance)
    {
        startCity = startCity.toUpperCase();
        destinationCity = destinationCity.toUpperCase();

        // validating input params
        if (startCity == null || startCity == "" || costImportance < 0 || travelHopImportance < 0
                || travelHopImportance < 0) {
            throw new IllegalArgumentException("Input parameters are not acceptable");
        }

        if(startCity.equals(destinationCity))
        {
            System.out.println(" you can not plan a trip between same Source and destination name");
            return null;
        }

        // checking if city name is present in map or not
        // if present then return null

        Vertex startCityVertex = nameToVertex.get(startCity);
        Vertex destinationVertex = nameToVertex.get(destinationCity);

        if (startCityVertex == null || destinationVertex == null) {
            return null;
        }

        //checking if traveler is vaccinated or not
        if(isVaccinated)
        {
            for(Map.Entry<Vertex,List<Edge>> mapEntry:adjVertices.entrySet())
            {
                List<Edge> edgeList = adjVertices.get(mapEntry.getKey());
                for(Edge edge: edgeList)
                {
                    //getting each edge connection
                    try {
                        int weight = calculateWeightOfEdge(edge.getTravelCost(),edge.getTravelTime(),costImportance,travelTimeImportance,travelHopImportance);
                        //setting the edge weight
                        edge.setWeight(weight);
                        edge.getDestination().setWeightToTravelMode(weight,edge.getTravelMode());
                        //adding the weighted edge to the list
                        edges.add(edge);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

            }

            //Initializing graph object from the with the list of nodes and list of edges
            Graph graph = new Graph(nodes, edges);

            //passing graph to find the shortest path
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

            //getting the index of the start node
            int index = nodes.indexOf(startCityVertex);

            //getting index of the destination node
            int destinationIndex = nodes.indexOf(destinationVertex);

            //calling execute method to start finding the min distance
            dijkstra.execute(nodes.get(index));

            //String variable which will hold the string of paths
            String pathS;

            //getpath method will return the list of vertex
            LinkedList<Vertex> path = dijkstra.getPath(nodes.get(destinationIndex));

            for (Vertex vertex : path) {

                if(vertex.getTravelMode()==null)
                {
                    pathS = "Start " + vertex.getCityName();
                    pathstring.add(pathS);

                }
                else
                {
                    pathS = vertex.getTravelMode() +" " + vertex.getCityName();
                    pathstring.add(pathS);

                }

            }
            return pathstring;
        }
        else
        {
            System.out.println("Non vaccinated functionality yet to be implemented, mentioned in the limitation part of external documentation");
            return null;
        }

    }



    //Calculating edge weight according to given user inputs( travelImportances)
    /**
     * @param
     * flightCost = flightcost of the corresponding edge
     * @param
     * flightTime = flightTime of the corresponding edge
     * @param
     * costImp = costImportance of the corresponding edge
     * @param
     * travelImp = travelImportance of the corresponding edge
     * @param
     * travelHopImp = travelHopImportance of the corresponding edge
     *
     * @return
     * will return the weight of the corresponding edge
     */

    private int calculateWeightOfEdge(int flightCost, int flightTime, int costImp, int travelImp, int travelHopImp)
    {
        return (flightCost * costImp)  + ( flightTime * travelImp ) + (travelHopImp);
    }


    private static List<String> testCaseOne()
    {
        TravelAssistant travelAssistant = new TravelAssistant();
        //Adding cities to the travelAssistant
        travelAssistant.addCity("A",true,2,200);
        travelAssistant.addCity("B",true,2,500);
        travelAssistant.addCity("C",true,5,500);

        travelAssistant.addFlight("A","B",2,2);
        travelAssistant.addFlight("B","C",1,1);
        travelAssistant.addFlight("A","C",8,10);
        travelAssistant.addTrain("B","C",4,8);

        List<String> path =travelAssistant.planTrip("A","C",true,1,1,1);
        return path;
    }

    private static List<String> testCaseTwo()
    {
        TravelAssistant travelAssistant = new TravelAssistant();
        //Adding cities to the travelAssistant
        travelAssistant.addCity("A",true,2,200);
        travelAssistant.addCity("B",true,2,500);
        travelAssistant.addCity("C",true,5,500);

        //----------------------------------------- Second test Scenarios ------------------------------------------
        travelAssistant.addFlight("A","B",2,2);
        travelAssistant.addTrain("B","C",1,1);
        travelAssistant.addFlight("A","C",8,10);
        travelAssistant.addTrain("B","C",4,8);

        List<String> path =travelAssistant.planTrip("A","C",true,1,1,1);
        return path;
    }


    public static void main(String[] args) throws IllegalArgumentException {



        List<String>  pathOne = testCaseOne();
        System.out.println("Shortest path for testcaseOne is: -->  ");
        System.out.println(pathOne);

        List<String > pathTwo = testCaseTwo();
        System.out.println("Shortest path for testcaseTwo is: -->  ");
        System.out.println(pathTwo);



    }

}
