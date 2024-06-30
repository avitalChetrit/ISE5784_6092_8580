package geometries;

import primitives.Ray;
import primitives.Vector;

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
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// Find intersection points with the plane containing the triangle
		List<Point> intersectionPoints = plane.findIntersections(ray);
		// If there are no intersection points with the plane, return null
		if (intersectionPoints == null)
			return null;

		var head = ray.getHead();
		Vector v = ray.getDirection();

		// Check if the intersection point lies inside the triangle
		Vector v1 = vertices.getFirst().subtract(head);
		Vector v2 = vertices.get(1).subtract(head);
		Vector n1 = v1.crossProduct(v2).normalize();
		double sign1 = alignZero(v.dotProduct(n1));
		if (sign1 == 0)
			return null;

		Vector v3 = vertices.get(2).subtract(head);
		Vector n2 = v2.crossProduct(v3).normalize();
		double sign2 = alignZero(v.dotProduct(n2));
		if (sign1 * sign2 <= 0)
			return null;

		Vector n3 = v3.crossProduct(v1).normalize();
		double sign3 = alignZero(v.dotProduct(n3));
		if (sign1 * sign3 <= 0)
			return null;

		return List.of(new GeoPoint(this, intersectionPoints.getFirst()));
	}

}