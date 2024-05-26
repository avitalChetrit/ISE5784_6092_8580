package primitives;

/**
 * TODO
 * 
 * @author Avital and Tal
 */
public class Ray {
	/** point in ray */
	private final Point head;
	/** vector direction */
	private final Vector direction;

	/**
	 * ray constructor
	 * 
	 * @param point  in ray
	 * @param vector in ray
	 */
	public Ray(Point point, Vector vector) {
		head = point;
		direction = vector.normalize(); // Ensure vector is normalized
	}

	public Point getHead() {
		return head;
	}

	public Vector getDirection() {
		return direction;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	@Override
	public String toString() {
		return "Ray:" + head + "->" + direction;
	}

}