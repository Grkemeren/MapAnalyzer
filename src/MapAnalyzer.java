import java.util.Locale;

/**
 * the main method of the application.
 * runs the inputProcessor for input array.
 */
public class MapAnalyzer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        String[] input = FileIO.readFile(args[0],true,true);
        if (input == null) return;
        String result = InputProcessor.process(input);
        FileIO.writeToFile(args[1],result,false,false);
    }
}