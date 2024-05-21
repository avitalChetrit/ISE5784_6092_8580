/**
 * 
 */
package unittests;
import org.junit.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Plane;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
* Unit tests for primitives.Point class
* @author Tal and Avital
*/
class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	public void testPlaneConstructorWithPointAndNormal() {
        // Test case setup
        Point point = new Point(1, 2, 3);
        Vector normal = new Vector(1, 1, 1);

        // Create the plane
        Plane plane = new Plane(point, normal);

        // Verify that the normal vector is normalized
        assertEquals(1.0, plane.getNormal().length(), 1e-10);
    }

}
