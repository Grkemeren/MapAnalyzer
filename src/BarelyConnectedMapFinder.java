import java.util.*;

/**
 * class for finding the roads which connects all cities with minimum distance.
 * has 4 attributes: roads for the roads may can be constructed,
 * barelyConnectedRoads is the roads who selected to be constructed.
 * PossibleRoadsToAppend to tracking the roads who may be constructed according the algorithm.
 * unitedPoints to track which cities is united before for eliminating cycling.
 */
public class BarelyConnectedMapFinder {
    private HashMap<Integer,Road> roads;
    private HashMap<Integer,Road> barelyConnectedRoads; // road-id -> road
    private List<Road> possibleRoadsToAppend;
    private Set<String> unitedPoints;

    /**
     * the main algorithm which finds the barelyConnectedMap. starts with the given city and appends all connected
     * cities to possibleRoadsToAppend.
     * selects the smallest road from possibleRoadsToAppend which has at least one point doest exist in unitedPoints
     * and appends it to barelyConnectedRoads.
     * @param point the point which algorithm selects in each step.
     * @return the BarelyConnectedRoads Hashmap.
     */
    private HashMap<Integer,Road> BCMRecursion(String point){
        unitedPoints.add(point); // for specifying the starting point.
        for (Road road : roads.values()) {
            if (road.getPoints().contains(point)) {
                possibleRoadsToAppend.add(road);
            }
        }
        Collections.sort(possibleRoadsToAppend);
        Road selectedRoad = null;
        do {
            if (possibleRoadsToAppend.isEmpty()) return barelyConnectedRoads; // if there is no non-united point left;
            selectedRoad = possibleRoadsToAppend.get(0);
            possibleRoadsToAppend.remove(0);
        } while (unitedPoints.containsAll(selectedRoad.getPoints())); // if selected road both end is united to group.
        List<String> points = selectedRoad.getPoints();
        String nextPoint = unitedPoints.contains(points.get(0)) ? points.get(1) : points.get(0);
        unitedPoints.add(nextPoint); // since starting point is in already united with the group.
        barelyConnectedRoads.put(selectedRoad.getId(),selectedRoad);
        return BCMRecursion(nextPoint);
    }

    /**
     * constructs the Lists and hashmaps. Sorts the all the cities according to their names and
     * selects the first one. starts the recursion algorithm BCMRecursion with selected city.
     * @return barelyConnectedRoads when the recursion finished.
     */
    public HashMap<Integer,Road> findBarelyConnectedMap(HashMap<Integer, Road> roads){
        this.roads = roads;
        barelyConnectedRoads = new HashMap<>();
        possibleRoadsToAppend = new ArrayList<>();
        unitedPoints = new HashSet<>();
        List<String> allPoints = new ArrayList<>();
        for (Road road : roads.values()) { // collect all the points possible.
            allPoints.add(road.getPoints().get(0));
            allPoints.add(road.getPoints().get(1));
        }
        Collections.sort(allPoints); // determine starting point.
        String initialPoint = allPoints.get(0);
        return BCMRecursion(initialPoint);

    }

    /**
     * for formatting the barely connected to a report of which roads is constructed.
     * @param barelyConnectedRoads the Map which contains the selected roads.
     * @return report as a String.
     */
    public static String formatOutput(HashMap<Integer,Road> barelyConnectedRoads){
        StringBuilder sb = new StringBuilder();
        List<Road> barelyConnectedMap = new ArrayList<>(barelyConnectedRoads.values());
        Collections.sort(barelyConnectedMap);
        for (Road road: barelyConnectedMap) {
            sb.append(road.getPoints().get(0)).append("\t").append(road.getPoints().get(1)).append("\t")
                    .append(road.getDistance()).append("\t").append(road.getId()).append("\n");
        }
        return sb.toString();
    }
}
