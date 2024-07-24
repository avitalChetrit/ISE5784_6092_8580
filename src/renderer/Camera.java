package renderer;
import geometries.Plane;
import java.util.ArrayList;
import java.util.List;
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

    //-----Improvements-----
    //Anti-Aliasing
    //private static int ANTI_ALIASING_FACTOR = 1;
	/**
	 * Enumeration representing different types of super-sampling techniques.
	 */
	public enum SUPER_SAMPLING_TYPE {NONE, REGULAR, ADAPTIVE}
	/**
	 * Represents the settings for super-sampling used in rendering.
	 */
	private SUPER_SAMPLING_TYPE superSamplingType = SUPER_SAMPLING_TYPE.ADAPTIVE; // type of the super-sampling (eg. NONE,RANDOM, GRID)
	/**
	 * Grid size for regular super-sampling grid.
	 * Specifies the number of samples taken per pixel in both dimensions.
	 */
	private int superSamplingGridSize = 9; // grid size for regular super-sampling (e.g. 9 for 9x9 grid)
	/**
	 * Maximum recursion depth for adaptive super-sampling.
	 * Determines how deeply adaptive super-sampling algorithm recurses to refine pixel samples.
	 */
	private int adaptiveSuperSamplingMaxRecursionDepth = 3; // constant max depth for adaptive super-sampling
    //Focus
//  private boolean depthOfFieldFlag = false;
//  private double focalDistance = 2;
//  private double apertureSize = 0.01;
//  private static int NUM_OF_APERTURE_POINTS = 2;
//ON/OFF button default is off
//  private boolean depthButton = false;
//  //focal length
//  private double focalLength = 2;
//  private double apertureSize = 0.01;
//  private static final int NUMBER_OF_APERTURE_POINTS = 10;
  //Aperture properties
	/**
	 * Represents the camera's settings and parameters for rendering and image capturing.
	 */
	private int APERTURE_NUMBER_OF_POINTS = 9; // Number of points in the aperture (e.g., 9 for a 9x9 grid).
	/**
	 * The size of the aperture used for depth of field effects.
	 * Larger values simulate a larger aperture, resulting in a shallower depth of field.
	 */
	private double apertureSize = 0;
	/**
	 * Array of points defining the aperture shape.
	 * These points determine the distribution and shape of the aperture opening.
	 */
	private Point[] aperturePoints;
	/**
	 * The distance from the camera where objects appear in sharp focus.
	 * Determines the focal plane of the camera.
	 */
	private double focalDistance = 2;
	/**
	 * The plane in space that corresponds to the focal distance.
	 * Objects at this distance will be in sharp focus when captured by the camera.
	 */
	private Plane FOCAL_PLANE;
	/**
	 * Flag indicating whether multi-threading is enabled for rendering.
	 * When enabled, rendering tasks are distributed across multiple threads for improved performance.
	 */
	private boolean multiThreading = false;
	/**
	 * Interval at which progress is printed during rendering.
	 * Determines how frequently rendering progress information is output.
	 */
	private double printInterval;
	/**
	 * Number of threads used for multi-threaded rendering.
	 * Specifies the maximum number of threads that can concurrently render parts of the image.
	 */
	private double threadsCount = 3;

  //----------
  //endregion
    
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
//  public List<Ray> constructRays(int nX, int nY, int j, int i) {
//  List<Ray> rays = new ArrayList<>(ANTI_ALIASING_FACTOR * ANTI_ALIASING_FACTOR);
//  Point pixelCenter = getPixelCenter(nX, nY, j, i);
//  double rY = (height / nY) / ANTI_ALIASING_FACTOR;
//  double rX = (width / nX) / ANTI_ALIASING_FACTOR;
//  double x, y;
//
//  for (int rowNumber = 0; rowNumber < ANTI_ALIASING_FACTOR; rowNumber++) {
//      for (int colNumber = 0; colNumber < ANTI_ALIASING_FACTOR; colNumber++) {
//          y = -(rowNumber - (ANTI_ALIASING_FACTOR - 1d) / 2) * rY;
//          x = (colNumber - (ANTI_ALIASING_FACTOR - 1d) / 2) * rX;
//          Point pIJ = pixelCenter;
//          if (!isZero(y)) pIJ = pIJ.add(vUp.scale(y));
//          if (!isZero(x)) pIJ = pIJ.add(vRight.scale(x));
//          rays.add(new Ray(centerPoint, pIJ.subtract(centerPoint)));
//      }
//  }
//  return rays;
//}
//  /**
//  * Constructs a ray through a pixel from the camera
//  *
//  * @param nX The number of pixels in the x direction
//  * @param nY The number of pixels in the y direction
//  * @param j  The pixel's x coordinate
//  * @param i  The pixel's y coordinate
//  *
//  * @return The ray through the pixel
//  */
// public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
//     Point pc = centerPoint.add(vTo.scale(distance)); // center point of the view plane
//     double pixelWidth = width / nX; // width of a pixel
//     double pixelHeight = height / nY; // height of a pixel
//     double pcX = (nX - 1) / 2.0; // center pixel value in x direction
//     double pcY = (nY - 1) / 2.0; // center pixel value in y direction
//     double rightDistance = (j - pcX) * pixelWidth; // x offset of j from the center pixel
//     double upDistance = -1 * (i - pcY) * pixelHeight; // y offset of i from the center pixel
//
//     Point pij = pc; // start at the center of the view plane
//
//     // we need to check if the distance is zero, because we can't scale a vector by
//     // zero
//     if (!isZero(rightDistance)) {
//         // if the distance to move right is not zero, move right
//         pij = pij.add(vRight.scale(rightDistance));
//     }
//
//     if (!isZero(upDistance)) {
//         // if the distance to move up is not zero, move up
//         pij = pij.add(vUp.scale(upDistance));
//     }
//
//     // construct a ray from the camera origin in the direction of the pixel at (j,i)
//     return new Ray(centerPoint, pij.subtract(centerPoint));
// }
//
//

	/**
	 * Constructs a grid of rays starting from the given top-left point with specified
	 * horizontal and vertical spacing.
	 *
	 * @param topLeftRayPoint The starting point (top-left corner) of the grid of rays.
	 * @param raySpacingHorizontal The horizontal spacing between adjacent rays in the grid.
	 * @param raySpacingVertical The vertical spacing between adjacent rays in the grid.
	 * @param gridSize The number of rows and columns in the grid (gridSize x gridSize).
	 * @return A list containing the constructed rays in row-major order.
	 */
    private List<Ray> constructGridOfRays(Point topLeftRayPoint, double raySpacingHorizontal, double raySpacingVertical,
                                          int gridSize) {
        Ray ray;
        List<Ray> rays = new ArrayList<>(gridSize*gridSize);
        // create the grid of rays
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Point rayPoint = topLeftRayPoint;
                // move the ray down
                if (row > 0) {
                    rayPoint = rayPoint.add(vUp.scale(-row * raySpacingVertical));
                }
                // move the ray right
                if (col > 0) {
                    rayPoint = rayPoint.add(vRight.scale(col * raySpacingHorizontal));
                }
                // create the ray
                ray = new Ray(position, rayPoint.subtract(position));
                // add the ray to the list
                rays.add(ray);
            }
        }
        return rays;
    }
    /**
     * Calculates the color of a pixel using super-sampling
     *
     * @param mainRay     the main ray to trace around
     * @param pixelWidth  the width of the pixel
     * @param pixelHeight the height of the pixel
     * @return color of the pixel
     */
    private Color calcSupersamplingColor(Ray mainRay, double pixelWidth, double pixelHeight) {
        // locate the point of the top left ray to start constructing the grid from
        Point centerOfPixel = mainRay.getPoint(viewPlaneDistance);
        // amount to move to get from one super-sampling ray location to the next
        double raySpacingVertical = pixelHeight / (superSamplingGridSize + 1);
        double raySpacingHorizontal = pixelWidth / (superSamplingGridSize + 1);
        Point topLeftRayPoint = centerOfPixel //
                .add(vRight.scale(-pixelWidth / 2 - raySpacingHorizontal)) //
                .add(vUp.scale(pixelHeight / 2 - raySpacingVertical));
        List<Ray> rays = constructGridOfRays(topLeftRayPoint, raySpacingHorizontal, raySpacingVertical,
                superSamplingGridSize);
        Color result = Color.BLACK;
        for (Ray ray : rays) {
            result = result.add(rayTracer.traceRay(ray));
        }
        // divide the color by the number of rays to get the average color
        int numRays = (int) superSamplingGridSize * superSamplingGridSize;
        return result.reduce(numRays);
    }
    /**
     * Calculates the color of a pixel using adaptive super-sampling
     *
     * @param centerRay   the ray to trace around
     * @param pixelWidth  the width of the pixel
     * @param pixelHeight the height of the pixel
     * @param level       the level of the adaptive super-sampling (if level is 0,
     *                    return)
     * @return color of the pixel
     */
    private Color calcAdaptiveSupersamplingColor(Ray centerRay, double pixelWidth, double pixelHeight, int level) {
        // spacing between rays vertically
        double halfPixelHeight = pixelHeight / 2;
        // spacing between rays horizontally
        double halfPixelWidth = pixelWidth / 2;
        // move 1/4 left and 1/4 up from the center ray
        Point topLeftRayPoint = centerRay.getPoint(viewPlaneDistance) //
                .add(vRight.scale(-(halfPixelWidth / 2))) //
                .add(vUp.scale(halfPixelHeight / 2));
        // gridSize is always 2 since the grid is always a 2x2 grid of rays in
        // adaptive super-sampling
        List<Ray> rays = constructGridOfRays(topLeftRayPoint, halfPixelWidth, halfPixelHeight, 2);
        // get the colors of the rays
        List<Color> colors = rays.stream().map(ray -> rayTracer.traceRay(ray)).toList();
        // if recursion level is 1, return the average color of the rays
        if (level == 1) {
            return colors.get(0).add(colors.get(1), colors.get(2), colors.get(3)).reduce(4);
        }
        // check if the colors are all the similar enough to be considered the same
        if (colors.get(0).similar(colors.get(1)) //
                && colors.get(0).similar(colors.get(2)) //
                && colors.get(0).similar(colors.get(3))) {
            // if they are, return any one of them
            return colors.get(0);
        }
        // otherwise average the colors of the four parts of the pixel
        return calcAdaptiveSupersamplingColor(rays.get(0), halfPixelWidth, halfPixelHeight, level - 1) //
                .add(calcAdaptiveSupersamplingColor(rays.get(1), halfPixelWidth, halfPixelHeight, level - 1), //
                        calcAdaptiveSupersamplingColor(rays.get(2), halfPixelWidth, halfPixelHeight, level - 1), //
                        calcAdaptiveSupersamplingColor(rays.get(3), halfPixelWidth, halfPixelHeight, level - 1)) //
                .reduce(4);
    }
    /**
     * the function that goes through every point in the array and calculate the average color.
     *
     * @param ray the original ray to construct the surrounding beam.
     * @return the average color of the beam.
     */
    private Color averagedBeamColor(Ray ray) {
        // Initializing the averageColor to black and the apertureColor to null.
        Color averageColor = Color.BLACK, apertureColor;
        // The number of points in the aperture.
        int numOfPoints = this.aperturePoints.length;
        // A ray that is used to trace the ray from the aperture point to the focal point.
        Ray apertureRay;
        // Finding the intersection point of the ray and the focal plane.
        Point focalPoint = this.FOCAL_PLANE.findGeoIntersections(ray).get(0).point;
        // A for each loop that goes through every point in the array and calculate the average color.
        for (Point aperturePoint : this.aperturePoints) {
            apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
            apertureColor = rayTracer.traceRay(apertureRay);
            // Adding the color of the ray to the average color.
            averageColor = averageColor.add(apertureColor);
        }
        return averageColor.reduce(numOfPoints);
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
			
			 // calculates the center point of the image
			//camera.centerPoint = camera.position.add(camera.vTo.scale(distance));

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

		
		 /*
	     * set the adaptive flag.
	     *
	     * @param adaptive the adaptive flag to be set
	     * @return the Camera object
	     
	    public Builder setAdaptive(boolean adaptive) {
	        camera.adaptive = adaptive;
	        return this;
	    }*/
		
	    
	    /**
	     * Set multithreading functionality for accelerating the rendering speed.
	     * Initialize the number of threads.
	     * The default value is 0 (no threads).
	     * The recommended value for the multithreading is 3.
	     *
	     * @param threadsCount the threads amount
	     * @return This Camera object
	     */
	    public Builder setMultithreading(int threadsCount) {
	        if (threadsCount < 0)
	            throw new IllegalArgumentException("Threads parameter must be 0 or higher");
	        if (threadsCount != 0)
	            camera.threadsCount = threadsCount;
	        return this;
	    }
	    /**
	     * setter for printInterval
	     *
	     * @param interval The interval between prints in seconds
	     * @return camera
	     */
	    public  Builder setDebugPrint(double interval) {
	    	camera. printInterval = interval;
	    	return this;
	       
	    }
	    /**
	     * setter for whether you want to do multi threading
	     * on image(x set and not zero) or not(x set to zero)
	     *
	     * @param threads number of threads
	     * @return camera
	     */
	    public  Builder setMultithreading(double threads) {
	        camera.multiThreading = threads != 0;
	        camera.threadsCount = threads;
	        return this;
	    }
	    /**
	     * setting the aperture size as the given parameter, and initialize the points array.
	     *
	     * @param size the given parameter.
	     * @return the camera itself for farther initialization.
	     */
	    public  Builder setApertureSize(double size) {
	        camera.apertureSize = size;
	        /////initializing the points of the aperture.
	        if (size != 0) 
	        	camera.initializeAperturePoint();
	        return this;
	    }
	    /**
	     * Sets the focal distance of the camera and updates the corresponding focal plane.
	     * 
	     * <p>
	     * This method updates the focal distance of the camera, which determines the distance
	     * from the camera where objects will appear sharply in focus. It also recalculates 
	     * the focal plane based on the new focal distance and camera orientation.
	     * </p>
	     *
	     * @param focalDistance The new focal distance to set for the camera.
	     * @return The Builder instance for method chaining.
	     */
	    public  Builder setFocalDistance(double focalDistance) {
	    	camera.focalDistance = focalDistance;
	    	camera.FOCAL_PLANE = new Plane(camera.position.add(camera.vTo.scale(camera.focalDistance)), camera.vTo);
	        return this;
	    }
	    /**
	     * Moves camera to certain location and points to a single point
	     *
	     * @param location the camera's new location
	     * @param to       the point the camera points to
	     * @return Returns moved camera
	     */
	    public  Builder moveCamera(Point location, Point to) {
	        Vector vec;
	        try {
	            vec = to.subtract(location);
	        } catch (IllegalArgumentException ignore) {
	            throw new IllegalArgumentException("The camera cannot point at its starting location");
	        }
	        camera.position = location;
	        camera.vTo = vec.normalize();
	        //in order to determine Vup, we will find the intersection vector of two planes, the plane that Vto is represented
	        //as its normal, and the plane that includes the Y axis and the Vto vector (as demanded in the instructions).

	        //if the Vto is already on the Y axis, we will use the Z axis instead
	        if (camera.vTo.equals(new Vector(0,1,0)) ||camera.vTo.equals(new Vector(0,1,0).scale(-1))) {
	        	camera.vUp = (camera.vTo.crossProduct(new Vector(0,0,1))).crossProduct(camera.vTo).normalize();
	        } else {
	        	camera.vUp = (camera.vTo.crossProduct(new Vector(0,1,0))).crossProduct(camera.vTo).normalize();
	        }
	        camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
	        return this;
	    }
	    /**
	     * Set the super-sampling to NONE, REGULAR or ADAPTIVE
	     *
	     * @param type SUPER_SAMPLING_TYPE type of the super-sampling (NONE, REGULAR, ADAPTIVE)
	     * @return the current camera
	     */
	    public  Builder setSuperSampling(SUPER_SAMPLING_TYPE type) {
	        camera.superSamplingType = type;
	        return this;
	    }
	    /**
	     * Set the grid size of the super-sampling
	     *
	     * @param gridSize grid size of the super-sampling (e.g. 9 for 9x9 grid)
	     * @return the current camera
	     */
	    public Builder setSuperSamplingGridSize(int gridSize) {
	        camera.superSamplingGridSize = gridSize;
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
			
			/*
			 // Validate the values of the fields
			if (alignZero(camera.threadsCount) < 0)
				throw new IllegalStateException("threadsCount must be positive");
			if (alignZero(camera.superSampling) < 0)
				throw new IllegalStateException("superSampling must be positive");
			if (alignZero(camera.viewPlaneDistance) <= 0)
				throw new IllegalStateException("Distance must be positive");
			  */
			

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
	 * Renders the final image using ray tracing technique based on the camera's settings.
	 * Throws exceptions if essential resources like camera coordinates or image creation details are not initialized.
	 *
	 * <p>
	 * This method performs ray tracing to generate an image based on the camera's configuration and view plane settings.
	 * If multithreading is enabled, it distributes the rendering task across multiple threads to improve performance.
	 * Each pixel in the image corresponds to a ray cast into the scene to determine its color based on scene objects
	 * and lighting conditions.
	 * </p>
	 *
	 * @return The Camera object instance after rendering the image, allowing method chaining.
	 * @throws MissingResourceException If camera coordinates (position, vRight, vUp, vTo) or
	 *         image creation details (imageWriter or rayTracer) are not initialized.
	 */
	//שלב 8
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("Image creation details are not initialized", "Camera", "Writer info");
        int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

  for (int i = 0; i < nX; i+=interval) { // row
            for (int j = 0; j < nY; j+=interval) { // column
                //grid: 800/50 = 16, 500/50 = 10
                imageWriter.writePixel(i, j, color);
            }
        }
        return this;
    }
    /**
     * Creates image - sends to method inside ImageWriter class
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Image creation details are not initialized", "Camera", "Writer info");
        //call image writer
        imageWriter.writeToImage();
    }
	// stage5
	/**
	 * Writes the image to a file using the appropriate method of the image writer.
	 */

	  /**
     * Renders an image
     *///  להחזיר לקודם?שלב 8
    public Camera renderImage() {
        //info: coordinates of the camera != null
        if ((vRight == null) || (vUp == null) || (vTo == null) || (position == null))
            throw new MissingResourceException("Camera coordinates are not initialized", "Camera", "coordinates");
        //info: view plane variables != null (doubles != null)
        //info: final image creation
        if ((imageWriter == null) || (rayTracer == null))
            throw new MissingResourceException("Image creation details are not initialized", "Camera", "Writer info");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        if (multiThreading){
            Pixel.initialize(nY, nX, printInterval);
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                        castRay(nX, nY, pixel.col, pixel.row);
                    }
                }).start();
            }
            Pixel.waitToFinish();
        }
        else {
            Pixel.initialize(nY, nX, printInterval);
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j) {
                    castRay(nX, nY, j, i);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                }
        }
        return this;
    }

	  /**
     * Constructs a ray through a pixel from the camera and write its color to the
     * image
     *
     * @param numColumns The number of pixels in the x direction
     * @param numRows    The number of pixels in the y direction
     * @param col        The pixel's x coordinate
     * @param row        The pixel's y coordinate
     *///שלב 8
    public void castRay(int numColumns, int numRows, int col, int row) {
        Color color;
        // height and width of the pixel
        double pixelWidth = viewPlaneWidth / numColumns;
        double pixelHeight = viewPlaneHeight / numRows;
        Ray ray = constructRay(numColumns, numRows, col, row);
        if (superSamplingType == SUPER_SAMPLING_TYPE.ADAPTIVE) {
            color = calcAdaptiveSupersamplingColor(ray, pixelWidth, pixelHeight,
                    adaptiveSuperSamplingMaxRecursionDepth);
        } else if (superSamplingType == SUPER_SAMPLING_TYPE.REGULAR) {
            color = calcSupersamplingColor(ray, pixelWidth, pixelHeight);
        } else {
            color = rayTracer.traceRay(ray);
        }
        if (!isZero(this.apertureSize)) {
            color = color.add(averagedBeamColor(ray));
            color = color.reduce(2);
        }
        imageWriter.writePixel(col, row, color);
    }
//  /**
//  * set anti-aliasing factor.
//  * final number of rays will be (factor * factor)
//  *
//  * @param antiAliasingFactor num of rays for anti aliasing
//  * @return this Camera
//  */
// public Camera setAntiAliasingFactor(int antiAliasingFactor) {
//     if (antiAliasingFactor <= 0)
//         throw new IllegalArgumentException("anti aliasing factor must be positive integer");
//     ANTI_ALIASING_FACTOR = antiAliasingFactor;
//     return this;
// }

 
 /**
  * Set the max recursion depth for the adaptive super-sampling
  *
  * @param maxRecursionDepth max recursion depth for the adaptive super-sampling
  * @return the current camera
  */
 public Camera setAdaptiveSuperSamplingMaxRecursionDepth(int maxRecursionDepth) {
     this.adaptiveSuperSamplingMaxRecursionDepth = maxRecursionDepth;
     return this;
 }
 //    public Camera setDepthOfField(boolean flag) {
//     depthOfFieldFlag = flag;
//     return this;
// }
//
// public Camera setDepthOfField(boolean flag, double focalDistance, double apertureSize) {
//     this.depthOfFieldFlag = flag;
//     this.focalDistance = focalDistance;
//     this.apertureSize = apertureSize;
//     return this;
// }
// public Camera setFocalLength(double focalLength) {
//     this.focalLength = focalLength;
//     return this;
// }
//
// public Camera setApertureSize(double apertureSize) {
//     this.apertureSize = apertureSize;
//     return this;
// }
// public void setDepthButton(boolean button, double apertureSize, double focalLength) {
//     this.depthButton = button;
//     this.apertureSize = apertureSize;
//     this.focalLength = focalLength;
// }
// /***
//  * on/off button for depth of field
//  * @param depthButton on/off
//  */
// public void setDepthButton(boolean depthButton) {
//     this.depthButton = depthButton;
// }
 
 
 /**
  * the function that initialize the aperture size and the points that it represents.
  */
 private void initializeAperturePoint() {
     //the number of points in a row
     int pointsInRow = (int) Math.sqrt(this.APERTURE_NUMBER_OF_POINTS);
     //the array of point saved as an array
     this.aperturePoints = new Point[pointsInRow * pointsInRow];
     //calculating the initial values.
     double pointsDistance = (this.apertureSize * 2) / pointsInRow;
     //calculate the initial point to be the point with coordinates outside the aperture in the down left point,
     // so we won`t have to deal with illegal vectors.
     Point initialPoint = this.position
             .add(this.vUp.scale(-this.apertureSize - pointsDistance / 2)
             .add(this.vRight.scale(-this.apertureSize - pointsDistance / 2)));
     //initializing the points array
     for (int i = 1; i <= pointsInRow; i++) {
         for (int j = 1; j <= pointsInRow; j++) {
             this.aperturePoints[(i - 1) + (j - 1) * pointsInRow] = initialPoint
                     .add(this.vUp.scale(i * pointsDistance).add(this.vRight.scale(j * pointsDistance)));
         }
     }
 }

 
 

}