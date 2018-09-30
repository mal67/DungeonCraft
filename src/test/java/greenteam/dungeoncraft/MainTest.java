package greenteam.dungeoncraft;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import greenteam.dungeoncraft.Engine.GameEngine;
import greenteam.dungeoncraft.Game.Model.MapUtility;

public class MainTest {

	private MapUtility mapUtil;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		 mapUtil = new MapUtility();
		 GameEngine.Init();
	}

	@Test /* integration Test: test the load map utility */
	public void testMapLoader() {
		int rows = 2;
		int cols = 2;
		int[][] testGrid = new int[rows][cols];
		testGrid = mapUtil.loadMap("\\src\\testfiles\\testmap.txt",rows,cols);	
		assertArrayEquals(testGrid,new int[][]{{0,0},{1,0}});
	}
	
	@Test  /* integration Test: test the save map utility */
	public void testMapSaver() {
		int rows = 2;
		int cols = 2;
		int[][] testGrid = new int[rows][cols];
		
		String relativeFileSavePath = "\\src\\testfiles\\testmap2.txt";
		
		testGrid = mapUtil.loadMap(relativeFileSavePath,rows,cols);	
		
		int num = testGrid[0][1] +1;
		if ( num > 9 ) {
			testGrid[0][1] = 0;
		} else {
			testGrid[0][1] = testGrid[0][1] +1;
		}	
		mapUtil.saveMap(relativeFileSavePath, testGrid);
		assertArrayEquals(mapUtil.loadMap(relativeFileSavePath,rows,cols),testGrid);
	}

}
