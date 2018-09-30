package greenteam.dungeoncraft.Game.Controller;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;

public class Door extends GameObject3D {
    
	/* Constructor */
	public Door(Vec3f startPos, String name) {
		super(startPos, name);

	}
	/* Constructor */
	public Door(Vec3f startPos, String name, Mesh givenMesh) {
		super(startPos, name, givenMesh);
	}

	@Override 	/* initialize the game object with mesh and texture data */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\door.obj");
		Texture defaultTex = new Texture();
		defaultTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/door.png");	
		rawMesh.attachTexture(defaultTex);
		vaoID = rawMesh.getVaoID();
	}
	
	
	public void goThroughDoorToNextLevel() {
	    if (Main.getMap().getCoinCount() == Main.getPlayer().getCoins()) {
		Main.getMap().gotoNextMap();
	    }
	}
	
}
