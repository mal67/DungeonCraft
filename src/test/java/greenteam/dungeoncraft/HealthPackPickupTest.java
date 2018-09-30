package greenteam.dungeoncraft;

import org.junit.Before;
import org.junit.Test;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Shader.Shader;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.Coin;
import greenteam.dungeoncraft.Game.Controller.HealthPack;
import greenteam.dungeoncraft.Game.Controller.Player;
import greenteam.dungeoncraft.Game.Model.BotManager;
import greenteam.dungeoncraft.Game.Model.Map;
import greenteam.dungeoncraft.Game.View.ui.UIHUD;
import greenteam.dungeoncraft.Game.View.ui.UIMenu;

/* In this class we are testing the interaction between the player and the healthpack game object, specifically, if calling the pickUp function
 * within a healthpack object adds to the amounts of health the player has  */
public class HealthPackPickupTest {
	Shader defaultShader;
	private static Map map;
	private static Player player;
	
	
	@Before
	public void setUpBeforeClass_CreateContext() throws Exception {
	    map = new Map();
		player = new Player();
		
		int[][] newGrid = 	      { { 1, 1, 1, 1, 1 }, 
							{ 1, 1, 3, 2, 0 }, 
							{ 1, 1, 1, 2, 0 }, 
							{ 1, 1, 1, 1, 0 },
							{ 1, 1, 1, 1, 0 } };
		
		GameEngine.Init();
		map.initializeGridFromIntArr(newGrid);
		player.init();
	} 
	
	@Test //player's health should not go above the starting amount when picking up a healthpack
	public void testPlayerHealthCapOnPickUp() {
		Player ply = new Player();
		ply.init();
		HealthPack healthGameObj = new HealthPack(new Vec3f(0.0f,0.0f,0.0f), "health");
		int getCurrentHealth = ply.getHealth();
		healthGameObj.pickedUp(ply);
		assert(ply.getHealth() == getCurrentHealth);			
	}
	
	@Test
	public void testHealthPackPickupWithPlayerHealth() {
		Player ply = new Player();
		ply.init();
		//hit the player by 10 so we can test if the healthpack pickup works
		ply.playerHit(10);
		HealthPack healthGameObj = new HealthPack(new Vec3f(0.0f,0.0f,0.0f), "health");
		int getCurrentHealth = ply.getHealth();
		System.out.println(getCurrentHealth);
		healthGameObj.pickedUp(ply);
		assert(ply.getHealth() > getCurrentHealth);			
	}
}
