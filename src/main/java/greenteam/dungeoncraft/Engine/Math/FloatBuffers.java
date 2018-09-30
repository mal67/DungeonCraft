package greenteam.dungeoncraft.Engine.Math;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class FloatBuffers {
	
	/* create a float buffer from a float array that for openGL to read */
	public FloatBuffer convertToFlippedBufferFromVector(float[] verts) {
		assert(verts.length > 0);//change to try and catch?
		FloatBuffer vertsBuffer = BufferUtils.createFloatBuffer(verts.length);
		vertsBuffer.put(verts).flip();
		return vertsBuffer;
	}
	
	/* create a float buffer from an int array that for openGL to read */
	public IntBuffer convertToFlippedBufferFromIntVector(int[] verts) {
		assert(verts.length > 0);//change to try and catch?
		IntBuffer vertsBuffer = BufferUtils.createIntBuffer(verts.length);
		vertsBuffer.put(verts).flip(); // unit testing
		return vertsBuffer;
	}

	/* create a float buffer from a matrix that for openGL to read */
	public FloatBuffer convertToFlippedBufferFromMat(float[][] mat) {
		assert(mat.length > 0);//change to try and catch?
		assert(mat[0].length > 0);//change to try and catch?
		FloatBuffer buffer = BufferUtils.createFloatBuffer(mat.length * mat[0].length);
		for (int i = 0; i < mat.length; i++) {
			for (int k = 0; k < mat[0].length; k++) {
				buffer.put(mat[i][k]);
			}
		}
		buffer.flip();
		return buffer;
	}
}
