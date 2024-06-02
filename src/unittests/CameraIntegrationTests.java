package unittests.renderer; //TODO fixes stage4

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Integration tests for Camera ray construction and geometry intersections.
 */
public class CameraIntegrationTests {
	/** Camera builder for the tests */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setRayTracer(new SimpleRayTracer(new Scene("Test"))).setImageWriter(new ImageWriter("Test", 1, 1))
			.setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0)).setVpDistance(10);

	/**
	 * Integration test method for constructing rays from camera and checking
	 * intersections with geometries.
	 */
	@Test
	void testCameraIntegration() {
		// Define the camera parameters
		Camera camera = cameraBuilder.setVpSize(3, 3).build();

		// Sphere tests
		testRayIntersections(camera, new Sphere(new Point(0, 0, -3), 1), 2, 18);

		// Plane tests
		testRayIntersections(camera, new Plane(new Point(0, 0, -4), new Vector(0, 0, 1)), 9, 9);

		testRayIntersections(camera, new Plane(new Point(0, 0, -4), new Vector(0, 1, 1)), 9, 9);

		testRayIntersections(camera, new Plane(new Point(0, 0, -4), new Vector(0, -1, 1)), 6, 6);

		// Triangle tests
		testRayIntersections(camera, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 1,
				1);

		testRayIntersections(camera, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 2,
				2);
	}

	/**
	 * Helper method to test the number of intersections of rays from the camera
	 * with a given geometry.
	 * 
	 * @param camera                the camera
	 * @param geometry              the geometry to test intersections with
	 * @param expectedIntersections the expected number of intersections
	 * @param actualIntersections   the actual number of intersections to compare
	 */
	private void testRayIntersections(Camera camera, Intersectable geometry, int expectedIntersections,
			int actualIntersections) {
		int count = 0;
		int nX = 3, nY = 3;

		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				Ray ray = camera.constructRay(nX, nY, j, i);
				count += geometry.findIntsersections(ray).size();
			}
		}

		assertEquals(expectedIntersections, count, "Wrong number of intersections");
	}
}
