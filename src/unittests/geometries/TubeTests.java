/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import geometries.Tube;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Tal and Avital
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
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
		assertEquals(expectedNormal, actualNormal, "getNormal() does not return the correct normal vector");
		
		 // =============== Boundary Values Tests ==================
        //TC02: When connecting the point to the top of the beam
        //of the axis of the cylinder makes a right angle with the axis -
        // the point "is in front of the head of the horn" when (P-P0) is orthogonal to v
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 10, 0)),
                "ERROR: (P-P0) is orthogonal to v");
	}
	
	/**
	 * Tests the {@link geometries.Tube#findIntersections(primitives.Ray)} method.
	 * 
	 * <p>
	 * This test is currently disabled and will fail with the message "not yet
	 * implemented". It serves as a placeholder for future implementation.
	 */
	@Test
	@Disabled
	void testfindIntersections() {

		fail("not yet implemented");
	}

}
