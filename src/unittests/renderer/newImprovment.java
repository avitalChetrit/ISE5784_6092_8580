package unittests.renderer;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;
import static java.awt.Color.*;

@Suite
@SelectClasses({ CameraIntegrationTests.class, CameraTests.class, ImageWriterTest.class, improvmentTest.class,
		ReflectionRefractionTests.class, RenderTests.class, ShadowTests.class })

public class newImprovment {
	private final Scene scene = new Scene("mp1").setBackground(new Color(245, 245, 220));

    private final Camera.Builder cameraBuilder = Camera.getBuilder().setDirection(new Vector(-8, -8, -3), new Vector(-100, -100, 533.3333333333333))
            .setRayTracer(new SimpleRayTracer(scene)).setMultithreading(3).setSuperSampling(null);

    @Test
    public void mpTest() {
        Color strongGreen= new Color(0, 100, 0);
        Color emmisionGreen= new Color(0, 240, 10);
        Color emmisionStrongGreen= new Color(5, 92, 5);
        Color emmisionBlue= new Color(0, 10, 240);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.1)));
        scene.lights.add(
                new PointLight(new Color(255, 240, 245), new Point(-79, -20, 40))
                        .setKL(0.04).setKQ(2E-5));
        //.setLengthOfTheSide(2).setSoftShadowsRays(20)
        
        scene.lights.add(new DirectionalLight(new Color(WHITE), new Vector(-80, -80, 30)));
        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400), new Point(0, 50, -10), new Vector(0, -30, 25))
                        .setKL(0.1).setKQ(0.0001));
        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400), new Point(50, 0, -10), new Vector(-30, 0, 25))
                        .setKL(0.1).setKQ(0.0001));
        Material material = new Material().setKD(0.3).setKS(0.5).setShininess(50).setKR(0.3);

        scene.geometries.add(
                /**-------משטח2-------**/
                new Sphere(new Point(20, 0, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-20, 0, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(20, 10, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-20, 10, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(20, -10, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-20, -10, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(0, 20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(10, 20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-10, 20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(20, 20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-20, 20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(0, -20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(10, -20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-10, -20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(20, -20, 0), 5)
                        .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),
                new Sphere(new Point(-20, -20, 0), 5)
                       .setEmission(new Color(GREEN)).setMaterial(material).setEmission(emmisionGreen),

                /**-------משט1-------**/
                new Sphere(new Point(0, 0, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(10, 0, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-10, 0, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(20, 0, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-20, 0, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(0, 10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(10, 10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-10, 10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(20, 10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-20, 10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(0, -10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(10, -10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-10, -10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(20, -10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-20, -10, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(0, 20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(10, 20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-10, 20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(20, 20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-20, 20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(0, -20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(10, -20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-10, -20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(20, -20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                new Sphere(new Point(-20, -20, 10), 5)
                        .setEmission(strongGreen).setMaterial(material).setEmission(emmisionStrongGreen),
                /**-------משטח3-------**/
                new Sphere(new Point(20, 0, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-20, 0, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(20, 10, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-20, 10, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(20, -10, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-20, -10, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(0, 20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(10, 20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-10, 20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(20, 20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-20, 20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(0, -20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(10, -20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-10, -20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(20, -20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),
                new Sphere(new Point(-20, -20, -10), 5)
                        .setEmission(new Color(BLUE)).setMaterial(material).setEmission(emmisionBlue),

                /**-------משטח5-------**/
                new Sphere(new Point(20, 0, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-20, 0, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(20, 10, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-20, 10, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(20, -10, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-20, -10, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(0, 20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(10, 20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-10, 20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(20, 20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-20, 20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(0, -20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(10, -20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-10, -20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(20, -20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),
                new Sphere(new Point(-20, -20, -20), 5)
                        .setEmission(new Color(127, 0, 255)).setMaterial(material).setEmission(new Color(115, 0, 240)),

                /**-------משטח7------**/
                new Sphere(new Point(20, 0, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-20, 0, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(20, 10, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-20, 10, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(20, -10, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-20, -10, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(0, 20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(10, 20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-10, 20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(20, 20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-20, 20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(0, -20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(10, -20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-10, -20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(20, -20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),
                new Sphere(new Point(-20, -20, -30), 5)
                        .setEmission(new Color(255, 127, 0)).setMaterial(material).setEmission(new Color(240, 115, 0)),

                new Plane(
                        new Point(-8, -13, -35),
                        new Point(-8, 13, -35),
                        new Point(1, 1, -35)
                )
                        .setEmission(new Color(160, 82, 45))
                        .setMaterial(new Material().setKD(0.1).setKS(0.8).setShininess(60)),
                new Plane(
                        new Point(-8, -13, 50),
                        new Point(-8, 13, 50),
                        new Point(1, 1, 50)
                )
                        .setEmission(new Color(160, 82, 45))
                        .setMaterial(new Material().setKD(0.1).setKS(0.8).setShininess(100)),

                new Plane(
                        new Point(-8, -80, -4),
                        new Point(-4, -80, -3),
                        new Point(1, -80, -5)
                )
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKD(0.4).setShininess(6).setKR(0.8))
        );

        cameraBuilder
                .setLocation(new Point(100, 100, 30))
                .setVpDistance(80)
                .setVpSize(80, 80)
                .setImageWriter(new ImageWriter("mp1", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .moveCamera(new Point(9, 11, 40), new Point(0, -4, -4))
               // .setSuperSampling(Camera.SUPER_SAMPLING_TYPE.REGULAR)
                //.setApertureSize(0.001)
                .setFocalDistance(100)
                .setMultithreading(4)
                .build()
                .renderImage()
                .writeToImage();
    }

}
//.setSuperSampling(Camera.SUPER_SAMPLING_TYPE.REGULAR)
//.setApertureSize(0.01)*/
      



