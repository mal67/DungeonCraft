package greenteam.dungeoncraft.Engine.Scene;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Math.EulerAngle;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class Camera {

    protected float camXPos;
	protected float camYPos;
	protected float camZPos;
	protected Mat4f projVMat;
	protected Mat4f mVP;
	protected Shader shader;
	protected GameWindow wind;
	protected EulerAngle eAngle;
	protected Mat4f translate;
	protected boolean isActive;
	protected boolean mapVis;
	protected float flickerAmount; //used to produce a flicker effect when the gun the fired
	
	/* Constructor */
	public Camera() {
		projVMat = new Mat4f();
		eAngle = new EulerAngle();
		mVP = new Mat4f();
		translate = new Mat4f();
		camXPos = 0;
		camYPos = 0;
		camZPos = 0;
		mapVis = false;
		shader = GameEngine.getShaderManager().getDefaultShader();
		wind = GameEngine.getWindow();
	}

	/* initialize the camera, setting a default model_view_projection matrix */
	public void init() {
		eAngle.rotate(0, 0, 0);
		projVMat.projectionView(115, 0.01f, 1000.f, wind.getWidth(), wind.getHeight());
		mVP.mul(projVMat, eAngle);
		isActive = true;
	}
	 
	 
	/* update is called every frame */
	public void update() {
	    if (isActive) {
		// System.out.println(wind.getMouseXPos() +" , " + wind.getMouseYPos());

		// convert mouse positions to rotations for FPS Camera
		float y = (float) ((wind.getMouseXPos() - (wind.getWidth() / 2)) / wind.getWidth() * 360);
		float x = (float) ((wind.getMouseYPos() - (wind.getHeight() / 2)) / wind.getHeight() * 180);
		eAngle.rotate(x, y, 0);
		translate.translation(-camXPos, -camYPos, -camZPos);
		mVP.mul(projVMat,eAngle.mul(translate));

		// send new model_view_projection matrix to the shader
		shader.setUniformMat4f("MVP", mVP);
		shader.setUniformVec3f("lightPosition", new Vec3f(camXPos, camYPos, camZPos));
		shader.setUniformf("lightIntensity",1.0f);
	    }
	}

	public void setCamPos(float xPos, float yPos, float zPos) {
		camXPos = xPos;
		camYPos = yPos;
		camZPos = zPos;
	}

	public void setCamXPos(float xPos) {
		camXPos = xPos;
	}

	public void setCamYPos(float yPos) {
		camYPos = yPos;
	}

	public void setCamZPos(float zPos) {
		camYPos = zPos;
	}

	public float getCamXPos() {
		return camXPos;
	}

	public EulerAngle getCamEulerAngle() {
		return eAngle;
	}

	public float getCamYPos() {
		return camYPos;
	}

	public float getCamZPos() {
		return camZPos;
	}

	public void setShader(Shader shaderIn) {
		shader = shaderIn;
	}

	public void setWindow(GameWindow wind_) {
		wind = wind_;
	}

	public void setActive(boolean isActive) {
	    this.isActive = isActive;
	}
	
	public boolean getActive() {
	    return isActive;
	}
}
