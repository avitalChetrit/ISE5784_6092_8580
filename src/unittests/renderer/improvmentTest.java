package unittests.renderer;
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

/**
 * testing our picture for stage7, and improvements of stage8
 *
 * @author Tal and Avital
 */
public class improvmentTest {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

    //region stage7 - our pictures

    //region points
    Point A = new Point(70, -70, -150);
    Point B = new Point(-70, -70, -150);
    Point C = new Point(-70, 90, -150);
    Point D = new Point(70, 90, -150);
    Point E = new Point(100, -100, 0);
    Point F = new Point(-100, -100, 0);
    Point G = new Point(-100, 120, 0);
    Point H = new Point(100, 120, 0);
    //endregion

    //region flower petal points:
    //region lower-middle petals:

    //region origin points:
    Point J = new Point(-119.11,-68.14,-74.88);
    Point E1 = new Point(-101.9,-68.14,-40);
    Point K = new Point(-115.6,-68.14,-7.54);
    Point G1 = new Point(-141.46,-68.14,6.68);
    Point F1 = new Point(-170.99,-68.14,1.74);
    Point H1 = new Point(-196,-68.14,-31.45);
    Point I = new Point(-184.19,-68.14,-71.43);
    Point D1 = new Point(-152.42,-68.14,-87.38);
    //endregion

    //region petal edge:
    Point B1 = new Point(-90,-40,-80);
    Point B2 = new Point(-80,-40,-11);
    Point B3 = new Point(-115,-40,39);
    Point B4 = new Point(-160,-40,35);
    Point B5 = new Point(-211,-40,6);
    Point B6 = new Point(-212,-40,-53);
    Point B7 = new Point(-188,-40,-122);
    Point B8 = new Point(-122,-40,-125);
    //endregion
    //endregion

    //region middle petals:

    //region origin points:
    Point J2 = new Point(-120,-65,-87.44);
    Point E2 = new Point(-104.71,-65,-60.16);
    Point K2 = new Point(-104.82,-65,-19.44);
    Point G2 = new Point(-126.13,-65,3.64);
    Point F2 = new Point(-159.65,-65,8.9);
    Point H2 = new Point(-188.22,-65,-7.9);
    Point I2 = new Point(-198.3,-65,-52.55);
    Point D2 = new Point(-171.73,-65,-84.82);
    //endregion

    //region petal edge:
    Point B11 = new Point(-99.2,-10,-91.24);
    Point B12 = new Point(-70.02,-10,-42.72);
    Point B13 = new Point(-90.2,-10,15.14);
    Point B14 = new Point(-142.87,-10,43.47);
    Point B15 = new Point(-197.99,-10,28.2);
    Point B16 = new Point(-226.82,-10,-20.51);
    Point B17 = new Point(-212.86,-10,-88.64);
    Point B18 = new Point(-153.63,-10,-125.83);
    //endregion
    //endregion
    //region top petals:

    //region origin points:
    Point J3 = new Point(-119.11,-60,-60.88);
    Point E3 = new Point(-101.9,-60,-20);
    Point K3 = new Point(-115.6,-60,-7.54);
    Point G3 = new Point(-141.46,-60,6.68);
    Point F3 = new Point(-170.99,-60,1.74);
    Point H3 = new Point(-196,-60,-31.45);
    Point I3 = new Point(-184.19,-60,-51.43);
    Point D3 = new Point(-152.42,-60,-67.38);
    //endregion

    //region petal edge:
    Point B31 = new Point(-90,10,-80);
    Point B32 = new Point(-80,10,-11);
    Point B33 = new Point(-115,10,39);
    Point B34 = new Point(-160,10,35);
    Point B35 = new Point(-211,10,6);
    Point B36 = new Point(-212,10,-53);
    Point B37 = new Point(-188,10,-122);
    Point B38 = new Point(-122,10,-125);
    //endregion
    //endregion
    /**
     * test for creating a scene from scratch,
     * at least 3-4 object, with transparency, shadows and reflections
     */
   @Test
    public void myPicture() {
        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.geometries.add(
                new Polygon(A, B, F, E)
                        //.setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20))//floor
                , new Polygon(A, B, C, D)
                        //.setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20))//back
                , new Polygon(C, D, H, G)
                        //.setEmission(new Color(RED))
                        .setMaterial(new Material()
                                .setKD(0.5).setKS(0.5).setShininess(20))
                ,new Cylinder(60d,new Ray(new Point(0,-80,-40),new Vector(0,1,0)),90d)
                     //   .setEmission(new Color(20,20,190))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)
                                .setKR(0.4).setKT(0.99))
                ,new Cylinder(55d,new Ray(new Point(0,-78,-40),new Vector(0,1,0)),60d)
                        .setEmission(new Color(orange))
                        .setMaterial(new Material()
                                .setKR(0.3).setKT(0.99))
                ,new Sphere(new Point(0,-40,-40), 40d)
                        .setEmission(new Color(20, 0, 80))
                        .setMaterial(
                                new Material().setKD(0.25).setKS(0.25).setShininess(61)
                                        .setKT(0.99).setKR(0.4)
                        )

//                      ,new Sphere(10d,new Point(20,20,-20))
//                      .setEmission(new Color(GREEN))
//                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setKr(0.2).setKt(0.999))

//              ,new Polygon(new Point(-150, -150, -135), new Point(150, -150, -135),
//                      new Point(75, 75, -115), new Point(-75, 75, -115))
//                      .setEmission(new Color(RED))
//                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
//             ,new Sphere(40, new Point(30,30,-50))
//                      .setEmission(new Color(120, 20, 200))
//                      .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4)).setKt(0.62)) //reflective
//              ,new Sphere(30,new Point(90,-50,-80))
//                      .setEmission(new Color(RED))
//                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
//                              .setKt(new Double3(0.5, 0, 0))) //transparent

                ,new Tube(10,new Ray(new Point(-80,-80,-80),new Vector(0,1,0)))
                        .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKR(0.0002).setKT(0.45))
        );

        scene.lights.add(
                new PointLight(new Color(ORANGE), new Point(30, 70, -100))
                        .setKL(0.001).setKQ(0.0000002)
        );
//      scene.lights.add(new SpotLight(new Color(ORANGE), new Point(30, 70, -100), new Vector(0,1,0))
//      .setKl(0.001).setKq(0.0000002));
//scene.lights.add(new SpotLight(new Color(700, 400, 400),
//      new Point(30, 50, 0), new Vector(0, 0, -1)).setKl(4E-5).setKq(2E-7));
//scene.lights.add(new SpotLight(new Color(orange),
//      new Point(100, -10, 115), new Vector(-1, -1, -4)).setNarrowBeam(10).setKl(4E-4).setKq(2E-5));

        scene.lights.add(new SpotLight(new Color(orange),
                new Point(0,-78,-40),new Vector(0,1,0)).setNarrowBeam(10).setKL(4E-4).setKQ(2E-5));


        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200).setRayTracer(new SimpleRayTracer(scene))
		.setImageWriter(new ImageWriter("myPicture-7.3", 700, 700)).build().renderImage().writeToImage();
            } 

   
    @Test
    public void miniBar() {
        Scene scene = new Scene("mini bar test");
        scene.setAmbientLight(new AmbientLight(new Color(255,255,255), new Double3(0.2,0,0.4)));
        scene.geometries.add(

                new Polygon(new Point(80,-80,-150), new Point(-80,-80,-150), new Point(-110,-110,0), new Point(110,-110,0))
                        //.setEmission(new Color(RED))
                        .setMaterial(new Material()
                                .setKD(0.5).setKS(0.5).setShininess(20))
                , new Polygon(A, B, C, D)
                        //.setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20))//back
                , new Sphere(new Point(2,2,0), 300)
                //   .setEmission(new Color(138,0,210))
                //   .setMaterial(new Material().setKD(0.001).setKS(0.00001).setKR(1))
        );
        scene.lights.add(new PointLight(new Color(ORANGE), new Point(30, 70, -100))
                .setKL(0.001).setKQ(0.0000002));
        
        cameraBuilder.setLocation(new Point(-3.6, -14.39,0)).setVpDistance(1000).setVpSize(200, 200).setRayTracer(new SimpleRayTracer(scene))
		.setImageWriter(new ImageWriter("MiniBar", 700, 700)).build().renderImage().writeToImage();
    
    }
    //                //region sides:
//                //right side
//                , new Polygon(new Point(120,-80,10),new Point(120,60,10), new Point(120,60,-90), new Point(120,-80,-90))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //left side
//                , new Polygon(new Point(80,-80,10),new Point(80,60,10), new Point(80,60,-90), new Point(80,-80,-90))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //front
//                , new Polygon(new Point(120,-80,10),new Point(120,60,10), new Point(80,60,-90), new Point(80,-80,-90))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //front
//                , new Polygon(new Point(120,-80,10), new Point(80,-80,10),new Point(80,60,10),new Point(120,60,10))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //bottom
//                , new Polygon(new Point(120,-80,10), new Point(120,-80,-90),new Point(80,-80,-90),new Point(80,-80,10))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //endregion
//                //region top:
//                //right triangle
//                , new Polygon(new Point(120,60,10), new Point(120,60,-90), new Point(100,100,-50))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //left triangle
//                , new Polygon(new Point(80,60,10), new Point(80,60,-90), new Point(100,100,-50))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //front triangle
//                , new Polygon(new Point(120,60,10), new Point(80,60,-90), new Point(100,100,-50))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //back triangle
//                , new Polygon(new Point(100,100,-50),new Point(80,60,10),new Point(120,60,10))
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setKT(0.3).setKR(0.4))
//                //bottle nose
//                ,new Cylinder(5d,new Ray(new Point(100,90,-45),new Vector(0,1,0)),30d)
//                        .setEmission(new Color(0, 106, 78))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5)
//                                .setKR(0.4).setKT(0.3))
//
//                //endregion

    @Test
    public void wisCup()
    {
        Scene scene = new Scene("whiskey cup");
        scene.geometries.add(
//region geometries
                //region surfaces
                new Sphere(new Point(100,100,0), 500)
                        //.setEmission(new Color(20, 0, 80))
                        .setMaterial(
                                new Material().setKD(0.25).setKS(0.25).setShininess(61)
                                        .setKR(0.5).setKT(0.9)
                        )
                ,new Polygon(new Point(1000, -90, -1500), new Point(-1000, -90, -1500), new Point(-1000, -90, 1000), new Point(1000, -90, 1000))
                        //.setEmission(new Color(YELLOW))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20))//floor
//                , new Polygon(A, B, C, D)
//                       // .setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20))//back
                //endregion
                //region cup
                ,new Cylinder(60d,new Ray(new Point(0,-80,-40),new Vector(0,1,0)),90d)
                        //.setEmission(new Color(230,111,20))
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)
                                .setKR(0.009).setKT(0.63))
                ,new Cylinder(55d,new Ray(new Point(0,-78,-40),new Vector(0,1,0)),60d)
                        .setEmission(new Color(230,111,20))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)
                                .setKR(0.4).setKT(0.8))
                ,new Sphere(new Point(0,-40,-40), 40d)
                        .setEmission(new Color(165, 242, 243))
                        .setMaterial(
                                new Material().setKD(0.3).setKS(0.7).setShininess(0)
                                        .setKT(1).setKR(0.001)
                        )
                //endregion
                //region bottle
                //inside bottle:
                ,new Cylinder(25d,new Ray(new Point(100,-75,-40),new Vector(0,1,0)),100d)
                        .setEmission(new Color(169,64,7))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5)
                                .setKR(0.2).setKT(0.5))
                //bottle
                ,new Cylinder(30d,new Ray(new Point(100,-80,-40),new Vector(0,1,0)),170d)
                        .setEmission(new Color(0, 106, 78))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5)
                                .setKR(0.0009).setKT(0.63))
                //bottleneck
                ,new Cylinder(10d,new Ray(new Point(100,90,-40),new Vector(0,1,0)),27d)
                        .setEmission(new Color(0, 106, 78))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5)
                                .setKR(0.0009).setKT(0.63))
                //cap
                ,new Cylinder(13d,new Ray(new Point(100,110,-40),new Vector(0,1,0)),8d)
                        .setEmission(new Color(110, 106, 78))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5)
                                .setKR(0.009).setKT(0))
                //endregion

                //region clouds
                //region bottom left:
//                ,new Sphere(40, new Point(-130,-70,-90))
//                        .setEmission(new Color(255,147,41))
//                        .setMaterial(new Material().setKD(0.99).setKS(0.79)
//                                .setKR(0.04).setKT(0.00001))
//                ,new Sphere(30, new Point(-130,-70,-50))
//                        .setEmission(new Color(255,147,41))
//                        .setMaterial(new Material().setKD(0.99).setKS(0.79)
//                                .setKR(0.04).setKT(0.00001))
//                ,new Sphere(25, new Point(-100,-70,-90))
//                        .setEmission(new Color(255,147,41))
//                        .setMaterial(new Material().setKD(0.99).setKS(0.79)
//                                .setKR(0.04).setKT(0.00001))
                //endregion
//                //region
//                ,new Sphere(40, new Point(130,190,90))
//                        .setEmission(new Color(208,222,236))
//                        .setMaterial(new Material().setKD(0.9).setKS(0.7)
//                                .setKR(0.9).setKT(0.00001))
//                ,new Sphere(30, new Point(130,150,50))
//                        .setEmission(new Color(208,222,236))
//                        .setMaterial(new Material().setKD(0.9).setKS(0.7)
//                                .setKR(0.9).setKT(0.00001))
//                ,new Sphere(25, new Point(120,70,90))
//                        .setEmission(new Color(208,222,236))
//                        .setMaterial(new Material().setKD(0.9).setKS(0.7)
//                                .setKR(0.9).setKT(0.00001))
                //endregion
                //region rose/flower
                , new Sphere(new Point(-150,-40,-50),50)
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(300)
                                .setKR(0).setKT(1))
//                , new Sphere(5,new Point(-150,-40,-50))
//                        .setEmission(new Color(black))
//                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(300)
//                                .setKR(0).setKT(1))
                , new Sphere(new Point(-150,-40,-50),20)
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(300)
                                .setKR(0).setKT(1))
                //region lower-middle petals
//                , new Triangle(new Point(-119.11,-74.88,-68.14), new Point(-101.9,-40,-68.14), new Point(-81.63,-71.01,-63.6))//1
//                        .setEmission(new Color(yellow))
//                , new Triangle(new Point(-120,-50,-10), new Point(-120,-45,-30), new Point(-81.63,-71.01,-63.6))//2
//                        .setEmission(new Color(yellow))
//                , new Triangle(new Point(-140,-50,-10), new Point(-140,-40,-30), new Point(-81.63,-71.01,-63.6))//3
//                        .setEmission(new Color(yellow))
                , new Triangle(J,E1,B1)//1
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(E1,K,B2)//2
                        .setEmission(new Color(178,58,58))
                        //.setEmission(new Color(yellow))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(K,G1,B3)//3
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(G1,F1,B4)//4
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(F1,H1,B5)//5
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(H1,I,B6)//6
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(I,D1,B7)//7
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(D1,J,B8)//8
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                //endregion
                //region middle layer:
                , new Triangle(J2,E2,B11)//1
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(E2,K2,B12)//2
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(K2,G2,B13)//3
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(G2,F2,B14)//4
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(F2,H2,B15)//5
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(H2,I2,B16)//6
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(I2,D2,B17)//7
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(D2,J2,B18)//8
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                //endregion
                //region top layer:
//                , new Triangle(new Point(-119.11,-74.88,-68.14), new Point(-101.9,-40,-68.14), new Point(-81.63,-71.01,-63.6))//1
//                        .setEmission(new Color(yellow))
//                , new Triangle(new Point(-120,-50,-10), new Point(-120,-45,-30), new Point(-81.63,-71.01,-63.6))//2
//                        .setEmission(new Color(yellow))
//                , new Triangle(new Point(-140,-50,-10), new Point(-140,-40,-30), new Point(-81.63,-71.01,-63.6))//3
//                        .setEmission(new Color(yellow))
                , new Triangle(J3,E3,B31)//1
                        .setEmission(new Color(178,58,58))
                        //.setEmission(new Color(yellow))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(E3,K3,B32)//2
                        .setEmission(new Color(178,58,58))
                        //.setEmission(new Color(yellow))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(K3,G3,B33)//3
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(G3,F3,B34)//4
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(F3,H3,B35)//5
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(H3,I3,B36)//6
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(I3,D3,B37)//7
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                , new Triangle(D3,J3,B38)//8
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKD(1).setKS(0).setShininess(30)
                                .setKR(0).setKT(0))
                //endregion
                //endregion
//endregion
        );
        //region lighting
        scene.lights.add(new PointLight(new Color(blue), new Point(30, 70, 0))
                .setKL(0.001).setKQ(0.0000002));
        //light inside sphere
//        scene.lights.add(new SpotLight(new Color(ORANGE), new Point(0,-80,-40), new Vector(0,1,0))
//                        .setNarrowBeam(0.0001)
//                .setKL(0.001).setKq(0.0000002));
        scene.lights.add(new SpotLight(new Color(orange), new Point(100,-75,-40), new Vector(0,1,0))
                .setNarrowBeam(0.00000000000000000000001)
                .setKL(0.001).setKQ(0.0000002));
        scene.lights.add(new DirectionalLight(new Color(64,156,255), new Vector(0,-1,0)));
        //endregion
//        scene.lights.add(new DirectionalLight(new Color(ORANGE), new Vector(0,-40,-40)));
     
        
        cameraBuilder.setLocation(new Point(-3.6, -14.39,0)).moveCamera(new Point(80, 10, 300), new Point(0, -40, -40)).setVpDistance(700).setVpSize(700, 700).setRayTracer(new SimpleRayTracer(scene))
		.setImageWriter(new ImageWriter("whiskeyCup", 1000, 1000))
        .setSuperSampling(Camera.SUPER_SAMPLING_TYPE.ADAPTIVE)
        .setApertureSize(0.01).setFocalDistance(900)
        .setMultithreading(4).build().renderImage().writeToImage();
              
        //region camera movements
//        //move right
//        for(int i = 0; i < 5; i=i+1) {
//            String j = String.valueOf(i);
//            camera.moveCamera(new Point(80+i*80, 10, 300), new Point(0, -40, -40))
//                    .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .setImageWriter(new ImageWriter("whiskeyCup"+j, 1000, 1000))
//                    .renderImage();
//            camera.writeToImage();
//        }
//        //move left
//        for(int i = 0; i < 5; i=i+1) {
//            String j = String.valueOf(i+5);
//            camera.moveCamera(new Point(80-i*80, 10, 300), new Point(0, -40, -40))
//                    .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .setImageWriter(new ImageWriter("whiskeyCup"+j, 1000, 1000))
//                    .renderImage();
//            camera.writeToImage();
//        }
//        //move up
//        for(int i = 0; i < 5; i=i+1) {
//            String j = String.valueOf(i+10);
//            camera.moveCamera(new Point(80, 10+i*80, 300), new Point(0, -40, -40))
//                    .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .setImageWriter(new ImageWriter("whiskeyCup"+j, 1000, 1000))
//                    .renderImage();
//            camera.writeToImage();
//        }
//        //move down
//        for(int i = 0; i < 5; i=i+1) {
//            String j = String.valueOf(i+15);
//            camera.moveCamera(new Point(80, 10-i*80, 300), new Point(0, -40, -40))
//                    .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .setImageWriter(new ImageWriter("whiskeyCup"+j, 1000, 1000))
//                    .renderImage();
//            camera.writeToImage();
//        }
//        //rotate right
//        for(int i = 0; i < 5; i=i+1) {
//            String j = String.valueOf(i+20);
//            camera.rotateCamera(9)
//                    .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .setImageWriter(new ImageWriter("whiskeyCup" + j, 1000, 1000))
//                    .renderImage();
//            camera.writeToImage();
//        }
        //endregion

    }
//endregion

    //region stage8 - improvements
//    private Intersectable sphere = new Sphere(60d, new Point(0, 0, -200))
//            .setEmission(new Color(BLUE))
//            .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30));
//    private Material trMaterial = new Material().setKD(0.5).setKS(0.5).setShininess(30);

//    private Scene scene = new Scene("Test scene");
//    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
//            .setViewPlaneSize(200, 200).setViewPlaneDistance(1000)
//            .setRayTracer(new RayTracerBasic(scene));

//    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
//        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
//        scene.lights.add(
//                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3))
//                        .setKL(1E-5).setKq(1.5E-7));
//        camera.setImageWriter(new ImageWriter(pictName, 400, 400))
//                .renderImage()
//                .writeToImage();
//    } 

    @Test
    public void sphereTriangleMove2_AntiAliasing() {
        Scene scene = new Scene("Test scene");

        scene.geometries.add(
                new Sphere(new Point(0, 0, -200), 60d)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)),
                        new Triangle(new Point(-62, -32, 0), new Point(-32, -62, 0), new Point(-60, -60, -4))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)));

        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3))
                        .setKL(1E-5).setKQ(1.5E-7));
        
        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200).setRayTracer(new SimpleRayTracer(scene))
		.setImageWriter(new ImageWriter("antiAliasing_shadowSphereTriangleMove2", 400, 400)).build().renderImage().writeToImage();
        
          }

    //endregion

}