package greenteam.dungeoncraft.Game.View.ui;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;

public class HelpPanel extends GameObjectUI {
    
    private ExitButtonNo resumeGame;

    /* Constructor */
    public HelpPanel(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
	
	Texture uiExitButtonNoTex = new Texture();
	uiExitButtonNoTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/resume.png");
	float thisXPos = currentXPosInScreenSpace;
	float thisYPos = currentYPosInScreenSpace;
	resumeGame = new ExitButtonNo(uiExitButtonNoTex,thisXPos+300,thisYPos-110,0,200,100, "uiHelpPanelResume");	
    }
    
	@Override /* draw is called every frame (draws the mesh to the scene) */
	public void draw() {
		if (isActive) {
			glBindVertexArray(vaoID);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
			glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);
			
			//update and draw child ui elements
			resumeGame.drawUpdate();
			resumeGame.draw();
			
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0); 
		}
	}
	
	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		shader.setUniformMat4f("transform", transform);	
	    }
	}
    
	/* The function is called when the mouse is clicked on the ui element */
    	@Override
	public void onMouseClick() {
		System.out.println( "ui element clicked: " + name  );
	}
    
}
