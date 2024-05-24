package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Plane represents a flat geometric surface in three-dimensional space.
 */

	public class Plane implements Geometry {

		/**point in plane */
		private final Point point;
		/**vector in plane*/
		private final Vector normal;
		
		/**
	     * Constructs a Plane object using three points.
	     *
	     * <p>The constructor calculates the normal vector based on the points given, and stores one of the points
	     * as the reference point of the plane.
	     *
	     * @param point1 the first point
	     * @param point2 the second point
	     * @param point3 the third point
	     */
	    public Plane(Point point1, Point point2, Point point3) {
	        // Calculate the normal vector based on the given points
	        // Implementation pending
	        this.point = point1; // Store one of the points as the reference point
	        this.normal = null; // Normal vector calculation pending
	    }

    /**
     * Constructs a Plane object using a point and a normal vector.
     *
     * @param point1  a point on the plane
     * @param normal1 the normal vector to the plane
     */
    public Plane(Point point1, Vector normal1) {
        this.point = point1;
        
        // Ensure the normal vector is normalized
        this.normal = normal1.normalize();
    }
	

    /**
	 * new getNormal
	 * @return vector normal
	 */
    public Vector getNormal() {
	    // The normal vector to a plane is constant and can be pre-calculated
	    return normal;
	}
    
    
	/**
	 * Returns the normal vector to the plane at a given point.
	 *
	 * @param point the point on the surface of the plane
	 * @return the normal vector to the plane at the given point
	 */
	@Override
	public Vector getNormal(Point point) {
	    // get the vector from the head of the axis to the given point 
		vector v= point.subtract(axis.getHead());
	    //calculate the parameter t along the axis direction
		double t= alignZero(axis.getDirection.dotProduct(v);
	   //check if the point is on the axis of the cylinder
	   if(isZero(t)){
		   //if the point is on the axis of the cylinder' return the normalized טובבבב
		   return v. normlize();
	    // calculate the closest point '0' on the axis to the given point
	    Point 0=axis.getPoint(t);

	//return thr normalized vector from the closest point on the axis to the given point
	    retun point.substract(0).normalize;   

	
		
	}
	


}
