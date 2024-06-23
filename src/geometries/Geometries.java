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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;

		for (Intersectable geometry : this.geometries) {
			List<GeoPoint> intersections1 = geometry.findGeoIntersectionsHelper(ray);
			if (intersections1 != null) {
				if (intersections == null)
					intersections = new LinkedList<>();
				intersections.addAll(intersections1);
			}
		}

		if (intersections == null)
			return null;
		return intersections;

	}

}
