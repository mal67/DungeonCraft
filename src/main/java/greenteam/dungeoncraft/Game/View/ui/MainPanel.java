package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;

public class MainPanel extends GameObjectUI {

    /* Constructor */
    public MainPanel(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
    }
    
	/* The function is called when the mouse is clicked on the ui element */
    	@Override
	public void onMouseClick() {
		System.out.println( "ui element clicked: " + name  );
	}
    
}
