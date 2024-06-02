package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import java.util.List;

/**
 * Class Sphere represents a three-dimensional sphere.
 */
public class Sphere extends RadialGeometry {
	/** Center point of the sphere */
	private final Point center;

	/**
	 * Constructs a Sphere object with the given center point and radius.
	 *
	 * @param center the center point of the sphere
	 * @param radius the radius of the sphere
	 */
	public Sphere(Point center, double radius) {
		super(radius);
		this.center = center;
	}

	@Override
	public Vector getNormal(Point point) {
		// The normal vector to a sphere is the unit vector from the center to the point
		return point.subtract(center).normalize();
	}

	@Override
	public List<Point> findIntsersections(Ray ray) {
		// Initialize an empty list to store the intersection points
		// List<Point> intersections = new ArrayList<>();
		if (ray.getHead().equals(this.center)) {
			return List.of(ray.getPoint(this.radius));
		}

		// Calculate the vector from the ray's start point to the center of the sphere
		Vector u = this.center.subtract(ray.getHead());

		// Calculate the projection of u on the ray's direction vector
		double tm = u.dotProduct(ray.getDirection());

		// Calculate the distance from the ray's start point to the closest point to the
		// sphere's center
		double d = Math.sqrt(u.lengthSquared() - tm * tm);

		// If the distance is greater than the sphere's radius, there are no
		// intersections
		if (d >= this.radius) {
			return null; // Return an empty list
		}

		// Calculate the distance from the closest point to the intersection points on
		// the sphere's surface
		double th = Math.sqrt(radiusSquared - d * d);

		// Calculate the intersection points
		double t1 = tm - th;
		double t2 = tm + th;

		boolean t1Valid = Util.alignZero(t1) > 0;
		boolean t2Valid = Util.alignZero(t2) > 0;
		if (t1Valid && t2Valid) {
			Point p1 = ray.getPoint(t1);
			Point p2 = ray.getPoint(t2);
			return List.of(p1, p2);

		} else if (t2Valid) {
			Point p2 = ray.getPoint(t2);
			return List.of(p2);

		} else
			return null;
	}
}