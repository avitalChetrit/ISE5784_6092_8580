/**
 * 
 */
package unittests.geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Plane;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Tal and Avital
 */
class PlaneTests {
	/** A small constant for floating-point comparison precision. */
	private static final double DELTA = 0.00001;

	/**
	 * Test method for {@link Plane#getNormal()}.
	 * 
	 * <p>
	 * This test method verifies the behavior of the {@link Plane#getNormal()}
	 * method by checking the following conditions:
	 * </p>
	 * 
	 * <ul>
	 * <li>TC01: Test for a general case.</li>
	 * <li>Ensure the normal vector has unit length.</li>
	 * <li>Ensure the normal vector is perpendicular to the plane.</li>
	 * </ul>
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test for a general case
		Point point1 = new Point(0, 0, 0);
		Point point2 = new Point(1, 0, 0);
		Point point3 = new Point(0, 1, 0);
		Plane plane = new Plane(point1, point2, point3);
		Vector normal = plane.getNormal();
		// Ensure the normal vector has unit length
		assertEquals(1, normal.length(), DELTA, "Normal vector should have unit length");
		// Ensure the normal vector is perpendicular to the plane
		Vector vector1 = point2.subtract(point1);
		Vector vector2 = point3.subtract(point1);
		double dotProduct = normal.dotProduct(vector1);
		// Ensure the dot product is Equal to 0
		assertEquals(0, dotProduct, DELTA, "Normal vector is not perpendicular to the plane");
		dotProduct = normal.dotProduct(vector2);
		assertEquals(0, dotProduct, DELTA, "Normal vector is not perpendicular to the plane");
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testfindIntsersections() {
		Plane plane = new Plane(new Point(1, 0, 0), new Vector(1, 0, 0));
		// ============ Equivalence Partitions Tests ==============

		// TC01 EP: Ray does not intersect the plane
		Ray ray2 = new Ray(new Point(0, 0, 0), new Vector(-1, -1, 0));
		assertNull(plane.findIntersections(ray2), "Ray does not intersect the plane");

		// TC02 EP: Ray intersects the plane(1)
		Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(1, 1, 0));
		List<Point> result1 = plane.findIntersections(ray1);
		assertNotNull(result1, "Ray intersects the plane");
		assertEquals(1, result1.size(), DELTA, "Wrong number of points");
		assertEquals(new Point(1, 1, 0), result1.get(0), "Ray intersects plane at (1,1,0)");

		// =============== Boundary Values Tests ==================

		// TC03 BVA: Ray is parallel to the plane and not included in the plane
		Ray ray3 = new Ray(new Point(1, 0, 0), new Vector(0, 2, 0));
		assertNull(plane.findIntersections(ray3), "Ray is parallel to the plane and not included");

		// TC04 BVA: Ray is not on the plane
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 2, 0));
		assertNull(plane.findIntersections(ray), "Ray is  not on the plane");

		// TC05 BVA: Ray is orthogonal to the plane and starts before the plane
		Ray ray4 = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
		List<Point> result4 = plane.findIntersections(ray4);
		assertNotNull(result4, "Ray is orthogonal to the plane and starts before the plane");
		assertEquals(1, result4.size(), DELTA, "Wrong number of points");
		assertEquals(new Point(1, 0, 0), result4.get(0), "Ray intersects plane at (2, 2, 1)");

		// TC07 BVA: Ray is orthogonal to the plane and starts after the plane
		Ray ray6 = new Ray(new Point(2, 0, 0), new Vector(2, 0, 0));
		assertNull(plane.findIntersections(ray6), "Ray is orthogonal to the plane and starts after the plane");

		// TC08 BVA: Ray is neither orthogonal nor parallel to and begins at the plane
		Ray ray7 = new Ray(new Point(0, 1, 0), new Vector(0, 0, 2));
		assertNull(plane.findIntersections(ray7), "Ray is neither orthogonal nor parallel to and begins at the plane");

		// TC09 BVA: Ray is neither orthogonal nor parallel to the plane and begins in
		// the
		// same point which appears as reference point in the plane
		Ray ray8 = new Ray(new Point(1, 0, 0), new Vector(0, 1, 0));
		assertNull(plane.findIntersections(ray8),
				"Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane");

		// TC10 BVA: Ray is orthogonal to the plane and starts at the plane(0)
		Ray ray5 = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));
		assertNull(plane.findIntersections(ray5), "Ray is orthogonal to the plane and starts at the plane");

	}
}
