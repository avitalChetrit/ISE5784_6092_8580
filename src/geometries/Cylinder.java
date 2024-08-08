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
    @Override
    public int[][] calcBoundary() {
        Point firstBaseCenter = axisRay.getHead();
        Point secondBaseCenter = axisRay.getPoint(height);
        return new int[][]{minMax(firstBaseCenter.center.xyz.d1,secondBaseCenter.getX()),
                minMax(firstBaseCenter.getY(), secondBaseCenter.getY()),
                minMax(firstBaseCenter.getZ(), secondBaseCenter.getZ())};
    }
    private int[] minMax(double first,double second){
        return first < second? new int[]{(int)Math.floor(first-radius),(int)Math.ceil(second+radius)}:
                new int[]{(int)Math.floor(second-radius),(int)Math.ceil(first+radius)};
    }
}
