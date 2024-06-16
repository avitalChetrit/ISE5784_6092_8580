package geometries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Geometries class to represent a collection of geometric shapes
 * Implements the Composite design pattern
 */
/**
 * Class representing a collection of geometric shapes that are intersectable.
 */
public class Geometries extends Intersectable {

	/** List to hold the geometric shapes */
	private final List<Intersectable> geometries = new LinkedList<>();

	/**
	 * Default constructor for creating an empty Geometries object.
	 */
	public Geometries() {
	}

	/**
	 * Constructor for creating a Geometries object with specified geometries.
	 *
	 * @param geometries The intersectable geometries to add to this collection.
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * Adds one or more intersectable geometries to the collection.
	 *
	 * @param geometries The intersectable geometries to add.
	 */
	public void add(Intersectable... geometries) {
		Collections.addAll(this.geometries, geometries);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = null;
		for (Intersectable geometry : geometries) {
			var geometryIntersections = geometry.findIntersections(ray);
			if (geometryIntersections != null) {
				if (intersections == null)
					intersections = new LinkedList<>(geometryIntersections);
				else
					intersections.addAll(geometryIntersections);
			}
		}
		return intersections;
	}

}
