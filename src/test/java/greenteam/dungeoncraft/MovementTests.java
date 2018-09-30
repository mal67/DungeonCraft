package greenteam.dungeoncraft;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.Player;

public class MovementTests {

	private int[][] testGrid;
	
	@Before /* In this class we are testing the movement of the player using a test 5x5 grid */
	public void setUpBeforeClass_CreateContext() throws Exception {
	    new Main();
		//test grid:
		//starting from the top left corner <0,0> moving in the x positive direction would be downward
	    	int[][] newGrid = 		        { { 1, 1, 1, 1, 0 }, 
									{ 1, 1, 1, 1, 0 }, 
									{ 1, 1, 1, 1, 0 }, 
									{ 1, 1, 1, 1, 0 },
									{ 1, 1, 1, 1, 0 } };
	    	
	    	 GameEngine.Init();
		 Main.getMap().initializeGridFromIntArr(newGrid);
		 Main.getPlayer().init();
		 
	    	testGrid = newGrid;
	} 
	
	@Test
	public void testMovement() {
		Player ply = Main.getPlayer();
		ply.getMoves().setGrid(testGrid);
		Vec3f moveDir = new Vec3f(1,0,0); //move direction
		ply.getMoves().move(moveDir);
		ply.getMoves().move(moveDir);
		ply.getMoves().move(moveDir);
		ply.getMoves().move(moveDir);
		/* after moving in x (+1) 4 times the next position should be wall based on the test grid  */
		float playerXPos = ply.getTranPos().getX();
		//this move should hit a boundary preventing the player from moving
		ply.getMoves().move(moveDir);
		assertEquals(playerXPos,ply.getTranPos().getX(),0.001f);
		
		System.out.println(ply.getTranPos().getX());
		
	}
	
	
	

}
