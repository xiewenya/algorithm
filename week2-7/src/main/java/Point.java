/**
 * Created by bresai on 2017/1/24.
 */
public class Point {
    double x;
    double y;
    int index;
    double distance;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distance(Point p) {
        return (x - p.getX())*(x - p.getX()) + (y - p.getY())*(y - p.getY());
    }

}
