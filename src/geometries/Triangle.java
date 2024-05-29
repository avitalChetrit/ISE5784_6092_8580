package geometries;
import primitives.Ray;
import java.util.List;
import primitives.Point;
import java.util.ArrayList;
import static primitives.Util.*;


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

    /**
     * Finds the intersection points between a ray and the triangle.
     * 
     * @param ray the ray to intersect with the triangle
     * @return a list containing the intersection point(s) if exists, null otherwise
     */
	@Override
	public List<Point> findIntsersections(Ray ray) {
	    Vector n = getNormal(ray.getHead()); // Get the normal vector to the triangle's plane
	    double nv = n.dotProduct(ray.getDirection()); // Calculate the dot product of the normal and the ray's direction
	    if (isZero(nv)) // If the dot product is close to zero, the ray is parallel to the triangle's plane
	        return null; // Return null since there are no intersections
	    Point p0 = getVertices().get(0); // Get the first vertex of the triangle
	    double t = alignZero(n.dotProduct(p0.subtract(ray.getHead())) / nv); // Calculate t using the formula
	    if (t <= 0) // If t is less than or equal to zero, the intersection point is behind the ray's starting point
	        return null; // Return null since there are no intersections
	    Point intersection = ray.getPoint(t); // Calculate the intersection point using the ray's parameter t
	    if (!isPointInside(intersection)) // Check if the intersection point is inside the triangle
	        return null; // Return null since there are no intersections
	    return List.of(intersection); // Return a list containing the intersection point
	}
}