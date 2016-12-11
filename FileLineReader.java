import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileLineReader {
	private LinkedList<String> lines;
	private String filepath;
	
	public FileLineReader(String filepath)
	{
		lines = new LinkedList<String>();
		this.filepath = filepath;
	}
	
	public List<String> readLines()
	{
		BufferedReader r;
		try
		{
			r = new BufferedReader(new FileReader(filepath));
			String line;
			while((line = r.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch(IOException ex)
		{
			System.err.println(ex.toString());
		}
	
		return lines;
	}
	
}
