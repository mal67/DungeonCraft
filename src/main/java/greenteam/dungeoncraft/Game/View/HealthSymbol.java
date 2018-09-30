package greenteam.dungeoncraft.Game.View;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Mat4f;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObjectWithShader;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.Player;

public class HealthSymbol extends GameObjectWithShader {
    
    private Player ply;
    private Vec3f color;

    /* Constructor */
    public HealthSymbol(Shader inShader, Vec3f startPos, String nameIn) {
	super(inShader, startPos, nameIn);
	tranPos = new Vec3f(startPos.getX()+2, startPos.getY()-3, startPos.getZ()-5);
	color = new Vec3f(0,0,1);
	transform = new Mat4f();
	Mat4f projection = new Mat4f();
	projection.projectionView( 70, 0.01f, 1000.f, GameEngine.getWindow().getWidth(), GameEngine.getWindow().getHeight() );
	transform.mul(projection, new Mat4f().identity() );
	init();
	initMesh();
	
	try {
	    ply = Main.getPlayer();
	} catch (Exception e) {
	    System.err.print("could not set ply variable to player");
	    e.printStackTrace();
	}
	
    }
    
	@Override /* initialize the cube game object with a default cube mesh and a default texture */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\healthSymbol.obj");
		Texture gunTex = new Texture();
		gunTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/empty.png");	
		rawMesh.attachTexture(gunTex);
		vaoID = rawMesh.getVaoID();
	}
	
	public void convertPlayerHealthToColor() {
	    if (ply.getHealth() > 80) {
		color.setXYZ(0, 1, 0);
	    } else if (ply.getHealth() > 60) {
		color.setXYZ(0.25f, 0.75f, 0);
	    } else if (ply.getHealth() > 40) {
		color.setXYZ(0.5f, 0.5f, 0.0f);
	    } else if (ply.getHealth() > 20) {
		color.setXYZ(0.75f, 0.25f, 0.0f);
	    } else if (ply.getHealth() > 0) {
		color.setXYZ(2f, 0.0f, 0.0f);
	    }
	    
	}
	
	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		shader.setUniformMat4f("transform", transform);
		shader.setUniformVec3f("v3fTranslate", tranPos); 
		shader.setUniformVec3f("lightPosition",color);
		shader.setUniformf("lightIntensity",0f);
		shader.setUniformVec3f("lightColor",color);
	    }
		
	}
	
	@Override
	public void update() {
	    convertPlayerHealthToColor();
	}

}
