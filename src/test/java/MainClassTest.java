import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MainClassTest {

    public static final String fi = "-fi";
    public static final String inputFilePath = "..\\FireStations\\src\\test\\resources\\";
    public static final String fo = "-fo";
    public static final String outputFilePath = "..\\FireStations\\src\\test\\resources\\out\\";

    @BeforeAll
    public static void makeFolder()
    {
        File myObj = new File(outputFilePath);

        if (!myObj.isDirectory())
        {
            myObj.mkdir();
            System.out.println();
        }
    }


    @Test
    @DisplayName("fewRoadsAndCities")
    public void fewRoadsAndCities()
    {
        String inputFileName = "in.json";
        String outputFileName = "in.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);
        assertTrue(myObj.isFile());
    }

    @Test
    @DisplayName("noConnectionsBetweenCities")
    public void noConnectionsBetweenCities()
    {
        String inputFileName = "noConnectionsBetweenCities.json";
        String outputFileName = "noConnectionsBetweenCities.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);
        assertTrue(myObj.isFile());
    }




    @Test
    @DisplayName("manyCities")
    public void manyCities()
    {
        String inputFileName = "manyCities.json";
        String outputFileName = "manyCities.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);
        assertTrue(myObj.isFile());
    }

    @Test
    @DisplayName("manyRoads")
    public void manyRoads()
    {
        String inputFileName = "manyRoads.json";
        String outputFileName = "manyRoads.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);
        assertTrue(myObj.isFile());
    }


    @Test
    @DisplayName("oneStationCovers")
    public void oneStationCovers()
    {
        String inputFileName = "oneStationCovers.json";
        String outputFileName = "oneStationCovers.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);

        JSonFireStationsIO J = new JSonFireStationsIO();
        J.openInputFile(outputFilePath + outputFileName);
        assertEquals(1, J.loadCities().size());
    }


    @Test
    @DisplayName("twoStationsCovers")
    public void twoStationsCovers()
    {
        String inputFileName = "twoStationsCovers.json";
        String outputFileName = "twoStationsCovers.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);

        JSonFireStationsIO J = new JSonFireStationsIO();
        J.openInputFile(outputFilePath + outputFileName);
        assertEquals(2, J.loadCities().size());
    }


    @Test
    @DisplayName("allCitiesTooFar")
    public void allCitiesTooFar()
    {
        String inputFileName = "allCitiesTooFar.json";
        String outputFileName = "allCitiesTooFar.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);

        JSonFireStationsIO J = new JSonFireStationsIO();
        J.openInputFile(outputFilePath + outputFileName);
        assertEquals(11, J.loadCities().size());
    }


    @Test
    @DisplayName("maxDistances")
    public void maxDistances()
    {
        String inputFileName = "maxDistances.json";
        String outputFileName = "maxDistances.json";
        String[] args = {fi,inputFilePath + inputFileName ,fo,outputFilePath + outputFileName, StartArgs.LOCK_EXIT2};

        File myObj = new File(outputFilePath + outputFileName);
        MainClass.main(args);

        JSonFireStationsIO J = new JSonFireStationsIO();
        J.openInputFile(outputFilePath + outputFileName);
        System.out.println(J.openInputFile(outputFilePath + outputFileName));
        assertEquals(1, J.loadCities().size());
    }

    @AfterAll
    public static void delFolder()
    {
        File myObj = new File(outputFilePath);
        for(File file: myObj.listFiles())
        {
            file.delete();
        }

        myObj.delete();
    }

}