package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;

public class ResumeButton extends GameObjectUI {
    
    /* Constructor */
    public ResumeButton(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
    }
    
	/* The function is called when the mouse is clicked on the ui element */
    	@Override
	public void onMouseClick() {
    	    Main.getUiMenu().getUiMenuController().resumeGame();
		System.out.println( "ui element clicked: " + name  );
	}
    
}
