package greenteam.dungeoncraft;

import org.junit.Test;


import java.nio.FloatBuffer;


import greenteam.dungeoncraft.Engine.Math.FloatBuffers;


/* In this class we are testing if the buffer functions produce one and two dimensional array buffers from arrays 
 * feed to them.  Buffers are kept only in a 1D array form so the addressing of a buffer made from a 2D array 
 * is done using (col*+k). */
public class FloatBuffersTest {

	@Test
	public void checkMatFlippedFloatBufferConversion() {
		
		FloatBuffers fBuff = new FloatBuffers();
		
		float[][] inputMat = new float[][]{
			  { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			};
			
			int rows = 4;
			int cols = 10;
			FloatBuffer buff = fBuff.convertToFlippedBufferFromMat(inputMat);
			for (int i = 0; i < rows; i++) {
				for (int k = 0; k < cols; k++) {
					assert(inputMat[i][k] == buff.get(cols*i + k));
				}
			}	
	}
	
	@Test
	public void checkVecFlippedFloatBufferConversion() {
		
		FloatBuffers fBuff = new FloatBuffers();
		
		float[] inputVec = new float[]{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 };
			FloatBuffer buff = fBuff.convertToFlippedBufferFromVector(inputVec);
			for (int i = 0; i < inputVec.length; i++) {
					assert(inputVec[i] == buff.get(i));
				}
			}	
}