package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import geometries.*;
import primitives.*;
import java.util.List;

/**
 * A class to test the {@link Geometries} class.
 */
class GeometriesTests {

	/**
	 * Test case for {@link Geometries#findIntersections(Ray)}.
	 * <p>
	 * Tests the intersection of a ray with a collection of geometries.
	 */
	@Test
	void testfindIntsersections() {
		Sphere sphere2 = new Sphere(new Point(4, 0, 0), 4);
		Plane plane2 = new Plane(new Point(2, 2, 1), new Vector(0, 0, 2));
		Triangle triangle = new Triangle(new Point(0, 2, 0), new Point(0, -2, 0), new Point(2, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// EP: Some shapes intersect
		Geometries geometriesSomeIntersections = new Geometries(sphere2, plane2, triangle);
		Ray ray = new Ray(new Point(2, 2, -4), new Vector(0, 0, 2));
		List<Point> intersections = geometriesSomeIntersections.findIntersections(ray);
		// assertNotNull(intersections, "EP: Some shapes intersect");
		assertEquals(3, intersections.size(), "EP: Some shapes intersect");

		// =============== Boundary Values Tests ==================

		// BVA: Empty collection
		Geometries emptyGeometries = new Geometries();
		Ray ray0 = new Ray(new Point(5, -11, -4), new Vector(13, 21, -6));
		assertNull(emptyGeometries.findIntersections(ray0), "BVA: Empty collection");

		// BVA: No shapes intersect
		// Sphere sphere1 = new Sphere(new Point(1, 0, 0), 0.5);
		// Plane plane1 = new Plane(new Point(0, 0, 2), new Vector(0, 0, 1));
		Geometries geometriesNoIntersections = new Geometries(sphere2, plane2, triangle);
		Ray ray1 = new Ray(new Point(5, -11, -4), new Vector(13, 21, -6));
		assertNull(geometriesNoIntersections.findIntersections(ray1), "BVA: No shapes intersect");

		// BVA: Only one shape intersects
		Ray ray2 = new Ray(new Point(10, 0, -4), new Vector(0, 0, 2));
		List<Point> intersections1 = geometriesNoIntersections.findIntersections(ray2);
		// assertNotNull(intersections, "BVA: Only one shape intersects");
		assertEquals(1, intersections1.size(), "BVA: Only one shape intersects");

		// BVA: All shapes intersect

		Geometries geometriesAllIntersections = new Geometries(sphere2, plane2, triangle);
		Ray ray3 = new Ray(new Point(1, 1, -4), new Vector(0, 0, 2));
		List<Point> intersections2 = geometriesAllIntersections.findIntersections(ray3);
		assertNotNull(intersections2, "BVA: All shapes intersect");
		assertEquals(3, intersections2.size(), "BVA: All shapes intersect");

	}
}
