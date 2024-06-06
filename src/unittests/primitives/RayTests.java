package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A test class for the {@link Ray} class.
 */
class RayTests {

	/**
	 * Tests the {@link Ray#getPoint(double)} method.
	 * <p>
	 * This method tests the {@link Ray#getPoint(double)} method of the {@link Ray}
	 * class. It includes equivalence partitions tests and boundary values tests
	 * for:
	 * <ul>
	 * <li>Negative distance</li>
	 * <li>Positive distance</li>
	 * <li>Zero distance</li>
	 * </ul>
	 */
	@Test
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
	
	 /**
     * Tests the {@link Ray#findClosestPoint(List)} method.
     * <p>
     * This method tests the {@link Ray#findClosestPoint(List)} method of the {@link Ray}
     * class. It includes tests for:
     * <ul>
     * <li>Equivalence partition: A point in the middle of the list is closest to the start of the ray</li>
     * <li>Boundary Values Tests:
     *     <ul>
     *         <li>An empty list (expected result: null)</li>
     *         <li>The first point is the closest to the start of the ray</li>
     *         <li>The last point is the closest to the start of the ray</li>
     *     </ul>
     * </ul>
     */
    @Test
    void testFindClosestPoint() {
        // ============ Equivalence Partition Test ==============

        // Test 01 for a point in the middle of the list being closest to the start of the ray
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        List<Point> points = new ArrayList<>();
        points.add(new Point(3, 3, 3)); // Far from the ray
        points.add(new Point(2, 2, 2)); // Closer to the ray
        points.add(new Point(1, 1, 1)); // Closest to the ray
        points.add(new Point(4, 4, 4)); // Further from the ray
        Point closestPoint = ray.findClosestPoint(points);
        assertEquals(new Point(1, 1, 1), closestPoint, "Equivalence partition test failed");

        // =============== Boundary Values Tests ==================

        // Test 02 for an empty list (expected result: null)
        List<Point> emptyList = new ArrayList<>();
        Point closestPointEmptyList = ray.findClosestPoint(emptyList);
        assertNull(closestPointEmptyList, "Empty list test failed");

        // Test 03 for the first point being closest to the start of the ray
        List<Point> pointsFirstClosest = new ArrayList<>();
        pointsFirstClosest.add(new Point(1, 1, 1)); // Closest to the ray
        pointsFirstClosest.add(new Point(3, 3, 3)); // Far from the ray
        pointsFirstClosest.add(new Point(2, 2, 2)); // Closer to the ray
        Point closestPointFirst = ray.findClosestPoint(pointsFirstClosest);
        assertEquals(new Point(1, 1, 1), closestPointFirst, "First point closest test failed");

        // Test 0 for the last point being closest to the start of the ray
        List<Point> pointsLastClosest = new ArrayList<>();
        pointsLastClosest.add(new Point(3, 3, 3)); // Far from the ray
        pointsLastClosest.add(new Point(2, 2, 2)); // Closer to the ray
        pointsLastClosest.add(new Point(1, 1, 1)); // Closest to the ray
        Point closestPointLast = ray.findClosestPoint(pointsLastClosest);
        assertEquals(new Point(1, 1, 1), closestPointLast, "Last point closest test failed");
    }

}
