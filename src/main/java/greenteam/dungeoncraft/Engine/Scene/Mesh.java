package greenteam.dungeoncraft.Engine.Scene;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import greenteam.dungeoncraft.Engine.Math.FloatBuffers;

public class Mesh {

	protected float[] verts;
	protected int[] indices;
	protected float[] colors;
	protected float[] texCoords;
	protected float[] normals;
	protected float[] instance3DData;
	
	protected int instanceID;
	protected int vaoID;
	protected int textureID;
	protected int elementID;
	protected int vertID;
	protected int normalID;
	protected int vertCount;
	protected FloatBuffers fBuff;
	protected MeshLoader meshLoader;
	protected String meshSource;

	/* Constructor */
	public Mesh() {
	    	instance3DData = new float[1];
	    	instanceID = -1;
		fBuff = new FloatBuffers();
		meshLoader = new MeshLoader();
		
	}

	/* bind the UV data to the graphic card */
	public void bindUVs() {
		FloatBuffer texCBuffer = fBuff.convertToFlippedBufferFromVector(texCoords);
		textureID = glGenTextures();
		glBindBuffer(GL_ARRAY_BUFFER, textureID);
		glBufferData(GL_ARRAY_BUFFER, texCBuffer, GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/* bind the texture data to the graphic card */
	public void attachTexture(Texture texureIn) {
		
		if (textureID == 0) {
			// have not bound the uvs
			System.err.println("you must bind the UVs before binding a texture");
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glDeleteTextures( textureID );
		glBindTexture(GL_TEXTURE_2D, textureID);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texureIn.getTexWidth(), texureIn.getTexHeight(), 0,
		GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texureIn.getBuf());
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/* bind instance position data to the graphic card */
	public void bindInstance3DPositions(float[] instance3DDataIn) {
	    glBindVertexArray(vaoID);
	    	instance3DData = instance3DDataIn;
	    
		FloatBuffer instanceData = fBuff.convertToFlippedBufferFromVector(instance3DData);
		if (instanceID == -1) {
		    instanceID = glGenBuffers();
		}
		glBindBuffer(GL_ARRAY_BUFFER, instanceID);	
		glBufferData(GL_ARRAY_BUFFER, instanceData, GL_DYNAMIC_DRAW);
		GL20.glEnableVertexAttribArray(4);
		glVertexAttribPointer(4, 3, GL_FLOAT, false,0, 0);
		glVertexAttribDivisor(4,1);
		glBindBuffer(GL_ARRAY_BUFFER, 0); //deselect buffer
		glBindVertexArray(0);
	}
	
	/* bind the vertex and indices data to the graphic card */
	public void bindVerts() {
		vertCount = indices.length;

		FloatBuffer vertsBuffer = fBuff.convertToFlippedBufferFromVector(verts);
		IntBuffer indicesBuffer = fBuff.convertToFlippedBufferFromIntVector(indices);

		vertID = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vertID);	
		glBufferData(GL_ARRAY_BUFFER, vertsBuffer, GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false,3*4, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0); //deselect buffer
		
		elementID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);  //deselect buffer
	}

	/* bind the normal data to the graphic card */
	public void bindNormals() {
	    GL20.glEnableVertexAttribArray(3);
		FloatBuffer normalBuffer = fBuff.convertToFlippedBufferFromVector(normals);
		normalID = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, normalID);
		glBufferData(GL_ARRAY_BUFFER, normalBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);  //deselect buffer
	}

	/* bind the color data to the graphic card */
	public void bindColors() {
		FloatBuffer colBuffer = fBuff.convertToFlippedBufferFromVector(colors);

		int vboColID = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vboColID);
		glBufferData(GL_ARRAY_BUFFER, colBuffer, GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(1);
		glVertexAttribPointer(1,4, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);  //deselect buffer
	}

	/* bind the all the mesh data to the graphic card */
	public void bindMeshData() {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
	
		bindVerts();
		bindColors();
		bindUVs();
		bindNormals();
	
		glBindVertexArray(0);
	}

		public void defaultMeshInit() {
		    
			//vertex coordinates
			verts = new float[] {0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1,};	
			//color coordinates
			colors = new float[] { 0.5f };
			//indices numbers, used to reference vertex positions			
			indices = new int[] { 0, 1, 2, 2, 3, 0,};		
			//texture coordinates
			texCoords = new float[] {0, 1,0, 0.75f, 0.25f, 0.75f, 0.25f, 1,};			
			//normal coordinates
			normals = new float[] { 0, 0, 1, 0, };
			
			bindMeshData();
		}

	
	/* the mesh initialization function loads a mesh from source then binds the mesh data */
	public void init(String meshSourceIn) {
		meshSource = meshSourceIn;
		meshLoader.loadFromFile(meshSource);
		
		verts = meshLoader.vertArr;
		texCoords = meshLoader.texArr;
		indices = meshLoader.indicesArr;
		normals = meshLoader.normalArr;

		colors = new float[] { 0.5f };
		bindMeshData();
	}

	public void dispose() {
	   

	    // Dispose the buffer object
	    glBindVertexArray(vaoID);
	    
	    glBindBuffer(GL_ARRAY_BUFFER, 0);
	    GL15.glDeleteBuffers(instanceID);
	    GL11.glDeleteTextures(textureID);
	   // GL15.glDeleteBuffers(textureID);
	    GL15.glDeleteBuffers(elementID);
	    GL15.glDeleteBuffers(vertID);
	    GL15.glDeleteBuffers(normalID);
	    
	    // Dispose the vertex array
	    glBindVertexArray(0);
	    GL30.glDeleteVertexArrays(vaoID);
	}

	public int getVaoID() {
		return vaoID;
	}
	
	public int getElementID() {
		return elementID;
	}
	
	public int getVertID() {
		return vertID;
	}

	public int getVertCount() {
		return vertCount;
	}

	public int getTextureID() {
		return textureID;
	}

	public float[] getInstance3DData() {
	    return instance3DData;
	}
}
