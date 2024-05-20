/**
 * 
 */
package unittests;

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
	void testGetNormalPoint() {
		// ============ Partitions Tests ==============
		 Point p1 = new Point(1, 1, 1);
		    Point p2 = new Point(2, 2, 2);
		    Point p3 = new Point(3, 3, 3);
		    
		    // When
		    Plane plane = new Plane(p1, p2, p3);
		    Vector normal = plane.getNormal(p1);
		    
		    // Then
		    assertTrue(normal.length() <= 1, "The length of the normal vector is greater than 1");
	}

}
