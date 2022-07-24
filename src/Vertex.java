import java.util.HashMap;

//Class that will hold all the vertexes of the graph
//In our case it will be cities added by addCity() method
public class Vertex
{
    String cityName;
    Boolean testRequired;
    int timeToTest;
    int nightlyHotelCost;
    String travelMode;
    HashMap<Integer,String> weightToTravelMode = new HashMap<>();


    public Vertex(String cityName, Boolean testRequired, int timeToTest, int nightlyHotelCost) {
        this.cityName = cityName;
        this.testRequired = testRequired;
        this.timeToTest = timeToTest;
        this.nightlyHotelCost = nightlyHotelCost;
    }

    //getting cityname
    public String getCityName() {
        return cityName;
    }

    //setting cityname
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    //getting  the testrequired value
    public Boolean getTestRequired() {
        return testRequired;
    }

    // setting the testrequired value
    public void setTestRequired(Boolean testRequired) {
        this.testRequired = testRequired;
    }

    //Getting the time to test
    public int getTimeToTest() {
        return timeToTest;
    }

    //Setting the time to test
    public void setTimeToTest(int timeToTest) {
        this.timeToTest = timeToTest;
    }

    //Getting nightlyHotelcost
    public int getNightlyHotelCost() {
        return nightlyHotelCost;
    }

    //setting the nighltyHotelCost
    public void setNightlyHotelCost(int nightlyHotelCost) {
        this.nightlyHotelCost = nightlyHotelCost;
    }

    //setting the value of travelmode according to weight
    public void setWeightToTravelMode(int weight, String travelMode)
    {
        weightToTravelMode.put(weight,travelMode);
    }

    //getting the value of travelmode from the weight
    public HashMap<Integer,String> getWeightToTravelMode()
    {
        return this.weightToTravelMode;
    }

    //Getting the travelmode
    public String getTravelMode() {
        return travelMode;
    }

    //setting the travelmode
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

}
