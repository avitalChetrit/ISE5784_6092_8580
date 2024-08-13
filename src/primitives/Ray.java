package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Ray class represents a point and a direction vector in 3D space
 * 
 * @author Avital and Tal
 */
public class Ray {
	/** point in ray */
	private final Point head;
	/** vector direction */
	private final Vector direction;
	/**
	 * A constant delta value used for numerical approximations or small adjustments
	 */
	private static final double DELTA = 0.1;

	/**
	 * ray constructor
	 * 
	 * @param point  in ray
	 * @param vector in ray
	 */
	public Ray(Point point, Vector vector) {
		head = point;
		direction = vector.normalize(); // Ensure vector is normalized
	}

	/**
	 * ray constructor with offset point
	 * 
	 * @param point     in ray
	 * @param direction in ray
	 * @param normal    on plane
	 */
	public Ray(Point point, Vector direction, Vector normal) {
		this.direction = direction.normalize();
		double nv = normal.dotProduct(this.direction);
		Vector dltVector = normal.scale(nv < 0 ? -DELTA : DELTA);
		head = point.add(dltVector);

	}

	/**
	 * Returns the head point of the vector.
	 * 
	 * @return the head point of the vector
	 */
	public Point getHead() {
		return head;
	}

	/**
	 * Returns the direction vector.
	 * 
	 * @return the direction vector
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * Computes a point on the ray at a given distance from the ray's origin.
	 *
	 * @param t The distance from the ray's origin to the computed point.
	 * @return The computed point on the ray at the specified distance from its
	 *         origin. If t is zero, the method returns the ray's origin point.
	 */
	public Point getPoint(double t) {
		return Util.isZero(t) ? head : head.add(direction.scale(t));
	}

	/**
	 * Finds the closest point to the start of the ray from a collection of points.
	 * 
	 * @param points The collection of points.
	 * @return The closest point to the start of the ray.
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * Finds the closest GeoPoint to the start of the ray from a collection of
	 * GeoPoints.
	 * 
	 * @param intersections The collection of GeoPoints.
	 * @return The closest GeoPoint to the start of the ray.
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if (intersections == null || intersections.isEmpty()) {
			return null;
		}

		// Initialize variables to store the closest GeoPoint and its distance
		GeoPoint closestGeoPoint = null;
		double closestDistance = Double.POSITIVE_INFINITY;

		// Iterate through the list of GeoPoints
		for (GeoPoint geoPoint : intersections) {
			// Calculate the distance between the origin of the ray and the current GeoPoint
			double distance = head.distance(geoPoint.point);

			// Check if the current GeoPoint is closer than the previous closest GeoPoint
			if (distance < closestDistance) {
				closestGeoPoint = geoPoint;
				closestDistance = distance;
			}
		}

		// Return the closest GeoPoint
		return closestGeoPoint;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	@Override
	public String toString() {
		return "Ray:" + head + "->" + direction;
	}

	/// ???
	/**
	 * 
	 * Generates a bundle of rays originating from points in a list and directed
	 * towards a focus point.
	 * 
	 * @param focusPoint The point towards which all the rays will be directed.
	 * @param points     The list of points from which the rays will originate.
	 * @return A list of rays originating from the given points and directed towards
	 *         the focus point.
	 */
	public static List<Ray> RayBundle(Point focusPoint, List<Point> points) {
		List<Ray> rays = new ArrayList<>();
		for (Point point : points) {
			rays.add(new Ray(point, focusPoint.subtract(point)));
		}
		return rays;
	}
}
