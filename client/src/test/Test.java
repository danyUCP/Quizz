package test;

import data.Joueur;
import data.Partie;

public class Test 
{
	public static void main(String[] args) 
	{
		Joueur rebecca = new Joueur("Rebecca");
		Joueur laura = new Joueur("Laura");
		
		Partie p = new Partie();
		
		p.creerPartie(rebecca);		
		p.rejoindrePartie(laura);
		
		p.mancheSuivante();
		p.mancheSuivante();
		p.mancheSuivante();
		p.mancheSuivante();
		p.mancheSuivante();

		p.calculerScorePartie();

		System.out.println(p);
		
		/*
		Manche m1 = new Manche(rebecca, laura, "Test");
		
		Theme th1 = new Theme("Test");
		Question q1 = new Question("Qui a mangé la mousse au chocolat ?", "Edrisha", "Erice", "Gildas", "Daniel");
		Question q2 = new Question("Qui va gagner la prochaine Champions League ?", "PSG", "Barça", "Bayern", "Juventus");
		Question q3 = new Question("Qui est l'intrus ?", "Marc Lemaire", "Daniel François", "Laura Fustinoni", "Rebecca Simon");

		th1.ajouterQuestion(q1);
		th1.ajouterQuestion(q2);
		th1.ajouterQuestion(q3);
		
		m1.setTheme(th1);
		
		m1.ajouterReponseJ1(1);
		m1.comparerReponsesJ1(1, q1);
		
		m1.ajouterReponseJ1(3);
		m1.comparerReponsesJ1(3, q2);
		
		m1.ajouterReponseJ1(2);
		m1.comparerReponsesJ1(2, q3);

		m1.ajouterReponseJ2(0);
		m1.comparerReponsesJ2(0, q1);
		
		m1.ajouterReponseJ2(1);
		m1.comparerReponsesJ2(1, q2);
		
		m1.ajouterReponseJ2(3);
		m1.comparerReponsesJ2(3, q3);
		
		System.out.println(m1);
		*/
	}

}
