package geometries;

import primitives.Point;

/**
 * Abstract class RadialGeometry implements the Geometry interface and
 * represents a geometric body with a radius.  
 */
public abstract class RadialGeometry implements Geometry {

	/**
	 * Radius of the geometric body
	 */
	protected final double radius;

	/**
	 * Radius squared of the geometric body
	 */
	protected final double radiusSquared;

	/**
	 * Constructs a RadialGeometry object with the given radius.
	 *
	 * @param radius1 the radius1 of the geometric body      
	 */
	public RadialGeometry(double radius1) {
		radius = radius1;
		radiusSquared = radius1 * radius1;
	}
	/**
	 * getRadius
	 */
	public double getRadius() {
		
		return radius;
		}
}
