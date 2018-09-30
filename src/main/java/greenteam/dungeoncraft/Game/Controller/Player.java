package greenteam.dungeoncraft.Game.Controller;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.Camera;
import greenteam.dungeoncraft.Engine.Scene.GameObject;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.View.PlayerCamera;

public class Player extends GameObject {

	private PlayerMovement moves;
	private PlayerShoot shootLogic;
	private Camera cam;
	private int coins;
	private int score;
	private int health;
	private int startingHealth;
	private int playerState; //0 = alive, 1 = dead;
	private boolean playerHelper;
	
	/* Constructor */
	public Player() {
	    isActive = true;
	    startingHealth = 100;
	    health = startingHealth;
	    tranPos = new Vec3f(1+0.5f, 0, 1+0.5f); // start position
	    coins = 0;
	    score = 0;
	    playerState = 0;
	    playerHelper = true;
	}

	public void init() { /* initialize the player game object with it's own camera and movement classes */
	    shootLogic = new PlayerShoot(this);
	    moves = new PlayerMovement(this);
	    cam = new PlayerCamera();
	    cam.init();
	    GameEngine.getSceneManager().setMainCamera(cam);		
	    moves.init(GameEngine.getWindow());
	}
	
	public void playerHit(float hitAmount) {
		health -= hitAmount;
		
		if (health == 0) {
		    playerState = 1;	
		    isActive = false;
		    try {
			Main.getUiMenu().getUiMenuController().showDeathScreen();
		    } catch (Exception e) {
			System.out.println("could not show death panel, has the death panel been added to the scene manager");
		    }
		}
		
		System.out.println( "health: " + health);
		
	}
	
	public void recievedCoin() {
	    coins += 1;
	    score += 1;
	}
	
	public void recievedHealth() {
	    if (health < 100) {
	    	health += 20;
	    	if (health > 100) {
	    	    health = 100;
	    	}
	    }
	}
	
	public void enemyKilled() {
	    score += 5;
	}
	
	
	public void recievedAmmoPack() {
	    shootLogic.setAmmoAmount(shootLogic.getAmmoAmount() + 100);
	}

	@Override
	public void draw() {
	}

	@Override
	public void update() {
	    if (isActive) {
			cam.setCamPos(tranPos.getX(), tranPos.getY(), tranPos.getZ());
			GameEngine.getSceneManager().getMapCam().setCamPos(tranPos.getX(), tranPos.getY(), tranPos.getZ());
			moves.update();
			shootLogic.update();
	    }
	    if (playerHelper) {
		Main.getUiMenu().getUiMenuController().showHelpPanel();
		playerHelper = false;
	    }
	}

	@Override
	public void drawUpdate() {
	}
	
	
	
	public Camera getCam() {
		return cam;
	}

	public void setX(float xIn) {
		tranPos.setX(xIn);
	}

	public void setZ(float zIn) {
		tranPos.setZ(zIn);
	}
	
	public void setY(float yIn) {
		tranPos.setY(yIn);
	}
	
	public Vec3f getTranPos() {
		return tranPos;
	}
	
	public PlayerMovement getMoves() {
		return moves;
	}

	public int getCoins() {
		return coins;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getScore() {
	    return score;
	}

	public int getPlayerState() {
	    return playerState;
	}

	public PlayerShoot getShootLogic() {
	    return shootLogic;
	}

	public int getStartingHealth() {
	    return startingHealth;
	}

	public void setScore(int score) {
	    this.score = score;
	}

	public void setCoins(int coins) {
	    this.coins = coins;
	}

}
