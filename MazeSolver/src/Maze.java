import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Maze {

	private int numberOfRow;
	private int numberOfColumn;
	private int numberOfTreasures = 0;

	private boolean keepPlay = true;

	private char[][] map;
	private boolean[][] visitedChar;

	// Start point of the map
	private int initialRow = 1;
	private int initialColumn = 0;

	private Scanner fileScan;

	private Stack<Character> path = new Stack<Character>();
	private Stack<Character> temp = new Stack<Character>();
	private Stack<Integer> visitedRows = new Stack<Integer>();
	private Stack<Integer> visitedColumns = new Stack<Integer>();

	private ArrayList<Stack<Character>> paths = new ArrayList<Stack<Character>>();

	public Maze(File file) throws FileNotFoundException {
		// --------------------------------------------------------
		// Summary: Constructor of Maze class takes file parameter as File type and
		// assign
		// fileScan to file. Then it initialize numberOfColumn and numberOfRow by using
		// determineNumberOfColumn() and determineNumberOfRow() methods and calls
		// createMap() method for creating map
		// --------------------------------------------------------

		fileScan = new Scanner(file);
		numberOfColumn = determineNumberOfColumn(fileScan);
		numberOfRow = determineNumberOfRow(fileScan);
		createMap(file);
	}

	private int determineNumberOfRow(Scanner scanner) {
		// --------------------------------------------------------
		// Summary: To determine number of row we use determineNumberOfRow() method. We
		// give a scanner object as a parameter to scan text file and each scanned line
		// is our row. We initialize the row as 1 the add scanned rows count because of
		// we used first scanned row as column. Finally, it returns row.
		// --------------------------------------------------------

		int row = 1;
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			row++;
		}
		return row;
	}

	private int determineNumberOfColumn(Scanner scanner) {
		// --------------------------------------------------------
		// Summary: determineNumberOfColumn() method takes scanner parameter as Scanner
		// type and uses this scanner to find number of columns. Length of the row is
		// equals to number of column. Therefore it returns length of a line.
		// --------------------------------------------------------

		return scanner.nextLine().length();

	}

	public void createMap(File file) throws FileNotFoundException {
		// --------------------------------------------------------
		// Summary: createMap() method takes the file parameter as File type. It creates
		// the scanner objects and scans the given file. It declares 2D char array which
		// referenced as "map". It's row number is equals to numberOfRow and column
		// number is equals to numberOfColumn. Then method uses toCharArray() method to
		// assign each element of map array to file's each character.
		// --------------------------------------------------------

		Scanner scanner = new Scanner(file);
		map = new char[numberOfRow][numberOfColumn];
		visitedChar = new boolean[numberOfRow][numberOfColumn];
		for (int i = 0; i < map.length; i++) {
			char[] c = scanner.next().toCharArray();
			for (int j = 0; j < c.length; j++) {
				map[i][j] = c[j];
			}
		}
		scanner.close();
	}

	public boolean checkWallCollision(int x, int y) {
		// --------------------------------------------------------
		// Summary: checkWallCollision() method takes 2 integer parameter and checks
		// this given point is wall or not
		//
		// Precondition: checkWallCollision is an boolean type method.
		// Postcondition: method returns true or false for given condition.
		// --------------------------------------------------------

		if (map[x][y] == '+' || map[x][y] == '|' || map[x][y] == '-') {
			return true;
		} else
			return false;
	}

	public void setKeepPlay(boolean keepPlay) {
		// --------------------------------------------------------
		// Summary: Sets the keepPlay true or false. To control if the game is still
		// active or not.
		// --------------------------------------------------------
		this.keepPlay = keepPlay;
	}

	public boolean moveRight(int x, int y) {
		// --------------------------------------------------------
		// Summary: moveRight() method checks the right of the current position is safe
		// or not. It takes to integer as a parameter these are the current locations.
		// It checks the index of right side is outside of map or not, it uses
		// checkWallCollision() to determine right of the current position is a wall or
		// not. And also, it checks right side of the current position is visited before
		// or not by using visitedChar[][] array. If it is safe to move next position
		// then it returns true if it is not then it returns false
		// --------------------------------------------------------

		if ((y + 1) < numberOfColumn && !checkWallCollision(x, y + 1) && !visitedChar[x][y + 1]) {
			return true;
		}
		return false;
	}

	public boolean moveLeft(int x, int y) {
		// --------------------------------------------------------
		// Summary: moveLeft() method checks the left of the current position is safe
		// or not. It takes to integer as a parameter these are the current locations.
		// It checks the index of left side is outside of map or not, it uses
		// checkWallCollision() to determine left of the current position is a wall or
		// not. And also, it checks left side of the current position is visited before
		// or not by using visitedChar[][] array. If it is safe to move next position
		// then it returns true if it is not then it returns false
		// --------------------------------------------------------

		if ((y - 1) > 0 && !checkWallCollision(x, y - 1) && !visitedChar[x][y - 1]) {
			return true;
		}
		return false;
	}

	public boolean moveUp(int x, int y) {
		// --------------------------------------------------------
		// Summary: moveUp() method checks the upper side of the current position is
		// safe or not. It takes to integer as a parameter these are the current
		// locations. It checks the index of upper side is outside of map or not, it
		// uses checkWallCollision() to determine upper side of the current position is
		// a wall or not. And also, it checks upper side of the current position is
		// visited before or not by using visitedChar[][] array. If it is safe to move
		// next position then it returns true if it is not then it returns false
		// --------------------------------------------------------

		if ((x - 1) > 0 && !checkWallCollision(x - 1, y) && !visitedChar[x - 1][y]) {
			return true;
		}
		return false;
	}

	public boolean moveDown(int x, int y) {
		// --------------------------------------------------------
		// Summary: Checks the under side of the current position is safe or not. It
		// takes to integer as a parameter these are the current locations. It checks
		// the index of under side is outside of map or not, it uses
		// checkWallCollision() to determine under side of the current position is a
		// wall or not. And also, it checks under side of the current position is
		// visited before or not by using visitedChar[][] array. If it is safe to move
		// next position then it returns true if it is not then it returns false
		// --------------------------------------------------------

		if ((x + 1) < numberOfRow && !checkWallCollision(x + 1, y) && !visitedChar[x + 1][y]) {
			return true;
		}
		return false;
	}

	public boolean isTreasure(int x, int y) {
		// --------------------------------------------------------
		// Summary: isTreasure() method checks the given position has treasure. It takes
		// to integer as a parameter these are the current locations. If it has
		// treasure. Then it returns true, If it has not treasure. Then it returns
		// false.
		// --------------------------------------------------------
		if (map[x][y] == 'E') {
			return true;
		}
		return false;
	}

	public void findPaths(int currentRow, int currentColumn)
	/*
	 * Summary:
	 */
	{

		if (isTreasure(currentRow, currentColumn)) {
			path.push(map[currentRow][currentColumn]);
			temp.push(map[currentRow][currentColumn]);

			visitedRows.push(currentRow);
			visitedColumns.push(currentColumn);

			visitedChar[currentRow][currentColumn] = true;

			Stack<Character> path2 = new Stack<Character>();

			numberOfTreasures += 1;

			while (!path.isEmpty())
				path2.push(path.pop());

			paths.add(path2);
			temp.pop();
			temp.pop();
			visitedRows.pop();
			visitedColumns.pop();
			currentRow = visitedRows.pop();
			currentColumn = visitedColumns.pop();

			for (char c : temp) {
				path.push(c);
			}

			findPaths(currentRow, currentColumn);

		} else {

			path.push(map[currentRow][currentColumn]);
			temp.push(map[currentRow][currentColumn]);
			visitedRows.push(currentRow);
			visitedColumns.push(currentColumn);
			visitedChar[currentRow][currentColumn] = true;

			if (moveLeft(currentRow, currentColumn)) {
				currentColumn -= 1;
				if (currentRow == initialRow && currentColumn == initialColumn) {
					keepPlay = false;
				}
				findPaths(currentRow, currentColumn);

			} else if (moveUp(currentRow, currentColumn)) {
				currentRow -= 1;
				if (currentRow == initialRow && currentColumn == initialColumn) {
					keepPlay = false;
				}
				findPaths(currentRow, currentColumn);
			} else if (moveRight(currentRow, currentColumn))
				findPaths(currentRow, ++currentColumn);
			else if (moveDown(currentRow, currentColumn))
				findPaths(++currentRow, currentColumn);
			else if (keepPlay) {
				if (currentRow == initialRow && currentColumn == initialColumn) {
					keepPlay = false;
				} else {
					path.pop();
					path.pop();
					temp.pop();
					temp.pop();
					visitedRows.pop();
					visitedColumns.pop();
					currentRow = visitedRows.pop();
					currentColumn = visitedColumns.pop();
					findPaths(currentRow, currentColumn);
				}

			}

		}

	}

	public void sortPaths() {
		// --------------------------------------------------------
		// Summary: sortPaths() method sorts the paths ArrayList to increasing order of
		// paths lengths.
		//
		// --------------------------------------------------------

		for (int i = 0; i < paths.size(); i++) {

			for (int j = i + 1; j < paths.size(); j++) {

				Stack<Character> tempI = paths.get(i);
				Stack<Character> tempJ = paths.get(j);

				if (tempI.size() > tempJ.size()) {

					paths.set(i, tempJ);
					paths.set(j, tempI);

				}
			}
		}
	}

	public void printResults() {
		// --------------------------------------------------------
		// Summary: printResults() method method then prints
		// the results as desired.
		// --------------------------------------------------------

		System.out.println(numberOfTreasures + " treasures are found.");

		if (paths.size() > 0) {
			System.out.println("Paths are: ");

			for (int i = 0; i < numberOfTreasures; i++) {
				System.out.print(i + 1 + ") ");
				while (!paths.get(i).isEmpty()) {
					System.out.print(paths.get(i).pop());
				}
				System.out.println(" ");
			}
		}

	}

}
