package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;

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
		return (point.subtract(center).normalize());
	}

	@Override
	protected List<Intersectable.GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// Initialize an empty list to store the intersection GeoPoints
		// List<GeoPoint> intersections = new ArrayList<>();
		if (ray.getHead().equals(this.center))
			return List.of(new Intersectable.GeoPoint(this, ray.getPoint(this.radius)));

		// Calculate the vector from the ray's start point to the center of the sphere
		Vector u = this.center.subtract(ray.getHead());

		// Calculate the projection of u on the ray's direction vector
		double tm = u.dotProduct(ray.getDirection());
		// Calculate the distance from the ray's start point to the closest point to the
		// sphere's center
		double dSquared = u.lengthSquared() - tm * tm;
		double thSquared = this.radiusSquared - dSquared;
		// If the distance is greater than the sphere's radius, there are no
		// intersections
		if (alignZero(thSquared) <= 0)
			return null; // Return an empty list

		// Calculate the distance from the closest point to the intersection points on
		// the sphere's surface
		double th = Math.sqrt(thSquared);

		// Calculate the intersection points. It's always t2 > t1
		double t2 = tm + th;
		if (alignZero(t2) <= 0)
			return null; // both points are behind the ray

		double t1 = tm - th;

		return alignZero(t1) <= 0
				// Only one intersection point
				? List.of(new GeoPoint(this, ray.getPoint(t2)))
				// Two intersection points
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	}

}