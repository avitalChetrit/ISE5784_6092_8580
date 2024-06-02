package geometries;

import java.util.ArrayList;
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
public class Geometries implements Intersectable {

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
		for (Intersectable geometry : geometries) {
			this.geometries.add(geometry);
		}
	}

	@Override
	public List<Point> findIntsersections(Ray ray) {
		List<Point> intersections = new LinkedList<>();
		for (Intersectable geometry : geometries) {

			if (geometry.findIntsersections(ray) != null) {
				intersections.addAll(geometry.findIntsersections(ray));
			}
		}
		if (intersections.size() == 0) {
			return null;
		}

		return intersections;
	}

}
