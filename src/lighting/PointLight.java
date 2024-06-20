package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A class representing a point light source.
 * Inherits from Light and implements LightSource.
 */
public class PointLight extends Light implements LightSource {

    private Point position;
    private double kC = 1; // Constant attenuation
    private double kL = 0; // Linear attenuation
    private double kQ = 0; // Quadratic attenuation

    /**
     * Constructs a point light with the given intensity and position.
     *
     * @param intensity  The intensity (color) of the light source.
     * @param position   The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
   
    
   public PointLight setPosition(Point position) {
       this.position = position;
       return this;
   }


    /**
     * Getter for the constant attenuation coefficient (kC).
     *
     * @return This PointLight object.
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Getter for the linear attenuation coefficient (kL).
     *
     * @return This PointLight object.
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Getter for the quadratic attenuation coefficient (kQ).
     *
     * @return This PointLight object.
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

  
    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1d / (kC + kL * d + kQ * d * d));
    }
 
    
    @Override
    public Vector getL(Point p) {
        // if the point is the same as the light source, return null
        if (p.equals(position))
            return null;
        // otherwise, return the normalized vector from the light source to the point
        return p.subtract(position).normalize();
    }
   
}
