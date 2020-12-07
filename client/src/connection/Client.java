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
			
			System.out.println("Port de communication c�t� client : " + socket.getLocalPort());
			System.out.println("Nom de l'h�te distant : " + socket.getRemoteSocketAddress());
			LogClient.trace("Le client s'est connect� � : " + socket.getRemoteSocketAddress());
			
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
	
	
	
	public String envoyerInstruction(String instruction)
	{

		String reponse = null;
		
		try
		{
			sortie.println(instruction);
			System.out.println("\nEnvoi : " + instruction);
			sortie.flush();

			reponse = entree.readLine();
			System.out.println("Re�u : " + reponse);
			
			if(reponse.equals("DISCONNECT"))
			{
				deconnecter();
				return null;
			}
		} 
		catch(SocketException e)
		{
			String err = "LA CONNEXION AU SERVEUR A ETE INTERROMPUE ! ";
			System.err.println(err);
			LogClient.trace(err);		

			reponse = "ERREUR:La connexion au serveur a �t� interrompue !";
			deconnecter();
			
		}
		catch(NullPointerException e)
		{
			String err = "AUCUNE CONNEXION AVEC LE SERVEUR DETECTEE ! ";
			System.err.println(err);
			LogClient.trace(err);		
			
			reponse = "ERREUR:Aucune connexion avec le serveur detect�e !";
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
			
			System.out.println("Client d�connect�");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	



}
