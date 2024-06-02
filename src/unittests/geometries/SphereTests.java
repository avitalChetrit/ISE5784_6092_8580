/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

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
		Point center = new Point(1, 1, 1);
		double radius = 2;
		Sphere sphere = new Sphere(center, radius);

		Point testPoint = new Point(3, 1, 1); // A point on the surface of the sphere
		Vector normal = sphere.getNormal(testPoint);

		assertNotNull(normal, "getNormal() should not return null");

		// Expected normal vector
		Vector expectedNormal = new Vector(1, 0, 0);

		// Check if the returned normal vector is as expected
		assertEquals(expectedNormal, normal, "getNormal() did not return the correct normal vector");

		// Ensure the normal vector is normalized
		assertEquals(1, normal.length(), "Normal vector is not normalized");
	}

	/**
	 * Represents a point (0, 0, 1) in 3D space.
	 */
	private final Point p001 = new Point(0, 0, 1);

	/**
	 * Represents a point (1, 0, 0) in 3D space.
	 */
	private final Point p100 = new Point(1, 0, 0);

	/**
	 * Represents a vector (0, 0, 1) in 3D space.
	 */
	private final Vector v001 = new Vector(0, 0, 1);

	/**
	 * Test method for {@link geometries.Sphere#findIntsersections(primitives.Ray)}.
	 */

	@Test
	public void testfindIntsersections() {
		Sphere sphere = new Sphere(p100, 1d);
		final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
		final var exp = List.of(gp1, gp2);
		final Vector v310 = new Vector(3, 1, 0);
		final Vector v110 = new Vector(1, 1, 0);
		final Point p01 = new Point(-1, 0, 0);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntsersections(new Ray(p01, v110)), "Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		var result1 = sphere.findIntsersections(new Ray(p01, v310)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
		assertEquals(2, result1.size(), "Wrong number of points");
		assertEquals(exp, result1, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		Point p02 = new Point(0.5, 0, 0);
		var result2 = sphere.findIntsersections(new Ray(p02, v310)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p02))).toList();
		assertEquals(1, result2.size(), "Ray from inside sphere should intersect once");

		// TC04: Ray starts after the sphere (0 points)
		Point p03 = new Point(3, 0, 0);
		assertNull(sphere.findIntsersections(new Ray(p03, v310)), "Ray's line out of sphere");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		Point p04 = new Point(1, 0, 1);
		var result3 = sphere.findIntsersections(new Ray(p04, new Vector(-1, 0, -1))).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p04))).toList();
		assertEquals(1, result3.size(), "Ray from sphere surface inside should intersect once");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntsersections(new Ray(p04, new Vector(1, 0, 1))),
				"Ray from sphere surface outside should not intersect");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		var result4 = sphere.findIntsersections(new Ray(p01, new Vector(2, 0, 0))).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
		assertEquals(2, result4.size(), "Ray through center should intersect twice");

		// TC14: Ray starts at sphere and goes inside (1 points)
		Point p05 = new Point(2, 0, 0);
		var result5 = sphere.findIntsersections(new Ray(p05, new Vector(-1, 0, 0))).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p05))).toList();
		assertEquals(1, result5.size(), "Ray from surface through center should intersect once");

		// TC15: Ray starts inside (1 points)
		var result6 = sphere.findIntsersections(new Ray(p02, new Vector(2, 0, 0))).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p02))).toList();
		assertEquals(1, result6.size(), "Ray from inside through center should intersect once");

		// TC16: Ray starts at the center (1 points)
		var result7 = sphere.findIntsersections(new Ray(p100, new Vector(1, 0, 0))).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p100))).toList();
		assertEquals(1, result7.size(), "Ray from center should intersect once");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntsersections(new Ray(p05, new Vector(1, 0, 0))),
				"Ray from surface outside should not intersect");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntsersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
				"Ray's line out of sphere");
		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		Point p06 = new Point(1, 2, 0);
		assertNull(sphere.findIntsersections(new Ray(p06, new Vector(2, 0, 0))),
				"Ray tangential to sphere before tangent point should not intersect");

		// TC20: Ray starts at the tangent point
		Point p07 = new Point(2, 2, 0);
		assertNull(sphere.findIntsersections(new Ray(p07, new Vector(2, 0, 0))),
				"Ray tangential to sphere at tangent point should not intersect");

		// TC21: Ray starts after the tangent point
		Point p08 = new Point(1, 1, 0);
		assertNull(sphere.findIntsersections(new Ray(p08, new Vector(0, 1, 0))),
				"Ray tangential to sphere after tangent point should not intersect");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		Point p09 = new Point(-1, 1, 0);
		assertNull(sphere.findIntsersections(new Ray(p09, new Vector(1, 0, 0))),
				"Ray orthogonal to center line should not intersect");

	}
}
