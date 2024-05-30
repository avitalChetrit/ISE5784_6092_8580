package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import geometries.*;
import primitives.*;
import java.util.List;

class GeometriesTests {

	@Test
	void testFindIntersections() {
		// =============== Boundary Values Tests ==================

		// BVA: Empty collection
		Geometries emptyGeometries = new Geometries();
		Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 1, 0));
		assertNull(emptyGeometries.findIntersections(ray), "BVA: Empty collection");

		// BVA: No shapes intersect
		Sphere sphere1 = new Sphere(new Point(1, 0, 0), 0.5);
		Plane plane1 = new Plane(new Point(0, 0, 2), new Vector(0, 0, 1));
		Geometries geometriesNoIntersections = new Geometries(sphere1, plane1);
		ray = new Ray(new Point(0, 0, 3), new Vector(1, 0, 0));
		assertNull(geometriesNoIntersections.findIntersections(ray), "BVA: No shapes intersect");

		// BVA: Only one shape intersects
		ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
		List<Point> intersections = geometriesNoIntersections.findIntersections(ray);
		assertNotNull(intersections, "BVA: Only one shape intersects");
		assertEquals(1, intersections.size(), "BVA: Only one shape intersects");

		// BVA: All shapes intersect
		Sphere sphere2 = new Sphere(new Point(2, 0, 0), 0.5);
		Plane plane2 = new Plane(new Point(0, 0, 0.5), new Vector(0, 0, 1));
		Geometries geometriesAllIntersections = new Geometries(sphere1, plane1, sphere2, plane2);
		ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
		intersections = geometriesAllIntersections.findIntersections(ray);
		assertNotNull(intersections, "BVA: All shapes intersect");
		assertEquals(4, intersections.size(), "BVA: All shapes intersect");
		// ============ Equivalence Partitions Tests ==============

		// EP: Some shapes intersect
		Geometries geometriesSomeIntersections = new Geometries(sphere1, plane1, sphere2);
		ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
		intersections = geometriesSomeIntersections.findIntersections(ray);
		assertNotNull(intersections, "EP: Some shapes intersect");
		assertEquals(3, intersections.size(), "EP: Some shapes intersect");

	}
}
