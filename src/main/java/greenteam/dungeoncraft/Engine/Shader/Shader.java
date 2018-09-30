package greenteam.dungeoncraft.Engine.Shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import greenteam.dungeoncraft.Engine.Math.FloatBuffers;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;

import greenteam.dungeoncraft.Util.FileReader;

public class Shader {

	private int fragID;
	private int programID;
	private int vertID;
	private HashMap<String, Integer> uniforms;

	/* Constructor (receive a programID after creating a program for the new shader */
	public Shader() {
		uniforms = new HashMap<String, Integer>();
		programID = glCreateProgram();
	}
	
	/* used to load the vertex and fragment shaders from given file locations (no default locations should the one given fail - currently) */
	public void loadShadersFromFiles(String vertSource, String fragSource) {
		if (vertSource == null) {
			throw new IllegalArgumentException("vertex shader source must not be null");
		}
		if (fragSource == null) {
			throw new IllegalArgumentException("frag shader source must not be null");
		}
		try {
			FileReader fr = new FileReader();

			createVertShader(fr.ReadFile(vertSource));
			createFragShader(fr.ReadFile(fragSource));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("could not create vert & frag shaders");
		}
	}

	/* this function compiles the shader from a text string and proceeds to attach the shader (giving a shader ID used for referencing) */
	public void attachShader(String text, int shaderID) {
		glShaderSource(shaderID, text);
		glCompileShader(shaderID);

		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL_FALSE) {
			throw new IllegalArgumentException("failed to compile shader");
		}

		glAttachShader(programID, shaderID);
	}

	/* creates and attaches the fragment shader, giving a fragment ID*/
	public void createFragShader(String text) {
		fragID = glCreateShader(GL_FRAGMENT_SHADER);
		if (fragID == 0) {
			throw new IllegalArgumentException("failed to create fragment shader");
		}
		attachShader(text, fragID);
	}
	/* creates and attaches the vertex shader, giving a vertex ID*/
	public void createVertShader(String text) {
		vertID = glCreateShader(GL_VERTEX_SHADER);
		if (vertID == 0) {
			throw new IllegalArgumentException("failed to create vertex shader");
		}
		attachShader(text, vertID);
	}
	/* creates a uniform the shader can use for storing/using data */
	public void createUniform(String uniform) {
		int unifLoc = glGetUniformLocation(programID, uniform);
		 if (unifLoc == -1) {
			 throw new IllegalArgumentException("failed to get uniform location");
		 }
		uniforms.put(uniform, unifLoc);
	}
	/* creates an integer uniform */
	public void setUniformi(String unifName, int val) {
		glUniform1i(uniforms.get(unifName), val);
	}
	
	/* creates a float uniform */
	public void setUniformf(String unifName, float val) {
		glUniform1f(uniforms.get(unifName), val);
	}

	/* creates a vector 3 (float) uniform */
	public void setUniformVec3f(String unifName, Vec3f val) {
		glUniform3f(uniforms.get(unifName), val.getX(), val.getY(), val.getZ());
	}

	/* creates a matrix 4 (float) uniform */
	public void setUniformMat4f(String unifName, Mat4f matrix) {
		FloatBuffers fbuff = new FloatBuffers();
		glUniformMatrix4fv(uniforms.get(unifName), true, fbuff.convertToFlippedBufferFromMat(matrix.getAsFloatArray()));
	}

	/* links the program for the shader */
	public void link() {
		glLinkProgram(programID);
		if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			throw new IllegalArgumentException("failed to link program for shaders");
		}
	}
	/* binds the program for the shader */
	public void bind() {
		glUseProgram(programID);
	}
	/* unbinds the program for the shader */
	public void unbind() {
		glUseProgram(0);
	}

	public int getVertShaderID() {
		return vertID;
	}

	public int getFragShaderID() {
		return fragID;
	}

	public int getProgramID() {
		return programID;
	}
	
}
