package greenteam.dungeoncraft.Engine.Shader;

public class ShaderManager {

	private Shader defaultShader;
	private Shader defaultUIShader;
	private Shader defaultTextShader;
	
	/* initialize the default shader */
	
	public void init() {
	    	//create the default shader
		defaultShader = new Shader();
		defaultShader.loadShadersFromFiles("\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\vert.vs", 
									       "\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\frag.fs");
		defaultShader.link();
		defaultShader.bind();
		defaultShader.createUniform("lightPosition");
		defaultShader.createUniform("lightIntensity");
		defaultShader.createUniform("MVP");
		defaultShader.createUniform("transform");
		defaultShader.createUniform("uvScale");
		defaultShader.createUniform("diffuseColor");
		
		
	    	//create the default ui shader
		defaultUIShader = new Shader();
		defaultUIShader.loadShadersFromFiles("\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\ui_vert.vs", 
										   "\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\ui_frag.fs");
		defaultUIShader.link();
		defaultUIShader.bind();
		defaultUIShader.createUniform("transform");
		
		//create the default text shader
		defaultTextShader = new Shader();
		defaultTextShader.loadShadersFromFiles("\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\text_vert.vs", 
										      "\\src\\main\\java\\greenteam\\dungeoncraft\\shaders\\text_frag.fs");
		defaultTextShader.link();
		defaultTextShader.bind();
		defaultTextShader.createUniform("v3fTranslate");
		defaultTextShader.createUniform("textScale");
	}

	public Shader getDefaultShader() {
		return defaultShader;
	}
	
	public Shader getDefaultUIShader() {
		return defaultUIShader;
	}

	public Shader getDefaultTextShader() {
	    return defaultTextShader;
	}

}
