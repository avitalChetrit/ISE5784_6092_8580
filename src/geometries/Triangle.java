package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Util;
import java.util.List;
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

	@Override
	public List<Point> findIntsersections(Ray ray) {

		// we take three vectors from the same starting point and connect them to the
		// triangle's vertices
		// we get a pyramid

		// Check if the ray intersect the plane.
		if (plane.findIntsersections(ray) == null) {
			return null;
		}
		// the three vectors from the same starting point
		Vector v1 = vertices.get(0).subtract(ray.getHead());
		Vector v2 = vertices.get(1).subtract(ray.getHead());
		Vector v3 = vertices.get(2).subtract(ray.getHead());

		// we want to get a normal for each pyramid's face so we do the crossProduct
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();

		// the ray's vector - it has the same starting point as the three vectors from
		// above
		Vector v = ray.getDirection();

		// check if the vector's direction (from Subtraction between the ray's vector to
		// each vector from above) are equal
		// if not - there is no intersection point between the ray and the triangle
		if ((Util.alignZero(v.dotProduct(n1)) > 0 && Util.alignZero(v.dotProduct(n2)) > 0
				&& Util.alignZero(v.dotProduct(n3)) > 0)
				|| (Util.alignZero(v.dotProduct(n1)) < 0 && Util.alignZero(v.dotProduct(n2)) < 0
						&& Util.alignZero(v.dotProduct(n3)) < 0)) {

			return plane.findIntsersections(ray);
		}
		return null;
	}
}