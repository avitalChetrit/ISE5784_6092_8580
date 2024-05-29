package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Geometries class to represent a collection of geometric shapes
 * Implements the Composite design pattern
 */
public class Geometries implements Intersectable {
    // List to hold the geometric shapes
    private final List<Intersectable> geometries = new LinkedList<>();

    // Default constructor
    public Geometries() {
    }

    // Constructor with geometries
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    // Method to add geometries to the list
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        List<Point> intersections = null;

        for (Intersectable geometry : geometries) {
            List<Point> geometryIntersections = geometry.findIntsersections(ray);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }

        return intersections;
    }

}

