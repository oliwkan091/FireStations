import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;

class SCPGreedyTest {

    private SCPGreedy G;
    private Set<String> citiesNames;
    private HashMap<String, City> cities;


    @BeforeEach
    public void makesObjects()
    {
        G = new SCPGreedy();

        cities = new HashMap<String, City>();
        cities.put("A", new City("A", new HashMap<String, City>(), 6));
        cities.put("B", new City("B", new HashMap<String, City>(), 6));

        citiesNames = new HashSet<String>();
        citiesNames.add("A");
        citiesNames.add("B");
    }

    @Test
    @DisplayName("Should Not Continue When citiesNames Is Null In findAllFireStationsLocations")
    public void shouldNotContinueWhenCitiesNamesIsNullInFindAllFireStationsLocations()
    {
        assertNull(G.findAllFireStationsLocations(null, cities ));
    }

    @Test
    @DisplayName(" Should Not Continue When cities Is Null In findAllFireStationsLocations")
    public void shouldNotContinueWhenCitiesIsNullInFindAllFireStationsLocations()
    {
        assertNull(G.findAllFireStationsLocations(citiesNames, null ));
    }

    @Test
    @DisplayName("Should Not Continue When One Of Cities Key Is Null In findAllFireStationsLocations")
    public void shouldNotContinueWhenOneOfCitiesKeyIsNullInFindAllFireStationsLocations()
    {
        cities.put(null,new City("z", new HashMap<String, City>(), 6));
        assertNull(G.findAllFireStationsLocations(citiesNames, cities ));
    }

    @Test
    @DisplayName("Should Not Continue When One Of Cities City Key Is Null In findAllFireStationsLocations")
    public void shouldNotContinueWhenOneOfCitiesCityKeyIsNullInFindAllFireStationsLocations()
    {
        cities.put("z",null);
        assertNull(G.findAllFireStationsLocations(citiesNames, cities ));
    }

    @Test
    @DisplayName("should Not Continue When citiesNames Element Is Null In findAllFireStationsLocations")
    public void shouldNotContinueWhenCitiesNamesElementIsNullInFindAllFireStationsLocations()
    {
        citiesNames.add(null);
        assertNull(G.findAllFireStationsLocations(citiesNames, cities ));
    }



    @Test
    @DisplayName("Should Not Continue When citiesNames Is Null in findCityWithBestCoverage")
    public void shouldNotContinueWhenCitiesNamesIsNullInFindCityWithBestCoverage()
    {
        assertEquals("",G.findCityWithBestCoverage(null, cities ));
    }

    @Test
    @DisplayName("Should Not Continue When cities Is Null In findCityWithBestCoverage")
    public void shouldNotContinueWhenCitiesIsNullInFindCityWithBestCoverage()
    {
        assertEquals("",G.findCityWithBestCoverage(citiesNames, null ));
    }

    @Test
    @DisplayName("Should Not Continue When One Of Cities Key Is Null In findCityWithBestCoverage")
    public void shouldNotContinueWhenOneOfCitiesKeyIsNullInFindCityWithBestCoverage()
    {
        cities.put(null,new City("z", new HashMap<String, City>(), 6));
        assertEquals("",G.findCityWithBestCoverage(citiesNames, cities ));
    }

    @Test
    @DisplayName("Should Not Continue When One Of Cities City Key Is Null In findCityWithBestCoverage")
    public void shouldNotContinueWhenOneOfCitiesCityKeyIsNullInFindCityWithBestCoverage()
    {
        cities.put("z",null);
        assertEquals("",G.findCityWithBestCoverage(citiesNames, cities ));
    }

    @Test
    @DisplayName("Should Not Continue When citiesNames Element Is Null In findCityWithBestCoverage")
    public void shouldNotContinueWhenCitiesNamesElementIsNullInFindCityWithBestCoverage()
    {
        citiesNames.add(null);
        assertEquals("",G.findCityWithBestCoverage(citiesNames, cities ));
    }

    @Test
    @DisplayName("Should Not Continue When citiesNames Element Is Null In findCityWithBestCoverage")
    public void ShouldNotContinueWhencitiesNamesElementIsNullInFindCityWithBestCoverage()
    {

        // Object that manages reading and writing to JSon file
        JSonFireStationsIO jsonObj = new JSonFireStationsIO();
        if (!jsonObj.openInputFile(JSonFireStationsIO.defaultInputFileLocation)) {exit(0);}

        // Loads the maximum progarm runtime
        Integer timeout = jsonObj.loadTimeout();
        if(timeout == -1) {exit(0);}

        // Loads max distances between city and fire station
        Integer maxTravelTime = jsonObj.loadTravelMaxTime();
        if (maxTravelTime == -1) {exit(0);}

        // Loads all cities
        Set<String> citiesNames = jsonObj.loadCities();
        if (citiesNames == null) {exit(0);}

        // Creates cities objects based on previous data
        HashMap<String, City> cities = new HashMap<String, City>();
        for(String city: citiesNames)
        {
            cities.put(city, new City(city, cities, maxTravelTime));
        }

        assertNull(G.findAllFireStationsLocations(null, cities ));
    }


}