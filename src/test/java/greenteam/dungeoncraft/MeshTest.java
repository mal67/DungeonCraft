package greenteam.dungeoncraft;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.Scene.Mesh;

import org.junit.Test;
import org.junit.Before;

/* In this class we are testing if certain addresses are received correctly and without error when creating a mesh */
public class MeshTest {
	
		@Before
		public void setUpBeforeClass_CreateContext() throws Exception {
			//a context window must exist for openGL functionality to be tested
			glfwInit();
			long window = glfwCreateWindow(1280, 1280, "test", NULL, NULL);
			glfwMakeContextCurrent(window);
			GL.createCapabilities();
		} 
		
		@Test
		public void checkVaoAddress() {
				Mesh testMesh = new Mesh();
				testMesh.defaultMeshInit();
				assert(testMesh.getVaoID() >= 0);
				assert(GL11.glGetError() ==GL11.GL_NO_ERROR);
		}
		@Test
		public void checkMeshBindability() {
			
			Mesh testMesh = new Mesh();
			//check if vaoID is bindable
			glBindVertexArray(testMesh.getVaoID());
	        glBindVertexArray(0);
	        assert(GL11.glGetError() ==GL11.GL_NO_ERROR);
		}
}
