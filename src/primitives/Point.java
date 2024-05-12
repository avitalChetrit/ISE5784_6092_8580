package primitives;

/**
 * Point class represents a point in 3D space
 */
public class Point {
    /** Coordinate values of the point */

	    protected final Double3 xyz;
	    /**
	     * Constructor that accepts three double values representing the coordinates of the point
	     * @param x X coordinate value
	     * @param y Y coordinate value
	     * @param z Z coordinate value
	     */
	    public Point(double x, double y, double z) {
	        xyz = new Double3(x, y, z);
	     
	    }
	    /**
	     * Constructor that accepts a Double3 object representing the coordinates of the point
	     * @param xyz Double3 object representing the coordinates
	     */
	    public Point(Double3 xyz1) {
	        xyz =(xyz1);
	        
	    }
	    
	    /**
	     * Subtract operation between two points, returning a vector from the second point to the first
	     * @param other The other point
	     * @return Vector from 'other' to 'this' point
	     */
	    public Vector subtract(Point p) {
	    	return new Vector(xyz.subtract(p.xyz));
	    }
	    
	    /**
	     * Add a vector to the point, returning a new point
	     * @param p The vector to add
	     * @return New point after adding the vector
	     */
	    public Point add(Vector vec) {
	    	return new Point(xyz.add(vec.xyz));
	    }
	    
	    /**
	     * Calculate the squared distance between two points
	     * @param other The other point
	     * @return The squared distance between the two points
	     */
	    
	    public double distanceSquared (Point p) {
	    	return((xyz.d1-p.xyz.d1)*(xyz.d1-p.xyz.d1)+(xyz.d2-p.xyz.d2)*(xyz.d2-p.xyz.d2)+(xyz.d3-p.xyz.d3)*(xyz.d3-p.xyz.d3));
	    }
	    
	    /**
	     * Calculate the distance between two points
	     * @param other The other point
	     * @return The distance between the two points
	     */
	    public double distance (Point p) {
	    	return(Math.sqrt(distanceSquared(p)));
	    }

}