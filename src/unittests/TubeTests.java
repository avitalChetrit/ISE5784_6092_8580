/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Tube;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
/**
* Unit tests for primitives.Point class
* @author Tal and Avital
*/
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
		@Test
		void testGetNormal() {
			// ============ Partitions Tests ==============
			// TC01: Test for a general case
			double radius = 1;
			Ray axisRay = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)); // Tube along the Z-axis
			Tube tube = new Tube(radius, axisRay);
			// Choose a point on the tube
			Point testPoint = new Point(1, 0, 1); // Point on the surface of the tube
			// Calculate the vector from the center of the tube to the test point
			Vector expectedNormal = new Vector(1, 0, 0); // Normal vector pointing outwards
			// Get the normal vector at the test point
			Vector actualNormal = tube.getNormal(testPoint);
			//TC10 Check if the actual normal is equal to the expected normal
			assertEquals(expectedNormal, actualNormal, "getNormal() does not return the correct normal vector");
		}

}
