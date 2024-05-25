package primitives;
import geometries.Tube;
import java.util.Objects;
import primitives.Ray;

/**
 * Vector class represents a vector in 3D space
 */
public class Vector extends Point {
	/**
     * Constructor that accepts three double values representing the components of the vector
     * @param xyz1 component value
     * @throws IllegalArgumentException In case of zero vector
     */
	public Vector(Double3 xyz1) {
		super(xyz1);
		if (xyz1.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Zero vector is not allowed");
        }	}
	/**
     * Constructor that accepts a Double3 object representing the components of the vector
     * @param x Double3 object representing the components
     * @param y Double3 object representing the components
     * @param z Double3 object representing the components
     * @throws IllegalArgumentException In case of zero vector
     */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Zero vector is not allowed");
        }		}
	/**
     * Add operation between two vectors, returning a new vector
     * @param v The other vector to add
     * @return New vector after adding 'other' vector
     * @throws IllegalArgumentException In case of zero vector
     */
public Vector add(Vector v) {
	return new Vector(xyz.add(v.xyz));
}
/**
 * Scale the vector by a scalar value, returning a new vector
 * @param num The scalar value to scale the vector by
 * @return New scaled vector
 * @throws IllegalArgumentException In case of zero vector
 */
public Vector scale(double num) {
	return new Vector (this.xyz.scale(num));
}
/**
 * Calculate the dot product between two vectors
 * @param other The other vector
 * @return The dot product between the two vectors
 */
public double dotProduct(Vector other) {
	return this.xyz.d1*other.xyz.d1 +this.xyz.d2*other.xyz.d2 + this.xyz.d3*other.xyz.d3;
}

/**
 * Calculate the cross product between two vectors, returning a new vector
 * @param other The other vector
 * @return New vector which is the cross product of 'this' and 'other' vectors
 * @throws IllegalArgumentException In case of zero vector
 */
public Vector crossProduct(Vector other) {
    Vector result = new Vector(
        xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2,
        xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3,
        xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1
    );
    if (result.equals(Double3.ZERO)) {
        throw new IllegalArgumentException("Resulting vector is zero vector");
    }
    return result;
}
/**
 * Calculate the squared length of the vector
 * @return Squared length of the vector
 */

public double lengthSquared() {
    return this.dotProduct(this);
}


/**
 * Calculate the length of the vector
 * @return Length of the vector
 */
public double length() {
    return Math.sqrt(lengthSquared());
}
/**
 * Normalize the vector (returning a new normalized vector)
 * @return Normalized vector
 * @throws IllegalArgumentException In case of zero vector
 */
public Vector normalize() {
    double len = this.length();
    if (len == 0) {
        throw new IllegalArgumentException("Cannot normalize zero vector");
    }
    return new Vector(xyz.reduce(len));
  }

//Override equals method
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    return (obj instanceof Vector other)
    		&& this.xyz.equals(other.xyz);
    }


// Override hashCode method
@Override
public int hashCode() {
    return Objects.hashCode(xyz);
}

@Override
public String toString() {
    return "Vector: (" + xyz.d1 + ", " + xyz.d2 + ", " + xyz.d3 + ")";
} 


	
 

}
