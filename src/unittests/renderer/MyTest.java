
package unittests.renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Depth of Field (DoF) rendering in the renderer package.
 */
public class MyTest {

    /**
     * Test for rendering a scene with depth of field (DoF) effect. This test sets
     * up a scene with multiple spheres at varying distances and a plane as the
     * background. It configures the camera with DoF settings including focal length
     * and aperture radius, and verifies the rendering output to check the DoF
     * effect.
     */
    @Test
    public void testDepthOfField() {

        Scene scene = new Scene("DoF");

        final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(new Point(0, 0, 2500))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(200, 200)
            .setVpDistance(850)
            .setFocalSize(20, 1600, 100)
            .setRayTracer(new SimpleRayTracer(scene));

        final Camera.Builder cameraBuilder1 = Camera.getBuilder()
            .setLocation(new Point(0, 0, 2500))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(200, 200)
            .setVpDistance(850)
            .setFocalSize(20, 1600, 1)
            .setRayTracer(new SimpleRayTracer(scene));

        AmbientLight ambientLight = new AmbientLight(new Color(30, 30, 30), 0.1);
        scene.setAmbientLight(ambientLight);

        // Define the plane with a pink color for the background
        Geometry plane = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1))
            .setEmission(new Color(255, 192, 203)); // Pink color
        plane.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKR(0.02));
        // Add spheres and triangles in a diagonal line
        scene.geometries.add(
            plane,

            // First sphere
            new Sphere(new Point(-200, -200, 1200), 50).setEmission(new Color(210, 4, 45)),

            // Triangle behind the first sphere
            new Triangle(new Point(-200, -200, 1200), new Point(-100, -100, 1000), new Point(-300, -100, 1000))
                .setEmission(new Color(255, 0, 0)) // Red Triangle
                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(50)),

            // Second sphere
            new Sphere(new Point(-100, -100, 1000), 50).setEmission(new Color(222, 49, 99)),

            // Triangle behind the second sphere
            new Triangle(new Point(-100, -100, 1000), new Point(0, 0, 800), new Point(-200, 0, 800))
                .setEmission(new Color(0, 255, 0)) // Green Triangle
                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(50)),

            // Third sphere
            new Sphere(new Point(0, 0, 800), 50).setEmission(new Color(169, 92, 104)),

            // Triangle behind the third sphere
            new Triangle(new Point(0, 0, 800), new Point(100, 100, 600), new Point(-100, 100, 600))
                .setEmission(new Color(0, 0, 255)) // Blue Triangle
                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(50)),

            // Fourth sphere
            new Sphere(new Point(100, 100, 600), 50).setEmission(new Color(250, 160, 160)),

            // Triangle behind the fourth sphere
            new Triangle(new Point(100, 100, 600), new Point(200, 200, 400), new Point(0, 200, 400))
                .setEmission(new Color(255, 165, 0)) // Orange Triangle
                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(50)),

            // Fifth sphere
            new Sphere(new Point(200, 200, 400), 50).setEmission(new Color(100, 200, 100)),

            // Triangle behind the fifth sphere
            new Triangle(new Point(200, 200, 400), new Point(300, 300, 200), new Point(100, 300, 200))
                .setEmission(new Color(150, 150, 255)) // Light Blue Triangle
                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(50))
         
        );

        // Add light source
        scene.lights.add(new DirectionalLight(new Color(70, 172, 21), new Vector(-1, 0, 0)));

        // Render images with and without DoF
        cameraBuilder1.setImageWriter(new ImageWriter("NF", 1200, 1200)).build().renderImage().writeToImage();
        cameraBuilder.setImageWriter(new ImageWriter("DF", 1200, 1200)).build().renderImage().writeToImage();
    }
}
