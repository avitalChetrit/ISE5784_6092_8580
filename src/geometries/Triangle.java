package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Util;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
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

	@Override
	public List<Intersectable.GeoPoint> findGeoIntersectionsHelper(Ray ray) {
	    // Find intersection points with the plane containing the triangle
	    List<Point> intersectionPoints = plane.findIntersections(ray);

	    // If there are no intersection points with the plane, return null
	    if (intersectionPoints == null || intersectionPoints.isEmpty()) {
	        return null;
	    }

	    List<Intersectable.GeoPoint> geoPoints = new ArrayList<>();
	    
	    for (Point intersectionPoint : intersectionPoints) {
	        // Check if the intersection point lies inside the triangle
	        Vector v = ray.getDirection();
	        Vector v1 = vertices.get(0).subtract(ray.getHead());
	        Vector v2 = vertices.get(1).subtract(ray.getHead());
	        Vector v3 = vertices.get(2).subtract(ray.getHead());

	        Vector n1 = v1.crossProduct(v2).normalize();
	        double sign1 = alignZero(v.dotProduct(n1));

	        Vector n2 = v2.crossProduct(v3).normalize();
	        double sign2 = alignZero(v.dotProduct(n2));

	        Vector n3 = v3.crossProduct(v1).normalize();
	        double sign3 = alignZero(v.dotProduct(n3));

	        if ((sign1 > 0 && sign2 > 0 && sign3 > 0) || (sign1 < 0 && sign2 < 0 && sign3 < 0)) {
	            // Intersection point is inside the triangle, create a GeoPoint and add to the list
	            geoPoints.add(new Intersectable.GeoPoint(this, intersectionPoint));
	        }
	    }

	    return geoPoints.isEmpty() ? null : geoPoints;
	}

}