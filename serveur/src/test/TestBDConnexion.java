package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import data.BDConnexion;

public class TestBDConnexion 
{

	public static void main(String[] args) 
	{
		BDConnexion connexion = BDConnexion.connecter();
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)) ;
		
		System.out.println(BDConnexion.getInfoParties("3"));
		
		/*
		BDConnexion.executerRequete("SELECT * FROM compte");
		
		try 
		{
			System.out.print("Pseudo : ");
			String pseudo = clavier.readLine();
			System.out.print("Mot de passe : ");
			String mdp = clavier.readLine();
			
			String requete = "CONNECT:" + pseudo + " " + mdp;
			System.out.println(requete);

			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		*/
		BDConnexion.deconnecter();
	}

}
