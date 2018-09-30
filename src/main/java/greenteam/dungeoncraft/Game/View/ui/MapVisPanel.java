package greenteam.dungeoncraft.Game.View.ui;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;

public class MapVisPanel extends GameObjectUI {

    /* Constructor */
    public MapVisPanel(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
    }
    
    @Override /* draw is called every frame (draws the mesh to the scene) */
	public void draw() {
		if (isActive) {
			glBindVertexArray(vaoID);
			rawMesh.attachTexture(uiTexture);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,GameEngine.getFbo().getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
			glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}
	}
    
    /* initialize the UI game object */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\mapVisPanel.obj");
		rawMesh.attachTexture(uiTexture);
		vaoID = rawMesh.getVaoID();
	}
    
    
}
