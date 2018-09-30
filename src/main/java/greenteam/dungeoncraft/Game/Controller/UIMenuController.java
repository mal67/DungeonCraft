package greenteam.dungeoncraft.Game.Controller;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Scene.SceneManager;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.View.ui.DeathPanel;
import greenteam.dungeoncraft.Game.View.ui.HelpPanel;
import greenteam.dungeoncraft.Game.View.ui.LeaderBoardPanel;



public class UIMenuController {

    private boolean isMenuActive;
    private SceneManager sceneM;
    private boolean startGame;
    public void init() {
	sceneM = GameEngine.getSceneManager();
	startGame = true;
    }
    
    /* called by exit button */
    public void showExitGamePanel() {
	if (isMenuActive) {
	try {
	    sceneM.getGameObjUIHashMap().get("uiMainPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderBoardPanel").setActive(false);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("could not find main panel ui game object in showExitGamePanel function (uiMenuController)");
	}
	
	
	try {
	    sceneM.getGameObjUIHashMap().get("uiExitPanel").setActive(true);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("could not find exit panel ui game object in showExitGamePanel function (uiMenuController)");
	}
	}
    }
    
    
    public void showDeathScreen() {
	try {
	    GameEngine.getWindow().enableMouseCursor();
	    Main.getUiHUD().hideUIHUD();
	    Main.getPlayer().getCam().setActive(false);
	    Main.getPlayer().getMoves().setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiDeathPanel").setActive(true);
	    DeathPanel deathPanel = (DeathPanel)sceneM.getGameObjUIHashMap().get("uiDeathPanel");
	    deathPanel.displayLeaderBoard();
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("could not find uiDeathPanel and show the Death Screen");
	}
    }
    
    /* called by leaderboard button */
    public void showLeaderBoardPanel() {
	if (isMenuActive) {
	try {
	    sceneM.getGameObjUIHashMap().get("uiMainPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExitPanel").setActive(false);
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("could not find main panel ui game object in showLeaderBoardPanel function (uiMenuController)");
	}
	
	try {
	    sceneM.getGameObjUIHashMap().get("uiLeaderBoardPanel").setActive(true);
	    LeaderBoardPanel leaderboardPanel = (LeaderBoardPanel)sceneM.getGameObjUIHashMap().get("uiLeaderBoardPanel");
	    leaderboardPanel.displayLeaderBoard();
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("could not find leaderboard panel ui game object in showLeaderBoardPanel function (uiMenuController)");
	}
	}
    }
    
    /* called at game start */
    public void showHelpPanel() {
	if (isMenuActive || startGame) {
	    startGame = false;
	try {
	    GameEngine.getWindow().enableMouseCursor();
	    Main.getUiHUD().hideUIHUD();
	    Main.getPlayer().getCam().setActive(false);
	    Main.getPlayer().getMoves().setActive(false);
	    
	    sceneM.getGameObjUIHashMap().get("uiExit").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiHelp").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderboard").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiMainPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExitPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiResume").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderBoardPanel").setActive(false);
	    
	    sceneM.getGameObjUIHashMap().get("uiHelpPanel").setActive(true);
	    HelpPanel helpPanel = (HelpPanel)sceneM.getGameObjUIHashMap().get("uiHelpPanel");
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("could not find uiHelpPanel and show the Death Screen");
	}
	}
    }
    
    
    
    public void exitGame() {
	System.exit(0);
    }
    
    
    /* called by resume button */
    public void resumeGame() {
	try {
	    GameEngine.getWindow().disableMouseCursor();
	    Main.getUiHUD().showUIHUD();
	    Main.getPlayer().getCam().setActive(true);
	    Main.getPlayer().getMoves().setActive(true);
	    sceneM.getGameObjUIHashMap().get("uiDeathPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiHelpPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExit").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiHelp").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderboard").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiMainPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExitPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiResume").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderBoardPanel").setActive(false);
	    isMenuActive = false;
	} catch (Exception e) {
	    System.err.println("failed to set UI menu objects by name in the scene manager boolean isActive to false, "
		    + "were the objects added to the sceneManager? ");
	    e.printStackTrace();
	}
    }
    
    /* called by restart button */
    public void restartGame() {
	try {
	    
	    GameEngine.getWindow().disableMouseCursor();
	    Main.getUiHUD().showUIHUD();
	    Main.getPlayer().getCam().setActive(true);
	    Main.getPlayer().getMoves().setActive(true);
	    sceneM.getGameObjUIHashMap().get("uiDeathPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiHelpPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExit").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiHelp").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderboard").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiMainPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExitPanel").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiResume").setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiLeaderBoardPanel").setActive(false);
	    isMenuActive = false;
	    Main.getMap().restartToFirstMap();
	} catch (Exception e) {
	    System.err.println("failed to set UI menu objects by name in the scene manager boolean isActive to false, "
		    + "were the objects added to the sceneManager? ");
	    e.printStackTrace();
	}
    }
    

    public void openMenu() {
	try {
	    GameEngine.getWindow().enableMouseCursor();
	    Main.getUiHUD().hideUIHUD();
	    Main.getPlayer().getCam().setActive(false);
	    Main.getPlayer().getMoves().setActive(false);
	    sceneM.getGameObjUIHashMap().get("uiExit").setActive(true);
	    sceneM.getGameObjUIHashMap().get("uiHelp").setActive(true);
	    sceneM.getGameObjUIHashMap().get("uiLeaderboard").setActive(true);
	    sceneM.getGameObjUIHashMap().get("uiMainPanel").setActive(true);
	    sceneM.getGameObjUIHashMap().get("uiResume").setActive(true);
	    isMenuActive = true;
	} catch (Exception e) {
	    System.err.println(
		    "failed to set UI menu objects by name in the scene manager boolean isActive to true, were the objects added to the sceneManager? ");
	    e.printStackTrace();
	}

    }

    public void setEscCallback() {
	SceneManager sceneM = GameEngine.getSceneManager();
	glfwSetKeyCallback(GameEngine.getWindow().getWindow(), (window, key, scancode, action, mods) -> {
	    if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
		if (isMenuActive) {
		    	resumeGame();
		} else {
			openMenu();
		}
	    }
	});

    }

}
