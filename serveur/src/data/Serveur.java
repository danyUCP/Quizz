package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Serveur 
{
	private InetAddress ipServeur;
	private int port;
	
	private ServerSocket socketServeur;
	private Socket socketClient;
	private BDConnexion bdConnect;
	
	private BufferedReader entree;
	private PrintWriter sortie;
	private RequeteManager reqManager;
	
	
	public Serveur()
	{
		try
		{
			this.socketServeur = new ServerSocket(2021);
			this.ipServeur = InetAddress.getLocalHost();
			this.port = socketServeur.getLocalPort();
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
			System.out.println("Serveur en attente sur " + this.ipServeur + " : " + this.port);

			this.socketClient = socketServeur.accept();
			System.out.println("Client " + socketClient.getInetAddress() + " connecté");

			this.entree = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
			this.sortie = new PrintWriter(this.socketClient.getOutputStream());
			this.reqManager = new RequeteManager();
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
	
	public void demarrer()
	{
		bdConnect = BDConnexion.connecter();
		this.enAttente();

		System.out.println("Serveur correctement démarré\n");
		
		//BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)) ;

		String instruction = "", reponse = "";
		do
		{
			try 
			{

				System.out.println("\nEn attente d'instruction");
				instruction = entree.readLine();
				reponse = reqManager.executerReq(instruction);

				System.out.println("Envoi de : " + reponse);
				sortie.println(reponse);
				sortie.flush();
			}
			catch(SocketException e){
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				break;
			}
			catch (NullPointerException e) 
			{
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				break;
			}	
			catch (IOException e) 
			{
				e.printStackTrace();
				break;
			}	

		}while(!instruction.equals("DISCONNECT") || !this.socketClient.isClosed());

		
		
	}
	
	
	
	public void deconnecter()
	{
		try 
		{
			this.entree.close();
			this.sortie.close();
			BDConnexion.deconnecter();		
			
			if(this.socketClient != null)
			{
				this.socketClient.close();
			}
			
			this.socketServeur.close();
			System.out.println("Serveur déconnecté");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	

}
