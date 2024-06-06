package renderer;

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

	@Override
	public Color traceRay(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}

