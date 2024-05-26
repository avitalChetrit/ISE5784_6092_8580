package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry represents any geometric body.
 */
public interface Geometry {

	/**
	 * Method that returns the normal vector to the surface body at a given point.
	 * 
	 * @param point the point on the body's surface
	 * @return a vector
	 */
	Vector getNormal(Point point);
}