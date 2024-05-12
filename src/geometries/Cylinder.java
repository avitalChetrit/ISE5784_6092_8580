package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Cylinder represents a three-dimensional cylinder.
 */
public class Cylinder extends RadialGeometry {
    private final double height; // Height of the cylinder
    
    /**
     * Constructs a Cylinder object with the given radius and height.
     *
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(double radius, double height) {
        super(radius);
        this.height = height;
    }
    
    /**
     * Override that returns null
     */
    @Override
	public Vector getNormal(Point point) {
	    // The normal vector to a plane is constant and can be pre-calculated
	    return null;
	}
}
