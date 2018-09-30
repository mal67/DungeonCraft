package greenteam.dungeoncraft;

import org.junit.Test;
import greenteam.dungeoncraft.Engine.GameWindow;

public class WindowTest {

	@Test /* window initialization test, asserting if the wind reference is received */
	public void checkWindowRef() {
		GameWindow wind = new GameWindow();
		wind.init();
		assert(wind.getWindow() > 0);
	}

}
