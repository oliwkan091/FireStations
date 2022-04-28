import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class containing all information about Cities
 */
public class City  {

    private String cityName;
    private HashMap<String, Integer> directNeighbors;
    private Set<String> neighboursCloserThanR;
    private int numberOfNeighboursCloserThanR;
    private HashMap<String, City> allCitiesFromFile;
    private int maxTravelTime;

    /**
     *  Constructor od City object
     * @param cityName Name of current city
     * @param cities HashMap of all known cities
     * @param maxTravelTime Max time distance between cities
     */
    public City(String cityName, HashMap<String, City> cities, int maxTravelTime) {
        this.cityName = cityName;
        this.directNeighbors = new HashMap<String, Integer>();
        this.neighboursCloserThanR = new HashSet<String>();
        this.allCitiesFromFile = cities;
        this.maxTravelTime = maxTravelTime;
        numberOfNeighboursCloserThanR = 0;
        addNeighboursCloserThanR(cityName);
    }

    /**
     * Calculates all possible connections between given city and other cities.
     * @param distanceSum Sum of distances between cities.
     * @param currentCity Current city for which calculations are performed
     */
    public void calculateNeighboursCLoserThanR(int distanceSum, City currentCity)
    {

        for (String key: directNeighbors.keySet())
        {
            if (((directNeighbors.get(key) + distanceSum) <= maxTravelTime) && (currentCity == null || !currentCity.neighboursCloserThanR.contains(key)))
            {
                if (currentCity == null)
                {
                    allCitiesFromFile.get(key).calculateNeighboursCLoserThanR(distanceSum+this.directNeighbors.get(key) ,this);
                }else if(!currentCity.neighboursCloserThanR.contains(key))
                {
                    currentCity.addNeighboursCloserThanR(key);
                    allCitiesFromFile.get(key).calculateNeighboursCLoserThanR(distanceSum+this.directNeighbors.get(key) ,currentCity);
                }
            }
        }
    }

    /**
     * Adds new direct neighbour and checks if it is not in reach
     * @param CityName Name of city to be added
     * @param distance Distance between cities
     */
    public void addDirectNeighbours(String CityName, int distance)
    {
        this.directNeighbors.put(CityName,distance);
        if(distance <= maxTravelTime)
        {
            addNeighboursCloserThanR(CityName);
        }
    }

    /**
     * Adds neighbor that is in reach, increment number of neighbors in reach
     * @param cityName name of city to add
     */
    public void addNeighboursCloserThanR(String cityName)
    {
        if (!this.neighboursCloserThanR.contains(cityName))
        {
            this.neighboursCloserThanR.add(cityName);
            numberOfNeighboursCloserThanR += 1;
        }
    }

    /**
     * Removes given subset of cities from CloserThanR. Used when given cities are already covered and program do not
     * care to find them
     * @param citySubset Subset of cities
     */
    public void removeSubsetOfNeighboursCloserThanR(Set<String> citySubset)
    {
        this.neighboursCloserThanR.removeAll(citySubset);
        numberOfNeighboursCloserThanR -= (numberOfNeighboursCloserThanR -citySubset.size());
    }

    /**
     * Converts given list of cities to JSon array of cities names
     * @param listToConvert List of cities
     * @return JSon array of city names
     */
    public static JSONArray convertCitiesListToJSonArray(ArrayList<City> listToConvert)
    {
        JSONArray jSonList =  new JSONArray();
        for (City city: listToConvert)
        {
            jSonList.add(city.getCityName());
        }

        return jSonList;
    };

    /**
     * Checks if data is valid in given hashMap<String, Integer>
     * @param mapToCheck hashMap to be checked
     * @return true if data is valid, false if not
     */
    public static boolean checkErrorsInHashMapStringInteger(HashMap<String, Integer> mapToCheck)
    {
        if(mapToCheck == null)
        {
            return false;
        }else
        {
            for(String s: mapToCheck.keySet())
            {
                if (s == null)
                {
                    System.out.println("Klucz w cities nie może być null");
                    return false;
                }else if (mapToCheck.get(s) == null)
                {
                    System.out.println("Obiekt City w HashMapie cities jest null");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if data is valid in given hashMap<String,String>
     * @param mapToCheck hashMap to be checked
     * @return true if data is valid, false if not
     */
    public static boolean checkErrorsInHashMapStringString(HashMap<String, String> mapToCheck)
    {
        if(mapToCheck == null)
        {
            return false;
        }else
        {
            for(String s: mapToCheck.keySet())
            {
                if (s == null)
                {
                    System.out.println("Klucz w cities nie może być null");
                    return false;
                }else if (mapToCheck.get(s) == null)
                {
                    System.out.println("Obiekt City w HashMapie cities jest null");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if data is valid in given hashMap<String,City>
     * @param mapToCheck  hashMap to be checked
     * @return true if data is valid, false if not
     */
    public static boolean checkErrorsInHashMapStringCity(HashMap<String, City> mapToCheck)
    {
        if (mapToCheck == null)
        {
            return false;
        }else
        {
            for(String s: mapToCheck.keySet())
            {
                if (s == null)
                {
                    System.out.println("Klucz w cities nie może być null");
                    return false;
                }else if (mapToCheck.get(s) == null)
                {
                    System.out.println("Obiekt City w HashMapie cities jest null");
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks if data is valid in given Set<String>
     * @param setToCheck Set to be checked
     * @return true if data is valid, false if not
     */
    public  static boolean checkErrorsSetString (Set<String> setToCheck)
    {
        if (setToCheck == null)
        {
            return false;
        }else
        {
            for(String s: setToCheck)
                if (s == null)
                {
                    System.out.println("Element Set'u jest null");
                    return false;
                }
        }
        return true;
    }




    /**
     * City name getter
     * @return name of city object
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Direct cities getter
     * @return HashMap of city name and distance
     */
    public HashMap<String, Integer> getDirectNeighbors() {
        return directNeighbors;
    }

    /**
     * Cities in reach getter
     * @return Set of cities that can be reached
     */
    public Set<String> getNeighboursCloserThanR() {
        return neighboursCloserThanR;
    }

    /**
     * Number of cities getter
     * @return Number of cities in reach
     */
    public int getNumberOfNeighboursCloserThanR() {
        return numberOfNeighboursCloserThanR;
    }

    /**
     * Max reach number getter, max distance that can be traveled from fires station to city
     * @return Number of max distance
     */
    public int getMaxTravelTime() {return maxTravelTime;}
}

