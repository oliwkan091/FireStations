import java.util.*;

import static java.lang.System.*;

/**
 * Class that manages the process of solving the Fires Stations Set Cover Problem
 */
public class MainClass {

    /**
     * Method called at the beginning  of program
     * @param args command line arguments: -fi path to JSon file with input data, -fo path to JSon file to save solution
     */
    public static void main(String[] args) {

        //The run method is called when the program duration is equal to timeout
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                exit(0);
            }
        };

        // Starts the counter to calculate the running time of the program
        long start = System.currentTimeMillis();

        StartArgs SA = new StartArgs(args);
        if(!SA.checkArgs()) {exit(0);}

        JSonFireStationsIO jsonObj = new JSonFireStationsIO();
        if (!jsonObj.openInputFile(SA.getInputFilePath())) {exit(0);}

        Integer timeout = jsonObj.loadTimeout();
        if(timeout == -1) {exit(0);}

        timer.schedule(task,timeout);

        Integer maxTravelTime = jsonObj.loadTravelMaxTime();
        if (maxTravelTime == -1) {exit(0);}

        Set<String> citiesNames = jsonObj.loadCities();
        if (citiesNames == null) {exit(0);}

        HashMap<String, City> cities = new HashMap<String, City>();
        for(String city: citiesNames)
        {
            cities.put(city, new City(city, cities, maxTravelTime));
        }


        ArrayList<HashMap<String, String>> roadsParameters = jsonObj.loadRoutes();
        if (roadsParameters == null) {exit(0);}

        ArrayList<Road> roads = new ArrayList<Road>();
        ConnectsCitiesWithRoad CS = new ConnectsCitiesWithRoad();
        for(HashMap<String, String> road : roadsParameters) {
            // If given road contains city that does not exist in cities, stops the program
            if (!CS.connect(road, cities, roads, citiesNames))
            {
                System.out.println("Miasto nie istnieje");
                exit(0);
            }
        }

        for (String city: citiesNames)
        {
            cities.get(city).calculateNeighboursCLoserThanR(0, null);
        }

        SCPGreedy solver = new SCPGreedy();

        ArrayList<City> coveredCities = solver.findAllFireStationsLocations(citiesNames, cities);

        if(coveredCities == null)
        {
            exit(0);
        }


        System.out.println("Wyniki");
        for (City city: coveredCities)
        {
            System.out.print(city.getCityName() + ", ");
        }

        // Saves results
        if (!jsonObj.saveFile(coveredCities,SA.getOutputFilePath())) {exit(0);}

        // Calculating duraion
        long finish = System.currentTimeMillis();

        System.out.println("\nCzas trwania programu: " + ((double)(finish - start))/1000);

        if (!SA.getIsTesting()) {exit(0);}
    }


}
