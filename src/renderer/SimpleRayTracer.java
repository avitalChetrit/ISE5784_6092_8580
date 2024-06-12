package renderer;
import primitives.Point;

//import java.awt.Point;
import java.util.List;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

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

//stage5
	 /**
     * Traces a ray in the scene and returns the color of the closest intersection point.
     *
     * @param ray The ray to trace.
     * @return The color of the closest intersection point, or the background color if no intersections are found.
     */
    public Color traceRay(Ray ray) {
        // Find intersections between the ray and the scene
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        return intersections==null?this.scene.background:calcColor(ray.findClosestPoint(intersections));
    }
    
    //stage5
    /**
     * Calculates the color of a given point in the scene.
     *
     * @param point The point in the scene. (Currently not used)
     * @return The color of the ambient light in the scene.
     */
    private Color calcColor(Point point) {
        // At this stage of the mini-project, return the color of the ambient light in the scene
        return this.scene.ambientLight.getIntensity();
    }

}

