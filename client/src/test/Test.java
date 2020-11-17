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

	}

}
