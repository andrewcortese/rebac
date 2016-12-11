
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileLineWriter {
	BufferedWriter r = null;
	
	public FileLineWriter(String filepath)
	{
		try
		{
			r = new BufferedWriter(new FileWriter(filepath));
		}
		catch(IOException ex)
		{
			System.err.println(ex.toString());
		}
	}
	
	public void writeLine(String line)
	{
		try
		{
			r.write(line);
			r.newLine();
		}
		catch(IOException ioe)
		{
			System.err.println("File write failed." + ioe.getMessage());
		}
	}
	
	
	public void writeLine(String format, Object... arguments)
	{
		this.writeLine(String.format(format, arguments));
	}
	
	public void writeLines(List<String> lines)
	{
		for(String line: lines)
		{
			this.writeLine(line);
		}
	}
}
