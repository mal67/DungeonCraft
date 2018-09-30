package greenteam.dungeoncraft.Engine.Math;

public class Mat4f {

	protected float[][] mat;
	
	
	/* Constructor  */
	public Mat4f() {
		mat = new float[4][4];

		// initialize Matrix
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < 4; k++) {
				mat[i][k] = 0.0f;
			}
		}
	}
	
	/* turns the matrix into an identity matrix  */
	public Mat4f identity() {

		mat[0][0] = 1;
		mat[0][1] = 0;
		mat[0][2] = 0;
		mat[0][3] = 0;
		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = 0;
		mat[1][3] = 0;
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 1;
		mat[2][3] = 0;
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = 0;
		mat[3][3] = 1;

		return this;
	}

	
	/* matrix multiplication - this matrix is multiplied by the parameter matrix then becomes the resulting matrix */
	public Mat4f mul(Mat4f otherMat) {

		float[][] newMat = new float[4][4];

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				for (int k = 0; k < 4; k++) {
					newMat[row][col] += mat[row][k] * otherMat.getVal(k, col);
				}
			}
		}

		mat = newMat;

		return this;
	}
	/* matrix multiplication - parameter matrix A is multiplied by the parameter matrix B then 
	 * this matrix becomes the resulting matrix  */
	public Mat4f mul(Mat4f matA, Mat4f matB) {

		float[][] newMat = new float[4][4];

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				for (int k = 0; k < 4; k++) {
					newMat[row][col] += matA.getVal(row, k) * matB.getVal(k, col);
				}
			}
		}

		mat = newMat;

		return this;
	}
	/* the matrix becomes it's transpose  */
	public Mat4f transpose() {

		float[][] newMat = new float[4][4];

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				newMat[row][col] = mat[col][row];
			}
		}

		mat = newMat;

		return this;
	}

	/* prints the matrix data to the system console  */
	public void printMat() {

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				System.out.print(mat[row][col] + " , ");
			}
			System.out.print("\n");
		}
	}

	/* object translation  */
	public Mat4f translation(float xIn, float yIn, float zIn) {

		mat[0][0] = 1;
		mat[0][1] = 0;
		mat[0][2] = 0;
		mat[0][3] = xIn;
		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = 0;
		mat[1][3] = yIn;
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = 1;
		mat[2][3] = zIn;
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = 0;
		mat[3][3] = 1;

		return this;
	}
	/* object scaling  */
	public Mat4f scaling(float xIn, float yIn, float zIn) {

		mat[0][0] = xIn;
		mat[0][1] = 0;
		mat[0][2] = 0;
		mat[0][3] = 0;
		mat[1][0] = 0;
		mat[1][1] = yIn;
		mat[1][2] = 0;
		mat[1][3] = 0;
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = zIn;
		mat[2][3] = 0;
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = 0;
		mat[3][3] = 1;

		return this;
	}

	/* projection Matrix - used to create camera perspective  */
	public Mat4f projectionView(float fov, float zNear, float zFar, float width, float height) {

		float aspectRatio = width / height;
		float fv = (float) Math.tan(Math.toRadians(fov / 2));
		float zm = zFar - zNear;
		float zp = zFar + zNear;
		float m00 = 1 / fv / aspectRatio;
		float m11 = 1 / fv;
		float m22 = -zp / zm;
		float m23 = (float) -(2 * Math.pow(zFar, zNear)) / zm;

		mat[0][0] = m00;
		mat[0][1] = 0;
		mat[0][2] = 0;
		mat[0][3] = 0;
		mat[1][0] = 0;
		mat[1][1] = m11;
		mat[1][2] = 0;
		mat[1][3] = 0;
		mat[2][0] = 0;
		mat[2][1] = 0;
		mat[2][2] = m22;
		mat[2][3] = m23;
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = -1;
		mat[3][3] = 0;

		return this;
	}
	
	public void setMat(float[][] matIn) {
		mat = matIn;
	}
	

	public float[][] getMat() {
		return mat;
	}

	public void setVal(int row, int col, float val) {
		mat[row][col] = val;
	}

	public float getVal(int row, int col) {
		return mat[row][col];
	}

	public float[][] getAsFloatArray() {
		return mat;
	}
}
