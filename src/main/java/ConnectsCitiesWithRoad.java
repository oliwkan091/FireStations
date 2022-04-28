import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Checks if both cities exists and if the distance between fits requirements. Creates road object, adds connection
 * to both city object
 */
public class ConnectsCitiesWithRoad {

    /**
     *
     * @param road HashMap that contains start city, end city and distance between them
     * @param cities HashMap of all cities
     * @param roads Contains all known roads already created
     * @param citiesNames Contains all known cities names
     * @return true if every data was correct and false if not
     */
    public boolean connect(HashMap<String, String> road, HashMap<String, City> cities,
                           ArrayList<Road> roads, Set<String> citiesNames) {

        if (!City.checkErrorsInHashMapStringString(road) || !City.checkErrorsInHashMapStringCity(cities) ||
                !Road.checkErrorsInArrayListRoad(roads) || !City.checkErrorsSetString(citiesNames))
        {
            System.out.println("Pliki wejściowe nie mogą być null");
            return false;
        }

        if ((doesCityExist(citiesNames, road.get(JSonFireStationsIO.START_CITY_IN_ROUTES))) &&
                (doesCityExist(citiesNames, road.get(JSonFireStationsIO.START_CITY_IN_ROUTES))) &&
                travelTimeValidity(road.get(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES))) {

            // Creates road object
            Road newRoad = new Road(road.get(JSonFireStationsIO.START_CITY_IN_ROUTES),
                    road.get(JSonFireStationsIO.END_CITY_IN_ROUTES),
                    Integer.parseInt(road.get(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES)));
            // Adds connection to start city
            cities.get(newRoad.getStartCity()).addDirectNeighbours(newRoad.getEndCity(), newRoad.getTravelTime());

            // Adds connection to end city
            cities.get(newRoad.getEndCity()).addDirectNeighbours(newRoad.getStartCity(), newRoad.getTravelTime());

            // Add new road to roads Collection
            roads.add(new Road(road.get(JSonFireStationsIO.START_CITY_IN_ROUTES), road.get(JSonFireStationsIO.END_CITY_IN_ROUTES),
                    Integer.parseInt(road.get(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES))));
            return true;
        // If data is no accurate
        } else {
            if (citiesNames.contains(road.get(JSonFireStationsIO.START_CITY_IN_ROUTES))) {
                System.out.println(JSonFireStationsIO.START_CITY_IN_ROUTES + " \"" + road.get(JSonFireStationsIO.START_CITY_IN_ROUTES) + "\" nie istnieje");
            }
            if (citiesNames.contains(road.get(JSonFireStationsIO.END_CITY_IN_ROUTES))) {
                System.out.println(JSonFireStationsIO.END_CITY_IN_ROUTES + " \"" + road.get(JSonFireStationsIO.END_CITY_IN_ROUTES)+ "\" nie istnieje");
            }
            if ((Integer.parseInt(road.get(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES))) <= JSonFireStationsIO.MIN_TRAVEL_TIME) {
                System.out.println(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES + " jest za mała");
            }
            if ((Integer.parseInt(road.get(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES))) >= JSonFireStationsIO.MAX_TRAVEL_TIME) {
                System.out.println(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES + " jest za duża");
            }

            return false;
        }
    }

    /**
     * Checks if city exists
     * @param citiesNames Set of cities names
     * @param city city to check
     * @return true if exists, false if not
     */
    public boolean doesCityExist(Set<String> citiesNames, String city)
    {
        if (citiesNames == null) {return false;}
        return citiesNames.contains(city);
    }

    /**
     * Checks if travel time is correct
     * @param travelTimeStr time to check
     * @return true if yes, false if not
     */
    public  boolean travelTimeValidity(String travelTimeStr)
    {
        try {
            int travelTime = Integer.parseInt(travelTimeStr);

            if ((travelTime >= JSonFireStationsIO.MIN_TRAVEL_TIME) && (travelTime <= JSonFireStationsIO.MAX_TRAVEL_TIME)) {
                return true;
            } else {
                return false;
            }
        }catch (NumberFormatException e)
        {
            return false;
        }
    }


}
