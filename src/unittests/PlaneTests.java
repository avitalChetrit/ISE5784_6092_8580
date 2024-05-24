/**
 * 
 */
package unittests;
import org.junit.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Plane;
import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for primitives.Point class
* @author Tal and Avital
*/
class PlaneTests {

	@Test
	void testPlaneConstructor() {
	    // ============ Boundary Value Tests ==============
	    // TC01: First and second points are the same
	    Point point1 = new Point(0, 0, 0);
	    Point point2 = new Point(0, 0, 0); // same as point1
	    Point point3 = new Point(1, 0, 0);
	    // Ensure an IllegalArgumentException is thrown when constructing a plane with two identical points
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
	            () -> new Plane(point1, point2, point3));
	    // Check if the exception message is correct
	    assertEquals("Constructing a plane with two identical points should throw an exception", exception.getMessage());

	    // TC02: All three points are collinear
	   Point point4 = new Point(1, 1, 1);
	   Point point5 = new Point(2, 2, 2); // collinear with point1 and point2
	    // Ensure an IllegalArgumentException is thrown when constructing a plane with three collinear points
	    exception = assertThrows(IllegalArgumentException.class, () -> new Plane(point1, point4, point5));
	    // Check if the exception message is correct
	    assertEquals("Constructing a plane with three collinear points should throw an exception", exception.getMessage());
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
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
		//TC01 Ensure the normal vector has unit length
		assertEquals(1, normal.length(), "Normal vector should have unit length");
		//TC10 Ensure the normal vector is perpendicular to the plane
		Vector vector1 = point2.subtract(point1);
		Vector vector2 = point3.subtract(point1);
		double dotProduct = normal.dotProduct(vector1);
		// ============ willingness Partitions Tests ==============

		assertTrue(Math.abs(dotProduct) < 1e-10, "Normal vector is not perpendicular to the plane");
		dotProduct = normal.dotProduct(vector2);
		assertTrue(Math.abs(dotProduct) < 1e-10, "Normal vector is not perpendicular to the plane");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalAtPoint() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test for a general case
		Point point1 = new Point(0, 0, 0);
		Point point2 = new Point(1, 0, 0);
		Point point3 = new Point(0, 1, 0);
		Plane plane = new Plane(point1, point2, point3);
		Point testPoint = new Point(0, 0, 0);
		Vector normal = plane.getNormal(testPoint);
		//TC01 Ensure the normal vector has unit length
		assertEquals(1, normal.length(), "Normal vector should have unit length");
		//TC10 Ensure the normal vector is perpendicular to the plane
		Vector vector1 = point2.subtract(point1);
		Vector vector2 = point3.subtract(point1);
		double dotProduct = normal.dotProduct(vector1);
		// ============ willingness Partitions Tests ==============

		assertTrue(Math.abs(dotProduct) < 1e-10, "Normal vector is not perpendicular to the plane");
		dotProduct = normal.dotProduct(vector2);
		assertTrue(Math.abs(dotProduct) < 1e-10, "Normal vector is not perpendicular to the plane");
	}
}
