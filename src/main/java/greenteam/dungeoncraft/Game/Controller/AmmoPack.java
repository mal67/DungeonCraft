package greenteam.dungeoncraft.Game.Controller;
import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.EulerAngle;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;

public class AmmoPack extends GameObject3D {
	
    private EulerAngle eAngle;
    private float rotation;
    
	/* Constructor */
	public AmmoPack(Vec3f startPos, String name) {
		super(startPos, name);
		eAngle = new EulerAngle();

	}
	/* Constructor */
	public AmmoPack(Vec3f startPos, String name, Mesh givenMesh) {
		super(startPos, name, givenMesh);
	}

	@Override 	/* initialize the game object with mesh and texture data */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\ammo.obj");
		Texture defaultTex = new Texture();
		defaultTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/gun.png");	
		rawMesh.attachTexture(defaultTex);
		vaoID = rawMesh.getVaoID();
	}
	/* pickup function called by the player when the player's coordinates matches the game objects */
	public void pickedUp(Player ply) {
	    
	      //add ammo to player's shooter
		try {
		    ply.recievedAmmoPack();
		} catch (Exception e) {
		    System.err.println( "could not set player ammo within the ammo pickup function");
		}

		isActive = false;
		
		//remove game object from list in the parent game object by name
		try {
		    parent.removeInstance(name);
		} catch (Exception e) {
		    System.err.println( "could not remove ammo from it's parent. was the parent object set?");
		}
	}
	
	@Override /* for performance purposes, the ammo are instanced (the mesh buffer information is used to display all active ammo) */
	public void addInstanceGameObj(Vec3f startPos, String nameIn) { //TODO find out of there is a way to add a new (classtype of this) with specifying the type
		try {
			AmmoPack newInstance = new AmmoPack(startPos, nameIn, rawMesh);
			newInstance.isInstance = true;
			newInstance.transform = transform;
			newInstance.parent = this;
			childGameObjects.add(newInstance);
		} catch (Exception e) {
			System.err.println("Could not add GameObject Instance");
		}
	}
	
	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		 if (!isInstance) {
		     rotation += 0.000000000000001f * System.nanoTime();
		     eAngle.rotate(0,rotation,0);
		     shader.setUniformMat4f("transform", eAngle.mul(transform));
		     shader.setUniformVec3f("lightPosition", new Vec3f(GameEngine.getSceneManager().mainCamera.getCamXPos(), 
			     GameEngine.getSceneManager().mainCamera.getCamYPos(), 
			     GameEngine.getSceneManager().mainCamera.getCamZPos()));
		 }
	    }
	}
	
	
}
