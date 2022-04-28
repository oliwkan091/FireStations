import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

/**
 * Class that manages reading and writing JSon data
 */
public class JSonFireStationsIO {

    // Remembers input file
    private JSONObject openJSonFile;

    /*
       Const names of JSon file elements
     */
    public static final String START_CITY_IN_ROUTES = "startCity";
    public static final String END_CITY_IN_ROUTES = "endCity";
    public static final String TRAVEL_TIME_IN_ROUTES = "maxTravelTime";

    public static final String CITIES_LIST_IN_JSON = "miasta";
    public static final String TIMEOUT_IN_JSON = "timeout";
    public static final String MAX_TRAVEL_TIME_IN_JSON = "max_czas_przejazdu";
    public static final String ROAD_HAS_MAP_IN_JSON = "drogi";
    public static final String TRAVEL_TIME_BETWEEN_CITIES = "czas_przejazdu";

    public static final int MAX_TRAVEL_TIME = 9999;
    public static final int MIN_TRAVEL_TIME = 1;

    public static final int MAX_DURATION_TIME = 9999;
    public static final int MIN_DURATION_TIME = 1;

    public static final String defaultInputFileLocation = "..\\FireStations\\src\\main\\resources\\in.json";
    public static final String defaultOutputFileLocation = "..\\FireStations\\src\\main\\resources\\out.json";


    /**
     * Opens JSon file by given ptah. Checks if the file exists and if it is valid
     * @param fileName Name of input file
     * @return true if file exists and is correct, false if not
     */
    public boolean openInputFile(String fileName)
    {
        // Checks if the JSon file is correct by parsing it JSon format
        try
        {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(fileName));
            this.openJSonFile = (JSONObject) obj;
            return true;
        }
        catch(FileSystemNotFoundException e)
        {
            System.out.println("Błąd pliku z danymi");
            System.out.println(e.getMessage());
            return false;
        }
        catch (IOException e)
        {
            System.out.println("Błąd pliku z danymi");
            System.out.println(e.getMessage());
            return false;
        }
        catch(ParseException e)
        {
            System.out.println("Błąd podczas parsowania danych");
            System.out.println(e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            System.out.println("Błąd pliku z danymi");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Loads timout varible for file
     * @return loaded variable or -1 if error occurred
     */
    public int loadTimeout()
    {
        // Tries to parse variable to int
        try
        {
            int timeout = Integer.parseInt(openJSonFile.get(TIMEOUT_IN_JSON).toString());
            if (timeout >= MIN_DURATION_TIME && timeout <= MAX_DURATION_TIME)
            {
                return timeout*1000;
            }else
                return -1;
        }catch (NullPointerException e)
        {
            System.out.println("Błąd zmiennej \"timeout\" w pliku wejściowym");
            return -1;
        }
    }

    /**
     * Loads max distance variable
     * @return max distance variable or -1 if error occurred
     */
    public int loadTravelMaxTime()
    {
        // Tries to parse variable to int
        try
        {
            int maxTravelTime = Integer.parseInt(openJSonFile.get(MAX_TRAVEL_TIME_IN_JSON).toString());
            if (maxTravelTime >= 0 && maxTravelTime < 10000)
            {
                return maxTravelTime;
            }else
            {
                return -1;
            }
        }catch (NullPointerException e)
        {
            System.out.println("Błąd zmiennej \"max_czas_przejazdu\" w pliku wejściowym");
            return -1;
        }
    }

    /**
     * Loads cities to Set list to prevent repetition from occurring
     * @return Set of all cities, null if error occurred
     */
    public Set<String> loadCities()
    {
        // Parse JSon to Set of string
        try {
            return new HashSet<String>((ArrayList<String>)openJSonFile.get(CITIES_LIST_IN_JSON));
        }catch (NullPointerException e)
        {
            System.out.println("Błąd zmiennej \"miasta\" w pliku wejściowym");
            return null;
        }
    }

    /**
     * Loads roads from file
     * @return ArrayList of HashMap containing start city, end city, distance of every road, null if error occurred
     */
    public ArrayList<HashMap<String, String>> loadRoutes()
    {
        ArrayList<HashMap<String, String>> roadsList = new ArrayList<HashMap<String, String>>();
        //Checks for possible errors
        try {
            //Parse JSon Array to itertor
            JSONArray roads = (JSONArray) openJSonFile.get(ROAD_HAS_MAP_IN_JSON);
            Iterator<JSONObject> iterator = roads.iterator();

            //Iterates through every path and adds to roadList
            while (iterator.hasNext()) {
                HashMap<String, String> tempHashMap = new HashMap<String, String>();
                JSONObject jsonObj = iterator.next();
                JSONArray ja = (JSONArray) jsonObj.get(CITIES_LIST_IN_JSON);
                tempHashMap.put(START_CITY_IN_ROUTES, ja.get(0).toString());
                tempHashMap.put(END_CITY_IN_ROUTES, ja.get(1).toString());
                tempHashMap.put(TRAVEL_TIME_IN_ROUTES, jsonObj.get(TRAVEL_TIME_BETWEEN_CITIES).toString());
                roadsList.add(tempHashMap);
            }

            return roadsList;
        }catch(NullPointerException e)
        {
            System.out.println("Błąd zmiennej \"drogi\" w pliku wejściowym");
            return null;
        }catch (IndexOutOfBoundsException e)
        {
            System.out.println("Dane jednej z dróg są uszkodzone");
            return null;
        }

    }

    /**
     * Saves solution to JSon file
     * @param citiesToSave Cities that are the solution for problem
     * @param fileName Path to save file
     * @return true if saved correctly, false if not
     */
    public boolean saveFile(ArrayList<City> citiesToSave, String fileName)
    {
        JSONObject obj = new JSONObject();
        obj.put(CITIES_LIST_IN_JSON, City.convertCitiesListToJSonArray(citiesToSave));

        try
        {
            FileWriter file = new FileWriter(fileName);
            file.write(obj.toString());
            file.flush();
            file.close();
            return true;

        }catch (IOException e)
        {
            System.out.println("Błąd pliku wynikowego");
            return false;
        }
    }

}
