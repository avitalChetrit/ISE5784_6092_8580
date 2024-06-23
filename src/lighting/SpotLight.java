package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.alignZero;

/**
 * A class representing a spotlight source. Inherits from PointLight and
 * implements LightSource.
 */
public class SpotLight extends PointLight {
	/**
	 * the direction vector.
	 */
	private Vector direction;
	/**
	 * the narrow beam value.
	 */
	private double narrowBeam = 1;

	/**
	 * Constructs a spotlight with the specified intensity, position, and direction.
	 *
	 * @param intensity The color intensity of the spotlight.
	 * @param position  The position of the spotlight in 3D space.
	 * @param direction The direction vector indicating the direction in which the
	 *                  spotlight is pointing. This vector will be normalized
	 *                  internally.
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	@Override
	public Vector getL(Point p) {
		return super.getL(p); // Returns the direction from the point to the light source
	}

	@Override
	public SpotLight setKC(double kC) {
		return (SpotLight) super.setKC(kC);
	}

	@Override
	public SpotLight setKL(double kL) {
		return (SpotLight) super.setKL(kL);
	}

	@Override
	public SpotLight setKQ(double kQ) {
		return (SpotLight) super.setKQ(kQ);
	}

	/**
	 * Get the intensity of the light at a given point.
	 *
	 * @param point the point at which to calculate the intensity
	 * @return the intensity of the light at point
	 */
	@Override
	public Color getIntensity(Point point) {
		double cos = alignZero(direction.dotProduct(getL(point)));
		return narrowBeam != 1
				? super.getIntensity(point).scale(Math.pow(Math.max(0, direction.dotProduct(getL(point))), narrowBeam))
				: super.getIntensity(point).scale(Math.max(0, direction.dotProduct(getL(point))));
	}

	/**
	 * Set the narrow beam factor. The narrow beam factor adjusts the concentration
	 * of the light beam.
	 *
	 * @param narrowBeam the narrow beam factor
	 * @return the SpotLight object
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}
}
