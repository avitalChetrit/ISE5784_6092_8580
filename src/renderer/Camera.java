package renderer;
//import geometries.Plane;
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
	/**
	 * The factor that determines the level of anti-aliasing applied, where a value of 1 indicates no anti-aliasing
	 * and higher values increase the anti-aliasing effect, which can enhance visual quality but may impact performance.
	 */
	int antiAliasingFactor = 1;
	/**
	 * The maximum level of adaptive anti-aliasing.
	 * <p>
	 * Defines the highest anti-aliasing intensity used when adaptive anti-aliasing is enabled.
	 * </p>
	 */
    private int maxAdaptiveLevel = 3;

	/**
	 * Indicates whether adaptive anti-aliasing is enabled; if set to {@code true}, the system will dynamically
	 * adjust the anti-aliasing level based on scene complexity, while {@code false} will apply a fixed anti-aliasing
	 * level as defined by {@link #antiAliasingFactor}.
	 */
	private boolean useAdaptive = false;


	private double printInterval;
	/**
	 * Number of threads used for multi-threaded rendering.
	 * Specifies the maximum number of threads that can concurrently render parts of the image.
	 */
	private double threadsCount = 0;

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
	 /**
     * Calculate a beam of rays
     * @param centerRay         the central ray to build beam around
     * @param vup               vector for target area plane
     * @param vright            vector for target area plane
     * @param targetAreaDis     distance of target area from centerRay's origin
     * @param targetAreaSide    length of target area side. target area full size = side*side
     * @param targetAreaRes     resolution of target area, number of rays on one side. total num of rays = res*res
     * @return list of rays
     */
    public List<Ray> constructRaysBeam(Ray centerRay, Vector vup, Vector vright, double targetAreaDis, double targetAreaSide, int targetAreaRes){
        //List<Point> points = Sampling.constructTargetAreaGrid(centerRay,vup,vright, targetAreaDis, targetAreaSide, targetAreaRes, k)
        List<Ray> rays = new ArrayList<>(targetAreaRes*targetAreaRes);
        Point origin = centerRay.getHead();
        Point centerTarget = centerRay.getPoint(targetAreaDis);
        double spacing = targetAreaSide / (targetAreaRes - 1);
        double scaleUp, scaleRight;
        Point destinationPoint;
        for (int i = 0; i < targetAreaRes; i++) {
            for (int j = 0; j < targetAreaRes; j++) {
                scaleUp = (-i + (targetAreaRes - 1d) / 2) * spacing;
                scaleRight = (j - (targetAreaRes - 1d) / 2) * spacing;
                destinationPoint = centerTarget;
                if (scaleUp != 0)
                    destinationPoint = destinationPoint.add(vup.scale(scaleUp));
                if (scaleRight != 0)
                    destinationPoint = destinationPoint.add(vright.scale(scaleRight));
                rays.add(new Ray(origin, destinationPoint.subtract(origin)));
            }
        }
        return rays;
    }

    /**
     * Calculate color of pixel, using adaptive antialiasing
     *
     * @param centerRay      the central ray to build beam around
     * @param targetAreaSide length of target area side. target area full size = side*side
     * @param level          level of recursion
     * @return color of pixel
     */
    private Color adaptiveHelper(Ray centerRay, Vector vup, Vector vright, double targetAreaDis, double targetAreaSide, int level) {
        // list of colors of four corners
        List<Ray> rays = new ArrayList<>(
                constructRaysBeam(centerRay, vup, vright, targetAreaDis, targetAreaSide / 2, 2));
        List<Color> colors = rays.stream().map(ray -> rayTracer.traceRay(ray)).toList();
        //if finished recursion, return average of colors
        if (level == 1)
            return colors.get(0).add(colors.get(1), colors.get(2), colors.get(3))
                    .reduce(4);
        //If the four colors are similar to each other, return one
        if (colors.get(0).similar(colors.get(1))
                && colors.get(0).similar(colors.get(2))
                && colors.get(0).similar(colors.get(3)))
            return colors.get(0);
        //else, call recursion
        Point origin = centerRay.getHead();
        Point p = centerRay.getPoint(viewPlaneDistance);
        Point[] points = {
                p.add(vup.scale(0.25 * targetAreaSide)).add(vright.scale(-0.25 * targetAreaSide)),
                p.add(vup.scale(0.25 * targetAreaSide)).add(vright.scale(0.25 * targetAreaSide)),
                p.add(vup.scale(-0.25 * targetAreaSide)).add(vright.scale(-0.25 * targetAreaSide)),
                p.add(vup.scale(-0.25 * targetAreaSide)).add(vright.scale(0.25 * targetAreaSide))};
        return adaptiveHelper(new Ray(origin, points[0].subtract(origin)), vup, vright, targetAreaDis, targetAreaSide / 2, level - 1).add(
                        adaptiveHelper(new Ray(origin, points[1].subtract(origin)), vup, vright, targetAreaDis, targetAreaSide / 2, level - 1),
                        adaptiveHelper(new Ray(origin, points[2].subtract(origin)), vup, vright, targetAreaDis, targetAreaSide / 2, level - 1),
                        adaptiveHelper(new Ray(origin, points[3].subtract(origin)), vup, vright, targetAreaDis, targetAreaSide / 2, level - 1))
                .reduce(4);
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
	    	if (threads < 0)
	            throw new IllegalArgumentException("threads count must be non-negative");
	        camera.threadsCount = threads;
	        return this;
	    }
	    /**
	     * setting the aperture size as the given parameter, and initialize the points array.
	     *
	     * @param size the given parameter.
	     * @return the camera itself for farther initialization.
	     */
	    
	    /**
	     * function that sets the antiAliasingFactor
	     * @param antiAliasingFactor value to set
	     * @return camera itself
	     */
	    public Builder setAntiAliasingFactor(int antiAliasingFactor) {
	    	camera.antiAliasingFactor = antiAliasingFactor;
	        return this;
	    }
	    
	    /**
	     * setter for UseAdaptive
	     *
	     * @param useAdaptive- the number of pixels in row/col of every pixel
	     * @return camera itself
	     */
	    public Builder setUseAdaptive(boolean useAdaptive) {
	        camera.useAdaptive = useAdaptive;
	        return this;
	    }
	/*    public  Builder setApertureSize(double size) {
	        camera.apertureSize = size;
	        /////initializing the points of the aperture.
	        if (size != 0) 
	        	camera.initializeAperturePoint();
	        return this;
	    }
	    */
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
	  /*  public  Builder setFocalDistance(double focalDistance) {
	    	camera.focalDistance = focalDistance;
	    	camera.FOCAL_PLANE = new Plane(camera.position.add(camera.vTo.scale(camera.focalDistance)), camera.vTo);
	        return this;
	    }
	    */
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
	    /*public  Builder setSuperSampling(SUPER_SAMPLING_TYPE type) {
	        camera.superSamplingType = type;
	        return this;
	    }
	    */
	    /**
	     * Set the grid size of the super-sampling
	     *
	     * @param gridSize grid size of the super-sampling (e.g. 9 for 9x9 grid)
	     * @return the current camera
	     */
	   /* public Builder setSuperSamplingGridSize(int gridSize) {
	        camera.superSamplingGridSize = gridSize;
	        return this;
	    }
	    */

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
    
    public Camera renderImage() {
        if (this.imageWriter == null)
            throw new UnsupportedOperationException("Missing imageWriter");
        if (this.rayTracer == null)
            throw new UnsupportedOperationException("Missing rayTracerBase");
    		int nX = imageWriter.getNx();
    		int nY = imageWriter.getNy();
    		for (int i = 0; i < nX; ++i)
    			for (int j = 0; j < nY; ++j) {
    				 Color color = castRay(nX,nY,i,j);
    	                this.imageWriter.writePixel(i, j, color);
    			}
    		return this;

    	}
 
    private Color castRay(int nX, int nY, int i, int j) {
      Color color;
      Ray ray = constructRay(nX, nY, i, j);

      if (useAdaptive)
          color = adaptiveHelper(ray, vUp, vRight, viewPlaneDistance, viewPlaneHeight / nY, maxAdaptiveLevel);
      else if (antiAliasingFactor == 1)
          color = rayTracer.traceRay(ray);
      else
    	  color = rayTracer.traceRays(  constructRaysBeam(ray, vUp, vRight, viewPlaneDistance, viewPlaneHeight / nY, antiAliasingFactor));
      imageWriter.writePixel(i, j, color);
      return color;
  }
   
    /**
     * setter for maxAdaptiveLevel
     *
     * @param maxAdaptiveLevel- The depth of the recursion
     * @return camera itself
     */
    public Camera setMaxAdaptiveLevel(int maxAdaptiveLevel) {
        this.maxAdaptiveLevel = maxAdaptiveLevel;
        return this;
    }
}