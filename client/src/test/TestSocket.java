package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import connection.SocketClient;

public class TestSocket 
{
	public static void main(String[] args) 
	{
		
		SocketClient socket = new SocketClient();
		
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)) ;
		String instruction = null;
		
		do
		{
			try 
			{
				System.out.print("\nTapez instruction : ");
				instruction = clavier.readLine();

				socket.envoyerInstruction(instruction);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}while(instruction != null);
		
		
		socket.deconnecter();
		
		
		


	}

}
