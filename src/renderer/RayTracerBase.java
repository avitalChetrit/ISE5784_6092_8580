package renderer;

import java.util.List;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract base class for ray tracing algorithms.
 */
public abstract class RayTracerBase {
	/** The scene being traced. */
	protected Scene scene;

	/**
	 * Constructs a new RayTracerBase with the specified scene.
	 * 
	 * @param scene The scene to be traced.
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Traces the given ray through the scene to determine the color at its intersection point.
	 *
	 * @param ray The ray to trace.
	 * @return The color at the ray's intersection with the scene.
	 * @throws NullPointerException If {@code ray} is {@code null}.
	 */
	public abstract Color traceRay(Ray ray);

	/**
	 * Tracing a Ray method - improvement anti-aliasing
	 * 
	 * @param rays given list of rays
	 * @return Color that we see from the rays' intersection with a geometry
	 */
	public abstract Color traceRays(List<Ray> rays);

	// stage 9-
	/**
	 * Performs adaptive ray tracing on a given list of rays and returns the
	 * calculated color.
	 *
	 * @param rays A list of objects to trace for color calculation.
	 * @return The resulting color from the adaptive super sampling process.
	 */
	public abstract Color adaptiveTraceRays(List<Ray> rays);
	/**
	 * Computes the final color for a list of rays, typically used for calculating effects like depth of field or antialiasing.
	 *
	 * @param rays The list of rays to be processed.
	 * @return The computed final color based on the provided rays.
	 * @throws NullPointerException If {@code rays} is {@code null}.
	 */
	public abstract Color computeFinalColor(List<Ray> rays);

}
