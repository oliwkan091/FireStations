import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Solves the Fires Stations Set Cover Problem
 */
public class SCPGreedy {

    /**
     * Searches for the most optimal fire station location.
     * @param citiesNames Names of all known cities
     * @param cities All known cities
     * @return ArrayList of locations of fire stations
     */
    public ArrayList<City> findAllFireStationsLocations(Set<String> citiesNames, HashMap<String, City> cities )
    {

        if(!City.checkErrorsSetString(citiesNames) || !City.checkErrorsInHashMapStringCity(cities)) {return null;}

        // Copies all known cities to new array, because it will be edited
        Set<String> citiesToCover = new HashSet<String>();
        for (String name: citiesNames)
        {
            citiesToCover.add(name);
        }

        ArrayList<City> coveredCities = new ArrayList<City>();
        while(!citiesToCover.isEmpty())
        {
            String cityThatCovers = findCityWithBestCoverage(citiesToCover, cities);
            if(cityThatCovers.equals("")) {return null;}
            coveredCities.add(cities.get(cityThatCovers));

            citiesToCover.removeAll(cities.get(cityThatCovers).getNeighboursCloserThanR());

            Set<String> citiesThatCovers = cities.get(cityThatCovers).getNeighboursCloserThanR();
            cities.remove(cityThatCovers);
            for(String key: cities.keySet())
            {
                cities.get(key).removeSubsetOfNeighboursCloserThanR(citiesThatCovers);
            }
        }

        return coveredCities;
    }

    /**
     * Checks parameters validity before calling recurrent function
     * @param citiesNames Set with all cities names
     * @param cities HashMap with cities
     * @return City with max number of roads or empty string when error occurred
     */
    public String findCityWithBestCoverage(Set<String> citiesNames, HashMap<String, City> cities)
    {
        if(!City.checkErrorsSetString(citiesNames) || !City.checkErrorsInHashMapStringCity(cities)) {return "";}
        return findMaxRoadedCityRec(citiesNames, cities);
    }

    /**
     * Searches for the city with the highest number of connections to cities within range of the station
     * @param citiesNames Set with names of all cities
     * @param cities HashMap with all cities
     * @return Name of city with the highest number of connections
     */
    private String findMaxRoadedCityRec(Set<String> citiesNames, HashMap<String, City> cities)
    {
        int maxCovered = 0;
        City maxCoveredCity = null;

        for (String city: citiesNames)
        {
            if (maxCovered < cities.get(city).getNumberOfNeighboursCloserThanR())
            {
                maxCovered = cities.get(city).getNumberOfNeighboursCloserThanR();
                maxCoveredCity = cities.get(city);
            }
        }

        return maxCoveredCity.getCityName();
    }

}
