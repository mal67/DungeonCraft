package greenteam.dungeoncraft.Engine.Render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.Scene.GameObject;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.GameObjectText;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.GameObjectWithShader;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Engine.Shader.ShaderManager;




/* The Render Class  
 * --------------------
 * This class draws all the objects to the scene by calling a function draw() in all the active game objects.
 *  Any instanced or child game object will be drawn by the parent game object calling it's draw() function.
 * */
public class Renderer {
    
    
    public void updates(SceneManager sceneManager, ShaderManager shaderManager) {
	
		for (Entry<String, GameObject> entry : sceneManager.getGameObjHashMap().entrySet()) {
		    entry.getValue().update();
		}
		
		for (Entry<String, GameObject3D> entry : sceneManager.getGameObj3DHashMap().entrySet()) {
		    entry.getValue().update();
		}
		
		for (Entry<String, GameObjectWithShader> entry : sceneManager.getGameObjWithShaderHashMap().entrySet()) {
		    entry.getValue().update();
		}
	
		for (Entry<String, GameObjectUI> entry : sceneManager.getGameObjUIHashMap().entrySet()) {
		    entry.getValue().update();
		}

		for (Entry<String, GameObjectText> entry : sceneManager.getGameObjTextHashMap().entrySet()) {
		    entry.getValue().update();
		}
		
	
    }

    public void render(SceneManager sceneManager, ShaderManager shaderManager) {

	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glDepthFunc(GL11.GL_LESS);
	
	//render standard game objects
	for (Entry<String, GameObject> entry : sceneManager.getGameObjHashMap().entrySet()) {
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	}
	
	//render 3D game objects
	shaderManager.getDefaultShader().bind();
	sceneManager.getMainCam().update();
	
	
	for (Entry<String, GameObject3D> entry : sceneManager.getGameObj3DHashMap().entrySet()) {
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	}
	shaderManager.getDefaultShader().unbind();

	glClear(GL_DEPTH_BUFFER_BIT);
	GL11.glEnable(GL11.GL_DEPTH_TEST);
	GL11.glDepthFunc(GL11.GL_LESS);
	// render game objects with custom shaders
	for (Entry<String, GameObjectWithShader> entry : sceneManager.getGameObjWithShaderHashMap().entrySet()) {
	    entry.getValue().getShader().bind();
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	    entry.getValue().getShader().unbind();
	}


	//render UI game objects
	// clear the depth buffer for the scene objects to stop ui elements from overlapping
	GL11.glDisable(GL11.GL_DEPTH_TEST);
	GL11.glDepthFunc(GL11.GL_NEVER);  

	shaderManager.getDefaultUIShader().bind();

	for (Entry<String, GameObjectUI> entry : sceneManager.getGameObjUIHashMap().entrySet()) {
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	}
	shaderManager.getDefaultUIShader().unbind();

	//render text game objects
	shaderManager.getDefaultTextShader().bind();
	for (Entry<String, GameObjectText> entry : sceneManager.getGameObjTextHashMap().entrySet()) {
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	}
	shaderManager.getDefaultTextShader().unbind();





    } 
    
    public void renderMapVis(SceneManager sceneManager, ShaderManager shaderManager) {

	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	//GL11.glEnable(GL11.GL_DEPTH_TEST);
	//GL11.glDepthFunc(GL11.GL_LESS);
	//GL11.glFrontFace(GL11.GL_CW);
			//GL11.glCullFace(GL11.GL_FRONT);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			//transparency parameters
			GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBlendFunc (GL11.GL_ONE, GL11.GL_ONE);
			GL11.glEnable(GL11.GL_CULL_FACE);
	
	//render standard game objects
	for (Entry<String, GameObject> entry : sceneManager.getGameObjHashMap().entrySet()) {
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	}
	
	//render 3D game objects
	shaderManager.getDefaultShader().bind();
	sceneManager.getMapCam().update();
	
	
	for (Entry<String, GameObject3D> entry : sceneManager.getGameObj3DHashMap().entrySet()) {
	    entry.getValue().drawUpdate();
	    entry.getValue().draw();
	}
	shaderManager.getDefaultShader().unbind();

    }

    
    
    
    // TODO unit test delta time/framerate when implemented by executing render function x times

}
