package greenteam.dungeoncraft;


import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import greenteam.dungeoncraft.Engine.Shader.Shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;

public class ShaderTest {
	
	private Shader testShader;
	
	@Before /* Inorder to test the shader creation functions we create a simple example and attempt to link to a program in openGL */
	public void setUpBeforeClass_CreateContext() throws Exception {
		//a context window must exist for openGL functionality to be tested
		glfwInit();
		long window = glfwCreateWindow(1280, 1280, "test", NULL, NULL);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		testShader = new Shader();
	}
	
	@Test 
	public void testVertShader() {
		String vertShader = 
				"#version 330 core   							     \n" +
		        "								                                 \n" +  
		        "void main() {                                         \n" +
		        "gl_Position = vec4(1.0, 0.0, 0.0, 1.0);  \n" +
		        "}";
		testShader.createVertShader(vertShader);
		assert(GL20.glGetShaderi(testShader.getVertShaderID(), GL20.GL_COMPILE_STATUS) != GL_FALSE);
	}
	@Test
	public void testFragShader() {
		String fragShader = 
				"#version 330 core   							    	\n" +
		        "								                                 	\n" +  
		        "void main() {                                         	\n" +
		        "gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);  \n" +
		        "}";
		testShader.createFragShader(fragShader);
		assert(GL20.glGetShaderi(testShader.getFragShaderID(), GL20.GL_COMPILE_STATUS) != GL_FALSE);	
	}	
	
	@After
	public void testShaderLinkage() {
		testShader.link();
		assert(GL20.glGetProgrami(testShader.getProgramID(), GL20.GL_LINK_STATUS) != GL11.GL_FALSE);
	}
}
