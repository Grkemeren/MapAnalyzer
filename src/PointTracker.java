/**
 * the object for tracking the cities of map for finding the ShortestPath.
 * used in ShortestPath algorithm.
 * has 4 attributes ,the point which identifies the point tracker, previous point, distance from initial city, and
 * the id of road which connects the two point.
 */
public class PointTracker implements Comparable<PointTracker> {
    private final String point;
    private final String previousPoint;
    private final int distanceFromInitialPoint;
    private final int roadId;

    public PointTracker(String point, String previousPoint, int distanceFromInitialPoint, int roadId) {
        this.point = point;
        this.previousPoint = previousPoint;
        this.distanceFromInitialPoint = distanceFromInitialPoint;
        this.roadId = roadId;
    }

    /**
     * the Compare method for sorting PointTracker objects according to distance from initial point.
     * @param o the object to be compared.
     * @return 0,1,-1
     */
    public int compareTo(PointTracker o) {
        if(this.distanceFromInitialPoint < o.distanceFromInitialPoint) {
            return -1;
        } else if(this.distanceFromInitialPoint > o.distanceFromInitialPoint) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getPoint() {
        return point;
    }

    public String getPreviousPoint() {
        return previousPoint;
    }

    public int getDistanceFromInitialPoint() {
        return distanceFromInitialPoint;
    }

    public int getRoadId() {
        return roadId;
    }

}
