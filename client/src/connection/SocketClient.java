package connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import data.Joueur;
import data.Question;

public class SocketClient
{
	private InetAddress ipServeur;
	private int port;
	private Socket socket;
	
	private BufferedReader entree;
	private PrintWriter sortie;
	
	public SocketClient()
	{
		try
		{
			this.socket = new Socket("127.0.0.1", 2021);
			this.ipServeur = socket.getInetAddress();
			this.port = socket.getPort();
						
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
	
	
	public void envoyerRequete(String requete)
	{
		String reponse = null;
		
		do
		{
			try
			{	
				sortie.println(requete);
				System.out.println("Envoi : " + requete);
				sortie.flush();

				reponse = entree.readLine();
				System.out.println("Reçu : " + reponse);
			} 
			catch(SocketException e){
				System.err.println("LA CONNEXION AU SERVEUR A ETE INTERROMPUE ! ");
				break;
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}while(requete != null);
	}
	
	public String envoyerInstruction(String instruction)
	{

		String reponse = null;
		
		try
		{
			sortie.println(instruction);
			System.out.println("\nEnvoi : " + instruction);
			sortie.flush();

			reponse = entree.readLine();
			System.out.println("Reçu : " + reponse);
		} 
		catch(SocketException e){
			System.err.println("LA CONNEXION AU SERVEUR A ETE INTERROMPUE ! ");
			reponse = "ERREUR:La connexion au serveur a été interrompue !";
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return reponse;
	}
	
	
	
	
	public void deconnecter()
	{
		try 
		{
			this.entree.close();
			this.sortie.close();

			this.socket.close();
			System.out.println("Client déconnecté");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	



}
