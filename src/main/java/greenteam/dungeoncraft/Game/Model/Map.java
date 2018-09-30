package greenteam.dungeoncraft.Game.Model;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.AmmoPack;
import greenteam.dungeoncraft.Game.Controller.Coin;
import greenteam.dungeoncraft.Game.Controller.Door;
import greenteam.dungeoncraft.Game.Controller.HealthPack;
import greenteam.dungeoncraft.Game.Controller.Player;
import greenteam.dungeoncraft.Game.View.CeilingPlane;
import greenteam.dungeoncraft.Game.View.CubeWithBar;
import greenteam.dungeoncraft.Game.View.GroundPlane;
import greenteam.dungeoncraft.Game.View.Wall;

public class Map {
    private MapUtility mapUtil = new MapUtility();
    private ProceduralMap procMap = new ProceduralMap();

    private int gridRows = 32;
    private int gridCols = 32;
    private int[][] grid;
    private GameObject3D groundPlane;
    private GameObject3D ceilingPlane;
    private GameObject3D parentWall;
    private GameObject3D parentCubeGrassed;
    private GameObject3D parentCoin;
    private GameObject3D parentHealthPack;
    private GameObject3D parentAmmo;
    private GameObject3D door;
    private int coinCount;
    
    
    public Map() {
	grid = new int[gridRows][gridCols];
	coinCount = 0;
    }

    /* initialize the grid from a 2D integer array (grid) */
    public void initializeGridFromIntArr(int[][] gridIn) {
		assert (gridIn.length > 0);
		assert (gridIn[0].length > 0);
		grid = gridIn;
		initializeMapGameObjects();
    }

    /* initialize the grid from a source location */
    public void initializeGridFromSource(String mapSource) { 

	grid = mapUtil.loadMap(mapSource, gridRows, gridCols);
	// prevent the starting grid from being a wall
	// grid[0][0] = 1;
	initializeMapGameObjects();
    }
    
    /* initialize the grid from a source location */
    public void initializeProceduralMap() {

	grid = procMap.createNewMap();
	// prevent the starting grid from being a wall
	grid[1][1] = 1;
	gridRows = grid.length;
	gridCols = grid[0].length;
	
	initializeMapGameObjects();
    }
    
   
    public void initializeMapGameObjects() {
	// Game object parents - hide original mesh to be instanced off-map
	groundPlane = new GroundPlane(new Vec3f(0, -2, 0), "groundplane");
	ceilingPlane = new CeilingPlane(new Vec3f(0, 2, 0), "ceilingplane");
	parentWall = new Wall(new Vec3f(0, -10, 0), "map_parent");
	parentCubeGrassed = new CubeWithBar(new Vec3f(0, -10, 0), "map_parent_y2");
	parentCoin = new Coin(new Vec3f(0, -10, 0), "coins");
	parentAmmo = new AmmoPack(new Vec3f(0, -10, 0), "ammo");
	parentHealthPack = new HealthPack(new Vec3f(0, -10, 0), "healthpack");
	door = new Door(new Vec3f(0, -1.0f, 1), "door");
	
	SceneManager sceneM = GameEngine.getSceneManager();

	sceneM.addGameObject3D(parentWall.name, parentWall);
	sceneM.addGameObject3D(parentCubeGrassed.name, parentCubeGrassed);
	sceneM.addGameObject3D(parentCoin.name, parentCoin);
	sceneM.addGameObject3D(parentAmmo.name, parentAmmo);
	sceneM.addGameObject3D(parentHealthPack.name, parentHealthPack);
	sceneM.addGameObject3D(groundPlane.name, groundPlane);
	sceneM.addGameObject3D(ceilingPlane.name, ceilingPlane);
	sceneM.addGameObject3D(door.name, door);
	    
	createGameObjectGridFromMap(grid);
    }
    
    /*
     * create game object grid from map 
     * ------------------------------------ 
     * using the 2d grid this function creates and sets the position (first parameter) and
     * the color (second parameter) of game objects to represent the map.
     * grid numbers: 2=coin; 3=enemy; 4=ammo; 5=health pack 6=door
     */
    public void createGameObjectGridFromMap(int[][] theGrid) {
	
	 float pivotXOffset = 0.5f;
	 float pivotYOffset = 0.5f;
	    
	    
	for (int i = 0; i < theGrid.length; i++) {
	    for (int k = 0; k < theGrid[0].length; k++) {

		if (theGrid[i][k] == 0) { // walls

		    if (Math.random() > 0.5) { // the second layer of wall cubes are randomly drawn (between a tall wall
					       // and a grassed cubed)
			parentWall.addInstanceGameObj(new Vec3f(i, -1, k), "map_" + i + k);
		    } else {
			parentCubeGrassed.addInstanceGameObj(new Vec3f(i, -1, k), "map_" + i + k);
		    }

		} else if (theGrid[i][k] == 2) { // coins
		    // since the grid starts at 0
		    // a box for the wall would go from 0 to 1,
		    // however a mesh should be centered (have a centered pivot for rotation)
		    // so the coins and other objects that rotate are shifted from being in there
		    // centered pivot positions (0,0)
		    // to the center of a box object (0.5,0.5).
		    // create the coin object.
		    parentCoin.addInstanceGameObj(new Vec3f(i + pivotXOffset, -0.85f, k + pivotYOffset),
			    "coin_" + i + k);// coin object (y0.5)
		    coinCount++;
		    
	    }  else if (theGrid[i][k] == 4) { // ammo
		
		parentAmmo.addInstanceGameObj(new Vec3f(i + pivotXOffset, -1f, k + pivotYOffset), "ammo_" + i + k);

	    } else if (theGrid[i][k] == 5) { // healthPack
		
		parentHealthPack.addInstanceGameObj(new Vec3f(i + pivotXOffset, -1f, k + pivotYOffset), "health_" + i + k);
		
		// add door to pass level next to start location
	    } else if (theGrid[i][k] == 6) { // exit door
	    	    door.setTranPos(new Vec3f(i,-1.0f,k));
	    }
	  }
	}
    }
    
    public void gotoNextMap() {
	 float pivotXOffset = 0.5f;
	 float pivotYOffset = 0.5f;
	 
	SceneManager sceneM = GameEngine.getSceneManager();
	grid = procMap.createNewMap();
	// prevent the starting grid from being a wall
	grid[1][1] = 1;
	
	gridRows = grid.length;
	gridCols = grid[0].length;
	
	Player ply = Main.getPlayer();
	ply.getCam().setCamPos(1+pivotXOffset, 0,1+pivotYOffset);
	ply.setTranPos(new Vec3f(1+pivotXOffset,0,1+pivotYOffset));
	ply.setCoins(0);
	coinCount = 0;

	parentWall.removeAllInstances();
	parentCubeGrassed.removeAllInstances();
	parentCoin.removeAllInstances();
	parentHealthPack.removeAllInstances();
	parentAmmo.removeAllInstances();

	Main.getBotManager().removeEnemiesFromScene();
 	Main.getPlayer().getMoves().setGrid(grid);
 	Main.getBotManager().init(grid);
 	createGameObjectGridFromMap(grid);
 	//garbage collection
 	System.gc();
    }
    
    public void restartToFirstMap() {
	 float pivotXOffset = 0.5f;
	 float pivotYOffset = 0.5f;
	 
	SceneManager sceneM = GameEngine.getSceneManager();
	grid = procMap.createNewMap();
	// prevent the starting grid from being a wall
	grid[1][1] = 1;
	
	gridRows = grid.length;
	gridCols = grid[0].length;
	
	Player ply = Main.getPlayer();
	ply.setHealth(ply.getStartingHealth());
	ply.setScore(0);
	ply.getCam().setCamPos(1+pivotXOffset, 0,1+pivotYOffset);
	ply.setTranPos(new Vec3f(1+pivotXOffset,0,1+pivotYOffset));
	ply.setCoins(0);
	coinCount = 0;

	parentWall.removeAllInstances();
	parentCubeGrassed.removeAllInstances();
	parentCoin.removeAllInstances();
	parentHealthPack.removeAllInstances();
	parentAmmo.removeAllInstances();

	Main.getBotManager().removeEnemiesFromScene();
	grid[1][1] = 1;
	Main.getPlayer().getMoves().setGrid(grid);
	Main.getPlayer().setActive(true);
	Main.getBotManager().init(grid);
	createGameObjectGridFromMap(grid);
	//garbage collection
	System.gc();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public int[][] getGrid() {
	return grid;
    }

    public void setGrid(int[][] grid) {
	this.grid = grid;
    }

    public int getCoinCount() {
        return coinCount;
    }

}
