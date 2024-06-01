/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Tal and Avital
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test for a general case
		Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
		Vector expectedNormal = new Vector(0, 0, 1); // Assuming the points define a triangle in the xy-plane
		assertEquals(expectedNormal, triangle.getNormal(new Point(0, 0, 0)),
				"getNormal() returned wrong normal vector");

		// =============== Boundary Values Tests ==================
		// No boundary values tests for this function
	}

	/**
	 * Test method for findIntersections function
	 */
	@Test
	void findIntersections() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray intersects the triangle at a point inside the triangle
		Triangle triangle = new Triangle(new Point(2,0,0), new Point(0,2,0), new Point(0,-2,0)); // Triangle with
																										// vertices at
																										// (2,0,0),
																										// (0,2,0), and
																										// (0,-2,0)
		Ray rayInside = new Ray(new Point(0,0,-2), new Vector(1,0,2)); // Ray from inside the triangle
		List<Point> resultInside = triangle.findIntsersections(rayInside);
		assertEquals(1, resultInside.size(), "Wrong number of intersection points");
		assertEquals(new Point(1,0,0), resultInside.getFirst(), "Ray intersects triangle at wrong point");

		// TC02: Ray outside the triangle against edge (0) 
		assertNull(triangle.findIntsersections(new Ray(new Point(-1, 0, -1), new Vector(0, 0, 1))), "Wrong number of intersection points");

		// TC03:  Ray outside the triangle against vertex (0)
		assertNull(triangle.findIntsersections(new Ray(new Point(3, 0, -1), new Vector(0, 0, 1))), "Wrong number of intersection points");
		
		
		// =============== Boundary Values Tests ==================

		// TC04: Ray start on a edge of triangle(0)
		assertNull(triangle.findIntsersections(new Ray(new Point(0,0,-1), new Vector(0,0,1))), "Ray start on a edge of triangle");

		// TC05: Ray start on the extension of an edge of triangle(0)
		assertNull(triangle.findIntsersections(new Ray(new Point(2,0,-1), new Vector(0,0,1))), "Ray start on the extension of an edge of triangle");

		// TC06: Ray start on a vertex of triangle
		assertNull(triangle.findIntsersections(new Ray(new Point(0,3,-1), new Vector(0,0,1))), "Ray start on a vertex of triangle");

	}

}
