package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Color;

/**
 * Abstract class Geometry represents any geometric body.
 */
public abstract class Geometry extends Intersectable {

	/**
	 * Field representing the emission color of the geometry.
	 */
	protected Color emission = Color.BLACK;

	/**
	 * Method that returns the emission color of the geometry.
	 *
	 * @return the emission color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Method to update the emission color of the geometry.
	 *
	 * @param emission the new emission color to set
	 * @return the updated Geometry object
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * Method that returns the normal vector to the surface body at a given point.
	 * This method must be implemented by subclasses.
	 *
	 * @param point the point on the body's surface
	 * @return a vector
	 */
	public abstract Vector getNormal(Point point);
}
