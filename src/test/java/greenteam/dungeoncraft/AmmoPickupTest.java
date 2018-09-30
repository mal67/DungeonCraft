package greenteam.dungeoncraft;

import org.junit.Before;
import org.junit.Test;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Shader.Shader;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.AmmoPack;
import greenteam.dungeoncraft.Game.Controller.Coin;
import greenteam.dungeoncraft.Game.Controller.Player;
import greenteam.dungeoncraft.Game.Model.BotManager;
import greenteam.dungeoncraft.Game.Model.Map;
import greenteam.dungeoncraft.Game.View.ui.UIHUD;
import greenteam.dungeoncraft.Game.View.ui.UIMenu;

/* In this class we are testing the interaction between the player and ammopack game object, specifically, if calling the pickUp function
 * within an ammo object adds to the amounts of ammo the player has  */
public class AmmoPickupTest {
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
	
	@Test
	public void testAmmoPickupWithPlayerShoot() {
		Player ply = new Player();
		ply.init();
		AmmoPack ammoGameObj = new AmmoPack(new Vec3f(0.0f,0.0f,0.0f), "ammo");
		int getCurrentAmmo = ply.getShootLogic().getAmmoAmount();
		ammoGameObj.pickedUp(ply);
		assert(ply.getShootLogic().getAmmoAmount() > getCurrentAmmo);			
	}
}
