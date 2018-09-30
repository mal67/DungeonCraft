package greenteam.dungeoncraft;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.Player;
import greenteam.dungeoncraft.Game.Controller.SkullEnemy;

import org.junit.Before;
import org.junit.Test;

public class EnemyDeathTest {
	
	@Before
	public void setUpBeforeClass_CreateContext() throws Exception {
		GameEngine.Init();
		new Main();
		//initialize player as the player recieves a score when the enemy is killed
		 Main.getPlayer().init();
	}

	@Test /*test the death of the skull enemy*/
	public void enemyRespawnTest() {
		
		Player rph = new Player();
		rph.init();
		SkullEnemy skullEnemy = new SkullEnemy(new Vec3f(0,0,0),"skullEnemy");
		
		int testRespawnCount = skullEnemy.getRespawnCount();
		int damageAmount =  skullEnemy.getHealth();
		// the enemy should die after being shoot by with a damage amount equal to it's health
		rph.getShootLogic().hitSkullEnemy(skullEnemy, damageAmount);
		// a respawn should be triggered after death if the maximum amount of respawns hasn't been reached
		System.out.println(testRespawnCount);
		System.out.println(skullEnemy.getRespawnCount());
		
		//assert that the respawn count has increased by one
		assert(skullEnemy.getRespawnCount() == (testRespawnCount + 1));
		
		int reSpawnsLeft = skullEnemy.getMaxRespawnAmount() - skullEnemy.getRespawnCount();
		//next test if the respawn limit works
		for (int i = 0; i < reSpawnsLeft; i++) {
		rph.getShootLogic().hitSkullEnemy(skullEnemy, damageAmount);
		}
	
		//check to see if the enemy is now dead due to reaching it's maxium amount of respawns
		assert(skullEnemy.isDead() == true);
	}
}
