package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogServeur 
{
	private static File fichierLog;


	public LogServeur()
	{
		if(fichierLog == null)
			fichierLog = new File("log.txt");
	}
	
	public static void trace(String resultat)
	{
		PrintWriter pw = null;
		
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm:ss");
		Date date = new Date();
		
		try
		{
			pw = new PrintWriter(new BufferedWriter(new FileWriter(fichierLog, true)));
			
			pw.println(formater.format(date) + " : " + resultat + "\n");
			System.out.println("\nLog sauvegardé\n");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			pw.close();
		}
		
	}

}
