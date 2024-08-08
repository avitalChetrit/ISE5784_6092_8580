package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class FinallmageTest {

    /**
     * Times:
     * stage 7: about 2 minutes
     * stage 8.1: 5 minutes with no effects
     *
     * stage 8.2: 6 minutes with adaptive super-sampling and multithreading
     */
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
    //endregion

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
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20))//floor
//                , new Polygon(A, B, C, D)
//                       // .setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(20))//back
                //endregion
                //region cup
                ,new Cylinder(60d,new Ray(new Point(0,-80,-40),new Vector(0,1,0)),90d)
                        //.setEmission(new Color(230,111,20))
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setKs(0).setKd(0.5).setKt(0.5).setKdG(0.5).setShininess(60)
                                .setKt(0.5))
                ,new Cylinder(55d,new Ray(new Point(0,-78,-40),new Vector(0,1,0)),60d)
                        .setEmission(new Color(230,111,20))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)
                                .setKr(0.2).setKt(0.8))
                ,new Sphere(new Point(0,-40,-40), 40d)
                        .setEmission(new Color(165, 242, 243))
                        .setMaterial(
                                new Material().setKd(0.8).setKs(0.3).setKsG(0.7).setKdG(0.3).setShininess(100)
                                        .setKt(0.6).setKr(0.4)
                        )
                //endregion
                //region bottle
                //inside bottle:
                ,new Cylinder(25d,new Ray(new Point(100,-75,-40),new Vector(0,1,0)),100d)
                        .setEmission(new Color(169,64,7))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                .setKr(0.2).setKt(0.5).setKdG(0.8))
                //bottle
                ,new Cylinder(30d,new Ray(new Point(100,-80,-40),new Vector(0,1,0)),170d)
                        .setEmission(new Color(0, 106, 78))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                .setKr(0.0009).setKt(0.63))
                //bottleneck
                ,new Cylinder(10d,new Ray(new Point(100,90,-40),new Vector(0,1,0)),27d)
                        .setEmission(new Color(0, 106, 78))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                .setKr(0.0009).setKt(0.63))
                //cap
                ,new Cylinder(13d,new Ray(new Point(100,110,-40),new Vector(0,1,0)),8d)
                        .setEmission(new Color(110, 106, 78))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5)
                                .setKr(0.009).setKt(0))
                //endregion
                //region rose/flower
                , new Sphere(50,new Point(-150,-40,-50))
                        .setEmission(new Color(black))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKsG(0.8).setShininess(300)
                                .setKr(0.5).setKt(0.5).setKdG(0.2))
//                , new Sphere(5,new Point(-150,-40,-50))
//                        .setEmission(new Color(black))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)
//                                .setKr(0).setKt(1))
//                , new Sphere(20,new Point(-150,-40,-50))
//                        .setEmission(new Color(black))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)
//                                .setKr(0).setKt(1))
                //region lower-middle petals
//                , new Triangle(new Point(-119.11,-74.88,-68.14), new Point(-101.9,-40,-68.14), new Point(-81.63,-71.01,-63.6))//1
//                        .setEmission(new Color(yellow))
//                , new Triangle(new Point(-120,-50,-10), new Point(-120,-45,-30), new Point(-81.63,-71.01,-63.6))//2
//                        .setEmission(new Color(yellow))
//                , new Triangle(new Point(-140,-50,-10), new Point(-140,-40,-30), new Point(-81.63,-71.01,-63.6))//3
//                        .setEmission(new Color(yellow))
                , new Triangle(J,E1,B1)//1
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(E1,K,B2)//2
                        .setEmission(new Color(178,58,58))
                        //.setEmission(new Color(yellow))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(K,G1,B3)//3
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(G1,F1,B4)//4
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(F1,H1,B5)//5
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(H1,I,B6)//6
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(I,D1,B7)//7
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(D1,J,B8)//8
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                //endregion
                //region middle layer:
                , new Triangle(J2,E2,B11)//1
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(E2,K2,B12)//2
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(K2,G2,B13)//3
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(G2,F2,B14)//4
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(F2,H2,B15)//5
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(H2,I2,B16)//6
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(I2,D2,B17)//7
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(D2,J2,B18)//8
                        .setEmission(new Color(140,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
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
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(E3,K3,B32)//2
                        .setEmission(new Color(178,58,58))
                        //.setEmission(new Color(yellow))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(K3,G3,B33)//3
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(G3,F3,B34)//4
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(F3,H3,B35)//5
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(H3,I3,B36)//6
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(I3,D3,B37)//7
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                , new Triangle(D3,J3,B38)//8
                        .setEmission(new Color(178,58,58))
                        .setMaterial(new Material().setKd(1).setKs(0).setShininess(30)
                                .setKr(0.5).setKt(0.5))
                //endregion
                //endregion
//endregion
        );
        //region lighting
        scene.lights.add(new PointLight(new Color(blue), new Point(30, 70, 0))
                .setKl(0.001).setKq(0.0000002));

        scene.lights.add(new PointLight(new Color(blue), new Point(64,-156,80))
                .setKl(0.001).setKq(0.0000002));
        //light inside sphere
//        scene.lights.add(new SpotLight(new Color(ORANGE), new Point(0,-80,-40), new Vector(0,1,0))
//                        .setNarrowBeam(0.0001)
//                .setKl(0.001).setKq(0.0000002));
        scene.lights.add(new SpotLight(new Color(orange), new Point(100,-75,-40), new Vector(0,1,0))
                .setNarrowBeam(0.00000000000000000000001)
                .setKl(0.001).setKq(0.0000002));

        scene.lights.add(new SpotLight(new Color(64,156,255), new Point(-150,-40,-50), new Vector(0,1,0))
                .setNarrowBeam(0.001)
                .setKl(0.001).setKq(0.0000002));
        scene.lights.add(new DirectionalLight(new Color(64,156,255), new Vector(0,-1,0)));
        //endregion
//        scene.lights.add(new DirectionalLight(new Color(ORANGE), new Vector(0,-40,-40)));
        Camera camera = new Camera(new Point(-3.6, -14.39,0), new Vector(0, 0, -1), new Vector(0, 1, 0));
//-24.43,3273.3,308.49 - more of a top view
        basicRender(scene, camera);
        //
        //regularRender(scene, camera);
        camera.writeToImage();
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

    private static void basicRender(Scene scene, Camera camera) {
        camera.moveCamera(new Point(80, 20, 300), new Point(0, -40, -40))
                .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
                .setRayTracer(new RayTracerBasic(scene))
                //.setImageWriter(new ImageWriter("whiskeyCup", 1000, 1000))
                //.setImageWriter(new ImageWriter("whiskeyCupNONE", 1000, 1000))
                //.setImageWriter(new ImageWriter("whiskeyCupREGULAR", 1000, 1000))
                .setImageWriter(new ImageWriter("whiskeyCupADAPTIVE", 1000, 1000))
                .setAntiAliasingFactor(9)
                .setUseAdaptive(true)
                //.setApertureSize(0.01).setFocalDistance(900)
                .setMultithreading(3).setDebugPrint(0.1)
                .renderImage();
    }
    private static void regularRender(Scene scene, Camera camera) {
        scene.setResolution(10);
        camera.moveCamera(new Point(80, 20, 300), new Point(0, -40, -40))
                .setViewPlaneSize(700, 700).setViewPlaneDistance(700)
                .setImageWriter(new ImageWriter("whiskeyCup", 1000, 1000))
                .setRayTracer(new RayTracerRegular(scene))
                //.setSuperSampling(Camera.SUPER_SAMPLING_TYPE.ADAPTIVE)
                //.setApertureSize(0.01).setFocalDistance(900)
                .setMultithreading(3).setDebugPrint(0.1)
                .renderImage();
    }
}
