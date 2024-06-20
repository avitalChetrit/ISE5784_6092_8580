package scene;
import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;
import lighting.Light;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;

/**
 * A class representing a scene.
 */
public class Scene {
    /** The name of the scene */
    public String name;

    /** The background color of the scene */
    public Color background;

    /** The ambient light of the scene */
    public AmbientLight ambientLight;

    /** The geometries in the scene */
    public Geometries geometries;

    /** The lights in the scene */
    public List<LightSource> lights;

    /**
     * Constructs a Scene object with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK; // Background color initialized to black
        this.ambientLight = null; // Ambient light initialized to null
        this.geometries = new Geometries(); // Initialize geometries to an empty model
        this.lights = new LinkedList<>(); // Initialize lights to an empty linked list
    }

    /**
     * Updates the background color of the scene.
     *
     * @param background The new background color.
     * @return This Scene object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Updates the ambient light of the scene.
     *
     * @param ambientLight The new ambient light.
     * @return This Scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Updates the geometries in the scene.
     *
     * @param geometries The new geometries.
     * @return This Scene object.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Updates the lights in the scene.
     *
     * @param lights The new list of lights.
     * @return This Scene object.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
