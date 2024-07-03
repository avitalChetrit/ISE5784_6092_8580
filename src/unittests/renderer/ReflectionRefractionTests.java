/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.*;
import org.junit.jupiter.api.Test;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		scene.geometries.add(
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)));
		scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
				.setKL(0.0004).setKQ(0.0000006));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(150, 150)
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)).build().renderImage().writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		scene.geometries.add(
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)).setMaterial(
						new Material().setKD(0.25).setKS(0.25).setShininess(20).setKT(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000))
						.setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKR(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)).setEmission(new Color(20, 20, 20))
						.setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
				.setKL(0.00001).setKQ(0.000005));

		cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000).setVpSize(2500, 2500)
				.setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500)).build().renderImage()
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		scene.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)).setKL(4E-5)
				.setKQ(2E-7));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
				.setImageWriter(new ImageWriter("refractionShadow", 600, 600)).build().renderImage().writeToImage();
	}

	@Test
	public void everything() {

		scene.setAmbientLight(new AmbientLight(new Color(BLUE), new Double3(0.20)));
		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKR(new Double3(0.8))), //
				new Sphere(new Point(-60, 70, 40), 30d).setEmission(new Color(600, 0, 600)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.9)),
				new Sphere(new Point(50, -20, -100), 30d).setEmission(new Color(50, 300, 400)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30)));
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.lights.add(new PointLight(new Color(100, 200, 200), new Point(60, 50, 100)) //
				.setKL(4E-5).setKQ(2E-7));
		scene.lights.add(new PointLight(new Color(YELLOW).reduce(2), new Point(-10, 50, -10)).setKL(0.00003)
				.setKC(1.00001).setKQ(0.000001));
		scene.lights.add(new DirectionalLight(new Color(255, 0, 0), new Vector(-5, -5, -5)));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
				.setImageWriter(new ImageWriter("everything", 600, 600)).build().renderImage().writeToImage();

	}
	/** Produce a picture of several spheres lighted by 2 spot lights */

}
