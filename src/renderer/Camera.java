package renderer;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
//minip1
	/**
	 * The RayTracerBase instance associated with the camera, used for tracing rays
	 * and determining the color of pixels in the rendered image.
	 */

	/** Aperture radius */
	private double apertureRadius = 0;

	/** DoF active */
	boolean depthOfFieledActive = true;
	/** Focal length */
	private double focalLength = 0;

	// private double focalDistance = 1000.0; // default value

	/**
	 * Aperture area grid density
	 */
	private int gridDensity = 1;// 7;

	/**
	 * depthOfFieled points on the aperture plane
	 */
	public List<Point> depthOfFieledPoints = null;

	// minip2
	// private boolean multiThreading = false;
	/**
	 * Flag to enable or disable super sampling.
	 * <p>
	 * {@code true} to enable, {@code false} to disable. Default is {@code false}.
	 * </p>
	 */
	private boolean superSempling = false;
	// Additions for minip2:
	/** The number of threads used for rendering. */
	private int threadsCount = 0;

	/** The interval for printing progress during rendering, in seconds. */
	private double printInterval = 0;

	/** The number of spare threads to be maintained during rendering. */
	private final int SPARE_THREADS = 2;
	/**
	 * Pixel manager for supporting: multi-threading debug print of progress
	 * percentage in Console window
	 */
	private PixelManager pixelManager;
	/** The maximum number of rays that can be traced by the camera. */
	private static final int MAX_RAYS = 100;

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
		 * Method to set DoFPoints and return the Builder
		 * 
		 * @param DoFPoints used for a list of points within the area based on a
		 *                  specified number of points, radius, center, up vector, and
		 *                  right vector.
		 * @return the builder instance *
		 */
		public Builder setdepthOfFieledPointsr(List<Point> DoFPoints) {
			this.camera.depthOfFieledPoints = DoFPoints;
			return this;
		}

		/**
		 * Sets the aperture radius for the camera.
		 *
		 * @param apertureRadius the aperture radius to set
		 * @param focalLength    the focal length to set
		 * @param gridDensity    the grid density to set
		 * @return the Builder instance
		 */
		public Builder setFocalSize(double apertureRadius, double focalLength, int gridDensity) {
			this.camera.apertureRadius = apertureRadius;
			this.camera.focalLength = focalLength;
			this.camera.gridDensity = gridDensity;
			return this;
		}

		/**
		 * Sets the aperture radius for the camera.
		 *
		 * @param apertureRadius the aperture radius to set
		 * @return the Builder instance
		 */
		public Builder setApertureRadius(double apertureRadius) {

			this.camera.apertureRadius = apertureRadius;
			return this;
		}

		/**
		 * Sets the grid density for the camera.
		 *
		 * <p>
		 * The grid density determines the number of grid lines or divisions that will
		 * be used in the camera's view plane for rendering purposes. This setting can
		 * be useful for various applications, such as aiding in alignment,
		 * visualization, or creating grid-based effects in the rendered image.
		 * </p>
		 *
		 * @param gridDensity The number of grid lines or divisions along each dimension
		 *                    of the camera's view plane. Must be a positive integer.
		 *                    Higher values increase the density of the grid, providing
		 *                    more detailed subdivisions.
		 * @return The current instance of the {@code Builder} class, allowing for
		 *         method chaining.
		 */
		public Builder setgridDensity(int gridDensity) {
			this.camera.gridDensity = gridDensity;
			return this;
		}

		/**
		 * Sets the focal length of the camera.
		 *
		 * <p>
		 * The focal length is a critical parameter that affects the perspective and
		 * depth of field in the rendered image. Increasing the focal length will zoom
		 * in on the scene, while decreasing it will provide a wider view. This setting
		 * is essential for controlling the amount of perspective distortion and
		 * achieving the desired focus effect in the final rendered image.
		 * </p>
		 *
		 * @param focalLength The focal length of the camera, in millimeters. This value
		 *                    must be greater than zero. The focal length determines how
		 *                    much the camera will zoom in or out on the scene.
		 * @return The current instance of the {@code Builder} class, allowing for
		 *         method chaining.
		 */
		public Builder setFocalLength(double focalLength) {
			this.camera.focalLength = focalLength;
			return this;
		}

		/**
		 * Activates or deactivates the depth of field effect for the camera.
		 *
		 * <p>
		 * Enabling the depth of field effect allows for a more realistic representation
		 * of focus in the rendered image. When activated, objects at different
		 * distances from the camera will appear with varying degrees of sharpness based
		 * on their distance from the focal point. Objects closer to or farther from the
		 * focal point will appear blurred according to their distance from the focal
		 * plane.
		 * </p>
		 *
		 * @param doFActive {@code true} to activate the depth of field effect, making
		 *                  the image focus on objects at different distances with a
		 *                  corresponding blur effect; {@code false} to deactivate it,
		 *                  rendering the scene with uniform sharpness.
		 * @return The current instance of the {@code Builder} class, allowing for
		 *         method chaining.
		 */
		public Builder depthOfFieledActive(boolean doFActive) {

			this.camera.depthOfFieledActive = doFActive;
			return this;
		}

		/**
		 * Sets the grid density for the camera's depth of field effect.
		 *
		 * @param gridDensity the grid density to set
		 * @return the Builder instance
		 */
		public Builder setGridDensity(int gridDensity) {
			this.camera.gridDensity = gridDensity;
			return this;
		}

		/**
		 * Method to set DoFPoints and return the Builder
		 * 
		 * @param DoFPoints used for a list of points within the area based on a
		 *                  specified number of points, radius, center, up vector, and
		 *                  right vector.
		 * @return the builder instance *
		 */
		public Builder setdepthOfFieledPoints(List<Point> DoFPoints) {
			this.camera.depthOfFieledPoints = DoFPoints;
			return this;
		}

		/** Sets the focal distance for depth of field. */
		/*
		 * public Builder setFocalDistance(double focalDistance) { if (focalDistance <=
		 * 0) throw new IllegalArgumentException("Focal distance must be positive");
		 * this.camera.focalDistance = focalDistance; return this; }
		 */
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

		// minip2
		/**
		 * Enables or disables multithreading.
		 *
		 * @param multiThreading true to enable multithreading, false to disable.
		 * @return the current Builder instance for method chaining.
		 */

		/*
		 * public Builder setMultiThreading(boolean multiThreading) {
		 * this.camera.multiThreading = multiThreading; return this; }
		 */
		/**
		 * amount of threads setter for multi-threading
		 * 
		 * @param threads number of threads to run at the same time
		 * @return camera (builder)
		 */
		public Builder setMultiThreading(int threads) {
			if (threads < -2)
				throw new IllegalArgumentException("Multithreading must be -2 or higher");
			if (threads >= -1)
				this.camera.threadsCount = threads;
			else { // == -2
				int cores = Runtime.getRuntime().availableProcessors() - this.camera.SPARE_THREADS;
				this.camera.threadsCount = cores <= 2 ? 1 : cores;
			}
			return this;
		}

		/**
		 * interval setter for debug print
		 * 
		 * @param interval the print interval
		 * @return camera (builder)
		 */
		public Builder setDebugPrint(double interval) {
			this.camera.printInterval = interval;
			return this;
		}

		/**
		 * Enables or disables super sampling.
		 *
		 * @param superSempling true to enable super sampling, false to disable.
		 * @return the current Builder instance for method chaining.
		 */
		public Builder setSuperSempling(boolean superSempling) {
			this.camera.superSempling = superSempling;
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

	// stage5, and 9
	/**
	 * This method performs image rendering by casting rays of light for each pixel
	 * in the image and computing their color. It utilizes the image dimensions
	 * provided by the imageWriter object to determine the appropriate number of
	 * rays for each pixel, then invokes the castRay method for each pixel.
	 * 
	 * @return The current state of the camera, for further use within this class or
	 *         in closely related classes.
	 */
	/*
	 * public Camera renderImage() { int nX = this.imageWriter.getNx(); int nY =
	 * this.imageWriter.getNy();
	 * 
	 * if (this.gridDensity!=1) { this.depthOfFieledPoints =
	 * Camera.generatePoints(gridDensity, apertureRadius, position, vUp, vRight);
	 * for (int i = 0; i < nX; i++) { for (int j = 0; j < nY; j++) { var focalPoint
	 * = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i)
	 * .getPoint(focalLength); imageWriter.writePixel(j, i,
	 * rayTracer.computeFinalColor(Ray.RayBundle(focalPoint, depthOfFieledPoints)));
	 * } } } else { //בלי שיפורים for (int i = 0; i < nX; i++) { for (int j = 0; j <
	 * nY; j++) { this.castRay(j, i); } } }
	 * 
	 * return this; }
	 */

	/**
	 * Renders the image using the configured ray tracer and image writer.
	 * 
	 * @return the Camera instance
	 */

	public Camera renderImage() {
		if (this.imageWriter == null)
			throw new UnsupportedOperationException("Missing imageWriter");
		if (this.rayTracer == null)
			throw new UnsupportedOperationException("Missing rayTracerBase");
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();

		pixelManager = new PixelManager(nY, nX, printInterval);

		if (this.gridDensity != 1) {
			this.depthOfFieledPoints = Camera.generatePoints(gridDensity, apertureRadius, position, vUp, vRight);
		}

		if (threadsCount == 0) { // Single-threaded rendering
			for (int i = 0; i < nY; ++i) {
				for (int j = 0; j < nX; ++j) {
					if (this.gridDensity != 1) {
						// Try optimizing the focal point generation
						var focalPoint = constructRay(nX, nY, j, i).getPoint(focalLength);
						List<Ray> rayBundle = Ray.RayBundle(focalPoint, depthOfFieledPoints);

						// Possibly limit the number of rays if memory is an issue
						if (rayBundle.size() > MAX_RAYS) {
							rayBundle = rayBundle.subList(0, MAX_RAYS);
						}

						imageWriter.writePixel(j, i, rayTracer.computeFinalColor(rayBundle));
						pixelManager.pixelDone();
					} else {
						castRay(j, i);
					}

				}
			}
		} else { // Multi-threaded rendering
			var threads = new LinkedList<Thread>(); // list of threads
			while (threadsCount-- > 0) // add appropriate number of threads
				threads.add(new Thread(() -> { // add a thread with its code
					PixelManager.Pixel pixel; // current pixel(row,col)
					// allocate pixel(row,col) in loop until there are no more pixels
					while ((pixel = pixelManager.nextPixel()) != null) {
						if (this.gridDensity != 1) {
							var focalPoint = constructRay(nX, nY, pixel.col(), pixel.row()).getPoint(focalLength);
							imageWriter.writePixel(pixel.col(), pixel.row(),
									rayTracer.computeFinalColor(Ray.RayBundle(focalPoint, depthOfFieledPoints)));
							pixelManager.pixelDone();
						} else {
							castRay(pixel.col(), pixel.row());
						}

					}
				}));
			// start all the threads
			for (var thread : threads)
				thread.start();
			// wait until all the threads have finished
			try {
				for (var thread : threads)
					thread.join();
			} catch (InterruptedException ignore) {
			}
		}
		return this;

	}

	/*
	 * public Camera renderImage() { int x = this.imageWriter.getNx(); int y =
	 * this.imageWriter.getNy();
	 * 
	 * if (depthOfFieledActive) { this.depthOfFieledPoints =
	 * Camera.generatePoints(gridDensity, apertureRadius, position, vUp, vRight);
	 * for (int i = 0; i < x; i++) { for (int j = 0; j < y; j++) { var focalPoint =
	 * constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i)
	 * .getPoint(focalLength); imageWriter.writePixel(j, i,
	 * rayTracer.computeFinalColor(Ray.RayBundle(focalPoint, depthOfFieledPoints)));
	 * } } } else { for (int i = 0; i < x; i++) { for (int j = 0; j < y; j++) {
	 * this.castRay(j, i); } } }
	 * 
	 * //if (!multiThreading) { if (antiAliasingRays == 1) { if (!superSempling) {
	 * //Basic image without enhancements in camera. for (int i = 0; i < nx; i++) {
	 * for (int j = 0; j < ny; j++) { castRay(nx, ny, i, j); } } } //There can't be
	 * super sampling without anti-aliasing, because only one beam is sent.
	 * 
	 * } else { //with anti-aliasing if (!superSempling) { //without super sempling
	 * and multi threading for (int i = 0; i < nx; i++) { for (int j = 0; j < ny;
	 * j++) { List<Ray> rays = this.constructRays(nx, ny, i, j); Color color =
	 * average(rays); this.imageWriter.writePixel(i, j, color); } } } else { //with
	 * super sempling for (int i = 0; i < nx; i++) { for (int j = 0; j < ny; j++) {
	 * // construct a ray through the current pixel List<Ray> rays =
	 * this.constructRays(nx, ny, i, j); // get the color of the point from trace
	 * ray Color color = this.rayTracer.adaptiveTraceRays(rays);
	 * imageWriter.writePixel(i, j, color); } } } } } else { //with multi threading
	 * if (antiAliasingRays == 1) { //without super sempling IntStream.range(0,
	 * ny).parallel() .forEach(i -> IntStream.range(0, nx).parallel() .forEach(j ->
	 * castRay(nx, ny, i, j))); } else { //with anti-aliasing if (!superSempling) {
	 * IntStream.range(0, ny).parallel() .forEach(i -> IntStream.range(0,
	 * nx).parallel() .forEach(j -> { List<Ray> rays = this.constructRays(nx, ny, i,
	 * j); Color color = average(rays); this.imageWriter.writePixel(i, j, color);
	 * })); } else { //all of the enhancements in camera. IntStream.range(0,
	 * ny).parallel() .forEach(i -> IntStream.range(0, nx).parallel() .forEach(j ->
	 * { List<Ray> rays = this.constructRays(nx, ny, i, j); // get the color of the
	 * point from trace ray Color color = this.rayTracer.adaptiveTraceRays(rays); //
	 * write the pixel color to the image imageWriter.writePixel(i, j, color); }));
	 * } } }//
	 * 
	 * return this;
	 * 
	 * }
	 */

	/**
	 * Casts a ray through the center of a pixel and colors the pixel using the ray
	 * tracer.
	 * 
	 * @param j number of the pixel in a row
	 * @param i number of the pixel in a column
	 */
	private void castRay(int j, int i) {
		// Constructs a ray through the given pixel.
		Ray ray = constructRay(this.imageWriter.getNx(), this.imageWriter.getNy(), j, i);

		// Uses rayTracer to calculate the color of the intersection point and returns
		// it.
		this.imageWriter.writePixel(j, i, rayTracer.traceRay(ray));

		// stage 9-
	}

	/**
	 * Finds the location of the central point of a specified pixel.
	 * 
	 * @param numXPixels the number of pixels along the horizontal axis (width) of
	 *                   the screen.
	 * @param numYPixels the number of pixels along the vertical axis (height) of
	 *                   the screen.
	 * @param i          the x coordinate of the pixel
	 * @param j          the y coordinate of the pixel
	 * @return the location of the central point of the specified pixel
	 */
	private Point findPixelLocation(int numXPixels, int numYPixels, int i, int j) {
		// Calculate the size of each pixel along the X and Y axes
		double pixelHeight = viewPlaneHeight / numYPixels;
		double pixelWidth = viewPlaneWidth / numXPixels;

		// Calculate the offset of the pixel from the center of the screen
		double offsetY = -(j - (numYPixels - 1d) / 2) * pixelHeight;
		double offsetX = (i - (numXPixels - 1d) / 2) * pixelWidth;

		// Compute the point at the center of the screen based on the camera's view
		// direction
		Point pixelPoint = position.add(vTo.scale(viewPlaneDistance));

		// Adjust the center point by the calculated offsets to get the exact pixel
		// location
		if (offsetY != 0)
			pixelPoint = pixelPoint.add(vUp.scale(offsetY));
		if (offsetX != 0)
			pixelPoint = pixelPoint.add(vRight.scale(offsetX));

		return pixelPoint;
	}

	// stage 9-
	/**
	 * Calculates the average color of a list of rays.
	 *
	 * @param rays the list of rays
	 * @return the average color of the rays
	 */
	private Color average(List<Ray> rays) {
		Color colorOfPixel = Color.BLACK;
		for (Ray ray : rays) {
			Color colorOfRay = this.rayTracer.traceRay(ray);
			colorOfPixel = colorOfPixel.add(colorOfRay);
		}
		return colorOfPixel.reduce(rays.size());
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
	/*
	 * private void castRay(int nX, int nY, int column, int row) { Ray ray =
	 * constructRay(nX, nY, column, row); Color color = rayTracer.traceRay(ray);
	 * imageWriter.writePixel(column, row, color);
	 * 
	 * }
	 */
	/**
	 * Generates a list of points randomly distributed within a circular area.
	 * 
	 * @param gridDensity The number of points to generate.
	 * @param radius      The radius of the circular area.
	 * @param center      The center point of the circular area.
	 * @param up          A vector representing the up direction for the circular
	 *                    area.
	 * @param right       A vector representing the right direction for the circular
	 *                    area.
	 * @return A list of points randomly distributed within the circular area.
	 */
	public static List<Point> generatePoints(int gridDensity, double radius, Point center, Vector up, Vector right) {
		List<Point> points = new ArrayList<>();

		for (int i = 0; i < gridDensity; i++) {
			double angle = 2 * Math.PI * Math.random();
			double r = radius * Math.sqrt(Math.random());
			double offsetX = r * Math.cos(angle);
			double offsetY = r * Math.sin(angle);

			Point point = center.add(right.scale(offsetX)).add(up.scale(offsetY));
			points.add(point);
		}
		return points;
	}

}