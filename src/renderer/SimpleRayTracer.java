package renderer;

import primitives.Point;
import primitives.Ray;
import scene.Scene;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Vector;
import static primitives.Util.alignZero;
import static java.lang.Math.*;
import java.util.List;
//import java.util.Vector;

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
	 *         if no intersections are found.
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
	 * Calculates the color at a given geometric point considering ambient light,
	 * emission from the geometry, and local lighting effects.
	 *
	 * @param gp  The geometric point at which to calculate the color.
	 * @param ray The ray that intersected with the geometry at the geometric point.
	 * @return The calculated color at the geometric point, taking into account
	 *         ambient light, emission, and local lighting effects (diffuse and
	 *         specular reflections).
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return scene.ambientLight.getIntensity().add(gp.geometry.getEmission()).add(calcLocalEffects(gp, ray));
	}

	/**
	 * Calculates the local effects (diffuse and specular reflections) of light on a
	 * given geometry point. This method considers the contribution of each light
	 * source in the scene.
	 *
	 * @param gp  The geometric point on which to calculate the local effects.
	 * @param ray The ray used to intersect with the geometry.
	 * @return The color resulting from local lighting effects, or null if there is
	 *         no interaction (nv == 0).
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Vector n = gp.geometry.getNormal(gp.point);
		Vector v = ray.getDirection();
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return null;
		Material material = gp.geometry.getMaterial();
		Color color = gp.geometry.getEmission();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) {
				// sign(nl) == sing(nv);
				Color iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
			}
		}
		return color;
	}

	/**
	 * Checks if a point on a geometry is unshaded by a given light source.
	 *
	 * @param gp The geometric point to check for shading.
	 * @param l  The direction vector from the point towards the light source.
	 * @return True if the point is unshaded by the light source, false otherwise.
	 */
	private boolean unshaded(GeoPoint gp, Vector l) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray ray = new Ray(gp.point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		return intersections.isEmpty();
	}

	/**
	 * Calculates the diffuse reflection component based on the material properties
	 * and the cosine of the angle between the normal vector and the light direction
	 * vector.
	 *
	 * @param material The material of the geometry.
	 * @param nl       The dot product of the normal vector and the light direction
	 *                 vector.
	 * @return The diffuse reflection color component.
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(abs(nl));
	}

	/**
	 * Calculates the specular reflection component based on the material
	 * properties, the normal vector, light direction vector, view direction vector,
	 * and the cosine of the angle between the view direction and the reflection
	 * direction.
	 *
	 * @param material The material of the geometry.
	 * @param n        The normal vector at the geometric point.
	 * @param l        The direction vector from the point to the light source.
	 * @param nl       The dot product of the normal vector and the light direction
	 *                 vector.
	 * @param v        The view direction vector.
	 * @return The specular reflection color component.
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Vector reflectVector = (l).subtract(n.scale(nl * 2));
		double max0_var = max(0, v.scale(-1).dotProduct(reflectVector));
		return material.kS.scale(pow(max0_var, material.shininess));

	}
}