package greenteam.dungeoncraft.Game.View.ui;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Controller.UIMenuController;


public class UIMenu {

    private UIMenuController uiMenuController;
    private ExitButton uiExit;
    private HelpButton uiHelp;
    private LeaderboardButton uiLeaderboard;
    private ResumeButton uiResume;
    private MainPanel uiMainPanel;
    private ExitPanel uiExitPanel;
    private DeathPanel uiDeathPanel;
    private LeaderBoardPanel uiLeaderBoardPanel;
    private SceneManager sceneM;
    private HelpPanel uiHelpPanel;
    

    public UIMenu() {
	uiMenuController = new UIMenuController();
    }

    public void init() {
	uiMenuController.init();
	sceneM = GameEngine.getSceneManager();
	//get the center of the screen with an offset to display on different screen resolutions
	float xPos = GameEngine.getWindow().getWidth()/2-400;
	float yPos = GameEngine.getWindow().getHeight()/2-200;
	float yOffset = 20;
	float xOffset = 20;
	
	//load ui textures to texture objects
	Texture uiResumeTex = new Texture();
	Texture uiExitTex = new Texture();
	Texture uiLeaderBoardTex = new Texture();
	Texture uiDeathPanelTex = new Texture();
	Texture uiLeaderBoardPanelTex = new Texture();
	Texture uiHelpPanelTex = new Texture();
	uiResumeTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/resume.png");
	uiExitTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/exit.png");
	uiLeaderBoardTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/leaderboard.png");
	uiDeathPanelTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/deathpanel.png");
	uiLeaderBoardPanelTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/deathpanel.png");
	uiHelpPanelTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/resume.png");
	
	//all the ui elements use the first ui element (exit button) to position themselves, which helps with resizing/ positioning the ui
	//for multiple resolutions.

	//exit button
	uiExit = new ExitButton(uiExitTex,xPos,yPos,0,200,100, "uiExit");	
	uiExit.setActive( false );

	//help button
	float yOffsetFromPrevElement = uiExit.getCurrentYPosInScreenSpace() + uiExit.getUiElementHeight() + yOffset;		
	uiHelp = new HelpButton(uiResumeTex,xPos,yOffsetFromPrevElement,0,200,100, "uiHelp"); 
	uiHelp.setActive( false );

	//leaderboard button
	yOffsetFromPrevElement = uiHelp.getCurrentYPosInScreenSpace() + uiHelp.getUiElementHeight() + yOffset;
	uiLeaderboard = new LeaderboardButton(uiLeaderBoardTex,xPos,yOffsetFromPrevElement,0,200,100, "uiLeaderboard"); 
	uiLeaderboard.setActive( false );

	//resume button
	yOffsetFromPrevElement = uiLeaderboard.getCurrentYPosInScreenSpace() + uiLeaderboard.getUiElementHeight() + yOffset;
	uiResume = new ResumeButton(uiResumeTex,xPos,yOffsetFromPrevElement,0,200,100, "uiResume"); 
	uiResume.setActive( false );
	
	//ui main panel
	float xOffsetFromPrevElement = uiExit.getCurrentXPosInScreenSpace() + uiExit.getUiElementWidth() + xOffset;
	yOffsetFromPrevElement = uiExit.getCurrentYPosInScreenSpace();
	uiMainPanel = new MainPanel(uiResumeTex,xOffsetFromPrevElement, yOffsetFromPrevElement,0,500,400 + yOffset*3, "uiMainPanel"); 
	uiMainPanel.setActive( false );
	
	//ui exit panel
	uiExitPanel = new ExitPanel(uiLeaderBoardTex,xOffsetFromPrevElement, yOffsetFromPrevElement,0,500,400 + yOffset*3, "uiExitPanel"); 
	uiExitPanel.setActive( false );
	
	//ui death panel
	uiDeathPanel = new DeathPanel(uiDeathPanelTex,xOffsetFromPrevElement, yOffsetFromPrevElement,0,500,400 + yOffset*3, "uiDeathPanel"); 
	uiDeathPanel.setActive( false );
	
	//ui leaderboard panel
	uiLeaderBoardPanel = new LeaderBoardPanel(uiLeaderBoardPanelTex,xOffsetFromPrevElement, yOffsetFromPrevElement,0,500,400 + yOffset*3, "uiLeaderBoardPanel"); 
	uiLeaderBoardPanel.setActive( false );
	
	//ui help panel
	uiHelpPanel = new HelpPanel(uiHelpPanelTex,xOffsetFromPrevElement-250, yOffsetFromPrevElement,0,800,400 + yOffset*3, "uiHelpPanel"); 
	uiHelpPanel.setActive( false );

	//add ui elements (game objects) to the scene
	
	//buttons
	sceneM.addGameObjectUI(uiExit.name, uiExit);
	sceneM.addGameObjectUI(uiHelp.name, uiHelp);
	sceneM.addGameObjectUI(uiLeaderboard.name, uiLeaderboard);
	sceneM.addGameObjectUI(uiResume.name, uiResume);
	
	//panels
	sceneM.addGameObjectUI(uiMainPanel.name, uiMainPanel);
	sceneM.addGameObjectUI(uiExitPanel.name, uiExitPanel);
	sceneM.addGameObjectUI(uiDeathPanel.name, uiDeathPanel);
	sceneM.addGameObjectUI(uiHelpPanel.name, uiHelpPanel);
	sceneM.addGameObjectUI(uiLeaderBoardPanel.name, uiLeaderBoardPanel);
	//controller callback
	uiMenuController.setEscCallback();
    }

    public UIMenuController getUiMenuController() {
        return uiMenuController;
    }

    public DeathPanel getUiDeathPanel() {
        return uiDeathPanel;
    }
}
