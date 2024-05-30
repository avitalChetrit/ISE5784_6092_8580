package geometries;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
	    List<Point> intersections = new ArrayList<>();

	    // Intersect the ray with the plane of the triangle
	    List<Point> planeIntersections = plane.findIntsersections(ray);
	    if (planeIntersections == null || planeIntersections.isEmpty()) {
	        return null;; // No intersection with the plane, return empty list
	    }

	    // Check if the intersection points are inside the triangle
	    for (Point intersection : planeIntersections) {
	        // Calculate vectors from p0 to the intersection point
	        Vector v0 = intersection.subtract(vertices[0]);
	        Vector v1 = p1.subtract(vertices[0]);
	        Vector v2 = p2.subtract(vertices[0]);

	        // Calculate normalized cross products
	        Vector n1 = v0.crossProduct(v1).normalize();
	        Vector n2 = v1.crossProduct(v2).normalize();
	        Vector n3 = v2.crossProduct(v0).normalize();

	        // Check if all dot products have the same sign
	        if (Util.isZero(n1.dotProduct(n2)) && Util.isZero(n1.dotProduct(n3)) && Util.isZero(n2.dotProduct(n3))) {
	            intersections.add(intersection);
	        }
	    }

	    return intersections.isEmpty() ? null : intersections;
	}
}