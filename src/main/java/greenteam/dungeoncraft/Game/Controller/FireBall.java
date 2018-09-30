package greenteam.dungeoncraft.Game.Controller;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.EulerAngle;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;

public class FireBall extends GameObject3D {

    private Vec3f movement; //the movement vector is used to normalize movement directions
    private boolean firing;
    private EulerAngle firingDirection;
    private float bulletSpeed;
    private float fireBallDamage;
    /* Constructor */
    public FireBall(Vec3f startPos, String name) {
	super(startPos, name);
	fireBallDamage = 20;
	bulletSpeed = 75.0f;
	movement = new Vec3f(0,0,0);
	firingDirection = new EulerAngle();
	isTransparent = true;
	usesInstancing = false;
    }

    @Override /* initialize the game object with mesh and texture data */
    public void initMesh() {
	rawMesh = new Mesh();
	rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\fireBall.obj");
	Texture fireBallTex = new Texture();
	fireBallTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/fireball.png");
	rawMesh.attachTexture(fireBallTex);
	vaoID = rawMesh.getVaoID();
    }

    /* detect what the fireball hits when shot */
    public void moveAndCollisionWithPlayerAndWalls() {
	// get rotation to move in the forward vector
	float sinYRot = (float) Math.sin(Math.toRadians(-firingDirection.getY()));
	float cosYRot = (float) Math.cos(Math.toRadians(-firingDirection.getY()));
	float moveAmount = (float) (GameEngine.getDeltaTime() * bulletSpeed);
	float moveInX = -sinYRot * moveAmount;
	float moveInZ = cosYRot * moveAmount;
	float newX = (tranPos.getX() + moveInX);
	float newZ = (tranPos.getZ() + moveInZ);
	// if out of bounds of grid
	if (newX < 0 || newX >= Main.getMap().getGrid().length || newZ < 0
		|| newZ >= Main.getMap().getGrid()[0].length) {
	} else if (Main.getMap().getGrid()[(int) newX][(int) newZ] != 0) { // if next position is a walkable path (not a wall)
	    tranPos.setX(newX);
	    tranPos.setZ(newZ);
	} 

	if (tranPos.distance(Main.getPlayer().getTranPos()) < 0.2f) {
	    hitPlayer(Main.getPlayer(),fireBallDamage);
	    isActive = false;
	}
    }

    public void hitPlayer(Player playerIn,float hitAmount){
	playerIn.playerHit(hitAmount);
    }

    @Override /* update is called every frame by the draw function */
    public void drawUpdate() {
	if (isActive) {
	    	moveAndCollisionWithPlayerAndWalls();
		addInstancePositionsToBufferForDrawing();
		shader.setUniformMat4f("transform", firingDirection);
		shader.setUniformi("uvScale", 1);
	}
    }
    
	@Override
	public void update() {
	    if (isActive) {
	    	moveAndCollisionWithPlayerAndWalls();
	    }
	}
	
	

    public EulerAngle getFiringDirection() {
	return firingDirection;
    }

    public void setFiringDirection(EulerAngle firingDirection) {
	this.firingDirection = firingDirection;
    }

    public float getFireBallDamage() {
        return fireBallDamage;
    }

    public void setFireBallDamage(float fireBallDamage) {
        this.fireBallDamage = fireBallDamage;
    }
}
