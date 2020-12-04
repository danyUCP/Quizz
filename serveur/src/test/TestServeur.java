package test;

import data.Serveur;

public class TestServeur 
{
	public static void main(String[] args) 
	{
		
		Serveur serveur;
		
		try
		{
			if(args.length == 1)
			{
				int port = Integer.parseInt(args[0]);
				serveur = new Serveur(port);
			}
			else
				serveur = new Serveur(2021);

		}
		catch(NumberFormatException e)
		{
			serveur = new Serveur(2021);
		}
		
		
		//serveur.demarrer();

		//serveur.deconnecter();



	}
}
