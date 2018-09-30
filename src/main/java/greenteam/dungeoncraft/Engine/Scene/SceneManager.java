package greenteam.dungeoncraft.Engine.Scene;

import java.util.HashMap;

import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Shader.Shader;

public class SceneManager {

    public Camera mainCamera;
    public MapCamera mapCam;
    private HashMap<String, GameObject> sceneGameObjects = new HashMap<String, GameObject>();
    private HashMap<String, GameObject3D> sceneGameObjects3D = new HashMap<String, GameObject3D>();
    private HashMap<String, GameObjectUI> sceneGameObjectsUI = new HashMap<String, GameObjectUI>();
    private HashMap<String, GameObjectWithShader> sceneGameObjectsWithShader = new HashMap<String, GameObjectWithShader>();
    private HashMap<String, GameObjectText> sceneGameObjectsText = new HashMap<String, GameObjectText>();
    
    public Mesh rawCubeMesh;
    private Shader defaultShader;

    /* initialize the scene manager */
    public void init(Shader newDefShader, GameWindow newWind) {
	// TODO check if GL.createCapabilities(); is set
	defaultShader = newDefShader;
	rawCubeMesh = new Mesh();

	// set pointer buffers
	for (int i = 0; i < 1000; i++) {
	   rawCubeMesh.defaultMeshInit();
	}

	mainCamera = new Camera();
	mainCamera.setShader(defaultShader);
	mainCamera.setWindow(newWind);
	mainCamera.init();
	
	mapCam = new MapCamera();
	mapCam.setShader(defaultShader);
	mapCam.setWindow(newWind);
	mapCam.init();
    }
    
    /*
     * add a game object to an object array which is used in the render class to
     * draw to game objects to the scene
     */
    public void addGameObject(String tag, GameObject gameObjIn) {
	try {
	    // TODO check gobj initialization
	    sceneGameObjects.put(tag, gameObjIn);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException("failed to add gameobject to scene map");
	}
    }

    /*
     * add a 3D game object to an object array which is used in the render class to
     * draw to game objects to the scene
     */
    public void addGameObject3D(String tag, GameObject3D gameObjIn) {
	try {
	    // TODO check gobj initialization
	    sceneGameObjects3D.put(tag, gameObjIn);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException("failed to add 3d gameobject to scene map");
	}
    }

    /*
     * add a UI game object to an object array which is used in the render class to
     * draw to game objects to the scene
     */
    public void addGameObjectUI(String tag, GameObjectUI gameObjIn) {
	try {
	    // TODO check gobj initialization
	    sceneGameObjectsUI.put(tag, gameObjIn);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException("failed to add ui gameobject to scene map");
	}
    }
    

    /*
     * add a Text game object to an object array which is used in the render class to
     * draw to text character game objects to the scene
     */
    public void addGameObjectText(String tag, GameObjectText gameObjIn) {
	try {
	    // TODO check gobj initialization
	    sceneGameObjectsText.put(tag, gameObjIn);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException("failed to add text gameobject to scene map");
	}
    }

    /*
     * add a game object with a custom shader to an object array which is used in
     * the render class to draw to game objects to the scene
     */
    public void addGameObjectWithShader(String tag, GameObjectWithShader gameObjWithShader) {
	try {
	    // TODO check gobj initialization
	    sceneGameObjectsWithShader.put(tag, gameObjWithShader);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException("failed to add gameobject with a custom shader to scene map");
	}
    }

    public void setDefaultShader(Shader newDefaultShader) {
	defaultShader = newDefaultShader;
    }

    public Camera getMainCam() {
	return mainCamera;
    }

    public HashMap<String, GameObject3D> getGameObj3DHashMap() {
	return sceneGameObjects3D;
    }

    public HashMap<String, GameObjectUI> getGameObjUIHashMap() {
	return sceneGameObjectsUI;
    }

    public HashMap<String, GameObjectWithShader> getGameObjWithShaderHashMap() {
	return sceneGameObjectsWithShader;
    }

    public HashMap<String, GameObjectText> getGameObjTextHashMap() {
        return sceneGameObjectsText;
    }

    public HashMap<String, GameObject> getGameObjHashMap() {
        return sceneGameObjects;
    }

    public MapCamera getMapCam() {
        return mapCam;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

}
