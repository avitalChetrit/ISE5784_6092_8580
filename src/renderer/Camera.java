package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * Camera class represents a camera in 3D space using the Builder Pattern. The
 * class implements Cloneable to support cloning of the Camera object.
 */
public class Camera implements Cloneable {
	/**
	 * Represents a camera in 3D space using the Builder Pattern. The camera is
	 * defined by its position, direction vectors, and view plane dimensions.
	 * Implements Cloneable to support cloning of the Camera object.
	 */

	/**
	 * The position of the camera in the 3D space.
	 */
	private Point position;

	/**
	 * The direction vector towards which the camera is pointing.
	 */
	private Vector vTo;

	/**
	 * The direction vector representing the up direction of the camera.
	 */
	private Vector vUp;

	/**
	 * The direction vector representing the right direction of the camera.
	 */
	private Vector vRight;

	/**
	 * The width of the view plane.
	 */
	private double viewPlaneWidth = 0.0;

	/**
	 * The height of the view plane.
	 */
	private double viewPlaneHeight = 0.0;

	/**
	 * The distance from the camera to the view plane.
	 */
	private double viewPlaneDistance = 0.0;

	// Rest of the class implementation...

	/**
	 * Private constractor
	 *
	 */
	private Camera() {
	}

	/**
	 * Returns a new Builder object for Camera.
	 *
	 * @return a new Builder object
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * Constructs a ray from the camera through a pixel.
	 * 
	 * @param nX The number of pixels in the x-axis.
	 * @param nY The number of pixels in the y-axis.
	 * @param j  The x-coordinate of the pixel.
	 * @param i  The y-coordinate of the pixel.
	 * @return The constructed ray.
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point Pc = position.add(vTo.normalize().scale(viewPlaneDistance));
		double Ry = viewPlaneHeight / nY;
		double Rx = viewPlaneWidth / nX;
		double Yi = -(i - (nY - 1) / 2) * Ry;
		double Xj = (j - (nX - 1) / 2) * Rx;
		Point Pij = Pc;
		if (Xj != 0)
			Pij = Pij.add(vRight.normalize().scale(Xj));
		if (Yi != 0)
			Pij = Pij.add(vUp.normalize().scale(Yi));
		Vector Vij = Pij.subtract(position);
		return new Ray(position, Vij.normalize());
	}

	/**
	 * Builder class for Camera, implementing the Builder Pattern.
	 */
	public static class Builder {
		/**
		 * Represents a builder for constructing Camera objects.
		 * This builder class allows for the creation of Camera objects with a fluent interface.
		 */

		private final Camera camera;

		/**
		 * Private constructor for Builder.
		 */
		private Builder() {
			camera = new Camera();
		}

		/**
		 * Constructs a Builder object with the given Camera object.
		 * 
		 * @param camera to initialize the Builder with
		 */

		private Builder(Camera camera) {
			this.camera = camera;
		}

		/**
		 * Sets the position of the camera.
		 * 
		 * @param location The position to set for the camera.
		 * @return The current Builder object.
		 * @throws IllegalArgumentException if the provided position is null.
		 */
		public Builder setLocation(Point location) {
			if (location == null) {
				throw new IllegalArgumentException("Camera position cannot be null");
			}
			camera.position = location;
			return this;
		}

		/**
		 * Sets the direction of the camera.
		 *
		 * @param vTo the direction vector (towards)
		 * @param vUp the direction vector (up)
		 * @return the current Builder object
		 * @throws IllegalArgumentException if the vectors are null or not orthogonal
		 */
		public Builder setDirection(Vector vTo, Vector vUp) {
			if (vTo == null || vUp == null) {
				throw new IllegalArgumentException("Direction vectors cannot be null");
			}
			if (!(vTo.dotProduct(vUp) == 0)) {
				throw new IllegalArgumentException("Direction vectors must be orthogonal");
			}

			camera.vTo = vTo.normalize();
			camera.vUp = vUp.normalize();
			return this;
		}

		/**
		 * Sets the view plane size.
		 *
		 * @param width  the view plane width
		 * @param height the view plane height
		 * @return the current Builder object
		 * @throws IllegalArgumentException if the width or height is non-positive
		 */
		public Builder setVpSize(double width, double height) {
			if (width <= 0 || height <= 0) {
				throw new IllegalArgumentException("View plane dimensions must be positive");
			}
			camera.viewPlaneWidth = width;
			camera.viewPlaneHeight = height;
			return this;
		}

		/**
		 * Sets the view plane distance.
		 *
		 * @param distance the view plane distance
		 * @return the current Builder object
		 * @throws IllegalArgumentException if the distance is non-positive
		 */
		public Builder setVpDistance(double distance) {
			if (distance <= 0) {
				throw new IllegalArgumentException("View plane distance must be positive");
			}
			camera.viewPlaneDistance = distance;
			return this;
		}

		/**
		 * Builds the Camera object.
		 *
		 * @return the built Camera object
		 * @throws MissingResourceException if any required field is not set
		 */
		public Camera build() {
			final String missingData = "Missing rendering data";
			if (camera.position == null) {
				throw new MissingResourceException(missingData, Camera.class.getName(), "position");
			}
			if (camera.vTo == null) {
				throw new MissingResourceException(missingData, Camera.class.getName(), "vTo");
			}
			if (camera.vUp == null) {
				throw new MissingResourceException(missingData, Camera.class.getName(), "vUp");
			}
			if (camera.viewPlaneWidth == 0) {
				throw new MissingResourceException(missingData, Camera.class.getName(), "viewPlaneWidth");
			}
			if (camera.viewPlaneHeight == 0) {
				throw new MissingResourceException(missingData, Camera.class.getName(), "viewPlaneHeight");
			}
			if (camera.viewPlaneDistance == 0) {
				throw new MissingResourceException(missingData, Camera.class.getName(), "viewPlaneDistance");
			}

			// Calculate the right vector
			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
			return (Camera) camera.clone();
		}
	}

	@Override
	public Camera clone() {
		try {
			return (Camera) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // Can't happen
		}
	}

	/**
	 * Retrieves the position of the camera.
	 * 
	 * @return The position of the camera.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Retrieves the direction vector towards which the camera is pointing.
	 * 
	 * @return The direction vector towards which the camera is pointing.
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * Retrieves the direction vector representing the up direction of the camera.
	 * 
	 * @return The direction vector representing the up direction of the camera.
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * Retrieves the direction vector representing the right direction of the
	 * camera.
	 * 
	 * @return The direction vector representing the right direction of the camera.
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * Retrieves the width of the view plane.
	 * 
	 * @return The width of the view plane.
	 */
	public double getViewPlaneWidth() {
		return viewPlaneWidth;
	}

	/**
	 * Retrieves the height of the view plane.
	 * 
	 * @return The height of the view plane.
	 */
	public double getViewPlaneHeight() {
		return viewPlaneHeight;
	}

	/**
	 * Retrieves the distance from the camera to the view plane.
	 * 
	 * @return The distance from the camera to the view plane.
	 */
	public double getViewPlaneDistance() {
		return viewPlaneDistance;
	}

}
