package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class Intersectable represents a geometry object that can be
 * intersected by a ray.
 */
public abstract class Intersectable {

	/**
	 * Finds intersection points between the intersectable object and a given ray.
	 *
	 * @param ray The ray to intersect with the object.
	 * @return A list of intersection points between the object and the ray. If no
	 *         intersections are found, an empty list is returned.
	 */
	public abstract List<Point> findIntersections(Ray ray);

	/**
	 * Inner static class GeoPoint representing a geometric intersection point with
	 * associated geometry.
	 */
	public static class GeoPoint {
		/**
		 * The geometry object of this intersection point.
		 */
		public Geometry geometry;

		/**
		 * The actual point of intersection.
		 */
		public Point point;

		/**
		 * Constructor for GeoPoint.
		 *
		 * @param geometry The geometry object of the intersection.
		 * @param point    The actual point of intersection.
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		/**
		 * Override of the equals method to check equality of GeoPoints.
		 *
		 * @param o The object to compare with.
		 * @return true if both objects are equal, false otherwise.
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			GeoPoint geoPoint = (GeoPoint) o;
			return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
		}

		/**
		 * Override of the toString method for GeoPoint.
		 *
		 * @return A string representation of the GeoPoint.
		 */
		@Override
		public String toString() {
			return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
		}
	}

	/**
	 * Public method findGeoIntersections for finding GeoPoints of intersections
	 * between the intersectable object and a given ray.
	 *
	 * @param ray The ray to intersect with the object.
	 * @return A list of GeoPoints representing intersection points between the
	 *         object and the ray.
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * Protected method findGeoIntersectionsHelper for finding GeoPoints of
	 * intersections between the intersectable object and a given ray. This method
	 * should be implemented in subclasses.
	 *
	 * @param ray The ray to intersect with the object.
	 * @return A list of GeoPoints representing intersection points between the
	 *         object and the ray.
	 */
	protected final List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {// לתקן
		// Placeholder implementation, replace with actual logic in subclasses
		return null; // Return an empty list as placeholder
	}

}
