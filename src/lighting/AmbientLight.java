package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * A class representing Ambient Light.
 */
public class AmbientLight extends Light {

    public static final AmbientLight NONE=new AmbientLight(new Color(java.awt.Color.BLACK),Double3.ZERO);
	/**
     * Constructs an AmbientLight object with the given intensity and scale factor.
     * 
     * @param intensity The original color of the ambient light.
     * @param scale     The scaling factor for the ambient light intensity.
     */
    public AmbientLight(Color intensity, Double3 KA) {
        super(intensity.scale(KA));
    }

    /**
     * Constructs an AmbientLight object with the given intensity and scale factor.
     * 
     * @param intensity The original color of the ambient light.
     * @param scale     The scaling factor for the ambient light intensity.
     */
    public AmbientLight(Color intensity, double KA) {
        super(intensity.scale(KA));
    }
}
