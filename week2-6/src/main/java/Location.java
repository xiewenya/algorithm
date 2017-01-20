/**
 * Created by bresai on 2017/1/20.
 */
public class Location {
    double x;
    double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance (Location end){
        return Math.sqrt((x - end.getX())*(x - end.getX()) + (y - end.getY())*(y - end.getY()));
    }
}
