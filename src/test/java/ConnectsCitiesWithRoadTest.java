import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectsCitiesWithRoadTest {

    private ConnectsCitiesWithRoad c;
    private  HashMap<String, String> road;
    private  HashMap<String, City> cities;
    private  ArrayList<Road> roads;
    private  Set<String> citiesNames;

    @BeforeEach
    public void makesVariables()
    {
        c = new ConnectsCitiesWithRoad();

        road = new HashMap<String, String>();
        road.put(JSonFireStationsIO.START_CITY_IN_ROUTES, "A");
        road.put(JSonFireStationsIO.END_CITY_IN_ROUTES, "B");
        road.put(JSonFireStationsIO.TRAVEL_TIME_IN_ROUTES, "6");

        cities = new HashMap<String, City>();
        cities.put("A", new City("A", new HashMap<String, City>(), 6));
        cities.put("B", new City("B", new HashMap<String, City>(), 6));

        roads = new ArrayList<Road>();
        roads.add(new Road("A","B",5));

        citiesNames = new HashSet<String>();
        citiesNames.add("A");
        citiesNames.add("B");
    }

    @Test
    @DisplayName("Should Not Continue When Road Equals Null")
    public void ShouldNotContinueWhenRoadEqualsNull()
    {
        assertFalse(c.connect(null, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Cities Equals Null")
    public void ShouldNotContinueWhenCitiesEqualsNull()
    {
        assertFalse(c.connect(road, null, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Roads Equals Null")
    public void ShouldNotContinueWhenRoadsEqualsNull()
    {
        assertFalse(c.connect(road, cities, null, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Cities Names Equals Null")
    public void ShouldNotContinueWhenCitiesNamesEqualsNull()
    {
        assertFalse(c.connect(road, cities, roads, null));
    }

    @Test
    @DisplayName("Should Not Continue When Road Key Is Null")
    public void ShouldNotContinueWhenRoadKeyIsNull()
    {
        road.put(null, "6");
        assertFalse(c.connect(road, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Road Key Is Null")
    public void ShouldNotContinueWhenRoadDistanceIsNull()
    {
        road.put(JSonFireStationsIO.START_CITY_IN_ROUTES, null);
        assertFalse(c.connect(road, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Cities Key Is Null")
    public void ShouldNotContinueWhenCitiesKeyIsNull()
    {
        cities.put(null, new City("Z", new HashMap<String, City>(), 6));
        assertFalse(c.connect(road, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Cities Element Is Null")
    public void ShouldNotContinueWhenCitiesElementIsNull()
    {
        cities.put("Z", null);
        assertFalse(c.connect(road, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Roads Element")
    public void ShouldNotContinueWhenRoadsElement()
    {
        roads.add(null);
        assertFalse(c.connect(road, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("Should Not Continue When Roads Element")
    public void ShouldNotContinueWhenCitiesNamesElementIsNull()
    {
        citiesNames.add(null);
        assertFalse(c.connect(road, cities, roads, citiesNames));
    }


    @Test
    @DisplayName("Should Not Continue When Roads Data Is Not Valid")
    public void ShouldNotContinueWhenRoadsDataIsNotValid()
    {
        HashMap<String, String> wrongCities = new HashMap<String, String>();
        wrongCities.put("X","Y");
        assertThrows(NumberFormatException.class, () -> c.connect(wrongCities, cities, roads, citiesNames));
    }

    @Test
    @DisplayName("doesCityExists Returns False When CityNames Null")
    public void doesCityExistsReturnsFalseWhenCityNamesNull()
    {
        assertFalse(c.doesCityExist(null, "A"));
    }

    @Test
    @DisplayName("doesCityExists Returns False When City Not In CityNames")
    public void doesCityExistsReturnsFalseWhenCityNotInCityNames()
    {
        assertFalse(c.doesCityExist(citiesNames, "X"));
    }

    @Test
    @DisplayName("doesCityExists Returns False When CityNames Is Null")
    public void doesCityExistsReturnsFalseWhenCityNamesIsNull()
    {
        assertFalse(c.doesCityExist(citiesNames, null));
    }

    @Test
    @DisplayName("doesCityExists Returns True When Data Valid")
    public void doesCityExistsReturnsTrueWhenDataValid()
    {
        assertTrue(c.doesCityExist(citiesNames, "A"));
    }

    @Test
    @DisplayName("travelTimeValidity Returns False When TravelTimeStr Is Null")
    public void travelTimeValidityReturnsFalseWhenTravelTimeStrIsNull()
    {
        assertFalse(c.travelTimeValidity(null));
    }

    @Test
    @DisplayName("travelTimeValidity Returns False When TravelTimeStr Is To Big")
    public void travelTimeValidityReturnsFalseWhenTravelTimeStrIsToBig()
    {
        assertFalse(c.travelTimeValidity(Integer.toString (JSonFireStationsIO.MAX_TRAVEL_TIME + 1)));
    }

    @Test
    @DisplayName("travelTimeValidity Returns False When TravelTimeStr Is To Small")
    public void travelTimeValidityReturnsFalseWhenTravelTimeStrIsToSmall()
    {
        assertFalse(c.travelTimeValidity(Integer.toString (JSonFireStationsIO.MIN_TRAVEL_TIME - 1)));
    }

    @Test
    @DisplayName("travelTimeValidity Returns False When TravelTimeStr Is Not Numeric")
    public void travelTimeValidityReturnsFalseWhenTravelTimeStrIsNotNumeric()
    {
        assertFalse(c.travelTimeValidity("h"));
    }

    @Test
    @DisplayName("travelTimeValidity Returns True When TravelTimeStr Is Number")
    public void travelTimeValidityReturnsTrueWhenTravelTimeStrIsNumber()
    {
        assertTrue(c.travelTimeValidity("4"));
    }

}