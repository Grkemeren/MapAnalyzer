import java.util.*;

/**
 * class for finding the shortest path from initial city to final city on given Map.
 */
public class ShortestPathFinder {
    private HashMap<String, PointTracker> shortestDistancesToPoints; //  pointName-> pointTracker
                                                                     // key-set is used as visited Points.
    private List<PointTracker> distancesToPoints;
    private HashMap<Integer, Road> roads;

    /**
     * selects the shortest pointTracker according the toCompare implementation from the distancesToPoints and adds it
     * to shortestDistancesToPoints, adds all the roads which connected with that point. construct PointTracker objects
     * for each point. if the point is already
     * in the key-set of ShortestDistancesToPoints it doesn't construct the object. add each object to distancesToPoints
     * and selects shortest PointTracker again if the selected point equals endpoint stops the recursion.
     * @param endpoint the city which is final. recursion stops when it came to this city.
     */
    private void shortestPathRecursion(String endpoint) {

        // Among all possible paths, it chooses the one closest to the start and not yet visited.
        // if point visited before don't take that point
        Collections.sort(distancesToPoints);
        PointTracker startingPointTracker;
        do {
            if(distancesToPoints.isEmpty()) {
                System.out.println("the map is incorrect please check the given map");
                return;}
            startingPointTracker = distancesToPoints.get(0);
            distancesToPoints.remove(0);
        } while (shortestDistancesToPoints.containsKey(startingPointTracker.getPoint()));

        // adds the point to map and makes is visited.
        shortestDistancesToPoints.put(startingPointTracker.getPoint(),startingPointTracker);

        // if the last point visited means shortest path to endpoint founded.
        if (startingPointTracker.getPoint().equals(endpoint)) {
            return;
        }
        // adds the roads connected with other end of the selected road which other ends is not visited yet.
        List<Road> newAddedRoads = new ArrayList<>();
        for (Road road:roads.values()) {
            if (road.getPoints().contains(startingPointTracker.getPoint()) &&
                    // discards the points which already visited. shortest distances keySet is used as visited points.
                    !shortestDistancesToPoints.keySet().containsAll(road.getPoints())) {
                newAddedRoads.add(road);
            }
        }
        Collections.sort(newAddedRoads); // my instructor requested sorting them before appending.

        // creates newPoint trackers for each road ended with unvisited point.
        for (Road road:newAddedRoads){
            String startPoint = startingPointTracker.getPoint();
            String endPoint = road.getPoints().get(0).equals(startPoint) ?
                    road.getPoints().get(1) : road.getPoints().get(0);
            distancesToPoints.add(new PointTracker(endPoint,startPoint,
                    startingPointTracker.getDistanceFromInitialPoint()+road.getDistance()
                    ,road.getId()));
        };
        shortestPathRecursion(endpoint);
    }

    /**
     * construct the lists and hashmaps which is gonna used in recursion and starts the recursion.
     * @param initialPoint initial city.
     * @param endpoint final city.
     * @param roads the roads of the real life map.
     * @return a hashmap which contains how point trackers for each city.
     */
    public HashMap<String, PointTracker> findPath(String initialPoint, String endpoint, HashMap<Integer, Road> roads) {
        this.roads = roads;
        shortestDistancesToPoints = new HashMap<>();
        distancesToPoints = new ArrayList<>();
        distancesToPoints.add(new PointTracker(initialPoint,
                initialPoint,0,0)); // specifying initial point for algorithm.
        shortestPathRecursion(endpoint);
        return shortestDistancesToPoints;
    }

    /**
     * for formatting the shortestDistancesToPoints to a report of which roads is used in the path.
     * @param initialPoint initial city.
     * @param endPoint final city.
     * @param dijkstraMap the hashmap which contains pointTrackers.
     * @param roads the roads of real life map.
     * @return report as a String.
     */
    public static String formatOutput(String initialPoint, String endPoint,HashMap<String,
            PointTracker> dijkstraMap,HashMap<Integer, Road> roads){
        StringBuilder sb = new StringBuilder();
        String currentPoint = endPoint;
        while(!currentPoint.equals(initialPoint)) {
            Road road = roads.get(dijkstraMap.get(currentPoint).getRoadId());
            sb.insert(0,road.getPoints().get(0)+"\t"+road.getPoints().get(1)+
                    "\t"+road.getDistance()+"\t"+road.getId()+"\n");
            currentPoint = dijkstraMap.get(currentPoint).getPreviousPoint();
        }
        return sb.toString();
    }
}
