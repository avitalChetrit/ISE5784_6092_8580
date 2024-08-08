package geometries;

import java.util.List;

import primitives.*;

/**
 * Class Tube represents a three-dimensional tube.
 */
public class Tube extends RadialGeometry {
	/** Axis ray of the tube */
	protected final Ray axisRay;

	/**
	 * Constructs a Tube object with the given radius and axis ray.
	 *
	 * @param radius  the radius of the tube
	 * @param axisRay the axis ray of the tube
	 */
	public Tube(double radius, Ray axisRay) {
		super(radius);
		this.axisRay = axisRay;
	}

	/**
	 * Returns the normal vector to the surface of the tube at a given point.
	 *
	 * @param point The point on the surface of the tube
	 * @return The normal vector to the surface of the tube at the given point
	 */
	@Override
	public Vector getNormal(Point point) {

		// Calculate the parameter t along the axis direction
		double t = axisRay.getDirection().dotProduct(point.subtract(axisRay.getHead()));

		// Calculate the closest point 'O' on the axis to the given point
		Point o = axisRay.getPoint(t);

		// Return the normalized vector from the closest point on the axis to the given
		// point
		return point.subtract(o).normalize();
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// Auto-generated method stub
		return null;
	}
    @Override
    public int[][] calcBoundary() {//there is no boundary to infinite geometric entity
        return null;
    }
}
