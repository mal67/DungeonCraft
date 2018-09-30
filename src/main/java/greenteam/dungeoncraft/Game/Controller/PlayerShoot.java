package greenteam.dungeoncraft.Game.Controller;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.util.ArrayList;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Game.Main;

public class PlayerShoot {
    
    private Player player;
    private int bulletDamage;
    private int ammoAmount;
    Vec3f bulletPos = new Vec3f(0,0,0);
    ArrayList<Vec3f> bulletPositionHistory = new ArrayList<Vec3f>();
    boolean hitWall;
    boolean hitEnemy;
    long startTime;
    
    public PlayerShoot(Player playerIn) {
	player = playerIn;
	bulletDamage = 2;
	ammoAmount = 300;
	startTime = 0;
    }
    
    public void update() {
	 if (Math.abs(System.currentTimeMillis() % 20000) >= 5000) {
        	if (glfwGetMouseButton(GameEngine.getWindow().getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS ) {
        	    if (ammoAmount > 0) {
        		ammoAmount -= 1f;
        		shootStart();
        	    }
        	}
	}
    }
    
    
    public void setBulletPosToPlayerPos() {
	//set bullet's start position to the start position of the player 
	bulletPos.setXYZ(player.getTranPos().getX(), player.getTranPos().getY(), player.getTranPos().getZ());
    }
    
    public Vec3f getBulletMoveDirFromPlayerAngle() {
	//set movement direction for the bullet
	float sinYRot = (float) Math.sin(Math.toRadians(player.getCam().getCamEulerAngle().getY()));
	float cosYRot = (float) Math.cos(Math.toRadians(player.getCam().getCamEulerAngle().getY()));
	float moveAmount = (float) (GameEngine.getDeltaTime() * 150f);
	float moveInX = sinYRot * moveAmount;
	float moveInZ = -cosYRot * moveAmount;
	
	return new Vec3f(moveInX,0,moveInZ);
    }
    
    public void shootStart() {
	setBulletPosToPlayerPos();
	hitWall = false;
	hitEnemy = false;	
	Vec3f moveDir = getBulletMoveDirFromPlayerAngle();
	//get list of bullet detection positions, stop if the bullet hits a wall
	for (int i = 0; i < 1000; i++) {
	    if(hitWall) {
		break;
	    }
	    moveBullet(moveDir);
	}
	//detect if any enemies are within the bullet position list (a list that holds all the positions the bullet has been in)
	ArrayList<SkullEnemy> enemyList = Main.getBotManager().getSkullEnemyGameObjects();
	//foreach enemy in the enemylist that are within a certain distance of the player, check all the bullet positions
	// if a bullet position from the bulletPositionHistory array is within a given distance to an enemy, 
	// the enemy should detect that hit
	// and a function in the enemy (related to the enemies health) should be called.
	for (int i = 0; i < enemyList.size(); i++) {
	    //check that the enemy is within a desire distance from player before doing bullet detection 
	    if (player.getTranPos().distance(enemyList.get(i).getTranPos())<10f) {
		for (int k = 0; k < bulletPositionHistory.size(); k++) {
		    if (bulletPositionHistory.get(k).distance(enemyList.get(i).getTranPos()) < 0.2f) {
			hitSkullEnemy(enemyList.get(i),bulletDamage);
			break;
		    }
		    //if one enemy was hit, don't do bullet hit detection for the rest of the enemies in the enemylist
		    if (hitEnemy) {
			break;
		    }
		}    
	    }
	}
	//clean up 
	bulletPositionHistory.clear();
    }
    
    public void hitSkullEnemy(SkullEnemy skullEnemyIn,float hitAmount){
	skullEnemyIn.enemyHit(hitAmount);
    }
    
    public void moveBullet(Vec3f moveDirection) {
	float newX = (bulletPos.getX() + moveDirection.getX());
	float newZ = (bulletPos.getZ() + moveDirection.getZ());
	if (newX < 0 || newX >= Main.getMap().getGrid().length || newZ < 0 || newZ >= Main.getMap().getGrid()[0].length) {
	    //do nothing
	} else if (Main.getMap().getGrid()[(int) newX][(int) newZ] != 0) { // if next position is a walkable path (not a wall)
	    bulletPos.setX(newX);
	    bulletPos.setZ(newZ);
	    bulletPositionHistory.add(new Vec3f(newX,0,newZ));
	}  else {
	    hitWall = true;
	}
    }


    public int getBulletDamage() {
        return bulletDamage;
    }


    public int getAmmoAmount() {
        return ammoAmount;
    }


    public void setAmmoAmount(int ammoAmount) {
        this.ammoAmount = ammoAmount;
    }
    
    

}
