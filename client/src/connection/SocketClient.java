package connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient
{
	private InetAddress ipServeur;
	private int port;
	private Socket socket;
	
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	
	private BufferedReader entree;
	private PrintWriter sortie;
	
	public SocketClient()
	{
		try
		{
			this.socket = new Socket("127.0.0.1", 2020);
			this.ipServeur = socket.getInetAddress();
			this.port = socket.getPort();
			
			this.bos = new BufferedOutputStream(this.socket.getOutputStream());
			this.bis = new BufferedInputStream(this.socket.getInputStream());
			
			this.entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.sortie = new PrintWriter(this.socket.getOutputStream());
			System.out.println("Port de communication côté client : " + socket.getLocalPort());
			System.out.println("Nom de l'hôte distant : " + socket.getRemoteSocketAddress());
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
	
	public String ecouterRequete()
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

			this.socket.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	



}
