package lighting;
import primitives.Color;
import primitives.Double3;

/**
 * A class representing Ambient Light.
 */
public class AmbientLight {
    /** The intensity of the ambient light */
    private final Color intensity;

    /** Constant representing no ambient light */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    /**
     * Constructs an AmbientLight object with the given intensity and scale factor.
     * @param intensity The original color of the ambient light.
     * @param scale The scaling factor for the ambient light intensity.
     */
    public AmbientLight(Color intensity, Double3 scale) {
        this.intensity = intensity.scale(scale);
    }

    /**
     * Constructs an AmbientLight object with the given intensity and scale factor.
     * @param intensity The original color of the ambient light.
     * @param scale The scaling factor for the ambient light intensity.
     */
    public AmbientLight(Color intensity, double scale) {
        this.intensity = intensity.scale(scale);
    }

    /**
     * Gets the intensity of the ambient light.
     * @return The color representing the intensity of the ambient light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
