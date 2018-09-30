package greenteam.dungeoncraft.Game.View;

import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class CeilingPlane extends GameObject3D {
	
	/* Constructor */
	public CeilingPlane(Vec3f startPos, String name) {
		super(startPos, name);
		transform.scaling(64, 1, 64);
	}
	
	/* Constructor */
	public CeilingPlane(Shader inShader, Vec3f startPos, String name, Mesh givenMesh) {
		super(startPos, name, givenMesh);
	}

	@Override /* initialize the game object with mesh and texture data */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\ceilingPlane.obj");
		Texture ceilingTex = new Texture();
		ceilingTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ceiling.png");
		rawMesh.attachTexture(ceilingTex);
		vaoID = rawMesh.getVaoID();
	}
	
	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	   	     addInstancePositionsToBufferForDrawing();
		     shader.setUniformMat4f("transform", transform);
		     shader.setUniformi("uvScale", 128);
		     shader.setUniformVec3f("diffuseColor", new Vec3f(1,1,1));
	}	
	
}
