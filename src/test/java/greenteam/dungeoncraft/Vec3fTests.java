package greenteam.dungeoncraft;

import static org.junit.Assert.*;

import org.junit.Test;

import greenteam.dungeoncraft.Engine.Math.Vec3f;

public class Vec3fTests {

	@Test /* vector normalization test */
	public void vectorNormalizeTest() {
		// x,y,z
		Vec3f testVector = new Vec3f(1, 2, 3);

		testVector.normalize();

		assertEquals(0.26726124191f, testVector.getX(), 0.0001f);
		assertEquals(0.53452248382f, testVector.getY(), 0.0001f);
		assertEquals(0.80178372573, testVector.getZ(), 0.0001f);
	}

}
