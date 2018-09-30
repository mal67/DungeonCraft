package greenteam.dungeoncraft.Game.View;

import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObject3D;
import greenteam.dungeoncraft.Engine.Scene.Mesh;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class PlayerBullet extends GameObject3D {

    /* Constructor */
    public PlayerBullet(Vec3f startPos, String name) {
	super(startPos, name);
    }

    /* Constructor */
    public PlayerBullet(Shader inShader, Vec3f startPos, String name, Mesh givenMesh) {
	super(startPos, name, givenMesh);
    }

    @Override /* initialize the game object with mesh and texture data */
    public void initMesh() {
	rawMesh = new Mesh();
	rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\fireBall.obj");
	Texture fireBallTex = new Texture();
	fireBallTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/red.png");
	rawMesh.attachTexture(fireBallTex);
	vaoID = rawMesh.getVaoID();
    }
}
