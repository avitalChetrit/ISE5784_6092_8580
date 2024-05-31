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
	 * Test method for {@link geometries.Plane#findIntsersections(primitives.Ray)}.
	 * 
	 * Equivalence Partitions: EP: Ray intersects the plane EP: Ray does not
	 * intersect the plane
	 * 
	 * Boundary Value Analysis: BVA: Ray is parallel to the plane and not included
	 * in the plane BVA: Ray is orthogonal to the plane and starts before the plane
	 * BVA: Ray is orthogonal to the plane and starts at the plane BVA: Ray is
	 * orthogonal to the plane and starts after the plane BVA: Ray is neither
	 * orthogonal nor parallel to and begins at the plane BVA: Ray is neither
	 * orthogonal nor parallel to the plane and begins in the same point which
	 * appears as reference point in the plane
	 */
	@Test
	void testfindIntsersections() {
		Plane plane = new Plane(new Point(1, 1, 1), new Vector(0, 0, 1));
		// ============ Equivalence Partitions Tests ==============

		// EP: Ray intersects the plane
		Ray ray1 = new Ray(new Point(2, 2, 0), new Vector(0, 0, 1));
		List<Point> result1 = plane.findIntsersections(ray1);
		assertNotNull(result1, "Ray intersects the plane");
		assertEquals(1, result1.size(), "Wrong number of points");
		assertEquals(new Point(2, 2, 1), result1.get(0), "Ray intersects plane at (2, 2, 1)");

		// EP: Ray does not intersect the plane
		Ray ray2 = new Ray(new Point(2, 2, -1), new Vector(0, 0, -1));
		assertNull(plane.findIntsersections(ray2), "Ray does not intersect the plane");

		// BVA: Ray is parallel to the plane and not included in the plane
		Ray ray3 = new Ray(new Point(2, 2, 3), new Vector(1, 0, 0));
		assertNull(plane.findIntsersections(ray3), "Ray is parallel to the plane and not included");

		// BVA: Ray is orthogonal to the plane and starts before the plane
		Ray ray4 = new Ray(new Point(2, 2, 0), new Vector(0, 0, 1));
		List<Point> result4 = plane.findIntsersections(ray4);
		assertNotNull(result4, "Ray is orthogonal to the plane and starts before the plane");
		assertEquals(1, result4.size(), "Wrong number of points");
		assertEquals(new Point(2, 2, 1), result4.get(0), "Ray intersects plane at (2, 2, 1)");

		// BVA: Ray is orthogonal to the plane and starts at the plane
		Ray ray5 = new Ray(new Point(2, 2, 1), new Vector(0, 0, 1));
		assertNull(plane.findIntsersections(ray5), "Ray is orthogonal to the plane and starts at the plane");

		// BVA: Ray is orthogonal to the plane and starts after the plane
		Ray ray6 = new Ray(new Point(2, 2, 3), new Vector(0, 0, 1));
		assertNull(plane.findIntsersections(ray6), "Ray is orthogonal to the plane and starts after the plane");

		// BVA: Ray is neither orthogonal nor parallel to and begins at the plane
		Ray ray7 = new Ray(new Point(2, 2, 1), new Vector(1, 1, 0));
		assertNull(plane.findIntsersections(ray7), "Ray is neither orthogonal nor parallel to and begins at the plane");

		// BVA: Ray is neither orthogonal nor parallel to the plane and begins in the
		// same point which appears as reference point in the plane
		Ray ray8 = new Ray(new Point(1, 1, 1), new Vector(1, 1, 1));
		List<Point> result8 = plane.findIntsersections(ray8);
		assertNotNull(result8, "Ray is neither orthogonal nor parallel and begins at reference point");
		assertEquals(1, result8.size(), "Wrong number of points");
		assertEquals(new Point(2, 2, 2), result8.get(0), "Ray intersects plane at (2, 2, 2)");
	}
}
