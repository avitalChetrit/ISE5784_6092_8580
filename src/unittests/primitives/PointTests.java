/**
 * 
 */
package unittests.primitives;

import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Tal and Avital
 */

class PointTests {
	/**
	 * A small constant representing the precision for floating-point comparison.
	 * This constant is used in unit tests to specify the maximum allowable difference
	 * between expected and actual values when comparing floating-point numbers.
	 */
	private static final double DELTA = 0.000001;

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		// ============Equivalence Partitions Tests ==============
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(2, 4, 6);
		Vector expected = new Vector(1, 2, 3);
		// TC01: Test that checks the property of result of the subtraction operation
		// between two points.
		assertEquals(expected, p2.subtract(p1), "wrong subtract result");
		// TC10: Test that checks the property of result of the subtraction operation
		// between a point and itself.
		assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), //
				"subtraction operation between a point and itself should throw an exception for zero vector");

	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============Equivalence Partitions Tests ==============

		Point p1 = new Point(1, 2, 3);
		Vector v1 = new Vector(2, 3, 4);
		Point expected = new Point(3, 5, 7);
		// TC01: Test that checks the property of result of the add operation.
		assertEquals(expected, p1.add(v1), "wrong add result");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// ============Equivalence Partitions Tests ==============
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(4, 5, 6);
		double expected = 27;
		// TC01: Test that checks the property of result of the calculating the squared
		// distance between two points.
		assertEquals(expected, p1.distanceSquared(p2), DELTA, "wrong distanceSquared result");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// ============Equivalence Partitions Tests ==============

		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(4, 5, 6);
		double expected = Math.sqrt(27);
		double expected2 = 0.0;
		// TC01: Test that checks the property of result of the calculating the squared
		// distance between two points.
		assertEquals(expected, p1.distance(p2), DELTA, "wrong distance result");
	    // =============== Boundary Values Tests ==================
	    // TC02: Test that checks the property of result of the calculating the squared
	    // distance between point to same point.
		assertEquals(expected2, p1.distance(p1), DELTA, "wrong distance result");
	}

}
