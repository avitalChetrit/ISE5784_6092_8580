package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class RayTests {
	@Test

	/**
	 * Tests the {@link Ray#getPoint(double)} method. Tests include:
	 * <ul>
	 * <li>Testing for negative distance</li>
	 * <li>Testing for positive distance</li>
	 * <li>Testing for zero distance</li>
	 * </ul>
	 */
	void testGetPoint() {
		// ============ Equivalence Partitions Tests ==============

		// Test 01 for negative distance
		Ray rayNegative = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
		Point pointNegative = rayNegative.getPoint(-3);
		assertEquals(new Point(-1, 2, 2), pointNegative, "Negative distance test failed");

		// Test 02 for positive distance
		Ray rayPositive = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
		Point pointPositive = rayPositive.getPoint(5);
		assertEquals(new Point(7, 2, 2), pointPositive, "Positive distance test failed");
		// =============== Boundary Values Tests ==================

		// Test 03 for zero distance
		Ray rayZero = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
		Point pointZero = rayZero.getPoint(0);
		assertEquals(new Point(2, 2, 2), pointZero, "Zero distance test failed");
	}

}
