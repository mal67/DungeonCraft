package greenteam.dungeoncraft.Engine.Scene;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;

public class GameObjectTextCharacter extends GameObject {

	protected boolean isActive;
	protected Texture uiTexture;
	
	/* Constructor */
	public GameObjectTextCharacter(Mesh  rawMeshIn, String nameIn) {
		name = nameIn;
		rawMesh = rawMeshIn;
		vaoID = rawMesh.getVaoID();
		init();
	}
	
	@Override
	public void init() {
		shader = GameEngine.getShaderManager().getDefaultUIShader();
		isActive = true;
	}

	
	@Override /* draw is called every frame (draws the mesh to the scene) */
	public void draw() {
		if (isActive) {
			glBindVertexArray(vaoID);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
			glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		}
	}

	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
	    
	    }
	}
	
	    @Override
	    public void update() {
		// TODO Auto-generated method stub
		
	    }

	public void setActive( boolean isActive ) {
		this.isActive = isActive;
	}
	
	
	
}



