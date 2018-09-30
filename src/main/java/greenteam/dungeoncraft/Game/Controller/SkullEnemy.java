package greenteam.dungeoncraft.Game.Controller;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.EulerAngle;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.Camera;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;

public class SkullEnemy extends GameObject3D {
    
    private int startingHealth;
    private int health;
    private int respawnCounter;
    private int maxRespawnAmount;
    private Vec3f diffuseColor;
    private EulerAngle eAngle;
    private FireBall fireBall; //enemy weapon
    private boolean isDead;
    private Camera cam;
    
	/* Constructor */
	public SkullEnemy(Vec3f startPos, String name) {
		super(startPos, name);
		startingHealth = 50;
		health = startingHealth;
		respawnCounter = 0;
		maxRespawnAmount = 0;
		usesInstancing = false;
		
		//the skull enemy owns the fireball game object it will fire
		fireBall = new FireBall(startPos, name + "_fireBall");
		fireBall.setActive( false );
		GameEngine.getSceneManager().addGameObject3D(fireBall.name,fireBall);
		eAngle = new EulerAngle();
		cam = GameEngine.getSceneManager().getMainCam();	
		diffuseColor = new Vec3f(1,1,1);
		 //startTime = System.currentTimeMillis() / 1000;
	}

	@Override /* initialize the game object with mesh and texture data */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\skull.obj");
		Texture skullTex = new Texture();
		skullTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/skull.png");
		rawMesh.attachTexture(skullTex);
		vaoID = rawMesh.getVaoID();
	}
	
	public void shootFireBall() {
	    //activate the fireball game object
	    fireBall.setFiringDirection(eAngle);
	    fireBall.setTranPos(tranPos);
	    fireBall.setActive(true);
	    //reset the position of the fireball to the position of this game object
	    fireBall.setTranPos(new Vec3f(tranPos.getX(),tranPos.getY(),tranPos.getZ()));
	}
	
	public void enemyHit(float hitAmount) {
	    if (isActive && isDead == false) {
        	    //logic
        	    health -= hitAmount;
        	    
        	    if (health <=0) {
        		Main.getPlayer().enemyKilled();
        		isActive = false;
        		isDead = true;
        		reSpawn();
        	    }
        	    //visuals
        	    diffuseColor.addX(-health/10000.0f);
        	    diffuseColor.addY(health/10000.0f);
        	    diffuseColor.addZ(health/10000.0f);
	   }
	}
	
	public void reSpawn(){
	    respawnCounter++;
	    if (respawnCounter+1 < maxRespawnAmount) {
		health = startingHealth;
		isActive = true;
		isDead = false;
	    } else {
		isActive = false;
	    }
	}

	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		    addInstancePositionsToBufferForDrawing();
		     Vec3f playerTrans = Main.getPlayer().getTranPos();
		     
		  
		     eAngle.lookAt(tranPos, new Vec3f(playerTrans.getX(),playerTrans.getY(),playerTrans.getZ()));
		     
		     shader.setUniformMat4f("transform", eAngle);
		     shader.setUniformi("uvScale", 1);
		     shader.setUniformVec3f("lightPosition", new Vec3f(GameEngine.getSceneManager().mainCamera.getCamXPos(), 
			     GameEngine.getSceneManager().mainCamera.getCamYPos(), 
			     GameEngine.getSceneManager().mainCamera.getCamZPos()));     
	    }
	}
	
	    
		@Override
		public void update() {
		   
		    if (tranPos.distance(Main.getPlayer().getTranPos()) < 10.0f && isDead == false) {
			isActive = true;
        			if (Main.getPlayer().getPlayerState() == 0) {
           			 //shoot a fireball every second
           			 if (Math.abs(System.currentTimeMillis() % 2000) >= 1500) { //every second
           				    shootFireBall();
           			}
			}
		    } else {
			isActive = false;			
		    }
		}
		

	public int getHealth() {
	    return health;
	}

	public int getRespawnCount() {
	    return respawnCounter;
	}

	public int getMaxRespawnAmount() {
	    return maxRespawnAmount;
	}

	public boolean isDead() {
	    return isDead;
	}	
}
