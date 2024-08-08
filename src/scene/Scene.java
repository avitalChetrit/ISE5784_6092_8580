package scene;

import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;
import lighting.LightSource;

import java.util.LinkedList;
import java.util.List;
import geometries.Polygon;
import lighting.AmbientLight;
import lighting.*;
import lighting.LightSource;
import primitives.Color;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import java.awt.*;
import java.util.HashMap;
/**
 * A class representing a scene.
 */
public class Scene {
	/** The name of the scene */
	public final String name;

	/** The background color of the scene */
	public Color background = Color.BLACK;;

	/** The ambient light of the scene */
	public AmbientLight ambientLight = AmbientLight.NONE;

	/** The geometries in the scene */
	public Geometries geometries = new Geometries();

	/** The lights in the scene */
	public List<LightSource> lights = new LinkedList<>();

	
	
    public Polygon[] faces;
    //scene geometric attributes-stage9
    /**
     * the size of the edge of the scene boundary on the X axis
     */
    private int xEdgeScene;
    /**
     * the size of the edge of the scene boundary on the Y axis
     */
    private int yEdgeScene;
    /**
     * the size of the edge of the scene boundary on the Z axis
     */
    private int zEdgeScene;
    /**
     * the resolution of the voxels that divide the scene
     */
    public double resolution = 10;
    /**
     * array of resolution to each axis
     */
    public double[] resolutions;
    //voxel attributes
    /**
     * hash map of all voxels in the scene - index as the key & list of geometric entities that intersects with the voxel as the value.
     */
    public HashMap<Double3, Geometries> voxels = new HashMap<Double3, Geometries>();
    /**
     * the size of the edge of the voxel on the X axis
     */
    private double xEdgeVoxel;
    /**
     * the size of the edge of the voxel on the Y axis
     */
    private double yEdgeVoxel;
    /**
     * the size of the edge of the voxel on the Z axis
     */
    private double zEdgeVoxel;
	
	
	
	
	
	/**
	 * Constructs a Scene object with the given name.
	 *
	 * @param name The name of the scene.
	 */
	public Scene(String name) {
		this.name = name;
	}

	   //region getters - voxels-stage9
    /**
     * xEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the X axis
     */
    public double getXEdgeVoxel() {
        return xEdgeVoxel;
    }
    /**
     * yEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the Y axis
     */
    public double getYEdgeVoxel() {
        return yEdgeVoxel;
    }
    /**
     * zEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the Z axis
     */
    public double getZEdgeVoxel() {
        return zEdgeVoxel;
    }
    //endregion
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

    /**
     * sets the resolution of the voxel grid from the tests
     *
     * @param resolution teh resolution of the grid
     * @return the object itself
     */
    public Scene setResolution(int resolution) {
        this.resolution = resolution;
        return this;
    }
    /**
     * calculates what voxels the scene has and the attributes of the voxels
     */
    public void calcVoxels() {
        this.setBoundary(); //V
        this.setSceneEdges(); //V
        this.setResolution(); //V
        this.setVoxelsEdges(); //V
        this.setVoxelsGeometries(); //@TODO: FIX
        this.setFaces(); //V
    }
    /**
     * sets the boundary of the geometries in the scene
     */
    private void setBoundary() {
        this.geometries.boundary = this.geometries.calcBoundary();
    }
    /**
     * sets the edges of the scene
     */
    private void setSceneEdges() {
        this.xEdgeScene = this.geometries.boundary[0][1] - this.geometries.boundary[0][0];
        this.yEdgeScene = this.geometries.boundary[1][1] - this.geometries.boundary[1][0];
        this.zEdgeScene = this.geometries.boundary[2][1] - this.geometries.boundary[2][0];
    }
    /**
     * sets the resolution of the scene to divide to voxels
     */
    private void setResolution() {
        resolutions = new double[]{resolution, resolution, resolution};
    }
    /**
     * sets the attributes of the voxels
     */
    private void setVoxelsEdges() {
        this.xEdgeVoxel = ((double) this.xEdgeScene) / resolution;
        this.yEdgeVoxel = ((double) this.yEdgeScene) / resolution;
        this.zEdgeVoxel = ((double) this.zEdgeScene) / resolution;
    }
    /**
     * attaches the voxels to each geometric entity in the scene
     */
    private void setVoxelsGeometries() {
        this.voxels = this.geometries.attachVoxel(this);
    }
    /**
     * sets the regular grid faces field
     */
    private void setFaces(){
        //points of the scene regular grid
        int[][] gridBoundary = geometries.getBoundary();
        primitives.Point p1 = new primitives.Point(gridBoundary[0][0],gridBoundary[1][0], gridBoundary[2][0]);//(0,0,0)
        primitives.Point p2 = new primitives.Point(gridBoundary[0][1], gridBoundary[1][0], gridBoundary[2][0]);//(1,0,0)
        primitives.Point p3 = new primitives.Point(gridBoundary[0][0], gridBoundary[1][1], gridBoundary[2][0]);//(0,1,0)
        primitives.Point p4 = new primitives.Point(gridBoundary[0][0], gridBoundary[1][0], gridBoundary[2][1]);//(0,0,1)
        primitives.Point p5 = new primitives.Point(gridBoundary[0][1], gridBoundary[1][1], gridBoundary[2][0]);//(1,1,0)
        primitives.Point p6 = new primitives.Point(gridBoundary[0][1], gridBoundary[1][0], gridBoundary[2][1]);//(1,0,1)
        primitives.Point p7 = new primitives.Point(gridBoundary[0][0], gridBoundary[1][1], gridBoundary[2][1]);//(0,1,1)
        primitives.Point p8 = new Point(gridBoundary[0][1], gridBoundary[1][1], gridBoundary[2][1]);//(1,1,1)
        //faces of the regular grid
        geometries.Polygon bottom = new geometries.Polygon(p1, p2, p5, p3);//bottom
        geometries.Polygon front = new geometries.Polygon(p1, p2, p6, p4);//front
        geometries.Polygon left = new geometries.Polygon(p1, p3, p7, p4);//left
        geometries.Polygon up = new geometries.Polygon(p4, p6, p8, p7);//up
        geometries.Polygon behind = new geometries.Polygon(p3, p5, p8, p7);//behind
        geometries.Polygon right = new geometries.Polygon(p2, p5, p8, p6);//right
        //Memory wise, we won't use the ray function of findClosestPoint, since we will be in need to create a list of points
        this.faces = new Polygon[]{bottom, front, left, up, behind, right};
    }
}
