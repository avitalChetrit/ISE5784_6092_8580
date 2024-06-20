package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A class representing a directional light source.
 * Inherits from Light and implements LightSource.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a directional light with the given intensity and direction.
     *
     * @param intensity  The intensity (color) of the light source.
     * @param direction  The direction vector of the light source.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction; // Directional light shines from infinity towards the scene
    }
}

