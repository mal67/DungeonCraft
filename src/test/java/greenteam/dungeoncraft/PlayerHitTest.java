package greenteam.dungeoncraft;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Game.Controller.FireBall;
import greenteam.dungeoncraft.Game.Controller.Player;

import org.junit.Before;
import org.junit.Test;

public class PlayerHitTest {
	
	@Before
	public void setUpBeforeClass_CreateContext() throws Exception {
		GameEngine.Init();
	}

	@Test /*test for when player gets hit from a fireBall
	       *makes sure the health is reduced by the correct amount, which is 10 (healthDecreaseRate)
	       */
	public void healthDecrease() {
		
		Player rph = new Player();
		FireBall fireBall = new FireBall(new Vec3f(0,0,0),"fireBall");
		
		int expectedHealth = rph.getHealth();
		expectedHealth -=  fireBall.getFireBallDamage();
		fireBall.hitPlayer(rph, fireBall.getFireBallDamage());
		
		assert(rph.getHealth() == expectedHealth);
		
		
	}

}
