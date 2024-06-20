package renderer;

import primitives.Point;
import primitives.Ray;
import scene.Scene;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Vector;

/**
 * A simple ray tracer implementation.
 * <p>
 * This class extends the {@link RayTracerBase} abstract base class.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a new SimpleRayTracer with the specified scene.
     *
     * @param scene The scene to be traced.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray in the scene and returns the color of the closest intersection
     * point.
     *
     * @param ray The ray to trace.
     * @return The color of the closest intersection point, or the background color
     * if no intersections are found.
     */

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersectionsHelper(ray);
        if (points == null) {
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(points);
        return calcColor(closestPoint, ray);
    }
    
    /**
     * Calculates the color of a given intersection point in the scene.
     *
     * @param gp The intersection point in the scene.
     * @return The color of the ambient light plus the emission color of the geometry.
     */
    private Color calcColor(GeoPoint gp,Ray ray) {
    	return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());//.add(calcLocalEffects(gp,ray));
    }


}
