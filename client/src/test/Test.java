package test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import data.Joueur;
import data.Partie;

public class Test 
{
	public static void main(String[] args) 
	{
		// bonjour Danieeeeeel !
		Joueur rebecca = new Joueur("Rebecca");
		Joueur laura = new Joueur("Laura");
		
		Partie p = new Partie();
		
		p.creerPartie(rebecca);		
		p.rejoindrePartie(laura);
		
		while(p.getNbManches() < 6)
			p.mancheSuivante();

		p.calculerScorePartie();

		System.out.println(p);

		/*
		Socket soc = null;
		
		try 
		{
			soc = new Socket("127.0.0.1", 2020);

			System.out.println("Port de communication côté client : " + soc.getLocalPort());
			System.out.println("Nom de l'hôte distant : " + soc.getRemoteSocketAddress());

			//Ecriture dans le flux
			String requete = "Commande depuis le client\r\n";
			
			BufferedOutputStream bos = new BufferedOutputStream(soc.getOutputStream());
			bos.write(requete.getBytes());
			bos.flush();
			
			bos.write(("" + soc.getLocalPort()).getBytes());
			bos.flush();
			//Lecture dans le flux
			BufferedInputStream bis = new BufferedInputStream(soc.getInputStream());
			
			String contenu = "";
			int stream;
			while((stream = bis.read()) != -1)
			{
				contenu += (char)stream;
			}
			
			System.out.println(contenu);
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				soc.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		*/
	}

}
