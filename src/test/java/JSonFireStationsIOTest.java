import org.junit.After;
import org.junit.jupiter.api.*;

import java.io.File;
import java.text.ParseException;
import java.util.*;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;

public class JSonFireStationsIOTest {

    public static final String fi = "-fi";
    public static final String inputFilePath = "..\\FireStations\\src\\test\\resources\\";
    public static final String fo = "-fo";
    public static final String outputFilePath = "..\\FireStations\\src\\test\\resources\\out\\";
    private static final String[] args = {"-fi", "..\\FireStations\\src\\test\\resources\\in.json", "-fo", "..\\FireStations\\src\\test\\resources\\out\\out.json"};
    private JSonFireStationsIO jSonObj;

    @BeforeEach
    public void createNejSonObj()
    {
        jSonObj = new JSonFireStationsIO();
    }

    @Test
    @DisplayName("Should Not Open Not Existing File")
    public void shouldNotOpenNotExistingFile()
    {
        assertFalse(jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\a.json"));
    }

    @Test
    @DisplayName("Should Not Parse File When cityTravelTime Is Not Int")
    public void shouldNotParseFileWhenCityTravelTimeIsNotInt()
    {
        assertFalse(jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inCityTravelTimeIsNotInt.json"));
    }

    @Test
    @DisplayName("Should Not Parse File When Input File Is Not Valid")
    public void shouldNotParseFileWhenInputFileIsNotValid()
    {
        assertFalse(jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inErrorFile.json"));
    }

    @Test
    @DisplayName("Should Not Parse File When Max Travel Time Is Not Int")
    public void shouldNotParseFileWhenMaxTravelTimeIsNotInt()
    {
        assertFalse(jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inMaxTravelTimeIsNotInt.json"));
    }

    @Test
    @DisplayName("Should Not Load Cities When Cities Variable Does Not Exist")
    public void shouldNotLoadCitiesWhenCitiesVariableDoesNotExist()
    {
        jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inNoCities.json");
        assertNotEquals(-1, jSonObj.loadTimeout());
        assertNotEquals(-1, jSonObj.loadTravelMaxTime());
        assertNull(jSonObj.loadCities());//
        assertNotNull(jSonObj.loadRoutes());
    }

    @Test
    @DisplayName("Should Not Load maxTravelTime When travelTimeVariable Does Not Exist")
    public void shouldNotLoadMaxTravelTimeWhenTravelTimeVariableDoesNotExist()
    {
        jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inNoMaxTravelTime.json");
        assertNotEquals(-1, jSonObj.loadTimeout());
        assertEquals(-1, jSonObj.loadTravelMaxTime());
        assertNotNull(jSonObj.loadCities());
        assertNotNull(jSonObj.loadRoutes());
    }

    @Test
    @DisplayName("should Not Load Road When Road Variable Does Not Exist")
    public void shouldNotLoadRoadWhenRoadVariableDoesNotExist()
    {
        jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inNoRoad.json");
        assertNotEquals(-1, jSonObj.loadTimeout());
        assertNotEquals(-1, jSonObj.loadTravelMaxTime());
        assertNotNull(jSonObj.loadCities());
        assertNull(jSonObj.loadRoutes());


    }

    @Test
    @DisplayName("Should Not Load Timeout When Timeout Varible Does Not Exist")
    public void shouldNotLoadTimeoutWhenTimeoutVaribleDoesNotExist()
    {
        jSonObj.openInputFile("..\\FireStations\\src\\test\\resources\\inNoTimeout.json");
        assertEquals(-1, jSonObj.loadTimeout());
        assertNotEquals(-1, jSonObj.loadTravelMaxTime());
        assertNotNull(jSonObj.loadCities());
        assertNotNull(jSonObj.loadRoutes());
    }


    @Test
    @DisplayName("Should Return Start City Does Not Exists")
    public void shouldReturnStartCityDoesNotExists()
    {

        String inputFileName = "inStartCityDoesNotExists.json";
        String outputFileName = "inStartCityDoesNotExists.json";
        String[] args =  {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};
        /*
            Main Fragment
         */
        StartArgs SA = new StartArgs(args);
        if(!SA.checkArgs()) {exit(0);}

        // Object that manages reading and writing to JSon file
        JSonFireStationsIO jsonObj = new JSonFireStationsIO();
        if (!jsonObj.openInputFile(SA.getInputFilePath())) {exit(0);}

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

        // Loads all roads
        ArrayList<HashMap<String, String>> roadsParameters = jsonObj.loadRoutes();
        if (roadsParameters == null) {exit(0);}

        ArrayList<Road> roads = new ArrayList<Road>();
        ConnectsCitiesWithRoad CS = new ConnectsCitiesWithRoad();
        assertFalse(CS.connect(roadsParameters.get(6), cities, roads, citiesNames));
    }

    @Nested
    class WithOutputFile
    {

        @BeforeAll
        public static void makeFolder()
        {
            File myObj = new File("..\\FireStations\\src\\test\\resources\\out\\");

            if (!myObj.isDirectory())
            {
                myObj.mkdir();
                System.out.println();
            }
        }

        @Test
        @DisplayName("Should Work Fine And Calculate Solution ")
        public void shouldWorkFineAndCalculateSolution ()
        {
            String inputFileName = "in.json";
            String outputFileName = "normalOut.json";
            String[] args =  {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};
            MainClass.main(args);
            File myObj = new File(outputFilePath + outputFileName);
            assertTrue(myObj.isFile());
        }

        @AfterEach
        public  void delFolder()
        {
            File myObj = new File("..\\FireStations\\src\\test\\resources\\out\\outFine.json");
            myObj.delete();
        }

    }


}