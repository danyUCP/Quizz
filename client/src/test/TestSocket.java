package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import connection.Client;

public class TestSocket 
{
	public static void main(String[] args) 
	{
		if(args.length == 1)
			Client.setPort(Integer.parseInt(args[0]));
		
		Client socket = new Client();
		
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)) ;
		String instruction = null, reponse = null;
		
		do
		{
			try 
			{
				System.out.print("\nTapez instruction : ");
				instruction = clavier.readLine(); 

				reponse = socket.envoyerInstruction(instruction);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}while(reponse != null && !socket.estDeconnecte());
		
		
		//socket.deconnecter();
		
		
		


	}

}
