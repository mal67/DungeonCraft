package greenteam.dungeoncraft.Game.View;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.Camera;
import greenteam.dungeoncraft.Game.Main;

public class PlayerCamera extends Camera {

    /* update is called every frame */
    @Override
    public void update() {
	if (isActive) {
	    // convert mouse positions to rotations for FPS Camera
	    float y = (float) ((wind.getMouseXPos() - (wind.getWidth() / 2)) / wind.getWidth() * 360);
	    float x = (float) ((wind.getMouseYPos() - (wind.getHeight() / 2)) / wind.getHeight() * 180);
	    eAngle.rotate(x, y, 0);
	    translate.translation(-camXPos, -camYPos, -camZPos);
	    mVP.mul(projVMat,eAngle.mul(translate));

	    // send new model_view_projection matrix to the shader
	    shader.setUniformMat4f("MVP", mVP);
	    shader.setUniformVec3f("lightPosition", new Vec3f(camXPos, camYPos, camZPos));
	    shader.setUniformf("lightIntensity",1.0f);
	    
	    if (Main.getPlayer().getShootLogic().getAmmoAmount() > 0) {
		if (glfwGetMouseButton(GameEngine.getWindow().getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
		    //flicker lighting effect when the gun is fired
		    flickerAmount += 1f;
		    shader.setUniformVec3f("lightPosition", new Vec3f(camXPos, camYPos + (float)Math.sin(flickerAmount)/5, camZPos));
		    shader.setUniformf("lightIntensity", 1 + (float)Math.sin(flickerAmount)/5);
		}

		if (glfwGetMouseButton(GameEngine.getWindow().getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
		    shader.setUniformVec3f("lightPosition", new Vec3f(camXPos, camYPos, camZPos));
		    shader.setUniformf("lightIntensity",1.0f);

		}
	    }
	}
    }

}
