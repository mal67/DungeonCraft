package greenteam.dungeoncraft.Engine.Math;

public class EulerAngle extends Mat4f {

	private float x;
	private float y;
	private float z;

	/* rotate the euler angles by multipling X/Y/Z rotations */
	public Mat4f rotate(float xIn, float yIn, float zIn) {

		// store values
		x = xIn;
		y = yIn;
		z = zIn;

		// convert to radians
		float x_ = (float) Math.toRadians(xIn);
		float y_ = (float) Math.toRadians(yIn);
		float z_ = (float) Math.toRadians(zIn);

		// rotate individual axes
		EulerAngle rX = new EulerAngle();
		EulerAngle rY = new EulerAngle();
		EulerAngle rZ = new EulerAngle();
		rX.rXRot(x_);
		rY.rYRot(y_);
		rZ.rZRot(z_);

		// combine
		mat = rX.mul(rY).mul(rZ).getMat();

		return this;
	}

	/* rotate the euler angle in X */
	public EulerAngle rXRot(float a) {

		float m11 = (float) (Math.cos(a));
		float m12 = (float) (-Math.sin(a));
		float m21 = (float) (Math.sin(a));
		float m22 = (float) (Math.cos(a));

		mat[0][0] = 1;
		mat[0][1] = 0;
		mat[0][2] = 0;
		mat[0][3] = 0;
		mat[1][0] = 0;
		mat[1][1] = m11;
		mat[1][2] = m12;
		mat[1][3] = 0;
		mat[2][0] = 0;
		mat[2][1] = m21;
		mat[2][2] = m22;
		mat[2][3] = 0;
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = 0;
		mat[3][3] = 1;

		return this;
	}

	/* rotate the euler angle in Y */
	public EulerAngle rYRot(float b) {

		float m00 = (float) (Math.cos(b));
		float m02 = (float) (Math.sin(b));
		float m20 = (float) (-Math.sin(b));
		float m22 = (float) (Math.cos(b));

		mat[0][0] = m00;
		mat[0][1] = 0;
		mat[0][2] = m02;
		mat[0][3] = 0;
		mat[1][0] = 0;
		mat[1][1] = 1;
		mat[1][2] = 0;
		mat[1][3] = 0;
		mat[2][0] = m20;
		mat[2][1] = 0;
		mat[2][2] = m22;
		mat[2][3] = 0;
		mat[3][0] = 0;
		mat[3][1] = 0;
		mat[3][2] = 0;
		mat[3][3] = 1;

		return this;
	}

	/* rotate the euler angles in Z */
	public EulerAngle rZRot(float c) {

		float m00 = (float) (Math.cos(c));
		float m01 = (float) (-Math.sin(c));
		float m10 = (float) (Math.sin(c));
		float m11 = (float) (Math.cos(c));

		mat[0][0] = m00;
		mat[0][1] = m01;
		mat[0][2] = 0;
		mat[0][3] = 0;
		mat[1][0] = m10;
		mat[1][1] = m11;
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

	public EulerAngle lookAt(Vec3f eye, Vec3f target) {
	    // target is the target position in world space
	    // eye is the camera position in world space
	    
	    //lookat using euler angles
	    target.sub(eye); //get direction vector
	    float r = (float) Math.sqrt(target.getX()*target.getX() + target.getZ() * target.getZ()); //length
	    float yaw = (float) Math.atan2(target.getX(), target.getZ()); //calculate yaw
	    float pitch = (float) Math.atan2(target.getY()*-1.0f, r); //calculate pitch
	    
	    //convert to degrees for the rotation computation (val*57.29: converts rad val -> degrees)
	    this.rotate(pitch*57.2958f, yaw*57.2958f, 0); 
	    //rotate 180 to face the target (due to starting rotation)
	    this.rotate(this.getX(), this.getY(), 0);
    
	    return this;
	}
	
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

}
