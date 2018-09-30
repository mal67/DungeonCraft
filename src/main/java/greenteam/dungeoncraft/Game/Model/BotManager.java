package greenteam.dungeoncraft.Game.Model;

import java.util.ArrayList;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.SkullEnemy;

public class BotManager {
   
    	private ArrayList<SkullEnemy> skullEnemyGameObjects;
    	private float[][] gameGrid;
    	private int numberOfEnemies;
    	
    	
    public BotManager() {
	 skullEnemyGameObjects = new ArrayList<SkullEnemy>();
	 numberOfEnemies = 0;
    }

    public void init(int[][] theGrid) {
	SceneManager sceneM = GameEngine.getSceneManager();

	for (int i = 0; i < theGrid.length; i++) {
	    for (int k = 0; k < theGrid[0].length; k++ ) {
		if (theGrid[i][k] == 3) {
		    float pivotXOffset = 0.5f;
		    float pivotYOffset = 0.5f;
		    SkullEnemy skullEnemy = new SkullEnemy(new Vec3f(i + pivotXOffset, -0.1f, k + pivotYOffset), "skullenemy_" + skullEnemyGameObjects.size()); 
		    //keep the enemy game objects in a separate array from the sceneManager for the player to use when 
		    //detecting the vector distance between his/her forward position and the enemy. (for detecting shooting)
		    skullEnemyGameObjects.add( skullEnemy );
		    sceneM.addGameObject3D(skullEnemy.name, skullEnemy);
		}
	    }
	}
    }
    
    
    public void removeEnemiesFromScene() {
	try {
	    SceneManager sceneM = GameEngine.getSceneManager();
	    
	    for (int i = 0; i < skullEnemyGameObjects.size(); i++) {
	        
	        sceneM.getGameObj3DHashMap().remove("skullenemy_" + i );
	        
	    }
	} catch (Exception e) {
	    System.err.println("could not remove enemies from scene manager:");
	    e.printStackTrace();
	}
	
	
    }

    public ArrayList<SkullEnemy> getSkullEnemyGameObjects() {
        return skullEnemyGameObjects;
    }

}
