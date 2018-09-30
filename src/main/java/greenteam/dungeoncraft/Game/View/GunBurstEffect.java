package greenteam.dungeoncraft.Game.View;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
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
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObjectWithShader;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;
import greenteam.dungeoncraft.Game.Main;

public class GunBurstEffect extends GameObjectWithShader {

    private Texture gunBurstTex;
    private boolean burstActive;
    private float flickerAmount;
    
    /* Constructor */
    public GunBurstEffect(Shader inShader, Vec3f startPos, String nameIn) {
	super(inShader, startPos, nameIn);
	tranPos = new Vec3f(startPos.getX()+2, startPos.getY()-3, startPos.getZ()-5);
	transform = new Mat4f();
	Mat4f projection = new Mat4f();
	projection.projectionView( 70, 0.01f, 1000.f, GameEngine.getWindow().getWidth(), GameEngine.getWindow().getHeight() );
	transform.mul(projection, new Mat4f().identity() );
	
	init();
	initMesh();
    }
    
	@Override /* initialize the cube game object with a default cube mesh and a default texture */
	public void initMesh() {
		gunBurstTex = new Texture();
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\gunBurst.obj");
		gunBurstTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/weaponBurst.png");
		rawMesh.attachTexture(gunBurstTex);
		vaoID = rawMesh.getVaoID();
	}
	
	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		shader.setUniformMat4f("transform", transform);
		shader.setUniformVec3f("v3fTranslate", tranPos); 
		shader.setUniformVec3f("lightPosition", new Vec3f(0, 0, 0));
		shader.setUniformf("lightIntensity", 1f);
		shader.setUniformVec3f("lightColor", new Vec3f(0f,0f, 0f));
		burstActive = false;
		    
		if(Main.getPlayer().getShootLogic().getAmmoAmount() > 0) {
        		if (glfwGetMouseButton(GameEngine.getWindow().getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
        		    flickerAmount += 1f;
        		    shader.setUniformf("lightIntensity", 1f + (float)Math.sin(flickerAmount)/5);
        			 burstActive = true;
        		}
		}
	    }
	}
	
	@Override /* draw is called every frame (draws the mesh to the scene) */
	public void draw() {
		if (isVisible) {
		    GL11.glEnable (GL11.GL_BLEND);
			shader.bind();
			glBindVertexArray(vaoID);
			
			long millis = System.currentTimeMillis() % 1000;
			if (burstActive && millis % 4 == 0) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
			} else {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			}
			glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
			glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0);

			for (int i = 0; i < childGameObjects.size(); i++) {
				childGameObjects.get(i).draw();
			}
			shader.unbind();
		}
		GL11.glDisable (GL11.GL_BLEND);
	}
	
	

}
