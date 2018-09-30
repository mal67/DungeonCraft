package greenteam.dungeoncraft.Engine.Math;

public class Vec3f {

    private float x;
    private float y;
    private float z;

    /* Constructor */
    public Vec3f(float xIn, float yIn, float zIn) {
	this.x = xIn;
	this.y = yIn;
	this.z = zIn;
    }

    public Vec3f cross(Vec3f rhs) {
	this.x = y * rhs.z - z * rhs.y;
	this.y = z * rhs.x - x * rhs.z;
	this.z = x * rhs.y - y * rhs.x;
	return this;
    }

    public float dot(Vec3f rhs) {
	return (x * rhs.getX() + y * rhs.getY() + z * rhs.getZ());
    }

    /* normalizes the vector */
    public Vec3f normalize() {
	double length = Math.sqrt(x * x + y * y + z * z);
	x = (float) (x / length);
	y = (float) (y / length);
	z = (float) (z / length);
	return this;
    }

    public float distance(Vec3f vecB) {
	return (float) Math.sqrt(Math.pow(this.x - vecB.getX(), 2) 
					      + Math.pow(this.y - vecB.getY(), 2)
					      + Math.pow(this.z - vecB.getZ(), 2));
    }

    public void addX(float xIn) {
	this.x += xIn;
    }

    public void addY(float yIn) {
	this.y += yIn;
    }

    public void addZ(float zIn) {
	this.z += zIn;
    }

    // mutators
    public void setX(float xIn) {
	this.x = xIn;
    }

    public void setY(float yIn) {
	this.y = yIn;
    }

    public void setZ(float zIn) {
	this.z = zIn;
    }

    // accessors
    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public float getZ() {
	return z;
    }

    public Vec3f setXYZ(float xIn, float yIn, float zIn) {

	this.x = xIn;
	this.y = yIn;
	this.z = zIn;

	return this;

    }
    
    public Vec3f setXYZ(Vec3f rhsTranPos) {

	this.x = rhsTranPos.getX();
	this.y = rhsTranPos.getY();
	this.z = rhsTranPos.getZ();

	return this;

    }
    
    

    public Vec3f sub(Vec3f rhs) {

	this.x = this.x - rhs.getX();
	this.y = this.y - rhs.getY();
	this.z = this.z - rhs.getZ();

	return this;
    }

    public Vec3f scalarMult(float scalar) {
	this.x = this.x * scalar;
	this.y = this.y * scalar;
	this.z = this.z * scalar;
	return this;
    }

    public Vec3f div() {
	return new Vec3f(0, 0, 0);
    }

    // unit test when implementing
    public float length() {
	return 0f;
    }
}
