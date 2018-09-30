package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;
import greenteam.dungeoncraft.Game.Main;

public class MapVisPlayer extends GameObject3D {
	
	/* Constructor */
	public MapVisPlayer(Vec3f startPos, String name) {
		super(startPos, name);
	}
	
	/* Constructor */
	public MapVisPlayer(Shader inShader, Vec3f startPos, String name, Mesh givenMesh) {
		super(startPos, name, givenMesh);
	}

	@Override /* initialize the game object with mesh and texture data */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\mapVisPlayer.obj");
		Texture defaultTex = new Texture();
		defaultTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/red.png");	
		rawMesh.attachTexture(defaultTex);
		vaoID = rawMesh.getVaoID();
	}
	
	
	    @Override /* update is called every frame by the draw function */
	    public void drawUpdate() {
		if (isActive) {
		    	//set this position to player's position
		    	tranPos.setXYZ(Main.getPlayer().getTranPos());
			addInstancePositionsToBufferForDrawing();
			shader.setUniformMat4f("transform", transform);
			shader.setUniformi("uvScale", 1);
			shader.setUniformVec3f("diffuseColor", new Vec3f(1,1,1));
		    }
	    }
	    
	    
}
