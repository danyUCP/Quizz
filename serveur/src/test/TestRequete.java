package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import data.BDConnexion;
import data.RequeteManager;

public class TestRequete {

	public static void main(String[] args) 
	{
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in)) ;
		
		BDConnexion connexion = BDConnexion.connecter();
		RequeteManager reqManager = new RequeteManager();
		
		String req;
		
		try 
		{
			do
			{
				req = clavier.readLine();
				reqManager.executerReq(req);
			}while(!req.equals("DISCONNECT"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
		

	}

}
