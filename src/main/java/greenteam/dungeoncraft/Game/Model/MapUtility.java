package greenteam.dungeoncraft.Game.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import greenteam.dungeoncraft.Util.FileReader;
/**
*  used to save a 2d array to a text file consisting of the grid map for the game
*/
public class MapUtility {
	
	 /* used to save a 2d array to a text file consisting of the grid map for the game */
	public void saveMap(String relativePath,int[][] gridToSave) {
		if (relativePath == null) {
			throw new IllegalArgumentException("relative path can not be null");
		}
		try {
			System.out.println("mapSaved");
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < gridToSave.length; i++) {
			   for(int k = 0; k < gridToSave[0].length; k++) {
				   builder.append(gridToSave[i][k]+"");
			   		}
			}
			
			String projectPath = new File("").getAbsolutePath();
			BufferedWriter writer = new BufferedWriter(new FileWriter(projectPath + relativePath));
			writer.write(builder.toString());
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/* used to load a 2d array from a text file for the grid map */
	public int[][] loadMap(String relativePath,int rows,int cols) {
		if (relativePath == null) {
			throw new IllegalArgumentException("relative path can not be null");
		}
		
		int[][] newGrid = new int[rows][cols];
		try {
			FileReader fr = new FileReader();
			String stringOfMap = fr.ReadFile(relativePath);
			for(int i = 0; i < rows; i++) {
				   for(int k = 0; k < cols; k++) {
					   newGrid[i][k] = Character.getNumericValue(stringOfMap.charAt(rows*i + k));
				   }
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newGrid;
	}
	/* this function resets the grid to all zeros/walls, used currently when building a new map */
	public int[][] resettedMap(int rows,int cols) {
		int[][] newGrid = new int[rows][cols];
		System.out.println("resetting map:");
		for(int i = 0; i < rows; i++) {
			   for(int k = 0; k < cols; k++) {
				   newGrid[i][k] = 0;
			   }
		}	
		return newGrid;
	}

}
