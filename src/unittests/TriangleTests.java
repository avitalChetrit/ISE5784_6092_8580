/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
/**
* Unit tests for primitives.Point class
* @author Tal and Avital
*/
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test for a general case
		Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
		Vector expectedNormal = new Vector(0, 0, 1); // Assuming the points define a triangle in the xy-plane
		assertEquals(expectedNormal, triangle.getNormal(new Point(0, 0, 0)), "getNormal() returned wrong normal vector");
		
		// =============== Boundary Values Tests ==================
		// No boundary values tests for this function
	}
}
