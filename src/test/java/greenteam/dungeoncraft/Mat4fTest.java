package greenteam.dungeoncraft;

import static org.junit.Assert.*;

import org.junit.Test;

import greenteam.dungeoncraft.Engine.Math.Mat4f;

public class Mat4fTest {

	@Test 
	public void matrixMultiplicationTest() {
		
		Mat4f testMatrix1 = new Mat4f();
		Mat4f testMatrix2 = new Mat4f();
		Mat4f resultMatrix = new Mat4f();
		
		//Initialize testMatrix1
		float [][] arr = new float [4][4];
		
		arr[0][0] = 1; 				    arr[0][1] = 2; 					arr[0][2] = 3; 				arr[0][3] = 4;
		arr[1][0] = 5; 					arr[1][1] = 4; 				    arr[1][2] = 1; 				arr[1][3] = 6;
		arr[2][0] = 1; 					arr[2][1] = 4; 					arr[2][2] = 6;  				arr[2][3] = 2;
		arr[3][0] = 2; 					arr[3][1] = 3; 					arr[3][2] = 4; 				arr[3][3] = 6;
				
		//Initialize testMatrix2
		float [][] arr2 = new float [4][4];
		
		arr2[0][0] = 8; 				    arr2[0][1] = 4; 					arr2[0][2] = 1; 				arr2[0][3] = 0;
		arr2[1][0] = 1; 					arr2[1][1] = 0; 				    arr2[1][2] = 0; 				arr2[1][3] = 6;
		arr2[2][0] = 4; 					arr2[2][1] = 2; 					arr2[2][2] = 4;  			    arr2[2][3] = 1;
		arr2[3][0] = 7; 					arr2[3][1] = 5; 					arr2[3][2] = 4; 				arr2[3][3] = 6;
		
		//result of multiplying testMatrix1 and testMatrix2
		float [][] resultArr = new float [4][4];
		
		resultArr[0][0] = 50; 				    resultArr[0][1] = 30; 					resultArr[0][2] = 29; 				resultArr[0][3] = 39;
		resultArr[1][0] = 90; 					resultArr[1][1] = 52; 				    resultArr[1][2] = 33; 				resultArr[1][3] = 61;
		resultArr[2][0] = 50; 					resultArr[2][1] = 26; 					resultArr[2][2] = 33;  			resultArr[2][3] = 42;
		resultArr[3][0] = 77; 					resultArr[3][1] = 46; 					resultArr[3][2] = 42; 				resultArr[3][3] = 58;
		
		testMatrix1.setMat(arr);
		testMatrix2.setMat(arr2);
		resultMatrix.mul(testMatrix1, testMatrix2);
		
		assertArrayEquals(resultArr, resultMatrix.getAsFloatArray());		
      }

}
