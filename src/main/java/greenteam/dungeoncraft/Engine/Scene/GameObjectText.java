package greenteam.dungeoncraft.Engine.Scene;
import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Engine.GameWindow;
import greenteam.dungeoncraft.Engine.Math.Vec3f;

public class GameObjectText extends GameObject {

    private String lineOfText;
    private float spaceBetweenText;
    private float currentXPosInScreenSpace;
    private float currentYPosInScreenSpace;
    private float textScale;
    protected float pixelW;
    protected float pixelH;
    protected GameWindow wind;
    
	/* Constructor */
	public GameObjectText(float x,float y,float textScaleIn, String nameIn,String textToDraw) {
		name = nameIn;

		lineOfText = textToDraw;
		wind = GameEngine.getWindow();
		pixelW = 1.0f / (wind.getWidth()/2);
		pixelH = 1.0f / (wind.getHeight()/2);	
		textScale = textScaleIn*(pixelW/pixelH);
		spaceBetweenText = textScale/1.2f;
		
		//set the current X and current Y position field variables to the X and Y positions given
		currentXPosInScreenSpace = x;
		currentYPosInScreenSpace = y;
		tranPos = new Vec3f(-1+x*pixelW,-1+y*pixelH, 0);
		init();
	}

	@Override
	public void init() {
	    	shader = GameEngine.getShaderManager().getDefaultTextShader();
		isActive = true;
	}
	
	/* move the text to a new position on the screen */
	public void moveText(float xPos, float yPos) {
	    tranPos = new Vec3f(-1+xPos*pixelW,-1+yPos*pixelH, 0);
	    //set the current X and current Y position field variables to the X and Y positions we just put into the Vec3f tranPos
	    currentXPosInScreenSpace = xPos;
	    currentYPosInScreenSpace = yPos;
	}

    @Override /* draw is called every frame (draws the text meshes to the scene) */
    public void draw() {
	if (isActive) {
	    int asciiNumber;
	    Vec3f textOffset = new Vec3f(tranPos.getX(),tranPos.getY(),tranPos.getZ());
	    
	    for (int i = 0; i < lineOfText.length(); i++) {
		
		asciiNumber = (char)(int)lineOfText.charAt(i);
		    if (asciiNumber == 32) { //blank space
			textOffset.addX(spaceBetweenText);
			 shader.setUniformVec3f("v3fTranslate", textOffset);
		    }
        	    if (GameEngine.getTextManager().getTextGameObjectCharacters().containsKey(asciiNumber)) {
        	    	GameEngine.getTextManager().getTextGameObjectCharacters().get(asciiNumber).draw();
        	    	
        	    	textOffset.addX(spaceBetweenText);
        	        shader.setUniformVec3f("v3fTranslate", textOffset);
        	    }
	    }
	}
    }

	@Override /* update is called every frame by the draw function */
	public void drawUpdate() {
	    if (isActive) {
	    //set starting position and scale for the text
	    shader.setUniformVec3f("v3fTranslate", tranPos);
	    shader.setUniformf("textScale", textScale);
	    }
	}

	public void setLineOfText(String lineOfText) {
	    this.lineOfText = lineOfText;
	}
	
	
	    @Override
	    public void update() {
		// TODO Auto-generated method stub
		
	    }

	    public float getCurrentXPosInScreenSpace() {
	        return currentXPosInScreenSpace;
	    }

	    public float getCurrentYPosInScreenSpace() {
	        return currentYPosInScreenSpace;
	    }


}
