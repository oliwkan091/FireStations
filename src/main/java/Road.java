import java.util.ArrayList;

/**
 * Class contains all information about roads
 */
public class Road {

    // Name of first city
    private String startCity;
    // Name of second city
    private String endCity;
    // Distance between cities
    private int travelTime;

    /**
     * Constructor of Road object
     * @param startCity Name of first city
     * @param endCity Name of second city
     * @param travelTime Distance between cities
     */
    public Road(String startCity, String endCity, int travelTime) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.travelTime = travelTime;
    }

    public static boolean checkErrorsInArrayListRoad(ArrayList<Road> arrayToCheck)
    {
        if (arrayToCheck == null)
        {
            System.out.println("obiekt jest null");
            return false;
        }else
        {
            for(Road s: arrayToCheck)
                if (s == null)
                {
                    System.out.println("Element Set'u jest null");
                    return false;
                }
        }
        return true;
    }

    /**
     * Start city name getter
     * @return Name of first city
     */
    public String getStartCity() {
        return startCity;
    }

    /**
     * End city name getter
     * @return Name of second city
     */
    public String getEndCity() {
        return endCity;
    }

    /**
     * Distance between cities getter
     * @return Distance between cities
     */
    public int getTravelTime() {
        return travelTime;
    }
}
