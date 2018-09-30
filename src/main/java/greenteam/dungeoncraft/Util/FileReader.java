package greenteam.dungeoncraft.Util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

//read string from file
public class FileReader {
	
	public String ReadFile(String FileLoc)  {
		StringBuilder source = new StringBuilder();
		String filePath = new File("").getAbsolutePath();
		List<String> lines = new ArrayList<String>();
		
		try {
				lines = Files.readAllLines(Paths.get(filePath + FileLoc));
			    for (String line : lines) {
			         source.append(line).append("\n");
			    }
		} catch (IOException e) {
			System.err.println( "FileReader could not load file from path: " + filePath + FileLoc );
			e.printStackTrace();
		}
		return source.toString();
	}

}
