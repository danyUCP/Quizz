package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

public class SocketServeur 
{
	private InetAddress ipClient;
	private int port;
	private ServerSocket socketServeur;
	
	private Socket socketClient;

	private BufferedReader entree;
	private PrintWriter sortie;
	
	
	public SocketServeur()
	{
		try
		{
			this.socketServeur = new ServerSocket(2021);
			this.ipClient = socketServeur.getInetAddress();
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
			System.out.println("Serveur en attente sur " + InetAddress.getLocalHost() + " : " + this.port);


			this.socketClient = socketServeur.accept();
			System.out.println("Client " + this.ipClient + " connecté");

			this.entree = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
			this.sortie = new PrintWriter(this.socketClient.getOutputStream());

			//this.ecouterRequete();
			this.demarrer();
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
	
	
	public String ecouterRequete()
	{		
		String requete = "";
		System.out.println("J'écoute");
		
		try
		{
			while((requete = entree.readLine()) != null)
			{
				System.out.println("Message reçu : " + requete);
				sortie.println("OK j'ai reçu : " + requete);
				sortie.flush();
				System.out.println("OK");
			}			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("FIN");
		
		return requete;
	}
	
	public void demarrer()
	{
		System.err.println("Lancement du traitement de la connexion cliente");

		boolean closeConnexion = false;
		//tant que la connexion est active, on traite les demandes
		while(!socketClient.isClosed()){

			try {
				
				//On attend la demande du client            
				String response = entree.readLine();
				System.err.println(response);

				//On traite la demande du client en fonction de la commande envoyée
				String toSend = "";

				switch(response.toUpperCase()){
				case "FULL":
					toSend = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM).format(new Date());
					break;
				case "DATE":
					toSend = DateFormat.getDateInstance(DateFormat.FULL).format(new Date());
					break;
				case "HOUR":
					toSend = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date());
					break;
				case "CLOSE":
					toSend = "Communication terminée"; 
					closeConnexion = true;
					break;
				default : 
					toSend = "Commande inconnu !";                     
					break;
				}

				//On envoie la réponse au client
				sortie.println(toSend);
				//Il FAUT IMPERATIVEMENT UTILISER flush()
				//Sinon les données ne seront pas transmises au client
				//et il attendra indéfiniment
				sortie.flush();

				if(closeConnexion){
					System.err.println("COMMANDE CLOSE DETECTEE ! ");
					sortie = null;
					entree = null;
					socketClient.close();
					break;
				}
			}catch(SocketException e){
				System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}         
		}
	}

	public void deconnecter()
	{
		try 
		{
			this.entree.close();
			this.sortie.close();
			
			if(this.socketClient != null)
			{
				//this.bos.close();
				//this.bis.close();

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
