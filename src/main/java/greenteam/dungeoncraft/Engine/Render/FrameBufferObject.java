package greenteam.dungeoncraft.Engine.Render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import greenteam.dungeoncraft.Engine.GameEngine;


//frame buffer object
public class FrameBufferObject {
    
    private int frameBufferID;
    private int textureID;
    
    public FrameBufferObject() {

    }

    public void init(int width, int height) {
	
	addFrameBufferObj();
	bindFrameBufferObj(width,height);
	addFrameBufferObjTexture(width,height);
    }
    
    
    public void addFrameBufferObj() {
	frameBufferID = GL30.glGenFramebuffers();
	GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
	GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
	
    }
    
    
    public void bindFrameBufferObj(int width, int height) {
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
        GL11.glViewport(0, 0, width, height);
    }
    
    public void unbindFrameBufferObj() {
	GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, GameEngine.getWindow().getWidth(), GameEngine.getWindow().getHeight());
    }
    
    
    public void addFrameBufferObjTexture(int width, int height) {
	textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width,height, 0,
		GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,0);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
        	textureID, 0);
    }

    public int getTextureID() {
        return textureID;
    }
    
    
}
