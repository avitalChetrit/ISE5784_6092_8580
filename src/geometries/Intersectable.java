package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import primitives.Double3;
import primitives.Point;
import primitives.Point;
import primitives.Ray;
import primitives.Ray;
import scene.Scene;
import java.util.LinkedList;
import java.util.List;
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
			return obj instanceof GeoPoint geoPoint //
					&& geometry == geoPoint.geometry && point.equals(geoPoint.point);

		}

		@Override
		public String toString() {
			return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + '}';
		}

	}
	   /**
     * boundary of the entity represented by the array [x[min,max],y[min,max],z[min,max]]
     */
    public int[][] boundary;
    /**
     * finds the boundary values of the geometric entity or a group of geometric entities
     *
     * @return the geometry boundary
     */
    protected abstract int[][] calcBoundary();

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
	  /**
     * boundary getter
     *
     * @return the boundary
     */
    public int[][] getBoundary() {
        return boundary;
    }
    /**
     * return the indexes of all voxels that the geometric entity intersects with
     * @param scene the scene that we would use its voxels
     * @return the indexes of the voxels intersected with this
     */
    protected List<Double3> findVoxels(Scene scene) {
        List<Double3> indexes = new LinkedList<>();//since we won't remove any voxel but only add we will use linked list
        double xEdgeVoxel=scene.getXEdgeVoxel();
        double yEdgeVoxel=scene.getYEdgeVoxel();
        double zEdgeVoxel=scene.getZEdgeVoxel();
        int xMinIndex = (int) ((this.boundary[0][0] - scene.geometries.boundary[0][0]) / xEdgeVoxel - 0.01);
        int xMaxIndex = (int) ((this.boundary[0][1] - scene.geometries.boundary[0][0]) / xEdgeVoxel - 0.01);
        int yMinIndex = (int) ((this.boundary[1][0] - scene.geometries.boundary[1][0]) / yEdgeVoxel - 0.01);
        int yMaxIndex = (int) ((this.boundary[1][1] - scene.geometries.boundary[1][0]) / yEdgeVoxel - 0.01);
        int zMinIndex = (int) ((this.boundary[2][0] - scene.geometries.boundary[2][0]) / zEdgeVoxel - 0.01);
        int zMaxIndex = (int) ((this.boundary[2][1] - scene.geometries.boundary[2][0]) / zEdgeVoxel - 0.01);
        //move over all the voxels in the range of indexes
        for (int i = xMinIndex; i <= xMaxIndex; i++) {
            for (int j = yMinIndex; j <= yMaxIndex; j++) {
                for (int k = zMinIndex; k <= zMaxIndex; k++) {
                    indexes.add(new Double3(i, j, k));
                }
            }
        }
        return indexes;
    }
}
