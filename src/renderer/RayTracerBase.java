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
	 * Abstract method to trace a ray and compute its color.
	 * 
	 * @param ray The ray to be traced.
	 * @return The color computed for the traced ray.
	 */
	public abstract Color traceRay(Ray ray);

	/**
	 * Tracing a Ray method - improvement anti-aliasing
	 * 
	 * @param rays given list of rays
	 * @return Color that we see from the rays' intersection with a geometry
	 */
	public abstract Color traceRays(List<Ray> rays);

///???
	/**
	 * Computes the final color by tracing a list of rays and averaging their
	 * resulting colors.
	 * 
	 * @param rays The list of rays to be traced.
	 * @return The averaged color resulting from tracing all the rays in the list.
	 */
	/*
	 * public Color computeFinalColor(List<Ray> rays) { Color finalColor =
	 * Color.BLACK; for (Ray ray : rays) { finalColor =
	 * finalColor.add(traceRay(ray)); } return finalColor.scale(1.0/(rays.size()));
	 * }
	 */
	public abstract Color computeFinalColor(List<Ray> rays);

}
