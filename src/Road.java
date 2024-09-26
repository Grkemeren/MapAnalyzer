import java.util.List;

/**
 * class for storing data of roads and comparing them according their length.
 */
public class Road implements Comparable<Road>{
    private final List<String> points;
    private final int distance;
    private final int id;

    public Road(List<String> points, int distance, int id) {
        this.points = points;
        this.distance = distance;
        this.id = id;
    }

    /**
     * to compare the roads firstly according their length, if length is equal sorts according to their ids.
     * @param o the object to be compared.
     * @return 1,0,-1 according to compare.
     */
    public int compareTo(Road o) {
        if(this.distance < o.distance) {
            return -1;
        } else if(this.distance > o.distance) {
            return 1;
        } else {
            if(this.id < o.id) {
                return -1;
            } else if(this.id > o.id) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public List<String> getPoints() {
        return points;
    }


    public int getDistance() {
        return distance;
    }


    public int getId() {
        return id;
    }
}
