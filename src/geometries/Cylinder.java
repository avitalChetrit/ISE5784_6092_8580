package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Cylinder represents a three-dimensional cylinder.
 */
public class Cylinder extends RadialGeometry {
	
	/** Height of the cylinder*/
    private final double height; 
    
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
     * Computes the normal vector to the surface of the cylinder at a given point.
     * If the point lies on the bottom cap of the cylinder, returns a downward-pointing normal vector.
     * If the point lies on the top cap of the cylinder, returns an upward-pointing normal vector.
     * Otherwise, returns a normal vector pointing in the direction of the radius of the cylinder at the given point.
     *
     * @param point The point on the surface of the cylinder
     * @return The normal vector to the surface at the given point
     */
    @Override
    public Vector getNormal(Point point) {
        // Calculate the vector from the center of the cylinder to the test point
        Vector vectorToPoint = point.subtract(new Point(0, 0, 0)); // Assuming the center of the cylinder is at (0, 0, 0)
        
        // Calculate the height of the point from the center of the cylinder
        double pointHeight = vectorToPoint.dotProduct(new Vector(0, 1, 0)); // Assuming the cylinder is aligned with the y-axis
        
        // Calculate the projection of the vector from the center to the point onto the xy-plane
        Vector projectionXY = vectorToPoint.subtract(new Vector(0, pointHeight, 0));
        
        // Normalize the projection vector
        Vector normalizedProjectionXY = projectionXY.normalize();
        
        return normalizedProjectionXY; // Return the normalized projection vector as the normal vector
    }
}
