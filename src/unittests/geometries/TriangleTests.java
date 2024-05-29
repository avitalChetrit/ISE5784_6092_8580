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
        Triangle triangle = new Triangle(new Point(1, 1, 1), new Point(2, 1, 1), new Point(1, 2, 1)); // Triangle with vertices at (1,1,1), (2,1,1), and (1,2,1)
        Ray rayInside = new Ray(new Point(1.5, 1.5, 0), new Vector(0, 0, 1)); // Ray from inside the triangle
        List<Point> resultInside = triangle.findIntsersections(rayInside);
        assertEquals(1, resultInside.size(), "Wrong number of intersection points");
        assertTrue(resultInside.contains(new Point(1.5, 1.5, 1)), "Ray intersects triangle at wrong point");
        
        // TC02: Ray intersects the triangle at a point on the edge of the triangle
        Ray rayEdge = new Ray(new Point(1, 1, 0), new Vector(0, 0, 1)); // Ray along the edge of the triangle
        List<Point> resultEdge = triangle.findIntsersections(rayEdge);
        assertEquals(1, resultEdge.size(), "Wrong number of intersection points");
        assertTrue(resultEdge.contains(new Point(1, 1, 1)), "Ray intersects triangle edge at wrong point");
        
        // TC03: Ray intersects the triangle at a point on the vertex of the triangle
        Ray rayVertex = new Ray(new Point(1, 2, 0), new Vector(0, 0, 1)); // Ray through one of the triangle's vertices
        List<Point> resultVertex = triangle.findIntsersections(rayVertex);
        assertEquals(1, resultVertex.size(), "Wrong number of intersection points");
        assertTrue(resultVertex.contains(new Point(1, 2, 1)), "Ray intersects triangle vertex at wrong point");
        
        // =============== Boundary Values Tests ==================
        
        // TC04: Ray intersects the triangle at a point outside the triangle
        Ray rayOutside = new Ray(new Point(1, 3, 0), new Vector(0, 0, 1)); // Ray outside the triangle
        List<Point> resultOutside = triangle.findIntsersections(rayOutside);
        assertEquals(0, resultOutside.size(), "Intersection with triangle when ray is outside");
        
        // TC05: Ray is parallel to the triangle's plane
        Ray rayParallel = new Ray(new Point(1, 1, 0), new Vector(1, 1, 0)); // Ray parallel to triangle's plane
        List<Point> resultParallel = triangle.findIntsersections(rayParallel);
        assertEquals(0, resultParallel.size(), "Intersection with triangle when ray is parallel");
        
        // TC06: Ray is orthogonal to the triangle's plane
        Ray rayOrthogonal = new Ray(new Point(1, 1, 0), new Vector(0, 0, 1)); // Ray orthogonal to triangle's plane
        List<Point> resultOrthogonal = triangle.findIntsersections(rayOrthogonal);
        assertEquals(1, resultOrthogonal.size(), "No intersection with triangle when ray is orthogonal");
        assertTrue(resultOrthogonal.contains(new Point(1, 1, 1)), "Ray orthogonal to triangle's plane intersects at wrong point");
    }
	
}
