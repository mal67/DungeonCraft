package greenteam.dungeoncraft.Engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.Render.*;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Engine.Scene.TextManager;
import greenteam.dungeoncraft.Engine.Shader.ShaderManager;

import static org.lwjgl.glfw.GLFW.*;

public class GameEngine {
	private static GameWindow wind;
	private static Inputs inps;
	private static Renderer rend;
	private static long windRef;
	private static FrameBufferObject fbo;
	
	private static ShaderManager shaderManager;
	private static SceneManager sceneManager;
	private static TextManager textManager;
	
	private static double timeAtLastFrame;
	
	/* initialize the game engine  */
	public static void Init() {
		wind = new GameWindow();
		wind.init();
		windRef = wind.getWindow();
		rend = new Renderer();
		inps = new Inputs();
		fbo = new FrameBufferObject();
		
		
		InitOpenGL();
		
		//managers
		shaderManager = new ShaderManager();
		shaderManager.init();
		sceneManager = new SceneManager();
		sceneManager.init(shaderManager.getDefaultShader(), wind);
		textManager = new TextManager();
		textManager.init();
	}
	
	/* initialize openGL and set gl parameters  */
	public static void InitOpenGL() {
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glFrontFace(GL11.GL_CW);
		//GL11.glCullFace(GL11.GL_FRONT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		//transparency parameters
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBlendFunc (GL11.GL_ONE, GL11.GL_ONE);
		//GL11.glEnable(GL11.GL_CULL_FACE);
		  GL11.glDepthMask(true);
	}

	public static SceneManager getSceneManager() {
		return sceneManager;
	}
	
	public static ShaderManager getShaderManager() {
		return shaderManager;
	}

	public static GameWindow getWindow() {
		return wind;
	}

	public static void setInputRefObject(Inputs inpRefObject) {
		inps = inpRefObject;
	}
	

	public static void dispose() {
	} // unit test dispose works when implementing

	/* deltaTime is the time since the last frame, used to make sure movement/physics/game logic 
	 * is more consistent with different frame rates  */
	public static double getDeltaTime() {
	    return (System.nanoTime() - timeAtLastFrame)*0.00000000005d;
	}
	
	static int framesPerSecond = 0;
	/* this function runs the engine until the game/window closes  */
	public static void run() {
		glfwShowWindow(windRef);
		long start = System.currentTimeMillis() / 1000;
		fbo.init(800, 600);
		while (!glfwWindowShouldClose(windRef)) { // main game loop
		    	
		    	//inputs
			inps.update();
			rend.updates(sceneManager, shaderManager);
			//renderer
			fbo.bindFrameBufferObj(800, 600);
			rend.renderMapVis(sceneManager,shaderManager);
			fbo.unbindFrameBufferObj();
			
			rend.render(sceneManager,shaderManager);
			//delta time & fps counter
			timeAtLastFrame = System.nanoTime();
			framesPerSecond++;
			if ((System.currentTimeMillis() / 1000 - start) == 1) {
			    //System.out.println( " second : framerate : " + framesPerSecond);
			    start = System.currentTimeMillis() / 1000;
			    framesPerSecond = 0;
			}	
			glfwSwapBuffers(windRef);
			glfwPollEvents();
		}
		dispose();
	}

	public static TextManager getTextManager() {
	    return textManager;
	}

	public static FrameBufferObject getFbo() {
	    return fbo;
	}
}
