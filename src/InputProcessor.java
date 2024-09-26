import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * this class for processing the input.txt into wanted algorithms.
 */
public class InputProcessor {
    /**
     * takes the input array processes it with shortestPath and BarelyConnectedMap algorithms then returns the result.
     * @param input the roads of city as array.
     * @return report of the algorithms results.
     */
    public static String process(String[] input){
        HashMap<Integer, Road> roads = new HashMap<>();
        StringBuilder sbOutput = new StringBuilder();
        String[] firstLine = input[0].split("\t");
        String initialPoint = firstLine[0];
        String endPoint = firstLine[1];
        // construct the roads Hashmap from input txt
        for(String line: Arrays.copyOfRange(input,1,input.length)) {
            String[] lineElements = line.split("\t");
            try {
                List<String> points = new ArrayList<>(Arrays.asList(lineElements[0],lineElements[1]));
                int distance = Integer.parseInt(lineElements[2]);
                int id = Integer.parseInt(lineElements[3]);
                roads.put(id,new Road(points,distance,id));
            } catch (Exception e){
                System.out.println("an error occured while Integer casting of Id or Distance");
                e.printStackTrace();
            }
        }

        // finds the shortest path for unchangedMap.
        ShortestPathFinder pathFinder = new ShortestPathFinder();
        HashMap<String, PointTracker> shortestPathOnUnchangedMap = pathFinder.findPath(initialPoint,endPoint,roads);

        // finds the barelyConnectedMap.
        BarelyConnectedMapFinder mapFinder = new BarelyConnectedMapFinder();
        HashMap<Integer,Road> barelyConnectedMap = mapFinder.findBarelyConnectedMap(roads);

        // finds the shortestPath for barelyConnectedMap.
        HashMap<String, PointTracker> shortestPathOnBarelyConnectedMap =
                pathFinder.findPath(initialPoint,endPoint,barelyConnectedMap);

        sbOutput.append("Fastest Route from "+initialPoint+" to "+endPoint+
                " ("+shortestPathOnUnchangedMap.get(endPoint).getDistanceFromInitialPoint()+" KM):\n");
        sbOutput.append(ShortestPathFinder.formatOutput(initialPoint,endPoint,shortestPathOnUnchangedMap,roads));
        sbOutput.append("Roads of Barely Connected Map is:\n");
        sbOutput.append(BarelyConnectedMapFinder.formatOutput(barelyConnectedMap));
        sbOutput.append("Fastest Route from "+initialPoint+" to "+endPoint+" on Barely Connected Map ("+
                shortestPathOnBarelyConnectedMap.get(endPoint).getDistanceFromInitialPoint()+" KM):\n");
        sbOutput.append(ShortestPathFinder.formatOutput(initialPoint,endPoint,shortestPathOnBarelyConnectedMap,roads));
        sbOutput.append("Analysis:\n");
        sbOutput.append(String.format("Ratio of Construction Material Usage Between Barely Connected and " +
                "Original Map: %.2f\n",getConstructionCost(barelyConnectedMap)/getConstructionCost(roads)));
        sbOutput.append(String.format("Ratio of Fastest Route Between Barely Connected and Original Map:" +
                " %.2f",(float)shortestPathOnBarelyConnectedMap.get(endPoint).getDistanceFromInitialPoint()/
                (float)shortestPathOnUnchangedMap.get(endPoint).getDistanceFromInitialPoint()));
        return sbOutput.toString();
    }

    /**
     * for calculating the construction cost of the all the roads of map.
     * @param roads hashmap Integer-> Road
     * @return total cost as int.
     */
    private static float getConstructionCost(HashMap<Integer, Road> roads) {
        float total = 0;
        for (Road road:roads.values()) {
            total += road.getDistance();
        }
        return total;
    }
}
