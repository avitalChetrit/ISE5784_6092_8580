package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * The Intersectable interface represents a geometrical object that can be
 * intersected by a ray. Implementing classes should provide a method to find
 * intersections of the object with a given ray.
 */
public interface Intersectable {
	/**
	 * Finds intersection points between the intersectable object and a given ray.
	 * 
	 * @param ray The ray to intersect with the object.
	 * @return A list of intersection points between the object and the ray. If no
	 *         intersections are found, an empty list is returned.
	 */
	public List<Point> findIntersections(Ray ray);

}
