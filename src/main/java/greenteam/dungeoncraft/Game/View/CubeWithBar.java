package greenteam.dungeoncraft.Game.View;

import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class CubeWithBar extends GameObject3D {
	
	/* Constructor */
	public CubeWithBar(Vec3f startPos, String name) {
		super(startPos, name);
	}
	
	/* Constructor */
	public CubeWithBar(Shader inShader, Vec3f startPos, String name, Mesh givenMesh) {
		super(startPos, name, givenMesh);
	}

	@Override /* initialize the game object with mesh and texture data */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\cubeWithBar.obj");
		Texture defaultTex = new Texture();
		defaultTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/wall.png");	
		rawMesh.attachTexture(defaultTex);
		vaoID = rawMesh.getVaoID();
	}
	
}
