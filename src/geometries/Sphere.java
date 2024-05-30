package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.ArrayList;
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

	/**
	 * Finds the intersection points between a given ray and this sphere.
	 * 
	 * @param ray The ray to find intersections with
	 * @return A list of intersection points between the ray and the sphere, or an
	 *         empty list if there are no intersections
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// Initialize an empty list to store the intersection points
		List<Point> intersections = new ArrayList<>();

		// Calculate the vector from the ray's start point to the center of the sphere
		Vector u = center.subtract(ray.getHead());

		// Calculate the projection of u on the ray's direction vector
		double tm = ray.getDirection().dotProduct(u);

		// Calculate the distance from the ray's start point to the closest point to the
		// sphere's center
		double d = Math.sqrt(u.lengthSquared() - tm * tm);

		// If the distance is greater than the sphere's radius, there are no
		// intersections
		if (d > radius) {
			return intersections; // Return an empty list
		}

		// Calculate the distance from the closest point to the intersection points on
		// the sphere's surface
		double th = Math.sqrt(radiusSquared - d * d);

		// Calculate the intersection points
		double t1 = tm - th;
		double t2 = tm + th;

		// Add the intersection points to the list
		if (t1 > 0) {
			Point p1 = ray.getPoint(t1);
			intersections.add(p1);
		}
		if (t2 > 0) {
			Point p2 = ray.getPoint(t2);
			intersections.add(p2);
		}

		return intersections;
	}

}