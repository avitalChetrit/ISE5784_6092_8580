/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Cylinder class
 * 
 * @author Tal and Avital
 */
class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Partitions Tests ==============
		// TC01: Test for a general case
		double radius = 1;
		double height = 2;
		Cylinder cylinder = new Cylinder(radius, height);
		// Choose a point on the surface of the cylinder
		Point testPoint = new Point(1, 0, 1); // Point on the surface of the cylinder
		// Calculate the vector from the center of the cylinder to the test point
		Vector expectedNormal = new Vector(0, 1, 0); // Normal vector pointing outwards
		// Get the normal vector at the test point
		Vector actualNormal = cylinder.getNormal(testPoint);
		// Check if the actual normal is equal to the expected normal
		assertEquals(expectedNormal, actualNormal, "getNormal() does not return the correct normal vector");
	}
}
