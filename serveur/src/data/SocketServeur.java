package data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketServeur 
{
	private InetAddress ipClient;
	private int port;
	private ServerSocket socketServeur;
	
	private Socket socketClient;
	
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	
	private BufferedReader entree;
	private PrintWriter sortie;
	
	public SocketServeur()
	{
		try
		{
			this.socketServeur = new ServerSocket(2020);
			this.ipClient = socketServeur.getInetAddress();
			this.port = socketServeur.getLocalPort();
			
			//this.bos = new BufferedOutputStream(this.socketServeur.getOutputStream());
			//this.bis = new BufferedInputStream(this.socketServeur.getInputStream());
			
			this.enAttente();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void enAttente()
	{		
		try
		{
			System.out.println("Serveur en attente sur " + InetAddress.getLocalHost() + " : " + this.socketServeur.getLocalPort());

			while(true)
			{
				this.socketClient = socketServeur.accept();
				System.out.println("Client " + socketClient.getInetAddress() + " connect�");
				
				this.entree = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
				this.sortie = new PrintWriter(this.socketClient.getOutputStream());
			}
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public void envoyerDonnees(String req)
	{
		String requete = "Commande depuis le client\r\n";
		
		try
		{			
			bos.write(requete.getBytes());
			bos.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void envoyerRequete(String req)
	{
		String requete = "Commande depuis le client\r\n";
		
		try
		{			
			
			sortie.println(requete);
			bos.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String ecouter()
	{		
		String contenu = "";
		int stream;
		
		try
		{
			while((stream = bis.read()) != -1)
				contenu += (char)stream;
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return contenu;
	}
	
	public void deconnecter()
	{
		try 
		{
			this.bos.close();
			this.bis.close();

			this.entree.close();
			this.sortie.close();
			
			this.socketClient.close();
			this.socketServeur.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
