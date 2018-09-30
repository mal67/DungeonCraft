package greenteam.dungeoncraft.Game.Model;
import java.util.*;

public class ProceduralMap {

	private int gridRows = 31;
	private int gridCols = 31;
	private int numOfExit = 0;
	private int[][] grid;
	private int exitPositionNumber;
	
	public int[][] createNewMap() {
	    	numOfExit = 0;
	    	exitPositionNumber = 0;
	    	grid = new int[gridRows][gridCols];
		splitCells(gridRows-1, gridCols-1);//
		return transposeGrid(addExitDoor(addBorder(grid)));
	}
	
	public int[][] addBorder(int[][] grid) {
	    assert(grid.length > 0);
	    assert(grid[0].length > 0);	    
	    int[][] gridWithBorder = new int[grid.length+1][grid[0].length+1];
	    for (int i = 1; i < grid.length; i ++) {
		    for (int k = 1; k < grid[0].length; k ++) {
			gridWithBorder[i][k] = grid[i-1][k-1];
		    }
		}
	    return gridWithBorder;
	}
	
	public int[][] transposeGrid(int[][] grid) {
	    int[][] transposeGrid = new int[grid.length][grid[0].length];
	    
	    for (int i = 0; i < grid.length; i ++) {
		    for (int k = 0; k < grid[0].length; k ++) {
			transposeGrid[k][i] = grid[i][k];
		    }
		}   
	    return transposeGrid;
	}
	
	//1:bottom right 2:top left, 3:top right
		public int[][] addExitDoor(int[][] grid) {	    
		    if (exitPositionNumber == 1) {
			grid[grid[0].length-2][0] = 6;
			
		    } else if (exitPositionNumber == 2) {
			grid[1][grid.length-1] = 6;
			
		    }else if (exitPositionNumber == 3) {
			grid[grid.length-2][grid[0].length-1] = 6;		
		    }    
		    return grid;
		}

	// First: cutting map into several cells
	public void splitCells(int gridRows, int gridCols) {
		int splitNum = (int) (Math.random() * 4) + 3;
		// the number of the columns and the number of the rows of a map, rows and
		// columns split the map into several cells
		// and there is one room in one cell.
		int cellNumofColumn = splitNum / 2 + 1;
		int cellNumofRow = splitNum - cellNumofColumn + 1;
//		the length of two sides of all the cells;
		int lengthOfCells = gridCols / cellNumofColumn;
		int widthOfCells = gridRows / cellNumofRow;
		for (int currentRow = 0; currentRow < cellNumofRow; currentRow++) {
			for (int currentColumn = 0; currentColumn < cellNumofColumn; currentColumn++) {
				int X = lengthOfCells * currentColumn;
				int Y = widthOfCells * currentRow;
//				recording the middle point of four sides of each cell
				int[][] midPointsOfCell = { { X + lengthOfCells / 2, Y }, { X, Y + widthOfCells / 2 },
						{ X + lengthOfCells, Y + widthOfCells / 2 }, { X + lengthOfCells / 2, Y + widthOfCells } };
				int maxBeginPointX = (cellNumofColumn - 1) * lengthOfCells;
				int maxBeginPointY = (cellNumofRow - 1) * widthOfCells;
//				Drawing a room in a cell
				drawRooms(gridRows, gridCols, lengthOfCells, widthOfCells, X, Y, midPointsOfCell, maxBeginPointX,
						maxBeginPointY);
			}
		}
	}

	// Second: Drawing room in cells
	public void drawRooms(int gridR, int gridC, int cellLength, int cellWidth, int beginX, int beginY,
			int[][] fourMidOfSide, int maxX, int maxY) {
// any side of room  is not smaller than 5 and not larger than the responding side of cell
		int roomLength = (int) (Math.random() * (cellLength - 5)) + 5;
		int roomWideth = (int) (Math.random() * (cellWidth - 5)) + 5;
//calculating the coordinate of the bottom-left point of a room in an Array
		int BottomLeftPoint[] = { ((int) (Math.random() * (cellLength - roomLength - 1))) + beginX + 1,
				((int) (Math.random() * (cellWidth - roomWideth - 1))) + beginY + 1 };

		// the four sides of room: the sequence is bottom,left,right,top
		int[][] fourMidOfRoom = { { BottomLeftPoint[0] + roomLength / 2, BottomLeftPoint[1] },
				{ BottomLeftPoint[0], BottomLeftPoint[1] + roomWideth / 2 },
				{ BottomLeftPoint[0] + roomLength, BottomLeftPoint[1] + roomWideth / 2 },
				{ BottomLeftPoint[0] + roomLength / 2, BottomLeftPoint[1] + roomWideth } };

		// the coordinate of four corner of a room: bottom left, bottom right,top
		// left,,top right
		int[][] fourCorners = { { BottomLeftPoint[0], BottomLeftPoint[1] },
				{ BottomLeftPoint[0] + roomLength - 1, BottomLeftPoint[1] },
				{ BottomLeftPoint[0], BottomLeftPoint[1] + roomWideth - 1 },
				{ BottomLeftPoint[0] + roomLength - 1, BottomLeftPoint[1] + roomWideth - 1 } };

		// >>>invoking the method to generate a room
		for (int i = 0; i < roomLength; i++) {
			for (int k = 0; k < roomWideth; k++) {
				grid[BottomLeftPoint[0] + i][BottomLeftPoint[1] + k] = 1;
			}
		}
		// >>>Invoking method: Adding entry and exit
		addingEntryAndExit(gridR, gridC, beginX, beginY, maxX, maxY, roomLength, BottomLeftPoint, fourMidOfRoom);

		// >>>Invoking method: Linking rooms together
		for (int numOfSize = 0; numOfSize < 4; numOfSize++) {
			// 0=bottom,1=left,2=right,3=top
			linkPoints(fourMidOfRoom[numOfSize], fourMidOfSide[numOfSize], numOfSize, beginX, beginY, maxX, maxY);
		}

		// >>>Choosing stuff locations
		addingStuff(roomLength, roomWideth, BottomLeftPoint);

	}

	// Step3: Adding entry and exit
	private void addingEntryAndExit(int gridR, int gridC, int beginX, int beginY, int maxX, int maxY, int roomLength,
			int[] BottomLeftPoint, int[][] fourMidOfRoom) {
		// Adding an entry
		if (beginX == 0 && beginY == 0) {
			for (int i = beginX; i < BottomLeftPoint[0] + roomLength / 2; i++) {
				grid[i][0] = 1;
			}
			for (int i = beginY; i < BottomLeftPoint[1]; i++) {
				grid[BottomLeftPoint[0] + roomLength / 2][i] = 1;
			}
		}
		// Adding an exit randomly
		int chooseExit = (int) (Math.random() * 2);
		if (chooseExit == 0 && numOfExit == 0) {

			// Drawing the exit path which links bottom right corner to the bottom right
			// room.
			if (beginX == maxX && beginY == 0) {
				numOfExit = 1;
				System.out.println("exit is bottom right");
				exitPositionNumber = 1;
				for (int i = fourMidOfRoom[0][0]; i < gridC; i++) {
					grid[i][0] = 1;
				}
				for (int j = 0; j < fourMidOfRoom[0][1]; j++) {
					grid[fourMidOfRoom[0][0]][j] = 1;
				}
			}

			// Drawing the exit path which links top left corner to the top left room.
			if (beginX == 0 && beginY == maxY) {
				numOfExit = 1;
				System.out.println("exit is top left");
				exitPositionNumber = 2;
				for (int i = 0; i < fourMidOfRoom[3][0]; i++) {
					grid[i][gridR - 1] = 1;
				}
				for (int j = fourMidOfRoom[3][1]; j < gridR; j++) {
					grid[fourMidOfRoom[3][0]][j] = 1;
				}
			}
		}

		// there is no exit when the last room being generated
		// Drawing the exit path which links bottom right corner to the bottom right
		// room.
		if (beginX == maxX && beginY == maxY && numOfExit == 0) {
			numOfExit = 1;
			System.out.println("exit is top right");
			exitPositionNumber = 3;
			for (int i = fourMidOfRoom[3][0]; i < gridC; i++) {
				grid[i][gridR - 1] = 1;
			}
			for (int j = fourMidOfRoom[3][1]; j < gridR; j++) {
				grid[fourMidOfRoom[0][0]][j] = 1;
			}
		}
	}

	// Step 5: Adding stuff into every room
	public void addingStuff(int roomLength, int roomWideth, int[] BottomLeftPoint) {
		HashSet<String> randPoints = new HashSet();// used to record distinct points and its order in a room
		HashSet<String> checkPoints = new HashSet();// used to check whether the being added point exists or not
		int lengthOfHash = randPoints.size();
		int pointX = 0;
		int pointY = 0;
		while (lengthOfHash < 9) {

			// the first four points are stuff
			if (lengthOfHash < 4) {
				pointX = (int) (Math.random() * roomLength) + BottomLeftPoint[0];
				pointY = (int) (Math.random() * roomWideth) + BottomLeftPoint[1];

			}
			// the rest points are blocks in room
			else {
				pointX = (int) (Math.random() * (roomLength - 2)) + BottomLeftPoint[0] + 1;
				pointY = (int) (Math.random() * (roomWideth - 2)) + BottomLeftPoint[1] + 1;

			}
			String XandY = pointX + "," + pointY;
			// checking the being added point
			if (checkPoints.add(XandY)) {
				String XandYandOrder = pointX + "," + pointY + "," + lengthOfHash;
				randPoints.add(XandYandOrder);
			}

			lengthOfHash = randPoints.size();

		}
		// >--using iterator to give value to the points where there is stuff or block
		Iterator iterator = randPoints.iterator();
		int fourRandStuff = 2;
		/*
		 * The meaning of numbers 2=coin; 3=enemy; 4=ammo; 5=health pack
		 */
		while (iterator.hasNext()) {
			String tempPosition = (String) iterator.next();
			String[] randPosition = tempPosition.split(",");
			int x = Integer.parseInt(randPosition[0]);
			int y = Integer.parseInt(randPosition[1]);
			int order = Integer.parseInt(randPosition[2]);
			if (order < 4) {
				grid[x][y] = fourRandStuff;
				fourRandStuff = fourRandStuff + 1;
			} else {
				grid[x][y] = 0;
			}
		}
	}

	// Step 4: Link Room together
	private void linkPoints(int[] locRoom, int[] locCell, int posPoint, int startX, int startY, int maxX, int maxY) {

//		Drawing a path which links the bottom side a room to the bottom side of its cell
//		If the room is next to bottom side of the map, ignoring this block
		if (posPoint == 0 && startY > 0) {
			if (locRoom[0] - locCell[0] > 0) {
				for (int y = locCell[1]; y <= locRoom[1]; y++) {
					grid[locCell[0]][y] = 1;
				}
				for (int x = locCell[0]; x < locRoom[0]; x++) {
					grid[x][locRoom[1]] = 1;

				}
			} else if (locRoom[0] - locCell[0] < 0) {
				for (int y = locCell[1]; y <= locRoom[1]; y++) {
					grid[locCell[0]][y] = 1;
				}
				for (int x = locRoom[0]; x < locCell[0]; x++) {
					grid[x][locRoom[1]] = 1;

				}
			} else if (locRoom[0] == locCell[0]) {
				for (int y = locCell[1]; y <= locRoom[1]; y++) {
					grid[locRoom[0]][y] = 1;
				}
			}
		}
//		Drawing a path which links the top side a room to the top side of its cell
//		If the room is next to top side of the map, ignoring this block

		if (posPoint == 3 && startY != maxY) {
			if (locRoom[0] - locCell[0] > 0) {
				for (int y = locRoom[1]; y <= locCell[1]; y++) {
					grid[locCell[0]][y] = 1;
				}
				for (int x = locCell[0]; x <= locRoom[0]; x++) {
					grid[x][locRoom[1] - 1] = 1;

				}
			} else if (locRoom[0] - locCell[0] < 0) {
				for (int y = locRoom[1]; y <= locCell[1]; y++) {
					grid[locCell[0]][y] = 1;
				}
				for (int x = locRoom[0]; x <= locCell[0]; x++) {
					grid[x][locRoom[1] - 1] = 1;

				}
			} else if (locRoom[0] == locCell[0]) {
				for (int y = locRoom[1]; y <= locCell[1]; y++) {
					grid[locCell[0]][y] = 1;
				}
			}
		}
//		Drawing a path which links the left side a room to the left side of its cell
//		If the room is next to left side of the map, ignoring this block
		if (posPoint == 1 && startX > 0) {

			if (locRoom[1] - locCell[1] > 0) {
				for (int x = locCell[0]; x <= locRoom[0]; x++) {
					grid[x][locCell[1]] = 1;
				}
				for (int y = locCell[1]; y < locRoom[1]; y++) {
					grid[locRoom[0]][y] = 1;
				}
			} else if (locRoom[1] - locCell[1] < 0) {
				for (int x = locCell[0]; x <= locRoom[0]; x++) {
					grid[x][locCell[1]] = 1;
				}
				for (int y = locRoom[1]; y < locCell[1]; y++) {
					grid[locRoom[0]][y] = 1;
				}
			} else if (locRoom[1] - locCell[1] == 0) {
				for (int x = locCell[0]; x <= locRoom[0]; x++) {
					grid[x][locCell[1]] = 1;
				}
			}
		}
//		Drawing a path which links the right side a room to the right side of its cell
//		If the room is next to right side of the map, ignoring this block
		if (posPoint == 2 && startX != maxX) {
			if (locRoom[1] - locCell[1] > 0) {
				for (int x = locRoom[0]; x <= locCell[0]; x++) {
					grid[x][locCell[1]] = 1;
				}
				for (int y = locCell[1]; y <= locRoom[1]; y++) {
					grid[locRoom[0] - 1][y] = 1;
				}
			} else if (locRoom[1] - locCell[1] < 0) {
				for (int x = locRoom[0]; x <= locCell[0]; x++) {
					grid[x][locCell[1]] = 1;
				}
				for (int y = locRoom[1]; y <= locCell[1]; y++) {
					grid[locRoom[0] - 1][y] = 1;
				}
			} else if (locRoom[1] == locCell[1]) {
				for (int x = locRoom[0]; x <= locCell[0]; x++) {
					grid[x][locCell[1]] = 1;
				}
			}
		}

	}
}
