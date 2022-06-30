import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(System.in);
		String fileName = input.nextLine(); // Expects the text file's name which contains the map.
		input.close();
		
		try {
			
			File file = new File(fileName); 
			
			Maze maze = new Maze(file);
			maze.findPaths(1,0);
			maze.sortPaths();
			maze.printResults();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
