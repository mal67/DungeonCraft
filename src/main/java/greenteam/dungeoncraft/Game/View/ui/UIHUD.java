package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObjectText;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Engine.Shader.Shader;
import greenteam.dungeoncraft.Game.View.CubeWithBar;
import greenteam.dungeoncraft.Game.View.GunBurstEffect;
import greenteam.dungeoncraft.Game.View.HealthSymbol;
import greenteam.dungeoncraft.Game.View.WeaponTitan;

public class UIHUD {
    
    private Crosshair crosshair;
    private MapVisPanel mapVis;
    private MapVisPlayer mapVisPlayer;
    private WeaponTitan weaponUI;
    private HealthSymbol healthUI;
    private GunBurstEffect gunBurstEffect;
    public UIHUD() {}
    
    public void init() {
	initCrosshair();
	initWeaponAndWeaponUIGameObjects();
	
    }
    
    public void initCrosshair() {
	SceneManager sceneM = GameEngine.getSceneManager();

	//load ui textures to texture objects
	Texture crosshairTex = new Texture();
	crosshairTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/crosshair.png");
	
	float xPos = GameEngine.getWindow().getWidth()/2;
	float yPos = GameEngine.getWindow().getHeight()/2;

	crosshair = new Crosshair(crosshairTex,xPos-25,yPos-25,-10,50,50, "crosshair");	
	sceneM.addGameObjectUI(crosshair.name, crosshair);
	
	//ui element
	mapVis = new MapVisPanel(crosshairTex,xPos*2-300,yPos*2-300,0,250,250, "mapvis");	
	sceneM.addGameObjectUI(mapVis.name, mapVis);
	//3d element used for ui (map vis)
	mapVisPlayer = new MapVisPlayer(new Vec3f(0,0, 0), "mapvisplayer");
	sceneM.addGameObject3D(mapVisPlayer.name, mapVisPlayer);
	drawScoreHUD();
    }
    
    public void drawScoreHUD() {
	
	SceneManager sceneM = GameEngine.getSceneManager();
	Texture scorePanelTex = new Texture();
	scorePanelTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/scorepanel.png");
	ScoreUI scorePanel = new ScoreUI(scorePanelTex,20,20,0,350,75, "scorePanel");	
	scorePanel.setHasAlpha(true);
	sceneM.addGameObjectUI(scorePanel.name, scorePanel);
	
	Texture ammoPanelTex = new Texture();
	ammoPanelTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/red.png");
	AmmoUI ammoPanel = new AmmoUI(ammoPanelTex,400,20,0,75,75, "ammoPanelTex");	
	ammoPanel.setHasAlpha(true);
	sceneM.addGameObjectUI(ammoPanel.name, ammoPanel);
	
    }
    
    
    public void hideUIHUD() {
	try {
	    crosshair.setActive(false);
	    mapVis.setActive(false);
	    weaponUI.setActive(false);
	    healthUI.setActive(false);
	    gunBurstEffect.setActive(false);
	} catch (Exception e) {
	    System.err.println("could not hide all UI HUD game objects in the UIHUD class");
	    e.printStackTrace();
	}
    }
    
    
    public void showUIHUD() {
  	try {
	    crosshair.setActive(true);
	    mapVis.setActive(true);
	    weaponUI.setActive(true);
	    healthUI.setActive(true);
	    gunBurstEffect.setActive(true);
	} catch (Exception e) {
	    System.err.println("could not show all UI HUD game objects in the UIHUD class");
	    e.printStackTrace();
	}
      }
    
    
    
	public void initWeaponAndWeaponUIGameObjects() {
		//move inside of a class gameobject with custom shader?
		Shader weaponShader = new Shader();
		weaponShader.loadShadersFromFiles("\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\weapon_ui_vert.vs", "\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\weapon_ui_frag.fs");
		weaponShader.link();
		weaponShader.bind();
		weaponShader.createUniform("v3fTranslate");
		weaponShader.createUniform("transform");
		weaponShader.createUniform("lightPosition");
		weaponShader.createUniform("lightIntensity");
		weaponShader.createUniform("lightColor");
		
		
		//create hud + weapon game objects
		SceneManager sceneM = GameEngine.getSceneManager();
		weaponUI = new WeaponTitan(weaponShader,new Vec3f(0, 0, 0), "weaponUI"); 
		healthUI = new HealthSymbol(weaponShader,new Vec3f(0, 0, 0), "healthUI"); 
		gunBurstEffect = new GunBurstEffect(weaponShader,new Vec3f(0, 0, 0), "gunBurstEffect"); 
		
		//add game objects to the scene
		sceneM.addGameObjectWithShader(gunBurstEffect.name, gunBurstEffect);
		sceneM.addGameObjectWithShader(weaponUI.name, weaponUI);
		sceneM.addGameObjectWithShader(healthUI.name, healthUI);
	}
	

}
