package greenteam.dungeoncraft.Game;

import greenteam.dungeoncraft.Engine.*;
import greenteam.dungeoncraft.Game.Controller.Player;
import greenteam.dungeoncraft.Game.Controller.LeaderBoardUtility;
import greenteam.dungeoncraft.Game.Model.BotManager;
import greenteam.dungeoncraft.Game.Model.Map;
import greenteam.dungeoncraft.Game.View.ui.UIHUD;
import greenteam.dungeoncraft.Game.View.ui.UIMenu;

/**
* This program is a grid based dungeon game in openGL.
*/
public class Main {

	private static Map map;
	private static Player player;
	private static BotManager botManager;
	private static UIMenu uiMenu;
	private static UIHUD uiHUD;
	private static LeaderBoardUtility xmlUtil;
	
	public Main() {
		map = new Map();
		player = new Player();
		uiMenu = new UIMenu();
		uiHUD = new UIHUD();
		botManager = new BotManager();
		xmlUtil = new LeaderBoardUtility();
	}
	/* initialize the game; */
	public static void init() {
	    	
		GameEngine.Init();
		GameEngine.getSceneManager().addGameObject("player", player);
		//map.initializeGridFromSource("\\src\\main\\java\\greenteam\\dungeoncraft\\map2.txt");
		map.initializeProceduralMap();
		player.init();
		player.name = xmlUtil.produceRandomNumberedName();
		player.getMoves().setGrid(map.getGrid());
		botManager.init(map.getGrid());
		uiHUD.init();
		uiMenu.init();
		GameEngine.run();
	}
	
	public static Map getMap() {
		return map;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static BotManager getBotManager() {
	    return botManager;
	}
	public static UIMenu getUiMenu() {
	    return uiMenu;
	}

	public static UIHUD getUiHUD() {
	    return uiHUD;
	}
	public static void setUiHUD(UIHUD uiHUD) {
	    Main.uiHUD = uiHUD;
	}
	
	public static void main(String[] args) {
		new Main();
		Main.init();
	}
	public static LeaderBoardUtility getXmlUtil() {
		return xmlUtil;
	}
	public static void setXmlUtil(LeaderBoardUtility xmlUtil) {
		Main.xmlUtil = xmlUtil;
	}

	
}