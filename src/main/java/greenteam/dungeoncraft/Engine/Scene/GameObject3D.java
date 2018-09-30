package greenteam.dungeoncraft.Engine.Scene;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;

public class GameObject3D extends GameObject {

    protected ArrayList<GameObject3D> childGameObjects = new ArrayList<GameObject3D>();
    protected GameObject3D parent;

    /* Constructor */
    public GameObject3D(Vec3f startPos, String nameIn) {
	name = nameIn;
	usesInstancing = true; //uses instances buffer binding by default (inorder to position the object without the instance 
	//buffer, the position must be sent by a shader uniform via a shader.uniformMat4f or shader.uniformVec3f call in update.
	//sending a number of instance data this way is greatly slower so binding to a buffer is method currently used.

	//for instanced objects the transform position is kept in a separate vector as instances will only be using 
	//this vector for their individuals transforms relative to the original object
	//(rotation/scaling would be done in the original object using the transform matrix then used for all instances)
	tranPos = new Vec3f(startPos.getX(), startPos.getY(), startPos.getZ());
	initMesh();
	init();
    }

    /* Constructor */
    public GameObject3D(Vec3f startPos, String nameIn, Mesh givenMesh) {
	name = nameIn;
	tranPos = new Vec3f(startPos.getX(), startPos.getY(), startPos.getZ());
	rawMesh = givenMesh;
	vaoID = rawMesh.getVaoID();
	init();
    }

    @Override
    public void init() {
	if (!isInstance) {
	    //set the default transform matrix to an identity matrix
	    transform = new Mat4f().identity();  
	}

	shader = GameEngine.getShaderManager().getDefaultShader();
	isActive = true;
	isTransparent = false;
    }

    /* initialize the cube game object with a default cube mesh and a default texture */
    public void initMesh() {
	rawMesh = new Mesh();
	rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\cube.obj");
	Texture defaultTex = new Texture();
	defaultTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/wall.png");	
	rawMesh.attachTexture(defaultTex);
	vaoID = rawMesh.getVaoID();
    }

    @Override /* draw is called every frame (draws the mesh to the scene) */
    public void draw() {
	if (isActive) {
	    if (isTransparent) {
		GL11.glEnable(GL11.GL_BLEND);
	    }

	    if (childGameObjects.size() > rawMesh.getInstance3DData().length / 3 && usesInstancing) {
		addInstancePositionsToBufferForDrawing();
	    }

	    if (!isInstance) {
		glBindVertexArray(vaoID);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
		//glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
	    }
	    glDrawElementsInstanced(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0, 1+childGameObjects.size());
	    // clean up
	    if (!isInstance) {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	    }
	    if (isTransparent) {
		GL11.glDisable(GL11.GL_BLEND);
	    }
	}
    }


    /* used to find an instanced object by name */
    public GameObject3D getInstanceObjByName(String instanceName) { //TODO: sort out finding error and return
	try {
	    for (int i = 0; i < childGameObjects.size(); i++) {
		if (childGameObjects.get(i).name.equals(instanceName)) {
		    return childGameObjects.get(i);
		}
	    }
	} catch (Exception e) {
	    System.err.println("Could not find GameObject Instance");
	}
	return this;
    }
    
    public void addInstancePositionsToBufferForDrawing() {
	float[] instancePositionData = new float[3*childGameObjects.size() + 3];

	//add the translation for the starting object to the instance buffer
	instancePositionData[0] = tranPos.getX();
	instancePositionData[1] = tranPos.getY();
	instancePositionData[2] = tranPos.getZ();

	//add the translation for any child object (if they exist) to the instance buffer
	for (int i = 0, k = 3; i < childGameObjects.size(); i++, k+=3) {
	    instancePositionData[k] = childGameObjects.get(i).getTranPos().getX();
	    instancePositionData[k+1] = childGameObjects.get(i).getTranPos().getY();
	    instancePositionData[k+2] = childGameObjects.get(i).getTranPos().getZ();
	}

	rawMesh.bindInstance3DPositions(instancePositionData);

    }

    /* for performance purposes, the 3d game object can be instanced (the mesh buffer information is used to display all active cubes) */
    public void addInstanceGameObj(Vec3f startPos, String nameIn) {
	try {
	    GameObject3D newInstance = new GameObject3D(startPos, nameIn, rawMesh);
	    newInstance.isInstance = true;
	    newInstance.transform = transform;
	    newInstance.parent = this;
	    childGameObjects.add(newInstance);
	} catch (Exception e) {
	    System.err.println("Could not add GameObject Instance");
	}
    }

    public void removeInstance(String instanceName) {
	try {
	    for (int i = 0; i < childGameObjects.size(); i++) {
		if (childGameObjects.get(i).name.equals(instanceName)) {
		    childGameObjects.remove(i);

		    //update instance buffer positions (to not draw the object that was removed)
		    addInstancePositionsToBufferForDrawing();
		}
	    }

	} catch (Exception e) {
	    System.err.println("Could not remove GameObject Instance");

	}
    }
    
   public void removeAllInstances() {
       try {
	   childGameObjects.clear();
		    //update instance buffer positions (to not draw the object that was removed)
		
	    addInstancePositionsToBufferForDrawing();
	    

	} catch (Exception e) {
	    System.err.println("Could not remove all GameObject Instances");

	}
   }

    @Override /* update is called every frame by the draw function */
    public void drawUpdate() {
	if (isActive) {
	    if (!isInstance) {
		//if the game object has no children then update the buffer positions constantly
		if (childGameObjects.size()==0) {
		    addInstancePositionsToBufferForDrawing();
		}
		//send transform, uv and color parameters to the shader
		shader.setUniformMat4f("transform", transform);
		shader.setUniformi("uvScale", 1);
		shader.setUniformVec3f("diffuseColor", new Vec3f(1,1,1));
	    }
	}
    }

    @Override
    public void update() {
	// TODO Auto-generated method stub
	
    }
    
    
    public void dispose() {
	//delete buffers from the mesh
	rawMesh.dispose();
	childGameObjects.clear();
    }
    

}
