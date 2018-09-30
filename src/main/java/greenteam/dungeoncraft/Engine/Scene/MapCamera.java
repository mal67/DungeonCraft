package greenteam.dungeoncraft.Engine.Scene;

import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Math.EulerAngle;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class MapCamera {

	private float camXPos;
	private float camYPos;
	private float camZPos;
	private Mat4f projVMat;
	private Mat4f mVP;
	private Shader shader;
	private GameWindow wind;
	private EulerAngle eAngle;
	private Mat4f translate;
	private boolean isActive;
	private boolean mapVis;
	private float flickerAmount; //used to produce a flicker effect when the gun the fired
	
	/* Constructor */
	public MapCamera() {
		projVMat = new Mat4f();
		eAngle = new EulerAngle();
		mVP = new Mat4f();
		translate = new Mat4f();
		camXPos = 0;
		camYPos = 0;
		camZPos = 0;
		mapVis = false;
	}

	/* initialize the camera, setting a default model_view_projection matrix */
	public void init() {
		eAngle.rotate(0, 0, 0);
		projVMat.projectionView(120, 0.01f, 111f, wind.getWidth(), wind.getHeight());
		mVP.mul(projVMat, eAngle);
		isActive = true;
	}
	 
	 
	/* update is called every frame */
	public void update() {
	    if (isActive) {
		// System.out.println(wind.getMouseXPos() +" , " + wind.getMouseYPos());

		// convert mouse positions to rotations for FPS Camera
		//float y = (float) ((wind.getMouseXPos() - (wind.getWidth() / 2)) / wind.getWidth() * 360);
		//float x = (float) ((wind.getMouseYPos() - (wind.getHeight() / 2)) / wind.getHeight() * 180);
		eAngle.rotate(90, 90, 0);
		translate.translation(-camXPos, -camYPos+5, -camZPos);
		mVP.mul(projVMat,eAngle.mul(translate));

		// send new model_view_projection matrix to the shader
		shader.setUniformMat4f("MVP", mVP);
		shader.setUniformVec3f("lightPosition", new Vec3f(camXPos, camYPos, camZPos));
		   shader.setUniformf("lightIntensity", 15);
		
	
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
