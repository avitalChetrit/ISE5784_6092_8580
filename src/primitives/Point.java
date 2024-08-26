package primitives;

import java.util.ArrayList;
import java.util.List;

/**
 * Point class represents a point in 3D space
 */
public class Point {
	/** Coordinate values of the point */
	public final Double3 xyz;

	/** Zero triad (0,0,0) */
	public static final Point ZERO = new Point(0, 0, 0);

	/** getPoint */

	/**
	 * Constructor that accepts three double values representing the coordinates of
	 * the point
	 * 
	 * @param x coordinate value
	 * @param y coordinate value
	 * @param z coordinate value
	 */
	public Point(double x, double y, double z) {
		this.xyz = new Double3(x, y, z);
	}

	/**
	 * Constructor that accepts a Double3 object representing the coordinates of the
	 * point
	 * 
	 * @param xyz1 representing the coordinates
	 */
	public Point(Double3 xyz1) {
		this.xyz = xyz1;
	}

	/**
	 * Subtract operation between two points, returning a vector from the second
	 * point to the first
	 * 
	 * @param other The other point
	 * @return Vector from 'other' to 'this' point
	 */
	public Vector subtract(Point other) {
		return new Vector(xyz.subtract(other.xyz));
	}

	/**
	 * Add a vector to the point, returning a new point
	 * 
	 * @param vec The vector to add
	 * @return New point after adding the vector
	 */
	public Point add(Vector vec) {
		return new Point(xyz.add(vec.xyz));
	}

	/**
	 * Calculate the squared distance between two points
	 * 
	 * @param p The other point
	 * @return The squared distance between the two points
	 */
	public double distanceSquared(Point p) {
		double deltaX = (xyz.d1 - p.xyz.d1);
		double deltaY = (xyz.d2 - p.xyz.d2);
		double deltaZ = (xyz.d3 - p.xyz.d3);
		return (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
	}

	/**
	 * Calculate the distance between two points
	 * 
	 * @param p the other point
	 * @return The distance between the two points
	 */
	public double distance(Point p) {
		return (Math.sqrt(distanceSquared(p)));
	}

	// Override equals method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Point other) && this.xyz.equals(other.xyz);
	}

	// Override hashCode method
	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public String toString() {
		return "P" + xyz;
	}
	/// ???
	/**
	 * Generates a list of points distributed randomly within a circle.
	 * 
	 * @param gridDensity The number of points to generate.
	 * @param radius The radius of the circle within which points are distributed.
	 * @param center The center point of the circle.
	 * @param up A vector representing the upward direction from the center.
	 * @param right A vector representing the rightward direction from the center.
	 * @return A list of {@link Point} objects randomly distributed within the circle.
	 */
		public static List<Point> generatePoints(int gridDensity, double radius, Point center, Vector up, Vector right) {
			List<Point> points  = new ArrayList<>();

			for (int i = 0; i < gridDensity; i++) {
				double angle = 2 * Math.PI * Math.random();
				double r = radius * Math.sqrt(Math.random());
				double offsetX = r * Math.cos(angle);
				double offsetY = r * Math.sin(angle);

				Point point = center.add(right.scale(offsetX)).add(up.scale(offsetY));
				points.add(point);
			}
			return points;
		}

}
