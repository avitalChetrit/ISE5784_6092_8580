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
	 * A small constant representing the precision for floating-point comparison.
	 * This constant is used in unit tests to specify the maximum allowable difference
	 * between expected and actual values when comparing floating-point numbers.
	 */
	private static final double DELTA = 0.000001;

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: ensure normal is correct
        final Point p1=new Point(0, 0, 0);
        final Point p2=new Point(1, 0, 0);
        final Point p3=new Point(0, 1,0);
        Triangle triangle = new Triangle(p1, p2, p3);
        final Vector exp=triangle.getNormal(p1);
        assertFalse(exp.equals(new Vector(1, 0, 0)) || exp.equals(new Vector(-1, 0, 0)),
                "ERROR: GetNormal() does not work correctly");
        //ensure that the normal is orthogonal to the vector between the points
        assertEquals(0, triangle.getNormal(p2).dotProduct(p1.subtract(p2)), DELTA);
        assertEquals(0, triangle.getNormal(p1).dotProduct(p1.subtract(p3)), DELTA);
        // ensure |result| = 1
        assertEquals(1, triangle.getNormal(new Point(0, 0, 0)).length(), DELTA, "ERROR: Normal is len not 1");
    }
	
	
	
	
	
	
	
	/**
	 * Test method for findIntersections function
	 */
	@Test
	void findIntersections() {
		// ============ Equivalence Partitions Tests ==============

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray intersects the triangle at a point inside the triangle
		Triangle triangle = new Triangle(new Point(2, 0, 0), new Point(0, 2, 0), new Point(0, -2, 0)); // Triangle with
																										// vertices at
																										// (2,0,0),
																										// (0,2,0), and
																										// (0,-2,0)
		Ray rayInside = new Ray(new Point(0, 0, -2), new Vector(1, 0, 2)); // Ray from inside the triangle
		List<Point> resultInside = triangle.findIntersections(rayInside);
		assertEquals(1, resultInside.size(), "Wrong number of intersection points");
		assertEquals(new Point(1, 0, 0), resultInside.getFirst(), "Ray intersects triangle at wrong point");

		// TC02: Ray outside the triangle against edge (0)
		assertNull(triangle.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(0, 0, 1))),
				"Wrong number of intersection points");

		// TC03: Ray outside the triangle against vertex (0)
		assertNull(triangle.findIntersections(new Ray(new Point(3, 0, -1), new Vector(0, 0, 1))),
				"Wrong number of intersection points");

		// =============== Boundary Values Tests ==================

		// TC04: Ray start on a edge of triangle(0)
		assertNull(triangle.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 1))),
				"Ray start on a edge of triangle");

		// TC05: Ray start on the extension of an edge of triangle(0)
		assertNull(triangle.findIntersections(new Ray(new Point(2, 0, -1), new Vector(0, 0, 1))),
				"Ray start on the extension of an edge of triangle");

		// TC06: Ray start on a vertex of triangle
		assertNull(triangle.findIntersections(new Ray(new Point(0, 3, -1), new Vector(0, 0, 1))),
				"Ray start on a vertex of triangle");

	}

}
