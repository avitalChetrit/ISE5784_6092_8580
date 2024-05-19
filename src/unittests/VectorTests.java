/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;


import org.junit.jupiter.api.Test;

/**
 * 
 */
class VectorTests {
	 Vector v1         = new Vector(1, 2, 3);
     Vector v1Opposite = new Vector(-1, -2, -3);
     Vector v2         = new Vector(-2, -4, -6);
     Vector v3         = new Vector(0, 3, -2);
     Vector v4         = new Vector(1, 2, 2);

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============ Partitions Tests ==============
	}

	/**
	 * Test method for {@link primitives.Vector#scale(int)}.
	 */
	@Test
	void testScale() {
		// ============ Partitions Tests ==============
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Partitions Tests ==============
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		
		 // ============ Equivalence Partitions Tests ==============
		 Vector vr = v1.crossProduct(v3);
		 // TC01: Test that length of cross-product is proper (orthogonal vectors taken
		 // for simplicity)
		 assertEquals(v1.length() * v3.length(), vr.length(), "crossProduct() wrong result length");
		 // TC02: Test cross-product result orthogonality to its operands
		 assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
		 assertEquals(0, vr.dotProduct(v3), "crossProduct() result is not orthogonal to 2nd operand");
		 // =============== Boundary Values Tests ==================
		 // TC11: test zero vector from cross-product of parallel vectors
		 assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), //
		 "crossProduct() for parallel vectors does not throw an exception");
		 }

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Partitions Tests ==============
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Partitions Tests ==============
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Partitions Tests ==============
	}

}
