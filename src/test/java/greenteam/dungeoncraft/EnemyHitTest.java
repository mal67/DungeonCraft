package greenteam.dungeoncraft;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Game.Controller.Player;
import greenteam.dungeoncraft.Game.Controller.SkullEnemy;

import org.junit.Before;
import org.junit.Test;

public class EnemyHitTest {
	
	@Before
	public void setUpBeforeClass_CreateContext() throws Exception {
		GameEngine.Init();
	}

	@Test /*test for the player  hits the enemy
	       *makes sure the enemy health is reduced by the correct amount
	       */
	public void enemyHealthDecreaseTest() {
		
		Player rph = new Player();
		rph.init();
		SkullEnemy skullEnemy = new SkullEnemy(new Vec3f(0,0,0),"skullEnemy");
		
		int expectedHealth = skullEnemy.getHealth();
		int damageAmount =  rph.getShootLogic().getBulletDamage();
		expectedHealth -=  damageAmount;
		rph.getShootLogic().hitSkullEnemy(skullEnemy, damageAmount);
		
		assert(skullEnemy.getHealth() == expectedHealth);
		
		
	}

}
