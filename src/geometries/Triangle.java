package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Util;
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
	public List<Point> findIntersections(Ray ray) {
		var intersection = plane.findIntersections(ray);
		// Check if the ray intersect the plane.
		if (intersection == null)
			return null;

		// we take three vectors from the same starting point and connect them to the
		// triangle's vertices
		// we get a pyramid
		// the three vectors from the same starting point
		Point p0 = ray.getHead();
		// the ray's vector - it has the same starting point as the three vectors from
		// above
		Vector v = ray.getDirection();
		Vector v1 = vertices.get(0).subtract(p0);
		Vector v2 = vertices.get(1).subtract(p0);
		// we want to get a normal for each pyramid's face so we do the crossProduct
		Vector n1 = v1.crossProduct(v2).normalize();
		// check if the vector's direction (from Subtraction between the ray's vector to
		// each vector from above) are equal
		// if not - there is no intersection point between the ray and the triangle
		double sign1 = alignZero(v.dotProduct(n1));
		if (sign1 == 0)
			return null;

		Vector v3 = vertices.get(2).subtract(p0);
		Vector n2 = v2.crossProduct(v3).normalize();
		double sign2 = alignZero(v.dotProduct(n2));
		if (sign1 * sign2 <= 0)
			return null;

		Vector n3 = v3.crossProduct(v1).normalize();
		double sign3 = alignZero(v.dotProduct(n3));
		if (sign1 * sign3 <= 0)
			return null;

		return intersection;
	}
}