import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Checks all arguments given from console
 */
public class StartArgs {

    private String[] args;
    private String inputFilePath;
    private String outputFilePath;
    private boolean isTesting;

    //Const names of arguments
    public static final String FILE_INPUT = "fi";
    public static final String FILE_INPUT2 = "-fi";
    public static final String FILE_OUTPUT = "fo";
    public static final String FILE_OUTPUT2 = "-fo";
    public static final String LOCK_EXIT = "t";
    public static final String LOCK_EXIT2 = "-t";

    /**
     * Constructor
     * @param args arguments form console
     */
    public StartArgs(String[] args) {
        this.args = args;
        this.inputFilePath = "";
        this.outputFilePath = "";
    }

    /**
     * Iterate through args list to find necessary parameters like: input file and out file and checks accuracy.
     * @return true if data is valid, false if not
     */
    public boolean checkArgs()
    {
        isTesting = false;
        for (int i = 0; i< args.length; i++)
        {
            //File in
            if ((args[i].equals(FILE_INPUT) || args[i].equals(FILE_INPUT2)) && (i + 1 < args.length))
            {
                inputFilePath = args[i+1];
            }

            //File out
            if ((args[i].equals(FILE_OUTPUT) || args[i].equals(FILE_OUTPUT2)) && (i + 1 < args.length))
            {
                outputFilePath = args[i+1];
            }

            //loc
            if ((args[i].equals(LOCK_EXIT) || args[i].equals(LOCK_EXIT2)))
            {
                isTesting = true;
            }
        }

        if(inputFilePath.equals(""))
        {
            System.out.println("Ścieżka do pliku z danymi jest wymagana");
            return false;
        }

        if(outputFilePath.equals(""))
        {
            System.out.println("Ścieżka do pliku wynikowego jest wymagana");
            return false;
        }

        return true;
    }

    /**
     * Input file path getter
     * @return String of input path
     *
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * Output file path getter
     * @return String of output path
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     *
     * @return
     */
    public boolean getIsTesting() {
        return isTesting;
    }
}
