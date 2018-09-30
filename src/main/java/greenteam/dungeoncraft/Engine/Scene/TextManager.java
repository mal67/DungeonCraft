package greenteam.dungeoncraft.Engine.Scene;

import java.util.HashMap;

public class TextManager {
    
    private HashMap<Integer, GameObjectTextCharacter> textGameObjectCharacters = new HashMap<Integer, GameObjectTextCharacter>();
    
    public void init() {
	addTextCharactersToHashMap();
    }
    
    public void addTextCharactersToHashMap() { 
	
	//add numbers to hashmap
	for (int i = 48; i < 48+10;i++) {
		Mesh rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\fontObjs\\" + i + ".obj");
	GameObjectTextCharacter textChar = new GameObjectTextCharacter(rawMesh,"char_" + i);
	textGameObjectCharacters.put(i, textChar);
	}
	
	//add capital letters to hashmap
	for (int i = 65; i < 65+26;i++) {
		Mesh rawMesh = new Mesh();
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\fontObjs\\" + i + ".obj");
	GameObjectTextCharacter textChar = new GameObjectTextCharacter(rawMesh,"char_" + i);
	textGameObjectCharacters.put(i, textChar);
	}
	
	int offsetToLowerCaseLetterAscii = 32;
	//add lowercase letter ascii addresses (as capitals) to hashmap
	for (int i = 65; i < 65+26;i++) {
		Mesh rawMesh = new Mesh();
		int asciiNumber = i + offsetToLowerCaseLetterAscii;
		rawMesh.init("\\src\\main\\java\\greenteam\\dungeoncraft\\Assets\\fontObjs\\" + i + ".obj");
	GameObjectTextCharacter textChar = new GameObjectTextCharacter(rawMesh,"char_" + asciiNumber);
	textGameObjectCharacters.put(asciiNumber, textChar);
	}
    }

    public HashMap<Integer, GameObjectTextCharacter> getTextGameObjectCharacters() {
        return textGameObjectCharacters;
    }

}
