package primitives;
/**
 * Vector class represents a vector in 3D space
 */
public class Vector extends Point {
	/**
     * Constructor that accepts three double values representing the components of the vector
     * @param x X component value
     * @param y Y component value
     * @param z Z component value
     * @throws IllegalArgumentException In case of zero vector
     */
	public Vector(Double3 xyz1) {
		super(xyz1);
		if (xyz1.equals(xyz1.ZERO)) {//?
            throw new IllegalArgumentException("Zero vector is not allowed");
        }	}
	/**
     * Constructor that accepts a Double3 object representing the components of the vector
     * @param coords Double3 object representing the components
     * @throws IllegalArgumentException In case of zero vector
     */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (x==0&&y==0&&z==0) {
            throw new IllegalArgumentException("Zero vector is not allowed");
        }		}
	/**
     * Add operation between two vectors, returning a new vector
     * @param other The other vector to add
     * @return New vector after adding 'other' vector
     * @throws IllegalArgumentException In case of zero vector
     */
public Vector add(Vector v) {return new Vector(this.xyz.d1+v.xyz.d1,this.xyz.d2+v.xyz.d2,this.xyz.d3+v.xyz.d3);}
/**
 * Scale the vector by a scalar value, returning a new vector
 * @param scalar The scalar value to scale the vector by
 * @return New scaled vector
 * @throws IllegalArgumentException In case of zero vector
 */
public Vector scale(int num) {return new Vector (num*this.xyz.d1,num*this.xyz.d2,num*this.xyz.d3);}
/**
 * Calculate the dot product between two vectors
 * @param other The other vector
 * @return The dot product between the two vectors
 */
public double dotProduct(Vector other) {
    return xyz.product(other.xyz);
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
       - xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3,
        xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1
    );
    if (result.equals(xyz.ZERO)) {//?
        throw new IllegalArgumentException("Resulting vector is zero vector");
    }
    return result;
}
/**
 * Calculate the squared length of the vector
 * @return Squared length of the vector
 */
public double lengthSquared() {
    return xyz.product(xyz);
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
    double len = length();
    if (len == 0) {
        throw new IllegalArgumentException("Cannot normalize zero vector");
    }
    return new Vector(xyz.scale(1 / len));
    }
}