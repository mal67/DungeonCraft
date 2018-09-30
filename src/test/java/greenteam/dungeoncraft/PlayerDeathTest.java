package greenteam.dungeoncraft;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Game.Controller.FireBall;
import greenteam.dungeoncraft.Game.Controller.Player;

import org.junit.Before;
import org.junit.Test;

public class PlayerDeathTest {
	
	@Before
	public void setUpBeforeClass_CreateContext() throws Exception {
		GameEngine.Init();
	}

	@Test /*test if the player's state is changed after it is hit to the point where it's health is 0 or less*/
	public void healthDecrease() {
		
		Player rph = new Player();
		FireBall fireBall = new FireBall(new Vec3f(0,0,0),"fireBall");
		
		int totalPlayerHealth = rph.getHealth();
		fireBall.hitPlayer(rph, totalPlayerHealth);
		
		//state 0 is when the player is alive, state 1 is used for player death.
		assert(rph.getPlayerState() == 1);
		
		
	}

}
