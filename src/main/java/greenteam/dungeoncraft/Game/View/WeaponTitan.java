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

public class WeaponTitan extends GameObjectWithShader {
    
    private float flickerAmount; //used to produce a flicker effect when the gun the fired

    /* Constructor */
    public WeaponTitan(Shader inShader, Vec3f startPos, String nameIn) {
	super(inShader, startPos, nameIn);
	tranPos = new Vec3f(startPos.getX()+2, startPos.getY()-3, startPos.getZ()-5);
	transform = new Mat4f();
	Mat4f projection = new Mat4f();
	projection.projectionView( 70, 0.01f, 1000.f, GameEngine.getWindow().getWidth(), GameEngine.getWindow().getHeight() );
	transform.mul(projection, new Mat4f().identity() );
	init();
	initMesh();
	
    }
    
	@Override /* initialize the cube game object with a default cube mesh and a default texture */
	public void initMesh() {
		rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\gunUI.obj");
		Texture gunTex = new Texture();
		gunTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/gun.png");	
		rawMesh.attachTexture(gunTex);
		vaoID = rawMesh.getVaoID();
	}
	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
		shader.setUniformMat4f("transform", transform);
		shader.setUniformVec3f("v3fTranslate", tranPos); 
		shader.setUniformVec3f("lightPosition", new Vec3f(0, 0, 0));
		shader.setUniformf("lightIntensity",0f);
		shader.setUniformVec3f("lightColor", new Vec3f(0f,0f, 0f));
		if(Main.getPlayer().getShootLogic().getAmmoAmount() > 0) {
        		if (glfwGetMouseButton(GameEngine.getWindow().getWindow(), GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
        		    flickerAmount += 1f;
        		    shader.setUniformVec3f("lightPosition", new Vec3f(0, (float)Math.sin(flickerAmount)/3,  (float)Math.sin(flickerAmount)/2));
        		    shader.setUniformf("lightIntensity",0.25f);
        		}
	    }
	    }
		
	}
	

}
