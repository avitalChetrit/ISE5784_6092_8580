package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public final List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

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

		// Override equals method
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof GeoPoint geoPoint))
				return false;
			return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);

		}

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
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);// לתקן

}
