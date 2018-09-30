package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Scene.GameObjectText;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;

public class AmmoUI extends GameObjectUI {
    
    private GameObjectText ammoTextLine;
    

    /* Constructor */
    public AmmoUI(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
	ammoTextLine = new GameObjectText(xPosInScreenSpace,yPosInScreenSpace,0.045f, "ammotext","0000");
	GameEngine.getSceneManager().addGameObjectText(ammoTextLine.name, ammoTextLine);
    }
   
		@Override /* update is called every frame by the draw function */
		public void update() {
		    if (isActive) {
			ammoTextLine.setLineOfText(String.valueOf(Main.getPlayer().getShootLogic().getAmmoAmount()/10));			
		    	shader.setUniformMat4f("transform", transform);
		    }
		}
    
}
