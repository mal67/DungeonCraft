package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Scene.GameObjectText;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;

public class ScoreUI extends GameObjectUI {
    
    private GameObjectText coinTextLine;
    private GameObjectText scoreTextLine;
    

    /* Constructor */
    public ScoreUI(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
	coinTextLine = new GameObjectText(90,55,0.045f, "cointext","0000");
	scoreTextLine = new GameObjectText(90+130,55,0.045f, "scoretext","0000");
	//move to init?
	GameEngine.getSceneManager().addGameObjectText(coinTextLine.name, coinTextLine);
	GameEngine.getSceneManager().addGameObjectText(scoreTextLine.name, scoreTextLine);	
    }
   
		@Override /* update is called every frame by the draw function */
		public void update() {
		    if (isActive) {
			coinTextLine.setLineOfText(String.valueOf(Main.getMap().getCoinCount() - Main.getPlayer().getCoins()));
			scoreTextLine.setLineOfText(String.valueOf(Main.getPlayer().getScore()));
		    	shader.setUniformMat4f("transform", transform);
		    }
		}
    
}
