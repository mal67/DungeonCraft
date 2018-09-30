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
import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Math.Mat4f;

public class GameObjectUI extends GameObject {

	protected boolean isActive;
	protected float pixelW;
	protected float pixelH;
	protected float currentXPosInScreenSpace;
	protected float currentYPosInScreenSpace;
	protected int zIndex;
	protected GameWindow wind;
	protected float uiElementWidth;
	protected float uiElementHeight;
	protected boolean mouseDown;
	protected Texture uiTexture;
	protected boolean hasAlpha;
	
	/* Constructor */
	public GameObjectUI(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndexIn,float width, float height, String nameIn) {
		name = nameIn;
		uiTexture = tex;
		transform = new Mat4f();
		wind = GameEngine.getWindow();
		pixelW = 1.0f / (wind.getWidth()/2);
		pixelH = 1.0f / (wind.getHeight()/2);
		currentXPosInScreenSpace = 0;
		currentYPosInScreenSpace = 0;
		zIndex = zIndexIn;
		uiElementWidth = width;
		uiElementHeight = height;
		mouseDown = false;
		hasAlpha = false;
		moveInScreen(xPosInScreenSpace,yPosInScreenSpace, zIndex);
		initMesh();
		init();
	}
	
	
	/* Constructor */
	public GameObjectUI(float xPosInScreenSpace, float yPosInScreenSpace, int zIndex,float width, float height, String nameIn, Mesh givenMesh) {
		name = nameIn;
		rawMesh = givenMesh;
		vaoID = rawMesh.getVaoID();
		init();
	}
	
	@Override
	public void init() {
		shader = GameEngine.getShaderManager().getDefaultUIShader();
		isActive = true;
	}
	
	

	/* initialize the UI game object */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\plane.obj");
		rawMesh.attachTexture(uiTexture);
		vaoID = rawMesh.getVaoID();
	}
	
	/* the function is called when the mouse is clicked on the ui element */
	public void onMouseClick() {
		System.out.println( "element clicked: " + name  );
	}

	
	/* inputs specific to the ui element (using the window (glfw callbacks) ) */
	public void inputCallbacks() {
		float elementYPosFromBottomUp = wind.getHeight() - currentYPosInScreenSpace; 
		if (glfwGetMouseButton(wind.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS && mouseDown == false) {
			mouseDown = true;
			if (wind.getMouseXPos() > currentXPosInScreenSpace && wind.getMouseXPos() < currentXPosInScreenSpace + uiElementWidth) {
				if (wind.getMouseYPos() < elementYPosFromBottomUp && wind.getMouseYPos() > elementYPosFromBottomUp - uiElementHeight) {
					onMouseClick();
				}
			}
		}
		
		if (glfwGetMouseButton(wind.getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
			mouseDown = false;
		}
	}
	
	/* moves the ui element in screen space */
	public void moveInScreen(float x,float y,int zIndexIn) {
		currentXPosInScreenSpace = x;
		currentYPosInScreenSpace = y;
		//move to bottom left of screen by translating by (-1,-1,0) then add the x and y positions
		//(for our default mesh which draws verts from 0->1 (center to the right+top edges))
		//the zIndex value is multiple by 10 to prevent it from being too close in world space
		transform.translation(-1+x*pixelW, -1+y*pixelH,0.0f).mul( new Mat4f()
				 .scaling(uiElementWidth * pixelW, uiElementHeight * pixelH, 0.1f));
	}

	@Override /* draw is called every frame (draws the mesh to the scene) */
	public void draw() {
		if (isActive) {
			if (hasAlpha) {
			    GL11.glEnable( GL11.GL_BLEND );
			    GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}
			
			glBindVertexArray(vaoID);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
			glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			glBindVertexArray(0); 
			
			if (hasAlpha) { //cleanup
			    GL11.glDisable( GL11.GL_BLEND );
			    GL11.glBlendFunc (GL11.GL_ONE, GL11.GL_ONE);
			}
		}
	}

	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		shader.setUniformMat4f("transform", transform);
		inputCallbacks();
	    }
	}
	
	    @Override
	    public void update() {
		inputCallbacks();
	    }

	public void setActive( boolean isActive ) {
		this.isActive = isActive;
	}


	public float getCurrentXPosInScreenSpace() {
		return currentXPosInScreenSpace;
	}
	
	public float getCurrentYPosInScreenSpace() {
		return currentYPosInScreenSpace;
	}


	public float getUiElementHeight() {
		return uiElementHeight;
	}


	public float getUiElementWidth() {
		return uiElementWidth;
	}


	public void setHasAlpha(boolean hasAlpha) {
	    this.hasAlpha = hasAlpha;
	}

}
