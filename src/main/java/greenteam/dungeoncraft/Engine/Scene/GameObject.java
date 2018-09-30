package greenteam.dungeoncraft.Engine.Scene;

import greenteam.dungeoncraft.Engine.Math.EulerAngle;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public abstract class GameObject {

	public String name;
	protected int vaoID;
	protected int indicesID; 
	protected Mesh rawMesh;
	protected Shader shader;
	protected Vec3f tranPos;
	protected Mat4f scale;
	protected Vec3f colorVec;
	protected Mat4f translate;
	protected EulerAngle rotation;
	protected Mat4f transform;
	protected boolean isInstance;
	protected boolean isActive;
	protected boolean isTransparent;
	protected boolean usesInstancing;
	
	// TODO check efficiency between sending a mat4f(possibly by binding) for
	// translating/scale/rotation
	// while instancing to a sending vec3fs as shader uniforms.

	public GameObject() {
	}
	
	public abstract void init();

	public abstract void draw();
	
	public abstract void update();

	public abstract void drawUpdate();

	// accessors
	public int getVaoID() {
		return vaoID;
	}

	public boolean isActive() {
	    return isActive;
	}

	public void setActive(boolean isActive) {
	    this.isActive = isActive;
	}

	public Vec3f getTranPos() {
	    return tranPos;
	}

	public void setTranPos(Vec3f tranPos) {
	    this.tranPos = tranPos;
	}

	public Shader getShader() {
	    return shader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
