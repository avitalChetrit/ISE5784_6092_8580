/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Tal and Avital
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test

	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test for a general case
		Point center = new Point(0, 0, 0);
		double radius = 1;
		Sphere sphere = new Sphere(center, radius);
		Vector normal = sphere.getNormal(new Point(1, 0, 0));
		assertNotNull(normal, "getNormal() should return null");
	}
}
