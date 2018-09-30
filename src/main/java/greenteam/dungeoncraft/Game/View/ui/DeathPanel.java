package greenteam.dungeoncraft.Game.View.ui;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.xml.sax.SAXException;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.Math.Vec3f;
import greenteam.dungeoncraft.Engine.Scene.GameObjectText;
import greenteam.dungeoncraft.Engine.Scene.GameObjectUI;
import greenteam.dungeoncraft.Engine.Scene.Texture;
import greenteam.dungeoncraft.Game.Main;
import greenteam.dungeoncraft.Game.Controller.LeaderBoardUtility;
import greenteam.dungeoncraft.Game.Model.PlayerData;

public class DeathPanel extends GameObjectUI {

    private ExitButtonYes uiExitButtonYes;
    private RestartButton uiRestartButton;
    private GameObjectText playerTextLine;
    private List<String> lines;

    /* Constructor */
    public DeathPanel(Texture tex, float xPosInScreenSpace, float yPosInScreenSpace, int zIndex, float width,
	    float height, String nameIn) {
	super(tex, xPosInScreenSpace, yPosInScreenSpace, zIndex, width, height, nameIn);
	lines = new ArrayList<String>();
	playerTextLine = new GameObjectText(xPosInScreenSpace + width / 2 - 200, yPosInScreenSpace + height / 2, 0.045f,
		"lbplayertext", "");

	GameEngine.getSceneManager().addGameObjectText(playerTextLine.name, playerTextLine);
	Texture uiExitButtonYesTex = new Texture();
	Texture uiRestartButtonTex = new Texture();
	uiExitButtonYesTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/exit.png");
	uiRestartButtonTex.load("src/main/java/greenteam/dungeoncraft/Assets/Textures/ui/resume.png");
	float thisXPos = currentXPosInScreenSpace;
	float thisYPos = currentYPosInScreenSpace;
	uiExitButtonYes = new ExitButtonYes(uiExitButtonYesTex, thisXPos, thisYPos, 0, 200, 100, "uiExitButtonYes");
	uiRestartButton = new RestartButton(uiRestartButtonTex, thisXPos + 300, thisYPos, 0, 200, 100, "uiRestartButton");
    }

    @Override /* draw is called every frame (draws the mesh to the scene) */
    public void draw() {
	if (isActive) {
	    glBindVertexArray(vaoID);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, rawMesh.getTextureID());
	    glBindBuffer(GL_ARRAY_BUFFER, rawMesh.getVertID());
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rawMesh.getElementID());
	    glDrawElements(GL_TRIANGLES, rawMesh.getVertCount(), GL_UNSIGNED_INT, 0);

	    // update and draw child ui elements
	    uiExitButtonYes.drawUpdate();
	    uiExitButtonYes.draw();
	    uiRestartButton.drawUpdate();
	    uiRestartButton.draw();
	    
	    // get the X and Y positions from the text's positions to reset the text position 
	    //after it has shifted to display lines of text.
	    float xPos = playerTextLine.getCurrentXPosInScreenSpace();
	    float yPos = playerTextLine.getCurrentYPosInScreenSpace();
	    
	    GameEngine.getShaderManager().getDefaultTextShader().bind();
	    
	    //display user name and score
	    playerTextLine.setLineOfText("you are " + Main.getPlayer().getName());
	    playerTextLine.drawUpdate();
	    playerTextLine.draw();
	    playerTextLine.moveText(playerTextLine.getCurrentXPosInScreenSpace(), playerTextLine.getCurrentYPosInScreenSpace()+20);
	    playerTextLine.setLineOfText("your score was " + Main.getPlayer().getScore());
	    playerTextLine.drawUpdate();
	    playerTextLine.draw();
	    playerTextLine.moveText(playerTextLine.getCurrentXPosInScreenSpace(), playerTextLine.getCurrentYPosInScreenSpace()+20);
	    
	    
	   for (int i = 0; i < lines.size(); i++) {
	       	//move each line down by 20pixels
	     	playerTextLine.moveText(playerTextLine.getCurrentXPosInScreenSpace(), playerTextLine.getCurrentYPosInScreenSpace()+20);
		playerTextLine.setLineOfText(lines.get(i));
		playerTextLine.drawUpdate();
		playerTextLine.draw();
	    }
	   
	    GameEngine.getShaderManager().getDefaultTextShader().unbind();
	    shader.bind();
	    //reset the position of the text line object to it's starting position
	    playerTextLine.moveText(xPos, yPos);
	    playerTextLine.setLineOfText("");
		
	    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	    glBindVertexArray(0);
	}
    }

    @Override /* update is called every frame by the draw function */
    public void drawUpdate() {
	if (isActive) {
	    shader.setUniformMat4f("transform", transform);
	}
    }

    public void displayLeaderBoard() {
	LeaderBoardUtility lbUtil = Main.getXmlUtil();
	try {

	    lbUtil.parsePlayerXMLData();
	    lbUtil.updatePlayerScore();
	    //clear the lines array we are about to add lines foreach player on the leaderboard to
	    lines.clear();
	    
	    for (int i = 0; i < lbUtil.getPlyDList().playerData.size(); i++) {
		String playerName = lbUtil.getPlyDList().playerData.get(i).getName();
		int playerScore = lbUtil.getPlyDList().playerData.get(i).getScore();
		lines.add(playerName  + "  had score  " + playerScore);
	    }
	    
	    lbUtil.exportPlayerXMLData();

	} catch (ParserConfigurationException | SAXException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
    
    

    /* The function is called when the mouse is clicked on the ui element */
    @Override
    public void onMouseClick() {
	System.out.println("ui element clicked: " + name);
    }

}
