package test;

import connection.Client;
import ihm.Fenetre;

public class TestFenetre 
{


	public static void main(String[] args) 
	{
		if(args.length == 1)
			Client.setPort(Integer.parseInt(args[0]));
			
		Fenetre test = new Fenetre();
	}

}
