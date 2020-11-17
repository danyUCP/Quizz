package test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServeur 
{
	public static void main(String[] args) 
	{
		try
		{
			ServerSocket serveur = new ServerSocket(2020);
			System.out.println("Serveur en attente sur " + InetAddress.getLocalHost() + " : " + serveur.getLocalPort());


			/*
			
			while(true)
			{
				Socket client = serveur.accept();
				System.out.println("Client " + client.getInetAddress() + " connecté");
				
				
				//Lecture dans le flux
				BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
				
				
				String contenu = "";
				int stream;
				byte[] b = new byte[4096];
				stream = bis.read(b);
				contenu = new String(b, 0, stream);
				
				System.out.println(contenu);
				
				
				client.close();

				System.out.println("Client déconnecté");

			}
	
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		*/
	}
}
