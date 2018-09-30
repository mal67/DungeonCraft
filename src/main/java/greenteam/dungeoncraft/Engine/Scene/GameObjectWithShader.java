package greenteam.dungeoncraft.Engine.Scene;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class GameObjectWithShader extends GameObject {

	protected ArrayList<GameObjectWithShader> childGameObjects = new ArrayList<GameObjectWithShader>();
	protected boolean isVisible;

	/* Constructor */
	public GameObjectWithShader(Shader inShader, Vec3f startPos, String nameIn) {
		name = nameIn;
		tranPos = new Vec3f(startPos.getX(), startPos.getY(), startPos.getZ());
		shader = inShader;
		init();
		initMesh();
	}
	
	/* Constructor */
	public GameObjectWithShader(Shader inShader, Vec3f startPos, String nameIn, Mesh givenMesh) {
		name = nameIn;
		tranPos = new Vec3f(startPos.getX(), startPos.getY(), startPos.getZ());
		shader = inShader;
		rawMesh = givenMesh;
		vaoID = rawMesh.getVaoID();
		init();
	}

	@Override
	public void init() {
		isVisible = true;
		isActive = true;
	}

	/* initialize the game object with a default cube mesh and a default texture */
	public void initMesh() {
		rawMesh = GameEngine.getSceneManager().rawCubeMesh;
		Texture defaultTex = new Texture();
		rawMesh.attachTexture(defaultTex);
		vaoID = rawMesh.getVaoID();
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
		shader.setUniformMat4f("transform", transform);
		shader.setUniformVec3f("v3fTranslate", tranPos); 
		shader.setUniformVec3f("lightPosition", new Vec3f(0, 0, 0));
	     }
	} 
	
	    @Override
	    public void update() {
	    }

}
