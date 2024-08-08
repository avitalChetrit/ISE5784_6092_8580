package geometries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import primitives.Ray;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Ray;
import scene.Scene;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.List;
import java.util.List;

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
	 public Geometries remove(Geometry givenGeometry) {
	        Geometries list = new Geometries();
	        for (var geometry : geometries) {
	            if (!geometry.equals(givenGeometry))
	                list.add(geometry);
	        }
	        return list;
	    }
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;

		for (Intersectable geometry : this.geometries) {
			List<GeoPoint> intersections1 = geometry.findGeoIntersections(ray);
			if (intersections1 != null) {
				if (intersections == null)
					intersections = new LinkedList<>(intersections1);
				else
					intersections.addAll(intersections1);
			}
		}

		return intersections;
	}
    @Override
    public int[][] calcBoundary() {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (var geometry : geometries) {
            if (geometry.boundary[0][0] < minX)
                minX = geometry.boundary[0][0];
            if (geometry.boundary[0][1] > maxX)
                maxX = geometry.boundary[0][1];
            if (geometry.boundary[1][0] < minY)
                minY = geometry.boundary[1][0];
            if (geometry.boundary[1][1] > maxY)
                maxY = geometry.boundary[1][1];
            if (geometry.boundary[2][0] < minZ)
                minZ = geometry.boundary[2][0];
            if (geometry.boundary[2][1] > maxZ)
                maxZ = geometry.boundary[2][1];
        }
        return new int[][]{{(int) minX, (int) Math.ceil(maxX)},
                {(int) minY, (int) Math.ceil(maxY)},
                {(int) minZ, (int) Math.ceil(maxZ)}};
    }
    /**
     * move over all geometric entities of a scene and return a hashmap of all the none empty voxels
     *
     * @param scene the scene
     * @return the hash map of voxels
     */
    public HashMap<Double3, Geometries> attachVoxel(Scene scene) {
        HashMap<Double3, Geometries> voxels = new HashMap<>();
        List<Double3> voxelIndexes;
        for (var geometry : geometries) {
            voxelIndexes = geometry.findVoxels(scene);
            for (var index : voxelIndexes) {
                if (!voxels.containsKey(index))//the voxel already exists in the map
                    voxels.put(index, new Geometries(geometry));
                else {
                    voxels.get(index).add(geometry);
                }
            }
        }
        return voxels;
    }
    /**
     * boundary getter
     * @return the matrix of the boundary
     */
    public int[][] getBoundary(){
        return boundary;
    }
}
