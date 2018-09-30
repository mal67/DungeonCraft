package greenteam.dungeoncraft.Game.Controller;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Inputs;
import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Math.Vec3f;

public class PlayerMovement extends Inputs {
    
    private Vec3f moveDir; //the movement vector is used to normalize movement directions
    private Player player;
    private GameWindow wind;
    private int[][] grid;
    private boolean isActive;
    private float lookAheadRate; //how much should the move function look ahead in the direction given to predict wall collision.
    boolean jumping; 
    float jumpAmount;
	
    public PlayerMovement(Player playerIn) {
	player = playerIn;
    }
    
    
    public void init(GameWindow windowRef) {
	moveDir = new Vec3f(0,0,0);
	wind = windowRef;
	lookAheadRate = 5;
	isActive = true;
	jumping = false; 
	jumpAmount = 0;
    }
    
    public void setGrid(int[][] gridIn) {
	grid = gridIn;
    }

    /*
     * Inputs ------------------------------------ using the glfw's window reference
     * (eng.getWindowRef) this function sets certain inputs to actions.
     */
    public void update() {
	// First person Camera Movement
	float sinYRot = (float) Math.sin(Math.toRadians(player.getCam().getCamEulerAngle().getY()));
	float cosYRot = (float) Math.cos(Math.toRadians(player.getCam().getCamEulerAngle().getY()));

	float moveAmount = (float) (GameEngine.getDeltaTime() * 50f);
		
	if (glfwGetKey(wind.getWindow(), GLFW_KEY_W) == GLFW_PRESS) {
	    moveDir.setXYZ(sinYRot, 0, -cosYRot).normalize().scalarMult(moveAmount);
	    move(moveDir);
	}
	
	if (glfwGetKey(wind.getWindow(), GLFW_KEY_S) == GLFW_PRESS) {
	    moveDir.setXYZ(-sinYRot, 0, cosYRot).normalize().scalarMult(moveAmount);
	    move(moveDir);
	}
	if (glfwGetKey(wind.getWindow(), GLFW_KEY_A) == GLFW_PRESS) {
	    moveDir.setXYZ(-cosYRot, 0, -sinYRot).normalize().scalarMult(moveAmount);
	    move(moveDir);
	}
	
	if (glfwGetKey(wind.getWindow(), GLFW_KEY_D) == GLFW_PRESS) {
	    moveDir.setXYZ(cosYRot, 0, sinYRot).normalize().scalarMult(moveAmount);
	    move(moveDir);
	}
	
	if (glfwGetKey(wind.getWindow(), GLFW_KEY_SPACE) == GLFW_PRESS){
		if (jumping == false) {
		jumpAmount = 0;
		jumping = true;
		}
		
	}
	
	if(jumping) {
		moveJump();
	}
	
	// First Person Camera Look
	glfwSetCursorPosCallback(wind.getWindow(), (windowHandle, xpos, ypos) -> {
	    wind.setMouseXPos(xpos);
	    wind.setMouseYPos(ypos);
	});
	
	
    }
    
    //makes player jump
    public void moveJump(){
    	 if (jumpAmount <= 360 && jumping) {
    	    	int jumpHeight = 45;
    	    	int jumpSpeed = 10;
    	    	float moveAmount = (float) (GameEngine.getDeltaTime() * jumpHeight);
    	    	
    		 jumpAmount += jumpSpeed;
    		 //the jump function uses a math.sin function from 0 to 360 to increase and decrease the yPos of the player (camera)
    		 moveDir.setXYZ(0,(float) Math.sin(Math.toRadians(jumpAmount)),0).normalize().scalarMult(moveAmount);
    		 move(moveDir);
    	 } else {
    	         //reset the yPos to garuntee landing at the correct yPos;
    	     	 resetYPos();
    		 jumping = false;
    	 	}
    	}
    
    
    public void resetYPos() {
	player.getCam().setCamPos(player.getTranPos().getX(), 0, player.getTranPos().getZ());
	player.setY(0);
    }
    
    

    /*
     * movement function used by the player (generally after an input is received)
     * grid numbers: 2=coin; 3=enemy; 4=ammo; 5=health pack 6=door
     */
    public void move(Vec3f moveDirection) {
	if (isActive) {
	    float newX = (player.getCam().getCamXPos() + moveDirection.getX());
	    float newY = (player.getCam().getCamYPos() + moveDirection.getY());
	    float newZ = (player.getCam().getCamZPos() + moveDirection.getZ());
	    float lookAheadNewX = newX + moveDirection.getX()*(lookAheadRate);
	    float lookAheadNewZ = newZ + moveDirection.getZ()*(lookAheadRate);
	    
	    // if out of bounds of grid
	    if (lookAheadNewX < 0 || lookAheadNewX >= grid.length || lookAheadNewZ < 0 || lookAheadNewZ >= grid[0].length) {
		//do nothing
	    } else if (grid[(int) lookAheadNewX][(int) lookAheadNewZ] != 0) { // if next position is a walkable path (not a wall)
		player.getCam().setCamPos(newX, newY, newZ);
		player.setX(newX);
		player.setY(newY);
		player.setZ(newZ);
		
		if (grid[(int) newX][(int) newZ] == 2) { //if the position on the grid is a coin
		    pickupCoin(newX,newZ);
		    
		    
		} else if (grid[(int) newX][(int) newZ] == 4) {
		    
		    pickupAmmo(newX,newZ);
		    
		} else if (grid[(int) newX][(int) newZ] == 5) {
		    
		    pickupHealthPack(newX,newZ);
		    

		} else if (grid[(int) newX][(int) newZ] == 6) {
		    try {
		    
		    Door theDoor = (Door) GameEngine.getSceneManager().getGameObj3DHashMap().get("door");
		    theDoor.goThroughDoorToNextLevel();
		    
		    } catch (Exception e) {
			System.err.println("could not find the door by name and trigger the function 'goThroughDoorToNextLevel' within the object,");
			e.printStackTrace();
		    }
		    
		}
	    }	    
	}
    }
    
    
    public void pickupCoin(float newX,float newZ) {
	 int i = (int) newX;
	    int k = (int) newZ;
	    try {
		
		Coin theCoin = (Coin) GameEngine.getSceneManager().getGameObj3DHashMap().get("coins")
		    .getInstanceObjByName("coin_" + i + k);
		theCoin.pickedUp(player);
		
	    } catch (Exception e) {
		System.err.println("could not find the coin by name and trigger the function 'pickedUp' within the object,");
		e.printStackTrace();
	    }

	    grid[(int) newX][(int) newZ] = 1;
    }
    
    public void pickupAmmo(float newX,float newZ) {
	 int i = (int) newX;
	    int k = (int) newZ;
	    try {
		
		AmmoPack theAmmo = (AmmoPack) GameEngine.getSceneManager().getGameObj3DHashMap().get("ammo")
		    .getInstanceObjByName("ammo_" + i + k);
		theAmmo.pickedUp(player);
		
	    } catch (Exception e) {
		System.err.println("could not find the coin by name and trigger the function 'pickedUp' within the object,");
		e.printStackTrace();
	    }

	    grid[(int) newX][(int) newZ] = 1;
    }
    
    public void pickupHealthPack(float newX,float newZ) {
	 int i = (int) newX;
	    int k = (int) newZ;
	    try {
		HealthPack theHealthPack = (HealthPack) GameEngine.getSceneManager().getGameObj3DHashMap().get("healthpack")
		    .getInstanceObjByName("health_" + i + k);
		theHealthPack.pickedUp(player);
		
	    } catch (Exception e) {
		System.err.println("could not find the healthpack by name and trigger the function 'pickedUp' within the object,");
		e.printStackTrace();
	    }

	    grid[(int) newX][(int) newZ] = 1;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}