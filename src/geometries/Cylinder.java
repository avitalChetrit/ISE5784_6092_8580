package geometries;

import primitives.*;

/**
 * Class Cylinder represents a three-dimensional cylinder.
 */
public class Cylinder extends Tube {

	/** Height of the cylinder */
	@SuppressWarnings("unused")
	private final double height;

	/**
	 * Constructs a Cylinder object with the given radius and height.
	 *
	 * @param axis   the central axis of the cylinder
	 * @param radius the radius of the cylinder
	 * @param height the height of the cylinder
	 */
	public Cylinder(double radius, Ray axis, double height) {
		super(radius, axis);
		this.height = height;
	}

	@Override
	public Vector getNormal(Point point) {
		return null; // if we want to use cylinder in our pictures
	}
}