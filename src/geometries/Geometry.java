package geometries;
import primitives.Point;
import primitives.Vector;
/**
 * Interface Geometry represents any geometric body.
 */
public interface Geometry {
    
    /**
     * Method that returns the normal vector to the body at a given point.
     * @return a vector
     * @param point the point on the body's surface
     */
    Vector getNormal(Point point);
}