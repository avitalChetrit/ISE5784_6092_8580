package geometries;
import primitives.Point;

/**
 * Class Triangle represents a triangle in three-dimensional space.
 */
public class Triangle extends Polygon {
    /**
     * Constructs a Triangle object with three given points.
     *
     * @param point1 the first point of the triangle
     * @param point2 the second point of the triangle
     * @param point3 the third point of the triangle
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3); // Calls the constructor of the superclass Polygon
    }
}