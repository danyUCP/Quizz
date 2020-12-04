package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
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
	
	
	public Serveur(int port)
	{
		try
		{
			LogServeur log = new LogServeur();
			this.port = port;
				
			this.socketServeur = new ServerSocket(port);
			this.ipServeur = InetAddress.getLocalHost();
			this.demarrer();
		}
		catch (BindException e)
		{
			String err = "LE SERVEUR N'A PAS PU SE LANCER SUR LE PORT " + port + " ! ";
			System.err.println(err);
			LogServeur.trace(err);
			try 
			{
				this.port = 2021;
				this.socketServeur = new ServerSocket(port);
				this.ipServeur = InetAddress.getLocalHost();
				this.demarrer();

			} 
			catch (IOException e1) 
			{
				String err2 = "IMPOSSIBLE DE DEMARRER LE SERVEUR SUR LE PORT PAR DEFAUT. MERCI DE PRECISER UN PORT DIFFERENT";
				System.err.println(err2);
				LogServeur.trace(err2);
			}
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
			LogServeur.trace("Client " + socketClient.getInetAddress() + " connecté");

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
				
				if(reponse.equals("DISCONNECT"))
				{
					closeClient();
					demarrer();
					break;
				}
			}
			catch(SocketException e)
			{
				String err = "LA CONNEXION A ETE INTERROMPUE ! ";
				System.err.println(err);
				LogServeur.trace(err);
				closeClient();
				demarrer();
				break;
			}
			catch (NullPointerException e) 
			{
				String err = "LA CONNEXION A ETE INTERROMPUE ! ";
				System.err.println(err);
				LogServeur.trace(err);
				closeClient();
				demarrer();
				break;
			}	
			catch (IOException e) 
			{
				e.printStackTrace();
				break;
			}	

		}while(!instruction.equals("DISCONNECT") || this.socketClient.isClosed());

		
		this.deconnecter();
	}
	
	public void closeClient()
	{
		try 
		{
			this.entree.close();
			this.sortie.close();
			
			if(this.socketClient != null)
			{
				this.socketClient.close();
			}
			System.out.println("\nClient réinitialisé");

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
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
