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

import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;

public class Crosshair extends GameObjectUI {

    /* Constructor */
    public Crosshair(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
    }
    
    @Override /* draw is called every frame (draws the mesh to the scene) */
	public void draw() {
		if (isActive) {
			GL11.glEnable (GL11.GL_BLEND);
			glBindVertexArray(vaoID);
			rawMesh.attachTexture(uiTexture);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
			glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
			GL11.glDisable (GL11.GL_BLEND);
		}
	}
    
}
