package renderer;

import primitives.*;
import java.util.MissingResourceException;
import static primitives.Util.*;

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
	 * Private constructor
	 *
	 */
	public Camera() {
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
		Point pc = position.add(vTo.scale(viewPlaneDistance));
		double ry = viewPlaneHeight / nY;
		double rx = viewPlaneWidth / nX;
		double yi = -(i - (nY - 1) / 2.0) * ry;
		double xj = (j - (nX - 1) / 2.0) * rx;
		Point pij = pc;
		if (xj != 0)
			pij = pij.add(vRight.scale(xj));
		if (yi != 0)
			pij = pij.add(vUp.scale(yi));
		Vector Vij = pij.subtract(position);
		return new Ray(position, Vij.normalize());
	}

	/**
	 * Builder class for Camera, implementing the Builder Pattern.
	 */
	public static class Builder {
		/**
		 * Represents a builder for constructing Camera objects. This builder class
		 * allows for the creation of Camera objects with a fluent interface.
		 */
		private final Camera camera;

		/**
		 * Initial the superSampling
		 */
		private int superSampling = 0;

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
			if (vTo == null || vUp == null)
				throw new IllegalArgumentException("Direction vectors cannot be null");
			if (!isZero(vTo.dotProduct(vUp)))
				throw new IllegalArgumentException("Direction vectors must be orthogonal");

			camera.vTo = vTo.normalize();
			camera.vUp = vUp.normalize();
			camera.vRight = vTo.crossProduct(vUp).normalize();
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
			if (alignZero(width) <= 0 || alignZero(height) <= 0)
				throw new IllegalArgumentException("View plane dimensions must be positive");

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
			if (alignZero(distance) <= 0)
				throw new IllegalArgumentException("View plane distance must be positive");

			camera.viewPlaneDistance = distance;
			return this;
		}

		/**
		 * Sets the image writer used by the camera to write the rendered image.
		 *
		 * @param imageWriter the image writer to set
		 * @return the current Builder object
		 * @throws IllegalArgumentException if the provided image writer is null
		 */
		public Builder setImageWriter(ImageWriter imageWriter) {// stage5
			if (imageWriter == null)
				throw new IllegalArgumentException("Image writer cannot be null");

			camera.imageWriter = imageWriter;
			return this;
		}

		/**
		 * Sets the ray tracer base used by the camera to trace rays and render the
		 * scene.
		 *
		 * @param rayTracer the ray tracer base to set
		 * @return the current Builder object
		 * @throws IllegalArgumentException if the provided ray tracer base is null
		 */
		public Builder setRayTracer(RayTracerBase rayTracer) {// stage5
			if (rayTracer == null)
				throw new IllegalArgumentException("Ray tracer base cannot be null");

			camera.rayTracer = rayTracer;
			return this;
		}

		/**
		 * sets the superSampling flag of the Camera.
		 *
		 * @param superSampling the SuperSampling flag and amount of rays in beam
		 * @return this Camera object
		 */
		public Builder setSuperSampling(int superSampling) {
			this.superSampling = superSampling;
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
			if (camera.position == null)
				throw new MissingResourceException(missingData, Camera.class.getName(), "position");

			if (camera.vTo == null)
				throw new MissingResourceException(missingData, Camera.class.getName(), "vTo");
			if (camera.vUp == null)
				throw new MissingResourceException(missingData, Camera.class.getName(), "vUp");
			if (!isZero(camera.vTo.dotProduct(camera.vUp)))
				throw new IllegalArgumentException("Direction vectors must be perpendicular");
			// Calculate the right vector
			if (camera.vRight == null)
				camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

			// Validate the values of the fields
			if (alignZero(camera.viewPlaneWidth) <= 0)
				throw new IllegalStateException("Width must be positive");
			if (alignZero(camera.viewPlaneHeight) <= 0)
				throw new IllegalStateException("Height must be positive");
			if (alignZero(camera.viewPlaneDistance) <= 0)
				throw new IllegalStateException("Distance must be positive");

			if (this.camera.imageWriter == null) {
				throw new IllegalStateException("imageWriter can not be null");
			}
			if (this.camera.rayTracer == null) {
				throw new IllegalStateException("rayTracer can not be null");
			}

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new AssertionError(); // Can't happen
			}
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

	// stage5
	/**
	 * The image writer used by this camera to write the rendered image.
	 */
	private ImageWriter imageWriter;
	// stage5
	/**
	 * The ray tracer base used by this camera to trace rays and render the scene.
	 */
	private RayTracerBase rayTracer;

	// stage5
	/**
	 * This method prints a grid pattern onto the image, with specified intervals
	 * between grid lines and color for the grid lines.
	 *
	 * @param interval The interval between grid lines. Must be greater than 0.
	 * @param color    The color of the grid lines.
	 * @return The current state of the camera, for further use within this class or
	 *         in closely related classes.
	 * @throws IllegalArgumentException if the interval is not greater than 0.
	 */
	public Camera printGrid(int interval, Color color) {
		if (alignZero(interval) <= 0) {
			throw new IllegalArgumentException("Interval must be greater than 0");
		}
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		// Loop through the image and draw the grid lines
		for (int i = 0; i < nX; i += interval) {
			for (int j = 0; j < nY; j++) {
				imageWriter.writePixel(i, j, color); // Set the color of the grid line
			}
		}
		for (int j = 0; j < nY; j += interval) {
			for (int i = 0; i < nX; i++) {
				imageWriter.writePixel(i, j, color); // Set the color of the grid line
			}

		}
		return this;
	}

	// stage5
	/**
	 * Writes the image to a file using the appropriate method of the image writer.
	 */
	public void writeToImage() {
		// Check if image writer is initialized
		if (imageWriter == null) {
			throw new IllegalStateException("Image writer is not initialized");
		}

		// Call the appropriate method of the image writer to write the image
		imageWriter.writeToImage();
	}

	// stage5
	/**
	 * This method performs image rendering by casting rays of light for each pixel
	 * in the image and computing their color. It utilizes the image dimensions
	 * provided by the imageWriter object to determine the appropriate number of
	 * rays for each pixel, then invokes the castRay method for each pixel.
	 * 
	 * @return The current state of the camera, for further use within this class or
	 *         in closely related classes.
	 */
	public Camera renderImage() {
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		for (int i = 0; i < nX; ++i)
			for (int j = 0; j < nY; ++j)
				castRay(nX, nY, j, i);
		return this;

	}

	/**
	 * Casts a ray through a specific pixel in the image, computes the color of the
	 * pixel based on the ray-tracing algorithm, and writes the color to the
	 * corresponding pixel in the image.
	 *
	 * @param nX     The width of the image.
	 * @param nY     The height of the image.
	 * @param column The column index of the pixel.
	 * @param row    The row index of the pixel.
	 */
	private void castRay(int nX, int nY, int column, int row) {
		Ray ray = constructRay(nX, nY, column, row);
		Color color = rayTracer.traceRay(ray);
		imageWriter.writePixel(column, row, color);

	}

}
