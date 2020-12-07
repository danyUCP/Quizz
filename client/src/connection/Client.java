package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client
{
	private InetAddress ip;
	private static Integer port;
	private Socket socket;
	
	private BufferedReader entree;
	private PrintWriter sortie;
	
	public Client()
	{
		try
		{
			LogClient log = new LogClient();
			if(port == null)
				port = 2021;
			this.ip = InetAddress.getByName("127.0.0.1");
			this.socket = new Socket(ip, port);
			
						
			this.entree = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.sortie = new PrintWriter(this.socket.getOutputStream());
			
			System.out.println("Port de communication côté client : " + socket.getLocalPort());
			System.out.println("Nom de l'hôte distant : " + socket.getRemoteSocketAddress());
			LogClient.trace("Le client s'est connecté à : " + socket.getRemoteSocketAddress());
			
		} 
		catch (ConnectException e)
		{
			String err = "LE CLIENT N'A PAS PU SE CONNECTER A L'ADRESSE " + ip + "/" + port + " ! ";
			System.err.println(err);
			LogClient.trace(err);		
		}
		catch (SocketException e)
		{
			String err = "LE CLIENT N'A PAS PU SE CONNECTER A L'ADRESSE " + ip + "/" + port + " ! ";
			System.err.println(err);
			LogClient.trace(err);		
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
			
			if(reponse.equals("DISCONNECT"))
			{
				deconnecter();
				return null;
			}
		} 
		catch(SocketException e){
			System.err.println("LA CONNEXION AU SERVEUR A ETE INTERROMPUE ! ");
			reponse = "ERREUR:La connexion au serveur a été interrompue !";
			deconnecter();
			
		}
		catch(NullPointerException e)
		{
			System.err.println("AUCUNE CONNEXION AVEC LE SERVEUR DETECTEE ! ");
			reponse = "ERREUR:Aucune connexion avec le serveur detectée !";
			deconnecter();
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return reponse;
	}
	
	public boolean estDeconnecte()
	{
		if(socket == null)
			return true;
		
		return socket.isClosed();
	}
	
	
	
	
	public static Integer getPort() 
	{
		return port;
	}


	public static void setPort(Integer port) 
	{
		Client.port = port;
	}


	public void deconnecter()
	{
		try 
		{
			if(entree != null)
				this.entree.close();
			
			if(sortie != null)
				this.sortie.close();

			if(socket != null)
				this.socket.close();
			
			System.out.println("Client déconnecté");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	



}
