package primitives;

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
		// Initialize variables to store the closest point and its distance
		Point closestPoint = null;
		double closestDistance = Double.POSITIVE_INFINITY;

		// Iterate through the list of points
		for (Point point : points) {
			// Calculate the distance between the origin of the ray and the current point
			double distance = head.distance(point);
			// Check if the current point is closer than the previous closest point
			if (distance < closestDistance) {
				closestPoint = point;
				closestDistance = distance;
			}
		}

		// Return the closest point
		return closestPoint;
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

}